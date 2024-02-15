package nus.iss.team11.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import nus.iss.team11.controller.payload.JwtResponse;
import nus.iss.team11.controller.payload.LoginRequest;
import nus.iss.team11.controller.payload.RegisterRequest;
import nus.iss.team11.controller.service.SCSUserService;
import nus.iss.team11.security.JwtUtils;
import nus.iss.team11.security.UserDetailsImpl;
import nus.iss.team11.model.AzureImage;
import nus.iss.team11.model.CatSighting;
import nus.iss.team11.model.SCSUser;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class CommonController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder encoder;
	
	@Value("${SCS.app.jwtExpirationMs}")
	private int jwtExpirationMs;
	
	@Autowired
	SCSUserService scsUserService;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping(value = "/api/auth/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		Date expiration = new Date((new Date()).getTime() + jwtExpirationMs);
		String jwt = jwtUtils.generateJwtToken(authentication, expiration);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		String role = userDetails.getAuthorities().iterator().next().toString();
		
		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), role, expiration.getTime()));
	}
	
	@PostMapping(value = "/api/auth/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest loginRequest) {
		Optional<SCSUser> user = scsUserService.getUserByUsername(loginRequest.getUsername());
		if (!user.isEmpty()) {
			return new ResponseEntity<>("user already exists", HttpStatus.BAD_REQUEST);
		}
		
		SCSUser newUser = new SCSUser();
		newUser.setUsername(loginRequest.getUsername());
		newUser.setPassword(encoder.encode(loginRequest.getPassword()));
		
		newUser = scsUserService.saveSCSUser(newUser);
		
		return new ResponseEntity<>("new user created: "+newUser.getId(), HttpStatus.OK);
	}

	@PostMapping(value = "/api/logout")
	public ResponseEntity<String> apiLogout(HttpSession session) {
		session.invalidate();
		return new ResponseEntity<>("Logged out", HttpStatus.OK);
	}

	@GetMapping(value = "/android/test")
	public ResponseEntity<CatSighting> testComm() {
		CatSighting cs11 = new CatSighting();
		cs11.setSightingName("android test 111");
		cs11.setTime(LocalDate.now());
		cs11.setLocationLat(0f);
		cs11.setLocationLong(0f);

		List<AzureImage> imgs = new ArrayList<>();
		AzureImage img1 = new AzureImage();
		img1.setFileName("123");
		AzureImage img2 = new AzureImage();
		img2.setFileName("456");
		imgs.add(img1);
		imgs.add(img2);
		cs11.setImages(imgs);
		return new ResponseEntity<CatSighting>(cs11, HttpStatus.OK);
	}

	@GetMapping(value = "/android/test2")
	public ResponseEntity<List<CatSighting>> testComm2() {
		CatSighting cs11 = new CatSighting();
		cs11.setSightingName("android test 111");
		cs11.setTime(LocalDate.now());
		cs11.setLocationLat(0f);
		cs11.setLocationLong(0f);

		List<AzureImage> imgs = new ArrayList<>();
		AzureImage img1 = new AzureImage();
		img1.setFileName("123");
		AzureImage img2 = new AzureImage();
		img2.setFileName("456");
		imgs.add(img1);
		imgs.add(img2);
		cs11.setImages(imgs);

		CatSighting cs12 = new CatSighting();
		cs12.setSightingName("android test 111");
		cs12.setTime(LocalDate.now());
		cs12.setLocationLat(0f);
		cs12.setLocationLong(0f);

		List<AzureImage> imgs2 = new ArrayList<>();
		AzureImage img12 = new AzureImage();
		img12.setFileName("123");
		AzureImage img22 = new AzureImage();
		img22.setFileName("456");
		imgs2.add(img1);
		imgs2.add(img2);
		cs12.setImages(imgs2);
		List<CatSighting> csList = new ArrayList<>();
		csList.add(cs11);
		csList.add(cs12);
		return new ResponseEntity<List<CatSighting>>(csList, HttpStatus.OK);
	}

}
