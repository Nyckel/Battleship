package common;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class DisplayHelper {
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;

    public static int CELL_SIZE = 30;
    public static final int GRID_SIZE = 11 * CELL_SIZE + 1; // +1 is for grid line


    public static void drawGrid(Canvas cv) {
        GraphicsContext gc = cv.getGraphicsContext2D();

        gc.setFill(Color.BLACK);
        gc.setFont(new Font(15));
        gc.setStroke(Color.BLACK);

        int xPos = CELL_SIZE + 10;
        for (char letter = 'A'; letter <= 'J'; letter++) {
            gc.fillText(String.valueOf(letter), xPos, 20, CELL_SIZE);
            xPos+= CELL_SIZE;
        }
        int yPos = 50;
        for (int i = 1; i < 11; i++) {
            gc.fillText(String.valueOf(i), 10, yPos, CELL_SIZE);
            yPos+= CELL_SIZE;
        }

        // Vertical lines
        for(int i = 0 ; i < cv.getWidth() ; i+=CELL_SIZE){
            gc.strokeLine(i, 0, i, cv.getHeight() - (cv.getHeight()%CELL_SIZE));
        }

        // Horizontal lines
        for(int i = 0 ; i < cv.getHeight() ; i+=CELL_SIZE){
            gc.strokeLine(0, i, cv.getWidth(), i);
        }
    }


    public static void colorCells(Canvas canvas, int x, int y) {
        if (!(x >= 1 && x <= 10 && y >= 1 && y <= 10 )) {
            return;
        }
        else {
            GraphicsContext rect = canvas.getGraphicsContext2D();
            rect.setFill(Color.BLACK);
            rect.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }

    public static void clearCells(Canvas canvas, int x, int y){
        if (!(x >= 1 && x <= 10 && y >= 1 && y <= 10 )) {
            return;
        }
        else {
            GraphicsContext rect = canvas.getGraphicsContext2D();
            rect.setFill(Color.WHITE);
            rect.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }
}
