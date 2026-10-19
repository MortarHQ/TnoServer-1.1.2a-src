package fzzyhmstrs.emi_loot.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.SlotWidget;
import dev.emi.emi.api.widget.WidgetHolder;
import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.EMILootAgnos;
import fzzyhmstrs.emi_loot.EMILootClientAgnos;
import fzzyhmstrs.emi_loot.client.ClientBuiltPool;
import fzzyhmstrs.emi_loot.client.ClientGameplayLootTable;
import fzzyhmstrs.emi_loot.util.ConditionalStack;
import fzzyhmstrs.emi_loot.util.FloatTrimmer;
import fzzyhmstrs.emi_loot.util.IconGroupEmiWidget;
import fzzyhmstrs.emi_loot.util.InteractableTextWidget;
import fzzyhmstrs.emi_loot.util.LText;
import fzzyhmstrs.emi_loot.util.SymbolText;
import fzzyhmstrs.emi_loot.util.WidgetRowBuilder;
import fzzyhmstrs.emi_loot.util.stack.GameplayLootEmiStack;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import me.fzzyhmstrs.fzzy_config.util.FcText;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

public class GameplayLootRecipe implements EmiRecipe {
   private final ClientGameplayLootTable loot;
   private final List<EmiStack> outputStacks;
   private final GameplayLootEmiStack inputStack;
   private final List<WidgetRowBuilder> rowBuilderList = new ArrayList<>();

   public GameplayLootRecipe(GameplayLootRecipe.GameplayLootRecipeData data) {
      this.loot = data.loot;
      List<EmiStack> list = new ArrayList<>();
      data.loot.builtItems.forEach(builtPool -> {
         builtPool.stacks().forEach(stack -> list.addAll(stack.getStacks()));
         this.addWidgetBuilders(builtPool, false);
      });
      this.outputStacks = list;
      this.inputStack = new GameplayLootEmiStack(this.loot.id);
   }

   private void addWidgetBuilders(ClientBuiltPool newPool, boolean recursive) {
      if (recursive || this.rowBuilderList.isEmpty()) {
         this.rowBuilderList.add(new WidgetRowBuilder(154));
      }

      boolean added = false;

      for (WidgetRowBuilder builder : this.rowBuilderList) {
         if (builder.canAddPool(newPool)) {
            builder.addAndTrim(newPool);
            added = true;
            break;
         }
      }

      if (!added) {
         Optional<ClientBuiltPool> opt = this.rowBuilderList.get(this.rowBuilderList.size() - 1).addAndTrim(newPool);
         opt.ifPresent(clientMobBuiltPool -> this.addWidgetBuilders(clientMobBuiltPool, true));
      }
   }

   public EmiRecipeCategory getCategory() {
      return EmiClientPlugin.GAMEPLAY_CATEGORY;
   }

   @Nullable
   public ResourceLocation getId() {
      return ResourceLocation.fromNamespaceAndPath(
         "emi_loot", "/" + this.getCategory().id.getPath() + "/" + this.loot.id.getNamespace() + "/" + this.loot.id.getPath()
      );
   }

   public List<EmiIngredient> getInputs() {
      return new ArrayList<>();
   }

   public List<EmiIngredient> getCatalysts() {
      return super.getCatalysts();
   }

   public List<EmiStack> getOutputs() {
      return this.outputStacks;
   }

   public int getDisplayWidth() {
      return EMILoot.config.isTooltipStyle() ? 144 : 154;
   }

   public int getDisplayHeight() {
      if (!EMILoot.config.isTooltipStyle()) {
         return this.rowBuilderList.size() * 29 + 11;
      }

      int stacks = this.outputStacks.size();
      if (stacks <= 8) {
         return 29;
      }

      if (!EMILoot.config.isCompact(EMILoot.Type.GAMEPLAY)) {
         return 11 + 18 * ((stacks + 7) / 8);
      }

      int ingredients = 0;

      for (WidgetRowBuilder builder : this.rowBuilderList) {
         ingredients += builder.ingredientCount();
      }

      return ingredients <= 8 ? 29 : 11 + 18 * ((ingredients + 7) / 8);
   }

   public void addWidgets(WidgetHolder widgets) {
      int x = 0;
      int y = 0;
      widgets.add(new InteractableTextWidget(this.inputStack, 1, 0, 4210752, false).recipeContext(this));
      if (EMILootAgnos.isModLoaded(this.loot.id.getNamespace())) {
         widgets.addTooltip(LText.components(this.inputStack.getName(), this.loot.id.getNamespace()), 0, 0, 144, 10);
      } else {
         widgets.addTooltipText(List.of(this.inputStack.getName()), 0, 0, 144, 10);
      }

      if (EMILoot.config.isTooltipStyle()) {
         List<ConditionalStack> stacks = this.outputStacks.size() > 4 && EMILoot.config.isCompact(EMILoot.Type.GAMEPLAY)
            ? this.rowBuilderList.stream().map(WidgetRowBuilder::ingredients).collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll)
            : this.rowBuilderList.stream().map(WidgetRowBuilder::stacks).collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
         int i = 0;
         int j = 0;

         for (ConditionalStack stack : stacks) {
            SlotWidget widget = widgets.addSlot(stack.getIngredient(), i * 18, 11 + 18 * j);
            String rounded = FloatTrimmer.trimFloatString(stack.weight() / 100.0F, (Integer)EMILoot.config.chanceDecimalPlaces.get());
            widget.appendTooltip(FcText.INSTANCE.translatable("emi_loot.rolls", new Object[]{rounded}).withStyle(ChatFormatting.GRAY));
            if (EMILoot.config.isNotPlain()) {
               for (Tuple<Integer, Component> pair : stack.conditions()) {
                  widget.appendTooltip(SymbolText.of((Integer)pair.getA(), (Component)pair.getB()));
               }
            }

            if (++i > 7) {
               i = 0;
               j++;
            }
         }
      } else {
         y += 11;

         for (WidgetRowBuilder builder : this.rowBuilderList) {
            for (ClientBuiltPool pool : builder.getPoolList()) {
               IconGroupEmiWidget widget = EMILootClientAgnos.createIconGroupEmiWidget(x, y, pool, this);
               widgets.add(widget);
               x += widget.getWidth() + 6;
            }

            y += 29;
            x = 0;
         }
      }
   }

   public boolean supportsRecipeTree() {
      return false;
   }

   public boolean hideCraftable() {
      return super.hideCraftable();
   }

   public record GameplayLootRecipeData(ClientGameplayLootTable loot) {
      public static GameplayLootRecipe.GameplayLootRecipeData of(ClientGameplayLootTable loot) {
         loot.build(Minecraft.getInstance().level, Blocks.AIR);
         return new GameplayLootRecipe.GameplayLootRecipeData(loot);
      }
   }
}
