package com.bader88.rest.webservices.restfulwebservices.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {
	private static List<User> users = new ArrayList<>();
	private static int userIdCount = 0;
	
	static {
		users.add(new User(++userIdCount, "bader", LocalDate.now().minusYears(27)));
		users.add(new User(++userIdCount, "ibrahim", LocalDate.now().minusYears(26)));
		users.add(new User(++userIdCount, "nader", LocalDate.now().minusYears(22)));
	}

	public List<User> findAll() {
		return users;
	}

	public User findById(int id) {
		Predicate<? super User> predicate = user -> user.getId().equals(id); 
		return users.stream().filter(predicate).findFirst().orElse(null);
	}
	public User saveUser(User user) {
		user.setId(++userIdCount);
		users.add(user);
		return user;
	}
	
	public void deleteUserById(int id) {
		Predicate<? super User> predicate = user -> user.getId().equals(id);
		users.removeIf(predicate);
	}
}
