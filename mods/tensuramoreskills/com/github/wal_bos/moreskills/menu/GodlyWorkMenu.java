package com.github.wal_bos.moreskills.menu;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.handler.GodlyScienceSmitheryHandler;
import com.github.wal_bos.moreskills.registry.menu.ModMenus;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GodlyWorkMenu extends AbstractContainerMenu {
   public static final int PREVIOUS_PAGE = 1000000;
   public static final int NEXT_PAGE = 1000001;
   private static final String ARCHIVE_ROOT = "DivineIncrementArchive";
   private final GodlyWorkMenu.Kind kind;
   private final List<ResourceLocation> entries;
   private final SimpleContainer workContainer;
   private final SimpleContainerData data;
   private final Player owner;

   public GodlyWorkMenu(int containerId, Inventory inventory, RegistryFriendlyByteBuf buf) {
      this(containerId, inventory, (GodlyWorkMenu.Kind)buf.readEnum(GodlyWorkMenu.Kind.class));
   }

   public GodlyWorkMenu(int containerId, Inventory inventory, GodlyWorkMenu.Kind kind) {
      super((MenuType)ModMenus.GODLY_WORK_MENU.get(), containerId);
      this.kind = kind;
      this.owner = inventory.player;
      this.entries = buildEntries(kind);
      this.data = new SimpleContainerData(1);
      this.workContainer = kind == GodlyWorkMenu.Kind.DIVINE_INCREMENT ? new SimpleContainer(54) : new SimpleContainer(0);
      this.addDataSlots(this.data);
      if (kind == GodlyWorkMenu.Kind.DIVINE_INCREMENT) {
         this.addWorkSlots();
         this.addPlayerInventory(inventory, 140);
         this.loadArchivePage(0);
      }
   }

   public static void open(ServerPlayer player, GodlyWorkMenu.Kind kind) {
      player.openMenu(
         new SimpleMenuProvider((containerId, inventory, ignored) -> new GodlyWorkMenu(containerId, inventory, kind), Component.translatable(kind.titleKey())),
         buf -> buf.writeEnum(kind)
      );
   }

   public GodlyWorkMenu.Kind getKind() {
      return this.kind;
   }

   public List<ResourceLocation> getEntries() {
      return this.entries;
   }

   public int getPage() {
      return this.data.get(0);
   }

   public boolean clickMenuButton(Player player, int id) {
      if (this.kind != GodlyWorkMenu.Kind.GENESIS && this.kind != GodlyWorkMenu.Kind.DIVINE_CONSTRUCTION) {
         if (this.kind == GodlyWorkMenu.Kind.DIVINE_INCREMENT && (id == 1000000 || id == 1000001)) {
            if (player.level().isClientSide()) {
               return true;
            }

            int page = this.getPage();
            int maxPages = (Integer)MoreSkillsConfigs.GRAND.godlyScientist.archiveMaximumPages.get();
            int next = id == 1000000 ? Math.max(0, page - 1) : Math.min(maxPages - 1, page + 1);
            if (next != page) {
               this.saveArchivePage(page);
               this.data.set(0, next);
               this.loadArchivePage(next);
               this.broadcastChanges();
            }

            return true;
         } else {
            return false;
         }
      } else {
         if (id < 0 || id >= this.entries.size()) {
            return false;
         }

         if (player.level().isClientSide()) {
            return true;
         }

         if (player instanceof ServerPlayer serverPlayer) {
            if (this.kind == GodlyWorkMenu.Kind.GENESIS) {
               this.createGenesisWeapon(serverPlayer, this.entries.get(id));
            } else {
               this.createPermanentSkill(serverPlayer, this.entries.get(id));
            }

            return true;
         } else {
            return false;
         }
      }
   }

   public boolean stillValid(Player player) {
      if (!player.isAlive()) {
         return false;
      }

      ManasSkill required = this.kind.scientist() ? (ManasSkill)AllSkills.GODLY_SCIENTIST.get() : (ManasSkill)AllSkills.GODLY_SMITHERY.get();
      return SkillAPI.getSkillsFrom(player).getSkill(required).isPresent();
   }

   public void removed(Player player) {
      if (!player.level().isClientSide() && this.kind == GodlyWorkMenu.Kind.DIVINE_INCREMENT) {
         this.saveArchivePage(this.getPage());
      }

      super.removed(player);
   }

   public ItemStack quickMoveStack(Player player, int index) {
      if (index >= 0 && index < this.slots.size() && this.workContainer.getContainerSize() != 0) {
         Slot slot = (Slot)this.slots.get(index);
         if (!slot.hasItem()) {
            return ItemStack.EMPTY;
         }

         ItemStack stack = slot.getItem();
         ItemStack result = stack.copy();
         int workSize = this.workContainer.getContainerSize();
         if (index < workSize) {
            if (!this.moveItemStackTo(stack, workSize, this.slots.size(), true)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.moveItemStackTo(stack, 0, workSize, false)) {
            return ItemStack.EMPTY;
         }

         if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
         } else {
            slot.setChanged();
         }

         return result;
      } else {
         return ItemStack.EMPTY;
      }
   }

   private void createGenesisWeapon(ServerPlayer player, ResourceLocation id) {
      Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)AllSkills.GODLY_SCIENTIST.get());
      if (!optional.isEmpty()) {
         ManasSkillInstance instance = optional.get();
         int mode = 0;
         if (!instance.onCoolDown(mode) && !EnergyHelper.isOutOfEnergy(player, instance, mode)) {
            Item item = (Item)BuiltInRegistries.ITEM.get(id);
            ItemStack stack = item.getDefaultInstance();
            if (GodlyScienceSmitheryHandler.isWeapon(stack)) {
               stack.setCount(1);
               GodlyScienceSmitheryHandler.markGenesisWeapon(stack);
               if (!player.addItem(stack)) {
                  player.drop(stack, false);
               }

               instance.addMasteryPoint(player);
               instance.setCoolDown((Integer)MoreSkillsConfigs.GRAND.godlyScientist.genesisCooldownTicks.get(), mode);
               player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ANVIL_USE, SoundSource.PLAYERS, 1.0F, 1.2F);
               player.closeContainer();
            }
         }
      }
   }

   private void createPermanentSkill(ServerPlayer player, ResourceLocation id) {
      Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)AllSkills.GODLY_SMITHERY.get());
      if (!optional.isEmpty()) {
         ManasSkillInstance instance = optional.get();
         int mode = 0;
         if (!instance.onCoolDown(mode) && !EnergyHelper.isOutOfEnergy(player, instance, mode)) {
            ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(id);
            if (skill instanceof Skill tensuraSkill && isConstructible(tensuraSkill.getType())) {
               if (SkillAPI.getSkillsFrom(player).getSkill(skill).isPresent()) {
                  player.displayClientMessage(Component.translatable("tensuramoreskills.godly.already_owned"), true);
               } else {
                  TensuraSkillInstance created = new TensuraSkillInstance(skill);
                  created.setMastery(0.0);
                  created.getOrCreateTag().putBoolean("NoMagiculeCost", true);
                  if (SkillHelper.learnSkill(player, created)) {
                     instance.addMasteryPoint(player);
                     instance.setCoolDown((Integer)MoreSkillsConfigs.GRAND.godlySmithery.divineConstructionCooldownTicks.get(), mode);
                     player.level()
                        .playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
                     player.closeContainer();
                  }
               }
            }
         }
      }
   }

   private void addWorkSlots() {
      for (int row = 0; row < 6; row++) {
         for (int column = 0; column < 9; column++) {
            int index = column + row * 9;
            this.addSlot(new Slot(this.workContainer, index, 8 + column * 18, 18 + row * 18));
         }
      }
   }

   private void addPlayerInventory(Inventory inventory, int inventoryY) {
      for (int row = 0; row < 3; row++) {
         for (int column = 0; column < 9; column++) {
            this.addSlot(new Slot(inventory, column + row * 9 + 9, 8 + column * 18, inventoryY + row * 18));
         }
      }

      for (int column = 0; column < 9; column++) {
         this.addSlot(new Slot(inventory, column, 8 + column * 18, inventoryY + 58));
      }
   }

   private void loadArchivePage(int page) {
      this.workContainer.clearContent();
      this.getSkillInstance((ManasSkill)AllSkills.GODLY_SCIENTIST.get()).ifPresent(instance -> {
         CompoundTag archive = instance.getOrCreateTag().getCompound("DivineIncrementArchive");
         this.loadContainer(archive.getCompound("Page_" + page), this.workContainer);
      });
   }

   private void saveArchivePage(int page) {
      this.getSkillInstance((ManasSkill)AllSkills.GODLY_SCIENTIST.get()).ifPresent(instance -> {
         CompoundTag archive = instance.getOrCreateTag().getCompound("DivineIncrementArchive");
         String pageKey = "Page_" + page;
         if (this.workContainer.isEmpty()) {
            archive.remove(pageKey);
         } else {
            archive.put(pageKey, this.saveContainer(this.workContainer));
         }

         instance.getOrCreateTag().put("DivineIncrementArchive", archive);
         instance.markDirty();
      });
   }

   private CompoundTag saveContainer(SimpleContainer container) {
      NonNullList<ItemStack> items = NonNullList.withSize(container.getContainerSize(), ItemStack.EMPTY);

      for (int i = 0; i < container.getContainerSize(); i++) {
         items.set(i, container.getItem(i));
      }

      CompoundTag tag = new CompoundTag();
      ContainerHelper.saveAllItems(tag, items, this.owner.registryAccess());
      return tag;
   }

   private void loadContainer(CompoundTag tag, SimpleContainer container) {
      NonNullList<ItemStack> items = NonNullList.withSize(container.getContainerSize(), ItemStack.EMPTY);
      ContainerHelper.loadAllItems(tag, items, this.owner.registryAccess());

      for (int i = 0; i < items.size(); i++) {
         container.setItem(i, (ItemStack)items.get(i));
      }
   }

   private Optional<ManasSkillInstance> getSkillInstance(ManasSkill skill) {
      return SkillAPI.getSkillsFrom(this.owner).getSkill(skill);
   }

   private static List<ResourceLocation> buildEntries(GodlyWorkMenu.Kind kind) {
      if (kind == GodlyWorkMenu.Kind.GENESIS) {
         return BuiltInRegistries.ITEM
            .stream()
            .<ItemStack>map(Item::getDefaultInstance)
            .filter(GodlyScienceSmitheryHandler::isWeapon)
            .map(stack -> BuiltInRegistries.ITEM.getKey(stack.getItem()))
            .distinct()
            .sorted(Comparator.comparing(ResourceLocation::toString))
            .toList();
      } else {
         return kind == GodlyWorkMenu.Kind.DIVINE_CONSTRUCTION
            ? SkillAPI.getSkillRegistry()
               .entrySet()
               .stream()
               .map(entry -> (ManasSkill)entry.getValue())
               .filter(skill -> skill instanceof Skill tensuraSkill && isConstructible(tensuraSkill.getType()))
               .<ResourceLocation>map(ManasSkill::getRegistryName)
               .filter(id -> id != null)
               .sorted(Comparator.comparing(ResourceLocation::toString))
               .toList()
            : List.of();
      }
   }

   private static boolean isConstructible(SkillType type) {
      return type == SkillType.RESISTANCE || type == SkillType.EXTRA || type == SkillType.INTRINSIC;
   }

   public enum Kind {
      GENESIS(true, "tensuramoreskills.gui.godly.genesis"),
      DIVINE_INCREMENT(true, "tensuramoreskills.gui.godly.divine_increment"),
      DIVINE_CONSTRUCTION(false, "tensuramoreskills.gui.godly.divine_construction");

      private final boolean scientist;
      private final String titleKey;

      Kind(boolean scientist, String titleKey) {
         this.scientist = scientist;
         this.titleKey = titleKey;
      }

      public boolean scientist() {
         return this.scientist;
      }

      public String titleKey() {
         return this.titleKey;
      }
   }
}
