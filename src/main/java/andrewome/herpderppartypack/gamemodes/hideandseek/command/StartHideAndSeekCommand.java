package andrewome.herpderppartypack.gamemodes.hideandseek.command;

import andrewome.herpderppartypack.HerpDerpPartyPack;
import andrewome.herpderppartypack.gamemodes.hideandseek.util.HideAndSeekState;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;

import static andrewome.herpderppartypack.gamemodes.hideandseek.util.HideAndSeekPlayerInventory.equipHider;
import static andrewome.herpderppartypack.gamemodes.hideandseek.util.HideAndSeekPlayerInventory.equipSeeker;
import static andrewome.herpderppartypack.gamemodes.hideandseek.util.HideAndSeekState.TIME_TO_GET_READY;
import static andrewome.herpderppartypack.gamemodes.hideandseek.util.HideAndSeekState.TIME_TO_GET_READY_IN_TICKS;
import static andrewome.herpderppartypack.util.Constants.TICKS_PER_SECOND;
import static andrewome.herpderppartypack.util.PlayerInventory.clearInventory;

public class StartHideAndSeekCommand implements CommandExecutor {
    private HerpDerpPartyPack plugin;
    private HideAndSeekState state;

    public StartHideAndSeekCommand(HerpDerpPartyPack plugin) {
        this.plugin = plugin;
        this.state = plugin.getHideAndSeekState();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        // Check if session is ongoing
        if (state.isStarted()) {
            commandSender.sendMessage("HnS session already currently ongoing!");
            return true;
        }

        // Insert all online players into the hider hashmap and clear inventory.
        HashMap<String, Player> hiders = state.getHiders();
        HashMap<String, Player> seekers = state.getSeekers();
        for (Player p : Bukkit.getOnlinePlayers()) {
            clearInventory(p);
            hiders.put(p.getPlayerListName(), p);
        }

        // Select a random player to be the seeker
        int numPlayers = hiders.size();

        // Return a value between 0 and size (not inclusive)
        int rand = new Random().nextInt(numPlayers);
        Bukkit.getLogger().log(Level.INFO, rand + " " + numPlayers);
        // Get player
        Player seeker = new ArrayList<>(hiders.values()).get(rand);
        String seekerName = seeker.getPlayerListName();

        // Remove from hiders, add to seekers
        hiders.remove(seekerName);
        seekers.put(seekerName, seeker);

        Bukkit.broadcastMessage("==== Hide and Seek is starting ====");
        Bukkit.broadcastMessage("The seeker is " + seekerName + "! Hiders have " + TIME_TO_GET_READY + " seconds to hide!");

        // Equip hiders and seekers
        for (Player p : hiders.values())
            equipHider(p);
        for (Player p : seekers.values())
            equipSeeker(p);

        // Add 30s countdown
        for (int i : new int[]{1, 2, 3, 10, 20}) {
            // If time is larger than the actual duration, ignore
            if (i > TIME_TO_GET_READY)
                continue;

            new BukkitRunnable() {
                public void run() {
                    Bukkit.broadcastMessage("[HnS] " + i + " second(s) left!");
                }
            }.runTaskLater(plugin, TIME_TO_GET_READY_IN_TICKS - i * TICKS_PER_SECOND);
        }

        // Set states
        state.setStarted(true);

        return true;
    }
}
