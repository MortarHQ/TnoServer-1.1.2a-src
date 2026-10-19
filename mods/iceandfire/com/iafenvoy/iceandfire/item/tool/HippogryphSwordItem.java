package com.iafenvoy.iceandfire.item.tool;

import com.iafenvoy.iceandfire.registry.IafToolMaterials;
import com.iafenvoy.uranus.object.RegistryHelper;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

public class HippogryphSwordItem extends SwordItem {
   public HippogryphSwordItem() {
      super(
         IafToolMaterials.HIPPOGRYPH_SWORD_TOOL_MATERIAL,
         new Properties().component(DataComponents.ATTRIBUTE_MODIFIERS, createAttributes(IafToolMaterials.HIPPOGRYPH_SWORD_TOOL_MATERIAL, 3, -2.4F))
      );
   }

   public boolean hurtEnemy(ItemStack stack, LivingEntity targetEntity, LivingEntity attacker) {
      float f = (float)attacker.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
      float f3 = 1.0F
         + getMultiplier(EnchantmentHelper.getEnchantmentLevel(RegistryHelper.getEnchantment(attacker.registryAccess(), Enchantments.SWEEPING_EDGE), attacker))
            * f;
      if (attacker instanceof Player player) {
         for (LivingEntity LivingEntity : attacker.level().getEntitiesOfClass(LivingEntity.class, targetEntity.getBoundingBox().inflate(1.0, 0.25, 1.0))) {
            if (LivingEntity != player && LivingEntity != targetEntity && !attacker.isAlliedTo(LivingEntity) && attacker.distanceToSqr(LivingEntity) < 9.0) {
               LivingEntity.knockback(0.4F, Mth.sin(attacker.getYRot() * (float) (Math.PI / 180.0)), -Mth.cos(attacker.getYRot() * (float) (Math.PI / 180.0)));
               LivingEntity.hurt(attacker.level().damageSources().playerAttack(player), f3);
            }
         }

         player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(), 1.0F, 1.0F);
         player.sweepAttack();
      }

      return super.hurtEnemy(stack, targetEntity, attacker);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
      super.appendHoverText(stack, context, tooltip, type);
      tooltip.add(Component.translatable("item.iceandfire.legendary_weapon.desc").withStyle(ChatFormatting.GRAY));
      tooltip.add(Component.translatable("item.iceandfire.hippogryph_sword.desc_0").withStyle(ChatFormatting.GRAY));
      tooltip.add(Component.translatable("item.iceandfire.hippogryph_sword.desc_1").withStyle(ChatFormatting.GRAY));
   }

   public static float getMultiplier(int level) {
      return 1.0F - 1.0F / (level + 1);
   }
}
