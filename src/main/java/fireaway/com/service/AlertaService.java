package fireaway.com.service;
import fireaway.com.domainmodel.Alerta;
import fireaway.com.domainmodel.Sensor;
import fireaway.com.dto.AlertaRequestDto;
import fireaway.com.dto.AlertaResponseDto;
import fireaway.com.exceptions.ResourceNotFoundException;
import fireaway.com.repository.AlertaRepository;
import fireaway.com.repository.SensorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertaService {

    private final AlertaRepository alertaRepository;
    private final SensorRepository sensorRepository;

    public AlertaService(AlertaRepository alertaRepository, SensorRepository sensorRepository) {
        this.alertaRepository = alertaRepository;
        this.sensorRepository = sensorRepository;
    }

    public List<AlertaResponseDto> listarTodos() {
        List<Alerta> alertas = alertaRepository.findAll();
        return alertas.stream().map(a -> {
            AlertaResponseDto dto = new AlertaResponseDto();
            dto.setId(a.getId());
            dto.setNivel(a.getNivel());
            dto.setDescricao(a.getDescricao());
            dto.setLatitude(a.getLatitude());
            dto.setLongitude(a.getLongitude());
            dto.setDataHora(a.getDataHora());
            dto.setStatus(a.getStatus());

            return dto;
        }).collect(Collectors.toList());
    }


    public List<AlertaResponseDto> listarPorProximidade(double userLat, double userLon, double raio) {
        List<Alerta> todos = alertaRepository.findAll();

        return todos.stream()
                .filter(alerta -> {
                    double distancia = distancia(alerta.getLatitude(), alerta.getLongitude(), userLat, userLon);
                    return distancia <= raio;
                })
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Alerta saveAlerta(AlertaRequestDto dto) {
        Alerta alerta = new Alerta();
        alerta.setLatitude(dto.getLatitude());
        alerta.setLongitude(dto.getLongitude());
        alerta.setNivel(dto.getNivel());
        alerta.setDescricao(dto.getDescricao());
        alerta.setDataHora(dto.getDataHora());
        alerta.setStatus(dto.getStatus());

        if (dto.getSensorId() != null) {
            Sensor sensor = sensorRepository.findById(dto.getSensorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Sensor", dto.getSensorId()));
            alerta.setSensor(sensor);
        }

        return alertaRepository.save(alerta);
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

    public Alerta findById(Long id) {
        return alertaRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Alerta", id));
    }

    public void deleteAlerta(Long id){
        Alerta alerta = alertaRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Alerta", id));
        alertaRepository.delete(alerta);
    }

    public AlertaResponseDto toDto(Alerta alerta) {
        AlertaResponseDto dto = new AlertaResponseDto();
        dto.setId(alerta.getId());
        dto.setNivel(alerta.getNivel());
        dto.setDescricao(alerta.getDescricao());
        dto.setLatitude(alerta.getLatitude());
        dto.setLongitude(alerta.getLongitude());
        dto.setDataHora(alerta.getDataHora());
        dto.setStatus(alerta.getStatus());
        return dto;
    }






}

