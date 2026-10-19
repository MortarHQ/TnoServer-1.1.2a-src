package com.bobmowzie.mowziesmobs.server.ai.animation;

import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityCameraShake;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityFallingBlock;
import com.bobmowzie.mowziesmobs.server.entity.wroughtnaut.EntityWroughtnaut;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.ilexiconn.llibrary.server.animation.Animation;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class AnimationFWNStompAttackAI extends SimpleAnimationAI<EntityWroughtnaut> {
   public AnimationFWNStompAttackAI(EntityWroughtnaut entity, Animation animation) {
      super(entity, animation, true);
   }

   public void tick() {
      this.entity.setDeltaMovement(0.0, this.entity.getDeltaMovement().y, 0.0);
      double perpFacing = this.entity.yBodyRot * (Math.PI / 180.0);
      double facingAngle = perpFacing + (Math.PI / 2);
      int hitY = Mth.floor(this.entity.getBoundingBox().minY - 0.5);
      int tick = this.entity.getAnimationTick();
      int maxDistance = 6;
      ServerLevel world = (ServerLevel)this.entity.level();
      if (tick == 6) {
         this.entity.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_SHOUT_2.get(), 1.0F, 1.0F);
      } else if (tick > 9 && tick < 17) {
         if (tick == 10) {
            this.entity.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_STEP.get(), 1.2F, 0.5F + this.entity.getRandom().nextFloat() * 0.1F);
         } else if (tick == 12) {
            this.entity.playSound((SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), 2.0F, 1.0F + this.entity.getRandom().nextFloat() * 0.1F);
            EntityCameraShake.cameraShake(this.entity.level(), this.entity.position(), 25.0F, 0.1F, 0, 20);
         }

         if (tick % 2 == 0) {
            int distance = tick / 2 - 2;
            double spread = Math.PI * 2;
            int arcLen = Mth.ceil(distance * spread);
            double minY = this.entity.getBoundingBox().minY;
            double maxY = this.entity.getBoundingBox().maxY;

            for (int i = 0; i < arcLen; i++) {
               double theta = (i / (arcLen - 1.0) - 0.5) * spread + facingAngle;
               double vx = Math.cos(theta);
               double vz = Math.sin(theta);
               double px = this.entity.getX() + vx * distance;
               double pz = this.entity.getZ() + vz * distance;
               float factor = 1.0F - distance / 6.0F;
               AABB selection = new AABB(px - 1.5, minY, pz - 1.5, px + 1.5, maxY, pz + 1.5);

               for (Entity entity : world.getEntitiesOfClass(Entity.class, selection)) {
                  if (entity.onGround() && entity != this.entity && !(entity instanceof EntityFallingBlock)) {
                     float applyKnockbackResistance = 0.0F;
                     if (entity instanceof LivingEntity) {
                        entity.hurt(
                           entity.damageSources().mobAttack(this.entity),
                           (factor * 5.0F + 1.0F) * ((Double)ConfigHandler.COMMON.MOBS.FERROUS_WROUGHTNAUT.combatConfig.attackMultiplier.get()).floatValue()
                        );
                        applyKnockbackResistance = (float)((LivingEntity)entity).getAttribute(Attributes.KNOCKBACK_RESISTANCE).getValue();
                     }

                     double magnitude = world.random.nextDouble() * 0.15 + 0.1;
                     float x = 0.0F;
                     float y = 0.0F;
                     float z = 0.0F;
                     x = (float)(x + vx * factor * magnitude * (1.0F - applyKnockbackResistance));
                     y = (float)(y + (0.1 * (1.0F - applyKnockbackResistance) + factor * 0.15 * (1.0F - applyKnockbackResistance)));
                     z = (float)(z + vz * factor * magnitude * (1.0F - applyKnockbackResistance));
                     entity.setDeltaMovement(entity.getDeltaMovement().add(x, y, z));
                     if (entity instanceof ServerPlayer) {
                        ((ServerPlayer)entity).connection.send(new ClientboundSetEntityMotionPacket(entity));
                     }
                  }
               }

               if (world.random.nextBoolean()) {
                  int hitX = Mth.floor(px);
                  int hitZ = Mth.floor(pz);
                  BlockPos pos = new BlockPos(hitX, hitY, hitZ);
                  BlockPos abovePos = new BlockPos(pos).above();
                  BlockState block = world.getBlockState(pos);
                  BlockState blockAbove = world.getBlockState(abovePos);
                  if (!block.isAir() && block.isRedstoneConductor(world, pos) && !block.hasBlockEntity() && !blockAbove.blocksMotion()) {
                     EntityFallingBlock fallingBlock = new EntityFallingBlock(
                        (EntityType<?>)EntityHandler.FALLING_BLOCK.get(), world, block, (float)(0.4 + factor * 0.2)
                     );
                     fallingBlock.setPos(hitX + 0.5, hitY + 1, hitZ + 0.5);
                     world.addFreshEntity(fallingBlock);
                  }
               }
            }
         }
      }
   }
}
