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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HotelsServiceTest {

    @Autowired
    private RoomsRepository roomsRepository;
    @Autowired
    private HotelsRepository hotelsRepository;
    @Autowired
    private HotelsService hotelsService;
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
    public void testDeleteCascade() {
        //given
        final List<RoomEntity> rooms = new ArrayList<>();
        for (RoomCategory roomCategory : RoomCategory.values()) {
            rooms.add(new RoomEntity(null, 2D, persisted, roomCategory, null, null, null, null));
        }
        roomsRepository.saveAll(rooms);
        long roomsCount = roomsRepository.count();
        Assert.assertEquals(4, roomsCount);
        //when
        hotelsService.delete(persisted.getId());
        //then
        roomsCount = roomsRepository.count();
        final long hotelsCount = hotelsRepository.count();
        Assert.assertEquals(0, roomsCount);
        Assert.assertEquals(0, hotelsCount);
    }

    @Test
    public void testRoomNotFound() {
        //given
        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage("[HotelEntity] not found by id: [21313]");
        //when
        hotelsService.findById(21313L);
    }

    @Test
    public void testDeleteHotelNotFound() {
        //given
        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage("[HotelEntity] not found by id: [21313]");
        //when
        hotelsService.delete(21313L);
    }
}