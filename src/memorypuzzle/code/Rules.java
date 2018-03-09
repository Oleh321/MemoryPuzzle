package memorypuzzle.code;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import memorypuzzle.code.dbmanagers.ScoreManager;
import memorypuzzle.code.models.Level;
import memorypuzzle.code.models.Score;

import java.io.File;
import java.util.*;

public class Rules {
    private Field field;
    private GraphicsContext graphicsContext; // Класс для рисования на canvas

    private Level currentLevel;

    private int steps;
    private int couples;

    /**
     *  Начальная инициализация правил с передачей в него текущего уровня
     *  для возможности правильной отрисовки на форме игры.
     */
    public Rules(Level level, GraphicsContext gc){
        currentLevel = level;
        graphicsContext = gc;
        field = new Field(level.getRows(),
                level.getColumns(),
                level.getSize());

        steps = 0;
        couples = 0;

        int cells = level.getColumns()*level.getRows();
        int variety = level.getRecommendedVariety();
        loadGameField(cells, variety);
    }

    /**
     *  Рандомное заолнение картинками.
     *  Передача списка картинок в поле.
     */
    private void loadGameField(int cells, int variety) {
       List<String> allCells = new ArrayList<>();
       List<String> varietyImages = loadSelectedFilepathis(variety);
       for(int i = 0; i < cells/2; i++){
           allCells.add(varietyImages.get(i%variety));
           allCells.add(varietyImages.get(i%variety));
       }

       Collections.shuffle(allCells);
       field.initField(allCells);

    }

    // Возращение списка картинок по заданному количеству
    private List<String> loadSelectedFilepathis(int amount){
        List<String> filepathis = new ArrayList<>();

        File dir = new File("src//memorypuzzle//images");
        for(File f : dir.listFiles())
            filepathis.add(f.getName());

        Collections.shuffle(filepathis);
        return filepathis.subList(0, amount);
    }

    // Отрисовка поля
    public void paint(){
        field.draw(graphicsContext);
    }

    public void clickedField(int i, int j) {
        // Если выбрано уже удаленное поле или открытое одинаковое, то мы не делаем никаких действий
        if(field.getCell(i,j) == null ||
                (field.isEqualOpenedCells() && field.getCell(i,j).isOpen())) return;
        // Если открыто 2 картинки
        if(2==field.getOpendAmount()) {
            // И они одинаковые
            if(field.isEqualOpenedCells()) {
                // Тогда мы их удаляем
                field.deleteOpenedCells();
                couples++;
            }
            // А в другом случае - мы закрываем все картинки
            field.closeAllCells();
        }
        if(field.openCell(i,j))
            steps++;
        paint();

        if(isWin()){
            couples++;
        }
    }

    private ScoreManager scoreManager;

    /**
     * Проверяем не побит ли рекорд.
     * Если рекорд побит(текушщий уровень пройден за меньшее число ходов, чем в рекорде)
     * или его еще нету - то вызывается диалог для ввода имени игрока для отображении в рекорде
     */
    public void finishGame(){
        scoreManager = new ScoreManager();
        Score record = scoreManager.getRecordByLevel(currentLevel);

        if(record == null || record.getSteps() > steps){
            // Побит или или поставлен рекорд
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Рекорд");
            dialog.setHeaderText("Вы установили рекорд.");
            dialog.setContentText("Введите имя:");
            Optional<String> result = dialog.showAndWait();

            Score score = new Score(currentLevel.getId(),
                    currentLevel.getRecommendedVariety(),
                    "", steps);

            result.ifPresent(name->{
                score.setRecordsman(name);
                if(record == null) {
                    scoreManager.addNewScore(score);
                } else {
                    score.setId(record.getId());
                    scoreManager.updateScore(score);
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Поздравляю");
            alert.setHeaderText("Вы собрали все пары.");
            alert.show();
        }
    }

    public boolean isWin(){
        return field.isAllOpen();
    }

    public int getSteps() {
        return steps;
    }

    public int getCouples() {
        return couples;
    }
}

