package com.Utensils1;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.util.UriComponentsBuilder;


import com.Utensils1.Product.Product;
import com.Utensils1.Product.ProductDAO;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
@CrossOrigin(origins = "https://cookwithjoy1.herokuapp.com", maxAge = 3600)
@RestController
public class RESTController {
	@Autowired
	ProductDAO productdao;
	
	@Autowired
	ServletContext context;
	
	@RequestMapping(value = "/AddProduct" , method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> AddProduct(@RequestBody String data) {
		System.out.println(data);
		System.out.println("wtf");
		JSONParser jpar=new JSONParser();
		
		JSONObject jobj =new JSONObject(); 
		try{
		jobj=(JSONObject)jpar.parse(data);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		JSONObject json = new JSONObject();
	
		
			Product p = new Product();
		
			p.setProductName(jobj.get("ProductName").toString());
			
			p.setProductCategory(jobj.get("ProductCategory").toString());
			
			
			p.setProductPrice(jobj.get("ProductPrice").toString());
			p.setProductDescription(jobj.get("ProductDesc").toString());
			
			p.setProductQuantity(jobj.get("ProductQuant").toString());
			
			productdao.insert(p);
			
			json.put("msg", "Product Added Successfully");
			
	
		
		return new ResponseEntity<String>(json.toString(), HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/ViewProduct", method=RequestMethod.POST)
	public ResponseEntity<String> ViewProduct (){
		List<Product> list = productdao.getproducts();
		System.out.println(list);
		JSONArray jarr = new JSONArray();
		
		for(Product p: list )
		{
			JSONObject jobj = new JSONObject();
			
			jobj.put("ProductId", p.getProductId());
			jobj.put("ProductName", p.getProductName() );
			jobj.put("ProductPrice", p.getProductPrice());
			jobj.put("ProductQuantity", p.getProductQuantity());
			jobj.put("ProductDescription", p.getProductDescription());
			jobj.put("Productategory", p.getProductCategory());
			
			jarr.add(jobj);
		
		}
	
		
		System.out.println(jarr.toJSONString());
		return new ResponseEntity<String>(jarr.toString(), HttpStatus.CREATED);
		
	}
	
	@RequestMapping(value="/DeleteProduct", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> DeleteProduct(@RequestBody String data) {
		System.out.println(data);
		
		JSONParser jpar=new JSONParser();
		
		JSONObject jobj =new JSONObject(); 
		try{
		jobj=(JSONObject)jpar.parse(data);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		Product p = new Product();
		 int proid = Integer.parseInt(jobj.get("ProductID").toString());
		productdao.delete(proid);
		
	
		
		return new ResponseEntity<String>(jobj.toString(), HttpStatus.CREATED);

		}
	
	@RequestMapping(value="/UpdateProduct", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> UpdateProduct(@RequestBody String data) {
		System.out.println(data);
		
		JSONParser jpar=new JSONParser();
		
		JSONObject jobj =new JSONObject(); 
		try{
		jobj=(JSONObject)jpar.parse(data);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		JSONObject json = new JSONObject();
			
		int proid = Integer.parseInt(jobj.get("ProductID").toString());
		Product p = new Product();
		
		p.setProductId(proid);
		p.setProductName(jobj.get("ProductName").toString());
		p.setProductPrice(jobj.get("ProductPrice").toString());
		p.setProductDescription(jobj.get("ProductDesc").toString());
		p.setProductCategory(jobj.get("ProductCategory").toString());
		p.setProductQuantity(jobj.get("ProductQuant").toString());
		
		productdao.update(p);
		
		return new ResponseEntity<String>(json.toString(), HttpStatus.CREATED);


		}
	
	@RequestMapping(value="/jj", method=RequestMethod.POST)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String uploadFile(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail){
		
			
		System.out.println(uploadedInputStream);
		System.out.println(fileDetail);
		return "File uploaded Successfully!!";
		
		
	
	}
	
	@RequestMapping(value = "/upload" , method = RequestMethod.POST)
	public ResponseEntity<String> updateProfilePicture(MultipartHttpServletRequest request,
			HttpServletResponse response, UriComponentsBuilder ucBuilder) {

		System.out.println(request.getHeader("user"));

		System.out.println(request.getFile("file").getName());
		System.out.println(request.getFile("file").getSize());
		System.out.println(request.getFile("file").getContentType());
		System.out.println(request.getFile("file").getOriginalFilename());

		JSONObject json = new JSONObject();

		BufferedOutputStream stream = null;

		try {
			String path = context.getRealPath("/");

			System.out.println(path);

			File directory = null;

			System.out.println(request.getFile("file"));

			if (request.getFile("file").getContentType().contains("image")) {
				directory = new File(path + "\\resources\\images");

				System.out.println(directory);

				byte[] bytes = null;
				File file = null;
				bytes = request.getFile("file").getBytes();

				
				
				
				if (!directory.exists())
					directory.mkdirs();

				file = new File(directory.getAbsolutePath() + System.getProperty("file.separator")
						+ request.getHeader("user") + ".jpg");
			
				
				System.out.println(file.getAbsolutePath());
				
			

				stream = new BufferedOutputStream(new FileOutputStream(file));
				stream.write(bytes);
				
				json.put("status", "Image Uploaded");

				stream.close();

				Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
						  "cloud_name", "vishal2310",
						  "api_key", "628851412251664",
						  "api_secret", "tzdCQ2z-FvIDDBCJArFWQ-S3Yac"));
				
				
				
				Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
				System.out.println(uploadResult.get("url"));
				
				Product p = new Product();
				p.setImage(uploadResult.get("url").toString());
				
				productdao.insert(p);
				
				
				/*
				 * Profile p = ps.getProfile(request.getHeader("user"));
				 * 
				 * if( p != null ) { p.setImage("resources/images/" +
				 * HashManager.generateHashCode( request.getHeader("user") +
				 * hashname[0] ) + ".jpg" );
				 * 
				 * ps.updateProfile(p);
				 * 
				 * json.put("status", "Uploaded"); json.put("imagesrc",
				 * "resources/images/" + HashManager.generateHashCode(
				 * request.getHeader("user") + hashname[0] ) + ".jpg" ); }
				 */

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(json.toString());

		return new ResponseEntity<String>(json.toString(), HttpStatus.CREATED);
	}
	
	
	
	
	
	
	

	
}
