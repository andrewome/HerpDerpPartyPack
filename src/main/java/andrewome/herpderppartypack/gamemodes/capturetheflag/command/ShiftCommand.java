package andrewome.herpderppartypack.gamemodes.capturetheflag.command;

import andrewome.herpderppartypack.HerpDerpPartyPack;
import andrewome.herpderppartypack.gamemodes.capturetheflag.util.CtfState;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShiftCommand implements CommandExecutor {
    HerpDerpPartyPack plugin;
    CtfState state;

    public ShiftCommand(HerpDerpPartyPack plugin) {
        this.plugin = plugin;
        this.state = plugin.getCtfState();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length != 1) {
            return false;
        }

        if (!state.isStarted()) {
            commandSender.sendMessage("CTF not currently started.");
            return true;
        }

        String targetPlayerName = strings[0];
        Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

        // Broadcast and move player to other team
        boolean isBlue = state.getBlueTeam().containsKey(targetPlayerName);
        Bukkit.broadcastMessage(commandSender.getName() + " shifted " + targetPlayerName + " to the " +
                (isBlue ? "red" : "blue") + " team.");

        if (isBlue) {
            state.getBlueTeam().remove(targetPlayerName);
            state.getRedTeam().put(targetPlayerName, targetPlayer);
        } else {
            state.getRedTeam().remove(targetPlayerName);
            state.getBlueTeam().put(targetPlayerName, targetPlayer);
        }

        System.out.println(state.getBlueTeam());
        System.out.println(state.getRedTeam());

        return true;
    }
}
