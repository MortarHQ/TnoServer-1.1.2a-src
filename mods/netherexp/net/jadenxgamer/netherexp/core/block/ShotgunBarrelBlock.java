package net.jadenxgamer.netherexp.core.block;

import com.mojang.serialization.MapCodec;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.entity.ShotgunPellet;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.jadenxgamer.netherexp.util.VFXHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.easing.Easing;

public class ShotgunBarrelBlock extends HorizontalDirectionalBlock {
   public static final MapCodec<JNEHorizontalDirectionalBlock> CODEC = simpleCodec(JNEHorizontalDirectionalBlock::new);
   public static final BooleanProperty TRIGGERED = BooleanProperty.create("triggered");

   public ShotgunBarrelBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.defaultBlockState().setValue(TRIGGERED, false)).setValue(FACING, Direction.NORTH));
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
   }

   protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
      boolean powered = level.hasNeighborSignal(pos);
      boolean triggered = (Boolean)state.getValue(TRIGGERED);
      if (powered && !triggered) {
         level.scheduleTick(pos, this, 4);
         level.setBlock(pos, (BlockState)state.setValue(TRIGGERED, true), 4);
      } else if (!powered && triggered) {
         level.playSound(null, pos, JNESoundEvents.SHOTGUN_LOAD.get(), SoundSource.BLOCKS, 0.6F, 1.0F);
         level.setBlock(pos, (BlockState)state.setValue(TRIGGERED, false), 4);
      }
   }

   protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      Direction direction = (Direction)state.getValue(FACING);
      level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), JNESoundEvents.SHOTGUN_USE.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
      int count = (Integer)JNEConfigs.SHOTGUN_BARREL_BULLETS.get();
      VFXHelper.shotgunScreenShake(new Vec3(pos.getX(), pos.getY(), pos.getZ()), 8.0F, Easing.LINEAR);

      for (int i = 0; i < count; i++) {
         ShotgunPellet pellet = new ShotgunPellet(
            pos.relative(direction).getX() + 0.5, pos.relative(direction).getY() + 0.5, pos.relative(direction).getZ() + 0.5, level
         );
         pellet.shoot(direction.getStepX(), direction.getStepY(), direction.getStepZ(), 1.0F, 16.0F);
         level.addFreshEntity(pellet);
      }
   }

   protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
      return CODEC;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, TRIGGERED});
   }
}
