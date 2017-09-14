package br.com.sicop.certificado.jdk;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Certificado {
	
	private static final String KEYTOOL_LIST_KEYSTORE = "keytool -list -keystore " ;
	private static final String STOREPASS_CHANGEIT = " -storepass changeit" ;
	private static final String KEYTOOL_IMPORT_V_TRUSTCACERTS_FILE  = "keytool -import -v -trustcacerts -file " ;
	private static final String KEYSTORE_CACERTS  = " -keystore cacerts " ;
	private static final String KEYPASS_CHANGEIT_STOREPASS_CHANGEIT  = " -keypass changeit -storepass changeit " ;
	private static final String ECHO_ON  = "echo on " ;
	private static final String CD  = "cd " ;
	
	public static void addCertificado(String caminhoCertificado, String caminhoCacerts, String diretorioArquivo) throws IOException {
		createBATAdicionarCertificados(caminhoCertificado, caminhoCacerts,diretorioArquivo);
	}
	
	public static Process listarCertificados(String caminhoCacerts) {
		try {
			createBATListarCertificados(caminhoCacerts);
			return Runtime.getRuntime().exec("listar.bat");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static void createBATListarCertificados(String diretorioCacerts){
		try {
			diretorioCacerts = diretorioCacerts.replace("\\cacerts", "");
			List<String> lines = Arrays.asList(ECHO_ON, CD + diretorioCacerts , KEYTOOL_LIST_KEYSTORE + " cacerts " + STOREPASS_CHANGEIT);
			Path file = Paths.get("listar.bat");
			Files.write(file, lines, Charset.forName("UTF-8"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static Path createBATAdicionarCertificados(String caminhoCertificado, String diretorioCacerts, String diretorioArquivo){
		try {
			diretorioCacerts = diretorioCacerts.replace("\\cacerts", "");
			List<String> lines = Arrays.asList(ECHO_ON, 
											   CD + diretorioCacerts, 
											   KEYTOOL_IMPORT_V_TRUSTCACERTS_FILE + "\"" + caminhoCertificado + "\"" + KEYSTORE_CACERTS +KEYPASS_CHANGEIT_STOREPASS_CHANGEIT,
											   "pause");
			Path file = Paths.get(diretorioArquivo + ".bat");
			Files.write(file, lines, Charset.forName("UTF-8"));
			return file;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
