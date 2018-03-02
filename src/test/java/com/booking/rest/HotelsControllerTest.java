package com.booking.rest;

import com.booking.models.domain.HotelEntity;
import com.booking.models.vm.HotelVM;
import com.booking.repository.HotelsRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HotelsControllerTest extends AbstractControllerTest {

    @Autowired
    private HotelsRepository hotelsRepository;

    @Before
    public void setup() {
        this.hotelsRepository.deleteAllInBatch();
    }

    @Test
    public void testCreateInvalidHotel() throws Exception {
        //create hotel all fields are null
        mockMvc.perform(post("/hotels")
                .content(this.json(new HotelVM()))
                .contentType(contentType))
                .andExpect(status().isBadRequest());

        long count = hotelsRepository.count();
        Assert.assertEquals(0, count);

        //create hotel stars field is 0
        mockMvc.perform(post("/hotels")
                .content(this.json(new HotelVM(null, "hotel", 0, null, null)))
                .contentType(contentType))
                .andExpect(status().isBadRequest());

        count = hotelsRepository.count();
        Assert.assertEquals(0, count);

        //create hotel stars field is 6
        mockMvc.perform(post("/hotels")
                .content(this.json(new HotelVM(null, "hotel", 6, null, null)))
                .contentType(contentType))
                .andExpect(status().isBadRequest());

        count = hotelsRepository.count();
        Assert.assertEquals(0, count);

        //create hotel stars field is correct, name is null
        mockMvc.perform(post("/hotels")
                .content(this.json(new HotelVM(null, null, 4, null, null)))
                .contentType(contentType))
                .andExpect(status().isBadRequest());

        count = hotelsRepository.count();
        Assert.assertEquals(0, count);

        //create hotel stars field is correct, name is blank
        mockMvc.perform(post("/hotels")
                .content(this.json(new HotelVM(null, "   ", 4, null, null)))
                .contentType(contentType))
                .andExpect(status().isBadRequest());

        count = hotelsRepository.count();
        Assert.assertEquals(0, count);
    }

    @Test
    public void testCreateHotel() throws Exception {
        mockMvc.perform(post("/hotels")
                .content(this.json(new HotelVM(null, "hotel", 2, null, null)))
                .contentType(contentType))
                .andExpect(status().isCreated());
        final long count = hotelsRepository.count();
        Assert.assertEquals(1, count);
    }

    @Test
    public void testHotelNotFound() throws Exception {
        mockMvc.perform(get("/hotels/223424234")
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteHotel() throws Exception {
        //Given: create hotel
        final HotelEntity hotel = hotelsRepository.saveAndFlush(new HotelEntity(null, "hotel", 2, null, null));

        //expect created
        long count = hotelsRepository.count();
        Assert.assertEquals(1, count);

        //delete hotel by id
        mockMvc.perform(delete("/hotels/" + hotel.getId())
                .contentType(contentType))
                .andExpect(status().isNoContent());

        //expect zero hotels in DB
        count = hotelsRepository.count();
        Assert.assertEquals(0, count);
    }

    @Test
    public void testUpdateHotel() throws Exception {
        //given create room
        final HotelEntity hotelEntity = hotelsRepository.saveAndFlush(new HotelEntity(null, "hotel", 2, null, null));
        final HotelVM hotelVM = new HotelVM(null, "hotel", 1, null, null);

        mockMvc.perform(put("/hotels/" + hotelEntity.getId())
                .content(json(hotelVM))
                .contentType(contentType))
                .andExpect(status().isOk());


        List<HotelEntity> list = hotelsRepository.findAll();
        Assert.assertEquals(1 , list.get(0).getStars());
    }
}