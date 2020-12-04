package com.kaliber.usermanagement.controller;

import com.kaliber.usermanagement.exception.UserNotFoundException;
import com.kaliber.usermanagement.model.Notification;
import com.kaliber.usermanagement.model.User;
import com.kaliber.usermanagement.service.NotificationService;

import com.kaliber.usermanagement.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/notifications")
    public ResponseEntity<?> saveNotification(@RequestBody Notification notification)  {

        Notification notification1 = null;
        HashMap<String, Object> response = new HashMap<>();
        try{
            notification1 = notificationService.createNotification(notification);
            response.put("message", "Successfully posted notification");
            response.put("result", notification1);
            response.put("StatusCode", HttpStatus.OK);
        }catch (Exception e){
            response.put("message", e.getMessage());
            response.put("result", notification1);
            response.put("StatusCode", HttpStatus.OK);
            return new ResponseEntity<>( response, HttpStatus.OK);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
//    public ResponseEntity<?> createUser(@RequestBody User user) throws DuplicateKeyException {
//        try{
//            userService.saveNewUser(user);
//            Map<String, Object> response = new HashMap<String, Object>();
//            response.put("error", false);
//            response.put("message", "Successfully posted user details");
//            response.put("result", user);
//            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
//        }catch (DuplicateKeyException | UserNotFoundException e){
//            logger.error(e.getMessage());
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//
//    }


    @GetMapping("/notifications")
    public ResponseEntity<?> getNotificationForUser(HttpServletRequest httpServletRequest) throws UserNotFoundException {
        User user = userService.loadByUserName(httpServletRequest) ;
        String username = user.getUsername();
        ArrayList<Notification> notification = null; //notificationService.getNotificationsForUser(username);
//        Notification notification1 = null;
        HashMap<String, Object> response = new HashMap<>();
        try{
            notification = notificationService.getNotificationsForUser(username);
            response.put("message", "Successfully posted notification");
            response.put("result", notification);
            response.put("StatusCode", HttpStatus.OK);
        }catch (Exception e){
            response.put("message", e.getMessage());
            response.put("result", notification);
            response.put("StatusCode", HttpStatus.OK);
            return new ResponseEntity<>( response, HttpStatus.OK);
        }
        System.out.println("NOTIFICATIONSSSSSSSSSSSS IS SSSSSSSSSSSSSSSSSSSSSSSSS " + notification);

        return new ResponseEntity<>(response, HttpStatus.OK);


    }

    @GetMapping("/notifications/following")
    public ResponseEntity<?> getAllNotifications(HttpServletRequest httpServletRequest) throws UserNotFoundException {
        //Get the logged in username
        User user = userService.loadByUserName(httpServletRequest);
        //Get the following list of a user
        HashSet<String> userFollowingArray = user.getUsersFollowing();
        ArrayList<Notification> notifications = new ArrayList<>();
        System.out.println("NOtifications in controller are " + notifications);
        //Store the notifications in ArrayList and return in proper ResponseEntity
        HashMap<String, Object> response = new HashMap<>();
        try {
            for (String following : userFollowingArray) {
                notifications.addAll(notificationService.getByChallenges(following));
            }
            response.put("message", "Successfully posted notification");
            response.put("result", notifications);
            response.put("StatusCode", HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("result", notifications);
            response.put("StatusCode", HttpStatus.OK);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

        // Loop over the following list and get all notifications if  challengedBy or challengedTo contains the username
        //Return them
//
//        Map<String, Object> mapOfNotifications;
//        List<Notification> notificationArrayList = new ArrayList<Notification>();
//        notificationArrayList = notificationService.getAllNotifications();
//        System.out.println("User following array-----" + userFollowingArray);
//        for(int i=0;i<userFollowingArray.size();i++){
//
//            for(int j=0; j<notificationArrayList.size();j++) {
//                if(notificationArrayList[j]==userFollowingArray[i]){}
//            }
//        }
//        return new ResponseEntity<Map<String, Object>>(reponse, HttpStatus.OK);
//    }

//    @PostMapping("/notifications")
//    public ResponseEntity<?> function() {
//
//    }
//
//    @PatchMapping("/notifications")
//    public ResponseEntity<?> functionPatch(){
//
//    }
}

