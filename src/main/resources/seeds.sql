-- For MySQL5.6

DROP TABLE hotel
;

CREATE TABLE hotel (
    `id`   int          not null  primary key ,
    `name` varchar(256) not null,
    `charge` int not null,
    `option1` varchar(256) not null,
    `option2` varchar(256) not null,
    `option3` varchar(256) not null
) engine=innodb
;

INSERT INTO hotel
    (`id`, `name`, `charge`, `option1`, `option2`, `option3`)
VALUES
    (101, 'tokyu', 8500, 'Double bed', 'Morning set', 'Wifi' ),
    (102, 'apa', 5000, 'Single bed', 'No morning set', null ),
    (103, 'yourstay', 7700, 'Single bed', 'No smoking', 'Point card' )
;


DROP TABLE room
;

CREATE TABLE room (
    `id`       int          not null  primary key ,
    `hotel_id` int          not null  ,
    `name`     varchar(256) not null  ,
    FOREIGN KEY (hotel_id) REFERENCES hotel(id)
) engine=innodb
;

INSERT INTO room
    (`id`, `hotel_id`, `name`)
VALUES
    (201, 101, 201),
    (202, 101, 202),
    (203, 101, 203),
    (204, 101, 204),
    (205, 101, 205),
    (211, 102, 211),
    (212, 102, 212),
    (213, 102, 213),
    (214, 102, 214),
    (215, 102, 215),
    (221, 103, 221),
    (222, 103, 222),
    (223, 103, 223),
    (224, 103, 224),
    (225, 103, 225)
;

DROP TABLE customer
;

CREATE TABLE customer(
    `id`            int          not null  primary key ,
    `name`          varchar(256) not null  ,
    `email`         varchar(256),
    `password_hash` varchar(256),
    `token`         varchar(256)
) engine=innodb
;

INSERT INTO customer
    (`id`, `name`, `email`, `password_hash`, `token`)
VALUES
    (301, 'niko', NULL, 'aoisdjfodjfoi', 'jofijosjfosj'),
    (302, 'puffy', NULL, 'shifhihfiwhf', 'sjoeowojo')
;


DROP TABLE booking
;

CREATE TABLE booking (
    `id`            int          not null  primary key ,
    `hotel_id`      int          not null,
    `room_id`       int          not null,
    `customer_id`   int          not null,
    `in_date`       datetime     not null,
    `out_date`      datetime     not null,
    `canceled`      boolean,
    FOREIGN KEY (hotel_id)     REFERENCES hotel(id),
    FOREIGN KEY (room_id)      REFERENCES room(id),
    FOREIGN KEY (customer_id)  REFERENCES customer(id)
) engine=innodb
;

INSERT INTO booking
    (`id`, `hotel_id`, `room_id`, `customer_id`, `in_date`, `out_date`, `canceled`)
VALUES
    (1, 101, 201, 301, '2019-02-03 00:00:00', '2019-02-05 00:00:00', false),
    (2, 101, 202, 302, '2019-02-03 00:00:00', '2019-02-05 00:00:00', false),
    (3, 101, 201, 301, '2019-02-10 00:00:00', '2019-02-20 00:00:00', false),
    (4, 101, 202, 302, '2019-02-10 00:00:00', '2019-02-20 00:00:00', false)
;

DROP TABLE room_calendar
;

CREATE TABLE room_calendar (
    `dt`       datetime     not null ,
    `hotel_id` int          not null ,
    `room_id`  int          not null ,
    `bookable` boolean     not null ,
    PRIMARY KEY (dt, hotel_id, room_id)
) engine=innodb
;

ALTER TABLE room_calendar
PARTITION BY RANGE COLUMNS(dt) (
    PARTITION p201901 VALUES LESS THAN ('2019-01-01 00:00:00') ENGINE=InnoDB,
    PARTITION p201902 VALUES LESS THAN ('2019-02-01 00:00:00') ENGINE=InnoDB,
    PARTITION p201903 VALUES LESS THAN ('2019-03-01 00:00:00') ENGINE=InnoDB,
    PARTITION p201904 VALUES LESS THAN ('2019-04-01 00:00:00') ENGINE=InnoDB,
    PARTITION p201905 VALUES LESS THAN ('2019-05-01 00:00:00') ENGINE=InnoDB,
    PARTITION p201906 VALUES LESS THAN ('2019-06-01 00:00:00') ENGINE=InnoDB,
    PARTITION p201907 VALUES LESS THAN ('2019-07-01 00:00:00') ENGINE=InnoDB,
    PARTITION p201908 VALUES LESS THAN ('2019-08-01 00:00:00') ENGINE=InnoDB,
    PARTITION p201909 VALUES LESS THAN ('2019-09-01 00:00:00') ENGINE=InnoDB,
    PARTITION p201910 VALUES LESS THAN ('2019-10-01 00:00:00') ENGINE=InnoDB,
    PARTITION p201911 VALUES LESS THAN ('2019-11-01 00:00:00') ENGINE=InnoDB,
    PARTITION p201912 VALUES LESS THAN ('2019-12-01 00:00:00') ENGINE=InnoDB,
    PARTITION p999999 VALUES LESS THAN MAXVALUE ENGINE=InnoDB
);

DROP PROCEDURE IF EXISTS create_room_calendar;
DELIMITER //
CREATE PROCEDURE create_room_calendar
( IN from_ym_str VARCHAR(6), IN to_ym_str VARCHAR(6), OUT result_code INT, OUT result_message VARCHAR(255), OUT delete_recode_count INT , OUT create_recode_count INT )
BEGIN

  # 変数宣言
  DECLARE dtFrom     DATETIME;
  DECLARE dtTo       DATETIME;
  DECLARE dt         DATETIME;
  DECLARE intHotelId INT;
  DECLARE intRoomId  INT;
  DECLARE cntIndex   INT;
  DECLARE cntTotal   INT;
  DECLARE cntCreate  INT;
  DECLARE curRoom CURSOR FOR
  	SELECT id, hotel_id FROM room ;

  # 例外ハンドラー宣言
  #DECLARE EXIT HANDLER FOR SQLEXCEPTION SET result_code = 1;

  # 変数初期化
  SET result_code = 1; # エラー状態にセット
  SET dtFrom      = CAST(CONCAT(from_ym_str,'01') AS DATETIME);
  SET dtTo        = CAST(LAST_DAY(CAST(CONCAT(to_ym_str,'01') AS DATETIME)) AS DATETIME);
  SET cntIndex    = 0;
  SET cntTotal    = 0;
  SET cntCreate   = 0;

  SET result_message = 'delete room_calendar';
  # 削除条件が効かない。
  DELETE FROM room_calendar; #WHERE dt BETWEEN dtFrom AND dtTo;
  SELECT row_count() INTO delete_recode_count;

  SET result_message = 'get count room';
  SELECT COUNT(*) INTO cntTotal FROM room;

  SET result_message = 'open curRoom';
  OPEN curRoom;
  WHILE cntTotal > cntIndex DO

     FETCH curRoom INTO intRoomId, intHotelId;
     SET result_message = concat(' intHotelId:', CONVERT(intHotelId, char),' intRoomId:', CONVERT(intRoomId, char));

     SET dt = dtFrom;
     WHILE DATE_ADD(dtTo, INTERVAL 1 DAY) > dt DO

        INSERT INTO room_calendar (dt, hotel_id, room_id, bookable)
        VALUES (dt, intHotelId, intRoomId, true)
        ;
        SET cntCreate = cntCreate + 1;
        SET dt = DATE_ADD(dt, INTERVAL 1 DAY);

     END WHILE;
     SET cntIndex = cntIndex + 1;

  END WHILE;
  CLOSE curRoom;

  SET result_code = 0;
  SET create_recode_count = cntCreate;
END
//
DELIMITER ;

call create_room_calendar('201901','201912', @result_code, @result_message, @delete_recode_count, @create_recode_count );

select @result_code, @result_message, @delete_recode_count, @create_recode_count ;


select
  hotel.id
, hotel.name
, hotel.charge
, hotel.option1
, hotel.option2
, hotel.option3
, bookables.room_count
from hotel
inner join
(
select
  room.hotel_id as hotel_id
, count(room.id) as room_count
from room
where NOT EXISTS (
    select 'x'
      from room_calendar
     where room.id = room_calendar.room_id
       and room.hotel_id = room_calendar.hotel_id
       and room_calendar.bookable = false
       and room_calendar.dt BETWEEN '2019/01/15' and '2019/01/20'
)
group BY
 room.hotel_id
) bookables
on hotel.id = bookables.hotel_id