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
public class RenameFolder {
   String userID,parentPath,oldName,newName;
    final String pathMerger = "/";
    Connection mymongo;
     DBCollection collection;
     String userCollectionName;
     List<BasicDBObject> pathOfChildrenFolders = new ArrayList<BasicDBObject>();
       List<BasicDBObject> pathOfChildrenEmptyFolders = new ArrayList<BasicDBObject>();
       HashMap <String,String> replacementOfPath = new HashMap ();
  
     public RenameFolder(String userID,String parentPath,String oldName,String newName) throws UnknownHostException
    {
    this.userID=userID;this.parentPath=parentPath;this.oldName=oldName;this.newName=newName;
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
 if(oldName.contains("."))
 {
  
                BasicDBObject query = new BasicDBObject();
                query.put("_id",parentPath+pathMerger+oldName);
                DBCursor cursor = collection.find(query);
                                      
               if(cursor.hasNext())
               {
                    DBObject  renameFile=cursor.next();
                   BasicDBObject checknewquery = new BasicDBObject();
                checknewquery.put("_id",parentPath+pathMerger+newName+renameFile.get("type").toString());
                DBCursor tempCursor = collection.find(checknewquery);
                   if(tempCursor.hasNext())
                   {
                   return false;
                   }
                
                 GridFS file = new GridFS(mymongo.getDB(),userCollectionName);
                 InputStream data=file.findOne(query).getInputStream();
                 
                  BasicDBObject document = new BasicDBObject();
			document.append("_id",parentPath+pathMerger+newName+renameFile.get("type").toString());
			document.append("folder","0");
                        document.append("parent",parentPath);
                        document.append("name",newName+renameFile.get("type").toString());
                        document.append("type",renameFile.get("type").toString()); 
                        collection.insert(document);
			 GridFSInputFile inputFile = file.createFile(data);
	                inputFile.setId(parentPath+pathMerger+newName+renameFile.get("type").toString());
	                inputFile.put("path",parentPath);
                        inputFile.setFilename(newName+renameFile.get("type").toString());
	                inputFile.save();
                file.remove(file.findOne(query));
                collection.remove(renameFile);
                return true;
              }
               else
               {return false;}
 }
 else
 {return false;}
     } finally
            { 	mymongo.closeConnection();	}
 }
 public Boolean forFolder() throws UnknownHostException, IOException
 {
    try{  String mongoFolder=parentPath+pathMerger+oldName;
         	BasicDBObject query = new BasicDBObject();
                query.put("_id", mongoFolder);
                DBCursor cursor = collection.find(query);
                if (cursor.hasNext()) {
               BasicDBObject newquery = new BasicDBObject();
                newquery.put("_id", parentPath+pathMerger+newName);
                if(collection.find(newquery).hasNext())
                {
                return false;
                }
                 FolderDownload folder = new FolderDownload(userID,parentPath,oldName);
                 folder.renameFolder(newName);
                 return true;
                }
                else
                {return false;}
 
 }
    finally
            { 	mymongo.closeConnection();	}
 } 
}
