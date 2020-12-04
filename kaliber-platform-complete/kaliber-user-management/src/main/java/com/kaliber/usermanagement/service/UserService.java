package com.kaliber.usermanagement.service;

import com.kaliber.usermanagement.exception.UserNotFoundException;

import com.kaliber.usermanagement.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;

public interface UserService {


    User getByUserName(String userName) throws UserNotFoundException;

    User saveNewUser(User user) throws UserNotFoundException;

    String getAccessToken(String code) throws UserNotFoundException;

    String getGitlabAccessToken(String code) throws UserNotFoundException;

    User getGitlabUserProfile(String accessToken) throws UserNotFoundException;

    ArrayList<User> getAllUsers(Integer page, Integer limit) throws UserNotFoundException;

    User getUserProfile(String accessToken) throws UserNotFoundException;

    String generateToken(HttpServletResponse httpServletResponse, User user) throws UserNotFoundException;

    User loadByUserName(HttpServletRequest httpServletRequest) throws UserNotFoundException;

    int countOfUsers();

    User addTopics( String username, ArrayList<String> topics) throws UserNotFoundException;

    User followUsers(String username, ArrayList<String> usersToBeFollowed) throws  UserNotFoundException;

    User unfollowTopics(String username, ArrayList<String> topicsToUnfollow) throws  UserNotFoundException;

    User updateTotalPoints(String username, float totalPoints) throws  UserNotFoundException;
    
    boolean getUserGroups(String accessToken);

    HashSet<String> getFollowers(String username) throws UserNotFoundException;

    HashSet<String> getUsersFollowing(String username) throws UserNotFoundException;

    User acceptTerms(String username);

}