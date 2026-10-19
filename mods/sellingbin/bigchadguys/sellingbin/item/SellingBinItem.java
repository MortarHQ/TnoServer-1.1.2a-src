package bigchadguys.sellingbin.item;

import bigchadguys.sellingbin.block.BinMaterial;
import bigchadguys.sellingbin.block.BinMode;
import bigchadguys.sellingbin.init.ModBlocks;
import bigchadguys.sellingbin.init.ModDataComponents;
import java.util.Optional;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class SellingBinItem extends BlockItem {
   public SellingBinItem(Block block) {
      super(block, new Properties());
   }

   public String getDescriptionId(ItemStack stack) {
      BinMaterial material = getMaterial(stack).orElse(null);
      return super.getDescriptionId(stack) + (material == null ? "" : "." + material.getId());
   }

   public static ItemStack create(BinMaterial material, BinMode mode) {
      ItemStack stack = new ItemStack((ItemLike)ModBlocks.SELLING_BIN.get());
      setMaterial(stack, material);
      setMode(stack, mode);
      return stack;
   }

   public static Optional<BinMaterial> getMaterial(ItemStack stack) {
      return Optional.ofNullable((BinMaterial)stack.get((DataComponentType)ModDataComponents.BIN_MATERIAL.get()));
   }

   public static void setMaterial(ItemStack stack, BinMaterial material) {
      stack.set((DataComponentType)ModDataComponents.BIN_MATERIAL.get(), material);
   }

   public static Optional<BinMode> getMode(ItemStack stack) {
      return Optional.ofNullable((BinMode)stack.get((DataComponentType)ModDataComponents.BIN_MODE.get()));
   }

   public static void setMode(ItemStack stack, BinMode mode) {
      stack.set((DataComponentType)ModDataComponents.BIN_MODE.get(), mode);
   }
}
