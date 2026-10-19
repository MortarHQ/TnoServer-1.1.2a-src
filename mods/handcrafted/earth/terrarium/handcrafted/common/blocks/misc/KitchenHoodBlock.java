package earth.terrarium.handcrafted.common.blocks.misc;

import com.mojang.serialization.MapCodec;
import earth.terrarium.handcrafted.common.blocks.base.SimpleBlock;
import java.util.stream.Stream;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

@MethodsReturnNonnullByDefault
public class KitchenHoodBlock extends SimpleBlock {
   public static final MapCodec<KitchenHoodBlock> CODEC = simpleCodec(KitchenHoodBlock::new);
   public static final VoxelShape SHAPE = Stream.of(
         Block.box(3.0, 13.0, 3.0, 13.0, 16.0, 13.0),
         Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
         Block.box(3.0, 6.0, 3.0, 13.0, 9.0, 13.0),
         Block.box(4.0, 5.0, 4.0, 12.0, 15.0, 12.0)
      )
      .reduce(Shapes::or)
      .get();

   public KitchenHoodBlock(Properties properties) {
      super(properties);
   }

   protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
      return CODEC;
   }

   @NotNull
   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }
}
