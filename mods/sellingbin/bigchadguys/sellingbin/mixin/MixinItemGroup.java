package bigchadguys.sellingbin.mixin;

import bigchadguys.sellingbin.block.BinMaterial;
import bigchadguys.sellingbin.block.BinMode;
import bigchadguys.sellingbin.item.SellingBinItem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CreativeModeTab.class, priority = 1001)
public class MixinItemGroup {
   @Shadow
   private Collection<ItemStack> displayItems;
   @Shadow
   private Set<ItemStack> displayItemsSearchTab;

   @Inject(method = "buildContents(Lnet/minecraft/world/item/CreativeModeTab$ItemDisplayParameters;)V", at = @At("TAIL"))
   public void updateEntries(ItemDisplayParameters displayContext, CallbackInfo ci) {
      CreativeModeTab self = (CreativeModeTab)this;
      ResourceKey<CreativeModeTab> registryKey = (ResourceKey<CreativeModeTab>)BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(self).orElse(null);
      if (CreativeModeTabs.REDSTONE_BLOCKS.equals(registryKey)) {
         List<ItemStack> bins = new ArrayList<>();
         bins.add(SellingBinItem.create(BinMaterial.REDSTONE, BinMode.BLOCK_BOUND));
         this.displayItems.addAll(bins);
         this.displayItemsSearchTab.addAll(bins);
      } else if (CreativeModeTabs.FUNCTIONAL_BLOCKS.equals(registryKey)) {
         List<ItemStack> bins = new ArrayList<>();
         bins.add(SellingBinItem.create(BinMaterial.WOOD, BinMode.PLAYER_BOUND));
         bins.add(SellingBinItem.create(BinMaterial.REDSTONE, BinMode.BLOCK_BOUND));
         bins.add(SellingBinItem.create(BinMaterial.IRON, BinMode.PLAYER_BOUND));
         bins.add(SellingBinItem.create(BinMaterial.DIAMOND, BinMode.PLAYER_BOUND));
         bins.add(SellingBinItem.create(BinMaterial.NETHERITE, BinMode.PLAYER_BOUND));
         this.displayItems.addAll(bins);
         this.displayItemsSearchTab.addAll(bins);
      }
   }
}
