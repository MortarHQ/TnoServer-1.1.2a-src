package com.github.b4ndithelps.tennogamenolife.event;

import com.github.b4ndithelps.tennogamenolife.registry.item.Tenreincarnationitems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.Clone;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "tennogamenolife")
public final class SoulboundWeaponDeathHandler {
   private static final String PENDING_SICKLE = "tennogamenolife:pending_sickle";
   private static final String PENDING_CANNON = "tennogamenolife:pending_ex_machinas_weapon";

   private SoulboundWeaponDeathHandler() {
   }

   @SubscribeEvent
   public static void onLivingDrops(LivingDropsEvent event) {
      if (event.getEntity() instanceof Player player && !player.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
         event.getDrops().removeIf(drop -> {
            ItemStack stack = drop.getItem();
            if (!isSoulboundWeapon(stack)) {
               return false;
            }

            savePending(player, stack);
            return true;
         });
      }
   }

   @SubscribeEvent
   public static void onPlayerClone(Clone event) {
      if (event.isWasDeath()) {
         Player original = event.getOriginal();
         Player replacement = event.getEntity();
         copyPendingData(original, replacement);
         restorePending(replacement);
      }
   }

   @SubscribeEvent
   public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
      restorePending(event.getEntity());
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      Player player = event.getEntity();
      if (!player.level().isClientSide && player.tickCount % 20 == 0) {
         restorePending(player);
      }
   }

   private static void savePending(Player player, ItemStack stack) {
      String key = pendingKey(stack);
      CompoundTag persisted = getPersistedData(player);
      if (!persisted.contains(key)) {
         persisted.put(key, stack.saveOptional(player.registryAccess()));
         setPersistedData(player, persisted);
      }
   }

   private static void restorePending(Player player) {
      if (!player.level().isClientSide) {
         CompoundTag persisted = getPersistedData(player);
         restorePendingStack(player, persisted, "tennogamenolife:pending_sickle", (Item)Tenreincarnationitems.SICKLE.get());
         restorePendingStack(player, persisted, "tennogamenolife:pending_ex_machinas_weapon", (Item)Tenreincarnationitems.EX_MACHINAS_WEAPON.get());
         setPersistedData(player, persisted);
      }
   }

   private static void restorePendingStack(Player player, CompoundTag persisted, String key, Item item) {
      if (persisted.contains(key)) {
         if (hasItem(player, item)) {
            persisted.remove(key);
         } else {
            ItemStack stack = ItemStack.parseOptional(player.registryAccess(), persisted.getCompound(key));
            if (!stack.isEmpty() && stack.is(item)) {
               if (player.getInventory().add(stack)) {
                  persisted.remove(key);
                  player.getInventory().setChanged();
               }
            } else {
               persisted.remove(key);
            }
         }
      }
   }

   private static void copyPendingData(Player original, Player replacement) {
      CompoundTag originalPersisted = getPersistedData(original);
      if (originalPersisted.contains("tennogamenolife:pending_sickle") || originalPersisted.contains("tennogamenolife:pending_ex_machinas_weapon")) {
         CompoundTag replacementPersisted = getPersistedData(replacement);
         if (originalPersisted.contains("tennogamenolife:pending_sickle")) {
            replacementPersisted.put("tennogamenolife:pending_sickle", originalPersisted.getCompound("tennogamenolife:pending_sickle").copy());
         }

         if (originalPersisted.contains("tennogamenolife:pending_ex_machinas_weapon")) {
            replacementPersisted.put(
               "tennogamenolife:pending_ex_machinas_weapon", originalPersisted.getCompound("tennogamenolife:pending_ex_machinas_weapon").copy()
            );
         }

         setPersistedData(replacement, replacementPersisted);
      }
   }

   private static CompoundTag getPersistedData(Player player) {
      return player.getPersistentData().getCompound("PlayerPersisted");
   }

   private static void setPersistedData(Player player, CompoundTag persisted) {
      player.getPersistentData().put("PlayerPersisted", persisted);
   }

   private static boolean hasItem(Player player, Item item) {
      for (ItemStack stack : player.getInventory().items) {
         if (stack.is(item)) {
            return true;
         }
      }

      return player.getMainHandItem().is(item) || player.getOffhandItem().is(item);
   }

   private static boolean isSoulboundWeapon(ItemStack stack) {
      return stack.is((Item)Tenreincarnationitems.SICKLE.get()) || stack.is((Item)Tenreincarnationitems.EX_MACHINAS_WEAPON.get());
   }

   private static String pendingKey(ItemStack stack) {
      return stack.is((Item)Tenreincarnationitems.SICKLE.get()) ? "tennogamenolife:pending_sickle" : "tennogamenolife:pending_ex_machinas_weapon";
   }
}
