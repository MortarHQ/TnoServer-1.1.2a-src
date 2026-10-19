package com.benji.netherman.common.entity;

import com.benji.netherman.init.ModEntities;
import com.benji.netherman.init.ModSounds;
import java.util.EnumSet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class AzazelHumanUltimateGoal extends Goal {
   private final AzazelHumanEntity boss;
   private int currentAnimTick = 0;
   private final int maxAnimTick = 38;

   public AzazelHumanUltimateGoal(AzazelHumanEntity boss) {
      this.boss = boss;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      return this.boss.forcedAttackGoal == 4 && (Integer)this.boss.getEntityData().get(AzazelHumanEntity.BOSS_STATE) == 5;
   }

   public void start() {
      this.boss.forcedAttackGoal = 0;
      this.boss.getNavigation().stop();
      this.currentAnimTick = 0;
      this.boss.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 60);
   }

   public void tick() {
      this.currentAnimTick++;
      if (this.currentAnimTick == 15) {
         this.boss.level().playSound(null, this.boss.blockPosition(), (SoundEvent)ModSounds.STOMP.get(), SoundSource.HOSTILE, 3.0F, 0.8F);
         this.boss.level().playSound(null, this.boss.blockPosition(), (SoundEvent)ModSounds.GRUNT_8.get(), SoundSource.HOSTILE, 2.5F, 1.0F);
         this.boss.triggerScreenShake(8.0F, 50);
         if (!this.boss.level().isClientSide()) {
            AzazelEarthquakeEntity quake = (AzazelEarthquakeEntity)((EntityType)ModEntities.EARTHQUAKE_ENTITY.get()).create(this.boss.level());
            if (quake != null) {
               quake.setPos(this.boss.getX(), this.boss.getY(), this.boss.getZ());
               this.boss.level().addFreshEntity(quake);
            }
         }
      }

      if (this.currentAnimTick >= 38) {
         this.boss.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 5);
      }
   }

   public boolean canContinueToUse() {
      int state = (Integer)this.boss.getEntityData().get(AzazelHumanEntity.BOSS_STATE);
      return this.currentAnimTick < 38 && state != 40 && state < 100;
   }
}
