package fireaway.com.controller;

import fireaway.com.domainmodel.Mensagem;
import fireaway.com.domainmodel.Usuario;
import fireaway.com.dto.MensagemAuthDto;
import fireaway.com.dto.MensagemResponseDto;
import fireaway.com.service.MensagemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mensagem")
@Tag(name = "Mensagem Controller", description = "Gerencia as mensagens enviadas e recebidas entre os usuários")
public class MensagemController {

    private final MensagemService mensagemService;

    public MensagemController(MensagemService mensagemService) {
        this.mensagemService = mensagemService;
    }


    @Operation(summary = "Envio de mensagens entre usuários cadastrados")
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/enviar")
    public ResponseEntity<MensagemResponseDto> enviarMensagem(@RequestBody @Valid MensagemAuthDto mensagemDTO) {
        Usuario emissor = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Mensagem mensagem = mensagemService.enviarMensagem(emissor, mensagemDTO);
        MensagemResponseDto responseDto = mensagemService.toResponseDto(mensagem);
        return ResponseEntity.ok(responseDto);
    }


    @Operation(summary = "Retorna uma lista das mensagens recebidas")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/recebidas")
    public ResponseEntity<List<MensagemResponseDto>> listarRecebidas() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<MensagemResponseDto> mensagens = mensagemService.listarMensagensRecebidasComDadosEmissor(usuario);
        return ResponseEntity.ok(mensagens);
    }

}
