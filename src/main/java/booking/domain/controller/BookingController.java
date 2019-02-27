package booking.domain.controller;

import booking.domain.repository.entity.Booking;
import booking.domain.repository.entity.Customer;
import booking.domain.service.BookingServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookingController {

    private static final Logger log = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    BookingServiceImpl bookingService;

    @GetMapping("/customers/{customerName}")
    public Customer getCustomer(@PathVariable(value = "customerName") String name) {

        log.info("call getCustomer...");

        return bookingService.getCustomer(name);
    }

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

    @DeleteMapping("/customers/{customerId}/bookings/{bookingId}")
    public void deleteBooking(@PathVariable(value = "customerId")Integer customerId //
            , @PathVariable(value = "bookingId")Integer bookingId) {

        log.info("call deleteBooking...");

        bookingService.deleteBooking(bookingId);
    }

}
