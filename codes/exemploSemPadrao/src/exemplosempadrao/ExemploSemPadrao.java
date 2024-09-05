/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package exemplosempadrao;

import model.Admin;
import model.Notification;
import model.User;

/**
 *
 * @author Gustavo
 */
public class ExemploSemPadrao {

    /**
     * @param args the command line arguments
     */
   public static void main(String[] args) {
        // Criação de usuários
        User user1 = new User(1, "João", "joao@email.com");
        User user2 = new User(2, "Maria", "maria@email.com");
        Admin admin = new Admin("Gustavo");
        
        // Admin criando notificações
        Notification notification1 = admin.createNotification("Atualização do Sistema", "Uma nova versão está disponível!");
        Notification notification2 = admin.createNotification("Manutenção Programada", "O sistema estará fora do ar amanhã das 2h às 4h.");

        // Usuários verificando notificações antes de receber
        user1.checkNotifications();
        user2.checkNotifications();

        // Admin enviando notificações diretamente para os usuários
        user1.addNotification(notification1);
        user2.addNotification(notification2);

        // Usuários verificando notificações após receb-las
        user1.checkNotifications();
        user2.checkNotifications();



    }
    
}
