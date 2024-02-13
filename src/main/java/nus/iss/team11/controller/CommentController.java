package nus.iss.team11.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import nus.iss.team11.Payload.NewComment;
import nus.iss.team11.controller.service.CatService;
import nus.iss.team11.controller.service.CommentService;
import nus.iss.team11.controller.service.SCSUserService;
import nus.iss.team11.model.Cat;
import nus.iss.team11.model.Comment;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class CommentController {
	@Autowired
	CatService catService;
	@Autowired
	SCSUserService userService;
	@Autowired
	CommentService commentService;

	@GetMapping(value = "/api/comments/{id}")
	public ResponseEntity<String> getComments(@PathVariable Integer id) {
		Cat cat = catService.getCatById(id);
		List<Comment> comments = cat.getComments();
		JSONArray ncomments = new JSONArray();
		comments.stream().forEach(comment -> {
			ncomments.put(comment.toJSON());
		});
		return new ResponseEntity<>(ncomments.toString(), HttpStatus.OK);
	}

	@PostMapping(value="/api/comments")
	public ResponseEntity<String> createComment(@RequestBody NewComment new_comment){
		String username=new_comment.getUsername();
		String content=new_comment.getContent();
		List<String>labels=new_comment.getLabels();
		Comment newcomment=new Comment();
		newcomment.setContent(content);
		newcomment.setTime(new Date());
		newcomment.setNewlabels(labels);
		newcomment.setScsUser(userService.findUserByUsername(username));
		newcomment.setCat(catService.getCatById(new_comment.getCat_id()));
		newcomment=commentService.saveComment(newcomment);
		JSONObject json = newcomment.toJSON();
		return new ResponseEntity<>(json.toString(), HttpStatus.OK);
	}
	
	@GetMapping(value="/api/getallcomments")
	public ResponseEntity<String> getAllComments(){
		JSONArray comments = new JSONArray();

		commentService.getAll().stream().forEach(sighting -> {
			JSONObject sightingJSON = sighting.toJSON();
			comments.put(sightingJSON);
		});
		
		
		return new ResponseEntity<>(comments.toString(), HttpStatus.OK);
	}
	
}
