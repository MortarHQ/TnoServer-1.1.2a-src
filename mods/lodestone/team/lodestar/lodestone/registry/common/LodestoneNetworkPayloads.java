package team.lodestar.lodestone.registry.common;

import java.util.HashMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamMemberEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import team.lodestar.lodestone.network.ScreenshakePayload;
import team.lodestar.lodestone.network.TotemOfUndyingPayload;
import team.lodestar.lodestone.network.worldevent.SyncWorldEventPayload;
import team.lodestar.lodestone.network.worldevent.UpdateWorldEventPayload;
import team.lodestar.lodestone.systems.network.LodestoneNetworkPayloadData;
import team.lodestar.lodestone.systems.network.OneSidedPayloadData;
import team.lodestar.lodestone.systems.network.TwoSidedPayloadData;
import team.lodestar.lodestone.systems.network.particle.NetworkedParticleEffectPayload;

@EventBusSubscriber(bus = Bus.MOD)
public class LodestoneNetworkPayloads {
   public static final LodestoneNetworkPayloads.PayloadRegistryHelper LODESTONE_CHANNEL = new LodestoneNetworkPayloads.PayloadRegistryHelper("lodestone");

   @SubscribeEvent
   public static void register(RegisterPayloadHandlersEvent event) {
      PayloadRegistrar registrar = event.registrar("1");
      LODESTONE_CHANNEL.playToClient(registrar, "totem_of_undying", TotemOfUndyingPayload.class, TotemOfUndyingPayload::new);
      LODESTONE_CHANNEL.playToClient(registrar, "sync_world_event", SyncWorldEventPayload.class, SyncWorldEventPayload::new);
      LODESTONE_CHANNEL.playToClient(registrar, "update_world_event", UpdateWorldEventPayload.class, UpdateWorldEventPayload::new);
      LODESTONE_CHANNEL.playToClient(registrar, "screenshake", ScreenshakePayload.class, ScreenshakePayload::new);
      LODESTONE_CHANNEL.playToClient(registrar, "particle_effect", NetworkedParticleEffectPayload.class, NetworkedParticleEffectPayload::new);
   }

   public interface PayloadDataSupplier<T extends LodestoneNetworkPayloadData> {
      T createPayload(FriendlyByteBuf var1);
   }

   public static class PayloadRegistryHelper {
      public static final HashMap<Class<? extends LodestoneNetworkPayloadData>, Type<? extends LodestoneNetworkPayloadData>> PAYLOAD_TO_TYPE = new HashMap<>();
      public final String namespace;

      public PayloadRegistryHelper(String namespace) {
         this.namespace = namespace;
      }

      public <T extends OneSidedPayloadData> void playToClient(
         PayloadRegistrar registrar, String name, Class<T> clazz, LodestoneNetworkPayloads.PayloadDataSupplier<T> decoder
      ) {
         Type<T> type = this.createPayloadType(clazz, name);
         registrar.playToClient(type, this.createCodec(decoder), OneSidedPayloadData::handle);
      }

      public <T extends OneSidedPayloadData> void playToServer(
         PayloadRegistrar registrar, String name, Class<T> clazz, LodestoneNetworkPayloads.PayloadDataSupplier<T> decoder
      ) {
         Type<T> type = this.createPayloadType(clazz, name);
         registrar.playToServer(type, this.createCodec(decoder), OneSidedPayloadData::handle);
      }

      public <T extends TwoSidedPayloadData> void playBidirectional(
         PayloadRegistrar registrar, String name, Class<T> clazz, LodestoneNetworkPayloads.PayloadDataSupplier<T> decoder
      ) {
         Type<T> type = this.createPayloadType(clazz, name);
         registrar.playBidirectional(
            type, this.createCodec(decoder), new DirectionalPayloadHandler(TwoSidedPayloadData::handleClient, TwoSidedPayloadData::handleServer)
         );
      }

      public <T extends LodestoneNetworkPayloadData> StreamCodec<FriendlyByteBuf, T> createCodec(LodestoneNetworkPayloads.PayloadDataSupplier<T> supplier) {
         return StreamCodec.ofMember(this.encodePacket(), this.decodePacket(supplier));
      }

      public final <B extends FriendlyByteBuf, T extends LodestoneNetworkPayloadData> StreamMemberEncoder<B, T> encodePacket() {
         return LodestoneNetworkPayloadData::serialize;
      }

      public final <B extends FriendlyByteBuf, T extends LodestoneNetworkPayloadData> StreamDecoder<B, T> decodePacket(
         LodestoneNetworkPayloads.PayloadDataSupplier<T> supplier
      ) {
         return byteBuf -> {
            try {
               return supplier.createPayload(byteBuf);
            } catch (Exception e) {
               throw new RuntimeException("Couldn't decode payload type from channel " + this.namespace, e);
            }
         };
      }

      public <T extends LodestoneNetworkPayloadData> Type<T> createPayloadType(Class<T> clazz, String id) {
         Type<T> type = new Type(ResourceLocation.fromNamespaceAndPath(this.namespace, id));
         PAYLOAD_TO_TYPE.put(clazz, type);
         return type;
      }
   }
}
