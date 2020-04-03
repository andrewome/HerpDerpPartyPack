package andrewome.HerpDerpPartyPack.command.HideAndSeek;

import andrewome.HerpDerpPartyPack.util.HideAndSeekEditPlayerInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSeeker extends HideAndSeekEditPlayerInventory implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player))
            return false;

        Player player = (Player) commandSender;

        // Clear inventory and potion effects
        clearInventory(player);
        clearPotionEffects(player);

        // Equip player
        equipSeeker(player);

        return true;
    }
}
