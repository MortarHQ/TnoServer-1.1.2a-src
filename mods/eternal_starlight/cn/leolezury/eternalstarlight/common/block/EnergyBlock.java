package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESCriteriaTriggers;
import com.mojang.serialization.MapCodec;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;

public class EnergyBlock extends Block {
   public static final MapCodec<EnergyBlock> CODEC = simpleCodec(EnergyBlock::new);
   public static BooleanProperty LIT = BlockStateProperties.LIT;

   public EnergyBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(LIT, false));
   }

   protected MapCodec<EnergyBlock> codec() {
      return CODEC;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{LIT});
   }

   protected void onProjectileHit(Level level, BlockState blockState, BlockHitResult blockHitResult, Projectile projectile) {
      if ((Boolean)blockState.getValue(LIT) && blockHitResult.getType() != Type.MISS) {
         level.setBlockAndUpdate(blockHitResult.getBlockPos(), (BlockState)blockState.setValue(LIT, false));
      }

      if (projectile.getOwner() instanceof ServerPlayer serverPlayer) {
         ((PlayerTrigger)ESCriteriaTriggers.DEACTIVATE_ENERGY_BLOCK.get()).trigger(serverPlayer);
      }
   }

   protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
      if ((Boolean)blockState.getValue(LIT)) {
         level.setBlockAndUpdate(blockPos, (BlockState)blockState.setValue(LIT, false));
         if (player instanceof ServerPlayer serverPlayer) {
            ((PlayerTrigger)ESCriteriaTriggers.DEACTIVATE_ENERGY_BLOCK.get()).trigger(serverPlayer);
         }

         return InteractionResult.sidedSuccess(level.isClientSide);
      } else {
         return InteractionResult.PASS;
      }
   }
}
