package andrewome.herpderppartypack.command.hideandseek;

import andrewome.herpderppartypack.util.HideAndSeekEditPlayerInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHider extends HideAndSeekEditPlayerInventory implements CommandExecutor {

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
