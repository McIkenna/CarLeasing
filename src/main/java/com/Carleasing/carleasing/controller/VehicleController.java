package com.Carleasing.carleasing.controller;

import com.Carleasing.carleasing.model.Vehicle;
import com.Carleasing.carleasing.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@SpringBootApplication
@RequestMapping("/api/vehicle")
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @PostMapping(" ")
    public Vehicle saveVehicle(@RequestParam(value = "file") MultipartFile file, Vehicle vehicle){
        return vehicleService.save(file, vehicle);
    }

    @GetMapping("/{makeId}")
    public Iterable<Vehicle> findVehicle(@PathVariable String makeId){
        return vehicleService.findVehicle(makeId);
    }

    @GetMapping("/{makeId}/{vehicleId}")
    public Vehicle findVehicleByMakeId(@PathVariable String makeId, @PathVariable String vehicleId){
        return vehicleService.findVehicleByMakeId(makeId, vehicleId);
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
