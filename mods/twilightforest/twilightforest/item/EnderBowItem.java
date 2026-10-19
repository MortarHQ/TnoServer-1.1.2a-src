package twilightforest.item;

import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;

public class EnderBowItem extends BowItem {
   public static final String KEY = "twilightforest:ender";

   public EnderBowItem(Properties properties) {
      super(properties);
   }

   public AbstractArrow customArrow(AbstractArrow arrow, ItemStack projectileStack, ItemStack weaponStack) {
      arrow.getPersistentData().putBoolean("twilightforest:ender", true);
      return arrow;
   }
}
