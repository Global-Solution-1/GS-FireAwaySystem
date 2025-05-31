package fireaway.com.controller;


import fireaway.com.domainmodel.Sensor;
import fireaway.com.dto.SensorRequestDto;
import fireaway.com.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensor")
@Tag(name = "Sensor Controller", description = "Gerencia os sensores cadastrados que detectam dados")
public class SensorController {

    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }


    @Operation(summary = "Lista todos os sensores cadastrados")
    @GetMapping("/sensores")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SOCORRISTA')")
    public ResponseEntity<List<Sensor>> getAllSensors() {
        List<Sensor> sensores =  sensorService.findAll();
        return new ResponseEntity<>(sensores, HttpStatus.OK);
    }

    @Operation(summary = "Lista todos os sensores com paginação")
    @GetMapping("/pageable")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Page<Sensor>> getAllSensorsPaged(Pageable pageable) {
        Page<Sensor> sensor = sensorService.findAllPaged(pageable);
        return ResponseEntity.ok().body(sensor);
    }


    @Operation(summary = "Cadastra sensor")
    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<String> saveSensor(@RequestBody @Valid SensorRequestDto sensor) {
        Sensor savedSensor = sensorService.save(sensor);
        return ResponseEntity.ok("Sensor cadastrado com sucesso!");
    }



    @Operation(summary = "Busca sensor por ID")
    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SOCORRISTA')")
    public ResponseEntity<Sensor> getSensorById(@PathVariable Long id) {
        Sensor sensorId = sensorService.findById(id);
        return new ResponseEntity<>(sensorId, HttpStatus.OK);
    }


    @Operation(summary = "Deleta sensor")
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Sensor> deleteSensor(@PathVariable Long id) {
        sensorService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @Operation(summary = "Atualiza sensor")
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Sensor> updateSensor(@PathVariable Long id, @RequestBody @Valid SensorRequestDto sensor) {
        Sensor sensorUpdated = sensorService.updateSensor(id, sensor);
        return new ResponseEntity<>(sensorUpdated, HttpStatus.OK);
    }
}
