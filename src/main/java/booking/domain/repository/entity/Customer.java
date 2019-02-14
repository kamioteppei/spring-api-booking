package booking.domain.repository.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 256, nullable = true)
    private String email;

    @Column(length = 256, nullable = true)
    private String password_hash;

    @Column(length = 256, nullable = true)
    private String token;

}