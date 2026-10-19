package net.blay09.mods.balm.api;

import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import net.blay09.mods.balm.api.block.BalmBlockEntities;
import net.blay09.mods.balm.api.block.BalmBlocks;
import net.blay09.mods.balm.api.capability.BalmCapabilities;
import net.blay09.mods.balm.api.command.BalmCommands;
import net.blay09.mods.balm.api.compat.BalmModSupport;
import net.blay09.mods.balm.api.component.BalmComponents;
import net.blay09.mods.balm.api.config.BalmConfig;
import net.blay09.mods.balm.api.entity.BalmEntities;
import net.blay09.mods.balm.api.event.BalmEvents;
import net.blay09.mods.balm.api.item.BalmItems;
import net.blay09.mods.balm.api.loot.BalmLootTables;
import net.blay09.mods.balm.api.menu.BalmMenus;
import net.blay09.mods.balm.api.module.BalmModule;
import net.blay09.mods.balm.api.network.BalmNetworking;
import net.blay09.mods.balm.api.particle.BalmParticles;
import net.blay09.mods.balm.api.permission.BalmPermissions;
import net.blay09.mods.balm.api.provider.BalmProviders;
import net.blay09.mods.balm.api.proxy.ModProxy;
import net.blay09.mods.balm.api.proxy.PlatformProxy;
import net.blay09.mods.balm.api.proxy.SidedProxy;
import net.blay09.mods.balm.api.recipe.BalmRecipes;
import net.blay09.mods.balm.api.resources.BalmResources;
import net.blay09.mods.balm.api.sound.BalmSounds;
import net.blay09.mods.balm.api.stats.BalmStats;
import net.blay09.mods.balm.api.world.BalmWorldGen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;

public interface BalmRuntime<TLoadContext extends BalmRuntimeLoadContext> {
   BalmConfig getConfig();

   BalmEvents getEvents();

   BalmWorldGen getWorldGen();

   BalmBlocks getBlocks();

   BalmBlockEntities getBlockEntities();

   BalmItems getItems();

   BalmMenus getMenus();

   BalmNetworking getNetworking();

   BalmHooks getHooks();

   BalmRegistries getRegistries();

   BalmSounds getSounds();

   BalmEntities getEntities();

   BalmCapabilities getCapabilities();

   @Deprecated(forRemoval = true, since = "1.21.5")
   BalmProviders getProviders();

   BalmCommands getCommands();

   BalmLootTables getLootTables();

   BalmStats getStats();

   BalmRecipes getRecipes();

   BalmModSupport getModSupport();

   BalmParticles getParticles();

   BalmPermissions getPermissions();

   boolean isModLoaded(String var1);

   String getModName(String var1);

   <TProxy> SidedProxy<TProxy> sidedProxy(String var1, String var2);

   void initializeMod(String var1, TLoadContext var2, Runnable var3);

   void initializeIfLoaded(String var1, String var2);

   void addServerReloadListener(ResourceLocation var1, PreparableReloadListener var2);

   void addServerReloadListener(ResourceLocation var1, Consumer<ResourceManager> var2);

   BalmComponents getComponents();

   <T> PlatformProxy<T> platformProxy();

   <T> ModProxy<T> modProxy();

   String getPlatform();

   default void initializeModule(BalmModule module) {
      String modId = module.getId().getNamespace();
      module.registerConfig(this.getConfig());
      module.registerResources(this.getResources());
      module.registerAdditional(this.getRegistries());
      module.registerComponents(this.getComponents());
      module.registerBlocks(this.getBlocks().scoped(modId));
      module.registerBlockEntities(this.getBlockEntities());
      module.registerItems(this.getItems().scoped(modId));
      module.registerEntities(this.getEntities());
      module.registerWorldGen(this.getWorldGen());
      module.registerNetworking(this.getNetworking());
      module.registerMenus(this.getMenus());
      module.registerCapabilities(this.getCapabilities());
      module.registerCommands(this.getCommands());
      module.registerRecipes(this.getRecipes());
      module.registerLootTables(this.getLootTables());
      module.registerStats(this.getStats());
      module.registerSounds(this.getSounds());
      module.registerPermissions(this.getPermissions());
      module.registerParticles(this.getParticles());
      module.registerEvents(this.getEvents());
      module.initialize();
   }

   BalmProxy getProxy();

   boolean isReady();

   void onRuntimeAvailable(Runnable var1);

   void registerModule(BalmModule var1);

   BalmResources getResources();

   BalmEnvironment getEnvironment();

   boolean isDevelopmentEnvironment();

   Map<String, Path> lookupAllModPaths(String var1);

   Optional<Path> lookupModPath(String var1, String var2);
}
