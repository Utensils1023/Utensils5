package com.Utensils1;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.Utensils1.Category.Category;
import com.Utensils1.Category.CategoryDAO;

@RestController
public class RESTCatController {
	@Autowired
	CategoryDAO Categorydao;
	
	@RequestMapping(value = "/AddCategory" , method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> AddCategory(@RequestBody String data) {
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
	
		
			Category p = new Category();
		
			p.setCategoryName(jobj.get("CategoryName").toString());
			
		
			p.setCategoryDescription(jobj.get("CategoryDesc").toString());
			
			
			
			Categorydao.insert(p);
			
			json.put("msg", "Category Added Successfully");
			
	
		
		return new ResponseEntity<String>(json.toString(), HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/ViewCategory", method=RequestMethod.POST)
	public ResponseEntity<String> ViewCategory (){
		List<Category> list = Categorydao.getCategorys();
		System.out.println(list);
		JSONArray jarr = new JSONArray();
		
		for(Category p: list )
		{
			JSONObject jobj = new JSONObject();
			
			jobj.put("CategoryId", p.getCategoryId());
			jobj.put("CategoryName", p.getCategoryName() );
			jobj.put("CategoryDescription", p.getCategoryDescription());
			
			
			jarr.add(jobj);
		
		}
	
		
		System.out.println(jarr.toJSONString());
		return new ResponseEntity<String>(jarr.toString(), HttpStatus.CREATED);
		
	}
	
	@RequestMapping(value="/DeleteCategory", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> DeleteCategory(@RequestBody String data) {
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
		
		Category p = new Category();
		 int proid = Integer.parseInt(jobj.get("CategoryID").toString());
		Categorydao.delete(proid);
		
	
		
		return new ResponseEntity<String>(jobj.toString(), HttpStatus.CREATED);

		}
	
	@RequestMapping(value="/UpdateCategory", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> UpdateCategory(@RequestBody String data) {
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
			
		int proid = Integer.parseInt(jobj.get("CategoryID").toString());
		Category p = new Category();
		
		p.setCategoryId(proid);
		p.setCategoryName(jobj.get("CategoryName").toString());
		
		p.setCategoryDescription(jobj.get("CategoryDesc").toString());
	
		
		Categorydao.update(p);
		
		return new ResponseEntity<String>(json.toString(), HttpStatus.CREATED);


		}		
	
	
}
