package twilightforest.compat.top;

import java.util.function.Function;
import mcjty.theoneprobe.api.ITheOneProbe;

public class TopCompat implements Function<ITheOneProbe, Void> {
   public Void apply(ITheOneProbe api) {
      api.registerElementFactory(QuestRamWoolElement.Factory.INSTANCE);
      api.registerEntityProvider(TOPQuestRamWoolProvider.INSTANCE);
      api.registerProvider(ChiseledBookshelfSpawnProvider.INSTANCE);
      return null;
   }
}
