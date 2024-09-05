/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.bean;

/**
 *
 * @author Gustavo
 */
import utils.Publisher;
import utils.Subscriber;

import java.util.HashMap;
import java.util.Map;

public class Admin implements Publisher {

  private final String name;
    private final Map<Integer, User> users; 

    public Admin(String name) {
        this.name = name;
        this.users = new HashMap<>();
    }

    @Override
    public void addSubscriber(Subscriber subscriber) {
        User user = (User) subscriber;
        users.put(user.getId(), user);
    }

    @Override
    public void removeSubscriber(Subscriber subscriber) {
        User user = (User) subscriber;
        users.remove(user.getId());
    }

    @Override
    public void updateSubscriber(Subscriber subscriber) {
        User user = (User) subscriber;
        User existingUser = users.get(user.getId());
        if (existingUser != null) {
            existingUser.setReceiveNotifications(user.wantsNotifications());
        }
    }

    @Override
    public void notifySubscribers(Notification notification) {
        for (User user : users.values()) {
            if (user.wantsNotifications()) {
                user.update(notification);
            }
        }
    }

    public void publishNotification(String title, String message) {
        Notification notification = new Notification(title, message);
        notifySubscribers(notification);
    }

    public void changeUserNotificationPreference(int userId, boolean receiveNotifications) {
        User user = users.get(userId);
        if (user != null) {
            user.setReceiveNotifications(receiveNotifications);
            updateSubscriber(user); 
        }
    }

    public User findUserById(int userId) {
        return users.get(userId);
    }

    public void showAllUsers() {
        for (User user : users.values()) {
            System.out.println("ID: " + user.getId() + ", Nome: " + user.getName() + ", Receber Notificações: " + user.wantsNotifications());
        }
    }
}