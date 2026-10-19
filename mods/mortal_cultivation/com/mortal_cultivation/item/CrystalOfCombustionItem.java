package com.mortal_cultivation.item;

import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;

public class CrystalOfCombustionItem extends EssenceCharcoalItem {
   public CrystalOfCombustionItem(Properties properties) {
      super(properties, 11000);
   }

   @Override
   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      String tooltipKey = "mortal_cultivation.item.crystal_of_combustion.tooltip";
      tooltip.add(Component.translatable(tooltipKey));
   }

   public int getUseDuration(ItemStack stack, LivingEntity entity) {
      return 14;
   }

   public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
      if (!level.isClientSide && entity instanceof Player player) {
         IExistence existence = TensuraStorages.getExistenceFrom(player);
         existence.setEP(existence.getEP() + 5000.0);
         existence.markDirty();
      }

      stack.shrink(1);
      return stack;
   }
}
