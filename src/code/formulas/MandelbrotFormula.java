package code.formulas;

public class MandelbrotFormula implements FractalFormula{
    public static double zoom = 0.001; // Чем МЕНЬШЕ это число, тем глубже приближение
    static double centerX = -0.7436438870371587;
    static double centerY = 0.13182590420531197;

    public static void setPivot(double x, double y) {
        centerX = x;
        centerY = y;
    }

    @Override
    public int calculatePixel(int x, int y, int width, int height, int maxIter) {
        double aspectRatio = (double) width / height;

        double cr = centerX + (x - width / 2.0) * zoom * aspectRatio / width;
        double ci = centerY + (y - height / 2.0) * zoom / height;

        double zr = 0, zi = 0;
        int iter = 0;
        while (zr * zr + zi * zi < 4.0 && iter < maxIter) {
            double temp = zr * zr - zi * zi + cr;
            zi = 2.0 * zr * zi + ci;
            zr = temp;
            iter++;
        }

        return iter;
    }
}
