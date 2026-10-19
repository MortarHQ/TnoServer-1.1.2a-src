package net.blay09.mods.balm.neoforge.client;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.client.keymappings.BalmKeyMappings;
import net.blay09.mods.balm.api.client.rendering.BalmModels;
import net.blay09.mods.balm.api.client.rendering.BalmRenderers;
import net.blay09.mods.balm.api.client.rendering.BalmTextures;
import net.blay09.mods.balm.api.client.screen.BalmScreens;
import net.blay09.mods.balm.common.BalmLoadContexts;
import net.blay09.mods.balm.common.LegacyNamespaceResolver;
import net.blay09.mods.balm.common.NamespaceResolver;
import net.blay09.mods.balm.common.client.CommonBalmClientRuntime;
import net.blay09.mods.balm.neoforge.ModBusEventRegisters;
import net.blay09.mods.balm.neoforge.NeoForgeLoadContext;
import net.blay09.mods.balm.neoforge.client.keymappings.NeoForgeBalmKeyMappings;
import net.blay09.mods.balm.neoforge.client.rendering.NeoForgeBalmModels;
import net.blay09.mods.balm.neoforge.client.rendering.NeoForgeBalmRenderers;
import net.blay09.mods.balm.neoforge.client.rendering.NeoForgeBalmTextures;
import net.blay09.mods.balm.neoforge.client.screen.NeoForgeBalmScreens;
import net.blay09.mods.balm.neoforge.event.NeoForgeBalmClientEvents;
import net.blay09.mods.balm.neoforge.event.NeoForgeBalmEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;

public class NeoForgeBalmClientRuntime extends CommonBalmClientRuntime<NeoForgeLoadContext> {
   private final NamespaceResolver legacyNamespaceResolver = new LegacyNamespaceResolver(() -> ModLoadingContext.get().getActiveNamespace());
   private final BalmRenderers renderers = new NeoForgeBalmRenderers(this.legacyNamespaceResolver);
   @Deprecated(forRemoval = true, since = "1.21.5")
   private final BalmTextures textures = new NeoForgeBalmTextures();
   private final BalmScreens screens = new NeoForgeBalmScreens(this.legacyNamespaceResolver);
   private final BalmKeyMappings keyMappings = new NeoForgeBalmKeyMappings(this.legacyNamespaceResolver);
   private final BalmModels models = new NeoForgeBalmModels(this.legacyNamespaceResolver);

   public NeoForgeBalmClientRuntime() {
      NeoForgeBalmClientEvents.registerEvents((NeoForgeBalmEvents)Balm.getEvents());
   }

   @Override
   public BalmRenderers getRenderers() {
      return this.renderers;
   }

   @Deprecated(forRemoval = true, since = "1.21.5")
   @Override
   public BalmTextures getTextures() {
      return this.textures;
   }

   @Override
   public BalmScreens getScreens() {
      return this.screens;
   }

   @Override
   public BalmModels getModels() {
      return this.models;
   }

   @Override
   public BalmKeyMappings getKeyMappings() {
      return this.keyMappings;
   }

   public void initializeMod(String modId, NeoForgeLoadContext context, Runnable initializer) {
      BalmLoadContexts.register(modId, context);
      initializer.run();
      IEventBus modEventBus = context.modBus();
      ModBusEventRegisters.register(modId, modEventBus);
   }

   @Override
   public void addResourceReloadListener(ResourceLocation identifier, PreparableReloadListener reloadListener) {
      if (Minecraft.getInstance().getResourceManager() instanceof ReloadableResourceManager reloadableResourceManager) {
         reloadableResourceManager.registerReloadListener(reloadListener);
      }
   }
}
