package fireaway.com.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class MensagemAuthDto {

    @NotBlank(message = "É necessário fornecer o email do destinatário da mensagem")
    private String emailReceptor;

    @NotBlank(message = "O conteúdo da mensagem precisa ser inserido")
    private String conteudo;
}
