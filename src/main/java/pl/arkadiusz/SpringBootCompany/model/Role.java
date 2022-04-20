package pl.arkadiusz.SpringBootCompany.model;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter
public class Role {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    public Integer getId() {
        return id;
    }

    // remaining getters and setters
}
