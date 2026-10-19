package com.tom.storagemod.block;

import com.mojang.serialization.MapCodec;
import com.tom.storagemod.Content;
import com.tom.storagemod.block.entity.PaintedBlockEntity;
import com.tom.storagemod.client.ClientUtil;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PaintedTrimBlock extends BaseEntityBlock implements IPaintable, IConfiguratorHighlight {
   public static final MapCodec<PaintedTrimBlock> CODEC = ChestBlock.simpleCodec(properties -> new PaintedTrimBlock());

   public PaintedTrimBlock() {
      super(Properties.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(3.0F));
   }

   public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
      tooltip.add(Component.translatable("tooltip.toms_storage.paintable"));
      ClientUtil.tooltip("trim", tooltip);
   }

   protected ItemInteractionResult useItemOn(
      ItemStack item, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult p_316140_
   ) {
      return this.tryScrape(item, state, world, pos, player, hand)
         ? ItemInteractionResult.sidedSuccess(world.isClientSide())
         : ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
   }

   @Override
   public boolean paint(Level world, BlockPos pos, BlockState to) {
      BlockEntity te = world.getBlockEntity(pos);
      return te != null && te instanceof PaintedBlockEntity ? ((PaintedBlockEntity)te).setPaintedBlockState(to) : false;
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new PaintedBlockEntity(pos, state);
   }

   public RenderShape getRenderShape(BlockState p_149645_1_) {
      return RenderShape.MODEL;
   }

   public ItemStack getCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
      return new ItemStack((ItemLike)Content.inventoryTrim.get());
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   @Override
   public int getHighlightColor() {
      return 8947848;
   }

   @Override
   public VoxelShape getHighlightShape(BlockState state, BlockGetter level, BlockPos pos) {
      return Shapes.block();
   }
}
