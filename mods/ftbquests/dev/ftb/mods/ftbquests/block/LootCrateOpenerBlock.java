package dev.ftb.mods.ftbquests.block;

import com.mojang.serialization.MapCodec;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import dev.ftb.mods.ftbquests.block.entity.LootCrateOpenerBlockEntity;
import dev.ftb.mods.ftbquests.block.neoforge.LootCrateOpenerBlockImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class LootCrateOpenerBlock extends BaseEntityBlock {
   private static final MapCodec<LootCrateOpenerBlock> CODEC = simpleCodec(LootCrateOpenerBlock::new);
   public static final Properties PROPS = Properties.of().mapColor(MapColor.WOOD).strength(1.8F);

   public LootCrateOpenerBlock(Properties props) {
      super(PROPS);
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
      return blockEntityProvider().create(blockPos, blockState);
   }

   @ExpectPlatform
   @Transformed
   public static BlockEntitySupplier<LootCrateOpenerBlockEntity> blockEntityProvider() {
      return LootCrateOpenerBlockImpl.blockEntityProvider();
   }

   protected MapCodec<LootCrateOpenerBlock> codec() {
      return null;
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }

   protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
      if (!level.isClientSide && level.getBlockEntity(blockPos) instanceof LootCrateOpenerBlockEntity opener) {
         player.displayClientMessage(Component.translatable("block.ftbquests.loot_crate_opener.rightclick", new Object[]{opener.getOutputCount()}), true);
      }

      return InteractionResult.sidedSuccess(level.isClientSide);
   }

   public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
      super.setPlacedBy(level, blockPos, blockState, livingEntity, itemStack);
      if (level.getBlockEntity(blockPos) instanceof LootCrateOpenerBlockEntity opener && livingEntity instanceof Player) {
         opener.setOwner(livingEntity.getUUID());
      }
   }
}
