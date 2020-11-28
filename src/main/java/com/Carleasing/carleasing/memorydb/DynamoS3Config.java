package com.Carleasing.carleasing.memorydb;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoS3Config {

    public static  final String SERVICE_ENDPOINT = "https://dynamodb.us-east-2.amazonaws.com";
    public static final String REGION = "us-east-2";
    public static final  String ACCESS_KEY = "AKIA2MECEY27UUHICJ7D";
    public static final String SECRET_KEY = "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";


@Bean
    public DynamoDBMapper mapperz(){
        return new DynamoDBMapper(amazonDynamoDBConfig() );
    }

    private AmazonDynamoDB amazonDynamoDBConfig(){
        return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(SERVICE_ENDPOINT, REGION))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(ACCESS_KEY,SECRET_KEY))).build();
    }


    private AmazonS3 s3clientConfig() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(REGION))
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();

    }
}