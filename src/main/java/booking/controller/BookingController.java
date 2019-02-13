package booking.controller;

import booking.repository.entity.Booking;
import booking.repository.entity.Customer;
import booking.repository.entity.Hotel;
import booking.repository.entity.Room;
import booking.service.BookingServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/v1")
public class BookingController {

    private static final Logger log = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    BookingServiceImpl bookingService;

    @GetMapping("/customers/{customerId}/bookings")
    public List<Booking> getMyBookings(@PathVariable(value = "customerId") Integer customerId) {

        log.info("call getMyBookings...");

        Customer customer = new Customer();
        customer.setId(customerId);

        return bookingService.getBookingList(customer);
    }

    @GetMapping("/customers/{customerId}/bookings/{bookingId}")
    public Booking getBookingById(@PathVariable(value = "customerId")Integer customerId //
                                , @PathVariable(value = "bookingId")Integer bookingId) {

        log.info("call getBookingById...");

        return bookingService.getBooking(bookingId);
    }

    @PostMapping("/customers/{customerId}/bookings")
    public Booking createBooking(@PathVariable(value = "customerId")Integer customerId //
                                , @Valid @RequestBody Booking booking) {

        log.info("call createBooking...");

        Booking bookingSaved = bookingService.saveBooking(booking);
        return bookingSaved;
    }

    @PutMapping("/customers/{customerId}/bookings/{bookingId}")
    public Booking modifyBooking(@PathVariable(value = "customerId")Integer customerId //
                                , @PathVariable(value = "bookingId")Integer bookingId //
                                , @Valid @RequestBody Booking booking) {


        log.info("call modifyBooking...");

        Booking bookingModified = bookingService.saveBooking(booking);

        return bookingModified;
    }

}
