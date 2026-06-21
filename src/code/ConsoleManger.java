package code;

import code.enums.Formula;

import java.util.HashMap;
import java.util.Scanner;

public class ConsoleManger {
    public static Formula getOptionFromUser() {
        Scanner scanner = new Scanner(System.in);

        HashMap<Integer, Formula> options = new HashMap<>();
        options.put(1, Formula.Mandelbrot);
        options.put(2, Formula.Julia);
        options.put(3, Formula.BurningShip);
        int option = 0;

        while (!options.containsKey(option)) {
            System.out.print("""
                    Выберите фрактал:
                    1. Фрактал Мандельброта
                    2. Множество Жюлиа
                    3. Горящий Корабль
                    (Введите номер опции):""");
            int o;
            try {
                o = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Нужно ввести целое число.");
                scanner.next();
                continue;
            }
            if (!options.containsKey(o)) {
                System.out.println("Опции с номером " + o + " не найдено.");
            }
            else option = o;
        }
        System.out.println("------------------");

        return options.get(option);
    }

    public static int getMandelbrotCount() {
        return getPositiveNum("Введите число изображений:");
    }

    public static int getMandelbrotStep() {
        return getPositiveNum("Введите шаг(множитель) приближения:");
    }

    public static int getMandelbrotOffset() {
        return getPositiveNum("Введите сдвиг N (фрактал предварительно приблизится N РАЗ):");
    }

    private static int getPositiveNum(String inputMessage) {
        Scanner scanner = new Scanner(System.in);
        int num = -1;

        while (num < 0) {
            System.out.print(inputMessage);
            int o;
            try {
                o = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Нужно ввести положительное целое число.");
                scanner.next();
                continue;
            }
            if (o < 0) System.out.println("Нужно ввести положительное целое число.");
            else num = o;
        }

        return num;
    }
}
