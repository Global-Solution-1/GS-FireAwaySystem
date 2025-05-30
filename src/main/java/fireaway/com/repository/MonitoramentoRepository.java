package fireaway.com.repository;

import fireaway.com.domainmodel.Monitoramento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonitoramentoRepository extends JpaRepository<Monitoramento, Long> {
    List<Monitoramento> findTop3BySensorIdOrderByDataHoraDesc(Long sensorId);
}
