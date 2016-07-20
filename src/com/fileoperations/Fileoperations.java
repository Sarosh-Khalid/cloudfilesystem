package com.fileoperations;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.connection.Connection;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.mongodb.util.JSON;
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import com.sun.jersey.multipart.FormDataParam;

import org.apache.log4j.Logger;

import com.sun.jersey.core.header.FormDataContentDisposition;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.ws.rs.QueryParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Context;
import org.bson.types.ObjectId;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@Path("/fileops")
public class Fileoperations {
	  Connection mymongo;
        final String pathMerger = "/";
	public Fileoperations() throws UnknownHostException{
         mymongo=new Connection();
        }
        private String getUserCollection(String id)
        {
          DBCollection usercol = mymongo.getMembersCol();
         BasicDBObject query = new BasicDBObject();
         query.put("_id",id);
	 DBCursor cursor = usercol.find(query);
         if(! cursor.hasNext())
         {return "false";}
         DBObject col=cursor.next();
         return col.get("collection").toString();
        }
       	//Final
        @POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("text/html")
	public Response uploadFile(
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition fileInputDetails,
                        @FormDataParam("path") String path ,
			@FormDataParam("id") String  id)  {
            try{   
            String status;
		String colName=getUserCollection(id);
                
		if(colName.equals("false"))
                {
                status="Login Again";
            	return Response.status(200).entity(status).build();
                }
                DBCollection collection = mymongo.getCollection(colName);
		BasicDBObject query = new BasicDBObject();
                query.put("_id",path);
                DBCursor cursor = collection.find(query);
		if (cursor.hasNext()) {
			// Build our document and add all the fields
                    query.put("_id",path+pathMerger+fileInputDetails.getFileName());
		    cursor = collection.find(query);	
                    if(cursor.hasNext())
                    {
                    	 
            			status="File Already Exist";
            			return Response.status(200).entity(status).build();
                    }
                    BasicDBObject document = new BasicDBObject();
			document.append("_id", path+pathMerger+fileInputDetails.getFileName());
			document.append("folder","0");
                        document.append("parent",path);
                        document.append("name",fileInputDetails.getFileName());
                        String type=fileInputDetails.getFileName();
                        int index=type.lastIndexOf(".");
                        type=type.substring(index);
                        document.append("type",type); 
                        collection.insert(document);
			
                        // Now let's store the binary file data using filestore GridFS  
			GridFS fileStore = new GridFS(mymongo.getDB(), colName);
	                GridFSInputFile inputFile = fileStore.createFile(fileInputStream);
	                inputFile.setId(path+pathMerger+fileInputDetails.getFileName());
	                inputFile.put("path",path);
                        inputFile.setFilename(fileInputDetails.getFileName());
	                inputFile.save();
                        status = "true";
		       return Response.status(200).entity(status).build();
		} else {
		        status="Operation Failed @ "+path+id;
			return Response.status(200).entity(status).build();
			
		}
            }
            finally
            { 	mymongo.closeConnection();	}
	}
	//Final
        @GET
	@Path("/download/file")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response downloadFilebyID(@QueryParam("id")  String userid,@QueryParam("path")  String path,@QueryParam("name") String name) throws IOException {
		try{
		
            Response response = null;
            String status;
		String colName=getUserCollection(userid);
                
		if(colName.equals("false"))
                {
                status="Login Again";
                response = Response.status(404).entity(status).type("text/plain").build();
                }
                  if(!name.contains("."))
                {
                    FolderDownload folder = new FolderDownload(userid,path,name);
                    File zipped = folder.makeFolder();
                     if(zipped == null)
                     {
                response = Response.status(404).entity(" Unable to get file with ID: " + path+pathMerger+name).type("text/plain").	build();
                     }
                     else{
                     Response.ResponseBuilder builder;
                     builder = Response.ok(zipped);
	             builder.header("Content-Disposition", "attachment; filename=" + name+".zip");
                     response = builder.build();
                     }
                    return response;
                  
                }
                else{
		DBCollection collection = mymongo.getCollection(colName);
		String fileid=path+pathMerger+name;
		BasicDBObject query = new BasicDBObject();
                query.put("_id", fileid);
                DBCursor cursor = collection.find(query);
                if (cursor.hasNext()) {
                HashMap<String, String> fields = new HashMap<String,String>();
	        DBObject temp = cursor.next();
                fields.put("_id",temp.get("_id").toString());
                fields.put("name",temp.get("name").toString());
	        GridFS fileStore = new GridFS(mymongo.getDB(), colName);
	            GridFSDBFile gridFile;
                    gridFile = fileStore.findOne(query);
                    ResponseBuilder builder;
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    fields.put("name",gridFile.getFilename());
                    InputStream in = gridFile.getInputStream();
		    int data = in.read();
	            while (data >= 0) {
	            out.write((char) data);
	            data = in.read();
	            }
		    out.flush();
	
                  
                   builder = Response.ok(out.toByteArray());
		
                builder.header("Content-Disposition", "attachment; filename=" + fields.get("name"));
		response = builder.build();
                }else {
        	response = Response.status(404).entity(" Unable to get file with ID: " + fileid).type("text/plain").	build();
        }
                  }
		return response;
                }
                  finally
            { 	mymongo.closeConnection();	}
	}
        //Final
        @POST
	@Path("/pathdetails/specific")
	@Produces(MediaType.TEXT_HTML)
	public String pathDetailsSpecific(@FormParam("type")  String type,@FormParam("id")  String id ,@FormParam("path") String path) {
		try{
	    	 String status; 
                 JSONArray details=new JSONArray();
                 BasicDBObject query = new BasicDBObject();
                 String colName=getUserCollection(id);
                 if(colName.equals("false"))
                 {
                  status="Login Again";
            	  return status;
                 }
                DBCollection collection = mymongo.getCollection(colName);
                
              
	       
                 List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
                  switch (type)
                  {
                  case"files":
                  obj.add(new BasicDBObject("folder","0"));
                  break;
                  case"audio":
                  obj.add(new BasicDBObject("type",".mp3"));    
                  break;
                  case"video":
                   obj.add(new BasicDBObject("type",".wmv"));      
                  break;
                  case"photos":
                   obj.add(new BasicDBObject("type",".jpeg"));
                    obj.add(new BasicDBObject("type",".jpg"));
                     obj.add(new BasicDBObject("type",".png"));
                  break;
                  case"documents":
                   obj.add(new BasicDBObject("type",".docx"));
                    obj.add(new BasicDBObject("type",".pdf"));
                  break;
                  default:
                  }
                  BasicDBObject specquery = new BasicDBObject();
                  specquery.put("$or", obj);
                  
               DBCursor cursor = collection.find(specquery);  
                 for (int count=0;cursor.hasNext();count++) 
                 {
                         DBObject temp = cursor.next();
                         JSONObject fields = new JSONObject();
                         fields.put("path",temp.get("_id").toString());
	                 fields.put("name",temp.get("name").toString());
                         fields.put("isfolder",temp.get("folder").toString());
                         fields.put("type",temp.get("type").toString());
                          fields.put("path",temp.get("parent").toString());
                         details.add(count,fields);
                }
                    return details.toJSONString();
                }  finally
            { 	mymongo.closeConnection();	}	
                             
	}
       
        //Final
	@POST
	@Path("/pathdetails")
	@Produces(MediaType.TEXT_HTML)
	public String pathDetails(@FormParam("id")  String id ,@FormParam("path") String path)  {
		try{  
	    	 String status; 
                 JSONArray details=new JSONArray();
                 BasicDBObject query = new BasicDBObject();
                 String colName=getUserCollection(id);
                 if(colName.equals("false"))
                 {
                  status="Login Again";
            	  return status;
                 }
                DBCollection collection = mymongo.getCollection(colName);
                
                query.put("parent",path);
	        DBCursor cursor = collection.find(query);
                 for (int count=0;cursor.hasNext();count++) 
                 {
                         DBObject temp = cursor.next();
                         JSONObject fields = new JSONObject();
                         fields.put("path",temp.get("_id").toString());
	                 fields.put("name",temp.get("name").toString());
                         fields.put("isfolder",temp.get("folder").toString());
                         fields.put("type",temp.get("type").toString());
                         details.add(count,fields);
                }
                    return details.toJSONString();
                }  finally
            { 	mymongo.closeConnection();	}	
                             
	}
       	//Final
        @POST
	@Path("/view")
	@Produces(MediaType.TEXT_HTML)
        public String view(@FormParam("id")  String id ,@FormParam("path") String path,@FormParam("name") String name)         {
        if(name.contains("."))
        {
        return "Not Supported yet..";
        }
        else
        {
        return path+pathMerger+name;
        }
        }
	//Final
        @POST
	@Path("/parentpath")
	@Produces(MediaType.TEXT_HTML)
        public String getParentPath(@FormParam("id")  String id ,@FormParam("path") String path)         {
       try{  String colName=getUserCollection(id);
         if(path.equals("Home") || path.equals("SELF"))
         {return "Home";}
         String status;
                if(colName.equals("false"))
                 {
                  status="Home";
            	  return status;
                 }
                DBCollection collection = mymongo.getCollection(colName);
                BasicDBObject query = new BasicDBObject();
             
                query.put("_id",path);
                DBCursor cursor = collection.find(query);
		if (cursor.hasNext()) 
                {return cursor.next().get("parent").toString();}
                return "Home";
       }  finally
            { 	mymongo.closeConnection();	}
        }
	
        //Final	
        @POST
	@Path("/makefolder")
	@Produces(MediaType.TEXT_HTML)
         public String makeFolder(@FormParam("path")  String path,@FormParam("id")  String id)  {
		try{
                String colName=getUserCollection(id);
                String status;
                if(colName.equals("false"))
                 {
                  status="Login Again";
            	  return status;
                 }
                DBCollection collection = mymongo.getCollection(colName);
                BasicDBObject query = new BasicDBObject();
             
                query.put("_id",path);
                DBCursor cursor = collection.find(query);
		if (! cursor.hasNext()) 
                {
                  status = "Wrong Path !!!path:"+path;
		 JSONObject result = new JSONObject();
                 result.put("result",status);
                 return result.toJSONString();
                }
		   String name="untitled";   
                query.put("_id",path+pathMerger+name);
		    cursor = collection.find(query);	
                    for(int suffix=1;cursor.hasNext();suffix++)
                    {
                      name="untitled";
                        name +=String.valueOf(suffix);
                        query.put("_id",path+pathMerger+name);
		    cursor = collection.find(query);
                       
                    }
                
                
                BasicDBObject document = new BasicDBObject();
                document.put("_id",path+pathMerger+name);
                document.put("folder","1");
                document.put("name",name);
                document.put("parent",path);
                 document.put("type","1");
                collection.insert(document);
               JSONObject result = new JSONObject();
               result.put("result","true");
               result.put("name", name);
               return result.toJSONString();
                }  finally
            { 	mymongo.closeConnection();	}

	}
	
        @POST
	@Path("/rename")
	@Produces(MediaType.TEXT_HTML)
        public String rename(@FormParam("id")  String id ,@FormParam("path") String path,@FormParam("newname") String newname,@FormParam("oldname") String oldname) throws UnknownHostException, IOException 
      {
           RenameFolder folder = new RenameFolder(id,path,oldname,newname);
             if(oldname.contains("."))
             {      return folder.forSingleFile().toString();       }
           else
             {  if(folder.forFolder())
             {
             return delete(id,path,oldname);
             }
             else{return "false";}
                 }
        
     }
       //Final
        @POST
	@Path("/copy")
	@Produces(MediaType.TEXT_HTML)
        public String copy(@FormParam("id")  String id ,@FormParam("path") String path,@FormParam("name") String name,@FormParam("newpath") String newPath) throws UnknownHostException, IOException 
      {
           CopyClass cp = new CopyClass(id,path,name,newPath);
             if(name.contains("."))
             {      return cp.forSingleFile().toString();       }
           else
             {   return cp.forFolder().toString();         }
        
     }
      //Final
        @POST
	@Path("/delete")
	@Produces(MediaType.TEXT_HTML)
        public String delete(@FormParam("id")  String id ,@FormParam("path") String path,@FormParam("name") String name)   
        {
             try{   String colName=getUserCollection(id);
                String status;
                if(colName.equals("false"))
                 {
                  status="Login Again";
            	  return status;
                 }
                DBCollection collection = mymongo.getCollection(colName);
                BasicDBObject query = new BasicDBObject();
                query.put("_id",path+pathMerger+name);
                DBCursor cursor = collection.find(query);
                                      
               if(cursor.hasNext())
               {
                  DBObject  delete=cursor.next();
               if( name.contains("."))
               {
                GridFS file = new GridFS(mymongo.getDB(),colName);
                file.remove(file.findOne(query));
                collection.remove(delete);
                return"true";
               }
               else
               {
                 
               String parentFolder=path+pathMerger+name;
               BasicDBObject query2 = new BasicDBObject();
               query2.put("parent",parentFolder);
             System.out.println(parentFolder);
               cursor=collection.find(query2);
               if(! cursor.hasNext())
              {
                   collection.remove(delete);
                   return "true";
               }
                 while (cursor.hasNext()) 
                {
               DBObject deleteFolderChild=cursor.next();
                 String isfolder = deleteFolderChild.get("folder").toString();
                    if( isfolder.equals("1"))
                      {
                       String folderName=deleteFolderChild.get("name").toString();
                       delete(id,parentFolder,folderName);
                       collection.remove(deleteFolderChild);
                      }
                     else
                     {
                         String fileName=deleteFolderChild.get("name").toString();
                         delete(id,parentFolder,fileName);
                     }
                     }
                  collection.remove(delete);
                  return "true";
               }
               }
               else
               {return "Not Found"+path+"/"+name;   }
              
             }  finally
            { 	mymongo.closeConnection();	}                                              
           
    
        
        }

	//Final
        @POST
	@Path("/move")
	@Produces(MediaType.TEXT_HTML)
        public String move(@FormParam("id")  String id ,@FormParam("path") String path,@FormParam("name") String name,@FormParam("newpath") String newPath) throws UnknownHostException, IOException 
      {
           if(copy(id,path,name,newPath).equals("true")) 
           {return delete(id,path,name);}
           else
           {return "false";}
     }
}

