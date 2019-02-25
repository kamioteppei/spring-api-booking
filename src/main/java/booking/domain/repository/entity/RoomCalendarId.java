package booking.domain.repository.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Date;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class RoomCalendarId implements Serializable {

    private Date dt;

    private Integer hotelId;

    private Integer roomId;
}
