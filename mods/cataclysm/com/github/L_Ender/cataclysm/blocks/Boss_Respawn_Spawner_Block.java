package com.github.L_Ender.cataclysm.blocks;

import com.github.L_Ender.cataclysm.blockentities.Boss_Respawn_Spawner_Block_Entity;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import com.mojang.serialization.MapCodec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.phys.BlockHitResult;

public class Boss_Respawn_Spawner_Block extends BaseEntityBlock {
   public static final MapCodec<Boss_Respawn_Spawner_Block> CODEC = simpleCodec(Boss_Respawn_Spawner_Block::new);
   public static final BooleanProperty LIT = BlockStateProperties.LIT;

   public MapCodec<Boss_Respawn_Spawner_Block> codec() {
      return CODEC;
   }

   public Boss_Respawn_Spawner_Block(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(LIT, false));
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new Boss_Respawn_Spawner_Block_Entity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_152180_, BlockState p_152181_, BlockEntityType<T> p_152182_) {
      return createTickerHelper(p_152182_, (BlockEntityType)ModTileentites.BOSS_RESPAWNER.get(), Boss_Respawn_Spawner_Block_Entity::tick);
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      if (!(Boolean)state.getValue(LIT)) {
         if (!level.isClientSide && level.getBlockEntity(pos) instanceof Boss_Respawn_Spawner_Block_Entity spawnerBlockEntitylockentity) {
            ItemStack itemstack = spawnerBlockEntitylockentity.getTheItem();
            if (stack.is(itemstack.getItem())) {
               stack.consume(1, player);
               spawnerBlockEntitylockentity.onHit(level);
               level.gameEvent(GameEvent.BLOCK_CHANGE, pos, Context.of(player, state));
            }
         }

         return ItemInteractionResult.sidedSuccess(level.isClientSide);
      } else {
         return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
      }
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> p_48814_) {
      p_48814_.add(new Property[]{LIT});
   }

   protected RenderShape getRenderShape(BlockState state) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }
}
