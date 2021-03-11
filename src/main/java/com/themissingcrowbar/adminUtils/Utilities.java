package com.themissingcrowbar.adminUtils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Utilities {
    public static final String banMessage = "Banned, heat to high.";

    public static void ban(String UUID) throws SQLException, NotAWarningException {
        int banTime = getBanTime(UUID);
        if (banTime <= 0)
            return;

        Date date = Date.from(LocalDate.now().plusDays(banTime).atStartOfDay(ZoneId.systemDefault()).toInstant());

        Bukkit.getOfflinePlayer(java.util.UUID.fromString(UUID)).banPlayer(banMessage, date);
    }

    public static int getBanTime(String UUID) throws SQLException, NotAWarningException {
        ResultSetConnectionWrapper result = Database.getWarnings(UUID);

        double heat = getHeat(result);
        int offset = 0;
        while (heat > 1000) {
            offset += 7;
            result.resultSet.beforeFirst();
            heat = Utilities.getHeat(result, offset);
        }

        return offset;
    }

    public static double getHeat(ResultSetConnectionWrapper warning) throws SQLException, NotAWarningException {
        if (!isWarning(warning.resultSet))
            throw new NotAWarningException(warning.resultSet + " is not a valid warning");

        double heat = 0d;

        while (warning.resultSet.next()) {
            ResultSetConnectionWrapper warnType = Database.getWarnTypes(warning.resultSet.getInt(2));
            warnType.resultSet.next();

            int weight = warnType.resultSet.getInt(2);
            double decayRate = warnType.resultSet.getBigDecimal(5).doubleValue();
            long age = ChronoUnit.DAYS.between(warning.resultSet.getObject(3, LocalDate.class), LocalDate.now());

            warnType.close();

            heat+=weight*Math.exp(decayRate*age);
        }
        return heat;
    }

    public static double getHeat(ResultSetConnectionWrapper warning, int offset) throws SQLException, NotAWarningException {
        if (!isWarning(warning.resultSet))
            throw new NotAWarningException(warning.resultSet + " is not a valid warning");

        double heat = 0d;

        while (warning.resultSet.next()) {
            ResultSetConnectionWrapper warnType = Database.getWarnTypes(warning.resultSet.getInt(2));
            warnType.resultSet.next();

            int weight = warnType.resultSet.getInt(2);
            double decayRate = warnType.resultSet.getBigDecimal(5).doubleValue();
            long age = ChronoUnit.DAYS.between(warning.resultSet.getObject(3, LocalDate.class), LocalDate.now());

            warnType.close();

            heat+=weight*Math.exp(decayRate*(age+offset));
        }
        return heat;
    }

    public static boolean isWarning(ResultSet warning) {
        try {
            warning.next();
            warning.getInt(1);
            warning.getInt(2);
            warning.getDate(3);
            warning.getString(4);
            warning.getString(5);
            warning.beforeFirst();
            return true;
        } catch (SQLException throwables) {
            return false;
        }
    }

    public static String getUUID(Player player, String username) {
        if (player == null) {
            try {
                HttpResponse<JsonNode> response = Unirest.get("https://playerdb.co/api/player/minecraft/"+username).asJson();
                JSONObject json = response.getBody().getObject();
                if (!json.getBoolean("success"))
                    return null;

                return json.getJSONObject("data").getJSONObject("player").getString("id");

            } catch (UnirestException e) {
                return null;
            }
        }
        else
            return player.getUniqueId().toString();
    }
}
