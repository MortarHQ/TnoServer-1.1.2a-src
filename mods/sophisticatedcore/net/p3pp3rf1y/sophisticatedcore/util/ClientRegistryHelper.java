package net.p3pp3rf1y.sophisticatedcore.util;

import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.RegistryAccess;

public class ClientRegistryHelper {
   public static Optional<RegistryAccess> getRegistryAccess() {
      ClientLevel level = Minecraft.getInstance().level;
      return level == null ? Optional.empty() : Optional.of(level.registryAccess());
   }
}
