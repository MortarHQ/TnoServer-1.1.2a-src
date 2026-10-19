package com.github.b4ndithelps.tennogamenolife.ability.skill.ultimate;

import com.github.b4ndithelps.tennogamenolife.ability.skill.intrinsic.FourthProtectionManager;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.data.TensuraBlockTags;
import io.github.manasmods.tensura.event.TensuraSkillEvents;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillGriefEvent;
import io.github.manasmods.tensura.world.TensuraGameRules;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;

@EventBusSubscriber(modid = "tennogamenolife")
public final class TengekiProgressiveDestruction {
   private static final int CELL_SIZE = 2;
   private static final int GLOBAL_CELLS_PER_TICK = 512;
   private static final int GLOBAL_COLUMNS_PER_TICK = 96;
   private static final int SHOCKWAVE_STRUCTURE_HEIGHT = 64;
   private static final int SURFACE_SEARCH_DEPTH = 128;
   private static final List<TengekiProgressiveDestruction.DestructionTask> ACTIVE_TASKS = new ArrayList<>();
   private static final List<TengekiProgressiveDestruction.AftermathTask> ACTIVE_AFTERMATH_TASKS = new ArrayList<>();

   private TengekiProgressiveDestruction() {
   }

   public static void start(
      ServerLevel level, Vec3 center, float destructionRadius, float aftermathRadius, LivingEntity owner, ManasSkillInstance skillInstance
   ) {
      if (!(destructionRadius <= 0.0F)
         && !(aftermathRadius <= 0.0F)
         && !DestructiveSkillSafety.shouldPreventTerrainDamage()
         && TensuraGameRules.canSkillGrief(level)) {
         ACTIVE_TASKS.add(new TengekiProgressiveDestruction.DestructionTask(level, center, destructionRadius, aftermathRadius, owner, skillInstance));
      }
   }

   public static void startSmoothCrater(
      ServerLevel level, Vec3 center, float destructionRadius, float craterDepth, float aftermathRadius, LivingEntity owner, ManasSkillInstance skillInstance
   ) {
      if (!(destructionRadius <= 0.0F)
         && !(craterDepth <= 0.0F)
         && !(aftermathRadius <= 0.0F)
         && !DestructiveSkillSafety.shouldPreventTerrainDamage()
         && TensuraGameRules.canSkillGrief(level)) {
         ACTIVE_TASKS.add(
            new TengekiProgressiveDestruction.DestructionTask(
               level, center, destructionRadius, aftermathRadius, owner, skillInstance, true, Math.min(craterDepth, destructionRadius)
            )
         );
      }
   }

   @SubscribeEvent
   public static void onServerTick(Post event) {
      if ((!ACTIVE_TASKS.isEmpty() || !ACTIVE_AFTERMATH_TASKS.isEmpty()) && event.hasTime()) {
         if (!ACTIVE_TASKS.isEmpty()) {
            int remainingCells = 512;
            int remainingTasks = ACTIVE_TASKS.size();
            Iterator<TengekiProgressiveDestruction.DestructionTask> iterator = ACTIVE_TASKS.iterator();

            while (iterator.hasNext() && remainingCells > 0) {
               TengekiProgressiveDestruction.DestructionTask task = iterator.next();
               int taskBudget = Math.max(1, remainingCells / remainingTasks);
               int processed = task.tick(taskBudget);
               remainingCells -= processed;
               remainingTasks--;
               if (task.isComplete()) {
                  iterator.remove();
                  if (!task.wasCancelled()) {
                     ACTIVE_AFTERMATH_TASKS.add(task.createAftermath());
                  }
               }
            }
         }

         if (!ACTIVE_AFTERMATH_TASKS.isEmpty()) {
            int remainingColumns = 96;
            int remainingTasks = ACTIVE_AFTERMATH_TASKS.size();
            Iterator<TengekiProgressiveDestruction.AftermathTask> iterator = ACTIVE_AFTERMATH_TASKS.iterator();

            while (iterator.hasNext() && remainingColumns > 0) {
               TengekiProgressiveDestruction.AftermathTask task = iterator.next();
               int taskBudget = Math.max(1, remainingColumns / remainingTasks);
               int processed = task.tick(taskBudget);
               remainingColumns -= processed;
               remainingTasks--;
               if (task.isComplete()) {
                  iterator.remove();
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onServerStopping(ServerStoppingEvent event) {
      ACTIVE_TASKS.clear();
      ACTIVE_AFTERMATH_TASKS.clear();
   }

   private static List<TengekiProgressiveDestruction.Cell> buildCells(Vec3 center, float radius, boolean smoothCrater, float craterDepth) {
      int minX = (int)Math.floor(center.x - radius);
      int minY = smoothCrater ? (int)Math.floor(center.y - craterDepth) - 1 : (int)Math.floor(center.y - radius);
      int minZ = (int)Math.floor(center.z - radius);
      int maxX = (int)Math.floor(center.x + radius);
      int maxY = smoothCrater ? (int)Math.floor(center.y) : (int)Math.floor(center.y + radius);
      int maxZ = (int)Math.floor(center.z + radius);
      double radiusSquared = radius * radius;
      List<TengekiProgressiveDestruction.Cell> cells = new ArrayList<>();

      for (int x = minX; x <= maxX; x += 2) {
         int cellMaxX = Math.min(x + 2 - 1, maxX);

         for (int y = minY; y <= maxY; y += 2) {
            int cellMaxY = Math.min(y + 2 - 1, maxY);

            for (int z = minZ; z <= maxZ; z += 2) {
               int cellMaxZ = Math.min(z + 2 - 1, maxZ);
               if (distanceToCellSquared(center, x, y, z, cellMaxX, cellMaxY, cellMaxZ) <= radiusSquared) {
                  cells.add(
                     new TengekiProgressiveDestruction.Cell(
                        x, y, z, cellMaxX, cellMaxY, cellMaxZ, distanceToCellCenterSquared(center, x, y, z, cellMaxX, cellMaxY, cellMaxZ)
                     )
                  );
               }
            }
         }
      }

      cells.sort(Comparator.comparingDouble(TengekiProgressiveDestruction.Cell::distanceSquared));
      return cells;
   }

   private static double distanceToCellSquared(Vec3 center, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
      double dx = distanceToRange(center.x, minX + 0.5, maxX + 0.5);
      double dy = distanceToRange(center.y, minY + 0.5, maxY + 0.5);
      double dz = distanceToRange(center.z, minZ + 0.5, maxZ + 0.5);
      return dx * dx + dy * dy + dz * dz;
   }

   private static double distanceToRange(double value, double min, double max) {
      if (value < min) {
         return min - value;
      } else {
         return value > max ? value - max : 0.0;
      }
   }

   private static double distanceToCellCenterSquared(Vec3 center, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
      double dx = (minX + maxX + 1) * 0.5 - center.x;
      double dy = (minY + maxY + 1) * 0.5 - center.y;
      double dz = (minZ + maxZ + 1) * 0.5 - center.z;
      return dx * dx + dy * dy + dz * dz;
   }

   private static List<TengekiProgressiveDestruction.Column> buildColumns(Vec3 center, float radius) {
      int minX = (int)Math.floor(center.x - radius);
      int minZ = (int)Math.floor(center.z - radius);
      int maxX = (int)Math.ceil(center.x + radius);
      int maxZ = (int)Math.ceil(center.z + radius);
      double radiusSquared = radius * radius;
      List<TengekiProgressiveDestruction.Column> columns = new ArrayList<>();

      for (int x = minX; x <= maxX; x++) {
         double dx = x + 0.5 - center.x;

         for (int z = minZ; z <= maxZ; z++) {
            double dz = z + 0.5 - center.z;
            double distanceSquared = dx * dx + dz * dz;
            if (distanceSquared <= radiusSquared) {
               columns.add(new TengekiProgressiveDestruction.Column(x, z, distanceSquared));
            }
         }
      }

      columns.sort(Comparator.comparingDouble(TengekiProgressiveDestruction.Column::distanceSquared));
      return columns;
   }

   private static boolean isScorchableSurface(BlockState state) {
      return state.is(Blocks.GRASS_BLOCK)
         || state.is(Blocks.DIRT)
         || state.is(Blocks.COARSE_DIRT)
         || state.is(Blocks.PODZOL)
         || state.is(Blocks.MYCELIUM)
         || state.is(Blocks.ROOTED_DIRT)
         || state.is(Blocks.DIRT_PATH)
         || state.is(Blocks.SAND)
         || state.is(Blocks.RED_SAND)
         || state.is(Blocks.GRAVEL)
         || state.is(Blocks.CLAY)
         || state.is(Blocks.MUD)
         || state.is(Blocks.MUDDY_MANGROVE_ROOTS)
         || state.is(Blocks.STONE)
         || state.is(Blocks.DEEPSLATE)
         || state.is(Blocks.TUFF)
         || state.is(Blocks.CALCITE)
         || state.is(Blocks.TERRACOTTA)
         || state.is(Blocks.NETHERRACK)
         || state.is(Blocks.END_STONE);
   }

   private static boolean isNaturalGroundSurface(BlockState state) {
      return state.is(Blocks.GRASS_BLOCK)
         || state.is(Blocks.DIRT)
         || state.is(Blocks.COARSE_DIRT)
         || state.is(Blocks.PODZOL)
         || state.is(Blocks.MYCELIUM)
         || state.is(Blocks.ROOTED_DIRT)
         || state.is(Blocks.DIRT_PATH)
         || state.is(Blocks.SAND)
         || state.is(Blocks.RED_SAND)
         || state.is(Blocks.GRAVEL)
         || state.is(Blocks.CLAY)
         || state.is(Blocks.MUD)
         || state.is(Blocks.MUDDY_MANGROVE_ROOTS)
         || state.is(Blocks.NETHERRACK)
         || state.is(Blocks.END_STONE);
   }

   private static boolean isClearableSurfaceVegetation(BlockState state) {
      return state.is(BlockTags.SMALL_FLOWERS)
         || state.is(BlockTags.TALL_FLOWERS)
         || state.is(BlockTags.SAPLINGS)
         || state.is(Blocks.SHORT_GRASS)
         || state.is(Blocks.TALL_GRASS)
         || state.is(Blocks.FERN)
         || state.is(Blocks.LARGE_FERN)
         || state.is(Blocks.DEAD_BUSH)
         || state.is(Blocks.PINK_PETALS)
         || state.is(Blocks.BROWN_MUSHROOM)
         || state.is(Blocks.RED_MUSHROOM)
         || state.is(Blocks.SWEET_BERRY_BUSH)
         || state.is(Blocks.VINE);
   }

   private static final class AftermathTask {
      private final ServerLevel level;
      private final Vec3 center;
      private final int minY;
      private final int maxY;
      private final LivingEntity owner;
      private final ManasSkillInstance skillInstance;
      private final boolean smoothCrater;
      private final float craterRadius;
      private final float craterDepth;
      private final List<TengekiProgressiveDestruction.Column> columns;
      private int nextColumn;
      private int nextFluidCleanupColumn;
      private boolean cancelled;

      private AftermathTask(
         ServerLevel level,
         Vec3 center,
         float craterRadius,
         LivingEntity owner,
         ManasSkillInstance skillInstance,
         boolean smoothCrater,
         float profileRadius,
         float craterDepth
      ) {
         this.level = level;
         this.center = center;
         this.minY = Math.max(level.getMinBuildHeight(), (int)Math.floor(center.y - craterRadius) - 2);
         this.maxY = Math.min(level.getMaxBuildHeight() - 1, (int)Math.ceil(center.y + craterRadius + 64.0));
         this.owner = owner;
         this.skillInstance = skillInstance;
         this.smoothCrater = smoothCrater;
         this.craterRadius = profileRadius;
         this.craterDepth = craterDepth;
         this.columns = TengekiProgressiveDestruction.buildColumns(center, craterRadius);
         this.nextFluidCleanupColumn = smoothCrater ? 0 : this.columns.size();
      }

      private int tick(int columnBudget) {
         if (!DestructiveSkillSafety.shouldPreventTerrainDamage() && TensuraGameRules.canSkillGrief(this.level)) {
            int processed;
            for (processed = 0; processed < columnBudget && this.nextColumn < this.columns.size(); processed++) {
               this.processColumn(this.columns.get(this.nextColumn++));
            }

            while (processed < columnBudget && this.smoothCrater && this.nextColumn >= this.columns.size() && this.nextFluidCleanupColumn < this.columns.size()) {
               int reverseIndex = this.columns.size() - 1 - this.nextFluidCleanupColumn++;
               this.evaporateFluidsInColumn(this.columns.get(reverseIndex));
               processed++;
            }

            return processed;
         } else {
            this.cancelled = true;
            return 0;
         }
      }

      private boolean isComplete() {
         return this.cancelled || this.nextColumn >= this.columns.size() && (!this.smoothCrater || this.nextFluidCleanupColumn >= this.columns.size());
      }

      private void processColumn(TengekiProgressiveDestruction.Column column) {
         BlockPos chunkCheck = new BlockPos(column.x(), this.level.getMinBuildHeight(), column.z());
         if (this.level.hasChunkAt(chunkCheck) && this.level.getWorldBorder().isWithinBounds(chunkCheck)) {
            if (this.smoothCrater && this.isInsideCraterProfile(column)) {
               this.processSmoothCraterColumn(column);
            } else {
               MutableBlockPos cursor = new MutableBlockPos();
               int surfaceY = this.level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, column.x(), column.z()) - 1;
               int lowestSearchY = Math.max(this.minY, surfaceY - 128);
               int fallbackSurfaceY = -1;

               while (surfaceY >= lowestSearchY) {
                  cursor.set(column.x(), surfaceY, column.z());
                  BlockState surface = this.level.getBlockState(cursor);
                  if (TengekiProgressiveDestruction.isScorchableSurface(surface)) {
                     if (fallbackSurfaceY < 0) {
                        fallbackSurfaceY = surfaceY;
                     }

                     if (TengekiProgressiveDestruction.isNaturalGroundSurface(surface)) {
                        this.clearShockwaveColumn(column.x(), column.z(), surfaceY);
                        this.clearSurfaceVegetation(cursor);
                        if (!surface.is(Blocks.COARSE_DIRT)) {
                           this.replaceWithCoarseDirt(cursor, surface);
                        }

                        return;
                     }

                     surfaceY--;
                  } else {
                     surfaceY--;
                  }
               }

               if (fallbackSurfaceY >= 0) {
                  cursor.set(column.x(), fallbackSurfaceY, column.z());
                  this.clearShockwaveColumn(column.x(), column.z(), fallbackSurfaceY);
                  this.clearSurfaceVegetation(cursor);
                  BlockState fallbackSurface = this.level.getBlockState(cursor);
                  if (!fallbackSurface.is(Blocks.COARSE_DIRT)) {
                     this.replaceWithCoarseDirt(cursor, fallbackSurface);
                  }
               }
            }
         }
      }

      private boolean isInsideCraterProfile(TengekiProgressiveDestruction.Column column) {
         double dx = column.x() + 0.5 - this.center.x;
         double dz = column.z() + 0.5 - this.center.z;
         return dx * dx + dz * dz <= this.craterRadius * this.craterRadius;
      }

      private void processSmoothCraterColumn(TengekiProgressiveDestruction.Column column) {
         double dx = column.x() + 0.5 - this.center.x;
         double dz = column.z() + 0.5 - this.center.z;
         double normalizedDistance = Math.sqrt(dx * dx + dz * dz) / this.craterRadius;
         double remainingProfile = Math.max(0.0, 1.0 - normalizedDistance * normalizedDistance);
         double depth = this.craterDepth * Math.pow(remainingProfile, 1.25);
         int floorBlockY = (int)Math.ceil(this.center.y - depth - 0.5) - 1;
         int surfaceTopY = this.level.getHeight(Types.WORLD_SURFACE, column.x(), column.z()) - 1;
         int topY = Math.min(this.level.getMaxBuildHeight() - 1, Math.max(this.maxY, surfaceTopY));
         floorBlockY = Math.max(this.level.getMinBuildHeight(), floorBlockY);
         MutableBlockPos cursor = new MutableBlockPos();

         for (int y = topY; y > floorBlockY; y--) {
            cursor.set(column.x(), y, column.z());
            BlockState state = this.level.getBlockState(cursor);
            if (!state.isAir() || !state.getFluidState().isEmpty()) {
               this.removeShockwaveBlock(cursor, state);
            }
         }

         cursor.set(column.x(), floorBlockY, column.z());
         BlockState floor = this.level.getBlockState(cursor);
         if (!floor.isAir()) {
            this.clearSurfaceVegetation(cursor);
            if (!floor.is(Blocks.COARSE_DIRT) && TengekiProgressiveDestruction.isScorchableSurface(floor)) {
               this.replaceWithCoarseDirt(cursor, floor);
            }
         }
      }

      private void clearShockwaveColumn(int x, int z, int surfaceY) {
         MutableBlockPos structurePos = new MutableBlockPos();
         int topY = Math.min(this.maxY, this.level.getMaxBuildHeight() - 1);

         for (int y = surfaceY + 1; y <= topY; y++) {
            structurePos.set(x, y, z);
            BlockState state = this.level.getBlockState(structurePos);
            if (!state.isAir()) {
               this.removeShockwaveBlock(structurePos, state);
            }
         }
      }

      private void evaporateFluidsInColumn(TengekiProgressiveDestruction.Column column) {
         BlockPos chunkCheck = new BlockPos(column.x(), this.level.getMinBuildHeight(), column.z());
         if (this.level.hasChunkAt(chunkCheck) && this.level.getWorldBorder().isWithinBounds(chunkCheck)) {
            int surfaceY = this.level.getHeight(Types.WORLD_SURFACE, column.x(), column.z()) - 1;
            int topY = Math.min(this.level.getMaxBuildHeight() - 1, Math.max(this.maxY, surfaceY));
            MutableBlockPos cursor = new MutableBlockPos();

            for (int y = topY; y >= this.minY; y--) {
               cursor.set(column.x(), y, column.z());
               BlockState state = this.level.getBlockState(cursor);
               if (!state.getFluidState().isEmpty()) {
                  this.removeShockwaveBlock(cursor, state);
               }
            }
         }
      }

      private void clearSurfaceVegetation(BlockPos surfacePos) {
         MutableBlockPos vegetationPos = new MutableBlockPos();

         for (int offsetY = 1; offsetY <= 2; offsetY++) {
            vegetationPos.set(surfacePos.getX(), surfacePos.getY() + offsetY, surfacePos.getZ());
            BlockState vegetation = this.level.getBlockState(vegetationPos);
            if (TengekiProgressiveDestruction.isClearableSurfaceVegetation(vegetation)) {
               this.removeLeaf(vegetationPos, vegetation);
            }
         }
      }

      private void removeLeaf(BlockPos pos, BlockState state) {
         this.removeShockwaveBlock(pos, state);
      }

      private void removeShockwaveBlock(BlockPos pos, BlockState state) {
         boolean containsFluid = !state.getFluidState().isEmpty();
         if (!state.is(TensuraBlockTags.MAGIC_EXPLOSION_IMMUNE) && (containsFluid || !(state.getBlock().defaultDestroyTime() < 0.0F)) && this.canGrief(pos)) {
            boolean changed = !state.getFluidState().isEmpty()
               ? this.level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState())
               : this.level.removeBlock(pos, false);
            if (changed) {
               this.notifyGrief(pos);
            }
         }
      }

      private void replaceWithCoarseDirt(BlockPos pos, BlockState state) {
         if (!state.is(TensuraBlockTags.MAGIC_EXPLOSION_IMMUNE) && !(state.getBlock().defaultDestroyTime() < 0.0F) && this.canGrief(pos)) {
            if (this.level.setBlockAndUpdate(pos, Blocks.COARSE_DIRT.defaultBlockState())) {
               this.notifyGrief(pos);
            }
         }
      }

      private boolean canGrief(BlockPos pos) {
         return FourthProtectionManager.isInsideBarrier(this.level, Vec3.atCenterOf(pos))
            ? false
            : !((SkillGriefEvent)TensuraSkillEvents.SKILL_GRIEF_PRE.invoker())
               .grief(this.skillInstance, this.level, this.owner, pos.getX(), pos.getY(), pos.getZ())
               .isFalse();
      }

      private void notifyGrief(BlockPos pos) {
         ((SkillGriefEvent)TensuraSkillEvents.SKILL_GRIEF_POS.invoker()).grief(this.skillInstance, this.level, this.owner, pos.getX(), pos.getY(), pos.getZ());
      }
   }

   private record Cell(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, double distanceSquared) {
   }

   private record Column(int x, int z, double distanceSquared) {
   }

   private static final class DestructionTask {
      private final ServerLevel level;
      private final Vec3 center;
      private final float radius;
      private final double radiusSquared;
      private final float aftermathRadius;
      private final LivingEntity owner;
      private final ManasSkillInstance skillInstance;
      private final List<TengekiProgressiveDestruction.Cell> cells;
      private final boolean smoothCrater;
      private final float craterDepth;
      private int nextCell;
      private boolean cancelled;

      private DestructionTask(ServerLevel level, Vec3 center, float radius, float aftermathRadius, LivingEntity owner, ManasSkillInstance skillInstance) {
         this(level, center, radius, aftermathRadius, owner, skillInstance, false, radius);
      }

      private DestructionTask(
         ServerLevel level,
         Vec3 center,
         float radius,
         float aftermathRadius,
         LivingEntity owner,
         ManasSkillInstance skillInstance,
         boolean smoothCrater,
         float craterDepth
      ) {
         this.level = level;
         this.center = center;
         this.radius = radius;
         this.radiusSquared = radius * radius;
         this.aftermathRadius = aftermathRadius;
         this.owner = owner;
         this.skillInstance = skillInstance;
         this.smoothCrater = smoothCrater;
         this.craterDepth = craterDepth;
         this.cells = TengekiProgressiveDestruction.buildCells(center, radius, smoothCrater, craterDepth);
      }

      private int tick(int cellBudget) {
         if (!DestructiveSkillSafety.shouldPreventTerrainDamage() && TensuraGameRules.canSkillGrief(this.level)) {
            int processed;
            for (processed = 0; processed < cellBudget && this.nextCell < this.cells.size(); processed++) {
               this.destroyCell(this.cells.get(this.nextCell++));
            }

            return processed;
         } else {
            this.cancelled = true;
            return 0;
         }
      }

      private boolean isComplete() {
         return this.cancelled || this.nextCell >= this.cells.size();
      }

      private boolean wasCancelled() {
         return this.cancelled;
      }

      private TengekiProgressiveDestruction.AftermathTask createAftermath() {
         return new TengekiProgressiveDestruction.AftermathTask(
            this.level, this.center, this.aftermathRadius, this.owner, this.skillInstance, this.smoothCrater, this.radius, this.craterDepth
         );
      }

      private void destroyCell(TengekiProgressiveDestruction.Cell cell) {
         MutableBlockPos pos = new MutableBlockPos();

         for (int x = cell.minX(); x <= cell.maxX(); x++) {
            double dx = x + 0.5 - this.center.x;

            for (int y = cell.minY(); y <= cell.maxY(); y++) {
               if (y >= this.level.getMinBuildHeight() && y < this.level.getMaxBuildHeight()) {
                  double dy = y + 0.5 - this.center.y;

                  for (int z = cell.minZ(); z <= cell.maxZ(); z++) {
                     double dz = z + 0.5 - this.center.z;
                     if (this.smoothCrater ? this.isInsideSmoothCrater(dx, dz, y + 0.5) : !(dx * dx + dy * dy + dz * dz > this.radiusSquared)) {
                        pos.set(x, y, z);
                        if (this.level.hasChunkAt(pos) && this.level.getWorldBorder().isWithinBounds(pos)) {
                           this.destroyBlock(pos);
                        }
                     }
                  }
               }
            }
         }
      }

      private boolean isInsideSmoothCrater(double dx, double dz, double blockCenterY) {
         double horizontalDistanceSquared = dx * dx + dz * dz;
         if (!(horizontalDistanceSquared > this.radiusSquared) && !(blockCenterY > this.center.y)) {
            double normalizedDistance = Math.sqrt(horizontalDistanceSquared) / this.radius;
            double remainingProfile = Math.max(0.0, 1.0 - normalizedDistance * normalizedDistance);
            double depth = this.craterDepth * Math.pow(remainingProfile, 1.25);
            return blockCenterY >= this.center.y - depth;
         } else {
            return false;
         }
      }

      private void destroyBlock(BlockPos pos) {
         if (!FourthProtectionManager.isInsideBarrier(this.level, Vec3.atCenterOf(pos))) {
            BlockState state = this.level.getBlockState(pos);
            boolean containsFluid = !state.getFluidState().isEmpty();
            if ((!state.isAir() || containsFluid)
               && !state.is(TensuraBlockTags.MAGIC_EXPLOSION_IMMUNE)
               && (containsFluid || !(state.getBlock().defaultDestroyTime() < 0.0F))) {
               if (!((SkillGriefEvent)TensuraSkillEvents.SKILL_GRIEF_PRE.invoker())
                  .grief(this.skillInstance, this.level, this.owner, pos.getX(), pos.getY(), pos.getZ())
                  .isFalse()) {
                  boolean changed;
                  if (containsFluid) {
                     changed = this.level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                  } else {
                     changed = this.level.removeBlock(pos, false);
                  }

                  if (changed) {
                     ((SkillGriefEvent)TensuraSkillEvents.SKILL_GRIEF_POS.invoker())
                        .grief(this.skillInstance, this.level, this.owner, pos.getX(), pos.getY(), pos.getZ());
                  }
               }
            }
         }
      }
   }
}
