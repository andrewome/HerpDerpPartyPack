package andrewome.herpderppartypack.gamemodes.hideandseek.util;

import org.bukkit.entity.Player;

import java.time.LocalTime;
import java.util.HashMap;

import static andrewome.herpderppartypack.util.Constants.TICKS_PER_SECOND;

public class HideAndSeekState {
    public static final int HELMETS_ON_COOLDOWN = 80;
    public static final int HELMETS_ON_COOLDOWN_IN_TICKS = HELMETS_ON_COOLDOWN * TICKS_PER_SECOND;
    public static final int HELMETS_ON_DURATION = 20;
    public static final int HELMETS_ON_DURATION_IN_TICKS = HELMETS_ON_DURATION * TICKS_PER_SECOND;
    public static final int TIME_TO_GET_READY = 30; // seconds
    public static final int TIME_TO_GET_READY_IN_TICKS = TIME_TO_GET_READY * TICKS_PER_SECOND;

    private boolean started = false;
    private boolean helmetsOnOnCooldown = false;
    private LocalTime helmetsOntimestamp = null;
    private HashMap<String, Player> seekers = new HashMap<>();
    private HashMap<String, Player> hiders = new HashMap<>();

    public void resetStates() {
        this.started = false;
        this.helmetsOnOnCooldown = false;
        this.helmetsOntimestamp = null;
        this.seekers = new HashMap<>();
        this.hiders = new HashMap<>();
    }

    public boolean isHelmetsOnOnCooldown() {
        return helmetsOnOnCooldown;
    }

    public void setHelmetsOnOnCooldown(boolean helmetsOnOnCooldown) {
        this.helmetsOnOnCooldown = helmetsOnOnCooldown;
    }

    public LocalTime getHelmetsOntimestamp() {
        return helmetsOntimestamp;
    }

    public void setHelmetsOntimestamp(LocalTime helmetsOntimestamp) {
        this.helmetsOntimestamp = helmetsOntimestamp;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public HashMap<String, Player> getSeekers() {
        return seekers;
    }

    public void setSeekers(HashMap<String, Player> seekers) {
        this.seekers = seekers;
    }

    public HashMap<String, Player> getHiders() {
        return hiders;
    }

    public void setHiders(HashMap<String, Player> hiders) {
        this.hiders = hiders;
    }
}