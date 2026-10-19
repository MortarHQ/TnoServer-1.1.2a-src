package it.hurts.octostudios.octolib.module.particle;

import it.hurts.octostudios.octolib.client.shake.ShakeSystem;
import it.hurts.octostudios.octolib.module.config.ConfigManager;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.WeakHashMap;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import org.apache.logging.log4j.util.Cast;

public class OctoRenderManager {
   static long lastTick = 0L;
   static Queue<RenderProvider<?, ?>> providers = new ArrayDeque<>();
   static WeakHashMap<RenderProvider<?, ?>, RenderBuffer<?, ?>> map = new WeakHashMap<>();

   public static void worldExit(LocalPlayer player) {
      map.clear();
      providers.clear();
      ConfigManager.reloadAll();
   }

   public static void clientTick(ClientLevel level) {
      long time = level.getGameTime();
      if (time != lastTick) {
         lastTick = time;
         Iterator<RenderProvider<?, ?>> iterator = providers.iterator();

         while (iterator.hasNext()) {
            RenderProvider<? extends RenderProvider<?, ? extends RenderBuffer<?, ?>>, ? extends RenderBuffer<? extends RenderProvider<?, ?>, ?>> p = (RenderProvider<? extends RenderProvider<?, ? extends RenderBuffer<?, ?>>, ? extends RenderBuffer<? extends RenderProvider<?, ?>, ?>>)iterator.next();
            RenderBuffer buffer = getOrCreateBuffer((RenderProvider)Cast.cast(p));
            if (!p.shouldRender((RenderBuffer)Cast.cast(buffer))) {
               map.remove(p);
               iterator.remove();
            } else if (time % p.getUpdateFrequency() == 0L) {
               buffer.tick(p);
            }
         }
      }
   }

   public static void clientRenderTick() {
      ShakeSystem.updateAll();
   }

   public static <B extends RenderBuffer<P, B>, P extends RenderProvider<P, B>> B getOrCreateBuffer(P provider) {
      if (map.containsKey(provider)) {
         return (B)map.get(provider);
      }

      B buffer = provider.createBuffer();
      map.put(provider, buffer);
      return buffer;
   }

   public static <B extends RenderBuffer<P, B>, P extends RenderProvider<P, B>> void registerProvider(P provider) {
      if (!map.containsKey(provider)) {
         providers.add(provider);
         map.put(provider, provider.createBuffer());
      }
   }

   public static Queue<RenderProvider<?, ?>> getProviders() {
      return providers;
   }
}
