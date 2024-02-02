package nus.iss.team11.controller;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import nus.iss.team11.controller.service.CatService;
import nus.iss.team11.model.Cat;
import nus.iss.team11.model.Comment;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class CommentController {
	@Autowired
	CatService catService;

	@GetMapping(value = "/api/comments/{id}")
	public ResponseEntity<String> getComments(@PathVariable Integer id) {
		Cat cat = catService.findCatById(id);
		List<Comment> comments = cat.getComments();
		JSONArray ncomments = new JSONArray();
		comments.stream().forEach(comment -> {
			ncomments.put(comment.toJSON());
			});
		return new ResponseEntity<>(ncomments.toString(), HttpStatus.OK);
	}
}
