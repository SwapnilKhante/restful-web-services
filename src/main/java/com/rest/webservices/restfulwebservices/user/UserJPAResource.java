package com.rest.webservices.restfulwebservices.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserJPAResource {

	@Autowired
	private UserDAOService service;
	
	@Autowired
	private UserRepository UserRepository;

	@Autowired
	private PostRepository postRepository;
	
	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers() {
		return UserRepository.findAll();
	}

	@GetMapping("/jpa/users/{id}")
	public Resource<User> retrieveUser(@PathVariable int id) {
		Optional<User> user = UserRepository.findById(id);
		 if(!user.isPresent()) {
			 throw new UserNotFoundException("Id : " +id);
		 }
		 
		 //HATEOAS resource added to provide a link to fetch all users
		 // Hyper media as the engine of application state
		 
		 Resource<User> resource = new Resource<User>(user.get());
		  ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		  resource.add(linkTo.withRel("link-to-all-users"));
		 
		 return resource;
	}

	@DeleteMapping("/jpa//users/{id}")
	public void deleteUser(@PathVariable int id) {
		 UserRepository.deleteById(id);
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User createdUser = UserRepository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}")
				.buildAndExpand(createdUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/jpa/users/{id}/post")
	public List<Post> retrievePostForUser(@PathVariable int id) {
		Optional<User> userOptioal = UserRepository.findById(id);
		if(!userOptioal.isPresent()) {
			throw new UserNotFoundException("Id -:"+ id);
		}
		User user = userOptioal.get();
		return user.getPost();
	}
	
	@PostMapping("/jpa/users/{id}/post")
	public ResponseEntity<Object> createPost(@PathVariable int id,  @RequestBody Post post) {
		Optional<User> userOptioal = UserRepository.findById(id);
		if(!userOptioal.isPresent()) {
			throw new UserNotFoundException("Id -:"+ id);
		}
		User user = userOptioal.get(); 
		post.setUser(user);
	    postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}")
				.buildAndExpand(post.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
}
