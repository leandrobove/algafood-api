package com.github.algafood.infrastructure.service.storage;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.github.algafood.core.storage.StorageProperties;
import com.github.algafood.domain.service.FotoStorageService;

public class DiscoLocalFotoStorageService implements FotoStorageService {

	@Autowired
	private StorageProperties storageProperties;

	@Override
	public void armazenar(NovaFoto novaFoto) {

		Path arquivoPath = this.getArquivoPath(novaFoto.getNomeArquivo());

		try {
			FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
		} catch (Exception e) {
			throw new StorageException("Não foi possível armazenar o arquivo", e);
		}
	}

	private Path getArquivoPath(String nomeArquivo) {
		return storageProperties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo));
	}

	@Override
	public void remover(String nomeArquivo) {

		Path arquivoPath = this.getArquivoPath(nomeArquivo);

		try {
			Files.deleteIfExists(arquivoPath);
		} catch (Exception e) {
			throw new StorageException("Não foi possível excluir o arquivo", e);
		}
	}

	@Override
	public FotoRecuperada recuperar(String nomeArquivo) {

		Path arquivoPath = this.getArquivoPath(nomeArquivo);
		
		try {
			FotoRecuperada fotoRecuperada = FotoRecuperada.builder()
					.inputStream(Files.newInputStream(arquivoPath))
					.build();
		
			return fotoRecuperada;
		} catch (Exception e) {
			throw new StorageException("Não foi possível recuperar o arquivo", e);
		}
	}

}
