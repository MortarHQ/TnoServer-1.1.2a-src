package com.benji.netherman.common.block;

import com.benji.netherman.init.ModSounds;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EntranceBlock extends Block {
   public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, 3);
   public static final BooleanProperty CLOSING = BooleanProperty.create("closing");
   private static final VoxelShape SHAPE_0 = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape SHAPE_1 = Block.box(2.0, 2.0, 2.0, 14.0, 14.0, 14.0);
   private static final VoxelShape SHAPE_2 = Block.box(5.0, 5.0, 5.0, 11.0, 11.0, 11.0);

   public EntranceBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(STAGE, 0)).setValue(CLOSING, false));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{STAGE, CLOSING});
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return switch (state.getValue(STAGE)) {
         case 1 -> SHAPE_1;
         case 2 -> SHAPE_2;
         case 3 -> Shapes.empty();
         default -> SHAPE_0;
      };
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
      if (!level.isClientSide()) {
         if ((Integer)state.getValue(STAGE) != 0) {
            return InteractionResult.PASS;
         }

         Map<BlockPos, Integer> distances = new HashMap<>();
         Queue<BlockPos> queue = new LinkedList<>();
         queue.add(pos);
         distances.put(pos, 0);

         while (!queue.isEmpty() && distances.size() < 1000) {
            BlockPos current = queue.poll();
            int currentDist = distances.get(current);

            for (Direction dir : Direction.values()) {
               BlockPos neighbor = current.relative(dir);
               if (!distances.containsKey(neighbor) && level.getBlockState(neighbor).is(this)) {
                  distances.put(neighbor, currentDist + 1);
                  queue.add(neighbor);
               }
            }
         }

         for (Entry<BlockPos, Integer> entry : distances.entrySet()) {
            BlockPos targetPos = entry.getKey();
            int distance = entry.getValue();
            int delay = (int)(Math.pow(distance, 0.8) * 3.0);
            level.scheduleTick(targetPos, this, Math.max(1, delay));
         }
      }

      return InteractionResult.SUCCESS;
   }

   protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      int currentStage = (Integer)state.getValue(STAGE);
      boolean isClosing = (Boolean)state.getValue(CLOSING);
      if (!isClosing) {
         if (currentStage == 0) {
            level.playSound(null, pos, (SoundEvent)ModSounds.ENTRANCE.get(), SoundSource.BLOCKS, 1.0F, 0.9F + random.nextFloat() * 0.2F);
            level.setBlock(pos, (BlockState)state.setValue(STAGE, 1), 3);
            level.scheduleTick(pos, this, 3);
         } else if (currentStage == 1) {
            level.setBlock(pos, (BlockState)state.setValue(STAGE, 2), 3);
            level.scheduleTick(pos, this, 3);
         } else if (currentStage == 2) {
            level.setBlock(pos, (BlockState)state.setValue(STAGE, 3), 3);
            level.scheduleTick(pos, this, 100);
            level.setBlock(pos, (BlockState)level.getBlockState(pos).setValue(CLOSING, true), 3);
         }
      } else if (currentStage == 3) {
         level.playSound(null, pos, (SoundEvent)ModSounds.ENTRANCE.get(), SoundSource.BLOCKS, 1.0F, 0.9F + random.nextFloat() * 0.2F);
         level.setBlock(pos, (BlockState)state.setValue(STAGE, 2), 3);
         level.scheduleTick(pos, this, 3);
      } else if (currentStage == 2) {
         level.setBlock(pos, (BlockState)state.setValue(STAGE, 1), 3);
         level.scheduleTick(pos, this, 3);
      } else if (currentStage == 1) {
         level.setBlock(pos, (BlockState)((BlockState)state.setValue(STAGE, 0)).setValue(CLOSING, false), 3);
      }
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      int stage = (Integer)state.getValue(STAGE);
      if (stage == 1 || stage == 2) {
         for (int i = 0; i < 2; i++) {
            double px = pos.getX() + random.nextDouble();
            double py = pos.getY() + random.nextDouble();
            double pz = pos.getZ() + random.nextDouble();
            level.addParticle(DustParticleOptions.REDSTONE, px, py, pz, 0.0, 0.05, 0.0);
         }
      }
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }
}
