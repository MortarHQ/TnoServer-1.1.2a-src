package cc.sighs.oelib.network.api;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public final class NetworkPacketTypes {
   private static final Map<Class<?>, Type<?>> CACHE = new ConcurrentHashMap<>();
   private static final Map<ResourceLocation, Class<?>> REVERSE = new ConcurrentHashMap<>();

   private NetworkPacketTypes() {
   }

   public static <T extends INetworkPacket<T> & CustomPacketPayload> Type<T> typeOf(Class<T> packetClass) {
      Type<T> type = (Type<T>)CACHE.computeIfAbsent(packetClass, cls -> {
         NetworkPacket meta = packetClass.getAnnotation(NetworkPacket.class);
         if (meta == null) {
            throw new IllegalArgumentException("Packet class " + packetClass.getName() + " is missing @NetworkPacket");
         } else {
            String modId = meta.modId();
            String id = meta.id();
            if (modId != null && !modId.isEmpty() && id != null && !id.isEmpty()) {
               ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(modId, id);
               return new Type(resourceLocation);
            } else {
               throw new IllegalArgumentException("Packet class " + packetClass.getName() + " has empty NetworkPacket.modId or id");
            }
         }
      });
      REVERSE.putIfAbsent(type.id(), packetClass);
      return type;
   }

   public static Class<?> classOf(ResourceLocation id) {
      return REVERSE.get(id);
   }
}
