package memorypuzzle.code.models;

/**
 * Класс-модель "Клетка", из которой состоят поля
 *  i,j - координаты клетки
 *  imagePath - путь к рисунку
 *  isOpen - флаг, которой служет для того, чтоб понять открыта ли клетка
 */
public class Cell {
    private int i;
    private int j;
    private String imagePath;
    private boolean isOpen;

    public Cell(int i, int j, String imagePath, boolean isOpen) {
        this.i = i;
        this.j = j;
        this.imagePath = imagePath;
        this.isOpen = isOpen;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        return imagePath != null ? imagePath.equals(cell.imagePath) : cell.imagePath == null;
    }

    @Override
    public int hashCode() {
        return imagePath != null ? imagePath.hashCode() : 0;
    }
}

