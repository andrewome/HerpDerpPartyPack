package andrewome.herpderppartypack.gamemodes.zombieinfection.command;

import andrewome.herpderppartypack.HerpDerpPartyPack;
import andrewome.herpderppartypack.gamemodes.zombieinfection.util.ZombieModeState;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static andrewome.herpderppartypack.util.PlayerInventory.clearInventory;
import static andrewome.herpderppartypack.util.PlayerInventory.clearPotionEffects;

public class StopZombieModeCommand implements CommandExecutor {
    private HerpDerpPartyPack plugin;
    private ZombieModeState state;

    public StopZombieModeCommand(HerpDerpPartyPack plugin) {
        this.plugin = plugin;
        this.state = plugin.getZombieModeState();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!state.isTriggered()) {
            commandSender.sendMessage("Zombie Infection not currently started.");
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
        Bukkit.broadcastMessage(commandSender.getName() + " has stopped the session!");
        return true;
    }
}
