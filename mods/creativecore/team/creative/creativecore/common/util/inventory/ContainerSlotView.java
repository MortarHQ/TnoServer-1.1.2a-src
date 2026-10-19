package team.creative.creativecore.common.util.inventory;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import team.creative.creativecore.CreativeCore;
import team.creative.creativecore.common.gui.packet.ImmediateItemStackPacket;

public class ContainerSlotView {
   public static final ContainerSlotView EMPTY = new ContainerSlotView(null, null, 0) {
      @Override
      public ItemStack get() {
         return ItemStack.EMPTY;
      }

      @Override
      public void set(ItemStack stack) {
      }

      @Override
      public void changed() {
      }
   };
   public final Player player;
   public final Container container;
   public final int index;

   public static ContainerSlotView mainHand(Player player) {
      return new ContainerSlotView(player, player.getInventory(), player.getInventory().selected);
   }

   public static ContainerSlotView offHand(Player player) {
      return new ContainerSlotView(player, player.getInventory(), 40);
   }

   public ContainerSlotView(@Nullable Player player, Container container, int index) {
      this.player = player;
      this.container = container;
      this.index = index;
   }

   public ItemStack get() {
      return this.container.getItem(this.index);
   }

   public void set(ItemStack stack) {
      this.container.setItem(this.index, stack);
      this.changed();
   }

   public void changed() {
      this.container.setChanged();
      if (this.player != null && this.player instanceof ServerPlayer s) {
         CreativeCore.NETWORK.sendToClient(new ImmediateItemStackPacket(this), s);
      }
   }
}
