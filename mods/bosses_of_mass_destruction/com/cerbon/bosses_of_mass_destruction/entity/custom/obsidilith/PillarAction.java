package com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith;

import com.cerbon.bosses_of_mass_destruction.block.BMDBlocks;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import com.cerbon.cerbons_api.api.general.event.EventScheduler;
import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.static_utilities.CapabilityUtils;
import com.cerbon.cerbons_api.api.static_utilities.MathUtils;
import com.cerbon.cerbons_api.api.static_utilities.MobUtils;
import com.cerbon.cerbons_api.api.static_utilities.ParticleUtils;
import com.cerbon.cerbons_api.api.static_utilities.RandomUtils;
import com.cerbon.cerbons_api.api.static_utilities.SoundUtils;
import com.cerbon.cerbons_api.api.static_utilities.VecUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class PillarAction implements IActionWithCooldown {
   private final LivingEntity entity;
   private final EventScheduler eventScheduler;
   public static final double pillarXzDistance = 13.0;
   public static final double maxYDistance = 15.0;
   public static final int pillarDelay = 40;

   public PillarAction(LivingEntity entity) {
      this.entity = entity;
      this.eventScheduler = CapabilityUtils.getLevelEventScheduler(entity.level());
   }

   @Override
   public int perform() {
      Level level = this.entity.level();
      if (!(level instanceof ServerLevel)) {
         return 80;
      }

      List<BlockPos> pillarPositions = this.getPillarPositions();
      SoundUtils.playSound(
         (ServerLevel)level, this.entity.position(), (SoundEvent)BMDSounds.OBSIDILITH_PREPARE_ATTACK.get(), SoundSource.HOSTILE, 3.0F, 1.4F, 64.0, null
      );
      pillarPositions.forEach(
         blockPos -> {
            MathUtils.lineCallback(
               MobUtils.eyePos(this.entity),
               VecUtils.asVec3(blockPos).add(0.5, 0.5, 0.5),
               13,
               (vec3, integer) -> ParticleUtils.spawnParticle(
                  (ServerLevel)level, (ParticleOptions)BMDParticles.PILLAR_SPAWN_INDICATOR_2.get(), vec3, Vec3.ZERO, 0, 0.0
               )
            );
            ParticleUtils.spawnParticle(
               (ServerLevel)level,
               (ParticleOptions)BMDParticles.PILLAR_SPAWN_INDICATOR.get(),
               VecUtils.asVec3(blockPos.above(5)),
               new Vec3(0.3, 3.0, 0.3),
               20,
               0.0
            );
         }
      );
      this.eventScheduler
         .addEvent(
            new TimedEvent(() -> pillarPositions.forEach(blockPos -> this.buildPillar(blockPos, (ServerLevel)level)), 40, 1, () -> !this.entity.isAlive())
         );
      return 100;
   }

   private List<BlockPos> getPillarPositions() {
      int numPillars = 4;
      List<BlockPos> pillars = new ArrayList<>();

      for (int i = 0; i < numPillars; i++) {
         Vec3 position = VecUtils.planeProject(RandomUtils.randVec(), VecUtils.yAxis).normalize().scale(13.0).add(this.entity.position());
         BlockPos above = BMDUtils.findGroundBelow(this.entity.level(), BlockPos.containing(position).above(14), pos -> true);
         BlockPos ground = BMDUtils.findGroundBelow(this.entity.level(), above, pos -> true);
         if (!(above.getY() - ground.getY() > 15.0)) {
            pillars.add(ground);
         }
      }

      return pillars;
   }

   private void buildPillar(BlockPos pos, ServerLevel serverLevel) {
      int pillarHeight = 2;
      IntStream.range(0, pillarHeight).forEach(i -> this.entity.level().setBlockAndUpdate(pos.above(i), Blocks.OBSIDIAN.defaultBlockState()));
      BlockPos pillarTop = pos.above(pillarHeight);
      this.entity.level().setBlockAndUpdate(pillarTop, ((Block)BMDBlocks.OBSIDILITH_RUNE.get()).defaultBlockState());
      SoundUtils.playSound(serverLevel, VecUtils.asVec3(pos), SoundEvents.BASALT_PLACE, SoundSource.HOSTILE, 1.0F, 16.0, null);
   }
}
