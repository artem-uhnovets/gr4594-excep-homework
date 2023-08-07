public class Main {
    public static void main(String[] args){
        // 1. Исключения блоков catch используем от частного к общему -> Throwable в конце
        // 2. Убираем FileNotFoundException - с файлом не работаем
        // 3. Если в последнем catch необходимо именно Throwable ex,
        // то в метод printSum можно немного переписать, чтобы выбрасывал исключение.
        // Как пример использовал ClassCastException, но так как ClassCastException сам
        // по себе Unchecked, то по идеи нет смысла throws, поэтому
        // решил просто указать throws Exception.
        try {
            int a = 90;
            int b = 3;
            System.out.println(a / b);
            printSum(23, 234);
            int[] abc = { 1, 2 };
            abc[3] = 9;
        } catch (NullPointerException ex) {
            System.out.println("Указатель не может указывать на null!");
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("Массив выходит за пределы своего размера!");
        } catch (Throwable ex) {
            System.out.println("Что-то пошло не так..." + ex.getClass().getSimpleName());
        }
    }

    public static void printSum(Object a, Object b) throws Exception {
        System.out.println((Integer) a + (Integer) b);
    }
}