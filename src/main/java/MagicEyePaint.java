import processing.core.PApplet;

/**
 * Created by dusti on 3/22/2017.
 */
public class MagicEyePaint extends PApplet {
    public static final int DRAW_AREA_SIZE = 500;
    public static final int PATTERN_REPEAT_TIMES = 6;
    public static final int PATTERN_WIDTH = (int) ((DRAW_AREA_SIZE * 1.2) / (PATTERN_REPEAT_TIMES - 1));

    private int[][] pattern;

    @Override
    public void settings() {
        size(DRAW_AREA_SIZE + PATTERN_WIDTH * PATTERN_REPEAT_TIMES, DRAW_AREA_SIZE);
    }

    @Override
    public void setup() {
        pattern = getRandomRGBPattern(DRAW_AREA_SIZE, PATTERN_WIDTH);
        background(0);
    }

    private int[][] getRandomRGBPattern(int height, int width) {
        int[][] pattern = new int[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                pattern[row][col] = color(random(255), random(255), random(255));
            }
        }
        return pattern;
    }

    @Override
    public void draw() {
        if (mousePressed) {
            stroke(255);
            strokeWeight(30);
            line(mouseX, mouseY, pmouseX, pmouseY);
        }

        loadPixels();
        //drawSineWaveInDrawArea();
        drawPattern();
        extendPattern();
        updatePixels();
    }

    private void drawSineWaveInDrawArea() {
        float centerX, centerY;
        centerX = centerY = DRAW_AREA_SIZE / 2;
        for (int row = 0; row < DRAW_AREA_SIZE; row++) {
            for (int col = 0; col < DRAW_AREA_SIZE; col++) {
                pixels[row * width + col] = color(map(sin(dist(row, col, centerY, centerX) / 10f), -1, 1, 0, 255));
            }
        }
    }
    private void extendPattern() {
        int drawAreaX = 0;
        for (int x = DRAW_AREA_SIZE + 2 * PATTERN_WIDTH; x < width; x++) {
            for (int y = 0; y < DRAW_AREA_SIZE; y++) {
                if (drawAreaX < DRAW_AREA_SIZE) {
                  //  System.out.println("happening");
                    int color = pixels[y * width + drawAreaX];
                    int offset = (int) map(color, color(0), color(255), 10, 0);

                    pixels[y  * width + x] = pixels[y  * width + x - offset - PATTERN_WIDTH];
              //      System.out.println(offset);

                } else {
                    pixels[y  * width + x] = pixels[y  * width + x - PATTERN_WIDTH];
                }
            }
            drawAreaX++;
        }
    }

    private void drawPattern() {
        for (int patternRow = 0; patternRow < DRAW_AREA_SIZE; patternRow++) {
            for (int patternCol = 0; patternCol < PATTERN_WIDTH; patternCol++) {
                int pixelsRow = patternRow;
                int pixelsCol = patternCol + DRAW_AREA_SIZE;
                pixels[pixelsRow * width + pixelsCol] = pattern[patternRow][patternCol];
                pixels[pixelsRow * width + pixelsCol + PATTERN_WIDTH] = pattern[patternRow][patternCol];

            }
        }
    }

    public static void main(String[] args) {
        PApplet.main("MagicEyePaint");
    }
}
