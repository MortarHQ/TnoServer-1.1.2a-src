package net.jadenxgamer.elysium_api.impl.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;

public class SolidStructureVoidBlock extends Block {
   public static final EnumProperty<SolidStructureVoidBlock.Color> COLOR = EnumProperty.create("color", SolidStructureVoidBlock.Color.class);

   public SolidStructureVoidBlock(Properties properties) {
      super(properties);
      this.defaultBlockState().setValue(COLOR, SolidStructureVoidBlock.Color.WHITE);
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockPos pos = context.getClickedPos();
      int sum = pos.getX() + pos.getY() + pos.getZ();
      SolidStructureVoidBlock.Color color = sum % 2 == 0 ? SolidStructureVoidBlock.Color.WHITE : SolidStructureVoidBlock.Color.BLACK;
      return (BlockState)this.defaultBlockState().setValue(COLOR, color);
   }

   protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
      super.onPlace(state, level, pos, oldState, movedByPiston);
      if (!level.isClientSide) {
         int sum = pos.getX() + pos.getY() + pos.getZ();
         SolidStructureVoidBlock.Color expectedColor = sum % 2 == 0 ? SolidStructureVoidBlock.Color.WHITE : SolidStructureVoidBlock.Color.BLACK;
         SolidStructureVoidBlock.Color currentColor = (SolidStructureVoidBlock.Color)state.getValue(COLOR);
         if (currentColor != expectedColor) {
            BlockState newState = (BlockState)state.setValue(COLOR, expectedColor);
            level.setBlock(pos, newState, 3);
         }
      }
   }

   protected boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
      Player player = useContext.getPlayer();
      return player != null && player.isShiftKeyDown() && !player.getMainHandItem().is(this.asItem());
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{COLOR});
   }

   public enum Color implements StringRepresentable {
      WHITE("white"),
      BLACK("black");

      private final String name;

      Color(String name) {
         this.name = name;
      }

      @NotNull
      public String getSerializedName() {
         return this.name;
      }
   }
}
