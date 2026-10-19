package com.github.L_Ender.cataclysm.items;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ItemAbilities;

public class Gauntlet_of_Guard extends Cataclysm_Weapon {
   public Gauntlet_of_Guard(Properties group) {
      super(group);
   }

   public UseAnim getUseAnimation(ItemStack p_77661_1_) {
      return UseAnim.BOW;
   }

   public int getUseDuration(ItemStack pStack, LivingEntity pEntity) {
      return 72000;
   }

   public InteractionResultHolder<ItemStack> use(Level p_77659_1_, Player p_77659_2_, InteractionHand p_77659_3_) {
      ItemStack item = p_77659_2_.getItemInHand(p_77659_3_);
      InteractionHand otherhand = p_77659_3_ == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
      ItemStack otheritem = p_77659_2_.getItemInHand(otherhand);
      if (otheritem.canPerformAction(ItemAbilities.SHIELD_BLOCK) && !p_77659_2_.getCooldowns().isOnCooldown(otheritem.getItem())) {
         return InteractionResultHolder.fail(item);
      }

      p_77659_2_.startUsingItem(p_77659_3_);
      return InteractionResultHolder.consume(item);
   }

   public void onUseTick(Level worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
      double radius = 11.0;
      Level world = livingEntityIn.level();

      for (LivingEntity entity : world.getEntitiesOfClass(LivingEntity.class, livingEntityIn.getBoundingBox().inflate(radius))) {
         if (!(entity instanceof Player) || !((Player)entity).getAbilities().invulnerable) {
            Vec3 diff = entity.position().subtract(livingEntityIn.position().add(0.0, 0.0, 0.0));
            diff = diff.normalize().scale(0.1);
            entity.setDeltaMovement(entity.getDeltaMovement().subtract(diff));
         }
      }

      if (world.isClientSide) {
         for (int i = 0; i < 3; i++) {
            int j = world.random.nextInt(2) * 2 - 1;
            int k = world.random.nextInt(2) * 2 - 1;
            double d0 = livingEntityIn.getX() + 0.25 * j;
            double d1 = (float)livingEntityIn.getY() + world.random.nextFloat();
            double d2 = livingEntityIn.getZ() + 0.25 * k;
            double d3 = world.random.nextFloat() * j;
            double d4 = (world.random.nextFloat() - 0.5) * 0.125;
            double d5 = world.random.nextFloat() * k;
            world.addParticle(ParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
         }
      }
   }

   public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
      return true;
   }

   public boolean isEnchantable(ItemStack stack) {
      return true;
   }

   public int getEnchantmentValue() {
      return 16;
   }

   public boolean canAttackBlock(BlockState state, Level worldIn, BlockPos pos, Player player) {
      return !player.isCreative();
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltips, TooltipFlag flags) {
      super.appendHoverText(stack, context, tooltips, flags);
      tooltips.add(Component.translatable("item.cataclysm.gauntlet_of_guard.desc").withStyle(ChatFormatting.DARK_GREEN));
   }
}
