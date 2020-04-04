package andrewome.herpderppartypack.gamemodes.hideandseek.util;

import andrewome.herpderppartypack.util.PlayerInventory;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static andrewome.herpderppartypack.util.Constants.TICKS_PER_SECOND;

public abstract class HideAndSeekPlayerInventory extends PlayerInventory {
    public static void equipHider(Player player) {
        // Generate inventory and arm player
        ItemStack[] items = generateEmptyInventory();

        // feather, food, invis pots and ender pearl
        items[0] = new ItemStack(Material.FEATHER);
        items[1] = new ItemStack(Material.ENDER_PEARL);
        items[2] = new ItemStack(Material.SNOW_BALL, 64);
        items[3] = new ItemStack(Material.COOKED_BEEF, 64);
        ItemStack potion = new ItemStack(Material.POTION, 64);
        PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();
        potionMeta.setDisplayName("8 min Invis Pot kek");
        potionMeta.addCustomEffect(
                new PotionEffect(PotionEffectType.INVISIBILITY, 8 * 60 * TICKS_PER_SECOND, 1),
                true
        );
        potion.setItemMeta(potionMeta);
        items[4] = potion;

        // equip player with said items
        equipPlayer(player, items);
    }

    public static void equipSeeker(Player player) {
        // Generate inventory and arm player
        ItemStack[] items = generateEmptyInventory();

        // diamond sword in inventory slot 0, bow in inventory slot 1 etc
        items[0] = new ItemStack(Material.DIAMOND_SWORD);
        items[0].addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 8);
        items[1] = new ItemStack(Material.BOW);
        items[1].addEnchantment(Enchantment.ARROW_DAMAGE, 2);
        items[2] = new ItemStack(Material.COOKED_BEEF, 64);
        items[3] = new ItemStack(Material.ARROW, 64);

        // equip with full diamond armour
        items = equipFullDiamondArmour(items);
        equipPlayer(player, items);
    }
}