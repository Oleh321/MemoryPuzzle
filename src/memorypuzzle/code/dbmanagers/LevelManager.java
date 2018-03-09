package memorypuzzle.code.dbmanagers;

import memorypuzzle.code.models.Level;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LevelManager {

    /**
     * Считываем из базы данных все уровни с рекомендованными или обязательными настройками
     * Предназначен для выбора настроей на форме "Settings"
     * @return список уровней
     */
    public List<Level> loadLevels(){
        List<Level> levels = new ArrayList<>();
        try {
            Connection conn = H2Connector.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM level;");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                levels.add(new Level(
                        rs.getInt("id"),
                        rs.getInt("rows"),
                        rs.getInt("columns"),
                        rs.getInt("recom_variety"),
                        rs.getInt("cell_size")
                ));
            }
            pstmt.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return levels;
    }

}
