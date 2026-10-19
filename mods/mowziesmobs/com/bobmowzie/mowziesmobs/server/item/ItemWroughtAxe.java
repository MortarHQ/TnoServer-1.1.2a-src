package com.bobmowzie.mowziesmobs.server.item;

import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.DataHandler;
import com.bobmowzie.mowziesmobs.server.capability.PlayerData;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;

public class ItemWroughtAxe extends AxeItem {
   public ItemWroughtAxe(Properties properties) {
      super(Tiers.IRON, properties);
   }

   public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStackMaterial) {
      return (Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.AXE_OF_A_THOUSAND_METALS.breakable.get()
         && super.isValidRepairItem(itemStack, itemStackMaterial);
   }

   public boolean isEnchantable(ItemStack p_77616_1_) {
      return true;
   }

   public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
      return super.supportsEnchantment(stack, enchantment);
   }

   public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
      if (!(entity instanceof Player)) {
         return false;
      }

      PlayerData data = DataHandler.getData(entity, DataHandler.PLAYER_DATA);
      return !data.getAxeCanAttack() && data.getUntilAxeSwing() > 0;
   }

   public boolean onEntitySwing(ItemStack stack, LivingEntity entity, InteractionHand hand) {
      return entity instanceof Player ? DataHandler.<PlayerData>getData(entity, DataHandler.PLAYER_DATA).getUntilAxeSwing() > 0 : false;
   }

   public boolean hurtEnemy(ItemStack heldItemStack, LivingEntity entityHit, LivingEntity attacker) {
      if ((Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.AXE_OF_A_THOUSAND_METALS.breakable.get()) {
         heldItemStack.hurtAndBreak(2, attacker, LivingEntity.getSlotForHand(InteractionHand.MAIN_HAND));
      }

      if (!entityHit.level().isClientSide) {
         entityHit.playSound(SoundEvents.ANVIL_LAND, 0.3F, 0.5F);
      }

      return true;
   }

   public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
      if (hand == InteractionHand.MAIN_HAND && player.getAttackStrengthScale(0.5F) == 1.0F) {
         PlayerData data = DataHandler.getData(player, DataHandler.PLAYER_DATA);
         if (data.getUntilAxeSwing() <= 0) {
            boolean verticalAttack = player.isShiftKeyDown() && player.onGround();
            if (verticalAttack) {
               AbilityHandler.INSTANCE.sendAbilityMessage(player, AbilityHandler.WROUGHT_AXE_SLAM_ABILITY);
            } else {
               AbilityHandler.INSTANCE.sendAbilityMessage(player, AbilityHandler.WROUGHT_AXE_SWING_ABILITY);
            }

            data.setVerticalSwing(verticalAttack);
            data.setUntilAxeSwing(30);
            player.startUsingItem(hand);
            if ((Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.AXE_OF_A_THOUSAND_METALS.breakable.get() && !player.getAbilities().instabuild) {
               player.getItemInHand(hand).hurtAndBreak(2, player, LivingEntity.getSlotForHand(hand));
            }
         }

         return new InteractionResultHolder(InteractionResult.SUCCESS, player.getItemInHand(hand));
      } else {
         return super.use(world, player, hand);
      }
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
      super.appendHoverText(stack, context, tooltip, flagIn);
      if (!(Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.AXE_OF_A_THOUSAND_METALS.breakable.get()) {
         tooltip.add(Component.translatable(this.getDescriptionId() + ".text.0").setStyle(ItemHandler.TOOLTIP_STYLE));
      }

      tooltip.add(Component.translatable(this.getDescriptionId() + ".text.1").setStyle(ItemHandler.TOOLTIP_STYLE));
      tooltip.add(Component.translatable(this.getDescriptionId() + ".text.2").setStyle(ItemHandler.TOOLTIP_STYLE));
   }
}
