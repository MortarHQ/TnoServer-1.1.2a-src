package com.mortal_cultivation.item;

import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class DemonSpiritPillItem extends Item {
   private static final double EP_INCREASE = 1000.0;
   private static final double MP_INCREASE = 500.0;

   public DemonSpiritPillItem(Properties properties) {
      super(properties);
   }

   public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
      ItemStack resultStack = super.finishUsingItem(stack, level, livingEntity);
      if (!level.isClientSide && livingEntity instanceof Player player) {
         IExistence existence = TensuraStorages.getExistenceFrom(player);
         existence.setEP(existence.getEP() + 1000.0);
         AttributeInstance magicule = player.getAttribute(TensuraAttributes.MAX_MAGICULE);
         if (magicule != null) {
            magicule.setBaseValue(magicule.getBaseValue() + 500.0);
            existence.setMagicule(existence.getMagicule() + 500.0);
         }

         existence.markDirty();
      }

      return resultStack;
   }

   public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.EAT;
   }
}
