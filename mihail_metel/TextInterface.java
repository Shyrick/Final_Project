package mihail_metel;

import Shyrick.User;
import Shyrick.UserController;
import crazyjedi.BookingManager;
import crazyjedi.Hotel;
import crazyjedi.HotelManager;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class TextInterface {

    private Scanner scanner = new Scanner(System.in);
    private Shyrick.UserController userController;
    private final BookingManager bookingManager;
    private final HotelManager hotelManager;
    private User editedUser;

    private static TextInterface interFace;
    private Map<String,Map> loginMenu = new HashMap<>();

    public Scanner getScanner() {
        return scanner;
    }

    private Map<String,Map> adminMenu = new HashMap<>();
    private Map<String,Map> userMenu  = new HashMap<>();

    private Shyrick.User you = null;

    public static void create(UserController userController, BookingManager bookingManager) {
        interFace = new TextInterface(userController, bookingManager);
    }

    private TextInterface(UserController userController, BookingManager bookingManager) {
        this.userController = userController;
        this.bookingManager = bookingManager;
        this.hotelManager = bookingManager.getHotelManager();

        try{
            // structure of login menu:     login-register-exit
            loginMenu.put("Вход", null);
            loginMenu.put("Регистрация", null);

            //structure of admin menu:  hotel -> add, find by name, find by city, edit, remove
            //                          user -> add user, add admin, find, edit, remove
            //                          booking -> add, find, edit, remove
            adminMenu.put("Отели", new HashMap<String, Map>());
            adminMenu.put("Пользователи", new HashMap<String, Map>());
            adminMenu.put("Бронирования", new HashMap<String, Map>());

            adminMenu.get("Отели").put("Добавить", null);
            adminMenu.get("Отели").put("Поиск по имени", null);
            adminMenu.get("Отели").put("Поиск по городу", null);
            adminMenu.get("Отели").put("Редактировать отель", null);
            adminMenu.get("Отели").put("Удалить отель", null);

            adminMenu.get("Пользователи").put("Вывести список пользователей на экран", null);

            Map<String, Map> addAdminOrUerUserMenu = new HashMap<>();
            addAdminOrUerUserMenu.put("Добавить пользователя", null);
            addAdminOrUerUserMenu.put("Добавить администратора", null);
            adminMenu.get("Пользователи").put("Добавить пользователя или администратора", addAdminOrUerUserMenu);

            Map<String, Map> editUserMenu = new HashMap<>();
            editUserMenu.put("Редактировать данные: найти по Id", null);
            editUserMenu.put("Редактировать данные: найти по логину", null);

            Map<String, Map> removeUserMenu = new HashMap<>();
            removeUserMenu.put("Удалить пользователя: найти по логину", null);
            removeUserMenu.put("Удалить пользователя: найти по Id", null);

            adminMenu.get("Пользователи").put("Изменить данные", editUserMenu);
            adminMenu.get("Пользователи").put("Удалить", removeUserMenu);


            adminMenu.get("Бронирования").put("Сделать бронирование", null);
            adminMenu.get("Бронирования").put("Найти бронирования пользователя", null);
            adminMenu.get("Бронирования").put("Поис бронирования по ID", null);
            adminMenu.get("Бронирования").put("Изменить данные бронирования", null);
            adminMenu.get("Бронирования").put("Отменить бронирование", null);

            //structure of user menu:  hotel -> find by name, find by city
            //                         personal data -> edit, remove
            //                         my bookings -> add, see, edit, cancel
            userMenu.put("Поиск отелей", new HashMap<String, Map>());
            userMenu.put("Редактировать персональные данные", new HashMap<String, Map>());
            userMenu.put("Действия со своими бронированиями", new HashMap<String, Map>());

            userMenu.get("Поиск отелей").put("find by name", null);
            userMenu.get("Поиск отелей").put("find by city", null);

            userMenu.get("Персональные данные").put("Редактировать свои данные", null);
            userMenu.get("Персональные данные").put("Удалить свои данные", null);

            userMenu.get("Действия со своими бронированиями").put("add", null);
            userMenu.get("Действия со своими бронированиями").put("see", null);
            userMenu.get("Действия со своими бронированиями").put("edit", null);
            userMenu.get("Действия со своими бронированиями").put("cancel", null);
        }
        catch (RuntimeException e) {
            System.out.println("Exception in menu initialization:" + e.toString());
        }
    }

    public static TextInterface getInterFace() {
        return interFace;
    }

    public Map<String, Map> getLoginMenu() {
        return loginMenu;
    }

    public Map<String, Map> getAdminMenu() {
        return adminMenu;
    }

    public Map<String, Map> getUserMenu() {
        return userMenu;
    }

    /**
     * Рекурсивный метод текстового интерфейса, который принимает Мап со структурой команда-Мап, в котором
     *  хранится текст команды или подменю и ссылкой на следующие мапы(в случае когда есть подуровни), либо Null.
     *  Если ссылка на Мап равна нул, то запускается метод соответствующий имени команды
     * @param map Мап со структурой команда-Мап
     */
    public void runTUI(Map<String,Map> map) {
        String str;

        do {
            int i = 0;
            Map<String,String> temp = new HashMap<>();      // temp - map, linking text of commands and its number

            for (Map.Entry<String, Map> e:  map.entrySet()){
                temp.put(Integer.toString(++i), e.getKey());
                System.out.println(i + ".  " + e.getKey());
            }
            System.out.println((i + 1) + ".  Выход");

            str = scanner.next();
            if (str.equals("Выход") || str.equals(Integer.toString(i + 1))){return;}

            if (map.keySet().contains(str) ) {      // if user entered text
                if (map.get(str)!= null) {
                    System.out.println(str);
                    runTUI(map.get(str));
                }
                else {
                    switchCommands(str);
                }
            }
            if (temp.keySet().contains(str)) {      // if user entered command number
                if (map.get(temp.get(str))!= null) {
                    System.out.println(temp.get(str));
                    runTUI(map.get(temp.get(str)));
                }
                else {
                    switchCommands(temp.get(str));
                }
            }
        } while (!str.equals("Выход"));
    }

    /**
     * Ищет по имени м запускает метод-обертку интерфейса, который в свою очередь запускает метод контроллера
     * @param command - текстовое обозначение пункта меню, которому должен соответствовать метод обертки в интерфейсе и
     *                метод одного из контроллеров
     */
    private void switchCommands(String command) {
        switch (command){
            // логин
            case "Вход": login();
                break;
            case "Регистрация": register();
                break;
            case "Вывести список пользователей на экран":
                userController.showUsers();
                break;
            case "Добавить пользователя":
                addUser();
                break;
            case "Добавить администратора":
                addAdmin();
                break;
            case "Редактировать данные: найти по логину":
                editUserByLogin();
                break;
            case "Редактировать данные: найти по Id":
                editUserById();
                break;
            case "Удалить пользователя: найти по логину":
                removeUserByLogin();
                break;
            case "Удалить пользователя: найти по Id":
                removeUserById();
                break;
            case "Редактировать свои данные":
                userController.editeUser();
                break;
            case "Удалить свои данные":
                userController.deleteUser();
                break;
            case "Сделать бронирование":
                addBooking();
                break;
        }
    }

    private void addBooking() {
        System.out.println("Введите дату начала бронирования");
        try{
            editedUser = userController.getTempUser();
            Date dateBegin = getDate();
            Date dateEnd = getDate();
            System.out.println("Доступные города");
            //System.out.println(hotelManager.cities);
            System.out.println("Введите ID");
            int cityId = scanner.nextByte();
            System.out.println("Доступные отели в городе");
            hotelManager.getHotelsByCity(cityId).forEach(System.out::println);
            System.out.println("Введите ID отеля");
            int hotelId = scanner.nextByte();
            Hotel hotel = hotelManager.getHotelsByCity(cityId).get(hotelId);
            System.out.println("Комнаты доступные для бронирования:");
            hotel.getRooms().stream().filter(r->
                bookingManager.checkBookingPossible(dateBegin,dateEnd,r)).forEach(System.out::println);
            System.out.println("Введите ID комнаты");
            int roomId = scanner.nextByte();

            bookingManager.addBooking(dateBegin,dateEnd,editedUser,
                    hotelId, roomId);
        }
        catch (Exception e){
            System.out.println(e.toString());
        }


    }

    private Date getDate() throws Exception {
        Date date;
        try{
            date = new Date(getYear(),getMonth(),getDay());
        }
        catch (Exception e){
            throw new RuntimeException("Некорректно введена дата");
        }
        return date;
    }

    private void removeUserById() {
        findUserById();
        if (editedUser!= null) {
            userController.setTempUser(editedUser);
            userController.deleteUser();
        }
        else {
            System.out.println("Ползователь не найден");
        }
    }

    private void removeUserByLogin() {
        findUserByLogin();
        if (editedUser!= null) {
            userController.setTempUser(editedUser);
            userController.deleteUser();
        }
        else {
            System.out.println("Ползователь не найден");
        }
    }

    private void editUserById() {
        findUserById();
        if (editedUser!= null) {
            userController.setTempUser(editedUser);
            userController.editeUser();
        }
        else {
            System.out.println("Ползователь не найден");
        }
    }

    private void editUserByLogin() {
        findUserByLogin();
        if (editedUser!= null) {
            userController.setTempUser(editedUser);
            userController.editeUser();
        }
        else {
            System.out.println("Ползователь не найден");
        }
    }

    private void login() {
        System.out.println("Введите логин");
        String login = scanner.next();

        if (userController.hasLogin(login)) {
            userController.setTempUser(userController.findByLogin(login));
            System.out.println("Добро пожаловать " + userController.getTempUser());
        }
        else {
            System.out.println("Несущствующий логин.");
        }

        if (userController.getTempUser()!= null) {
            if (userController.getTempUser().getIsAdmin()) {
                runTUI(interFace.getAdminMenu());
            }
            else {
                runTUI(interFace.getUserMenu());
            }
        }
    }

    private void findUserById() {
        System.out.println("Введите ID пользователя");
        editedUser = userController.findById(getId());
        System.out.println(editedUser);
    }

    private void findUserByLogin() {
        System.out.println("Введите логин пользователя");
        String str = getName();
        editedUser = userController.findByLogin(str);
        System.out.println(editedUser);
    }

    private void addAdmin() {
        userController.registerUser();
        userController.getTempUser().setAdmin(true);
    }

    private void addUser() {
        userController.registerUser();
        userController.getTempUser().setAdmin(false);
    }

    private void register() {
        userController.registerUser();
    }


    // Methods for data input
    private int getId() {
        return scanner.nextInt();
    }

    private String getName() {
        return scanner.next();
    }

    private String getCity() {
        System.out.println("City:");
        return scanner.next();
    }

    private int getDuration() throws RuntimeException {
        System.out.println("Продолжительность (дней):");
        try{return scanner.nextInt();}
        catch (RuntimeException e){
            throw new RuntimeException("Ошибка ввода продолжительности");
        }
    }

    private int getDay() throws RuntimeException  {
        System.out.println("День: ");
        try{return scanner.nextInt();}
        catch (RuntimeException e){
            throw new RuntimeException("Ошибка ввода дня");
        }
    }

    private int getMonth() throws RuntimeException  {
        System.out.println("Месяц:");
        try{return scanner.nextInt();}
        catch (RuntimeException e){
            throw new RuntimeException("Ошибка ввода месяца");
        }
    }

    private int getYear() throws RuntimeException {
        System.out.println("Год:");
        try{return scanner.nextInt();}
        catch (RuntimeException e){
            throw new RuntimeException("Ошибка ввода года");
        }
    }
}
