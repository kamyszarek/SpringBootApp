package pl.arkadiusz.SpringBootCompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import pl.arkadiusz.SpringBootCompany.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE login = ?1")
    public User findByLogin(String login);

    @Query("SELECT u FROM User u WHERE u.login = :login")
    public User getUserByUsername(@Param("login") String login);

    @Transactional
    @Modifying
    @Query("delete from User u where u.login= :login")
    void deleteUser(@Param("login") String login);

    @Transactional
    @Modifying
    @Query("update User u set u.password = ?2 where u.login = ?1")
    void changePassword(String login, String password);

}
