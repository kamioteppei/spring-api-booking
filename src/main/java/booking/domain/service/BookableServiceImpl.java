package booking.domain.service;

import booking.domain.model.Bookable;
import booking.domain.repository.entity.Hotel;
import booking.domain.repository.entity.Room;
import lombok.var;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookableServiceImpl implements BookableService {

    @PersistenceContext
    private EntityManager entityManager;

    final private static String sqlGetBookableList = "" //
            + " select " //
            + "   hotel.id " //
            + " , hotel.name " //
            + " , hotel.charge " //
            + " , hotel.option1 " //
            + " , hotel.option2 " //
            + " , hotel.option3 " //
            + " , bookables.room_count " //
            + " from hotel " //
            + " inner join " //
            + "             ( " //
            + "                     select " //
            + "                             room.hotel_id as hotel_id " //
            + "                     , count(room.id) as room_count " //
            + "     from room " //
            + "     where NOT EXISTS ( " //
            + "             select 'x' " //
            + "             from room_calendar " //
            + "             where room.id = room_calendar.room_id " //
            + "             and room.hotel_id = room_calendar.hotel_id " //
            + "             and room_calendar.dt BETWEEN :dtFrom and :dtTo " //
            + "             and room_calendar.bookable = false " //
            + "     ) " //
            + "     group BY " //
            + "     room.hotel_id " //
            + " ) bookables " //
            + "     on hotel.id = bookables.hotel_id "; //

    @Override
    public List<Bookable> getBookableList(Date dtFrom, Date dtTo){

//        return entityManager.createQuery(sqlGetBookableList,Bookable.class).getResultList();
        Query q = entityManager.createNativeQuery(sqlGetBookableList);
        q.setParameter("dtFrom", dtFrom);
        q.setParameter("dtTo", dtTo);
        List<Object[]> resultList = q.getResultList();

        List<Bookable> bookableList = new ArrayList<Bookable>();
        int i = 0;
        for (Object[] rs : resultList) {

            var hotel = new Hotel();
            hotel.setId(Integer.valueOf(rs[0].toString()));
            hotel.setName(rs[1].toString());
            hotel.setCharge(Integer.valueOf(rs[2].toString()));
            if (rs[3] == null) {
                hotel.setOption1(null);
            }else {
                hotel.setOption1(rs[3].toString());
            }
            if (rs[4] == null) {
                hotel.setOption2(null);
            }else {
                hotel.setOption2(rs[4].toString());
            }
            if (rs[5] == null) {
                hotel.setOption3(null);
            }else {
                hotel.setOption3(rs[5].toString());
            }

            var bookable = new Bookable();
            bookable.setId(i);
            bookable.setHotel(hotel);
            bookable.setRoom_count(Integer.valueOf(rs[6].toString()));
            bookable.setDtFrom(dtFrom);
            bookable.setDtTo(dtTo);

            bookableList.add(bookable);
            i++;
        }

        return bookableList;
    }

    final private static String sqlGetBookableRoomList = "" + //
            " select\n" +
            "   r.id as room_id\n" +
            " , r.name as room_name\n" +
            " from room r\n" +
            " where\n" +
            "      r.hotel_id = :hotelId\n" +
            " and  NOT EXISTS (\n" +
            "    select 'x'\n" +
            "      from room_calendar rc\n" +
            "     where r.id        = rc.room_id\n" +
            "       and r.hotel_id  = rc.hotel_id\n" +
            "       and rc.dt BETWEEN :dtFrom and :dtTo\n" +
            "       and rc.bookable = false\n" +
            " )\n";

    @Override
    public List<Room> getBookableRoomList(Date dtFrom, Date dtTo, Hotel hotel){

        Query q = entityManager.createNativeQuery(sqlGetBookableRoomList);
        q.setParameter("dtFrom", dtFrom);
        q.setParameter("dtTo", dtTo);
        q.setParameter("hotelId", hotel.getId());
        List<Object[]> resultList = q.getResultList();

        List<Room> bookableRoomList = new ArrayList<Room>();
        for (Object[] rs : resultList) {

            var room = new Room();
            room.setId(Integer.valueOf(rs[0].toString()));
            room.setName(rs[1].toString());
            room.setHotel(hotel);

            bookableRoomList.add(room);
        }

        return bookableRoomList;
    }

    final private static String sqlUpdateRoomCalendarBookable = "" //
            + " UPDATE " //
            + "   room_calendar rc " //
            + " SET  " //
            + "   rc.bookable = :bookable  " //
            + " WHERE  " //
            + "   rc.dt BETWEEN :dtFrom AND :dtTo " //
            + " AND rc.hotel_id = :hotelId  " //
            + " AND rc.room_id  = :roomId "; //

    @Override
    public Integer updateRoomCalendarBookable(Date dtFrom, Date dtTo, Integer hotelId, Integer roomId, boolean bookable){

        Query q = entityManager.createNativeQuery(sqlUpdateRoomCalendarBookable);
        q.setParameter("dtFrom", dtFrom);
        q.setParameter("dtTo", dtTo);
        q.setParameter("hotelId", hotelId);
        q.setParameter("roomId", roomId);
        q.setParameter("bookable", false);
        Integer cnt = q.executeUpdate();

        return cnt;
    }

}