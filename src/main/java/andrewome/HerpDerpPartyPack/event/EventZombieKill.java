package andrewome.HerpDerpPartyPack.event;

import andrewome.HerpDerpPartyPack.states.ZombieModeState;
import andrewome.HerpDerpPartyPack.util.ZombieEditPlayerInventory;
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

        // Check if player is a human - if it is, convert to zombie
        Player player = e.getEntity();
        String playerName = player.getPlayerListName();
        HashMap<String, Player> humans = state.getHumans();
        HashMap<String, Player> zombies = state.getZombies();

        if (state.getHumans().containsKey(playerName)) {
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

        // Check if anymore humans exist
        if (humans.size() == 0) {
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
            Bukkit.broadcastMessage("All humans are DEAD. Zombie mode stopping!");
        }
    }
}
