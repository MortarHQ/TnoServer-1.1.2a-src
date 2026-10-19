package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.network.PacketDistributor;
import twilightforest.init.TFParticleType;
import twilightforest.init.TFSounds;
import twilightforest.network.ParticlePacket;

public class CastleDoorBlock extends Block {
   public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
   public static final BooleanProperty VANISHED = BooleanProperty.create("vanish");
   private static final VoxelShape REAPPEARING_BB = Shapes.create(new AABB(0.375, 0.375, 0.375, 0.625, 0.625, 0.625));

   public CastleDoorBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(ACTIVE, false)).setValue(VANISHED, false));
   }

   private static boolean isBlockLocked(Level level, BlockPos pos) {
      return false;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{ACTIVE, VANISHED});
   }

   public boolean skipRendering(BlockState state, BlockState otherState, Direction direction) {
      return otherState.getBlock() instanceof CastleDoorBlock && otherState.getValue(VANISHED) == state.getValue(VANISHED);
   }

   public VoxelShape getOcclusionShape(BlockState state, BlockGetter getter, BlockPos pos) {
      return !state.getValue(VANISHED) && state.getValue(ACTIVE) ? super.getOcclusionShape(state, getter, pos) : Shapes.empty();
   }

   public VoxelShape getCollisionShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      return state.getValue(VANISHED) ? Shapes.empty() : super.getCollisionShape(state, getter, pos, context);
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      return state.getValue(VANISHED) ? REAPPEARING_BB : super.getShape(state, getter, pos, context);
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
      return this.onActivation(level, pos, state);
   }

   public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
      if (!(block instanceof CastleDoorBlock) && level.hasNeighborSignal(pos)) {
         this.onActivation(level, pos, state);
      }
   }

   private InteractionResult onActivation(Level level, BlockPos pos, BlockState state) {
      if ((Boolean)state.getValue(VANISHED) || (Boolean)state.getValue(ACTIVE)) {
         return InteractionResult.FAIL;
      } else if (isBlockLocked(level, pos)) {
         level.playSound(null, pos, (SoundEvent)TFSounds.DOOR_ACTIVATED.get(), SoundSource.BLOCKS, 1.0F, 0.3F);
         return InteractionResult.PASS;
      } else {
         this.changeToActiveBlock(level, pos, state);
         return InteractionResult.SUCCESS;
      }
   }

   private void changeToActiveBlock(Level level, BlockPos pos, BlockState originState) {
      if (originState.getBlock() instanceof CastleDoorBlock) {
         level.setBlockAndUpdate(pos, (BlockState)originState.setValue(ACTIVE, true));
      }

      level.scheduleTick(pos, originState.getBlock(), 2 + level.getRandom().nextInt(5));
   }

   public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if ((Boolean)state.getValue(VANISHED)) {
         if ((Boolean)state.getValue(ACTIVE)) {
            level.setBlockAndUpdate(pos, (BlockState)((BlockState)state.setValue(VANISHED, false)).setValue(ACTIVE, false));
         } else {
            this.changeToActiveBlock(level, pos, state);
         }

         this.playReappearSound(level, pos);
      } else if ((Boolean)state.getValue(ACTIVE)) {
         level.setBlockAndUpdate(pos, (BlockState)((BlockState)state.setValue(VANISHED, true)).setValue(ACTIVE, false));
         level.scheduleTick(pos, this, 80);
         this.playVanishSound(level, pos);
         this.vanishParticles(level, pos);

         for (Direction e : Direction.values()) {
            this.checkAndActivateCastleDoor(level, pos.relative(e));
         }
      }
   }

   private void playVanishSound(Level level, BlockPos pos) {
      level.playSound(null, pos, (SoundEvent)TFSounds.DOOR_VANISH.get(), SoundSource.BLOCKS, 0.125F, level.getRandom().nextFloat() * 0.25F + 1.75F);
   }

   private void playReappearSound(Level level, BlockPos pos) {
      level.playSound(null, pos, (SoundEvent)TFSounds.DOOR_REAPPEAR.get(), SoundSource.BLOCKS, 0.125F, level.getRandom().nextFloat() * 0.25F + 1.25F);
   }

   public void checkAndActivateCastleDoor(Level level, BlockPos pos) {
      BlockState state = level.getBlockState(pos);
      if (state.getBlock() instanceof CastleDoorBlock && !(Boolean)state.getValue(VANISHED) && !(Boolean)state.getValue(ACTIVE) && !isBlockLocked(level, pos)) {
         this.changeToActiveBlock(level, pos, state);
      }
   }

   private void vanishParticles(Level level, BlockPos pos) {
      if (level instanceof ServerLevel serverLevel) {
         RandomSource rand = level.getRandom();
         ParticlePacket particlePacket = new ParticlePacket();

         for (int dx = 0; dx < 4; dx++) {
            for (int dy = 0; dy < 4; dy++) {
               for (int dz = 0; dz < 4; dz++) {
                  particlePacket.queueParticle(
                     (ParticleOptions)TFParticleType.ANNIHILATE.get(),
                     false,
                     pos.getX() + (dx + 0.5) / 4.0,
                     pos.getY() + (dy + 0.5) / 4.0,
                     pos.getZ() + (dz + 0.5) / 4.0,
                     rand.nextGaussian() * 0.2,
                     rand.nextGaussian() * 0.2,
                     rand.nextGaussian() * 0.2
                  );
               }
            }
         }

         PacketDistributor.sendToPlayersNear(serverLevel, null, pos.getX(), pos.getY(), pos.getZ(), 32.0, particlePacket, new CustomPacketPayload[0]);
      }
   }
}
