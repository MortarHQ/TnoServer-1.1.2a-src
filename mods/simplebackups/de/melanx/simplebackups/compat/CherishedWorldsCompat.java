package de.melanx.simplebackups.compat;

import com.illusivesoulworks.cherishedworlds.client.favorites.FavoritesList;
import de.melanx.simplebackups.config.CommonConfig;
import net.neoforged.fml.ModList;

public class CherishedWorldsCompat {
   public static boolean isFavorite(String worldName) {
      return !CommonConfig.onlyFavorites() || FavoritesList.contains(worldName);
   }

   public static boolean isLoaded() {
      return ModList.get().isLoaded("cherishedworlds");
   }
}
