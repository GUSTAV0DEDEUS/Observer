package model.bean;
import utils.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class User implements Subscriber {
    private final int id;
    private final String name;
    private final String email;
    private boolean receiveNotifications;
    private final List<Notification> notifications; 

    public User(int id, String name, String email, boolean receiveNotifications) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.receiveNotifications = receiveNotifications;
        this.notifications = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public void setReceiveNotifications(boolean receiveNotifications) {
        this.receiveNotifications = receiveNotifications;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    @Override
    public void update(Notification notification) {
        notifications.add(notification);
        System.out.println("Nova notificação para " + name + ": " + notification.getTitle() + " - " + notification.getMessage());
    }

    @Override
    public boolean wantsNotifications() {
        return receiveNotifications;
    }
}
