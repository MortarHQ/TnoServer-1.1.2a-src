package earth.terrarium.handcrafted.common.blocks.misc;

import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class PotBlock extends Block {
   public static final VoxelShape THIN_SHAPE = Stream.of(
         Block.box(5.0, 12.0, 5.0, 11.0, 14.0, 11.0), Block.box(4.0, 0.0, 4.0, 12.0, 11.0, 12.0), Block.box(6.0, 11.0, 6.0, 10.0, 12.0, 10.0)
      )
      .reduce(Shapes::or)
      .get();
   public static final VoxelShape MEDIUM_SHAPE = Stream.of(
         Block.box(3.0, 10.0, 3.0, 13.0, 12.0, 13.0), Block.box(2.0, 0.0, 2.0, 14.0, 9.0, 14.0), Block.box(4.0, 9.0, 4.0, 12.0, 10.0, 12.0)
      )
      .reduce(Shapes::or)
      .get();
   public static final VoxelShape WIDE_SHAPE = Stream.of(
         Block.box(2.0, 6.0, 2.0, 14.0, 8.0, 14.0), Block.box(1.0, 0.0, 1.0, 15.0, 5.0, 15.0), Block.box(3.0, 5.0, 3.0, 13.0, 6.0, 13.0)
      )
      .reduce(Shapes::or)
      .get();
   public static final VoxelShape THICK_SHAPE = Stream.of(
         Block.box(3.0, 12.0, 3.0, 13.0, 14.0, 13.0), Block.box(2.0, 0.0, 2.0, 14.0, 11.0, 14.0), Block.box(4.0, 11.0, 4.0, 12.0, 12.0, 12.0)
      )
      .reduce(Shapes::or)
      .get();
   private final PotBlock.PotSize size;

   public PotBlock(PotBlock.PotSize size, Properties properties) {
      super(properties);
      this.size = size;
   }

   public PotBlock.PotSize size() {
      return this.size;
   }

   @NotNull
   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return switch (this.size) {
         case THIN -> THIN_SHAPE;
         case MEDIUM -> MEDIUM_SHAPE;
         case WIDE -> WIDE_SHAPE;
         case THICK -> THICK_SHAPE;
      };
   }

   public enum PotSize {
      THIN,
      MEDIUM,
      WIDE,
      THICK;
   }
}
