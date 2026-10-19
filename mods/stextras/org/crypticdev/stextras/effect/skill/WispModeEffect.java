package org.crypticdev.stextras.effect.skill;

import dev.architectury.event.events.common.TickEvent;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.crypticdev.stextras.config.race.WispfolkConfig;
import org.crypticdev.stextras.registary.effects.STExtrasMobEffects;

public class WispModeEffect extends MobEffect {
   private static final Set<UUID> TRACKED_PLAYERS = new HashSet<>();
   private static final Set<UUID> GRANTED_FLIGHT = new HashSet<>();
   private static final Set<UUID> GRANTED_NIGHT_VISION = new HashSet<>();
   private static final Map<UUID, WispModeEffect.SafePhasePosition> LAST_SAFE_PHASE_POSITIONS = new HashMap<>();
   private static final Map<UUID, WispModeEffect.PhaseAccessSnapshot> LAST_PHASE_ACCESS_SNAPSHOTS = new HashMap<>();
   private static boolean hooksInitialized;

   public WispModeEffect() {
      super(MobEffectCategory.BENEFICIAL, 11591935);
   }

   public static void initServerHooks() {
      if (!hooksInitialized) {
         hooksInitialized = true;
         TickEvent.SERVER_POST.register(WispModeEffect::serverTickCleanup);
      }
   }

   public static boolean isActive(LivingEntity entity) {
      return entity != null && entity.hasEffect(TensuraMobEffects.getReference(STExtrasMobEffects.WISP_MODE));
   }

   public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
      return true;
   }

   public boolean applyEffectTick(LivingEntity entity, int amplifier) {
      if (!(entity instanceof ServerPlayer sp)) {
         return true;
      } else {
         UUID playerId = sp.getUUID();
         TRACKED_PLAYERS.add(playerId);
         if (!sp.isCreative() && !sp.isSpectator()) {
            boolean hadMayfly = sp.getAbilities().mayfly;
            boolean changedAbilities = false;
            if (!sp.getAbilities().mayfly) {
               sp.getAbilities().mayfly = true;
               changedAbilities = true;
            }

            if (!sp.getAbilities().flying) {
               sp.getAbilities().flying = true;
               changedAbilities = true;
            }

            if (changedAbilities) {
               sp.onUpdateAbilities();
            }

            if (!hadMayfly) {
               GRANTED_FLIGHT.add(playerId);
            }
         } else {
            boolean changedAbilities = false;
            if (!sp.getAbilities().mayfly) {
               sp.getAbilities().mayfly = true;
               changedAbilities = true;
            }

            if (!sp.getAbilities().flying) {
               sp.getAbilities().flying = true;
               changedAbilities = true;
            }

            if (changedAbilities) {
               sp.onUpdateAbilities();
            }
         }

         sp.fallDistance = 0.0F;
         enforcePhaseBlacklist(sp, playerId);
         MobEffectInstance cur = sp.getEffect(MobEffects.NIGHT_VISION);
         if (cur == null || cur.getAmplifier() != 1 || cur.getDuration() <= 220) {
            sp.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 300, 1, true, false, false));
            GRANTED_NIGHT_VISION.add(playerId);
         }

         return true;
      }
   }

   public static void serverTickCleanup(MinecraftServer server) {
      Iterator<UUID> iterator = TRACKED_PLAYERS.iterator();

      while (iterator.hasNext()) {
         UUID id = iterator.next();
         ServerPlayer sp = server.getPlayerList().getPlayer(id);
         if (sp == null) {
            GRANTED_FLIGHT.remove(id);
            GRANTED_NIGHT_VISION.remove(id);
            iterator.remove();
         } else if (!sp.hasEffect(TensuraMobEffects.getReference(STExtrasMobEffects.WISP_MODE))) {
            cleanupPlayer(sp, id);
            iterator.remove();
         }
      }
   }

   private static void cleanupPlayer(ServerPlayer sp, UUID id) {
      if (GRANTED_NIGHT_VISION.remove(id)) {
         MobEffectInstance nightVision = sp.getEffect(MobEffects.NIGHT_VISION);
         if (nightVision != null && nightVision.getAmplifier() == 1 && nightVision.isAmbient() && !nightVision.isVisible()) {
            sp.removeEffect(MobEffects.NIGHT_VISION);
         }
      }

      popOutAbove(sp);
      if (!sp.isCreative() && !sp.isSpectator() && GRANTED_FLIGHT.remove(id)) {
         sp.getAbilities().flying = false;
         sp.getAbilities().mayfly = false;
         sp.onUpdateAbilities();
      }
   }

   public static boolean isDimensionBlacklisted(ResourceLocation dimensionId) {
      WispfolkConfig config = getConfig();
      return config != null && config.blacklistedDimensions != null ? config.blacklistedDimensions.contains(dimensionId.toString()) : false;
   }

   public static boolean isOpacSupportEnabled() {
      WispfolkConfig config = getConfig();
      return config != null && config.opacSupport;
   }

   public static boolean isBlockBlacklisted(BlockState state) {
      WispfolkConfig config = getConfig();
      if (config != null && config.blacklistedBlocks != null && !config.blacklistedBlocks.isEmpty()) {
         ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock());
         return blockId != null && config.blacklistedBlocks.contains(blockId.toString());
      } else {
         return false;
      }
   }

   public static boolean intersectsBlacklistedBlock(Level level, AABB bounds) {
      MutableBlockPos mutable = new MutableBlockPos();
      int minX = Mth.floor(bounds.minX);
      int maxX = Mth.floor(bounds.maxX);
      int minY = Mth.floor(bounds.minY);
      int maxY = Mth.floor(bounds.maxY);
      int minZ = Mth.floor(bounds.minZ);
      int maxZ = Mth.floor(bounds.maxZ);

      for (int x = minX; x <= maxX; x++) {
         for (int y = minY; y <= maxY; y++) {
            for (int z = minZ; z <= maxZ; z++) {
               mutable.set(x, y, z);
               BlockState state = level.getBlockState(mutable);
               if (isBlockBlacklisted(state)) {
                  VoxelShape shape = state.getCollisionShape(level, mutable);
                  if (!shape.isEmpty()) {
                     for (AABB shapeBox : shape.toAabbs()) {
                        if (shapeBox.move(mutable).intersects(bounds)) {
                           return true;
                        }
                     }
                  }
               }
            }
         }
      }

      return false;
   }

   private static WispfolkConfig getConfig() {
      return (WispfolkConfig)ConfigRegistry.getConfig(WispfolkConfig.class);
   }

   private static void popOutAbove(ServerPlayer sp) {
      double step = 0.5;
      int maxSteps = 60;
      double startX = sp.getX();
      double startY = sp.getY();
      double startZ = sp.getZ();
      Vec3 deltaMovement = sp.getDeltaMovement();
      Level level = sp.level();
      int worldCeil = level.getMaxBuildHeight() - 2;
      AABB currentBox = sp.getBoundingBox().deflate(1.0E-6);
      if (level.noCollision(sp, currentBox)) {
         sp.fallDistance = 0.0F;
      } else {
         for (int i = 0; i < 60; i++) {
            double targetY = Math.min(startY + i * 0.5, worldCeil);
            double dy = targetY - sp.getY();
            AABB moved = sp.getBoundingBox().move(0.0, dy, 0.0);
            if (level.noCollision(sp, moved.deflate(1.0E-6))) {
               sp.teleportTo(startX, targetY, startZ);
               sp.setDeltaMovement(deltaMovement);
               sp.fallDistance = 0.0F;
               return;
            }
         }

         int topY = level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, Mth.floor(startX), Mth.floor(startZ));
         double safeY = Math.min(topY + 0.1, worldCeil);
         sp.teleportTo(startX, safeY, startZ);
         sp.setDeltaMovement(deltaMovement);
         sp.fallDistance = 0.0F;
      }
   }

   private static void enforcePhaseBlacklist(ServerPlayer sp, UUID id) {
      AABB bounds = sp.getBoundingBox().deflate(1.0E-6);
      if (!intersectsBlacklistedBlock(sp.level(), bounds)) {
         LAST_SAFE_PHASE_POSITIONS.put(id, new WispModeEffect.SafePhasePosition(sp.level().dimension(), sp.position()));
      } else {
         WispModeEffect.SafePhasePosition safePosition = LAST_SAFE_PHASE_POSITIONS.get(id);
         if (safePosition != null && safePosition.dimension().equals(sp.level().dimension())) {
            Vec3 pos = safePosition.position();
            AABB safeBounds = sp.getBoundingBox().move(pos.x - sp.getX(), pos.y - sp.getY(), pos.z - sp.getZ()).deflate(1.0E-6);
            if (!intersectsBlacklistedBlock(sp.level(), safeBounds)) {
               sp.teleportTo(pos.x, pos.y, pos.z);
               sp.setDeltaMovement(Vec3.ZERO);
               sp.fallDistance = 0.0F;
               return;
            }
         }

         popOutAbove(sp);
      }
   }

   private record PhaseAccessSnapshot(ResourceKey<Level> dimension, Set<Long> blockedChunks) {
   }

   private record SafePhasePosition(ResourceKey<Level> dimension, Vec3 position) {
   }
}
