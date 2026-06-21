package code.formulas;

public class JuliaFormula implements FractalFormula{
    @Override
    public int calculatePixel(int x, int y, int width, int height, int maxIter) {
        double zr = (x - width / 2.0) / (width / 4.0);
        double zi = (y - height / 2.0) / (height / 4.0);

        double cr = -0.7;
        double ci = 0.27015;

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
