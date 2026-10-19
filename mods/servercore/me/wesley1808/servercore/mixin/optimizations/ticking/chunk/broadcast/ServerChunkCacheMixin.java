package me.wesley1808.servercore.mixin.optimizations.ticking.chunk.broadcast;

import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import it.unimi.dsi.fastutil.objects.ReferenceLinkedOpenHashSet;
import java.util.List;
import java.util.function.Consumer;
import me.wesley1808.servercore.common.interfaces.chunk.IServerChunkCache;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerChunkCache.ChunkAndHolder;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ServerChunkCache.class, priority = 900)
public class ServerChunkCacheMixin implements IServerChunkCache {
   @Unique
   private final ReferenceLinkedOpenHashSet<ChunkHolder> servercore$requiresBroadcast = new ReferenceLinkedOpenHashSet(128);

   @Redirect(method = "tickChunks()V", at = @At(value = "INVOKE", target = "Ljava/util/List;forEach(Ljava/util/function/Consumer;)V", ordinal = 0))
   private void servercore$broadcastChanges(List<ChunkAndHolder> list, Consumer<ChunkAndHolder> consumer) {
      ObjectListIterator var3 = this.servercore$requiresBroadcast.iterator();

      while (var3.hasNext()) {
         ChunkHolder holder = (ChunkHolder)var3.next();
         LevelChunk chunk = holder.getTickingChunk();
         if (chunk != null) {
            holder.broadcastChanges(chunk);
         }
      }

      this.servercore$requiresBroadcast.clear();
   }

   @Override
   public void servercore$requiresBroadcast(ChunkHolder holder) {
      this.servercore$requiresBroadcast.add(holder);
   }
}
