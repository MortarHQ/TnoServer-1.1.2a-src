package nadiendev.constructionwand.wand.undo;

import javax.annotation.Nullable;
import nadiendev.constructionwand.basics.WandUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

public class DestroySnapshot implements ISnapshot {
   private final BlockState block;
   private final BlockPos pos;

   public DestroySnapshot(BlockState block, BlockPos pos) {
      this.pos = pos;
      this.block = block;
   }

   @Nullable
   public static DestroySnapshot get(Level world, Player player, BlockPos pos) {
      return !WandUtil.isBlockRemovable(world, player, pos) ? null : new DestroySnapshot(world.getBlockState(pos), pos);
   }

   @Override
   public BlockPos getPos() {
      return this.pos;
   }

   @Override
   public BlockState getBlockState() {
      return this.block;
   }

   @Override
   public ItemStack getRequiredItems() {
      return ItemStack.EMPTY;
   }

   @Override
   public boolean execute(Level world, Player player, BlockHitResult rayTraceResult) {
      if (world instanceof ServerLevel serverLevel) {
         if (!WandUtil.isBlockRemovable(world, player, this.pos)) {
            return false;
         }

         ItemStack silkTool = new ItemStack(Items.NETHERITE_PICKAXE);
         Holder<Enchantment> silkHolder = serverLevel.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.SILK_TOUCH);
         silkTool.enchant(silkHolder, 1);
         return player instanceof ServerPlayer serverPlayer
            ? serverLevel.destroyBlock(this.pos, true, serverPlayer, 512)
            : serverLevel.destroyBlock(this.pos, true, null, 512);
      } else {
         return WandUtil.removeBlock(world, player, this.block, this.pos);
      }
   }

   @Override
   public boolean canRestore(Level world, Player player) {
      if (!world.isInWorldBounds(this.pos)) {
         return false;
      } else if (!world.mayInteract(player, this.pos)) {
         return false;
      } else if (player.isCreative()) {
         return true;
      } else {
         return !world.isEmptyBlock(this.pos) && !world.getBlockState(this.pos).canBeReplaced(Fluids.EMPTY)
            ? false
            : !WandUtil.entitiesCollidingWithBlock(world, this.block, this.pos);
      }
   }

   @Override
   public boolean restore(Level world, Player player) {
      return WandUtil.placeBlock(world, player, this.block, this.pos, null);
   }

   @Override
   public void forceRestore(Level world) {
      world.setBlockAndUpdate(this.pos, this.block);
   }
}
