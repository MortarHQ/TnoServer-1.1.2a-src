package net.mcreator.rpgstylemorebosses.procedures;

import java.util.Comparator;
import net.mcreator.rpgstylemorebosses.RpgStyleMoreBossesMod;
import net.mcreator.rpgstylemorebosses.entity.TheExoframeEntity;
import net.mcreator.rpgstylemorebosses.init.RpgStyleMoreBossesModItems;
import net.mcreator.rpgstylemorebosses.init.RpgStyleMoreBossesModMobEffects;
import net.mcreator.rpgstylemorebosses.network.RpgStyleMoreBossesModVariables;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class TheExoframeOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (!RpgStyleMoreBossesModVariables.MapVariables.get(world).ExoframeDieAnimPlay) {
            if (!RpgStyleMoreBossesModVariables.MapVariables.get(world).ExoframeAbilityUse) {
               RpgStyleMoreBossesModVariables.MapVariables.get(world).BC++;
               RpgStyleMoreBossesModVariables.MapVariables.get(world).syncData(world);
            }

            if (RpgStyleMoreBossesModVariables.MapVariables.get(world).BC > 310.0) {
               RpgStyleMoreBossesModVariables.MapVariables.get(world).BC = 0.0;
               RpgStyleMoreBossesModVariables.MapVariables.get(world).syncData(world);
            }

            if (!RpgStyleMoreBossesModVariables.MapVariables.get(world).ExoframeAbilityUse) {
               if (!world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 4.0, 4.0, 4.0), e -> true).isEmpty()) {
                  RpgStyleMoreBossesModVariables.MapVariables.get(world).ExoframeAbilityUse = true;
                  RpgStyleMoreBossesModVariables.MapVariables.get(world).syncData(world);
                  if (Math.random() < 0.5) {
                     PunchProcedure.execute(world, x, y, z, entity);
                     RpgStyleMoreBossesMod.queueServerWork(40, () -> {
                        RpgStyleMoreBossesModVariables.MapVariables.get(world).ExoframeAbilityUse = false;
                        RpgStyleMoreBossesModVariables.MapVariables.get(world).syncData(world);
                     });
                  } else if (Math.random() < 0.8) {
                     Punch2Procedure.execute(world, x, y, z, entity);
                     RpgStyleMoreBossesMod.queueServerWork(60, () -> {
                        RpgStyleMoreBossesModVariables.MapVariables.get(world).ExoframeAbilityUse = false;
                        RpgStyleMoreBossesModVariables.MapVariables.get(world).syncData(world);
                     });
                  } else {
                     SetBlockProcedure.execute(world, x, y, z, entity);
                     RpgStyleMoreBossesMod.queueServerWork(80, () -> {
                        RpgStyleMoreBossesModVariables.MapVariables.get(world).ExoframeAbilityUse = false;
                        RpgStyleMoreBossesModVariables.MapVariables.get(world).syncData(world);
                     });
                  }
               }

               if (world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 25.0, 25.0, 25.0), e -> true).isEmpty() && Math.random() < 0.01) {
                  RpgStyleMoreBossesModVariables.MapVariables.get(world).ExoframeAbilityUse = true;
                  RpgStyleMoreBossesModVariables.MapVariables.get(world).syncData(world);
                  JumpKickProcedure.execute(world, x, y, z, entity);
                  RpgStyleMoreBossesMod.queueServerWork(70, () -> {
                     RpgStyleMoreBossesModVariables.MapVariables.get(world).ExoframeAbilityUse = false;
                     RpgStyleMoreBossesModVariables.MapVariables.get(world).syncData(world);
                  });
               }

               if (world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 20.0, 20.0, 20.0), e -> true).isEmpty() && Math.random() < 0.01) {
                  RpgStyleMoreBossesModVariables.MapVariables.get(world).ExoframeAbilityUse = true;
                  RpgStyleMoreBossesModVariables.MapVariables.get(world).syncData(world);
                  DashPunchProcedure.execute(world, x, y, z, entity);
                  RpgStyleMoreBossesMod.queueServerWork(50, () -> {
                     RpgStyleMoreBossesModVariables.MapVariables.get(world).ExoframeAbilityUse = false;
                     RpgStyleMoreBossesModVariables.MapVariables.get(world).syncData(world);
                  });
               }
            }

            if (RpgStyleMoreBossesModVariables.MapVariables.get(world).BC == 0.0
               && !RpgStyleMoreBossesModVariables.MapVariables.get(world).ExoframeAbilityUse
               && (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1.0F) < 600.0F) {
               RpgStyleMoreBossesModVariables.MapVariables.get(world).ExoframeAbilityUse = true;
               RpgStyleMoreBossesModVariables.MapVariables.get(world).syncData(world);
               BackdashProcedure.execute(world, entity);
               RpgStyleMoreBossesMod.queueServerWork(35, () -> {
                  ChargeProcedure.execute(world, entity);
                  RpgStyleMoreBossesMod.queueServerWork(160, () -> {
                     RpgStyleMoreBossesModVariables.MapVariables.get(world).ExoframeAbilityUse = false;
                     RpgStyleMoreBossesModVariables.MapVariables.get(world).syncData(world);
                  });
               });
            }

            if (RpgStyleMoreBossesModVariables.MapVariables.get(world).BC == 200.0
               && !RpgStyleMoreBossesModVariables.MapVariables.get(world).ExoframeAbilityUse
               && (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1.0F) < 700.0F) {
               RpgStyleMoreBossesModVariables.MapVariables.get(world).ExoframeAbilityUse = true;
               RpgStyleMoreBossesModVariables.MapVariables.get(world).syncData(world);
               BackdashProcedure.execute(world, entity);
               RpgStyleMoreBossesMod.queueServerWork(36, () -> {
                  TickSummonProcedure.execute(world, x, y, z, entity);
                  RpgStyleMoreBossesMod.queueServerWork(70, () -> {
                     RpgStyleMoreBossesModVariables.MapVariables.get(world).ExoframeAbilityUse = false;
                     RpgStyleMoreBossesModVariables.MapVariables.get(world).syncData(world);
                  });
               });
            }
         }

         if (!RpgStyleMoreBossesModVariables.MapVariables.get(world).ExoframeDieAnimPlay
            && 100.0F >= (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1.0F)) {
            RpgStyleMoreBossesModVariables.MapVariables.get(world).ExoframeDieAnimPlay = true;
            RpgStyleMoreBossesModVariables.MapVariables.get(world).syncData(world);
            if (entity instanceof TheExoframeEntity) {
               ((TheExoframeEntity)entity).setAnimation("empty");
            }

            if (entity instanceof TheExoframeEntity) {
               ((TheExoframeEntity)entity).setAnimation("leave");
            }

            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(RpgStyleMoreBossesModMobEffects.STUN, 60, 1, false, false));
            }

            RpgStyleMoreBossesMod.queueServerWork(
               40,
               () -> {
                  if (world instanceof ServerLevel _level) {
                     _level.addFreshEntity(new ExperienceOrb(_level, x + 0.5, y + 5.0, z + 0.5, 100));
                  }

                  if (world instanceof ServerLevel _level) {
                     _level.addFreshEntity(new ExperienceOrb(_level, x + 0.5, y + 5.0, z + 0.5, 100));
                  }

                  if (world instanceof ServerLevel _level) {
                     _level.addFreshEntity(new ExperienceOrb(_level, x + 0.5, y + 5.0, z + 0.5, 100));
                  }

                  if (world instanceof ServerLevel _level) {
                     _level.addFreshEntity(new ExperienceOrb(_level, x + 0.5, y + 5.0, z + 0.5, 100));
                  }

                  if (world instanceof ServerLevel _level) {
                     _level.addFreshEntity(new ExperienceOrb(_level, x + 0.5, y + 5.0, z + 0.5, 100));
                  }

                  if (world instanceof ServerLevel _level) {
                     ItemEntity entityToSpawn = new ItemEntity(
                        _level, x + 0.5, y + 5.0, z + 0.5, new ItemStack((ItemLike)RpgStyleMoreBossesModItems.EXOFRAME_PART.get())
                     );
                     entityToSpawn.setPickUpDelay(10);
                     entityToSpawn.setUnlimitedLifetime();
                     _level.addFreshEntity(entityToSpawn);
                  }

                  if (world instanceof ServerLevel _level) {
                     _level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z, 200, 1.0, 2.0, 1.0, 0.0);
                  }

                  if (world instanceof ServerLevel _level) {
                     _level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z, 50, 2.0, 3.0, 2.0, 1.0);
                  }

                  Vec3 _center = new Vec3(x, y, z);

                  for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(35.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                     .toList()) {
                     if (entityiterator instanceof ServerPlayer _player) {
                        AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("rpg_style_more_bosses:kill_exoframe"));
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
            );
            RpgStyleMoreBossesMod.queueServerWork(
               43,
               () -> {
                  if (!entity.level().isClientSide()) {
                     entity.discard();
                  }

                  RpgStyleMoreBossesMod.queueServerWork(
                     400,
                     () -> {
                        Vec3 _center = new Vec3(x, y, z);

                        for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(250.0), e -> true)
                           .stream()
                           .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                           .toList()) {
                           if (entityiterator instanceof Player) {
                              if (entityiterator instanceof ServerPlayer _player && !_player.level().isClientSide()) {
                                 ResourceKey<Level> destinationType = Level.OVERWORLD;
                                 if (_player.level().dimension() == destinationType) {
                                    return;
                                 }

                                 ServerLevel nextLevel = _player.server.getLevel(destinationType);
                                 if (nextLevel != null) {
                                    _player.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.WIN_GAME, 0.0F));
                                    _player.teleportTo(nextLevel, _player.getX(), _player.getY(), _player.getZ(), _player.getYRot(), _player.getXRot());
                                    _player.connection.send(new ClientboundPlayerAbilitiesPacket(_player.getAbilities()));

                                    for (MobEffectInstance _effectinstance : _player.getActiveEffects()) {
                                       _player.connection.send(new ClientboundUpdateMobEffectPacket(_player.getId(), _effectinstance, false));
                                    }

                                    _player.connection.send(new ClientboundLevelEventPacket(1032, BlockPos.ZERO, 0, false));
                                 }
                              }

                              Entity _ent = entityiterator;
                              _ent.teleportTo(
                                 entityiterator instanceof ServerPlayer _player && !_player.level().isClientSide()
                                    ? (
                                       _player.getRespawnDimension().equals(_player.level().dimension()) && _player.getRespawnPosition() != null
                                          ? _player.getRespawnPosition().getX()
                                          : _player.level().getLevelData().getSpawnPos().getX()
                                    )
                                    : 0.0,
                                 entityiterator instanceof ServerPlayer _player && !_player.level().isClientSide()
                                    ? (
                                       _player.getRespawnDimension().equals(_player.level().dimension()) && _player.getRespawnPosition() != null
                                          ? _player.getRespawnPosition().getY()
                                          : _player.level().getLevelData().getSpawnPos().getY()
                                    )
                                    : 0.0,
                                 entityiterator instanceof ServerPlayer _player && !_player.level().isClientSide()
                                    ? (
                                       _player.getRespawnDimension().equals(_player.level().dimension()) && _player.getRespawnPosition() != null
                                          ? _player.getRespawnPosition().getZ()
                                          : _player.level().getLevelData().getSpawnPos().getZ()
                                    )
                                    : 0.0
                              );
                              if (_ent instanceof ServerPlayer _serverPlayer) {
                                 _serverPlayer.connection
                                    .teleport(
                                       entityiterator instanceof ServerPlayer _player && !_player.level().isClientSide()
                                          ? (
                                             _player.getRespawnDimension().equals(_player.level().dimension()) && _player.getRespawnPosition() != null
                                                ? _player.getRespawnPosition().getX()
                                                : _player.level().getLevelData().getSpawnPos().getX()
                                          )
                                          : 0.0,
                                       entityiterator instanceof ServerPlayer _player && !_player.level().isClientSide()
                                          ? (
                                             _player.getRespawnDimension().equals(_player.level().dimension()) && _player.getRespawnPosition() != null
                                                ? _player.getRespawnPosition().getY()
                                                : _player.level().getLevelData().getSpawnPos().getY()
                                          )
                                          : 0.0,
                                       entityiterator instanceof ServerPlayer _player && !_player.level().isClientSide()
                                          ? (
                                             _player.getRespawnDimension().equals(_player.level().dimension()) && _player.getRespawnPosition() != null
                                                ? _player.getRespawnPosition().getZ()
                                                : _player.level().getLevelData().getSpawnPos().getZ()
                                          )
                                          : 0.0,
                                       _ent.getYRot(),
                                       _ent.getXRot()
                                    );
                              }

                              if ((new Object() {
                                       public boolean checkGamemode(Entity _ent) {
                                          if (_ent instanceof ServerPlayer _serverPlayer) {
                                             return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.ADVENTURE;
                                          } else {
                                             return _ent.level().isClientSide() && _ent instanceof Player _player
                                                ? Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
                                                   && Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode()
                                                      == GameType.ADVENTURE
                                                : false;
                                          }
                                       }
                                    })
                                    .checkGamemode(entityiterator)
                                 && entityiterator instanceof ServerPlayer _player) {
                                 _player.setGameMode(GameType.SURVIVAL);
                              }
                           }
                        }
                     }
                  );
               }
            );
         }
      }
   }
}
