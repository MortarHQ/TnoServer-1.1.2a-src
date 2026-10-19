package team.creative.creativecore.common.network;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public class CreativeNetworkPacket<T extends CreativePacket> {
   public final Type<T> sid;
   public final Type<T> cid;
   public final Class<T> classType;
   public final Supplier<T> supplier;
   public List<CreativeNetworkField> parsers = new ArrayList<>();
   public final boolean fabric;

   public CreativeNetworkPacket(ResourceLocation id, Class<T> classType, Supplier<T> supplier, boolean fabric) {
      this.sid = new Type(ResourceLocation.tryBuild(id.getNamespace(), id.getPath() + "s"));
      this.cid = new Type(ResourceLocation.tryBuild(id.getNamespace(), id.getPath() + "c"));
      this.classType = classType;
      this.supplier = supplier;
      this.fabric = fabric;

      for (Field field : this.classType.getFields()) {
         if (!Modifier.isTransient(field.getModifiers()) && !CreativeNetwork.isSideOnlyPresent(field)) {
            CreativeNetworkField parser = CreativeNetworkField.create(field);
            if (parser == null) {
               throw new RuntimeException("Could not find parser for " + classType.getName() + "." + field.getName() + "! type: " + field.getType().getName());
            }

            this.parsers.add(parser);
         }
      }
   }

   public void write(T packet, RegistryFriendlyByteBuf buffer, PacketFlow flow) {
      for (CreativeNetworkField parser : this.parsers) {
         parser.write(packet, buffer, flow);
      }
   }

   public T read(RegistryFriendlyByteBuf buffer, PacketFlow flow) {
      T message = this.supplier.get();

      for (CreativeNetworkField parser : this.parsers) {
         parser.read(message, buffer, flow);
      }

      message.setType(!this.fabric && flow == PacketFlow.CLIENTBOUND ? this.cid : this.sid);
      return message;
   }
}
