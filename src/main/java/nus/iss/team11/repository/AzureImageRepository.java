package nus.iss.team11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import nus.iss.team11.model.AzureImage;

@Repository
public interface AzureImageRepository extends JpaRepository<AzureImage, Integer>{
	AzureImage findByFileName(String fileName);
	
	@Modifying
    @Transactional
	@Query("delete from AzureImage a where a.catSighting.id=:catSightingId")
	void deleteAllByCatSightingId(@Param("catSightingId") int catSightingId);
	}
