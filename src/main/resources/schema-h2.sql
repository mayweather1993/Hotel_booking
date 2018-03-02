DROP TABLE IF EXISTS additional_options;
DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS hotels;
DROP TABLE IF EXISTS rooms;
DROP TABLE IF EXISTS rooms_bookings;
DROP TABLE IF EXISTS rooms_options;
DROP TABLE IF EXISTS users;

CREATE TABLE additional_options (
  id    BIGINT GENERATED BY DEFAULT AS IDENTITY,
  price DOUBLE,
  title VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE TABLE bookings (
  id                 BIGINT GENERATED BY DEFAULT AS IDENTITY,
  created_date       TIMESTAMP,
  from_date          TIMESTAMP NOT NULL,
  last_modified_date TIMESTAMP,
  price              DOUBLE,
  to_date            TIMESTAMP NOT NULL,
  user_id            BIGINT    NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE hotels (
  id                 BIGINT GENERATED BY DEFAULT AS IDENTITY,
  created_date       TIMESTAMP,
  last_modified_date TIMESTAMP,
  name               VARCHAR(255) NOT NULL,
  stars              INTEGER      NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE rooms (
  id                 BIGINT GENERATED BY DEFAULT AS IDENTITY,
  category           VARCHAR(255) NOT NULL,
  created_date       TIMESTAMP,
  last_modified_date TIMESTAMP,
  price              DOUBLE       NOT NULL,
  hotel_id           BIGINT       NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE rooms_bookings (
  id      BIGINT NOT NULL,
  room_id BIGINT NOT NULL
);

CREATE TABLE rooms_options (
  room_id   BIGINT NOT NULL,
  option_id BIGINT NOT NULL
);

CREATE TABLE users (
  id                 BIGINT GENERATED BY DEFAULT AS IDENTITY,
  created_date       TIMESTAMP,
  email              VARCHAR(255) NOT NULL,
  last_modified_date TIMESTAMP,
  PRIMARY KEY (id)
);

ALTER TABLE hotels
  ADD CONSTRAINT UK_m7xlqsndengqp2kpd5nux2s3i UNIQUE (name);

ALTER TABLE rooms
  ADD CONSTRAINT UK3i34prv8iqxhy904kxg9nx3x3 UNIQUE (hotel_id, category);

ALTER TABLE users
  ADD CONSTRAINT UK_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);

ALTER TABLE bookings
  ADD CONSTRAINT FKeyog2oic85xg7hsu2je2lx3s6
FOREIGN KEY (user_id)
REFERENCES users;

ALTER TABLE rooms
  ADD CONSTRAINT FKp5lufxy0ghq53ugm93hdc941k
FOREIGN KEY (hotel_id)
REFERENCES hotels
ON DELETE CASCADE;

ALTER TABLE rooms_bookings
  ADD CONSTRAINT FKa2apoc3a9qjdbewne0hfrnvr9
FOREIGN KEY (room_id)
REFERENCES rooms;

ALTER TABLE rooms_bookings
  ADD CONSTRAINT FKa3yxnxl9qayjwrqgt0ie504eq
FOREIGN KEY (id)
REFERENCES bookings;

ALTER TABLE rooms_options
  ADD CONSTRAINT FKetivtggboub4u0te9sb57wgix
FOREIGN KEY (option_id)
REFERENCES additional_options
ON DELETE CASCADE;


ALTER TABLE rooms_options
  ADD CONSTRAINT FK33c6ncn6owq9kq10k119qi48i
FOREIGN KEY (room_id)
REFERENCES rooms
ON DELETE CASCADE;

