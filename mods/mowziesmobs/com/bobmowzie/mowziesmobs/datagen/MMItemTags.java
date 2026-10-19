package com.bobmowzie.mowziesmobs.datagen;

import com.bobmowzie.mowziesmobs.server.block.BlockHandler;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider.TagLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags.Items;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MMItemTags extends ItemTagsProvider {
   public static final TagKey<Item> CAN_HIT_GROTTOL = key("can_hit_grottol");
   public static final TagKey<Item> HAND_WEAPONS = key("hand_weapons");

   public MMItemTags(
      PackOutput output,
      CompletableFuture<Provider> lookupProvider,
      CompletableFuture<TagLookup<Block>> blockTags,
      @Nullable ExistingFileHelper existingFileHelper
   ) {
      super(output, lookupProvider, blockTags, "mowziesmobs", existingFileHelper);
   }

   protected void addTags(@NotNull Provider provider) {
      this.addToVanillaTags();
      this.addToCommonTags();
      this.tag(CAN_HIT_GROTTOL)
         .addOptional(ResourceLocation.fromNamespaceAndPath("cagedmobs", "dnasamplerdiamond"))
         .addOptional(ResourceLocation.fromNamespaceAndPath("cagedmobs", "dnasamplernetherite"));
      this.tag(HAND_WEAPONS).add((Item)ItemHandler.EARTHREND_GAUNTLET.value());
   }

   private void addToVanillaTags() {
      this.tag(ItemTags.DURABILITY_ENCHANTABLE).add((Item)ItemHandler.SPEAR.value()).add((Item)ItemHandler.NAGA_FANG_DAGGER.value());
      this.tag(ItemTags.SHARP_WEAPON_ENCHANTABLE).add((Item)ItemHandler.SPEAR.value()).add((Item)ItemHandler.NAGA_FANG_DAGGER.value());
      this.tag(ItemTags.SWORD_ENCHANTABLE).add((Item)ItemHandler.SPEAR.value()).add((Item)ItemHandler.NAGA_FANG_DAGGER.value());
      this.tag(ItemTags.FIRE_ASPECT_ENCHANTABLE)
         .add((Item)ItemHandler.SPEAR.value())
         .add((Item)ItemHandler.NAGA_FANG_DAGGER.value())
         .add((Item)ItemHandler.WROUGHT_AXE.value());
      this.tag(ItemTags.AXES).add((Item)ItemHandler.WROUGHT_AXE.value());
      this.tag(ItemTags.BOW_ENCHANTABLE).add((Item)ItemHandler.BLOWGUN.value());
      this.tag(ItemTags.BOW_ENCHANTABLE).add((Item)ItemHandler.BLOWGUN.value());
      this.tag(ItemTags.HEAD_ARMOR)
         .add((Item)ItemHandler.GEOMANCER_BEADS.value())
         .add((Item)ItemHandler.WROUGHT_HELMET.value())
         .add((Item)ItemHandler.SOL_VISAGE.value())
         .add((Item)ItemHandler.UMVUTHANA_MASK_FURY.value())
         .add((Item)ItemHandler.UMVUTHANA_MASK_FEAR.value())
         .add((Item)ItemHandler.UMVUTHANA_MASK_RAGE.value())
         .add((Item)ItemHandler.UMVUTHANA_MASK_BLISS.value())
         .add((Item)ItemHandler.UMVUTHANA_MASK_MISERY.value())
         .add((Item)ItemHandler.UMVUTHANA_MASK_FAITH.value());
      this.tag(ItemTags.CHEST_ARMOR).add((Item)ItemHandler.GEOMANCER_ROBE.value());
      this.tag(ItemTags.LEG_ARMOR).add((Item)ItemHandler.GEOMANCER_BELT.value());
      this.tag(ItemTags.FOOT_ARMOR).add((Item)ItemHandler.GEOMANCER_SANDALS.value());
      this.tag(ItemTags.PIGLIN_LOVED).add((Item)ItemHandler.SOL_VISAGE.value());
      this.tag(ItemTags.PLANKS).add(((Block)BlockHandler.PAINTED_ACACIA.value()).asItem());
      this.tag(ItemTags.WOODEN_SLABS).add(((Block)BlockHandler.PAINTED_ACACIA_SLAB.value()).asItem());
   }

   private void addToCommonTags() {
      this.tag(Items.TOOLS_SPEAR).add((Item)ItemHandler.SPEAR.value());
      this.tag(Items.MUSIC_DISCS).add((Item)ItemHandler.PETIOLE_MUSIC_DISC.value());
      this.tag(Items.SEEDS).add((Item)ItemHandler.FOLIAATH_SEED.value());
      this.tag(Items.SLIME_BALLS).add((Item)ItemHandler.GLOWING_JELLY.value());
   }

   private static TagKey<Item> key(String path) {
      return ItemTags.create(ResourceLocation.fromNamespaceAndPath("mowziesmobs", path));
   }
}
