package team.creative.creativecore.common.network.type;

import java.lang.reflect.Type;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import org.jetbrains.annotations.Nullable;

public abstract class NetworkFieldTypeClass<T> extends NetworkFieldType<T> {
   protected abstract void writeContent(T var1, RegistryFriendlyByteBuf var2);

   @Override
   public final void write(T content, Class classType, @Nullable Type genericType, RegistryFriendlyByteBuf buffer, PacketFlow flow) {
      this.writeContent(content, buffer);
   }

   protected abstract T readContent(RegistryFriendlyByteBuf var1);

   @Override
   public final T read(Class classType, @Nullable Type genericType, RegistryFriendlyByteBuf buffer, PacketFlow flow) {
      return this.readContent(buffer);
   }
}
