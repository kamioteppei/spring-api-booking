package booking.domain.service;


import booking.domain.model.Bookable;
import booking.domain.repository.entity.Booking;
import booking.domain.repository.entity.Customer;
import booking.domain.repository.entity.Hotel;
import booking.domain.repository.entity.Room;

import java.sql.Date;
import java.util.List;

public interface BookableService {

    public List<Bookable> getBookableList(Date dtFrom, Date dtTo);

    public List<Room> getBookableRoomList(Date dtFrom, Date dtTo, Hotel hotel);

    public Integer updateRoomCalendarBookable(Date dtFrom, Date dtTo, Integer hotelId, Integer roomId, boolean bookable);

}