package memorypuzzle.code;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import memorypuzzle.code.models.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с полем (матрицей клеток)
 */
public class Field {
    private static final int IMG_INDENT = 5;
    private static final String PATH_IMG_DIR = "memorypuzzle//images//";

    private int rows;
    private int colunms;
    private int size;

    private Cell[][] cells;

    public Field(int rows, int colunms, int size) {
        this.rows = rows;
        this.colunms = colunms;
        this.size = size;
    }

    public void initField(List<String> allCells) {
        cells = new Cell[rows][colunms];
        int indImg = 0;
        for(int i =0; i < rows; i++)
            for(int j = 0; j < colunms; j++){
                cells[i][j] = new Cell(i, j, allCells.get(indImg), false);
                indImg++;
            }
    }

    /**
     * Сравнение открытых ячеек
     * @return true - рисунки в них одинаковые
     */
    public boolean isEqualOpenedCells(){
        List<Cell> couple = new ArrayList<>();
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < colunms; j++)
                if(cells[i][j] != null && cells[i][j].isOpen())
                    couple.add(cells[i][j]);

        if(couple.size()!= 2) return false;

        return couple.get(0).equals(couple.get(1));
    }

    /**
     * Удаление открытых клеток
     */
    public void deleteOpenedCells(){
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < colunms; j++)
                if(cells[i][j] != null && cells[i][j].isOpen())
                    cells[i][j] = null;
    }

    /**
     * Открытие клетки по индексам
     */
    public boolean openCell(int i, int j){
        boolean open = false;
        if(cells[i][j] != null) {
            if(!cells[i][j].isOpen())
                open = true;
            cells[i][j].setOpen(true);
        }
        return open;
    }

    /**
     * Закрытие всех клеток
     */
    public void closeAllCells(){
        for(int i =0; i < rows; i++)
            for(int j = 0; j < colunms; j++)
                if(cells[i][j]!=null) cells[i][j].setOpen(false);
    }

    /**
     * Получение количества открытых клеток
     */
    public int getOpendAmount(){
        int opens = 0;
        for(int i =0; i < rows; i++)
            for(int j = 0; j < colunms; j++)
                if(cells[i][j] != null && cells[i][j].isOpen())
                    opens++;
        return opens;
    }

    /**
     * Отрисовка поля
     */
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0,
                gc.getCanvas().getWidth(),
                gc.getCanvas().getHeight());
        gc.setFill(Color.DARKGRAY);
        for(int i = 0; i < rows; i++)
            for (int j = 0; j < colunms; j++) {
                if (cells[i][j] == null)
                    continue;
                if (cells[i][j].isOpen()) {
                    gc.strokeRect(j * size, i * size, size - 1, size - 1);
                    gc.drawImage(new Image(PATH_IMG_DIR + cells[i][j].getImagePath()),
                            j * size + IMG_INDENT,
                            i * size + IMG_INDENT,
                            size - 2 * IMG_INDENT,
                            size - 2 * IMG_INDENT);
                } else {
                    gc.fillRect(j * size, i * size, size - 1, size - 1);
                }
            }
    }

    /**
     * Проверка на остаток клеток
     */
    public boolean isAllOpen(){
        for(int i = 0; i < rows; i++)
            for (int j = 0; j < colunms; j++)
                if (cells[i][j] != null && !cells[i][j].isOpen())
                    return false;
        return true;
}

    /**
     * Получение клетки по индексу
     */
    public Cell getCell(int i, int j) {
        return cells[i][j];
    }
}
