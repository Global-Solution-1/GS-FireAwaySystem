package fireaway.com.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import fireaway.com.domainmodel.Sensor;
import fireaway.com.domainmodel.enuns.NivelAlerta;
import fireaway.com.domainmodel.enuns.StatusAlerta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class AlertaRequestDto {

    private NivelAlerta nivel;
    private String descricao;
    private Double latitude;
    private Double longitude;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "America/Sao_Paulo")
    private LocalDateTime dataHora;
    private StatusAlerta status;
    private Long sensorId;
}
