package com.lokapos.services.impl;

import com.google.firebase.messaging.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lokapos.entities.Notification;
import com.lokapos.services.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Override
    public void sendMessageToToken(Notification request) throws ExecutionException, InterruptedException {
        if (request.getToken() == null) {
            return;
        }
        Message message = getPreconfiguredMessageToToken(request);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(message);
        String response = sendAndGetResponse(message);
        logger.info("Sent message to token. Device token: {}, {} msg {}", request.getToken(), response, jsonOutput);
    }

    private void sendMessageToMultipleTokens(List<Notification> request) {

        List<Message> message = new ArrayList<>();
        for (Notification notificationRequest : request) {
            Message addMessage = getPreconfiguredMessageToToken(notificationRequest);
            message.add(addMessage);
        }

        if (!message.isEmpty()) {
            FirebaseMessaging.getInstance().sendEachAsync(message);
        }

    }


    @Override
    public void pushNotificationSingle(Notification notification) throws ExecutionException, InterruptedException {

        sendMessageToToken(notification);
    }

    @Override
    public void pushNotificationList(List<Notification> notificationList) {

        if (notificationList.isEmpty()) {
            System.out.println("OKE");
        } else {
            sendMessageToMultipleTokens(notificationList);

        }

    }


    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }

    private Message getPreconfiguredMessageToToken(Notification request) {
        return getPreconfiguredMessageBuilder(request).setToken(request.getToken())
                .build();
    }

    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder()
                        .setTag(topic).build()).build();
    }

    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
    }

    private Message.Builder getPreconfiguredMessageBuilder(Notification request) {
        AndroidConfig androidConfig = getAndroidConfig(request.getType().name());
        ApnsConfig apnsConfig = getApnsConfig(request.getType().name());
        com.google.firebase.messaging.Notification notification = com.google.firebase.messaging.Notification.builder()
                .setTitle(request.getTitle())
                .setBody(request.getBody())
                .build();
        return Message.builder()
                .setApnsConfig(apnsConfig).setAndroidConfig(androidConfig).setNotification(notification);
    }
}
