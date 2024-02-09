package nus.iss.team11.controller.service;

import java.util.List;

import nus.iss.team11.model.AzureImage;

public interface AzureImageService {
	AzureImage findImageByFileName(String fileName);
	AzureImage saveImage(AzureImage azureImage);
	List<AzureImage> findAll();
}
