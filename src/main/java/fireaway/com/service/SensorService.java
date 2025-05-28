package fireaway.com.service;


import fireaway.com.domainmodel.Sensor;
import fireaway.com.dto.SensorRequestDto;
import fireaway.com.repository.SensorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorService {

    private final SensorRepository sensorRepository;

    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public Sensor save(SensorRequestDto sensor) {
        Sensor sensorEntity = new Sensor();
        sensorEntity.setTipo(sensor.getTipo());
        sensorEntity.setLatitude(sensor.getLatitude());
        sensorEntity.setLongitude(sensor.getLongitude());
        sensorEntity.setAtivo(sensor.getAtivo());
        return sensorRepository.save(sensorEntity);
    }

    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }
}
