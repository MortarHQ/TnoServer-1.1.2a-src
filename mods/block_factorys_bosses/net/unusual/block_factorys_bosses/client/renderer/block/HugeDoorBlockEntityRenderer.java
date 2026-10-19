package net.unusual.block_factorys_bosses.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.function.Function;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.unusual.block_factorys_bosses.block.HugeDoorBlock;
import net.unusual.block_factorys_bosses.block.entity.HugeDoorBlockEntity;
import net.unusual.block_factorys_bosses.geckolib.EmissiveRenderLayer;
import net.unusual.block_factorys_bosses.geckolib.util.CustomBlockGeoModel;
import net.unusual.block_factorys_bosses.init.BossesRiseRenderTypes;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.texture.AnimatableTexture;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class HugeDoorBlockEntityRenderer extends GeoBlockRenderer<HugeDoorBlockEntity> {
   private final ResourceLocation lockedTextureLocation;
   private final ResourceLocation unlockedTextureLocation;
   private final ResourceLocation unlockingTextureLocation;
   @Nullable
   private final ResourceLocation lockedEmissiveTextureLocation;
   @Nullable
   private final ResourceLocation unlockedEmissiveTextureLocation;
   @Nullable
   private final ResourceLocation unlockingEmissiveTextureLocation;
   private boolean useEmissiveTexture = false;

   public HugeDoorBlockEntityRenderer(Context context, ResourceLocation blockId, boolean hasEmissiveTextures) {
      super(new CustomBlockGeoModel<HugeDoorBlockEntity>(blockId) {
         public RenderType getRenderType(HugeDoorBlockEntity animatable, ResourceLocation texture) {
            return RenderType.entityCutout(texture);
         }
      });
      CustomBlockGeoModel<HugeDoorBlockEntity> model = (CustomBlockGeoModel<HugeDoorBlockEntity>)this.getGeoModel();
      this.lockedTextureLocation = model.buildFormattedTexturePath(blockId.withSuffix("_locked"));
      this.unlockedTextureLocation = model.buildFormattedTexturePath(blockId.withSuffix("_unlocked"));
      this.unlockingTextureLocation = model.buildFormattedTexturePath(blockId.withSuffix("_unlocking"));
      if (!hasEmissiveTextures) {
         this.lockedEmissiveTextureLocation = null;
         this.unlockedEmissiveTextureLocation = null;
         this.unlockingEmissiveTextureLocation = null;
      } else {
         this.lockedEmissiveTextureLocation = EmissiveRenderLayer.addEmissiveTextureSuffix(this.lockedTextureLocation);
         this.unlockedEmissiveTextureLocation = EmissiveRenderLayer.addEmissiveTextureSuffix(this.unlockedTextureLocation);
         this.unlockingEmissiveTextureLocation = EmissiveRenderLayer.addEmissiveTextureSuffix(this.unlockingTextureLocation);
         this.addRenderLayer(
            new EmissiveRenderLayer<HugeDoorBlockEntity>(
               this,
               blockEntity -> {
                  if (((HugeDoorBlockEntity)this.animatable).getUnlockingTextureFrame() >= 0) {
                     return this.unlockingEmissiveTextureLocation;
                  } else {
                     return ((HugeDoorBlockEntity)this.animatable).getBlockState().getValue(HugeDoorBlock.OPEN)
                        ? this.unlockedEmissiveTextureLocation
                        : this.lockedEmissiveTextureLocation;
                  }
               }
            ) {
               public RenderType getRenderType(HugeDoorBlockEntity animatable, ResourceLocation texture) {
                  return BossesRiseRenderTypes.entityTranslucentEmissiveCull(texture);
               }

               public void render(
                  PoseStack poseStack,
                  HugeDoorBlockEntity animatable,
                  BakedGeoModel bakedModel,
                  RenderType renderType,
                  MultiBufferSource bufferSource,
                  VertexConsumer buffer,
                  float partialTick,
                  int packedLight,
                  int packedOverlay
               ) {
                  HugeDoorBlockEntityRenderer.this.useEmissiveTexture = true;
                  super.render(poseStack, animatable, bakedModel, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
                  HugeDoorBlockEntityRenderer.this.useEmissiveTexture = false;
               }
            }
         );
      }
   }

   public ResourceLocation getTextureLocation(HugeDoorBlockEntity animatable) {
      if (!this.useEmissiveTexture) {
         return this.chooseTexture(this.lockedTextureLocation, this.unlockedTextureLocation, this.unlockingTextureLocation);
      }

      assert this.unlockingEmissiveTextureLocation != null && this.unlockedEmissiveTextureLocation != null && this.lockedEmissiveTextureLocation != null;
      return this.chooseTexture(this.lockedEmissiveTextureLocation, this.unlockedEmissiveTextureLocation, this.unlockingEmissiveTextureLocation);
   }

   private ResourceLocation chooseTexture(ResourceLocation locked, ResourceLocation unlocked, ResourceLocation unlocking) {
      if (((HugeDoorBlockEntity)this.animatable).getUnlockingTextureFrame() >= 0) {
         return unlocking;
      } else {
         return ((HugeDoorBlockEntity)this.animatable).getBlockState().getValue(HugeDoorBlock.OPEN) ? unlocked : locked;
      }
   }

   public boolean shouldRenderOffScreen(HugeDoorBlockEntity blockEntity) {
      return true;
   }

   public AABB getRenderBoundingBox(HugeDoorBlockEntity blockEntity) {
      if (blockEntity.isPlayingOpeningAnimation()) {
         return AABB.ofSize(blockEntity.getBlockPos().getCenter(), this.getViewDistance(), this.getViewDistance(), this.getViewDistance());
      }

      BlockPos rootPos = blockEntity.getBlockPos();
      Direction facing = (Direction)blockEntity.getBlockState().getValue(HugeDoorBlock.FACING);
      Direction directionToOtherHinge = HugeDoorBlock.getDirectionToOtherHinge(facing);
      int width = HugeDoorBlock.getWidth(blockEntity.getBlockState());
      int height = HugeDoorBlock.getHeight(blockEntity.getBlockState());
      int halfWidth = width / 2 + 1;
      BlockPos centerPos = rootPos.relative(directionToOtherHinge, halfWidth);
      return AABB.encapsulatingFullBlocks(centerPos.offset(-halfWidth, 0, -halfWidth), centerPos.offset(halfWidth, height - 1, halfWidth)).inflate(2.0);
   }

   public int getViewDistance() {
      return 256;
   }

   public void render(
      HugeDoorBlockEntity animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay
   ) {
      Vector3f offset = HugeDoorBlock.getHorizontalCenterOffset(animatable.getBlockState());
      poseStack.translate(offset.x(), offset.y(), offset.z());
      super.render(animatable, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
   }

   public void updateAnimatedTextureFrame(HugeDoorBlockEntity animatable) {
      int unlockingTextureFrame = animatable.getUnlockingTextureFrame();
      if (unlockingTextureFrame < 0) {
         super.updateAnimatedTextureFrame(animatable);
      } else {
         AnimatableTexture.setAndUpdate(this.getTextureLocation(animatable), unlockingTextureFrame);
      }
   }
}
