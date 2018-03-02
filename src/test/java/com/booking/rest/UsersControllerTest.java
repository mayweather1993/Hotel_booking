package com.booking.rest;

import com.booking.models.domain.UserEntity;
import com.booking.models.vm.UserVM;
import com.booking.repository.UsersRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UsersControllerTest extends AbstractControllerTest {

    @Autowired
    private UsersRepository usersRepository;

    @Before
    public void setup() {
        this.usersRepository.deleteAllInBatch();
    }

    @Test
    public void testCreateInvalidUser() throws Exception {
        //create hotel all fields are null
        mockMvc.perform(post("/users")
                .content(this.json(new UserVM()))
                .contentType(contentType))
                .andExpect(status().isBadRequest());

        long count = usersRepository.count();
        Assert.assertEquals(0, count);

        //create invalid email
        mockMvc.perform(post("/users")
                .content(this.json(new UserVM(null, "test", null, null, null)))
                .contentType(contentType))
                .andExpect(status().isBadRequest());

        count = usersRepository.count();
        Assert.assertEquals(0, count);

        //create blank email
        mockMvc.perform(post("/users")
                .content(this.json(new UserVM(null, "  ", null, null, null)))
                .contentType(contentType))
                .andExpect(status().isBadRequest());

        count = usersRepository.count();
        Assert.assertEquals(0, count);
    }

    @Test
    public void testCreateUser() throws Exception {
        mockMvc.perform(post("/users")
                .content(this.json(new UserVM(null, "user@user.com", null, null, null)))
                .contentType(contentType))
                .andExpect(status().isCreated());
        final long count = usersRepository.count();
        Assert.assertEquals(1, count);
    }

    @Test
    public void testUserNotFound() throws Exception {
        mockMvc.perform(get("/users/223424234")
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteUser() throws Exception {
        //Given: create user
        final UserEntity user = usersRepository.saveAndFlush(new UserEntity(null, "user@email.com", null, null, null));

        //expect created
        long count = usersRepository.count();
        Assert.assertEquals(1, count);

        //delete user by id
        mockMvc.perform(delete("/users/" + user.getId())
                .contentType(contentType))
                .andExpect(status().isNoContent());

        //expect zero users in DB
        count = usersRepository.count();
        Assert.assertEquals(0, count);
    }

    @Test
    public void testUpdateUser() throws Exception {
        final UserEntity user = usersRepository.saveAndFlush(new UserEntity(null, "user@email.com", null, null, null));
        final UserVM userVM = new UserVM(null, "userVM@user.com", null, null, null);
        mockMvc.perform(put("/users/" + user.getId())
                .content(json(userVM))
                .contentType(contentType))
                .andExpect(status().isOk());


        Optional<UserEntity> userEntity = usersRepository.findById(user.getId());
        Assert.assertEquals("userVM@user.com", userEntity.get().getEmail());
    }

}














