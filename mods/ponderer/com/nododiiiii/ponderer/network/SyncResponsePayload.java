package com.nododiiiii.ponderer.network;

import com.mojang.logging.LogUtils;
import com.nododiiiii.ponderer.platform.PondererServices;
import com.nododiiiii.ponderer.ponder.PondererClientCommands;
import com.nododiiiii.ponderer.ponder.SceneStore;
import com.nododiiiii.ponderer.ponder.SyncMeta;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public record SyncResponsePayload(
   List<SyncResponsePayload.FileEntry> scripts, List<SyncResponsePayload.FileEntry> structures, boolean finalChunk, int serverSkippedCount
) implements CustomPacketPayload {
   public static final Type<SyncResponsePayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("ponderer", "sync_response"));
   public static final StreamCodec<RegistryFriendlyByteBuf, SyncResponsePayload> CODEC = StreamCodec.of(
      SyncResponsePayload::encode, SyncResponsePayload::decode
   );
   private static final Logger LOGGER = LogUtils.getLogger();
   private static final int MAX_SYNC_FILE_BYTES = 786432;
   private static final int MAX_SYNC_PAYLOAD_BYTES = 921600;
   @Nullable
   private static SyncResponsePayload.PullSession activeSession;

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   private static void encode(RegistryFriendlyByteBuf buf, SyncResponsePayload payload) {
      buf.writeVarInt(payload.scripts().size());

      for (SyncResponsePayload.FileEntry entry : payload.scripts()) {
         writeEntry(buf, entry);
      }

      buf.writeVarInt(payload.structures().size());

      for (SyncResponsePayload.FileEntry entry : payload.structures()) {
         writeEntry(buf, entry);
      }

      buf.writeBoolean(payload.finalChunk());
      buf.writeVarInt(payload.serverSkippedCount());
   }

   private static SyncResponsePayload decode(RegistryFriendlyByteBuf buf) {
      int scriptsSize = buf.readVarInt();
      List<SyncResponsePayload.FileEntry> scripts = new ArrayList<>(scriptsSize);

      for (int i = 0; i < scriptsSize; i++) {
         scripts.add(readEntry(buf));
      }

      int structuresSize = buf.readVarInt();
      List<SyncResponsePayload.FileEntry> structures = new ArrayList<>(structuresSize);

      for (int i = 0; i < structuresSize; i++) {
         structures.add(readEntry(buf));
      }

      boolean finalChunk = buf.readBoolean();
      int serverSkippedCount = buf.readVarInt();
      return new SyncResponsePayload(scripts, structures, finalChunk, serverSkippedCount);
   }

   public static void sendBatched(ServerPlayer player) {
      if (player != null) {
         List<SceneStore.SyncFileRef> scripts = SceneStore.collectServerScriptRefs(player.server);
         List<SceneStore.SyncFileRef> structures = SceneStore.collectServerStructureRefs(player.server);
         List<SyncResponsePayload.FileEntry> scriptBatch = new ArrayList<>();
         List<SyncResponsePayload.FileEntry> structureBatch = new ArrayList<>();
         int currentBytes = 0;
         int skippedByServer = 0;

         for (SceneStore.SyncFileRef ref : scripts) {
            SyncResponsePayload.QueueResult result = queueFile(player, ref, scriptBatch, structureBatch, true, currentBytes);
            currentBytes = result.currentBytes();
            skippedByServer += result.skippedByServer();
         }

         for (SceneStore.SyncFileRef ref : structures) {
            SyncResponsePayload.QueueResult result = queueFile(player, ref, scriptBatch, structureBatch, false, currentBytes);
            currentBytes = result.currentBytes();
            skippedByServer += result.skippedByServer();
         }

         PondererServices.NETWORK.sendToPlayer(player, new SyncResponsePayload(List.copyOf(scriptBatch), List.copyOf(structureBatch), true, skippedByServer));
      }
   }

   public static void handle(SyncResponsePayload payload) {
      SyncResponsePayload.PullSession session = activeSession;
      if (session == null) {
         session = new SyncResponsePayload.PullSession(PondererClientCommands.consumePullMode());
         activeSession = session;
      }

      applyEntries(payload.scripts(), "scripts", ".json", session);
      applyEntries(payload.structures(), "structures", ".nbt", session);
      session.skipped = session.skipped + payload.serverSkippedCount();
      if (payload.finalChunk()) {
         if (!session.syncedHashes.isEmpty()) {
            SyncMeta.recordHashes(session.syncedHashes);
         }

         SceneStore.reloadFromDisk();
         Minecraft.getInstance().execute(PonderIndex::reload);
         notifyClient(Component.translatable("ponderer.cmd.pull.done", new Object[]{session.written, session.skipped, session.conflicts}));
         if (session.conflicts > 0 && "check".equals(session.pullMode)) {
            notifyClient(Component.translatable("ponderer.cmd.pull.hint_force"));
            notifyClient(Component.translatable("ponderer.cmd.pull.hint_keep"));
         }

         activeSession = null;
      }
   }

   private static void applyEntries(List<SyncResponsePayload.FileEntry> entries, String category, String ext, SyncResponsePayload.PullSession session) {
      for (SyncResponsePayload.FileEntry entry : entries) {
         Path localFile = resolveLocalPath(entry, ext);
         String displayId = displayId(entry.id(), entry.pack());
         if (localFile == null) {
            LOGGER.warn("Rejected unsafe {} path from server: {} pack={}", new Object[]{category, entry.id(), entry.pack()});
            session.skipped++;
         } else {
            String metaKey = SyncMeta.metaKey(category, entry.id(), entry.pack());
            if (!"force".equals(session.pullMode)) {
               String status = SyncMeta.checkConflict(metaKey, entry.bytes(), localFile);
               if ("both_modified".equals(status)) {
                  session.conflicts++;
                  if ("check".equals(session.pullMode)) {
                     notifyClient(Component.translatable("ponderer.cmd.pull.conflict_both", new Object[]{displayId}));
                     session.skipped++;
                     continue;
                  }

                  if ("keep_local".equals(session.pullMode)) {
                     session.skipped++;
                     continue;
                  }

                  notifyClient(Component.translatable("ponderer.cmd.pull.conflict_server", new Object[]{displayId}));
               } else if ("local_modified".equals(status) && "keep_local".equals(session.pullMode)) {
                  session.skipped++;
                  continue;
               }
            }

            if (writeFile(localFile, entry.bytes())) {
               session.syncedHashes.put(metaKey, entry.bytes());
               session.written++;
            } else {
               session.skipped++;
               notifyClient(Component.translatable("ponderer.cmd.pull.write_failed", new Object[]{displayId}));
            }
         }
      }
   }

   @Nullable
   private static Path resolveLocalPath(SyncResponsePayload.FileEntry entry, String ext) {
      if (".json".equals(ext)) {
         return SceneStore.findLocalSceneFile(entry.id(), entry.pack());
      }

      ResourceLocation loc = ResourceLocation.tryParse(entry.id());
      return loc == null ? null : SceneStore.resolveLocalSyncStructurePath(loc, entry.pack());
   }

   private static boolean writeFile(Path path, byte[] bytes) {
      try {
         Files.createDirectories(path.getParent());
         Files.write(path, bytes);
         return true;
      } catch (Exception e) {
         LOGGER.warn("Failed to write file: {}", path, e);
         return false;
      }
   }

   private static SyncResponsePayload.QueueResult queueFile(
      ServerPlayer player,
      SceneStore.SyncFileRef ref,
      List<SyncResponsePayload.FileEntry> scriptBatch,
      List<SyncResponsePayload.FileEntry> structureBatch,
      boolean script,
      int currentBytes
   ) {
      long size;
      try {
         size = Files.size(ref.path());
      } catch (Exception e) {
         player.sendSystemMessage(Component.translatable("ponderer.cmd.pull.server_skip_failed", new Object[]{displayId(ref.id(), ref.pack())}));
         return new SyncResponsePayload.QueueResult(currentBytes, 1);
      }

      if (size > 786432L) {
         player.sendSystemMessage(Component.translatable("ponderer.cmd.pull.server_skip_too_large", new Object[]{displayId(ref.id(), ref.pack()), size}));
         return new SyncResponsePayload.QueueResult(currentBytes, 1);
      }

      if (currentBytes > 0 && currentBytes + size > 921600L) {
         flushBatch(player, scriptBatch, structureBatch, false, 0);
         currentBytes = 0;
      }

      byte[] bytes;
      try {
         bytes = Files.readAllBytes(ref.path());
      } catch (Exception e) {
         player.sendSystemMessage(Component.translatable("ponderer.cmd.pull.server_skip_failed", new Object[]{displayId(ref.id(), ref.pack())}));
         return new SyncResponsePayload.QueueResult(currentBytes, 1);
      }

      if (bytes.length > 786432) {
         player.sendSystemMessage(
            Component.translatable("ponderer.cmd.pull.server_skip_too_large", new Object[]{displayId(ref.id(), ref.pack()), bytes.length})
         );
         return new SyncResponsePayload.QueueResult(currentBytes, 1);
      }

      SyncResponsePayload.FileEntry entry = new SyncResponsePayload.FileEntry(ref.id(), ref.pack(), bytes);
      if (script) {
         scriptBatch.add(entry);
      } else {
         structureBatch.add(entry);
      }

      return new SyncResponsePayload.QueueResult(currentBytes + bytes.length, 0);
   }

   private static void flushBatch(
      ServerPlayer player,
      List<SyncResponsePayload.FileEntry> scriptBatch,
      List<SyncResponsePayload.FileEntry> structureBatch,
      boolean finalChunk,
      int skippedByServer
   ) {
      if (!scriptBatch.isEmpty() || !structureBatch.isEmpty() || finalChunk) {
         PondererServices.NETWORK
            .sendToPlayer(player, new SyncResponsePayload(List.copyOf(scriptBatch), List.copyOf(structureBatch), finalChunk, skippedByServer));
         scriptBatch.clear();
         structureBatch.clear();
      }
   }

   private static void notifyClient(Component message) {
      if (Minecraft.getInstance().player != null) {
         Minecraft.getInstance().player.displayClientMessage(message, false);
      }
   }

   private static String displayId(String id, @Nullable String pack) {
      return pack != null && !pack.isBlank() ? "[" + pack + "] " + id : id;
   }

   private static void writeEntry(RegistryFriendlyByteBuf buf, SyncResponsePayload.FileEntry entry) {
      buf.writeUtf(entry.id());
      writeOptionalUtf(buf, entry.pack());
      buf.writeByteArray(entry.bytes());
   }

   private static SyncResponsePayload.FileEntry readEntry(RegistryFriendlyByteBuf buf) {
      return new SyncResponsePayload.FileEntry(buf.readUtf(), readOptionalUtf(buf), buf.readByteArray());
   }

   private static void writeOptionalUtf(RegistryFriendlyByteBuf buf, @Nullable String value) {
      boolean present = value != null && !value.isBlank();
      buf.writeBoolean(present);
      if (present) {
         buf.writeUtf(value);
      }
   }

   @Nullable
   private static String readOptionalUtf(RegistryFriendlyByteBuf buf) {
      return buf.readBoolean() ? buf.readUtf() : null;
   }

   public record FileEntry(String id, @Nullable String pack, byte[] bytes) {
   }

   private static final class PullSession {
      private final String pullMode;
      private int written;
      private int skipped;
      private int conflicts;
      private final Map<String, byte[]> syncedHashes = new HashMap<>();

      private PullSession(String pullMode) {
         this.pullMode = pullMode;
      }
   }

   private record QueueResult(int currentBytes, int skippedByServer) {
   }
}
