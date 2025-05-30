package fireaway.com.dto;

import fireaway.com.domainmodel.enuns.NivelAlerta;
import fireaway.com.domainmodel.enuns.StatusAlerta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class AlertaResponseDto {
    private Long id;
    private NivelAlerta nivel;
    private String descricao;
    private Double latitude;
    private Double longitude;
    private LocalDateTime dataHora;
    private StatusAlerta status;


}
