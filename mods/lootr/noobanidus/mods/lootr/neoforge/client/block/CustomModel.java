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
import net.neoforged.neoforge.client.model.IDynamicBakedModel;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.geometry.IGeometryBakingContext;
import net.neoforged.neoforge.client.model.geometry.IGeometryLoader;
import net.neoforged.neoforge.client.model.geometry.IUnbakedGeometry;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.neoforge.init.ModBlockProperties;
import org.jetbrains.annotations.Nullable;

public class CustomModel implements IUnbakedGeometry<CustomModel> {
   private final UnbakedModel opened;
   private final UnbakedModel unopened;
   @Nullable
   private final UnbakedModel vanilla;

   public CustomModel(UnbakedModel opened, UnbakedModel unopened, @Nullable UnbakedModel vanilla) {
      this.opened = opened;
      this.unopened = unopened;
      this.vanilla = vanilla;
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
      return new CustomModel.CustomBakedModel(
         context.useAmbientOcclusion(),
         context.isGui3d(),
         context.useBlockLight(),
         spriteGetter.apply(context.getMaterial("particle")),
         overrides,
         buildModel(this.opened, modelTransform, bakery, spriteGetter),
         buildModel(this.unopened, modelTransform, bakery, spriteGetter),
         this.vanilla == null ? null : buildModel(this.vanilla, modelTransform, bakery, spriteGetter),
         context.getTransforms()
      );
   }

   public void resolveParents(Function<ResourceLocation, UnbakedModel> modelGetter, IGeometryBakingContext context) {
      this.opened.resolveParents(modelGetter);
      this.unopened.resolveParents(modelGetter);
      if (this.vanilla != null) {
         this.vanilla.resolveParents(modelGetter);
      }
   }

   private record CustomBakedModel(
      boolean ambientOcclusion,
      boolean gui3d,
      boolean isSideLit,
      TextureAtlasSprite particle,
      ItemOverrides overrides,
      @Nullable BakedModel opened,
      @Nullable BakedModel unopened,
      @Nullable BakedModel vanilla,
      ItemTransforms cameraTransforms
   ) implements IDynamicBakedModel {
      private CustomBakedModel(
         boolean ambientOcclusion,
         boolean gui3d,
         boolean isSideLit,
         TextureAtlasSprite particle,
         ItemOverrides overrides,
         @Nullable BakedModel opened,
         @Nullable BakedModel unopened,
         @Nullable BakedModel vanilla,
         ItemTransforms cameraTransforms
      ) {
         if (opened != null && unopened != null) {
            this.isSideLit = isSideLit;
            this.cameraTransforms = cameraTransforms;
            this.ambientOcclusion = ambientOcclusion;
            this.gui3d = gui3d;
            this.particle = particle;
            this.overrides = overrides;
            this.opened = opened;
            this.unopened = unopened;
            this.vanilla = vanilla;
         } else {
            throw new IllegalArgumentException("Opened and unopened models cannot be null");
         }
      }

      public List<BakedQuad> getQuads(
         @Nullable BlockState state, @Nullable Direction side, RandomSource rand, ModelData extraData, @Nullable RenderType renderType
      ) {
         BakedModel model;
         if (LootrAPI.isVanillaTextures() && this.vanilla != null) {
            model = this.vanilla;
         } else if (extraData.has(ModBlockProperties.OPENED)) {
            if (extraData.get(ModBlockProperties.OPENED) == Boolean.TRUE) {
               model = this.opened;
            } else {
               model = this.unopened;
            }
         } else {
            model = this.unopened;
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
         if (LootrAPI.isVanillaTextures() && this.vanilla != null) {
            return this.vanilla.getParticleIcon(data);
         } else {
            return data.get(ModBlockProperties.OPENED) == Boolean.TRUE ? this.opened.getParticleIcon(data) : this.unopened.getParticleIcon(data);
         }
      }

      public ItemTransforms getTransforms() {
         return this.cameraTransforms;
      }

      public ItemOverrides getOverrides() {
         return this.overrides;
      }
   }

   public static final class Loader implements IGeometryLoader<CustomModel> {
      public static final CustomModel.Loader BARREL_INSTANCE = new CustomModel.Loader(true);
      public static final CustomModel.Loader INSTANCE = new CustomModel.Loader(false);
      private final boolean deprecated;

      private Loader(boolean deprecated) {
         this.deprecated = deprecated;
      }

      public CustomModel read(JsonObject modelContents, JsonDeserializationContext deserializationContext) {
         if (this.deprecated) {
            LootrAPI.LOG.error("Using deprecated `lootr:custom_barrel` model loader! Please use `lootr:custom_model` instead.");
         }

         UnbakedModel unopened = (UnbakedModel)deserializationContext.deserialize(GsonHelper.getAsJsonObject(modelContents, "unopened"), BlockModel.class);
         UnbakedModel opened = (UnbakedModel)deserializationContext.deserialize(GsonHelper.getAsJsonObject(modelContents, "opened"), BlockModel.class);
         UnbakedModel vanilla = null;
         if (modelContents.has("vanilla")) {
            vanilla = (UnbakedModel)deserializationContext.deserialize(GsonHelper.getAsJsonObject(modelContents, "vanilla"), BlockModel.class);
         }

         return new CustomModel(opened, unopened, vanilla);
      }
   }
}
