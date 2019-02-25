package booking.domain.repository;

import booking.domain.repository.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository
        extends JpaRepository<Room, Integer> {
}