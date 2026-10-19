package fzzyhmstrs.emi_loot.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.SlotWidget;
import dev.emi.emi.api.widget.WidgetHolder;
import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.EMILootClientAgnos;
import fzzyhmstrs.emi_loot.client.ClientBlockLootTable;
import fzzyhmstrs.emi_loot.client.ClientBuiltPool;
import fzzyhmstrs.emi_loot.client.InitializedSupplier;
import fzzyhmstrs.emi_loot.util.ConditionalStack;
import fzzyhmstrs.emi_loot.util.FloatTrimmer;
import fzzyhmstrs.emi_loot.util.IconGroupEmiWidget;
import fzzyhmstrs.emi_loot.util.SymbolText;
import fzzyhmstrs.emi_loot.util.WidgetRowBuilder;
import fzzyhmstrs.emi_loot.util.stack.BlockLootEmiStack;
import fzzyhmstrs.emi_loot.util.stack.BlockStateEmiStack;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import me.fzzyhmstrs.fzzy_config.util.FcText;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class BlockLootRecipe implements EmiRecipe {
   private final ResourceLocation id;
   private final InitializedSupplier<EmiStack> inputStack;
   private final BlockLootEmiStack lootStack;
   private final InitializedSupplier<List<EmiStack>> outputStacks;
   private final List<WidgetRowBuilder> rowBuilderList;
   private final boolean isSimple;
   private List<EmiIngredient> inputStacks;

   public BlockLootRecipe(BlockLootRecipe.BlockLootRecipeData data) {
      this.id = data.id;
      this.inputStack = data.inputStack;
      this.outputStacks = data.outputStacks;
      this.rowBuilderList = data.rowBuilderList;
      this.isSimple = data.guaranteed;
      this.lootStack = new BlockLootEmiStack(data.id);
   }

   public BlockLootRecipe init() {
      this.inputStack.init();
      this.outputStacks.init();
      this.inputStacks = this.inputStack.get().getItemStack().isEmpty()
         ? List.of(this.lootStack)
         : List.of((EmiIngredient)this.inputStack.get(), this.lootStack);
      return this;
   }

   public EmiRecipeCategory getCategory() {
      return EmiClientPlugin.BLOCK_CATEGORY;
   }

   @Nullable
   public ResourceLocation getId() {
      return this.id;
   }

   public List<EmiIngredient> getInputs() {
      return this.inputStacks;
   }

   public List<EmiIngredient> getCatalysts() {
      return super.getCatalysts();
   }

   public List<EmiStack> getOutputs() {
      return this.outputStacks.get();
   }

   public int getDisplayWidth() {
      return EMILoot.config.isTooltipStyle() ? 144 : 160;
   }

   public int getDisplayHeight() {
      if (!EMILoot.config.isTooltipStyle()) {
         return 23 + 29 * (this.rowBuilderList.size() - 1);
      }

      int stacks = this.outputStacks.get().size();
      if (stacks <= 5) {
         return 18;
      }

      if (!EMILoot.config.isCompact(EMILoot.Type.BLOCK)) {
         return 18 + 18 * ((stacks - 6) / 8 + 1);
      }

      int ingredients = 0;

      for (WidgetRowBuilder builder : this.rowBuilderList) {
         ingredients += builder.ingredientCount();
      }

      return ingredients <= 4 ? 29 : 18 + 18 * ((ingredients - 5) / 8 + 1);
   }

   public void addWidgets(WidgetHolder widgets) {
      widgets.addSlot((EmiIngredient)this.inputStack.get(), 0, 0);
      widgets.addTexture(EmiTexture.EMPTY_ARROW, 20, 0);
      int x = 46;
      int y = 0;
      if (EMILoot.config.isTooltipStyle()) {
         List<ConditionalStack> stacks = this.outputStacks.get().size() > 4 && EMILoot.config.isCompact(EMILoot.Type.BLOCK)
            ? this.rowBuilderList.stream().map(WidgetRowBuilder::ingredients).collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll)
            : this.rowBuilderList.stream().map(WidgetRowBuilder::stacks).collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
         int i = 3;
         int j = 0;

         for (ConditionalStack stack : stacks) {
            SlotWidget widget = widgets.addSlot(stack.getIngredient(), i * 18, 18 * j).recipeContext(this);
            String rounded = FloatTrimmer.trimFloatString(Math.max(stack.weight() / 100.0F, 0.01F), (Integer)EMILoot.config.chanceDecimalPlaces.get());
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
         for (WidgetRowBuilder builder : this.rowBuilderList) {
            for (ClientBuiltPool pool : builder.getPoolList()) {
               IconGroupEmiWidget widget = EMILootClientAgnos.createIconGroupEmiWidget(x, y, pool, this);
               widgets.add(widget);
               x += widget.getWidth() + 6;
            }

            y += 29;
            x = 46;
         }
      }
   }

   public boolean supportsRecipeTree() {
      return this.isSimple;
   }

   public boolean hideCraftable() {
      return super.hideCraftable();
   }

   public record BlockLootRecipeData(
      ResourceLocation id,
      List<WidgetRowBuilder> rowBuilderList,
      boolean guaranteed,
      InitializedSupplier<EmiStack> inputStack,
      InitializedSupplier<List<EmiStack>> outputStacks
   ) {
      public static BlockLootRecipe.BlockLootRecipeData of(ClientBlockLootTable loot) {
         List<ItemStack> itemStackList = new ArrayList<>();
         boolean allStacksGuaranteed = true;
         List<WidgetRowBuilder> rowBuilderList = new ArrayList<>();
         ResourceLocation blockId = loot.blockId;
         Block block = (Block)BuiltInRegistries.BLOCK.get(blockId);
         loot.build(Minecraft.getInstance().level, block);

         for (ClientBuiltPool builtPool : loot.builtItems) {
            for (ConditionalStack stack : builtPool.stacks()) {
               if (stack.weight() < 100.0F) {
                  allStacksGuaranteed = false;
               }

               itemStackList.addAll(stack.getRawStacks());
            }

            if (loot.isSimple) {
               addSimpleWidgetBuilder(rowBuilderList, builtPool);
            } else {
               addWidgetBuilders(rowBuilderList, builtPool, false);
            }
         }

         ResourceLocation id = ResourceLocation.fromNamespaceAndPath(
            "emi_loot", "/" + EmiClientPlugin.BLOCK_CATEGORY.id.getPath() + "/" + loot.id.getNamespace() + "/" + loot.id.getPath()
         );
         InitializedSupplier<List<EmiStack>> outputStacks = new InitializedSupplier<>(() -> {
            List<EmiStack> list = new ArrayList<>();

            for (ItemStack stackx : itemStackList) {
               list.add(EmiStack.of(stackx));
            }

            return list;
         });
         InitializedSupplier<EmiStack> inputStack = new InitializedSupplier<>(
            () -> block.asItem() == Items.AIR ? new BlockStateEmiStack(block.defaultBlockState(), blockId) : EmiStack.of(block)
         );
         return new BlockLootRecipe.BlockLootRecipeData(id, rowBuilderList, allStacksGuaranteed || loot.isSimple, inputStack, outputStacks);
      }

      private static void addSimpleWidgetBuilder(List<WidgetRowBuilder> rowBuilderList, ClientBuiltPool newPool) {
         WidgetRowBuilder builder = new WidgetRowBuilder(115);
         builder.addSimple(newPool);
         rowBuilderList.add(builder);
      }

      private static void addWidgetBuilders(List<WidgetRowBuilder> rowBuilderList, ClientBuiltPool newPool, boolean recursive) {
         boolean newBuilder = false;
         WidgetRowBuilder builder;
         if (!recursive && !rowBuilderList.isEmpty()) {
            builder = rowBuilderList.get(rowBuilderList.size() - 1);
         } else {
            builder = new WidgetRowBuilder(115);
            newBuilder = true;
         }

         Optional<ClientBuiltPool> opt = builder.addAndTrim(newPool);
         if (newBuilder) {
            rowBuilderList.add(builder);
         }

         opt.ifPresent(clientMobBuiltPool -> addWidgetBuilders(rowBuilderList, clientMobBuiltPool, true));
      }
   }
}
