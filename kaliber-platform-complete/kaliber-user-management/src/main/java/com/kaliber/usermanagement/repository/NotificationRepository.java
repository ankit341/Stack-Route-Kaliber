package com.kaliber.usermanagement.repository;

import com.kaliber.usermanagement.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {

    ArrayList<Notification> findByChallengedTo(String following);

    ArrayList<Notification> findByChallengedBy(String following);

}
