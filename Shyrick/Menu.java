package Shyrick;

import java.util.Scanner;

public class Menu {

UserController userController = new UserController();

    public void helloManu () {

        userController.daoUser.readDB();

        Scanner scanner = new Scanner(System.in);

        byte choise = 0;

        do {
            System.out.println("1 - Войти");
            System.out.println("2 - Зарегистрироваться");

            choise = scanner.nextByte();

            if (choise == 1) {
                if (userController.daoUser.getUserListSize() != 0 ){
                    userController.login();
                } else {
                    System.out.println("В базеда данных нет пользователей. Зарегистрируйтесь");
                    userController.registerUser();
                }


            }
            if (choise == 2) {
                userController.registerUser();
                break;
            }

            if (choise != 1 && choise != 2) {
                System.out.println("Не верный выбор. Повторите ввод");
            }

        } while (choise != 1 && choise != 2);
    }

    public void userMainMenu (){

        UserController userController = new UserController();
        Scanner scanner = new Scanner(System.in);

        byte choise = 0;


        do {
            System.out.println("1 -  Изменить личные данные");
            System.out.println("2 -  Удалить пользователя");
            System.out.println("3 -  Показать список пользователей");

            choise = scanner.nextByte();

            if (choise == 1) userController.editeUser();
            if (choise == 2) userController.deleteUser();
            if (choise == 3) {
                userController.daoUser.showUsers();
            }else System.out.println("Не верный выбор. Повторите ввод");

        } while (choise == 1 && choise != 2 && choise != 3);
    }


//    public void adminMainMenu() {
//        System.out.println("Меню администратора");
//    }

}
