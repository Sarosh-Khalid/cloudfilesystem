package com.signing;



import com.connection.Connection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class validateSignup {
	 
	String fullname;
	String email;
	String password;
	String pattern= "^[a-zA-Z0-9 ]*$";
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	validateSignup() {}	
	public validateSignup(String vfullname,String vemail, String vpassword){
	
               fullname=vfullname;
		email=vemail;
		password=vpassword;
		}
	public void setFullname(String vfullname){
		fullname=vfullname;
	}
	public void setEmail(String vemail){
		email=vemail;
	}
	public void setPassword(String vpassword){
		password=vpassword;
	}

	public boolean validateFullname(){
		if(fullname.matches(pattern)){
			return true;
			}
		else{
			return false;
		}
	}
	
	public int validateEmail() throws UnknownHostException{
		
           
                Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
                //return matcher.find();
                if(!matcher.find()){
                    return 0;
                }
                               
               Connection mymongo = new Connection();
                DBCollection collection=mymongo.getMembersCol();
                BasicDBObject  query =new BasicDBObject();
                query.put("_id",email);
                DBCursor d=collection.find(query);
                DBObject l;
                if(d.hasNext()){
                    l=d.next();
                    Object mongoEmail=new String();
                    mongoEmail=l.get("_id");
                    if(mongoEmail.equals(email)){
                         mymongo.closeConnection();
                        return 1;
                    }
                    else{
                         mymongo.closeConnection();
                        return 2;
                    }
                }
                else{
                     mymongo.closeConnection();
                    return 2;
                }
            } 

	public boolean validatePassword(){
		if(password.length()<8){
		return false;
		}
		else{
			return true;
		}
		
	}

   
	
}
