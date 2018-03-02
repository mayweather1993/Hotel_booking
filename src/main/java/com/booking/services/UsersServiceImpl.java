package com.booking.services;

import com.booking.models.BookingsDTO;
import com.booking.models.domain.BookingEntity;
import com.booking.models.domain.HotelEntity;
import com.booking.models.domain.RoomEntity;
import com.booking.models.domain.UserEntity;
import com.booking.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UsersServiceImpl extends AbstractService<UsersRepository, UserEntity> implements UsersService {
    public UsersServiceImpl(final UsersRepository repository) {
        super(repository);
    }

    @Override
    public Set<BookingsDTO> getBookings(final Long userId) {
        final UserEntity userEntity = findById(userId);
        final List<BookingEntity> bookings = userEntity.getBookings();

        final Set<BookingsDTO> bookingsSet = new HashSet<>(bookings.size());
        for (BookingEntity bookingEntity : bookings) {
            final List<String> hotels = new ArrayList<>();
            final Double price = bookingEntity.getPrice();
            for (RoomEntity roomEntity : bookingEntity.getRooms()) {
                final HotelEntity hotel = roomEntity.getHotel();
                hotels.add(String.format("%s(%d)_%s", hotel.getName(), hotel.getStars(), roomEntity.getCategory().name()));
            }
            bookingsSet.add(new BookingsDTO(bookingEntity.getFromDate(), bookingEntity.getToDate(), hotels, price));
        }
        return bookingsSet;
    }
}
