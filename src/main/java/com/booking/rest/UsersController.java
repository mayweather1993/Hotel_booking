package com.booking.rest;

import com.booking.models.BookingsDTO;
import com.booking.models.PageableApi;
import com.booking.models.domain.BookingEntity;
import com.booking.models.domain.UserEntity;
import com.booking.models.vm.UserVM;
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
import java.util.Set;
import java.util.stream.Collectors;

import static com.booking.GeneralConstants.ID;
import static com.booking.GeneralConstants.ID_PATH;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody final UserVM vm) {
        final UserEntity entity = toEntity(vm);
        final UserEntity saved = usersService.save(entity);
        final UserVM savedVM = toVM(saved);
        return new ResponseEntity<>(savedVM, CREATED);
    }

    @PageableApi
    @GetMapping
    public ResponseEntity getPage(final Pageable pageable) {
        final Page<UserEntity> page = usersService.getPerPage(pageable);
        final List<UserVM> vms = page.getContent().stream()
                .map(this::toVM)
                .collect(Collectors.toList());
        final PageImpl<UserVM> vmPage = new PageImpl<>(vms, pageable, page.getTotalElements());
        return new ResponseEntity<>(vmPage, OK);
    }

    @GetMapping(ID_PATH)
    public ResponseEntity getById(@PathVariable(ID) final Long id) {
        final UserEntity entity = usersService.findById(id);
        final UserVM vm = toVM(entity);
        return new ResponseEntity<>(vm, OK);
    }

    @GetMapping(ID_PATH + "/bookings")
    public ResponseEntity getUserBookings(@PathVariable(ID) final Long id) {
        final Set<BookingsDTO> bookings = usersService.getBookings(id);
        return new ResponseEntity<>(bookings, OK);
    }

    @DeleteMapping(ID_PATH)
    public ResponseEntity delete(@PathVariable(ID) final Long id) {
        usersService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }


    @PutMapping(ID_PATH)
    public ResponseEntity update(@PathVariable(ID) final Long id, @RequestBody final UserVM userVM) {
        final UserEntity entity = toEntity(userVM);
        entity.setId(id);
        final UserEntity userEntity = usersService.save(entity);
        final UserVM vm = toVM(userEntity);
        return new ResponseEntity<>(vm , HttpStatus.OK);
    }


    private UserVM toVM(final UserEntity entity) {
        final UserVM vm = new UserVM();
        vm.setCreatedDate(entity.getCreatedDate());
        vm.setId(entity.getId());
        vm.setLastModifiedDate(entity.getLastModifiedDate());
        vm.setEmail(entity.getEmail());
        vm.setBookings(
                entity.getBookings().stream()
                        .map(BookingEntity::getId)
                        .collect(Collectors.toList())
        );
        return vm;
    }

    private UserEntity toEntity(final UserVM vm) {
        final UserEntity entity = new UserEntity();
        entity.setCreatedDate(vm.getCreatedDate());
        entity.setLastModifiedDate(vm.getLastModifiedDate());
        entity.setId(vm.getId());
        entity.setEmail(vm.getEmail());
        return entity;
    }

}
