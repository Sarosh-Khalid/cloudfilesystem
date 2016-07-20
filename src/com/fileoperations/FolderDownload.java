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
public class FolderDownload {
   String userID,parentPath,folderName;
    final String pathMerger = "/";
    Connection mymongo;
     DBCollection collection;
     String userCollectionName;
      List<BasicDBObject> pathOfChildrenFolders = new ArrayList<BasicDBObject>();
       List<BasicDBObject> pathOfChildrenEmptyFolders = new ArrayList<BasicDBObject>();
  
      public FolderDownload(String userID,String parentPath,String folderName) throws UnknownHostException
    {
    this.userID=userID;this.parentPath=parentPath;this.folderName=folderName;
    mymongo=new Connection();
   DBCollection usercol = mymongo.getMembersCol();
         BasicDBObject query = new BasicDBObject();
         query.put("_id",userID);
	 DBCursor cursor = usercol.find(query);
         DBObject col=cursor.next();
         userCollectionName=col.get("collection").toString();
        collection = mymongo.getCollection(userCollectionName);
    }
   private void getPathOfAllChildrenFolder(String parentPath,String folderName)
 {
  BasicDBObject toFindAllFolderInFolder=new BasicDBObject();
                pathOfChildrenFolders.add(new BasicDBObject("path",parentPath+pathMerger+folderName));
                pathOfChildrenEmptyFolders.add(new BasicDBObject("parent",parentPath+pathMerger+folderName));
             //   pathOfChildrenEmptyFolders.add(new BasicDBObject("path",parentPath+pathMerger+folderName));
               toFindAllFolderInFolder.put("parent",parentPath+pathMerger+folderName);
               DBCursor allFolder=collection.find(toFindAllFolderInFolder);
               while(allFolder.hasNext())
               {
               DBObject indivFolder=allFolder.next();
              getPathOfAllChildrenFolder(indivFolder.get("parent").toString(),indivFolder.get("name").toString());
             
               }
 
 }
    public File makeFolder() throws IOException
    {
            try{   
                String mongoFolder=parentPath+pathMerger+folderName;
         	BasicDBObject query = new BasicDBObject();
                query.put("_id", mongoFolder);
                DBCursor cursor = collection.find(query);
                if (cursor.hasNext()) {
               
               getPathOfAllChildrenFolder(parentPath,folderName);
               BasicDBObject toFindAllFilesInFolder=new BasicDBObject();
               toFindAllFilesInFolder.put("$or",pathOfChildrenFolders);
               GridFS fileStore = new GridFS(mymongo.getDB(), userCollectionName);
	       List <GridFSDBFile> AllFiles=fileStore.find(toFindAllFilesInFolder);
               File zip = new File(folderName+".zip");
               ZipOutputStream folderToZip=new ZipOutputStream(new FileOutputStream(zip));
             
               for(int i=0;i<AllFiles.size();i++)
               {
                 GridFSDBFile indivFile=AllFiles.get(i);
                 ByteArrayOutputStream out = new ByteArrayOutputStream();
                 InputStream in = indivFile.getInputStream();
		 int data = in.read();
	         while (data >= 0) {
	         out.write((char) data);
	         data = in.read();
	         }
		
                 out.flush();
                 String zipPath;
                 zipPath=indivFile.get("path").toString()+pathMerger+indivFile.getFilename();
                 zipPath=  zipPath.replaceFirst(parentPath,"");
                 zipPath=zipPath.replaceFirst(pathMerger,"");
	         ZipEntry add = new ZipEntry(zipPath);
                 folderToZip.putNextEntry(add);
                 folderToZip.write(out.toByteArray(),0,out.toByteArray().length);
               
               }
              
                
               BasicDBObject toFindAllEmptyFilesInFolder=new BasicDBObject();
               toFindAllEmptyFilesInFolder.put("$or",pathOfChildrenEmptyFolders);
               DBCursor emptyFolder=collection.find(toFindAllEmptyFilesInFolder);
               List <String> emptyFolderPathToAdd=new ArrayList<String>();
               while(emptyFolder.hasNext())
               {
               DBObject temp=emptyFolder.next();
               BasicDBObject isEmpty=new BasicDBObject();
               isEmpty.put("parent",temp.get("_id").toString());
              
                   if(!collection.find(isEmpty).hasNext())
               {
                   if(!temp.get("_id").toString().contains("."))
                   {
                emptyFolderPathToAdd.add(temp.get("_id").toString());
                   }
                   }
              
               }
               for(int i=0;i<emptyFolderPathToAdd.size();i++) 
               {
                 String temp=emptyFolderPathToAdd.get(i).replaceFirst(parentPath,"");
                 temp=temp.replaceFirst(pathMerger,"");
                 ZipEntry add = new ZipEntry(temp+pathMerger);
                 folderToZip.putNextEntry(add);
                 
               }
               
               folderToZip.closeEntry();
               folderToZip.close();
               return zip;
               }
               else
               {   return null;   }
            
            }
             finally
            { 	mymongo.closeConnection();	}
    
   
    }
   public Boolean copyFolder(String newPath) throws IOException
    {
          try{
              String mongoFolder=parentPath+pathMerger+folderName;
          
         	BasicDBObject query = new BasicDBObject();
                query.put("_id", mongoFolder);
                DBCursor cursor = collection.find(query);
                if (cursor.hasNext()) {
               BasicDBObject newquery = new BasicDBObject();
                newquery.put("_id", newPath+pathMerger+folderName);
                if(collection.find(newquery).hasNext())
                {
                return false;
                }
                
               getPathOfAllChildrenFolder(parentPath,folderName);
               BasicDBObject toFindAllFilesInFolder=new BasicDBObject();
               toFindAllFilesInFolder.put("$or",pathOfChildrenFolders);
               GridFS fileStore = new GridFS(mymongo.getDB(), userCollectionName);
	       List <GridFSDBFile> AllFiles=fileStore.find(toFindAllFilesInFolder);
            
               for(int i=0;i<AllFiles.size();i++)
               {
                 GridFSDBFile indivFile=AllFiles.get(i);
                 InputStream data = indivFile.getInputStream();
		  String zipPath;
                 zipPath=indivFile.get("path").toString();
                 String tempFileName=indivFile.getFilename();
                         
                 zipPath=  zipPath.replaceFirst(parentPath,newPath);
                  BasicDBObject document = new BasicDBObject();
			document.append("_id",zipPath+pathMerger+tempFileName);
			document.append("folder","0");
                        document.append("parent",zipPath);
                        document.append("name",tempFileName);
                        int index=tempFileName.lastIndexOf(".");
                        document.append("type",tempFileName.substring(index));
                       collection.insert(document);
                 GridFSInputFile inputFile = fileStore.createFile(data);
	                inputFile.setId(zipPath+pathMerger+tempFileName);
	                inputFile.put("path",zipPath);
                        inputFile.setFilename(tempFileName);
	                inputFile.save();
		
               }
              
                
               BasicDBObject toFindAllEmptyFilesInFolder=new BasicDBObject();
               toFindAllEmptyFilesInFolder.put("$or",pathOfChildrenEmptyFolders);
               DBCursor allFolders=collection.find(toFindAllEmptyFilesInFolder);
               while(allFolders.hasNext())
               {
               DBObject temp=allFolders.next();
               if(temp.get("folder").toString().equals("1"))
               { 
                String tempPath=temp.get("parent").toString().replaceFirst(parentPath,newPath);
                   BasicDBObject document = new BasicDBObject();
                document.put("_id",tempPath+pathMerger+temp.get("name"));
                document.put("folder","1");
                document.put("name",temp.get("name"));
                document.put("parent",tempPath);
                 document.put("type","1");
                collection.insert(document);
               
               }
               }
              
              return true;
               }
               else
               {   return false;   }
          
          }
           finally
            { 	mymongo.closeConnection();	}
    }
   
    public Boolean renameFolder(String newName) throws IOException
     {
              try{  String mongoFolder=parentPath+pathMerger+folderName;
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
                BasicDBObject doc = new BasicDBObject();
                doc.put("_id",parentPath+pathMerger+newName);
                doc.put("folder","1");
                doc.put("name",newName);
                doc.put("parent",parentPath);
                 doc.put("type","1");
               
                collection.insert(doc);
                
               getPathOfAllChildrenFolder(parentPath,folderName);
               BasicDBObject toFindAllFilesInFolder=new BasicDBObject();
               toFindAllFilesInFolder.put("$or",pathOfChildrenFolders);
               GridFS fileStore = new GridFS(mymongo.getDB(), userCollectionName);
	       List <GridFSDBFile> AllFiles=fileStore.find(toFindAllFilesInFolder);
            
              for(int i=0;i<AllFiles.size();i++)
               {
                 GridFSDBFile indivFile=AllFiles.get(i);
                 InputStream data = indivFile.getInputStream();
		  String zipPath;
                  zipPath=indivFile.get("path").toString();
                  String tempFileName=indivFile.getFilename();
                  zipPath=  zipPath.replaceFirst(parentPath+pathMerger+folderName,parentPath+pathMerger+newName);
                  BasicDBObject document = new BasicDBObject();
			document.append("_id",zipPath+pathMerger+tempFileName);
			document.append("folder","0");
                        document.append("parent",zipPath);
                        document.append("name",tempFileName);
                        int index=tempFileName.lastIndexOf(".");
                        document.append("type",tempFileName.substring(index));
                        collection.insert(document);
                 GridFSInputFile inputFile = fileStore.createFile(data);
	                inputFile.setId(zipPath+pathMerger+tempFileName);
	                inputFile.put("path",zipPath);
                        inputFile.setFilename(tempFileName);
	                inputFile.save();
		
               }
              
               
               BasicDBObject toFindAllEmptyFilesInFolder=new BasicDBObject();
               toFindAllEmptyFilesInFolder.put("$or",pathOfChildrenEmptyFolders);
               DBCursor allFolders=collection.find(toFindAllEmptyFilesInFolder);
               while(allFolders.hasNext())
               {
               DBObject temp=allFolders.next();
               if(temp.get("folder").toString().equals("1"))
               { 
                String tempPath=temp.get("parent").toString();
                tempPath = tempPath.replaceFirst(parentPath+pathMerger+folderName,parentPath+pathMerger+newName);
                BasicDBObject updocument = new BasicDBObject();
                updocument.put("_id",tempPath+pathMerger+temp.get("name"));
                updocument.put("folder","1");
                updocument.put("name",temp.get("name"));
                updocument.put("parent",tempPath);
                 updocument.put("type","1");
               collection.insert(updocument);
               
               }
               }
              
              return true;
               }
               else
               {   return false;   }
                
              }
               finally
            { 	mymongo.closeConnection();	}
    }
}
