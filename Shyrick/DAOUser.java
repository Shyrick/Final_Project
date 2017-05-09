package Shyrick;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DAOUser {

    private List<User> usersList = new ArrayList<>();
    private File file = new File("src\\Shyrick\\UsersList.txt");


    public DAOUser() {
        readDB();
    }

    /**
     * Метод выводит в консоль список зарегиситрированных пользователей
     */
    public void showUsers() {

        for (int i = 0; i < usersList.size(); i++) {

            System.out.println(usersList.get(i));
        }
    }

    /**
     * Метод считывает базу данных из текстового файла UserList.txt и записывает пользователй в список
     * методом addUserToList()
     */
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
        //System.out.println("Успешно загружено " +  getUserListSize() + " пользователей");

    }

    /**
     * Метод перезаписывает список пользователей в тексотовый файл UserList.txt
     */
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

    /**
     * Метод создает нового пользователя методом createUserFromFile() и добавляет его в список пользователей usersList
     */
    public void addUserToList(int id, String login, String firstName, String lastName, boolean isAdmin){

        usersList.add(createUserFromFile(id, login, firstName, lastName, isAdmin));
    }

    /**
     * Метод создает нового пользователя по данным, считаным из файла
     * @param id - id пользователя
     * @param login логин пользователя
     * @param  firstName - имя пользователя
     * @param lastName  - фамилия поьлзователея
     * @param isAdmin =true если пользователь является администратором
     */
    private User createUserFromFile(int id, String login, String firstName, String lastName, boolean isAdmin){

        return new User(id, login, firstName, lastName, isAdmin);
    }

    /**
     * Метод возвращает количество зарегистрированных пользователей (размер списка пользователей)
     */
    public int getUserListSize(){

        return  usersList.size();
    }

    /**
     * Метод возвращает пользователя с указанным индексом из списка зарегистрированных пользователей
     *  @param index - индекс пользователя в списке
     */
    public User getUserFromList(int index){

        User user = usersList.get(index);
        return user;
    }

    /**
     * Метод добавляет пользователя в список пользователей
     */
    public void addUserToList(User user){
        usersList.add(user);
    }

    /**
     * Метод удаляет пользователя из списка пользователей
     */
    public void removeUserFromList (User user){
        usersList.remove(user);
    }

    /**
     * Метод возвращает пользователя с указанным id
     * @param id - id пользователя
     */
    public User findById(int id){
        return usersList.stream().filter(u->u.getId()==id).findFirst().orElse(null);
    }

    /**
     * Метод возвращает пользователя с указанным логином
     * @param login логин пользователя
     */
    public User findByLogin(String login){
        return usersList.stream().filter(u->u.getLogin().equals(login)).findFirst().orElse(null);
    }

    public boolean hasLogin(String login) {
        return usersList.stream().filter(user -> user.getLogin().equals(login)).findFirst().orElse(null)!= null;
    }


}
