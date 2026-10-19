package com.chen1335.curioEnchantment.data;

import com.chen1335.curioEnchantment.API.objects.CETags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider.TagLookup;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

public class CEItemTagsProvider extends ItemTagsProvider {
   public CEItemTagsProvider(
      PackOutput output, CompletableFuture<Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, ExistingFileHelper existingFileHelper
   ) {
      super(output, lookupProvider, blockTags, "curio_enchantment", existingFileHelper);
   }

   protected void addTags(@NotNull Provider provider) {
      this.tag(CETags.ItemTags.CURIOS_ENCHANTABLE);
   }
}
