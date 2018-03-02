package com.booking.rest;

import com.booking.models.PageableApi;
import com.booking.models.domain.BookingEntity;
import com.booking.models.domain.RoomEntity;
import com.booking.models.domain.UserEntity;
import com.booking.models.vm.BookingVM;
import com.booking.services.BookingsService;
import com.booking.services.RoomsService;
import com.booking.services.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.booking.GeneralConstants.ID;
import static com.booking.GeneralConstants.ID_PATH;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/bookings")
@AllArgsConstructor
public class BookingsController {

    private final BookingsService bookingsService;
    private final RoomsService roomsService;
    private final UsersService usersService;

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody final BookingVM vm) {
        final BookingEntity entity = toEntity(vm);
        final BookingEntity saved = bookingsService.save(entity);
        final BookingVM savedVM = toVM(saved);
        return new ResponseEntity<>(savedVM, CREATED);
    }

    @PageableApi
    @GetMapping
    public ResponseEntity getPage(final Pageable pageable) {
        final Page<BookingEntity> page = bookingsService.getPerPage(pageable);
        final List<BookingVM> bookingVMS = page.getContent().stream()
                .map(this::toVM)
                .collect(Collectors.toList());
        final PageImpl<BookingVM> vmPage = new PageImpl<>(bookingVMS, pageable, page.getTotalElements());
        return new ResponseEntity<>(vmPage, OK);
    }

    @GetMapping(ID_PATH)
    public ResponseEntity getById(@PathVariable(ID) final Long id) {
        final BookingEntity hotelEntity = bookingsService.findById(id);
        final BookingVM vm = toVM(hotelEntity);
        return new ResponseEntity<>(vm, OK);
    }

    @DeleteMapping(ID_PATH)
    public ResponseEntity delete(@PathVariable(ID) final Long id) {
        bookingsService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @PutMapping(ID_PATH)
    public ResponseEntity update(@PathVariable(ID) final Long id, @RequestBody BookingVM bookingVM) {
        final BookingEntity entity = toEntity(bookingVM);
        entity.setId(id);
        BookingEntity bookingEntity = bookingsService.save(entity);
        BookingVM vm = toVM(bookingEntity);
        return new ResponseEntity<>(vm , HttpStatus.OK);
    }

    private BookingVM toVM(final BookingEntity entity) {
        final BookingVM vm = new BookingVM();
        vm.setCreatedDate(entity.getCreatedDate());
        vm.setId(entity.getId());
        vm.setLastModifiedDate(entity.getLastModifiedDate());
        vm.setUser(entity.getUser().getId());
        vm.setFrom(entity.getFromDate());
        vm.setTo(entity.getToDate());
        vm.setRoomIds(entity.getRooms().stream()
                .map(RoomEntity::getId)
                .collect(Collectors.toList()));
        return vm;
    }

    private BookingEntity toEntity(final BookingVM vm) {
        final List<RoomEntity> rooms = vm.getRoomIds().stream()
                .map(roomsService::findById)
                .collect(Collectors.toList());
        final UserEntity user = usersService.findById(vm.getUser());
        final BookingEntity entity = new BookingEntity();
        entity.setCreatedDate(vm.getCreatedDate());
        entity.setLastModifiedDate(vm.getLastModifiedDate());
        entity.setId(vm.getId());
        entity.setUser(user);
        entity.setFromDate(vm.getFrom());
        entity.setToDate(vm.getTo());
        entity.setRooms(rooms);
        return entity;
    }

}
