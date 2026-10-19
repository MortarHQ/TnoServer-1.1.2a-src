package twilightforest.world.components.placements;

import com.mojang.serialization.MapCodec;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import twilightforest.init.TFFeatureModifiers;

public class ChunkCenterModifier extends PlacementModifier {
   private static final ChunkCenterModifier INSTANCE = new ChunkCenterModifier();
   public static final MapCodec<ChunkCenterModifier> CODEC = MapCodec.unit(() -> INSTANCE);

   public static ChunkCenterModifier center() {
      return INSTANCE;
   }

   public Stream<BlockPos> getPositions(PlacementContext ctx, RandomSource random, BlockPos pos) {
      return Stream.of(new BlockPos((pos.getX() & -16) + 8, pos.getY(), (pos.getZ() & -16) + 8));
   }

   public PlacementModifierType<?> type() {
      return (PlacementModifierType<?>)TFFeatureModifiers.CHUNK_CENTERER.get();
   }
}
