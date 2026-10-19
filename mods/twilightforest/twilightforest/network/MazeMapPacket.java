package twilightforest.network;

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
import twilightforest.item.MazeMapItem;
import twilightforest.item.mapdata.TFMazeMapData;

public record MazeMapPacket(ClientboundMapItemDataPacket inner, boolean ore, int yCenter) implements CustomPacketPayload {
   public static final Type<MazeMapPacket> TYPE = new Type(TwilightForestMod.prefix("maze_map"));
   public static final StreamCodec<RegistryFriendlyByteBuf, MazeMapPacket> STREAM_CODEC = StreamCodec.composite(
      ClientboundMapItemDataPacket.STREAM_CODEC,
      MazeMapPacket::inner,
      ByteBufCodecs.BOOL,
      MazeMapPacket::ore,
      ByteBufCodecs.INT,
      MazeMapPacket::yCenter,
      MazeMapPacket::new
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void handle(final MazeMapPacket message, final IPayloadContext ctx) {
      if (ctx.flow().isClientbound()) {
         ctx.enqueueWork(new Runnable() {
            @Override
            public void run() {
               Level level = ctx.player().level();
               MapRenderer mapitemrenderer = Minecraft.getInstance().gameRenderer.getMapRenderer();
               String s = MazeMapItem.getMapName(message.inner().mapId().id());
               TFMazeMapData mapdata = TFMazeMapData.getMazeMapData(level, s);
               if (mapdata == null) {
                  mapdata = new TFMazeMapData(0, 0, message.inner().scale(), false, false, message.inner().locked(), level.dimension());
                  TFMazeMapData.registerMazeMapData(level, mapdata, s);
               }

               mapdata.ore = message.ore();
               mapdata.yCenter = message.yCenter();
               message.inner().applyToMap(mapdata);
               mapitemrenderer.update(message.inner().mapId(), mapdata);
            }
         });
      }
   }
}
