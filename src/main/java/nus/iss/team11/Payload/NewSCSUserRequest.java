package nus.iss.team11.Payload;

public class NewSCSUserRequest {
	private String username;
	private String password;
	
	public NewSCSUserRequest (String username, String password ) {
		super();
		this.setUsername(username);
		this.setPassword(password);
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	

}
