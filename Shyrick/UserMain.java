package Shyrick;

public class UserMain {


    public static void main(String[] args) {


        Menu menu = new Menu();


        menu.helloManu();

        UserController us = new UserController();

        us.daoUser.showUsers();



    }

}
