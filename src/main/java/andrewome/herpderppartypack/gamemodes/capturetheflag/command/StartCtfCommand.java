package andrewome.herpderppartypack.gamemodes.capturetheflag.command;

import andrewome.herpderppartypack.HerpDerpPartyPack;
import andrewome.herpderppartypack.gamemodes.capturetheflag.util.CtfState;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static andrewome.herpderppartypack.gamemodes.capturetheflag.util.CtfPlayerInventory.equipPlayer;
import static andrewome.herpderppartypack.gamemodes.capturetheflag.util.CtfPlayerInventory.clearInventory;
import static andrewome.herpderppartypack.gamemodes.capturetheflag.util.CtfPlayerInventory.clearPotionEffects;
import static andrewome.herpderppartypack.gamemodes.capturetheflag.util.CtfState.BLUE_TEAM_SPAWN;
import static andrewome.herpderppartypack.gamemodes.capturetheflag.util.CtfState.RED_TEAM_SPAWN;

public class StartCtfCommand implements CommandExecutor {
    HerpDerpPartyPack plugin;
    CtfState state;

    public StartCtfCommand(HerpDerpPartyPack plugin) {
        this.plugin = plugin;
        this.state = plugin.getCtfState();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        // Check if game mode is started
        if (state.isStarted()) {
            commandSender.sendMessage("Ctf is already currently ongoing!");
            return true;
        }

        // Get all online players
        ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        int numPlayers = players.size();

        // Init death counter
        HashMap<String, Integer> deathCounter = new HashMap<>();
        for (Player p : players) {
            deathCounter.put(p.getPlayerListName(), 0);
        }

        // Split up half half into blue/red
        int half = players.size() / 2;
        HashMap<String, Player> blueTeam = new HashMap<>();
        while (blueTeam.size() < half) {
            int rand = new Random().nextInt(numPlayers);
            Player player = players.get(rand);
            String playerName = player.getPlayerListName();
            blueTeam.put(playerName, player);
        }

        // insert the remainders into red team
        HashMap<String, Player> redTeam = new HashMap<>();
        for (Player p : players)
            // if not in blue team hashmap
            if (!blueTeam.containsKey(p.getPlayerListName()))
                redTeam.put(p.getPlayerListName(), p);

        // Inform players which team they're on
        Location blueTeamSpawn = BLUE_TEAM_SPAWN.clone();
        Location redTeamSpawn = RED_TEAM_SPAWN.clone();
        for (Player p : redTeam.values()) {
            p.sendMessage("You are assigned to the red team!");
            redTeamSpawn.setWorld(p.getWorld());
            p.teleport(redTeamSpawn);
        }
        for (Player p : blueTeam.values()) {
            p.sendMessage("You are assigned to the blue team!");
            blueTeamSpawn.setWorld(p.getWorld());
            p.teleport(blueTeamSpawn);
        }

        Bukkit.broadcastMessage("Blue team: " + blueTeam.values());
        Bukkit.broadcastMessage("Red team: " + redTeam.values());

        // Equip players
        for (Player p : players) {
            clearInventory(p);
            clearPotionEffects(p);
            equipPlayer(p);
        }

        // Set states
        state.setStarted(true);
        state.setDeathCounter(deathCounter);
        state.setBlueTeam(blueTeam);
        state.setRedTeam(redTeam);

        return true;
    }
}
