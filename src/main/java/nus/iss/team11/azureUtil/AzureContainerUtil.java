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
	
	@Autowired
	CSVUtil csvUtil;

	public AzureContainerUtil() {
		// Retrieve the connection string for use with the application.
		String connectStr = System.getenv("AZURE_STORAGE_CONNECTION_STRING");

		// Create a BlobServiceClient object using a connection string
		blobServiceClient = new BlobServiceClientBuilder().connectionString(connectStr).buildClient();
	}

	public List<String> listAllImages() {
		imageContainerClient = blobServiceClient.getBlobContainerClient(container);

		List<String> names = new ArrayList<String>();

		System.out.println("\nListing blobs...");
		for (BlobItem blobItem : imageContainerClient.listBlobs()) {
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
		csvContainerClient = blobServiceClient.getBlobContainerClient(csvContainer);
				
		BlobClient blobClient = csvContainerClient.getBlobClient(blobName);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		blobClient.downloadStream(outputStream);
		
		return csvUtil.readCSVIntoHashMap(new ByteArrayInputStream(outputStream.toByteArray()));
	}
}
