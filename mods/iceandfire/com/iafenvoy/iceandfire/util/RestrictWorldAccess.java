package com.iafenvoy.iceandfire.util;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.ticks.LevelTickAccess;
import org.jetbrains.annotations.Nullable;

public class RestrictWorldAccess implements ServerLevelAccessor {
   private final ServerLevelAccessor origin;
   private final Predicate<BlockPos> checker;

   public RestrictWorldAccess(ServerLevelAccessor origin, Predicate<BlockPos> checker) {
      this.origin = origin;
      this.checker = checker;
   }

   public ServerLevel getLevel() {
      return this.origin.getLevel();
   }

   public long nextSubTickCount() {
      return this.origin.nextSubTickCount();
   }

   public LevelTickAccess<Block> getBlockTicks() {
      return this.origin.getBlockTicks();
   }

   public LevelTickAccess<Fluid> getFluidTicks() {
      return this.origin.getFluidTicks();
   }

   public LevelData getLevelData() {
      return this.origin.getLevelData();
   }

   public DifficultyInstance getCurrentDifficultyAt(BlockPos pos) {
      return this.origin.getCurrentDifficultyAt(pos);
   }

   @Nullable
   public MinecraftServer getServer() {
      return this.origin.getServer();
   }

   public ChunkSource getChunkSource() {
      return this.origin.getChunkSource();
   }

   public RandomSource getRandom() {
      return this.origin.getRandom();
   }

   public void playSound(@Nullable Player except, BlockPos pos, SoundEvent sound, SoundSource category, float volume, float pitch) {
      this.origin.playSound(except, pos, sound, category, volume, pitch);
   }

   public void addParticle(ParticleOptions parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
      this.origin.addParticle(parameters, x, y, z, velocityX, velocityY, velocityZ);
   }

   public void levelEvent(@Nullable Player player, int eventId, BlockPos pos, int data) {
      this.origin.levelEvent(player, eventId, pos, data);
   }

   public void gameEvent(Holder<GameEvent> event, Vec3 emitterPos, Context emitter) {
      this.origin.gameEvent(event, emitterPos, emitter);
   }

   public float getShade(Direction direction, boolean shaded) {
      return this.origin.getShade(direction, shaded);
   }

   public LevelLightEngine getLightEngine() {
      return this.origin.getLightEngine();
   }

   public WorldBorder getWorldBorder() {
      return this.origin.getWorldBorder();
   }

   @Nullable
   public BlockEntity getBlockEntity(BlockPos pos) {
      return !this.checker.test(pos) ? null : this.origin.getBlockEntity(pos);
   }

   public BlockState getBlockState(BlockPos pos) {
      return !this.checker.test(pos) ? Blocks.AIR.defaultBlockState() : this.origin.getBlockState(pos);
   }

   public FluidState getFluidState(BlockPos pos) {
      return !this.checker.test(pos) ? Fluids.EMPTY.defaultFluidState() : this.origin.getFluidState(pos);
   }

   public List<Entity> getEntities(@Nullable Entity except, AABB box, Predicate<? super Entity> predicate) {
      return this.origin.getEntities(except, box, predicate);
   }

   public <T extends Entity> List<T> getEntities(EntityTypeTest<Entity, T> filter, AABB box, Predicate<? super T> predicate) {
      return this.origin.getEntities(filter, box, predicate);
   }

   public List<? extends Player> players() {
      return this.origin.players();
   }

   public boolean setBlock(BlockPos pos, BlockState state, int flags, int maxUpdateDepth) {
      return !this.checker.test(pos) ? false : this.origin.setBlock(pos, state, flags, maxUpdateDepth);
   }

   public boolean removeBlock(BlockPos pos, boolean move) {
      return this.origin.removeBlock(pos, move);
   }

   public boolean destroyBlock(BlockPos pos, boolean drop, @Nullable Entity breakingEntity, int maxUpdateDepth) {
      return this.origin.destroyBlock(pos, drop, breakingEntity, maxUpdateDepth);
   }

   public boolean isStateAtPosition(BlockPos pos, Predicate<BlockState> state) {
      return this.origin.isStateAtPosition(pos, state);
   }

   public boolean isFluidAtPosition(BlockPos pos, Predicate<FluidState> state) {
      return this.origin.isFluidAtPosition(pos, state);
   }

   @Nullable
   public ChunkAccess getChunk(int chunkX, int chunkZ, ChunkStatus leastStatus, boolean create) {
      return this.origin.getChunk(chunkX, chunkZ, leastStatus, create);
   }

   public int getHeight(Types heightmap, int x, int z) {
      return !this.checker.test(new BlockPos(x, 0, z)) ? 64 : this.origin.getHeight(heightmap, x, z);
   }

   public int getSkyDarken() {
      return this.origin.getSkyDarken();
   }

   public BiomeManager getBiomeManager() {
      return this.origin.getBiomeManager();
   }

   public Holder<Biome> getUncachedNoiseBiome(int biomeX, int biomeY, int biomeZ) {
      return this.origin.getUncachedNoiseBiome(biomeX, biomeY, biomeZ);
   }

   public boolean isClientSide() {
      return this.origin.isClientSide();
   }

   @Deprecated
   public int getSeaLevel() {
      return this.origin.getSeaLevel();
   }

   public DimensionType dimensionType() {
      return this.origin.dimensionType();
   }

   public RegistryAccess registryAccess() {
      return this.origin.registryAccess();
   }

   public FeatureFlagSet enabledFeatures() {
      return this.origin.enabledFeatures();
   }

   public boolean addFreshEntity(Entity entity) {
      return !this.checker.test(entity.blockPosition()) ? false : this.origin.addFreshEntity(entity);
   }
}
