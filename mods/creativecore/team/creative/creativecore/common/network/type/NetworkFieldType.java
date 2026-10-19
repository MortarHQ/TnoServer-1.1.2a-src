package team.creative.creativecore.common.network.type;

import java.lang.reflect.Type;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import org.jetbrains.annotations.Nullable;

public abstract class NetworkFieldType<T> {
   public abstract void write(T var1, Class var2, @Nullable Type var3, RegistryFriendlyByteBuf var4, PacketFlow var5);

   public abstract T read(Class var1, @Nullable Type var2, RegistryFriendlyByteBuf var3, PacketFlow var4);
}
