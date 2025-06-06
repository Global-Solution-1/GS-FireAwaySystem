package fireaway.com.dto;


import fireaway.com.domainmodel.Usuario;
import fireaway.com.domainmodel.enuns.PerfilUsuario;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class UsuarioRequestDto {

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


    @NotNull(message = "O perfil do usuário precisa ser fornecido")
    private PerfilUsuario perfil;

    @NotBlank(message = "O telefone precisa ser fornecido")
    @Size(min = 10, max = 13)
    private String telefone;

    public UsuarioRequestDto(Usuario usuario) {
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.cpf = usuario.getCpf();
        this.senha = usuario.getSenha();
        this.perfil = usuario.getPerfil();
        this.telefone = usuario.getTelefone();
    }
}
