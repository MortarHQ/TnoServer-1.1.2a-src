package net.p3pp3rf1y.sophisticatedcore.util;

import java.util.Optional;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.util.thread.SidedThreadGroups;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.apache.commons.lang3.Validate;

public class RegistryHelper {
   private RegistryHelper() {
   }

   public static ResourceLocation getItemKey(Item item) {
      ResourceLocation itemKey = BuiltInRegistries.ITEM.getKey(item);
      Validate.notNull(itemKey, "itemKey", new Object[0]);
      return itemKey;
   }

   public static <V> Optional<ResourceLocation> getRegistryName(Registry<V> registry, V registryEntry) {
      return Optional.ofNullable(registry.getKey(registryEntry));
   }

   public static Optional<RegistryAccess> getRegistryAccess() {
      if (Thread.currentThread().getThreadGroup() != SidedThreadGroups.SERVER && FMLEnvironment.dist.isClient()) {
         return ClientRegistryHelper.getRegistryAccess();
      }

      MinecraftServer currentServer = ServerLifecycleHooks.getCurrentServer();
      return currentServer == null ? Optional.empty() : Optional.of(currentServer.registryAccess());
   }

   public static ResourceLocation getBlockKey(Block block) {
      return BuiltInRegistries.BLOCK.getKey(block);
   }
}
