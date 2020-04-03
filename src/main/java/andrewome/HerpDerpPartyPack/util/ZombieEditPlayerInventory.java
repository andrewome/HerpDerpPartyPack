package andrewome.HerpDerpPartyPack.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static andrewome.HerpDerpPartyPack.util.Constants.TICKS_PER_SECOND;

public abstract class ZombieEditPlayerInventory extends EditPlayerInventory {
    protected void equipZombie(Player player) {
        ItemStack[] items = generateEmptyInventory();

        // Diamond sword with 500 sharpness
        items[0] = new ItemStack(Material.DIAMOND_SWORD);
        items[0].addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 500);

        // Helmet set to zombie head
        items[39] = new ItemStack(Material.SKULL_ITEM, 1, (short) 2);

        // Set boots
        items[36] = new ItemStack(Material.LEATHER_BOOTS);
        items[36].addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 5);


        // Give potion of speed
        ItemStack potion = new ItemStack(Material.POTION, 64);
        PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();
        potionMeta.setDisplayName("8 min speed Pot kek");
        potionMeta.addCustomEffect(
                new PotionEffect(PotionEffectType.SPEED, 8 * 60 * TICKS_PER_SECOND, 1),
                true
        );
        potion.setItemMeta(potionMeta);
        items[1] = potion;

        // Give potion of leaping
        potion = new ItemStack(Material.POTION, 64);
        potionMeta = (PotionMeta) potion.getItemMeta();
        potionMeta.setDisplayName("jump speed pot YEET");
        potionMeta.addCustomEffect(
                new PotionEffect(PotionEffectType.JUMP, 8 * 60 * TICKS_PER_SECOND, 5),
                true
        );
        potion.setItemMeta(potionMeta);
        items[2] = potion;

        // Give regen
        items[3] = new ItemStack(Material.COOKED_BEEF, 64);

        equipPlayer(player, items);
    }
}
