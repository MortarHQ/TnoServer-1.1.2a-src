package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.block.entity.TorreyaCampfireBlockEntity;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class TorreyaCampfireBlock extends CampfireBlock {
   public static final BooleanProperty STARFIRE = BooleanProperty.create("starfire");

   public TorreyaCampfireBlock(boolean spawnParticles, int fireDamage, Properties properties) {
      super(spawnParticles, fireDamage, properties);
      this.registerDefaultState((BlockState)this.defaultBlockState().setValue(STARFIRE, false));
   }

   public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
      return new TorreyaCampfireBlockEntity(blockPos, blockState);
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      if (stack.is(ESItems.STARFIRE.get()) && !(Boolean)state.getValue(STARFIRE) && (Boolean)state.getValue(LIT)) {
         level.setBlockAndUpdate(pos, (BlockState)state.setValue(STARFIRE, true));
         return ItemInteractionResult.sidedSuccess(level.isClientSide);
      } else {
         return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
      }
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
      if (level.isClientSide) {
         return blockState.getValue(LIT)
            ? createTickerHelper(blockEntityType, ESBlockEntities.TORREYA_CAMPFIRE.get(), CampfireBlockEntity::particleTick)
            : null;
      } else {
         return createTickerHelper(blockEntityType, ESBlockEntities.TORREYA_CAMPFIRE.get(), TorreyaCampfireBlockEntity::serverTick);
      }
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{STARFIRE});
   }
}
