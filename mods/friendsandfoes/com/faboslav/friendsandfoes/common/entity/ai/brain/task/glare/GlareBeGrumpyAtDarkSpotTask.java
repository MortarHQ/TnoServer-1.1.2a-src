package com.faboslav.friendsandfoes.common.entity.ai.brain.task.glare;

import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.GlareBrain;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.util.particle.ParticleSpawner;
import java.util.Map;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

public final class GlareBeGrumpyAtDarkSpotTask extends Behavior<GlareEntity> {
   private static final int MAX_GRUMPY_TICKS = 200;
   private static final float WITHING_DISTANCE = 2.0F;
   private int grumpyTicks;
   private final ParticleOptions particleEffect = new BlockParticleOption(ParticleTypes.BLOCK, Blocks.AZALEA.defaultBlockState());

   public GlareBeGrumpyAtDarkSpotTask() {
      super(Map.of((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_POS.get(), MemoryStatus.VALUE_PRESENT), 200);
   }

   protected boolean checkExtraStartConditions(ServerLevel world, GlareEntity glare) {
      GlobalPos darkSpotPos = glare.getDarkSpotPos();
      return canBeGrumpyAtDarkSpot(glare) && darkSpotPos.pos().closerToCenterThan(glare.position(), 2.0);
   }

   protected void start(ServerLevel world, GlareEntity glare, long time) {
      this.grumpyTicks = 0;
      glare.setGrumpy(true);
      BehaviorUtils.lookAtEntity(glare, glare.getOwner());
      this.applyGlowToHostileEntities(glare);
   }

   protected boolean canStillUse(ServerLevel world, GlareEntity glare, long time) {
      GlobalPos darkSpotPos = glare.getDarkSpotPos();
      return canBeGrumpyAtDarkSpot(glare) && darkSpotPos.pos().closerToCenterThan(glare.position(), 2.0);
   }

   protected void tick(ServerLevel world, GlareEntity glare, long time) {
      this.grumpyTicks++;
      BehaviorUtils.lookAtEntity(glare, glare.getOwner());
      if (!glare.isGrumpy()) {
         glare.setGrumpy(true);
      }

      if (this.grumpyTicks == 10) {
         glare.playGrumpinessSound();
      }

      if (this.grumpyTicks % 3 == 0 && glare.getRandom().nextBoolean()) {
         glare.playRustleSound();
      }

      if (this.grumpyTicks % 2 == 0 && glare.getRandom().nextBoolean()) {
         ParticleOptions particleEffect = new BlockParticleOption(ParticleTypes.BLOCK, Blocks.AZALEA.defaultBlockState());
         ParticleSpawner.spawnParticles(glare, particleEffect, glare.getRandom().nextIntBetweenInclusive(4, 10), 0.1);
      }
   }

   protected void stop(ServerLevel world, GlareEntity glare, long time) {
      glare.setGrumpy(false);
      glare.getBrain().eraseMemory((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_POS.get());
      GlareBrain.setDarkSpotLocatingCooldown(glare);
   }

   private void applyGlowToHostileEntities(GlareEntity glare) {
      AABB box = new AABB(glare.blockPosition()).inflate(24.0);

      for (Monster hostileEntity : glare.level().getEntitiesOfClass(Monster.class, box)) {
         this.applyGlowToEntity(hostileEntity);
      }
   }

   private void applyGlowToEntity(Monster entity) {
      entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200));
   }

   public static boolean canBeGrumpyAtDarkSpot(GlareEntity glare) {
      return GlareTravelToDarkSpotTask.canTravelToDarkSpot(glare) && glare.getOwner() != null;
   }
}
