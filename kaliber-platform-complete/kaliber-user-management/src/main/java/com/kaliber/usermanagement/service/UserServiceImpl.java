package com.kaliber.usermanagement.service;

import com.kaliber.usermanagement.exception.UserNotFoundException;
import com.kaliber.usermanagement.model.GitlabGroup;
import com.kaliber.usermanagement.model.User;
import com.kaliber.usermanagement.repository.UserRepository;
import com.kaliber.usermanagement.serviceproxy.SemanticServiceProxy;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    SemanticServiceProxy semanticServiceProxy;

    @Value("${google.base.url}")
    private String baseUrl;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    String redirectUrl;

    @Value("${gitlab.base.url}")
    private String baseUrlGitlab;

    @Value("${spring.security.oauth2.client.registration.gitlab.client-id}")
    String clientIdGitlab;

    @Value("${spring.security.oauth2.client.registration.gitlab.client-secret}")
    String clientSecretGitlab;

    @Value("${spring.security.oauth2.client.registration.gitlab.redirect-uri}")
    String redirectUrlGitlab;

    @Value("${gitlab.group.url}")
    String gitLabGroupUrl;

    @Value("${gitlab.kaliber.group}")
    String gitlabGroup;

    @Value("${gitlab.access.token.url}")
    String gitlabAccessTokenUrl;

    @Value("${gitlab.userdetails.url}")
    String gitlabProfileUrl;

    @Value("${google.access.token.url}")
    String googleAccessTokenUrl;

    @Value("${google.userdetails.url}")
    String googleProfileUrl;

    @Value("${jwt.signing.key}")
    String jwtSigningKey;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public ArrayList<User> getAllUsers(Integer page, Integer limit) throws UserNotFoundException {
        if(userRepository.findAll().isEmpty())
            throw new UserNotFoundException("There is no user in the sysytem");
        return (ArrayList<User>)userRepository.findAll();
    }



    public User getByUserName(String username) throws UserNotFoundException{
        if(userRepository.findByUsername(username).getUsername().isEmpty())
            throw new UserNotFoundException("No user found by this user name");
        return userRepository.findByUsername(username);
    }

    public User saveNewUser(User user) throws DuplicateKeyException {
        User newUser = null;

        if(userRepository.findByUsername(user.getUsername())== null) {
            newUser= userRepository.save(user);
            //Call the semantic service API to create person node in NEO4J
             semanticServiceProxy.createPersonNode(newUser.getUsername());
        }
        else{
            System.out.println("User already exists");
        }
        return newUser;
    }


    public User addTopics(String username, ArrayList<String> topics) {
        User user = userRepository.findByUsername(username);
        HashSet<String> followedTopics = new HashSet<String>();
        if(user.getFollowedTopics()!=null)
            followedTopics = user.getFollowedTopics();
        else
        {
            followedTopics = new HashSet<String>();
        }
        System.out.println("FOLLWOEDTOPICS BEFORE IS " + followedTopics);
        followedTopics.addAll(topics);
        System.out.println("FOLLOWEDTOPICS AFTER IS " + followedTopics);
        user.setFollowedTopics(followedTopics);
        System.out.println("USer data is " + user);
        User user1= userRepository.save(user);
        //Creating relation USER [INTRESTED_IN] Subject.
        if(user1 !=null){
            semanticServiceProxy.userFollowingSubject(username, followedTopics);
        }
        return user1;

    }

    @Override
    public User followUsers(String username, ArrayList<String> usersToBeFollowed) throws UserNotFoundException {
        //get the user data based on username.
        User user = userRepository.findByUsername(username);
        HashSet<String> followedUsers;
        //get the followed users list and update it with the new users
        if(user.getUsersFollowing()!=null)
            followedUsers =user.getUsersFollowing();
        else{
            followedUsers =new HashSet<String>();
        }
        followedUsers.addAll(usersToBeFollowed);
        user.setUsersFollowing(followedUsers);
        usersToBeFollowed.forEach((followedUsername)->
                {
                    User followedUser = userRepository.findByUsername(followedUsername);
                    HashSet<String> followers = followedUser.getFollowers();
                    if(followers==null)
                    {
                        followers = new HashSet<>();
                    }
                    followers.add(username);
                    followedUser.setFollowers(followers);
                    User user1 = userRepository.save(followedUser);
                    //Creating User [] user relationship.
                    if(user1 != null){
                        semanticServiceProxy.userFollowingUser(username, followedUsername);
                    }

                }
        );
        // retrun the new user object
        return userRepository.save(user);
    }

    @Override
    public User unfollowTopics(String username, ArrayList<String> topicsToUnfollow) throws UserNotFoundException {
        User user = userRepository.findByUsername(username);
        HashSet<String> followedTopics = user.getFollowedTopics();
        if(!topicsToUnfollow.isEmpty()) {
            followedTopics.removeAll(topicsToUnfollow);
        }
        return userRepository.save(user);
    }

    public String getAccessToken(String code) throws UserNotFoundException{
        String url = googleAccessTokenUrl;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Accept","application/json");
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(url)
                .queryParam("clientId",this.clientId)
                .queryParam("client_secret",this.clientSecret)
                .queryParam("code",code)
                .queryParam("grant_type","authorization_code")
                .queryParam("redirect_uri",this.redirectUrl);
        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<JSONObject> result = this.restTemplate.postForEntity(uriComponentsBuilder.toUriString(),request,JSONObject.class);
        System.out.println("Access Token" + result.getBody().get("access_token"));
        if(result.getBody().get("access_token").toString().isEmpty())
            throw new UserNotFoundException("There is some problwm with the generation of the access token");
        return (String)result.getBody().get("access_token");
    }

    public User getUserProfile(String accessToken) throws UserNotFoundException{
        String url = googleProfileUrl;
        HttpHeaders httpHeaders =new HttpHeaders();
        httpHeaders.set("Accept","application/json");
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(url)
                .queryParam("access_token",accessToken);
        ResponseEntity<JSONObject> result = this.restTemplate.getForEntity(uriComponentsBuilder.toUriString(),JSONObject.class);
        System.out.println("User Profile is"+ result);
        JSONObject userDetails = result.getBody();
        User user = new User();
        user.setAvatarURL((String)userDetails.get("picture"));
        user.setEmail((String)userDetails.get("email"));
        user.setUsername((String)userDetails.get("email"));
        user.setName((String)userDetails.get("given_name"));
        user.setTotalPoints(200);
        user.setUserRole(User.UserRole.PARTICIPANT);
        this.saveNewUser(user);
        return user;
    }

    @Override
    public String getGitlabAccessToken(String code) throws UserNotFoundException {
        System.out.println("Inside gitlab access code" + code);
        String url = gitlabAccessTokenUrl;
        System.out.println("Inside gitlab access token");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        System.out.println("Inside gitlab access token");
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).queryParam("client_id", this.clientIdGitlab)
                .queryParam("client_secret", this.clientSecretGitlab)
                .queryParam("code", code)
                .queryParam("grant_type", "authorization_code")
                .queryParam("redirect_uri", this.redirectUrlGitlab);
        System.out.println("Inside gitlab access token");
        HttpEntity<String> request = new HttpEntity<>(headers);
        System.out.println("Inside gitlab access token");
        ResponseEntity<JSONObject> result = this.restTemplate.postForEntity(builder.toUriString(),request,JSONObject.class);
        System.out.println("Inside gitlab access token");
        return (String) result.getBody().get("access_token");
    }

    @Override
    public User getGitlabUserProfile(String accessToken) throws UserNotFoundException {
        System.out.println("Inside the gitlab userProfile");
        String url = gitlabProfileUrl;
        HttpHeaders httpHeaders =new HttpHeaders();
        httpHeaders.set("Accept","application/json");
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(url)
                .queryParam("access_token",accessToken);
        ResponseEntity<JSONObject> result = this.restTemplate.getForEntity(uriComponentsBuilder.toUriString(),JSONObject.class);
        System.out.println("User Profile is"+ result);
        JSONObject userDetails = result.getBody();
        System.out.println("userdetails is" + userDetails);
        User user = new User();
        System.out.println("user avatar: " + userDetails.get("avatar_url"));
        System.out.println("user name: " + userDetails.get("username"));
        System.out.println("user : " + userDetails.get("user"));
        user.setAvatarURL((String)userDetails.get("avatar_url"));
        user.setEmail((String)userDetails.get("email"));
        user.setUsername((String)userDetails.get("email"));
        user.setName((String)userDetails.get("username"));
        user.setFollowedTopics(new HashSet<String>());
        if(getUserGroups(accessToken))
            user.setUserRole(User.UserRole.ADMIN);
        else
            user.setUserRole(User.UserRole.PARTICIPANT);
        user.setFollowedTopics(new HashSet<String>());
        System.out.println(user);
        this.saveNewUser(user);
        return user;
    }

    public String generateToken(HttpServletResponse httpServletResponse, User user) throws UserNotFoundException{
        Claims claims = Jwts.claims();
        claims.put("email", user.getEmail());
        claims.put("username",user.getEmail());
        claims.put("userRole" , user.getUserRole());

        String jwtToken = Jwts.builder().setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+ 36_00_000)).signWith(SignatureAlgorithm.HS512,jwtSigningKey)
                .compact();
        httpServletResponse.addHeader("Authorization","Bearer "+jwtToken);
        httpServletResponse.addHeader("Access-Control-Expose-Headers","Authorization");
        if(jwtToken.isEmpty())
            throw new UserNotFoundException("There is a problem with the jwt token generation");
        return jwtToken;
    }

    public User loadByUserName(HttpServletRequest httpServletRequest) throws UserNotFoundException{
        System.out.println("inside load byt username");
        User user =null;
        for(Cookie cookie:httpServletRequest.getCookies()){
            if(cookie.getName().equals("JWT_TOKEN")) {
                try {
                    String username = Jwts.parser().setSigningKey(jwtSigningKey).parseClaimsJws(cookie.getValue()).getBody()
                            .get("username", String.class);

                    if (username != null) {
                        System.out.println("user not null" + username);
                        user = this.userRepository.findByUsername(username);
                        System.out.println("user is .." + user);
                    }
                }catch (ExpiredJwtException e) {
                    return null;
                }
            }
        }
        return user;
    }

    public boolean getUserGroups(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(gitLabGroupUrl).queryParam("access_token",
                accessToken);
        GitlabGroup[] result = this.restTemplate.getForObject(builder.toUriString(), GitlabGroup[].class);
        if (result.length == 0) {
            return false;
        } else {
            for (int i = 0; i < result.length; i++) {
                if (result[i].getName().equals(gitlabGroup)) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public HashSet<String> getFollowers(String username) {
       User user = userRepository.findByUsername(username);
       HashSet<String> followers = user.getFollowers();
        System.out.println("FOLOWERS ARE "+ followers);
       return followers;
    }

    @Override
    public HashSet<String> getUsersFollowing(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username).getUsersFollowing();
    }

    @Override
    public int countOfUsers() {
        return userRepository.findAll().size();
    }

    @Override
    public User updateTotalPoints(String username, float pointsToBeUpdated) throws UserNotFoundException {
        User user = userRepository.findByUsername(username);
        float points = user.getTotalPoints();
        points += pointsToBeUpdated;
        user.setTotalPoints(points);
        return userRepository.save(user);
    }
    @Override
    public User acceptTerms(String username) {
        User user = userRepository.findByUsername(username);
        user.setTermsaccepted(true);
        User savedUser = userRepository.save(user);
        return savedUser;
    }
}