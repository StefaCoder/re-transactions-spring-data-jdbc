package com.myportfolio.retransactionsjdbc.controller;

import com.myportfolio.retransactionsjdbc.model.House;
import com.myportfolio.retransactionsjdbc.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HouseController {

    @Autowired
    HouseRepository houseRepository;

    @PostMapping("/house")
    public ResponseEntity<String> createHouse(@RequestBody House house){
        try {
            houseRepository.saveHouse(new House(house.getHouse_address(), house.getHouse_price(), house.getSeller_id()));
            return new ResponseEntity<>("House successfully created.", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("An error has occurred while saving the House. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/house/update/{id}")
    public ResponseEntity<String> updateHouseById(@PathVariable("id") int houseID, @RequestBody House house){
        House houseObj = houseRepository.findHouseById(houseID);

        if (houseObj != null) {
            houseObj.setHouse_address(house.getHouse_address());
            houseObj.setHouse_price(house.getHouse_price());
            houseObj.setSeller_id(house.getSeller_id());
            try {
                houseRepository.updateHouse(houseObj);
                return new ResponseEntity<>("House successfully updated.", HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<>("Something went wrong. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else {
            return new ResponseEntity<>("House with id " + houseID + " not found.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/house/{id}")
    public ResponseEntity<House> getHouseById(@PathVariable("id") int houseID){
        try {
            House houseObj = houseRepository.findHouseById(houseID);

            if (houseObj == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>(houseObj, HttpStatus.OK);
            }
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/house/address/{address}")
    public ResponseEntity<House> getHouseByAddress(@PathVariable("address") String address){
        House houseObj = houseRepository.findHouseByAddress(address);

        if (houseObj == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(houseObj, HttpStatus.OK);
        }
    }

    @DeleteMapping("/house/{id}")
    public ResponseEntity<String> removeHouseById(@PathVariable("id") int houseID){
        try {
            int response = houseRepository.deleteHouseById(houseID);
            if (response == 0){
                return new ResponseEntity<>("No House with id " + houseID + " found.", HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>("House with id " + houseID + " successfully removed.", HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>("Something went wrong. Cannot delete House. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/house")
    public ResponseEntity<String> removeAllHouses(){
        try {
            int response = houseRepository.deleteAllHouses();
            if (response == 0) {
                return new ResponseEntity<>("Cannot delete. No House has been found.", HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>(response + " Houses deleted.", HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>("Something went wrong. Cannot delete Houses. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
