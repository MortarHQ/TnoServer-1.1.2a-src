package net.unusual.block_factorys_bosses.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.unusual.block_factorys_bosses.procedures.AchievementIconsEntitySwingsItemProcedure;

public class AchievementIconsItem extends Item {
   public AchievementIconsItem() {
      super(new Properties().stacksTo(64).rarity(Rarity.COMMON));
   }

   public boolean onEntitySwing(ItemStack itemstack, LivingEntity entity, InteractionHand hand) {
      boolean retval = super.onEntitySwing(itemstack, entity, hand);
      AchievementIconsEntitySwingsItemProcedure.execute(itemstack);
      return retval;
   }
}
