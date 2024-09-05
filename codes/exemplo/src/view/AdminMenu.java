/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author Gustavo
 */
import java.util.Scanner;
import model.bean.Admin;
import model.bean.User;

public class AdminMenu {

    private final Admin admin;

    public AdminMenu(Admin admin) {
        this.admin = admin;
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("\n--- Menu Admin ---");
            System.out.println("1. Listar Usuários");
            System.out.println("2. Alterar Preferência de Notificações de um Usuário");
            System.out.println("3. Enviar Notificação");
            System.out.println("4. Visualizar Notificação de um Usuário");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    admin.showAllUsers();
                    break;

                case 2:
                    System.out.print("Digite o ID do usuário: ");
                    int userId = scanner.nextInt();
                    System.out.print("Receber notificações? (true/false): ");
                    boolean receiveNotifications = scanner.nextBoolean();
                    admin.changeUserNotificationPreference(userId, receiveNotifications);
                    break;

                case 3:
                    scanner.nextLine();  // Consumir newline
                    System.out.print("Digite o título da notificação: ");
                    String title = scanner.nextLine();
                    System.out.print("Digite a mensagem da notificação: ");
                    String message = scanner.nextLine();
                    admin.publishNotification(title, message);
                    break;

                case 4:
                    System.out.print("Digite o ID do usuário: ");
                    int userIdForView = scanner.nextInt();
                    User user = admin.findUserById(userIdForView);
                    if (user != null) {
                        UserNotificationView userView = new UserNotificationView(user);
                        userView.showNotifications();
                    } else {
                        System.out.println("Usuário com ID " + userIdForView + " não encontrado.");
                    }
                    break;

                case 5:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida, tente novamente.");
            }

        } while (option != 5);

        scanner.close();
    }
}
