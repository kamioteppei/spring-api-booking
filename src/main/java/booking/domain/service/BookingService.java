package booking.domain.service;


import booking.domain.repository.entity.Booking;
import booking.domain.repository.entity.Customer;
import booking.domain.repository.entity.Hotel;
import booking.domain.repository.entity.Room;

import java.util.List;

public interface BookingService {

    public List<Booking> getBookingList(Customer customer);

    public Booking getBooking(Integer id);

    public Booking saveBooking(Booking booking);

    public void deleteBooking(Integer id);

    public void deleteBooking(Booking booking);

    public List<Hotel> getHotelList();

    public List<Room> getRoomList();

    public List<Customer> getCustomerList();

}