package fireaway.com.controller;

import fireaway.com.domainmodel.Usuario;
import fireaway.com.dto.UsuarioMoradorDto;
import fireaway.com.dto.UsuarioRequestDto;
import fireaway.com.dto.UsuarioResponseDto;
import fireaway.com.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/usuario")
@Tag(name = "Usuario Controller", description = "Gerencia dos usuários cadastrados no sistema")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    @Operation(summary = "Cadastro de novos Moradores no sistema")
    @PostMapping("/cadastro/morador")
    public ResponseEntity<?> saveMorador(@RequestBody @Valid UsuarioMoradorDto dto) {
        usuarioService.saveMorador(dto);
        return ResponseEntity.ok(dto);
    }



    @Operation(summary = "Cadastro de usuário internos no sistema")
    @PostMapping("/cadastro/admin")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<UsuarioResponseDto> saveForAdmin(@RequestBody @Valid UsuarioRequestDto dto) {
        UsuarioResponseDto response = usuarioService.saveUsuario(dto);
        return ResponseEntity.ok(response);
    }



    @Operation(summary = "Lista todos os usuários cadastrados")
    @GetMapping("/usuarios")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<UsuarioResponseDto>> listUsuarios() {
        List<UsuarioResponseDto> usuarios = usuarioService.listAllUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Lista todos os usuários com paginação")
    @GetMapping("/pageable")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Page<UsuarioResponseDto>> listUsuariosPaged(Pageable pageable) {
        Page<UsuarioResponseDto> usuario = usuarioService.listAllPaged(pageable);
        return ResponseEntity.ok().body(usuario);
    }


    @Operation(summary = "Busca usuário por ID")
    @GetMapping("/usuarios/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<UsuarioResponseDto> getUsuarioById(@PathVariable Long id) {
        UsuarioResponseDto usuario = usuarioService.findById(id);
        return ResponseEntity.ok(usuario);
    }


    @Operation(summary = "Deleta usuário")
    @DeleteMapping("/usuarios/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Usuario> deleteUsuario(@PathVariable Long id){
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Atualiza dados de cadastro do Usuário Morador")
    @PutMapping("/atualiza")
    @PreAuthorize("hasRole('MORADOR')")
    public ResponseEntity<UsuarioResponseDto> atualizarCadastroMorador(@RequestBody @Valid UsuarioMoradorDto dto,
                                                                   Authentication authentication) {
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        Long usuarioId = usuarioLogado.getId();

        UsuarioResponseDto atualizado = usuarioService.updateMorador(usuarioId, dto);
        return ResponseEntity.ok(atualizado);
    }



    @Operation(summary = "Administrador atualiza dados de cadastro dos demais Usuários")
    @PutMapping("/altualiza/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<UsuarioResponseDto> atualizarUsuarioPorAdmin(@PathVariable Long id,
                                                                       @RequestBody @Valid UsuarioRequestDto dto) {
        UsuarioResponseDto usuarioAtualizado = usuarioService.updateUsuario(id, dto);
        return ResponseEntity.ok(usuarioAtualizado);
    }





}
