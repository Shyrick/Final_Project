package Final_Project.Users;



public class UserMain {

    public static void main(String[] args) {

        Users user1 = UserController.createUser("First1", "Last1");
        Users user2 = UserController.createUser("First2", "Last2");
        Users user3 = UserController.createUser("First3", "Last3");

//        System.out.println(user1);
//        System.out.println(user2);
//        System.out.println(user3);

        UserController userController = new UserController();

//        userController.registerUser(user1);
//        userController.registerUser(user2);
//        userController.registerUser(user3);
        user1.registerUser();
        user2.registerUser();
        user3.registerUser();

//        userController.showId();
        userController.showUsers();

        System.out.println("====================");
        user2.editeUser("FN2", "LN2");
        userController.showUsers();

        System.out.println("====================");
        user2.deleteUser();
        Users user4 = UserController.createUser("First4", "Last4");
//        user3.registerUser();
        user4.registerUser();
        userController.showUsers();
    }

}
