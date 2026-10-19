package twilightforest.item;

import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import twilightforest.entity.projectile.IceArrow;
import twilightforest.util.TFToolMaterials;

public class IceBowItem extends BowItem {
   public IceBowItem(Properties properties) {
      super(properties);
   }

   public AbstractArrow customArrow(AbstractArrow arrow, ItemStack projectileStack, ItemStack weaponStack) {
      return new IceArrow(arrow, projectileStack.copyWithCount(1), weaponStack);
   }

   public boolean isValidRepairItem(ItemStack toRepair, ItemStack repairWith) {
      return TFToolMaterials.ICE.getRepairIngredient().test(repairWith);
   }
}
