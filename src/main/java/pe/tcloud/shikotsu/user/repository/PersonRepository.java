package pe.tcloud.shikotsu.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.tcloud.shikotsu.user.model.Person;

import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {
}
