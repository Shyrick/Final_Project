package mihail_metel;

import Final_Project.Users.UserController;

public class InterfaceTest {
    public static void main(String[] args) {
        UserController usrCtrl = new UserController();
        //HotelManager hotelMngr = new HotelManagerImpl();
        //BookingManager bookingMngr = new BookingManager(hotelMngr);

        TextInterface.getInterFace().runTUI(TextInterface.getInterFace().getLoginMenu());
    }
}
