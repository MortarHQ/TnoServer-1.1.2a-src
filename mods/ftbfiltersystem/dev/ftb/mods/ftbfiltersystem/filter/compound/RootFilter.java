package dev.ftb.mods.ftbfiltersystem.filter.compound;

import dev.ftb.mods.ftbfiltersystem.api.FTBFilterSystemAPI;
import java.util.ArrayList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.resources.ResourceLocation;

public class RootFilter extends AndFilter {
   private static final ResourceLocation ID = FTBFilterSystemAPI.rl("root");

   public RootFilter() {
      super(null, new ArrayList<>());
   }

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   @Override
   public String asString(Provider registryAccess) {
      return this.getStringArg(registryAccess);
   }
}
