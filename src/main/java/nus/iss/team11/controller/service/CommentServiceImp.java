package nus.iss.team11.controller.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nus.iss.team11.model.Comment;
import nus.iss.team11.repository.CommentRepository;

@Service
public class CommentServiceImp implements CommentService {
@Autowired
CommentRepository commentrepository;

	@Override
	public Comment saveComment(Comment comment) {
		return commentrepository.save(comment);
	}

	@Override
	public List<Comment> getAll(){
		return commentrepository.findAll();
	}
	
	@Override
	public boolean checkFlagged(int catId) {
		if (commentrepository.getCountofFlag(catId) >0) {
			return true;
		}
		return false;
	}

}
