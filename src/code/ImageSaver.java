package code;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

public class ImageSaver {
    public static int maxIter = 500;

    public static void saveFractal(int[][] iterArray, String baseFileName) {
        int height = iterArray.length;
        int width = iterArray[0].length;

        // 1. Создаем пустой "холст" в оперативной памяти
        // TYPE_INT_RGB означает, что мы используем стандартные цвета без прозрачности
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 2. Проходим циклом по нашему массиву чисел
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int iters = iterArray[y][x];

                int rgbColor;
                // 1. Создаем палитру ключевых цветов (как на картинке)
                Color[] palette = new Color[] {
                        new Color(0, 0, 40),       // Глубокий темно-синий
                        new Color(0, 100, 200),    // Яркий синий
                        new Color(255, 255, 255),  // Белый
                        new Color(255, 150, 0),    // Огненно-оранжевый
                        new Color(50, 0, 0),       // Темно-красный / черный
                };

                if (iters == maxIter) {
                    rgbColor = Color.BLACK.getRGB(); // Сердцевина фрактала остается черной
                } else {
                    // 2. Нормализуем значение итераций от 0.0 до 1.0
                    double t = (double) iters / maxIter;
                    t = Math.sqrt(t); // Наш нелинейный трюк

                    double position = t * (palette.length - 1);
                    int index = (int) position;

                    // БЕЗОПАСНОСТЬ: Гарантируем, что index не выйдет за пределы (максимум 3 для длины 5)
                    index = Math.min(index, palette.length - 2);

                    double fraction = position - index;

                    // Теперь index + 1 гарантированно будет максимум 4, что легально для массива из 5 элементов
                    Color c1 = palette[index];
                    Color c2 = palette[index + 1];

                    // 3. Плавно смешиваем два соседних цвета
                    double rRaw = c1.getRed() + fraction * (c2.getRed() - c1.getRed());
                    double gRaw = c1.getGreen() + fraction * (c2.getGreen() - c1.getGreen());
                    double bRaw = c1.getBlue() + fraction * (c2.getBlue() - c1.getBlue());

                    // БЕЗОПАСНОСТЬ: Жестко вжимаем значения в рамки от 0 до 255, округляя их
                    int r = Math.max(0, Math.min(255, (int) Math.round(rRaw)));
                    int g = Math.max(0, Math.min(255, (int) Math.round(gRaw)));
                    int b = Math.max(0, Math.min(255, (int) Math.round(bRaw)));

                    rgbColor = new Color(r, g, b).getRGB();
                }
                // 3. Красим конкретный пиксель на холсте
                image.setRGB(x, y, rgbColor);
            }
        }

        // 4. Сохраняем получившийся холст в файл на диске
        try {
            // 2. Работаем с папкой
            String dirName = "output_fractals";
            Path dirPath = Paths.get(dirName);

            if (Files.notExists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            // 3. Умный подбор имени файла (Защита от перезаписи)
            // baseFileName например "mandelbrot"
            String extension = ".png";
            Path filePath = dirPath.resolve(baseFileName + extension); // Итог: output_fractals/mandelbrot.png

            int counter = 1;
            // Пока файл по такому пути существует, меняем имя
            while (Files.exists(filePath)) {
                String newName = baseFileName + "_" + counter + extension; // mandelbrot_1.png
                filePath = dirPath.resolve(newName);
                counter++;
            }

            // 4. Сохраняем файл (ImageIO все еще требует объект старого класса File, переводим Path в File)
            File outputFile = filePath.toFile();
            // ImageIO сам поймет, как сжать данные, основываясь на расширении "png"
            ImageIO.write(image, "png", outputFile);
            System.out.println("Изображение успешно сохранено: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении изображения: " + e.getMessage());
        }
    }
}
