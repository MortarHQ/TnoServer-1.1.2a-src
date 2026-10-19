package net.witherspawnanimation.procedures;

import javax.annotation.Nullable;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent.EntityPlaceEvent;
import net.witherspawnanimation.init.WitherSpawnAnimationModEntities;
import net.witherspawnanimation.init.WitherSpawnAnimationModGameRules;

@EventBusSubscriber
public class CallDetectAltarProcedure {
   @SubscribeEvent
   public static void onBlockPlace(EntityPlaceEvent event) {
      execute(event, event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), event.getState(), event.getEntity());
   }

   public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
      execute(null, world, x, y, z, blockstate, entity);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
      if (entity != null) {
         double yy = 0.0;
         double skull_pos = 0.0;
         String dir = "";
         String altar_type = "";
         if (world.getLevelData().getGameRules().getBoolean(WitherSpawnAnimationModGameRules.WITHER_SPAWN_ANIMATION)
            && world.getDifficulty() != Difficulty.PEACEFUL
            && Blocks.WITHER_SKELETON_SKULL == blockstate.getBlock()) {
            yy = y - 2.0;
            dir = RotationDetectProcedure.execute(world, x, y, z);
            if (!"z".equals(dir) && !"minz".equals(dir)) {
               skull_pos = SkullPositionCheckXProcedure.execute(world, x, y, z);
            } else {
               skull_pos = SkullPositionCheckZProcedure.execute(world, x, y, z);
            }

            altar_type = CallDetectionsProcedure.execute(world, x, y, z, blockstate);
            if (!"null".equals(altar_type)) {
               if ("z".equals(dir)) {
                  if ("sand".equals(altar_type)) {
                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = ((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_Z.get())
                           .spawn(_level, BlockPos.containing(skull_pos, yy, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                        }
                     }
                  } else if ("soil".equals(altar_type)) {
                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = ((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_SOIL_Z.get())
                           .spawn(_level, BlockPos.containing(skull_pos, yy, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                        }
                     }
                  } else if ("bygone".equals(altar_type)) {
                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = ((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_BYGONE_Z.get())
                           .spawn(_level, BlockPos.containing(skull_pos, yy, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                        }
                     }
                  } else if ("storm".equals(altar_type)) {
                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = ((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_STORM_Z.get())
                           .spawn(_level, BlockPos.containing(skull_pos, yy, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                        }
                     }
                  } else if ("storm_soil".equals(altar_type) && world instanceof ServerLevel _level) {
                     Entity entityToSpawn = ((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_STORM_SOIL_Z.get())
                        .spawn(_level, BlockPos.containing(skull_pos, yy, z), MobSpawnType.MOB_SUMMONED);
                     if (entityToSpawn != null) {
                     }
                  }
               } else if ("minz".equals(dir)) {
                  if ("sand".equals(altar_type)) {
                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = ((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_MIN_Z.get())
                           .spawn(_level, BlockPos.containing(skull_pos, yy, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                        }
                     }
                  } else if ("soil".equals(altar_type)) {
                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = ((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_SOIL_MIN_Z.get())
                           .spawn(_level, BlockPos.containing(skull_pos, yy, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                        }
                     }
                  } else if ("bygone".equals(altar_type)) {
                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = ((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_BYGONE_MIN_Z.get())
                           .spawn(_level, BlockPos.containing(skull_pos, yy, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                        }
                     }
                  } else if ("storm".equals(altar_type)) {
                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = ((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_STORM_MIN_Z.get())
                           .spawn(_level, BlockPos.containing(skull_pos, yy, z), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                        }
                     }
                  } else if ("storm_soil".equals(altar_type) && world instanceof ServerLevel _level) {
                     Entity entityToSpawn = ((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_STORM_SOIL_MIN_Z.get())
                        .spawn(_level, BlockPos.containing(skull_pos, yy, z), MobSpawnType.MOB_SUMMONED);
                     if (entityToSpawn != null) {
                     }
                  }
               } else if ("x".equals(dir)) {
                  if ("sand".equals(altar_type)) {
                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = ((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_X.get())
                           .spawn(_level, BlockPos.containing(x, yy, skull_pos), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                        }
                     }
                  } else if ("soil".equals(altar_type)) {
                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = ((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_SOIL_X.get())
                           .spawn(_level, BlockPos.containing(x, yy, skull_pos), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                        }
                     }
                  } else if ("bygone".equals(altar_type)) {
                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = ((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_BYGONE_X.get())
                           .spawn(_level, BlockPos.containing(x, yy, skull_pos), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                        }
                     }
                  } else if ("storm".equals(altar_type)) {
                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = ((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_STORM_X.get())
                           .spawn(_level, BlockPos.containing(x, yy, skull_pos), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                        }
                     }
                  } else if ("storm_soil".equals(altar_type) && world instanceof ServerLevel _level) {
                     Entity entityToSpawn = ((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_STORM_SOIL_X.get())
                        .spawn(_level, BlockPos.containing(x, yy, skull_pos), MobSpawnType.MOB_SUMMONED);
                     if (entityToSpawn != null) {
                     }
                  }
               } else if ("minx".equals(dir)) {
                  if ("sand".equals(altar_type)) {
                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = ((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_MIN_X.get())
                           .spawn(_level, BlockPos.containing(x, yy, skull_pos), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                        }
                     }
                  } else if ("soil".equals(altar_type)) {
                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = ((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_SOIL_MIN_X.get())
                           .spawn(_level, BlockPos.containing(x, yy, skull_pos), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                        }
                     }
                  } else if ("bygone".equals(altar_type)) {
                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = ((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_BYGONE_MIN_X.get())
                           .spawn(_level, BlockPos.containing(x, yy, skull_pos), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                        }
                     }
                  } else if ("storm".equals(altar_type)) {
                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = ((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_STORM_MIN_X.get())
                           .spawn(_level, BlockPos.containing(x, yy, skull_pos), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                        }
                     }
                  } else if ("storm_soil".equals(altar_type) && world instanceof ServerLevel _level) {
                     Entity entityToSpawn = ((EntityType)WitherSpawnAnimationModEntities.ANIMATED_ALTAR_STORM_SOIL_MIN_X.get())
                        .spawn(_level, BlockPos.containing(x, yy, skull_pos), MobSpawnType.MOB_SUMMONED);
                     if (entityToSpawn != null) {
                     }
                  }
               }

               if (!"soil".equals(altar_type) && !"sand".equals(altar_type) && !"bygone".equals(altar_type)) {
                  if (("storm".equals(altar_type) || "storm_soil".equals(altar_type)) && world instanceof ServerLevel _level) {
                     _level.getServer()
                        .getCommands()
                        .performPrefixedCommand(
                           new CommandSourceStack(
                                 CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null
                              )
                              .withSuppressedOutput(),
                           "advancement grant @p only witherstormmod:main/summon_wither_storm"
                        );
                  }
               } else if (entity instanceof ServerPlayer _player) {
                  AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("minecraft:nether/summon_wither"));
                  if (_adv != null) {
                     AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
                     if (!_ap.isDone()) {
                        for (String criteria : _ap.getRemainingCriteria()) {
                           _player.getAdvancements().award(_adv, criteria);
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
