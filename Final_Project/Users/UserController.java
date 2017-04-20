package Final_Project.Users;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserController {


    private static List<Integer> idList = new ArrayList<>();
    private static List<User> usersList = new ArrayList<>();
    private static DAO<User> userDAO = new DAO<>();

    static {
        //load users from file
        usersList = userDAO.toList();
    }

    public static int  lastId = 0;

    public static User createUser(String firstName, String lastName ){
            int id = lastId + 1;
            idList.add(id);
            lastId = id;

       return new User(id, firstName, lastName);
    }

    public static User createUserByLine(String line ){
        // Syntaxis of line: Type id Name Surname
        Scanner sc = new Scanner(new StringReader(line));
        String type = sc.next();
        int id = sc.nextInt();
        String name = sc.next();
        String surname = sc.next();

        return new User(id, name, surname);
    }

//    public void registerUser (Users user){
//        usersList.add(user);
//    }


// метод show() здесь временно для проверки работы
    public void showId() {
            for (int i = 0; i < idList.size() ; i++) {

                System.out.println(idList.get(i));
            }
    }
    public void showUsers() {
        for (int i = 0; i < usersList.size() ; i++) {

            System.out.println(usersList.get(i));
        }
    }

    public static class User implements Users {

        public  int id;
        private String firstName;
        private String lastName;

        public User(int id, String firstName, String lastName) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
        }


        @Override
        public void registerUser() {

            if (usersList.size() == 0 || usersList.indexOf(this) == -1) {
                usersList.add(this);

            } else System.out.println("такой пользователь уже зарегистрирован");




        }

        @Override
        public void deleteUser() {

            usersList.remove(this);
        }

        @Override
        public void editeUser(String firstName, String lastName) {

           this.setFirstName(firstName);
           this.setLastName(lastName);

        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    '}';
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public int getId() {
            return id;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }
    }


}