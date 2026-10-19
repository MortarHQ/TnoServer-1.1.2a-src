package com.tensura_guild.network;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class GuildNetwork {
   public static void onRegister(RegisterPayloadHandlersEvent event) {
      PayloadRegistrar registrar = event.registrar("1");
      registrar.playToClient(OpenDialoguePayload.TYPE, OpenDialoguePayload.STREAM_CODEC, OpenDialoguePayload::handle);
      registrar.playToClient(OpenGuildTaskContentPayload.TYPE, OpenGuildTaskContentPayload.STREAM_CODEC, OpenGuildTaskContentPayload::handle);
      registrar.playToClient(OpenDwarfTaskDialoguePayload.TYPE, OpenDwarfTaskDialoguePayload.STREAM_CODEC, OpenDwarfTaskDialoguePayload::handle);
      registrar.playToClient(AcceptGuildTaskResultPayload.TYPE, AcceptGuildTaskResultPayload.STREAM_CODEC, AcceptGuildTaskResultPayload::handle);
      registrar.playToClient(GuildPartyStatePayload.TYPE, GuildPartyStatePayload.STREAM_CODEC, GuildPartyStatePayload::handle);
      registrar.playToClient(GuildShopLimitReachedPayload.TYPE, GuildShopLimitReachedPayload.STREAM_CODEC, GuildShopLimitReachedPayload::handle);
      registrar.playToClient(GuildShopStatePayload.TYPE, GuildShopStatePayload.STREAM_CODEC, GuildShopStatePayload::handle);
      registrar.playToClient(GuildShopRefreshPayload.TYPE, GuildShopRefreshPayload.STREAM_CODEC, GuildShopRefreshPayload::handle);
      registrar.playToServer(CompleteDialoguePayload.TYPE, CompleteDialoguePayload.STREAM_CODEC, CompleteDialoguePayload::handle);
      registrar.playToServer(CloseDialoguePayload.TYPE, CloseDialoguePayload.STREAM_CODEC, CloseDialoguePayload::handle);
      registrar.playToServer(SaveGuildSignaturePayload.TYPE, SaveGuildSignaturePayload.STREAM_CODEC, SaveGuildSignaturePayload::handle);
      registrar.playToServer(AcceptGuildTaskPayload.TYPE, AcceptGuildTaskPayload.STREAM_CODEC, AcceptGuildTaskPayload::handle);
      registrar.playToServer(CompleteGuildTaskPayload.TYPE, CompleteGuildTaskPayload.STREAM_CODEC, CompleteGuildTaskPayload::handle);
      registrar.playToServer(DismissFailedGuildTaskPayload.TYPE, DismissFailedGuildTaskPayload.STREAM_CODEC, DismissFailedGuildTaskPayload::handle);
      registrar.playToServer(BuyGuildShopItemPayload.TYPE, BuyGuildShopItemPayload.STREAM_CODEC, BuyGuildShopItemPayload::handle);
      registrar.playToServer(RequestGuildShopStatePayload.TYPE, RequestGuildShopStatePayload.STREAM_CODEC, RequestGuildShopStatePayload::handle);
      registrar.playToServer(GuildPartyActionPayload.TYPE, GuildPartyActionPayload.STREAM_CODEC, GuildPartyActionPayload::handle);
      registrar.playToServer(GuildPartyChatPayload.TYPE, GuildPartyChatPayload.STREAM_CODEC, GuildPartyChatPayload::handle);
      registrar.playToServer(SetGuildTitlePayload.TYPE, SetGuildTitlePayload.STREAM_CODEC, SetGuildTitlePayload::handle);
      registrar.playToServer(DwarfTaskActionPayload.TYPE, DwarfTaskActionPayload.STREAM_CODEC, DwarfTaskActionPayload::handle);
   }
}
