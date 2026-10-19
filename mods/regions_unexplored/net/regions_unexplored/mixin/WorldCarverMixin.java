package net.regions_unexplored.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.regions_unexplored.registry.RUBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WorldCarver.class)
public abstract class WorldCarverMixin {
   @WrapOperation(
      method = "carveBlock(Lnet/minecraft/world/level/levelgen/carver/CarvingContext;Lnet/minecraft/world/level/levelgen/carver/CarverConfiguration;Lnet/minecraft/world/level/chunk/ChunkAccess;Ljava/util/function/Function;Lnet/minecraft/world/level/chunk/CarvingMask;Lnet/minecraft/core/BlockPos$MutableBlockPos;Lnet/minecraft/core/BlockPos$MutableBlockPos;Lnet/minecraft/world/level/levelgen/Aquifer;Lorg/apache/commons/lang3/mutable/MutableBoolean;)Z",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z", ordinal = 0)
   )
   private static boolean fixGrassQuery(BlockState state, Block block, Operation<Boolean> operation) {
      return (Boolean)operation.call(new Object[]{state, block})
         || (Boolean)operation.call(new Object[]{state, RUBlocks.PEAT_GRASS_BLOCK.get()})
         || (Boolean)operation.call(new Object[]{state, RUBlocks.SILT_GRASS_BLOCK.get()});
   }

   @WrapOperation(
      method = "carveBlock(Lnet/minecraft/world/level/levelgen/carver/CarvingContext;Lnet/minecraft/world/level/levelgen/carver/CarverConfiguration;Lnet/minecraft/world/level/chunk/ChunkAccess;Ljava/util/function/Function;Lnet/minecraft/world/level/chunk/CarvingMask;Lnet/minecraft/core/BlockPos$MutableBlockPos;Lnet/minecraft/core/BlockPos$MutableBlockPos;Lnet/minecraft/world/level/levelgen/Aquifer;Lorg/apache/commons/lang3/mutable/MutableBoolean;)Z",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z", ordinal = 2)
   )
   private static boolean fixDirtQuery(BlockState state, Block block, Operation<Boolean> operation) {
      return (Boolean)operation.call(new Object[]{state, block})
         || (Boolean)operation.call(new Object[]{state, RUBlocks.PEAT_DIRT.get()})
         || (Boolean)operation.call(new Object[]{state, RUBlocks.SILT_DIRT.get()});
   }
}
