package Final_Project.Users;

import java.util.Scanner;

public class HotelManagementSystem {
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        UserController userController = new UserController();
        HotelManagementSystem hMS = new HotelManagementSystem();

        Scanner scanner = new Scanner(System.in);
        String str = "";

        System.out.println("Available commands: " +
                "\n1. find hotels " +
                "\n2. register user" +
                "\n3. edit hotels");
        do{
            str = scanner.nextLine();
            switch (str) {
                case "find hotels":
                case "1":
                    hMS.findHotels(getYear(), getMonth(), getDay(), getDuration(), getCity());
                    System.out.println("find hotels");      // TODO   - then should be removed
                    break;
                case "register user":
                case "2":
                    System.out.println("register user");
                    break;
                case "edit hotels":
                case "3":
                    System.out.println("edit hotels");
                default:
            }
        }while (!str.equals("quit"));
    }

    private void findHotels(int year, int month, int day, int duration, String city) {

    }

    private static String getCity() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("City: ");
        return scanner.next();
    }

    private static int getDuration() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Duration (Days): ");
        return scanner.nextInt();
    }

    private static int getDay() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Since (D): ");
        return scanner.nextInt();
    }

    private static int getMonth() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Since (M): ");
        return scanner.nextInt();
    }

    private static int getYear() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Since (Y): ");
        return  scanner.nextInt();
    }
}
