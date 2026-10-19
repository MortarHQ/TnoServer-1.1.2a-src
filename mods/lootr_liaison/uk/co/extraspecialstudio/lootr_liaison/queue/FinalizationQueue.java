package uk.co.extraspecialstudio.lootr_liaison.queue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;
import uk.co.extraspecialstudio.lootr_liaison.Config;
import uk.co.extraspecialstudio.lootr_liaison.LiaisonNbt;
import uk.co.extraspecialstudio.lootr_liaison.lootr.LootrAdapter;
import uk.co.extraspecialstudio.lootr_liaison.mimic.MimicStabilityLayer;

public final class FinalizationQueue {
   private static final Deque<FinalizationQueue.Entry> QUEUE = new ArrayDeque<>();
   private static final Map<String, Boolean> SEEN = new ConcurrentHashMap<>();

   public static void enqueue(ResourceKey<Level> dim, BlockPos pos, long currentTick) {
      String key = dim.location().toString() + "|" + pos.toShortString();
      if (SEEN.putIfAbsent(key, true) == null) {
         QUEUE.addLast(new FinalizationQueue.Entry(dim, pos, currentTick));
      }
   }

   @SubscribeEvent
   public static void onServerTick(Post event) {
      MinecraftServer server = event.getServer();
      if (server != null) {
         long now = server.overworld().getGameTime();
         int delay = Config.finalizationDelayTicks;
         int max = Config.maxFinalizationsPerTick;
         int done = 0;

         while (done < max && !QUEUE.isEmpty()) {
            FinalizationQueue.Entry e = QUEUE.peekFirst();
            if (e == null || now < e.enqueueTick + delay) {
               break;
            }

            QUEUE.pollFirst();
            Level level = server.getLevel(e.dim);
            if (level != null && level.isLoaded(e.pos)) {
               BlockEntity be = level.getBlockEntity(e.pos);
               if (be instanceof RandomizableContainerBlockEntity rcb) {
                  CompoundTag tag = be.getPersistentData();
                  if (!LiaisonNbt.isFinalized(tag)) {
                     LootrAdapter.finalize(level, e.pos, rcb);
                     tag.putBoolean("lootr_liaison:finalized", true);
                     if (Config.enableArtifactsMimicCompat && "finalization".equalsIgnoreCase(Config.mimicRollTiming)) {
                        MimicStabilityLayer.rollAtFinalization(level, e.pos, rcb);
                     }

                     done++;
                  }
               }
            }
         }
      }
   }

   private static final class Entry {
      final ResourceKey<Level> dim;
      final BlockPos pos;
      final long enqueueTick;

      Entry(ResourceKey<Level> dim, BlockPos pos, long enqueueTick) {
         this.dim = dim;
         this.pos = pos;
         this.enqueueTick = enqueueTick;
      }
   }
}
