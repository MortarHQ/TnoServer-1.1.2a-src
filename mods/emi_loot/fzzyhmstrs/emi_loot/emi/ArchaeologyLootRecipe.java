package fzzyhmstrs.emi_loot.emi;

import com.google.common.collect.ArrayListMultimap;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.SlotWidget;
import dev.emi.emi.api.widget.WidgetHolder;
import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.EMILootAgnos;
import fzzyhmstrs.emi_loot.client.ClientArchaeologyLootTable;
import fzzyhmstrs.emi_loot.util.FloatTrimmer;
import fzzyhmstrs.emi_loot.util.InteractableTextWidget;
import fzzyhmstrs.emi_loot.util.LText;
import fzzyhmstrs.emi_loot.util.stack.ArchaeologyLootEmiStack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

public class ArchaeologyLootRecipe implements EmiRecipe {
   private final ClientArchaeologyLootTable loot;
   private final ArrayListMultimap<Float, EmiStack> lootStacksSorted;
   private final int lootStacksSortedSize;
   private final List<EmiStack> outputs;
   private boolean isGuaranteedNonChance = false;
   private final ArchaeologyLootEmiStack inputStack;
   private final float columns = 8.0F;

   public ArchaeologyLootRecipe(ArchaeologyLootRecipe.ArchaeologyLootRecipeData data) {
      this.loot = data.loot;
      this.isGuaranteedNonChance = data.guaranteed;
      ArrayListMultimap<Float, EmiStack> map2 = ArrayListMultimap.create();
      List<EmiStack> outputsList = new ArrayList<>();
      data.map.forEach((weight, item) -> {
         EmiStack stack = EmiStack.of(item);
         map2.put(weight, stack);
         outputsList.add(stack);
      });
      this.lootStacksSorted = map2;
      if (this.loot.items.size() <= 48 && !EMILoot.config.chestLootAlwaysStackSame) {
         this.lootStacksSortedSize = this.loot.items.size();
      } else {
         this.lootStacksSortedSize = this.lootStacksSorted.keySet().size();
      }

      this.outputs = outputsList;
      this.inputStack = new ArchaeologyLootEmiStack(this.loot.id);
   }

   public EmiRecipeCategory getCategory() {
      return EmiClientPlugin.ARCHAEOLOGY_CATEGORY;
   }

   @Nullable
   public ResourceLocation getId() {
      return ResourceLocation.fromNamespaceAndPath(
         "emi_loot", "/" + this.getCategory().id.getPath() + "/" + this.loot.id.getNamespace() + "/" + this.loot.id.getPath()
      );
   }

   public List<EmiIngredient> getInputs() {
      EmiIngredient sand = EmiIngredient.of(Ingredient.of(new ItemLike[]{Items.SUSPICIOUS_SAND, Items.SUSPICIOUS_GRAVEL}));
      EmiIngredient brush = EmiIngredient.of(Ingredient.of(new ItemLike[]{Items.BRUSH}));
      return Arrays.asList(sand, brush);
   }

   public List<EmiStack> getOutputs() {
      return this.outputs;
   }

   public int getDisplayWidth() {
      return 144;
   }

   public int getDisplayHeight() {
      int titleHeight = 11;
      int boxesHeight = (int)Math.ceil(this.lootStacksSortedSize / 8.0F) * (EMILoot.config.isCompact(EMILoot.Type.ARCHAEOLOGY) ? 18 : 19) - 1;
      return titleHeight + boxesHeight;
   }

   public void addWidgets(WidgetHolder widgets) {
      int titleSpace;
      int finalRowHeight;
      if (widgets.getHeight() < this.getDisplayHeight()) {
         titleSpace = 9;
         finalRowHeight = (widgets.getHeight() - titleSpace) / (int)Math.ceil(this.lootStacksSortedSize / 8.0);
      } else {
         titleSpace = 11;
         finalRowHeight = 18;
      }

      widgets.add(new InteractableTextWidget(this.inputStack, 1, 0, 4210752, false).recipeContext(this));
      if (EMILootAgnos.isModLoaded(this.loot.id.getNamespace())) {
         widgets.addTooltip(LText.components(this.inputStack.getName(), this.loot.id.getNamespace()), 0, 0, 144, 10);
      } else {
         widgets.addTooltipText(List.of(this.inputStack.getName()), 0, 0, 144, 10);
      }

      AtomicInteger index = new AtomicInteger(this.lootStacksSortedSize);

      for (Entry<Float, Collection<EmiStack>> entry : this.lootStacksSorted.asMap().entrySet()) {
         float weight = entry.getKey();
         Collection<EmiStack> items = entry.getValue();
         if (this.loot.items.size() <= 48 && !EMILoot.config.chestLootAlwaysStackSame) {
            for (EmiStack stack : items) {
               int row = (int)Math.ceil(index.get() / 8.0F) - 1;
               int column = (index.get() - 1) % 8;
               index.getAndDecrement();
               String fTrim = FloatTrimmer.trimFloatString(weight, (Integer)EMILoot.config.chanceDecimalPlaces.get());
               SlotWidget slotWidget = new SlotWidget(stack, column * 18, titleSpace + row * finalRowHeight).recipeContext(this);
               widgets.add(slotWidget.appendTooltip(LText.translatable("emi_loot.percentage", fTrim).withStyle(ChatFormatting.GRAY)));
            }
         } else {
            int row = (int)Math.ceil(index.get() / 8.0F) - 1;
            int column = (int)((index.get() - 1) % 8.0F);
            index.getAndDecrement();
            EmiIngredient ingredient = EmiIngredient.of(items.stream().toList());
            String fTrim = FloatTrimmer.trimFloatString(Math.max(weight / 100.0F, 0.01F), Math.max((Integer)EMILoot.config.chanceDecimalPlaces.get() + 1, 2));
            SlotWidget slotWidget = new SlotWidget(ingredient, column * 18, titleSpace + row * finalRowHeight).recipeContext(this);
            widgets.add(slotWidget.appendTooltip(LText.translatable("emi_loot.rolls", fTrim).withStyle(ChatFormatting.GRAY)));
         }
      }
   }

   public boolean supportsRecipeTree() {
      return super.supportsRecipeTree() && this.isGuaranteedNonChance;
   }

   public record ArchaeologyLootRecipeData(ClientArchaeologyLootTable loot, ArrayListMultimap<Float, ItemStack> map, boolean guaranteed) {
      public static ArchaeologyLootRecipe.ArchaeologyLootRecipeData of(ClientArchaeologyLootTable loot) {
         boolean isGuaranteedNonChance = false;
         if (loot.items.size() == 1 && loot.items.values().toFloatArray()[0] == 1.0F) {
            isGuaranteedNonChance = true;
         }

         ArrayListMultimap<Float, ItemStack> map2 = ArrayListMultimap.create();
         loot.items.forEach((item, weight) -> map2.put(weight, item));

         for (float key : map2.keySet()) {
            map2.get(key).sort(Comparator.comparingInt(s -> BuiltInRegistries.ITEM.getId(s.getItem())));
         }

         return new ArchaeologyLootRecipe.ArchaeologyLootRecipeData(loot, map2, isGuaranteedNonChance);
      }
   }
}
