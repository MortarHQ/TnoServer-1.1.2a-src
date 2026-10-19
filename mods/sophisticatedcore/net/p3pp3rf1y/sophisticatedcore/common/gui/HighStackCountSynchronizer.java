package net.p3pp3rf1y.sophisticatedcore.common.gui;

import net.minecraft.core.NonNullList;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerSynchronizer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.p3pp3rf1y.sophisticatedcore.network.SyncContainerStacksPayload;
import net.p3pp3rf1y.sophisticatedcore.network.SyncSlotStackPayload;

public class HighStackCountSynchronizer implements ContainerSynchronizer {
   private final ServerPlayer player;

   public HighStackCountSynchronizer(ServerPlayer player) {
      this.player = player;
   }

   public void sendInitialData(AbstractContainerMenu containerMenu, NonNullList<ItemStack> stacks, ItemStack carriedStack, int[] dataSlots) {
      PacketDistributor.sendToPlayer(
         this.player,
         new SyncContainerStacksPayload(containerMenu.containerId, containerMenu.incrementStateId(), stacks, carriedStack),
         new CustomPacketPayload[0]
      );
   }

   public void sendSlotChange(AbstractContainerMenu containerMenu, int slotInd, ItemStack stack) {
      PacketDistributor.sendToPlayer(
         this.player, new SyncSlotStackPayload(containerMenu.containerId, containerMenu.incrementStateId(), slotInd, stack), new CustomPacketPayload[0]
      );
   }

   public void sendCarriedChange(AbstractContainerMenu containerMenu, ItemStack stack) {
      this.player.connection.send(new ClientboundContainerSetSlotPacket(-1, containerMenu.incrementStateId(), -1, stack));
   }

   public void sendDataChange(AbstractContainerMenu containerMenu, int slotInd, int data) {
   }
}
