package net.jadenxgamer.elysium_api.api.surface_rules.condition;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.SurfaceRules.Condition;
import net.minecraft.world.level.levelgen.SurfaceRules.ConditionSource;
import net.minecraft.world.level.levelgen.SurfaceRules.Context;

public record BlockMatchConditionSource(Block targetBlock) implements ConditionSource {
   public static final KeyDispatchDataCodec<BlockMatchConditionSource> CODEC = KeyDispatchDataCodec.of(
      RecordCodecBuilder.mapCodec(
         instance -> instance.group(BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block").forGetter(BlockMatchConditionSource::targetBlock))
            .apply(instance, BlockMatchConditionSource::new)
      )
   );

   public KeyDispatchDataCodec<? extends ConditionSource> codec() {
      return CODEC;
   }

   public Condition apply(Context ctx) {
      MutableBlockPos pos = new MutableBlockPos();
      return () -> {
         pos.set(ctx.blockX, ctx.blockY, ctx.blockZ);
         return ctx.chunk.getBlockState(pos).is(this.targetBlock);
      };
   }

   @Override
   public String toString() {
      return "BlockMatchConditionSource[targetBlock=" + this.targetBlock + "]";
   }
}
