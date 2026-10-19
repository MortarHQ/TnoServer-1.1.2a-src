package net.p3pp3rf1y.sophisticatedbackpacks.data;

import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider.IntrinsicTagAppender;
import net.minecraft.data.tags.TagsProvider.TagLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeItemBase;

public class ItemTagProvider extends ItemTagsProvider {
   public ItemTagProvider(
      PackOutput packOutput,
      CompletableFuture<Provider> lookupProvider,
      CompletableFuture<TagLookup<Block>> blockTagProvider,
      @Nullable ExistingFileHelper existingFileHelper
   ) {
      super(packOutput, lookupProvider, blockTagProvider, "sophisticatedbackpacks", existingFileHelper);
   }

   protected void addTags(Provider pProvider) {
      IntrinsicTagAppender<Item> upgradeTag = this.tag(ModItems.BACKPACK_UPGRADE_TAG);
      BuiltInRegistries.ITEM
         .entrySet()
         .stream()
         .filter(
            entry -> ((ResourceKey)entry.getKey()).location().getNamespace().equals("sophisticatedbackpacks") && entry.getValue() instanceof UpgradeItemBase
         )
         .map(Entry::getValue)
         .forEach(item -> {
            ResourceLocation location = BuiltInRegistries.ITEM.getKey(item);
            if (location.getPath().contains("/")) {
               upgradeTag.addOptional(location);
            } else {
               upgradeTag.add(item);
            }
         });
   }
}
