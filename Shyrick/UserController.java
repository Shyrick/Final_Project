package Shyrick;

import java.util.Scanner;

public class UserController {

    private   int  id = 0;
   DAOUser daoUser = new DAOUser();

    public User tempUser;

    /**
     * Метод создает пользователя принимая параметры:
     * @param login логин пользователя
     * @param  firstName  имя пользователя
     * @param lastName  фамилия поьлзователея
     * В конструктор передается уникальныый id пользователя и значение поля isAdmin = false
     *@return новый пользователь
     */
    private  User createUser(String login, String firstName, String lastName){
        if (daoUser.getUserListSize() != 0) {
            id = daoUser.getUserFromList(daoUser.getUserListSize() - 1).getId() + 1;
        } else id = 0;
        return new User(id, login, firstName, lastName, false);
    }

    /**
     * Метод создает администратора принимая параметры:
     * @param login логин пользователя
     * @param  firstName - имя пользователя
     * @param lastName  - фамилия поьлзователея
     * В конструктор передается уникальныый id пользователя и значение поля isAdmin = true
     *@return новый пользователь-администратор
     */
    private User createAdmin(String login, String firstName, String lastName) {
        if (daoUser.getUserListSize() != 0) {
            id = daoUser.getUserFromList(daoUser.getUserListSize() - 1).getId() + 1;
        } else id = 0;
        return new User(id, login, firstName, lastName, true);
    }

    /**
     * Метод входа в систему зарегистрированного пользователя или администратора
     * Ожидает ввод логина с консоли, сравнивает полученное значение с базой данных пользвателей
     * находит пользователя с данным логином
     */
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


//        if (tempUser.getIsAdmin()) System.out.println("вызов меню админа");
//        else  System.out.println("вызов меню пользователя");
    }

    /**
     * Метод создает нового пользователя (методом createUser() ) и записывает его в базу данных пользователй
     * ожидает ввода с консоли логина, фамилии и имени
     */
    public void registerUser() {

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

        daoUser.addUserToList(createUser(login, firstName, lastName));

        tempUser = daoUser.getUserFromList(daoUser.getUserListSize()-1);

        System.out.println("Пользователь " + tempUser.getFirstName() + " " +
                tempUser.getLastName() + " Логин - " + tempUser.getLogin() +"  успешно зарегистрирован");
        System.out.println();

        daoUser.writeInDB();


    }

    /**
     * Метод создает нового пользователя (методом createAdmin() ) и записывает его в базу данных пользователй
     * ожидает ввода с консоли логина, фамилии и имени
     */
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
    }

    /**
     * Метод изменяет данные пользователя и записывает в базу данных пользователй
     * ожидает ввода с консоли новую фамилию и новое имя
     */
    public void editeUser() {

        String login = tempUser.getLogin();

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

        daoUser.writeInDB();

    }

    /**
     * Метод удаляет данные пользователя из базы данных
     */
    public void deleteUser(){

        String login = tempUser.getLogin();

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

        daoUser.writeInDB();

    }


    public User findById(int id){
        return daoUser.findById(id);
    }

    public User findByLogin(String login){
        return daoUser.findByLogin(login);
    }

    public void setTempUser(User user){
        tempUser = user;
    }

    public User getTempUser(){
        return tempUser;
    }

    public boolean hasLogin(String login) {
        return daoUser.hasLogin(login);
    }

    public void showUsers() {daoUser.showUsers();}
}
