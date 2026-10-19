package team.creative.creativecore.common.network;

import java.lang.reflect.Field;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import team.creative.creativecore.CreativeCore;
import team.creative.creativecore.common.network.type.NetworkFieldType;
import team.creative.creativecore.common.network.type.NetworkFieldTypes;

public class CreativeNetworkField {
   public final Field field;
   public boolean nullable;
   public final NetworkFieldType type;

   public CreativeNetworkField(Field field, NetworkFieldType type) {
      this.field = field;
      this.nullable = field.isAnnotationPresent(CanBeNull.class);
      this.type = type;
   }

   public void write(CreativePacket packet, RegistryFriendlyByteBuf buffer, PacketFlow flow) {
      try {
         Object content = this.field.get(packet);
         if (this.nullable) {
            buffer.writeBoolean(content != null);
         }

         if (content != null) {
            this.type.write(content, this.field.getType(), this.field.getGenericType(), buffer, flow);
         }
      } catch (IllegalArgumentException | IllegalAccessException e) {
         CreativeCore.LOGGER.error(e);
      }
   }

   public void read(CreativePacket packet, RegistryFriendlyByteBuf buffer, PacketFlow flow) {
      try {
         Object content;
         if (this.nullable && !buffer.readBoolean()) {
            content = null;
         } else {
            content = this.type.read(this.field.getType(), this.field.getGenericType(), buffer, flow);
         }

         this.field.set(packet, content);
      } catch (IllegalArgumentException | IllegalAccessException e) {
         CreativeCore.LOGGER.error(e);
      }
   }

   public static CreativeNetworkField create(Field field) {
      NetworkFieldType parser = NetworkFieldTypes.get(field);
      return parser != null ? new CreativeNetworkField(field, parser) : null;
   }
}
