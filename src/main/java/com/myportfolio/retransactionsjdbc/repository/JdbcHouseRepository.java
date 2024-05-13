package com.myportfolio.retransactionsjdbc.repository;

import com.myportfolio.retransactionsjdbc.model.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class JdbcHouseRepository implements HouseRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public int saveHouse(House house) {
        try {
            String sql = "INSERT INTO House(house_address, house_price, seller_id) VALUES(?,?,?)";

            if("".equals(house.getHouse_address()) || house.getHouse_price().compareTo(BigDecimal.ZERO) <= 0 || house.getSeller_id() == 0){
                throw new IllegalArgumentException("Invalid value provided. Address, price, and seller id cannot be blank or 0.");
            }

            return jdbcTemplate.update(sql, house.getHouse_address(), house.getHouse_price(), house.getSeller_id());
        }catch (DataAccessException dae){
            return 0;
        }

    }

    @Override
    public int updateHouse(House house) {
        try {
            String sql = "UPDATE House SET house_address=?, house_price=?, seller_id=? WHERE house_id=?";

            return jdbcTemplate.update(sql, house.getHouse_address(), house.getHouse_price(), house.getSeller_id(), house.getHouse_id());
        }catch (DataAccessException dae) {
            throw dae;
        }
    }

    @Override
    public House findHouseById(int houseID) {
        try {
            String sql = "SELECT * FROM House WHERE house_id=?";

            if(houseID <= 0){
                throw new IllegalArgumentException("House id cannot be 0 or a negative number.");
            }

            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(House.class), houseID);
        }catch (DataAccessException dae){
            System.out.println("House ID not found. " + dae.getMessage());
            return null;
        }
    }

    @Override
    public House findHouseByAddress(String houseAddress) {
        try {
            String sql = "SELECT * FROM House WHERE house_address=?";

            if (houseAddress.length() == 0) {
                throw new IllegalArgumentException("Address cannot be blank");
            }
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(House.class), houseAddress);
        }catch (DataAccessException dae){
            System.out.println("Cannot find house with address " + houseAddress + ". " + dae.getMessage());
            return null;
        }
    }

    @Override
    public int deleteHouseById(int houseID) {
        try {
            String sql = "DELETE FROM House WHERE house_id=?";

            if (houseID <= 0){
                throw new IllegalArgumentException("House Id cannot be 0 or a negative number.");
            }
            return jdbcTemplate.update(sql, houseID);
        }catch (DataAccessException dae){
            System.out.println("Cannot delete house with id " + houseID + ". " + dae.getMessage());
            return 0;
        }
    }

    @Override
    public int deleteAllHouses() {
        try {
            String sql = "DELETE FROM House";

            return jdbcTemplate.update(sql);
        }catch (DataAccessException dae){
            System.out.println("Something went wrong. Cannot delete houses. " + dae.getMessage());
            return 0;
        }
    }
}
