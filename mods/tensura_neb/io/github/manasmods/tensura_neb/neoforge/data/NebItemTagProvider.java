package io.github.manasmods.tensura_neb.neoforge.data;

import io.github.manasmods.tensura_neb.registry.NebItems;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider.TagLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class NebItemTagProvider extends ItemTagsProvider {
   public NebItemTagProvider(PackOutput output, CompletableFuture<Provider> future, CompletableFuture<TagLookup<Block>> provider, ExistingFileHelper helper) {
      super(output, future, provider, "tensura_neb", helper);
   }

   protected void addTags(Provider arg) {
      this.tag(ItemTags.SWORDS).add(new Item[]{(Item)NebItems.NIGHT_ROSE.get(), (Item)NebItems.WHITE_TIGER_AZURE_DRAGON_HALBERD.get()});
   }
}
