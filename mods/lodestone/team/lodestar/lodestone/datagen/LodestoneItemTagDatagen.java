package team.lodestar.lodestone.datagen;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider.TagLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.registry.common.tag.LodestoneItemTags;
import team.lodestar.lodestone.systems.datagen.providers.LodestoneItemTagsProvider;

public class LodestoneItemTagDatagen extends LodestoneItemTagsProvider {
   public LodestoneItemTagDatagen(
      PackOutput output,
      CompletableFuture<Provider> provider,
      CompletableFuture<TagLookup<Block>> blockProvider,
      @Nullable ExistingFileHelper existingFileHelper
   ) {
      super(output, provider, blockProvider, "lodestone", existingFileHelper);
   }

   public String getName() {
      return "Malum Item Tags";
   }

   protected void addTags(@NotNull Provider provider) {
      this.tag(LodestoneItemTags.ENCHANTMENT_HOLDER).add(new Item[]{Items.BOOK, Items.ENCHANTED_BOOK});
      this.tag(LodestoneItemTags.MELEE_ENCHANTABLE)
         .addTags(
            new TagKey[]{
               ItemTags.SWORD_ENCHANTABLE,
               LodestoneItemTags.AXE_ENCHANTABLE,
               ItemTags.TRIDENT_ENCHANTABLE,
               ItemTags.MACE_ENCHANTABLE,
               LodestoneItemTags.KNIFE_ENCHANTABLE
            }
         );
      this.tag(LodestoneItemTags.RANGED_ENCHANTABLE).addTags(new TagKey[]{ItemTags.CROSSBOW_ENCHANTABLE, ItemTags.BOW_ENCHANTABLE});
      this.tag(LodestoneItemTags.WEAPON_ENCHANTABLE).addTags(new TagKey[]{LodestoneItemTags.MELEE_ENCHANTABLE, LodestoneItemTags.RANGED_ENCHANTABLE});
      this.tag(LodestoneItemTags.SHIELD_ENCHANTABLE).add(Items.SHIELD);
      this.tag(LodestoneItemTags.AXE_ENCHANTABLE).addTags(new TagKey[]{ItemTags.AXES});
      this.tag(LodestoneItemTags.KNIFE_ENCHANTABLE).addTags(new TagKey[]{LodestoneItemTags.FD_KNIVES, LodestoneItemTags.C_KNIVES});
      this.tag(LodestoneItemTags.FD_KNIVES);
      this.tag(LodestoneItemTags.C_KNIVES);
      this.tag(LodestoneItemTags.NUGGETS_COPPER);
      this.tag(LodestoneItemTags.INGOTS_COPPER).add(Items.COPPER_INGOT);
      this.tag(LodestoneItemTags.NUGGETS_LEAD);
      this.tag(LodestoneItemTags.INGOTS_LEAD);
      this.tag(LodestoneItemTags.NUGGETS_SILVER);
      this.tag(LodestoneItemTags.INGOTS_SILVER);
      this.tag(LodestoneItemTags.NUGGETS_ALUMINUM);
      this.tag(LodestoneItemTags.INGOTS_ALUMINUM);
      this.tag(LodestoneItemTags.NUGGETS_NICKEL);
      this.tag(LodestoneItemTags.INGOTS_NICKEL);
      this.tag(LodestoneItemTags.NUGGETS_URANIUM);
      this.tag(LodestoneItemTags.INGOTS_URANIUM);
      this.tag(LodestoneItemTags.NUGGETS_OSMIUM);
      this.tag(LodestoneItemTags.INGOTS_OSMIUM);
      this.tag(LodestoneItemTags.NUGGETS_ZINC);
      this.tag(LodestoneItemTags.INGOTS_ZINC);
      this.tag(LodestoneItemTags.NUGGETS_TIN);
      this.tag(LodestoneItemTags.INGOTS_TIN);
      this.tag(LodestoneItemTags.NUGGETS_COBALT);
      this.tag(LodestoneItemTags.INGOTS_COBALT);
   }
}
