package booking.domain.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Hotel implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 30, nullable = false)
    private String name;
}