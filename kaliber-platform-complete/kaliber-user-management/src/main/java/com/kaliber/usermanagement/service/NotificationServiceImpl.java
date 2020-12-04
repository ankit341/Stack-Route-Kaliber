package com.kaliber.usermanagement.service;

import com.kaliber.usermanagement.exception.UserNotFoundException;
import com.kaliber.usermanagement.model.Notification;

import com.kaliber.usermanagement.repository.NotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    public List<Notification> getAllNotifications() throws UserNotFoundException{
        if (notificationRepository.findAll().isEmpty()){
            throw new UserNotFoundException("There are no following list of the user");
        }
        return (List<Notification> ) notificationRepository.findAll();

    }

    @Override
    public Notification createNotification(Notification notif){
        Notification notification = notificationRepository.save(notif);
        return notification;
    }

    @Override
    public ArrayList<Notification> getNotificationsForUser(String username) {

        return notificationRepository.findByChallengedTo(username);

    }

    @Override
    public ArrayList<Notification> getByChallenges(String following) {
        ArrayList<Notification> notifications = notificationRepository.findByChallengedBy(following);
        ArrayList<Notification> notifications1 = notificationRepository.findByChallengedTo(following);
        notifications.addAll(notifications1);
        System.out.println("Notifications are " + notifications);
        return notifications;
    }



}
