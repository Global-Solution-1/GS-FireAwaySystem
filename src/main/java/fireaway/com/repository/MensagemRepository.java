package fireaway.com.repository;

import fireaway.com.domainmodel.Mensagem;
import fireaway.com.domainmodel.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
    List<Mensagem> findByReceptor(Usuario receptor);
    List<Mensagem> findByEmissor(Usuario emissor);
}
