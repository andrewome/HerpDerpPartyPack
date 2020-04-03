package andrewome.herpderppartypack.command.zombieinfection;

import andrewome.herpderppartypack.HerpDerpPartyPack;
import andrewome.herpderppartypack.states.ZombieModeState;
import andrewome.herpderppartypack.util.EditPlayerInventory;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandStopZombie extends EditPlayerInventory implements CommandExecutor {
    private HerpDerpPartyPack plugin;
    private ZombieModeState state;

    public CommandStopZombie(HerpDerpPartyPack plugin) {
        this.plugin = plugin;
        this.state = plugin.getZombieModeState();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!state.isTriggered()) {
            commandSender.sendMessage("Zombie mode is not currently playing.");
            return true;
        }

        // Clear everyone and remove effects
        for (Player p : state.getHumans().values()) {
            clearInventory(p);
            clearPotionEffects(p);
        }
        for (Player p : state.getZombies().values()) {
            clearInventory(p);
            clearPotionEffects(p);
        }

        // reset states
        state.resetStates();
        Bukkit.broadcastMessage("Zombie mode is stopping!");
        return true;
    }
}
