package Final_Project.Users;

import Shyrick.UserController;
import crazyjedi.BookingManager;
import crazyjedi.HotelManager;
import mihail_metel.TextInterface;


/**
 * Главный класс всей системы, в котором в методе Main инициализируются объекты интерфейса и контроллеров пользователей,
 * бронирований и отелей и запускается текстовый интерфейс программы.
 */
public class HotelManagementSystem {

    /**
     * Главный метод всей системы, в котором инициализируются объекты интерфейса и контроллеров пользователей,
     * бронирований и отелей и запускается текстовый интерфейс программы.
     * @param args
     */
    public static void main(String[] args) {
        UserController userController = new UserController();
        HotelManager hotelManager = new HotelManager();
        BookingManager bookingManager = new BookingManager(hotelManager, userController);
        // HotelManager
        // BookingManager
        TextInterface.create(bookingManager); // менеджеры должны быть переданы как параметры, потом надо добавить и контроллеры отелей и букингов
        TextInterface.getInterFace().runTUI(TextInterface.getInterFace().getLoginMenu());
        TextInterface.getInterFace().getScanner().close();
    }
}