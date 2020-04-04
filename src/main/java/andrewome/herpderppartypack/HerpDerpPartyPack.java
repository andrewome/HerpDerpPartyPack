package andrewome.herpderppartypack;

import andrewome.herpderppartypack.event.OnWeatherChangeEvent;
import andrewome.herpderppartypack.gamemodes.capturetheflag.command.ShiftCommand;
import andrewome.herpderppartypack.gamemodes.capturetheflag.command.StartCtfCommand;
import andrewome.herpderppartypack.gamemodes.capturetheflag.command.StopCtfCommand;
import andrewome.herpderppartypack.gamemodes.capturetheflag.event.CtfEvents;
import andrewome.herpderppartypack.gamemodes.capturetheflag.util.CtfState;
import andrewome.herpderppartypack.gamemodes.hideandseek.command.*;
import andrewome.herpderppartypack.gamemodes.hideandseek.event.HideAndSeekKillEvent;
import andrewome.herpderppartypack.gamemodes.hideandseek.util.HideAndSeekState;
import andrewome.herpderppartypack.gamemodes.zombieinfection.command.StartZombieModeCommand;
import andrewome.herpderppartypack.gamemodes.zombieinfection.command.StopZombieModeCommand;
import andrewome.herpderppartypack.gamemodes.zombieinfection.event.ZombieKillEvent;
import andrewome.herpderppartypack.gamemodes.zombieinfection.util.ZombieModeState;
import org.bukkit.plugin.java.JavaPlugin;

public class HerpDerpPartyPack extends JavaPlugin {
    private HideAndSeekState hideAndSeekState = new HideAndSeekState();
    private ZombieModeState zombieModeState = new ZombieModeState();
    private CtfState ctfState = new CtfState();

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        this.getCommand("helmetsOn").setExecutor(new HelmetsOnCommand(this));
        this.getCommand("seeker").setExecutor(new EquipSeekerCommand());
        this.getCommand("hider").setExecutor(new EquipHiderCommand());
        this.getCommand("start_zombie").setExecutor(new StartZombieModeCommand(this));
        this.getCommand("stop_zombie").setExecutor(new StopZombieModeCommand(this));
        this.getCommand("start_hns").setExecutor(new StartHideAndSeekCommand(this));
        this.getCommand("stop_hns").setExecutor(new StopHideAndSeekCommand(this));
        this.getCommand("start_ctf").setExecutor(new StartCtfCommand(this));
        this.getCommand("stop_ctf").setExecutor(new StopCtfCommand(this));
        this.getCommand("shift").setExecutor(new ShiftCommand(this));

        this.getServer().getPluginManager().registerEvents(new OnWeatherChangeEvent(), this);
        this.getServer().getPluginManager().registerEvents(new ZombieKillEvent(zombieModeState), this);
        this.getServer().getPluginManager().registerEvents(new HideAndSeekKillEvent(hideAndSeekState), this);
        this.getServer().getPluginManager().registerEvents(new CtfEvents(this), this);
    }

    public HideAndSeekState getHideAndSeekState() {
        return hideAndSeekState;
    }

    public ZombieModeState getZombieModeState() {
        return zombieModeState;
    }

    public CtfState getCtfState() {
        return ctfState;
    }
}
