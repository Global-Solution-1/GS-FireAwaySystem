package fireaway.com.domainmodel;


import fireaway.com.domainmodel.enuns.TipoSensor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Sensor {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoSensor tipo;

    private Double latitude;
    private Double longitude;

    private boolean ativo;

    @OneToMany(mappedBy = "sensor")
    private List<Monitoramento> monitoramentos;
}
