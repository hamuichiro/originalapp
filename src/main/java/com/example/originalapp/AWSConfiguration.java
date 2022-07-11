package com.example.originalapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;


@Configuration
public class AWSConfiguration {
	
  @Value("${AWS_ACCESS_KEY_ID}")
  private String awsAccessKeyId;

  @Value("${AWS_SECRET_ACCESS_KEY}")
  private String awsSecretAccessKey;

  @Value("${AWS_DEFAULT_REGION}")
  private String awsDefaultRegion;
  
  @Bean
  public AmazonS3 s3Client() {
		// 認証情報を用意
		BasicAWSCredentials credentials = new BasicAWSCredentials(awsAccessKeyId, awsSecretAccessKey);
		 
		// クライアントを生成
		AmazonS3 client = AmazonS3ClientBuilder
			.standard()
			// 認証情報を設定
			.withCredentials(new AWSStaticCredentialsProvider(credentials))
			// リージョンを AP_NORTHEAST_1(東京) に設定
			.withRegion(awsDefaultRegion)
			.build();
		
		return client;
  }
}