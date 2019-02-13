package booking.service;

import booking.repository.BookingRepository;
import booking.repository.CustomerRepository;
import booking.repository.HotelRepository;
import booking.repository.RoomRepository;
import booking.repository.entity.Booking;
import booking.repository.entity.Customer;
import booking.repository.entity.Hotel;
import booking.repository.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import lombok.Data;

@Service
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Booking> getBookingList(Customer customer) {
        Booking booking = new Booking();
        booking.setCustomer(customer);
        return bookingRepository.findAll(Example.of(booking));
    }

    @Override
    public Booking getBooking(Integer id) {
        return bookingRepository.findById(id)//
         .orElseThrow(RuntimeException::new);
    }

    @Override
    @Transactional
    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    @Transactional
    public void deleteBooking(Integer id) {
        bookingRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteBooking(Booking booking) {
        bookingRepository.delete(booking);
    }

    @Override
    public List<Hotel> getHotelList() {
        return hotelRepository.findAll();
    }

    @Override
    public List<Room> getRoomList() {
        return roomRepository.findAll();
    }

    @Override
    public List<Customer> getCustomerList() {
        return customerRepository.findAll();
    }


}