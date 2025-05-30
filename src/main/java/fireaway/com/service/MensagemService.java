package fireaway.com.service;


import fireaway.com.domainmodel.Mensagem;
import fireaway.com.domainmodel.Usuario;
import fireaway.com.dto.MensagemAuthDto;
import fireaway.com.dto.MensagemResponseDto;
import fireaway.com.repository.MensagemRepository;
import fireaway.com.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MensagemService {

    private final MensagemRepository mensagemRepository;
    private final UsuarioRepository usuarioRepository;

    public MensagemService(MensagemRepository mensagemRepository, UsuarioRepository usuarioRepository) {
        this.mensagemRepository = mensagemRepository;
        this.usuarioRepository = usuarioRepository;
    }


    public Mensagem enviarMensagem(Usuario emissor, MensagemAuthDto mensagemDTO) {
        Usuario receptor = usuarioRepository.findByEmail(mensagemDTO.getEmailReceptor())
                .orElseThrow(() -> new RuntimeException("Receptor não encontrado"));

        Mensagem mensagem = new Mensagem();
        mensagem.setEmissor(emissor);
        mensagem.setReceptor(receptor);
        mensagem.setConteudo(mensagemDTO.getConteudo());
        mensagem.setDataHora(LocalDateTime.now());

        return mensagemRepository.save(mensagem);
    }

    public List<MensagemResponseDto> listarMensagensRecebidasComDadosEmissor(Usuario usuario) {
        List<Mensagem> mensagens = mensagemRepository.findByReceptor(usuario);

        return mensagens.stream().map(mensagem -> {
            MensagemResponseDto dto = new MensagemResponseDto();
            dto.setConteudo(mensagem.getConteudo());
            dto.setDataHora(mensagem.getDataHora());


            dto.setNomeEmissor(mensagem.getEmissor().getNome());
            dto.setEmailEmissor(mensagem.getEmissor().getEmail());
            dto.setPerfilEmissor(mensagem.getEmissor().getPerfil().name());

            return dto;
        }).collect(Collectors.toList());
    }



    public MensagemResponseDto toResponseDto(Mensagem mensagem) {
        MensagemResponseDto dto = new MensagemResponseDto();
        dto.setConteudo(mensagem.getConteudo());
        dto.setDataHora(mensagem.getDataHora());
        dto.setNomeEmissor(mensagem.getEmissor().getNome());
        dto.setEmailEmissor(mensagem.getEmissor().getEmail());
        dto.setPerfilEmissor(mensagem.getEmissor().getPerfil().name());
        return dto;
    }

}
