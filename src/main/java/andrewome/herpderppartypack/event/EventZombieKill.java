package andrewome.herpderppartypack.event;

import andrewome.herpderppartypack.states.ZombieModeState;
import andrewome.herpderppartypack.util.ZombieEditPlayerInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;

public class EventZombieKill extends ZombieEditPlayerInventory implements Listener {
    private ZombieModeState state;

    public EventZombieKill(ZombieModeState state) {
        this.state = state;
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        // Check if zombie mode is active
        if (!state.isStarted())
            return;

        Player player = e.getEntity();
        String playerName = player.getPlayerListName();
        HashMap<String, Player> humans = state.getHumans();
        HashMap<String, Player> zombies = state.getZombies();

        // Check if player is a human - if it is, convert to zombie
        if (humans.containsKey(playerName)) {
            handleHumanKilled(humans, zombies, e, playerName, player);
        }

        // Check if anymore humans exist
        if (humans.size() == 0) {
            handleNoHumansLeft(humans, zombies);
        }
    }

    private void handleNoHumansLeft(HashMap<String, Player> humans, HashMap<String, Player> zombies) {
        // Clear everyone and remove effects
        for (Player p : humans.values()) {
            clearInventory(p);
            clearPotionEffects(p);
        }
        for (Player p : zombies.values()) {
            clearInventory(p);
            clearPotionEffects(p);
        }

        // reset states
        state.resetStates();
        Bukkit.broadcastMessage("All humans are DEAD. Zombie mode stopping!");
    }

    private void handleHumanKilled(HashMap<String, Player> humans, HashMap<String, Player> zombies, PlayerDeathEvent e,
                           String playerName, Player player) {
        // Shift to zombie array
        humans.remove(playerName);
        zombies.put(playerName, player);

        // Clear invent
        clearInventory(player);
        clearPotionEffects(player);

        // Give zombie stuff
        equipZombie(player);

        // Set message
        e.setDeathMessage(playerName + " has been infected!!");
    }

}
