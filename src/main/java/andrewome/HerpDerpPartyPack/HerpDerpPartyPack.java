package andrewome.HerpDerpPartyPack;

import andrewome.HerpDerpPartyPack.command.HideAndSeek.CommandHelmetsOn;
import andrewome.HerpDerpPartyPack.command.HideAndSeek.CommandHider;
import andrewome.HerpDerpPartyPack.command.HideAndSeek.CommandSeeker;
import andrewome.HerpDerpPartyPack.command.ZombieInfection.CommandStartZombie;
import andrewome.HerpDerpPartyPack.command.ZombieInfection.CommandStopZombie;
import andrewome.HerpDerpPartyPack.event.EventZombieKill;
import andrewome.HerpDerpPartyPack.states.HideAndSeekState;
import andrewome.HerpDerpPartyPack.states.ZombieModeState;
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
