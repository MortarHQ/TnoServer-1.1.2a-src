package xaero.hud.minimap.world;

import net.minecraft.ResourceLocationException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import xaero.hud.minimap.world.container.MinimapWorldRootContainer;
import xaero.lib.common.util.IOUtils;

public class MinimapDimensionHelper {
   public double getDimensionDivision(MinimapWorld minimapWorld) {
      if (Minecraft.getInstance().level == null) {
         return 1.0;
      }

      double dimCoordinateScale = this.getDimCoordinateScale(minimapWorld);
      return Minecraft.getInstance().level.dimensionType().coordinateScale() / dimCoordinateScale;
   }

   public double getDimCoordinateScale(MinimapWorld minimapWorld) {
      if (minimapWorld == null) {
         return 1.0;
      }

      MinimapWorldRootContainer rootContainer = minimapWorld.getContainer().getRoot();
      ResourceKey<Level> dimKey = minimapWorld.getDimId();
      return dimKey == null ? 1.0 : rootContainer.getDimensionScale(dimKey);
   }

   public String getDimensionDirectoryName(ResourceKey<Level> dimKey) {
      if (dimKey == Level.OVERWORLD) {
         return "dim%0";
      }

      if (dimKey == Level.NETHER) {
         return "dim%-1";
      }

      if (dimKey == Level.END) {
         return "dim%1";
      }

      ResourceLocation identifier = dimKey.location();
      String path = identifier.getPath().replace('/', '%');
      path = IOUtils.replaceTrailingDots(path, ',');
      return "dim%" + identifier.getNamespace() + "$" + path;
   }

   public ResourceKey<Level> findDimensionKeyForOldName(LocalPlayer player, String oldName) {
      for (ResourceKey<Level> dk : player.connection.levels()) {
         if (oldName.equals(dk.location().getPath().replaceAll("[^a-zA-Z0-9_]+", ""))) {
            return dk;
         }
      }

      return null;
   }

   public ResourceKey<Level> getDimensionKeyForDirectoryName(String dirName) {
      String dimIdPart = dirName.substring(4);
      if (dimIdPart.equals("0")) {
         return Level.OVERWORLD;
      }

      if (dimIdPart.equals("1")) {
         return Level.END;
      }

      if (dimIdPart.equals("-1")) {
         return Level.NETHER;
      }

      String[] idArgs = dimIdPart.split("\\$");
      if (idArgs.length < 2) {
         return null;
      }

      String path = idArgs[1].replace('%', '/');
      path = path.replace(',', '.');

      try {
         return ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(idArgs[0], path));
      } catch (ResourceLocationException rle) {
         return null;
      }
   }
}
