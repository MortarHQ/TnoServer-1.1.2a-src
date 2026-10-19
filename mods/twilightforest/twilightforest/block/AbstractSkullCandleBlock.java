package twilightforest.block;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.SkullBlock.Type;
import net.minecraft.world.level.block.SkullBlock.Types;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.Nullable;
import twilightforest.block.entity.SkullCandleBlockEntity;
import twilightforest.components.item.SkullCandles;
import twilightforest.init.TFBlockEntities;
import twilightforest.init.TFDataComponents;

public abstract class AbstractSkullCandleBlock extends BaseEntityBlock implements LightableBlock {
   public static final IntegerProperty CANDLES = BlockStateProperties.CANDLES;
   private final Type type;

   public AbstractSkullCandleBlock(Type type, Properties properties) {
      super(properties);
      this.type = type;
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(LIGHTING, LightableBlock.Lighting.NONE)).setValue(CANDLES, 1)
      );
   }

   public Type getType() {
      return this.type;
   }

   public int getLightEmission(BlockState state, BlockGetter getter, BlockPos pos) {
      return switch ((LightableBlock.Lighting)state.getValue(LIGHTING)) {
         case NORMAL -> 3 * state.getValue(CANDLES);
         case OMINOUS -> 2 * state.getValue(CANDLES);
         case DIM -> state.getValue(CANDLES);
         default -> 0;
      };
   }

   public BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
      return ItemAbilities.FIRESTARTER_LIGHT == itemAbility && this.canBeLit(state)
         ? (BlockState)state.setValue(LIGHTING, LightableBlock.Lighting.NORMAL)
         : super.getToolModifiedState(state, context, itemAbility, simulate);
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new SkullCandleBlockEntity(pos, state, 0);
   }

   public static Block candleColorToCandle(AbstractSkullCandleBlock.CandleColors color) {
      return color != AbstractSkullCandleBlock.CandleColors.PLAIN
         ? Objects.requireNonNull((Block)BuiltInRegistries.BLOCK.get(ResourceLocation.withDefaultNamespace(color.getSerializedName() + "_candle")))
         : Blocks.CANDLE;
   }

   public static AbstractSkullCandleBlock.CandleColors candleToCandleColor(Item candle) {
      return candle != Blocks.CANDLE.asItem()
         ? AbstractSkullCandleBlock.CandleColors.valueOf(
            BuiltInRegistries.ITEM.getKey(candle).getPath().replace("_candle", "").replace("\"", "").toUpperCase(Locale.ROOT)
         )
         : AbstractSkullCandleBlock.CandleColors.PLAIN;
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.INVISIBLE;
   }

   public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
      super.setPlacedBy(level, pos, state, placer, stack);
      if (level.getBlockEntity(pos) instanceof SkullCandleBlockEntity sc) {
         SkullCandles skullCandles = (SkullCandles)stack.getOrDefault(TFDataComponents.SKULL_CANDLES, SkullCandles.DEFAULT);
         sc.setCandleColor(skullCandles.color());
         if (this.type == Types.PLAYER && stack.has(DataComponents.PROFILE)) {
            sc.setOwner((ResolvableProfile)stack.get(DataComponents.PROFILE));
         }
      }
   }

   public List<ItemStack> getDrops(BlockState state, net.minecraft.world.level.storage.loot.LootParams.Builder builder) {
      List<ItemStack> drops = super.getDrops(state, builder);
      Optional<ItemStack> skullStack = drops.stream().filter(item -> item.is(ItemTags.SKULLS) && !item.is(this.asItem())).findFirst();
      if (skullStack.isPresent()) {
         BlockEntity blockEntity = (BlockEntity)builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
         if (blockEntity instanceof SkullCandleBlockEntity sc) {
            if (!((ItemStack)builder.getParameter(LootContextParams.TOOL)).isEmpty()
               && ((ItemStack)builder.getParameter(LootContextParams.TOOL))
                     .getEnchantmentLevel(sc.getLevel().registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.SILK_TOUCH))
                  > 0) {
               ItemStack newStack = new ItemStack(this);
               newStack.set(TFDataComponents.SKULL_CANDLES, new SkullCandles(sc.getCandleColor(), (Integer)state.getValue(CANDLES)));
               if (this.type == Types.PLAYER && sc.getOwnerProfile() != null) {
                  newStack.set(DataComponents.PROFILE, sc.getOwnerProfile());
               }

               drops.remove(skullStack.get());
               drops.add(newStack);
            } else {
               drops.add(
                  new ItemStack(candleColorToCandle(AbstractSkullCandleBlock.CandleColors.colorFromInt(sc.getCandleColor())), (Integer)state.getValue(CANDLES))
               );
            }
         }
      }

      return drops;
   }

   public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
      ItemStack newStack = new ItemStack(this);
      if (level.getBlockEntity(pos) instanceof SkullCandleBlockEntity sc) {
         newStack.set(TFDataComponents.SKULL_CANDLES, new SkullCandles(sc.getCandleColor(), (Integer)state.getValue(CANDLES)));
         if (this.type == Types.PLAYER && sc.getOwnerProfile() != null) {
            newStack.set(DataComponents.PROFILE, sc.getOwnerProfile());
         }
      }

      return newStack;
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result
   ) {
      if (level.getBlockEntity(pos) instanceof SkullCandleBlockEntity sc
         && stack.is(ItemTags.CANDLES)
         && stack.is(candleColorToCandle(AbstractSkullCandleBlock.CandleColors.colorFromInt(sc.getCandleColor())).asItem())
         && !player.isShiftKeyDown()) {
         int candles = (Integer)state.getValue(CANDLES);
         if (candles < 4) {
            level.setBlockAndUpdate(pos, (BlockState)state.setValue(CANDLES, candles + 1));
            level.playSound(null, pos, SoundEvents.CANDLE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            stack.consume(1, player);
            level.getLightEngine().checkBlock(pos);
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
         }
      }

      return this.tryLightCandles(stack, state, level, pos, player);
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
      if (level.getBlockEntity(pos) instanceof SkullCandleBlockEntity sc && player.isSecondaryUseActive() && (Integer)state.getValue(CANDLES) > 0) {
         int newCandleAmount = (Integer)state.getValue(CANDLES) - 1;
         if (newCandleAmount > 0) {
            level.setBlockAndUpdate(pos, (BlockState)state.setValue(CANDLES, newCandleAmount));
         } else {
            boolean wall = state.getBlock() instanceof WallSkullCandleBlock;
            Block newBlock = this.getNoCandleSkull(wall);
            if (newBlock != null) {
               ResolvableProfile profile = sc.getOwnerProfile();
               BlockState newState;
               if (wall) {
                  newState = (BlockState)newBlock.defaultBlockState().setValue(WallSkullBlock.FACING, (Direction)state.getValue(WallSkullCandleBlock.FACING));
               } else {
                  newState = (BlockState)newBlock.defaultBlockState().setValue(SkullBlock.ROTATION, (Integer)state.getValue(SkullCandleBlock.ROTATION));
               }

               level.setBlockAndUpdate(pos, newState);
               level.setBlockEntity(new SkullBlockEntity(pos, newState));
               if (level.getBlockEntity(pos) instanceof SkullBlockEntity sc1) {
                  sc1.setOwner(profile);
               }
            }
         }

         level.playSound(null, pos, SoundEvents.CANDLE_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
         level.getLightEngine().checkBlock(pos);
         ItemStack candle = new ItemStack(candleColorToCandle(AbstractSkullCandleBlock.CandleColors.colorFromInt(sc.getCandleColor())));
         if (player.hasInfiniteMaterials()) {
            if (!player.getInventory().contains(candle)) {
               player.getInventory().add(candle);
            }
         } else if (!player.getInventory().add(candle)) {
            player.drop(candle, false);
         }

         return InteractionResult.sidedSuccess(level.isClientSide());
      } else {
         return super.useWithoutItem(state, level, pos, player, hitResult);
      }
   }

   @Nullable
   private Block getNoCandleSkull(boolean wall) {
      return switch ((Types)this.getType()) {
         case SKELETON -> {
            if (wall) {
               yield Blocks.SKELETON_WALL_SKULL;
            } else {
               yield Blocks.SKELETON_SKULL;
            }
         }
         case WITHER_SKELETON -> {
            if (wall) {
               yield Blocks.WITHER_SKELETON_WALL_SKULL;
            } else {
               yield Blocks.WITHER_SKELETON_SKULL;
            }
         }
         case PLAYER -> {
            if (wall) {
               yield Blocks.PLAYER_WALL_HEAD;
            } else {
               yield Blocks.PLAYER_HEAD;
            }
         }
         case ZOMBIE -> {
            if (wall) {
               yield Blocks.ZOMBIE_WALL_HEAD;
            } else {
               yield Blocks.ZOMBIE_HEAD;
            }
         }
         case CREEPER -> {
            if (wall) {
               yield Blocks.CREEPER_WALL_HEAD;
            } else {
               yield Blocks.CREEPER_HEAD;
            }
         }
         case PIGLIN -> {
            if (wall) {
               yield Blocks.PIGLIN_WALL_HEAD;
            } else {
               yield Blocks.PIGLIN_HEAD;
            }
         }
         default -> null;
      };
   }

   public void onProjectileHit(Level level, BlockState state, BlockHitResult result, Projectile projectile) {
      this.lightCandlesWithProjectile(level, state, result, projectile);
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rand) {
      if (state.getValue(LIGHTING) != LightableBlock.Lighting.NONE) {
         this.getParticleOffsets(state, level, pos).forEach(offset -> {
            Vec3 trueOffset = offset.add(pos.getX(), pos.getY(), pos.getZ());
            this.addParticlesAndSound(level, trueOffset.x(), trueOffset.y(), trueOffset.z(), rand, (LightableBlock.Lighting)state.getValue(LIGHTING));
         });
      }
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{LIGHTING, CANDLES});
   }

   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return createTickerHelper(type, (BlockEntityType)TFBlockEntities.SKULL_CANDLE.get(), SkullCandleBlockEntity::tick);
   }

   public enum CandleColors implements StringRepresentable {
      PLAIN(0),
      WHITE(1),
      LIGHT_GRAY(2),
      GRAY(3),
      BLACK(4),
      RED(5),
      ORANGE(6),
      YELLOW(7),
      GREEN(8),
      LIME(9),
      BLUE(10),
      CYAN(11),
      LIGHT_BLUE(12),
      PURPLE(13),
      MAGENTA(14),
      PINK(15),
      BROWN(16);

      private final int value;
      private static final Map<Integer, AbstractSkullCandleBlock.CandleColors> map = new HashMap<>();

      CandleColors(int value) {
         this.value = value;
      }

      public static AbstractSkullCandleBlock.CandleColors colorFromInt(int value) {
         return map.get(value);
      }

      public int getValue() {
         return this.value;
      }

      public String getSerializedName() {
         return this.name().toLowerCase(Locale.ROOT);
      }

      static {
         for (AbstractSkullCandleBlock.CandleColors color : values()) {
            map.put(color.value, color);
         }
      }
   }
}
