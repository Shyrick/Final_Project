package mihail_metel;

import Shyrick.User;
import Shyrick.UserController;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class TextInterface {

    private Scanner scanner = new Scanner(System.in);
    private Shyrick.UserController userController;
    private Shyrick.DAOUser daoUser;
    private User editedUser;

    private static TextInterface interFace;
    private Map<String,Map> loginMenu = new HashMap<>();
    private Map<String,Map> adminMenu = new HashMap<>();
    private Map<String,Map> userMenu  = new HashMap<>();

    private Shyrick.User you = null;

    public static void create(UserController userController) {
        interFace = new TextInterface(userController);
    }

    private TextInterface(UserController userController) {
        this.userController = userController;

        try{
            // structure of login menu:     login-register-exit
            loginMenu.put("login", null);
            loginMenu.put("register", null);

            //structure of admin menu:  hotel -> add, find by name, find by city, edit, remove
            //                          user -> add user, add admin, find, edit, remove
            //                          booking -> add, find, edit, remove
            adminMenu.put("hotel", new HashMap<String, Map>());
            adminMenu.put("user", new HashMap<String, Map>());
            adminMenu.put("booking", new HashMap<String, Map>());

            adminMenu.get("hotel").put("add", null);
            adminMenu.get("hotel").put("find by name", null);
            adminMenu.get("hotel").put("find by city", null);
            adminMenu.get("hotel").put("edit", null);
            adminMenu.get("hotel").put("remove", null);

            adminMenu.get("user").put("list users", null);
            adminMenu.get("user").put("add user", null);

            Map<String, Map> editUserMenu = new HashMap<>();
            editUserMenu.put("Edit user: find by name", null);
            editUserMenu.put("Edit user: find by ID", null);

            Map<String, Map> removeUserMenu = new HashMap<>();
            removeUserMenu.put("Remove: find by name", null);
            removeUserMenu.put("Remove: find by ID", null);

            adminMenu.get("user").put("edit", editUserMenu);
            adminMenu.get("user").put("remove", removeUserMenu);


            adminMenu.get("booking").put("add", null);
            adminMenu.get("booking").put("find by user", null);
            adminMenu.get("booking").put("find by ID", null);
            adminMenu.get("booking").put("edit", null);
            adminMenu.get("booking").put("remove", null);

            //structure of user menu:  hotel -> find by name, find by city
            //                         personal data -> edit, remove
            //                         my bookings -> add, see, edit, cancel
            userMenu.put("hotel", new HashMap<String, Map>());
            userMenu.put("personal data", new HashMap<String, Map>());
            userMenu.put("my bookings", new HashMap<String, Map>());

            userMenu.get("hotel").put("find by name", null);
            userMenu.get("hotel").put("find by city", null);

            userMenu.get("personal data").put("edit", null);
            userMenu.get("personal data").put("remove", null);

            userMenu.get("my bookings").put("add", null);
            userMenu.get("my bookings").put("see", null);
            userMenu.get("my bookings").put("edit", null);
            userMenu.get("my bookings").put("cancel", null);
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
                System.out.println(i + " " + e.getKey());
            }
            System.out.println((i + 1) + " exit");

            str = scanner.next();
            if (str.equals("exit") || str.equals(Integer.toString(i + 1))){return;}

            if (map.keySet().contains(str) ) {      // if user entered text
                if (map.get(str)!= null) {
                    runTUI(map.get(str));
                }
                else {
                    switchCommands(str);
                }
            }
            if (temp.keySet().contains(str)) {      // if user entered command number
                if (map.get(temp.get(str))!= null) {
                    runTUI(map.get(temp.get(str)));
                }
                else {
                    switchCommands(temp.get(str));
                }
            }
        } while (!str.equals("exit"));
    }

    /**
     * Ищет по имени м запускает метод-обертку интерфейса, который в свою очередь запускает метод контроллера
     * @param command - текстовое обозначение пункта меню, которому должен соответствовать метод обертки в интерфейсе и
     *                метод одного из контроллеров
     */
    private void switchCommands(String command) {
        switch (command){
            // логин
            case "login": login();
                break;
            case "register": register();
                break;

//            "list users"
//            "add user"
//            "add admin"
//            "edit"
//            "remove"

            case "list users":
               daoUser.showUsers();
                break;
            case "add user":
                addUser();
                break;
            case "add admin":
                addAdmin();
                break;
            case "Edit user: find by name":
                editUserByLogin();
                break;
            case "Edit user: find by ID":
                editUserById();
                break;
            case "Remove: find by name":
                removeUserByLogin();
                break;
            case "Remove: find by ID":
                removeUserById();
                break;

        }
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
        userController.login();
        if (userController.getTempUser()!= null) {
            if (userController.getTempUser().getIsAdmin()) {
                runTUI(interFace.getAdminMenu());
            }
            else {
                runTUI(interFace.getUserMenu());
            }
        }
    }

    private void removeUser() {
        userController.deleteUser();
    }

    private void editUser() {
        userController.editeUser();
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
        System.out.println("Input user data");
        userController.registerUser();
    }

    private void addUser() {
        System.out.println("Input user data");
        userController.registerUser();
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

    private int getDuration() {
        System.out.println("Duration (Days):");
        return scanner.nextInt();
    }

    private int getDay() {
        System.out.println("Since (D): ");
        return scanner.nextInt();
    }

    private int getMonth() {
        System.out.println("Since (M):");
        return scanner.nextInt();
    }

    private int getYear() {
        System.out.println("Since (Y):");
        return scanner.nextInt();
    }
}
