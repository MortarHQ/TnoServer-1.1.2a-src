package com.github.wal_bos.moreskills.handler.asmoday;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.item.AsmodayPrisonCubeItem;
import com.github.wal_bos.moreskills.net.msg.AsmodayPrisonRealmPayload;
import com.github.wal_bos.moreskills.registry.TenaddexItems;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public final class AsmodayPrisonRealmManager {
   public static final ResourceKey<Level> PRISON_DIMENSION = ResourceKey.create(
      Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "asmoday_prison_realm")
   );
   private static final Map<UUID, AsmodayPrisonRealmManager.PrisonState> PRISONS_BY_TARGET = new ConcurrentHashMap<>();
   private static final Map<UUID, UUID> TARGET_BY_OWNER = new ConcurrentHashMap<>();
   private static final Map<UUID, UUID> DROPPED_CUBE_TO_TARGET = new ConcurrentHashMap<>();

   private AsmodayPrisonRealmManager() {
   }

   public static boolean seal(ServerPlayer owner, ServerPlayer target, boolean mastered) {
      MinecraftServer server = owner.server;
      ServerLevel prisonLevel = server.getLevel(PRISON_DIMENSION);
      if (prisonLevel == null) {
         owner.sendSystemMessage(Component.literal("Missing prison dimension: " + PRISON_DIMENSION.location()).withStyle(ChatFormatting.RED));
         return false;
      }

      if (hasActivePrison(owner)) {
         owner.sendSystemMessage(Component.literal("You are already holding a closed space.").withStyle(ChatFormatting.RED));
         return false;
      }

      if (PRISONS_BY_TARGET.containsKey(target.getUUID())) {
         owner.sendSystemMessage(Component.literal("That player is already sealed.").withStyle(ChatFormatting.RED));
         return false;
      }

      Vec3 returnPos = target.position();
      ResourceKey<Level> returnDimension = target.level().dimension();
      Vec3 prisonPos = prisonPosFor(target.getUUID());
      buildPrisonBox(prisonLevel, prisonPos);
      AsmodayPrisonRealmManager.PrisonState state = new AsmodayPrisonRealmManager.PrisonState(
         owner.getUUID(),
         target.getUUID(),
         target.getGameProfile().getName(),
         returnDimension,
         returnPos,
         PRISON_DIMENSION,
         prisonPos,
         (Integer)cfg().prisonMaxStability.get(),
         owner.tickCount,
         mastered
      );
      PRISONS_BY_TARGET.put(target.getUUID(), state);
      TARGET_BY_OWNER.put(owner.getUUID(), target.getUUID());
      ItemStack cube = AsmodayPrisonCubeItem.createBoundCube(target.getUUID(), target.getGameProfile().getName(), owner.getUUID(), returnDimension, returnPos);
      if (!owner.getInventory().add(cube)) {
         owner.drop(cube, false);
      }

      target.teleportTo(prisonLevel, prisonPos.x, prisonPos.y, prisonPos.z, target.getYRot(), target.getXRot());
      target.setDeltaMovement(Vec3.ZERO);
      target.hurtMarked = true;
      applyPrisonEffects(target, true);
      protectPrisoner(target);
      sendPrisonFx(owner, target, 1, 160, 18.0, mastered);
      broadcast(owner, target, Component.literal(target.getGameProfile().getName() + " has been sealed beyond coordinates.").withStyle(ChatFormatting.DARK_RED));
      owner.sendSystemMessage(Component.literal("Your body becomes the lock. The Prison Cube is the key.").withStyle(ChatFormatting.DARK_RED));
      target.sendSystemMessage(Component.literal("You have been sealed in Asmoday's Five-Hundred-Year Closed Space.").withStyle(ChatFormatting.RED));
      owner.level().playSound(null, owner.blockPosition(), SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 1.6F, 0.12F);
      prisonLevel.playSound(null, BlockPos.containing(prisonPos), (SoundEvent)SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.PLAYERS, 1.2F, 0.18F);
      return true;
   }

   public static void tick(ServerLevel level) {
      tickPrisons(level);
      tickDroppedCubes(level);
   }

   public static void onPlayerLogin(ServerPlayer player) {
      AsmodayPrisonRealmManager.PrisonState state = PRISONS_BY_TARGET.get(player.getUUID());
      if (state != null) {
         MinecraftServer server = player.server;
         ServerLevel prisonLevel = server.getLevel(state.prisonDimension);
         if (prisonLevel != null) {
            buildPrisonBox(prisonLevel, state.prisonPos);
            player.teleportTo(prisonLevel, state.prisonPos.x, state.prisonPos.y, state.prisonPos.z, player.getYRot(), player.getXRot());
            player.setDeltaMovement(Vec3.ZERO);
            player.hurtMarked = true;
            applyPrisonEffects(player, true);
            protectPrisoner(player);
            player.sendSystemMessage(Component.literal("The Closed Space remembers you. Escape is denied.").withStyle(ChatFormatting.RED));
         }
      }
   }

   public static boolean hasActivePrison(Entity owner) {
      return TARGET_BY_OWNER.containsKey(owner.getUUID());
   }

   public static boolean isPrisoned(UUID target) {
      return PRISONS_BY_TARGET.containsKey(target);
   }

   public static void protectPrisoner(ServerPlayer target) {
      AsmodayPrisonRealmManager.PrisonState state = PRISONS_BY_TARGET.get(target.getUUID());
      if (state != null) {
         target.setHealth(target.getMaxHealth());
         target.setAbsorptionAmount(Math.max(target.getAbsorptionAmount(), ((Double)cfg().prisonProtectionAbsorption.get()).floatValue()));
         target.invulnerableTime = Math.max(target.invulnerableTime, (Integer)cfg().prisonProtectionInvulnerabilityTicks.get());
         target.fallDistance = 0.0F;
         target.setRemainingFireTicks(0);
         target.clearFire();
         target.setAirSupply(target.getMaxAirSupply());
         target.setDeltaMovement(target.getDeltaMovement().scale((Double)cfg().prisonProtectionMotionScale.get()));
         target.hurtMarked = true;
         IExistence existence = TensuraStorages.getExistenceFrom(target);
         existence.setSpiritualHealth(Math.max(existence.getSpiritualHealth(), target.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH)));
         existence.markDirty();
         applyPrisonEffects(target, false);
      }
   }

   public static void releaseOwnedBy(Entity owner, boolean violent, String reason) {
      UUID target = TARGET_BY_OWNER.get(owner.getUUID());
      if (target != null) {
         release(target, violent, reason);
      }
   }

   public static void releaseByCube(ServerPlayer releaser, ItemStack stack, boolean violent) {
      UUID targetId = AsmodayPrisonCubeItem.getTarget(stack);
      if (targetId == null) {
         releaser.sendSystemMessage(Component.literal("This cube holds no space.").withStyle(ChatFormatting.GRAY));
      } else if (!PRISONS_BY_TARGET.containsKey(targetId)) {
         releaser.sendSystemMessage(Component.literal("The cube is empty.").withStyle(ChatFormatting.GRAY));
         stack.shrink(1);
      } else {
         release(targetId, violent, releaser.getGameProfile().getName() + " opened the Prison Cube.");
         stack.shrink(1);
      }
   }

   public static void trackDroppedCube(ItemEntity entity) {
      ItemStack stack = entity.getItem();
      if (stack.is((Item)TenaddexItems.ASMODAY_PRISON_CUBE.get())) {
         UUID target = AsmodayPrisonCubeItem.getTarget(stack);
         if (target != null && PRISONS_BY_TARGET.containsKey(target)) {
            DROPPED_CUBE_TO_TARGET.put(entity.getUUID(), target);
         }
      }
   }

   public static void sendChargeFx(ServerPlayer owner, ServerPlayer target, int seconds, int maxSeconds, boolean mastered) {
      if (owner.level() instanceof ServerLevel level) {
         double var13 = Mth.clamp((double)seconds / maxSeconds, 0.0, 1.0);
         double size = (Double)cfg().prisonChargeBaseSize.get() + var13 * (Double)cfg().prisonChargeBonusSize.get();
         int duration = (Integer)cfg().prisonChargeFxDurationTicks.get();
         Vec3 center = target.position().add(0.0, target.getBbHeight() * 0.52, 0.0);
         AsmodayPrisonRealmPayload payload = new AsmodayPrisonRealmPayload(
            owner.getId() * 65536 + target.getId() * 31 + seconds,
            owner.getId(),
            target.getId(),
            center.x,
            center.y,
            center.z,
            size,
            duration,
            owner.getRandom().nextInt(),
            0,
            mastered
         );
         sendToWatchers(level, center, payload);
      }
   }

   private static void tickPrisons(ServerLevel tickLevel) {
      MinecraftServer server = tickLevel.getServer();
      Iterator<Entry<UUID, AsmodayPrisonRealmManager.PrisonState>> iterator = PRISONS_BY_TARGET.entrySet().iterator();

      while (iterator.hasNext()) {
         Entry<UUID, AsmodayPrisonRealmManager.PrisonState> entry = iterator.next();
         AsmodayPrisonRealmManager.PrisonState state = entry.getValue();
         ServerPlayer target = server.getPlayerList().getPlayer(state.target);
         ServerPlayer owner = server.getPlayerList().getPlayer(state.owner);
         if (target != null) {
            ServerLevel prisonLevel = server.getLevel(state.prisonDimension);
            if (prisonLevel != null) {
               buildPrisonBox(prisonLevel, state.prisonPos);
               if (!target.level().dimension().equals(state.prisonDimension)) {
                  target.teleportTo(prisonLevel, state.prisonPos.x, state.prisonPos.y, state.prisonPos.z, target.getYRot(), target.getXRot());
               } else {
                  double maxDistance = (Double)cfg().prisonMaxDistanceFromCenter.get();
                  if (target.position().distanceToSqr(state.prisonPos) > maxDistance * maxDistance
                     || target.getY() < state.prisonPos.y + ((Integer)cfg().prisonBoxFloorYOffset.get()).intValue() + 1.0
                     || target.getY() > state.prisonPos.y + ((Integer)cfg().prisonBoxCeilingYOffset.get()).intValue() - 1.0) {
                     target.teleportTo(state.prisonPos.x, state.prisonPos.y, state.prisonPos.z);
                  }
               }

               protectPrisoner(target);
               if (target.tickCount % (Integer)cfg().prisonAmbientFxIntervalTicks.get() == 0 && target.level() instanceof ServerLevel prisonServerLevel) {
                  Vec3 p = target.position().add(0.0, target.getBbHeight() * 0.55, 0.0);
                  prisonServerLevel.sendParticles(ParticleTypes.REVERSE_PORTAL, p.x, p.y, p.z, 34, 2.6, 1.8, 2.6, 0.08);
                  prisonServerLevel.sendParticles(ParticleTypes.DRAGON_BREATH, p.x, p.y, p.z, 18, 1.4, 1.0, 1.4, 0.025);
                  if (owner != null) {
                     sendPrisonFx(owner, target, 2, (Integer)cfg().prisonAmbientFxDurationTicks.get(), (Double)cfg().prisonAmbientFxSize.get(), state.mastered);
                  }
               }

               if (owner != null && owner.tickCount % (Integer)cfg().prisonPayIntervalTicks.get() == 0) {
                  boolean paid = payUpkeep(owner, state.mastered);
                  if (!paid) {
                     state.stability = state.stability - (Integer)cfg().prisonDestabilizeOnFail.get();
                     owner.sendSystemMessage(
                        Component.literal("The Closed Space destabilizes. Stability: " + Math.max(0, state.stability) + "%").withStyle(ChatFormatting.RED)
                     );
                     if (target.isAlive()) {
                        target.sendSystemMessage(
                           Component.literal("The prison weakens. Stability: " + Math.max(0, state.stability) + "%").withStyle(ChatFormatting.RED)
                        );
                     }

                     if (state.stability <= 0) {
                        releaseInternal(server, state, true, "The prison collapsed from unpaid spatial debt.");
                        iterator.remove();
                        TARGET_BY_OWNER.remove(state.owner);
                     }
                  } else if (state.stability < (Integer)cfg().prisonMaxStability.get()
                     && owner.tickCount % ((Integer)cfg().prisonPayIntervalTicks.get() * (Integer)cfg().prisonStabilityRecoverPayIntervals.get()) == 0) {
                     state.stability++;
                  }
               }
            }
         }
      }
   }

   private static void tickDroppedCubes(ServerLevel level) {
      Iterator<Entry<UUID, UUID>> iterator = DROPPED_CUBE_TO_TARGET.entrySet().iterator();

      while (iterator.hasNext()) {
         Entry<UUID, UUID> entry = iterator.next();
         if (!(level.getEntity(entry.getKey()) instanceof ItemEntity itemEntity && itemEntity.isAlive())) {
            iterator.remove();
         } else if (!itemEntity.getItem().is((Item)TenaddexItems.ASMODAY_PRISON_CUBE.get())) {
            iterator.remove();
         } else {
            boolean destroyedCondition = itemEntity.isInLava() || itemEntity.isOnFire() || itemEntity.getY() < level.getMinBuildHeight() - 32.0;
            if (destroyedCondition) {
               UUID target = entry.getValue();
               itemEntity.discard();
               release(target, true, "The Prison Cube was destroyed.");
               iterator.remove();
            }
         }
      }
   }

   private static boolean payUpkeep(ServerPlayer owner, boolean mastered) {
      double multiplier = mastered ? (Double)cfg().prisonMasteredDrainMultiplier.get() : 1.0;
      double magiculeCost = (Double)cfg().prisonMagiculeDrain.get() * multiplier;
      double auraCost = (Double)cfg().prisonAuraDrain.get() * multiplier;
      IExistence existence = TensuraStorages.getExistenceFrom(owner);
      if (existence.getMagicule() >= magiculeCost) {
         existence.setMagicule(existence.getMagicule() - magiculeCost);
         existence.markDirty();
         return true;
      } else if (existence.getAura() >= auraCost) {
         existence.setAura(existence.getAura() - auraCost);
         existence.markDirty();
         return true;
      } else {
         return false;
      }
   }

   private static void release(UUID target, boolean violent, String reason) {
      AsmodayPrisonRealmManager.PrisonState state = PRISONS_BY_TARGET.remove(target);
      if (state != null) {
         TARGET_BY_OWNER.remove(state.owner);
         DROPPED_CUBE_TO_TARGET.entrySet().removeIf(entry -> entry.getValue().equals(target));
         MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
         if (server != null) {
            releaseInternal(server, state, violent, reason);
         }
      }
   }

   private static void releaseInternal(MinecraftServer server, AsmodayPrisonRealmManager.PrisonState state, boolean violent, String reason) {
      ServerPlayer target = server.getPlayerList().getPlayer(state.target);
      ServerLevel returnLevel = server.getLevel(state.returnDimension);
      if (target != null && returnLevel != null) {
         Vec3 pos = state.returnPos;
         clearPrisonEffects(target);
         target.teleportTo(returnLevel, pos.x, pos.y, pos.z, target.getYRot(), target.getXRot());
         target.setDeltaMovement(Vec3.ZERO);
         target.hurtMarked = true;
         target.setHealth(Math.max(1.0F, target.getHealth()));
         target.setAbsorptionAmount(0.0F);
         target.invulnerableTime = violent
            ? (Integer)cfg().prisonViolentReleaseInvulnerabilityTicks.get()
            : (Integer)cfg().prisonSafeReleaseInvulnerabilityTicks.get();
         target.fallDistance = 0.0F;
         target.setRemainingFireTicks(0);
         target.clearFire();
         target.setAirSupply(target.getMaxAirSupply());
         clearPrisonEffects(target);
         if (violent) {
            target.addEffect(
               new MobEffectInstance(
                  TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE),
                  (Integer)cfg().prisonViolentReleaseMovementTicks.get(),
                  (Integer)cfg().prisonViolentReleaseMovementAmplifier.get(),
                  false,
                  false,
                  true
               )
            );
            returnLevel.sendParticles(ParticleTypes.EXPLOSION, pos.x, pos.y + 1.0, pos.z, 4, 0.5, 0.5, 0.5, 0.0);
            returnLevel.sendParticles(ParticleTypes.REVERSE_PORTAL, pos.x, pos.y + 1.0, pos.z, 130, 3.2, 2.2, 3.2, 0.24);
            returnLevel.playSound(null, BlockPos.containing(pos), (SoundEvent)SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.PLAYERS, 1.4F, 0.12F);
            returnLevel.playSound(null, BlockPos.containing(pos), SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 0.9F, 0.18F);
         } else {
            returnLevel.sendParticles(ParticleTypes.REVERSE_PORTAL, pos.x, pos.y + 1.0, pos.z, 70, 2.2, 1.4, 2.2, 0.12);
            returnLevel.playSound(null, BlockPos.containing(pos), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 0.45F);
         }

         target.sendSystemMessage(Component.literal(reason).withStyle(violent ? ChatFormatting.RED : ChatFormatting.GRAY));
      }

      ServerPlayer owner = server.getPlayerList().getPlayer(state.owner);
      if (owner != null) {
         owner.sendSystemMessage(Component.literal("The Five-Hundred-Year Closed Space has opened.").withStyle(ChatFormatting.DARK_RED));
      }
   }

   private static void clearPrisonEffects(ServerPlayer target) {
      target.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.SPATIAL_BLOCKADE));
      target.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_INTERFERENCE));
      target.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE));
      target.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.ENERGY_BLOCKADE));
   }

   private static void buildPrisonBox(ServerLevel level, Vec3 center) {
      BlockPos middle = BlockPos.containing(center);
      int minX = middle.getX() - (Integer)cfg().prisonBoxRadius.get();
      int maxX = middle.getX() + (Integer)cfg().prisonBoxRadius.get();
      int minY = middle.getY() + (Integer)cfg().prisonBoxFloorYOffset.get();
      int maxY = middle.getY() + (Integer)cfg().prisonBoxCeilingYOffset.get();
      int minZ = middle.getZ() - (Integer)cfg().prisonBoxRadius.get();
      int maxZ = middle.getZ() + (Integer)cfg().prisonBoxRadius.get();

      for (int x = minX; x <= maxX; x++) {
         for (int y = minY; y <= maxY; y++) {
            for (int z = minZ; z <= maxZ; z++) {
               boolean wall = x == minX || x == maxX || y == minY || y == maxY || z == minZ || z == maxZ;
               BlockPos pos = new BlockPos(x, y, z);
               if (wall) {
                  if (!level.getBlockState(pos).is(Blocks.BEDROCK)) {
                     level.setBlock(pos, Blocks.BEDROCK.defaultBlockState(), 2);
                  }
               } else if (!level.getBlockState(pos).isAir()) {
                  level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
               }
            }
         }
      }

      BlockPos light = middle.above(5);
      if (level.getBlockState(light).isAir()) {
         level.setBlock(light, Blocks.REDSTONE_BLOCK.defaultBlockState(), 2);
      }
   }

   private static void applyPrisonEffects(ServerPlayer target, boolean force) {
      if (force || target.tickCount % (Integer)cfg().prisonEffectRefreshTicks.get() == 0) {
         target.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.SPATIAL_BLOCKADE),
               (Integer)cfg().prisonEffectDurationTicks.get(),
               (Integer)cfg().prisonSpatialBlockadeAmplifier.get(),
               false,
               false,
               true
            )
         );
         target.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_INTERFERENCE),
               (Integer)cfg().prisonEffectDurationTicks.get(),
               (Integer)cfg().prisonMagicInterferenceAmplifier.get(),
               false,
               false,
               true
            )
         );
         target.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE),
               (Integer)cfg().prisonEffectDurationTicks.get(),
               (Integer)cfg().prisonMovementInterferenceAmplifier.get(),
               false,
               false,
               true
            )
         );
         target.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.ENERGY_BLOCKADE),
               (Integer)cfg().prisonEffectDurationTicks.get(),
               (Integer)cfg().prisonEnergyBlockadeAmplifier.get(),
               false,
               false,
               true
            )
         );
      }
   }

   private static Vec3 prisonPosFor(UUID uuid) {
      long most = uuid.getMostSignificantBits();
      long least = uuid.getLeastSignificantBits();
      int bits = (Integer)cfg().prisonCoordinateSpreadBits.get();
      long mask = (1L << bits) - 1L;
      long half = 1L << bits - 1;
      int x = (int)((most & mask) - half) * (Integer)cfg().prisonCoordinateSpacing.get();
      int z = (int)((least & mask) - half) * (Integer)cfg().prisonCoordinateSpacing.get();
      return new Vec3(x + 0.5, (Double)cfg().prisonSpawnY.get(), z + 0.5);
   }

   private static void sendPrisonFx(ServerPlayer owner, ServerPlayer target, int phase, int duration, double size, boolean mastered) {
      if (target.level() instanceof ServerLevel level) {
         Vec3 var10 = target.position().add(0.0, target.getBbHeight() * 0.52, 0.0);
         AsmodayPrisonRealmPayload payload = new AsmodayPrisonRealmPayload(
            owner.getId() * 65536 + target.getId() * 37 + phase * 999,
            owner.getId(),
            target.getId(),
            var10.x,
            var10.y,
            var10.z,
            size,
            duration,
            owner.getRandom().nextInt(),
            phase,
            mastered
         );
         sendToWatchers(level, var10, payload);
      }
   }

   private static void broadcast(ServerPlayer owner, ServerPlayer target, Component message) {
      if (owner.level() instanceof ServerLevel level) {
         AABB var7 = owner.getBoundingBox().inflate((Double)cfg().prisonWatchRange.get());

         for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, var7, p -> p != null && !p.isSpectator())) {
            player.sendSystemMessage(message);
         }
      }
   }

   private static void sendToWatchers(ServerLevel level, Vec3 center, AsmodayPrisonRealmPayload payload) {
      AABB box = new AABB(center, center).inflate((Double)cfg().prisonWatchRange.get());

      for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, box, p -> p != null && !p.isSpectator())) {
         NetworkManager.sendToPlayer(player, payload);
      }
   }

   private static MoreSkillsConfigs.AsmodayConfig cfg() {
      return MoreSkillsConfigs.GRAND.asmoday;
   }

   private static final class PrisonState {
      private final UUID owner;
      private final UUID target;
      private final String targetName;
      private final ResourceKey<Level> returnDimension;
      private final Vec3 returnPos;
      private final ResourceKey<Level> prisonDimension;
      private final Vec3 prisonPos;
      private int stability;
      private final int startTick;
      private final boolean mastered;

      private PrisonState(
         UUID owner,
         UUID target,
         String targetName,
         ResourceKey<Level> returnDimension,
         Vec3 returnPos,
         ResourceKey<Level> prisonDimension,
         Vec3 prisonPos,
         int stability,
         int startTick,
         boolean mastered
      ) {
         this.owner = owner;
         this.target = target;
         this.targetName = targetName;
         this.returnDimension = returnDimension;
         this.returnPos = returnPos;
         this.prisonDimension = prisonDimension;
         this.prisonPos = prisonPos;
         this.stability = stability;
         this.startTick = startTick;
         this.mastered = mastered;
      }
   }
}
