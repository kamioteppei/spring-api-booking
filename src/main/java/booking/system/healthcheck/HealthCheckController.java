package booking.system.healthcheck;

import booking.domain.model.Bookable;
import booking.domain.service.BookableServiceImpl;
import lombok.val;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/")
public class HealthCheckController {

    private static final Logger log = LoggerFactory.getLogger(HealthCheckController.class);

    @Autowired
    HealthCheckServiceImpl healthCheckService;

    @GetMapping("/")
    public String doHealthCheck() {

        var healthCheckMessage = "";
        var strCurrentDate = healthCheckService.getCurrentDate();
        if (strCurrentDate == null){
            healthCheckMessage = strCurrentDate + " DB is not working.";
        } else {
            healthCheckMessage = strCurrentDate + " Service is working!";
        }
        return healthCheckMessage;
    }

}
