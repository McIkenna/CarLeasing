package com.Carleasing.carleasing.controller;

import com.Carleasing.carleasing.model.Vehicle;
import com.Carleasing.carleasing.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@RestController
@SpringBootApplication
@RequestMapping("/api/vehicle")
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @PostMapping(" ")
    public Vehicle saveVehicle(@RequestBody Vehicle vehicle){
        return vehicleService.save(vehicle);
    }

    @GetMapping("/{vehicleId}")
    public Vehicle findVehicle(@PathVariable String vehicleId){
        return vehicleService.findVehicle(vehicleId);
    }

    @DeleteMapping("/{vehicleId}")
    public String deleteVehicle(@PathVariable String vehicleId){
        return vehicleService.deleteVehicle(vehicleId);
    }

    @PutMapping("/updateVehicle")
    public String updateVehicle(@RequestBody Vehicle vehicle){
        return vehicleService.updateVehicle(vehicle);
    }

    @GetMapping("/all")
    public Iterable<Vehicle> getAllVehicle(){
        return vehicleService.findAll();
    }
}
