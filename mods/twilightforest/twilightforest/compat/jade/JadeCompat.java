package twilightforest.compat.jade;

import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;
import twilightforest.block.ChiseledCanopyShelfBlock;
import twilightforest.block.DryingRackBlock;
import twilightforest.entity.passive.QuestRam;

@WailaPlugin
public class JadeCompat implements IWailaPlugin {
   public void registerClient(IWailaClientRegistration registration) {
      registration.registerEntityComponent(QuestRamWoolProvider.INSTANCE, QuestRam.class);
      registration.registerBlockComponent(ChiseledBookshelfSpawnProvider.INSTANCE, ChiseledCanopyShelfBlock.class);
      registration.registerBlockComponent(DryingRackProvider.INSTANCE, DryingRackBlock.class);
   }

   public void register(IWailaCommonRegistration registration) {
      registration.registerBlockDataProvider(DryingRackProvider.INSTANCE, DryingRackBlock.class);
   }
}
