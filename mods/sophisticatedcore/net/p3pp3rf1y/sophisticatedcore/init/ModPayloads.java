package net.p3pp3rf1y.sophisticatedcore.init;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.p3pp3rf1y.sophisticatedcore.network.SyncAdditionalSlotInfoPayload;
import net.p3pp3rf1y.sophisticatedcore.network.SyncBlockHighlightsPayload;
import net.p3pp3rf1y.sophisticatedcore.network.SyncContainerClientDataPayload;
import net.p3pp3rf1y.sophisticatedcore.network.SyncContainerStacksPayload;
import net.p3pp3rf1y.sophisticatedcore.network.SyncDatapackSettingsTemplatePayload;
import net.p3pp3rf1y.sophisticatedcore.network.SyncEmptySlotIconsPayload;
import net.p3pp3rf1y.sophisticatedcore.network.SyncPlayerSettingsPayload;
import net.p3pp3rf1y.sophisticatedcore.network.SyncSlotChangeErrorPayload;
import net.p3pp3rf1y.sophisticatedcore.network.SyncSlotStackPayload;
import net.p3pp3rf1y.sophisticatedcore.network.SyncTemplateSettingsPayload;
import net.p3pp3rf1y.sophisticatedcore.network.TransferFullSlotPayload;
import net.p3pp3rf1y.sophisticatedcore.network.TransferItemsPayload;
import net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox.PlayDiscPayload;
import net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox.StopDiscPlaybackPayload;
import net.p3pp3rf1y.sophisticatedcore.upgrades.tank.TankClickPayload;

public class ModPayloads {
   private ModPayloads() {
   }

   public static void registerPayloads(RegisterPayloadHandlersEvent event) {
      PayloadRegistrar registrar = event.registrar("sophisticatedcore").versioned("1.0");
      registrar.playToServer(SyncContainerClientDataPayload.TYPE, SyncContainerClientDataPayload.STREAM_CODEC, SyncContainerClientDataPayload::handlePayload);
      registrar.playToServer(TransferFullSlotPayload.TYPE, TransferFullSlotPayload.STREAM_CODEC, TransferFullSlotPayload::handlePayload);
      registrar.playToClient(SyncContainerStacksPayload.TYPE, SyncContainerStacksPayload.STREAM_CODEC, SyncContainerStacksPayload::handlePayload);
      registrar.playToClient(SyncSlotStackPayload.TYPE, SyncSlotStackPayload.STREAM_CODEC, SyncSlotStackPayload::handlePayload);
      registrar.playToClient(SyncPlayerSettingsPayload.TYPE, SyncPlayerSettingsPayload.STREAM_CODEC, SyncPlayerSettingsPayload::handlePayload);
      registrar.playToClient(PlayDiscPayload.TYPE, PlayDiscPayload.STREAM_CODEC, PlayDiscPayload::handlePayload);
      registrar.playToClient(StopDiscPlaybackPayload.TYPE, StopDiscPlaybackPayload.STREAM_CODEC, StopDiscPlaybackPayload::handlePayload);
      registrar.playToServer(TankClickPayload.TYPE, TankClickPayload.STREAM_CODEC, TankClickPayload::handlePayload);
      registrar.playToServer(TransferItemsPayload.TYPE, TransferItemsPayload.STREAM_CODEC, TransferItemsPayload::handlePayload);
      registrar.playToClient(SyncTemplateSettingsPayload.TYPE, SyncTemplateSettingsPayload.STREAM_CODEC, SyncTemplateSettingsPayload::handlePayload);
      registrar.playToClient(SyncAdditionalSlotInfoPayload.TYPE, SyncAdditionalSlotInfoPayload.STREAM_CODEC, SyncAdditionalSlotInfoPayload::handlePayload);
      registrar.playToClient(SyncEmptySlotIconsPayload.TYPE, SyncEmptySlotIconsPayload.STREAM_CODEC, SyncEmptySlotIconsPayload::handlePayload);
      registrar.playToClient(SyncSlotChangeErrorPayload.TYPE, SyncSlotChangeErrorPayload.STREAM_CODEC, SyncSlotChangeErrorPayload::handlePayload);
      registrar.playToClient(
         SyncDatapackSettingsTemplatePayload.TYPE, SyncDatapackSettingsTemplatePayload.STREAM_CODEC, SyncDatapackSettingsTemplatePayload::handlePayload
      );
      registrar.playToClient(SyncBlockHighlightsPayload.TYPE, SyncBlockHighlightsPayload.STREAM_CODEC, SyncBlockHighlightsPayload::handlePayload);
   }
}
