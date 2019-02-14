package booking.domain.service;

import booking.domain.repository.BookingRepository;
import booking.domain.repository.CustomerRepository;
import booking.domain.repository.HotelRepository;
import booking.domain.repository.RoomRepository;
import booking.domain.repository.entity.Booking;
import booking.domain.repository.entity.Customer;
import booking.domain.repository.entity.Hotel;
import booking.domain.repository.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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