package dev.ftb.mods.ftblibrary.util.client.neoforge;

import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.FluidStackHooks;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;

public class ClientUtilsImpl {
   public static ResourceLocation getStillTexture(FluidStack stack) {
      net.neoforged.neoforge.fluids.FluidStack neoforgeStack = new net.neoforged.neoforge.fluids.FluidStack(
         Holder.direct(stack.getFluid()), (int)stack.getAmount(), stack.getPatch()
      );
      TextureAtlasSprite stillTexture = FluidStackHooks.getStillTexture(neoforgeStack.getFluid());
      return stillTexture == null ? null : stillTexture.contents().name();
   }

   public static int getFluidColor(FluidStack stack) {
      net.neoforged.neoforge.fluids.FluidStack forgeStack = new net.neoforged.neoforge.fluids.FluidStack(
         Holder.direct(stack.getFluid()), (int)stack.getAmount(), stack.getPatch()
      );
      return FluidStackHooks.getColor(forgeStack.getFluid());
   }
}
