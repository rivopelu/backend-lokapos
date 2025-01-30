package com.lokapos.services;


import com.google.firebase.messaging.FirebaseMessagingException;
import com.lokapos.entities.Notification;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface NotificationService {

    void sendMessageToToken(Notification request) throws ExecutionException, InterruptedException;

    void pushNotificationSingle(Notification notification) throws ExecutionException, InterruptedException;

    void pushNotificationList(List<Notification> notificationList);


}

