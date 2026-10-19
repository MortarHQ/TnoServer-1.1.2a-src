package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.Item.Properties;

public class FlowglazeShieldItem extends ShieldItem {
   public FlowglazeShieldItem(Properties properties) {
      super(properties);
   }

   public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
      return repairCandidate.is(ESItems.FLOWGLAZE.get()) || super.isValidRepairItem(stack, repairCandidate);
   }
}
