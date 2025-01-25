-- Admin表
CREATE TABLE admin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50),
    name VARCHAR(255),
    new_password VARCHAR(255),
    avatar VARCHAR(255)
);

-- Article表
CREATE TABLE article (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    img VARCHAR(255),
    description TEXT,
    content TEXT,
    time VARCHAR(50)
);

-- Dept表
CREATE TABLE dept (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Employee表
CREATE TABLE employee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50),
    name VARCHAR(255),
    sex VARCHAR(10),
    no VARCHAR(50),
    age INT,
    description TEXT,
    dept_id INT,
    dept_name VARCHAR(255),
    avatar VARCHAR(255)
);