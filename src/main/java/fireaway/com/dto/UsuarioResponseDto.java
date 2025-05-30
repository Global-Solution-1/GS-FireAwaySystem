package fireaway.com.dto;

import fireaway.com.domainmodel.Usuario;
import fireaway.com.domainmodel.enuns.PerfilUsuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioResponseDto {

    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private PerfilUsuario perfil;

    public UsuarioResponseDto(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.cpf = usuario.getCpf();
        this.telefone = usuario.getTelefone();
        this.perfil = usuario.getPerfil();
    }

}
