package net.blay09.mods.balm.api.client;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.BalmRuntimeLoadContext;
import net.blay09.mods.balm.api.client.keymappings.BalmKeyMappings;
import net.blay09.mods.balm.api.client.module.BalmClientModule;
import net.blay09.mods.balm.api.client.rendering.BalmModels;
import net.blay09.mods.balm.api.client.rendering.BalmRenderers;
import net.blay09.mods.balm.api.client.rendering.BalmTextures;
import net.blay09.mods.balm.api.client.screen.BalmScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;

public interface BalmClientRuntime<TLoadContext extends BalmRuntimeLoadContext> {
   BalmRenderers getRenderers();

   BalmScreens getScreens();

   BalmModels getModels();

   BalmKeyMappings getKeyMappings();

   void initializeMod(String var1, TLoadContext var2, Runnable var3);

   default void initializeModule(BalmClientModule module) {
      String modId = module.getId().getNamespace();
      module.registerEvents(Balm.getEvents());
      module.registerRenderers(this.getRenderers().scoped(modId));
      module.registerScreens(this.getScreens().scoped(modId));
      module.registerModels(this.getModels().scoped(modId));
      module.registerKeyMappings(this.getKeyMappings().scoped(modId));
      module.initialize();
   }

   boolean isReady();

   void onRuntimeAvailable(Runnable var1);

   void registerModule(BalmClientModule var1);

   void addResourceReloadListener(ResourceLocation var1, PreparableReloadListener var2);

   @Deprecated(forRemoval = true, since = "1.21.5")
   BalmTextures getTextures();
}
