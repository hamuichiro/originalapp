package com.example.originalapp.service;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

@Service
public class S3Wrapper {
	
    @Autowired
    private AmazonS3 s3Client;
    
    @Value("${AWS_BUCKET}")
    private String awsBucket;
    
  public void upLoad(String filePath) throws Exception {	
	
	// アップロードするファイル
	File file = new File(filePath);
	 
	// === InputStreamからアップロードする場合 ===
	FileInputStream input = new FileInputStream(file);
		// メタ情報を生成
		ObjectMetadata metaData = new ObjectMetadata();
		//metaData.setContentLength(file.length());
		
		// リクエストを生成
		PutObjectRequest request = new PutObjectRequest(
				// アップロード先バケット名
				awsBucket,
				// アップロード後のキー名
				 file.getName(),
				// InputStream
				input,
				// メタ情報
				metaData
		);
		
		// アップロード
		s3Client.putObject(request);
	}

  public  S3ObjectInputStream download(String filePath) throws Exception{
 


      GetObjectRequest getRequest = new GetObjectRequest(awsBucket, filePath);

      S3Object object = s3Client.getObject(getRequest);
      S3ObjectInputStream inputStream = object.getObjectContent();

      //FileOutputStream fos = new FileOutputStream(new File("[出力先パス]"));
      //IOUtils.copy(object.getObjectContent(), fos);

      //fos.close();
      
      return inputStream;
  }
  
}