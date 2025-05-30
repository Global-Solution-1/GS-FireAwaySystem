package fireaway.com.dto;


import fireaway.com.domainmodel.Sensor;
import fireaway.com.domainmodel.enuns.TipoSensor;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SensorRequestDto {

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O tipo de sensor precisa ser definido")
    private TipoSensor tipo;

    @NotNull(message = "Forneça as coordenadas para uma orientação precisa")
    private Double latitude;

    @NotNull(message = "Forneça as coordenadas para uma orientação precisa")
    private Double longitude;

    @NotBlank(message = "Forneça o status do sensor a cadastrar")
    private String status;

    public SensorRequestDto(Sensor sensor){
        this.tipo = sensor.getTipo();
        this.latitude = sensor.getLatitude();
        this.longitude = sensor.getLongitude();
        this.status = sensor.getStatus();
    }
}
