package nus.iss.team11;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class CommonController {
	
	@GetMapping(value = "/roll")
	public ResponseEntity<String> getRoll(Integer elements) {
		Random ran = new Random();
		Integer randInt = ran.nextInt(7);
		return new ResponseEntity<>(randInt.toString(), HttpStatus.OK);
	}

}
