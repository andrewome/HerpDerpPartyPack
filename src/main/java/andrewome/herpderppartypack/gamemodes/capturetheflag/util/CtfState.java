package andrewome.herpderppartypack.gamemodes.capturetheflag.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CtfState {
    public static double DEATH_MULTIPLIER = 1.5;
    public static Material BLUE_TEAM_BUTTON_MATERIAL = Material.WOOD_BUTTON;
    public static Material RED_TEAM_BUTTON_MATERIAL = Material.STONE_BUTTON;
    public static Location RED_TEAM_SPAWN = new Location(null, -24.0, 68.0, 336.0);
    public static Location BLUE_TEAM_SPAWN = new Location(null, -27.0, 68.0, 402.0, (float) 180.0, (float) 0.0);

    private boolean started = false;
    private HashMap<String, Integer> deathCounter = new HashMap<>();
    private HashMap<String, Player> blueTeam = new HashMap<>();
    private HashMap<String, Player> redTeam = new HashMap<>();

    public void resetStates() {
        this.started = false;
        this.deathCounter = new HashMap<>();
        this.blueTeam = new HashMap<>();
        this.redTeam = new HashMap<>();
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public HashMap<String, Integer> getDeathCounter() {
        return deathCounter;
    }

    public void setDeathCounter(HashMap<String, Integer> deathCounter) {
        this.deathCounter = deathCounter;
    }

    public HashMap<String, Player> getBlueTeam() {
        return blueTeam;
    }

    public void setBlueTeam(HashMap<String, Player> blueTeam) {
        this.blueTeam = blueTeam;
    }

    public HashMap<String, Player> getRedTeam() {
        return redTeam;
    }

    public void setRedTeam(HashMap<String, Player> redTeam) {
        this.redTeam = redTeam;
    }
}
