package mihail_metel;

import Shyrick.User;
import crazyjedi.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


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

    public static void create(BookingManager bookingManager) {
        interFace = new TextInterface(bookingManager);
        System.out.println("Добро пожаловать в программу управления бронированиями группы 10 GoJava#6." +
                "Для выбора пунктов меню вводите соответствующий номер команды или ее полный текст." +
                "Приятного использования!\n");
    }

    private TextInterface(BookingManager bookingManager) {
        this.bookingManager = bookingManager;
        this.userController = bookingManager.getUserController();
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

            adminMenu.get("Отели").put("Показать отели", null);
            adminMenu.get("Отели").put("Добавить отель", null);
            adminMenu.get("Отели").put("Редактировать или удалить отель", null);

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
            
            adminMenu.get("Бронирования").put("Сделать бронирование на пользователя", null);
            adminMenu.get("Бронирования").put("Изменить или отменить бронирование", null);

            //structure of user menu:  hotel -> find by name, find by city
            //                         personal data -> edit, remove
            //                         my bookings -> add, see, edit, cancel
            userMenu.put("Найти и забронировать номер", null);
            userMenu.put("Редактировать персональные данные", new HashMap<String, Map>());
            userMenu.put("Действия со своими бронированиями", null);

            userMenu.get("Редактировать персональные данные").put("Редактировать свои данные", null);
            userMenu.get("Редактировать персональные данные").put("Удалить свои данные", null);
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

    private Map<String, Map> getAdminMenu() {
        return adminMenu;
    }

    private Map<String, Map> getUserMenu() {
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
                deleteMy();
                throw new RuntimeException("Такого пользователя больше нет.");
                //break;
            case "Сделать бронирование на пользователя":
                findUser();
                addBooking();
                break;
            case "Добавить отель":
                addHotel();
                break;
            case "Показать отели":
                hotelManager.getHotels().forEach(System.out::println);
                break;
            case "Редактировать или удалить отель":
                editOrRemoveHotel();
                break;
            
            case "Изменить или отменить бронирование":
                changeOrCanselBooking(true);    // делает админ
                break;
            case "Действия со своими бронированиями":
                changeOrCanselBooking(false);    // делает пользователь
                break;
            case "Найти и забронировать номер":
                addBooking();
                break;
        }
    }

    private void deleteMy() {
        System.out.println("Удалить также ваши бронирования? \n1. Да\n2. Нет, отменить удаление данных");
        try{
            int c = scanner.nextInt();
            if (c == 1) {
                bookingManager.removeBooking(bookingManager.getAllBookings().stream().filter(b->b.getUser().equals(editedUser)).collect(Collectors.toList()));
                userController.deleteUser();
            } else {
                System.out.println("Удаление данных отменено.");
            }
        } catch (RuntimeException e){
            System.out.println("Ошибка при отмене бронирования");
        }

    }

    private void editOrRemoveHotel() {
        try{
            Hotel hotel = selectHotel();
            System.out.println("1. Изменить название отеля\n" +
                    "2. Удалить отель и все его бронирования\n" +
                    "3. Заново ввести все комнаты (только при отсутствии бронирований)\n" +
                    "4. Добавить комнаты\n" +
                    "5. Назад");
            byte choise = scanner.nextByte();

            switch (choise) {
                case 1:
                    System.out.println("Введите новое название отеля");
                    hotel.setName(scanner.next());

                    hotelManager.updateHotel(hotel.getId(),hotel);

                    bookingManager.dumpBookings();
                    bookingManager.loadBookings();
                    break;
                case 2:
                    System.out.println("Вы вообще осознаете, что при этом отель и все бронирования будут удалены?" +
                            "\n1. Да\n2. Отмена");
                    byte с = scanner.nextByte();
                    if (с == 1) {
                        List<Booking> bookings;

                        if ( bookingManager.getAllBookings()!= null){
                            bookings = bookingManager.getAllBookings().stream().filter(b -> b.getHotel().equals(hotel)).collect(Collectors.toList());
                            if(bookings != null && bookings.size()!=0) {
                                bookingManager.removeBooking(bookings);
                            }
                        }
                        hotelManager.removeHotel(hotel);
                        System.out.println("Отель и все его бронирования безвозвратно удалены.");
                        return;
                    } else return;
                case 3:
                    if (bookingManager.getAllBookings().stream().filter(b -> b.getHotel().equals(hotel)).findFirst().orElse(null) == null) {
                        List<Integer> list = new ArrayList<>();
                        int s = hotel.getRooms().size();
                        for (int i = 0; i < s; i++) {
                            list.add(hotel.getRooms().get(i).getId());
                        }
                        for (int i = 0; i < s; i++) {
                            hotel.removeRoom(list.get(i));
                        }

                        byte ch;
                        do {
                            System.out.println("Введите цену на комнаты");
                            double price = scanner.nextDouble();
                            System.out.println("Введите количество людей  в комнате");
                            byte persons = scanner.nextByte();
                            System.out.println("Введите количество таких комнат");
                            int count = scanner.nextInt();

                            for (int i = 0; i < count; i++) {
                                hotelManager.createRoom(hotel, persons, new BigDecimal(price));
                            }

                            System.out.println("Хотите ли добавить еще комнаты в этот отель? \n1. Да\n2. Нет  ");
                            ch = scanner.nextByte();
                        } while (ch == 1);
                    } else {
                        System.out.println("В отеле есть комнаты с бронированиями, действие невозможно.");
                    }
                    return;
                case 4:
                    byte ch;
                    do {
                        System.out.println("Введите цену на комнаты");
                        double price = scanner.nextDouble();
                        System.out.println("Введите количество людей  в комнате");
                        byte persons = scanner.nextByte();
                        System.out.println("Введите количество таких комнат");
                        int count = scanner.nextInt();

                        for (int i = 0; i < count; i++) {
                            hotelManager.createRoom(hotel, persons, new BigDecimal(price));
                        }

                        System.out.println("Хотите ли добавить еще комнаты в этот отель? \n1. Да\n2. Нет  ");
                        ch = scanner.nextByte();
                    } while (ch == 1);
                    return;
                case 5:
                    return;
                default:
                    return;
            }
        }catch (RuntimeException e) {
            System.out.println(e);
        }

    }

    private Hotel selectHotel() throws RuntimeException {
        System.out.println("Выберите отель из списка ниже:");
        try{
            hotelManager.getHotels().forEach(System.out::println);
            System.out.println("\nВведите ID отеля");
            int id = scanner.nextInt();
            System.out.println(hotelManager.findHotelById(id));
            return hotelManager.findHotelById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Ошибка при выборе отелся");
        }

    }

    private void changeOrCanselBooking(boolean isAdmin) throws RuntimeException {
        try{
            Booking booking;
            if (isAdmin){
                booking = selectBooking();
            } else {
                System.out.println("Ваши бронирования:");
                if (bookingManager.getByUser(userController.getTempUser()) == null ||bookingManager.getByUser(userController.getTempUser()).size() ==0){
                    System.out.println("Нет ни одного бронирования");
                    return;
                }
                bookingManager.getByUser(userController.getTempUser()).forEach(b->{
                    System.out.println("Бронирование Id - " + b.getId() +
                            "\nОтель: " + b.getHotel().getName() +
                            " Комната: " + b.getRoom().getId() +
                            " Город: " + b.getHotel().getCity() +
                            "\nДата начала: " + b.getDateBegin() +
                            "\nДата конца: " + b.getDateEnd() + "\n");});

                System.out.println("Введите ID бронирования:");
                int id = scanner.nextInt();
                booking = bookingManager.findById(id);
            }

            if(booking == null) {throw new RuntimeException("Не найдено ни одного бронирования");}

            System.out.println("1. Изменить даты бронирования\n2. Отменить бронирование\n3. Назад");
            byte choise = scanner.nextByte();

            switch (choise){
                case 1:
                    System.out.println("Введите дату начала бронирования");
                    Date dateBegin = getDate();
                    System.out.println("Введите дату конца бронирования");
                    Date dateEnd = getDate();

                    if (dateBegin.compareTo(dateEnd) > 0) {throw new RuntimeException("Дата начала позже даты конца!");
                    }

                    bookingManager.removeBooking(booking);
                    List<Room> listRoom = booking.getHotel().getRooms().stream().filter(r->
                            bookingManager.checkBookingPossible(dateBegin,dateEnd,r)).collect(Collectors.toList());
                    if (listRoom == null || listRoom.size() == 0) {
                        System.out.println("Нет доступных комнат на этот период");
                        bookingManager.addBooking(booking.getDateBegin(),booking.getDateEnd(),booking.getUser(),booking.getHotel().getId(), booking.getRoom().getId());
                        return;
                    } else {
                        System.out.println("Комнаты доступные для бронирования:");
                        listRoom.stream().forEach(System.out::println);
                        System.out.println("\nВведите ID комнаты");
                        int roomId = scanner.nextInt();
                        booking.setDateBegin(dateBegin);
                        booking.setDateEnd(dateEnd);
                        bookingManager.addBooking(dateBegin,dateEnd,booking.getUser(),booking.getHotel().getId(), roomId);
                        System.out.println("Даты бронирования успешно изменены. Данные измененного бронирования:" +
                                "\n" + booking);
                    }
                    break;
                case 2:
                    bookingManager.removeBooking(booking);
                    System.out.println("Бронирование\n" + booking + "\nупешно отменено.");
                    break;
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private Booking selectBooking() throws RuntimeException {
        System.out.println("1. Выбрать из полного списка ID нужного бронирования" +
                "\n2. Выбрать среди бронирований пользователя\n" +
                "3. Выход");
        try{
            byte choise = scanner.nextByte();
            int id;
            switch (choise) {
                case 1:
                    bookingManager.getAllBookings().forEach(System.out::println);
                    System.out.println("Введите ID бронирования:");
                    id = scanner.nextInt();
                    return bookingManager.findById(id);
                case 2:
                    findUser();
                    bookingManager.getByUser(userController.getTempUser()).forEach(System.out::println);
                    System.out.println("Введите ID бронирования:");
                    id = scanner.nextInt();
                    return bookingManager.findById(id);
                default:
                    throw new RuntimeException("Выбор бронирования отменен.");
            }
        }catch (RuntimeException e) {
            System.out.println(e);
            throw new RuntimeException("Ошибка выбора бронирования");
        }
    }

    private void addHotel() {
        try{
            System.out.println("Введите название отеля");
            String hotelName = scanner.next();
            City city = addOrSelectCity();
            hotelManager.createHotel(city.getId(),hotelName);
            int hotelId = hotelManager.findHotelByName(hotelName).getId();
            byte choise;
            do{
                System.out.println("Введите цену на комнаты");
                double price = scanner.nextDouble();
                System.out.println("Введите количество людей  в комнате");
                byte persons = scanner.nextByte();
                System.out.println("Введите количество таких комнат");
                int count = scanner.nextInt();

                for (int i = 0; i < count; i++) {
                    Hotel hotel = hotelManager.findHotelById(hotelId);
                    hotelManager.createRoom(hotel, persons, new BigDecimal(price));
                }

                System.out.println("Хотите ли добавить еще комнаты в этот отель? \n1. Да\n2. Нет  ");
                choise = scanner.nextByte();
            } while (choise == 1);
        } catch (Exception e){
            System.out.println(e);
            System.out.println("Ошибка при создании отеля");
        }
    }

    private City addOrSelectCity() throws RuntimeException {
        try{
            System.out.println("Cуществующие города");
            hotelManager.getCities().forEach(System.out::println);
            System.out.println("1. Выбрать город");
            System.out.println("2. Добавить город");
            String str = scanner.next();
            if (str.equals("1")) {
                System.out.println("Введите номер города");
                int cityId = scanner.nextInt();

                return hotelManager.getCities().stream().filter(c->c.getId() == cityId).findFirst().orElse(null);
            } else if (str.equals("2")) {
                System.out.println("Введите город отеля");
                String cityName = scanner.next();

                hotelManager.addCity(cityName);

                return hotelManager.getCities().stream().filter(c->c.getName().equals(cityName)).findFirst().orElse(null);
            }
            else {
                System.out.println("Город не удалось создать или найти");
                throw new RuntimeException("Ошибка при создании города");
            }
        }catch (Exception e){
            throw e;
        }
    }

    private void addBooking() {
        City city;

        try{editedUser = userController.getTempUser();
            System.out.println("Cуществующие города");
            hotelManager.getCities().forEach(System.out::println);
            System.out.println("\nВведите номер города");
            int cityId = scanner.nextInt();
            city = hotelManager.getCities().stream().filter(c->c.getId() == cityId).findFirst().orElse(null);

            System.out.println("Доступные отели в городе");
            List<Hotel> hotelList = hotelManager.getHotelsByCity(city.getId());
            if (hotelList == null || hotelList.size() == 0) {
                System.out.println("К сожалению в этом городе не зарегистрировано ни одного отеля");
                return;
            }
            hotelList.forEach(System.out::println);
            System.out.println("\nВведите ID отеля");

            int hotelId = scanner.nextInt();
            Hotel hotel = hotelManager.findHotelById(hotelId);

            System.out.println("Введите дату начала бронирования");
            Date dateBegin = getDate();
            System.out.println("Введите дату конца бронирования");
            Date dateEnd = getDate();

            if (dateBegin.compareTo(dateEnd) > 0) {throw new RuntimeException("Дата начала позже даты конца!");
            }

            System.out.println("\nКомнаты доступные для бронирования:");
            hotel.getRooms().stream().filter(r->
                bookingManager.checkBookingPossible(dateBegin,dateEnd,r)).forEach(System.out::println);

            System.out.println("\nВведите ID комнаты");
            int roomId = scanner.nextInt();

            bookingManager.addBooking(dateBegin, dateEnd, editedUser, hotelId, roomId);
            System.out.println("\nБронирование на пользователя " + editedUser.getLogin() + "\nс " + dateBegin + "\nпо " + dateEnd +
                    "\nв отеле " + hotel.getName() + "\nв городе " + hotel.getCity() + " успешно добавлено.");
        }
        catch (Exception e){
            System.out.println("\nНе удалось создать бронирование");
        }
    }

    private Date getDate() throws RuntimeException {
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
            bookingManager.removeBooking(bookingManager.getAllBookings().stream().filter(b->b.getUser().equals(editedUser)).collect(Collectors.toList()));
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
            bookingManager.removeBooking(bookingManager.getAllBookings().stream().filter(b->b.getUser().equals(editedUser)).collect(Collectors.toList()));
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
        try{
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
        }catch (RuntimeException e) {
            System.out.println(e + "Ошибка при входе в систему");
        }
    }

    private void findUser() throws RuntimeException {
        System.out.println("Найти пользователя\n1. По ID\n2. По логину");

        try{
            byte choise = scanner.nextByte();
            if (choise == 1) {
                userController.showUsers();
                findUserById();
            }
            else if (choise == 2) {findUserByLogin();}
        }catch (RuntimeException e) {
            throw new RuntimeException("Ошибка поиска пользователя");
        }
    }

    private void findUserById() {
        System.out.println("Введите ID пользователя");
        editedUser = userController.findById(getId());
        userController.setTempUser(editedUser);
        System.out.println(editedUser);
    }

    private void findUserByLogin() {
        System.out.println("Введите логин пользователя");
        String str = getName();
        editedUser = userController.findByLogin(str);
        userController.setTempUser(editedUser);
        System.out.println(editedUser);
    }

    private void addAdmin() {
//        userController.registerUser();
//        userController.getTempUser().setAdmin(true);
        userController.registerAdmin();
    }

    private void addUser() {
        userController.registerUser();
//        userController.getTempUser().setAdmin(false);
    }

    private void register() {
        userController.registerUser();
    }


    // Methods for data input
    private int getId() throws RuntimeException {
        try{return scanner.nextInt();}
        catch (RuntimeException e){throw new RuntimeException("ошибка ввода ID");}
    }

    private String getName() throws RuntimeException {
        String name;
        try{name = scanner.next();
            if(name == null || name.length() == 0) {
                throw new RuntimeException("Некорректное имя");
            }
        } catch (RuntimeException e){
            System.out.println(e);
            throw new RuntimeException("Ошибка ввода имени");
        }
        return name;
    }

    private int getDay() throws RuntimeException  {
        System.out.println("День: ");
        try{
            int d = scanner.nextInt();
            if (d<1 || d > 31) {throw new RuntimeException("Неправильно введенный день");}
            return d;
        }
        catch (RuntimeException e){
            System.out.println(e);
            throw new RuntimeException("Ошибка ввода дня");
        }
    }

    private int getMonth() throws RuntimeException  {
        System.out.println("Месяц:");
        try{
            int m = scanner.nextInt() - 1;
            if (m < 1 || m > 12 ) {throw new RuntimeException("Неправильный месяц");}
            return m;
        }
        catch (RuntimeException e){
            System.out.println(e);
            throw new RuntimeException("Ошибка ввода месяца");
        }
    }

    private int getYear() throws RuntimeException {
        int maxYear = 2020;
        int minyear = 2017;

        System.out.println("Год:");
        try{
            int y = scanner.nextInt();
            if (y > maxYear || y < minyear) {throw new RuntimeException("некорректный год");}
            return y;}
        catch (RuntimeException e){
            System.out.println(e);
            throw new RuntimeException("Ошибка ввода года");
        }
    }
}
