package com.connection;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import java.net.UnknownHostException;
import java.util.Set;

/**
 *
 * @author Faraz
 */
public  class Connection {
     String dbname="FileSystem";
    final String newUserCollection="usercollection";
    final String userCol="Users";
     MongoClient mongoClient=null;
     DB mongoDB;
  public Connection() 
  {
    
  }   
  private void establishConnection(){
  if(mongoClient==null)
    {
      MongoClientURI uri  = new MongoClientURI("mongodb://352634:cs-120@ds023418.mlab.com:23418/example");
      try {
		mongoClient  = new MongoClient(uri);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      dbname=uri.getDatabase();
      //  mongoClient = new MongoClient("localhost", 27017);
     mongoDB = mongoClient.getDB(dbname);
    
    }
  }
   public DBCollection getCollection(String col) {
        establishConnection();
       return mongoDB.getCollection(col);
    }
   public DB getDB() {
       establishConnection();
       return this.mongoDB;
    }
   public DBCollection getMembersCol()
   {  establishConnection();
       return mongoDB.getCollection(userCol);}

    public String getNameOfNewUserCollection() {
    establishConnection();
        Set <String> allColNames = mongoDB.getCollectionNames();
   String temp =newUserCollection;
   Object[] toCompare= allColNames.toArray();
   for(int i=0;i<toCompare.length;i++)
   {
       if(temp.equals(toCompare[i].toString()))
       {
  temp=newUserCollection+String.valueOf(i);
       }
   }
   return temp;
    }
    public void closeConnection()
    {
       //  establishConnection();
    mongoClient.close();
    }
}
