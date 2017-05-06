package Final_Project.Users;

import Shyrick.UserController;
import crazyjedi.BookingManager;
import crazyjedi.HotelManager;
import mihail_metel.TextInterface;

public class HotelManagementSystem {

    public static void main(String[] args) {
        UserController userController = new UserController();
        HotelManager hotelManager = new HotelManager();
        BookingManager bookingManager = new BookingManager(hotelManager, userController);
        // HotelManager
        // BookingManager
        TextInterface.create(userController, bookingManager); // менеджеры должны быть переданы как параметры, потом надо добавить и контроллеры отелей и букингов
        TextInterface.getInterFace().runTUI(TextInterface.getInterFace().getLoginMenu());
        TextInterface.getInterFace().getScanner().close();
    }
}