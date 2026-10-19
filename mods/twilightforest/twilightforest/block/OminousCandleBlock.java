package twilightforest.block;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.HashMap;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.registries.DeferredBlock;
import twilightforest.block.entity.OminousCandleBlockEntity;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFParticleType;

public class OminousCandleBlock extends BaseEntityBlock {
   public static final MapCodec<OminousCandleBlock> CODEC = RecordCodecBuilder.mapCodec(
      app -> app.group(BuiltInRegistries.BLOCK.byNameCodec().fieldOf("candle").forGetter(block -> block.candle), propertiesCodec())
         .apply(app, OminousCandleBlock::new)
   );
   private static final VoxelShape ONE_AABB = Block.box(7.0, 0.0, 7.0, 9.0, 6.0, 9.0);
   private static final VoxelShape TWO_AABB = Block.box(5.0, 0.0, 6.0, 11.0, 6.0, 9.0);
   private static final VoxelShape THREE_AABB = Block.box(5.0, 0.0, 6.0, 10.0, 6.0, 11.0);
   private static final VoxelShape FOUR_AABB = Block.box(5.0, 0.0, 5.0, 11.0, 6.0, 10.0);
   public static final IntegerProperty CANDLES = BlockStateProperties.CANDLES;
   public static final HashMap<Block, DeferredBlock<OminousCandleBlock>> CANDLE_MAP = (HashMap<Block, DeferredBlock<OminousCandleBlock>>)Util.make(
      new HashMap(), map -> {
         map.put(Blocks.CANDLE, TFBlocks.OMINOUS_CANDLE);
         map.put(Blocks.WHITE_CANDLE, TFBlocks.OMINOUS_WHITE_CANDLE);
         map.put(Blocks.ORANGE_CANDLE, TFBlocks.OMINOUS_ORANGE_CANDLE);
         map.put(Blocks.MAGENTA_CANDLE, TFBlocks.OMINOUS_MAGENTA_CANDLE);
         map.put(Blocks.LIGHT_BLUE_CANDLE, TFBlocks.OMINOUS_LIGHT_BLUE_CANDLE);
         map.put(Blocks.YELLOW_CANDLE, TFBlocks.OMINOUS_YELLOW_CANDLE);
         map.put(Blocks.LIME_CANDLE, TFBlocks.OMINOUS_LIME_CANDLE);
         map.put(Blocks.PINK_CANDLE, TFBlocks.OMINOUS_PINK_CANDLE);
         map.put(Blocks.GRAY_CANDLE, TFBlocks.OMINOUS_GRAY_CANDLE);
         map.put(Blocks.LIGHT_GRAY_CANDLE, TFBlocks.OMINOUS_LIGHT_GRAY_CANDLE);
         map.put(Blocks.CYAN_CANDLE, TFBlocks.OMINOUS_CYAN_CANDLE);
         map.put(Blocks.PURPLE_CANDLE, TFBlocks.OMINOUS_PURPLE_CANDLE);
         map.put(Blocks.BLUE_CANDLE, TFBlocks.OMINOUS_BLUE_CANDLE);
         map.put(Blocks.BROWN_CANDLE, TFBlocks.OMINOUS_BROWN_CANDLE);
         map.put(Blocks.GREEN_CANDLE, TFBlocks.OMINOUS_GREEN_CANDLE);
         map.put(Blocks.RED_CANDLE, TFBlocks.OMINOUS_RED_CANDLE);
         map.put(Blocks.BLACK_CANDLE, TFBlocks.OMINOUS_BLACK_CANDLE);
      }
   );
   public static final Int2ObjectMap<List<Vec2>> CANDLE_OFFSETS = (Int2ObjectMap<List<Vec2>>)Util.make(() -> {
      Int2ObjectMap<List<Vec2>> int2objectmap = new Int2ObjectOpenHashMap();
      int2objectmap.defaultReturnValue(ImmutableList.of());
      int2objectmap.put(1, ImmutableList.of(new Vec2(0.5F, 0.5F)));
      int2objectmap.put(2, ImmutableList.of(new Vec2(0.375F, 0.5F), new Vec2(0.625F, 0.44F)));
      int2objectmap.put(3, ImmutableList.of(new Vec2(0.5F, 0.625F), new Vec2(0.375F, 0.5F), new Vec2(0.56F, 0.44F)));
      int2objectmap.put(4, ImmutableList.of(new Vec2(0.44F, 0.56F), new Vec2(0.625F, 0.56F), new Vec2(0.375F, 0.375F), new Vec2(0.56F, 0.375F)));
      return Int2ObjectMaps.unmodifiable(int2objectmap);
   });
   public final Block candle;

   public OminousCandleBlock(Block candle, Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(CANDLES, 1));
      this.candle = candle;
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      int candles = (Integer)state.getValue(CANDLES);
      int i = 1;

      for (Vec2 vec2 : (List)CANDLE_OFFSETS.get(candles)) {
         addParticlesAndSound(level, Vec3.atLowerCornerWithOffset(pos, vec2.x, getCurrentY(level.getGameTime(), 0.0F, pos, i++) + 0.5, vec2.y), random);
      }
   }

   public static double getCurrentY(long ticks, float partialTick, BlockPos pos, int candle) {
      double time = ((float)ticks + partialTick) * 0.014;
      double x = Math.sin(time * 7.0 + pos.getX() * candle);
      double y = Math.sin(time * 4.2 + pos.getY() * candle);
      double z = Math.sin(time * 3.0 + pos.getZ() * candle);
      return ((x + y + z) / 4.0 * 0.5 + 0.5) * 0.25;
   }

   private static void addParticlesAndSound(Level level, Vec3 offset, RandomSource random) {
      float f = random.nextFloat();
      if (f < 0.3F) {
         level.addParticle(ParticleTypes.SMOKE, offset.x, offset.y, offset.z, 0.0, 0.0, 0.0);
         if (f < 0.17F) {
            level.playLocalSound(
               offset.x + 0.5,
               offset.y + 0.5,
               offset.z + 0.5,
               SoundEvents.CANDLE_AMBIENT,
               SoundSource.BLOCKS,
               1.0F + random.nextFloat(),
               random.nextFloat() * 0.7F + 0.3F,
               false
            );
         }
      }

      level.addParticle((ParticleOptions)TFParticleType.OMINOUS_FLAME.get(), offset.x, offset.y, offset.z, 0.0, 0.0, 0.0);
   }

   protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return switch (state.getValue(CANDLES)) {
         case 2 -> TWO_AABB;
         case 3 -> THREE_AABB;
         case 4 -> FOUR_AABB;
         default -> ONE_AABB;
      };
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      if (player.getAbilities().mayBuild) {
         if (stack.isEmpty()) {
            BlockState newState = (BlockState)this.candle.defaultBlockState().setValue(CANDLES, (Integer)state.getValue(CANDLES));
            level.setBlock(pos, newState, 3);
            AbstractCandleBlock.extinguish(player, newState, level, pos);
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
         }

         if (stack.getItem() instanceof BlockItem blockItem
            && blockItem.getBlock() instanceof CandleBlock candleBlock
            && (Integer)state.getValue(CANDLES) < 4
            && this.candle == candleBlock) {
            level.setBlock(pos, (BlockState)state.setValue(CANDLES, (Integer)state.getValue(CANDLES) + 1), 3);
            candleBlock.setPlacedBy(level, pos, state, player, stack);
            if (player instanceof ServerPlayer) {
               CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, pos, stack);
            }

            player.awardStat(Stats.ITEM_USED.get(blockItem));
            BlockState newState = level.getBlockState(pos);
            SoundType soundtype = newState.getSoundType(level, pos, player);
            level.playSound(player, pos, soundtype.getPlaceSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
            level.gameEvent(GameEvent.BLOCK_PLACE, pos, Context.of(player, newState));
            stack.consume(1, player);
            return ItemInteractionResult.SUCCESS;
         }
      }

      return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
   }

   public static void eruptFlameParticles(Level level, BlockPos pos, BlockState state) {
      Vec3 start = Vec3.atLowerCornerOf(pos);

      for (Vec2 vec2 : (List)CANDLE_OFFSETS.get((Integer)state.getValue(CANDLES))) {
         for (int j = 0; j < 5; j++) {
            level.addParticle(
               (ParticleOptions)TFParticleType.OMINOUS_FLAME.get(),
               start.x + vec2.x,
               start.y + 0.5,
               start.z + vec2.y,
               (level.getRandom().nextDouble() - 0.5) * 0.05,
               0.015F,
               (level.getRandom().nextDouble() - 0.5) * 0.05
            );
         }
      }
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{CANDLES});
   }

   public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
      return new ItemStack(this.candle);
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new OminousCandleBlockEntity(pos, state);
   }
}
