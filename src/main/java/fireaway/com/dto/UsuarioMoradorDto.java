package fireaway.com.dto;

import fireaway.com.domainmodel.Endereco;
import fireaway.com.domainmodel.Usuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@NoArgsConstructor
public class UsuarioMoradorDto {

    @NotBlank(message = "Fornecer seu nome obrigatório")
    private String nome;

    @NotBlank(message = "O email precisa ser fornecido")
    @Email
    private String email;


    @NotBlank(message = "Fornecer o CPF é obrigatório")
    @Size(min = 11, max = 11, message = "O CPF precisa ter 11 caracteres")
    private String cpf;

    @NotBlank(message = "Você precisa fornecer uma senha")
    @Size(min = 8, max= 12, message = "Sua senha deve ter entre 8 e 12 caracteres")
    private String senha;

    @NotNull(message = "Endereço é obrigatório")
    @Valid
    private Endereco endereco;

    @NotBlank(message = "O telefone precisa ser fornecido")
    @Size(min = 10, max = 13)
    private String telefone;

    public UsuarioMoradorDto(Usuario usuario) {
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.cpf = usuario.getCpf();
        this.senha = usuario.getSenha();
        this.endereco = usuario.getEndereco();
        this.telefone = usuario.getTelefone();
    }

}
