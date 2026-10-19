package earth.terrarium.handcrafted.common.blockentities;

import earth.terrarium.handcrafted.common.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SmokerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class OvenBlockEntity extends AbstractFurnaceBlockEntity {
   public OvenBlockEntity(BlockPos pos, BlockState blockState) {
      super((BlockEntityType)ModBlockEntityTypes.OVEN.get(), pos, blockState, RecipeType.SMOKING);
   }

   protected Component getDefaultName() {
      return Component.translatable("container.smoker");
   }

   protected int getBurnDuration(ItemStack fuel) {
      return super.getBurnDuration(fuel) / 2;
   }

   protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
      return new SmokerMenu(containerId, inventory, this, this.dataAccess);
   }
}
