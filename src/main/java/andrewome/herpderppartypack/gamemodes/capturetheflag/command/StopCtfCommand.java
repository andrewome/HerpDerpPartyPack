package andrewome.herpderppartypack.gamemodes.capturetheflag.command;

import andrewome.herpderppartypack.HerpDerpPartyPack;
import andrewome.herpderppartypack.gamemodes.capturetheflag.util.CtfState;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static andrewome.herpderppartypack.util.PlayerInventory.clearInventory;
import static andrewome.herpderppartypack.util.PlayerInventory.clearPotionEffects;

public class StopCtfCommand implements CommandExecutor {
    private HerpDerpPartyPack plugin;
    private CtfState state;

    public StopCtfCommand(HerpDerpPartyPack plugin) {
        this.plugin = plugin;
        this.state = plugin.getCtfState();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!state.isStarted()) {
            commandSender.sendMessage("CTF not currently started.");
            return true;
        }

        // Clear everyone and remove effects
        for (Player p : state.getBlueTeam().values()) {
            clearInventory(p);
            clearPotionEffects(p);
        }
        for (Player p : state.getRedTeam().values()) {
            clearInventory(p);
            clearPotionEffects(p);
        }

        // reset states
        state.resetStates();
        Bukkit.broadcastMessage(commandSender.getName() + " has stopped the session!");
        return true;
    }
}
