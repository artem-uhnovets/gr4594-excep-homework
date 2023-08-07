import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Так как в условии не было указано, что именно понимается под пустой строкой,
        // тогда самостоятельно предполагаю, что множество пробелом так же являются пустой строкой.
        System.out.print("Введите любое значение -> ");
        String stroka = new Scanner(System.in).nextLine();
        if (stroka.trim().equals("")) {
            throw new RuntimeException("Пустые строки вводить нельзя!");
        } else {
            System.out.println("Вы ввели -> "+stroka);
        }
    }
}