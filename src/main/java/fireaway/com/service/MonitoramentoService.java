package fireaway.com.service;

import fireaway.com.domainmodel.Alerta;
import fireaway.com.domainmodel.Monitoramento;
import fireaway.com.domainmodel.Sensor;
import fireaway.com.domainmodel.enuns.NivelAlerta;
import fireaway.com.domainmodel.enuns.StatusAlerta;
import fireaway.com.domainmodel.enuns.TipoSensor;
import fireaway.com.dto.MonitoramentoRequestDto;
import fireaway.com.exceptions.ResourceNotFoundException;
import fireaway.com.repository.AlertaRepository;
import fireaway.com.repository.MonitoramentoRepository;
import fireaway.com.repository.SensorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MonitoramentoService {

    private final MonitoramentoRepository monitoramentoRepository;
    private final AlertaRepository alertaRepository;
    private final SensorRepository sensorRepository;


    private final float LIMITE_TEMPERATURA = 50.0f;
    private final float LIMITE_UMIDADE = 80.0f;
    private final float LIMITE_FUMACA = 5.0f;
    private final float LIMITE_MOVIMENTO = 1.0f;

    public MonitoramentoService(MonitoramentoRepository monitoramentoRepository,
                                AlertaRepository alertaRepository,
                                SensorRepository sensorRepository) {
        this.monitoramentoRepository = monitoramentoRepository;
        this.alertaRepository = alertaRepository;
        this.sensorRepository = sensorRepository;
    }

    public void processarMonitoramento(Monitoramento monitoramento) {

        Sensor sensor = monitoramento.getSensor();
        TipoSensor tipo = sensor.getTipo();
        float limite = getLimitePorTipo(tipo);


        if (monitoramento.getValor() > limite) {
            gerarAlertaIndividual(sensor, monitoramento);
        }


        List<Sensor> todosSensores = sensorRepository.findAll();


        final double PROXIMIDADE = 0.01;
        List<Sensor> sensoresProximos = todosSensores.stream()
                .filter(s -> !s.getId().equals(sensor.getId()) &&
                        Math.abs(s.getLatitude() - sensor.getLatitude()) < PROXIMIDADE &&
                        Math.abs(s.getLongitude() - sensor.getLongitude()) < PROXIMIDADE)
                .toList();


        long sensoresEmRisco = sensoresProximos.stream().filter(s -> {
            List<Monitoramento> ultimos = monitoramentoRepository.findTop3BySensorIdOrderByDataHoraDesc(s.getId());
            return ultimos.stream().anyMatch(m -> m.getValor() > getLimitePorTipo(s.getTipo()));
        }).count();

        if (sensoresEmRisco >= 2) {
            gerarAlertaColetivo(sensor, sensoresProximos);
        }
    }

    private void gerarAlertaIndividual(Sensor sensor, Monitoramento monitoramento) {
        Alerta alerta = new Alerta();
        alerta.setNivel(NivelAlerta.CRITICO);
        alerta.setDescricao("Sensor " + sensor.getTipo() + " ultrapassou limite isoladamente.");
        alerta.setDataHora(LocalDateTime.now());
        alerta.setStatus(StatusAlerta.ABERTO);
        alerta.setLatitude(sensor.getLatitude());
        alerta.setLongitude(sensor.getLongitude());
        alerta.setSensor(sensor);

        alertaRepository.save(alerta);
    }

    private void gerarAlertaColetivo(Sensor sensorBase, List<Sensor> sensoresProximos) {
        Alerta alerta = new Alerta();
        alerta.setNivel(NivelAlerta.CRITICO);
        alerta.setDescricao("Risco de incêndio: múltiplos sensores próximos detectaram situação crítica.");
        alerta.setDataHora(LocalDateTime.now());
        alerta.setStatus(StatusAlerta.ABERTO);
        alerta.setLatitude(sensorBase.getLatitude());
        alerta.setLongitude(sensorBase.getLongitude());
        alerta.setSensor(sensorBase);

        alertaRepository.save(alerta);
    }

    private float getLimitePorTipo(TipoSensor tipo) {
        return switch (tipo) {
            case TEMPERATURA -> 50.0f;
            case UMIDADE -> 80.0f;
            case FUMACA -> 5.0f;
            case MOVIMENTO -> 1.0f;
        };
    }


    public void salvarMonitoramentoEVerificarAlerta(Monitoramento monitoramento) {
        monitoramentoRepository.save(monitoramento);
        processarMonitoramento(monitoramento);
    }

    public List<Monitoramento> listarMonitoramento() {
        return monitoramentoRepository.findAll();
    }

    public Monitoramento buscarMonitoramentoPorId(Long id) {
        return monitoramentoRepository.findById(id).orElse(null);
    }

    public void deleteMonitoramento(Long id) {
        monitoramentoRepository.deleteById(id);
    }

    public Monitoramento updateMonitoramento(MonitoramentoRequestDto monitoramentoDto, Long id) {
        Optional<Monitoramento> optionalMonitoramento = monitoramentoRepository.findById(id);

        if (optionalMonitoramento.isPresent()) {
            Monitoramento monitoramento = optionalMonitoramento.get();
            monitoramento.setSensor(monitoramento.getSensor());
            monitoramento.setValor(monitoramento.getValor());
            monitoramento.setDataHora(monitoramento.getDataHora());

            return monitoramentoRepository.save(monitoramento);
        }else{
            throw new ResourceNotFoundException("Monitoramento", id);
        }
    }




}


