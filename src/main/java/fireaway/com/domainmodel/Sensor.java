package fireaway.com.domainmodel;


import fireaway.com.domainmodel.enuns.TipoSensor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.print.DocFlavor;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Sensor {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoSensor tipo;

    private Double latitude;
    private Double longitude;

    private String ativo;



    @OneToMany(mappedBy = "sensor")
    private List<Monitoramento> monitoramentos;
}
