/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Gustavo
 */
// Classe Admin
public class Admin {
    private final String name;

    public Admin(String name) {
        this.name = name;
    }

    // Método para criar e enviar notificações para um usuário específico
    public Notification createNotification(String title, String message) {
        return  new Notification(title, message);  
    }
}
