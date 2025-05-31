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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        if (tipo == TipoSensor.MOVIMENTO) {
            if (monitoramento.getValor() == 1.0f) {
                gerarAlertaIndividual(sensor, monitoramento);
            }
        } else {
            if (monitoramento.getValor() > limite) {
                gerarAlertaIndividual(sensor, monitoramento);
            }
        }

        List<Sensor> todosSensores = sensorRepository.findAll();

        final double RAIO_KM = 1.0;

        List<Sensor> sensoresProximos = todosSensores.stream()
                .filter(s -> !s.getId().equals(sensor.getId()) && estaProximo(sensor, s, RAIO_KM))
                .toList();

        long sensoresEmRisco = sensoresProximos.stream().filter(s -> {
            List<Monitoramento> ultimos = monitoramentoRepository.findTop3BySensorIdOrderByDataHoraDesc(s.getId());
            return ultimos.stream().anyMatch(m -> {
                if (s.getTipo() == TipoSensor.MOVIMENTO) {
                    return m.getValor() == 1.0f;
                } else {
                    return m.getValor() > getLimitePorTipo(s.getTipo());
                }
            });
        }).count();

        if (sensoresEmRisco >= 2) {
            gerarAlertaColetivo(sensor, sensoresProximos);
        }
    }

    private boolean estaProximo(Sensor s1, Sensor s2, double raioKm) {
        double distancia = distancia(s1.getLatitude(), s1.getLongitude(), s2.getLatitude(), s2.getLongitude());
        return distancia <= raioKm;
    }

    private double distancia(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
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


    public void saveMonitoramento(Monitoramento monitoramento) {
        monitoramentoRepository.save(monitoramento);
        processarMonitoramento(monitoramento);
    }

    public List<Monitoramento> listAll() {
        return monitoramentoRepository.findAll();
    }

    public Page<Monitoramento> listAllPaged(Pageable pageable){
        return monitoramentoRepository.findAll(pageable);
    }

    public Monitoramento findById(Long id) {
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


