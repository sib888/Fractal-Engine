package code.formulas;

public class BurnungShipFormula implements FractalFormula{
    @Override
    public int calculatePixel(int x, int y, int width, int height, int maxIter) {
        double cr = (x - width / 1.75) / (width / 3.5);
        double ci = (y - height / 2.5) / (height / 3.5);

        double zr = 0, zi = 0;
        int iter = 0;

        while (zr * zr + zi * zi < 4.0 && iter < maxIter) {
            double absZr = Math.abs(zr);
            double absZi = Math.abs(zi);

            double temp = absZr * absZr - absZi * absZi + cr;
            zi = 2.0 * absZr * absZi + ci;
            zr = temp;

            iter++;
        }

        return iter;
    }
}
