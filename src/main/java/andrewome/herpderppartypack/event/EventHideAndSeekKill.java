package andrewome.herpderppartypack.event;

import andrewome.herpderppartypack.states.HideAndSeekState;
import andrewome.herpderppartypack.util.HideAndSeekEditPlayerInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;

public class EventHideAndSeekKill extends HideAndSeekEditPlayerInventory implements Listener {
    private HideAndSeekState state;

    public EventHideAndSeekKill(HideAndSeekState state) {
        this.state = state;
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        // Check if zombie mode is active
        if (!state.isStarted())
            return;

        // Check if player is a hider - if it is, convert to zombie
        Player player = e.getEntity();
        String playerName = player.getPlayerListName();
        HashMap<String, Player> hiders = state.getHiders();
        HashMap<String, Player> seekers = state.getSeekers();

        if (hiders.containsKey(playerName)) {
            handleHiderKilled(hiders, seekers, e, playerName, player);
        }

        // Check if anymore humans exist
        if (hiders.size() == 0) {
            handleAllHidersKilled(hiders, seekers);
        }
    }

    private void handleAllHidersKilled(HashMap<String, Player> hiders, HashMap<String, Player> seekers) {
        // Clear everyone and remove effects
        for (Player p : hiders.values()) {
            clearInventory(p);
            clearPotionEffects(p);
        }
        for (Player p : seekers.values()) {
            clearInventory(p);
            clearPotionEffects(p);
        }

        // reset states
        state.resetStates();
        Bukkit.broadcastMessage("[HnS] All hiders are DEAD. Game ending!");
    }

    private void handleHiderKilled(HashMap<String, Player> hiders, HashMap<String, Player> seekers, PlayerDeathEvent e,
                      String playerName, Player player) {
        // Shift to seekers array
        hiders.remove(playerName);
        seekers.put(playerName, player);

        // Clear invent
        clearInventory(player);
        clearPotionEffects(player);

        // Give seeker stuff
        equipSeeker(player);

        // Set message
        e.setDeathMessage(playerName + " has been found!!");
    }
}
