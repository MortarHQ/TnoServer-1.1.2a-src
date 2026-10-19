package twilightforest.client;

import com.google.common.collect.MapMaker;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import twilightforest.beanification.Autowired;
import twilightforest.beanification.Component;
import twilightforest.beanification.PostConstruct;
import twilightforest.init.TFBiomes;
import twilightforest.world.components.BiomeColorAlgorithms;

@Component
public final class FoliageColorHandler {
   @Autowired
   private BiomeColorAlgorithms biomeColorAlgorithms;
   private final Map<ResourceKey<Biome>, FoliageColorHandler.Handler> REGISTRY = new HashMap<ResourceKey<Biome>, FoliageColorHandler.Handler>() {
      {
         this.put(TFBiomes.SPOOKY_FOREST, (o, x, z) -> FoliageColorHandler.this.biomeColorAlgorithms.spookyFoliage(x, z));
         this.put(TFBiomes.ENCHANTED_FOREST, (o, x, z) -> FoliageColorHandler.this.biomeColorAlgorithms.enchanted(o, (int)x, (int)z));
         this.put(TFBiomes.DARK_FOREST_CENTER, (o, x, z) -> FoliageColorHandler.this.biomeColorAlgorithms.darkForestCenterFoliage(x, z));
         this.put(TFBiomes.DARK_FOREST, (o, x, z) -> FoliageColorHandler.this.biomeColorAlgorithms.darkForest(BiomeColorAlgorithms.Type.Foliage));
         this.put(TFBiomes.SWAMP, (o, x, z) -> FoliageColorHandler.this.biomeColorAlgorithms.swamp(BiomeColorAlgorithms.Type.Foliage));
      }
   };
   private final Map<Biome, FoliageColorHandler.Handler> HANDLES = new MapMaker().weakKeys().makeMap();

   @PostConstruct(PostConstruct.Bus.GAME)
   private void setup(IEventBus bus) {
      bus.addListener(EntityLeaveLevelEvent.class, event -> {
         if (event.getLevel().isClientSide()) {
            this.HANDLES.clear();
         }
      });
   }

   public int get(int o, Biome biome, double x, double z) {
      FoliageColorHandler.Handler handler = this.HANDLES.get(biome);
      if (handler == null) {
         handler = this.REGISTRY
            .getOrDefault(
               Minecraft.getInstance().level == null
                  ? null
                  : Minecraft.getInstance().level.registryAccess().registryOrThrow(Registries.BIOME).getResourceKey(biome).orElse(null),
               FoliageColorHandler.Handler.DEFAULT
            );
         this.HANDLES.put(biome, handler);
      }

      return handler.apply(o, x, z);
   }

   @FunctionalInterface
   private interface Handler {
      FoliageColorHandler.Handler DEFAULT = (o, x, z) -> o;

      int apply(int var1, double var2, double var4);
   }
}
