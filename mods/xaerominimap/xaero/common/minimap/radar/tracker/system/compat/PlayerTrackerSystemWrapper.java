package xaero.common.minimap.radar.tracker.system.compat;

import java.util.Iterator;
import xaero.common.minimap.radar.tracker.system.IPlayerTrackerSystem;
import xaero.common.minimap.radar.tracker.system.ITrackedPlayerReader;
import xaero.hud.minimap.player.tracker.system.IRenderedPlayerTracker;

@Deprecated
public class PlayerTrackerSystemWrapper<P> implements IPlayerTrackerSystem<P> {
   private final IRenderedPlayerTracker<P> original;
   private final TrackedPlayerReaderWrapper<P> readerWrapper;

   public PlayerTrackerSystemWrapper(IRenderedPlayerTracker<P> original) {
      this.original = original;
      this.readerWrapper = new TrackedPlayerReaderWrapper<>(original.getReader());
   }

   public ITrackedPlayerReader<P> getReader() {
      return this.readerWrapper;
   }

   @Override
   public Iterator<P> getTrackedPlayerIterator() {
      return this.original.getTrackedPlayerIterator();
   }
}
