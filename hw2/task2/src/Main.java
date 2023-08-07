public class Main {
    public static void main(String[] args) {
        // если исходить из того, что нужно поймать исключение при делении на ноль
        // и должно быть введено именно значение int массива индекса 8,
        // то переписал код следующим образом
        try {
            int d = 0;
            int[] arr = new int[9];
            double catchedRes1 = arr[8] / d;
            System.out.println("catchedRes1 = " + catchedRes1);
        } catch (ArithmeticException e) {
            System.out.println("Catching exception: " + e);
        }
    }
}