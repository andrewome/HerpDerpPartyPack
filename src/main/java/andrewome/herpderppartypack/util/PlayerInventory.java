package andrewome.herpderppartypack.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.logging.Level;

import static andrewome.herpderppartypack.util.Constants.PLAYER_INVENTORY_SIZE;

public abstract class PlayerInventory {

    public static void clearInventory(Player player) {
        player.getInventory().clear();
    }

    public static void clearPotionEffects(Player player) {
        for (PotionEffect pe : player.getActivePotionEffects()) {
            player.removePotionEffect(pe.getType());
        }
    }

    private static boolean checkInventorySize(ItemStack[] items) {
        if (items.length != PLAYER_INVENTORY_SIZE) {
            Bukkit.getLogger().log(Level.WARNING, "ItemStack[] referenced in EquipPlayer has an invalid array size!");
            return false;
        }
        return true;
    }

    // refer to https://proxy.spigotmc.org/8d25a6d299b36fc40bfb9ffd9c2a21ea3ceb1128?url=http%3A%2F%2Fi.imgur.com%2FJDQnGk1.png
    protected static void equipPlayer(Player player, ItemStack[] items) {
        // len must be 41
        if (!checkInventorySize(items)) {
            return;
        }

        // equip items.
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null)
                player.getInventory().setItem(i, items[i]);
        }
    }

    protected static ItemStack[] generateEmptyInventory() {
        ItemStack[] is = new ItemStack[PLAYER_INVENTORY_SIZE];
        for(int i = 0; i < PLAYER_INVENTORY_SIZE; i++)
            is[i] = null;
        return is;
    }

    protected static ItemStack[] equipFullDiamondArmour(ItemStack[] items) {
        // len must be 41
        if (!checkInventorySize(items)) {
            return items;
        }

        // 39 is helmet, 38 is chest, 37 is legs, 36 is boots
        items[39] = new ItemStack(Material.DIAMOND_HELMET);
        items[38] = new ItemStack(Material.DIAMOND_CHESTPLATE);
        items[37] = new ItemStack(Material.DIAMOND_LEGGINGS);
        items[36] = new ItemStack(Material.DIAMOND_BOOTS);

        return items;
    }
}
