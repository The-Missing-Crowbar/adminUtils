package com.themissingcrowbar.adminUtils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import java.sql.SQLException;

public class CommandWarn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length < 2)
            return false;

        String UUID = Utilities.getUUID(Bukkit.getPlayer(strings[0]), strings[0]);
        if (UUID == null) {
            commandSender.sendMessage("Error, does user exist?");
            return false;
        }

        try { //second argument branching
            int typeId = Integer.parseInt(strings[1]);
            if (strings.length==2) {
                boolean temp = warn(commandSender, UUID, typeId);
                Utilities.ban(UUID);
                return temp;
            }
            try {
                int offences = Integer.parseInt(strings[2]);
                if (strings.length>=4) {
                    StringBuilder reason = new StringBuilder();
                    for (int i=3; i<strings.length; i++) {
                        reason.append(strings[i]).append(' ');
                    }
                    boolean temp = warn(commandSender, UUID, typeId, offences, reason.toString());
                    Utilities.ban(UUID);
                    return temp;
                }
                boolean temp = warn(commandSender, UUID, typeId, offences);
                Utilities.ban(UUID);
                return temp;
            } catch (NumberFormatException ignore) {
                boolean temp = warn(commandSender, UUID, typeId, strings[2]); // only reason
                Utilities.ban(UUID);
                return temp;
            }
        } catch (NumberFormatException ignore) {
            if (strings.length==2) {
                boolean temp = warn(commandSender, UUID, strings[1]);
                try {
                    Utilities.ban(UUID);
                } catch (SQLException | NotAWarningException throwables) {
                     return false;
                }
                return temp;
            }
            try {
                int offences = Integer.parseInt(strings[2]);
                if (strings.length>=4) {
                    StringBuilder reason = new StringBuilder();
                    for (int i=3; i<strings.length; i++) {
                        reason.append(strings[i]).append(' ');
                    }
                    boolean temp = warn(commandSender, UUID, strings[1], offences, reason.toString());
                    Utilities.ban(UUID);
                    return temp;
                }
                boolean temp = warn(commandSender, UUID, strings[1], offences);
                Utilities.ban(UUID);
                return temp;
            } catch (NumberFormatException _ignore) {
                StringBuilder reason = new StringBuilder();
                for (int i=2; i<strings.length; i++)
                    reason.append(strings[i]).append(' ');
                boolean temp = warn(commandSender, UUID, strings[1], reason.toString()); // only reason
                try {
                    Utilities.ban(UUID);
                } catch (SQLException | NotAWarningException throwables) {
                    return false;
                }
                return temp;
            } catch (SQLException | NotAWarningException throwables) {
                return false;
            }
        } catch (SQLException | NotAWarningException throwables) {
            return false;
        }
    }

    private boolean warn(CommandSender commandSender, String UUID, String typeName, int offences, String reason) {
        try {
            boolean success = Database.warn(UUID, typeName, offences, reason);
            if (success)
                commandSender.sendMessage("Warn success");
            return success;
        } catch (SQLException throwables) {
            return false;
        }
    }

    private boolean warn(CommandSender commandSender, String UUID, String typeName, int offences) {
        try {
            boolean success = Database.warn(UUID, typeName, offences);
            if (success)
                commandSender.sendMessage("Warn success");
            return success;
        } catch (SQLException throwables) {
            return false;
        }
    }

    private boolean warn(CommandSender commandSender, String UUID, String typeName, String reason) {
        try {
            boolean success = Database.warn(UUID, typeName, reason);
            if (success)
                commandSender.sendMessage("Warn success");
            return success;
        } catch (SQLException throwables) {
            return false;
        }
    }


    private boolean warn(CommandSender commandSender, String UUID, String typeName) {
        try {
            boolean success = Database.warn(UUID, typeName);
            if (success)
                commandSender.sendMessage("Warn success");
            return success;
        } catch (SQLException throwables) {
            return false;
        }
    }

    private boolean warn(CommandSender commandSender, String UUID, int typeId, int offences, String reason) {
        try {
            boolean success = Database.warn(UUID, typeId, offences, reason);
            if (success)
                commandSender.sendMessage("Warn success");
            return success;
        } catch (SQLException throwables) {
            return false;
        }
    }

    private boolean warn(CommandSender commandSender, String UUID, int typeId, int offences) {
        try {
            boolean success = Database.warn(UUID, typeId, offences);
            if (success)
                commandSender.sendMessage("Warn success");
            return success;
        } catch (SQLException throwables) {
            return false;
        }
    }


    private boolean warn(CommandSender commandSender, String UUID, int typeId, String reason) {
        try {
            boolean success = Database.warn(UUID, typeId, reason);
            if (success)
                commandSender.sendMessage("Warn success");
            return success;
        } catch (SQLException throwables) {
            return false;
        }
    }


    private boolean warn(CommandSender commandSender, String UUID, int typeId) {
        try {
            boolean success = Database.warn(UUID, typeId);
            if (success)
                commandSender.sendMessage("Warn success");
            return success;
        } catch (SQLException throwables) {
            return false;
        }
    }
}
