package twilightforest.block;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFSounds;

public class VanishingBlock extends Block {
   public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
   public static final BooleanProperty VANISHED = BooleanProperty.create("vanished");
   private static final VoxelShape VANISHED_SHAPE = box(6.0, 6.0, 6.0, 10.0, 10.0, 10.0);

   public VanishingBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(ACTIVE, false));
   }

   private static boolean areBlocksLocked(BlockGetter getter, BlockPos start) {
      int limit = 512;
      Deque<BlockPos> queue = new ArrayDeque<>();
      Set<BlockPos> checked = new HashSet<>();
      queue.offer(start);

      for (int iter = 0; !queue.isEmpty() && iter < limit; iter++) {
         BlockPos cur = queue.pop();
         BlockState state = getter.getBlockState(cur);
         if (state.getBlock() == TFBlocks.LOCKED_VANISHING_BLOCK.get() && (Boolean)state.getValue(LockedVanishingBlock.LOCKED)) {
            return true;
         }

         checked.add(cur);
         if (state.getBlock() instanceof VanishingBlock) {
            for (Direction facing : Direction.values()) {
               BlockPos neighbor = cur.relative(facing);
               if (!checked.contains(neighbor)) {
                  queue.offer(neighbor);
               }
            }
         }
      }

      return false;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{ACTIVE});
   }

   private boolean isVanished(BlockState state) {
      return state.hasProperty(VANISHED) && (Boolean)state.getValue(VANISHED);
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext ctx) {
      return this.isVanished(state) ? VANISHED_SHAPE : super.getShape(state, getter, pos, ctx);
   }

   public VoxelShape getCollisionShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext ctx) {
      return this.isVanished(state) ? Shapes.empty() : super.getCollisionShape(state, getter, pos, ctx);
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
      if (!this.isVanished(state) && !(Boolean)state.getValue(ACTIVE)) {
         if (areBlocksLocked(level, pos)) {
            level.playSound(null, pos, (SoundEvent)TFSounds.LOCKED_VANISHING_BLOCK.get(), SoundSource.BLOCKS, 1.0F, 0.3F);
         } else {
            this.activate(level, pos);
         }

         return InteractionResult.sidedSuccess(level.isClientSide());
      } else {
         return InteractionResult.PASS;
      }
   }

   public float getExplosionResistance(BlockState state, BlockGetter getter, BlockPos pos, Explosion explosion) {
      return !state.getValue(ACTIVE) ? 6000.0F : super.getExplosionResistance(state, getter, pos, explosion);
   }

   public boolean canEntityDestroy(BlockState state, BlockGetter getter, BlockPos pos, Entity entity) {
      return !state.getValue(ACTIVE) ? !areBlocksLocked(getter, pos) : super.canEntityDestroy(state, getter, pos, entity);
   }

   public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
      if (!level.isClientSide()) {
         if (!this.isVanished(state) && !(Boolean)state.getValue(ACTIVE) && level.hasNeighborSignal(pos) && !areBlocksLocked(level, pos)) {
            this.activate(level, pos);
         }
      }
   }

   public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (!level.isClientSide()) {
         if (this.isVanished(state)) {
            if ((Boolean)state.getValue(ACTIVE)) {
               level.setBlockAndUpdate(pos, (BlockState)((BlockState)state.setValue(VANISHED, false)).setValue(ACTIVE, false));
               level.playSound(null, pos, (SoundEvent)TFSounds.REAPPEAR_BLOCK.get(), SoundSource.BLOCKS, 0.3F, 0.3F);
            } else {
               level.playSound(null, pos, (SoundEvent)TFSounds.REAPPEAR_POOF.get(), SoundSource.BLOCKS, 0.3F, 0.5F);
               level.setBlockAndUpdate(pos, (BlockState)state.setValue(ACTIVE, true));
               level.scheduleTick(pos, this, 15);
            }
         } else if ((Boolean)state.getValue(ACTIVE)) {
            if (state.hasProperty(VANISHED)) {
               level.setBlockAndUpdate(pos, (BlockState)((BlockState)state.setValue(ACTIVE, false)).setValue(VANISHED, true));
               level.scheduleTick(pos, this, 80);
            } else {
               level.removeBlock(pos, false);
            }

            level.playSound(
               null,
               pos,
               state.getBlock() == TFBlocks.REAPPEARING_BLOCK.get() ? (SoundEvent)TFSounds.REAPPEAR_POOF.get() : (SoundEvent)TFSounds.VANISHING_BLOCK.get(),
               SoundSource.BLOCKS,
               0.3F,
               0.5F
            );

            for (Direction e : Direction.values()) {
               this.activate(level, pos.relative(e));
            }
         }
      }
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      if ((Boolean)state.getValue(ACTIVE)) {
         this.sparkle(level, pos);
      }
   }

   public void sparkle(Level level, BlockPos pos) {
      RandomSource random = level.getRandom();

      for (Direction direction : Direction.values()) {
         BlockPos blockpos = pos.relative(direction);
         if (!level.getBlockState(blockpos).isSolidRender(level, blockpos)) {
            Axis axis = direction.getAxis();
            double d1 = axis == Axis.X ? 0.5 + 0.5625 * direction.getStepX() : random.nextFloat();
            double d2 = axis == Axis.Y ? 0.5 + 0.5625 * direction.getStepY() : random.nextFloat();
            double d3 = axis == Axis.Z ? 0.5 + 0.5625 * direction.getStepZ() : random.nextFloat();
            level.addParticle(DustParticleOptions.REDSTONE, pos.getX() + d1, pos.getY() + d2, pos.getZ() + d3, 0.0, 0.0, 0.0);
         }
      }
   }

   private void activate(Level level, BlockPos pos) {
      BlockState state = level.getBlockState(pos);
      if (state.getBlock() instanceof VanishingBlock && !this.isVanished(state) && !(Boolean)state.getValue(ACTIVE)) {
         level.setBlockAndUpdate(pos, (BlockState)state.setValue(ACTIVE, true));
         level.scheduleTick(pos, state.getBlock(), 2 + level.getRandom().nextInt(5));
      }
   }
}
