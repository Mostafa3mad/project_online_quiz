## 🌟 **Quiz Online** 🌟

### **📄 Description**
🎓 A Java-based application for creating and managing online quizzes. This project uses the Swing library for the graphical user interface and follows design patterns like:

**✨ Builder, Command, Factory, Singleton, Observer ✨**

---

### **🚀 Features**
✅ User roles: Admin, Teacher, and Student.  
✅ Add, update, delete, and view quiz questions.  
✅ Track student scores.  
✅ User-friendly login interface.  
✅ Persistent database connection using MySQL.

---

### **🛠️ Technologies Used**
| **Technology**   | **Purpose**                     |
|-------------------|---------------------------------|
| 🖥️ **Java**       | Core programming language      |
| 🎨 **Swing**       | Graphical User Interface (GUI) |
| 🛢️ **MySQL**      | Database management            |
| 🧰 **Maven**      | Build and dependency management|
| 🧩 **Design Patterns** | Builder, Factory, Singleton, Command, Observer |

---

### **🖥️ Setup Instructions**
1. Clone the repository:  
   ```bash
   git clone https://github.com/Mostafa3mad/project_online_quiz.git
   ```
2. Navigate to the project directory:  
   ```bash
   cd project_online_quiz
   ```
3. Import the project into **IntelliJ IDEA** or your preferred IDE.  
4. Configure the database connection in `DatabaseConnection.java`:  
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/quiz_system";
   private static final String USER = "your_username";
   private static final String PASSWORD = "your_password";
   ```
5. Build and run the project:  
   ```bash
   mvn clean install
   mvn exec:java
   ```

---

### **📸 Screenshots**
| **Login Page**      | **Admin Dashboard**       |
|---------------------|--------------------------|
| ![login](https://github.com/user-attachments/assets/28614004-bb3f-43ab-a56a-f820d9b8e61e) | ![admin_panal](https://github.com/user-attachments/assets/d9bea48f-b6a8-4b9b-b46d-333909783aac) |
| **Quiz Interface**  | **Teacher Panel**        |
| ![quiz_panal](https://github.com/user-attachments/assets/0d6eb34a-39dd-4d16-aefd-60fd4f1bc781) | ![teacher_panal](https://github.com/user-attachments/assets/f8e9d184-3554-4a8e-910b-7762b06d4f1e) |

---

### **📂 Project Structure**
```plaintext
src/
├── main/
│   ├── java/
│   │   ├── com.mycompany.quiz_online1/
│   │   │   ├── Main.java
│   │   │   ├── builder/
│   │   │   │   └── CustomFrameBuilder.java
│   │   │   ├── command/
│   │   │   │   ├── AddQuestionCommand.java
│   │   │   │   ├── Command.java
│   │   │   │   └── CommandInvoker.java
│   │   │   ├── factory/
│   │   │   │   └── FrameFactory.java
│   │   │   ├── models/
│   │   │   │   ├── Question.java
│   │   │   │   └── QuestionBuilder.java
│   │   │   ├── observer/
│   │   │   │   ├── Observer.java
│   │   │   │   ├── Subject.java
│   │   │   │   ├── TeacherFrame.java
│   │   │   │   └── QuizFrame.java
│   │   │   ├── singleton/
│   │   │   │   └── DatabaseConnection.java
│   │   │   ├── template/
│   │   │   │   ├── LoginFrame.java
│   │   │   │   └── AdminFrame.java
```

---

### **🤝 Contributing**
Feel free to fork this repository, create a feature branch, and submit a pull request with your improvements.

---

### **📜 License**
This project is licensed under the **MIT License**. See the `LICENSE` file for details.

---

### **📞 Contact**
- **Author**: Mostafa Emad  
- 📧 **Email**: [mostafa.3mad.salah@gmail.com](mailto:mostafa.3mad.salah@gmail.com)  
- 🐙 **GitHub**: [Mostafa3mad](https://github.com/Mostafa3mad)  
