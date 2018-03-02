package com.booking.rest;

import com.booking.models.PageableApi;
import com.booking.models.RoomCategory;
import com.booking.models.domain.AdditionalOptionsEntity;
import com.booking.models.domain.HotelEntity;
import com.booking.models.domain.RoomEntity;
import com.booking.models.vm.RoomVM;
import com.booking.services.HotelsService;
import com.booking.services.RoomOptionsService;
import com.booking.services.RoomsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.booking.GeneralConstants.ID;
import static com.booking.GeneralConstants.ID_PATH;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/rooms")
@AllArgsConstructor
public class RoomsController {

    private final RoomsService roomsService;
    private final HotelsService hotelsService;
    private final RoomOptionsService roomOptionsService;

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody final RoomVM roomVM) {
        final RoomEntity entity = toEntity(roomVM);
        final RoomEntity saved = roomsService.save(entity);
        final RoomVM vm = toVM(saved);
        return new ResponseEntity<>(vm, CREATED);
    }

    @PageableApi
    @GetMapping
    public ResponseEntity getPage(@RequestParam(name = "category", required = false) final RoomCategory category, final Pageable pageable) {
        final Page<RoomEntity> page = roomsService.findByCategory(category, pageable);
        final List<RoomVM> roomVMS = page.getContent().stream()
                .map(this::toVM)
                .collect(Collectors.toList());
        final PageImpl<RoomVM> vmPage = new PageImpl<>(roomVMS, pageable, page.getTotalElements());
        return new ResponseEntity<>(vmPage, OK);
    }

    @GetMapping(ID_PATH)
    public ResponseEntity getById(@PathVariable(ID) final Long id) {
        final RoomEntity roomEntity = roomsService.findById(id);
        final RoomVM roomVM = toVM(roomEntity);
        return new ResponseEntity<>(roomVM, OK);
    }

    @DeleteMapping(ID_PATH)
    public ResponseEntity delete(@PathVariable(ID) final Long id) {
        roomsService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }


    @PutMapping(ID_PATH)
    public ResponseEntity update(@PathVariable(ID) final Long id, @RequestBody final RoomVM roomVM) {
        final RoomEntity entity = toEntity(roomVM);
        entity.setId(id);
        final RoomEntity roomEntity = roomsService.save(entity);
        final RoomVM vm = toVM(roomEntity);
        return new ResponseEntity<>(vm, HttpStatus.OK);
    }

    private RoomVM toVM(final RoomEntity entity) {
        final RoomVM vm = new RoomVM();
        vm.setCategory(entity.getCategory());
        vm.setCreatedDate(entity.getCreatedDate());
        vm.setLastModifiedDate(entity.getLastModifiedDate());
        vm.setHotelId(entity.getHotel().getId());
        vm.setId(entity.getId());
        vm.setPrice(entity.getPrice());
        vm.setAdditionalOptions(entity.getAdditionalOptionEntities().stream()
                .map(AdditionalOptionsEntity::getId)
                .collect(Collectors.toList()));
        return vm;
    }

    private RoomEntity toEntity(final RoomVM roomVM) {
        final RoomEntity entity = new RoomEntity();

        final List<Long> additionalOptions = roomVM.getAdditionalOptions();
        if (!CollectionUtils.isEmpty(additionalOptions)) {
            final List<AdditionalOptionsEntity> options = roomVM.getAdditionalOptions()
                    .stream()
                    .map(roomOptionsService::findById)
                    .collect(Collectors.toList());
            entity.setAdditionalOptionEntities(options);
        }

        final HotelEntity hotelEntity = hotelsService.findById(roomVM.getHotelId());
        entity.setCategory(roomVM.getCategory());
        entity.setCreatedDate(roomVM.getCreatedDate());
        entity.setLastModifiedDate(roomVM.getLastModifiedDate());
        entity.setId(roomVM.getId());
        entity.setPrice(roomVM.getPrice());
        entity.setHotel(hotelEntity);
        return entity;
    }
}
