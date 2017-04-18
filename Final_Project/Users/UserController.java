package Final_Project.Users;

import java.util.ArrayList;
import java.util.List;

public class UserController {


    private static List<Integer> idList = new ArrayList<>();
    private static List<Users> usersList = new ArrayList<>();

    public static int  lastId = 0;

    public static User createUser(String firstName, String lastName ){
            int id = lastId + 1;
            idList.add(id);
            lastId = id;

       return new User(id, firstName, lastName);

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