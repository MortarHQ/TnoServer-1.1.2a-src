package net.jadenxgamer.netherexp.mixin.worldgen;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.serialization.Codec;
import java.util.function.Function;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.util.CompatUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CaveWorldCarver;
import net.minecraft.world.level.levelgen.carver.NetherWorldCarver;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(NetherWorldCarver.class)
public abstract class NetherWorldCarverMixin extends CaveWorldCarver {
   public NetherWorldCarverMixin(Codec<CaveCarverConfiguration> codec) {
      super(codec);
   }

   @WrapMethod(
      method = "carveBlock(Lnet/minecraft/world/level/levelgen/carver/CarvingContext;Lnet/minecraft/world/level/levelgen/carver/CaveCarverConfiguration;Lnet/minecraft/world/level/chunk/ChunkAccess;Ljava/util/function/Function;Lnet/minecraft/world/level/chunk/CarvingMask;Lnet/minecraft/core/BlockPos$MutableBlockPos;Lnet/minecraft/core/BlockPos$MutableBlockPos;Lnet/minecraft/world/level/levelgen/Aquifer;Lorg/apache/commons/lang3/mutable/MutableBoolean;)Z"
   )
   private boolean netherexp$carveBlock(
      CarvingContext context,
      CaveCarverConfiguration config,
      ChunkAccess chunk,
      Function<BlockPos, Holder<Biome>> biomeGetter,
      CarvingMask carvingMask,
      MutableBlockPos pos,
      MutableBlockPos checkPos,
      Aquifer aquifer,
      MutableBoolean reachedSurface,
      Operation<Boolean> original
   ) {
      if (CompatUtil.AMPLIFIED_NETHER || !(Boolean)JNEConfigs.NETHER_WORLDGEN_OVERHAUL.get()) {
         return (Boolean)original.call(new Object[]{context, config, chunk, biomeGetter, carvingMask, pos, checkPos, aquifer, reachedSurface});
      } else if (this.canReplaceBlock(config, chunk.getBlockState(pos))) {
         BlockState state = pos.getY() <= context.getMinGenY() + 63 ? LAVA.createLegacyBlock() : CAVE_AIR;
         chunk.setBlockState(pos, state, false);
         return true;
      } else {
         return false;
      }
   }
}
