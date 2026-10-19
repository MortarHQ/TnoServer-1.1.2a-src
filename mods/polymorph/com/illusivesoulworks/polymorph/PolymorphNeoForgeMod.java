package com.illusivesoulworks.polymorph;

import com.illusivesoulworks.polymorph.client.ClientEventsListener;
import com.illusivesoulworks.polymorph.common.CommonEventsListener;
import com.illusivesoulworks.polymorph.common.PolymorphNeoForgeCapabilities;
import com.illusivesoulworks.polymorph.common.network.ClientPayloadHandler;
import com.illusivesoulworks.polymorph.common.network.ServerPayloadHandler;
import com.illusivesoulworks.polymorph.common.network.client.CPacketBlockEntityListener;
import com.illusivesoulworks.polymorph.common.network.client.CPacketPersistentRecipeSelection;
import com.illusivesoulworks.polymorph.common.network.client.CPacketPlayerRecipeSelection;
import com.illusivesoulworks.polymorph.common.network.server.SPacketHighlightRecipe;
import com.illusivesoulworks.polymorph.common.network.server.SPacketPlayerRecipeSync;
import com.illusivesoulworks.polymorph.common.network.server.SPacketRecipeHandshake;
import com.illusivesoulworks.polymorph.common.network.server.SPacketRecipesList;
import com.illusivesoulworks.polymorph.common.network.server.SPacketUpdatePreview;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod("polymorph")
public class PolymorphNeoForgeMod {
   public PolymorphNeoForgeMod(IEventBus eventBus) {
      PolymorphCommonMod.init();
      PolymorphNeoForgeCapabilities.setup(eventBus);
      eventBus.addListener(this::setup);
      eventBus.addListener(this::clientSetup);
      eventBus.addListener(this::registerPayload);
   }

   private void setup(FMLCommonSetupEvent evt) {
      PolymorphCommonMod.setup();
      NeoForge.EVENT_BUS.register(new CommonEventsListener());
   }

   private void clientSetup(FMLClientSetupEvent evt) {
      PolymorphCommonMod.clientSetup();
      NeoForge.EVENT_BUS.register(new ClientEventsListener());
   }

   private void registerPayload(RegisterPayloadHandlersEvent evt) {
      PayloadRegistrar registrar = evt.registrar("polymorph");
      registrar.playToClient(SPacketRecipesList.TYPE, SPacketRecipesList.STREAM_CODEC, ClientPayloadHandler.getInstance()::handlePacket);
      registrar.playToClient(SPacketHighlightRecipe.TYPE, SPacketHighlightRecipe.STREAM_CODEC, ClientPayloadHandler.getInstance()::handlePacket);
      registrar.playToClient(SPacketPlayerRecipeSync.TYPE, SPacketPlayerRecipeSync.STREAM_CODEC, ClientPayloadHandler.getInstance()::handlePacket);
      registrar.playToClient(SPacketUpdatePreview.TYPE, SPacketUpdatePreview.STREAM_CODEC, ClientPayloadHandler.getInstance()::handlePacket);
      registrar.playToClient(SPacketRecipeHandshake.TYPE, SPacketRecipeHandshake.STREAM_CODEC, ClientPayloadHandler.getInstance()::handlePacket);
      registrar.playToServer(
         CPacketPersistentRecipeSelection.TYPE, CPacketPersistentRecipeSelection.STREAM_CODEC, ServerPayloadHandler.getInstance()::handlePacket
      );
      registrar.playToServer(CPacketPlayerRecipeSelection.TYPE, CPacketPlayerRecipeSelection.STREAM_CODEC, ServerPayloadHandler.getInstance()::handlePacket);
      registrar.playToServer(CPacketBlockEntityListener.TYPE, CPacketBlockEntityListener.STREAM_CODEC, ServerPayloadHandler.getInstance()::handlePacket);
   }
}
