package fireaway.com.controller;

import fireaway.com.domainmodel.Usuario;
import fireaway.com.dto.UsuarioMoradorDto;
import fireaway.com.dto.UsuarioRequestDto;
import fireaway.com.dto.UsuarioResponseDto;
import fireaway.com.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/usuario")
@Tag(name = "Usuario Controller")
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
        List<UsuarioResponseDto> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }



    @Operation(summary = "Busca usuário por ID")
    @GetMapping("/usuarios/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<UsuarioResponseDto> getUsuarioById(@PathVariable Long id) {
        UsuarioResponseDto usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }


    @Operation(summary = "Deleta usuário")
    @DeleteMapping("/usuarios/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Usuario> deleteUsuario(@PathVariable Long id){
        usuarioService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/atualiza")
    @PreAuthorize("hasRole('MORADOR')")
    public ResponseEntity<UsuarioResponseDto> atualizarMeuCadastro(@RequestBody @Valid UsuarioMoradorDto dto,
                                                                   Authentication authentication) {
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        Long usuarioId = usuarioLogado.getId();

        UsuarioResponseDto atualizado = usuarioService.atualizarMorador(usuarioId, dto);
        return ResponseEntity.ok(atualizado);
    }



    @PutMapping("/altualiza/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<UsuarioResponseDto> atualizarUsuarioPorAdmin(@PathVariable Long id,
                                                                       @RequestBody @Valid UsuarioRequestDto dto) {
        UsuarioResponseDto usuarioAtualizado = usuarioService.atualizarUsuario(id, dto);
        return ResponseEntity.ok(usuarioAtualizado);
    }





}
