package fireaway.com.controller;


import fireaway.com.domainmodel.Monitoramento;
import fireaway.com.domainmodel.Sensor;
import fireaway.com.dto.MonitoramentoRequestDto;
import fireaway.com.repository.SensorRepository;
import fireaway.com.service.MonitoramentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/monitoramento")
@Tag(name = "Monitoramento Controller")
public class MonitoramentoController {
    private final MonitoramentoService monitoramentoService;
    private final SensorRepository sensorRepository;

    public MonitoramentoController(MonitoramentoService monitoramentoService, SensorRepository sensorRepository) {
        this.monitoramentoService = monitoramentoService;
        this.sensorRepository = sensorRepository;
    }


    @Operation(summary = "Cadastro de monitoramentos")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<String> cadastrarMonitoramento(@RequestBody @Valid MonitoramentoRequestDto dto) {

        Sensor sensor = sensorRepository.findById(dto.getSensorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sensor não encontrado"));


        Monitoramento monitoramento = new Monitoramento();
        monitoramento.setSensor(sensor);
        monitoramento.setValor(dto.getValor());
        monitoramento.setDescricao(dto.getDescricao());
        monitoramento.setDataHora(LocalDateTime.now());


        monitoramentoService.salvarMonitoramentoEVerificarAlerta(monitoramento);

        return ResponseEntity.ok("Monitoramento cadastrado e alerta verificado");
    }


    @Operation(summary = "Listagem de monitoramentos cadastrados")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SOCORRISTA')")
    @GetMapping
    public ResponseEntity<List<Monitoramento>> listarMonitoramentos() {
        List<Monitoramento> monitoramentos = monitoramentoService.listarMonitoramento();
        return ResponseEntity.ok(monitoramentos);
    }



    @Operation(summary = "Busca monitoramento por ID")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SOCORRISTA')")
    @GetMapping("/{id}")
    public ResponseEntity<Monitoramento> buscarMonitoramentoPorId(@PathVariable Long id) {
        Monitoramento monitoramento = monitoramentoService.buscarMonitoramentoPorId(id);
        return ResponseEntity.ok(monitoramento);
    }



    @Operation(summary = "Deleta monitoramento")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirMonitoramento(@PathVariable Long id) {
        monitoramentoService.deleteMonitoramento(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Atualiza monitoramento")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarMonitoramento(@PathVariable Long id, @RequestBody @Valid MonitoramentoRequestDto dto) {
        monitoramentoService.updateMonitoramento(dto, id);
        return ResponseEntity.ok("Monitoramento atualizado com sucesso");
    }


}
