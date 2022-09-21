package com.github.algafood.infrastructure.service.storage;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.github.algafood.core.storage.StorageProperties;
import com.github.algafood.domain.service.FotoStorageService;

public class S3FotoStorageService implements FotoStorageService {

	@Autowired
	private AmazonS3 amazonS3;

	@Autowired
	private StorageProperties storageProperties;

	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			// define o caminho da foto no storage s3 ex: catalogo/nomedafoto
			String caminhoArquivo = this.getCaminhoArquivo(novaFoto.getNomeArquivo());
	
			//define o content type da foto ex: image/jpeg
			ObjectMetadata objectMetaData = new ObjectMetadata();
			objectMetaData.setContentType(novaFoto.getContentType());
			objectMetaData.setContentLength(novaFoto.getTamanho());
			
			// instancia um request com permissão publica de leitura
			PutObjectRequest putObjectRequest = new PutObjectRequest(
					storageProperties.getS3().getBucket(),
					caminhoArquivo,
					novaFoto.getInputStream(),
					objectMetaData
			).withCannedAcl(CannedAccessControlList.PublicRead);
			
			amazonS3.putObject(putObjectRequest);
		} catch (Exception e) {
			throw new StorageException("Não foi possível enviar arquivo para Amazon S3", e);
		}
	}

	private String getCaminhoArquivo(String nomeArquivo) {
		return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
	}

	@Override
	public void remover(String nomeArquivo) {
		// define o caminho da foto no storage s3 ex: catalogo/nomedafoto
		String caminhoArquivo = this.getCaminhoArquivo(nomeArquivo);
		
		try {			
			DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(
					storageProperties.getS3().getBucket(), caminhoArquivo
			);
			
			amazonS3.deleteObject(deleteObjectRequest);
		} catch (Exception e) {
			throw new StorageException(
					String.format("Não foi possível excluir arquivo %s na Amazon S3.", caminhoArquivo), e);
		}
	}

	@Override
	public FotoRecuperada recuperar(String nomeArquivo) {
		// define o caminho da foto no storage s3 ex: catalogo/nomedafoto
		String caminhoArquivo = this.getCaminhoArquivo(nomeArquivo);
		
		URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), caminhoArquivo);
		
		FotoRecuperada fotoRecuperada = FotoRecuperada.builder()
				.url(url.toString())
				.build();

		return fotoRecuperada;
	}

}
