package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.block.BMDBlocks;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.hitbox.HitboxId;
import com.cerbon.bosses_of_mass_destruction.packet.custom.PlaceS2CPacket;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.cerbons_api.api.general.event.EventScheduler;
import com.cerbon.cerbons_api.api.general.event.EventSeries;
import com.cerbon.cerbons_api.api.general.event.IEvent;
import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.network.Dispatcher;
import com.cerbon.cerbons_api.api.static_utilities.SoundUtils;
import com.cerbon.cerbons_api.api.static_utilities.VecUtils;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class BlossomAction implements IActionWithCooldown {
   private final VoidBlossomEntity entity;
   private final EventScheduler eventScheduler;
   private final Supplier<Boolean> shouldCancel;
   private final List<Vec3> blossomPositions = Stream.of(
         VecUtils.xAxis,
         VecUtils.zAxis,
         VecUtils.xAxis.reverse(),
         VecUtils.zAxis.reverse(),
         VecUtils.xAxis.add(VecUtils.zAxis),
         VecUtils.xAxis.add(VecUtils.zAxis.reverse()),
         VecUtils.xAxis.reverse().add(VecUtils.zAxis),
         VecUtils.xAxis.reverse().add(VecUtils.zAxis.reverse())
      )
      .map(vec3 -> vec3.normalize().scale(15.0))
      .toList();

   public BlossomAction(VoidBlossomEntity entity, EventScheduler eventScheduler, Supplier<Boolean> shouldCancel) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
      this.shouldCancel = shouldCancel;
   }

   @Override
   public int perform() {
      Level level = this.entity.level();
      if (!(level instanceof ServerLevel)) {
         return 80;
      }

      this.eventScheduler
         .addEvent(
            new EventSeries(
               new IEvent[]{
                  new TimedEvent(() -> this.entity.getEntityData().set(VoidBlossomEntity.hitbox, HitboxId.SpikeWave3.getId()), 20, 1, this.shouldCancel),
                  new TimedEvent(() -> this.entity.getEntityData().set(VoidBlossomEntity.hitbox, HitboxId.Idle.getId()), 80)
               }
            )
         );
      this.placeBlossoms((ServerLevel)level);
      return 120;
   }

   private void placeBlossoms(ServerLevel level) {
      List<BlockPos> positions = this.blossomPositions.stream().map(pos -> BlockPos.containing(pos.add(this.entity.position()))).collect(Collectors.toList());
      Collections.shuffle(positions);
      float hpRatio = this.entity.getHealth() / this.entity.getMaxHealth();
      int protectedPositions;
      if (hpRatio < VoidBlossomEntity.hpMilestones.get(1)) {
         protectedPositions = 6;
      } else if (hpRatio < VoidBlossomEntity.hpMilestones.get(2)) {
         protectedPositions = 3;
      } else {
         protectedPositions = 0;
      }

      SoundUtils.playSound(level, this.entity.position(), (SoundEvent)BMDSounds.SPIKE_WAVE_INDICATOR.get(), SoundSource.HOSTILE, 2.0F, 0.7F, 64.0, null);

      for (int i = 0; i < 8; i++) {
         int i1 = i;
         this.eventScheduler
            .addEvent(
               new TimedEvent(
                  () -> {
                     BlockPos blossomPos = positions.get(i1);
                     level.setBlockAndUpdate(blossomPos, Blocks.MOSS_BLOCK.defaultBlockState());
                     level.setBlockAndUpdate(blossomPos.above(), ((Block)BMDBlocks.VOID_BLOSSOM.get()).defaultBlockState());
                     Dispatcher.sendToClientsLoadingPos(
                        new PlaceS2CPacket(VecUtils.asVec3(blossomPos).add(VecUtils.unit.scale(0.5))), level, this.entity.position()
                     );
                     SoundUtils.playSound(
                        level,
                        VecUtils.asVec3(blossomPos),
                        (SoundEvent)BMDSounds.PETAL_BLADE.get(),
                        SoundSource.HOSTILE,
                        1.0F,
                        SoundUtils.randomPitch(this.entity.getRandom()),
                        64.0,
                        null
                     );
                     if (i1 < protectedPositions) {
                        for (int x = -1; x <= 1; x++) {
                           for (int z = -1; z <= 1; z++) {
                              for (int y = 0; y <= 2; y++) {
                                 if (x != 0 || z != 0) {
                                    level.setBlockAndUpdate(blossomPos.offset(x, y, z), ((Block)BMDBlocks.VINE_WALL.get()).defaultBlockState());
                                 }
                              }
                           }
                        }
                     }
                  },
                  40 + i * 8,
                  1,
                  this.shouldCancel
               )
            );
      }
   }
}
