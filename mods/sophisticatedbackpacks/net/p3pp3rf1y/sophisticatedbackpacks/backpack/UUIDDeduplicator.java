package net.p3pp3rf1y.sophisticatedbackpacks.backpack;

import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.IBackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.util.PlayerInventoryProvider;

public class UUIDDeduplicator {
   private UUIDDeduplicator() {
   }

   public static void checkForDuplicateBackpacksAndRemoveTheirUUID(Player player, UUID backpackUuid, ItemStack backpack) {
      IBackpackWrapper backpackWrapper = BackpackWrapper.fromStack(backpack);
      PlayerInventoryProvider.get().runOnBackpacks(player, (otherBackpack, inventoryHandlerName, identifier, slot) -> {
         if (otherBackpack != backpack) {
            IBackpackWrapper wrapper = BackpackWrapper.fromStack(otherBackpack);
            wrapper.getContentsUuid().ifPresent(uuid -> {
               if (uuid.equals(backpackUuid)) {
                  dedupeBackpackWrappers(backpackWrapper, wrapper);
               }
            });
         }

         return false;
      });
   }

   public static void dedupeBackpackItemEntityInArea(ItemEntity newBackpackItemEntity) {
      IBackpackWrapper newBackpackWrapper = BackpackWrapper.fromStack(newBackpackItemEntity.getItem());
      newBackpackWrapper.getContentsUuid().ifPresent(backpackId -> dedupeBackpackItemEntityInArea(newBackpackWrapper, newBackpackItemEntity, backpackId));
   }

   private static void dedupeBackpackItemEntityInArea(IBackpackWrapper newBackpackWrapper, ItemEntity newBackpackItemEntity, UUID backpackId) {
      for (ItemEntity entity : newBackpackItemEntity.level()
         .getEntitiesOfClass(ItemEntity.class, newBackpackItemEntity.getBoundingBox().inflate(10.0), Entity::isAlive)) {
         if (entity != newBackpackItemEntity) {
            checkEntityBackpackIdMatchAndRemoveIfItDoes(newBackpackWrapper, backpackId, entity);
         }
      }
   }

   private static boolean checkEntityBackpackIdMatchAndRemoveIfItDoes(IBackpackWrapper newBackpackWrapper, UUID newBackpackId, ItemEntity entity) {
      IBackpackWrapper entityBackpackWrapper = BackpackWrapper.fromStack(entity.getItem());
      return entityBackpackWrapper.getContentsUuid().map(backpackId -> {
         if (backpackId.equals(newBackpackId)) {
            dedupeBackpackWrappers(newBackpackWrapper, entityBackpackWrapper);
            return true;
         } else {
            return false;
         }
      }).orElse(false);
   }

   public static IBackpackWrapper dedupeBackpackWrappers(IBackpackWrapper firstBackpackWrapper, IBackpackWrapper secondBackpackWrapper) {
      if (isFirstBackpackPreferred(firstBackpackWrapper, secondBackpackWrapper)) {
         removeUuid(secondBackpackWrapper);
         return firstBackpackWrapper;
      } else {
         removeUuid(firstBackpackWrapper);
         return secondBackpackWrapper;
      }
   }

   public static boolean isFirstBackpackPreferred(IBackpackWrapper firstBackpackWrapper, IBackpackWrapper secondBackpackWrapper) {
      return getInventorySize(firstBackpackWrapper) >= getInventorySize(secondBackpackWrapper);
   }

   private static int getInventorySize(IBackpackWrapper backpackWrapper) {
      return backpackWrapper.getInventoryHandler().getSlots();
   }

   private static void removeUuid(IBackpackWrapper backpackWrapper) {
      backpackWrapper.removeContentsUUIDTag();
      backpackWrapper.onContentsNbtUpdated();
   }
}
