package fireaway.com.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "O email precisa ser informado")
    @Email
    private String email;

    @NotBlank(message = "Você precisa fornecer uma senha")
    private String senha;
}
