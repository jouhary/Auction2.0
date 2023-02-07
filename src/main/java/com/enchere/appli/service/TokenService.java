package com.enchere.appli.service;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.enchere.appli.dao.TokenDAO;
import com.enchere.appli.exception.SessionLostException;
import com.enchere.appli.model.Token;


public class TokenService {
	
	public static Token getToken(String token) throws Exception {
		return TokenDAO.getToken(token);
	}
	
	public static void bearerToken(String hash) throws Exception {
		Token token = null;
		
		boolean ans = false;
		try {
			token = getToken(hash);
			if(token!=null) {
				if(token.getDateExpiration().after(Timestamp.valueOf(LocalDateTime.now()))){
					ans = true;
				}
				else {
					throw new SessionLostException();
				}
			}
			else {
				//update token
				throw new SessionLostException();
			}
		} catch (SessionLostException e) {
			throw e;
			// TODO: handle exception
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		} 
		
	}
	public static int getByUser(String token, Connection c)  throws Exception{
		return TokenDAO.getidUserByToken(token,c);

	}
}
