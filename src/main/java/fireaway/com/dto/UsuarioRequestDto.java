package fireaway.com.dto;

import fireaway.com.domainmodel.Usuario;
import fireaway.com.domainmodel.enuns.PerfilUsuario;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class UsuarioRequestDto {

    private String nome;

    private String email;

    private String cpf;
    private String senha;

    @Enumerated(EnumType.STRING)
    private PerfilUsuario perfil;

    public UsuarioRequestDto(Usuario usuario) {
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.cpf = usuario.getCpf();
        this.senha = usuario.getSenha();
        this.perfil = usuario.getPerfil();
    }
}
