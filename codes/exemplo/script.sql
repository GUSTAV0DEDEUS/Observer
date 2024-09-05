CREATE DATABASE exemplo;

-- Criando a tabela Admin
CREATE TABLE exemplo.Admin (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Inserindo um único Admin no sistema
INSERT INTO exemplo.Admin (id, name) VALUES (1, 'Main Admin');

-- Criando a tabela User com o atributo `receive_notifications`
CREATE TABLE exemplo.User (
    id INT PRIMARY KEY AUTO_INCREMENT,
    admin_id INT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    receive_notifications BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (admin_id) REFERENCES exemplo.Admin(id)
);

-- Criando a tabela Notification
CREATE TABLE exemplo.Notification (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criando a tabela relacional entre User e Notification
CREATE TABLE exemplo.UserNotification (
    user_id INT,
    notification_id INT,
    PRIMARY KEY (user_id, notification_id),
    FOREIGN KEY (user_id) REFERENCES exemplo.User(id),
    FOREIGN KEY (notification_id) REFERENCES exemplo.Notification(id)
);
