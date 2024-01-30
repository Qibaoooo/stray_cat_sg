package nus.iss.team11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nus.iss.team11.model.Cat;

@Repository
public interface CatRepository extends JpaRepository<Cat, Integer>{

}
