package com.github.L_Ender.cataclysm.message;

import com.github.L_Ender.cataclysm.ClientProxy;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public abstract class MessageBossBar implements CustomPacketPayload {
   protected final UUID bossEvent;
   protected final int rendertype;
   protected final int remainlife;

   public MessageBossBar(UUID bossEvent, int rendertype, int remainlife) {
      this.bossEvent = bossEvent;
      this.rendertype = rendertype;
      this.remainlife = remainlife;
   }

   public UUID getBossEvent() {
      return this.bossEvent;
   }

   public int getRendertype() {
      return this.rendertype;
   }

   public int getRemainlife() {
      return this.remainlife;
   }

   public static class Display extends MessageBossBar {
      public static final Type<MessageBossBar.Display> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("cataclysm", "add_custom_bossbar"));
      public static final StreamCodec<RegistryFriendlyByteBuf, MessageBossBar.Display> STREAM_CODEC = StreamCodec.composite(
         UUIDUtil.STREAM_CODEC,
         MessageBossBar::getBossEvent,
         ByteBufCodecs.INT,
         MessageBossBar::getRendertype,
         ByteBufCodecs.INT,
         MessageBossBar::getRemainlife,
         MessageBossBar.Display::new
      );

      public Display(UUID bossEvent, int rendertype, int remainlife) {
         super(bossEvent, rendertype, remainlife);
      }

      public Type<MessageBossBar.Display> type() {
         return TYPE;
      }

      public static void execute(MessageBossBar.Display payload, IPayloadContext context) {
         ClientProxy.bossBarRenderTypes.put(payload.bossEvent, new ClientProxy.BossBarData(payload.rendertype, payload.remainlife));
      }
   }

   public static class Remove extends MessageBossBar {
      public static final Type<MessageBossBar.Remove> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("cataclysm", "remove_custom_bossbar"));
      public static final StreamCodec<RegistryFriendlyByteBuf, MessageBossBar.Remove> STREAM_CODEC = StreamCodec.composite(
         UUIDUtil.STREAM_CODEC,
         MessageBossBar::getBossEvent,
         ByteBufCodecs.INT,
         MessageBossBar::getRendertype,
         ByteBufCodecs.INT,
         MessageBossBar::getRemainlife,
         MessageBossBar.Remove::new
      );

      public Remove(UUID bossEvent, int rendertype, int remainlife) {
         super(bossEvent, rendertype, remainlife);
      }

      public Type<MessageBossBar.Remove> type() {
         return TYPE;
      }

      public static void execute(MessageBossBar.Remove payload, IPayloadContext context) {
         ClientProxy.bossBarRenderTypes.remove(payload.bossEvent);
      }
   }
}
