package com.booking.services;

import com.booking.exceptions.InvalidDataException;
import com.booking.models.domain.AdditionalOptionsEntity;
import com.booking.models.domain.BookingEntity;
import com.booking.models.domain.RoomEntity;
import com.booking.repository.BookingsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Slf4j
public class BookingsServiceImpl extends AbstractService<BookingsRepository, BookingEntity> implements BookingsService {
    public BookingsServiceImpl(final BookingsRepository repository) {
        super(repository);
    }

    @Override
    public BookingEntity save(final BookingEntity entity) {
        //validation
        final Instant fromDate = entity.getFromDate();
        final Instant toDate = entity.getToDate();
        final LocalDate fromLocalDate = LocalDateTime.ofInstant(fromDate, ZoneId.systemDefault()).toLocalDate();
        final LocalDate nowLocalDate = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).toLocalDate();
        if (nowLocalDate.compareTo(fromLocalDate) > 0) {
            throw new InvalidDataException("You can't book room in past time.");
        }
        if (toDate.compareTo(fromDate) < 0) {
            throw new InvalidDataException("To date can't be less than from date.");
        }
        final long days = DAYS.between(fromDate, toDate);
        if (days < 1) {
            throw new InvalidDataException("You have to book room for 1 day at least");
        }

        //calculating price
        final double roomsPrice = calculatePrice(entity.getRooms(), days);
        entity.setPrice(roomsPrice);
        return super.save(entity);
    }

    private double calculatePrice(final List<RoomEntity> rooms, final long days) {
        Double roomsPrice = 0D;
        for (RoomEntity room : rooms) {
            final Double roomPricePerDay = room.getPrice();
            final double roomAddOptionsPricePerDay = room.getAdditionalOptionEntities()
                    .stream()
                    .mapToDouble(AdditionalOptionsEntity::getPrice)
                    .sum();

            roomsPrice = (roomPricePerDay + roomAddOptionsPricePerDay) * days + roomsPrice;
        }

        return roomsPrice;
    }
}
