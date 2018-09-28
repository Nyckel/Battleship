package common;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ships.Ship;

import java.util.ArrayList;

public class DisplayHelper {
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;

    public static int CELL_SIZE = 30;
    public static final int GRID_SIZE = 11 * CELL_SIZE + 1; // +1 is for grid line


    public static Position getClickPosition(MouseEvent event) throws Exception {
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (x < CELL_SIZE || x > GRID_SIZE || y < CELL_SIZE || y > GRID_SIZE) {
            throw new Exception("Click out of grid");
        }
        return new Position((x - x%CELL_SIZE)/CELL_SIZE, (y - y%CELL_SIZE)/CELL_SIZE);
    }

    public static void drawGrid(Canvas cv) {
        GraphicsContext gc = cv.getGraphicsContext2D();

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, cv.getWidth(), cv.getHeight());

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


    public static void colorCell(Canvas canvas, Position pos, Color color) {
        if (!(pos.x >= 1 && pos.x <= 10 && pos.y >= 1 && pos.y <= 10 )) {
            return;
        }

        GraphicsContext rect = canvas.getGraphicsContext2D();
        rect.setFill(color);
        rect.fillRect(pos.x * CELL_SIZE + 1, pos.y * CELL_SIZE + 1, CELL_SIZE -2, CELL_SIZE -2);
    }

    public static void colorCells(Canvas canvas, ArrayList<Position> positions, Color color) {
        for (Position pos : positions) colorCell(canvas, pos, color);
    }

    public static void clearAllCells(Canvas canvas) {
        for (int y = 1; y < 11; y++) {
            for (int x = 1; x < 11; x++) {
                clearCell(canvas, new Position(x, y));
            }
        }
    }

    public static void clearCell(Canvas canvas, Position pos){
        colorCell(canvas, pos, Color.WHITE);
    }

    public static void addCrossToCell(Canvas cv, Position pos, Color color) {
        GraphicsContext gc = cv.getGraphicsContext2D();

        gc.setStroke(color);
        int minXPos = pos.x * CELL_SIZE + 1;
        int maxXPos = minXPos + CELL_SIZE - 2;
        int minYPos = pos.y * CELL_SIZE + 1;
        int maxYPos = minYPos + CELL_SIZE - 2;

        gc.strokeLine(minXPos, minYPos, maxXPos, maxYPos);
        gc.strokeLine(minXPos, maxYPos, maxXPos, minYPos);

    }

}
