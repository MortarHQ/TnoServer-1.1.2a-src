package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.config.CMCommonConfig;
import com.github.L_Ender.cataclysm.entity.effect.Flame_Strike_Entity;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModSounds;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class The_Immolator extends Cataclysm_Weapon {
   public The_Immolator(Properties properties) {
      super(properties);
   }

   public UseAnim getUseAnimation(ItemStack p_77661_1_) {
      return UseAnim.SPEAR;
   }

   public int getUseDuration(ItemStack pStack, LivingEntity pEntity) {
      return 72000;
   }

   public void releaseUsing(ItemStack p_43394_, Level p_43395_, LivingEntity p_43396_, int p_43397_) {
      if (p_43396_ instanceof Player player) {
         int i = this.getUseDuration(p_43394_, p_43396_) - p_43397_;
         boolean hasSucceeded = false;
         double headY = player.getY() + 1.0;
         int standingOnY = Mth.floor(player.getY()) - 2;
         if (i >= 45) {
            float f1 = (float)Math.cos(Math.toRadians(p_43396_.getYRot() + 90.0F));
            float f2 = (float)Math.sin(Math.toRadians(p_43396_.getYRot() + 90.0F));
            float f0 = (float)Mth.atan2(f1, f2);
            if (this.spawnFlameStrike(player.getX(), player.getZ(), standingOnY, headY, f0, 45, 0, 0, p_43395_, 2.5F, player)) {
               hasSucceeded = true;
            }

            if (hasSucceeded) {
               if (!p_43395_.isClientSide) {
                  player.getCooldowns().addCooldown(this, CMCommonConfig.Immolator.cooldown);
               }

               ScreenShake_Entity.ScreenShake(p_43395_, player.position(), 30.0F, 0.15F, 0, 30);
               p_43395_.playSound(
                  null,
                  player.getX(),
                  player.getY(),
                  player.getZ(),
                  (SoundEvent)ModSounds.EXPLOSION.get(),
                  SoundSource.PLAYERS,
                  1.5F,
                  1.0F / (player.getRandom().nextFloat() * 0.4F + 0.8F)
               );
            }
         }
      }
   }

   public void onUseTick(Level worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
      int i = this.getUseDuration(stack, livingEntityIn) - count;
      if (i == 10) {
         this.masseffectParticle(worldIn, livingEntityIn, 2.0F);
      }

      if (i == 20) {
         this.masseffectParticle(worldIn, livingEntityIn, 3.5F);
      }

      if (i == 30) {
         this.masseffectParticle(worldIn, livingEntityIn, 5.0F);
      }

      if (i == 45) {
         livingEntityIn.playSound((SoundEvent)ModSounds.MALEDICTUS_SHORT_ROAR.get(), 1.0F, 1.0F);
      }
   }

   private void yall(Level world, LivingEntity caster) {
      double radius = 6.0;
      ScreenShake_Entity.ScreenShake(world, caster.position(), 30.0F, 0.1F, 0, 30);
      world.playSound(
         null,
         caster.getX(),
         caster.getY(),
         caster.getZ(),
         (SoundEvent)ModSounds.EXPLOSION.get(),
         SoundSource.PLAYERS,
         1.5F,
         1.0F / (caster.getRandom().nextFloat() * 0.4F + 0.8F)
      );

      for (Entity entity : world.getEntities(caster, caster.getBoundingBox().inflate(radius, radius, radius))) {
         if (entity instanceof LivingEntity) {
            entity.hurt(world.damageSources().mobAttack(caster), (float)caster.getAttributeValue(Attributes.ATTACK_DAMAGE) * 2.0F);
         }
      }
   }

   private boolean spawnFlameStrike(
      double x, double z, double minY, double maxY, float rotation, int duration, int wait, int delay, Level world, float radius, LivingEntity player
   ) {
      BlockPos blockpos = BlockPos.containing(x, maxY, z);
      boolean flag = false;
      double d0 = 0.0;

      do {
         BlockPos blockpos1 = blockpos.below();
         BlockState blockstate = world.getBlockState(blockpos1);
         if (blockstate.isFaceSturdy(world, blockpos1, Direction.UP)) {
            if (!world.isEmptyBlock(blockpos)) {
               BlockState blockstate1 = world.getBlockState(blockpos);
               VoxelShape voxelshape = blockstate1.getCollisionShape(world, blockpos);
               if (!voxelshape.isEmpty()) {
                  d0 = voxelshape.max(Axis.Y);
               }
            }

            flag = true;
            break;
         }

         blockpos = blockpos.below();
      } while (blockpos.getY() >= minY);

      if (flag) {
         world.addFreshEntity(new Flame_Strike_Entity(world, x, blockpos.getY() + d0, z, rotation, duration, wait, delay, radius, 6.0F, 2.0F, false, player));
         return true;
      } else {
         return false;
      }
   }

   private void masseffectParticle(Level world, LivingEntity caster, float radius) {
      if (world.isClientSide) {
         for (int j = 0; j < 70; j++) {
            float angle = (float)(Math.random() * 2.0 * Math.PI);
            double distance = Math.sqrt(Math.random()) * radius;
            double extraX = caster.getX() + distance * Mth.cos(angle);
            double extraY = caster.getY() + 0.3F;
            double extraZ = caster.getZ() + distance * Mth.sin(angle);
            world.addParticle(ParticleTypes.FLAME, extraX, extraY, extraZ, 0.0, world.random.nextGaussian() * 0.04, 0.0);
         }
      }
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack itemstack = player.getItemInHand(hand);
      ItemStack otherHand = hand == InteractionHand.MAIN_HAND
         ? player.getItemInHand(InteractionHand.OFF_HAND)
         : player.getItemInHand(InteractionHand.MAIN_HAND);
      if (otherHand.is((Item)ModItems.THE_IMMOLATOR.get())) {
         player.startUsingItem(hand);
         return InteractionResultHolder.consume(itemstack);
      } else {
         return InteractionResultHolder.fail(itemstack);
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

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flags) {
      tooltip.add(Component.translatable("item.cataclysm.annihilator.desc").withStyle(ChatFormatting.DARK_GREEN));
      tooltip.add(Component.translatable("item.cataclysm.immolator.desc").withStyle(ChatFormatting.DARK_GREEN));
      tooltip.add(Component.translatable("item.cataclysm.immolator2.desc").withStyle(ChatFormatting.DARK_GREEN));
   }
}
