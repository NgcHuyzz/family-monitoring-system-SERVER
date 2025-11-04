package com.family.server.controller;

import java.sql.Timestamp;
import java.util.UUID;
import java.util.regex.Pattern;

import com.family.server.model.User;
import com.family.server.repository.UserDAO;

public class LoginController {
	public LoginController()
	{
		
	}
	
	// return 0 for true, 1 for username or password empty,
	// 2 for username don't comply with regulations
	// 3 for password don't comply with regulations, 4 username or password don't true
	public int checkLogin(String username, String password)
	{
		if(username.isEmpty() || password.isEmpty())
			return 1;
		
		if(!checkEmail(username))
		{
			return 2;
		}
		
		if(!checkPassword(password))
		{
			return 3;
		}
		
		if(!checkUserPassInDatabase(username, password))
		{
			return 4;
		}
	
		return 0;
	}
	
	// return 0 for true, 1 for username or password empty,
	// 2 for username don't comply with regulations, 3 for username is exist
	// 4 for password don't comply with regulations, 5 for confirmPassword not equal password
	// 6 username or password don't true
	public int checkRegister(String username, String password, String confirmPassword)
	{
		if(username.isEmpty() || password.isEmpty())
			return 1;
		
		if(!checkEmail(username))
		{
			return 2;
		}
		
		if(!checkPasswordConfirm(password, confirmPassword)) 
		{
			return 3;
		}
		
		if(!checkPassword(password))
		{
			return 4;
		}
		
		if(!checkUsername(username))
		{
			return 5;
		}
		
		if(!checkUserPassInDatabase(username, password))
		{
			return 6;
		}
		
		return 0;
	}
	
	public void addUser(String username, String password)
	{
		User model = new User();
		model.setId(UUID.randomUUID());
		model.setEmail(username);
		model.setPassword(password);
		model.setCreateAt(new Timestamp(System.currentTimeMillis()));
		UserDAO udao = new UserDAO();
		udao.addUser(model);
		udao.close();
	}
	
	public String getUserID(String username)
	{
		UserDAO udao = new UserDAO();
		String id = udao.getIDByUsername(username);
		udao.close();
		return id;
	}
	
	public void changePassword(String id, String password)
	{
		UserDAO udao = new UserDAO();
		User model = udao.getByID(id);
		model.setPassword(password);
		udao.updateUser(model);
		udao.close();
	}
	
	private boolean checkEmail(String email)
	{
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@"
				+ "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

		// Compile the regex
		Pattern p = Pattern.compile(emailRegex);
		
		// Check if email matches the pattern
		return p.matcher(email).matches();
	}
	
	private boolean checkUsername(String username)
	{
		UserDAO udao = new UserDAO();
		boolean check = udao.checkExistUsername(username);
		udao.close();
		return check;
	}
	
	private boolean checkPassword(String password)
	{
		String passwordRegex =
			    "^(?=.*[a-z])"           
			  + "(?=.*[A-Z])"            
			  + "(?=.*\\d)"               
			  + "(?=.*[@#$%^*&+=()_!\\-])" 
			  + "(?=\\S+$)"               
			  + ".{8,30}$";   
		
		Pattern p = Pattern.compile(passwordRegex);
		
		return p.matcher(password).matches();
	}
	
	private boolean checkUserPassInDatabase(String username, String password)
	{
		UserDAO udao = new UserDAO();
		boolean check = udao.checkUserPassword(username,password);
		udao.close();
		return check;
	}
	
	private boolean checkPasswordConfirm(String password1, String password2)
	{
		return password1.equals(password2);
	}
}
