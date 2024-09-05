# Implementação Sem o Padrão Observer

No exemplo abaixo, foi implementado um sistema de notificações para uma newsletter, onde os **usuários** recebem as informações desejadas por meio de notificações criadas por um **administrador**. O foco é a comunicação de um administrador para vários usuários, uma relação de 1 para muitos. Esse modelo inicial, no entanto, não implementa o padrão Observer, e o processo de notificação é feito manualmente.

Vamos explorar mais a fundo a estrutura e os comportamentos implementados.

### Admin
A classe `Admin` é responsável por criar as notificações. A instância de `Admin` pode gerar notificações por meio do método `createNotification`, que aceita um título e uma mensagem e retorna uma nova instância da classe `Notification`. Nesse exemplo, o `Admin` não tem controle sobre a entrega das notificações; ele apenas cria.

```java
public class Admin {
    private final String name;

    public Admin(String name) {
        this.name = name;
    }

    public Notification createNotification(String title, String message) {
        return new Notification(title, message);  
    }
}
```

### Notification
A classe `Notification` define o formato de uma notificação. Ela contém os atributos `title` e `message`, além dos métodos getters que permitem recuperar essas informações.

```java
public class Notification {
    private final String title;
    private final String message;

    public Notification(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}
```

### User
A classe `User` é o core da nossa aplicação. Cada instância de `User` possui um ID, nome, email e uma lista de notificações. O usuário pode adicionar notificações à sua lista usando o método `addNotification`. Além disso, o método `checkNotifications` permite que o usuário verifique suas notificações e as exiba na tela, se houver alguma disponível.

```java
import java.util.ArrayList;
import java.util.List;

public class User {
    private final int id;
    private final String name;
    private final String email;
    private final List<Notification> notifications;   
    
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

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    public void checkNotifications() {
        if (notifications.isEmpty()) {
            System.out.println(name + ", você não tem novas notificações.");
        } else {
            System.out.println(name + ", você tem " + notifications.size() + " novas notificações:");
            for (Notification notification : notifications) {
                System.out.println("- " + notification.getTitle() + ": " + notification.getMessage());
            }
        }
    }
}
```

### Main
A classe `Main` demonstra a interação entre `Admin` e `User`. O administrador cria as notificações e, por fim, essas notificações são distribuidas para os usuários selecionados usando o método `addNotification`.

```java
public class ExemploSemPadrao {
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

        // Usuários verificando notificações após recebê-las
        user1.checkNotifications();
        user2.checkNotifications();
    }
}
```

![video](https://github.com/GUSTAV0DEDEUS/Observer/blob/main/videos/sem-padrao.gif)

O **usuário** precisa explicitamente verificar se tem novas notificações ao chamar o método `checkNotifications`. Ou seja, o sistema não notifica os usuários, o que pode não ser eficiente para um sistema real.

Esses problemas evidenciam a falta de **desacoplamento** entre quem gera as notificações e quem as recebe. A dependência direta entre o `Admin` e os `Users` torna o código menos flexível e mais difícil de manter.

# Implementação com o Padrão Observer

Agora vamos implementar o padrão Observer para melhorar o sistema de notificações da newsletter. Com o padrão, o **admin** atuará como o **publisher**, e os **usuários** como **subscribers**. Isso tornará o sistema mais flexível e permitirá que os usuários se inscrevam ou cancelem a inscrição para receber notificações de forma automática, sem a necessidade de verificação manual.

## Interfaces

### Publisher
A interface `Publisher` define os métodos que serão usados para adicionar, remover e notificar os assinantes. Também possui um método para atualizar as preferências de notificações de um usuário.

```java
public interface Publisher {
    void addSubscriber(Subscriber subscriber);
    void removeSubscriber(Subscriber subscriber);
    void updateSubscriber(Subscriber subscriber);
    void notifySubscribers(Notification notification);
}
```

### Subscriber
A interface `Subscriber` define os métodos que um assinante precisa implementar, incluindo a atualização das notificações e a verificação de interesse nas notificações.

```java
public interface Subscriber {
    void update(Notification notification);
    boolean wantsNotifications();
}
```

## Modelos

### Admin
A classe `Admin` implementa a interface `Publisher` e é responsável por gerenciar os assinantes. Ela mantém um mapa de usuários e os notifica quando novas notificações são publicadas.

```java
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
```

### User
A classe `User` implementa a interface `Subscriber`. Ela possui uma lista de notificações e pode ser configurada para receber ou não notificações.

```java
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
```

### Notification
A classe `Notification` permanece a mesma, servindo como o modelo para as notificações que os usuários recebem.

```java
public class Notification {
    private final String title;
    private final String message;

    public Notification(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}
```

## Main

### Exemplo Sem Menu
Neste exemplo, temos um fluxo simples onde o `Admin` gerencia as inscrições e envia notificações.

```java
import model.bean.Admin;
import model.bean.Notification;
import model.bean.User;

public class Exemplo {
    public static void main(String[] args) {
        Admin admin = new Admin("Main Admin");

        User user1 = new User(1, "User 1", "user1@example.com", true);
        User user2 = new User(2, "User 2", "user2@example.com", false);
        User user3 = new User(3, "User 3", "user3@example.com", true);

        admin.addSubscriber(user1);
        admin.addSubscriber(user2);
        admin.addSubscriber(user3);
        
        admin.removeSubscriber(user3);
        
        admin.notifySubscribers(new Notification("Oi", "Tudo bem"));
    }
}
```

![video](https://github.com/GUSTAV0DEDEUS/Observer/blob/main/videos/padrao-sem-menu.gif)

### Exemplo com Menu
Aqui, foi adicionado um menu para permitir que o administrador altere as preferências de notificações, envie notificações e visualize as notificações de usuários específicos.

```java
import model.bean.Admin;
import model.bean.Notification;
import model.bean.User;
import view.AdminMenu;

public class Exemplo {
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
```

![video](https://github.com/GUSTAV0DEDEUS/Observer/blob/main/videos/padrao-menu.gif)

### Menu Admin
Essa classe implementa o menu de administração, permitindo que o administrador gerencie os usuários e as notificações.

```java
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
            System.out.println("4. Visualizar Notificações de um Usuário");
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
```

### Visualização de Notificações do Usuário
Essa classe permite visualizar as notificações recebidas por um usuário específico.

```java
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
        List<

Notification> notifications = user.getNotifications();

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
```
