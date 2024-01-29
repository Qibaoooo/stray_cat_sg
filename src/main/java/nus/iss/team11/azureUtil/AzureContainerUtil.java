package nus.iss.team11.azureUtil;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;

@Component
public class AzureContainerUtil {
	private BlobServiceClient blobClient;
	private BlobContainerClient blobContainerClient;

	@Value("${azure.container.name}")
	private String container;

	@Value("${azure.storage.account.name}")
	private String storage;

	public AzureContainerUtil() {
		// Retrieve the connection string for use with the application.
		String connectStr = System.getenv("AZURE_STORAGE_CONNECTION_STRING");

		// Create a BlobServiceClient object using a connection string
		blobClient = new BlobServiceClientBuilder().connectionString(connectStr).buildClient();
	}

	public List<String> listAllImages() {
		blobContainerClient = blobClient.getBlobContainerClient(container);

		List<String> names = new ArrayList<String>();

		System.out.println("\nListing blobs...");
		for (BlobItem blobItem : blobContainerClient.listBlobs()) {
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
}
