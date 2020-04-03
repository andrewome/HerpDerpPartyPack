package andrewome.herpderppartypack.states;

import java.time.LocalTime;

import static andrewome.herpderppartypack.util.Constants.TICKS_PER_SECOND;

public class HideAndSeekState {
    public static final int HELMETS_ON_COOLDOWN = 80;
    public static final int HELMETS_ON_COOLDOWN_IN_TICKS = HELMETS_ON_COOLDOWN * TICKS_PER_SECOND;
    public static final int HELMETS_ON_DURATION = 20;
    public static final int HELMETS_ON_DURATION_IN_TICKS = HELMETS_ON_DURATION * TICKS_PER_SECOND;

    private boolean helmetsOnOnCooldown = false;
    private LocalTime helmetsOntimestamp = null;

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
}
