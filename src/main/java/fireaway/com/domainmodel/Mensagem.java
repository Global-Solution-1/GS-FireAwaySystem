package fireaway.com.domainmodel;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Mensagem {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "emissor_id")
    private Usuario emissor;

    @ManyToOne
    @JoinColumn(name = "receptor_id")
    private Usuario receptor;


    private String conteudo;
    private LocalDateTime dataHora;
}
