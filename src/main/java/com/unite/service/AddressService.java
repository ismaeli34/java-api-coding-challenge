package com.unite.service;

import com.unite.dto.AddressDto;
import com.unite.enums.AddressType;

import java.util.List;

public interface AddressService {
    List<AddressDto> getAll();
    AddressDto getById(String addressId);
    List<AddressDto> getByUser(String userId, AddressType type);
    AddressDto create(AddressDto dto);
     void delete(String id);
     AddressDto update(AddressDto dto, String id);


}
