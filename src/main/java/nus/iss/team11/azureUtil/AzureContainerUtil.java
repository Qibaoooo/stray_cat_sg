package nus.iss.team11.azureUtil;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;

@Component
public class AzureContainerUtil {
	private BlobServiceClient blobClient;
//	private BlobContainerClient blobContainerClient;
	private String CONTAINER_NAME = "images";
	
	public AzureContainerUtil() {
		// Retrieve the connection string for use with the application. 
		String connectStr = System.getenv("AZURE_STORAGE_CONNECTION_STRING");

		// Create a BlobServiceClient object using a connection string
		blobClient = new BlobServiceClientBuilder()
		    .connectionString(connectStr)
		    .buildClient();
		
	}
	
	public void listAllImages() {

		BlobContainerClient blobContainerClient = blobClient.getBlobContainerClient(CONTAINER_NAME);
		// List the blob(s) in the container.
		System.out.println("\nListing blobs...");
		for (BlobItem blobItem : blobContainerClient.listBlobs()) {
		    System.out.println("\t" + blobItem.getName());
		}
	}
}
