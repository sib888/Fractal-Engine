package code;

import code.enums.Formula;

public class Main {
    public static void main(String[] args) throws Exception {
        Formula formula = ConsoleManger.getOptionFromUser();

        int width = 1920;
        int height = 1080;
        int maxIter = 3000;
        ImageSaver.maxIter = maxIter;

        System.out.println("Начало расчета...");
        long start = System.currentTimeMillis();

        // Получаем тот самый массив чисел из пула потоков
        FractalGenerator generator = new FractalGenerator(formula, width, height, maxIter);

        if (formula == Formula.Mandelbrot) {
            generator.generateFractalSet(21, 5);
        }
        else {
            int[][] result = generator.generateFractal();
            ImageSaver.saveFractal(result, formula.toString());
        }

        long end = System.currentTimeMillis();
        System.out.println("Расчет окончен за: " + (end - start) + " мс");
    }
}
