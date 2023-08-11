import java.io.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        start();

    }

    private static void start() {
        while (true) {
            optionMenu();
            int option = 0;
            try {
                option = Integer.parseInt(new Scanner(System.in).nextLine());
                if (option < 1 || option > 3) {
                    throw new WrongOptionException("Неверно выбрано действие!");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            switch (option) {
                case 1 -> randomGenerate();
                case 2 -> manualAdd();
                case 3 -> System.exit(0);
//                default -> throw new WrongOptionException("Неверно выбрано действие!");
            }
        }
    }

    private static void manualAdd() {
        String newItem = inputByUser();
        checkInputData(newItem);
        try {
            saveToFile(newItem);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void randomGenerate() {
        System.out.println("Было сгенерировано:");
        for (int i = 0; i < 10; i++) {
            String newItem = randomUser();
            checkInputData(newItem);
            try {
                saveToFile(newItem);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(newItem);
        }
    }

    private static void optionMenu() {
        System.out.println("Выберите действие:\n" +
                            "1. Случайная генерация 10 записей\n" +
                            "2. Ручной ввод\n" +
                            "3. Выход");
    }

    private static String randomUser() {
        int year = getRandomNum(1900, 2023);
        int month = getRandomNum(1, 12);
        int day = validMaxDay(year, month);

        String yearStr = new DecimalFormat( "0000" ).format(year);
        String monthStr = new DecimalFormat( "00" ).format(month);
        String dayStr = new DecimalFormat( "00" ).format(day);

        String phoneNum = getRandomPhoneNum();

        String gender = getRandomNum(1,2) == 1 ? "f" : "m";

        String fullname = getRandomFullname(gender);

        return String.format("%s %s.%s.%s %s %s",
                fullname, dayStr, monthStr, yearStr, phoneNum, gender);
    }

    private static String getRandomFullname(String gender) {
        List<String> lastnamesMale = List.of("Иванов","Петров", "Сидоров", "Смирнов", "Васильев",
                "Кузнецов", "Сергеев", "Андреев");
        List<String> lastnamesFemale = List.of("Иванова","Петрова", "Сидорова", "Смирнова", "Васильева",
                "Кузнецова", "Сергеева", "Андреева");

        List<String> firstnamesMale = List.of("Александр","Максим", "Михаил", "Марк", "Иван",
                "Артем", "Лев", "Дмитрий");
        List<String> firstnamesFemale = List.of("София","Анна", "Мария", "Алиса", "Ева",
                "Виктория", "Полина", "Варвара");

        List<String> middlenamesMale = List.of("Степанович","Иванович", "Егорович", "Андреевич",
                "Алексеевич", "Сергеевич", "Васильевич", "Борисович");
        List<String> middlenamesFemale = List.of("Андреевна","Матвеевна", "Тимофеевна", "Алексеевна",
                "Сергеевна", "Николавена", "Ивановна", "Степановна");

        Random rnd = new Random();
        if (gender.equals("f")) {
            return lastnamesFemale.get(rnd.nextInt(8))+" "+
                    firstnamesFemale.get(rnd.nextInt(8))+" "+
                    middlenamesFemale.get(rnd.nextInt(8));
        } else {
            return lastnamesMale.get(rnd.nextInt(8))+" "+
                    firstnamesMale.get(rnd.nextInt(8))+" "+
                    middlenamesMale.get(rnd.nextInt(8));
        }
    }

    private static String getRandomPhoneNum() {
        return String.format("%s%s%s%s%s%s%s%s%s%s",
                getRandomNum(1,9),
                getRandomNum(1,9),
                getRandomNum(1,9),
                getRandomNum(1,9),
                getRandomNum(1,9),
                getRandomNum(1,9),
                getRandomNum(1,9),
                getRandomNum(1,9),
                getRandomNum(1,9),
                getRandomNum(1,9));
    }

    private static int getRandomNum(int min, int max) {
        Random random = new Random();
        return random.nextInt((max+1) - min) + min;
    }

    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    private static int validMaxDay(int year, int month) {
        int maxDay = 31;
        switch (month) {
            case 4, 6, 9, 11 -> maxDay = 30;
            case 2 -> {
                if (isLeapYear(year)) {
                    maxDay = 29;
                } else { maxDay = 28; }
            }
        }
        return getRandomNum(1, maxDay);
    }

    private static void saveToFile(String newItem) throws IOException {
        String lastname = newItem.split(" ")[0].trim();
        String fileName = lastname.concat(".txt");
        File itemFile = new File(fileName);
        boolean isItemExist = false;

        if (itemFile.createNewFile()) {
            System.out.println("Создание нового файла "+fileName);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fileName));
             FileWriter fw = new FileWriter(fileName, true);) {
            String str = null;
            while ((str = br.readLine()) != null) {
                if (str.equalsIgnoreCase(newItem)) {
                    isItemExist = true;
                }
            }
            if (!isItemExist) {
                fw.write(newItem+"\n");
            }
        }
    }

    private static void checkInputData(String newItem) {
        checkFullname(newItem);
        checkDate(newItem);
        checkPhoneNumber(newItem);
        checkGender(newItem);
    }

    private static void checkGender(String newItem) {
        String gender = newItem.split(" ")[5].trim();
        if (!gender.equalsIgnoreCase("f") && !gender.equalsIgnoreCase("m")) {
            throw new WrongDataException("Некорректно введен пол");
        }
    }

    private static void checkPhoneNumber(String newItem) {
        String phoneNumber = newItem.split(" ")[4].trim();

        if (!phoneNumber.matches("\\d{10}")) {
            throw new WrongDataException("Некорректно введен номер телефона");
        }

    }

    private static void checkDate(String newItem) {
        String date = newItem.split(" ")[3].trim();
        String day = date.split("\\.")[0];
        String month = date.split("\\.")[1];
        String year = date.split("\\.")[2];

        if (!day.matches("\\d{2}")) {
            throw new WrongDataException("Некорректно введена дата - день!");
        }

        if (!month.matches("\\d{2}")) {
            throw new WrongDataException("Некорректно введена дата - месяц!");
        }
        // для года конечно можно не делать ограничение в 4 цифры, но применим такую условность
        if (!year.matches("\\d{4}")) {
            throw new WrongDataException("Некорректно введена дата - год!");
        }

    }

    private static void checkFullname(String newItem) {
        String lastname = newItem.split(" ")[0].trim();
        String firstname = newItem.split(" ")[1].trim();
        String middlename = newItem.split(" ")[2].trim();

        if (lastname.matches("[0-9]+") || lastname.equals("")) {
            throw new WrongDataException("Фамилия введена неверно!");
        }

        if (firstname.matches("[0-9]+") || firstname.equals("")) {
            throw new WrongDataException("Имя введено неверно!");
        }

        if (middlename.matches("[0-9]+") || middlename.equals("")) {
            throw new WrongDataException("Отчество введено неверно!");
        }

    }

    private static String inputByUser() {
        System.out.println("Введите одной строкой через пробел следущие данные:");
        System.out.println("Фамилия Имя Отчество");
        System.out.println("дата рождения в формате dd.mm.yyyy");
        System.out.println("номер телефона - число без 8 и без +7");
        System.out.println("пол - символ латиницей f или m");
        return checkInputSize(new Scanner(System.in).nextLine());
    }

    private static String checkInputSize(String newItem) {
        String[] newItemArr = newItem.trim().split(" ");
        if (newItemArr.length != 6) {
            throw new InputSizeException("Введено не верное количество данных, " +
                                    "ожидается 6, введено " + newItemArr.length);
        }
        return newItem;
    }
}