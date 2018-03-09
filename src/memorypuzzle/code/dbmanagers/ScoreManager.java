package memorypuzzle.code.dbmanagers;

import memorypuzzle.code.models.Level;
import memorypuzzle.code.models.Score;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ScoreManager {

    private Connection conn;

    public ScoreManager() {
        conn = H2Connector.getInstance().getConnection();
    }

    /**
     * Нахождение рекорда по размерам поля и разнообразию.
     * Предназначен для сравнения текущего рекорда с результатом игры
     * @param level -  размер уровня и разнообразии элементов
     * @return текущий рекорд
     */
    public Score getRecordByLevel(Level level) {
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT * FROM record WHERE id_level = ? AND variety = ?;");
            pstmt.setInt(1, level.getId());
            pstmt.setInt(2, level.getRecommendedVariety());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                 return new Score(rs.getInt("id"),
                         rs.getInt("id_level"),
                         rs.getInt("variety"),
                         rs.getString("name"),
                         rs.getInt("moves"));
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Запись новый рекорда в базу данных
     * Если не был установлен еще рекорд на такой карте с таким разнообразием, то записывается новый
     * @param score - подробное описание информации об игроке, размере карты,
     *             разнообразии элементов, и количества ходов
     * @return индекс записи или -1, если запись не удалась
     */
    public int addNewScore(Score score) {
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO  record (id_level, variety,name,moves) VALUES(?,?,?,?);");
            pstmt.setInt(1, score.getLevel().getId());
            pstmt.setInt(2, score.getVariety());
            pstmt.setString(3, score.getRecordsman());
            pstmt.setInt(4, score.getSteps());
            int ind = pstmt.executeUpdate();
            pstmt.close();

            return ind;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Обновляет рекорда в базу данных
     * Если был побит существующий рекорд на такой карте с таким разнообразием, то записывается новый
     * @param score - подробное описание информации об игроке, размере карты,
     *             разнообразии элементов, и количества ходов
     */
    public void updateScore(Score score) {
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE record SET id_level = ?, variety = ?,name = ?,moves = ? WHERE id = ?;");
            pstmt.setInt(1, score.getLevel().getId());
            pstmt.setInt(2, score.getVariety());
            pstmt.setString(3, score.getRecordsman());
            pstmt.setInt(4, score.getSteps());
            pstmt.setInt(5, score.getId());
            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Служит для вывода таблицы рекордсменов
     * @return список рекордов
     */
    public List<Score> loadScores(){
        List<Score> scores = new ArrayList<>();
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT r.id, r.id_level, l.rows, l.columns, r.variety, r.name, r.moves " +
                            "FROM record r INNER JOIN level l ON r.id_level = l.id;");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                scores.add(new Score(rs.getInt("id"),
                        new Level(rs.getInt("id_level"),
                                rs.getInt("rows"),
                                rs.getInt("columns"), 0,0),
                        rs.getInt("variety"),
                        rs.getString("name"),
                        rs.getInt("moves")));
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scores;
    }


}
