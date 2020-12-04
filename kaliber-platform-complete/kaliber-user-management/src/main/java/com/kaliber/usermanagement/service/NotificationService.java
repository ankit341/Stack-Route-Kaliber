package com.kaliber.usermanagement.service;

import com.kaliber.usermanagement.model.Notification;

import java.util.ArrayList;

public interface NotificationService {


    ArrayList<Notification> getByChallenges(String following);

    Notification createNotification(Notification notif);

    ArrayList<Notification> getNotificationsForUser(String username);


}
