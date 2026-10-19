package twilightforest.client;

import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import twilightforest.TwilightForestMod;

public class JappaPackReloadListener implements ResourceManagerReloadListener {
   private static boolean jappaPackLoaded = false;
   public static final JappaPackReloadListener INSTANCE = new JappaPackReloadListener();

   public void onResourceManagerReload(ResourceManager manager) {
      jappaPackLoaded = Minecraft.getInstance()
         .getResourceManager()
         .listPacks()
         .anyMatch(pack -> pack.getResource(PackType.CLIENT_RESOURCES, TwilightForestMod.prefix("jappa_models.marker")) != null);
   }

   public boolean isJappaPackLoaded() {
      return jappaPackLoaded;
   }
}
