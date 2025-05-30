package fireaway.com.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class MonitoramentoRequestDto {

    @NotNull(message = "O monitoramento precisa ser associado a um sensor cadastrado")
    private Long sensorId;

    @NotNull(message = "Informe o valor de resultado do monitoramento")
    private float valor;


    private String descricao;
}
