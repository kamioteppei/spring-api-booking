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
public class RoomCalendar  implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private RoomCalendarId id;

    @Column(nullable = false)
    private boolean bookable;
}