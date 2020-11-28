package com.Carleasing.carleasing.service;


import com.Carleasing.carleasing.exception.VehicleException;
import com.Carleasing.carleasing.model.Vehicle;
import com.Carleasing.carleasing.repository.VehicleRepository;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class VehicleService implements VehicleRepository {

    @Autowired
    private DynamoDBMapper mapper;

    @Override
    public Vehicle save(Vehicle vehicle) {
        try{
            mapper.save(vehicle);
            return  vehicle;
        }
        catch(Exception e){
            throw new VehicleException("This user '" + vehicle.getVehicleId() + "' already exist");
        }
    }

    @Override
    public Vehicle findVehicle(String vehicleId) {
     return mapper.load(Vehicle.class, vehicleId);

    }

    @Override
    public String deleteVehicle(String vehicleId) {

            mapper.delete(vehicleId);
            return "Vehicle deleted !!";


    }

    @Override
    public String updateVehicle(Vehicle vehicle) {
            mapper.save(vehicle, buildExpression(vehicle));
            return "record Updated";
    }



    private DynamoDBSaveExpression buildExpression(Vehicle vehicle){

        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedMap=new HashMap<>();
        expectedMap.put("vehicleId", new ExpectedAttributeValue((new AttributeValue().withS(vehicle.getVehicleId()))));
        dynamoDBSaveExpression.setExpected(expectedMap);
        return dynamoDBSaveExpression;
    }


    public Iterable<Vehicle> findAll() {
        return mapper.scan(Vehicle.class, new DynamoDBScanExpression());
    }
}
