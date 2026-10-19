package com.tensura_tno.compat;

import com.tensura_tno.TensuraTNOMod;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

public final class YigdCompat {
   private static final String PLAYER_DEATH_PROXY_TAG = "tensura_tno_yigd_player_death_proxy";
   private static final ThreadLocal<Integer> PLAYER_DEATH_REPLACEMENT_DEPTH = new ThreadLocal<>();
   private static volatile boolean yigdPresent = true;

   private YigdCompat() {
   }

   public static void register() {
      if (!ModList.get().isLoaded("yigd")) {
         yigdPresent = false;
      } else {
         NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, LivingDropsEvent.class, event -> {
            if (yigdPresent) {
               LivingEntity clone = event.getEntity();
               if (consumePlayerDeathProxyMarker(clone)) {
                  Collection<ItemEntity> drops = event.getDrops();
                  if (!drops.isEmpty()) {
                     ServerPlayer owner = getCloneOwner(clone);
                     if (owner != null) {
                        if (clone.level() instanceof ServerLevel deathLevel) {
                           if (!deathLevel.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
                              try {
                                 createGraveFromCloneDrops(owner, clone.position(), event.getSource(), deathLevel, drops);
                                 drops.clear();
                              } catch (ClassNotFoundException e) {
                                 yigdPresent = false;
                              } catch (Exception e) {
                                 TensuraTNOMod.LOGGER.error("[TensuraTNO] YigdCompat: failed to create a grave for a player-death proxy", e);
                              }
                           }
                        }
                     }
                  }
               }
            }
         });
      }
   }

   public static void markPlayerDeathProxy(LivingEntity clone) {
      clone.getPersistentData().putBoolean("tensura_tno_yigd_player_death_proxy", true);
   }

   public static void clearPlayerDeathProxyMarker(LivingEntity clone) {
      clone.getPersistentData().remove("tensura_tno_yigd_player_death_proxy");
   }

   public static void beginPlayerDeathReplacement() {
      Integer depth = PLAYER_DEATH_REPLACEMENT_DEPTH.get();
      PLAYER_DEATH_REPLACEMENT_DEPTH.set(depth == null ? 1 : depth + 1);
   }

   public static void endPlayerDeathReplacement() {
      Integer depth = PLAYER_DEATH_REPLACEMENT_DEPTH.get();
      if (depth != null && depth > 1) {
         PLAYER_DEATH_REPLACEMENT_DEPTH.set(depth - 1);
      } else {
         PLAYER_DEATH_REPLACEMENT_DEPTH.remove();
      }
   }

   public static boolean isPlayerDeathReplacementActive() {
      Integer depth = PLAYER_DEATH_REPLACEMENT_DEPTH.get();
      return depth != null && depth > 0;
   }

   static boolean consumePlayerDeathProxyMarker(LivingEntity clone) {
      CompoundTag data = clone.getPersistentData();
      if (!data.getBoolean("tensura_tno_yigd_player_death_proxy")) {
         return false;
      }

      clearPlayerDeathProxyMarker(clone);
      return true;
   }

   private static ServerPlayer getCloneOwner(LivingEntity entity) {
      try {
         Class<?> cloneClass = Class.forName("io.github.manasmods.tensura.entity.human.CloneEntity");
         if (!cloneClass.isInstance(entity)) {
            return null;
         } else {
            return cloneClass.getMethod("getOwner").invoke(entity) instanceof ServerPlayer player ? player : null;
         }
      } catch (Exception | NoClassDefFoundError ignored) {
         return null;
      }
   }

   private static void createGraveFromCloneDrops(ServerPlayer player, Vec3 pos, DamageSource source, ServerLevel level, Collection<ItemEntity> drops) throws Exception {
      List<ItemStack> stacks = new ArrayList<>();

      for (ItemEntity drop : drops) {
         if (!drop.getItem().isEmpty()) {
            stacks.add(drop.getItem().copy());
         }
      }

      if (!stacks.isEmpty()) {
         YigdCompat.GravePlacement grave = createGraveFromStacks(player, level, pos, source, stacks);
         scheduleGraveLocationMessage(player, grave.graveComponent(), grave.respawnComponent());
      }
   }

   private static YigdCompat.GravePlacement createGraveFromStacks(ServerPlayer owner, ServerLevel level, Vec3 pos, DamageSource source, List<ItemStack> stacks) throws Exception {
      Class<?> dropRuleClass = Class.forName("com.b1n_ry.yigd.util.DropRule");
      Object putInGrave = Enum.valueOf(dropRuleClass.asSubclass(Enum.class), "PUT_IN_GRAVE");
      Class<?> graveItemClass = Class.forName("com.b1n_ry.yigd.data.GraveItem");
      Constructor<?> graveItemCtor = graveItemClass.getConstructor(ItemStack.class, dropRuleClass);
      int mainSize = Math.max(owner.getInventory().items.size(), stacks.size());
      int armorSize = owner.getInventory().armor.size();
      int offhandSize = owner.getInventory().offhand.size();
      int vanillaSize = mainSize + armorSize + offhandSize;
      Object emptyGraveItem = graveItemCtor.newInstance(ItemStack.EMPTY, putInGrave);
      NonNullList<Object> graveItems = NonNullList.withSize(vanillaSize, emptyGraveItem);

      for (int i = 0; i < stacks.size(); i++) {
         Object graveItem = graveItemCtor.newInstance(stacks.get(i).copy(), putInGrave);
         if (i < vanillaSize) {
            graveItems.set(i, graveItem);
         } else {
            graveItems.add(graveItem);
         }
      }

      Class<?> inventoryClass = Class.forName("com.b1n_ry.yigd.components.InventoryComponent");
      Constructor<?> inventoryCtor = inventoryClass.getDeclaredConstructor(NonNullList.class, Map.class, int.class, int.class, int.class);
      inventoryCtor.setAccessible(true);
      Map<String, Object> emptyModItems = createEmptyModInventory(owner, inventoryClass);
      Object inventory = inventoryCtor.newInstance(graveItems, emptyModItems, mainSize, armorSize, offhandSize);
      Class<?> expClass = Class.forName("com.b1n_ry.yigd.components.ExpComponent");
      Constructor<?> expCtor = expClass.getDeclaredConstructor(int.class, double.class);
      expCtor.setAccessible(true);
      Object exp = expCtor.newInstance(0, 0.0);
      Class<?> graveClass = Class.forName("com.b1n_ry.yigd.components.GraveComponent");
      Constructor<?> graveCtor = graveClass.getConstructor(
         ResolvableProfile.class, inventoryClass, expClass, ServerLevel.class, Vec3.class, Component.class, UUID.class
      );
      Object grave = graveCtor.newInstance(
         new ResolvableProfile(owner.getGameProfile()),
         inventory,
         exp,
         level,
         pos.add(0.0, 0.5, 0.0),
         source.getLocalizedDeathMessage(owner),
         source.getEntity() instanceof ServerPlayer killer ? killer.getUUID() : null
      );
      if (!(Boolean)graveClass.getMethod("isEmpty").invoke(grave)) {
         graveClass.getMethod("backUp").invoke(grave);
      }

      Class<?> respawnClass = Class.forName("com.b1n_ry.yigd.components.RespawnComponent");
      Object respawn = respawnClass.getConstructor(ServerPlayer.class).newInstance(owner);
      Class<?> contextClass = Class.forName("com.b1n_ry.yigd.data.DeathContext");
      Object context = contextClass.getConstructor(ServerPlayer.class, ServerLevel.class, Vec3.class, DamageSource.class)
         .newInstance(owner, level, pos, source);
      graveClass.getMethod("generateOrDrop", Direction.class, contextClass, respawnClass).invoke(grave, owner.getDirection(), context, respawn);
      return new YigdCompat.GravePlacement(grave, respawn);
   }

   private static Map<String, Object> createEmptyModInventory(ServerPlayer owner, Class<?> inventoryClass) throws Exception {
      Object ownerInventory = inventoryClass.getConstructor(ServerPlayer.class).newInstance(owner);
      Predicate<Object> rejectAll = ignored -> false;
      Object emptyInventory = inventoryClass.getMethod("filteredInv", Predicate.class).invoke(ownerInventory, rejectAll);
      Field modItemsField = inventoryClass.getDeclaredField("modInventoryItems");
      modItemsField.setAccessible(true);
      return new HashMap<>((Map<? extends String, ? extends Object>)modItemsField.get(emptyInventory));
   }

   public static void repairLegacyGraveInventory(Object graveComponent, ServerPlayer player) {
      if (yigdPresent && graveComponent != null) {
         try {
            Class<?> graveClass = graveComponent.getClass();
            Object inventory = graveClass.getMethod("getInventoryComponent").invoke(graveComponent);
            Class<?> inventoryClass = inventory.getClass();
            Field itemsField = inventoryClass.getDeclaredField("items");
            itemsField.setAccessible(true);
            List<Object> items = (List<Object>)itemsField.get(inventory);
            int mainSize = readIntField(inventoryClass, inventory, "mainSize");
            int armorSize = readIntField(inventoryClass, inventory, "armorSize");
            int offhandSize = readIntField(inventoryClass, inventory, "offHandSize");
            int requiredSize = mainSize + armorSize + offhandSize;
            if (requiredSize <= 0) {
               requiredSize = player.getInventory().getContainerSize();
            }

            Object emptyGraveItem = inventoryClass.getField("EMPTY_GRAVE_ITEM").get(null);
            List<Object> repairedValues = copyAndPadLegacyInventoryList(items, requiredSize, emptyGraveItem);
            boolean repairedVanillaSlots = repairedValues != null;
            if (!repairedVanillaSlots) {
               repairedValues = new ArrayList<>(items);
            }

            Field modItemsField = inventoryClass.getDeclaredField("modInventoryItems");
            modItemsField.setAccessible(true);
            Map<String, Object> oldModItems = (Map<String, Object>)modItemsField.get(inventory);
            Map<String, Object> repairedModItems = createEmptyModInventory(player, inventoryClass);
            repairedModItems.putAll(oldModItems);
            boolean repairedModSlots = repairedModItems.size() > oldModItems.size();
            if (!repairedVanillaSlots && !repairedModSlots) {
               return;
            }

            NonNullList<Object> repairedItems = NonNullList.create();
            repairedItems.addAll(repairedValues);
            Constructor<?> inventoryCtor = inventoryClass.getDeclaredConstructor(NonNullList.class, Map.class, int.class, int.class, int.class);
            inventoryCtor.setAccessible(true);
            Object repairedInventory = inventoryCtor.newInstance(repairedItems, repairedModItems, mainSize, armorSize, offhandSize);
            Field graveInventoryField = graveClass.getDeclaredField("inventoryComponent");
            graveInventoryField.setAccessible(true);
            graveInventoryField.set(graveComponent, repairedInventory);
            TensuraTNOMod.LOGGER.warn("[TensuraTNO] Repaired a legacy YIGD grave inventory before claim");
         } catch (Exception e) {
            TensuraTNOMod.LOGGER.error("[TensuraTNO] YigdCompat: failed to repair a legacy grave inventory", e);
         }
      }
   }

   static List<Object> copyAndPadLegacyInventoryList(List<Object> items, int requiredSize, Object emptyGraveItem) {
      if (requiredSize > 0 && items.size() < requiredSize) {
         List<Object> repaired = new ArrayList<>(requiredSize);
         repaired.addAll(items);

         while (repaired.size() < requiredSize) {
            repaired.add(emptyGraveItem);
         }

         return repaired;
      } else {
         return null;
      }
   }

   private static int readIntField(Class<?> owner, Object instance, String name) throws Exception {
      Field field = owner.getField(name);
      return field.getInt(instance);
   }

   private static void scheduleGraveLocationMessage(ServerPlayer player, Object graveComponent, Object respawnComponent) throws Exception {
      Class<?> yigdClass = Class.forName("com.b1n_ry.yigd.Yigd");
      Field endOfTickField = yigdClass.getField("END_OF_TICK");
      List<Runnable> endOfTick = (List<Runnable>)endOfTickField.get(null);
      endOfTick.add(() -> {
         try {
            if (wasGraveGenerated(respawnComponent)) {
               sendGraveLocationMessage(player, graveComponent);
            }
         } catch (Exception e) {
            TensuraTNOMod.LOGGER.error("[TensuraTNO] YigdCompat: failed to announce grave location", e);
         }
      });
   }

   private static boolean wasGraveGenerated(Object respawnComponent) throws Exception {
      if (respawnComponent == null) {
         return true;
      }

      Method wasGraveGenerated = respawnComponent.getClass().getMethod("wasGraveGenerated");
      return (Boolean)wasGraveGenerated.invoke(respawnComponent);
   }

   private static void sendGraveLocationMessage(ServerPlayer player, Object graveComponent) throws Exception {
      if (shouldInformGraveLocation() && graveComponent != null) {
         Method getPos = graveComponent.getClass().getMethod("getPos");
         Method getWorldKey = graveComponent.getClass().getMethod("getWorldRegistryKey");
         BlockPos gravePos = (BlockPos)getPos.invoke(graveComponent);
         Object worldKey = getWorldKey.invoke(graveComponent);
         Method location = worldKey.getClass().getMethod("location");
         player.sendSystemMessage(
            Component.translatable(
               "text.yigd.message.grave_location", new Object[]{gravePos.getX(), gravePos.getY(), gravePos.getZ(), location.invoke(worldKey).toString()}
            )
         );
      }
   }

   private static boolean shouldInformGraveLocation() {
      try {
         Class<?> configClass = Class.forName("com.b1n_ry.yigd.config.YigdConfig");
         Object config = configClass.getMethod("getConfig").invoke(null);
         Object graveConfig = config.getClass().getField("graveConfig").get(config);
         return graveConfig.getClass().getField("informGraveLocation").getBoolean(graveConfig);
      } catch (ClassNotFoundException e) {
         yigdPresent = false;
         return false;
      } catch (Exception e) {
         return true;
      }
   }

   private record GravePlacement(Object graveComponent, Object respawnComponent) {
   }
}
