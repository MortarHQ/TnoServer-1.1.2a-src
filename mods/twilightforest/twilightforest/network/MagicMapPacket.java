package twilightforest.network;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.MapRenderer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.network.protocol.game.ClientboundMapItemDataPacket;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import twilightforest.TwilightForestMod;
import twilightforest.item.MagicMapItem;
import twilightforest.item.mapdata.TFMagicMapData;

public record MagicMapPacket(ClientboundMapItemDataPacket inner, List<String> conqueredStructures) implements CustomPacketPayload {
   public static final Type<MagicMapPacket> TYPE = new Type(TwilightForestMod.prefix("magic_map"));
   public static final StreamCodec<RegistryFriendlyByteBuf, MagicMapPacket> STREAM_CODEC = StreamCodec.composite(
      ClientboundMapItemDataPacket.STREAM_CODEC,
      MagicMapPacket::inner,
      ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()),
      MagicMapPacket::conqueredStructures,
      MagicMapPacket::new
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void handle(final MagicMapPacket message, final IPayloadContext ctx) {
      if (ctx.flow().isClientbound()) {
         ctx.enqueueWork(new Runnable() {
            @Override
            public void run() {
               Level level = ctx.player().level();
               MapRenderer mapitemrenderer = Minecraft.getInstance().gameRenderer.getMapRenderer();
               String s = MagicMapItem.getMapName(message.inner.mapId().id());
               TFMagicMapData mapdata = TFMagicMapData.getMagicMapData(level, s);
               if (mapdata == null) {
                  mapdata = new TFMagicMapData(0, 0, message.inner.scale(), false, false, message.inner.locked(), level.dimension());
                  TFMagicMapData.registerMagicMapData(level, mapdata, s);
               }

               message.inner.applyToMap(mapdata);
               mapdata.conqueredStructures.clear();
               mapdata.conqueredStructures.addAll(message.conqueredStructures());
               mapitemrenderer.update(message.inner.mapId(), mapdata);
            }
         });
      }
   }
}
