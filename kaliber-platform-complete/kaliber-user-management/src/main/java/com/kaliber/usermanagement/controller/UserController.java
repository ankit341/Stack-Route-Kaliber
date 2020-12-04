package com.kaliber.usermanagement.controller;

import com.kaliber.usermanagement.exception.UserNotFoundException;
import com.kaliber.usermanagement.model.User;
import com.kaliber.usermanagement.service.UserService;
import com.kaliber.usermanagement.util.CookieUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
public class UserController {
	@Autowired
	private UserService userService;

	public static Logger logger = LogManager.getLogger(UserService.class);

	//
	@GetMapping("/users")
	public ResponseEntity<?> getAllUsers(@RequestParam(name="page", required=false) Integer page,
										 @RequestParam(name="limit", required=false) Integer limit
	) {
		if (page == null && limit == null) {
			page = 0;
			limit = 10;
		}


		List<User> userArrayList;
		try{
			int totalNumberOfUsers = userService.countOfUsers();
			userArrayList = userService.getAllUsers(page, limit);
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("error", false);
			response.put("message", "Successfully getting user details");
			response.put("count", totalNumberOfUsers);
			response.put("result", userArrayList);
			response.put("status", HttpStatus.OK);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}catch (UserNotFoundException userNotFound){
			logger.error(userNotFound.getMessage());
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("error", true);
			response.put("message", "Could not retrieve all users");
			response.put("count", 0);
			response.put("result", new ArrayList<String>());
			response.put("status", HttpStatus.OK);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}

	}

	//To get user details by username
	@GetMapping("/users/{username}")
	public ResponseEntity<?> getByUserName(@PathVariable("username") String userName) {
		User user;
		System.out.println("IN users/username and username is " + userName);
		try {

			user = userService.getByUserName(userName);
			System.out.println("IN TRY and USER IS "+user);

			Map<String, Object> response = new HashMap<String, Object>();
			response.put("error", false);
			response.put("message", "Successfully getting userName");
			response.put("result", user);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (UserNotFoundException e) {
			System.out.println("IN CATCH OF USER");
			logger.error("No user with this username");

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

	}

	// To post the data related to user details
	@PostMapping("/users")
	public ResponseEntity<?> createUser(@RequestBody User user) throws DuplicateKeyException {
		try{
			userService.saveNewUser(user);
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("error", false);
			response.put("message", "Successfully posted user details");
			response.put("result", user);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}catch (DuplicateKeyException | UserNotFoundException e){
			logger.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}


	}

	@GetMapping("/userprofile")
	public ResponseEntity<User> getUserProfile(HttpServletRequest httpServletRequest) throws UserNotFoundException {
		System.out.println("inside userprofile controller");
		User user;
		System.out.println("inside /userProfile");
		user = userService.loadByUserName(httpServletRequest);
		System.out.println("user Profile is" + user);
		return new ResponseEntity<>(user,HttpStatus.OK);
	}

	@GetMapping(value = "/logout")
	public void userLogout(HttpServletResponse response) {
		String cookiename = "JWT_TOKEN";
		CookieUtil.clearCookie(response, cookiename);
	}

   //To save new topics added by the user
	@PatchMapping("/users/{username}/follow/topics")
	public ResponseEntity<?> addTopic(@PathVariable("username") String username,
									  @RequestBody Map<String, ArrayList<String>> topics) throws UserNotFoundException {
		ArrayList<String> topicsToBeAdded = topics.get("followedTopics");

		User user = userService.addTopics(username, topicsToBeAdded);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("error", false);
		response.put("message", "Successfully updated topics following");
		response.put("result", user);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

	// To follow users
	@PatchMapping("/users/{username}/follow/users")
	public ResponseEntity<?> followUser (@PathVariable("username") String username,
										 @RequestBody Map<String, ArrayList<String>> request) throws UserNotFoundException {
		ArrayList<String> usersToBeFollowed = request.get("users");
		User user = userService.followUsers(username, usersToBeFollowed);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("error", false);
		response.put("message", "Successfully updated user");
		response.put("result", user);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

   // To Unfollow the topics added by user
	@PatchMapping("/users/{username}/unfollow/topics")
		public ResponseEntity<?> unfollowTopics(@PathVariable("username") String username,
				@RequestBody Map<String, ArrayList<String>> topicList) throws UserNotFoundException {
			ArrayList<String> topicsToUnfollow = topicList.get("topics");
			System.out.println("HERE and topics to unfollow is " + topicsToUnfollow);
			User user = userService.unfollowTopics(username, topicsToUnfollow);
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("error", false);
			response.put("message", "Successfully updated user");
			response.put("result", user);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	//To get the followers list
	@GetMapping("users/{username}/followers")
	public ResponseEntity<?> toGetFollowers(@PathVariable ("username") String username)throws UserNotFoundException{

		HashSet<String> followers;
		try{
			followers = userService.getFollowers(username);
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("error", false);
			response.put("message", "Successfully getting followers");
			response.put("result", followers);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (UserNotFoundException e) {
			logger.error("No user with this username");

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

	}

	// To get the list of users following
	@GetMapping("users/{username}/following")
	public ResponseEntity<?> toGetUsersFollowing(@PathVariable ("username") String username)throws UserNotFoundException{
		HashSet<String> following;
		try{
			following = userService.getUsersFollowing(username);
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("error", false);
			response.put("message", "Successfully getting UsersFollowing");
			response.put("result", following);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (UserNotFoundException e) {
			logger.error("No user with this username");

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

	}

	@PatchMapping("users/{username}/totalpoints/{totalPoints}")
	public ResponseEntity<?> updatePoints(@PathVariable("username") String username,
										  @PathVariable("totalPoints") float totalPoints) throws UserNotFoundException {
		HashMap<String, Object> response = new HashMap<>();
		try{
			User modifiedUser = userService.updateTotalPoints(username, totalPoints);
			response.put("result", modifiedUser);
			response.put("count", 1);
			response.put("status", HttpStatus.OK);
			response.put("error", false);
			response.put("message", "Successfully updated the status of the quiz");
		} catch (UserNotFoundException e) {
			response.put("result", new ArrayList<String>());
			response.put("count", 0);
			response.put("status", HttpStatus.OK);
			response.put("error", true);
			response.put("message", "Status of the quiz cannot be changed as the quiz does not exist");
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
    @PatchMapping("/users/{username}/terms")
    public ResponseEntity<?> termsAndConditionsAccepted (@PathVariable("username") String  username) {
        User savedUser = userService.acceptTerms(username);
        HashMap<String, Object> response = new HashMap<String, Object>();
        response.put("result", savedUser);
        response.put("message", "Successfully updated User entity");
        response.put("errror", false);
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}