package fireaway.com.domainmodel;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter @Setter
@NoArgsConstructor
public class Endereco {

    @NotBlank(message = "Informe o logradouro completo do seu endereço")
    private String logradouro;

    @NotBlank(message = "Informe a cidade")
    private String cidade;

    @NotBlank(message = "Informe o estado")
    private String estado;

    @NotBlank(message = "O CEP é obrigatório")
    @Size(min = 8,max = 8, message = "CEP precisa ter 6 caracteres")
    private String cep;

}
