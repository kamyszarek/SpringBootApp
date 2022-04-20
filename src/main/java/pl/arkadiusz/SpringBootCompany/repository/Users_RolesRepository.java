package pl.arkadiusz.SpringBootCompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.arkadiusz.SpringBootCompany.model.Users_roles;

public interface Users_RolesRepository extends JpaRepository<Users_roles, Long> {
}
