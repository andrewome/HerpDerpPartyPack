package andrewome.herpderppartypack.gamemodes.hideandseek.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static andrewome.herpderppartypack.gamemodes.hideandseek.util.HideAndSeekPlayerInventory.equipHider;
import static andrewome.herpderppartypack.util.PlayerInventory.clearInventory;
import static andrewome.herpderppartypack.util.PlayerInventory.clearPotionEffects;

public class EquipHiderCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player))
            return false;

        Player player = (Player) commandSender;

        // Clear inventory and effects
        clearInventory(player);
        clearPotionEffects(player);

        // Equip player
        equipHider(player);

        return true;
    }
}
