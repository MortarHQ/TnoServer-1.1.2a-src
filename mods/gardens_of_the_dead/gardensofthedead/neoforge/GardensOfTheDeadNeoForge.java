package gardensofthedead.neoforge;

import gardensofthedead.GardensOfTheDead;
import gardensofthedead.neoforge.region.GardensOfTheDeadNeoForgeRegion;
import gardensofthedead.registry.ModSurfaceRules;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.SurfaceRuleManager.RuleCategory;

@Mod("gardens_of_the_dead")
public class GardensOfTheDeadNeoForge {
   public GardensOfTheDeadNeoForge(IEventBus modBus) {
      GardensOfTheDead.init();
      if (FMLEnvironment.dist == Dist.CLIENT) {
         new GardensOfTheDeadNeoForgeClient(modBus);
      }

      modBus.addListener(this::commonSetup);
   }

   private void commonSetup(FMLCommonSetupEvent event) {
      event.enqueueWork(() -> {
         Regions.register(new GardensOfTheDeadNeoForgeRegion());
         SurfaceRuleManager.addSurfaceRules(RuleCategory.NETHER, "gardens_of_the_dead", ModSurfaceRules.makeRules());
      });
   }
}
