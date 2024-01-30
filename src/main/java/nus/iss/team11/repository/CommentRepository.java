package nus.iss.team11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nus.iss.team11.model.Comment;


@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer>{

	List<Comment> findByScsuser(SCSUser user);
	List<Comment> findByCat(Cat cat);
}
