package com.github.L_Ender.cataclysm.blocks;

import com.github.L_Ender.cataclysm.blockentities.SandstoneIgniteTrap_Block_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import com.mojang.serialization.MapCodec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
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

public class Sandstone_Ignite_Trap extends BaseEntityBlock {
   public static final MapCodec<Sandstone_Ignite_Trap> CODEC = simpleCodec(Sandstone_Ignite_Trap::new);
   public static final BooleanProperty LIT = BlockStateProperties.LIT;

   public Sandstone_Ignite_Trap(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)this.defaultBlockState().setValue(LIT, false));
   }

   public MapCodec<Sandstone_Ignite_Trap> codec() {
      return CODEC;
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
      activate(worldIn.getBlockState(pos), worldIn, pos, entityIn);
      super.stepOn(worldIn, pos, state, entityIn);
   }

   public RenderShape getRenderShape(BlockState p_149645_1_) {
      return RenderShape.MODEL;
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

   private static void activate(BlockState state, Level world, BlockPos pos, Entity entity) {
      if (!(Boolean)state.getValue(LIT) && shouldTrigger(entity)) {
         world.setBlock(pos, (BlockState)state.setValue(LIT, true), 3);
         world.playLocalSound(
            pos.getX() + 0.5,
            pos.getY() + 0.5,
            pos.getZ() + 0.5,
            (SoundEvent)ModSounds.FLAME_TRAP.get(),
            SoundSource.BLOCKS,
            1.0F + world.random.nextFloat(),
            world.random.nextFloat() * 0.7F + 0.3F,
            false
         );
      }
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new SandstoneIgniteTrap_Block_Entity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_152180_, BlockState p_152181_, BlockEntityType<T> p_152182_) {
      return createTickerHelper(p_152182_, (BlockEntityType)ModTileentites.SANDSTONE_IGNITE_TRAP.get(), SandstoneIgniteTrap_Block_Entity::commonTick);
   }
}
