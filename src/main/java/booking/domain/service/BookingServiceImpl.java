package booking.domain.service;

import booking.domain.repository.*;
import booking.domain.repository.entity.*;
import lombok.val;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Random;

@Service
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {

    private static final Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);

    @Autowired
    BookableServiceImpl bookableService;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomRepository roomRepository;

//    @Autowired
//    private CustomerRepository customerRepository;


    @Override
    public List<Booking> getBookingList(Customer customer) {
        Booking booking = new Booking();
        booking.setCustomer(customer);
        // TODO Here is a bug that canceled bookings are omitted contrary to expectation.
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

        // Request parameter has no room_id.
        // Because booking service resolve room_id by hotel_id.
        // So, this method get room_id and set it booking variable.
        val sqlDtFrom = new Date(booking.getInDate().getTime());
        val sqlDtTo   = new Date(booking.getOutDate().getTime());

        var roomList =  bookableService.getBookableRoomList(sqlDtFrom, sqlDtTo, booking.getHotel());
//        roomList.forEach(room -> log.info(room.getId().toString()));
        var rand = new Random();
        int intRandom = rand.nextInt(roomList.size());
        var randRoom = roomList.get(intRandom);
        booking.setRoom(randRoom);

        // Update room_calendar table that bookable column to be false.
        var cntUpdate = bookableService.updateRoomCalendarBookable( //
                 sqlDtFrom, sqlDtTo, booking.getHotel().getId(), randRoom.getId(),false);
//        log.info(String.valueOf(cntUpdate));

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

//    @Override
//    public List<Customer> getCustomerList() {
//        return customerRepository.findAll();
//    }


}