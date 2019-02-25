package booking.domain.repository;

import booking.domain.repository.entity.Room;
import booking.domain.repository.entity.RoomCalendar;
import booking.domain.repository.entity.RoomCalendarId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface RoomCalendarRepository
        extends JpaRepository<RoomCalendar, RoomCalendarId> {
}