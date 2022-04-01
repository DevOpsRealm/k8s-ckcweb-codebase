package ph.edu.ckc.k8sckcbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ph.edu.ckc.k8sckcbackend.entities.Menu;


import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByUrl(String url);
    Optional<Menu> findByUuid(String uuid);
}
