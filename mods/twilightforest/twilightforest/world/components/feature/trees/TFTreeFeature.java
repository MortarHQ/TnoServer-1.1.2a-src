package twilightforest.world.components.feature.trees;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import java.util.Set;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator.Context;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;
import twilightforest.util.RootPlacer;
import twilightforest.world.components.feature.config.TFTreeFeatureConfig;

public abstract class TFTreeFeature<T extends TFTreeFeatureConfig> extends Feature<T> {
   public TFTreeFeature(Codec<T> configIn) {
      super(configIn);
   }

   public final boolean place(FeaturePlaceContext<T> context) {
      WorldGenLevel worldgenlevel = context.level();
      RandomSource randomsource = context.random();
      BlockPos blockpos = context.origin();
      T treeconfiguration = (T)context.config();
      Set<BlockPos> set = Sets.newHashSet();
      Set<BlockPos> set1 = Sets.newHashSet();
      Set<BlockPos> set2 = Sets.newHashSet();
      Set<BlockPos> set3 = Sets.newHashSet();
      BiConsumer<BlockPos, BlockState> biconsumer = (pos, state) -> {
         set.add(pos.immutable());
         worldgenlevel.setBlock(pos, state, 19);
      };
      BiConsumer<BlockPos, BlockState> biconsumer1 = (pos, state) -> {
         set1.add(pos.immutable());
         worldgenlevel.setBlock(pos, state, 19);
      };
      BiConsumer<BlockPos, BlockState> biconsumer2 = (pos, state) -> {
         set2.add(pos.immutable());
         worldgenlevel.setBlock(pos, state, 19);
      };
      BiConsumer<BlockPos, BlockState> biconsumer3 = (pos, state) -> {
         set3.add(pos.immutable());
         worldgenlevel.setBlock(pos, state, 19);
      };
      boolean flag = this.generate(worldgenlevel, randomsource, blockpos, biconsumer, biconsumer1, new RootPlacer(biconsumer2, 1), treeconfiguration);
      if (flag && (!set1.isEmpty() || !set2.isEmpty())) {
         if (!treeconfiguration.decorators.isEmpty()) {
            Context treedecorator$context = new Context(worldgenlevel, biconsumer3, randomsource, set1, set2, set);
            treeconfiguration.decorators.forEach(p_225282_ -> p_225282_.place(treedecorator$context));
         }

         return BoundingBox.encapsulatingPositions(Iterables.concat(set, set1, set2, set3)).map(boundingBox -> {
            DiscreteVoxelShape discretevoxelshape = TreeFeature.updateLeaves(worldgenlevel, boundingBox, set1, set3, set);
            StructureTemplate.updateShapeAtEdge(worldgenlevel, 3, discretevoxelshape, boundingBox.minX(), boundingBox.minY(), boundingBox.minZ());
            return true;
         }).orElse(false);
      } else {
         return false;
      }
   }

   protected abstract boolean generate(
      WorldGenLevel var1,
      RandomSource var2,
      BlockPos var3,
      BiConsumer<BlockPos, BlockState> var4,
      BiConsumer<BlockPos, BlockState> var5,
      RootPlacer var6,
      T var7
   );
}
