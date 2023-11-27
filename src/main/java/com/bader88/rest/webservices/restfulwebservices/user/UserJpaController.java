package com.bader88.rest.webservices.restfulwebservices.user;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bader88.rest.webservices.restfulwebservices.jpa.PostRepository;
import com.bader88.rest.webservices.restfulwebservices.jpa.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/jpa")
public class UserJpaController {
	
	private UserRepository userRepository;
	private PostRepository postRepository;
	
	public UserJpaController(UserRepository repository,PostRepository postRepository) {
		this.userRepository=repository;
		this.postRepository=postRepository;
	}

	@GetMapping(path = "/users")
	public List<User> retrieveAllUsers(){
		return userRepository.findAll();
	}
	
	@GetMapping(path = "/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		
		if(user.isEmpty()) {
			throw new UserNotFoundException("The ID : "+id+" is NOT Found");
		}
		EntityModel<User> entityModel = EntityModel.of(user.get()	);
		
		WebMvcLinkBuilder link =  linkTo(methodOn(this.getClass()).retrieveAllUsers());
		entityModel.add(link.withRel("all-users"));
		
		return entityModel;
	}
	
	@GetMapping(path = "/users/{id}/posts")
	public List<Post> retriePostsByUserId(@PathVariable int id){
		Optional<User> user = userRepository.findById(id);
		
		if(user.isEmpty()){
			throw new UserNotFoundException("The ID : "+id+" is NOT Found");
		}
		
		return user.get().getPost();
	}
	
	@PostMapping(path = "/users")
	public ResponseEntity<User> saveNewUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(savedUser.getId())
						.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@PostMapping(path = "/users/{id}/posts")
	public ResponseEntity<Object> saveNewPost(@PathVariable int id, @Valid @RequestBody Post post) {
		Optional<User> user = userRepository.findById(id);
		
		if(user.isEmpty()) {
			throw new UserNotFoundException("The ID : "+id+" is NOT Found");
		}
		
		post.setUser(user.get());
		
		Post savedPost = postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
						.replacePath("/jpa/users/{id}/posts") // Replace the current path with the desired path
						.buildAndExpand(post.getUser().getId())
						.toUri();
		
		return ResponseEntity.created(location).build();
		
	}
	
	@DeleteMapping(path = "/users/{id}")
	public void deleteUser(@PathVariable int id){
		userRepository.deleteById(id);
	}
	
	
}
