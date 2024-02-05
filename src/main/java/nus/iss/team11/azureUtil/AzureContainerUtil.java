package nus.iss.team11.azureUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.opencsv.exceptions.CsvException;

import nus.iss.team11.dataUtil.CSVUtil;

@Component
public class AzureContainerUtil {
	private BlobServiceClient blobServiceClient;
	private BlobContainerClient imageContainerClient;
	private BlobContainerClient csvContainerClient;
	

	@Value("${azure.container.name}")
	private String container;

	@Value("${azure.container.csv.name}")
	private String csvContainer;

	@Value("${azure.storage.account.name}")
	private String storage;

	private BlobContainerClient getCsvContainerClient() {
		if (csvContainerClient == null) {
			csvContainerClient = blobServiceClient.getBlobContainerClient(csvContainer);
		}
		return csvContainerClient;
	}

	private BlobContainerClient getImageContainerClient() {
		if (imageContainerClient == null) {
			imageContainerClient = blobServiceClient.getBlobContainerClient(container);
		}
		return imageContainerClient;
	}

	
	@Autowired
	CSVUtil csvUtil;

	public AzureContainerUtil() {
		// Retrieve the connection string for use with the application.
		String connectStr = System.getenv("AZURE_STORAGE_CONNECTION_STRING");

		// Create a BlobServiceClient object using a connection string
		blobServiceClient = new BlobServiceClientBuilder().connectionString(connectStr).buildClient();
	}

	public List<String> listAllImages() {

		List<String> names = new ArrayList<String>();

		System.out.println("\nListing blobs...");
		for (BlobItem blobItem : getImageContainerClient().listBlobs()) {
			names.add(blobItem.getName());
		}

		return names;
	}

	public String deriveImageURL(String fileName) throws Exception {
		if (fileName == null) {
			throw new Exception("fileName cannot be null");
		}
		return String.format("https://%1$s.blob.core.windows.net/%2$s/%3$s", storage, container, fileName);
	}
	
	public HashMap<String, String> readCSVIntoHashMap(String blobName) throws IOException, CsvException {
				
		BlobClient blobClient = getCsvContainerClient().getBlobClient(blobName);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		blobClient.downloadStream(outputStream);
		
		return csvUtil.readCSVIntoHashMap(new ByteArrayInputStream(outputStream.toByteArray()));
	}
	
	public String uploadImageToContainer(byte[] imageFile, String fileName) {
	    BlobClient blobClient = getImageContainerClient().getBlobClient(fileName);
	    
	    try (ByteArrayInputStream dataStream = new ByteArrayInputStream(imageFile)) {
	    	blobClient.upload(dataStream, imageFile.length);
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }

		return blobClient.getBlobUrl();
	}
}
