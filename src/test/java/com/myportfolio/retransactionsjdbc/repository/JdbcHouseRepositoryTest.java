package com.myportfolio.retransactionsjdbc.repository;

import com.myportfolio.retransactionsjdbc.model.House;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class JdbcHouseRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private JdbcHouseRepository jdbcHouseRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    // --- test saveHouse method ---

    @Test
    void allFieldsWithValidDataSaveHouse(){
        House houseToSave = new House("123 Test Street, New York, NY 12345 USA", BigDecimal.TEN, 1000);

        when(jdbcTemplate.update(anyString(), any(), any(), any())).thenReturn(1);

        int rowsCreated = jdbcHouseRepository.saveHouse(houseToSave);

        assertEquals(1, rowsCreated);
        verify(jdbcTemplate).update(anyString(), eq(houseToSave.getHouse_address()), eq(houseToSave.getHouse_price()), eq(houseToSave.getSeller_id()));
    }

    @Test
    void emptyAddressValueThrowsIllegalArgumentException(){
        House house = new House("", BigDecimal.TEN, 1000);

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcHouseRepository.saveHouse(house);
        });
    }

    @Test
    void zeroBalanceValueThrowsIllegalArgumentException(){
        House house = new House("123 Test Street, New York, NY 12345 USA", BigDecimal.ZERO, 1000);

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcHouseRepository.saveHouse(house);
        });
    }

    @Test
    void sellerIdValueZeroThrowsIllegalArgumentException(){
        House house = new House("123 Test Street, New York, NY 12345 USA", BigDecimal.TEN, 0);

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcHouseRepository.saveHouse(house);
        });
    }

    @Test
    void invalidSqlQueryThrowsDataAccessException(){
        House house = new House("123 Test Street, New York, NY 12345 USA", BigDecimal.TEN, 1000);

        when(jdbcTemplate.update(anyString(), any(), any(), any())).thenThrow(new DataAccessResourceFailureException("Invalid SQL query."));

        int rowsAdded = jdbcHouseRepository.saveHouse(house);

        assertEquals(0, rowsAdded);
    }

    // --- test updateHouse method ---

    @Test
    void validDataUpdateHouse(){
        House houseToUpdate = new House(6, "123 Test Street, New York, NY 12345 USA", BigDecimal.valueOf(720000.00), 18);

//        when(jdbcTemplate.update(anyString(), any(), any(), any(), any())).thenReturn(1);

        when(jdbcTemplate.update(eq("UPDATE House SET house_address=?, house_price=?, seller_id=? WHERE house_id=?"), anyString(), any(BigDecimal.class), anyInt(), eq(6))).thenReturn(1);

        int rowsUpdated = jdbcHouseRepository.updateHouse(houseToUpdate);

        assertEquals(1, rowsUpdated);
        assertEquals(6, houseToUpdate.getHouse_id());
        assertEquals("123 Test Street, New York, NY 12345 USA", houseToUpdate.getHouse_address());
    }

    @Test
    void invalidUpdateHouseSqlQueryThrowsDataAccessException(){
        House houseToUpdate = new House("123 Test Street, New York, NY 12345 USA", BigDecimal.valueOf(720000.00), 18);

        when(jdbcTemplate.update(anyString(), anyString(), any(BigDecimal.class), anyInt(), eq(6))).thenThrow(new DataAccessResourceFailureException("Invalid SQL query."));

        int rowsUpdated = jdbcHouseRepository.updateHouse(houseToUpdate);

        assertEquals(0, rowsUpdated);
    }

    // --- test findHouseById method ---

    @Test
    void invalidFindHouseByIdSqlQueryThrowsDataAccessException(){
        int houseID = 6;

        when(jdbcTemplate.queryForObject(anyString(), any(BeanPropertyRowMapper.class), eq(houseID))).thenThrow(new DataAccessResourceFailureException("Invalid SQL query."));

        House houseObj = jdbcHouseRepository.findHouseById(houseID);

        assertNull(houseObj);
    }

    @Test
    void houseIdValueZeroThrowsIllegalArgumentException(){
        int houseID = 0;

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcHouseRepository.findHouseById(houseID);
        });
    }

    @Test
    void houseIdNegativeValueThrowsIllegalArgumentException(){
        int houseID = -1;

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcHouseRepository.findHouseById(houseID);
        });
    }

    // --- test findHouseByAddress method ---

    @Test
    void invalidFindHouseByAddressSqlQueryThrowsDataAccessException(){
        String houseAddress = "123 Test Street, New York, NY 12345 USA";

        when(jdbcTemplate.queryForObject(anyString(), any(BeanPropertyRowMapper.class), eq(houseAddress))).thenThrow(new DataAccessResourceFailureException("Invalid SQL query."));

        House houseObj = jdbcHouseRepository.findHouseByAddress(houseAddress);

        assertNull(houseObj);
    }

    @Test
    void houseAddressEmptyValueThrowsIllegalArgumentException(){
        String houseAddress = "";

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcHouseRepository.findHouseByAddress(houseAddress);
        });
    }

    // --- test deleteHouseById method ---

    @Test
    void invalidDeleteByIdSqlQueryThrowsDataAccessException(){
        int houseID = 1;

        when(jdbcTemplate.update(anyString(), eq(houseID))).thenThrow(new DataAccessResourceFailureException("Invalid SQL query."));

        int rowsDeleted = jdbcHouseRepository.deleteHouseById(houseID);

        assertEquals(0, rowsDeleted);
    }

    @Test
    void houseIdWithValueZeroThrowsIllegalArgumentException(){
        int houseID = 0;

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcHouseRepository.deleteHouseById(houseID);
        });
    }

    @Test
    void negativeHouseIdThrowsIllegalArgumentException(){
        int houseID = -1;

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcHouseRepository.deleteHouseById(houseID);
        });
    }

    // --- test deleteAllHouses method ---

    @Test
    void invalidDeleteAllHousesSqlQueryThrowsDataAccessException(){
        when(jdbcTemplate.update(anyString())).thenThrow(new DataAccessResourceFailureException("Invalid SQL query."));

        int rowsDeleted = jdbcHouseRepository.deleteAllHouses();

        assertEquals(0, rowsDeleted);
    }
}