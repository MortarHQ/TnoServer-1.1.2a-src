package nadiendev.constructionwand.items.wand;

import javax.annotation.Nonnull;
import nadiendev.constructionwand.basics.ConfigServer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;

public class ItemWandBasic extends ItemWand {
   private final Tier tier;

   public ItemWandBasic(Properties properties, Tier tier) {
      super(properties.durability(tier.getUses()));
      this.tier = tier;
   }

   public int getMaxDamage(ItemStack stack) {
      return ConfigServer.getWandProperties(this).getDurability();
   }

   @Override
   public int remainingDurability(ItemStack stack) {
      return stack.getMaxDamage() - stack.getDamageValue();
   }

   @Override
   public boolean isValidRepairItem(@Nonnull ItemStack toRepair, @Nonnull ItemStack repair) {
      return this.tier.getRepairIngredient().test(repair);
   }
}
