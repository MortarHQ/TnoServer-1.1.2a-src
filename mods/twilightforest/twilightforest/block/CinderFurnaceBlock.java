package twilightforest.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.jetbrains.annotations.Nullable;
import twilightforest.block.entity.CinderFurnaceBlockEntity;
import twilightforest.init.TFBlockEntities;

public class CinderFurnaceBlock extends AbstractFurnaceBlock {
   public static final MapCodec<CinderFurnaceBlock> CODEC = simpleCodec(CinderFurnaceBlock::new);

   public CinderFurnaceBlock(Properties properties) {
      super(properties);
   }

   protected MapCodec<? extends AbstractFurnaceBlock> codec() {
      return CODEC;
   }

   protected void openContainer(Level level, BlockPos pos, Player player) {
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new CinderFurnaceBlockEntity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return createTickerHelper(type, (BlockEntityType)TFBlockEntities.CINDER_FURNACE.get(), CinderFurnaceBlockEntity::tick);
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      if ((Boolean)state.getValue(LIT)) {
         double d0 = pos.getX() + 0.5;
         double d1 = pos.getY();
         double d2 = pos.getZ() + 0.5;
         if (random.nextDouble() < 0.1) {
            level.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
         }

         Direction direction = (Direction)state.getValue(FACING);
         Axis direction$axis = direction.getAxis();
         double d4 = random.nextDouble() * 0.6 - 0.3;
         double d5 = direction$axis == Axis.X ? direction.getStepX() * 0.52 : d4;
         double d6 = random.nextDouble() * 6.0 / 16.0;
         double d7 = direction$axis == Axis.Z ? direction.getStepZ() * 0.52 : d4;
         level.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0, 0.0, 0.0);
         level.addParticle(ParticleTypes.FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0, 0.0, 0.0);
      }
   }
}
