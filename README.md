# 📦 Product Management System

## 📝 Overview

This project consists of a mobile app and a Java desktop app designed to streamline product management and sales tracking. The mobile app allows users to log in and scan product barcodes, with data seamlessly synchronized to the desktop application. The desktop app provides functionalities for managing products, creating bills, generating PDFs, and tracking sales records by day, month, and year.

## ✨ Features

- **Mobile App**:
  - User login
  - Product barcode scanning
  - Data synchronization with the desktop app

- **Desktop App**:
  - Product management
  - Add Product
  - Edit/Delete Product
  - Bill creation
  - PDF generation
  - Sales tracking by day, month, and year

## 🛠️ Technologies Used

- **Mobile App**: Java
- **Desktop App**: Java

## 🚀 Installation

### Prerequisites

- **Mobile App**: 
  - A device/emulator to run the app

- **Desktop App**: 
  - JDK (Java Development Kit)
  - IDE (e.g., IntelliJ IDEA, Eclipse) or Command Line for Java

### Cloning the Repository

1. **Clone the repository**:

    ```bash
    git clone https://github.com/navin280123/Online-Bill-Generator.git
    ```

2. **Navigate to the project directory**:

    ```bash
    cd product-management-system
    ```
3. **Add google-services.json File for Firebase Connection to Your particular Account**
   -In firebase use
   -Firebase Authentication by Email and Password.
   -Firebase Realtime Storage.

### Setting Up the Mobile App

1. For mobile Application Move to this Repository ----------> (https://github.com/navin280123/Online-Bill-Generator-App)

### Setting Up the Desktop App

1. **Navigate to the desktop app directory**:

    ```bash
    cd desktop-app
    ```

2. **Compile and run the Java application** (using an IDE or command line):

    ```bash
    javac Login.java
    java Login
    ```

## 📋 Usage

- **Mobile App**: Log in and scan product barcodes. The data will be synchronized to the desktop application.
- **Desktop App**: Manage products, create bills, generate PDFs, and track sales records.

## 📸 Screenshots
Following are the Screenshot:-
**1.Login.java**

![image](https://github.com/user-attachments/assets/f0c96c2c-bdfd-4c68-961c-b8f213be1315)

*Feature*
1. Login Feature.
2. Signup Feature.

**2.mainPage.java**

*Product Panel*
![image](https://github.com/user-attachments/assets/5fabfd90-37c4-4be3-82c5-af7d2dce506d)

*Bill Panel*

![image](https://github.com/user-attachments/assets/cb587ab2-4bfd-47fe-b6b6-eb2ff519724e)

*Report Panel*
![image](https://github.com/user-attachments/assets/26f6a716-7ea4-4bab-aa9d-8012c089bf82)


*Feature*
1. User DashBoard to Access all the Option with ease.

**3. Add Product**

![image](https://github.com/user-attachments/assets/7c3b6aaa-7bd1-4beb-b48c-08d6b4d8045a)

*Features*

1. Add The Product which will be Stored in the firebase realtime database.

**4. Create Bill **

![image](https://github.com/user-attachments/assets/fd10162e-363e-40dd-acbd-336a1b79a6f1)


*Features*

1. This Panel is used to Create Bill with user details and this panel is in sync with app if the app scan any bar code which is present in the product list of this particular store the amount will be automatically calulated. User has also option to add product from drop down menu.
   

**PDF Viewer**

![image](https://github.com/user-attachments/assets/9f244d4d-aa83-4b82-a5be-b395a7ea7568)


*Features*

1. User can see the history of the say in pdf formate.



## 🤝 Contributing

1. **Fork the repository**
2. **Create a new branch**:

    ```bash
    git checkout -b feature/your-feature-name
    ```

3. **Make your changes**
4. **Commit your changes**:

    ```bash
    git commit -m 'Add some feature'
    ```

5. **Push to the branch**:

    ```bash
    git push origin feature/your-feature-name
    ```

6. **Open a pull request**

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Thanks to everyone who contributed to this project.
