package Shyrick;

import Final_Project.Users.DAO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserController implements DAO {

    private   int  id = 0;
    private List<User> usersList = new ArrayList<>();
    private User tempUser;

    File file = new File("UsersList.txt");

    @Override
    public List load() {
        readDB();
        return usersList;
    }

    @Override
    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (int i = 0; i < usersList.size(); i++) {
                writer.write(usersList.get(i).getId() + " "
                        + usersList.get(i).getLogin() + " "
                        + usersList.get(i).getFirstName() + " "
                        +  usersList.get(i).getLastName() + " "
                        + usersList.get(i).isAdmin);
                if (i < usersList.size() - 1)  writer.write( "\n");
                writer.flush();
            }
            } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void readDB (){

        int i = 0;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {

                byte id = scanner.nextByte();
                String login = scanner.next();
                String firstName = scanner.next();
                String lastName = scanner.next();
                boolean isAdmin = scanner.nextBoolean();
                usersList.add(createUserFromFile(id, login, firstName, lastName, isAdmin));
                this.id = id;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка во время чтения файла");
            e.printStackTrace();
        }

    }

    public void mainManu (){

        Scanner scanner = new Scanner(System.in);

        System.out.println("1 - Войти");
        System.out.println("2 - Зарегистрироваться");

        byte choise = scanner.nextByte();

        if (choise == 1){
            login();
        }
        if (choise == 2) {
            registerUser();
        }

    }

    public void usersMenu (){

    System.out.println("1 -  Изменить личные данные");
    System.out.println("2 -  Удалить пользователя");
    System.out.println("3 -  Показать список пользователей");
    Scanner scanner = new Scanner(System.in);
    byte choise = scanner.nextByte();
    if (choise == 1) editeUser();
    if (choise == 2) deleteUser();
    if (choise == 3) showUsers();
}

    public void login (){
    Scanner scanner = new Scanner(System.in);
    System.out.println("Введите логин");
    String login = scanner.nextLine();
    int flag1 = 0;
    int indexOfUser = 0;
    if (usersList.size() != 0 ) {


        do {
            flag1 = 0;
            for (int i = 0; i < usersList.size(); i++) {
                if (usersList.get(i).login.equals(login)) {
                    flag1 = 1;
                    indexOfUser = i;
                    break;
                }
            }
            if (flag1 == 0) {
                System.out.println("Неверный логин. Повторите ввод логина");

                login = scanner.nextLine();
            }

        } while (flag1 == 0);
    }
    tempUser = usersList.get(indexOfUser);
    System.out.println("Добро пожаловать " + tempUser.firstName + " " + tempUser.lastName );

//    if (tempUser.isAdmin) adminMenu();
//    else  usersMenu();
}

    private void adminMenu() {
        System.out.println("Меню администратора");
    }

    public void showUsers() {

        for (int i = 0; i < usersList.size() ; i++) {

            System.out.println(usersList.get(i));
        }
    }

    private  User createUser(String login, String firstName, String lastName){

         id ++;
        return new User(id, login, firstName, lastName, false);
    }

    private  User createUserFromFile(int id, String login, String firstName, String lastName, boolean isAdmin){


        return new User(id, login, firstName, lastName, isAdmin);
    }

    public void registerUser() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите логин");
        String login = scanner.nextLine();
        int flag1 = 0;
        if (usersList.size() != 0 ){

            do {
                flag1 = 0;
                for (int i = 0; i < usersList.size() ; i++) {
                    if (usersList.get(i).login.equals(login)){
                        flag1 = 1;
                        break;
                    }
                }
                if (flag1 == 1){
                    System.out.println("Такой логин уже зарегистрирован");
                    System.out.println("Введите логин");
                    login = scanner.nextLine();
                }

            } while (flag1 == 1);
        }
        System.out.println("Введите Имя");
        String firstName = scanner.nextLine();

        System.out.println("Введите Фамилию");
        String lastName = scanner.nextLine();

        usersList.add(createUser(login, firstName, lastName));
        tempUser = usersList.get(usersList.size()-1);

        System.out.println("Пользователь " + usersList.get(usersList.size()-1).firstName + " " +
                usersList.get(usersList.size()-1).lastName + " Логин - " + usersList.get(usersList.size()-1).login +"  успешно зарегистрирован");

// Добавить запись пользователя в файл
        save();
//        usersMenu();

    }

    public void editeUser() {

        String login = tempUser.getLogin();

        if (usersList.size() != 0 ){

            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите новое Имя");
            String newFirstName = scanner.nextLine();
            System.out.println("Введите новоую Фамилию");
            String newLastName = scanner.nextLine();

            for (int i = 0; i < usersList.size() ; i++) {

                if (usersList.get(i).login.equals(login)){
                    usersList.get(i).setFirstName(newFirstName);
                    usersList.get(i).setLastName(newLastName);

                    System.out.println("Данные пользователя успешно изменены");
                    save();
                    break;
                }
            }

        } else System.out.println("Еще нет ни одного пользователя");
    }

    public void deleteUser(){

        String login = tempUser.getLogin();
        int indexOfUser = 0;
        if (usersList.size() != 0 ) {
            for (int i = 0; i < usersList.size(); i++) {

                if (usersList.get(i).login.equals(login)) {
                    indexOfUser = i;
                    break;
                }
            }

            System.out.println("Вы действительно хотите удалить Пользователя " + usersList.get(indexOfUser).firstName + " " +
                    usersList.get( indexOfUser).lastName + " Логин - " + usersList.get( indexOfUser).login +"  ?");
            System.out.println("Для подтверждения нажмите 1 и Enter, для отмены - любую клавишу и Enter");

            Scanner scanner = new Scanner(System.in);
            byte confirm = scanner.nextByte();

    // Как защитить от ввода не числа???
//            String confirm = scanner.nextLine();
//            if (confirm.equals(1))
//          Не работает!!!

            if (confirm == 1){

                usersList.remove(usersList.get(indexOfUser));
                save();
                System.out.println("Даннне пользователя успешно удалены");

            }else System.out.println("Ну и ладно");

        } else System.out.println("Удаление невозможно. Еще нет ни одного пользователя");

 //       usersMenu();
    }

    public User findById(int id) {
        return usersList.stream().filter(u->u.getId() == id).findFirst().orElse(null);
    }

    public User findByLogin(String str) {
        return usersList.stream().filter(u->u.getLogin().equals(str)).findFirst().orElse(null);
    }

    public User getTempUser() {
        return tempUser;
    }

    public void setTempUser(User tempUser) {
        this.tempUser = tempUser;
    }

    public static class User{

        private   int id;
        private String login;
        private String firstName;
        private String lastName;
        private boolean isAdmin;

        public User(int id, String login, String firstName, String lastName, boolean isAdmin) {
            this.id = id;
            this.login = login;
            this.firstName = firstName;
            this.lastName = lastName;
            this.isAdmin = isAdmin;
        }


        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", login='" + login + '\'' +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", isAdmin=" + isAdmin +
                    '}';
        }

        public int getId() {
            return id;
        }

        public String getLogin() {
            return login;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public boolean isAdmin() {
            return isAdmin;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public void setAdmin(boolean admin) {
            isAdmin = admin;
        }
    }
}
