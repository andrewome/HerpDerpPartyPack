package andrewome.HerpDerpPartyPack.command.ZombieInfection;

import andrewome.HerpDerpPartyPack.HerpDerpPartyPack;
import andrewome.HerpDerpPartyPack.states.ZombieModeState;
import andrewome.HerpDerpPartyPack.util.EditPlayerInventory;
import andrewome.HerpDerpPartyPack.util.ZombieEditPlayerInventory;
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

import static andrewome.HerpDerpPartyPack.states.ZombieModeState.TIME_TO_GET_READY;
import static andrewome.HerpDerpPartyPack.states.ZombieModeState.TIME_TO_GET_READY_IN_TICKS;
import static andrewome.HerpDerpPartyPack.util.Constants.TICKS_PER_SECOND;

public class CommandStartZombie extends ZombieEditPlayerInventory implements CommandExecutor {
    private HerpDerpPartyPack plugin;
    private ZombieModeState state;

    public CommandStartZombie(HerpDerpPartyPack plugin) {
        this.plugin = plugin;
        this.state = plugin.getZombieModeState();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        // Check if session is ongoing!
        if (state.isTriggered()) {
            commandSender.sendMessage("Session currently ongoing!");
            return true;
        }

        Bukkit.broadcastMessage("==== Zombie Mode Starting ====");
        Bukkit.broadcastMessage("Zombie is being picked in " + TIME_TO_GET_READY + " seconds! RUN FOR YOUR LIVES!");

        // Insert all online players into the human hashmap and clear inventory.
        HashMap<String, Player> humans = state.getHumans();
        for (Player p : Bukkit.getOnlinePlayers()) {
            clearInventory(p);
            humans.put(p.getPlayerListName(), p);
        }

        // Set state
        state.setTriggered(true);

        // add timers
        addTimers();

        return true;
    }

    private void addTimers() {
        // Set count downs
        for (int i : new int[]{1, 2, 3, 10, 20}) {
            // If time is larger than the actual duration, ignore
            if (i > TIME_TO_GET_READY)
                continue;

            new BukkitRunnable() {
                public void run() {
                    Bukkit.broadcastMessage("[Zombie] " + i + " second(s) left!");
                }
            }.runTaskLater(plugin, TIME_TO_GET_READY_IN_TICKS - i * TICKS_PER_SECOND);
        }

        // After specified time, select 1 random player and set as zombie.
        new BukkitRunnable() {
            public void run() {
                HashMap<String, Player> humans = state.getHumans();
                HashMap<String, Player> zombies = state.getZombies();

                int numPlayers = humans.size();
                if (numPlayers == 0)
                    return;

                // Return a value between 0 and size (not inclusive)
                int rand = new Random().nextInt(numPlayers);
                Bukkit.getLogger().log(Level.INFO, rand + " " + numPlayers);
                // Get player
                Player zombiePlayer = new ArrayList<>(humans.values()).get(rand);
                String zombiePlayerName = zombiePlayer.getPlayerListName();

                // Remove from humans hashmap
                humans.remove(zombiePlayerName);

                // Add to zombies hashmap
                zombies.put(zombiePlayerName, zombiePlayer);

                // Give human human stuff
                for (Player p : humans.values()) {
                    equipHuman(p);
                }

                // Give zombie zombie stuff
                for (Player p : zombies.values()) {
                    equipZombie(p);
                }

                // Set started state
                state.setStarted(true);

                // Announce the zombie!
                Bukkit.broadcastMessage("==== [Zombie] ====");
                Bukkit.broadcastMessage(zombiePlayerName + " is the Zombie!! RUN!!!");
            }
        }.runTaskLater(plugin, TIME_TO_GET_READY_IN_TICKS);
    }
}
