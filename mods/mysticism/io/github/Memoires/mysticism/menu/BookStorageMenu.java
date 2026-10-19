package io.github.Memoires.mysticism.menu;

import io.github.Memoires.mysticism.ability.subclass.IBookStorage;
import io.github.Memoires.mysticism.menu.container.BookStorageContainer;
import io.github.Memoires.mysticism.menu.slot.BookSlot;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments.Mutable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

public class BookStorageMenu extends AbstractContainerMenu {
   private static final Logger log = LogManager.getLogger(BookStorageMenu.class);
   private final ManasSkill skill;
   private final Player player;
   private final int page;
   private final SimpleContainer container;
   private static final int BOOK_START = 36;

   public BookStorageMenu(int id, Inventory inv, SimpleContainer container, ManasSkill skill, int page) {
      super(null, id);
      this.skill = skill;
      this.player = inv.player;
      this.page = page;
      this.container = container;
      container.startOpen(inv.player);
      this.addPlayerInventory(inv);
      this.addBookSlots(container);
   }

   public boolean stillValid(Player player) {
      return player.isAlive();
   }

   private void addPlayerInventory(Inventory playerInventory) {
      for (int i = 0; i < 3; i++) {
         for (int l = 0; l < 9; l++) {
            this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 21 + l * 18, 130 + i * 18));
         }
      }

      for (int i = 0; i < 9; i++) {
         this.addSlot(new Slot(playerInventory, i, 21 + i * 18, 188));
      }
   }

   private void addBookSlots(SimpleContainer container) {
      int slotIndex = 27 * this.page;
      int size = container.getContainerSize() - 27 * this.page;

      for (int i = 0; i < 3 && size > 0; i++) {
         for (int j = 0; j < 9 && size > 0; j++) {
            this.addSlot(new BookSlot(container, slotIndex, 21 + j * 18, 30 + i * 18));
            slotIndex++;
            size--;
         }
      }
   }

   public boolean clickMenuButton(Player player, int i) {
      if (i == 0 && this.page > 0) {
         ManasSkillInstance instance = this.getSkillInstance(player);
         if (instance != null && instance.getSkill() instanceof IBookStorage bookStorage) {
            this.removed(player);
            if (player instanceof ServerPlayer serverPlayer) {
               bookStorage.openBookStoragePage(serverPlayer, instance, this.page - 1);
            }
         }

         return true;
      } else if (i == 1 && this.page < (this.container.getContainerSize() - 1) / 27) {
         ManasSkillInstance instance = this.getSkillInstance(player);
         if (instance != null && instance.getSkill() instanceof IBookStorage bookStorage) {
            this.removed(player);
            if (player instanceof ServerPlayer serverPlayer) {
               bookStorage.openBookStoragePage(serverPlayer, instance, this.page + 1);
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Nullable
   public ManasSkillInstance getSkillInstance(Player player) {
      Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(player).getSkill(this.skill);
      return optional.orElse(null);
   }

   public void removed(Player pPlayer) {
      ManasSkillInstance instance = this.getSkillInstance(pPlayer);
      if (instance != null && this.container instanceof BookStorageContainer storageContainer && instance.getSkill() instanceof IBookStorage bookStorage) {
         bookStorage.saveContainer(instance, pPlayer, storageContainer);
      }

      this.container.stopOpen(pPlayer);
      super.removed(pPlayer);
   }

   public int getLibrarySize() {
      return Math.min(this.container.getContainerSize() - this.page * 27, 27);
   }

   private int getBookEnd() {
      return 36 + this.getLibrarySize();
   }

   public ItemStack quickMoveStack(Player pPlayer, int i) {
      ItemStack copy = ItemStack.EMPTY;
      Slot slot = (Slot)this.slots.get(i);
      if (!slot.hasItem()) {
         return copy;
      }

      ItemStack stack = slot.getItem();
      copy = stack.copy();
      if (i >= 0 && i < 36) {
         if (i < 27) {
            if (!this.moveItemStackTo(stack, 36, this.getBookEnd(), false) && !this.moveItemStackTo(stack, 27, 36, false)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.moveItemStackTo(stack, 36, this.getBookEnd(), false) && !this.moveItemStackTo(stack, 0, 27, false)) {
            return ItemStack.EMPTY;
         }
      } else if (i >= 36 && i < this.getBookEnd()) {
         if (!this.moveItemStackTo(stack, 0, 36, false)) {
            return ItemStack.EMPTY;
         }
      } else if (!this.moveItemStackTo(stack, 0, 27, false) && !this.moveItemStackTo(stack, 40, 40 + this.getLibrarySize(), false)) {
         return ItemStack.EMPTY;
      }

      if (stack.isEmpty()) {
         slot.setByPlayer(ItemStack.EMPTY);
      } else {
         slot.setChanged();
      }

      if (stack.getCount() == copy.getCount()) {
         return ItemStack.EMPTY;
      }

      slot.onTake(this.player, stack);
      return copy;
   }

   protected boolean moveItemStackTo(ItemStack pStack, int pStartIndex, int pEndIndex, boolean pReverseDirection) {
      boolean flag = false;
      int i = pStartIndex;
      if (pReverseDirection) {
         i = pEndIndex - 1;
      }

      if (pStack.isStackable()) {
         while (!pStack.isEmpty() && (pReverseDirection ? i >= pStartIndex : i < pEndIndex)) {
            Slot slot = (Slot)this.slots.get(i);
            ItemStack itemstack = slot.getItem();
            if (!itemstack.isEmpty() && slot.mayPlace(pStack) && ItemStack.isSameItemSameComponents(pStack, itemstack)) {
               int j = itemstack.getCount() + pStack.getCount();
               int maxSize = i >= 40 ? this.container.getMaxStackSize(pStack) : Math.min(itemstack.getMaxStackSize(), slot.getMaxStackSize());
               if (j <= maxSize) {
                  pStack.setCount(0);
                  itemstack.setCount(j);
                  slot.setChanged();
                  flag = true;
               } else if (itemstack.getCount() < maxSize) {
                  pStack.shrink(maxSize - itemstack.getCount());
                  itemstack.setCount(maxSize);
                  slot.setChanged();
                  flag = true;
               }
            }

            if (pReverseDirection) {
               i--;
            } else {
               i++;
            }
         }
      }

      if (!pStack.isEmpty()) {
         if (pReverseDirection) {
            i = pEndIndex - 1;
         } else {
            i = pStartIndex;
         }

         while (pReverseDirection ? i >= pStartIndex : i < pEndIndex) {
            Slot pSlot = (Slot)this.slots.get(i);
            ItemStack stack = pSlot.getItem();
            if (stack.isEmpty() && pSlot.mayPlace(pStack)) {
               if (pStack.getCount() > pSlot.getMaxStackSize()) {
                  pSlot.set(pStack.split(pSlot.getMaxStackSize()));
               } else {
                  pSlot.set(pStack.split(pStack.getCount()));
               }

               pSlot.setChanged();
               flag = true;
               break;
            }

            if (pReverseDirection) {
               i--;
            } else {
               i++;
            }
         }
      }

      return flag;
   }

   public void clicked(int slotId, int button, ClickType clickType, Player player) {
      if (!player.level().isClientSide() && button == 1 && clickType == ClickType.PICKUP && this.isBookStorageSlot(slotId)) {
         this.handleBookRightClick(player, slotId);
      } else {
         super.clicked(slotId, button, clickType, player);
      }
   }

   private boolean handleBookRightClick(Player player, int slotId) {
      if (!(player instanceof ServerPlayer)) {
         return false;
      } else {
         Slot slot = (Slot)this.slots.get(slotId);
         ItemStack stack = slot.getItem();
         if (!this.isUpgradeableBook(stack)) {
            return false;
         } else {
            ManasSkillInstance instance = this.getSkillInstance(player);
            if (instance == null) {
               return false;
            } else if (instance.getSkill() instanceof IBookStorage bookStorage) {
               return !bookStorage.isAllowedToUpgradeEnchantments() ? false : this.upgradeBook(player, slotId);
            } else {
               return false;
            }
         }
      }
   }

   private boolean isBookStorageSlot(int slotId) {
      int bookEnd = this.getBookEnd();
      return slotId >= 36 && slotId < bookEnd && slotId < this.slots.size();
   }

   private boolean isUpgradeableBook(ItemStack stack) {
      if (stack.isEmpty()) {
         return false;
      }

      if (!stack.is(Items.ENCHANTED_BOOK)) {
         return false;
      }

      ItemEnchantments enchantments = (ItemEnchantments)stack.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY);
      return !enchantments.isEmpty();
   }

   public boolean canUpgradeBook(Player player, int slotId) {
      if (!this.isBookStorageSlot(slotId)) {
         return false;
      }

      Slot slot = (Slot)this.slots.get(slotId);
      ItemStack stack = slot.getItem();
      if (!this.isUpgradeableBook(stack)) {
         return false;
      }

      ManasSkillInstance instance = this.getSkillInstance(player);
      if (instance == null) {
         return false;
      }

      if (instance.getSkill() instanceof IBookStorage bookStorage) {
         if (!bookStorage.isAllowedToUpgradeEnchantments()) {
            return false;
         }

         ItemEnchantments enchantments = (ItemEnchantments)stack.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY);

         for (Entry<Holder<Enchantment>> entry : enchantments.entrySet()) {
            Holder<Enchantment> enchantment = (Holder<Enchantment>)entry.getKey();
            int level = entry.getIntValue();
            ResourceLocation id = this.getEnchantmentId(enchantment);
            if (id != null) {
               String enchantmentId = id.toString();
               if (!bookStorage.getBlacklistEnchantments().contains(enchantmentId)) {
                  int maxAllowed = this.getMaxAllowedLevel(bookStorage, enchantment, enchantmentId);
                  if (level < maxAllowed) {
                     return true;
                  }
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public boolean canDowngradeBook(Player player, int slotId) {
      if (!this.isBookStorageSlot(slotId)) {
         return false;
      }

      Slot slot = (Slot)this.slots.get(slotId);
      ItemStack stack = slot.getItem();
      if (!this.isUpgradeableBook(stack)) {
         return false;
      }

      ManasSkillInstance instance = this.getSkillInstance(player);
      if (instance == null) {
         return false;
      }

      if (instance.getSkill() instanceof IBookStorage bookStorage) {
         if (!bookStorage.isAllowedToUpgradeEnchantments()) {
            return false;
         }

         ItemEnchantments enchantments = (ItemEnchantments)stack.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY);

         for (Entry<Holder<Enchantment>> entry : enchantments.entrySet()) {
            Holder<Enchantment> enchantment = (Holder<Enchantment>)entry.getKey();
            int level = entry.getIntValue();
            ResourceLocation id = this.getEnchantmentId(enchantment);
            if (id != null) {
               String enchantmentId = id.toString();
               if (!bookStorage.getBlacklistEnchantments().contains(enchantmentId) && level > 1) {
                  return true;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public boolean upgradeBook(Player player, int slotId) {
      if (this.container instanceof BookStorageContainer bookContainer) {
         if (!this.isBookStorageSlot(slotId)) {
            return false;
         }

         Slot slot = (Slot)this.slots.get(slotId);
         ItemStack stack = slot.getItem();
         if (!this.isUpgradeableBook(stack)) {
            return false;
         }

         ManasSkillInstance instance = this.getSkillInstance(player);
         if (instance == null) {
            return false;
         }

         if (instance.getSkill() instanceof IBookStorage bookStorage) {
            if (!bookStorage.isAllowedToUpgradeEnchantments()) {
               return false;
            }

            ItemEnchantments enchantments = (ItemEnchantments)stack.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY);
            Mutable mutable = new Mutable(enchantments);

            for (Entry<Holder<Enchantment>> entry : enchantments.entrySet()) {
               Holder<Enchantment> enchantment = (Holder<Enchantment>)entry.getKey();
               int level = entry.getIntValue();
               ResourceLocation id = this.getEnchantmentId(enchantment);
               if (id != null) {
                  String enchantmentId = id.toString();
                  if (!bookStorage.getBlacklistEnchantments().contains(enchantmentId)) {
                     int maxAllowed = this.getMaxAllowedLevel(bookStorage, enchantment, enchantmentId);
                     if (level < maxAllowed) {
                        int cost = (level + 1) * 10;
                        Component enchantName = ((Enchantment)enchantment.value()).description();
                        if (player.experienceLevel < cost && !player.isCreative()) {
                           player.displayClientMessage(
                              Component.translatable("mysticism.skill.scholar.library_of_babel.failed_to_level_up", new Object[]{cost})
                                 .withStyle(ChatFormatting.RED),
                              false
                           );
                           player.playNotifySound((SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), SoundSource.PLAYERS, 0.5F, 1.0F);
                           return false;
                        }

                        if (!player.isCreative()) {
                           player.displayClientMessage(
                              Component.translatable("mysticism.skill.scholar.library_of_babel.leveled_up", new Object[]{enchantName, cost})
                                 .withStyle(ChatFormatting.GREEN),
                              false
                           );
                           player.giveExperienceLevels(-cost);
                        }

                        mutable.set(enchantment, level + 1);
                        stack.set(DataComponents.STORED_ENCHANTMENTS, mutable.toImmutable());
                        slot.setChanged();
                        bookStorage.saveContainer(instance, player, bookContainer);
                        this.broadcastChanges();
                        player.playNotifySound(SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 0.8F, 1.2F);
                        instance.addMasteryPoint(player, (level + 2) / 2);
                        return true;
                     }
                  }
               }
            }

            return false;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean downgradeBook(Player player, int slotId) {
      if (this.container instanceof BookStorageContainer bookContainer) {
         if (!this.isBookStorageSlot(slotId)) {
            return false;
         }

         Slot slot = (Slot)this.slots.get(slotId);
         ItemStack stack = slot.getItem();
         if (!this.isUpgradeableBook(stack)) {
            return false;
         }

         ManasSkillInstance instance = this.getSkillInstance(player);
         if (instance == null) {
            return false;
         }

         if (instance.getSkill() instanceof IBookStorage bookStorage) {
            if (!bookStorage.isAllowedToUpgradeEnchantments()) {
               return false;
            }

            ItemEnchantments enchantments = (ItemEnchantments)stack.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY);
            Mutable mutable = new Mutable(enchantments);

            for (Entry<Holder<Enchantment>> entry : enchantments.entrySet()) {
               Holder<Enchantment> enchantment = (Holder<Enchantment>)entry.getKey();
               int level = entry.getIntValue();
               ResourceLocation id = this.getEnchantmentId(enchantment);
               if (id != null) {
                  String enchantmentId = id.toString();
                  if (!bookStorage.getBlacklistEnchantments().contains(enchantmentId) && level > 1) {
                     mutable.set(enchantment, level - 1);
                     stack.set(DataComponents.STORED_ENCHANTMENTS, mutable.toImmutable());
                     slot.setChanged();
                     bookStorage.saveContainer(instance, player, bookContainer);
                     this.broadcastChanges();
                     player.playNotifySound(SoundEvents.GRINDSTONE_USE, SoundSource.PLAYERS, 0.8F, 1.0F);
                     return true;
                  }
               }
            }

            return false;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public int getMaxAllowedLevel(IBookStorage bookStorage, Holder<Enchantment> enchantment, String enchantmentId) {
      int vanillaMax = ((Enchantment)enchantment.value()).getMaxLevel();
      return bookStorage.getBlackListBonusLevelEnchantments().contains(enchantmentId)
         ? vanillaMax
         : vanillaMax + bookStorage.getMaximumBonusLevel(this.getSkillInstance(this.player), this.player);
   }

   public ResourceLocation getEnchantmentId(Holder<Enchantment> enchantment) {
      return enchantment.unwrapKey().<ResourceLocation>map(ResourceKey::location).orElse(null);
   }

   public ManasSkill getSkill() {
      return this.skill;
   }

   public Player getPlayer() {
      return this.player;
   }

   public int getPage() {
      return this.page;
   }
}
