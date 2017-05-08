package Shyrick;

import crazyjedi.Hotel;
import crazyjedi.HotelManager;

import java.util.List;
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
            System.out.println("4 - Поиск отеля по названию");
            System.out.println("5 - Поиск отеля по городу");

            choise = scanner.nextByte();

            if (choise == 1) userController.editeUser();
            if (choise == 2) userController.deleteUser();
            if (choise == 3) {
                userController.daoUser.showUsers();
            }
            if (choise == 4) {
                System.out.println("Введите название отеля");
                Scanner name = new Scanner(System.in);
                boolean flag = false;
                do {
                    if(!(name.hasNext()) || !(name.nextLine() instanceof String)) {
                        System.out.println("Некорректные данные! Попробуйте еще раз");
                    } else flag = true;
                }while(! flag);

                HotelManager hotelManager = new HotelManager();
//                System.out.println(hotelManager.getHotelsByName(name.nextLine()));
            }
            if (choise == 5) {
                System.out.println("Введите название отеля");
                Scanner name = new Scanner(System.in);
                boolean flag = false;
                do {
                    if(!(name.hasNext()) || !(name.nextLine() instanceof String)) {
                        System.out.println("Некорректные данные! Попробуйте еще раз");
                    } else flag = true;
                }while(! flag);

                HotelManager hotelManager = new HotelManager();
//                List<Hotel> result = hotelManager.getHotelsByCity(name.nextLine());
//                for (Hotel iterator : result) {
//                    System.out.println(iterator);
//                }
            }
            else System.out.println("Не верный выбор. Повторите ввод");

        } while (choise == 1 && choise != 2 && choise != 3 && choise != 4 && choise != 5);
    }


//    public void adminMainMenu() {
//        System.out.println("Меню администратора");
//    }

}
