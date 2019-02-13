-- For MySQL5.6

DROP TABLE hotel
;

CREATE TABLE hotel (
    `id`   int          not null  primary key ,
    `name` varchar(256) not null
) engine=innodb
;

INSERT INTO hotel
    (`id`, `name`)
VALUES
    (101, 'tokyu'),
    (102, 'apa')
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
    (211, 102, 211),
    (212, 102, 212)
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

