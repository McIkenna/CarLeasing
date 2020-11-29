package com.Carleasing.carleasing.repository;

import com.Carleasing.carleasing.model.Vehicle;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public interface VehicleRepository{

    Vehicle save(MultipartFile multipartFile, Vehicle vehicle);
    Vehicle findVehicle(String vehicleId);
    String deleteVehicle(String vehicleId);
    String updateVehicle(Vehicle vehicle);

    Iterable<Vehicle> findAll();
}
