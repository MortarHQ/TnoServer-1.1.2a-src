package com.tno_tensura_extras.compat;

import com.tno_tensura_extras.TensuraExtras;
import com.tno_tensura_extras.compat.ftbchunks.FtbChunksProtection;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.neoforged.fml.ModList;
import org.jetbrains.annotations.Nullable;

public final class ProtectionCompat {
   private static final String FTB_CHUNKS = "ftbchunks";
   private static Boolean ftbChunks;
   private static boolean loggedFailure;

   private ProtectionCompat() {
   }

   public static boolean mayBind(@Nullable Entity actor, BlockPos pos) {
      if (actor instanceof ServerPlayer player && ftbChunksLoaded()) {
         try {
            return FtbChunksProtection.mayBreakHere(player, pos);
         } catch (LinkageError error) {
            disableFtbChunks(error);
            return true;
         } catch (RuntimeException exception) {
            logFailure(exception);
            return true;
         }
      } else {
         return true;
      }
   }

   public static boolean mayRelocate(
      MinecraftServer server, UUID binder, ResourceKey<Level> fromDimension, BlockPos from, ResourceKey<Level> toDimension, BlockPos to
   ) {
      if (!ftbChunksLoaded()) {
         return true;
      }

      ServerPlayer player = server.getPlayerList().getPlayer(binder);
      if (player == null) {
         return false;
      }

      try {
         return FtbChunksProtection.mayEdit(player, fromDimension, from) && FtbChunksProtection.mayEdit(player, toDimension, to);
      } catch (LinkageError error) {
         disableFtbChunks(error);
         return true;
      } catch (RuntimeException exception) {
         logFailure(exception);
         return true;
      }
   }

   private static boolean ftbChunksLoaded() {
      if (ftbChunks == null) {
         ftbChunks = ModList.get().isLoaded("ftbchunks");
      }

      return ftbChunks;
   }

   private static void disableFtbChunks(LinkageError error) {
      ftbChunks = false;
      TensuraExtras.LOGGER.error("Incompatible FTB Chunks build, disabling its protection checks", error);
   }

   private static void logFailure(RuntimeException exception) {
      if (!loggedFailure) {
         loggedFailure = true;
         TensuraExtras.LOGGER.error("FTB Chunks protection check failed, allowing the delivery", exception);
      }
   }
}
