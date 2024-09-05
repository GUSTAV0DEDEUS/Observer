/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author Gustavo
 */
import java.util.List;
import model.bean.Notification;
import model.bean.User;

public class UserNotificationView {

    private final User user;

    public UserNotificationView(User user) {
        this.user = user;
    }

    public void showNotifications() {
        System.out.println("\n--- Notificações para " + user.getName() + " ---");
        List<Notification> notifications = user.getNotifications();

        if (notifications.isEmpty()) {
            System.out.println("Você não tem notificações.");
        } else {
            for (int i = 0; i < notifications.size(); i++) {
                Notification notification = notifications.get(i);
                System.out.println((i + 1) + ". " + notification.getTitle() + ": " + notification.getMessage());
            }
        }
    }
}
