package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.config.CMCommonConfig;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Flame_Jet_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.util.AttributeUtils;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Infernal_forge extends PickaxeItem {
   public Infernal_forge(Tier toolMaterial, Properties props) {
      super(toolMaterial, props);
   }

   public boolean hurtEnemy(ItemStack heldItemStack, LivingEntity target, LivingEntity attacker) {
      if (!target.level().isClientSide) {
         target.playSound((SoundEvent)ModSounds.HAMMERTIME.get(), 0.5F, 0.5F);
         target.knockback(1.0, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
      }

      return true;
   }

   public InteractionResult useOn(UseOnContext context) {
      ItemStack stack = context.getItemInHand();
      Player player = context.getPlayer();
      if (player != null && player.getMainHandItem() == stack) {
         this.EarthQuake(context, player);
         player.getCooldowns().addCooldown(this, CMCommonConfig.InfernalForge.cooldown);
         return InteractionResult.SUCCESS;
      } else {
         return super.useOn(context);
      }
   }

   private void EarthQuake(UseOnContext context, Player player) {
      Level world = context.getLevel();
      boolean berserk = player.getMaxHealth() * 1.0F / 2.0F >= player.getHealth();
      double radius = 4.0;
      if (world.isClientSide) {
         BlockState block = world.getBlockState(player.blockPosition().below());
         double NumberofParticles = radius * 4.0;

         for (double i = 0.0; i < 80.0; i++) {
            double d0 = player.getX() + radius * Mth.sin((float)(i / NumberofParticles * 360.0));
            double d1 = player.getY() + 0.15;
            double d2 = player.getZ() + radius * Mth.cos((float)(i / NumberofParticles * 360.0));
            double d3 = world.getRandom().nextGaussian() * 0.2;
            double d4 = world.getRandom().nextGaussian() * 0.2;
            double d5 = world.getRandom().nextGaussian() * 0.2;
            world.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, block), d0, d1, d2, d3, d4, d5);
            if (berserk) {
               world.addParticle(ParticleTypes.FLAME, d0, d1, d2, d3, d4, d5);
            }
         }
      } else {
         float finalDamage = AttributeUtils.OriginDamage(player, context.getItemInHand());
         ScreenShake_Entity.ScreenShake(world, player.position(), 30.0F, 0.1F, 0, 30);
         world.playSound(
            null,
            player.getX(),
            player.getY(),
            player.getZ(),
            (SoundEvent)ModSounds.EXPLOSION.get(),
            SoundSource.PLAYERS,
            1.5F,
            1.0F / (player.getRandom().nextFloat() * 0.4F + 0.8F)
         );

         for (Entity entity : world.getEntities(player, player.getBoundingBox().inflate(radius, radius, radius))) {
            if (entity instanceof LivingEntity) {
               entity.hurt(world.damageSources().mobAttack(player), finalDamage);
               entity.setDeltaMovement(entity.getDeltaMovement().multiply(0.0, 2.0, 0.0));
               if (berserk) {
                  entity.igniteForSeconds(5.0F);
               }
            }
         }
      }
   }

   private void spawnJet(Level level, LivingEntity player, double x, double z, double minY, double maxY, float rotation, int delay) {
      BlockPos blockpos = BlockPos.containing(x, maxY, z);
      boolean flag = false;
      double d0 = 0.0;

      do {
         BlockPos blockpos1 = blockpos.below();
         BlockState blockstate = level.getBlockState(blockpos1);
         if (blockstate.isFaceSturdy(level, blockpos1, Direction.UP)) {
            if (!level.isEmptyBlock(blockpos)) {
               BlockState blockstate1 = level.getBlockState(blockpos);
               VoxelShape voxelshape = blockstate1.getCollisionShape(level, blockpos);
               if (!voxelshape.isEmpty()) {
                  d0 = voxelshape.max(Axis.Y);
               }
            }

            flag = true;
            break;
         }

         blockpos = blockpos.below();
      } while (blockpos.getY() >= Mth.floor(minY) - 1);

      if (flag) {
         level.addFreshEntity(new Flame_Jet_Entity(level, x, blockpos.getY() + d0, z, rotation, delay, 7.0F, player));
      }
   }

   public void setDamage(ItemStack stack, int damage) {
      super.setDamage(stack, 0);
   }

   public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
      return true;
   }

   public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStackMaterial) {
      return false;
   }

   public int getEnchantmentValue() {
      return 16;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flags) {
      tooltip.add(Component.translatable("item.cataclysm.infernal_forge.desc").withStyle(ChatFormatting.DARK_GREEN));
      tooltip.add(Component.translatable("item.cataclysm.infernal_forge.desc2").withStyle(ChatFormatting.DARK_GREEN));
   }
}
