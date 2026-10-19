package twilightforest.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import twilightforest.block.entity.KeepsakeCasketBlockEntity;
import twilightforest.block.entity.SkullChestBlockEntity;
import twilightforest.enums.BlockLoggingEnum;
import twilightforest.init.TFBlockEntities;
import twilightforest.init.TFDataComponents;
import twilightforest.init.TFItems;
import twilightforest.init.TFSounds;

public class KeepsakeCasketBlock extends SkullChestBlock {
   public static final IntegerProperty BREAKAGE = IntegerProperty.create("damage", 0, 2);
   public static final MapCodec<KeepsakeCasketBlock> CODEC = simpleCodec(KeepsakeCasketBlock::new);

   public KeepsakeCasketBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(FACING, Direction.NORTH)).setValue(BREAKAGE, 0));
   }

   @Override
   protected MapCodec<? extends KeepsakeCasketBlock> codec() {
      return CODEC;
   }

   @Override
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new KeepsakeCasketBlockEntity(pos, state);
   }

   @Override
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return createTickerHelper(type, (BlockEntityType)TFBlockEntities.KEEPSAKE_CASKET.get(), SkullChestBlockEntity::tick);
   }

   @Override
   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result
   ) {
      boolean flag = false;
      if (((BlockLoggingEnum)state.getValue(BlockLoggingEnum.MULTILOGGED)).getBlock() == Blocks.AIR
         || ((BlockLoggingEnum)state.getValue(BlockLoggingEnum.MULTILOGGED)).getFluid() != Fluids.EMPTY) {
         if (stack.is((Item)TFItems.CHARM_OF_KEEPING_3.get()) && (Integer)state.getValue(BREAKAGE) > 0) {
            stack.consume(1, player);
            level.setBlockAndUpdate(pos, (BlockState)state.setValue(BREAKAGE, (Integer)state.getValue(BREAKAGE) - 1));
            level.playSound(null, pos, (SoundEvent)TFSounds.CASKET_REPAIR.get(), SoundSource.BLOCKS, 0.5F, level.getRandom().nextFloat() * 0.1F + 0.9F);
            flag = true;
         } else {
            if (level.isClientSide()) {
               return ItemInteractionResult.SUCCESS;
            }

            MenuProvider provider = this.getMenuProvider(state, level, pos);
            if (provider != null) {
               player.openMenu(provider);
            }

            flag = true;
         }
      }

      return flag ? ItemInteractionResult.sidedSuccess(level.isClientSide()) : ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
   }

   @Override
   protected void modifyDrop(BlockState state, ItemStack stack) {
      if ((Integer)state.getValue(BREAKAGE) > 0) {
         stack.set(TFDataComponents.CASKET_DAMAGE, (Integer)state.getValue(BREAKAGE));
      }
   }

   public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
      level.setBlock(pos, (BlockState)state.setValue(BREAKAGE, (Integer)stack.getOrDefault(TFDataComponents.CASKET_DAMAGE, 0)), 2);
      super.setPlacedBy(level, pos, state, placer, stack);
   }

   @Override
   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{BREAKAGE});
   }

   public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
      if ((Integer)state.getValue(BREAKAGE) > 0) {
         ItemStack itemstack = new ItemStack(this);
         itemstack.applyComponents(
            DataComponentPatch.builder().set((DataComponentType)TFDataComponents.CASKET_DAMAGE.get(), (Integer)state.getValue(BREAKAGE)).build()
         );
         return itemstack;
      } else {
         return super.getCloneItemStack(state, target, level, pos, player);
      }
   }
}
