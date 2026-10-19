package net.unusual.block_factorys_bosses.geckolib.boneCache;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.ToLongFunction;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.unusual.block_factorys_bosses.geckolib.util.AnimatableId;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BoneDataCacheLayer<T extends GeoAnimatable> extends GeoRenderLayer<T> {
   private final ToLongFunction<T> animatableIdConverter;
   @Nullable
   private BoneDataCache currentBoneDataCache = null;
   @Nullable
   private ArrayList<String> alwaysWantedBones;

   public BoneDataCacheLayer(GeoRenderer<T> entityRendererIn, ToLongFunction<T> animatableIdConverter) {
      super(entityRendererIn);
      this.animatableIdConverter = animatableIdConverter;
   }

   public static <T extends Entity & GeoAnimatable> BoneDataCacheLayer<T> forEntity(GeoRenderer<T> entityRendererIn) {
      return new BoneDataCacheLayer<>(entityRendererIn, x$0 -> AnimatableId.ofEntity(x$0));
   }

   public static <T extends BlockEntity & GeoAnimatable> BoneDataCacheLayer<T> forBlockEntity(GeoRenderer<T> entityRendererIn) {
      return new BoneDataCacheLayer<>(entityRendererIn, x$0 -> AnimatableId.ofBlockEntity(x$0));
   }

   public BoneDataCacheLayer<T> withAlwaysWanted(String... boneNames) {
      if (this.alwaysWantedBones == null) {
         this.alwaysWantedBones = new ArrayList<>(boneNames.length);
      }

      this.alwaysWantedBones.addAll(Arrays.asList(boneNames));
      return this;
   }

   public void preRender(
      PoseStack poseStack,
      T animatable,
      BakedGeoModel bakedModel,
      @Nullable RenderType renderType,
      MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      float partialTick,
      int packedLight,
      int packedOverlay
   ) {
      long animatableId = this.animatableIdConverter.applyAsLong(animatable);
      AnimatableManager<GeoAnimatable> animatableManager = animatable.getAnimatableInstanceCache().getManagerForId(animatableId);
      BoneDataCache boneDataCache = (BoneDataCache)animatableManager.getData(BoneDataCache.DATA_TICKET);
      if (boneDataCache != null) {
         boneDataCache.maybeClearUnwantedBoneData();
         if (boneDataCache.hasBoneData()) {
            this.currentBoneDataCache = boneDataCache;
         }
      } else if (this.alwaysWantedBones != null) {
         boneDataCache = new BoneDataCache();

         for (String boneName : this.alwaysWantedBones) {
            boneDataCache.getOrCreateBoneData(boneName).setAlwaysWanted();
         }

         animatableManager.setData(BoneDataCache.DATA_TICKET, boneDataCache);
         this.currentBoneDataCache = boneDataCache;
      }
   }

   public void renderForBone(
      PoseStack poseStack,
      T animatable,
      GeoBone bone,
      RenderType renderType,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      float partialTick,
      int packedLight,
      int packedOverlay
   ) {
      if (this.currentBoneDataCache != null) {
         BoneData boneTransform = this.currentBoneDataCache.getBoneData(bone.getName());
         if (boneTransform != null) {
            boneTransform.worldTransform.set(poseStack.last().pose());
            boneTransform.markUpdated();
         }
      }
   }

   public void render(
      PoseStack poseStack,
      T animatable,
      BakedGeoModel bakedModel,
      @Nullable RenderType renderType,
      MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      float partialTick,
      int packedLight,
      int packedOverlay
   ) {
      this.currentBoneDataCache = null;
   }
}
