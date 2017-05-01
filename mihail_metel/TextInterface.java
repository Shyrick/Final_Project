package mihail_metel;

import Final_Project.Users.UserController;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class TextInterface {
    private static final TextInterface interFace = new TextInterface();
    private Map<String,Map> loginMenu = new HashMap<>();
    private Map<String,Map> adminMenu = new HashMap<>();
    private Map<String,Map> userMenu  = new HashMap<>();

    private Shyrick.UserController.User you = null;


    private TextInterface() {

        try{
            // structure of login menu:     login-register-exit
            loginMenu.put("login", new HashMap<String, Map>());
            loginMenu.put("register", null);

            loginMenu.get("login").put("admin", null);
            loginMenu.get("login").put("client", null);

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

            adminMenu.get("user").put("add user", null);
            adminMenu.get("user").put("add admin", null);
            adminMenu.get("user").put("find by name", null);
            adminMenu.get("user").put("find by ID", null);
            adminMenu.get("user").put("edit", null);
            adminMenu.get("user").put("remove", null);

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
     * @param map - Мап со структурой команда-Мап
     */
    public void runTUI(Map<String,Map> map) {
        String str = "";
        Scanner scanner = new Scanner(System.in);
        UserController.User you = null;

        do {
            System.out.println("Available commands: ");
            int i = 0;
            Map<String,String> temp = new HashMap<>();

            for (Map.Entry<String, Map> e:  map.entrySet()){
                temp.put(Integer.toString(++i), e.getKey());    // temp - map, linking text of commands and their number
                System.out.println(i + " " + e.getKey());
            }
            System.out.println((i + 1) + " exit");

            str = scanner.nextLine();
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

        scanner.close();
    }

    /**
     * Ищет по имени м запускает метод-обертку интерфейса, который в свою очередь запускает метод контроллера
     * @param command - текстовое обозначение пункта меню, которому должен соответствовать метод обертки в интерфейсе и
     *                метод одного из контроллеров
     */
    private void switchCommands(String command) {
        switch (command){
            case "admin": admin();
                break;
            case "client": client();
                break;
            case "register": register();
                break;
            case "exit":

                break;
        }
    }

    private void admin() {
        System.out.println("Admin menu: ");
        runTUI(interFace.getAdminMenu());
    }

    private void client() {
        System.out.println("User menu: ");
        runTUI(interFace.getUserMenu());
    }

    private void register() {
        System.out.println("register method");
    }
}
