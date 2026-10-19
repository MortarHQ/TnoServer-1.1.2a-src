package net.blay09.mods.waystones.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.menu.BalmMenuProvider;
import net.blay09.mods.waystones.api.TeleportFlags;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystoneTypes;
import net.blay09.mods.waystones.block.entity.PortstoneBlockEntity;
import net.blay09.mods.waystones.core.PlayerWaystoneManager;
import net.blay09.mods.waystones.core.WaystoneImpl;
import net.blay09.mods.waystones.menu.ModMenus;
import net.blay09.mods.waystones.menu.WaystoneSelectionMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class PortstoneBlock extends WaystoneBlockBase {
   public static final MapCodec<PortstoneBlock> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(DyeColor.CODEC.fieldOf("color").forGetter(PortstoneBlock::getColor), propertiesCodec()).apply(instance, PortstoneBlock::new)
   );
   private static final VoxelShape[] LOWER_SHAPES = new VoxelShape[]{
      Shapes.or(
            box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0),
            new VoxelShape[]{
               box(1.0, 3.0, 1.0, 15.0, 7.0, 15.0),
               box(2.0, 7.0, 2.0, 14.0, 9.0, 14.0),
               box(3.0, 9.0, 3.0, 13.0, 16.0, 7.0),
               box(4.0, 9.0, 7.0, 12.0, 16.0, 10.0),
               box(4.0, 9.0, 10.0, 12.0, 12.0, 12.0)
            }
         )
         .optimize(),
      Shapes.or(
            box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0),
            new VoxelShape[]{
               box(1.0, 3.0, 1.0, 15.0, 7.0, 15.0),
               box(2.0, 7.0, 2.0, 14.0, 9.0, 14.0),
               box(9.0, 9.0, 3.0, 13.0, 16.0, 13.0),
               box(6.0, 9.0, 4.0, 9.0, 16.0, 12.0),
               box(4.0, 9.0, 4.0, 6.0, 12.0, 12.0)
            }
         )
         .optimize(),
      Shapes.or(
            box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0),
            new VoxelShape[]{
               box(1.0, 3.0, 1.0, 15.0, 7.0, 15.0),
               box(2.0, 7.0, 2.0, 14.0, 9.0, 14.0),
               box(3.0, 9.0, 9.0, 13.0, 16.0, 13.0),
               box(4.0, 9.0, 6.0, 12.0, 16.0, 9.0),
               box(4.0, 9.0, 4.0, 12.0, 12.0, 6.0)
            }
         )
         .optimize(),
      Shapes.or(
            box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0),
            new VoxelShape[]{
               box(1.0, 3.0, 1.0, 15.0, 7.0, 15.0),
               box(2.0, 7.0, 2.0, 14.0, 9.0, 14.0),
               box(3.0, 9.0, 3.0, 7.0, 16.0, 13.0),
               box(7.0, 9.0, 4.0, 10.0, 16.0, 12.0),
               box(10.0, 9.0, 4.0, 12.0, 12.0, 12.0)
            }
         )
         .optimize()
   };
   private static final VoxelShape[] UPPER_SHAPES = new VoxelShape[]{
      Shapes.or(box(3.0, 0.0, 3.0, 13.0, 7.0, 7.0), box(4.0, 0.0, 7.0, 12.0, 2.0, 9.0)).optimize(),
      Shapes.or(box(9.0, 0.0, 3.0, 13.0, 7.0, 13.0), box(7.0, 0.0, 4.0, 9.0, 2.0, 12.0)).optimize(),
      Shapes.or(box(3.0, 0.0, 9.0, 13.0, 7.0, 13.0), box(4.0, 0.0, 7.0, 12.0, 2.0, 9.0)).optimize(),
      Shapes.or(box(3.0, 0.0, 3.0, 7.0, 7.0, 13.0), box(7.0, 0.0, 4.0, 9.0, 2.0, 12.0)).optimize()
   };
   private final DyeColor color;

   public PortstoneBlock(DyeColor color, Properties properties) {
      super(properties);
      this.color = color;
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(HALF, DoubleBlockHalf.LOWER)).setValue(WATERLOGGED, false)
      );
   }

   public DyeColor getColor() {
      return this.color;
   }

   public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      Direction direction = (Direction)state.getValue(FACING);
      return state.getValue(HALF) == DoubleBlockHalf.UPPER ? UPPER_SHAPES[direction.get2DDataValue()] : LOWER_SHAPES[direction.get2DDataValue()];
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new PortstoneBlockEntity(pos, state);
   }

   @Override
   public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult blockHitResult) {
      if (!world.isClientSide) {
         ResourceLocation targetWaystoneType = this.getTargetWaystoneType();
         final Collection<Waystone> waystones = PlayerWaystoneManager.getTargetsForWaystoneType(player, targetWaystoneType);
         PlayerWaystoneManager.ensureSortingIndex(player, waystones);
         Balm.getNetworking()
            .openGui(
               player,
               new BalmMenuProvider<Collection<Waystone>>() {
                  public Component getDisplayName() {
                     return Component.translatable("container.waystones." + PortstoneBlock.this.color.getSerializedName() + "_portstone");
                  }

                  public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player playerx) {
                     return new WaystoneSelectionMenu(
                        (MenuType<WaystoneSelectionMenu>)ModMenus.portstoneSelection.get(), null, windowId, waystones, Set.of(TeleportFlags.PORTSTONE)
                     );
                  }

                  public Collection<Waystone> getScreenOpeningData(ServerPlayer serverPlayer) {
                     return waystones;
                  }

                  public StreamCodec<RegistryFriendlyByteBuf, Collection<Waystone>> getScreenStreamCodec() {
                     return WaystoneImpl.LIST_STREAM_CODEC;
                  }
               }
            );
      }

      return InteractionResult.SUCCESS;
   }

   private ResourceLocation getTargetWaystoneType() {
      return WaystoneTypes.getSharestone(this.color).orElse(WaystoneTypes.WAYSTONE);
   }

   @Override
   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{HALF});
   }

   public boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType) {
      return false;
   }

   @Override
   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flag) {
      MutableComponent component = Component.translatable("tooltip.waystones.portstone");
      component.withStyle(ChatFormatting.GRAY);
      list.add(component);
      super.appendHoverText(stack, context, list, flag);
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }
}
