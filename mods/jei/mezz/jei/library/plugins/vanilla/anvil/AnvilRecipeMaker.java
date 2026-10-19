package mezz.jei.library.plugins.vanilla.anvil;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.recipe.vanilla.IJeiAnvilRecipe;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.common.platform.IPlatformItemStackHelper;
import mezz.jei.common.platform.Services;
import mezz.jei.common.util.ErrorUtil;
import mezz.jei.common.util.RegistryUtil;
import mezz.jei.library.plugins.vanilla.ingredients.subtypes.EnchantedBookSubtypeInterpreter;
import mezz.jei.library.util.ResourceLocationUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments.Mutable;
import net.minecraft.world.level.ItemLike;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class AnvilRecipeMaker {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final ItemStack ENCHANTED_BOOK = new ItemStack(Items.ENCHANTED_BOOK);

   private AnvilRecipeMaker() {
   }

   public static List<IJeiAnvilRecipe> getAnvilRecipes(IVanillaRecipeFactory vanillaRecipeFactory, IIngredientManager ingredientManager) {
      IIngredientHelper<ItemStack> ingredientHelper = ingredientManager.getIngredientHelper(VanillaTypes.ITEM_STACK);
      return Stream.concat(getRepairRecipes(vanillaRecipeFactory, ingredientHelper), getBookEnchantmentRecipes(vanillaRecipeFactory, ingredientManager))
         .toList();
   }

   private static Stream<IJeiAnvilRecipe> getBookEnchantmentRecipes(IVanillaRecipeFactory vanillaRecipeFactory, IIngredientManager ingredientManager) {
      Registry<Enchantment> registry = RegistryUtil.getRegistry(Registries.ENCHANTMENT);
      List<AnvilRecipeMaker.EnchantmentData> enchantmentDatas = registry.holders().map(AnvilRecipeMaker.EnchantmentData::new).toList();
      return ingredientManager.getAllItemStacks()
         .stream()
         .filter(ItemStack::isEnchantable)
         .flatMap(ingredient -> getBookEnchantmentRecipes(vanillaRecipeFactory, enchantmentDatas, ingredient));
   }

   private static Stream<IJeiAnvilRecipe> getBookEnchantmentRecipes(
      IVanillaRecipeFactory vanillaRecipeFactory, List<AnvilRecipeMaker.EnchantmentData> enchantmentDatas, ItemStack ingredient
   ) {
      List<ItemStack> ingredientSingletonList = List.of(ingredient);
      return enchantmentDatas.stream()
         .filter(data -> data.canEnchant(ingredient))
         .map(data -> data.getEnchantedBooks(ingredient))
         .filter(enchantedBooks -> !enchantedBooks.isEmpty())
         .map(enchantedBooks -> {
            List<ItemStack> outputs = getEnchantedIngredients(ingredient, (List<ItemStack>)enchantedBooks);
            String ingredientId = EnchantedBookSubtypeInterpreter.INSTANCE.getStringName(ingredient);
            String ingredientIdPath = ResourceLocationUtil.sanitizePath(ingredientId);
            String id = "enchantment." + ingredientIdPath;
            ResourceLocation uid = ResourceLocation.fromNamespaceAndPath("minecraft", id);
            return new AnvilRecipe(ingredientSingletonList, (List<ItemStack>)enchantedBooks, outputs, uid);
         });
   }

   private static List<ItemStack> getEnchantedIngredients(ItemStack ingredient, List<ItemStack> enchantedBooks) {
      return Lists.transform(enchantedBooks, enchantedBook -> getEnchantedIngredient(ingredient, enchantedBook));
   }

   private static ItemStack getEnchantedIngredient(ItemStack ingredient, ItemStack enchantedBook) {
      ItemStack enchantedIngredient = ingredient.copy();
      ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(enchantedBook);
      EnchantmentHelper.setEnchantments(enchantedIngredient, enchantments);
      return enchantedIngredient;
   }

   private static Stream<AnvilRecipeMaker.RepairData> getRepairData() {
      return Stream.of(
         new AnvilRecipeMaker.RepairData(
            Tiers.WOOD.getRepairIngredient(),
            new ItemStack(Items.WOODEN_SWORD),
            new ItemStack(Items.WOODEN_PICKAXE),
            new ItemStack(Items.WOODEN_AXE),
            new ItemStack(Items.WOODEN_SHOVEL),
            new ItemStack(Items.WOODEN_HOE)
         ),
         new AnvilRecipeMaker.RepairData(Ingredient.of(ItemTags.PLANKS), new ItemStack(Items.SHIELD)),
         new AnvilRecipeMaker.RepairData(
            Tiers.STONE.getRepairIngredient(),
            new ItemStack(Items.STONE_SWORD),
            new ItemStack(Items.STONE_PICKAXE),
            new ItemStack(Items.STONE_AXE),
            new ItemStack(Items.STONE_SHOVEL),
            new ItemStack(Items.STONE_HOE)
         ),
         new AnvilRecipeMaker.RepairData(
            (Ingredient)((ArmorMaterial)ArmorMaterials.LEATHER.value()).repairIngredient().get(),
            new ItemStack(Items.LEATHER_HELMET),
            new ItemStack(Items.LEATHER_CHESTPLATE),
            new ItemStack(Items.LEATHER_LEGGINGS),
            new ItemStack(Items.LEATHER_BOOTS)
         ),
         new AnvilRecipeMaker.RepairData(
            Tiers.IRON.getRepairIngredient(),
            new ItemStack(Items.IRON_SWORD),
            new ItemStack(Items.IRON_PICKAXE),
            new ItemStack(Items.IRON_AXE),
            new ItemStack(Items.IRON_SHOVEL),
            new ItemStack(Items.IRON_HOE)
         ),
         new AnvilRecipeMaker.RepairData(
            (Ingredient)((ArmorMaterial)ArmorMaterials.IRON.value()).repairIngredient().get(),
            new ItemStack(Items.IRON_HELMET),
            new ItemStack(Items.IRON_CHESTPLATE),
            new ItemStack(Items.IRON_LEGGINGS),
            new ItemStack(Items.IRON_BOOTS)
         ),
         new AnvilRecipeMaker.RepairData(
            (Ingredient)((ArmorMaterial)ArmorMaterials.CHAIN.value()).repairIngredient().get(),
            new ItemStack(Items.CHAINMAIL_HELMET),
            new ItemStack(Items.CHAINMAIL_CHESTPLATE),
            new ItemStack(Items.CHAINMAIL_LEGGINGS),
            new ItemStack(Items.CHAINMAIL_BOOTS)
         ),
         new AnvilRecipeMaker.RepairData(
            Tiers.GOLD.getRepairIngredient(),
            new ItemStack(Items.GOLDEN_SWORD),
            new ItemStack(Items.GOLDEN_PICKAXE),
            new ItemStack(Items.GOLDEN_AXE),
            new ItemStack(Items.GOLDEN_SHOVEL),
            new ItemStack(Items.GOLDEN_HOE)
         ),
         new AnvilRecipeMaker.RepairData(
            (Ingredient)((ArmorMaterial)ArmorMaterials.GOLD.value()).repairIngredient().get(),
            new ItemStack(Items.GOLDEN_HELMET),
            new ItemStack(Items.GOLDEN_CHESTPLATE),
            new ItemStack(Items.GOLDEN_LEGGINGS),
            new ItemStack(Items.GOLDEN_BOOTS)
         ),
         new AnvilRecipeMaker.RepairData(
            Tiers.DIAMOND.getRepairIngredient(),
            new ItemStack(Items.DIAMOND_SWORD),
            new ItemStack(Items.DIAMOND_PICKAXE),
            new ItemStack(Items.DIAMOND_AXE),
            new ItemStack(Items.DIAMOND_SHOVEL),
            new ItemStack(Items.DIAMOND_HOE)
         ),
         new AnvilRecipeMaker.RepairData(
            (Ingredient)((ArmorMaterial)ArmorMaterials.DIAMOND.value()).repairIngredient().get(),
            new ItemStack(Items.DIAMOND_HELMET),
            new ItemStack(Items.DIAMOND_CHESTPLATE),
            new ItemStack(Items.DIAMOND_LEGGINGS),
            new ItemStack(Items.DIAMOND_BOOTS)
         ),
         new AnvilRecipeMaker.RepairData(
            Tiers.NETHERITE.getRepairIngredient(),
            new ItemStack(Items.NETHERITE_SWORD),
            new ItemStack(Items.NETHERITE_AXE),
            new ItemStack(Items.NETHERITE_HOE),
            new ItemStack(Items.NETHERITE_SHOVEL),
            new ItemStack(Items.NETHERITE_PICKAXE)
         ),
         new AnvilRecipeMaker.RepairData(
            (Ingredient)((ArmorMaterial)ArmorMaterials.NETHERITE.value()).repairIngredient().get(),
            new ItemStack(Items.NETHERITE_BOOTS),
            new ItemStack(Items.NETHERITE_HELMET),
            new ItemStack(Items.NETHERITE_LEGGINGS),
            new ItemStack(Items.NETHERITE_CHESTPLATE)
         ),
         new AnvilRecipeMaker.RepairData(Ingredient.of(new ItemLike[]{Items.PHANTOM_MEMBRANE}), new ItemStack(Items.ELYTRA)),
         new AnvilRecipeMaker.RepairData(
            (Ingredient)((ArmorMaterial)ArmorMaterials.TURTLE.value()).repairIngredient().get(), new ItemStack(Items.TURTLE_HELMET)
         )
      );
   }

   private static Stream<IJeiAnvilRecipe> getRepairRecipes(IVanillaRecipeFactory vanillaRecipeFactory, IIngredientHelper<ItemStack> ingredientHelper) {
      return getRepairData().flatMap(repairData -> getRepairRecipes(repairData, vanillaRecipeFactory, ingredientHelper));
   }

   private static Stream<IJeiAnvilRecipe> getRepairRecipes(
      AnvilRecipeMaker.RepairData repairData, IVanillaRecipeFactory vanillaRecipeFactory, IIngredientHelper<ItemStack> ingredientHelper
   ) {
      Ingredient repairIngredient = repairData.getRepairIngredient();
      List<ItemStack> repairables = repairData.getRepairables();
      List<ItemStack> repairMaterials = List.of(repairIngredient.getItems());
      return repairables.stream()
         .mapMulti(
            (itemStack, consumer) -> {
               String uid = EnchantedBookSubtypeInterpreter.INSTANCE.getStringName(itemStack);
               String ingredientIdPath = ResourceLocationUtil.sanitizePath(uid);
               String itemModId = ingredientHelper.getResourceLocation(itemStack).getNamespace();
               ItemStack damagedThreeQuarters = itemStack.copy();
               damagedThreeQuarters.setDamageValue(damagedThreeQuarters.getMaxDamage() * 3 / 4);
               ItemStack damagedHalf = itemStack.copy();
               damagedHalf.setDamageValue(damagedHalf.getMaxDamage() / 2);
               List<ItemStack> damagedThreeQuartersSingletonList = List.of(damagedThreeQuarters);
               IJeiAnvilRecipe repairWithSame = vanillaRecipeFactory.createAnvilRecipe(
                  damagedThreeQuartersSingletonList,
                  damagedThreeQuartersSingletonList,
                  List.of(damagedHalf),
                  ResourceLocation.fromNamespaceAndPath(itemModId, "anvil.self_repair." + ingredientIdPath)
               );
               consumer.accept(repairWithSame);
               if (!repairMaterials.isEmpty()) {
                  ItemStack damagedFully = itemStack.copy();
                  damagedFully.setDamageValue(damagedFully.getMaxDamage());
                  IJeiAnvilRecipe repairWithMaterial = vanillaRecipeFactory.createAnvilRecipe(
                     List.of(damagedFully),
                     repairMaterials,
                     damagedThreeQuartersSingletonList,
                     ResourceLocation.fromNamespaceAndPath(itemModId, "anvil.materials_repair." + ingredientIdPath)
                  );
                  consumer.accept(repairWithMaterial);
               }
            }
         );
   }

   public static int findLevelsCost(ItemStack leftStack, ItemStack rightStack) {
      Player player = Minecraft.getInstance().player;
      if (player == null) {
         return -1;
      }

      Inventory fakeInventory = new Inventory(player);

      try {
         AnvilMenu repair = new AnvilMenu(0, fakeInventory);
         ((Slot)repair.slots.get(0)).set(leftStack);
         ((Slot)repair.slots.get(1)).set(rightStack);
         return repair.getCost();
      } catch (RuntimeException e) {
         String left = ErrorUtil.getItemStackInfo(leftStack);
         String right = ErrorUtil.getItemStackInfo(rightStack);
         LOGGER.error("Could not get anvil level cost for: ({} and {}).", left, right, e);
         return -1;
      }
   }

   private static final class EnchantmentData {
      private final Holder<Enchantment> enchantment;
      private final List<ItemStack> enchantedBooks;

      private EnchantmentData(Holder<Enchantment> enchantment) {
         this.enchantment = enchantment;
         this.enchantedBooks = getEnchantedBooks(enchantment);
      }

      public List<ItemStack> getEnchantedBooks(ItemStack ingredient) {
         IPlatformItemStackHelper itemStackHelper = Services.PLATFORM.getItemStackHelper();
         List<ItemStack> list = this.enchantedBooks.stream().filter(enchantedBook -> itemStackHelper.isBookEnchantable(ingredient, enchantedBook)).toList();
         return list.size() == this.enchantedBooks.size() ? this.enchantedBooks : list;
      }

      private boolean canEnchant(ItemStack ingredient) {
         try {
            return ((Enchantment)this.enchantment.value()).canEnchant(ingredient);
         } catch (RuntimeException e) {
            String stackInfo = ErrorUtil.getItemStackInfo(ingredient);
            AnvilRecipeMaker.LOGGER.error("Failed to check if ingredient can be enchanted: {}", stackInfo, e);
            return false;
         }
      }

      private static List<ItemStack> getEnchantedBooks(Holder<Enchantment> enchantment) {
         return IntStream.rangeClosed(1, ((Enchantment)enchantment.value()).getMaxLevel()).mapToObj(level -> {
            ItemStack bookEnchant = AnvilRecipeMaker.ENCHANTED_BOOK.copy();
            Mutable itemEnchantments = new Mutable(EnchantmentHelper.getEnchantmentsForCrafting(bookEnchant));
            itemEnchantments.set(enchantment, level);
            EnchantmentHelper.setEnchantments(bookEnchant, itemEnchantments.toImmutable());
            return bookEnchant;
         }).toList();
      }
   }

   private static class RepairData {
      private final Ingredient repairIngredient;
      private final List<ItemStack> repairables;

      public RepairData(Ingredient repairIngredient, ItemStack... repairables) {
         this.repairIngredient = repairIngredient;
         this.repairables = List.of(repairables);
      }

      public Ingredient getRepairIngredient() {
         return this.repairIngredient;
      }

      public List<ItemStack> getRepairables() {
         return this.repairables;
      }
   }
}
