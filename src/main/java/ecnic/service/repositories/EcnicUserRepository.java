package ecnic.service.repositories;

import ecnic.service.models.entities.EcnicUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EcnicUserRepository extends JpaRepository<EcnicUser, Integer> {
    Optional<EcnicUser> findByUsername(String username);
}
