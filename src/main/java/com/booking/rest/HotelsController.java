package com.booking.rest;

import com.booking.models.PageableApi;
import com.booking.models.domain.HotelEntity;
import com.booking.models.vm.HotelVM;
import com.booking.services.HotelsService;
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
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/hotels")
@AllArgsConstructor
public class HotelsController {

    private final HotelsService hotelsService;

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody final HotelVM vm) {
        final HotelEntity entity = toEntity(vm);
        final HotelEntity saved = hotelsService.save(entity);
        final HotelVM savedVM = toVM(saved);
        return new ResponseEntity<>(savedVM, CREATED);
    }

    @PageableApi
    @GetMapping
    public ResponseEntity getPage(final Pageable pageable) {
        final Page<HotelEntity> page = hotelsService.getPerPage(pageable);
        final List<HotelVM> hotelVMS = page.getContent().stream()
                .map(this::toVM)
                .collect(Collectors.toList());
        final PageImpl<HotelVM> vmPage = new PageImpl<>(hotelVMS, pageable, page.getTotalElements());
        return new ResponseEntity<>(vmPage, OK);
    }

    @GetMapping(ID_PATH)
    public ResponseEntity getById(@PathVariable(ID) final Long id) {
        final HotelEntity hotelEntity = hotelsService.findById(id);
        final HotelVM vm = toVM(hotelEntity);
        return new ResponseEntity<>(vm, OK);
    }

    @DeleteMapping(ID_PATH)
    public ResponseEntity delete(@PathVariable(ID) final Long id) {
        hotelsService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }


    @PutMapping(ID_PATH)
    public ResponseEntity update(@PathVariable(ID) final Long id, @Valid @RequestBody final HotelVM hotelVM) {
        final HotelEntity entity = toEntity(hotelVM);
        entity.setId(id);
        final HotelEntity hotelEntity = hotelsService.save(entity);
        final HotelVM vm = toVM(hotelEntity);
        return new ResponseEntity<>(vm, HttpStatus.OK);
    }

    private HotelVM toVM(final HotelEntity entity) {
        final HotelVM vm = new HotelVM();
        vm.setCreatedDate(entity.getCreatedDate());
        vm.setId(entity.getId());
        vm.setLastModifiedDate(entity.getLastModifiedDate());
        vm.setName(entity.getName());
        vm.setStars(entity.getStars());
        return vm;
    }

    private HotelEntity toEntity(final HotelVM vm) {
        final HotelEntity entity = new HotelEntity();
        entity.setCreatedDate(vm.getCreatedDate());
        entity.setLastModifiedDate(vm.getLastModifiedDate());
        entity.setId(vm.getId());
        entity.setName(vm.getName());
        entity.setStars(vm.getStars());
        return entity;
    }

}
