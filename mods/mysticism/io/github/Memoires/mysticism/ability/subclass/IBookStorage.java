package io.github.Memoires.mysticism.ability.subclass;

import dev.architectury.networking.NetworkManager;
import io.github.Memoires.mysticism.menu.BookStorageMenu;
import io.github.Memoires.mysticism.menu.container.BookStorageContainer;
import io.github.Memoires.mysticism.network.s2c.OpenCustomStorageMenuPayload;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import java.util.List;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IBookStorage<S extends ManasSkill & IBookStorage<S>> {
   BookStorageContainer getBookStorage(ManasSkillInstance var1, Provider var2);

   default void openBookStorage(LivingEntity entity, ManasSkillInstance instance) {
      if (entity instanceof ServerPlayer player) {
         player.closeContainer();
         this.openBookStoragePage(player, instance, 0);
         player.playNotifySound((SoundEvent)TensuraSoundEvents.SPATIAL_STORAGE.get(), SoundSource.PLAYERS, 0.75F, 1.0F);
      }
   }

   int getMaximumBonusLevel(ManasSkillInstance var1, Player var2);

   default List<String> getBlacklistEnchantments() {
      return List.of();
   }

   default List<String> getBlackListBonusLevelEnchantments() {
      return List.of();
   }

   default boolean isAllowedToUpgradeEnchantments() {
      return false;
   }

   default void openBookStoragePage(ServerPlayer player, ManasSkillInstance instance, int page) {
      player.nextContainerCounter();
      ManasSkill skill = instance.getSkill();
      BookStorageContainer container = this.getBookStorage(instance, player.registryAccess());
      boolean mastered = instance.isMastered(player);
      NetworkManager.sendToPlayer(
         player,
         new OpenCustomStorageMenuPayload(
            OpenCustomStorageMenuPayload.StorageType.BOOK,
            player.containerCounter,
            container.getContainerSize(),
            container.getMaxStackSize(),
            page,
            mastered,
            player.getId(),
            skill.getRegistryName()
         )
      );
      player.containerMenu = new BookStorageMenu(player.containerCounter, player.getInventory(), container, skill, page);
      player.initMenu(player.containerMenu);
   }

   default boolean addItemToBookStorage(ManasSkillInstance instance, LivingEntity entity, ItemStack stack) {
      BookStorageContainer container = this.getBookStorage(instance, entity.registryAccess());
      if (container.canAddItem(stack)) {
         container.addItem(stack);
         this.saveContainer(instance, entity, container);
         return true;
      } else {
         this.saveContainer(instance, entity, container);
         return false;
      }
   }

   default void setItemInBookStorage(ManasSkillInstance instance, LivingEntity entity, ItemStack stack, int slot) {
      BookStorageContainer container = this.getBookStorage(instance, entity.registryAccess());
      container.setItem(slot, stack);
      this.saveContainer(instance, entity, container);
   }

   default void moveItemsToBookStorage(ManasSkillInstance from, ManasSkillInstance to, LivingEntity entity, boolean openNewStorage) {
      IBookStorage newStorage = (IBookStorage)to.getSkill();
      BookStorageContainer container = this.getBookStorage(from, entity.registryAccess());
      if (!container.isEmpty()) {
         for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty() && !newStorage.addItemToBookStorage(to, entity, stack) && entity instanceof Player player && !player.addItem(stack)) {
               player.drop(stack, false);
            }
         }

         container.clearContent();
         from.getOrCreateTag().remove("BookStorage");
         from.markDirty();
      }

      if (openNewStorage) {
         newStorage.openBookStorage(entity, to);
      }
   }

   default void dropAllItems(ManasSkillInstance instance, Player player) {
      BookStorageContainer container = this.getBookStorage(instance, player.registryAccess());
      if (!container.isEmpty()) {
         for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty() && !player.addItem(stack)) {
               player.drop(stack, false);
            }
         }

         container.clearContent();
         instance.getOrCreateTag().remove("BookStorage");
         instance.markDirty();
      }
   }

   default void saveContainer(ManasSkillInstance instance, LivingEntity entity, BookStorageContainer container) {
      CompoundTag tag = instance.getOrCreateTag();
      tag.put("BookStorage", container.createTag(entity.registryAccess()));
      instance.markDirty();
      SkillAPI.getSkillsFrom(entity).markDirty();
   }
}
