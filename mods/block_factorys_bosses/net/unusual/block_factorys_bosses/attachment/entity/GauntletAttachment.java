package net.unusual.block_factorys_bosses.attachment.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.neoforged.neoforge.event.entity.player.PlayerFlyableFallEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;
import net.neoforged.neoforge.network.PacketDistributor;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.boss.yeti.IceSpikeClusterEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.IceSpikeEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.IceSpikeProjectileEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.YetiEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseDataAttachments;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import net.unusual.block_factorys_bosses.item.IceGauntletItem;
import net.unusual.block_factorys_bosses.network.IceGauntletMessage;
import net.unusual.block_factorys_bosses.util.SpatialUtil;
import org.jetbrains.annotations.UnknownNullability;

@EventBusSubscriber
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class GauntletAttachment implements INBTSerializable<CompoundTag> {
   public static final int SOUND_CHANCE = 5;
   public static final long SHOT_DELAY = 2L;
   public static final float LERP = 0.5F;
   protected static final ResourceLocation PATH = BossesRise.prefix("animations/player/frozen_gauntlet.animation.json");
   public static final PlayerAnimationHandler.QueuedPlayerAnimation ATTACK_AND_HOLD = new PlayerAnimationHandler.QueuedPlayerAnimation(
      List.of(
         new PlayerAnimationHandler.TimedPlayerAnimation(new PlayerAnimationHandler.PlayerAnimation(PATH, "animation.charge_attack_start", 0.5F), 6),
         new PlayerAnimationHandler.TimedPlayerAnimation(new PlayerAnimationHandler.PlayerAnimation(PATH, "animation.charge_attack_hold", 0.5F, true), 4)
      )
   );
   public static final PlayerAnimationHandler.PlayerAnimation ATTACK_END = new PlayerAnimationHandler.PlayerAnimation(PATH, "animation.charge_attack", 0.5F);
   public static final PlayerAnimationHandler.QueuedPlayerAnimation GRAB_AND_HOLD = new PlayerAnimationHandler.QueuedPlayerAnimation(
      List.of(
         new PlayerAnimationHandler.TimedPlayerAnimation(new PlayerAnimationHandler.PlayerAnimation(PATH, "animation.grab_icespike", 0.5F), 4),
         new PlayerAnimationHandler.TimedPlayerAnimation(new PlayerAnimationHandler.PlayerAnimation(PATH, "animation.hold_icespike", 0.5F, true), 8)
      )
   );
   public static final PlayerAnimationHandler.PlayerAnimation SPIKE_THROW = new PlayerAnimationHandler.PlayerAnimation(PATH, "animation.throw_icespike", 0.5F);
   public static final PlayerAnimationHandler.QueuedPlayerAnimation JUMP_AND_HOLD = new PlayerAnimationHandler.QueuedPlayerAnimation(
      List.of(
         new PlayerAnimationHandler.TimedPlayerAnimation(new PlayerAnimationHandler.PlayerAnimation(PATH, "animation.yeti_gaunltet_leap_smash_start", 0.5F), 11),
         new PlayerAnimationHandler.TimedPlayerAnimation(
            new PlayerAnimationHandler.PlayerAnimation(PATH, "animation.yeti_gaunltet_leap_smash_loop_air", 0.5F, true), 5
         )
      )
   );
   public static final PlayerAnimationHandler.PlayerAnimation LAND = new PlayerAnimationHandler.PlayerAnimation(
      PATH, "animation.yeti_gauntlet_leap_smash_hit", 0.5F
   );
   protected boolean active = false;
   protected UUID[] shards = new UUID[4];
   protected long lastShotTick = 0L;

   public static GauntletAttachment fromPlayer(Player player) {
      return (GauntletAttachment)player.getData(BossesRiseDataAttachments.GAUNTLET_ATTACHMENT);
   }

   public UUID[] getShards() {
      return this.shards;
   }

   public @UnknownNullability CompoundTag serializeNBT(Provider provider) {
      CompoundTag tag = new CompoundTag();
      tag.putBoolean("active", this.active);
      if (this.shards[0] != null) {
         tag.putUUID("shard_0", this.shards[0]);
      }

      if (this.shards[1] != null) {
         tag.putUUID("shard_1", this.shards[1]);
      }

      if (this.shards[2] != null) {
         tag.putUUID("shard_2", this.shards[2]);
      }

      if (this.shards[3] != null) {
         tag.putUUID("shard_3", this.shards[3]);
      }

      tag.putLong("last_shot", this.lastShotTick);
      return tag;
   }

   public void deserializeNBT(Provider provider, CompoundTag tag) {
      this.active = tag.getBoolean("active");
      if (tag.contains("shard_0")) {
         this.shards[0] = tag.getUUID("shard_0");
      }

      if (tag.contains("shard_1")) {
         this.shards[1] = tag.getUUID("shard_1");
      }

      if (tag.contains("shard_2")) {
         this.shards[2] = tag.getUUID("shard_2");
      }

      if (tag.contains("shard_3")) {
         this.shards[3] = tag.getUUID("shard_3");
      }

      this.lastShotTick = tag.getLong("last_shot");
   }

   public int getNewShardIndex(RandomSource random) {
      List<Integer> options = new ArrayList<>();

      for (int i = 0; i < this.shards.length; i++) {
         if (this.shards[i] == null) {
            options.add(i);
         }
      }

      return options.isEmpty() ? -1 : options.size() > 1 ? options.get(random.nextInt(options.size())) : options.getFirst();
   }

   public boolean isHoldingAnyShards() {
      for (int i = 0; i < this.shards.length; i++) {
         if (this.shards[i] != null) {
            return true;
         }
      }

      return false;
   }

   @SubscribeEvent
   private static void tickEvent(Post event) {
      fromPlayer(event.getEntity()).tick(event.getEntity());
   }

   public void tick(Player player) {
      if (player instanceof ServerPlayer serverPlayer) {
         boolean hasShard = false;
         boolean hasSpace = false;

         for (int i = 0; i < this.shards.length; i++) {
            if (this.shards[i] != null) {
               if (serverPlayer.serverLevel().getEntity(this.shards[i]) instanceof IceSpikeClusterEntity) {
                  hasShard = true;
               } else {
                  this.shards[i] = null;
                  hasSpace = true;
               }
            } else {
               hasSpace = true;
            }
         }

         if (hasShard) {
            if (serverPlayer.isUsingItem() && serverPlayer.getUseItem().is((Item)BossesRiseItems.ICE_GAUNTLET.get())) {
               if (!hasSpace) {
                  return;
               }

               for (IceSpikeClusterEntity spike : serverPlayer.level()
                  .getEntitiesOfClass(IceSpikeClusterEntity.class, AABB.ofSize(player.getEyePosition().add(player.getViewVector(1.0F)), 3.0, 2.0, 3.0))) {
                  if (!spike.getHeld()) {
                     int index = this.getNewShardIndex(player.getRandom());
                     if (index == -1) {
                        break;
                     }

                     spike.getEntityData().set(IceSpikeClusterEntity.DATA_REMAINING_HIT, index);
                     this.shards[index] = spike.getUUID();
                     serverPlayer.serverLevel().sendParticles(ParticleTypes.SNOWFLAKE, spike.getX(), spike.getY(), spike.getZ(), 15, 0.25, 0.5, 0.25, 0.0);
                     serverPlayer.serverLevel()
                        .playSound(
                           null,
                           spike.blockPosition(),
                           (SoundEvent)BossesRiseSounds.ICICLE_PICKUP.value(),
                           SoundSource.PLAYERS,
                           0.9F,
                           (float)Mth.nextDouble(player.getRandom(), 0.15, 0.35)
                        );
                     spike.setHeld(true);
                     spike.setOwnerUUID(player.getUUID());
                  }
               }
            } else {
               PlayerAnimationHandler.fromPlayer(player).startAnimation(player, SPIKE_THROW, 9);
               if (player.level().getGameTime() - this.lastShotTick < 2L) {
                  return;
               }

               for (int i = 0; i < this.shards.length; i++) {
                  if (this.shards[i] != null && serverPlayer.serverLevel().getEntity(this.shards[i]) instanceof IceSpikeClusterEntity spikeCluster) {
                     Vec3 viewVector = player.getViewVector(1.0F);
                     IceSpikeProjectileEntity spike = new IceSpikeProjectileEntity(
                        (EntityType<? extends IceSpikeProjectileEntity>)BossesRiseEntities.ICE_SPIKE_PR.get(), player, player.level()
                     );
                     spike.setPos(player.getEyePosition());
                     spike.shoot(viewVector.x, viewVector.y, viewVector.z, 4.0F, i * 5);
                     spike.setSilent(true);
                     spike.setBaseDamage(4.0);
                     spike.setKnockback(8);
                     player.level().addFreshEntity(spike);
                     serverPlayer.serverLevel()
                        .playSound(
                           null,
                           spikeCluster.blockPosition(),
                           (SoundEvent)BossesRiseSounds.THROW_ICICLE.value(),
                           SoundSource.PLAYERS,
                           0.9F,
                           (float)Mth.nextDouble(spikeCluster.getRandom(), 1.15, 1.75)
                        );
                     spikeCluster.discard();
                     this.shards[i] = null;
                     this.lastShotTick = player.level().getGameTime();
                     break;
                  }
               }
            }
         }
      }
   }

   @SubscribeEvent
   private static void preFall(LivingFallEvent event) {
      if (event.getEntity() instanceof ServerPlayer player && onFall(player)) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   private static void preFlyingFall(PlayerFlyableFallEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         onFall(player);
      }
   }

   public static boolean onFall(ServerPlayer player) {
      GauntletAttachment attachment = fromPlayer(player);
      if (attachment.active) {
         PlayerAnimationHandler.fromPlayer(player).startAnimation(player, LAND, 12);
         attachment.active = false;
         iceBurst(player, player.position(), player.serverLevel(), 8.0F, 40, 3.0F, (random, integer) -> random.nextInt(10) == 0 || integer < 4);
         player.getCooldowns().addCooldown((Item)BossesRiseItems.ICE_GAUNTLET.get(), 200);
         player.level()
            .getEntities(
               player,
               player.getBoundingBox().inflate(2.0),
               entity1 -> {
                  if (entity1 == player) {
                     return false;
                  } else {
                     return entity1 instanceof OwnableEntity ownable && ownable.getOwner() == player
                        ? false
                        : entity1.isAlive() && !entity1.isSpectator() && !(entity1 instanceof IceSpikeEntity) && !(entity1 instanceof IceSpikeClusterEntity);
                  }
               }
            )
            .forEach(entity -> {
               entity.hurt(entity.level().damageSources().freeze(), 10.0F);
               entity.setTicksFrozen(200);
            });
         return true;
      } else {
         return false;
      }
   }

   @SubscribeEvent
   private static void onJump(LivingJumpEvent event) {
      if (event.getEntity() instanceof ServerPlayer player
         && player.isUsingItem()
         && player.getUseItem().is(BossesRiseItems.ICE_GAUNTLET)
         && !player.getCooldowns().isOnCooldown((Item)BossesRiseItems.ICE_GAUNTLET.get())) {
         GauntletAttachment attachment = fromPlayer(player);
         if (!attachment.active && !attachment.isHoldingAnyShards()) {
            attachment.active = true;
            player.getUseItem().hurtAndBreak(10, player, player.getEquipmentSlotForItem(player.getUseItem()));
            player.stopUsingItem();
            PlayerAnimationHandler.fromPlayer(player).startAnimation(player, JUMP_AND_HOLD);
            player.getCooldowns().addCooldown((Item)BossesRiseItems.ICE_GAUNTLET.get(), 400);
            SpatialUtil.pushEntity(player, player.getViewVector(1.0F).add(0.0, 2.0, 0.0));
         }
      }
   }

   public static void iceBurst(
      LivingEntity living, Vec3 center, ServerLevel level, float range, int count, float damage, BiFunction<RandomSource, Integer, Boolean> cluster
   ) {
      Vec3 viewVector = living.getViewVector(1.0F);
      if (living.getXRot() > 85.0F) {
         viewVector = living.calculateViewVector(85.0F, living.getViewYRot(1.0F));
      } else if (living.getXRot() < -85.0F) {
         viewVector = living.calculateViewVector(-85.0F, living.getViewYRot(1.0F));
      }

      Vec3 diff = new Vec3(viewVector.x, 0.0, viewVector.z).normalize().scale(range);
      PacketDistributor.sendToPlayersTrackingEntityAndSelf(living, new IceGauntletMessage(center, diff.length()), new CustomPacketPayload[0]);

      for (int j = 0; j < count; j++) {
         if (cluster.apply(level.random, j)) {
            IceSpikeClusterEntity spikeEntity = new IceSpikeClusterEntity((EntityType<IceSpikeClusterEntity>)BossesRiseEntities.ICE_SPIKE_CLUSTER.get(), level);
            Vec3 wanted = diff.scale((j + 1) / 35.0 + 0.25).yRot(360.0F * level.random.nextFloat() * (float) (Math.PI / 180.0));
            Vec3 hit = IceGauntletItem.placeProper(level, living, center.add(wanted), living.getEyePosition());
            if (hit != null) {
               spikeEntity.moveTo(hit);
               spikeEntity.setDelay((int)Math.sqrt(16 + j));
               spikeEntity.setDamage(damage);
               spikeEntity.setOwnerUUID(living.getUUID());
               spikeEntity.setEvil(living instanceof YetiEntity);
               if (j % 5 != 0) {
                  spikeEntity.setSilent(true);
               }

               level.addFreshEntity(spikeEntity);
               spikeEntity.lookAt(Anchor.FEET, spikeEntity.position().add(wanted.add(0.0, wanted.length() * Mth.randomBetween(level.random, 0.75F, 1.0F), 0.0)));
            }
         } else {
            IceSpikeEntity spikeEntity = new IceSpikeEntity((EntityType<IceSpikeEntity>)BossesRiseEntities.ICE_SPIKE.get(), level);
            Vec3 wanted = diff.scale((j + 1) / 35.0 + 0.25).yRot(360.0F * level.random.nextFloat() * (float) (Math.PI / 180.0));
            Vec3 hit = IceGauntletItem.placeProper(level, living, center.add(wanted), living.getEyePosition());
            if (hit != null) {
               spikeEntity.moveTo(hit);
               spikeEntity.setScale(j / (count / 4) + 1);
               spikeEntity.setDelay((int)Math.sqrt(spikeEntity.getDelay() + j));
               spikeEntity.setDamage(damage);
               spikeEntity.setOwnerUUID(living.getUUID());
               spikeEntity.setEvil(living instanceof YetiEntity);
               if (j % 5 != 0) {
                  spikeEntity.setSilent(true);
               }

               level.addFreshEntity(spikeEntity);
               spikeEntity.lookAt(Anchor.FEET, spikeEntity.position().add(wanted.add(0.0, wanted.length() * Mth.randomBetween(level.random, 0.75F, 1.0F), 0.0)));
            }
         }
      }
   }

   public static void delayedBurst(LivingEntity living, Vec3 center, ServerLevel level, float range, int count, float damage, int delay) {
      Vec3 viewVector = living.getViewVector(1.0F);
      if (living.getXRot() > 85.0F) {
         viewVector = living.calculateViewVector(85.0F, living.getViewYRot(1.0F));
      } else if (living.getXRot() < -85.0F) {
         viewVector = living.calculateViewVector(-85.0F, living.getViewYRot(1.0F));
      }

      Vec3 diff = new Vec3(viewVector.x, 0.0, viewVector.z).normalize().scale(range);
      PacketDistributor.sendToPlayersTrackingEntityAndSelf(living, new IceGauntletMessage(center, diff.length()), new CustomPacketPayload[0]);

      for (int j = 0; j < count; j++) {
         if (level.random.nextInt(10) == 0) {
            IceSpikeClusterEntity spikeEntity = new IceSpikeClusterEntity((EntityType<IceSpikeClusterEntity>)BossesRiseEntities.ICE_SPIKE_CLUSTER.get(), level);
            Vec3 wanted = diff.scale((j + 1) / 35.0 + 0.25).yRot(360.0F * level.random.nextFloat() * (float) (Math.PI / 180.0));
            Vec3 hit = IceGauntletItem.placeProper(level, living, center.add(wanted), living.getEyePosition());
            if (hit != null) {
               spikeEntity.moveTo(hit);
               spikeEntity.setDelay(delay);
               spikeEntity.setDamage(damage);
               spikeEntity.setOwnerUUID(living.getUUID());
               spikeEntity.setEvil(living instanceof YetiEntity);
               if (j % 5 != 0) {
                  spikeEntity.setSilent(true);
               }

               level.addFreshEntity(spikeEntity);
               spikeEntity.lookAt(Anchor.FEET, spikeEntity.position().add(wanted.add(0.0, wanted.length() * Mth.randomBetween(level.random, 0.75F, 1.0F), 0.0)));
            }
         } else {
            IceSpikeEntity spikeEntity = new IceSpikeEntity((EntityType<IceSpikeEntity>)BossesRiseEntities.ICE_SPIKE.get(), level);
            Vec3 wanted = diff.scale((j + 1) / 35.0 + 0.25).yRot(360.0F * level.random.nextFloat() * (float) (Math.PI / 180.0));
            Vec3 hit = IceGauntletItem.placeProper(level, living, center.add(wanted), living.getEyePosition());
            if (hit != null) {
               spikeEntity.moveTo(hit);
               spikeEntity.setScale(j / (count / 4) + 1);
               spikeEntity.setDelay(delay);
               spikeEntity.setDamage(damage);
               spikeEntity.setOwnerUUID(living.getUUID());
               spikeEntity.setEvil(living instanceof YetiEntity);
               if (j % 5 != 0) {
                  spikeEntity.setSilent(true);
               }

               level.addFreshEntity(spikeEntity);
               spikeEntity.lookAt(Anchor.FEET, spikeEntity.position().add(wanted.add(0.0, wanted.length() * Mth.randomBetween(level.random, 0.75F, 1.0F), 0.0)));
            }
         }
      }
   }

   public static void iceWave(LivingEntity living, ServerLevel level, float range, int count, Vec3 center, Vec3 angle, float damage, float speed) {
      Vec3 diff = new Vec3(angle.x, 0.0, angle.z).normalize().scale(range);
      PacketDistributor.sendToPlayersTrackingEntityAndSelf(living, new IceGauntletMessage(center, diff.length()), new CustomPacketPayload[0]);
      level.sendParticles(ParticleTypes.SNOWFLAKE, center.x, center.y, center.z, 15, 2.0, 0.5, 2.0, 0.0);

      for (int j = 0; j < count; j++) {
         if (level.random.nextInt(10) == 0) {
            IceSpikeClusterEntity spikeEntity = new IceSpikeClusterEntity((EntityType<IceSpikeClusterEntity>)BossesRiseEntities.ICE_SPIKE_CLUSTER.get(), level);
            Vec3 wanted = diff.scale((j + 1) / 35.0).yRot((80.0F * level.random.nextFloat() - 40.0F) * (float) (Math.PI / 180.0));
            Vec3 hit = IceGauntletItem.placeProper(level, living, center.add(wanted), living.getEyePosition());
            if (hit != null) {
               spikeEntity.moveTo(hit);
               spikeEntity.setDelay(16 + (int)(j / speed));
               spikeEntity.setDamage(damage);
               spikeEntity.setOwnerUUID(living.getUUID());
               spikeEntity.setEvil(living instanceof YetiEntity);
               if (j % 5 != 0) {
                  spikeEntity.setSilent(true);
               }

               level.addFreshEntity(spikeEntity);
               spikeEntity.lookAt(Anchor.FEET, spikeEntity.position().add(wanted.add(0.0, wanted.length() * Mth.randomBetween(level.random, 0.75F, 1.0F), 0.0)));
            }
         } else {
            IceSpikeEntity spikeEntity = new IceSpikeEntity((EntityType<IceSpikeEntity>)BossesRiseEntities.ICE_SPIKE.get(), level);
            Vec3 wanted = diff.scale((j + 1) / 35.0).yRot((80.0F * level.random.nextFloat() - 40.0F) * (float) (Math.PI / 180.0));
            Vec3 hit = IceGauntletItem.placeProper(level, living, center.add(wanted), living.getEyePosition());
            if (hit != null) {
               spikeEntity.moveTo(hit);
               spikeEntity.setScale(j / 8 + 1);
               spikeEntity.setDelay(spikeEntity.getDelay() + (int)(j / speed));
               spikeEntity.setDamage(damage);
               spikeEntity.setOwnerUUID(living.getUUID());
               spikeEntity.setEvil(living instanceof YetiEntity);
               if (j % 5 != 0) {
                  spikeEntity.setSilent(true);
               }

               level.addFreshEntity(spikeEntity);
               spikeEntity.lookAt(Anchor.FEET, spikeEntity.position().add(wanted.add(0.0, wanted.length() * Mth.randomBetween(level.random, 0.75F, 1.0F), 0.0)));
            }
         }
      }
   }
}
