package andrewome.herpderppartypack.gamemodes.hideandseek.command;

import andrewome.herpderppartypack.HerpDerpPartyPack;
import andrewome.herpderppartypack.gamemodes.hideandseek.util.HideAndSeekState;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static andrewome.herpderppartypack.gamemodes.hideandseek.util.HideAndSeekState.*;
import static andrewome.herpderppartypack.util.Constants.*;

public class HelmetsOnCommand implements CommandExecutor {
    private HerpDerpPartyPack plugin;
    private HideAndSeekState states;

    public HelmetsOnCommand(HerpDerpPartyPack plugin) {
        this.plugin = plugin;
        this.states = plugin.getHideAndSeekState();
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        // Check if on cooldown.
        if (states.isHelmetsOnOnCooldown()) {
            long seconds
                = (long)HELMETS_ON_COOLDOWN - ChronoUnit.SECONDS.between(states.getHelmetsOntimestamp(), LocalTime.now());
            commandSender.sendMessage("Command is on cool down! " + seconds + " seconds remaining.");
            return true;
        }

        // Get all online players without helmets
        List<Player> players = Bukkit.getOnlinePlayers()
            .stream()
            .filter((Player p) -> p.getInventory().getHelmet() == null)
            .collect(Collectors.toList());

        // For each online player, helmets on.
        Bukkit.broadcastMessage("Helmets On! " + HELMETS_ON_DURATION + " seconds starts now!");
        for (Player p : players) {
            p.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET));
        }

        // Add the timers
        addTimers(players);

        // Update states
        states.setHelmetsOnOnCooldown(true);
        states.setHelmetsOntimestamp(LocalTime.now());

        return true;
    }

    private void addTimers(List<Player> players) {
        /* Timers with regards to the DURATION of the helmets on */
        // Set a timer to remove helmet
        new BukkitRunnable() {
            public void run() {
                for (Player p : players) {
                    p.sendMessage("Time is up! Helmets off!");
                    p.getInventory().setHelmet(null);
                }
            }
        }.runTaskLater(plugin, HELMETS_ON_DURATION_IN_TICKS);

        // Set reminders for duration of helmets on
        for (int i : new int[]{1, 2, 3, 10}) {
            // If time is larger than the actual duration, ignore
            if (i > HELMETS_ON_DURATION)
                continue;

            new BukkitRunnable() {
                public void run() {
                    Bukkit.broadcastMessage(i + " second(s) left!");
                }
            }.runTaskLater(plugin, HELMETS_ON_DURATION_IN_TICKS - i * TICKS_PER_SECOND);
        }

        /* Timers with regards to the COOLDOWN of the helmets on */
        // Set timer to start cooldown between helmets off
        new BukkitRunnable() {
            public void run() {
                Bukkit.broadcastMessage("Helmets On is now off cooldown!");
                states.setHelmetsOnOnCooldown(false);
            }
        }.runTaskLater(plugin, HELMETS_ON_COOLDOWN_IN_TICKS);

        // Set reminders for cooldown of helmets on
        for (int i : new int[]{60, 30, 10, 5}) {
            // If time is larger than the actual duration, ignore
            if (i > HELMETS_ON_COOLDOWN)
                continue;

            new BukkitRunnable() {
                public void run() {
                    Bukkit.broadcastMessage(i + " seconds before Helmets On can be used again!");
                }
            }.runTaskLater(plugin, HELMETS_ON_COOLDOWN_IN_TICKS - i * TICKS_PER_SECOND);
        }
    }
}
