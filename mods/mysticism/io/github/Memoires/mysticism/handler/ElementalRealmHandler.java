package io.github.Memoires.mysticism.handler;

import dev.architectury.event.events.common.TickEvent;
import dev.architectury.event.events.common.TickEvent.ServerLevelTick;
import io.github.Memoires.mysticism.entity.item.LightningEssenceItemEntity;
import io.github.Memoires.mysticism.registry.dimension.MysticismDimensions;
import io.github.Memoires.mysticism.registry.entity.MysticismMonsterEntityTypes;
import io.github.Memoires.mysticism.registry.item.MysticismMobDropItems;
import io.github.Memoires.mysticism.registry.world.MysticismBiomes;
import io.github.manasmods.manascore.race.api.SpawnPointHelper;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.tensura.event.TensuraLevelEvents;
import io.github.manasmods.tensura.event.TensuraLevelEvents.ChunkTickEvent;
import io.github.manasmods.tensura.registry.dimension.TensuraDimensions;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;

public class ElementalRealmHandler {
   public static void init() {
      EntityEvents.LIVING_PRE_TICK.register(ElementalRealmHandler::sendTargetToER);
      EntityEvents.LIVING_PRE_TICK.register(ElementalRealmHandler::sendTargetToLab);
      EntityEvents.LIVING_PRE_TICK.register(ElementalRealmHandler::teleportMemoiresUp);
      TensuraLevelEvents.CHUNK_TICK_PRE
         .register(
            (ChunkTickEvent)(level, chunk) -> {
               int x = chunk.getPos().getMinBlockX() + level.getRandom().nextInt(16);
               int z = chunk.getPos().getMinBlockZ() + level.getRandom().nextInt(16);
               int y = level.getHeight(Types.WORLD_SURFACE, x, z);
               BlockPos blockPos = new BlockPos(x, y, z);
               if (level.getLevel().getBiome(blockPos).is(MysticismBiomes.FIRE_BIOME) && level.getRandom().nextInt(45000) == 0) {
                  int count = level.getRandom().nextInt(3) + 1;

                  for (int i = 0; i < count; i++) {
                     for (int yMob = 58; yMob < level.getMaxBuildHeight(); yMob++) {
                        BlockPos pos = new BlockPos(x, yMob, z);
                        BlockPos blockBelow = pos.below();
                        BlockState stateBelow = level.getBlockState(blockBelow);
                        if (stateBelow.isSolidRender(level, blockBelow)
                           && level.isEmptyBlock(pos)
                           && Mob.checkMobSpawnRules(EntityType.BLAZE, level, MobSpawnType.NATURAL, pos, level.getRandom())) {
                           Entity blaze = EntityType.BLAZE.create(level, null, pos, MobSpawnType.NATURAL, true, true);
                           if (blaze != null) {
                              level.addFreshEntity(blaze);
                           }
                           break;
                        }
                     }
                  }
               }

               if (level.getLevel().getBiome(blockPos).is(MysticismBiomes.WIND_BIOME) && level.getRandom().nextInt(45000) == 0) {
                  int count = level.getRandom().nextInt(3) + 1;

                  for (int i = 0; i < count; i++) {
                     for (int yMob = 58; yMob < level.getMaxBuildHeight(); yMob++) {
                        BlockPos pos = new BlockPos(x, yMob, z);
                        BlockPos blockBelow = pos.below();
                        BlockState stateBelow = level.getBlockState(blockBelow);
                        if (stateBelow.isSolidRender(level, blockBelow)
                           && level.isEmptyBlock(pos)
                           && Mob.checkMobSpawnRules(EntityType.BREEZE, level, MobSpawnType.NATURAL, pos, level.getRandom())) {
                           Entity breeze = EntityType.BREEZE.create(level, null, pos, MobSpawnType.NATURAL, true, true);
                           if (breeze != null) {
                              level.addFreshEntity(breeze);
                           }
                           break;
                        }
                     }
                  }
               }
            }
         );
      TickEvent.SERVER_LEVEL_POST.register((ServerLevelTick)level -> {
         if (!level.isClientSide()) {
            if (level.dimension().equals(MysticismDimensions.ELEMENTAL_REALM)) {
               int random = level.getRandom().nextInt(300);
               if (random == 0) {
                  List<ServerPlayer> players = level.players();
                  if (!players.isEmpty()) {
                     for (ServerPlayer player : players) {
                        int x = Mth.floor(player.getX()) + level.getRandom().nextInt(256) - 128;
                        int z = Mth.floor(player.getZ()) + level.getRandom().nextInt(256) - 128;
                        int y = level.getHeight(Types.WORLD_SURFACE, x, z);
                        BlockPos surface = level.getHeightmapPos(Types.WORLD_SURFACE, new BlockPos(x, 0, z));
                        if (level.getBiome(surface).is(MysticismBiomes.WIND_BIOME)) {
                           BlockPos target = level.findLightningTargetAround(surface);
                           if (target != null) {
                              LightningBolt lightning = (LightningBolt)EntityType.LIGHTNING_BOLT.create(level);
                              if (lightning != null) {
                                 lightning.moveTo(target.getX() + 0.5, target.getY(), target.getZ() + 0.5);
                                 lightning.setVisualOnly(false);
                                 level.addFreshEntity(lightning);
                                 if (level.random.nextFloat() < 0.1F) {
                                    ItemStack stack = new ItemStack(MysticismMobDropItems.LIGHTNING_ESSENCE);
                                    LightningEssenceItemEntity entity = new LightningEssenceItemEntity(level, x, y, z, stack);
                                    level.addFreshEntity(entity);
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      });
   }

   private static void teleportMemoiresUp(LivingEntity entity) {
      if (!entity.level().isClientSide()) {
         if (entity.getType().equals(MysticismMonsterEntityTypes.MEMOIRES.get())) {
            double y = entity.getY();
            if (!(y > -60.0)) {
               entity.teleportTo(entity.getX(), 150.0, entity.getZ());
               entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 120, 5));
            }
         }
      }
   }

   private static void sendTargetToER(LivingEntity entity) {
      if (!entity.level().isClientSide()) {
         if (entity.level().dimension().equals(TensuraDimensions.LABYRINTH)) {
            if (!entity.isOnPortalCooldown()) {
               double y = entity.getY();
               if (!(y < 158.0) && !(y > 210.0)) {
                  if (entity instanceof ServerPlayer sp) {
                     ServerLevel targetLevel = sp.getServer().getLevel(MysticismDimensions.ELEMENTAL_REALM);
                     if (targetLevel != null) {
                        entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 120, 1));
                        SpawnPointHelper.teleportToAcrossDimensions(
                           entity, targetLevel, entity.getX(), entity.getY(), entity.getZ(), entity.getXRot(), entity.getYRot()
                        );
                        sp.setPortalCooldown();
                     }
                  }
               }
            }
         }
      }
   }

   private static void sendTargetToLab(LivingEntity entity) {
      if (!entity.level().isClientSide()) {
         if (entity.level().dimension().equals(MysticismDimensions.ELEMENTAL_REALM)) {
            if (!entity.isOnPortalCooldown()) {
               double y = entity.getY();
               if (!(y > -60.0)) {
                  if (entity instanceof ServerPlayer sp) {
                     ServerLevel targetLevel = sp.getServer().getLevel(TensuraDimensions.LABYRINTH);
                     if (targetLevel != null) {
                        entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 120, 1));
                        SpawnPointHelper.teleportToAcrossDimensions(entity, targetLevel, 17.0, 109.0, 683.0, entity.getXRot(), entity.getYRot());
                        sp.setPortalCooldown();
                     }
                  }
               }
            }
         }
      }
   }
}
