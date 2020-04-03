package andrewome.herpderppartypack.states;

import org.bukkit.entity.Player;

import java.util.HashMap;

import static andrewome.herpderppartypack.util.Constants.TICKS_PER_SECOND;

public class ZombieModeState {
    public static final int TIME_TO_GET_READY = 30; // seconds
    public static final int TIME_TO_GET_READY_IN_TICKS = TIME_TO_GET_READY * TICKS_PER_SECOND;

    private boolean triggered = false;
    private boolean started = false;
    private HashMap<String, Player> zombies = new HashMap<>();
    private HashMap<String, Player> humans = new HashMap<>();

    public void resetStates() {
        this.triggered = false;
        this.started = false;
        this.zombies = new HashMap<>();
        this.humans = new HashMap<>();
    }

    public HashMap<String, Player> getZombies() {
        return zombies;
    }

    public void setZombies(HashMap<String, Player> zombies) {
        this.zombies = zombies;
    }

    public HashMap<String, Player> getHumans() {
        return humans;
    }

    public void setHumans(HashMap<String, Player> humans) {
        this.humans = humans;
    }

    public boolean isTriggered() {
        return triggered;
    }

    public void setTriggered(boolean triggered) {
        this.triggered = triggered;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
