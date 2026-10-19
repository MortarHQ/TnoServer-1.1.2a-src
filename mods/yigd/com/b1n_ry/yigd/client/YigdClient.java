package com.b1n_ry.yigd.client;

import com.b1n_ry.yigd.Yigd;
import com.b1n_ry.yigd.client.render.GraveBlockEntityRenderer;
import com.b1n_ry.yigd.config.GraveConfig;
import com.b1n_ry.yigd.config.YigdConfig;
import com.b1n_ry.yigd.events.YigdClientEventHandler;
import com.b1n_ry.yigd.networking.packets.UpdateConfigC2SPacket;
import com.b1n_ry.yigd.util.YigdResourceHandler;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingIn;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;

@Mod(value = "yigd", dist = Dist.CLIENT)
public class YigdClient {
   public YigdClient(IEventBus modBus, ModContainer modContainer) {
      modBus.addListener(this::clientModInitializer);
      modBus.addListener(YigdResourceHandler::clientResourceEvent);
      NeoForge.EVENT_BUS.register(new YigdClientEventHandler());
      modContainer.registerExtensionPoint(
         IConfigScreenFactory.class, (IConfigScreenFactory)(container, screen) -> (Screen)AutoConfig.getConfigScreen(YigdConfig.class, screen).get()
      );
      NeoForge.EVENT_BUS.addListener(LoggingIn.class, event -> {
         GraveConfig graveConfig = YigdConfig.getConfig().graveConfig;
         PacketDistributor.sendToServer(new UpdateConfigC2SPacket(graveConfig.claimPriority, graveConfig.graveRobbing.robPriority), new CustomPacketPayload[0]);
      });
   }

   public void clientModInitializer(FMLClientSetupEvent event) {
      BlockEntityRenderers.register((BlockEntityType)Yigd.GRAVE_BLOCK_ENTITY.get(), GraveBlockEntityRenderer::new);
   }
}
