package fireaway.com.dto;

import fireaway.com.domainmodel.enuns.PerfilUsuario;
import fireaway.com.domainmodel.enuns.TipoSensor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginResponseDto {

    private String token;
    private PerfilUsuario perfil;
    private String nome;

    public LoginResponseDto(String token, PerfilUsuario perfil, String nome) {
        this.token = token;
        this.perfil = perfil;
        this.nome = nome;
    }
}
