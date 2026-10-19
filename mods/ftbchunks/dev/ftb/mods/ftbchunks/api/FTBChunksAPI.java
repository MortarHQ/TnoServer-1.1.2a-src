package dev.ftb.mods.ftbchunks.api;

import dev.ftb.mods.ftbchunks.api.client.FTBChunksClientAPI;
import dev.ftb.mods.ftblibrary.math.ChunkDimPos;
import java.util.Objects;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus.Internal;

public class FTBChunksAPI {
   public static final String MOD_ID = "ftbchunks";
   public static final String MOD_NAME = "FTB Chunks";
   private static FTBChunksAPI.API instance;
   private static FTBChunksClientAPI clientInstance;

   @NotNull
   public static FTBChunksAPI.API api() {
      return Objects.requireNonNull(instance);
   }

   @NotNull
   public static FTBChunksClientAPI clientApi() {
      return Objects.requireNonNull(clientInstance);
   }

   public static ResourceLocation rl(String path) {
      return ResourceLocation.fromNamespaceAndPath("ftbchunks", path);
   }

   @Internal
   public static void _init(FTBChunksAPI.API instance) {
      if (FTBChunksAPI.instance != null) {
         throw new IllegalStateException("can't init more than once!");
      }

      FTBChunksAPI.instance = instance;
   }

   @Internal
   public static void _initClient(FTBChunksClientAPI instance) {
      if (clientInstance != null) {
         throw new IllegalStateException("can't init more than once!");
      }

      clientInstance = instance;
   }

   public interface API {
      boolean isManagerLoaded();

      ClaimedChunkManager getManager();

      ClaimResult claimAsPlayer(ServerPlayer var1, ResourceKey<Level> var2, ChunkPos var3, boolean var4);

      boolean isChunkForceLoaded(ChunkDimPos var1);
   }
}
