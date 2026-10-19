package net.darkhax.bookshelf.common.mixin.patch.item;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import net.darkhax.bookshelf.common.api.item.IItemHooks;
import net.darkhax.bookshelf.common.api.util.DataHelper;
import net.darkhax.bookshelf.common.impl.Constants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeModeTab.class)
public class MixinCreativeModeTab {
   @Shadow
   private Collection<ItemStack> displayItems;
   @Shadow
   private Set<ItemStack> displayItemsSearchTab;
   @Unique
   private static final Map<ResourceLocation, TagKey<Item>> TAG_CACHE = new HashMap<>();
   @Unique
   private static final ResourceLocation OP_ITEMS_ID = ResourceLocation.fromNamespaceAndPath("minecraft", "op_blocks");

   @Inject(method = "buildContents(Lnet/minecraft/world/item/CreativeModeTab$ItemDisplayParameters;)V", at = @At("TAIL"))
   private void buildContents(ItemDisplayParameters parameters, CallbackInfo cbi) {
      CreativeModeTab self = (CreativeModeTab)this;
      ResourceLocation id = BuiltInRegistries.CREATIVE_MODE_TAB.getKey(self);
      if (id != null && (!self.isAlignedRight() || id.equals(OP_ITEMS_ID)) && (!id.equals(OP_ITEMS_ID) || parameters.hasPermissions())) {
         TagKey<Item> tabTag = TAG_CACHE.computeIfAbsent(
            id, key -> TagKey.create(Registries.ITEM, Constants.id("creative_tab/" + key.getNamespace() + "/" + key.getPath()))
         );

         for (Holder<Item> tagEntry : DataHelper.getTagOrEmpty(parameters.holders(), Registries.ITEM, tabTag)) {
            try {
               Item item = (Item)tagEntry.value();
               if (item instanceof IItemHooks hooks) {
                  hooks.addCreativeTabForms(self, stackx -> {
                     this.displayItems.add(stackx);
                     this.displayItemsSearchTab.add(stackx);
                  });
               } else {
                  ItemStack stack = new ItemStack(item);
                  this.displayItems.add(stack);
                  this.displayItemsSearchTab.add(stack);
               }
            } catch (Exception e) {
               Constants.LOG.error("Unable to add tag entries to creative tab!", e);
            }
         }
      }
   }
}
