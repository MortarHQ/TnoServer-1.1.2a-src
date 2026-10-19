package dev.ftb.mods.ftblibrary.icon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.architectury.registry.registries.RegistrarManager;
import dev.ftb.mods.ftblibrary.FTBLibrary;
import dev.ftb.mods.ftblibrary.util.ModUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.Enemy;
import org.slf4j.Logger;

public class EntityIconLoader extends SimplePreparableReloadListener<Map<EntityType<?>, EntityIconLoader.EntityIconSettings>> {
   public static final Icon NORMAL = Icon.getIcon("ftblibrary:textures/faces/normal.png");
   public static final Icon HOSTILE = Icon.getIcon("ftblibrary:textures/faces/hostile.png");
   private static final Logger LOGGER = LogUtils.getLogger();
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
   private static final Map<EntityType<?>, Map<ResourceLocation, Icon>> ICON_CACHE = new HashMap<>();
   private static final Map<EntityType<?>, EntityIconLoader.EntityIconSettings> ENTITY_SETTINGS = new HashMap<>();
   private static final Set<EntityType<?>> DYNAMIC_JSON_TEXTURES = new HashSet<>();

   protected Map<EntityType<?>, EntityIconLoader.EntityIconSettings> prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
      Map<EntityType<?>, EntityIconLoader.EntityIconSettings> map = new HashMap<>();
      DYNAMIC_JSON_TEXTURES.clear();

      for (Entry<ResourceKey<EntityType<?>>, EntityType<?>> entry : RegistrarManager.get("ftblibrary").get(Registries.ENTITY_TYPE).entrySet()) {
         ResourceLocation id = entry.getKey().location();
         EntityType<?> entityType = entry.getValue();
         String basePath = getBasePath(id);
         ResourceLocation invisible = FTBLibrary.rl(basePath + ".invisible");
         EntityIconLoader.EntityIconSettings entityIconSettings = null;
         if (resourceManager.getResource(invisible).isPresent()) {
            LOGGER.error("Entity {} is using legacy invisible texture, please update it to use the new system!", id);
            entityIconSettings = EntityIconLoader.EntityIconSettings.OLD_HIDDEN;
         }

         Optional<Resource> resource = resourceManager.getResource(FTBLibrary.rl(basePath + ".json"));
         if (resource.isPresent()) {
            entityIconSettings = this.loadEntitySetting(id, resource.get());
            DYNAMIC_JSON_TEXTURES.add(entityType);
         } else {
            ResourceLocation imgLoc = FTBLibrary.rl(basePath + ".png");
            if (resourceManager.getResource(imgLoc).isPresent()) {
               entityIconSettings = EntityIconLoader.EntityIconSettings.forImage(imgLoc);
            }
         }

         if (entityIconSettings == null && entityType.getCategory() != MobCategory.MISC) {
            if (ModUtils.isDevMode()) {
               LOGGER.error("Missing entity icon settings for {}", id);
            }

            entityIconSettings = EntityIconLoader.EntityIconSettings.legacy();
         }

         map.put(entityType, entityIconSettings);
      }

      return map;
   }

   protected void apply(Map<EntityType<?>, EntityIconLoader.EntityIconSettings> entityIconDataMap, ResourceManager resourceManager, ProfilerFiller profiler) {
      ICON_CACHE.clear();
      ENTITY_SETTINGS.clear();
      ENTITY_SETTINGS.putAll(entityIconDataMap);
      FTBLibrary.LOGGER.debug("loaded {} entity face icons ({} using dynamic json textures)", entityIconDataMap.size(), DYNAMIC_JSON_TEXTURES.size());
   }

   public static boolean isDynamicTexture(EntityType<?> type) {
      return DYNAMIC_JSON_TEXTURES.contains(type);
   }

   private static String getBasePath(ResourceLocation id) {
      return "textures/faces/" + id.getNamespace() + "/" + id.getPath();
   }

   private EntityIconLoader.EntityIconSettings loadEntitySetting(ResourceLocation id, Resource resource) {
      try {
         JsonElement jsonElement = (JsonElement)GsonHelper.fromJson(GSON, resource.openAsReader(), JsonElement.class);
         DataResult<EntityIconLoader.EntityIconSettings> settings = EntityIconLoader.EntityIconSettings.CODEC.parse(JsonOps.INSTANCE, jsonElement);
         return (EntityIconLoader.EntityIconSettings)settings.getOrThrow();
      } catch (IOException e) {
         LOGGER.error("Failed to load entity icon settings for {}", id, e);
      } catch (IllegalStateException e) {
         LOGGER.error("Failed to parse entity icon settings for {}", id, e);
      }

      return null;
   }

   private static Optional<Icon> getIconCache(Entity entity) {
      return getSettings(entity.getType())
         .map(
            settings -> settings.useMobTexture
               ? getOrCreateIcon(entity.getType(), Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity).getTextureLocation(entity), settings)
               : settings.texture.<Icon>map(resourceLocation -> getOrCreateIcon(entity.getType(), resourceLocation, settings)).orElse(null)
         );
   }

   public static Optional<EntityIconLoader.EntityIconSettings> getSettings(EntityType<?> entityType) {
      return Optional.ofNullable(ENTITY_SETTINGS.get(entityType));
   }

   private static Icon getOrCreateIcon(EntityType<?> entityType, ResourceLocation texture, EntityIconLoader.EntityIconSettings settings) {
      return ICON_CACHE.computeIfAbsent(entityType, i -> new HashMap<>())
         .computeIfAbsent(texture, t -> new EntityImageIcon(t, settings.mainSlice.orElse(null), settings.children, settings.defaultImageSize.orElse(null)));
   }

   public static Icon getIcon(Entity entity) {
      return getIconCache(entity).orElseGet(() -> entity instanceof Enemy ? HOSTILE : NORMAL);
   }

   public static Icon getIcon(EntityType<?> entityType) {
      Entity entity = entityType.create(Minecraft.getInstance().level);
      return entity == null ? NORMAL : getIcon(entity);
   }

   public record EntityIconSettings(
      boolean useMobTexture,
      Optional<ResourceLocation> texture,
      Optional<EntityImageIcon.Slice> mainSlice,
      List<EntityImageIcon.ChildIconData> children,
      EntityIconLoader.WidthHeight widthHeight,
      Optional<EntityIconLoader.WidthHeight> defaultImageSize,
      double scale,
      boolean defaultEnabled
   ) {
      private static final EntityIconLoader.EntityIconSettings OLD_HIDDEN = new EntityIconLoader.EntityIconSettings(
         false, Optional.empty(), Optional.empty(), List.of(), EntityIconLoader.WidthHeight.DEFAULT, Optional.empty(), 1.0, true
      );
      public static final Codec<EntityIconLoader.EntityIconSettings> CODEC = RecordCodecBuilder.create(
            builder -> builder.group(
                  Codec.BOOL.optionalFieldOf("use_mob_texture", false).forGetter(s -> s.useMobTexture),
                  ResourceLocation.CODEC.optionalFieldOf("texture").forGetter(s -> s.texture),
                  EntityImageIcon.Slice.CODEC.optionalFieldOf("slice").forGetter(entityIconData -> entityIconData.mainSlice),
                  EntityImageIcon.ChildIconData.CODEC.listOf().optionalFieldOf("children", List.of()).forGetter(entityIconData -> entityIconData.children),
                  EntityIconLoader.WidthHeight.CODEC.optionalFieldOf("size", EntityIconLoader.WidthHeight.DEFAULT).forGetter(s -> s.widthHeight),
                  EntityIconLoader.WidthHeight.CODEC.optionalFieldOf("default_image_size").forGetter(s -> s.defaultImageSize),
                  Codec.DOUBLE.optionalFieldOf("scale", 1.0).forGetter(s -> s.scale),
                  Codec.BOOL.optionalFieldOf("default_enabled", true).forGetter(s -> s.defaultEnabled)
               )
               .apply(builder, EntityIconLoader.EntityIconSettings::new)
         )
         .validate(
            settings -> settings.texture().isEmpty() && !settings.useMobTexture
               ? DataResult.error(() -> "Texture is required if use_mob_texture is false")
               : DataResult.success(settings)
         );

      public static EntityIconLoader.EntityIconSettings forImage(ResourceLocation imgLoc) {
         return new EntityIconLoader.EntityIconSettings(
            false, Optional.of(imgLoc), Optional.empty(), List.of(), EntityIconLoader.WidthHeight.DEFAULT, Optional.empty(), 1.0, true
         );
      }

      public static EntityIconLoader.EntityIconSettings legacy() {
         return OLD_HIDDEN;
      }
   }

   public record WidthHeight(int width, int height) {
      public static final EntityIconLoader.WidthHeight DEFAULT = new EntityIconLoader.WidthHeight(16, 16);
      public static final Codec<EntityIconLoader.WidthHeight> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               Codec.INT.fieldOf("width").forGetter(EntityIconLoader.WidthHeight::width),
               Codec.INT.fieldOf("height").forGetter(EntityIconLoader.WidthHeight::height)
            )
            .apply(instance, EntityIconLoader.WidthHeight::new)
      );
   }
}
