package me.wesley1808.servercore.common.services.platform;

import java.util.Optional;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MinecraftPlatform {
   boolean hasPermission(CommandSourceStack var1, String var2, int var3);

   boolean shouldForceChunkTicks(ChunkMap var1, ChunkPos var2);

   Component parseText(MinecraftServer var1, String var2);

   @Nullable
   default EntityType<?> getEntityType(String key) {
      return null;
   }

   @NotNull
   default Optional<ResourceKey<EntityType<?>>> getEntityTypeKey(EntityType<?> type) {
      return Optional.empty();
   }
}
