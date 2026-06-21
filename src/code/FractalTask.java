package code;

import code.formulas.FractalFormula;

import java.util.concurrent.Callable;

public class FractalTask implements Callable<int[]> {
    private final int row;
    private final int width;
    private final int height;
    private final int maxIter;
    private final FractalFormula fractalFormula;

    public FractalTask(int row, int width, int height, int maxIter, FractalFormula fractalFormula) {
        this.row = row;
        this.width = width;
        this.height = height;
        this.maxIter = maxIter;
        this.fractalFormula = fractalFormula;
    }

    @Override
    public int[] call() {
        int[] pixels = new int[width];
        for (int x = 0; x < width; x++) {
            int iter = fractalFormula.calculatePixel(x, row, width, height, maxIter);
            pixels[x] = iter;
        }
        return pixels;
    }
}
