package cn.star.structblend.mixin;

import cn.star.structblend.terrain.ColumnHeightScan;
import cn.star.structblend.terrain.PairedHeightQuery;
import java.util.OptionalInt;
import java.util.function.Predicate;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import org.apache.commons.lang3.mutable.MutableObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoiseBasedChunkGenerator.class)
public abstract class PairedHeightMixin {
   @Shadow
   protected abstract OptionalInt iterateNoiseColumn(
      LevelHeightAccessor var1, RandomState var2, int var3, int var4, MutableObject<NoiseColumn> var5, Predicate<BlockState> var6
   );

   @Inject(
      method = "iterateNoiseColumn(Lnet/minecraft/world/level/LevelHeightAccessor;Lnet/minecraft/world/level/levelgen/RandomState;IILorg/apache/commons/lang3/mutable/MutableObject;Ljava/util/function/Predicate;)Ljava/util/OptionalInt;",
      at = @At("HEAD"),
      cancellable = true
   )
   private void structblend$pairedHeights(
      LevelHeightAccessor heights,
      RandomState randomState,
      int x,
      int z,
      MutableObject<NoiseColumn> output,
      Predicate<BlockState> predicate,
      CallbackInfoReturnable<OptionalInt> callback
   ) {
      if (output == null) {
         PairedHeightQuery.Request request = PairedHeightQuery.current(this, heights, randomState, x, z);
         if (request != null) {
            if (request.begin(predicate)) {
               try {
                  NoiseSettings settings = ((NoiseGeneratorSettings)((NoiseBasedChunkGenerator)this).generatorSettings().value())
                     .noiseSettings()
                     .clampToHeightAccessor(heights);
                  int cellHeight = settings.getCellHeight();
                  int size = Math.max(0, Math.floorDiv(settings.height(), cellHeight)) * cellHeight;
                  int top = Math.floorDiv(settings.minY(), cellHeight) * cellHeight + size;
                  ColumnHeightScan<BlockState> scan = new ColumnHeightScan<>(Types.WORLD_SURFACE_WG.isOpaque(), Types.OCEAN_FLOOR_WG.isOpaque(), top, size);
                  OptionalInt terminal = this.iterateNoiseColumn(heights, randomState, x, z, null, scan);
                  request.complete(scan, terminal);
               } finally {
                  request.endComputation();
               }
            }

            OptionalInt result = request.resultFor(predicate);
            if (result != null) {
               callback.setReturnValue(result);
            }
         }
      }
   }
}
