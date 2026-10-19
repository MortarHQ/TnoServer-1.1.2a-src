package com.iafenvoy.iceandfire.item.tool;

import com.iafenvoy.iceandfire.registry.IafSounds;
import com.iafenvoy.iceandfire.registry.IafToolMaterials;
import java.util.List;
import java.util.Random;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;

public class AmphithereMacuahuitlItem extends SwordItem {
   public AmphithereMacuahuitlItem() {
      super(
         IafToolMaterials.AMPHITHERE_SWORD_TOOL_MATERIAL,
         new Properties().component(DataComponents.ATTRIBUTE_MODIFIERS, createAttributes(IafToolMaterials.AMPHITHERE_SWORD_TOOL_MATERIAL, 3, -2.4F))
      );
   }

   public boolean hurtEnemy(ItemStack stack, LivingEntity targetEntity, LivingEntity attacker) {
      targetEntity.playSound((SoundEvent)IafSounds.AMPHITHERE_GUST.get(), 1.0F, 1.0F);
      targetEntity.playSound(SoundEvents.SHIELD_BLOCK, 1.0F, 1.0F);
      targetEntity.hasImpulse = true;
      double xRatio = -Mth.sin(attacker.getYRot() * (float) (Math.PI / 180.0));
      double zRatio = Mth.cos(attacker.getYRot() * (float) (Math.PI / 180.0));
      float strength = -0.6F;
      float f = Mth.sqrt((float)(xRatio * xRatio + zRatio * zRatio));
      targetEntity.setDeltaMovement(
         targetEntity.getDeltaMovement().x / 2.0 - xRatio / f * strength, 0.8, targetEntity.getDeltaMovement().z / 2.0 - zRatio / f * strength
      );
      Random rand = new Random();

      for (int i = 0; i < 20; i++) {
         double d0 = rand.nextGaussian() * 0.02;
         double d1 = rand.nextGaussian() * 0.02;
         double d2 = rand.nextGaussian() * 0.02;
         targetEntity.level()
            .addParticle(
               ParticleTypes.CLOUD,
               targetEntity.getX() + rand.nextFloat() * targetEntity.getBbWidth() * 5.0F - targetEntity.getBbWidth() - d0 * 10.0,
               targetEntity.getY() + rand.nextFloat() * targetEntity.getBbHeight() - d1 * 10.0,
               targetEntity.getZ() + rand.nextFloat() * targetEntity.getBbWidth() * 5.0F - targetEntity.getBbWidth() - d2 * 10.0,
               d0,
               d1,
               d2
            );
      }

      return super.hurtEnemy(stack, targetEntity, attacker);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
      super.appendHoverText(stack, context, tooltip, type);
      tooltip.add(Component.translatable("item.iceandfire.legendary_weapon.desc").withStyle(ChatFormatting.GRAY));
      tooltip.add(Component.translatable("item.iceandfire.amphithere_macuahuitl.desc_0").withStyle(ChatFormatting.GRAY));
      tooltip.add(Component.translatable("item.iceandfire.amphithere_macuahuitl.desc_1").withStyle(ChatFormatting.GRAY));
   }
}
