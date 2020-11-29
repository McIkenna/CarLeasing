package com.Carleasing.carleasing.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@DynamoDBDocument
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "VehicleDetails")
public class Vehicle {

    @DynamoDBHashKey(attributeName = "vehicleId")
    @DynamoDBAutoGeneratedKey
    private String vehicleId;
    @DynamoDBAttribute
    private String model;
    @DynamoDBAttribute
    private String style;
    @DynamoDBAttribute
    private String year;
    @DynamoDBAttribute
    private String trimLevel;
    @DynamoDBAttribute
    private String color;
    @DynamoDBAttribute
    private int odometer;
    @DynamoDBAttribute
    private String regNo;
    @DynamoDBAttribute
    private double carValue;
    @DynamoDBAttribute
    private int period;
    @DynamoDBAttribute
    private String plan;
    @DynamoDBAttribute
    private double leasePrice;
    @DynamoDBAttribute
    private int quantity;
    @DynamoDBAttribute
    private String carImage;


}
