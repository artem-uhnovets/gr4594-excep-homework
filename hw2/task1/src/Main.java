import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        float number = getFloatNum();
        System.out.println("Entered number is - "+number);
        System.out.println("Type of number is - "+getType(number));


    }

    private static float getFloatNum() {
        String numStr = "";
        while (!isNum(numStr)) {
            System.out.print("Enter a float number pls -> ");
            numStr = new Scanner(System.in).nextLine();
        }
        return Float.parseFloat(numStr);
    }

    private static Boolean isNum(String numStr) {
        try {
            Float.parseFloat(numStr);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    private static String getType(Object obj) {
        return obj.getClass().getName();
    }
}