package nus.iss.team11.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nus.iss.team11.model.SCSUser;

@Repository
public interface SCSUserRepository extends JpaRepository<SCSUser, Integer>{

	Optional<SCSUser> findByUsername(String username);

}
