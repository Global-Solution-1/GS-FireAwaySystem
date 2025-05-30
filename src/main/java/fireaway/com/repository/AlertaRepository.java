package fireaway.com.repository;

import fireaway.com.domainmodel.Alerta;
import fireaway.com.domainmodel.enuns.StatusAlerta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    boolean existsBySensorIdAndStatus(Long sensorId, StatusAlerta status);
}
