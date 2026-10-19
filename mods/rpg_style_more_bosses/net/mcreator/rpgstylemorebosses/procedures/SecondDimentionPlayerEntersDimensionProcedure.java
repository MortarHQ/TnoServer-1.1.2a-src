package net.mcreator.rpgstylemorebosses.procedures;

import net.mcreator.rpgstylemorebosses.RpgStyleMoreBossesMod;
import net.mcreator.rpgstylemorebosses.entity.BossCounterEntity;
import net.mcreator.rpgstylemorebosses.entity.TheExoframeEntity;
import net.mcreator.rpgstylemorebosses.init.RpgStyleMoreBossesModEntities;
import net.mcreator.rpgstylemorebosses.network.RpgStyleMoreBossesModVariables;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class SecondDimentionPlayerEntersDimensionProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         if (world instanceof ServerLevel _serverworld) {
            StructureTemplate template = _serverworld.getStructureManager()
               .getOrCreate(ResourceLocation.fromNamespaceAndPath("rpg_style_more_bosses", "the_exoframe_dungeon_part1"));
            if (template != null) {
               template.placeInWorld(
                  _serverworld,
                  new BlockPos(0, 0, 0),
                  new BlockPos(0, 0, 0),
                  new StructurePlaceSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false),
                  _serverworld.random,
                  3
               );
            }
         }

         if (world instanceof ServerLevel _serverworld) {
            StructureTemplate template = _serverworld.getStructureManager()
               .getOrCreate(ResourceLocation.fromNamespaceAndPath("rpg_style_more_bosses", "the_exoframe_dungeon_part2"));
            if (template != null) {
               template.placeInWorld(
                  _serverworld,
                  new BlockPos(0, 0, 322),
                  new BlockPos(0, 0, 322),
                  new StructurePlaceSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false),
                  _serverworld.random,
                  3
               );
            }
         }

         RpgStyleMoreBossesMod.queueServerWork(
            10,
            () -> {
               Entity _ent = entity;
               _ent.teleportTo(177.0, 93.0, 7.0);
               if (_ent instanceof ServerPlayer _serverPlayer) {
                  _serverPlayer.connection.teleport(177.0, 93.0, 7.0, _ent.getYRot(), _ent.getXRot());
               }

               if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                  _entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 60, 1, false, false));
               }

               if (world.getEntitiesOfClass(
                        TheExoframeEntity.class, AABB.ofSize(new Vec3(entity.getX(), entity.getY(), entity.getZ()), 800.0, 800.0, 800.0), e -> true
                     )
                     .isEmpty()
                  && world.getEntitiesOfClass(
                        BossCounterEntity.class, AABB.ofSize(new Vec3(entity.getX(), entity.getY(), entity.getZ()), 800.0, 800.0, 800.0), e -> true
                     )
                     .isEmpty()
                  && !RpgStyleMoreBossesModVariables.MapVariables.get(world).ExoframeIfSpawning
                  && !RpgStyleMoreBossesModVariables.MapVariables.get(world).ExoframeBossCounterSpawnDebug) {
                  if (world instanceof ServerLevel _level) {
                     Entity entityToSpawn = ((EntityType)RpgStyleMoreBossesModEntities.BOSS_COUNTER.get())
                        .spawn(_level, new BlockPos(178, 93, 373), MobSpawnType.MOB_SUMMONED);
                     if (entityToSpawn != null) {
                        entityToSpawn.setYRot(world.getRandom().nextFloat() * 360.0F);
                     }
                  }

                  RpgStyleMoreBossesModVariables.MapVariables.get(world).ExoframeBossCounterSpawnDebug = true;
                  RpgStyleMoreBossesModVariables.MapVariables.get(world).syncData(world);
               }

               if ((new Object() {
                        public boolean checkGamemode(Entity _ent) {
                           if (_ent instanceof ServerPlayer _serverPlayer) {
                              return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.SURVIVAL;
                           } else {
                              return _ent.level().isClientSide() && _ent instanceof Player _player
                                 ? Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
                                    && Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode()
                                       == GameType.SURVIVAL
                                 : false;
                           }
                        }
                     })
                     .checkGamemode(entity)
                  && entity instanceof ServerPlayer _player) {
                  _player.setGameMode(GameType.ADVENTURE);
               }
            }
         );
      }
   }
}
