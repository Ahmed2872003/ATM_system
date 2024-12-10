
CREATE DATABASE IF NOT EXISTS ATM_system;
USE ATM_system;



CREATE TABLE admin (
    id int PRIMARY KEY auto_increment,
    password VARCHAR(255) NOT NULL CHECK (LENGTH(password) >= 8)
);

INSERT INTO admin (password)
VALUES	("UjM74JkGFHbhQ598lJKk/A==");

CREATE TABLE user (
    id int PRIMARY KEY auto_increment,
    name VARCHAR(255) NOT NULL
);


CREATE TABLE account (
    id int PRIMARY KEY auto_increment,
    card_number CHAR(16) NOT NULL UNIQUE,
    pin VARCHAR(255) NOT NULL,
    balance INT CHECK (balance >= 0 AND balance % 50 = 0),
    shared BOOLEAN NOT NULL
);



CREATE TABLE transaction_type (
    id int PRIMARY KEY auto_increment,
    type VARCHAR(255) NOT NULL
);

INSERT INTO transaction_type (id, type)
VALUES (1, "Deposit"),
		(2, "Withdrawal");
        

CREATE TABLE transaction_log (
    id int PRIMARY KEY auto_increment,
    user_id INT NOT NULL,
    account_id INT NOT NULL,
    transaction_type_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (account_id) REFERENCES account(id),
    FOREIGN KEY (transaction_type_id) REFERENCES transaction_type(id)
);


CREATE TABLE user_account (
    user_id INT NOT NULL,
    account_id INT NOT NULL,
    PRIMARY KEY (user_id, account_id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (account_id) REFERENCES account(id)
);


DELIMITER $$

CREATE TRIGGER validate_card_number
BEFORE INSERT ON account
FOR EACH ROW
BEGIN
    -- Check if card_number is exactly 16 digits
    IF NOT NEW.card_number REGEXP '^[0-9]{16}$' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid card number, must be 16 digits';
    END IF;
END $$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER check_balance_on_insert
BEFORE INSERT ON account
FOR EACH ROW
BEGIN
    IF NEW.balance < 50 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Balance must be initially 50.';
    END IF;
END $$

DELIMITER ;
