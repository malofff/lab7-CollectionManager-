package Server.datebase;

import Server.collection.ServerHumanBeing;
import exceptions.EnumNotFoundException;
import exceptions.InvalidFieldException;
import exceptions.SQLNoDataException;
import exceptions.SQLUniqueException;
import general.IO;
import validation.HumanBeingBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.concurrent.CopyOnWriteArrayList;

public class PostgreHumanBeingDataBase implements HumanBeingDataBase {
    private final HumanBeingBuilder builder;

    public PostgreHumanBeingDataBase(HumanBeingBuilder builder) {
        this.builder = builder;
    }

    @Override
    public ServerHumanBeing getHumanBeing(long id) throws SQLNoDataException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM humanbeings WHERE ID = ?")) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            return buildHumanBeing(rs);
        } catch (SQLException e) {
            throw new SQLNoDataException();
        }
    }

    @Override
    public int getNextId() throws SQLNoDataException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT MAX(id) FROM humanbeings")) {
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt(1) + 1;
        } catch (SQLException e) {
            throw new SQLNoDataException();
        }
    }

    @Override
    public void insertHumanBeing(ServerHumanBeing humanBeing) throws Exception {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO humanbeings VALUES (?,?,?,?,?,?,?,?,?,?)")) {
            initStatement(statement, humanBeing);
            statement.execute();
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public boolean deleteHumanBeingsOfUser(String username) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM humanbeings WHERE username = ?")) {
            statement.setString(1, username);
            statement.execute();
            return true;
        } catch (SQLException e) {
            IO.errPrint(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteHumanBeingById(long id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM humanbeings WHERE id = ?")) {
            statement.setInt(1, (int) id);
            statement.execute();
            return true;
        } catch (SQLException e) {
            IO.errPrint(e.getMessage());
            return false;
        }
    }

    @Override
    public void updateHumanBeing(long id, ServerHumanBeing humanBeing) throws SQLNoDataException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE humanbeings SET " +
                     "name = ?," +
                     "coordinate_x = ?," +
                     "coordinate_y = ?," +
                     "creation_date = ?" +
                     "realHero = ?," +
                     "has_Toothpick = ?," +
                     "impact_speed = ?," +
                     "weapon_type = ?," +
                     "mood = ?," +
                     "username = ? " +
                     "WHERE id = ?")) {
            initStatement(statement, humanBeing);
            statement.setLong(11, id);
            statement.execute();
        } catch (SQLException e) {
            throw new SQLNoDataException();
        }
    }

    @Override
    public CopyOnWriteArrayList<ServerHumanBeing> getAll() throws SQLNoDataException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM humanbeings")) {
            CopyOnWriteArrayList<ServerHumanBeing> arrayList = new CopyOnWriteArrayList<>();
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                arrayList.add(buildHumanBeing(rs));
            }
            return arrayList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLNoDataException();
        }
    }


    private ServerHumanBeing buildHumanBeing(ResultSet rs) throws SQLException {
        try {
            builder.setName(rs.getString(1));
            builder.setCoordinateX(rs.getLong(2));
            builder.setCoordinateY(rs.getLong(3));
            builder.setCreationDate(LocalDate.parse(rs.getString(4)));
            builder.setRealHero(rs.getString(5));
            builder.setHasToothpick(rs.getString(6));
            builder.setImpactSpeed(rs.getInt(7));
            builder.setWeaponType(builder.checkWeaponType(rs.getString(8)));
            builder.setMood(builder.checkMood(rs.getString(9)));
            builder.setUserName(rs.getString(10));
            return new ServerHumanBeing(builder.getHumanBeing(), rs.getInt(11));
        } catch (InvalidFieldException | EnumNotFoundException notExcepted) {
            notExcepted.printStackTrace();
        }
        return null;
    }

    private void initStatement(PreparedStatement statement, ServerHumanBeing humanBeing) throws SQLException {
        statement.setString(1, humanBeing.getName());
        statement.setLong(2, humanBeing.getCoordinates().getX());
        statement.setLong(3, humanBeing.getCoordinates().getY());
        statement.setString(4, humanBeing.getCreationDate().toString());
        statement.setString(5, String.valueOf(humanBeing.isRealHero()));
        statement.setString(6,String.valueOf(humanBeing.isHasToothpick()));
        statement.setInt(7, humanBeing.getImpactSpeed());
        statement.setString(8,humanBeing.getWeaponType().getStr());
        statement.setString(9, humanBeing.getMood().getStr());
        statement.setString(10, humanBeing.getUsername());
    }

    private Connection getConnection() throws SQLException {
        return DataBaseConnector.getConnection();
    }
}