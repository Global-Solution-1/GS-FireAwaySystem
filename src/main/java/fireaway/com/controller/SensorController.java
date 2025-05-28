package fireaway.com.controller;


import fireaway.com.domainmodel.Sensor;
import fireaway.com.dto.SensorRequestDto;
import fireaway.com.repository.SensorRepository;
import fireaway.com.service.SensorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensor")
public class SensorController {

    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping("/sensores")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'BOMBEIRO')")
    public ResponseEntity<List<Sensor>> getAllSensors() {
        List<Sensor> sensores =  sensorService.findAll();
        return new ResponseEntity<>(sensores, HttpStatus.OK);
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Sensor> saveSensor(@RequestBody SensorRequestDto sensor) {
        Sensor savedSensor = sensorService.save(sensor);
        return new ResponseEntity<>(savedSensor, HttpStatus.OK);
    }
}
