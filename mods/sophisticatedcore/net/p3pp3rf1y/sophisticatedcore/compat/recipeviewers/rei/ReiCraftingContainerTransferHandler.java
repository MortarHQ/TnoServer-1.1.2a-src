package net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.rei;

import it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.List;
import me.shedaniel.rei.api.client.registry.transfer.TransferHandler;
import me.shedaniel.rei.api.client.registry.transfer.TransferHandler.ApplicabilityResult;
import me.shedaniel.rei.api.client.registry.transfer.TransferHandler.Context;
import me.shedaniel.rei.api.client.registry.transfer.TransferHandler.Result;
import me.shedaniel.rei.api.client.registry.transfer.simple.SimpleTransferHandler;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.InputIngredient;
import me.shedaniel.rei.api.common.transfer.info.stack.SlotAccessor;
import me.shedaniel.rei.api.common.util.CollectionUtils;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.impl.client.transfer.SimpleTransferHandlerImpl;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.network.PacketDistributor;
import net.p3pp3rf1y.sophisticatedcore.common.gui.ICraftingContainer;
import net.p3pp3rf1y.sophisticatedcore.common.gui.StorageContainerMenuBase;

public class ReiCraftingContainerTransferHandler<C extends StorageContainerMenuBase<?>, D extends Display> implements SimpleTransferHandler {
   private final Class<? extends C> containerClass;
   private final CategoryIdentifier<D> categoryIdentifier;
   private final RecipeType<? extends Recipe<?>> recipeType;

   public static <C extends StorageContainerMenuBase<?>> TransferHandler crafting(Class<? extends C> containerClass) {
      return new ReiCraftingContainerTransferHandler(containerClass, BuiltinPlugin.CRAFTING, RecipeType.CRAFTING);
   }

   public static <C extends StorageContainerMenuBase<?>> TransferHandler smithing(Class<? extends C> containerClass) {
      return new ReiCraftingContainerTransferHandler(containerClass, BuiltinPlugin.SMITHING, RecipeType.SMITHING);
   }

   public ReiCraftingContainerTransferHandler(
      Class<? extends C> containerClass, CategoryIdentifier<D> categoryIdentifier, RecipeType<? extends Recipe<?>> recipeType
   ) {
      this.containerClass = containerClass;
      this.categoryIdentifier = categoryIdentifier;
      this.recipeType = recipeType;
   }

   public ApplicabilityResult checkApplicable(Context context) {
      return this.containerClass.isInstance(context.getMenu())
            && !((StorageContainerMenuBase)context.getMenu()).getOpenOrFirstCraftingContainer(this.recipeType).isEmpty()
            && this.categoryIdentifier.equals(context.getDisplay().getCategoryIdentifier())
            && context.getContainerScreen() != null
         ? ApplicabilityResult.createApplicable()
         : ApplicabilityResult.createNotApplicable();
   }

   public Iterable<SlotAccessor> getInputSlots(Context context) {
      StorageContainerMenuBase<?> storageContainerMenuBase = (StorageContainerMenuBase<?>)context.getMenu();
      return storageContainerMenuBase.getOpenOrFirstCraftingContainer(this.recipeType)
         .map(c -> ((ICraftingContainer)c).getRecipeSlots().stream().map(ReiSlotAccessor::fromSlot).toList())
         .orElse(List.of());
   }

   public Iterable<SlotAccessor> getInventorySlots(Context context) {
      StorageContainerMenuBase<?> storageContainerMenuBase = (StorageContainerMenuBase<?>)context.getMenu();
      return storageContainerMenuBase.realInventorySlots.stream().map(ReiSlotAccessor::fromSlot).toList();
   }

   public Result handle(Context context) {
      List<InputIngredient<ItemStack>> inputs = this.getInputsIndexed(context);
      List<SlotAccessor> inputSlots = (List<SlotAccessor>)this.getInputSlots(context);
      List<SlotAccessor> inventorySlots = (List<SlotAccessor>)this.getInventorySlots(context);
      List<InputIngredient<ItemStack>> missing = SimpleTransferHandlerImpl.hasItemsIndexed(context, inventorySlots, inputs);
      if (missing.isEmpty()) {
         if (!context.isActuallyCrafting()) {
            return Result.createSuccessful();
         }

         StorageContainerMenuBase<?> storageContainerMenuBase = (StorageContainerMenuBase<?>)context.getMenu();
         storageContainerMenuBase.getOpenOrFirstCraftingContainer(this.recipeType).ifPresent(openOrFirstCraftingContainer -> {
            if (!openOrFirstCraftingContainer.isOpen()) {
               storageContainerMenuBase.getOpenContainer().ifPresent(c -> {
                  c.setIsOpen(false);
                  storageContainerMenuBase.setOpenTabId(-1);
               });
               openOrFirstCraftingContainer.setIsOpen(true);
               storageContainerMenuBase.setOpenTabId(openOrFirstCraftingContainer.getUpgradeContainerId());
            }
         });
         AbstractContainerScreen<?> containerScreen = context.getContainerScreen();
         context.getMinecraft().setScreen(containerScreen);
         ResourceLocation recipeTypeId = BuiltInRegistries.RECIPE_TYPE.getKey(this.recipeType);
         if (recipeTypeId != null) {
            List<Integer> inputSlotIds = inputSlots.stream().map(ReiSlotAccessor.class::cast).map(ReiSlotAccessor::getIndex).toList();
            List<Integer> inventorySlotIds = inventorySlots.stream().map(ReiSlotAccessor.class::cast).map(ReiSlotAccessor::getIndex).toList();
            PacketDistributor.sendToServer(
               new ReiTransferRecipePayload(
                  (ResourceLocation)context.getDisplay().getDisplayLocation().get(),
                  recipeTypeId,
                  save(inputs),
                  inputSlotIds,
                  inventorySlotIds,
                  context.isStackedCrafting()
               ),
               new CustomPacketPayload[0]
            );
         }

         return Result.createSuccessful();
      } else {
         IntSet missingIndices = new IntLinkedOpenHashSet(missing.size());

         for (InputIngredient<ItemStack> ingredient : missing) {
            missingIndices.add(ingredient.getDisplayIndex());
         }

         return Result.createFailed(Component.translatable("error.rei.not.enough.materials"))
            .renderer(
               (matrices, mouseX, mouseY, delta, widgets, bounds, d) -> this.getMissingInputRenderer()
                  .renderMissingInput(context, inputs, missing, missingIndices, matrices, mouseX, mouseY, delta, widgets, bounds)
            )
            .tooltipMissing(CollectionUtils.map(missing, ingredient -> EntryIngredients.ofItemStacks(ingredient.get())));
      }
   }

   private static CompoundTag save(List<InputIngredient<ItemStack>> inputs) {
      CompoundTag tag = new CompoundTag();
      tag.put("Inputs", saveInputs(inputs));
      return tag;
   }

   private static Tag saveInputs(List<InputIngredient<ItemStack>> inputs) {
      ListTag tag = new ListTag();

      for (InputIngredient<ItemStack> input : inputs) {
         CompoundTag innerTag = new CompoundTag();
         innerTag.put("Ingredient", EntryIngredients.ofItemStacks(input.get()).saveIngredient());
         innerTag.putInt("Index", input.getIndex());
         tag.add(innerTag);
      }

      return tag;
   }
}
