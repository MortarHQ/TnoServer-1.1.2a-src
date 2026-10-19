package com.chronodawn.blocks;

import com.chronodawn.ChronoDawn;
import com.chronodawn.entities.bosses.EntropyKeeperEntity;
import com.chronodawn.registry.ModEntities;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;

public class EntropyCryptTrapdoorBlock extends TrapDoorBlock {
   public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");
   private static final Set<BlockPos> spawnedPositions = ConcurrentHashMap.newKeySet();
   private static final BlockSetType ENTROPY_CRYPT_TYPE = BlockSetType.IRON;

   public EntropyCryptTrapdoorBlock(Properties properties) {
      super(ENTROPY_CRYPT_TYPE, properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any())
                           .setValue(FACING, Direction.NORTH))
                        .setValue(OPEN, false))
                     .setValue(HALF, Half.BOTTOM))
                  .setValue(POWERED, false))
               .setValue(WATERLOGGED, false))
            .setValue(ACTIVATED, false)
      );
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{ACTIVATED});
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
      if (!(Boolean)state.getValue(ACTIVATED)) {
         if (!level.isClientSide) {
            if (spawnedPositions.contains(pos)) {
               level.setBlock(pos, (BlockState)state.setValue(ACTIVATED, true), 3);
               return super.useWithoutItem((BlockState)state.setValue(ACTIVATED, true), level, pos, player, hitResult);
            } else {
               boolean spawned = this.spawnEntropyKeeper((ServerLevel)level, pos, player);
               if (spawned) {
                  level.setBlock(pos, (BlockState)state.setValue(ACTIVATED, true), 3);
                  spawnedPositions.add(pos.immutable());
                  player.displayClientMessage(Component.translatable("message.chronodawn.entropy_keeper_awakens"), true);
                  ChronoDawn.LOGGER.debug("Entropy Keeper spawned from trapdoor at {} by player {}", pos, player.getName().getString());
                  return InteractionResult.SUCCESS;
               } else {
                  ChronoDawn.LOGGER.error("Failed to spawn Entropy Keeper at {}", pos);
                  return InteractionResult.FAIL;
               }
            }
         } else {
            return InteractionResult.SUCCESS;
         }
      } else {
         if (!level.isClientSide) {
            state = (BlockState)state.cycle(OPEN);
            level.setBlock(pos, state, 10);
            this.playToggleSound(player, level, pos, (Boolean)state.getValue(OPEN));
         }

         return InteractionResult.SUCCESS;
      }
   }

   private void playToggleSound(Player player, Level level, BlockPos pos, boolean open) {
      level.playSound(
         player,
         pos,
         open ? ENTROPY_CRYPT_TYPE.trapdoorOpen() : ENTROPY_CRYPT_TYPE.trapdoorClose(),
         SoundSource.BLOCKS,
         1.0F,
         level.getRandom().nextFloat() * 0.1F + 0.9F
      );
   }

   private boolean spawnEntropyKeeper(ServerLevel level, BlockPos trapdoorPos, Player player) {
      BlockPos spawnPos = this.findSpawnPosition(level, trapdoorPos);
      if (spawnPos == null) {
         ChronoDawn.LOGGER.warn("Could not find valid spawn position for Entropy Keeper near {}", trapdoorPos);
         return false;
      } else {
         EntropyKeeperEntity keeper = (EntropyKeeperEntity)((EntityType)ModEntities.ENTROPY_KEEPER.get()).create(level);
         if (keeper != null) {
            keeper.moveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, 0.0F, 0.0F);
            double dx = player.getX() - spawnPos.getX();
            double dz = player.getZ() - spawnPos.getZ();
            float yaw = (float)(Math.atan2(dz, dx) * (180.0 / Math.PI)) - 90.0F;
            keeper.setYRot(yaw);
            keeper.setYHeadRot(yaw);
            keeper.finalizeSpawn(level, level.getCurrentDifficultyAt(spawnPos), MobSpawnType.TRIGGERED, null);
            return level.addFreshEntity(keeper);
         } else {
            ChronoDawn.LOGGER.error("Failed to create Entropy Keeper entity");
            return false;
         }
      }
   }

   private BlockPos findSpawnPosition(ServerLevel level, BlockPos trapdoorPos) {
      int[] offsets = new int[]{3, -3, 4, -4, 5, -5};

      for (int xOff : offsets) {
         for (int zOff : offsets) {
            BlockPos checkPos = trapdoorPos.offset(xOff, 0, zOff);

            for (int y = 2; y >= -2; y--) {
               BlockPos testPos = checkPos.above(y);
               BlockPos belowPos = testPos.below();
               if (level.getBlockState(testPos).isAir() && level.getBlockState(testPos.above()).isAir() && !level.getBlockState(belowPos).isAir()) {
                  return testPos;
               }
            }
         }
      }

      return trapdoorPos.above(2);
   }

   public static void resetSpawnTracking() {
      spawnedPositions.clear();
   }
}
