package fireaway.com.domainmodel;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import fireaway.com.domainmodel.enuns.TipoSensor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String status;



    @OneToMany(mappedBy = "sensor")
    @JsonManagedReference
    private List<Monitoramento> monitoramentos;
}
