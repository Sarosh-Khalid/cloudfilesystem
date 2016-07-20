/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fileoperations;

import com.connection.Connection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.ws.rs.core.Response;
//import org.apache.tomcat.jni.Time;

/**
 *
 * @author Faraz
 */
public class CopyClass {
   String userID,parentPath,name,newPath;
    final String pathMerger = "/";
    Connection mymongo;
     DBCollection collection;
     String userCollectionName;
    
     public CopyClass(String userID,String parentPath,String name,String newPath) throws UnknownHostException
    {
    this.userID=userID;this.parentPath=parentPath;this.name=name;this.newPath=newPath;
    mymongo=new Connection();
   DBCollection usercol = mymongo.getMembersCol();
         BasicDBObject query = new BasicDBObject();
         query.put("_id",userID);
	 DBCursor cursor = usercol.find(query);
         DBObject col=cursor.next();
         userCollectionName=col.get("collection").toString();
        collection = mymongo.getCollection(userCollectionName);
    }
 
 public Boolean forSingleFile()
 {
     try{
 if(name.contains("."))
 {
  
                BasicDBObject query = new BasicDBObject();
                query.put("_id",parentPath+pathMerger+name);
                DBCursor cursor = collection.find(query);
                                      
               if(cursor.hasNext())
               {
                    BasicDBObject checknewquery = new BasicDBObject();
                checknewquery.put("_id",newPath+pathMerger+name);
                DBCursor tempCursor = collection.find(checknewquery);
                   if(tempCursor.hasNext())
                   {
                   return false;
                   }
                  DBObject  copyFile=cursor.next();
                 GridFS fileDB = new GridFS(mymongo.getDB(),userCollectionName);
                 InputStream data=fileDB.findOne(query).getInputStream();
                 
                  BasicDBObject document = new BasicDBObject();
			document.append("_id",newPath+pathMerger+name);
			document.append("folder","0");
                        document.append("parent",newPath);
                        document.append("name",name);
                        document.append("type",copyFile.get("type").toString()); 
                        collection.insert(document);
			 GridFSInputFile inputFile = fileDB.createFile(data);
	                inputFile.setId(newPath+pathMerger+name);
	                inputFile.put("path",newPath);
                        inputFile.setFilename(name);
	                inputFile.save();
                return true;
              }
               else
               {return false;}
 }
 else
 {return false;}
     }  finally
            { 	mymongo.closeConnection();	}
 }
 
 public Boolean forFolder() throws UnknownHostException, IOException
 {
 FolderDownload folder = new FolderDownload(userID,parentPath,name);
 return folder.copyFolder(newPath);
 }
}
