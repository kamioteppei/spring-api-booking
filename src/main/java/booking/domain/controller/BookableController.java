package booking.domain.controller;

import booking.domain.model.Bookable;
import booking.domain.repository.entity.Booking;
import booking.domain.repository.entity.Customer;
import booking.domain.service.BookableServiceImpl;
import booking.domain.service.BookingServiceImpl;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookableController {

    private static final Logger log = LoggerFactory.getLogger(BookableController.class);

    @Autowired
    BookableServiceImpl bookableService;

    @GetMapping("/bookables")
    public List<Bookable> getBookables(@RequestParam(value = "dtFrom") @DateTimeFormat(pattern="yyyy-MM-dd") java.util.Date dtFrom //
                                     , @RequestParam(value = "dtTo")   @DateTimeFormat(pattern="yyyy-MM-dd") java.util.Date dtTo  ) {

        log.info("call getBookables...");

        val sqlDtFrom = new Date(dtFrom.getTime());
        val sqlDtTo   = new Date(dtTo.getTime());

        return bookableService.getBookableList(sqlDtFrom, sqlDtTo);
    }

}
