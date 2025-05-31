package fireaway.com.service;


import fireaway.com.domainmodel.Sensor;
import fireaway.com.dto.SensorRequestDto;
import fireaway.com.exceptions.ResourceNotFoundException;
import fireaway.com.repository.SensorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        sensorEntity.setStatus(sensor.getStatus());
        return sensorRepository.save(sensorEntity);
    }

    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }

    public Page<Sensor> findAllPaged(Pageable pageable) {
        return sensorRepository.findAll(pageable);
    }

    public Sensor findById(Long id) {
        return sensorRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Sensor", id));
    }

    public void deleteById(Long id) {
        sensorRepository.deleteById(id);
    }

    public Sensor updateSensor(Long id, SensorRequestDto sensorDto) {
        Optional<Sensor> optionalSensor = sensorRepository.findById(id);

        if (optionalSensor.isPresent()) {
            Sensor existingSensor = optionalSensor.get();
            existingSensor.setTipo(sensorDto.getTipo());
            existingSensor.setLatitude(sensorDto.getLatitude());
            existingSensor.setLongitude(sensorDto.getLongitude());
            existingSensor.setStatus(sensorDto.getStatus());

            return sensorRepository.save(existingSensor);
        } else {
            throw new ResourceNotFoundException("Sensor", id);
        }
    }

}
