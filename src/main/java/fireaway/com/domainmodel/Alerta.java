package fireaway.com.domainmodel;


import fireaway.com.domainmodel.enuns.NivelAlerta;
import fireaway.com.domainmodel.enuns.StatusAlerta;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Alerta {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private NivelAlerta nivel;

    private String descricao;
    private Double latitude;
    private Double longitude;

    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    private StatusAlerta status;
}
