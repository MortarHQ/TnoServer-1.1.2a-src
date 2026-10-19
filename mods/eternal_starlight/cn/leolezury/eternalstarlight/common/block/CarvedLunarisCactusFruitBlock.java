package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.entity.living.AethersentGolem;
import cn.leolezury.eternalstarlight.common.entity.living.GrimstoneGolem;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import com.mojang.serialization.MapCodec;
import java.util.function.Predicate;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.pattern.BlockPattern.BlockPatternMatch;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;

public class CarvedLunarisCactusFruitBlock extends HorizontalDirectionalBlock {
   public static final MapCodec<CarvedLunarisCactusFruitBlock> CODEC = simpleCodec(CarvedLunarisCactusFruitBlock::new);
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   @Nullable
   private BlockPattern snowGolemBase;
   @Nullable
   private BlockPattern snowGolemFull;
   @Nullable
   private BlockPattern ironGolemBase;
   @Nullable
   private BlockPattern ironGolemFull;
   @Nullable
   private BlockPattern grimstoneGolemBase;
   @Nullable
   private BlockPattern grimstoneGolemFull;
   @Nullable
   private BlockPattern aethersentGolemBase;
   @Nullable
   private BlockPattern aethersentGolemFull;
   private static final Predicate<BlockState> LUNARIS_CACTUS_FRUIT_PREDICATE = state -> state != null
      && (state.is((Block)ESBlocks.CARVED_LUNARIS_CACTUS_FRUIT.get()) || state.is((Block)ESBlocks.LUNARIS_CACTUS_FRUIT_LANTERN.get()));

   public CarvedLunarisCactusFruitBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH));
   }

   public MapCodec<? extends CarvedLunarisCactusFruitBlock> codec() {
      return CODEC;
   }

   protected void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
      if (!blockState2.is(blockState.getBlock())) {
         this.trySpawnGolem(level, blockPos);
      }
   }

   public boolean canSpawnGolem(LevelReader levelReader, BlockPos blockPos) {
      return this.getOrCreateSnowGolemBase().find(levelReader, blockPos) != null
         || this.getOrCreateIronGolemBase().find(levelReader, blockPos) != null
         || this.getOrCreateGrimstoneGolemBase().find(levelReader, blockPos) != null
         || this.getOrCreateAethersentGolemBase().find(levelReader, blockPos) != null;
   }

   private void trySpawnGolem(Level level, BlockPos blockPos) {
      BlockPatternMatch snowGolemMatch = this.getOrCreateSnowGolemFull().find(level, blockPos);
      BlockPatternMatch ironGolemMatch = this.getOrCreateIronGolemFull().find(level, blockPos);
      BlockPatternMatch grimstoneGolemMatch = this.getOrCreateGrimstoneGolemFull().find(level, blockPos);
      BlockPatternMatch aethersentGolemMatch = this.getOrCreateAethersentGolemFull().find(level, blockPos);
      if (snowGolemMatch != null) {
         SnowGolem snowGolem = (SnowGolem)EntityType.SNOW_GOLEM.create(level);
         if (snowGolem != null) {
            spawnGolemInWorld(level, snowGolemMatch, snowGolem, snowGolemMatch.getBlock(0, 2, 0).getPos());
         }
      } else if (ironGolemMatch != null) {
         IronGolem ironGolem = (IronGolem)EntityType.IRON_GOLEM.create(level);
         if (ironGolem != null) {
            ironGolem.setPlayerCreated(true);
            spawnGolemInWorld(level, ironGolemMatch, ironGolem, ironGolemMatch.getBlock(1, 2, 0).getPos());
         }
      } else if (grimstoneGolemMatch != null) {
         GrimstoneGolem grimstoneGolem = (GrimstoneGolem)ESEntities.GRIMSTONE_GOLEM.get().create(level);
         if (grimstoneGolem != null) {
            spawnGolemInWorld(level, grimstoneGolemMatch, grimstoneGolem, grimstoneGolemMatch.getBlock(0, 1, 0).getPos());
         }
      } else if (aethersentGolemMatch != null) {
         AethersentGolem aethersentGolem = (AethersentGolem)ESEntities.AETHERSENT_GOLEM.get().create(level);
         if (aethersentGolem != null) {
            spawnGolemInWorld(level, aethersentGolemMatch, aethersentGolem, aethersentGolemMatch.getBlock(0, 1, 0).getPos());
         }
      }
   }

   private static void spawnGolemInWorld(Level level, BlockPatternMatch blockPatternMatch, Entity entity, BlockPos blockPos) {
      clearPatternBlocks(level, blockPatternMatch);
      entity.moveTo(blockPos.getX() + 0.5, blockPos.getY() + 0.05, blockPos.getZ() + 0.5, 0.0F, 0.0F);
      level.addFreshEntity(entity);

      for (ServerPlayer serverPlayer : level.getEntitiesOfClass(ServerPlayer.class, entity.getBoundingBox().inflate(5.0))) {
         CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayer, entity);
      }

      updatePatternBlocks(level, blockPatternMatch);
   }

   public static void clearPatternBlocks(Level level, BlockPatternMatch blockPatternMatch) {
      for (int i = 0; i < blockPatternMatch.getWidth(); i++) {
         for (int j = 0; j < blockPatternMatch.getHeight(); j++) {
            BlockInWorld blockInWorld = blockPatternMatch.getBlock(i, j, 0);
            level.setBlock(blockInWorld.getPos(), Blocks.AIR.defaultBlockState(), 2);
            level.levelEvent(2001, blockInWorld.getPos(), Block.getId(blockInWorld.getState()));
         }
      }
   }

   public static void updatePatternBlocks(Level level, BlockPatternMatch blockPatternMatch) {
      for (int i = 0; i < blockPatternMatch.getWidth(); i++) {
         for (int j = 0; j < blockPatternMatch.getHeight(); j++) {
            BlockInWorld blockInWorld = blockPatternMatch.getBlock(i, j, 0);
            level.blockUpdated(blockInWorld.getPos(), Blocks.AIR);
         }
      }
   }

   public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
      return (BlockState)this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING});
   }

   private BlockPattern getOrCreateSnowGolemBase() {
      if (this.snowGolemBase == null) {
         this.snowGolemBase = BlockPatternBuilder.start()
            .aisle(new String[]{" ", "#", "#"})
            .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK)))
            .build();
      }

      return this.snowGolemBase;
   }

   private BlockPattern getOrCreateSnowGolemFull() {
      if (this.snowGolemFull == null) {
         this.snowGolemFull = BlockPatternBuilder.start()
            .aisle(new String[]{"^", "#", "#"})
            .where('^', BlockInWorld.hasState(LUNARIS_CACTUS_FRUIT_PREDICATE))
            .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK)))
            .build();
      }

      return this.snowGolemFull;
   }

   private BlockPattern getOrCreateIronGolemBase() {
      if (this.ironGolemBase == null) {
         this.ironGolemBase = BlockPatternBuilder.start()
            .aisle(new String[]{"~ ~", "###", "~#~"})
            .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.IRON_BLOCK)))
            .where('~', blockInWorld -> blockInWorld.getState().isAir())
            .build();
      }

      return this.ironGolemBase;
   }

   private BlockPattern getOrCreateIronGolemFull() {
      if (this.ironGolemFull == null) {
         this.ironGolemFull = BlockPatternBuilder.start()
            .aisle(new String[]{"~^~", "###", "~#~"})
            .where('^', BlockInWorld.hasState(LUNARIS_CACTUS_FRUIT_PREDICATE))
            .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.IRON_BLOCK)))
            .where('~', blockInWorld -> blockInWorld.getState().isAir())
            .build();
      }

      return this.ironGolemFull;
   }

   private BlockPattern getOrCreateGrimstoneGolemBase() {
      if (this.grimstoneGolemBase == null) {
         this.grimstoneGolemBase = BlockPatternBuilder.start()
            .aisle(new String[]{" ", "#"})
            .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(ESBlocks.GRIMSTONE_BRICKS.get())))
            .build();
      }

      return this.grimstoneGolemBase;
   }

   private BlockPattern getOrCreateGrimstoneGolemFull() {
      if (this.grimstoneGolemFull == null) {
         this.grimstoneGolemFull = BlockPatternBuilder.start()
            .aisle(new String[]{"^", "#"})
            .where('^', BlockInWorld.hasState(LUNARIS_CACTUS_FRUIT_PREDICATE))
            .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(ESBlocks.GRIMSTONE_BRICKS.get())))
            .build();
      }

      return this.grimstoneGolemFull;
   }

   private BlockPattern getOrCreateAethersentGolemBase() {
      if (this.aethersentGolemBase == null) {
         this.aethersentGolemBase = BlockPatternBuilder.start()
            .aisle(new String[]{" ", "#"})
            .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(ESBlocks.AETHERSENT_BLOCK.get())))
            .build();
      }

      return this.aethersentGolemBase;
   }

   private BlockPattern getOrCreateAethersentGolemFull() {
      if (this.aethersentGolemFull == null) {
         this.aethersentGolemFull = BlockPatternBuilder.start()
            .aisle(new String[]{"^", "#"})
            .where('^', BlockInWorld.hasState(LUNARIS_CACTUS_FRUIT_PREDICATE))
            .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(ESBlocks.AETHERSENT_BLOCK.get())))
            .build();
      }

      return this.aethersentGolemFull;
   }
}
