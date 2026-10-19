package team.creative.creativecore.common.network;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.apache.logging.log4j.Logger;
import team.creative.creativecore.CreativeCore;
import team.creative.creativecore.common.level.ISubLevel;

public class CreativeNetwork {
   private final HashMap<Class<? extends CreativePacket>, CreativeNetworkPacket> packetTypes = new HashMap<>();
   private final Logger logger;
   private final String modid;
   private String version;
   private PayloadRegistrar registrar;
   private int id = 0;

   public static boolean isSideOnlyPresent(Field field) {
      return field.isAnnotationPresent(OnlyIn.class);
   }

   public CreativeNetwork(int version, Logger logger, ResourceLocation location) {
      this.logger = logger;
      this.version = version + "";
      this.modid = location.getNamespace();
      this.logger.debug("Created network " + location);
      ModLoadingContext.get().getActiveContainer().getEventBus().addListener(this::register);
   }

   public void register(RegisterPayloadHandlersEvent event) {
      this.registrar = event.registrar(this.modid).versioned(this.version).optional();

      for (CreativeNetworkPacket packet : this.packetTypes.values()) {
         this.registerType(packet);
      }
   }

   private <T extends CreativePacket> void registerType(CreativeNetworkPacket<CreativePacket> handler) {
      IPayloadHandler<CreativePacket> executor = (packet, ctx) -> {
         try {
            ctx.enqueueWork(() -> packet.execute(ctx.player()));
         } catch (Throwable e) {
            CreativeCore.LOGGER.error("Executing a packet ran into an exception", e);
            CreativeCore.LOGGER.catching(e);
            throw e;
         }
      };
      this.registrar
         .playToServer(
            handler.cid, StreamCodec.ofMember((x, y) -> handler.write(x, y, PacketFlow.CLIENTBOUND), x -> handler.read(x, PacketFlow.CLIENTBOUND)), executor
         );
      this.registrar
         .playToClient(
            handler.sid, StreamCodec.ofMember((x, y) -> handler.write(x, y, PacketFlow.SERVERBOUND), x -> handler.read(x, PacketFlow.SERVERBOUND)), executor
         );
   }

   public <T extends CreativePacket> void registerType(Class<T> classType, Supplier<T> supplier) {
      CreativeNetworkPacket handler = new CreativeNetworkPacket<>(ResourceLocation.tryBuild(this.modid, this.id + ""), classType, supplier, false);
      this.packetTypes.put(classType, handler);
      if (this.registrar != null) {
         this.registerType(handler);
      }

      this.id++;
   }

   public CreativeNetworkPacket getType(Class<? extends CreativePacket> clazz) {
      return this.packetTypes.get(clazz);
   }

   protected <T extends CreativePacket> T prepare(T packet, PacketFlow flow) {
      packet.setType(flow.isClientbound() ? this.packetTypes.get(packet.getClass()).cid : this.packetTypes.get(packet.getClass()).sid);
      return packet;
   }

   public void sendToServer(CreativePacket message) {
      PacketDistributor.sendToServer(this.prepare(message, PacketFlow.CLIENTBOUND), new CustomPacketPayload[0]);
   }

   public void sendToClient(CreativePacket message, ServerPlayer player) {
      PacketDistributor.sendToPlayer(player, this.prepare(message, PacketFlow.SERVERBOUND), new CustomPacketPayload[0]);
   }

   public void sendToClient(CreativePacket message, Level level, BlockPos pos) {
      if (level instanceof ISubLevel) {
         this.sendToClientTracking(message, ((ISubLevel)level).getHolder());
      } else {
         this.sendToClient(message, level.getChunkAt(pos));
      }
   }

   public void sendToClient(CreativePacket message, LevelChunk chunk) {
      PacketDistributor.sendToPlayersTrackingChunk(
         (ServerLevel)chunk.getLevel(), chunk.getPos(), this.prepare(message, PacketFlow.SERVERBOUND), new CustomPacketPayload[0]
      );
   }

   public void sendToClientTracking(CreativePacket message, Entity entity) {
      if (entity.level() instanceof ISubLevel sub) {
         this.sendToClientTracking(message, sub.getHolder());
      } else {
         PacketDistributor.sendToPlayersTrackingEntity(entity, this.prepare(message, PacketFlow.SERVERBOUND), new CustomPacketPayload[0]);
      }
   }

   public void sendToClientTrackingAndSelf(CreativePacket message, Entity entity) {
      if (entity.level() instanceof ISubLevel sub) {
         this.sendToClientTrackingAndSelf(message, sub.getHolder());
      } else {
         PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, this.prepare(message, PacketFlow.SERVERBOUND), new CustomPacketPayload[0]);
      }
   }

   public void sendToClientAll(MinecraftServer server, CreativePacket message) {
      PacketDistributor.sendToAllPlayers(this.prepare(message, PacketFlow.SERVERBOUND), new CustomPacketPayload[0]);
   }
}
