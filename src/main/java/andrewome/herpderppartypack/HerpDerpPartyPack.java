package andrewome.herpderppartypack;

import andrewome.herpderppartypack.command.hideandseek.CommandHelmetsOn;
import andrewome.herpderppartypack.command.hideandseek.CommandHider;
import andrewome.herpderppartypack.command.hideandseek.CommandSeeker;
import andrewome.herpderppartypack.command.zombieinfection.CommandStartZombie;
import andrewome.herpderppartypack.command.zombieinfection.CommandStopZombie;
import andrewome.herpderppartypack.event.EventZombieKill;
import andrewome.herpderppartypack.states.HideAndSeekState;
import andrewome.herpderppartypack.states.ZombieModeState;
import org.bukkit.plugin.java.JavaPlugin;

public class HerpDerpPartyPack extends JavaPlugin {
    private HideAndSeekState hideAndSeekState = new HideAndSeekState();
    private ZombieModeState zombieModeState = new ZombieModeState();

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        this.getCommand("helmetsOn").setExecutor(new CommandHelmetsOn(this));
        this.getCommand("seeker").setExecutor(new CommandSeeker());
        this.getCommand("hider").setExecutor(new CommandHider());
        this.getCommand("start_zombie").setExecutor(new CommandStartZombie(this));
        this.getCommand("stop_zombie").setExecutor(new CommandStopZombie(this));

        this.getServer().getPluginManager().registerEvents(new EventZombieKill(zombieModeState), this);
    }

    public HideAndSeekState getHideAndSeekState() {
        return hideAndSeekState;
    }

    public ZombieModeState getZombieModeState() {
        return zombieModeState;
    }
}
