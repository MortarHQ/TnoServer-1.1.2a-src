package noobanidus.mods.lootr.common.api.client;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public interface ILootrFabricModelProvider {
   void provideModels(ILootrFabricModelProvider.Acceptor var1);

   interface Acceptor {
      void acceptCustomModel(ResourceLocation var1, ResourceLocation var2, ResourceLocation var3, @Nullable ResourceLocation var4);

      @Deprecated
      default void acceptBarrelModel(
         ResourceLocation modelName,
         ResourceLocation modelOpenedLocation,
         ResourceLocation modelUnopenedLocation,
         @Nullable ResourceLocation modelVanillaLocation
      ) {
         this.acceptCustomModel(modelName, modelOpenedLocation, modelUnopenedLocation, modelVanillaLocation);
      }

      void acceptBrushableModel(
         ResourceLocation var1, ResourceLocation var2, ResourceLocation var3, ResourceLocation var4, ResourceLocation var5, ResourceLocation var6
      );
   }
}
