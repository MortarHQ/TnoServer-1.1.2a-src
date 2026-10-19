package team.creative.creativecore.client.render.model;

import java.util.function.Function;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.geometry.IGeometryBakingContext;
import net.neoforged.neoforge.client.model.geometry.IUnbakedGeometry;
import team.creative.creativecore.client.CreativeCoreClient;

public record CreativeUnbakedModel(ResourceLocation item, ResourceLocation block) implements IUnbakedGeometry<CreativeUnbakedModel> {
   public BakedModel bake(
      IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides
   ) {
      CreativeBlockModel renderBlock = CreativeCoreClient.BLOCK_MODEL_TYPES.get(this.block);
      CreativeItemModel renderItem = CreativeCoreClient.ITEM_MODEL_TYPES.get(this.item);
      if (renderBlock == null && renderItem == null) {
         throw new RuntimeException("Could not find renderer " + this.block + ", " + this.item);
      } else {
         return renderItem != null ? renderItem.create(renderBlock) : CreativeItemBoxModel.EMPTY.create(renderBlock);
      }
   }
}
