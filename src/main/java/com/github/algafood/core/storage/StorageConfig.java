package com.github.algafood.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.github.algafood.core.storage.StorageProperties.TipoStorage;
import com.github.algafood.domain.service.FotoStorageService;
import com.github.algafood.infrastructure.service.storage.DiscoLocalFotoStorageService;
import com.github.algafood.infrastructure.service.storage.S3FotoStorageService;

@Configuration
public class StorageConfig {

	@Autowired
	private StorageProperties storageProperties;

	@Bean
	@ConditionalOnProperty(name = "algafood.storage.tipo", havingValue = "s3") //só injeta este bean se o tipo for = s3
	public AmazonS3 amazonS3() {

		var credentials = new BasicAWSCredentials(storageProperties.getS3().getIdChaveAcesso(),
				storageProperties.getS3().getChaveAcessoSecreta());

		return AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(storageProperties.getS3().getRegiao())
				.build();
	}
	
	@Bean
	public FotoStorageService fotoStorageService() {
		if(storageProperties.getTipo().equals(TipoStorage.LOCAL)) {
			return new DiscoLocalFotoStorageService();
		} else {
			return new S3FotoStorageService();
		}
	} 
	
}
