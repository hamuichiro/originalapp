package com.example.originalapp.service;

import java.io.File;
import java.io.FileInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

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
		metaData.setContentLength(file.length());
		
		// リクエストを生成
		PutObjectRequest request = new PutObjectRequest(
				// アップロード先バケット名
				awsBucket,
				// アップロード後のキー名
				"upload/example.txt",
				// InputStream
				input,
				// メタ情報
				metaData
		);
		
		// アップロード
		s3Client.putObject(request);
		
		input.close();
	}
}

	
