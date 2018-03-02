INSERT
INTO
  users
  (created_date, email, last_modified_date)
VALUES
  ('2018-02-28T14:30:40.906Z', 'string@test.com', '2018-02-28T14:30:40.906Z');

INSERT
INTO
  hotels
  (id, created_date, last_modified_date, name, stars)
VALUES
  (1, '2018-02-28T14:30:40.906Z', '2018-02-28T14:30:40.906Z', 'Hilton', 5);

INSERT
INTO
  rooms
  (id, category, created_date, hotel_id, last_modified_date, price)
VALUES
  (1, 'LUX', '2018-02-28T14:30:40.906Z', 1, '2018-02-28T14:30:40.906Z', 150);

INSERT
INTO
  additional_options
  (id, price, title)
VALUES
  (1, 10, 'Breakfast');

INSERT
INTO
  additional_options
  (id, price, title)
VALUES
  (2, 20, 'Parking');