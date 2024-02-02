package nus.iss.team11.controller.service;

import nus.iss.team11.model.AzureImage;

public interface AzureImageService {
	AzureImage findImageByFileName(String fileName);
}
