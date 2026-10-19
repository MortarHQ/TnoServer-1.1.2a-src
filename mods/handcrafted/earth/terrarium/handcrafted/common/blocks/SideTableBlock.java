package earth.terrarium.handcrafted.common.blocks;

import earth.terrarium.handcrafted.common.blockentities.ContainerBlockEntity;
import earth.terrarium.handcrafted.common.constants.ConstantComponents;
import earth.terrarium.handcrafted.common.utils.TooltipUtils;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class SideTableBlock extends SimpleTableBlock implements EntityBlock {
   public static final VoxelShape SHAPE = Stream.of(
         Block.box(2.0, 12.0, 2.0, 14.0, 16.0, 14.0),
         Block.box(4.0, 8.0, 4.0, 12.0, 12.0, 12.0),
         Block.box(3.0, 0.0, 10.0, 6.0, 12.0, 13.0),
         Block.box(10.0, 0.0, 3.0, 13.0, 12.0, 6.0),
         Block.box(3.0, 0.0, 3.0, 6.0, 12.0, 6.0),
         Block.box(10.0, 0.0, 10.0, 13.0, 12.0, 13.0)
      )
      .reduce(Shapes::or)
      .get();

   public SideTableBlock(Properties properties) {
      super(properties);
   }

   @NotNull
   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new ContainerBlockEntity(pos, state);
   }

   protected boolean hasAnalogOutputSignal(BlockState state) {
      return true;
   }

   protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
      return AbstractContainerMenu.getRedstoneSignalFromContainer((Container)level.getBlockEntity(pos));
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      TooltipUtils.addDescriptionComponent(tooltipComponents, ConstantComponents.SHEET);
   }
}
