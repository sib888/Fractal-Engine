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
                System.out.println("Вы не правы.");
                scanner.next();
                continue;
            }
            if (!options.containsKey(o)) {
                System.out.println("Нет такой опции.");
            }
            else option = o;
        }
        System.out.println("------------------");

        return options.get(option);
    }
}
