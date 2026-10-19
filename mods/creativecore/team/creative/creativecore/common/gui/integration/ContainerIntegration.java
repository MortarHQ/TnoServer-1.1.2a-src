package team.creative.creativecore.common.gui.integration;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import team.creative.creativecore.CreativeCore;
import team.creative.creativecore.common.gui.GuiLayer;
import team.creative.creativecore.common.gui.packet.LayerClosePacket;
import team.creative.creativecore.common.network.CreativePacket;

public class ContainerIntegration extends AbstractContainerMenu implements IGuiIntegratedParent {
   private List<GuiLayer> layers = new ArrayList<>();
   private final Player player;

   public ContainerIntegration(MenuType<ContainerIntegration> type, int id, Player player, GuiLayer layer) {
      super(type, id);
      this.player = player;
      layer.setParent(this);
      this.layers.add(layer);
   }

   public ContainerIntegration(MenuType<ContainerIntegration> type, int id, Player player) {
      super(type, id);
      this.player = player;
   }

   public void broadcastChanges() {
      super.broadcastChanges();
      this.tick();
   }

   public void tick() {
      for (GuiLayer layer : this.layers) {
         layer.tick();
      }
   }

   @Override
   public List<GuiLayer> getLayers() {
      return this.layers;
   }

   @Override
   public GuiLayer getTopLayer() {
      return this.layers.isEmpty() ? EMPTY : this.layers.get(this.layers.size() - 1);
   }

   @Override
   public boolean isContainer() {
      return true;
   }

   public void removed(Player playerIn) {
      super.removed(playerIn);

      for (GuiLayer layer : this.layers) {
         layer.closed();
      }
   }

   public boolean stillValid(Player playerIn) {
      return true;
   }

   @Override
   public Player getPlayer() {
      return this.player;
   }

   @Override
   public boolean isClient() {
      return this.player.level().isClientSide;
   }

   @Override
   public void openLayer(GuiLayer layer) {
      layer.setParent(this);
      this.layers.add(layer);
      layer.init();
   }

   @Override
   public void closeTopLayer() {
      int index = this.layers.size() - 1;
      if (index != -1) {
         this.closeLayer(index);
      }
   }

   @Override
   public void closeLayer(GuiLayer layer) {
      int index = this.layers.indexOf(layer);
      if (index != -1) {
         this.closeLayer(index);
      }
   }

   @Override
   public void closeLayer(int layer) {
      for (int i = layer; i < this.layers.size(); i++) {
         this.layers.get(i).closed();
      }

      this.send(new LayerClosePacket(layer));
      this.layers = this.layers.subList(0, layer);
      if (this.layers.isEmpty()) {
         if (this.isClient()) {
            Minecraft.getInstance().setScreen(null);
         } else {
            ((ServerPlayer)this.player).closeContainer();
         }
      } else {
         this.getTopLayer().becameTopLayer();
      }
   }

   @Override
   public void send(CreativePacket packet) {
      if (this.isClient()) {
         CreativeCore.NETWORK.sendToServer(packet);
      } else {
         CreativeCore.NETWORK.sendToClient(packet, (ServerPlayer)this.player);
      }
   }

   public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
      return null;
   }

   @Override
   public Provider provider() {
      return this.player.registryAccess();
   }
}
