package com.Carleasing.carleasing.service;


import com.Carleasing.carleasing.exception.VehicleException;
import com.Carleasing.carleasing.model.CommonUtils;
import com.Carleasing.carleasing.model.Vehicle;
import com.Carleasing.carleasing.repository.VehicleRepository;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class VehicleService implements VehicleRepository {


    private Logger logger = LoggerFactory.getLogger(CarMakeService.class);

    @Autowired
    private DynamoDBMapper mapper;

    @Autowired
    AmazonS3 s3Client;

    @Override
    public Vehicle save(MultipartFile multipartFile, Vehicle vehicle) {
        try{
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            String fileUrl = CommonUtils.S3SERVICE_ENDPOINT + "/" + CommonUtils.BUCKET_MODEL_NAME + "/" + fileName;

        vehicle.setCarImage(fileUrl);
            mapper.save(vehicle);
        s3Client.putObject(
                new PutObjectRequest(CommonUtils.BUCKET_MODEL_NAME, fileName , file));
        }/*catch (Exception e) {
			e.printStackTrace();
		}*/
        catch (
    AmazonServiceException ase) {
        logger.info("Caught an AmazonServiceException from GET requests, rejected reasons:");
        logger.info("Error Message:    " + ase.getMessage());
        logger.info("HTTP Status Code: " + ase.getStatusCode());
        logger.info("AWS Error Code:   " + ase.getErrorCode());
        logger.info("Error Type:       " + ase.getErrorType());
        logger.info("Request ID:       " + ase.getRequestId());

    } catch (
    AmazonClientException ace) {
        logger.info("Caught an AmazonClientException: ");
        logger.info("Error Message: " + ace.getMessage());
    } catch (IOException ioe) {
        logger.info("IOE Error Message: " + ioe.getMessage());
        }
        return  vehicle;
}

    @Override
    public Vehicle findVehicle(String vehicleId) {
     return mapper.load(Vehicle.class, vehicleId);

    }

    @Override
    public String deleteVehicle(String vehicleId) {
        try {
            Vehicle veh = mapper.load(Vehicle.class, vehicleId);
            mapper.delete(veh);
            String fileName = veh.getCarImage().substring(veh.getCarImage().lastIndexOf("/") + 1);
            s3Client.deleteObject(new DeleteObjectRequest(CommonUtils.BUCKET_MODEL_NAME + "/", fileName));
            return "Vehicle deleted !!";
        }catch(Exception ex){
            throw new VehicleException("This Vehicle Cannot be deleted");
        }
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

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
