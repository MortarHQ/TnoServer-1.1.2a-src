package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
import twilightforest.init.TFItems;
import twilightforest.init.TFSounds;

public class LockedVanishingBlock extends VanishingBlock {
   public static final BooleanProperty LOCKED = BooleanProperty.create("locked");

   public LockedVanishingBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)this.defaultBlockState().setValue(LOCKED, true));
   }

   @Override
   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder.add(new Property[]{LOCKED}));
   }

   @Override
   public float getExplosionResistance(BlockState state, BlockGetter getter, BlockPos pos, Explosion explosion) {
      return state.getValue(LOCKED) ? 6000000.0F : super.getExplosionResistance(state, getter, pos, explosion);
   }

   @Override
   public boolean canEntityDestroy(BlockState state, BlockGetter getter, BlockPos pos, Entity entity) {
      return !(Boolean)state.getValue(LOCKED) && super.canEntityDestroy(state, getter, pos, entity);
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      if (!stack.isEmpty() && stack.is((Item)TFItems.TOWER_KEY.get()) && (Boolean)state.getValue(LOCKED)) {
         if (!level.isClientSide()) {
            stack.shrink(1);
            level.setBlockAndUpdate(pos, (BlockState)state.setValue(LOCKED, false));
            level.playSound(null, pos, (SoundEvent)TFSounds.UNLOCK_VANISHING_BLOCK.get(), SoundSource.BLOCKS, 0.3F, 0.6F);
         }

         return ItemInteractionResult.sidedSuccess(level.isClientSide());
      } else {
         return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
      }
   }
}
