package booking.system.healthcheck;

import booking.domain.model.Bookable;
import booking.domain.repository.entity.Hotel;
import booking.domain.repository.entity.Room;
import booking.domain.service.BookableService;
import lombok.val;
import lombok.var;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

@Service
@Transactional(readOnly = true)
public class HealthCheckServiceImpl {

    @PersistenceContext
    private EntityManager entityManager;

    final private static String sqlGetCurrentDate = "" + //
            " SELECT DATE_FORMAT( now() , '%Y-%m-%d %H:%i:%s' )\n";

    public String getCurrentDate(){
        Query q = entityManager.createNativeQuery(sqlGetCurrentDate);
        List<Object> resultList = q.getResultList();
        String currentDate = null;
        for (Object rs : resultList) {
            currentDate = rs.toString();
        }
        return currentDate;
    }
}