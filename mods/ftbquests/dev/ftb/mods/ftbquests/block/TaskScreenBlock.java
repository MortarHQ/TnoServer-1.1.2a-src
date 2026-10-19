package dev.ftb.mods.ftbquests.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftbquests.block.entity.ITaskScreen;
import dev.ftb.mods.ftbquests.block.entity.TaskScreenAuxBlockEntity;
import dev.ftb.mods.ftbquests.block.entity.TaskScreenBlockEntity;
import dev.ftb.mods.ftbquests.block.neoforge.TaskScreenBlockImpl;
import dev.ftb.mods.ftbquests.client.FTBQuestsClient;
import dev.ftb.mods.ftbquests.item.ScreenBlockItem;
import dev.ftb.mods.ftbquests.net.BlockConfigRequestMessage;
import dev.ftb.mods.ftbquests.quest.BaseQuestFile;
import dev.ftb.mods.ftbquests.quest.ServerQuestFile;
import dev.ftb.mods.ftbquests.quest.task.Task;
import dev.ftb.mods.ftbquests.registry.ModBlocks;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class TaskScreenBlock extends BaseEntityBlock {
   private static final MapCodec<TaskScreenBlock> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(propertiesCodec()).and(Codec.INT.fieldOf("size").forGetter(TaskScreenBlock::getSize)).apply(instance, TaskScreenBlock::new)
   );
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   public static final Properties PROPS = Properties.of().mapColor(DyeColor.BLACK).strength(0.3F);
   private final int size;

   public TaskScreenBlock(Properties props, int size) {
      super(props);
      this.size = size;
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(FACING, Direction.NORTH));
   }

   public int getSize() {
      return this.size;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{FACING});
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
      return blockEntityProvider().create(blockPos, blockState);
   }

   @ExpectPlatform
   @Transformed
   public static BlockEntitySupplier<TaskScreenBlockEntity> blockEntityProvider() {
      return TaskScreenBlockImpl.blockEntityProvider();
   }

   @ExpectPlatform
   @Transformed
   public static BlockEntitySupplier<TaskScreenAuxBlockEntity> blockEntityAuxProvider() {
      return TaskScreenBlockImpl.blockEntityAuxProvider();
   }

   protected MapCodec<? extends TaskScreenBlock> codec() {
      return CODEC;
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
      return !this.validatePlaceable(blockPlaceContext)
         ? null
         : (BlockState)super.getStateForPlacement(blockPlaceContext).setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
   }

   private boolean validatePlaceable(BlockPlaceContext ctx) {
      int screenSize = ScreenBlockItem.getSize(ctx.getItemInHand());
      if (screenSize == 1) {
         return true;
      }

      Direction facing = ctx.getHorizontalDirection();
      return BlockPos.betweenClosedStream(getMultiblockBounds(ctx.getClickedPos(), this.getSize(), facing))
         .allMatch(pos -> ctx.getLevel().getBlockState(pos).canBeReplaced(ctx));
   }

   public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
      super.setPlacedBy(level, blockPos, blockState, livingEntity, itemStack);
      if (level.getBlockEntity(blockPos) instanceof TaskScreenBlockEntity coreScreen) {
         if (livingEntity instanceof ServerPlayer sp) {
            ServerQuestFile.INSTANCE.getTeamData(sp).ifPresent(d -> coreScreen.setTeamId(d.getTeamId()));
         }

         Direction facing = (Direction)blockState.getValue(FACING);
         BlockState auxState = (BlockState)((Block)ModBlocks.AUX_SCREEN.get()).defaultBlockState().setValue(FACING, facing);
         BlockPos.betweenClosedStream(getMultiblockBounds(blockPos, this.getSize(), facing)).filter(pos -> !pos.equals(blockPos)).forEach(auxPos -> {
            level.setBlockAndUpdate(auxPos, auxState);
            if (level.getBlockEntity(auxPos) instanceof TaskScreenAuxBlockEntity auxScreen) {
               auxScreen.setCoreScreen(coreScreen);
            }
         });
      }
   }

   public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState newState, boolean isMoving) {
      if (blockState.getBlock() != newState.getBlock() && level.getBlockEntity(blockPos) instanceof ITaskScreen taskScreen) {
         taskScreen.getCoreScreen().ifPresent(coreScreen -> {
            coreScreen.removeAllAuxScreens();
            if (coreScreen != taskScreen) {
               level.destroyBlock(coreScreen.getBlockPos(), true, null);
            }
         });
         super.onRemove(blockState, level, blockPos, newState, isMoving);
      }
   }

   public float getDestroyProgress(BlockState blockState, Player player, BlockGetter blockGetter, BlockPos blockPos) {
      return player.level().getBlockEntity(blockPos) instanceof ITaskScreen taskScreen && taskScreen.isIndestructible()
         ? 0.0F
         : super.getDestroyProgress(blockState, player, blockGetter, blockPos);
   }

   protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
      if (player instanceof ServerPlayer sp && level.getBlockEntity(blockPos) instanceof ITaskScreen taskScreen) {
         if (!taskScreen.hasPermissionToEdit(sp)) {
            sp.displayClientMessage(Component.translatable("block.ftbquests.screen.no_permission").withStyle(ChatFormatting.RED), true);
            return InteractionResult.FAIL;
         }

         taskScreen.getCoreScreen()
            .ifPresent(
               coreScreen -> NetworkManager.sendToPlayer(
                  sp, new BlockConfigRequestMessage(coreScreen.getBlockPos(), BlockConfigRequestMessage.BlockType.TASK_SCREEN)
               )
            );
      }

      return InteractionResult.sidedSuccess(level.isClientSide);
   }

   public void appendHoverText(ItemStack itemStack, TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
      super.appendHoverText(itemStack, context, list, tooltipFlag);
      CustomData data = (CustomData)itemStack.get(DataComponents.BLOCK_ENTITY_DATA);
      if (data != null) {
         CompoundTag subTag = data.copyTag();
         BaseQuestFile questFile = FTBQuestsClient.getClientQuestFile();
         if (questFile != null) {
            Task task = questFile.getTask(subTag.getLong("TaskID"));
            if (task != null) {
               list.add(
                  Component.translatable("ftbquests.chapter")
                     .append(": ")
                     .append(task.getQuest().getChapter().getTitle().copy().withStyle(ChatFormatting.YELLOW))
               );
               list.add(Component.translatable("ftbquests.quest").append(": ").append(task.getQuest().getMutableTitle().withStyle(ChatFormatting.YELLOW)));
               list.add(Component.translatable("ftbquests.task").append(": ").append(task.getMutableTitle().withStyle(ChatFormatting.YELLOW)));
            }
         }
      }
   }

   public static BoundingBox getMultiblockBounds(BlockPos corePos, int size, Direction facing) {
      if (size == 1) {
         return new BoundingBox(corePos);
      }

      int size2 = size / 2;
      facing = facing.getCounterClockWise();
      BlockPos pos1 = new BlockPos(corePos.getX() - size2 * facing.getStepX(), corePos.getY(), corePos.getZ() - size2 * facing.getStepZ());
      BlockPos pos2 = new BlockPos(corePos.getX() + size2 * facing.getStepX(), corePos.getY() + size - 1, corePos.getZ() + size2 * facing.getStepZ());
      return BoundingBox.fromCorners(pos1, pos2);
   }

   public static class Aux extends TaskScreenBlock {
      private static final MapCodec<TaskScreenBlock.Aux> CODEC = simpleCodec(TaskScreenBlock.Aux::new);

      public Aux(Properties props) {
         super(props, 0);
      }

      @Nullable
      @Override
      public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
         return blockEntityAuxProvider().create(blockPos, blockState);
      }

      @Override
      protected MapCodec<TaskScreenBlock.Aux> codec() {
         return CODEC;
      }
   }
}
