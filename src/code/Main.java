package code;

import code.enums.Formula;

public class Main {
    public static void main(String[] args) throws Exception {
        Formula formula = ConsoleManger.getOptionFromUser();

        int width = 1920;
        int height = 1080;
        int maxIter = 3000;
        ImageSaver.maxIter = maxIter;

        long start;

        FractalGenerator generator = new FractalGenerator(formula, width, height, maxIter);

        if (formula == Formula.Mandelbrot) {
            int count = ConsoleManger.getMandelbrotCount();
            int step = ConsoleManger.getMandelbrotStep();
            int offset = ConsoleManger.getMandelbrotOffset();

            System.out.println("Начало расчета...");
            start = System.currentTimeMillis();

            generator.generateFractalSet(count, step, offset);
        }
        else {
            System.out.println("Начало расчета...");
            start = System.currentTimeMillis();

            int[][] result = generator.generateFractal();
            ImageSaver.saveFractal(result, formula.toString());
        }

        long end = System.currentTimeMillis();
        System.out.println("Расчет окончен за: " + (end - start) + " мс");
    }
}
