package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.data.ESCrests;
import cn.leolezury.eternalstarlight.common.entity.misc.CrestEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class EnchantedGrimstoneBricksBlock extends HorizontalDirectionalBlock {
   public static final MapCodec<EnchantedGrimstoneBricksBlock> CODEC = simpleCodec(EnchantedGrimstoneBricksBlock::new);

   public EnchantedGrimstoneBricksBlock(Properties properties) {
      super(properties);
   }

   protected MapCodec<EnchantedGrimstoneBricksBlock> codec() {
      return CODEC;
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
      return (BlockState)this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
   }

   protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
      player.displayClientMessage(Component.translatable("message.eternal_starlight.enchanted_grimstone_pre"), true);
      player.displayClientMessage(Component.translatable("message.eternal_starlight.enchanted_grimstone_pre"), true);
      return InteractionResult.sidedSuccess(level.isClientSide);
   }

   public BlockState playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {
      player.displayClientMessage(Component.translatable("message.eternal_starlight.enchanted_grimstone_post"), true);
      CrestEntity crest = new CrestEntity(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ESCrests.BOULDERS_SHIELD);
      level.addFreshEntity(crest);
      return super.playerWillDestroy(level, blockPos, blockState, player);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING});
   }
}
