package com.iafenvoy.iceandfire.item;

import com.iafenvoy.iceandfire.data.component.MiscData;
import com.iafenvoy.iceandfire.registry.IafDataComponents;
import com.iafenvoy.iceandfire.registry.IafSounds;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class DeathwormGauntletItem extends Item {
   public DeathwormGauntletItem() {
      super(new Properties().durability(500).component((DataComponentType)IafDataComponents.USER_ID.get(), -1));
   }

   public int getUseDuration(ItemStack stack, LivingEntity user) {
      return 10;
   }

   public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.BOW;
   }

   public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand hand) {
      ItemStack itemStackIn = playerIn.getItemInHand(hand);
      playerIn.startUsingItem(hand);
      return new InteractionResultHolder(InteractionResult.PASS, itemStackIn);
   }

   public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseTicks) {
      if ((Integer)stack.getOrDefault((DataComponentType)IafDataComponents.USER_ID.get(), -1) != entity.getId()) {
         stack.set((DataComponentType)IafDataComponents.USER_ID.get(), entity.getId());
      }

      MiscData.get(entity).setLungeTicks(this.getUseDuration(stack, entity) - remainingUseTicks);
   }

   public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity user, int timeLeft) {
      stack.set((DataComponentType)IafDataComponents.USER_ID.get(), -1);
   }

   public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
      if (user instanceof Player player) {
         Vec3 Vector3d = player.getViewVector(1.0F).normalize();
         double range = 5.0;

         for (LivingEntity livingEntity : world.getEntitiesOfClass(
            LivingEntity.class,
            new AABB(player.getX() - range, player.getY() - range, player.getZ() - range, player.getX() + range, player.getY() + range, player.getZ() + range),
            livingEntityx -> livingEntityx != player
         )) {
            Vec3 delta = new Vec3(livingEntity.getX() - player.getX(), livingEntity.getY() - player.getY(), livingEntity.getZ() - player.getZ());
            double d0 = delta.length();
            delta = delta.normalize();
            double d1 = Vector3d.dot(delta);
            boolean canSee = d1 > 1.0 - 0.5 / d0 && player.hasLineOfSight(livingEntity);
            if (canSee) {
               livingEntity.hurt(world.damageSources.playerAttack(player), 3.0F);
               livingEntity.knockback(0.5, livingEntity.getX() - player.getX(), livingEntity.getZ() - player.getZ());
            }
         }

         player.getCooldowns().addCooldown(this, 20);
      }

      user.playSound((SoundEvent)IafSounds.DEATHWORM_ATTACK.get(), 1.0F, 1.0F);
      stack.set((DataComponentType)IafDataComponents.USER_ID.get(), -1);
      return super.finishUsingItem(stack, world, user);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
      super.appendHoverText(stack, context, tooltip, type);
      tooltip.add(Component.translatable("item.iceandfire.legendary_weapon.desc").withStyle(ChatFormatting.GRAY));
      tooltip.add(Component.translatable("item.iceandfire.deathworm_gauntlet.desc_0").withStyle(ChatFormatting.GRAY));
      tooltip.add(Component.translatable("item.iceandfire.deathworm_gauntlet.desc_1").withStyle(ChatFormatting.GRAY));
   }
}
