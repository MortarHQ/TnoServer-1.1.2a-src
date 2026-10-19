package xaero.map.region;

import java.util.HashMap;
import net.minecraft.world.level.block.state.BlockState;

public class OverlayManager {
   private HashMap<BlockState, HashMap<Byte, HashMap<Short, Overlay>>> overlayMap;
   private int numberOfUniques = 0;
   private Object[] keyHolder;

   public OverlayManager() {
      this.overlayMap = new HashMap<>();
      this.keyHolder = new Object[5];
   }

   public synchronized Overlay getOriginal(Overlay o) {
      o.fillManagerKeyHolder(this.keyHolder);
      return this.getOriginal(this.overlayMap, o, 0);
   }

   private Overlay getOriginal(HashMap map, Overlay o, int index) {
      Object byKey = map.get(this.keyHolder[index]);
      if (index == this.keyHolder.length - 1) {
         if (byKey == null) {
            this.numberOfUniques++;
            map.put(this.keyHolder[index], o);
            return o;
         } else {
            return (Overlay)byKey;
         }
      } else {
         if (byKey == null) {
            byKey = new HashMap();
            map.put(this.keyHolder[index], byKey);
         }

         return this.getOriginal((HashMap)byKey, o, ++index);
      }
   }

   public int getNumberOfUniqueOverlays() {
      return this.numberOfUniques;
   }
}
