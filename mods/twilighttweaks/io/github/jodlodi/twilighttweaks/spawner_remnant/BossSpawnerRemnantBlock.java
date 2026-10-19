package io.github.jodlodi.twilighttweaks.spawner_remnant;

import com.mojang.serialization.MapCodec;
import io.github.jodlodi.twilighttweaks.TwilightTweaks;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import twilightforest.block.SpecialMagicLogBlock;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BossSpawnerRemnantBlock extends BaseEntityBlock {
   public static final MapCodec<BossSpawnerRemnantBlock> CODEC = simpleCodec(BossSpawnerRemnantBlock::new);
   private static final VoxelShape WITH_HITBOX = Block.box(6.0, 6.0, 6.0, 10.0, 10.0, 10.0);
   private static final VoxelShape NO_HITBOX = Shapes.empty();

   public BossSpawnerRemnantBlock(Properties properties) {
      super(properties);
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> container) {
      super.createBlockStateDefinition(container);
      container.add(new Property[]{SpecialMagicLogBlock.ACTIVE});
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      if (level.getBlockEntity(pos) instanceof BossSpawnerRemnantBlockEntity spawnerRemnant && spawnerRemnant.reSpawnerTick == 0) {
         if (stack.is((Item)TwilightTweaks.TIME_POWDER.get())) {
            if (!player.getAbilities().instabuild) {
               stack.shrink(1);
            }

            spawnerRemnant.reSpawnerTick++;
            if (level.isClientSide) {
               for (int i = 0; i < 30; i++) {
                  level.addParticle(
                     ParticleTypes.CRIT,
                     pos.getX() + level.random.nextFloat(),
                     pos.getY() + level.random.nextFloat(),
                     pos.getZ() + level.random.nextFloat(),
                     0.0,
                     0.0,
                     0.0
                  );
               }
            }
         } else if (level.isClientSide) {
            player.displayClientMessage(
               Component.translatable("remnant.twilighttweaks.use").withStyle(new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GOLD}), true
            );
         }

         return ItemInteractionResult.SUCCESS;
      } else {
         return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
      }
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return createTickerHelper(type, (BlockEntityType)TwilightTweaks.BOSS_SPAWNER_REMNANT_ENTITY.get(), BossSpawnerRemnantBlockEntity::shtick);
   }

   @NotNull
   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.INVISIBLE;
   }

   @NotNull
   public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
      return blockGetter.getBlockEntity(pos) instanceof BossSpawnerRemnantBlockEntity spawnerRemnant && !spawnerRemnant.active ? NO_HITBOX : WITH_HITBOX;
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new BossSpawnerRemnantBlockEntity(pos, state);
   }

   public boolean canEntityDestroy(BlockState state, BlockGetter world, BlockPos pos, Entity entity) {
      return entity instanceof Player player && player.getAbilities().instabuild;
   }

   protected void spawnDestroyParticles(Level level, Player player, BlockPos pos, BlockState state) {
   }
}
