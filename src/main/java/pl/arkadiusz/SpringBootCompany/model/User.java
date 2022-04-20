package pl.arkadiusz.SpringBootCompany.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
public class User {

    public User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

    private String name;
    private String surname;
    private String login;
    private String password;
    @Transient
    private String new_password;
    @Transient
    private String confirm_password;
    private boolean enabled;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    private long address_id;




    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private long user_id;
        private String name;
        private String surname;
        private String login;
        private String password;
        private boolean enabled;
        private long address_id;

        private Builder() {
        }


        public Builder user_id(long user_id) {
            this.user_id = user_id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder login(String login) {
            this.login = login;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder address_id(long address_id) {
            this.address_id = address_id;
            return this;
        }

        public User build() {
            User user = new User();
            user.setUser_id(user_id);
            user.setName(name);
            user.setSurname(surname);
            user.setLogin(login);
            user.setPassword(password);
            user.setAddress_id(address_id);
            user.enabled = this.enabled;
            return user;
        }
    }

}
