package com.booking.services;

import com.booking.exceptions.ResourceNotFoundException;
import com.booking.models.RoomCategory;
import com.booking.models.domain.HotelEntity;
import com.booking.models.domain.RoomEntity;
import com.booking.repository.HotelsRepository;
import com.booking.repository.RoomsRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import static com.booking.models.RoomCategory.BASIC;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoomsServiceTest {

    @Autowired
    private RoomsRepository roomsRepository;
    @Autowired
    private HotelsRepository hotelsRepository;
    @Autowired
    private RoomsService roomsService;
    private HotelEntity persisted;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        hotelsRepository.deleteAllInBatch();
        roomsRepository.deleteAllInBatch();

        this.persisted = hotelsRepository.saveAndFlush(new HotelEntity(null, "hotel", 3, null, null));

    }

    @Test
    public void testSaveNonUniqueCategoryPerHotel() {
        //given
        long count = roomsRepository.count();
        Assert.assertEquals(0, count);
        //when
        RoomEntity saved = roomsService.save(new RoomEntity(null, 2D, persisted, BASIC, null, null, null, null));
        //then
        Assert.assertNotNull(saved.getId());
        count = roomsRepository.count();
        Assert.assertEquals(1, count);

        //when
        try {
            roomsService.save(new RoomEntity(null, 2D, persisted, BASIC, null, null, null, null));
            fail();
        } catch (DataIntegrityViolationException ignored) {
        }
        //then
        count = roomsRepository.count();
        Assert.assertEquals(1, count);

        //when create room same category in another hotel
        final HotelEntity hotelEntity = hotelsRepository.saveAndFlush(new HotelEntity(null, "hotel2", 3, null, null));
        roomsService.save(new RoomEntity(null, 2D, hotelEntity, BASIC, null, null, null, null));

        //then
        count = roomsRepository.count();
        Assert.assertEquals(2, count);
    }

    @Test
    public void testGetRooms() {
        //given
        roomsService.save(new RoomEntity(null, 2D, persisted, BASIC, null, null, null, null));
        roomsService.save(new RoomEntity(null, 2D, persisted, RoomCategory.LUX, null, null, null, null));
        roomsService.save(new RoomEntity(null, 2D, persisted, RoomCategory.PRESIDENT, null, null, null, null));
        //when
        Page<RoomEntity> page = roomsService.findByCategory(null, Pageable.unpaged());
        //then
        Assert.assertEquals(3, page.getContent().size());

        //when filter by category
        page = roomsService.findByCategory(BASIC, Pageable.unpaged());
        //then
        Assert.assertEquals(1, page.getContent().size());
    }

    @Test
    public void testRoomNotFound() {
        //given
        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage("[RoomEntity] not found by id: [21313]");
        //when
        roomsService.findById(21313L);
    }

    @Test
    public void testDeleteRoomNotFound() {
        //given
        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage("[RoomEntity] not found by id: [21313]");
        //when
        roomsService.delete(21313L);
    }
}