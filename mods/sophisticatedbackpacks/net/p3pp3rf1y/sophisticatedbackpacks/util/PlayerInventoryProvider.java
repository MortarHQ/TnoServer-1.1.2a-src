package net.p3pp3rf1y.sophisticatedbackpacks.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;

public class PlayerInventoryProvider {
   public static final String MAIN_INVENTORY = "main";
   public static final String OFFHAND_INVENTORY = "offhand";
   public static final String ARMOR_INVENTORY = "armor";
   private final Map<String, PlayerInventoryHandler> playerInventoryHandlers = new LinkedHashMap<>();
   private final List<String> renderedHandlers = new ArrayList<>();
   private static final PlayerInventoryProvider serverProvider = new PlayerInventoryProvider();
   private static final PlayerInventoryProvider clientProvider = new PlayerInventoryProvider();

   public static PlayerInventoryProvider get() {
      return FMLEnvironment.dist == Dist.CLIENT ? clientProvider : serverProvider;
   }

   private PlayerInventoryProvider() {
      this.addPlayerInventoryHandler(
         "main",
         player -> PlayerInventoryHandler.SINGLE_IDENTIFIER,
         (player, identifier) -> player.getInventory().items.size(),
         (player, identifier, slot) -> (ItemStack)player.getInventory().items.get(slot),
         true,
         false,
         false,
         false
      );
      this.addPlayerInventoryHandler(
         "offhand",
         player -> PlayerInventoryHandler.SINGLE_IDENTIFIER,
         (player, identifier) -> player.getInventory().offhand.size(),
         (player, identifier, slot) -> (ItemStack)player.getInventory().offhand.get(slot),
         false,
         false,
         false,
         false
      );
      this.addPlayerInventoryHandler(
         "armor",
         player -> PlayerInventoryHandler.SINGLE_IDENTIFIER,
         (player, identifier) -> 1,
         (player, identifier, slot) -> (ItemStack)player.getInventory().armor.get(EquipmentSlot.CHEST.getIndex()),
         false,
         true,
         false,
         true
      );
   }

   public void addPlayerInventoryHandler(
      String name,
      Function<Player, Set<String>> identifiersGetter,
      PlayerInventoryHandler.SlotCountGetter slotCountGetter,
      PlayerInventoryHandler.SlotStackGetter slotStackGetter,
      boolean visibleInGui,
      boolean rendered,
      boolean ownRenderer,
      boolean accessibleByAnotherPlayer
   ) {
      Map<String, PlayerInventoryHandler> temp = new LinkedHashMap<>(this.playerInventoryHandlers);
      this.playerInventoryHandlers.clear();
      this.playerInventoryHandlers
         .put(name, new PlayerInventoryHandler(identifiersGetter, slotCountGetter, slotStackGetter, visibleInGui, ownRenderer, accessibleByAnotherPlayer));
      this.playerInventoryHandlers.putAll(temp);
      if (rendered) {
         ArrayList<String> tempRendered = new ArrayList<>(this.renderedHandlers);
         this.renderedHandlers.clear();
         this.renderedHandlers.add(name);
         this.renderedHandlers.addAll(tempRendered);
      }
   }

   public Optional<PlayerInventoryProvider.RenderInfo> getBackpackFromRendered(Player player) {
      for (String handlerName : this.renderedHandlers) {
         PlayerInventoryHandler invHandler = this.playerInventoryHandlers.get(handlerName);
         if (invHandler == null) {
            return Optional.empty();
         }

         for (String identifier : invHandler.getIdentifiers(player)) {
            for (int slot = 0; slot < invHandler.getSlotCount(player, identifier); slot++) {
               ItemStack slotStack = invHandler.getStackInSlot(player, identifier, slot);
               if (slotStack.getItem() instanceof BackpackItem) {
                  return invHandler.hasItsOwnRenderer()
                     ? Optional.empty()
                     : Optional.of(new PlayerInventoryProvider.RenderInfo(slotStack, handlerName.equals("armor")));
               }
            }
         }
      }

      return Optional.empty();
   }

   private Map<String, PlayerInventoryHandler> getPlayerInventoryHandlers() {
      return this.playerInventoryHandlers;
   }

   public Optional<PlayerInventoryHandler> getPlayerInventoryHandler(String name) {
      return Optional.ofNullable(this.getPlayerInventoryHandlers().get(name));
   }

   public void runOnBackpacks(Player player, PlayerInventoryProvider.BackpackInventorySlotConsumer backpackInventorySlotConsumer) {
      this.runOnBackpacks(player, backpackInventorySlotConsumer, false);
   }

   public void runOnBackpacks(
      Player player, PlayerInventoryProvider.BackpackInventorySlotConsumer backpackInventorySlotConsumer, boolean onlyAccessibleByAnotherPlayer
   ) {
      for (Entry<String, PlayerInventoryHandler> entry : this.getPlayerInventoryHandlers().entrySet()) {
         PlayerInventoryHandler invHandler = entry.getValue();
         if (!onlyAccessibleByAnotherPlayer || invHandler.isAccessibleByAnotherPlayer()) {
            for (String identifier : invHandler.getIdentifiers(player)) {
               for (int slot = 0; slot < invHandler.getSlotCount(player, identifier); slot++) {
                  ItemStack slotStack = invHandler.getStackInSlot(player, identifier, slot);
                  if (slotStack.getItem() instanceof BackpackItem && backpackInventorySlotConsumer.accept(slotStack, entry.getKey(), identifier, slot)) {
                     return;
                  }
               }
            }
         }
      }
   }

   public interface BackpackInventorySlotConsumer {
      boolean accept(ItemStack var1, String var2, String var3, int var4);
   }

   public static class RenderInfo {
      private final ItemStack backpack;
      private final boolean isArmorSlot;

      public RenderInfo(ItemStack backpack, boolean isArmorSlot) {
         this.backpack = backpack;
         this.isArmorSlot = isArmorSlot;
      }

      public ItemStack getBackpack() {
         return this.backpack;
      }

      public boolean isArmorSlot() {
         return this.isArmorSlot;
      }
   }
}
