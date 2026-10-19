package twilightforest.data.helpers;

import com.mojang.datafixers.util.Pair;
import java.util.concurrent.CompletableFuture;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.DataComponentPatch.Builder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments.Mutable;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.neoforged.neoforge.common.Tags.Items;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import twilightforest.TwilightForestMod;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.init.TFBlocks;

public abstract class CraftingDataHelper extends RecipeProvider {
   public CraftingDataHelper(PackOutput output, CompletableFuture<Provider> provider) {
      super(output, provider);
   }

   protected final void charmRecipe(RecipeOutput output, String name, DeferredHolder<Item, ? extends Item> result, DeferredHolder<Item, ? extends Item> item) {
      ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, (ItemLike)result.get())
         .requires((ItemLike)item.get(), 4)
         .unlockedBy("has_item", has((ItemLike)item.get()))
         .save(output, TwilightForestMod.prefix(name));
   }

   protected final void castleBlock(RecipeOutput output, DeferredBlock<? extends Block> result, ItemLike... ingredients) {
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)result.get(), 4)
         .pattern("##")
         .pattern("##")
         .define('#', Ingredient.of(ingredients))
         .unlockedBy("has_castle_brick", has((ItemLike)TFBlocks.CASTLE_BRICK.get()))
         .save(output, this.locCastle(BuiltInRegistries.BLOCK.getKey((Block)result.get()).getPath()));
   }

   protected final void woodenStairsBlock(
      RecipeOutput output, ResourceLocation loc, DeferredBlock<? extends Block> result, DeferredBlock<? extends Block> criteria, ItemLike... ingredients
   ) {
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)result.get(), 8)
         .pattern("#  ")
         .pattern("## ")
         .pattern("###")
         .define('#', Ingredient.of(ingredients))
         .unlockedBy("has_item", has((ItemLike)criteria.get()))
         .group("wooden_stairs")
         .save(output, loc);
   }

   protected final void stairsBlock(
      RecipeOutput output, ResourceLocation loc, DeferredBlock<? extends Block> result, DeferredBlock<? extends Block> criteria, ItemLike... ingredients
   ) {
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)result.get(), 8)
         .pattern("#  ")
         .pattern("## ")
         .pattern("###")
         .define('#', Ingredient.of(ingredients))
         .unlockedBy("has_item", has((ItemLike)criteria.get()))
         .save(output, loc);
   }

   protected final void stairsRightBlock(
      RecipeOutput output, ResourceLocation loc, DeferredBlock<? extends Block> result, DeferredBlock<? extends Block> criteria, ItemLike... ingredients
   ) {
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)result.get(), 8)
         .pattern("###")
         .pattern(" ##")
         .pattern("  #")
         .define('#', Ingredient.of(ingredients))
         .unlockedBy("has_item", has((ItemLike)criteria.get()))
         .save(output, loc);
   }

   protected final void compressedBlock(RecipeOutput output, DeferredBlock<? extends Block> result, TagKey<Item> ingredient, DeferredItem<Item> middleIng) {
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)result.get())
         .pattern("###")
         .pattern("#m#")
         .pattern("###")
         .define('#', ingredient)
         .define('m', middleIng)
         .unlockedBy("has_item", has(ingredient))
         .save(output, TwilightForestMod.prefix("compressed_blocks/" + result.getId().getPath()));
   }

   protected final void reverseCompressBlock(RecipeOutput output, DeferredHolder<Item, ? extends Item> result, DeferredBlock<? extends Block> ingredient) {
      ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)result.get(), 9)
         .requires(ingredient)
         .unlockedBy("has_item", has(ingredient))
         .save(output, TwilightForestMod.prefix("compressed_blocks/reversed/" + ingredient.getId().getPath()));
   }

   protected final void helmetItem(RecipeOutput output, DeferredHolder<Item, ? extends Item> result, TagKey<Item> material) {
      this.helmetItem(output, result, material, DataComponentPatch.builder());
   }

   protected final void helmetItem(RecipeOutput output, DeferredHolder<Item, ? extends Item> result, TagKey<Item> material, Builder component) {
      ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, new ItemStack(result, 1, component.build()))
         .pattern("###")
         .pattern("# #")
         .define('#', material)
         .unlockedBy("has_item", has(material))
         .save(output, this.locEquip(result.getKey().location().getPath()));
   }

   protected final void chestplateItem(RecipeOutput output, DeferredHolder<Item, ? extends Item> result, TagKey<Item> material) {
      this.chestplateItem(output, result, material, DataComponentPatch.builder());
   }

   protected final void chestplateItem(RecipeOutput output, DeferredHolder<Item, ? extends Item> result, TagKey<Item> material, Builder component) {
      ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, new ItemStack(result, 1, component.build()))
         .pattern("# #")
         .pattern("###")
         .pattern("###")
         .define('#', material)
         .unlockedBy("has_item", has(material))
         .save(output, this.locEquip(result.getKey().location().getPath()));
   }

   protected final void leggingsItem(RecipeOutput output, DeferredHolder<Item, ? extends Item> result, TagKey<Item> material) {
      this.leggingsItem(output, result, material, DataComponentPatch.builder());
   }

   protected final void leggingsItem(RecipeOutput output, DeferredHolder<Item, ? extends Item> result, TagKey<Item> material, Builder component) {
      ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, new ItemStack(result, 1, component.build()))
         .pattern("###")
         .pattern("# #")
         .pattern("# #")
         .define('#', material)
         .unlockedBy("has_item", has(material))
         .save(output, this.locEquip(result.getKey().location().getPath()));
   }

   protected final void bootsItem(RecipeOutput output, DeferredHolder<Item, ? extends Item> result, TagKey<Item> material) {
      this.bootsItem(output, result, material, DataComponentPatch.builder());
   }

   protected final void bootsItem(RecipeOutput output, DeferredHolder<Item, ? extends Item> result, TagKey<Item> material, Builder component) {
      ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, new ItemStack(result, 1, component.build()))
         .pattern("# #")
         .pattern("# #")
         .define('#', material)
         .unlockedBy("has_item", has(material))
         .save(output, this.locEquip(result.getKey().location().getPath()));
   }

   protected final void pickaxeItem(RecipeOutput output, DeferredHolder<Item, ? extends Item> result, TagKey<Item> material, TagKey<Item> handle) {
      this.pickaxeItem(output, result, material, handle, DataComponentPatch.builder());
   }

   protected final void pickaxeItem(
      RecipeOutput output, DeferredHolder<Item, ? extends Item> result, TagKey<Item> material, TagKey<Item> handle, Builder component
   ) {
      ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, new ItemStack(result, 1, component.build()))
         .pattern("###")
         .pattern(" X ")
         .pattern(" X ")
         .define('#', material)
         .define('X', handle)
         .unlockedBy("has_item", has(material))
         .save(output, this.locEquip(result.getKey().location().getPath()));
   }

   protected final void swordItem(RecipeOutput output, DeferredHolder<Item, ? extends Item> result, TagKey<Item> material, TagKey<Item> handle) {
      this.swordItem(output, result, material, handle, DataComponentPatch.builder());
   }

   protected final void swordItem(
      RecipeOutput output, DeferredHolder<Item, ? extends Item> result, TagKey<Item> material, TagKey<Item> handle, Builder component
   ) {
      ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, new ItemStack(result, 1, component.build()))
         .pattern("#")
         .pattern("#")
         .pattern("X")
         .define('#', material)
         .define('X', handle)
         .unlockedBy("has_item", has(material))
         .save(output, this.locEquip(result.getKey().location().getPath()));
   }

   protected final void axeItem(RecipeOutput output, DeferredHolder<Item, ? extends Item> result, TagKey<Item> material, TagKey<Item> handle) {
      this.axeItem(output, result, material, handle, DataComponentPatch.builder());
   }

   protected final void axeItem(RecipeOutput output, DeferredHolder<Item, ? extends Item> result, TagKey<Item> material, TagKey<Item> handle, Builder component) {
      ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, new ItemStack(result, 1, component.build()))
         .pattern("##")
         .pattern("#X")
         .pattern(" X")
         .define('#', material)
         .define('X', handle)
         .unlockedBy("has_item", has(material))
         .save(output, this.locEquip(result.getKey().location().getPath()));
   }

   protected final void shovelItem(
      RecipeOutput output, DeferredHolder<Item, ? extends Item> result, TagKey<Item> material, TagKey<Item> handle, Builder component
   ) {
      ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, new ItemStack(result, 1, component.build()))
         .pattern("#")
         .pattern("X")
         .pattern("X")
         .define('#', material)
         .define('X', handle)
         .unlockedBy("has_item", has(material))
         .save(output, this.locEquip(result.getKey().location().getPath()));
   }

   protected final void hoeItem(RecipeOutput output, DeferredHolder<Item, ? extends Item> result, TagKey<Item> material, TagKey<Item> handle, Builder component) {
      ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, new ItemStack(result, 1, component.build()))
         .pattern("##")
         .pattern(" X")
         .pattern(" X")
         .define('#', material)
         .define('X', handle)
         .unlockedBy("has_item", has(material))
         .save(output, this.locEquip(result.getKey().location().getPath()));
   }

   @SafeVarargs
   protected final Builder buildEnchants(Provider provider, Pair<ResourceKey<Enchantment>, Integer>... enchantments) {
      RegistryLookup<Enchantment> lookup = provider.lookupOrThrow(Registries.ENCHANTMENT);
      Mutable itemEnchants = new Mutable(ItemEnchantments.EMPTY);

      for (Pair<ResourceKey<Enchantment>, Integer> pair : enchantments) {
         itemEnchants.set(lookup.getOrThrow((ResourceKey)pair.getFirst()), (Integer)pair.getSecond());
      }

      return DataComponentPatch.builder().set(DataComponents.ENCHANTMENTS, itemEnchants.toImmutable());
   }

   protected final void buttonBlock(RecipeOutput output, String name, DeferredBlock<? extends Block> result, DeferredBlock<? extends Block> material) {
      ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, (ItemLike)result.get())
         .requires((ItemLike)material.get())
         .unlockedBy("has_item", has((ItemLike)material.get()))
         .group("wooden_button")
         .save(output, this.locWood(name + "_button"));
   }

   protected final void doorBlock(RecipeOutput output, String name, DeferredBlock<? extends Block> result, DeferredBlock<? extends Block> material) {
      ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)result.get(), 3)
         .pattern("##")
         .pattern("##")
         .pattern("##")
         .define('#', (ItemLike)material.get())
         .unlockedBy("has_item", has((ItemLike)material.get()))
         .group("wooden_door")
         .save(output, this.locWood(name + "_door"));
   }

   protected final void fenceBlock(RecipeOutput output, String name, DeferredBlock<? extends Block> result, DeferredBlock<? extends Block> material) {
      ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)result.get(), 3)
         .pattern("#S#")
         .pattern("#S#")
         .define('#', (ItemLike)material.get())
         .define('S', Items.RODS_WOODEN)
         .unlockedBy("has_item", has((ItemLike)material.get()))
         .group("wooden_fence")
         .save(output, this.locWood(name + "_fence"));
   }

   protected final void gateBlock(RecipeOutput output, String name, DeferredBlock<? extends Block> result, DeferredBlock<? extends Block> material) {
      ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)result.get())
         .pattern("S#S")
         .pattern("S#S")
         .define('#', (ItemLike)material.get())
         .define('S', Items.RODS_WOODEN)
         .unlockedBy("has_item", has((ItemLike)material.get()))
         .group("wooden_fence_gate")
         .save(output, this.locWood(name + "_gate"));
   }

   protected final void planksBlock(RecipeOutput output, String name, DeferredBlock<? extends Block> result, TagKey<Item> material) {
      ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)result.get(), 4)
         .requires(material)
         .unlockedBy("has_item", has(material))
         .group("planks")
         .save(output, this.locWood(name + "_planks"));
   }

   protected final void plateBlock(RecipeOutput output, String name, DeferredBlock<? extends Block> result, DeferredBlock<? extends Block> material) {
      ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)result.get())
         .pattern("##")
         .define('#', (ItemLike)material.get())
         .unlockedBy("has_item", has((ItemLike)material.get()))
         .group("wooden_pressure_plate")
         .save(output, this.locWood(name + "_plate"));
   }

   protected final void woodenSlabBlock(RecipeOutput output, String name, DeferredBlock<? extends Block> result, DeferredBlock<? extends Block> material) {
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)result.get(), 6)
         .pattern("###")
         .define('#', (ItemLike)material.get())
         .unlockedBy("has_item", has((ItemLike)material.get()))
         .group("wooden_slab")
         .save(output, this.locWood(name + "_slab"));
   }

   protected final void slabBlock(RecipeOutput output, String name, DeferredBlock<? extends Block> result, DeferredBlock<? extends Block> material) {
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)result.get(), 6)
         .pattern("###")
         .define('#', (ItemLike)material.get())
         .unlockedBy("has_item", has((ItemLike)material.get()))
         .save(output, this.locWood(name + "_slab"));
   }

   protected final void bannerPattern(RecipeOutput output, String name, DeferredBlock<? extends Block> trophy, DeferredHolder<Item, ? extends Item> result) {
      ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)result.get())
         .requires(Ingredient.of(ItemTagGenerator.PAPER))
         .requires(Ingredient.of(new ItemLike[]{((Block)trophy.get()).asItem()}))
         .unlockedBy("has_trophy", has((ItemLike)trophy.get()))
         .save(output);
   }

   protected final void trapdoorBlock(RecipeOutput output, String name, DeferredBlock<? extends Block> result, DeferredBlock<? extends Block> material) {
      ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)result.get(), 2)
         .pattern("###")
         .pattern("###")
         .define('#', (ItemLike)material.get())
         .unlockedBy("has_item", has((ItemLike)material.get()))
         .group("wooden_trapdoor")
         .save(output, this.locWood(name + "_trapdoor"));
   }

   protected final void woodBlock(RecipeOutput output, String name, DeferredBlock<? extends Block> result, DeferredBlock<? extends Block> material) {
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)result.get(), 3)
         .pattern("##")
         .pattern("##")
         .define('#', (ItemLike)material.get())
         .unlockedBy("has_item", has((ItemLike)material.get()))
         .group("bark")
         .save(output, this.locWood(name + "_wood"));
   }

   protected final void strippedWoodBlock(RecipeOutput output, String name, DeferredBlock<? extends Block> result, DeferredBlock<? extends Block> material) {
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)result.get(), 3)
         .pattern("##")
         .pattern("##")
         .define('#', (ItemLike)material.get())
         .unlockedBy("has_item", has((ItemLike)material.get()))
         .save(output, this.locWood(name + "_stripped_wood"));
   }

   protected final void signBlock(RecipeOutput output, String name, DeferredHolder<Item, ? extends Item> result, DeferredBlock<? extends Block> material) {
      ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)result.get(), 3)
         .pattern("###")
         .pattern("###")
         .pattern(" - ")
         .define('#', (ItemLike)material.get())
         .define('-', Items.RODS_WOODEN)
         .unlockedBy("has_item", has((ItemLike)material.get()))
         .group("wooden_sign")
         .save(output, this.locWood(name + "_sign"));
   }

   protected final void hangingSignBlock(RecipeOutput output, String name, DeferredHolder<Item, ? extends Item> result, DeferredBlock<? extends Block> material) {
      ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)result.get(), 6)
         .pattern("| |")
         .pattern("###")
         .pattern("###")
         .define('#', (ItemLike)material.get())
         .define('|', net.minecraft.world.item.Items.CHAIN)
         .unlockedBy("has_item", has((ItemLike)material.get()))
         .group("hanging_sign")
         .save(output, this.locWood(name + "_hanging_sign"));
   }

   protected final void banisterBlock(RecipeOutput output, String name, DeferredBlock<? extends Block> result, DeferredBlock<? extends Block> material) {
      this.banisterBlock(output, name, result, (Block)material.get());
   }

   protected final void banisterBlock(RecipeOutput output, String name, DeferredBlock<? extends Block> result, Block material) {
      ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)result.get(), 3)
         .pattern("---")
         .pattern("| |")
         .define('-', material)
         .define('|', Items.RODS_WOODEN)
         .unlockedBy("has_item", has(material))
         .group("wooden_banister")
         .save(output, this.locWood(name + "_banister"));
   }

   protected final void dryingRackBlock(RecipeOutput output, String name, DeferredBlock<? extends Block> result, DeferredBlock<? extends Block> material) {
      this.dryingRackBlock(output, name, result, (Block)material.get());
   }

   protected final void dryingRackBlock(RecipeOutput output, String name, DeferredBlock<? extends Block> result, Block material) {
      ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)result.get())
         .pattern("---")
         .define('-', material)
         .unlockedBy("has_item", has(material))
         .group("drying_rack")
         .save(output, this.locWood(name + "_drying_rack"));
   }

   protected final void chestBlock(
      RecipeOutput output,
      String name,
      DeferredHolder<Block, ? extends ChestBlock> chest,
      DeferredHolder<Block, ? extends ChestBlock> trapped,
      DeferredBlock<? extends Block> material
   ) {
      ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)chest.get(), 2)
         .pattern("###")
         .pattern("#C#")
         .pattern("###")
         .define('#', (ItemLike)material.get())
         .define('C', net.minecraft.world.item.Items.CHEST)
         .unlockedBy("has_item", has((ItemLike)material.get()))
         .group("chest")
         .save(output, this.locWood(name + "_chest"));
      ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)trapped.get(), 2)
         .pattern("###")
         .pattern("#C#")
         .pattern("###")
         .define('#', (ItemLike)material.get())
         .define('C', net.minecraft.world.item.Items.TRAPPED_CHEST)
         .unlockedBy("has_item", has((ItemLike)material.get()))
         .group("trapped_chest")
         .save(output, this.locWood(name + "_trapped_chest"));
   }

   protected final void fieryConversion(RecipeOutput output, DeferredHolder<Item, ? extends Item> result, Item armor, int vials) {
      ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, (ItemLike)result.get())
         .requires(armor)
         .requires(Ingredient.of(ItemTagGenerator.FIERY_VIAL), vials)
         .unlockedBy("has_item", has(ItemTagGenerator.FIERY_VIAL))
         .group(result.getKey().location().getPath())
         .save(output, this.locEquip("fiery_" + BuiltInRegistries.ITEM.getKey(armor).getPath()));
   }

   protected final void buildBoats(
      RecipeOutput output, DeferredHolder<Item, ? extends Item> boat, DeferredHolder<Item, ? extends Item> chestBoat, DeferredBlock<? extends Block> planks
   ) {
      ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, (ItemLike)boat.get())
         .pattern("P P")
         .pattern("PPP")
         .define('P', (ItemLike)planks.get())
         .group("boat")
         .unlockedBy("in_water", insideOf(Blocks.WATER))
         .save(output);
      ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, (ItemLike)chestBoat.get())
         .requires((ItemLike)boat.get())
         .requires(Items.CHESTS_WOODEN)
         .group("chest_boat")
         .unlockedBy("has_boat", has(ItemTags.BOATS))
         .save(output);
   }

   protected final ResourceLocation locCastle(String name) {
      return TwilightForestMod.prefix("castleblock/" + name);
   }

   protected final ResourceLocation locEquip(String name) {
      return TwilightForestMod.prefix("equipment/" + name);
   }

   protected final ResourceLocation locNaga(String name) {
      return TwilightForestMod.prefix("nagastone/" + name);
   }

   protected final ResourceLocation locWood(String name) {
      return TwilightForestMod.prefix("wood/" + name);
   }

   protected static Criterion<TriggerInstance> has(TagKey<Item> tag) {
      return inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(tag).build()});
   }
}
