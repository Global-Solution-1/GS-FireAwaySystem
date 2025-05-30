package fireaway.com.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;


@Getter @Setter
@NoArgsConstructor
public class MensagemResponseDto {

    private String conteudo;
    private LocalDateTime dataHora;
    private String nomeEmissor;
    private String emailEmissor;
    private String perfilEmissor;
}
