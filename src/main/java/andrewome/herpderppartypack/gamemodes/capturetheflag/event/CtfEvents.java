package andrewome.herpderppartypack.gamemodes.capturetheflag.event;

import andrewome.herpderppartypack.HerpDerpPartyPack;
import andrewome.herpderppartypack.gamemodes.capturetheflag.util.CtfState;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.material.Wool;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

import static andrewome.herpderppartypack.gamemodes.capturetheflag.util.CtfPlayerInventory.hasFlag;
import static andrewome.herpderppartypack.gamemodes.capturetheflag.util.CtfState.*;
import static andrewome.herpderppartypack.util.Constants.TICKS_PER_SECOND;
import static andrewome.herpderppartypack.util.PlayerInventory.clearInventory;
import static andrewome.herpderppartypack.util.PlayerInventory.clearPotionEffects;
import static org.bukkit.util.NumberConversions.floor;

public class CtfEvents implements Listener {
    HerpDerpPartyPack plugin;
    CtfState state;

    public CtfEvents(HerpDerpPartyPack plugin) {
        this.plugin = plugin;
        this.state = plugin.getCtfState();
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        // Check if ctf mode is on
        if (!state.isStarted())
            return;

        Player player = e.getEntity();
        String playerName = player.getPlayerListName();
        HashMap<String, Integer> deathCounter = state.getDeathCounter();

        // if player is not part of the game, ignore
        if (!deathCounter.containsKey(playerName))
            return;

        // Respawn player immediately
        player.spigot().respawn();

        // Remove all wool from inventory if any (lazy, i know :3)
        player.getInventory().remove(Material.WOOL);

        // Increment deaths by 1
        int numDeaths = deathCounter.get(playerName);
        deathCounter.put(playerName, ++numDeaths);

        // duration is floor(number of seconds = 1.5 * number of deaths)
        int duration = floor(DEATH_MULTIPLIER * numDeaths);

        // Send messages about how long before player can move
        ArrayList<Integer> durationArr = new ArrayList<>();
        for (int i = 1; i <= duration; i++) {
            durationArr.add(i);
        }
        for (int i : durationArr) {
            int secondsToRespawn = duration - i;
            new BukkitRunnable() {
                public void run() {
                    player.sendMessage("[CTF] You can move in " + i + " second(s)!");
                }
            }.runTaskLater(plugin, secondsToRespawn * TICKS_PER_SECOND);
        }

        // Remove potion effects after timeout
        new BukkitRunnable() {
            public void run() {
                clearPotionEffects(player);
                player.sendMessage("[CTF] You may now re-enter the battle!");

                // Teleport back to the battlefield
                boolean isBlue = state.getBlueTeam().containsKey(playerName);
                Location blueTeamSpawn = BLUE_TEAM_SPAWN.clone();
                Location redTeamSpawn = RED_TEAM_SPAWN.clone();
                blueTeamSpawn.setWorld(player.getWorld());
                redTeamSpawn.setWorld(player.getWorld());
                if (isBlue)
                    player.teleport(blueTeamSpawn);
                else
                    player.teleport(redTeamSpawn);
            }
        }.runTaskLater(plugin, duration * TICKS_PER_SECOND + 5);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        // Check if ctf mode is on
        if (!state.isStarted())
            return;

        Player player = e.getPlayer();

        // Add effects upon respawn which is 1 tick later
        new BukkitRunnable() {
            public void run() {
                // Set effect (slowness 128, leap 128)
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1000 * TICKS_PER_SECOND, 128));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000 * TICKS_PER_SECOND, 128));
                // Hopefully will be cleared by the timeout set in onKill lol
            }
        }.runTaskLater(plugin, 1);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        // Check if ctf mode is on
        if (!state.isStarted())
            return;

        Player player = e.getPlayer();
        String playerName = player.getPlayerListName();

        // Check if player is playing
        if (!state.getDeathCounter().containsKey(playerName)) {
            return;
        }

        if (e.getClickedBlock() == null)
            return;

        // Check if button pressed is either blue team or red team's button and must be right click
        Material clickedBlock = e.getClickedBlock().getType();
        if (clickedBlock != BLUE_TEAM_BUTTON_MATERIAL && clickedBlock != RED_TEAM_BUTTON_MATERIAL) {
            return;
        }
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        // Get the team of the player
        boolean isBlue = state.getBlueTeam().containsKey(playerName);

        // Check if has flag. If contains, congrats, game ends.
        if (hasFlag(state, player, isBlue)) {
            Bukkit.broadcastMessage(playerName + " has captured the " + (isBlue ? "red" : "blue") + " team's flag!");
            Bukkit.broadcastMessage("Game ending.");
            for (Player p : state.getBlueTeam().values()) {
                clearPotionEffects(p);
                clearInventory(p);
            }
            for (Player p : state.getRedTeam().values()) {
                clearPotionEffects(p);
                clearInventory(p);
            }
            state.resetStates();
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        // Check if ctf mode is on
        if (!state.isStarted())
            return;

        Player player = e.getPlayer();
        String playerName = player.getPlayerListName();

        // Check if player is playing
        if (!state.getDeathCounter().containsKey(playerName)) {
            return;
        }

        // Get type of block destroyed
        Block blockDestroyed = e.getBlock();
        if (blockDestroyed.getType() != Material.WOOL)
            return;

        Wool blueWool = new Wool(DyeColor.BLUE);
        Wool redWool = new Wool(DyeColor.RED);
        Wool woolBlockDestroyed = (Wool) blockDestroyed.getState().getData();

        // Check if flag was taken
        boolean isBlue = state.getBlueTeam().containsKey(playerName);
        boolean flagTaken = false;
        if ((isBlue && woolBlockDestroyed.equals(redWool)) || (!isBlue && woolBlockDestroyed.equals(blueWool))) {
            flagTaken = true;
        }

        // Flag is taken, broadcast message, add block to player inventory
        if (flagTaken) {
            Bukkit.broadcastMessage(playerName + " has taken the " + (isBlue ? "red" : "blue") + " team's flag!!");
            player.getInventory().addItem(woolBlockDestroyed.toItemStack(1));
        }

        // Finally, cancel the breaking of the flag
        e.setCancelled(true);
    }
}
