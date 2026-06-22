package com.unite.service.impl;
import com.unite.dto.AddressDto;
import com.unite.entity.Address;
import com.unite.enums.AddressType;
import com.unite.exceptions.ResourceNotFoundException;
import com.unite.repository.AddressRepository;
import com.unite.service.AddressService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private static final Logger log =
            LoggerFactory.getLogger(AddressServiceImpl.class);

    private final AddressRepository repository;
        private final ModelMapper modelMapper;

        public AddressServiceImpl(AddressRepository repository, ModelMapper modelMapper) {
            this.repository = repository;
            this.modelMapper = modelMapper;
        }

        public List<AddressDto> getAll() {
            log.info("Fetching all addresses");
            return repository.findAll()
                    .stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        }



    public AddressDto getById(String addressId) {
        log.info("Fetching address by id: {}", addressId);

        Address address = repository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Address not found with id: " + addressId));

        return convertToDto(address);
    }

    public List<AddressDto> getByUser(String userId, AddressType type) {

        log.info("Fetching addresses for userId={}, type={}", userId, type);

        List<Address> results = repository.findByUserId(userId);

        if (type != null) {
            results = results.stream()
                    .filter(a -> a.getType() == type)
                    .collect(Collectors.toList());
        }

        if (results.isEmpty()) {
            throw new ResourceNotFoundException(
                    String.format("No addresses found for userId=%s%s",
                            userId,
                            type != null ? ", type=" + type : ""));
        }

        return results.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AddressDto create(AddressDto dto) {
        Address address = modelMapper.map(dto, Address.class);

        if (address.getId() == null) {
            address.setId(java.util.UUID.randomUUID().toString());
        }

        repository.save(address);
        log.info("Address created successfully with id {}", address.getId());
        return modelMapper.map(address, AddressDto.class);
    }

    @Override
    public void delete(String id) {
        Address address = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Address not found with id: " + id));

        repository.deleteById(id);

        log.info("Address deleted successfully with id {}", id);
    }

    @Override
    public AddressDto update(AddressDto dto, String id) {

        Address existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Address not found with id: " + id));

        existing.setUserId(dto.getUserId());
        existing.setAddressName(dto.getAddressName());
        existing.setStreet(dto.getStreet());
        existing.setCity(dto.getCity());
        existing.setPostalCode(dto.getPostalCode());
        existing.setRegion(dto.getRegion());
        existing.setCountry(dto.getCountry());
        existing.setType(dto.getType());

         repository.update(existing);

        log.info("Address updated successfully with id {}", id);

        return modelMapper.map(existing, AddressDto.class);
    }


    private AddressDto convertToDto(Address address) {
            return modelMapper.map(address, AddressDto.class);
        }


    }

