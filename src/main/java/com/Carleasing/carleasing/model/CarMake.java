package com.Carleasing.carleasing.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@DynamoDBDocument
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "CarMake")
public class CarMake {
    @DynamoDBHashKey(attributeName = "makeId")
    @DynamoDBAutoGeneratedKey
    private String makeId;
    @DynamoDBAttribute
    private String make;
    @DynamoDBAttribute
    private String carImageUrl;

}
