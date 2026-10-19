package net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.emi;

import dev.emi.emi.api.recipe.EmiPlayerInventory;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.recipe.handler.EmiCraftContext;
import dev.emi.emi.api.recipe.handler.StandardRecipeHandler;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.platform.EmiClient;
import dev.emi.emi.registry.EmiRecipeFiller;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.network.PacketDistributor;
import net.p3pp3rf1y.sophisticatedcore.common.gui.ICraftingContainer;
import net.p3pp3rf1y.sophisticatedcore.common.gui.StorageContainerMenuBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerBase;

public class EmiGridMenuInfo<C extends StorageContainerMenuBase<?>> implements StandardRecipeHandler<C> {
   private final RecipeType<? extends Recipe<?>> recipeType;

   public static <C extends StorageContainerMenuBase<?>> EmiGridMenuInfo<C> crafting() {
      return new EmiGridMenuInfo<>(RecipeType.CRAFTING);
   }

   public static <C extends StorageContainerMenuBase<?>> EmiGridMenuInfo<C> smithing() {
      return new EmiGridMenuInfo<>(RecipeType.SMITHING);
   }

   private EmiGridMenuInfo(RecipeType<? extends Recipe<?>> recipeType) {
      this.recipeType = recipeType;
   }

   public List<Slot> getInputSources(C handler) {
      List<Slot> slots = new ArrayList<>(handler.realInventorySlots.stream().filter(s -> s.mayPickup(Minecraft.getInstance().player)).toList());
      slots.addAll(this.getCraftingSlots(handler));
      return slots;
   }

   public EmiPlayerInventory getInventory(AbstractContainerScreen<C> screen) {
      C handler = (C)screen.getMenu();
      List<Slot> slots = handler.getOpenOrFirstCraftingContainer(this.recipeType).isPresent()
         ? this.getInputSources(handler)
         : this.getPlayerInventorySlots(handler);
      return new EmiPlayerInventory(slots.stream().map(Slot::getItem).map(EmiStack::of).toList());
   }

   public List<Slot> getCraftingSlots(C handler) {
      UpgradeContainerBase<?, ?> openOrFirstCraftingContainer = (UpgradeContainerBase<?, ?>)handler.getOpenOrFirstCraftingContainer(this.recipeType)
         .orElse(null);
      return Collections.unmodifiableList(openOrFirstCraftingContainer instanceof ICraftingContainer cc ? cc.getRecipeSlots() : Collections.emptyList());
   }

   private List<Slot> getPlayerInventorySlots(C handler) {
      Player player = Minecraft.getInstance().player;
      return player == null
         ? List.of()
         : handler.realInventorySlots
            .stream()
            .filter(slot -> slot.container instanceof Inventory)
            .filter(slot -> slot.getContainerSlot() >= 0 && slot.getContainerSlot() < 36)
            .filter(slot -> slot.mayPickup(player))
            .toList();
   }

   @Nullable
   public Slot getOutputSlot(C handler) {
      return handler.getOpenOrFirstCraftingContainer(this.recipeType).map(c -> c.getSlots().getLast()).orElse(null);
   }

   public boolean supportsRecipe(EmiRecipe recipe) {
      return VanillaEmiRecipeCategories.CRAFTING.equals(recipe.getCategory()) && recipe.supportsRecipeTree();
   }

   public boolean canCraft(EmiRecipe recipe, EmiCraftContext<C> context) {
      return ((StorageContainerMenuBase)context.getScreenHandler()).getOpenOrFirstCraftingContainer(this.recipeType).isPresent()
         && super.canCraft(recipe, context);
   }

   public boolean craft(EmiRecipe recipe, EmiCraftContext<C> context) {
      List<ItemStack> stacks = EmiRecipeFiller.getStacks(this, recipe, context.getScreen(), 1);
      if (stacks != null) {
         C container = (C)context.getScreenHandler();
         Optional<? extends UpgradeContainerBase<?, ?>> potentialCraftingContainer = container.getOpenOrFirstCraftingContainer(this.recipeType);
         UpgradeContainerBase<?, ?> openOrFirstCraftingContainer = (UpgradeContainerBase<?, ?>)potentialCraftingContainer.get();
         if (!openOrFirstCraftingContainer.isOpen()) {
            container.getOpenContainer().ifPresent(c -> {
               c.setIsOpen(false);
               container.setOpenTabId(-1);
            });
            openOrFirstCraftingContainer.setIsOpen(true);
            container.setOpenTabId(openOrFirstCraftingContainer.getUpgradeContainerId());
         }

         Minecraft.getInstance().setScreen(context.getScreen());
         if (!EmiClient.onServer) {
            return EmiRecipeFiller.clientFill(this, recipe, context.getScreen(), stacks, context.getDestination());
         }

         int action = switch (context.getDestination()) {
            case NONE -> 0;
            case CURSOR -> 1;
            case INVENTORY -> 2;
            default -> throw new MatchException(null, null);
         };
         ResourceLocation recipeTypeId = BuiltInRegistries.RECIPE_TYPE.getKey(this.recipeType);
         if (recipeTypeId != null) {
            Slot output = this.getOutputSlot(container);
            PacketDistributor.sendToServer(
               new EmiTransferRecipePayload(
                  recipe.getId(),
                  recipeTypeId,
                  action,
                  this.getInputSources(container).stream().map(s -> s == null ? -1 : s.index).toList(),
                  this.getCraftingSlots(recipe, container).stream().map(s -> s == null ? -1 : s.index).toList(),
                  output == null ? -1 : output.index,
                  stacks,
                  context.getAmount() > 1
               ),
               new CustomPacketPayload[0]
            );
         }

         return true;
      } else {
         return false;
      }
   }
}
