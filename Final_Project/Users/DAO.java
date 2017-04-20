package Final_Project.Users;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DAO<T extends UserController.User> {
    private String path;

    DAO() {
        this.path = "users.txt";
    }

    DAO(String path) {
        this.path = path;
    }

    public List<UserController.User> toList() {
        List<UserController.User> list = new ArrayList<>();
        File file = new File(path);

        try {
            Scanner sc = new Scanner(new FileReader(file));
            while (sc.hasNext()) {
                String line = sc.nextLine();
                list.add(UserController.createUserByLine(line));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            return list;
        }
    }
}
