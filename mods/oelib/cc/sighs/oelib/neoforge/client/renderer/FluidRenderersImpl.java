package cc.sighs.oelib.neoforge.client.renderer;

import cc.sighs.oelib.renderer.FluidRef;
import cc.sighs.oelib.renderer.FluidRenderAttributes;
import cc.sighs.oelib.renderer.spi.IFluidRenderers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;

public final class FluidRenderersImpl implements IFluidRenderers {
   @Override
   public FluidRenderAttributes resolve(FluidRef ref) {
      Fluid fluid = ref.getFluid();
      IClientFluidTypeExtensions props = IClientFluidTypeExtensions.of(fluid);
      FluidStack stack = new FluidStack(fluid, 1000);
      int color = props.getTintColor(stack);
      ResourceLocation stillTex = props.getStillTexture(stack);
      TextureAtlasSprite sprite = (TextureAtlasSprite)Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTex);
      return sprite.atlasLocation() == MissingTextureAtlasSprite.getLocation() ? null : new FluidRenderAttributes(sprite, color);
   }
}
