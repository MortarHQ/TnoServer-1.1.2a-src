package team.lodestar.lodestone.systems.rendering.rendeertype;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import team.lodestar.lodestone.LodestoneLib;
import team.lodestar.lodestone.systems.rendering.LodestoneRenderType;

public class RenderTypeProvider {
   private final Function<RenderTypeToken, LodestoneRenderType> provider;
   private final ConcurrentHashMap<RenderTypeToken, LodestoneRenderType> cache = new ConcurrentHashMap<>();
   private boolean hasGoneNuclear;
   private long nuclearTimeCache = 0L;
   private int nuclearRenderTypeCount = 0;

   public RenderTypeProvider(Function<RenderTypeToken, LodestoneRenderType> provider) {
      this.provider = provider;
   }

   public LodestoneRenderTypeBuilder apply(RenderTypeToken token) {
      return new LodestoneRenderTypeBuilder(this, token);
   }

   protected LodestoneRenderType createRenderType(RenderTypeToken token) {
      if (this.hasGoneNuclear) {
         return this.cache.entrySet().iterator().next().getValue();
      }

      if (this.cache.containsKey(token)) {
         return this.cache.get(token);
      }

      RenderTypeToken unique = token.unique();
      LodestoneRenderType renderType = this.provider.apply(unique);
      this.cache.put(unique, renderType);
      if (this.checkNuclear()) {
         this.hasGoneNuclear = true;
         LodestoneLib.LOGGER
            .warn(
               "RenderTypeProvider has been called too often in a short time! This is very dangerous. Current count: {}, Time since last check: {}msRender Type Provider: {}, Render Type Token: {}",
               this.nuclearRenderTypeCount,
               System.currentTimeMillis() - this.nuclearTimeCache,
               this,
               unique
            );
      }

      return renderType;
   }

   @Override
   public String toString() {
      List<String> cacheSample = this.cache
         .entrySet()
         .stream()
         .limit(5L)
         .map(entry -> entry.getKey().getIdentifier() + " -> " + entry.getValue().name)
         .toList();
      return "RenderTypeProvider{provider=" + this.provider + ", cache sample=" + cacheSample + "}";
   }

   public boolean checkNuclear() {
      if (this.cache.size() >= 200) {
         return true;
      }

      int maxCalls = 1000;
      long thresholdMillis = 5000L;
      if (this.nuclearTimeCache == 0L) {
         this.nuclearTimeCache = System.currentTimeMillis();
      }

      this.nuclearRenderTypeCount++;
      long now = System.currentTimeMillis();
      if (now - this.nuclearTimeCache > thresholdMillis) {
         boolean tooOften = this.nuclearRenderTypeCount > maxCalls;
         this.nuclearRenderTypeCount = 0;
         this.nuclearTimeCache = now;
         return tooOften;
      } else {
         return false;
      }
   }
}
