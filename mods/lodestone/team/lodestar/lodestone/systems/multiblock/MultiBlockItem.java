package team.lodestar.lodestone.systems.multiblock;

import java.util.function.Supplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class MultiBlockItem extends BlockItem {
   public final Supplier<? extends MultiBlockStructure> structure;

   public MultiBlockItem(Block block, Properties properties, Supplier<? extends MultiBlockStructure> structure) {
      super(block, properties);
      this.structure = structure;
   }

   protected boolean canPlace(BlockPlaceContext context, BlockState state) {
      return !this.structure.get().canPlace(context) ? false : super.canPlace(context, state);
   }

   protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
      this.structure.get().place(context);
      return super.placeBlock(context, state);
   }
}
