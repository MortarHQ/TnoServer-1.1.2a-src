package cn.leolezury.eternalstarlight.common.world.gen.surface;

import cn.leolezury.eternalstarlight.common.world.gen.chunkgenerator.StarlightSurfaceSystem;
import com.mojang.serialization.MapCodec;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.SurfaceRules.Condition;
import net.minecraft.world.level.levelgen.SurfaceRules.ConditionSource;
import net.minecraft.world.level.levelgen.SurfaceRules.Context;

public class OnSurfaceCondition implements ConditionSource {
   public static final OnSurfaceCondition INSTANCE = new OnSurfaceCondition();
   public static final KeyDispatchDataCodec<OnSurfaceCondition> CODEC = KeyDispatchDataCodec.of(MapCodec.unit(INSTANCE));

   public KeyDispatchDataCodec<? extends ConditionSource> codec() {
      return CODEC;
   }

   public Condition apply(Context context) {
      return context.system instanceof StarlightSurfaceSystem system && system.getStarlightBiomeSource() != null
         ? () -> context.blockY >= system.getStarlightBiomeSource().getHeight(context.blockX, context.blockZ, context.randomState.sampler()) - 8
         : () -> false;
   }
}
