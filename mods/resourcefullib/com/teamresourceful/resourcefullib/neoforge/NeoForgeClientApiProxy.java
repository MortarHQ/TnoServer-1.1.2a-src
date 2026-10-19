package com.teamresourceful.resourcefullib.neoforge;

import com.teamresourceful.resourcefullib.common.ApiProxy;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;

public final class NeoForgeClientApiProxy implements ApiProxy {
   public static final NeoForgeClientApiProxy INSTANCE = new NeoForgeClientApiProxy();

   @Override
   public RegistryAccess getRegistryAccess() {
      Objects.requireNonNull(Minecraft.getInstance().getConnection(), "Client is not connected to a server");
      return Minecraft.getInstance().getConnection().registryAccess();
   }
}
