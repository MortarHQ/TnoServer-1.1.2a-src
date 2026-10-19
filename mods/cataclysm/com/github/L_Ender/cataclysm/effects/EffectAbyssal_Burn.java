package com.github.L_Ender.cataclysm.effects;

import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent.ChorusFruit;

public class EffectAbyssal_Burn extends MobEffect {
   public EffectAbyssal_Burn() {
      super(MobEffectCategory.HARMFUL, 6619391);
   }

   public boolean applyEffectTick(LivingEntity LivingEntityIn, int amplifier) {
      boolean flag = LivingEntityIn.hurt(LivingEntityIn.damageSources().source(CMDamageTypes.ABYSSAL_BURN), 1.0F);
      if (flag
         && LivingEntityIn.getRandom().nextFloat() < 0.75F - LivingEntityIn.getHealth() / LivingEntityIn.getMaxHealth()
         && !LivingEntityIn.level().isClientSide) {
         double d0 = LivingEntityIn.getX();
         double d1 = LivingEntityIn.getY();
         double d2 = LivingEntityIn.getZ();

         for (int i = 0; i < 8; i++) {
            double d3 = LivingEntityIn.getX() + (LivingEntityIn.getRandom().nextDouble() - 0.5) * 8.0;
            double d4 = Mth.clamp(
               LivingEntityIn.getY() + (LivingEntityIn.getRandom().nextInt(8) - 4),
               LivingEntityIn.level().getMinBuildHeight(),
               LivingEntityIn.level().getMinBuildHeight() + ((ServerLevel)LivingEntityIn.level()).getLogicalHeight() - 1
            );
            double d5 = LivingEntityIn.getZ() + (LivingEntityIn.getRandom().nextDouble() - 0.5) * 8.0;
            if (LivingEntityIn.isPassenger()) {
               LivingEntityIn.stopRiding();
            }

            Vec3 vec3 = LivingEntityIn.position();
            LivingEntityIn.level().gameEvent(GameEvent.TELEPORT, vec3, Context.of(LivingEntityIn));
            ChorusFruit event = new ChorusFruit(LivingEntityIn, d3, d4, d5);
            if (this.randomTeleportInwater(LivingEntityIn, event.getTargetX(), event.getTargetY(), event.getTargetZ(), true)) {
               SoundEvent soundevent = LivingEntityIn instanceof Fox ? SoundEvents.FOX_TELEPORT : SoundEvents.CHORUS_FRUIT_TELEPORT;
               LivingEntityIn.level().playSound((Player)null, d0, d1, d2, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
               LivingEntityIn.playSound(soundevent, 1.0F, 1.0F);
               break;
            }
         }
      }

      return true;
   }

   private boolean randomTeleportInwater(LivingEntity param1, double param2, double param4, double param6, boolean param8) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 1
      // 01: invokevirtual net/minecraft/world/entity/LivingEntity.getX ()D
      // 04: dstore 9
      // 06: aload 1
      // 07: invokevirtual net/minecraft/world/entity/LivingEntity.getY ()D
      // 0a: dstore 11
      // 0c: aload 1
      // 0d: invokevirtual net/minecraft/world/entity/LivingEntity.getZ ()D
      // 10: dstore 13
      // 12: dload 4
      // 14: dstore 15
      // 16: bipush 0
      // 17: istore 17
      // 19: dload 2
      // 1a: dload 4
      // 1c: dload 6
      // 1e: invokestatic net/minecraft/core/BlockPos.containing (DDD)Lnet/minecraft/core/BlockPos;
      // 21: astore 18
      // 23: aload 1
      // 24: invokevirtual net/minecraft/world/entity/LivingEntity.level ()Lnet/minecraft/world/level/Level;
      // 27: astore 19
      // 29: aload 19
      // 2b: aload 18
      // 2d: invokevirtual net/minecraft/world/level/Level.hasChunkAt (Lnet/minecraft/core/BlockPos;)Z
      // 30: ifeq 8d
      // 33: bipush 0
      // 34: istore 20
      // 36: iload 20
      // 38: ifne 73
      // 3b: aload 18
      // 3d: invokevirtual net/minecraft/core/BlockPos.getY ()I
      // 40: aload 19
      // 42: invokevirtual net/minecraft/world/level/Level.getMinBuildHeight ()I
      // 45: if_icmple 73
      // 48: aload 18
      // 4a: invokevirtual net/minecraft/core/BlockPos.below ()Lnet/minecraft/core/BlockPos;
      // 4d: astore 21
      // 4f: aload 19
      // 51: aload 21
      // 53: invokevirtual net/minecraft/world/level/Level.getBlockState (Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;
      // 56: astore 22
      // 58: aload 22
      // 5a: invokevirtual net/minecraft/world/level/block/state/BlockState.blocksMotion ()Z
      // 5d: ifeq 66
      // 60: bipush 1
      // 61: istore 20
      // 63: goto 70
      // 66: dload 15
      // 68: dconst_1
      // 69: dsub
      // 6a: dstore 15
      // 6c: aload 21
      // 6e: astore 18
      // 70: goto 36
      // 73: iload 20
      // 75: ifeq 8d
      // 78: aload 1
      // 79: dload 2
      // 7a: dload 15
      // 7c: dload 6
      // 7e: invokevirtual net/minecraft/world/entity/LivingEntity.teleportTo (DDD)V
      // 81: aload 19
      // 83: aload 1
      // 84: invokevirtual net/minecraft/world/level/Level.noCollision (Lnet/minecraft/world/entity/Entity;)Z
      // 87: ifeq 8d
      // 8a: bipush 1
      // 8b: istore 17
      // 8d: iload 17
      // 8f: ifne 9e
      // 92: aload 1
      // 93: dload 9
      // 95: dload 11
      // 97: dload 13
      // 99: invokevirtual net/minecraft/world/entity/LivingEntity.teleportTo (DDD)V
      // 9c: bipush 0
      // 9d: ireturn
      // 9e: iload 8
      // a0: ifeq ab
      // a3: aload 19
      // a5: aload 1
      // a6: bipush 46
      // a8: invokevirtual net/minecraft/world/level/Level.broadcastEntityEvent (Lnet/minecraft/world/entity/Entity;B)V
      // ab: aload 1
      // ac: instanceof net/minecraft/world/entity/PathfinderMob
      // af: ifeq bc
      // b2: aload 1
      // b3: checkcast net/minecraft/world/entity/PathfinderMob
      // b6: invokevirtual net/minecraft/world/entity/PathfinderMob.getNavigation ()Lnet/minecraft/world/entity/ai/navigation/PathNavigation;
      // b9: invokevirtual net/minecraft/world/entity/ai/navigation/PathNavigation.stop ()V
      // bc: bipush 1
      // bd: ireturn
   }

   public boolean shouldApplyEffectTickThisTick(int p_295629_, int p_295734_) {
      int i = 40 >> p_295734_;
      return i > 0 ? p_295629_ % i == 0 : true;
   }
}
