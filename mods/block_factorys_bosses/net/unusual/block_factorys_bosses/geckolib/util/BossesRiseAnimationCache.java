package net.unusual.block_factorys_bosses.geckolib.util;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Function;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.PreparableReloadListener.PreparationBarrier;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import software.bernie.geckolib.GeckoLibClient;
import software.bernie.geckolib.GeckoLibConstants;
import software.bernie.geckolib.loading.FileLoader;
import software.bernie.geckolib.loading.object.BakedAnimations;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BossesRiseAnimationCache {
   public static final String CUSTOM_ANIMATION_PATH = "anim_bosses_rise";
   private static Map<ResourceLocation, BakedAnimations> BOSSES_RISE_ANIMATIONS = Collections.emptyMap();

   public static Map<ResourceLocation, BakedAnimations> getBakedAnimations() {
      return BOSSES_RISE_ANIMATIONS;
   }

   public static void registerReloadListener() {
      GeckoLibClient.init();
      if (Minecraft.getInstance().getResourceManager() instanceof ReloadableResourceManager resourceManager) {
         resourceManager.registerReloadListener(BossesRiseAnimationCache::reload);
      }
   }

   public static CompletableFuture<Void> reload(
      PreparationBarrier stage,
      ResourceManager resourceManager,
      ProfilerFiller preparationsProfiler,
      ProfilerFiller reloadProfiler,
      Executor backgroundExecutor,
      Executor gameExecutor
   ) {
      Map<ResourceLocation, BakedAnimations> animations = new Object2ObjectOpenHashMap();
      return CompletableFuture.allOf(loadAnimations(backgroundExecutor, resourceManager, animations::put))
         .<Void>thenCompose(stage::wait)
         .thenAcceptAsync(empty -> BOSSES_RISE_ANIMATIONS = animations, gameExecutor);
   }

   private static CompletableFuture<Void> loadAnimations(
      Executor backgroundExecutor, ResourceManager resourceManager, BiConsumer<ResourceLocation, BakedAnimations> elementConsumer
   ) {
      return loadResources(
         backgroundExecutor,
         resourceManager,
         resource -> {
            try {
               return (BakedAnimations)CustomKeyFramesAdapter.GEO_GSON
                  .fromJson(GsonHelper.getAsJsonObject(FileLoader.loadFile(resource, resourceManager), "animations"), BakedAnimations.class);
            } catch (Exception ex) {
               throw GeckoLibConstants.exception(resource, "Error loading animation file", ex);
            }
         },
         elementConsumer
      );
   }

   private static <T> CompletableFuture<Void> loadResources(
      Executor executor, ResourceManager resourceManager, Function<ResourceLocation, T> loader, BiConsumer<ResourceLocation, T> map
   ) {
      return CompletableFuture.<Map>supplyAsync(
            () -> resourceManager.listResources("anim_bosses_rise", fileName -> fileName.toString().endsWith(".json")), executor
         )
         .thenAcceptAsync(resources -> {
            for (ResourceLocation resource : resources.keySet()) {
               if (resource.getNamespace().toLowerCase(Locale.ROOT).equals("block_factorys_bosses")) {
                  map.accept(resource, loader.apply(resource));
               }
            }
         }, executor);
   }
}
