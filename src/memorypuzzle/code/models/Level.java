package memorypuzzle.code.models;

/**
 * Класс-модель "Уровень", который отвечает за настройку игры
 *  id - уникальный индекс уровня в базе данных
 *  rows, columns - количество строк и столбцов
 *  recommendedVariety - рекомендованое разнообразие элементов
 *  size - размер высоты и ширины клетки, для того,
 *  чтобы было корректное отображение при выборе разных уровней
 */
public class Level {
    private int id;
    private int rows;
    private int columns;
    private int recommendedVariety;
    private int size;

    public Level(int rows, int columns, int recommendedVariety) {
        this.rows = rows;
        this.columns = columns;
        this.recommendedVariety = recommendedVariety;
    }

    public Level(int id, int rows, int columns, int recommendedVariety, int size) {
        this.id = id;
        this.rows = rows;
        this.columns = columns;
        this.recommendedVariety = recommendedVariety;
        this.size = size;
    }

    public Level(int id) {
        this(id, 0, 0, 0, 0) ;
    }

    public int getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRecommendedVariety() {
        return recommendedVariety;
    }

    public void setRecommendedVariety(int recommendedVariety) {
        this.recommendedVariety = recommendedVariety;
    }

    @Override
    public String toString() {
        return rows + " х " + columns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Level level = (Level) o;

        if (id != level.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
