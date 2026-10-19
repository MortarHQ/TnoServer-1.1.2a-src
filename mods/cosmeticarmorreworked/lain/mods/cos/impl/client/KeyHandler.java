package lain.mods.cos.impl.client;

import com.mojang.blaze3d.platform.InputConstants;
import java.util.function.Consumer;
import lain.mods.cos.impl.client.gui.GuiCosArmorInventory;
import lain.mods.cos.impl.network.payload.PayloadOpenCosArmorInventory;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.client.event.ClientTickEvent.Pre;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;

public enum KeyHandler {
   INSTANCE;

   private final Minecraft mc = Minecraft.getInstance();
   public KeyMapping keyOpenCosArmorInventory = new KeyMapping("cos.key.opencosarmorinventory", InputConstants.UNKNOWN.getValue(), "key.categories.inventory");

   private void handleClientTick(Pre event) {
      if (this.mc.isWindowActive()) {
         if (this.keyOpenCosArmorInventory.consumeClick() && !(this.mc.screen instanceof GuiCosArmorInventory)) {
            PacketDistributor.sendToServer(new PayloadOpenCosArmorInventory(), new CustomPacketPayload[0]);
         }
      }
   }

   public void registerEvents() {
      NeoForge.EVENT_BUS.addListener(this::handleClientTick);
   }

   public void registerKeyMappings(Consumer<KeyMapping> register) {
      register.accept(this.keyOpenCosArmorInventory);
   }
}
