package com.tno_tensura_extras.util;

import com.tno_tensura_extras.TensuraExtras;
import com.tno_tensura_extras.skill.unique.TimeTravelerSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import java.io.IOException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelResource;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;

public final class TimeTravelManager {
   public static final ResourceLocation SKILL_ID = ResourceLocation.fromNamespaceAndPath("tno_tensura_extras", "time_traveler");
   private static final String SEED_TAG = "time_traveler_seed";
   private static final Set<String> RESET_DIRECTORY_NAMES = Set.of("region", "entities", "poi", "data");
   private static final Path STARTER_KIT_DATA = Path.of("data", "starterkit");
   private static final Set<Path> PRESERVED_SAVED_DATA = Set.of(
      Path.of("data", "sophisticatedbackpacks.dat"),
      Path.of("data", "sophisticatedbackpacks.dat_old"),
      Path.of("data", "sophisticatedbackpacks_backpack_templates.dat"),
      Path.of("data", "sophisticatedbackpacks_backpack_templates.dat_old"),
      Path.of("data", "sophisticatedcore_linked_storage_groups.dat"),
      Path.of("data", "sophisticatedcore_linked_storage_groups.dat_old"),
      Path.of("data", "irons_spellbooks_data.dat"),
      Path.of("data", "irons_spellbooks_data.dat_old")
   );
   private static final Path POCKET_DIMENSION_DIRECTORY = Path.of("dimensions", "irons_spellbooks", "pocket_dimension");
   private static final Object STATE_LOCK = new Object();
   private static volatile TimeTravelManager.PendingReset pending;

   private TimeTravelManager() {
   }

   public static void recordSeed(ManasSkillInstance instance, LivingEntity entity) {
      if (instance != null && !entity.level().isClientSide() && entity.level() instanceof ServerLevel level) {
         CompoundTag tag = instance.getOrCreateTag();
         if (!tag.contains("time_traveler_seed", 4)) {
            tag.putLong("time_traveler_seed", level.getSeed());
            instance.markDirty();
         }
      }
   }

   public static void recordSeed(LivingEntity entity) {
      if (entity != null && !entity.level().isClientSide()) {
         try {
            SkillAPI.getSkillsFrom(entity).getSkill(SKILL_ID).ifPresent(instance -> recordSeed(instance, entity));
         } catch (RuntimeException var2) {
         }
      }
   }

   public static boolean request(ManasSkillInstance instance, LivingEntity entity) {
      if (!(entity instanceof ServerPlayer player && entity.level() instanceof ServerLevel level)) {
         return false;
      } else if (instance != null && !instance.onCoolDown(0)) {
         MinecraftServer server = level.getServer();
         if (isAllowedServer(server) && pending == null) {
            recordSeed(instance, entity);
            long seed = level.getSeed();
            if (instance != null) {
               CompoundTag skillTag = instance.getTag();
               if (skillTag != null && skillTag.contains("time_traveler_seed", 4)) {
                  seed = skillTag.getLong("time_traveler_seed");
               }
            }

            String levelId;
            Path worldRoot;
            try {
               worldRoot = server.getWorldPath(LevelResource.ROOT).toAbsolutePath().normalize();
               if (!isValidWorldRoot(worldRoot)) {
                  return false;
               }

               Path fileName = worldRoot.getFileName();
               if (fileName == null || fileName.toString().isBlank()) {
                  return false;
               }

               levelId = fileName.toString();
            } catch (RuntimeException exception) {
               TensuraExtras.LOGGER.error("Unable to locate the world for Time Traveler", exception);
               return false;
            }

            TimeTravelManager.PendingReset reset = new TimeTravelManager.PendingReset(
               server,
               worldRoot,
               levelId,
               player.getUUID(),
               player.level().dimension().location(),
               player.getX(),
               player.getY(),
               player.getZ(),
               player.getYRot(),
               player.getXRot(),
               seed
            );
            synchronized (STATE_LOCK) {
               if (pending != null) {
                  return false;
               }

               pending = reset;
            }

            try {
               LooterManager.clearActiveShadowsForWorldReset(player);
               TimeTravelerSkill.applySuccessfulUse(instance, entity, 0);
               if (!server.saveEverything(false, true, false)) {
                  clearPending(reset);
                  return false;
               } else {
                  reset.haltRequested = true;
                  server.halt(false);
                  return true;
               }
            } catch (RuntimeException exception) {
               clearPending(reset);
               TensuraExtras.LOGGER.error("Unable to save the world for Time Traveler", exception);
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public static boolean request(LivingEntity entity) {
      if (entity == null) {
         return false;
      }

      try {
         return SkillAPI.getSkillsFrom(entity).getSkill(SKILL_ID).map(instance -> request(instance, entity)).orElse(false);
      } catch (RuntimeException ignored) {
         return false;
      }
   }

   private static boolean isAllowedServer(MinecraftServer server) {
      return server != null
         && server.isSingleplayer()
         && !server.isDedicatedServer()
         && !server.isPublished()
         && server.getPlayerList() != null
         && server.getPlayerList().getPlayerCount() == 1;
   }

   public static boolean isPending() {
      return pending != null;
   }

   public static boolean isResetReady() {
      TimeTravelManager.PendingReset reset = pending;
      return reset != null && reset.resetReady;
   }

   public static boolean isResetFailed() {
      TimeTravelManager.PendingReset reset = pending;
      return reset != null && reset.resetFailed;
   }

   public static String getPendingLevelId() {
      TimeTravelManager.PendingReset reset = pending;
      return reset == null ? null : reset.levelId;
   }

   public static void markClientReopenStarted() {
      TimeTravelManager.PendingReset reset = pending;
      if (reset != null) {
         reset.clientReopenStarted = true;
      }
   }

   public static void completeClientReset() {
      synchronized (STATE_LOCK) {
         pending = null;
      }
   }

   public static void cancelClientReset() {
      completeClientReset();
   }

   public static void onServerStopping(ServerStoppingEvent event) {
      TimeTravelManager.PendingReset reset = pending;
      if (reset != null && reset.server == event.getServer() && !reset.haltRequested) {
         completeClientReset();
      }
   }

   public static void onServerStopped(ServerStoppedEvent event) {
      TimeTravelManager.PendingReset reset = pending;
      if (reset != null && reset.server == event.getServer() && reset.haltRequested) {
         boolean success = resetWorld(reset.worldRoot, reset.seed);
         reset.resetReady = success;
         reset.resetFailed = !success;
         if (!success) {
            TensuraExtras.LOGGER.error("Time Traveler could not reset world contents at {}", reset.worldRoot);
         }
      }
   }

   public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer serverPlayer) {
         recordSeed(serverPlayer);
         TimeTravelManager.PendingReset reset = pending;
         if (reset != null && reset.resetReady && reset.playerId.equals(serverPlayer.getUUID())) {
            Path currentRoot;
            try {
               currentRoot = serverPlayer.server.getWorldPath(LevelResource.ROOT).toAbsolutePath().normalize();
            } catch (RuntimeException exception) {
               return;
            }

            if (currentRoot.equals(reset.worldRoot)) {
               ResourceKey<Level> dimension = ResourceKey.create(Registries.DIMENSION, reset.dimension);
               ServerLevel target = serverPlayer.server.getLevel(dimension);
               if (target != null) {
                  serverPlayer.teleportTo(target, reset.x, reset.y, reset.z, reset.yRot, reset.xRot);
               }

               LooterManager.clearActiveShadowsForWorldReset(serverPlayer);
               reset.playerRestored = true;
            }
         }
      }
   }

   private static boolean resetWorld(Path worldRoot, long seed) {
      if (!isValidWorldRoot(worldRoot)) {
         return false;
      }

      List<Path> targets = new ArrayList<>();
      addResetDirectories(worldRoot, targets);
      Path dimensions = worldRoot.resolve("dimensions");
      if (isSafeDirectory(worldRoot, dimensions)) {
         collectResetDirectories(worldRoot, dimensions, 0, targets);
      }

      Path nether = worldRoot.resolve("DIM-1");
      if (isSafeDirectory(worldRoot, nether)) {
         collectResetDirectories(worldRoot, nether, 0, targets);
      }

      Path end = worldRoot.resolve("DIM1");
      if (isSafeDirectory(worldRoot, end)) {
         collectResetDirectories(worldRoot, end, 0, targets);
      }

      targets = targets.stream().distinct().sorted(Comparator.comparingInt(Path::getNameCount)).toList();

      try {
         for (Path target : targets) {
            clearDirectoryContents(worldRoot, target);
         }

         resetLevelData(worldRoot, seed);
         return true;
      } catch (IOException | RuntimeException exception) {
         TensuraExtras.LOGGER.error("Failed to clear Time Traveler world data at {}", worldRoot, exception);
         return false;
      }
   }

   private static void addResetDirectories(Path worldRoot, List<Path> targets) {
      for (String name : RESET_DIRECTORY_NAMES) {
         Path candidate = worldRoot.resolve(name);
         if (isSafeDirectory(worldRoot, candidate)) {
            targets.add(candidate);
         }
      }
   }

   private static void collectResetDirectories(Path worldRoot, Path directory, int depth, List<Path> targets) {
      if (depth <= 8 && isSafeDirectory(worldRoot, directory) && !isPreservedDimension(worldRoot, directory)) {
         try (DirectoryStream<Path> entries = Files.newDirectoryStream(directory)) {
            for (Path child : entries) {
               if (!Files.isSymbolicLink(child) && Files.isDirectory(child, LinkOption.NOFOLLOW_LINKS)) {
                  String name = child.getFileName().toString();
                  if (RESET_DIRECTORY_NAMES.contains(name)) {
                     targets.add(child.toAbsolutePath().normalize());
                  } else {
                     collectResetDirectories(worldRoot, child, depth + 1, targets);
                  }
               }
            }
         } catch (IOException exception) {
            throw new TimeTravelManager.WorldResetException(exception);
         }
      }
   }

   private static void clearDirectoryContents(final Path worldRoot, Path directory) throws IOException {
      final Path normalized = directory.toAbsolutePath().normalize();
      if (!isSafeDirectory(worldRoot, normalized)) {
         throw new IOException("Unsafe reset directory: " + normalized);
      }

      if (!isPreservedDimension(worldRoot, normalized)) {
         final Path preservedStarterKit = worldRoot.resolve(STARTER_KIT_DATA).toAbsolutePath().normalize();
         Files.walkFileTree(normalized, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
               return TimeTravelManager.isUnder(dir, preservedStarterKit) ? FileVisitResult.SKIP_SUBTREE : FileVisitResult.CONTINUE;
            }

            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
               Path relative = worldRoot.toAbsolutePath().normalize().relativize(file.toAbsolutePath().normalize());
               if (!TimeTravelManager.isUnder(file, preservedStarterKit) && !TimeTravelManager.PRESERVED_SAVED_DATA.contains(relative)) {
                  Files.deleteIfExists(file);
               }

               return FileVisitResult.CONTINUE;
            }

            public FileVisitResult postVisitDirectory(Path dir, IOException exception) throws IOException {
               if (exception != null) {
                  throw exception;
               }

               if (!dir.equals(normalized) && !TimeTravelManager.isUnder(dir, preservedStarterKit)) {
                  Files.deleteIfExists(dir);
               }

               return FileVisitResult.CONTINUE;
            }
         });
      }
   }

   private static boolean isPreservedDimension(Path worldRoot, Path directory) {
      return isUnder(directory, worldRoot.resolve(POCKET_DIMENSION_DIRECTORY));
   }

   private static boolean isUnder(Path path, Path root) {
      Path normalizedPath = path.toAbsolutePath().normalize();
      return normalizedPath.equals(root) || normalizedPath.startsWith(root);
   }

   private static void resetLevelData(Path worldRoot, long seed) throws IOException {
      Path levelDat = worldRoot.resolve("level.dat");
      if (!Files.isRegularFile(levelDat, LinkOption.NOFOLLOW_LINKS)) {
         throw new IOException("Missing level.dat");
      }

      CompoundTag root = NbtIo.readCompressed(levelDat, NbtAccounter.unlimitedHeap());
      if (!root.contains("Data", 10)) {
         throw new IOException("Invalid level.dat Data tag");
      }

      CompoundTag data = root.getCompound("Data");
      data.putLong("Time", 0L);
      data.putLong("DayTime", 0L);
      if (data.contains("WorldGenSettings", 10)) {
         CompoundTag worldGenSettings = data.getCompound("WorldGenSettings");
         worldGenSettings.putLong("seed", seed);
         data.put("WorldGenSettings", worldGenSettings);
      }

      root.put("Data", data);
      Path temporary = Files.createTempFile(worldRoot, "level.dat", ".time_traveler");

      try {
         NbtIo.writeCompressed(root, temporary);

         try {
            Files.move(temporary, levelDat, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
         } catch (AtomicMoveNotSupportedException exception) {
            Files.move(temporary, levelDat, StandardCopyOption.REPLACE_EXISTING);
         }
      } finally {
         Files.deleteIfExists(temporary);
      }
   }

   private static boolean isValidWorldRoot(Path root) {
      return root != null
         && root.getParent() != null
         && Files.isDirectory(root, LinkOption.NOFOLLOW_LINKS)
         && Files.isRegularFile(root.resolve("level.dat"), LinkOption.NOFOLLOW_LINKS);
   }

   private static boolean isSafeDirectory(Path worldRoot, Path candidate) {
      if (candidate != null && !Files.isSymbolicLink(candidate)) {
         Path normalizedRoot = worldRoot.toAbsolutePath().normalize();
         Path normalizedCandidate = candidate.toAbsolutePath().normalize();
         return !normalizedRoot.equals(normalizedCandidate)
            && normalizedCandidate.startsWith(normalizedRoot)
            && Files.isDirectory(normalizedCandidate, LinkOption.NOFOLLOW_LINKS);
      } else {
         return false;
      }
   }

   private static void clearPending(TimeTravelManager.PendingReset reset) {
      synchronized (STATE_LOCK) {
         if (pending == reset) {
            pending = null;
         }
      }
   }

   private static final class PendingReset {
      private final MinecraftServer server;
      private final Path worldRoot;
      private final String levelId;
      private final UUID playerId;
      private final ResourceLocation dimension;
      private final double x;
      private final double y;
      private final double z;
      private final float yRot;
      private final float xRot;
      private final long seed;
      private volatile boolean haltRequested;
      private volatile boolean resetReady;
      private volatile boolean resetFailed;
      private volatile boolean clientReopenStarted;
      private volatile boolean playerRestored;

      private PendingReset(
         MinecraftServer server,
         Path worldRoot,
         String levelId,
         UUID playerId,
         ResourceLocation dimension,
         double x,
         double y,
         double z,
         float yRot,
         float xRot,
         long seed
      ) {
         this.server = server;
         this.worldRoot = worldRoot;
         this.levelId = levelId;
         this.playerId = playerId;
         this.dimension = dimension;
         this.x = x;
         this.y = y;
         this.z = z;
         this.yRot = yRot;
         this.xRot = xRot;
         this.seed = seed;
      }
   }

   private static final class WorldResetException extends RuntimeException {
      private WorldResetException(IOException cause) {
         super(cause);
      }
   }
}
