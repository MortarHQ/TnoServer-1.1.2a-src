package org.popcraft.chunky.platform;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.visitors.CollectFields;
import net.minecraft.nbt.visitors.FieldSelector;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Unit;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.storage.LevelResource;
import org.popcraft.chunky.platform.util.Location;
import org.popcraft.chunky.util.Input;

public class NeoForgeWorld implements World {
   private static final int TICKING_LOAD_DURATION = Input.tryInteger(System.getProperty("chunky.tickingLoadDuration")).orElse(0);
   private static final TicketType<Unit> CHUNKY = TicketType.create("chunky", (unit, unit2) -> 0);
   private static final TicketType<Unit> CHUNKY_TICKING = TicketType.create("%s_ticking".formatted("chunky"), (unit, unit2) -> 0, TICKING_LOAD_DURATION * 20);
   private static final boolean UPDATE_CHUNK_NBT = Boolean.getBoolean("chunky.updateChunkNbt");
   private final ServerLevel world;
   private final Border worldBorder;

   public NeoForgeWorld(ServerLevel world) {
      this.world = world;
      this.worldBorder = new NeoForgeBorder(world.getWorldBorder());
   }

   @Override
   public String getName() {
      return this.world.dimension().location().toString();
   }

   @Override
   public String getKey() {
      return this.getName();
   }

   @Override
   public CompletableFuture<Boolean> isChunkGenerated(int x, int z) {
      if (Thread.currentThread() != this.world.getServer().getRunningThread()) {
         return CompletableFuture.<CompletableFuture<Boolean>>supplyAsync(() -> this.isChunkGenerated(x, z), this.world.getServer())
            .thenCompose(Function.identity());
      }

      ChunkPos chunkPos = new ChunkPos(x, z);
      ServerChunkCache serverChunkCache = this.world.getChunkSource();
      ChunkMap chunkStorage = serverChunkCache.chunkMap;
      ChunkHolder loadedChunkHolder = chunkStorage.getVisibleChunkIfPresent(chunkPos.toLong());
      if (loadedChunkHolder != null && loadedChunkHolder.getLatestStatus() == ChunkStatus.FULL) {
         return CompletableFuture.completedFuture(true);
      }

      if (UPDATE_CHUNK_NBT) {
         return chunkStorage.readChunk(chunkPos)
            .thenApply(
               optionalNbt -> optionalNbt.filter(chunkNbt -> chunkNbt.contains("Status", 8))
                  .map(chunkNbt -> chunkNbt.getString("Status"))
                  .map(status -> "minecraft:full".equals(status) || "full".equals(status))
                  .orElse(false)
            );
      }

      FieldSelector statusSelector = new FieldSelector(StringTag.TYPE, "Status");
      CollectFields statusCollector = new CollectFields(new FieldSelector[]{statusSelector});
      return serverChunkCache.chunkScanner().scanChunk(chunkPos, statusCollector).thenApply(ignored -> {
         if (!(statusCollector.getResult() instanceof CompoundTag chunkNbt)) {
            return false;
         } else {
            String status = chunkNbt.getString("Status");
            return "minecraft:full".equals(status) || "full".equals(status);
         }
      });
   }

   @Override
   public CompletableFuture<Void> getChunkAtAsync(int x, int z) {
      if (Thread.currentThread() != this.world.getServer().getRunningThread()) {
         return CompletableFuture.<CompletableFuture<Void>>supplyAsync(() -> this.getChunkAtAsync(x, z), this.world.getServer())
            .thenCompose(Function.identity());
      }

      ChunkPos chunkPos = new ChunkPos(x, z);
      ServerChunkCache serverChunkCache = this.world.getChunkSource();
      serverChunkCache.addRegionTicket(CHUNKY, chunkPos, 0, Unit.INSTANCE);
      if (TICKING_LOAD_DURATION > 0) {
         serverChunkCache.addRegionTicket(CHUNKY_TICKING, chunkPos, 1, Unit.INSTANCE);
      }

      CompletableFuture<Void> chunkFuture = CompletableFuture.allOf(this.world.getChunkSource().getChunkFutureMainThread(x, z, ChunkStatus.FULL, true));
      chunkFuture.whenCompleteAsync((ignored, throwable) -> serverChunkCache.removeRegionTicket(CHUNKY, chunkPos, 0, Unit.INSTANCE), this.world.getServer());
      return chunkFuture;
   }

   @Override
   public UUID getUUID() {
      throw new UnsupportedOperationException();
   }

   @Override
   public int getSeaLevel() {
      return this.world.getSeaLevel();
   }

   @Override
   public Location getSpawn() {
      BlockPos pos = this.world.getSharedSpawnPos();
      float rot = this.world.getSharedSpawnAngle();
      return new Location(this, pos.getX(), pos.getY(), pos.getZ(), rot, 0.0F);
   }

   @Override
   public Border getWorldBorder() {
      return this.worldBorder;
   }

   @Override
   public int getElevation(int x, int z) {
      int height = this.world.getHeight(Types.MOTION_BLOCKING, x, z) + 1;
      int logicalHeight = this.world.getLogicalHeight();
      if (height >= logicalHeight) {
         MutableBlockPos pos = new MutableBlockPos(x, logicalHeight, z);
         int air = 0;

         while (pos.getY() > this.world.getMinBuildHeight()) {
            pos = pos.move(Direction.DOWN);
            BlockState blockState = this.world.getBlockState(pos);
            if (blockState.isSolid() && air > 1) {
               return pos.getY() + 1;
            }

            air = blockState.isAir() ? air + 1 : 0;
         }
      }

      return height;
   }

   @Override
   public int getMaxElevation() {
      return this.world.getLogicalHeight();
   }

   @Override
   public void playEffect(Player player, String effect) {
      Location location = player.getLocation();
      BlockPos pos = BlockPos.containing(location.getX(), location.getY(), location.getZ());
      Input.tryInteger(effect).ifPresent(eventId -> this.world.levelEvent(eventId, pos, 0));
   }

   @Override
   public void playSound(Player player, String sound) {
      Location location = player.getLocation();
      this.world
         .getServer()
         .registryAccess()
         .registry(Registries.SOUND_EVENT)
         .flatMap(soundEventRegistry -> soundEventRegistry.getOptional(ResourceLocation.tryParse(sound)))
         .ifPresent(soundEvent -> this.world.playSound(null, location.getX(), location.getY(), location.getZ(), soundEvent, SoundSource.MASTER, 2.0F, 1.0F));
   }

   @Override
   public Optional<Path> getDirectory(String name) {
      if (name == null) {
         return Optional.empty();
      }

      Path directory = DimensionType.getStorageFolder(this.world.dimension(), this.world.getServer().getWorldPath(LevelResource.ROOT))
         .normalize()
         .resolve(name);
      return Files.exists(directory) ? Optional.of(directory) : Optional.empty();
   }

   public ServerLevel getWorld() {
      return this.world;
   }
}
