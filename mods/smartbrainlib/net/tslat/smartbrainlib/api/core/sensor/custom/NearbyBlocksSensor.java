package net.tslat.smartbrainlib.api.core.sensor.custom;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.level.block.state.BlockState;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.PredicateSensor;
import net.tslat.smartbrainlib.object.SquareRadius;
import net.tslat.smartbrainlib.registry.SBLMemoryTypes;
import net.tslat.smartbrainlib.registry.SBLSensors;
import net.tslat.smartbrainlib.util.BrainUtils;

public class NearbyBlocksSensor<E extends LivingEntity> extends PredicateSensor<BlockState, E> {
   private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(new MemoryModuleType[]{SBLMemoryTypes.NEARBY_BLOCKS.get()});
   protected SquareRadius radius = new SquareRadius(1.0, 1.0);

   public NearbyBlocksSensor() {
      this.setPredicate((state, entity) -> !state.isAir());
   }

   @Override
   public List<MemoryModuleType<?>> memoriesUsed() {
      return MEMORIES;
   }

   @Override
   public SensorType<? extends ExtendedSensor<?>> type() {
      return SBLSensors.NEARBY_BLOCKS.get();
   }

   public NearbyBlocksSensor<E> setRadius(double radius) {
      return this.setRadius(radius, radius);
   }

   public NearbyBlocksSensor<E> setRadius(double xz, double y) {
      this.radius = new SquareRadius(xz, y);
      return this;
   }

   @Override
   protected void doTick(ServerLevel level, E entity) {
      List<Pair<BlockPos, BlockState>> blocks = new ObjectArrayList();

      for (BlockPos pos : BlockPos.betweenClosed(entity.blockPosition().subtract(this.radius.toVec3i()), entity.blockPosition().offset(this.radius.toVec3i()))) {
         BlockState state = level.getBlockState(pos);
         if (this.predicate().test(state, entity)) {
            blocks.add(Pair.of(pos.immutable(), state));
         }
      }

      if (blocks.isEmpty()) {
         BrainUtils.clearMemory(entity, SBLMemoryTypes.NEARBY_BLOCKS.get());
      } else {
         BrainUtils.setMemory(entity, SBLMemoryTypes.NEARBY_BLOCKS.get(), blocks);
      }
   }
}
