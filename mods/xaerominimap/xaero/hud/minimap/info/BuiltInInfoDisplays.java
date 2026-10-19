package xaero.hud.minimap.info;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import xaero.hud.minimap.BuiltInHudModules;
import xaero.hud.minimap.common.config.option.MinimapProfiledConfigOptions;
import xaero.hud.minimap.info.widget.InfoDisplayCommonWidgetFactories;
import xaero.hud.minimap.info.widget.InfoDisplayCycleWidgetFactory;
import xaero.hud.minimap.module.MinimapSession;
import xaero.hud.minimap.world.MinimapWorld;
import xaero.hud.minimap.world.MinimapWorldManager;
import xaero.lib.common.config.option.value.io.serialization.BuiltInConfigValueIOCodecs;

public class BuiltInInfoDisplays {
   private static List<InfoDisplay<?>> ALL = new ArrayList<>();
   public static final InfoDisplay<Boolean> COORDINATES = InfoDisplay.Builder.<Boolean>begin()
      .setId("coords")
      .setName(Component.translatable("gui.xaero_infodisplay_coords"))
      .setDefaultState(true)
      .setCodec(BuiltInConfigValueIOCodecs.BOOLEAN)
      .setWidgetFactory(InfoDisplayCommonWidgetFactories.OFF_ON)
      .setCompiler((displayInfo, compiler, session, availableWidth, playerPos) -> {
         if ((Boolean)displayInfo.getEffectiveState()) {
            String coords = playerPos.getX() + ", " + playerPos.getY() + ", " + playerPos.getZ();
            if (Minecraft.getInstance().font.width(coords) >= availableWidth) {
               String stringLevel = playerPos.getY() + "";
               coords = playerPos.getX() + ", " + playerPos.getZ();
               compiler.addLine(coords);
               compiler.addLine(stringLevel);
            } else {
               compiler.addLine(coords);
            }
         }
      })
      .setLegacyGetter(legacySettings -> {
         Boolean result = legacySettings.showCoordsLegacy;
         legacySettings.showCoordsLegacy = null;
         return result;
      })
      .setDestination(ALL::add)
      .build();
   public static final InfoDisplay<Boolean> OVERWORLD_COORDINATES = InfoDisplay.Builder.<Boolean>begin()
      .setId("overworld_coords")
      .setName(Component.translatable("gui.xaero_infodisplay_overworld_coords"))
      .setDefaultState(false)
      .setCodec(BuiltInConfigValueIOCodecs.BOOLEAN)
      .setWidgetFactory(InfoDisplayCommonWidgetFactories.OFF_ON)
      .setCompiler((displayInfo, compiler, session, availableWidth, playerPos) -> {
         if ((Boolean)displayInfo.getEffectiveState()) {
            Minecraft mc = Minecraft.getInstance();
            double coordinateScale = mc.level.dimensionType().coordinateScale();
            if (coordinateScale != 1.0) {
               int overworldPlayerX = Mth.floor(playerPos.getX() * coordinateScale);
               int overworldPlayerZ = Mth.floor(playerPos.getZ() * coordinateScale);
               String coords = "Xo: " + overworldPlayerX + ", Zo: " + overworldPlayerZ;
               compiler.addWords(coords);
            }
         }
      })
      .setDestination(ALL::add)
      .build();
   public static final InfoDisplay<Boolean> CHUNK_COORDINATES = InfoDisplay.Builder.<Boolean>begin()
      .setId("chunk_coords")
      .setName(Component.translatable("gui.xaero_infodisplay_chunk_coords"))
      .setDefaultState(false)
      .setCodec(BuiltInConfigValueIOCodecs.BOOLEAN)
      .setWidgetFactory(InfoDisplayCommonWidgetFactories.OFF_ON)
      .setCompiler((displayInfo, compiler, session, availableWidth, playerPos) -> {
         if ((Boolean)displayInfo.getEffectiveState()) {
            int chunkX = playerPos.getX() >> 4;
            int chunkZ = playerPos.getZ() >> 4;
            int insideX = playerPos.getX() & 15;
            int insideZ = playerPos.getZ() & 15;
            String coords = "C " + chunkX + ", " + chunkZ + " (" + insideX + "; " + insideZ + ")";
            compiler.addWords(coords);
         }
      })
      .setDestination(ALL::add)
      .build();
   public static final InfoDisplay<Boolean> ANGLES = InfoDisplay.Builder.<Boolean>begin()
      .setId("angles")
      .setName(Component.translatable("gui.xaero_infodisplay_angles"))
      .setDefaultState(false)
      .setCodec(BuiltInConfigValueIOCodecs.BOOLEAN)
      .setWidgetFactory(InfoDisplayCommonWidgetFactories.OFF_ON)
      .setCompiler(
         (displayInfo, compiler, session, availableWidth, playerPos) -> {
            if ((Boolean)displayInfo.getEffectiveState()) {
               Minecraft mc = Minecraft.getInstance();
               compiler.addLine(
                  Component.literal(
                     String.format("%.1f / %.1f", Mth.wrapDegrees(mc.getCameraEntity().getYRot()), Mth.wrapDegrees(mc.getCameraEntity().getXRot()))
                  )
               );
            }
         }
      )
      .setLegacyGetter(legacySettings -> {
         Boolean result = legacySettings.showAnglesLegacy;
         legacySettings.showAnglesLegacy = null;
         return result;
      })
      .setDestination(ALL::add)
      .build();
   public static final InfoDisplay<Boolean> DIMENSION = InfoDisplay.Builder.<Boolean>begin()
      .setId("dimension")
      .setName(Component.translatable("gui.xaero_infodisplay_dimension"))
      .setDefaultState(false)
      .setCodec(BuiltInConfigValueIOCodecs.BOOLEAN)
      .setWidgetFactory(InfoDisplayCommonWidgetFactories.OFF_ON)
      .setCompiler((displayInfo, compiler, session, availableWidth, playerPos) -> {
         if ((Boolean)displayInfo.getEffectiveState()) {
            ResourceLocation dimensionId = Minecraft.getInstance().level.dimension().location();
            if (dimensionId != null) {
               String dimensionName = dimensionId.getNamespace().equals("minecraft") ? dimensionId.getPath() : dimensionId.toString();
               compiler.addLine(dimensionName);
            }
         }
      })
      .setLegacyGetter(legacySettings -> {
         Boolean result = legacySettings.showDimensionNameLegacy;
         legacySettings.showDimensionNameLegacy = null;
         return result;
      })
      .setDestination(ALL::add)
      .build();
   public static final InfoDisplay<Boolean> BIOME = InfoDisplay.Builder.<Boolean>begin()
      .setId("biome")
      .setName(Component.translatable("gui.xaero_infodisplay_biome"))
      .setDefaultState(false)
      .setCodec(BuiltInConfigValueIOCodecs.BOOLEAN)
      .setWidgetFactory(InfoDisplayCommonWidgetFactories.OFF_ON)
      .setCompiler(
         (displayInfo, compiler, session, availableWidth, playerPos) -> {
            if ((Boolean)displayInfo.getEffectiveState()) {
               Holder<Biome> biomeHolder = Minecraft.getInstance().level.getBiome(playerPos);
               Biome biome = biomeHolder == null ? null : (Biome)biomeHolder.value();
               ResourceLocation biomeRL = biome == null ? null : Minecraft.getInstance().level.registryAccess().registryOrThrow(Registries.BIOME).getKey(biome);
               String biomeText = biomeRL == null
                  ? I18n.get("gui.xaero_unknown_biome", new Object[0])
                  : I18n.get("biome." + biomeRL.getNamespace() + "." + biomeRL.getPath(), new Object[0]);
               compiler.addWords(biomeText);
            }
         }
      )
      .setLegacyGetter(legacySettings -> {
         Boolean result = legacySettings.showBiomeLegacy;
         legacySettings.showBiomeLegacy = null;
         return result;
      })
      .setDestination(ALL::add)
      .build();
   public static final InfoDisplay<Boolean> WEATHER = InfoDisplay.Builder.<Boolean>begin()
      .setId("weather")
      .setName(Component.translatable("gui.xaero_infodisplay_weather"))
      .setDefaultState(false)
      .setCodec(BuiltInConfigValueIOCodecs.BOOLEAN)
      .setWidgetFactory(InfoDisplayCommonWidgetFactories.OFF_ON)
      .setCompiler((displayInfo, compiler, session, availableWidth, playerPos) -> {
         if ((Boolean)displayInfo.getEffectiveState()) {
            if (Minecraft.getInstance().level.isThundering()) {
               compiler.addLine(Component.translatable("gui.xaero_weather_thundering"));
            } else if (Minecraft.getInstance().level.isRaining()) {
               compiler.addLine(Component.translatable("gui.xaero_weather_raining"));
            }
         }
      })
      .setLegacyGetter(legacySettings -> {
         Boolean result = legacySettings.displayWeatherInfoLegacy;
         legacySettings.displayWeatherInfoLegacy = null;
         return result;
      })
      .setDestination(ALL::add)
      .build();
   public static final InfoDisplay<Integer> LIGHT_LEVEL = InfoDisplay.Builder.<Integer>begin()
      .setId("light_level")
      .setName(Component.translatable("gui.xaero_infodisplay_light_level"))
      .setDefaultState(0)
      .setCodec(BuiltInConfigValueIOCodecs.INTEGER)
      .setWidgetFactory(
         new InfoDisplayCycleWidgetFactory<>(
            Lists.newArrayList(new Integer[]{0, 1, 2, 3, 4}),
            Lists.newArrayList(
               new Component[]{
                  Component.translatable("gui.xaero_off"),
                  Component.translatable("gui.xaero_light_block"),
                  Component.translatable("gui.xaero_light_sky"),
                  Component.translatable("gui.xaero_light_all"),
                  Component.translatable("gui.xaero_light_both2")
               }
            )
         )
      )
      .setCompiler((displayInfo, compiler, session, availableWidth, playerPos) -> {
         int showLightLevel = displayInfo.getEffectiveState();
         if (showLightLevel != 0) {
            Minecraft mc = Minecraft.getInstance();
            int blockLight = mc.level.getBrightness(LightLayer.BLOCK, playerPos);
            int skyLight = mc.level.getBrightness(LightLayer.SKY, playerPos);
            if (showLightLevel == 1) {
               compiler.addLine(Component.translatable("gui.xaero_block_light_value", new Object[]{blockLight}));
            } else if (showLightLevel == 2) {
               compiler.addLine(Component.translatable("gui.xaero_sky_light_value", new Object[]{skyLight}));
            } else if (showLightLevel == 3) {
               compiler.addLine(Component.translatable("gui.xaero_all_light_value", new Object[]{Math.max(blockLight, skyLight)}));
            } else {
               compiler.addLine(Component.translatable("gui.xaero_both_light_value", new Object[]{blockLight, skyLight}));
            }
         }
      })
      .setLegacyGetter(legacySettings -> {
         Integer result = legacySettings.showLightLevelLegacy;
         legacySettings.showLightLevelLegacy = null;
         return result;
      })
      .setDestination(ALL::add)
      .build();
   public static final InfoDisplay<Integer> TIME = InfoDisplay.Builder.<Integer>begin()
      .setId("time")
      .setName(Component.translatable("gui.xaero_infodisplay_time"))
      .setDefaultState(0)
      .setCodec(BuiltInConfigValueIOCodecs.INTEGER)
      .setWidgetFactory(
         new InfoDisplayCycleWidgetFactory<>(
            Lists.newArrayList(new Integer[]{0, 1, 2, 3, 4}),
            Lists.newArrayList(
               new Component[]{
                  Component.translatable("gui.xaero_off"),
                  Component.translatable("%s+%s", new Object[]{Component.translatable("gui.xaero_day"), Component.translatable("gui.xaero_24h")}),
                  Component.translatable("%s+%s", new Object[]{Component.translatable("gui.xaero_day"), Component.translatable("gui.xaero_12h")}),
                  Component.translatable("gui.xaero_24h"),
                  Component.translatable("gui.xaero_12h")
               }
            )
         )
      )
      .setCompiler((displayInfo, compiler, session, availableWidth, playerPos) -> {
         int showTime = displayInfo.getEffectiveState();
         if (showTime > 0) {
            Minecraft mc = Minecraft.getInstance();
            long totalTime = 6000L + mc.level.getDayTime();
            int dayNumber = (int)(totalTime / 24000L) + 1;
            int dayTimeSinceMidnight = (int)(totalTime % 24000L);
            int timeHours = dayTimeSinceMidnight / 1000;
            int minutes = (int)(dayTimeSinceMidnight % 1000 / 1000.0 * 60.0);
            if ((showTime - 1) % 2 == 0) {
               if (showTime < 3) {
                  compiler.addWords(I18n.get("gui.xaero_day", new Object[0]) + String.format(" %d, %02d:%02d", dayNumber, timeHours, minutes));
               } else {
                  compiler.addWords(String.format("%02d:%02d", timeHours, minutes));
               }
            } else {
               String half = "AM";
               if (timeHours >= 12) {
                  timeHours -= 12;
                  half = "PM";
               }

               if (timeHours == 0) {
                  timeHours = 12;
               }

               if (showTime < 3) {
                  compiler.addWords(I18n.get("gui.xaero_day", new Object[0]) + String.format(" %d, %02d:%02d %s", dayNumber, timeHours, minutes, half));
               } else {
                  compiler.addWords(String.format("%02d:%02d %s", timeHours, minutes, half));
               }
            }
         }
      })
      .setLegacyGetter(legacySettings -> {
         Integer result = legacySettings.showTimeLegacy;
         legacySettings.showTimeLegacy = null;
         return result;
      })
      .setDestination(ALL::add)
      .build();
   private static final Calendar CALENDAR = Calendar.getInstance();
   public static final InfoDisplay<Integer> REAL_TIME = InfoDisplay.Builder.<Integer>begin()
      .setId("real_time")
      .setName(Component.translatable("gui.xaero_infodisplay_real_time"))
      .setDefaultState(0)
      .setCodec(BuiltInConfigValueIOCodecs.INTEGER)
      .setWidgetFactory(
         new InfoDisplayCycleWidgetFactory<>(
            Lists.newArrayList(new Integer[]{0, 1, 2}),
            Lists.newArrayList(
               new Component[]{Component.translatable("gui.xaero_off"), Component.translatable("gui.xaero_24h"), Component.translatable("gui.xaero_12h")}
            )
         )
      )
      .setCompiler((displayInfo, compiler, session, availableWidth, playerPos) -> {
         int showTime = displayInfo.getEffectiveState();
         if (showTime > 0) {
            CALENDAR.setTimeInMillis(System.currentTimeMillis());
            int timeHours = CALENDAR.get(11);
            int minutes = CALENDAR.get(12);
            if (showTime == 1) {
               compiler.addWords(String.format("%02d:%02d IRL", timeHours, minutes));
            } else {
               String half = "AM";
               if (timeHours >= 12) {
                  timeHours -= 12;
                  half = "PM";
               }

               if (timeHours == 0) {
                  timeHours = 12;
               }

               compiler.addWords(String.format("%02d:%02d %s IRL", timeHours, minutes, half));
            }
         }
      })
      .setDestination(ALL::add)
      .build();
   public static final InfoDisplay<Boolean> HIGHLIGHTS = InfoDisplay.Builder.<Boolean>begin()
      .setId("highlights")
      .setName(Component.translatable("gui.xaero_infodisplay_highlights"))
      .setDefaultState(true)
      .setCodec(BuiltInConfigValueIOCodecs.BOOLEAN)
      .setWidgetFactory(InfoDisplayCommonWidgetFactories.ALWAYS_ON)
      .setCompiler(
         (displayInfo, compiler, session, availableWidth, playerPos) -> {
            if ((Boolean)displayInfo.getEffectiveState()) {
               if (session.getProcessor().getMinimapWriter().getDimensionHighlightHandler() != null) {
                  session.getProcessor()
                     .getMinimapWriter()
                     .getDimensionHighlightHandler()
                     .addBlockHighlightTooltips(compiler, playerPos.getX(), playerPos.getZ(), availableWidth, true);
               }
            }
         }
      )
      .setDestination(ALL::add)
      .build();
   public static final InfoDisplay<Boolean> LIGHT_OVERLAY_INDICATOR = InfoDisplay.Builder.<Boolean>begin()
      .setId("light_overlay_indicator")
      .setName(Component.translatable("gui.xaero_infodisplay_light_overlay_indicator"))
      .setDefaultState(true)
      .setCodec(BuiltInConfigValueIOCodecs.BOOLEAN)
      .setWidgetFactory(InfoDisplayCommonWidgetFactories.ALWAYS_ON)
      .setCompiler((displayInfo, compiler, session, availableWidth, playerPos) -> {
         if ((Boolean)displayInfo.getEffectiveState()) {
            if ((Integer)session.getModMain().getHudConfigs().getClientConfigManager().getEffective(MinimapProfiledConfigOptions.LIGHT_OVERLAY_TYPE) > 0) {
               compiler.addWords(I18n.get("gui.xaero_light_overlay_status", new Object[0]));
            }
         }
      })
      .setDestination(ALL::add)
      .build();
   public static final InfoDisplay<Boolean> MANUAL_CAVE_MODE_INDICATOR = InfoDisplay.Builder.<Boolean>begin()
      .setId("manual_cave_mode_indicator")
      .setName(Component.translatable("gui.xaero_infodisplay_manual_cave_mode_indicator"))
      .setDefaultState(true)
      .setCodec(BuiltInConfigValueIOCodecs.BOOLEAN)
      .setWidgetFactory(InfoDisplayCommonWidgetFactories.ALWAYS_ON)
      .setCompiler((displayInfo, compiler, session, availableWidth, playerPos) -> {
         if ((Boolean)displayInfo.getEffectiveState()) {
            if (session.getProcessor().isManualCaveMode()) {
               compiler.addWords(I18n.get("gui.xaero_manual_cave_mode", new Object[0]));
            }
         }
      })
      .setDestination(ALL::add)
      .build();
   public static final InfoDisplay<Boolean> CUSTOM_SUB_WORLD = InfoDisplay.Builder.<Boolean>begin()
      .setId("custom_sub_world")
      .setName(Component.translatable("gui.xaero_infodisplay_custom_sub_world"))
      .setDefaultState(true)
      .setCodec(BuiltInConfigValueIOCodecs.BOOLEAN)
      .setWidgetFactory(InfoDisplayCommonWidgetFactories.ALWAYS_ON)
      .setCompiler((displayInfo, compiler, hudSession, availableWidth, playerPos) -> {
         if ((Boolean)displayInfo.getEffectiveState()) {
            MinimapSession session = BuiltInHudModules.MINIMAP.getCurrentSession();
            MinimapWorldManager minimapWorldManager = session.getWorldManager();
            MinimapWorld currentWorld = minimapWorldManager.getCurrentWorld();
            if (currentWorld != null && minimapWorldManager.getAutoWorld() != currentWorld) {
               compiler.addWords(I18n.get("gui.xaero_using_custom_subworld", new Object[]{currentWorld.getContainer().getSubName()}));
            }
         }
      })
      .setDestination(ALL::add)
      .build();

   public static void forEach(Consumer<InfoDisplay<?>> action) {
      ALL.forEach(action);
   }
}
