# 🐛 IssueTracker

A command-line Issue Tracking System built with **Java** and **MySQL**. Allows you to add, view, search, update and delete issues directly from the terminal.

---

## 🚀 Features

- ➕ **Add Issues** — Create issues with title, description and priority
- 📋 **View All Issues** — List all issues sorted by latest first
- 🔍 **Search by Priority** — Filter issues by Low / Medium / High
- 🔄 **Update Status** — Change status to Open / In Progress / Closed
- 🗑️ **Delete Issues** — Remove issues by ID
- 🕒 **Timestamps** — Every issue stores its created date & time
- 📊 **Total Count** — Shows total issue count on the main menu
- ✅ **DB Connection Test** — Validates DB connection on startup

---

## 🛠️ Tech Stack

| Technology | Purpose |
|------------|---------|
| Java | Core language |
| MySQL | Database |
| JDBC | Database connectivity |
| mysql-connector-j | MySQL Java driver |

---

## 📁 Project Structure

```
IssueTracker/
│
├── Issue.java          # Issue model with getters, setters, toString
├── IssueDAO.java       # Database operations (CRUD + search)
├── DBConnection.java   # MySQL connection setup
├── Main.java           # Main menu and user interaction
└── README.md
```

---

## ⚙️ Setup & Installation

### Prerequisites
- Java JDK 11 or above
- MySQL Server
- mysql-connector-j JAR file

### 1. Clone the repository
```bash
git clone https://github.com/ChinmayaK11/IssueTracker.git
cd IssueTracker
```

### 2. Create the MySQL database
```sql
CREATE DATABASE issue_tracker_db;

USE issue_tracker_db;

CREATE TABLE issues (
    issue_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    priority VARCHAR(50),
    status VARCHAR(50),
    created_at VARCHAR(100)
);
```

### 3. Configure DB credentials
Edit `DBConnection.java` or set environment variables:
```bash
export DB_URL="jdbc:mysql://localhost:3306/issue_tracker_db"
export DB_USER="root"
export DB_PASSWORD="yourpassword"
```

### 4. Compile and run
```bash
javac -cp .:mysql-connector-j-9.6.0.jar *.java
java -cp .:mysql-connector-j-9.6.0.jar Main
```

> **Windows users:** Replace `:` with `;` in the classpath

---

## 📸 Sample Output

```
===== ISSUE TRACKING SYSTEM =====
Total Issues: 3
----------------------------------
1. Add Issue
2. View All Issues
3. Search Issues by Priority
4. Update Issue Status
5. Delete Issue
6. Exit
Enter your choice:
```

---

## 👨‍💻 Author

**Chinmaya Kagolli**
- GitHub: [@ChinmayaK11](https://github.com/ChinmayaK11)

---

> 💡 *Simple, clean issue tracking from your terminal!*
