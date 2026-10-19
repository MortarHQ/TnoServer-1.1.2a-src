package artifacts.world.placement;

import artifacts.Artifacts;
import artifacts.registry.ModPlacementModifierTypes;
import com.mojang.serialization.MapCodec;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class CampsiteHeightRangePlacement extends PlacementModifier {
   private static final CampsiteHeightRangePlacement INSTANCE = new CampsiteHeightRangePlacement();
   public static final MapCodec<CampsiteHeightRangePlacement> CODEC = MapCodec.unit(() -> INSTANCE);

   private CampsiteHeightRangePlacement() {
   }

   public static CampsiteHeightRangePlacement campsiteHeightRange() {
      return INSTANCE;
   }

   public Stream<BlockPos> getPositions(PlacementContext context, RandomSource randomSource, BlockPos pos) {
      int minY = Artifacts.CONFIG.general.campsite.minY.get();
      int maxY = Artifacts.CONFIG.general.campsite.maxY.get();
      return minY > maxY ? Stream.of() : Stream.of(pos.atY(Mth.randomBetweenInclusive(randomSource, minY, maxY)));
   }

   public PlacementModifierType<?> type() {
      return ModPlacementModifierTypes.CAMPSITE_HEIGHT_RANGE.value();
   }
}
