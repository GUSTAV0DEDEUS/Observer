/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package utils;

import model.bean.Notification;

/**
 *
 * @author Gustavo
 */


public interface Publisher {
    void addSubscriber(Subscriber subscriber);
    void removeSubscriber(Subscriber subscriber);
    void updateSubscriber(Subscriber subscriber);
    void notifySubscribers(Notification notification);
}


