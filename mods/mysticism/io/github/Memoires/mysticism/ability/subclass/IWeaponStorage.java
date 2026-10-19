package io.github.Memoires.mysticism.ability.subclass;

import dev.architectury.networking.NetworkManager;
import io.github.Memoires.mysticism.menu.WeaponStorageMenu;
import io.github.Memoires.mysticism.menu.container.WeaponStorageContainer;
import io.github.Memoires.mysticism.network.s2c.OpenCustomStorageMenuPayload;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IWeaponStorage<S extends ManasSkill & IWeaponStorage<S>> {
   WeaponStorageContainer getWeaponStorage(ManasSkillInstance var1, Provider var2, LivingEntity var3);

   default void openWeaponStorageMenu(LivingEntity entity, ManasSkillInstance instance) {
      if (entity instanceof ServerPlayer player) {
         player.closeContainer();
         this.openWeaponStorageMenu(player, instance, 0);
         player.playNotifySound((SoundEvent)TensuraSoundEvents.SPATIAL_STORAGE.get(), SoundSource.PLAYERS, 0.5F, 1.0F);
      }
   }

   default void openWeaponStorageMenu(ServerPlayer player, ManasSkillInstance instance, int page) {
      player.nextContainerCounter();
      S skill = (S)instance.getSkill();
      WeaponStorageContainer container = this.getWeaponStorage(instance, player.registryAccess(), player);
      boolean mastered = instance.isMastered(player);
      NetworkManager.sendToPlayer(
         player,
         new OpenCustomStorageMenuPayload(
            OpenCustomStorageMenuPayload.StorageType.WEAPON,
            player.containerCounter,
            container.getContainerSize(),
            container.getMaxStackSize(),
            page,
            mastered,
            player.getId(),
            skill.getRegistryName()
         )
      );
      player.containerMenu = new WeaponStorageMenu(player.containerCounter, player.getInventory(), container, skill, page, mastered);
      player.initMenu(player.containerMenu);
   }

   default boolean addItemToWeaponStorage(ManasSkillInstance instance, LivingEntity entity, ItemStack stack) {
      if (!WeaponStorageContainer.isWeapon(stack)) {
         return false;
      } else {
         WeaponStorageContainer container = this.getWeaponStorage(instance, entity.registryAccess(), entity);
         if (container.canAddItem(stack)) {
            container.addItem(stack);
            this.saveContainer(instance, entity, container);
            return true;
         } else {
            this.saveContainer(instance, entity, container);
            return false;
         }
      }
   }

   default void setItemInWeaponStorage(ManasSkillInstance instance, LivingEntity entity, ItemStack stack, int slot) {
      WeaponStorageContainer container = this.getWeaponStorage(instance, entity.registryAccess(), entity);
      container.setItem(slot, stack);
      this.saveContainer(instance, entity, container);
   }

   default void moveItemsToWeaponStorage(ManasSkillInstance from, ManasSkillInstance to, LivingEntity entity, boolean openNewStorage) {
      IWeaponStorage newStorage = (IWeaponStorage)to.getSkill();
      WeaponStorageContainer container = this.getWeaponStorage(from, entity.registryAccess(), entity);
      if (!container.isEmpty()) {
         for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty() && !newStorage.addItemToWeaponStorage(to, entity, stack) && entity instanceof Player player && !player.addItem(stack)) {
               player.drop(stack, false);
            }
         }

         container.clearContent();
         from.getOrCreateTag().remove("WeaponStorage");
         from.markDirty();
      }

      if (openNewStorage) {
         newStorage.openWeaponStorageMenu(entity, to);
      }
   }

   default void dropAllItems(ManasSkillInstance instance, Player player) {
      WeaponStorageContainer container = this.getWeaponStorage(instance, player.registryAccess(), player);
      if (!container.isEmpty()) {
         for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty() && !player.addItem(stack)) {
               player.drop(stack, false);
            }
         }

         container.clearContent();
         instance.getOrCreateTag().remove("WeaponStorage");
         instance.markDirty();
      }
   }

   default void saveContainer(ManasSkillInstance instance, LivingEntity entity, WeaponStorageContainer container) {
      CompoundTag tag = instance.getOrCreateTag();
      tag.put("WeaponStorage", container.createTag(entity.registryAccess()));
      instance.markDirty();
      SkillAPI.getSkillsFrom(entity).markDirty();
   }
}
