package nus.iss.team11.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import nus.iss.team11.model.Cat;
import nus.iss.team11.model.Comment;
import nus.iss.team11.model.SCSUser;


@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer>{

	List<Comment> findByScsUser(SCSUser user);
	List<Comment> findByCat(Cat cat);
	
	@Query("SELECT COUNT(c) FROM Comment c WHERE c.cat.id = :catId AND c.isFlagged = true")
	int getCountofFlag(@Param("catId") Integer catId);
}
