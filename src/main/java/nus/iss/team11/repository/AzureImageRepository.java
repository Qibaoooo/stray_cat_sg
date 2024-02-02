package nus.iss.team11.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nus.iss.team11.model.AzureImage;

public interface AzureImageRepository extends JpaRepository<AzureImage, Integer>{
	AzureImage findByFileName(String fileName);
}
