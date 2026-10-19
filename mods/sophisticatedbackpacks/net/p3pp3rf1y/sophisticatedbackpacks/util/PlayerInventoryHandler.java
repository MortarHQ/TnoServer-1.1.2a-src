package net.p3pp3rf1y.sophisticatedbackpacks.util;

import java.util.Collections;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class PlayerInventoryHandler {
   public static final Set<String> SINGLE_IDENTIFIER = Collections.singleton("");
   private final Function<Player, Set<String>> identifiersGetter;
   private final PlayerInventoryHandler.SlotCountGetter slotCountGetter;
   private final PlayerInventoryHandler.SlotStackGetter slotStackGetter;
   private final boolean visibleInGui;
   private final boolean ownRenderer;
   private final boolean accessibleByAnotherPlayer;

   public PlayerInventoryHandler(
      Function<Player, Set<String>> identifiersGetter,
      PlayerInventoryHandler.SlotCountGetter slotCountGetter,
      PlayerInventoryHandler.SlotStackGetter slotStackGetter,
      boolean visibleInGui,
      boolean ownRenderer,
      boolean accessibleByAnotherPlayer
   ) {
      this.identifiersGetter = identifiersGetter;
      this.slotCountGetter = slotCountGetter;
      this.slotStackGetter = slotStackGetter;
      this.visibleInGui = visibleInGui;
      this.ownRenderer = ownRenderer;
      this.accessibleByAnotherPlayer = accessibleByAnotherPlayer;
   }

   public int getSlotCount(Player player, String identifier) {
      return this.slotCountGetter.getSlotCount(player, identifier);
   }

   public ItemStack getStackInSlot(Player player, String identifier, int slot) {
      return this.slotStackGetter.getStackInSlot(player, identifier, slot);
   }

   public boolean isVisibleInGui() {
      return this.visibleInGui;
   }

   public Set<String> getIdentifiers(Player player) {
      return this.identifiersGetter.apply(player);
   }

   public boolean hasItsOwnRenderer() {
      return this.ownRenderer;
   }

   public boolean isAccessibleByAnotherPlayer() {
      return this.accessibleByAnotherPlayer;
   }

   public interface SlotCountGetter {
      int getSlotCount(Player var1, String var2);
   }

   public interface SlotStackGetter {
      ItemStack getStackInSlot(Player var1, String var2, int var3);
   }
}
