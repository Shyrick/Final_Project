package Final_Project.Users;

import Shyrick.UserController;
import mihail_metel.TextInterface;

import java.util.Scanner;

public class HotelManagementSystem {

    public static void main(String[] args) {
        UserController userController = new UserController();
        userController.readDB();
        // HotelManager
        // BookingManager
        TextInterface.create(userController); // менеджеры должны быть переданы как параметры, потом надо добавить и контроллеры отелей и букингов
        TextInterface.getInterFace().runTUI(TextInterface.getInterFace().getLoginMenu());
        TextInterface.getInterFace().getScanner().close();
    }
}