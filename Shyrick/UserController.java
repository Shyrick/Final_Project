package Shyrick;

import java.util.Scanner;

public class UserController {

    private   int  id = 0;
   DAOUser daoUser = new DAOUser();

    public User tempUser;


    private  User createUser(String login, String firstName, String lastName){

         id = daoUser.getUserFromList(daoUser.getUserListSize()-1).getId() + 1;
        return new User(id, login, firstName, lastName, false);
    }

    private Shyrick.User createAdmin(String login, String firstName, String lastName){

        id = daoUser.getUserFromList(daoUser.getUserListSize()-1).getId() + 1;
        return new Shyrick.User(id, login, firstName, lastName, true);
    }

    public void login (){

        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите логин (и нажмите Enter)");
        String login = scanner.nextLine();
        int flag1 = 0;
        int indexOfUser = 0;

        if (daoUser.getUserListSize() != 0 ) {

            do {
                flag1 = 0;
                for (int i = 0; i < daoUser.getUserListSize(); i++) {
                    if (daoUser.getUserFromList(i).getLogin().equals(login)) {
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
        tempUser = daoUser.getUserFromList(indexOfUser);
        System.out.println("Добро пожаловать " + tempUser.getFirstName() + " " + tempUser.getLastName() );
        System.out.println();


        if (tempUser.getIsAdmin()) System.out.println("вызов меню админа");
        else  System.out.println("вызов меню пользователя");

//      Вызом меню пользователя
//        menu.userMainMenu();
    }

    public void registerUser() {
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu();

        System.out.println("Введите логин");
        String login = scanner.nextLine();
        int flag1 = 0;
        if (daoUser.getUserListSize() != 0 ){

            do {
                flag1 = 0;
                for (int i = 0; i < daoUser.getUserListSize() ; i++) {
                    if (daoUser.getUserFromList(i).getLogin().equals(login)){
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

        daoUser.addUserToList(createUser(login, firstName, lastName));

        tempUser = daoUser.getUserFromList(daoUser.getUserListSize()-1);

        System.out.println("Пользователь " + tempUser.getFirstName() + " " +
                tempUser.getLastName() + " Логин - " + tempUser.getLogin() +"  успешно зарегистрирован");
        System.out.println();

        daoUser.writeInDB();

        System.out.println("вызов меню пользователя");
//      Вызом меню пользователя
//        menu.userMainMenu();
    }

    public void registerAdmin() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите логин");
        String login = scanner.nextLine();
        int flag1 = 0;
        if (daoUser.getUserListSize() != 0 ){

            do {
                flag1 = 0;
                for (int i = 0; i < daoUser.getUserListSize() ; i++) {
                    if (daoUser.getUserFromList(i).getLogin().equals(login)){
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

        daoUser.addUserToList(createAdmin(login, firstName, lastName));

        tempUser = daoUser.getUserFromList(daoUser.getUserListSize()-1);

        System.out.println("Администратор " + tempUser.getFirstName() + " " +
                tempUser.getLastName() + " Логин - " + tempUser.getLogin() +"  успешно зарегистрирован");
        System.out.println();

        daoUser.writeInDB();

        System.out.println("вызов меню администратора");
//      Вызом меню пользователя
//        menu.adminMainMenu();
    }

    public void editeUser() {

        String login = tempUser.getLogin();
        Menu menu = new Menu();

        if (daoUser.getUserListSize() != 0 ){

            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите новое Имя");
            String newFirstName = scanner.nextLine();
            System.out.println("Введите новоую Фамилию");
            String newLastName = scanner.nextLine();

            for (int i = 0; i <daoUser.getUserListSize() ; i++) {

                if (daoUser.getUserFromList(i).getLogin().equals(login)){
                    daoUser.getUserFromList(i).setFirstName(newFirstName);
                    daoUser.getUserFromList(i).setLastName(newLastName);

                    System.out.println("Данные пользователя успешно изменены");
                    break;
                }
            }

        } else System.out.println("Еще нет ни одного пользователя");

        System.out.println("Вызов меню пользователя");
//      Вызом меню пользователя
//        menu.userMainMenu();

    }

    public void deleteUser(){

        String login = tempUser.getLogin();
        Menu menu = new Menu();
        Scanner scanner = new Scanner(System.in);

        int indexOfUser = 0;

        for (int i = 0; i < daoUser.getUserListSize(); i++) {

            if (daoUser.getUserFromList(i).getLogin().equals(login)) {
                indexOfUser = i;
                break;
            }
        }

        System.out.println("Вы действительно хотите удалить Пользователя " + daoUser.getUserFromList(indexOfUser).getFirstName() + " " +
                daoUser.getUserFromList(indexOfUser).getLastName() + " Логин - " + daoUser.getUserFromList(indexOfUser).getLogin() +"  ?");
        System.out.println("Для подтверждения нажмите 1 и Enter, для отмены - любую клавишу и Enter");


        byte confirm = 0;
        if (scanner.hasNextByte()){
            confirm = scanner.nextByte();
        } else System.out.println("Не хотите удаляться? Верное решение!");

        if (confirm == 1){

            daoUser.removeUserFromList(daoUser.getUserFromList(indexOfUser));
            System.out.println("Даннне пользователя успешно удалены");
        }

        System.out.println("Вызов меню пользователя");
//      Вызом меню пользователя
//        menu.userMainMenu();



    }

    public User findById(int id){
        System.out.println("Нужно написть метод findById");
        return null;
    }

    public User findByLogin(String login){
        System.out.println("Нужно написть метод findByLogin");
        return null;
    }

    public void setTempUser(User user){
        tempUser = user;
    }

    public User getTempUser(){
        return tempUser;
    }

    public boolean hasLogin(String login) {
        return true; // нужно написать //usersList.stream().filter(user -> user.getLogin().equals(login)).findFirst().orElse(null)!= null;
    }
}
