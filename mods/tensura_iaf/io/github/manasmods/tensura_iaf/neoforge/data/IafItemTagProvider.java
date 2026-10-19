package io.github.manasmods.tensura_iaf.neoforge.data;

import com.iafenvoy.iceandfire.registry.IafItems;
import io.github.manasmods.tensura.data.TensuraItemTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider.TagLookup;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class IafItemTagProvider extends ItemTagsProvider {
   public IafItemTagProvider(PackOutput output, CompletableFuture<Provider> future, CompletableFuture<TagLookup<Block>> provider, ExistingFileHelper helper) {
      super(output, future, provider, "tensura_iaf", helper);
   }

   protected void addTags(Provider arg) {
      this.tag(TensuraItemTags.RAW_MONSTER_CONSUMABLES)
         .add(
            new Item[]{
               (Item)IafItems.FIRE_DRAGON_FLESH.get(),
               (Item)IafItems.FIRE_DRAGON_HEART.get(),
               (Item)IafItems.ICE_DRAGON_FLESH.get(),
               (Item)IafItems.ICE_DRAGON_HEART.get(),
               (Item)IafItems.LIGHTNING_DRAGON_FLESH.get(),
               (Item)IafItems.LIGHTNING_DRAGON_HEART.get(),
               (Item)IafItems.HYDRA_HEART.get()
            }
         );
      this.tag(TensuraItemTags.DUBIOUS_EFFECT_INGREDIENT)
         .add(new Item[]{(Item)IafItems.LIGHTNING_STEW.get(), (Item)IafItems.FIRE_STEW.get(), (Item)IafItems.FROST_STEW.get(), (Item)IafItems.AMBROSIA.get()});
      this.tag(TensuraItemTags.DUBIOUS_BREWING_INGREDIENT).add((Item)IafItems.SIREN_TEAR.get());
      this.tag(TensuraItemTags.DUBIOUS_POISON_INGREDIENT)
         .add(new Item[]{(Item)IafItems.ROTTEN_EGG.get(), (Item)IafItems.COCKATRICE_EYE.get(), (Item)IafItems.HYDRA_FANG.get()});
      this.tag(TensuraItemTags.DUBIOUS_MAGIC_INGREDIENT).add(new Item[]{(Item)IafItems.PIXIE_WINGS.get(), (Item)IafItems.ECTOPLASM.get()});
      this.tag(TensuraItemTags.DUBIOUS_CRYSTAL_INGREDIENT)
         .add(
            new Item[]{
               (Item)IafItems.SUMMONING_CRYSTAL_FIRE.get(), (Item)IafItems.SUMMONING_CRYSTAL_ICE.get(), (Item)IafItems.SUMMONING_CRYSTAL_LIGHTNING.get()
            }
         );
   }
}
