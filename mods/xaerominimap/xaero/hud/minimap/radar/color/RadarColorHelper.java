package xaero.hud.minimap.radar.color;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.scores.Team;
import xaero.hud.minimap.radar.category.EntityRadarCategory;
import xaero.hud.minimap.radar.category.setting.EntityRadarCategorySettings;
import xaero.hud.minimap.radar.state.RadarList;

public class RadarColorHelper {
   public int getTeamColor(Entity e) {
      Integer teamColor = null;
      Team team = e.getTeam();
      if (team != null) {
         teamColor = team.getColor().getColor();
      }

      return teamColor == null ? -1 : teamColor;
   }

   public int getEntityColor(
      Entity entity, float offY, boolean cave, int heightLimit, int startFadingAt, boolean heightBasedFade, RadarColor radarColor, RadarColor fallbackColor
   ) {
      int color = this.getRadarColorHex(entity, radarColor, fallbackColor);
      float heightFade = heightBasedFade ? this.getEntityHeightFade(offY, heightLimit, startFadingAt) : 1.0F;
      if (heightFade >= 1.0F) {
         return color;
      }

      int red = color >> 16 & 0xFF;
      int green = color >> 8 & 0xFF;
      int blue = color & 0xFF;
      int alpha = 255;
      if (cave) {
         alpha = (int)(alpha * heightFade);
      } else {
         red = (int)(red * heightFade);
         green = (int)(green * heightFade);
         blue = (int)(blue * heightFade);
      }

      return alpha << 24 | red << 16 | green << 8 | blue;
   }

   private int getRadarColorHex(Entity entity, RadarColor radarColor, RadarColor fallbackColor) {
      if (radarColor != null) {
         return radarColor.getHex();
      }

      int entityTeamColour = this.getTeamColor(entity);
      return entityTeamColour != -1 ? 0xFF000000 | entityTeamColour : fallbackColor.getHex();
   }

   public RadarColor getFallbackColor(RadarList radarList) {
      return this.getFallbackColor(radarList.getClientCategory(), radarList.getSyncedCategory());
   }

   public RadarColor getFallbackColor(EntityRadarCategory category, EntityRadarCategory syncedCategory) {
      EntityRadarCategory fallbackCategory = syncedCategory == null ? category : syncedCategory;

      while (true) {
         EntityRadarCategory superCategory = fallbackCategory.getSuperCategory();
         if (superCategory == null) {
            if (fallbackCategory != syncedCategory) {
               return RadarColor.WHITE;
            }

            superCategory = category;
         }

         fallbackCategory = superCategory;
         Double categorySettingValue = fallbackCategory.getSettingValue(EntityRadarCategorySettings.COLOR);
         if (categorySettingValue != null) {
            int colorSetting = categorySettingValue.intValue();
            if (colorSetting != -1) {
               return RadarColor.fromIndex(colorSetting);
            }
         }
      }
   }

   public float getEntityHeightFade(float offY, int heightLimit, int startFadingAt) {
      float level = heightLimit - offY;
      if (level < 0.0F) {
         level = 0.0F;
      }

      float brightness = 1.0F;
      int threshold = startFadingAt == 0 ? heightLimit * 3 / 4 : heightLimit - startFadingAt;
      if (level <= threshold) {
         brightness = 0.25F + 0.5F * level / threshold;
      }

      return brightness;
   }
}
