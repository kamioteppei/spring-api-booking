package booking.domain.model;

import booking.domain.repository.entity.Hotel;
import booking.domain.repository.entity.Room;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class Bookable implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Hotel hotel;

    private Date  dtFrom;

    private Date dtTo;

    private Integer room_count;

}