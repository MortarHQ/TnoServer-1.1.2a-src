package xaero.common.minimap.render.radar;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import xaero.hud.minimap.MinimapLogs;
import xaero.hud.minimap.radar.icon.definition.BuiltInRadarIconDefinitions;

@Deprecated
public class EntityIconDefinitions {
   private static StringBuilder VARIANT_STRING_BUILDER = new StringBuilder();

   public static <E extends Entity> Object getVariant(ResourceLocation entityTexture, EntityRenderer<? super E> entityRenderer, E entity) {
      return BuiltInRadarIconDefinitions.getVariant(entityTexture, entityRenderer, entity);
   }

   public static void buildVariantIdString(StringBuilder stringBuilder, EntityRenderer entityRenderer, Entity entity) {
      ResourceLocation entityTexture = null;

      try {
         ResourceLocation entityTextureUnchecked = entityRenderer.getTextureLocation(entity);
         entityTexture = entityTextureUnchecked;
      } catch (Throwable e) {
         MinimapLogs.LOGGER.error("Exception while fetching entity texture to build its variant ID for " + EntityType.getKey(entity.getType()));
         MinimapLogs.LOGGER
            .error(
               "The exception is most likely on another mod's end and suppressing it here could lead to more issues. Please report to appropriate mod devs.", e
            );
      }

      if (entityTexture != null) {
         stringBuilder.append(getVariant(entityTexture, entityRenderer, entity));
      }
   }

   public static String getVariantString(EntityRenderer entityRenderer, Entity entity) {
      StringBuilder stringBuilder = VARIANT_STRING_BUILDER;
      stringBuilder.setLength(0);
      buildVariantIdString(stringBuilder, entityRenderer, entity);
      return stringBuilder.toString();
   }
}
