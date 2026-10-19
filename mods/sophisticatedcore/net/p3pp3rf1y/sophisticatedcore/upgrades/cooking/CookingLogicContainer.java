package net.p3pp3rf1y.sophisticatedcore.upgrades.cooking;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.items.IItemHandler;
import net.p3pp3rf1y.sophisticatedcore.common.gui.SlotSuppliedHandler;

public class CookingLogicContainer<T extends AbstractCookingRecipe> {
   private final Supplier<CookingLogic<T>> supplyCoookingLogic;
   private final List<Slot> smeltingSlots = new ArrayList<>();
   private int removeCount;
   private final Player player;

   public CookingLogicContainer(final Player player, final Supplier<CookingLogic<T>> supplyCoookingLogic, Consumer<Slot> addSlot) {
      this.player = player;
      this.supplyCoookingLogic = supplyCoookingLogic;
      this.addSmeltingSlot(addSlot, new CookingLogicContainer.CookingSlot(() -> supplyCoookingLogic.get().getCookingInventory(), 0, -100, -100));
      this.addSmeltingSlot(addSlot, new CookingLogicContainer.CookingSlot(() -> supplyCoookingLogic.get().getCookingInventory(), 1, -100, -100));
      this.addSmeltingSlot(addSlot, new SlotSuppliedHandler(() -> supplyCoookingLogic.get().getCookingInventory(), 2, -100, -100) {
         @Override
         public boolean mayPlace(ItemStack stack) {
            return false;
         }

         public ItemStack remove(int amount) {
            if (this.hasItem()) {
               CookingLogicContainer.this.removeCount = CookingLogicContainer.this.removeCount + Math.min(amount, this.getItem().getCount());
            }

            return super.remove(amount);
         }

         public void onTake(Player playerx, ItemStack stack) {
            this.checkTakeAchievements(stack);
            super.onTake(player, stack);
         }

         protected void checkTakeAchievements(ItemStack stack) {
            stack.onCraftedBy(player.level(), player, CookingLogicContainer.this.removeCount);
            if (player instanceof ServerPlayer serverplayer) {
               supplyCoookingLogic.get().awardUsedRecipesAndPopExperience(serverplayer);
            }

            CookingLogicContainer.this.removeCount = 0;
            EventHooks.firePlayerSmeltedEvent(player, stack);
         }
      });
   }

   private void addSmeltingSlot(Consumer<Slot> addSlot, Slot slot) {
      addSlot.accept(slot);
      this.smeltingSlots.add(slot);
   }

   public int getBurnTimeTotal() {
      return this.supplyCoookingLogic.get().getBurnTimeTotal();
   }

   public long getBurnTimeFinish() {
      return this.supplyCoookingLogic.get().getBurnTimeFinish();
   }

   public long getCookTimeFinish() {
      return this.supplyCoookingLogic.get().getCookTimeFinish();
   }

   public int getCookTimeTotal() {
      return this.supplyCoookingLogic.get().getCookTimeTotal();
   }

   public boolean isCooking() {
      return this.supplyCoookingLogic.get().isCooking();
   }

   public boolean isBurning(Level level) {
      return this.supplyCoookingLogic.get().isBurning(level);
   }

   public List<Slot> getCookingSlots() {
      return this.smeltingSlots;
   }

   private static class CookingSlot extends SlotSuppliedHandler {
      public CookingSlot(Supplier<IItemHandler> itemHandlerSupplier, int slot, int xPosition, int yPosition) {
         super(itemHandlerSupplier, slot, xPosition, yPosition);
      }

      public int getMaxStackSize(ItemStack stack) {
         return stack.getMaxStackSize();
      }
   }
}
