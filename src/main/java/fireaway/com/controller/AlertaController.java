package fireaway.com.controller;

import fireaway.com.domainmodel.Alerta;
import fireaway.com.domainmodel.Usuario;
import fireaway.com.dto.AlertaRequestDto;
import fireaway.com.dto.AlertaResponseDto;
import fireaway.com.service.AlertaService;
import fireaway.com.service.GeocodingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/alertas")
@Tag(name = "Alerta Controller", description = "Gerencia os alertas gerados pelo monitoramento de sensores")
public class AlertaController {

    private final AlertaService alertaService;
    private final GeocodingService geocodingService;

    public AlertaController(AlertaService alertaService, GeocodingService geocodingService) {
        this.alertaService = alertaService;
        this.geocodingService = geocodingService;
    }

    @Operation(summary = "Lista todos os alertas cadastrados")
    @GetMapping("/todos")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SOCORRISTA')")
    public ResponseEntity<List<AlertaResponseDto>> listarTodos() {
        return ResponseEntity.ok(alertaService.listarTodos());
    }


    @Operation(summary = "Lista apenas os alarmes gerados por sensores próximos a localização do Usuário Morador")
    @GetMapping("/proximos")
    @PreAuthorize("hasRole('MORADOR')")
    public ResponseEntity<?> listarProximos() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String cep = usuario.getEndereco().getCep();

        double[] latLon = geocodingService.getLatLonFromCep(cep);
        if (latLon == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Não foi possível localizar o CEP informado.");
        }

        double raioKm = 10.0;

        List<AlertaResponseDto> alertas = alertaService.listarPorProximidade(latLon[0], latLon[1], raioKm);

        if (alertas.isEmpty()) {
            return ResponseEntity.ok("Não há sensores próximos cadastrados ainda.");
        }

        return ResponseEntity.ok(alertas);
    }


    @Operation(summary = "Busca alerta por ID")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SOCORRISTA')")
    @GetMapping("{id}")
    public ResponseEntity<Alerta> buscarPorId(@PathVariable Long id) {
        Alerta alertaId = alertaService.findById(id);
        return ResponseEntity.ok(alertaId);
    }


    @Operation(summary = "Deleta alerta por ID")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SOCORRISTA')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Alerta> deleteAlerta(@PathVariable Long id) {
        alertaService.deleteAlerta(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SOCORRISTA')")
    public ResponseEntity<Alerta> saveAlerta(@RequestBody AlertaRequestDto alerta){
        Alerta alertaCreated = alertaService.saveAlerta(alerta);
        return ResponseEntity.status(HttpStatus.CREATED).body(alertaCreated);
    }



}
