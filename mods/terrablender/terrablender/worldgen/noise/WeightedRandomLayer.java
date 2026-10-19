package terrablender.worldgen.noise;

import java.util.List;
import net.minecraft.util.random.WeightedEntry;
import terrablender.util.WeightedRandomList;

public abstract class WeightedRandomLayer<T extends WeightedEntry> implements AreaTransformer0 {
   private final WeightedRandomList<T> weightedEntries;

   public WeightedRandomLayer(List<T> entries) {
      this.weightedEntries = WeightedRandomList.create(entries);
   }

   @Override
   public int apply(AreaContext context, int x, int y) {
      return this.weightedEntries.getRandom(context).map(this::getEntryIndex).orElse(this.getDefaultIndex());
   }

   protected abstract int getEntryIndex(T var1);

   protected int getDefaultIndex() {
      return 0;
   }
}
