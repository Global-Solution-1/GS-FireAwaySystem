package fireaway.com.repository;

import fireaway.com.domainmodel.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Long> { }