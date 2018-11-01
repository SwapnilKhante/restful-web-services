package com.rest.webservices.restfulwebservices.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDAOService {

	private static List<User> Users = new ArrayList<>();

	private static int userCount = 3;
	static {
		Users.add(new User(1, "Adam", new Date()));
		Users.add(new User(2, "Jill", new Date()));
		Users.add(new User(3, "Jack", new Date()));
	}

	public List<User> findAll() {
		return Users;
	}

	public User save(User user) {
		if (user.getId() == null) {
			user.setId(++userCount);
		}
		Users.add(user);
		return user;
	}

	public User findOne(int id) {
		for (User user : Users) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}

	public User deleteUser(int id) {
		Iterator<User> iterator = Users.iterator();
		while (iterator.hasNext()) {
			User user = iterator.next();
			if (user.getId() == id) {
				iterator.remove();
				return user;
			}
		}
		return null;
	}
}
