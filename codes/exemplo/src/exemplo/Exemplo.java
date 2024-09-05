/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package exemplo;
import model.bean.Admin;
import model.bean.Notification;
import model.bean.User;
import view.AdminMenu;

/**
 *
 * @author Gustavo
 */
public class Exemplo {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        Admin admin = new Admin("Main Admin");

        User user1 = new User(1, "User 1", "user1@example.com", true);
        User user2 = new User(2, "User 2", "user2@example.com", false);
        User user3 = new User(3, "User 3", "user3@example.com", true);

        admin.addSubscriber(user1);
        admin.addSubscriber(user2);
        admin.addSubscriber(user3);
        
        
        AdminMenu adminMenu = new AdminMenu(admin);
        adminMenu.showMenu();
    }


    
}
