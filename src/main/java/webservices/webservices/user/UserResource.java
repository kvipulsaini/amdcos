package webservices.webservices.user;

import java.net.URI;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {
	
	@Autowired
	private UserDaoService service;
	
	// retriveAllUsers
	@GetMapping("/users")
	public List<User> retriveAllUsers(){
		return service.findAll();
	}
	
	// GET /user/{id}
	@GetMapping("/user/{id}")
	public User retrieveUser(@PathVariable int id) {
		 User user = service.findOne(id);
		if(user==null)
			throw new UserNotFoundException("id-"+id);
		 
		 return user;
		
	}
	
	// Delete /user/{id}
		@DeleteMapping("/user/{id}")
		public void  deleteUser(@PathVariable int id) {
			try {
				User user = service.deleteById(id);
			}
			catch(Exception e){
				if(user==null)
				throw new UserNotFoundException("id-"+id);		
			}
//			 User user = service.deleteById(id);
//			if(user==null)
//				
		}
	
	// Created & return the URI
	// input details of user 
	
	@PostMapping("users")
	public ResponseEntity<Object> createUser(@Validated @RequestBody User user) {
		User savedUser = service.save(user);
		
		 URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
		
	}
	

}
