package me.decce.gnetum;

import com.mojang.blaze3d.platform.InputConstants.Type;
import java.util.HashMap;
import java.util.List;
import me.decce.gnetum.compat.embeddium.GnetumEmbeddiumCompat;
import me.decce.gnetum.gui.ConfigScreen;
import me.decce.gnetum.util.AnyBooleanValue;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLModContainer;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingIn;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent.DebugText;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.Lazy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.embeddedt.embeddium.api.OptionGUIConstructionEvent;

@Mod(value = "gnetum", dist = Dist.CLIENT)
public final class Gnetum {
   public static final Logger LOGGER = LogManager.getLogger();
   public static final String MOD_ID = "gnetum";
   public static final String HAND_ELEMENT = "gnetum:minecraft_hand";
   public static final String OTHER_MODS = "gnetum_unknown";
   public static final FpsCounter FPS_COUNTER = new FpsCounter();
   public static GnetumConfig config;
   public static PassManager passManager;
   public static UncachedElements uncachedElements;
   public static String currentElement;
   public static ElementType currentElementType;
   public static long lastSwapNanos;
   public static boolean rendering;
   public static boolean renderingCanceled;
   public static final Lazy<KeyMapping> CONFIG_MAPPING = Lazy.of(
      () -> new KeyMapping("gnetum.config.keyMapping", KeyConflictContext.IN_GAME, Type.KEYSYM, 269, "key.categories.misc")
   );

   public Gnetum(FMLModContainer container, IEventBus modBus, Dist dist) {
      modBus.addListener(Gnetum::registerBindings);
      NeoForge.EVENT_BUS.addListener(this::onClientTick);
      NeoForge.EVENT_BUS.addListener(this::onCustomizeF3Text);
      NeoForge.EVENT_BUS.addListener(this::onPlayerJoin);
      if (ModList.get().isLoaded("embeddium")) {
         OptionGUIConstructionEvent.BUS.addListener(GnetumEmbeddiumCompat::onSodiumPagesRegister);
      }

      container.registerExtensionPoint(IConfigScreenFactory.class, new GnetumConfigScreenFactory());
   }

   public static GnetumConfig getConfig() {
      if (config == null) {
         ensureInitialized();
      }

      return config;
   }

   public static void ensureInitialized() {
      if (passManager == null) {
         passManager = new PassManager();
         uncachedElements = new UncachedElements();
      }

      GnetumConfig.reload();
   }

   public static CacheSetting getCacheSetting(String vanillaOverlay) {
      vanillaOverlay = PackedVanillaElements.consider(vanillaOverlay);
      if (!config.mapVanillaElements.containsKey(vanillaOverlay)) {
         config.mapVanillaElements.put(vanillaOverlay, new CacheSetting(SuggestedPass.get(vanillaOverlay)));
      }

      return config.mapVanillaElements.get(vanillaOverlay);
   }

   public static CacheSetting getCacheSetting(String moddedOverlay, ElementType type) {
      if (type == ElementType.VANILLA) {
         return getCacheSetting(moddedOverlay);
      }

      HashMap<String, CacheSetting> map = type == ElementType.PRE ? config.mapModdedElementsPre : config.mapModdedElementsPost;
      if (!map.containsKey(moddedOverlay)) {
         map.put(moddedOverlay, new CacheSetting(type == ElementType.PRE ? 1 : config.numberOfPasses));
      }

      return map.get(moddedOverlay);
   }

   public static void disableCachingForCurrentElement(String reason) {
      if (currentElement != null && currentElementType != null) {
         CacheSetting cacheSetting = getCacheSetting(currentElement, currentElementType);
         if (cacheSetting.enabled.get() && cacheSetting.enabled.value == AnyBooleanValue.AUTO) {
            LOGGER.info("Disabling caching for element {}. Reason: {}", currentElement, reason);
            cacheSetting.enabled.defaultValue = false;
            FramebufferManager.getInstance().dropCurrentFrame();
         }
      }
   }

   public static void registerBindings(RegisterKeyMappingsEvent event) {
      event.register((KeyMapping)CONFIG_MAPPING.get());
   }

   public void onClientTick(Post event) {
      while (((KeyMapping)CONFIG_MAPPING.get()).consumeClick()) {
         if (!(Minecraft.getInstance().screen instanceof ConfigScreen)) {
            Minecraft.getInstance().setScreen(new ConfigScreen(PerformanceAnalyzer.analyze()));
         }
      }
   }

   public void onCustomizeF3Text(DebugText event) {
      if (config.isEnabled() && config.showHudFps.get() && Minecraft.getInstance().getDebugOverlay().showDebugScreen()) {
         List<String> left = event.getLeft();
         if (left.size() > 2) {
            event.getLeft()
               .add(
                  2,
                  String.format(
                     "HUD: %d fps (%d passes, max %s)",
                     FPS_COUNTER.getFps(),
                     config.numberOfPasses,
                     config.getMaxFps() == 125 ? "unlimited" : config.getMaxFps()
                  )
               );
         }
      }
   }

   public void onPlayerJoin(LoggingIn event) {
      ensureInitialized();
      FPS_COUNTER.reset();
      FramebufferManager.getInstance().reset();
      PackedVanillaElements.reset();
   }
}
