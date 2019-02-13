package booking.service;


import booking.repository.entity.Booking;
import booking.repository.entity.Customer;
import booking.repository.entity.Hotel;
import booking.repository.entity.Room;

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