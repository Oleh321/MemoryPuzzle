package memorypuzzle.code.models;

/**
 * Класс-модель "Счет", который отвечает за результаты игры
 *  id - уникальный индекс уровня в базе данных
 *  level - пройденный уровень
 *  variety - разнообразие элементов
 *  recordsman - имя игрока, которое он введет после установления рекорда
 *  steps - количество ходов, за которые он прошел уровень
 */
public class Score {
    private int id;
    private Level level;
    private int variety;
    private String recordsman;
    private int steps;

    public Score(int levelId, int variety, String recordsman, int steps) {
        this.id = id;
        this.level = new Level(levelId);
        this.variety = variety;
        this.recordsman = recordsman;
        this.steps = steps;
    }

    public Score(int id, int levelId, int variety, String recordsman, int steps) {
        this.id = id;
        this.level = new Level(levelId);
        this.variety = variety;
        this.recordsman = recordsman;
        this.steps = steps;
    }

    public Score(int id, Level level, int variety, String recordsman, int steps) {
        this.id = id;
        this.level = level;
        this.variety = variety;
        this.recordsman = recordsman;
        this.steps = steps;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int getVariety() {
        return variety;
    }

    public void setVariety(int variety) {
        this.variety = variety;
    }

    public String getRecordsman() {
        return recordsman;
    }

    public void setRecordsman(String recordsman) {
        this.recordsman = recordsman;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Score score = (Score) o;

        return id == score.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}

