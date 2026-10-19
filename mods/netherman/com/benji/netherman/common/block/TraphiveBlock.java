package com.benji.netherman.common.block;

import com.benji.netherman.common.block.entity.TraphiveBlockEntity;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class TraphiveBlock extends Block implements EntityBlock {
   public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
   private static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);

   public TraphiveBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(OPEN, false));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{OPEN});
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return state.getValue(OPEN) ? Shapes.empty() : SHAPE;
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
      return InteractionResult.PASS;
   }

   public void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
      if (!level.isClientSide()) {
         this.activateWave(level, hit.getBlockPos());
      }
   }

   public void activateWave(Level level, BlockPos pos) {
      if (!(Boolean)level.getBlockState(pos).getValue(OPEN)) {
         Map<BlockPos, Integer> distances = new HashMap<>();
         Queue<BlockPos> queue = new LinkedList<>();
         queue.add(pos);
         distances.put(pos, 0);
         int maxDist = 0;

         while (!queue.isEmpty() && distances.size() < 1000) {
            BlockPos current = queue.poll();
            int currentDist = distances.get(current);
            maxDist = Math.max(maxDist, currentDist);

            for (Direction dir : Direction.values()) {
               BlockPos neighbor = current.relative(dir);
               if (!distances.containsKey(neighbor) && level.getBlockState(neighbor).is(this)) {
                  distances.put(neighbor, currentDist + 1);
                  queue.add(neighbor);
               }
            }
         }

         for (Entry<BlockPos, Integer> entry : distances.entrySet()) {
            if (level.getBlockEntity(entry.getKey()) instanceof TraphiveBlockEntity be) {
               be.triggerWave(entry.getValue(), maxDist);
            }
         }
      }
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new TraphiveBlockEntity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return (lvl, p, st, entity) -> {
         if (entity instanceof TraphiveBlockEntity be) {
            TraphiveBlockEntity.tick(lvl, p, st, be);
         }
      };
   }
}
