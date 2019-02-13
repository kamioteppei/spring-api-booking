package booking;

import booking.repository.entity.Booking;
import booking.repository.entity.Customer;
import booking.repository.entity.Hotel;
import booking.repository.entity.Room;
import booking.service.BookingServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookingServicesTests {

	// BookingServicesTests.javaを実行すると、mainのApplication.javaのMainが1回実行される。
	// その後にこのクラスのテストが順に呼ばれる

	private static final Logger log = LoggerFactory.getLogger(BookingServicesTests.class);

	@Autowired
	BookingServiceImpl bookingService;

	@Test
	public void contextLoads()  throws Exception {
		Assert.isTrue(bookingService != null ,"bookingService can not be null.");
	}

	@Test
	public void getBookingListByCustomerIdTest() {

		log.info("test start getBookingList...");

		Customer customer = new Customer();
		customer.setId(301);

		List<Booking> bookingList = bookingService.getBookingList(customer);

		bookingList.forEach((Booking booking) -> log.info(booking.toString()));

		Assert.isTrue(bookingList.size() == 2, "does not match count to expect");

		log.info("test end getBookingList...");
	}

    @Test
    public void getBookingByIdTest() {

        log.info("test start getBookingByIdTest...");

        Booking booking = bookingService.getBooking(2);

        log.info(booking.toString());

        Assert.isTrue(booking.getHotel().getId() == 101, "does not match hotel id to expect");
        Assert.isTrue(booking.getRoom().getId() == 202, "does not match room id to expect");

        log.info("test end getBookingByIdTest...");
    }

    @Test
    public void saveNewBookingTest() {

        log.info("test start saveNewBookingTest...");

        Hotel hotel = new Hotel();
        hotel.setId(102);

        Room room = new Room();
        room.setId(211);

        Customer customer = new Customer();
        customer.setId(302);

        Booking booking = new Booking();
        booking.setHotel(hotel);
        booking.setRoom(room);
        booking.setCustomer(customer);
        booking.setInDate( java.sql.Date.valueOf(//
                LocalDateTime.of(2019, 2, 15, 0, 0, 0).toLocalDate()));
        booking.setOutDate( java.sql.Date.valueOf(//
                LocalDateTime.of(2019, 2, 17, 0, 0, 0).toLocalDate()));

        log.info(booking.toString());

        Booking bookingSaved = bookingService.saveBooking(booking);

        log.info(bookingSaved.toString());

        Assert.isTrue(bookingSaved.getId() >= 1, "booking id does not incremental");

        log.info("test end saveNewBookingTest...");
    }

    @Test
    public void cancelBookingTest() {

        log.info("test start cancelBookingTest...");

        Booking bookingCancel = bookingService.getBooking(2);
        bookingCancel.setOutDate( java.sql.Date.valueOf(//
                LocalDateTime.of(2019, 2, 17, 0, 0, 0).toLocalDate()));
        bookingCancel.setCanceled(true);

        log.info(bookingCancel.toString());

        Booking bookingCanceled = bookingService.saveBooking(bookingCancel);

        log.info(bookingCanceled.toString());

        Assert.isTrue(bookingCanceled.isCanceled() == true, "booking does not canceled");

        log.info("test end cancelBookingTest...");
    }

//        // --------------------------------------------------------
//        log.info("fkErrorBooking...");
//
//        Booking bookingError1 = bookingService.getBooking(3);
//        Room room2 = new Room();
//        room.setId(999);
//        bookingError1.setRoom(room2);
//
//        log.info(bookingError1.toString());
//
//        Booking bookingError2 = bookingService.saveBooking(bookingError1);
//
//        log.info(bookingError2.toString());
//
//        // --------------------------------------------------------
//        log.info("deleteBooking...");
//
//        Booking deleteBooking1 = bookingService.getBooking(23);
//
//        log.info(deleteBooking1.toString());
//
//        bookingService.deleteBooking(deleteBooking1);
//
//        log.info("deleted...");
//
//        // --------------------------------------------------------
//        log.info("deleteBookingById...");
//
//        Booking deleteBookingById = bookingService.getBooking(24);
//
//        log.info(deleteBookingById.toString());
//
//        bookingService.deleteBooking(deleteBookingById.getId());
//
//        log.info("deleted...");
//
//        // --------------------------------------------------------

}
