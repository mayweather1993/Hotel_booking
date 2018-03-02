package com.booking.services;

import com.booking.models.BookingsDTO;
import com.booking.models.domain.UserEntity;

import java.util.Set;

public interface UsersService extends CrudService<UserEntity> {

    Set<BookingsDTO> getBookings(final Long userId);

}
