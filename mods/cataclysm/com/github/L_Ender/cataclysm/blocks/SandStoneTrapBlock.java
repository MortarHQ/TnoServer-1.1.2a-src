package com.github.L_Ender.cataclysm.blocks;

import com.github.L_Ender.cataclysm.init.ModTag;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class SandStoneTrapBlock extends Block {
   public static final BooleanProperty LIT = BlockStateProperties.LIT;

   public SandStoneTrapBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)this.defaultBlockState().setValue(LIT, false));
   }

   public boolean isRandomlyTicking(BlockState state) {
      return (Boolean)state.getValue(LIT);
   }

   public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
      if ((Boolean)state.getValue(LIT)) {
         worldIn.setBlock(pos, (BlockState)state.setValue(LIT, false), 3);
      }
   }

   public void stepOn(Level worldIn, BlockPos pos, BlockState state, Entity entityIn) {
      super.stepOn(worldIn, pos, state, entityIn);
   }

   @OnlyIn(Dist.CLIENT)
   public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
   }

   public static boolean shouldTrigger(Entity entity) {
      if (!(entity instanceof LivingEntity) || entity.getType().is(ModTag.SANDSTONE_TRAP_NOT_DETECTED)) {
         return false;
      } else {
         return !(entity instanceof Player) ? !(entity instanceof ArmorStand) : !((Player)entity).isCreative() && !entity.isSpectator();
      }
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{LIT});
   }
}
