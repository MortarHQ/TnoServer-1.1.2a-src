package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.refill;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.Codec;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import net.p3pp3rf1y.sophisticatedbackpacks.api.IBlockPickResponseUpgrade;
import net.p3pp3rf1y.sophisticatedbackpacks.client.gui.SBPTranslationHelper;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModDataComponents;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.init.ModCoreDataComponents;
import net.p3pp3rf1y.sophisticatedcore.inventory.ITrackedContentsItemHandler;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogic;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IFilteredUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ITickableUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogic.ObservableFilterItemStackHandler;
import net.p3pp3rf1y.sophisticatedcore.util.CapabilityHelper;
import net.p3pp3rf1y.sophisticatedcore.util.CodecHelper;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;
import net.p3pp3rf1y.sophisticatedcore.util.StreamCodecHelper;

public class RefillUpgradeWrapper
   extends UpgradeWrapperBase<RefillUpgradeWrapper, RefillUpgradeItem>
   implements IFilteredUpgrade,
   ITickableUpgrade,
   IBlockPickResponseUpgrade {
   private static final int REFILL_RANGE = 3;
   private static final int COOLDOWN = 5;
   public static final Codec<Map<Integer, RefillUpgradeWrapper.TargetSlot>> TARGET_SLOTS_CODEC = Codec.unboundedMap(
      CodecHelper.STRING_ENCODED_INT, RefillUpgradeWrapper.TargetSlot.CODEC
   );
   public static final StreamCodec<FriendlyByteBuf, Map<Integer, RefillUpgradeWrapper.TargetSlot>> TARGET_SLOTS_STREAM_CODEC = StreamCodecHelper.ofMap(
      ByteBufCodecs.INT, RefillUpgradeWrapper.TargetSlot.STREAM_CODEC, HashMap::new
   );
   private final Map<Integer, RefillUpgradeWrapper.TargetSlot> targetSlots;
   private final FilterLogic filterLogic;

   public RefillUpgradeWrapper(IStorageWrapper backpackWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
      super(backpackWrapper, upgrade, upgradeSaveHandler);
      this.filterLogic = new FilterLogic(
         upgrade, upgradeSaveHandler, ((RefillUpgradeItem)this.upgradeItem).getFilterSlotCount(), ModCoreDataComponents.FILTER_ATTRIBUTES
      );
      this.targetSlots = new HashMap<>(
         (Map<? extends Integer, ? extends RefillUpgradeWrapper.TargetSlot>)upgrade.getOrDefault(ModDataComponents.TARGET_SLOTS, new HashMap())
      );
      if (((RefillUpgradeItem)this.upgradeItem).allowsTargetSlotSelection()) {
         ObservableFilterItemStackHandler filterHandler = this.filterLogic.getFilterHandler();
         filterHandler.setOnSlotChange(s -> this.onFilterChange(filterHandler, s));
      }

      this.filterLogic.setAllowByDefault(true);
   }

   private void onFilterChange(ObservableFilterItemStackHandler filterHandler, int slot) {
      if (filterHandler.getStackInSlot(slot).isEmpty()) {
         this.targetSlots.remove(slot);
         this.saveTargetSlots();
      } else if (!this.targetSlots.containsKey(slot)) {
         this.setTargetSlot(slot, RefillUpgradeWrapper.TargetSlot.ANY);
      }
   }

   public Map<Integer, RefillUpgradeWrapper.TargetSlot> getTargetSlots() {
      return this.targetSlots;
   }

   public void setTargetSlot(int slot, RefillUpgradeWrapper.TargetSlot targetSlot) {
      this.targetSlots.put(slot, targetSlot);
      this.saveTargetSlots();
   }

   private void saveTargetSlots() {
      this.upgrade.set(ModDataComponents.TARGET_SLOTS, ImmutableMap.copyOf(this.targetSlots));
      this.save();
   }

   public FilterLogic getFilterLogic() {
      return this.filterLogic;
   }

   public void tick(@Nullable Entity entity, Level level, BlockPos pos) {
      if (!this.isInCooldown(level)) {
         if (!(entity instanceof Player)) {
            level.getEntities(EntityType.PLAYER, new AABB(pos).inflate(3.0), p -> true).forEach(this::refillItemFor);
         } else {
            this.refillItemFor(entity);
         }

         this.setCooldown(level, 5);
      }
   }

   private void refillItemFor(Entity entity) {
      CapabilityHelper.runOnItemHandler(entity, playerInvHandler -> InventoryHelper.iterate(this.filterLogic.getFilterHandler(), (slot, filter) -> {
         if (!filter.isEmpty()) {
            this.tryRefillFilter(entity, playerInvHandler, filter, this.getTargetSlots().getOrDefault(slot, RefillUpgradeWrapper.TargetSlot.ANY));
         }
      }));
   }

   private void tryRefillFilter(@Nonnull Entity entity, IItemHandler playerInvHandler, ItemStack filter, RefillUpgradeWrapper.TargetSlot targetSlot) {
      if (entity instanceof Player player) {
         int missingCount = targetSlot.missingCountGetter.getMissingCount(player, playerInvHandler, filter);
         if (ItemStack.isSameItemSameComponents(player.containerMenu.getCarried(), filter)) {
            missingCount -= Math.min(missingCount, player.containerMenu.getCarried().getCount());
         }

         if (missingCount != 0) {
            IItemHandler extractFromHandler = this.storageWrapper.getInventoryForUpgradeProcessing();
            ItemStack toMove = filter.copy();
            toMove.setCount(missingCount);
            ItemStack extracted = InventoryHelper.extractFromInventory(toMove, extractFromHandler, true);
            if (!extracted.isEmpty()) {
               ItemStack remaining = targetSlot.filler.fill(player, playerInvHandler, extracted);
               if (remaining.getCount() != extracted.getCount()) {
                  ItemStack toExtract = extracted.copy();
                  toExtract.setCount(extracted.getCount() - remaining.getCount());
                  InventoryHelper.extractFromInventory(toExtract, extractFromHandler, false);
               }
            }
         }
      }
   }

   public boolean allowsTargetSlotSelection() {
      return ((RefillUpgradeItem)this.upgradeItem).allowsTargetSlotSelection();
   }

   @Override
   public boolean pickBlock(Player player, ItemStack filter) {
      if (!((RefillUpgradeItem)this.upgradeItem).supportsBlockPick()) {
         return false;
      }

      AtomicInteger stashSlot = new AtomicInteger(-1);
      AtomicBoolean hasItemInBackpack = new AtomicBoolean(false);
      ITrackedContentsItemHandler inventoryHandler = this.storageWrapper.getInventoryForUpgradeProcessing();
      InventoryHelper.iterate(inventoryHandler, (slot, stack) -> {
         if (ItemStack.isSameItemSameComponents(stack, filter)) {
            hasItemInBackpack.set(true);
            stashSlot.set(slot);
         }
      }, () -> stashSlot.get() > -1);
      ItemStack toExtract = filter.copy();
      toExtract.setCount(filter.getMaxStackSize());
      if (hasItemInBackpack.get() && !InventoryHelper.extractFromInventory(toExtract, inventoryHandler, true).isEmpty()) {
         player.getInventory().selected = player.getInventory().getSuitableHotbarSlot();
         ItemStack selectedItem = player.getInventory().getSelected();
         if ((
               inventoryHandler.getStackInSlot(stashSlot.get()).getCount() > filter.getMaxStackSize()
                  || !inventoryHandler.isItemValid(stashSlot.get(), selectedItem)
            )
            && !inventoryHandler.insertItem(selectedItem, true).isEmpty()) {
            if (this.canMoveSelectedToInventory(player)) {
               ItemStack extracted = InventoryHelper.extractFromInventory(toExtract, inventoryHandler, false);
               player.setItemInHand(InteractionHand.MAIN_HAND, extracted);
               player.getInventory().add(selectedItem);
               return true;
            } else {
               player.displayClientMessage(Component.translatable("gui.sophisticatedbackpacks.status.no_space_for_mainhand_item"), true);
               return false;
            }
         } else {
            ItemStack extracted = InventoryHelper.extractFromInventory(toExtract, inventoryHandler, false);
            inventoryHandler.insertItem(selectedItem, false);
            player.setItemInHand(InteractionHand.MAIN_HAND, extracted);
            return true;
         }
      } else {
         return false;
      }
   }

   private boolean canMoveSelectedToInventory(Player player) {
      int countToAdd = player.getMainHandItem().getCount();

      for (int slot = 0; slot < player.getInventory().getContainerSize() - 5; slot++) {
         if (slot != player.getInventory().selected) {
            ItemStack slotStack = player.getInventory().getItem(slot);
            if (slotStack.isEmpty()) {
               return true;
            }

            if (ItemStack.isSameItemSameComponents(slotStack, player.getMainHandItem())) {
               countToAdd -= slotStack.getMaxStackSize() - slotStack.getCount();
               if (countToAdd <= 0) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public enum TargetSlot implements StringRepresentable {
      ANY(
         "any",
         SBPTranslationHelper.INSTANCE.translUpgrade("refill.target_slot.any", new Object[0]),
         SBPTranslationHelper.INSTANCE.translUpgrade("refill.target_slot.any.tooltip", new Object[0]).withStyle(ChatFormatting.DARK_GREEN),
         (player, playerInvHandler, filter) -> InventoryHelper.getCountMissingInHandler(playerInvHandler, filter, filter.getMaxStackSize()),
         (player, playerInvHandler, stackToAdd) -> refillAnywhereInInventory(playerInvHandler, stackToAdd)
      ),
      MAIN_HAND(
         "main_hand",
         SBPTranslationHelper.INSTANCE.translUpgrade("refill.target_slot.main_hand", new Object[0]),
         SBPTranslationHelper.INSTANCE.translUpgrade("refill.target_slot.main_hand.tooltip", new Object[0]).withStyle(ChatFormatting.DARK_GREEN),
         (player, playerInvHandler, filter) -> getMissingCount(player.getMainHandItem(), filter),
         (player, playerInvHandler, stackToAdd) -> refillSlot(
            player::getMainHandItem, stackToAdd, stack -> player.setItemInHand(InteractionHand.MAIN_HAND, stack)
         )
      ),
      OFF_HAND(
         "off_hand",
         SBPTranslationHelper.INSTANCE.translUpgrade("refill.target_slot.off_hand", new Object[0]),
         SBPTranslationHelper.INSTANCE.translUpgrade("refill.target_slot.off_hand.tooltip", new Object[0]).withStyle(ChatFormatting.DARK_GREEN),
         (player, playerInvHandler, filter) -> getMissingCount((ItemStack)player.getInventory().offhand.getFirst(), filter),
         (player, playerInvHandler, stackToAdd) -> refillSlot(
            () -> (ItemStack)player.getInventory().offhand.getFirst(), stackToAdd, stack -> player.setItemInHand(InteractionHand.OFF_HAND, stack)
         )
      ),
      TOOLBAR_1(
         "toolbar_1",
         Component.literal("1"),
         SBPTranslationHelper.INSTANCE.translUpgrade("refill.target_slot.hotbar.tooltip", new Object[]{1}).withStyle(ChatFormatting.DARK_GREEN),
         (player, playerInvHandler, filter) -> getMissingCount(player.getInventory().getItem(0), filter),
         (player, playerInvHandler, stackToAdd) -> refillSlot(
            () -> player.getInventory().getItem(0), stackToAdd, stack -> player.getInventory().setItem(0, stack)
         )
      ),
      TOOLBAR_2(
         "toolbar_2",
         Component.literal("2"),
         SBPTranslationHelper.INSTANCE.translUpgrade("refill.target_slot.hotbar.tooltip", new Object[]{2}).withStyle(ChatFormatting.DARK_GREEN),
         (player, playerInvHandler, filter) -> getMissingCount(player.getInventory().getItem(1), filter),
         (player, playerInvHandler, stackToAdd) -> refillSlot(
            () -> player.getInventory().getItem(1), stackToAdd, stack -> player.getInventory().setItem(1, stack)
         )
      ),
      TOOLBAR_3(
         "toolbar_3",
         Component.literal("3"),
         SBPTranslationHelper.INSTANCE.translUpgrade("refill.target_slot.hotbar.tooltip", new Object[]{3}).withStyle(ChatFormatting.DARK_GREEN),
         (player, playerInvHandler, filter) -> getMissingCount(player.getInventory().getItem(2), filter),
         (player, playerInvHandler, stackToAdd) -> refillSlot(
            () -> player.getInventory().getItem(2), stackToAdd, stack -> player.getInventory().setItem(2, stack)
         )
      ),
      TOOLBAR_4(
         "toolbar_4",
         Component.literal("4"),
         SBPTranslationHelper.INSTANCE.translUpgrade("refill.target_slot.hotbar.tooltip", new Object[]{4}).withStyle(ChatFormatting.DARK_GREEN),
         (player, playerInvHandler, filter) -> getMissingCount(player.getInventory().getItem(3), filter),
         (player, playerInvHandler, stackToAdd) -> refillSlot(
            () -> player.getInventory().getItem(3), stackToAdd, stack -> player.getInventory().setItem(3, stack)
         )
      ),
      TOOLBAR_5(
         "toolbar_5",
         Component.literal("5"),
         SBPTranslationHelper.INSTANCE.translUpgrade("refill.target_slot.hotbar.tooltip", new Object[]{5}).withStyle(ChatFormatting.DARK_GREEN),
         (player, playerInvHandler, filter) -> getMissingCount(player.getInventory().getItem(4), filter),
         (player, playerInvHandler, stackToAdd) -> refillSlot(
            () -> player.getInventory().getItem(4), stackToAdd, stack -> player.getInventory().setItem(4, stack)
         )
      ),
      TOOLBAR_6(
         "toolbar_6",
         Component.literal("6"),
         SBPTranslationHelper.INSTANCE.translUpgrade("refill.target_slot.hotbar.tooltip", new Object[]{6}).withStyle(ChatFormatting.DARK_GREEN),
         (player, playerInvHandler, filter) -> getMissingCount(player.getInventory().getItem(5), filter),
         (player, playerInvHandler, stackToAdd) -> refillSlot(
            () -> player.getInventory().getItem(5), stackToAdd, stack -> player.getInventory().setItem(5, stack)
         )
      ),
      TOOLBAR_7(
         "toolbar_7",
         Component.literal("7"),
         SBPTranslationHelper.INSTANCE.translUpgrade("refill.target_slot.hotbar.tooltip", new Object[]{7}).withStyle(ChatFormatting.DARK_GREEN),
         (player, playerInvHandler, filter) -> getMissingCount(player.getInventory().getItem(6), filter),
         (player, playerInvHandler, stackToAdd) -> refillSlot(
            () -> player.getInventory().getItem(6), stackToAdd, stack -> player.getInventory().setItem(6, stack)
         )
      ),
      TOOLBAR_8(
         "toolbar_8",
         Component.literal("8"),
         SBPTranslationHelper.INSTANCE.translUpgrade("refill.target_slot.hotbar.tooltip", new Object[]{8}).withStyle(ChatFormatting.DARK_GREEN),
         (player, playerInvHandler, filter) -> getMissingCount(player.getInventory().getItem(7), filter),
         (player, playerInvHandler, stackToAdd) -> refillSlot(
            () -> player.getInventory().getItem(7), stackToAdd, stack -> player.getInventory().setItem(7, stack)
         )
      ),
      TOOLBAR_9(
         "toolbar_9",
         Component.literal("9"),
         SBPTranslationHelper.INSTANCE.translUpgrade("refill.target_slot.hotbar.tooltip", new Object[]{9}).withStyle(ChatFormatting.DARK_GREEN),
         (player, playerInvHandler, filter) -> getMissingCount(player.getInventory().getItem(8), filter),
         (player, playerInvHandler, stackToAdd) -> refillSlot(
            () -> player.getInventory().getItem(8), stackToAdd, stack -> player.getInventory().setItem(8, stack)
         )
      );

      private final String name;
      private final Component acronym;
      private final Component description;
      private final RefillUpgradeWrapper.TargetSlot.MissingCountGetter missingCountGetter;
      private final RefillUpgradeWrapper.TargetSlot.Filler filler;
      public static final Codec<RefillUpgradeWrapper.TargetSlot> CODEC = StringRepresentable.fromEnum(RefillUpgradeWrapper.TargetSlot::values);
      public static final StreamCodec<FriendlyByteBuf, RefillUpgradeWrapper.TargetSlot> STREAM_CODEC = NeoForgeStreamCodecs.enumCodec(
         RefillUpgradeWrapper.TargetSlot.class
      );
      private static final Map<String, RefillUpgradeWrapper.TargetSlot> NAME_VALUES;
      private static final RefillUpgradeWrapper.TargetSlot[] VALUES;

      TargetSlot(
         String name,
         Component acronym,
         Component description,
         RefillUpgradeWrapper.TargetSlot.MissingCountGetter missingCountGetter,
         RefillUpgradeWrapper.TargetSlot.Filler filler
      ) {
         this.name = name;
         this.acronym = acronym;
         this.description = description;
         this.missingCountGetter = missingCountGetter;
         this.filler = filler;
      }

      public String getSerializedName() {
         return this.name;
      }

      public RefillUpgradeWrapper.TargetSlot next() {
         return VALUES[(this.ordinal() + 1) % VALUES.length];
      }

      public RefillUpgradeWrapper.TargetSlot previous() {
         return VALUES[Math.floorMod(this.ordinal() - 1, VALUES.length)];
      }

      public static RefillUpgradeWrapper.TargetSlot fromName(String name) {
         return NAME_VALUES.getOrDefault(name, ANY);
      }

      public Component getAcronym() {
         return this.acronym;
      }

      public Component getDescription() {
         return this.description;
      }

      private static ItemStack refillAnywhereInInventory(IItemHandler playerInvHandler, ItemStack extracted) {
         AtomicReference<ItemStack> remainingStack = new AtomicReference<>(extracted);
         InventoryHelper.iterate(playerInvHandler, (slot, stack) -> {
            if (ItemStack.isSameItemSameComponents(stack, remainingStack.get())) {
               remainingStack.set(playerInvHandler.insertItem(slot, remainingStack.get(), false));
            }
         }, () -> remainingStack.get().isEmpty());
         ItemStack remaining = remainingStack.get();
         if (!remaining.isEmpty()) {
            ItemStack afterInsert = InventoryHelper.insertIntoInventory(remaining, playerInvHandler, true);
            if (afterInsert.getCount() == remaining.getCount()) {
               return remaining;
            }

            ItemStack toInsert = remaining.copy();
            toInsert.setCount(remaining.getCount() - afterInsert.getCount());
            return InventoryHelper.insertIntoInventory(toInsert, playerInvHandler, false);
         } else {
            return remaining;
         }
      }

      private static int getMissingCount(ItemStack stack, ItemStack filter) {
         return ItemStack.isSameItemSameComponents(stack, filter) ? filter.getMaxStackSize() - stack.getCount() : filter.getMaxStackSize();
      }

      private static ItemStack refillSlot(Supplier<ItemStack> getSlotContents, ItemStack stackToAdd, Consumer<ItemStack> setSlotContents) {
         ItemStack contents = getSlotContents.get();
         if (contents.isEmpty()) {
            setSlotContents.accept(stackToAdd);
            return ItemStack.EMPTY;
         } else if (ItemStack.isSameItemSameComponents(contents, stackToAdd)) {
            contents.grow(stackToAdd.getCount());
            return ItemStack.EMPTY;
         } else {
            return stackToAdd;
         }
      }

      static {
         Builder<String, RefillUpgradeWrapper.TargetSlot> builder = new Builder();

         for (RefillUpgradeWrapper.TargetSlot value : values()) {
            builder.put(value.getSerializedName(), value);
         }

         NAME_VALUES = builder.build();
         VALUES = values();
      }

      private static class Constants {
         private static final String HOTBAR_TRANSL = "refill.target_slot.hotbar.tooltip";
      }

      private interface Filler {
         ItemStack fill(Player var1, IItemHandler var2, ItemStack var3);
      }

      private interface MissingCountGetter {
         int getMissingCount(Player var1, IItemHandler var2, ItemStack var3);
      }
   }
}
