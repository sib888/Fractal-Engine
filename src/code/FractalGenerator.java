package code;

import code.enums.Formula;
import code.formulas.BurnungShipFormula;
import code.formulas.FractalFormula;
import code.formulas.JuliaFormula;
import code.formulas.MandelbrotFormula;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FractalGenerator {
    private FractalFormula currentFormula;

    private final int width;
    private final int height;
    private final int maxIter;

    public FractalGenerator(Formula type, int width, int height, int maxIter) {
        ChangeFromula(type);

        this.width = width;
        this.height = height;
        this.maxIter = maxIter;
    }

    public void ChangeFromula(Formula type) {
        if (type == Formula.Mandelbrot) currentFormula = new MandelbrotFormula();
        else if (type == Formula.Julia) currentFormula = new JuliaFormula();
        else if (type == Formula.BurningShip) currentFormula = new BurnungShipFormula();
        else currentFormula = null;
    }

    public int[][] generateFractal() throws Exception {
        int[][] image = new int[height][width];

        // 1. Создаем пул потоков. Считаем, сколько ядер есть у процессора ПК
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(cores);

        // Список "будущих" результатов (Future)
        List<Future<int[]>> futures = new ArrayList<>();

        // 2. Нарезаем картинку на задачи и отправляем в пул
        for (int y = 0; y < height; y++) {
            FractalTask task = new FractalTask(y, width, height, maxIter, currentFormula);
            Future<int[]> futureResult = executor.submit(task); // Поток забрал задачу в фон
            futures.add(futureResult);
        }

        // 3. Собираем результаты (метод future.get() ждет, пока поток досчитает)
        for (int y = 0; y < height; y++) {
            image[y] = futures.get(y).get();
        }

        // 4. Обязательно тушим пул потоков по окончании работы
        executor.shutdown();

        return image;
    }

    public void generateFractalSet(int count, int step, int offset) throws Exception {
        if (!(currentFormula instanceof MandelbrotFormula)) return;

        //MandelbrotFormula.setPivot(-0.7375447850, 0.1318252536);

        double currentZoom = 2 / (Math.pow(step, offset));
        for (int i = 0; i < count; i++) {
            MandelbrotFormula.zoom = currentZoom;
            var fractal = generateFractal();
            ImageSaver.saveFractal(fractal, "Mandelbrot");

            currentZoom /= step;
        }
    }

    public void generateFractalSet(int count, int step) throws Exception {
        generateFractalSet(count, step, 0);
    }
}
