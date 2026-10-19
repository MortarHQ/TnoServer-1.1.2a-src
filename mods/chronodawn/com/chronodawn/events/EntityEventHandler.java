package com.chronodawn.events;

import com.chronodawn.ChronoDawn;
import com.chronodawn.blocks.ClockTowerTeleporterBlock;
import com.chronodawn.blocks.ClockTowerTeleporterBlockEntity;
import com.chronodawn.core.dimension.DimensionStabilizer;
import com.chronodawn.core.teleport.TeleporterChargingHandler;
import com.chronodawn.core.time.ReversedResonance;
import com.chronodawn.core.time.TimeDistortionEffect;
import com.chronodawn.entities.bosses.TimeGuardianEntity;
import com.chronodawn.entities.bosses.TimeTyrantEntity;
import com.chronodawn.items.equipment.TemporalAmberArmorItem;
import com.chronodawn.registry.ModBlocks;
import com.chronodawn.registry.ModDimensions;
import com.chronodawn.registry.ModEffects;
import com.chronodawn.worldgen.spawning.TimeGuardianSpawner;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.event.events.common.EntityEvent.LivingDeath;
import dev.architectury.event.events.common.TickEvent.Player;
import dev.architectury.event.events.common.TickEvent.ServerLevelTick;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class EntityEventHandler {
   private static final Map<ResourceLocation, AtomicInteger> timeDistortionTickCounters = new ConcurrentHashMap<>();

   public static void register() {
      TickEvent.SERVER_LEVEL_POST.register((ServerLevelTick)level -> {
         if (level.dimension().location().equals(ModDimensions.CHRONO_DAWN_DIMENSION.location())) {
            ResourceLocation dimensionId = level.dimension().location();
            timeDistortionTickCounters.putIfAbsent(dimensionId, new AtomicInteger(0));
            AtomicInteger tickCounter = timeDistortionTickCounters.get(dimensionId);
            if (tickCounter.incrementAndGet() >= 5) {
               tickCounter.set(0);
               processChronoDawnEntities(level);
            }

            TimeGuardianSpawner.checkAndSpawnGuardians(level);
         }
      });
      EntityEvent.LIVING_DEATH.register((LivingDeath)(entity, source) -> {
         if (entity instanceof TimeGuardianEntity && entity.level() instanceof ServerLevel serverLevel) {
            handleTimeGuardianDefeat(serverLevel, (TimeGuardianEntity)entity);
         }

         if (entity instanceof TimeTyrantEntity && entity.level() instanceof ServerLevel serverLevel) {
            handleTimeTyrantDefeat(serverLevel, (TimeTyrantEntity)entity);
         }

         return EventResult.pass();
      });
      TickEvent.PLAYER_POST.register((Player)player -> {
         if (player instanceof ServerPlayer serverPlayer) {
            TeleporterChargingHandler.tick(serverPlayer);
            handleChronoAegisClarity(serverPlayer);
            TemporalAmberArmorItem.tryAutoRepair(serverPlayer);
         }
      });
      ChronoDawn.LOGGER
         .debug(
            "Registered EntityEventHandler with time distortion effect, Time Guardian spawning, Time Tyrant spawning, boss defeat triggers, and teleporter charging"
         );
   }

   private static void processChronoDawnEntities(ServerLevel level) {
      for (Entity entity : level.getAllEntities()) {
         if (entity instanceof LivingEntity livingEntity) {
            TimeDistortionEffect.applyTimeDistortion(livingEntity);
         }
      }
   }

   private static void handleTimeGuardianDefeat(ServerLevel level, TimeGuardianEntity guardian) {
      ChronoDawn.LOGGER.debug("Time Guardian defeated at [{}, {}, {}]", new Object[]{guardian.getX(), guardian.getY(), guardian.getZ()});
      BlockPos searchPos = guardian.blockPosition().below(8);
      BlockPos upTeleporterPos = findNearbyUpTeleporter(level, searchPos);
      if (upTeleporterPos == null) {
         ChronoDawn.LOGGER.error("Could not find UP teleporter on 4th floor! Cannot place DOWN teleporter.");
      } else {
         BlockPos teleporterPos = upTeleporterPos.above(6);
         level.setBlock(
            teleporterPos,
            (BlockState)((Block)ModBlocks.CLOCK_TOWER_TELEPORTER.get())
               .defaultBlockState()
               .setValue(ClockTowerTeleporterBlock.DIRECTION, ClockTowerTeleporterBlock.TeleportDirection.DOWN),
            3
         );
         if (level.getBlockEntity(teleporterPos) instanceof ClockTowerTeleporterBlockEntity be) {
            be.setTargetPos(upTeleporterPos);
         }

         ChronoDawn.LOGGER
            .debug(
               "Spawned DOWN teleporter at [{}, {}, {}] targeting UP teleporter at [{}, {}, {}]",
               new Object[]{
                  teleporterPos.getX(), teleporterPos.getY(), teleporterPos.getZ(), upTeleporterPos.getX(), upTeleporterPos.getY(), upTeleporterPos.getZ()
               }
            );
      }
   }

   private static BlockPos findNearbyUpTeleporter(ServerLevel level, BlockPos center) {
      int searchRadius = 20;
      int verticalSearchRange = 10;

      for (int x = -searchRadius; x <= searchRadius; x++) {
         for (int z = -searchRadius; z <= searchRadius; z++) {
            for (int y = -verticalSearchRange; y <= verticalSearchRange; y++) {
               BlockPos checkPos = center.offset(x, y, z);
               BlockState state = level.getBlockState(checkPos);
               if (state.getBlock() instanceof ClockTowerTeleporterBlock) {
                  ClockTowerTeleporterBlock.TeleportDirection direction = (ClockTowerTeleporterBlock.TeleportDirection)state.getValue(
                     ClockTowerTeleporterBlock.DIRECTION
                  );
                  if (direction == ClockTowerTeleporterBlock.TeleportDirection.UP) {
                     return checkPos;
                  }
               }
            }
         }
      }

      return null;
   }

   private static void handleTimeTyrantDefeat(ServerLevel level, TimeTyrantEntity tyrant) {
      ChronoDawn.LOGGER.debug("Time Tyrant defeated at [{}, {}, {}]", new Object[]{tyrant.getX(), tyrant.getY(), tyrant.getZ()});
      Component coreMessage = Component.translatable("message.chronodawn.stasis_core_destroyed")
         .withStyle(new ChatFormatting[]{ChatFormatting.DARK_PURPLE, ChatFormatting.BOLD});

      for (ServerPlayer player : level.getServer().getPlayerList().getPlayers()) {
         player.displayClientMessage(coreMessage, false);
      }

      ReversedResonance.triggerOnTimeTyrantDefeat(level, tyrant.position());
      DimensionStabilizer.stabilizeDimension(level);
   }

   private static void handleChronoAegisClarity(ServerPlayer player) {
      Holder<MobEffect> aegisEffect = BuiltInRegistries.MOB_EFFECT.wrapAsHolder((MobEffect)ModEffects.CHRONO_AEGIS_BUFF.get());
      if (player.hasEffect(aegisEffect)) {
         if (player.tickCount % 40 == 0) {
            player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
            player.removeEffect(MobEffects.WEAKNESS);
            player.removeEffect(MobEffects.DIG_SLOWDOWN);
         }
      }
   }
}
