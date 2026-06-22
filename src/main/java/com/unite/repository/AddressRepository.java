package com.unite.repository;


import com.unite.entity.Address;
import com.unite.enums.AddressType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class AddressRepository {

    private static final Logger log =
            LoggerFactory.getLogger(AddressRepository.class);

    private final JdbcTemplate jdbcTemplate;

    public AddressRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Address> findAll() {
        return jdbcTemplate.query("SELECT * FROM addresses", this::mapRow);
    }

    public List<Address> findByUserId(String userId) {
        log.info("DB QUERY userId = {}", userId);

        List<Address> list = jdbcTemplate.query(
                "SELECT * FROM addresses WHERE user_id = ?",
                this::mapRow,
                userId
        );

        log.info("RESULT SIZE = {}", list.size());

        return list;
    }

    public int save(Address address) {
        return jdbcTemplate.update("""
            INSERT INTO addresses 
            (id, user_id, address_name, street, city, postal_code, region, country, address_type)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """,
                address.getId(),
                address.getUserId(),
                address.getAddressName(),
                address.getStreet(),
                address.getCity(),
                address.getPostalCode(),
                address.getRegion(),
                address.getCountry(),
                address.getType().name()
        );
    }

    private Address mapRow(ResultSet rs, int rowNum) throws SQLException {
        Address a = new Address();
        a.setId(rs.getString("id"));
        a.setUserId(rs.getString("user_id"));
        a.setAddressName(rs.getString("address_name"));
        a.setStreet(rs.getString("street"));
        a.setCity(rs.getString("city"));
        a.setPostalCode(rs.getString("postal_code"));
        a.setRegion(rs.getString("region"));
        a.setCountry(rs.getString("country"));
        a.setType(AddressType.valueOf(rs.getString("address_type").toUpperCase()));
        return a;
    }



    public Optional<Address> findById(String id) {
        List<Address> results = jdbcTemplate.query(
                "SELECT * FROM addresses WHERE id = ?",
                this::mapRow,
                id
        );
        return results.stream().findFirst();
    }

    public int update(Address address) {

        log.info(
                "Updating id={}, city={}, country={}",
                address.getId(),
                address.getCity(),
                address.getCountry()
        );

        int rows= jdbcTemplate.update("""
        UPDATE addresses
        SET user_id = ?,
            address_name = ?,
            street = ?,
            city = ?,
            postal_code = ?,
            region = ?,
            country = ?,
            address_type = ?
        WHERE id = ?
        """,
                address.getUserId(),
                address.getAddressName(),
                address.getStreet(),
                address.getCity(),
                address.getPostalCode(),
                address.getRegion(),
                address.getCountry(),
                address.getType().name(),
                address.getId()
        );
        return rows;
    }

    public int deleteById(String id) {
        return jdbcTemplate.update(
                "DELETE FROM addresses WHERE id = ?",
                id
        );
    }
}