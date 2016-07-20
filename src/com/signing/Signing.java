package com.signing;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.connection.Connection;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.core.Context;




@Path("/signing")
public class Signing {
	
    Connection mymongo ;
    public Signing() throws UnknownHostException{   mymongo = new Connection();    }
       	@POST
    @Path("/ValidateEmail")
    @Produces(MediaType.TEXT_HTML)
    public String ValidateEmail(@FormParam("email") String id) throws UnknownHostException 
    {
   validateSignup validEmail=new validateSignup();
		validEmail.setEmail(id);
		
              Integer isValid=validEmail.validateEmail();
		if(isValid.equals(0)){
		//mymongo.closeConnection();
                    return "Invalid Email Address.";
			
		}
		else if(isValid.equals(1)){
			//mymongo.closeConnection();
			return "This email is already registerd.";
			
		}
		else{
			//mymongo.closeConnection();
                        return "";
		}
    }
       @POST
    @Path("/ValidatePassword")
    @Produces(MediaType.TEXT_HTML)
    public String ValidatePassword(@FormParam("password") String pass) 
    {
    validateSignup validPassword =new validateSignup();
    validPassword.setPassword(pass);
		boolean isValid=validPassword.validatePassword();
		if(!isValid){
			return "Password is too short.";
		
		}
		else{
			
			return "";
		}
    }
      @POST
    @Path("/ValidateFullName")
    @Produces(MediaType.TEXT_HTML)
    public String ValidateFullName(@FormParam("fullname") String fullname) 
    {
    validateSignup validFullname =new validateSignup();
     validFullname.setFullname(fullname);
	    boolean isValid=validFullname.validateFullname();
	    if(!isValid){	    	
	    	return "Invalid Input. Please enter a valid name.";
	    	
	    }
	    return "";

    }
     @POST
      @Path("/signup")
      @Produces(MediaType.TEXT_HTML)
    public String signup(@FormParam("fullname") String name,@FormParam("email") String id,@FormParam("password") String pass) throws UnknownHostException
      {
       
             validateSignup validateForm=new validateSignup(name,id,pass);
		Integer isValidEmail=validateForm.validateEmail();
		if(name.isEmpty() || id.isEmpty() || pass.isEmpty()){
			return "Please fill out all the required fields.";
			
		}
		else if(!validateForm.validateFullname()){
			return "Invalid Name.";
		}
		else if(isValidEmail.equals(0)){
			return "Invalid Email Address.";
		}
		else if(isValidEmail.equals(1)){
			return "This email is already registerd.";
		}
		else if(!validateForm.validatePassword()){
			return "Password is too short.";
		}
		else{
       
         DBCollection usercollection = mymongo.getMembersCol();
        
         String newUserCol=mymongo.getNameOfNewUserCollection();
        BasicDBObject doc = new BasicDBObject();
           doc.put("_id",id);
            doc.put("name",name);
           doc.put("password",pass);
           doc.put("collection",newUserCol);
           usercollection.insert(doc);
         
         DBCollection collection =mymongo.getCollection(newUserCol);
           BasicDBObject document = new BasicDBObject();
           document.put("_id","Home");
           document.put("folder","1");
           document.put("name","Home");
           document.put("parent","SELF");
           document.put("type","1");
            collection.insert(document);
            mymongo.closeConnection();
                }
		return "1";
      
      
      
      }
     @POST
     @Path("/signin")
     @Produces(MediaType.TEXT_HTML)
     public String signin(@Context HttpServletRequest request,@FormParam("email") String id,@FormParam("password") String pass)         {
              DBCollection collection = mymongo.getMembersCol();
              BasicDBObject query = new BasicDBObject();
              List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
              obj.add(new BasicDBObject("_id",id));
              obj.add(new BasicDBObject("password",pass));
              query.put("$and", obj);
              DBCursor cursor = collection.find(query);
            // PrintWriter out =response.getWriter();
              if (cursor.hasNext()) {
                  DBObject temp = cursor.next();
                  
                 HttpSession session=request.getSession();
			session.setAttribute("id",temp.get("_id").toString());
			session.setAttribute("email",temp.get("_id").toString());
			session.setAttribute("fullname",temp.get("name").toString());
			 mymongo.closeConnection();
                        return "true";
                
              }
              else  { mymongo.closeConnection();return ("Invalid Email Or Password.");                   
              }
        
              
      }
 	
 	
}
