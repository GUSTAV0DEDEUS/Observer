/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Gustavo
 */
import java.util.ArrayList;
import java.util.List;

public class User {
    private final int id;
    private final String name;
    private final String email;
    private final List<Notification> notifications;  // Lista de notificações

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.notifications = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    // Método para adicionar uma notificação ao usuário
    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    // Método para o usuário verificar as notificações
    public void checkNotifications() {
        if (notifications.isEmpty()) {
            System.out.println(name + ", você não tem novas notificações.");
        } else {
            System.out.println(name + ", você tem " + notifications.size() + " novas notificações:");
            for (Notification notification : notifications) {
                System.out.println("- " + notification.getTitle() + ": " + notification.getMessage());
            }
            // Limpa as notificações após visualizá-las
            notifications.clear();
        }
    }
}