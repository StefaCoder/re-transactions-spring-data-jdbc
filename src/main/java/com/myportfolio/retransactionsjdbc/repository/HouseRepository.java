package com.myportfolio.retransactionsjdbc.repository;

import com.myportfolio.retransactionsjdbc.model.House;

public interface HouseRepository {

    int saveHouse(House house);

    int updateHouse(House house);

    House findHouseById(int houseID);

    House findHouseByAddress(String houseAddress);

    int deleteHouseById(int houseID);

    int deleteAllHouses();
}
