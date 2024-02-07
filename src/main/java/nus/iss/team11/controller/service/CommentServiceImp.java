package nus.iss.team11.controller.service;

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
}
