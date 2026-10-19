package com.finderfeed.raids_enhanced.content.items.electromancer_staff;

import com.finderfeed.fdlib.FDLibCalls;
import com.finderfeed.fdlib.systems.shake.FDShakeData;
import com.finderfeed.fdlib.systems.shake.PositionedScreenShakePacket;
import com.finderfeed.fdlib.util.FDTargetFinder;
import com.finderfeed.raids_enhanced.REUtil;
import com.finderfeed.raids_enhanced.content.entities.ball_lightning.BallLightningEntity;
import com.finderfeed.raids_enhanced.content.items.ItemWithDescription;
import com.finderfeed.raids_enhanced.content.particles.SimpleTexturedParticleOptions;
import com.finderfeed.raids_enhanced.content.particles.lightning_strike.LightningStrikeParticleOptions;
import com.finderfeed.raids_enhanced.content.particles.slash_particle.SlashParticleOptions;
import com.finderfeed.raids_enhanced.content.util.HorizontalCircleRandomDirections;
import com.finderfeed.raids_enhanced.init.REConfigs;
import com.finderfeed.raids_enhanced.init.REParticles;
import com.finderfeed.raids_enhanced.init.RESounds;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.CollisionContext;

public class ZapperStaff extends ItemWithDescription {
   public ZapperStaff(Properties props) {
      super(props, Component.translatable("raidsenhanced.item_description.zapper_staff").withStyle(ChatFormatting.AQUA));
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      if (!level.isClientSide) {
         ServerLevel serverLevel = (ServerLevel)level;
         Vec3 lookAngle = player.getLookAngle();
         if (!this.usesAsLightning(player)) {
            if (!player.isCreative()) {
               player.getCooldowns().addCooldown(this, REConfigs.CONFIG.get().zapperStaffLightningUseCooldown);
            }

            PositionedScreenShakePacket.send(
               (ServerLevel)level, FDShakeData.builder().frequency(5.0F).amplitude(2.5F).inTime(0).stayTime(0).outTime(3).build(), player.position(), 10.0
            );
            this.damageAndPushAwayEntities(player);
            this.castParticles(player);
            ZapperStaffCastEntity.summon(player, player.position());
            level.playSound(null, player.getX(), player.getY(), player.getZ(), RESounds.ENGINEER_LIGHTNING_CAST.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
         } else {
            Vec3 ppos = player.position().add(0.0, player.getEyeHeight() * 0.8F, 0.0).add(lookAngle.scale(0.5));
            serverLevel.sendParticles(
               new SlashParticleOptions(REParticles.ELECTRIC_SLASH.get(), lookAngle, 3, 0.0F, 2.0F, level.random.nextBoolean()),
               ppos.x,
               ppos.y,
               ppos.z,
               1,
               0.0,
               0.0,
               0.0,
               0.0
            );
            if (!player.isCreative()) {
               player.getCooldowns().addCooldown(this, REConfigs.CONFIG.get().zapperStaffBallLightningUseCooldown);
            }

            level.playSound(
               null,
               player.getX(),
               player.getY(),
               player.getZ(),
               RESounds.ENGINEER_BALL_LIGHTNING_LAUNCH.get(),
               SoundSource.HOSTILE,
               2.0F,
               level.random.nextFloat() * 0.1F + 0.8F
            );
            BallLightningEntity.summon(player, level, player.getEyePosition().add(lookAngle), lookAngle.scale(2.0));
         }

         return InteractionResultHolder.success(player.getItemInHand(hand));
      } else {
         return super.use(level, player, hand);
      }
   }

   public boolean usesAsLightning(Player player) {
      Vec3 lookAngle = player.getLookAngle();
      double dot = lookAngle.dot(new Vec3(0.0, 1.0, 0.0));
      if (player.onGround() && dot < -0.75) {
         ClipContext clipContext = new ClipContext(
            player.getEyePosition(), player.getEyePosition().add(lookAngle.scale(3.0)), Block.COLLIDER, Fluid.NONE, CollisionContext.empty()
         );
         BlockHitResult res = player.level().clip(clipContext);
         if (res.getType() != Type.MISS) {
            return false;
         }
      }

      return true;
   }

   private void damageAndPushAwayEntities(LivingEntity caster) {
      int damage = 10;
      Vec3 cylinderStart = caster.position().add(0.0, -2.0, 0.0);

      for (LivingEntity entity : FDTargetFinder.getEntitiesInCylinder(
         LivingEntity.class, caster.level(), cylinderStart, 3.0F + caster.getBbHeight(), 3.0F, e -> e != caster
      )) {
         entity.hurt(caster.level().damageSources().mobAttack(caster), damage * 1.5F);
         Vec3 between = entity.position().subtract(caster.position());
         Vec3 pushVector = between.normalize().scale(2.0);
         if (entity.onGround()) {
            pushVector = pushVector.add(0.0, 0.25, 0.0);
         }

         if (entity instanceof ServerPlayer serverPlayer) {
            FDLibCalls.setServerPlayerSpeed(serverPlayer, pushVector);
            serverPlayer.hasImpulse = true;
         } else {
            entity.setDeltaMovement(pushVector);
            entity.hasImpulse = true;
         }
      }
   }

   private void castParticles(LivingEntity caster) {
      Vec3 lpos = caster.position().add(caster.getForward().multiply(1.0, 0.0, 1.0).normalize().scale(0.75));
      REUtil.lightningDebris((ServerLevel)caster.level(), lpos, 60.0);
      List<ServerPlayer> players = FDTargetFinder.getEntitiesInSphere(ServerPlayer.class, caster.level(), caster.position(), 40.0F);

      for (Vec3 dir : new HorizontalCircleRandomDirections(caster.level().random, 6, 0.0F)) {
         Vec3 direction = dir.add(0.0, 0.5, 0.0);
         Vec3 pos = lpos.add(direction.multiply(0.4, 0.25, 0.4));

         for (ServerPlayer serverPlayer : players) {
            ((ServerLevel)caster.level())
               .sendParticles(
                  serverPlayer,
                  new LightningStrikeParticleOptions(REParticles.LIGHTNING_STRIKE.get(), direction, 1.0F, 4),
                  true,
                  pos.x,
                  pos.y,
                  pos.z,
                  1,
                  0.0,
                  0.0,
                  0.0,
                  0.0
               );
         }
      }

      for (ServerPlayer serverPlayer : players) {
         ((ServerLevel)caster.level())
            .sendParticles(
               serverPlayer,
               new SimpleTexturedParticleOptions(REParticles.VERTICAL_LIGHTNING.get(), 2.0F, 5),
               true,
               lpos.x,
               lpos.y + 2.0,
               lpos.z,
               1,
               0.0,
               0.0,
               0.0,
               0.0
            );
      }
   }
}
