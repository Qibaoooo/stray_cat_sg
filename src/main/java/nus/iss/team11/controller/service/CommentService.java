package nus.iss.team11.controller.service;

import java.util.List;

import nus.iss.team11.model.Comment;

public interface CommentService {

	Comment saveComment(Comment comment);
	List<Comment> getAll();
}
