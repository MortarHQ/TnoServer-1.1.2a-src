package net.tslat.smartbrainlib.api.core.sensor.vanilla;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.object.SquareRadius;
import net.tslat.smartbrainlib.registry.SBLSensors;
import net.tslat.smartbrainlib.util.BrainUtils;

public class SecondaryPoiSensor<E extends Villager> extends ExtendedSensor<E> {
   private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(new MemoryModuleType[]{MemoryModuleType.SECONDARY_JOB_SITE});
   protected SquareRadius radius = new SquareRadius(8.0, 4.0);

   public SecondaryPoiSensor() {
      this.setScanRate(entity -> 40);
   }

   public SecondaryPoiSensor<E> setRadius(int radius) {
      return this.setRadius(radius, radius);
   }

   public SecondaryPoiSensor<E> setRadius(double xz, double y) {
      this.radius = new SquareRadius(xz, y);
      return this;
   }

   @Override
   public List<MemoryModuleType<?>> memoriesUsed() {
      return MEMORIES;
   }

   @Override
   public SensorType<? extends ExtendedSensor<?>> type() {
      return SBLSensors.SECONDARY_POI.get();
   }

   protected void doTick(ServerLevel level, E entity) {
      ResourceKey<Level> dimension = level.dimension();
      BlockPos pos = entity.blockPosition();
      ImmutableSet<Block> testPoiBlocks = entity.getVillagerData().getProfession().secondaryPoi();
      List<GlobalPos> poiPositions = new ObjectArrayList();
      if (!testPoiBlocks.isEmpty()) {
         for (BlockPos testPos : BlockPos.betweenClosed(
            pos.getX() - (int)this.radius.xzRadius() / 2,
            pos.getY() - (int)this.radius.yRadius() / 2,
            pos.getZ() - (int)this.radius.xzRadius() / 2,
            pos.getX() + (int)this.radius.xzRadius() / 2,
            pos.getY() + (int)this.radius.yRadius() / 2,
            pos.getZ() + (int)this.radius.xzRadius() / 2
         )) {
            if (testPoiBlocks.contains(level.getBlockState(testPos).getBlock())) {
               poiPositions.add(GlobalPos.of(dimension, testPos.immutable()));
            }
         }

         if (poiPositions.isEmpty()) {
            BrainUtils.clearMemory(entity, MemoryModuleType.SECONDARY_JOB_SITE);
         } else {
            BrainUtils.setMemory(entity, MemoryModuleType.SECONDARY_JOB_SITE, poiPositions);
         }
      }
   }
}
