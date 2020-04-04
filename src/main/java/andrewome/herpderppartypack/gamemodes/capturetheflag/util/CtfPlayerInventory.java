package andrewome.herpderppartypack.gamemodes.capturetheflag.util;

import andrewome.herpderppartypack.util.PlayerInventory;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;

import java.util.HashMap;

import static andrewome.herpderppartypack.gamemodes.capturetheflag.util.CtfState.BLUE_TEAM_BUTTON_MATERIAL;
import static andrewome.herpderppartypack.gamemodes.capturetheflag.util.CtfState.RED_TEAM_BUTTON_MATERIAL;

public abstract class CtfPlayerInventory extends PlayerInventory {
    public static void equipPlayer(Player player) {
        ItemStack[] items = generateEmptyInventory();

        // Give bow, iron sword and food
        items[0] = new ItemStack(Material.BOW);
        items[0].addEnchantment(Enchantment.ARROW_INFINITE, 1);
        items[1] = new ItemStack(Material.STONE_SWORD);
        items[2] = new ItemStack(Material.ARROW, 64);
        items[3] = new ItemStack(Material.COOKED_BEEF, 64);

        // Arm player
        equipPlayer(player, items);
    }

    // returns true if player has the flag and click correct button!
    public static boolean checkFlagAndButton(CtfState state, Player player, boolean isBlue, Material clickedBlock) {
        // Get invent
        ItemStack[] inventory = player.getInventory().getContents();

        // Check if got correct wool
        if (containsCorrectWool(inventory, isBlue) && clickedCorrectBlock(clickedBlock, isBlue))
            return true;

        return false;
    }

    private static boolean clickedCorrectBlock(Material clickedBlock, boolean isBlue) {
        if (isBlue && clickedBlock == BLUE_TEAM_BUTTON_MATERIAL || !isBlue && clickedBlock == RED_TEAM_BUTTON_MATERIAL)
            return true;
        return false;
    }

    private static boolean containsCorrectWool(ItemStack[] inventory, boolean isBlue) {
        Wool blueWool = new Wool(DyeColor.BLUE);
        Wool redWool = new Wool(DyeColor.RED);
        for (ItemStack is : inventory) {
            if (is == null)
                continue;

            if (is.getType() == Material.WOOL) {
                Wool is_wool = (Wool) is.getData();
                // contains blue wool and is red team
                if (is_wool.equals(blueWool) && !isBlue)
                    return true;
                // contains red wool and is blue team
                if (is_wool.equals(redWool) && isBlue)
                    return true;
            }
        }
        return false;
    }
}
