package io.github.Memoires.mysticism.ability.subclass;

import dev.architectury.networking.NetworkManager;
import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.menu.FlarefrostStorehouseMenu;
import io.github.Memoires.mysticism.network.s2c.OpenFlarefrostStorehouseMenuPayload;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.subclass.ISpatialStorage;
import io.github.manasmods.tensura.menu.container.SpatialStorageContainer;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IFlarefrostStorehouse<S extends ManasSkill & IFlarefrostStorehouse<S>> extends ISpatialStorage {
   UniqueMysticismSkillConfig.Phaser CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Phaser;

   default int getSpatialStorageIdOffset() {
      return 0;
   }

   default void openStorehouseMenu(LivingEntity entity, ManasSkillInstance instance) {
      if (entity instanceof ServerPlayer player) {
         player.closeContainer();
         this.openStorehouseMenu(player, instance);
         player.playNotifySound((SoundEvent)TensuraSoundEvents.SPATIAL_STORAGE.get(), SoundSource.PLAYERS, 0.5F, 1.0F);
      }
   }

   default void openStorehouseMenu(ServerPlayer player, ManasSkillInstance instance) {
      player.nextContainerCounter();
      S skill = (S)instance.getSkill();
      SpatialStorageContainer container = this.getSpatialStorage(instance, player.registryAccess());
      NetworkManager.sendToPlayer(
         player,
         new OpenFlarefrostStorehouseMenuPayload(
            player.containerCounter, container.getContainerSize(), container.getMaxStackSize(), player.getId(), skill.getRegistryName()
         )
      );
      player.containerMenu = new FlarefrostStorehouseMenu(player.containerCounter, player.getInventory(), container, skill);
      player.initMenu(player.containerMenu);
   }

   default void moveItemsToSpatialStorage(ManasSkillInstance from, ManasSkillInstance to, LivingEntity entity, boolean openNewStorage) {
      ISpatialStorage newStorage = (ISpatialStorage)to.getSkill();
      SpatialStorageContainer container = this.getSpatialStorage(from, entity.registryAccess());
      if (!container.isEmpty()) {
         for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty() && !newStorage.addItemToSpatialStorage(to, entity, stack) && entity instanceof Player player && !player.addItem(stack)) {
               player.drop(stack, false);
            }
         }

         container.clearContent();
         from.getOrCreateTag().remove("Storehouse");
         from.markDirty();
      }

      if (openNewStorage) {
         newStorage.openSpatialStorage(entity, to);
      }
   }

   default void setItemInSpatialStorage(ManasSkillInstance instance, LivingEntity entity, ItemStack stack, int slot) {
      SpatialStorageContainer container = this.getSpatialStorage(instance, entity.registryAccess());
      container.setItem(slot, stack);
      this.saveContainer(instance, entity, container);
   }

   default void saveContainer(ManasSkillInstance instance, LivingEntity entity, SpatialStorageContainer container) {
      CompoundTag tag = instance.getOrCreateTag();
      tag.put("Storehouse", container.createTag(entity.registryAccess()));
      instance.markDirty();
      SkillAPI.getSkillsFrom(entity).markDirty();
   }
}
