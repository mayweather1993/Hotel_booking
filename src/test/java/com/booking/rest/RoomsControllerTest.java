package com.booking.rest;

import com.booking.models.domain.HotelEntity;
import com.booking.models.domain.RoomEntity;
import com.booking.models.vm.RoomVM;
import com.booking.repository.HotelsRepository;
import com.booking.repository.RoomsRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.booking.models.RoomCategory.BASIC;
import static com.booking.models.RoomCategory.PRESIDENT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RoomsControllerTest extends AbstractControllerTest {

    @Autowired
    private HotelsRepository hotelsRepository;
    @Autowired
    private RoomsRepository roomsRepository;

    private HotelEntity persisted;

    @Before
    public void setup() {
        this.hotelsRepository.deleteAllInBatch();
        this.roomsRepository.deleteAllInBatch();
        this.persisted = hotelsRepository.saveAndFlush(new HotelEntity(null, "hotel", 3, null, null));
    }

    @Test
    public void testCreateInvalidHRoom() throws Exception {
        //create hotel all fields are null
        mockMvc.perform(post("/rooms")
                .content(this.json(new RoomVM()))
                .contentType(contentType))
                .andExpect(status().isBadRequest());

        long count = roomsRepository.count();
        Assert.assertEquals(0, count);

        //create price is null
        mockMvc.perform(post("/rooms")
                .content(this.json(new RoomVM(null, null, persisted.getId(), BASIC, null, null, null)))
                .contentType(contentType))
                .andExpect(status().isBadRequest());

        count = roomsRepository.count();
        Assert.assertEquals(0, count);

        //create hotelId is null
        mockMvc.perform(post("/rooms")
                .content(this.json(new RoomVM(null, 2D, null, BASIC, null, null, null)))
                .contentType(contentType))
                .andExpect(status().isBadRequest());

        count = roomsRepository.count();
        Assert.assertEquals(0, count);

        //create room category is null
        mockMvc.perform(post("/rooms")
                .content(this.json(new RoomVM(null, 2D, persisted.getId(), null, null, null, null)))
                .contentType(contentType))
                .andExpect(status().isBadRequest());

        count = roomsRepository.count();
        Assert.assertEquals(0, count);
    }

    @Test
    public void testRoomNotFound() throws Exception {
        mockMvc.perform(get("/rooms/223424234")
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateRoom() throws Exception {
        mockMvc.perform(post("/rooms")
                .content(this.json(new RoomVM(null, 2D, persisted.getId(), BASIC, null, null, null)))
                .contentType(contentType))
                .andExpect(status().isCreated());
        final long count = roomsRepository.count();
        Assert.assertEquals(1, count);
    }

    @Test
    public void testDeleteRoom() throws Exception {
        //given create room
        final RoomEntity room = roomsRepository.saveAndFlush(new RoomEntity(null, 2D, persisted, BASIC, null, null, null, null));
        long count = roomsRepository.count();
        Assert.assertEquals(1, count);
        //delete room by id
        mockMvc.perform(delete("/rooms/" + room.getId())
                .contentType(contentType))
                .andExpect(status().isNoContent());

        //expect zero rooms in DB
        count = roomsRepository.count();
        Assert.assertEquals(0, count);
    }

    @Test
    public void testUpdateRoom() throws Exception {
        //given create room
        final RoomEntity room = roomsRepository.saveAndFlush(new RoomEntity(null, 2D, persisted, BASIC, null, null, null, null));
        final RoomVM room1 = new RoomVM(null, 2D, persisted.getId(), PRESIDENT, null, null, null);

        mockMvc.perform(put("/rooms/" + room.getId())
                .content(json(room1))
                .contentType(contentType))
                .andExpect(status().isOk());

        final List<RoomEntity> list = roomsRepository.findAll();
        Assert.assertEquals(PRESIDENT, list.get(0).getCategory());
    }

}