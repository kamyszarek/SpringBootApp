package pl.arkadiusz.SpringBootCompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.arkadiusz.SpringBootCompany.model.Address;
import pl.arkadiusz.SpringBootCompany.model.User;

import javax.transaction.Transactional;
import java.awt.*;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Transactional
    <S extends Event> S save(S entity);

    @Query("SELECT a FROM Address a WHERE id = ?1")
    public Address findAddressById(Long id);


}
