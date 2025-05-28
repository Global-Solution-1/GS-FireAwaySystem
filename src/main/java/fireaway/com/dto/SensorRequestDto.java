package fireaway.com.dto;


import fireaway.com.domainmodel.Sensor;
import fireaway.com.domainmodel.enuns.TipoSensor;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SensorRequestDto {
    @Enumerated(EnumType.STRING)
    private TipoSensor tipo;

    private Double latitude;
    private Double longitude;

    private String ativo;

    public SensorRequestDto(Sensor sensor){
        this.tipo = sensor.getTipo();
        this.latitude = sensor.getLatitude();
        this.longitude = sensor.getLongitude();
        this.ativo = sensor.getAtivo();
    }
}
