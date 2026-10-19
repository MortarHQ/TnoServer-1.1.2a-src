package noobanidus.mods.lootr.common.command;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunk.EntityCreationType;

public final class CustomConvertJob {
   private static final ThreadFactory THREAD_FACTORY = new ThreadFactoryBuilder().setDaemon(true).setNameFormat("lootr-convert-%d").build();
   private static final TicketType<ChunkPos> CONVERT_TICKET = TicketType.create("lootr_convert", Comparator.comparingLong(ChunkPos::toLong));
   private static Thread convertThread;
   private static final int TICKET_LEVEL = 2;
   private static final int batchSize = 2;

   public static void start(MinecraftServer server, ServerLevel level, List<ChunkPos> positions, CommandSourceStack src) {
      if (convertThread != null && convertThread.isAlive()) {
         src.sendFailure(Component.literal("A conversion job is already running."));
      } else {
         AtomicBoolean running = new AtomicBoolean(true);
         AtomicInteger processed = new AtomicInteger();
         AtomicInteger converted = new AtomicInteger();
         AtomicInteger skipped = new AtomicInteger();
         AtomicInteger convertedBlockEntities = new AtomicInteger();
         convertThread = THREAD_FACTORY.newThread(
            () -> {
               try {
                  for (int i = 0; i < positions.size() && running.get(); i += 2) {
                     int from = i;
                     int to = Math.min(i + 2, positions.size());
                     CompletableFuture<Void> batchDone = new CompletableFuture<>();
                     server.execute(
                        () -> {
                           try {
                              for (int j = from; j < to; j++) {
                                 ChunkPos cp = positions.get(j);
                                 int convertedCount = processOneChunkOnServerThread(level, cp, src);
                                 processed.incrementAndGet();
                                 if (convertedCount > 0) {
                                    converted.incrementAndGet();
                                    convertedBlockEntities.addAndGet(convertedCount);
                                 } else {
                                    skipped.incrementAndGet();
                                 }
                              }

                              if (processed.get() % 50 == 0) {
                                 src.sendSuccess(
                                    () -> Component.literal(
                                       "Progress: "
                                          + processed.get()
                                          + "/"
                                          + positions.size()
                                          + " converted="
                                          + converted.get()
                                          + " chunks, skipped="
                                          + skipped.get()
                                          + " empty chunks, converted a total of "
                                          + convertedBlockEntities.get()
                                          + " block entities to custom inventories."
                                    ),
                                    true
                                 );
                              }

                              batchDone.complete(null);
                           } catch (Throwable t) {
                              batchDone.completeExceptionally(tx);
                           }
                        }
                     );
                     batchDone.join();
                  }

                  server.execute(
                     () -> src.sendSuccess(
                        () -> Component.literal(
                           "Conversion complete. processed=" + processed.get() + " converted=" + converted.get() + " skipped=" + skipped.get()
                        ),
                        true
                     )
                  );
               } catch (Throwable t) {
                  server.execute(() -> src.sendFailure(Component.literal("Conversion failed: " + t)));
               }
            }
         );
         convertThread.start();
      }
   }

   private static int processOneChunkOnServerThread(ServerLevel level, ChunkPos pos, CommandSourceStack src) {
      if (level == null) {
         src.sendFailure(Component.literal("Level not found."));
         return 0;
      }

      level.getChunkSource().addRegionTicket(CONVERT_TICKET, pos, 2, pos);
      Consumer<String> reporter = msg -> src.sendSuccess(() -> Component.literal(msg), true);

      try {
         LevelChunk chunk = level.getChunk(pos.x, pos.z);
         int changed = 0;

         for (BlockPos bePos : chunk.getBlockEntitiesPos()) {
            BlockEntity be = chunk.getBlockEntity(bePos, EntityCreationType.IMMEDIATE);
            if (be != null) {
               changed += convertAt(bePos, level, reporter, src.registryAccess());
            }
         }

         return changed;
      } finally {
         level.getChunkSource().removeRegionTicket(CONVERT_TICKET, pos, 2, pos);
      }
   }

   private static int convertAt(BlockPos pos, ServerLevel level, Consumer<String> reporter, Provider registries) {
      return CommandLootr.convertToCustom(pos, level, reporter, registries) ? 1 : 0;
   }
}
