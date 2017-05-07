package Shyrick;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DAOUser {

    private List<User> usersList = new ArrayList<>();
    private File file = new File("ShyrickUsersList.txt");

    public DAOUser() {
        readDB();
    }

    public void showUsers() {

        for (int i = 0; i < usersList.size(); i++) {

            System.out.println(usersList.get(i));
        }
    }

    public void readDB (){

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {

                    int id = (int) scanner.nextByte();
                    String login = scanner.next();
                    String firstName = scanner.next();
                    String lastName = scanner.next();
                    boolean isAdmin = scanner.nextBoolean();
                    addUserToList(id, login, firstName, lastName, isAdmin);

//                    System.out.print(getUserListSize());
//                    System.out.println(getUserFromList(getUserListSize()-1));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Успешно загружено " +  getUserListSize() + " пользователей");

    }

    public void writeInDB (){

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {

            for (int i = 0; i < usersList.size() ; i++) {
                if (i < usersList.size()-1 )  writer.write(usersList.get(i).userToStringForWrite()+"\n");
                else writer.write(usersList.get(i).userToStringForWrite());
            }

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addUserToList(int id, String login, String firstName, String lastName, boolean isAdmin){

        usersList.add(createUserFromFile(id, login, firstName, lastName, isAdmin));
    }

    private User createUserFromFile(int id, String login, String firstName, String lastName, boolean isAdmin){


        return new User(id, login, firstName, lastName, isAdmin);
    }

    public int getUserListSize(){

        return  usersList.size();
    }

    public User getUserFromList(int index){

        User user = usersList.get(index);
        return user;
    }

    public void addUserToList(User user){
        usersList.add(user);
    }

    public void removeUserFromList (User user){
        usersList.remove(user);
    }

    public User findById(int id){
        return usersList.stream().filter(u->u.getId()==id).findFirst().orElse(null);
    }

    public User findByLogin(String login){
        return usersList.stream().filter(u->u.getLogin().equals(login)).findFirst().orElse(null);
    }

    public boolean hasLogin(String login) {
        return usersList.stream().filter(user -> user.getLogin().equals(login)).findFirst().orElse(null)!= null;
    }


}
