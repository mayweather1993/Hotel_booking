package com.booking.rest;

import com.booking.models.RoomCategory;
import com.booking.models.domain.*;
import com.booking.models.vm.BookingVM;
import com.booking.repository.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookingsControllerTest extends AbstractControllerTest {
    @Autowired
    private BookingsRepository bookingsRepository;
    @Autowired
    private HotelsRepository hotelsRepository;
    @Autowired
    private RoomsRepository roomsRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private AdditionalOptionsRepository additionalOptionsRepository;

    @Before
    public void setup() {
        this.bookingsRepository.deleteAllInBatch();
        this.hotelsRepository.deleteAllInBatch();
        this.additionalOptionsRepository.deleteAllInBatch();
        this.roomsRepository.deleteAllInBatch();
        this.usersRepository.deleteAllInBatch();
        final long count = bookingsRepository.count();
        Assert.assertEquals(0, count);
    }


    @Test
    public void testCreateBooking() throws Exception {
        //given: create hotel, 2 rooms and user
        final AdditionalOptionsEntity additionalOptionsEntity1 = new AdditionalOptionsEntity("Breakfast", 10D);
        final AdditionalOptionsEntity additionalOptionsEntity2 = new AdditionalOptionsEntity("Parking", 20D);
        final List<AdditionalOptionsEntity> options = Arrays.asList(additionalOptionsEntity1, additionalOptionsEntity2);
        additionalOptionsRepository.saveAll(options);
//        final List<AdditionalOptionsEntity> options = additionalOptionsRepository.findAllById(Arrays.asList(1L, 2L));

        final HotelEntity hotel = hotelsRepository.save(new HotelEntity(null, "hotel", 3, null, null));
        final RoomEntity room1 = roomsRepository.save(new RoomEntity(null, 2D, hotel, RoomCategory.BASIC, null, null, null, options));
        final RoomEntity room2 = roomsRepository.save(new RoomEntity(null, 10D, hotel, RoomCategory.LUX, null, null, null, null));
        final UserEntity user = usersRepository.save(new UserEntity(null, "user@user.com", null, null, null));
        final Instant now = Instant.now().plusSeconds(10);
        //when
        mockMvc.perform(post("/bookings")
                .content(this.json(new BookingVM(now, now.plus(2, ChronoUnit.DAYS), user.getId(), asList(room1.getId(), room2.getId()))))
                .contentType(contentType))
                .andExpect(status().isCreated());
        //then
        final List<BookingEntity> all = bookingsRepository.findAll();
        final long count = all.size();
        Assert.assertEquals(1, count);

        Assert.assertEquals(84D, all.get(0).getPrice(), 0);
    }

    /**
     * Price should be calculated automatically
     */
    @Test
    public void testCreateBookingWithPredefinedPrice() throws Exception {
        //given: create hotel, 2 rooms and user
        final HotelEntity hotel = hotelsRepository.saveAndFlush(new HotelEntity(null, "hotel", 3, null, null));
        final RoomEntity room1 = roomsRepository.save(new RoomEntity(null, 2D, hotel, RoomCategory.BASIC, null, null, null, null));
        final RoomEntity room2 = roomsRepository.save(new RoomEntity(null, 10D, hotel, RoomCategory.LUX, null, null, null, null));
        final UserEntity user = usersRepository.save(new UserEntity(null, "user@user.com", null, null, null));
        final Instant now = Instant.now().plusSeconds(10);
        final BookingVM bookingVM = new BookingVM(now, now.plus(1, ChronoUnit.DAYS), user.getId(), asList(room1.getId(), room2.getId()));
        bookingVM.setPrice(100D);
        //when
        mockMvc.perform(post("/bookings")
                .content(this.json(bookingVM))
                .contentType(contentType))
                .andExpect(status().isCreated());
        //then
        final List<BookingEntity> all = bookingsRepository.findAll();
        final long count = all.size();
        Assert.assertEquals(1, count);

        Assert.assertEquals(12D, all.get(0).getPrice(), 0);
    }

    @Test
    public void testCreateBookingToDateLessThanFrom() throws Exception {
        //given: create hotel, room and user
        final HotelEntity hotel = hotelsRepository.saveAndFlush(new HotelEntity(null, "hotel", 3, null, null));
        final RoomEntity room = roomsRepository.save(new RoomEntity(null, 2D, hotel, RoomCategory.BASIC, null, null, null, null));
        final UserEntity user = usersRepository.save(new UserEntity(null, "user@user.com", null, null, null));
        final Instant now = Instant.now().plusSeconds(10);

        //when
        mockMvc.perform(post("/bookings")
                .content(this.json(new BookingVM(now, now.minusSeconds(100), user.getId(), Collections.singletonList(room.getId()))))
                .contentType(contentType))
                .andExpect(status().isBadRequest());
        //then
        final long count = bookingsRepository.count();
        Assert.assertEquals(0, count);
    }

    @Test
    public void testCreateBookingDurationLessThanOneDay() throws Exception {
        //given: create hotel, room and user
        final HotelEntity hotel = hotelsRepository.saveAndFlush(new HotelEntity(null, "hotel", 3, null, null));
        final RoomEntity room = roomsRepository.save(new RoomEntity(null, 2D, hotel, RoomCategory.BASIC, null, null, null, null));
        final UserEntity user = usersRepository.save(new UserEntity(null, "user@user.com", null, null, null));
        final Instant now = Instant.now().plusSeconds(30);
        //when
        mockMvc.perform(post("/bookings")
                .content(this.json(new BookingVM(now, now, user.getId(), Collections.singletonList(room.getId()))))
                .contentType(contentType))
                .andExpect(status().isBadRequest());
        //then
        final long count = bookingsRepository.count();
        Assert.assertEquals(0, count);
    }

    @Test
    public void testCreateBookingFromLessThanNow() throws Exception {
        //given: create hotel, room and user
        final HotelEntity hotel = hotelsRepository.saveAndFlush(new HotelEntity(null, "hotel", 3, null, null));
        final RoomEntity room = roomsRepository.save(new RoomEntity(null, 2D, hotel, RoomCategory.BASIC, null, null, null, null));
        final UserEntity user = usersRepository.save(new UserEntity(null, "user@user.com", null, null, null));
        final Instant now = Instant.now();
        //when
        mockMvc.perform(post("/bookings")
                .content(this.json(new BookingVM(now, now, user.getId(), Collections.singletonList(room.getId()))))
                .contentType(contentType))
                .andExpect(status().isBadRequest());
        //then
        final long count = bookingsRepository.count();
        Assert.assertEquals(0, count);
    }

    @Test
    public void testBookingNotFound() throws Exception {
        mockMvc.perform(get("/bookings/223424234")
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateBooking() {


    }
}