package noobanidus.mods.lootr.neoforge.client.block;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.function.Function;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.IDynamicBakedModel;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.geometry.IGeometryBakingContext;
import net.neoforged.neoforge.client.model.geometry.IGeometryLoader;
import net.neoforged.neoforge.client.model.geometry.IUnbakedGeometry;
import noobanidus.mods.lootr.neoforge.init.ModBlockProperties;
import org.jetbrains.annotations.Nullable;

public class BrushableModel implements IUnbakedGeometry<BrushableModel> {
   private final UnbakedModel opened;
   private final UnbakedModel stage_0;
   private final UnbakedModel stage_1;
   private final UnbakedModel stage_2;
   private final UnbakedModel stage_3;

   public BrushableModel(UnbakedModel opened, UnbakedModel stage_0, UnbakedModel stage_1, UnbakedModel stage_2, UnbakedModel stage_3) {
      this.opened = opened;
      this.stage_0 = stage_0;
      this.stage_1 = stage_1;
      this.stage_2 = stage_2;
      this.stage_3 = stage_3;
   }

   @Nullable
   private static BakedModel buildModel(UnbakedModel entry, ModelState modelTransform, ModelBaker bakery, Function<Material, TextureAtlasSprite> spriteGetter) {
      return entry.bake(bakery, spriteGetter, modelTransform);
   }

   public BakedModel bake(
      IGeometryBakingContext context,
      ModelBaker bakery,
      Function<Material, TextureAtlasSprite> spriteGetter,
      ModelState modelTransform,
      ItemOverrides overrides
   ) {
      BakedModel opened = buildModel(this.opened, modelTransform, bakery, spriteGetter);
      BakedModel stage_0 = buildModel(this.stage_0, modelTransform, bakery, spriteGetter);
      BakedModel stage_1 = buildModel(this.stage_1, modelTransform, bakery, spriteGetter);
      BakedModel stage_2 = buildModel(this.stage_2, modelTransform, bakery, spriteGetter);
      BakedModel stage_3 = buildModel(this.stage_3, modelTransform, bakery, spriteGetter);
      if (opened != null && stage_0 != null && stage_1 != null && stage_2 != null && stage_3 != null) {
         return new BrushableModel.BrushableBakedModel(
            context.useAmbientOcclusion(),
            context.isGui3d(),
            context.useBlockLight(),
            spriteGetter.apply(context.getMaterial("particle")),
            overrides,
            opened,
            stage_0,
            stage_1,
            stage_2,
            stage_3,
            context.getTransforms()
         );
      } else {
         throw new IllegalStateException("BrushableModel could not bake all sub-models");
      }
   }

   public void resolveParents(Function<ResourceLocation, UnbakedModel> modelGetter, IGeometryBakingContext context) {
      this.opened.resolveParents(modelGetter);
      this.stage_0.resolveParents(modelGetter);
      this.stage_1.resolveParents(modelGetter);
      this.stage_2.resolveParents(modelGetter);
      this.stage_3.resolveParents(modelGetter);
   }

   private record BrushableBakedModel(
      boolean ambientOcclusion,
      boolean gui3d,
      boolean isSideLit,
      TextureAtlasSprite particle,
      ItemOverrides overrides,
      BakedModel opened,
      BakedModel stage_0,
      BakedModel stage_1,
      BakedModel stage_2,
      BakedModel stage_3,
      ItemTransforms cameraTransforms
   ) implements IDynamicBakedModel {
      public List<BakedQuad> getQuads(
         @Nullable BlockState state, @Nullable Direction side, RandomSource rand, ModelData extraData, @Nullable RenderType renderType
      ) {
         BakedModel model = this.stage_0;
         if (extraData.has(ModBlockProperties.OPENED) && extraData.get(ModBlockProperties.OPENED) == Boolean.TRUE) {
            model = this.opened;
         } else if (state != null) {
            switch (state.getValue(BlockStateProperties.DUSTED)) {
               case 1:
                  model = this.stage_1;
                  break;
               case 2:
                  model = this.stage_2;
                  break;
               case 3:
                  model = this.stage_3;
            }
         }

         return model.getQuads(state, side, rand, extraData, renderType);
      }

      public boolean useAmbientOcclusion() {
         return this.ambientOcclusion;
      }

      public boolean isGui3d() {
         return this.gui3d;
      }

      public boolean usesBlockLight() {
         return this.isSideLit;
      }

      public boolean isCustomRenderer() {
         return false;
      }

      public TextureAtlasSprite getParticleIcon() {
         return this.particle;
      }

      public TextureAtlasSprite getParticleIcon(ModelData data) {
         return this.stage_0.getParticleIcon(data);
      }

      public ItemTransforms getTransforms() {
         return this.cameraTransforms;
      }

      public ItemOverrides getOverrides() {
         return this.overrides;
      }
   }

   public static final class Loader implements IGeometryLoader<BrushableModel> {
      public static final BrushableModel.Loader INSTANCE = new BrushableModel.Loader();

      private Loader() {
      }

      public BrushableModel read(JsonObject modelContents, JsonDeserializationContext deserializationContext) {
         UnbakedModel opened = (UnbakedModel)deserializationContext.deserialize(GsonHelper.getAsJsonObject(modelContents, "opened"), BlockModel.class);
         UnbakedModel stage_0 = (UnbakedModel)deserializationContext.deserialize(GsonHelper.getAsJsonObject(modelContents, "stage_0"), BlockModel.class);
         UnbakedModel stage_1 = (UnbakedModel)deserializationContext.deserialize(GsonHelper.getAsJsonObject(modelContents, "stage_1"), BlockModel.class);
         UnbakedModel stage_2 = (UnbakedModel)deserializationContext.deserialize(GsonHelper.getAsJsonObject(modelContents, "stage_2"), BlockModel.class);
         UnbakedModel stage_3 = (UnbakedModel)deserializationContext.deserialize(GsonHelper.getAsJsonObject(modelContents, "stage_3"), BlockModel.class);
         return new BrushableModel(opened, stage_0, stage_1, stage_2, stage_3);
      }
   }
}
