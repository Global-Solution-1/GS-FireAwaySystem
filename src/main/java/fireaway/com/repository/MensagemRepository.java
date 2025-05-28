package fireaway.com.repository;

import fireaway.com.domainmodel.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> { }
