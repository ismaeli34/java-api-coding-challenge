package com.unite.controller;

import com.unite.dto.AddressDto;
import com.unite.enums.AddressType;
import com.unite.response.ApiResponseMessage;
import com.unite.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/v1/addresses")
public class AddressesController {

    private final AddressService addressService;

    public AddressesController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public List<AddressDto> getAll() {
        return addressService.getAll();
    }

    @GetMapping("/user/{userId}")
    public List<AddressDto> getByUser(
            @PathVariable String userId,
            @RequestParam(required = false) AddressType type
    ) {
        return addressService.getByUser(userId, type);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressDto> getById(
            @PathVariable String addressId
    ) {
        AddressDto addressDto = addressService.getById(addressId);
        return new ResponseEntity<>(addressDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AddressDto>  create(@Valid  @RequestBody AddressDto dto) {
        AddressDto addressDto = addressService.create(dto);
        return new ResponseEntity<>(addressDto, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDto> update(
            @PathVariable String id,
            @Valid @RequestBody AddressDto dto) {
        AddressDto update = addressService.update(dto, id);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseMessage>  delete(@PathVariable String id) {
        addressService.delete(id);
        ApiResponseMessage message = new ApiResponseMessage("Address is deleted Successfully ", true, HttpStatus.OK);
        return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);

    }
}

