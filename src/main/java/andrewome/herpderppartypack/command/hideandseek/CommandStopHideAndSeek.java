package andrewome.herpderppartypack.command.hideandseek;

import andrewome.herpderppartypack.HerpDerpPartyPack;
import andrewome.herpderppartypack.states.HideAndSeekState;
import andrewome.herpderppartypack.states.ZombieModeState;
import andrewome.herpderppartypack.util.EditPlayerInventory;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandStopHideAndSeek extends EditPlayerInventory implements CommandExecutor {
    private HerpDerpPartyPack plugin;
    private HideAndSeekState state;

    public CommandStopHideAndSeek(HerpDerpPartyPack plugin) {
        this.plugin = plugin;
        this.state = plugin.getHideAndSeekState();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!state.isStarted()) {
            commandSender.sendMessage("HnS session not currently started.");
            return true;
        }

        // Clear everyone and remove effects
        for (Player p : state.getHiders().values()) {
            clearInventory(p);
            clearPotionEffects(p);
        }
        for (Player p : state.getSeekers().values()) {
            clearInventory(p);
            clearPotionEffects(p);
        }

        // reset states
        state.resetStates();
        Bukkit.broadcastMessage("Zombie mode is stopping!");
        return true;
    }
}
