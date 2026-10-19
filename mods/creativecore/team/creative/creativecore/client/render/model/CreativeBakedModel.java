package team.creative.creativecore.client.render.model;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CreativeBakedModel implements BakedModel {
   public final ModelResourceLocation location;
   public final CreativeItemModel item;
   protected ItemStack renderedStack = null;
   private BakedModel modelCache;
   public ItemOverrides customOverride = new ItemOverrides() {
      public BakedModel resolve(BakedModel original, ItemStack stack, ClientLevel level, LivingEntity entity, int p_173469_) {
         CreativeBakedModel.this.renderedStack = stack;
         return super.resolve(original, stack, level, entity, p_173469_);
      }
   };

   public CreativeBakedModel(ModelResourceLocation location, CreativeItemModel item) {
      this.location = location;
      this.item = item;
   }

   public BakedModel get() {
      if (this.modelCache == null) {
         this.modelCache = Minecraft.getInstance().getModelManager().getModel(this.location);
      }

      return this.modelCache;
   }

   @NotNull
   public List<BakedQuad> getQuads(
      @Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData data, @Nullable RenderType renderType
   ) {
      return this.get().getQuads(state, side, rand, data, renderType);
   }

   public boolean useAmbientOcclusion() {
      return this.get().useAmbientOcclusion();
   }

   public boolean isGui3d() {
      return this.get().isGui3d();
   }

   public boolean usesBlockLight() {
      return this.get().usesBlockLight();
   }

   public boolean isCustomRenderer() {
      return false;
   }

   public TextureAtlasSprite getParticleIcon() {
      return this.get().getParticleIcon();
   }

   public ItemOverrides getOverrides() {
      return this.customOverride;
   }

   public ItemTransforms getTransforms() {
      return this.get().getTransforms();
   }

   public BakedModel applyTransform(ItemDisplayContext transformType, PoseStack poseStack, boolean applyLeftHandTransform) {
      if (this.renderedStack != null) {
         this.item.applyCustomOpenGLHackery(poseStack, this.renderedStack, transformType);
      }

      return super.applyTransform(transformType, poseStack, applyLeftHandTransform);
   }

   public List<BakedQuad> getQuads(BlockState state, Direction direction, RandomSource source) {
      return this.get().getQuads(state, direction, source);
   }
}
