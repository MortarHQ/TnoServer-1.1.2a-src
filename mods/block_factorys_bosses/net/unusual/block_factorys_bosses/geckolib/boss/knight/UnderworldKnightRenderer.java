package net.unusual.block_factorys_bosses.geckolib.boss.knight;

import com.mojang.datafixers.util.Pair;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.boss.knight.UnderworldKnightEntity;
import net.unusual.block_factorys_bosses.geckolib.EmissiveRenderLayer;
import net.unusual.block_factorys_bosses.geckolib.MatrixAnimationProcessorTwo;
import net.unusual.block_factorys_bosses.geckolib.boss.CinematicRenderer;
import net.unusual.block_factorys_bosses.geckolib.util.CustomEntityGeoModel;
import net.unusual.block_factorys_bosses.geckolib.util.ParticleLocator;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import software.bernie.geckolib.GeckoLibConstants;
import software.bernie.geckolib.animation.AnimationProcessor;
import software.bernie.geckolib.cache.GeckoLibCache;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.ClientUtil;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class UnderworldKnightRenderer extends GeoEntityRenderer<UnderworldKnightEntity> implements CinematicRenderer<UnderworldKnightEntity> {
   public static final Map<String, ParticleLocator> BONER = new HashMap<>();
   public static final ResourceLocation PHASE_1_MODEL = BossesRise.prefix("knight_boss");
   private static final ResourceLocation PHASE_2_TEXTURE = BossesRise.prefix("textures/entity/knight_boss_phase2.png");

   public UnderworldKnightRenderer(Context context) {
      super(context, new UnderworldKnightRenderer.UnderworldKnightModel(PHASE_1_MODEL));
      this.addRenderLayer(new EmissiveRenderLayer(this));
   }

   @Nullable
   public RenderType getRenderType(UnderworldKnightEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
      boolean invisible = animatable.isInvisible();
      if (invisible && !animatable.isInvisibleTo(ClientUtil.getClientPlayer())) {
         return RenderType.itemEntityTranslucentCull(texture);
      } else if (!invisible) {
         return RenderType.entityTranslucent(texture);
      } else {
         return Minecraft.getInstance().shouldEntityAppearGlowing(animatable) ? RenderType.outline(texture) : null;
      }
   }

   public boolean shouldRender(UnderworldKnightEntity livingEntity, Frustum camera, double camX, double camY, double camZ) {
      return livingEntity.isCinematic() || super.shouldRender(livingEntity, camera, camX, camY, camZ);
   }

   public Pair<Float, Float> getCinematicYawAndPitch(UnderworldKnightEntity animatable, GeoBone cameraBone, LocalPlayer player) {
      Vector3d rot = cameraBone.getRotationVector().mul(-180.0 / Math.PI);
      rot.y = rot.y + animatable.getYRot();
      return new Pair((float)rot.y(), (float)rot.x());
   }

   private static class UnderworldKnightModel extends CustomEntityGeoModel<UnderworldKnightEntity> {
      private final MatrixAnimationProcessorTwo<UnderworldKnightEntity> animationProcessor = new MatrixAnimationProcessorTwo(this);
      private BakedGeoModel currentKnightModel = null;

      public UnderworldKnightModel(ResourceLocation location) {
         super(location);
      }

      public BakedGeoModel getBakedModel(ResourceLocation location) {
         BakedGeoModel model = (BakedGeoModel)GeckoLibCache.getBakedModels().get(location);
         if (model == null) {
            if (!location.getPath().contains("geo/")) {
               throw GeckoLibConstants.exception(location, "Invalid model resource path provided - GeckoLib models must be placed in assets/<modid>/geo/");
            } else {
               throw GeckoLibConstants.exception(location, "Unable to find model");
            }
         } else {
            if (model != this.currentKnightModel) {
               this.animationProcessor.setActiveModel(model);
               this.currentKnightModel = model;
            }

            return this.currentKnightModel;
         }
      }

      public AnimationProcessor<UnderworldKnightEntity> getAnimationProcessor() {
         return this.animationProcessor;
      }

      public ResourceLocation getTextureResource(UnderworldKnightEntity knight) {
         return knight.isTransformed() ? UnderworldKnightRenderer.PHASE_2_TEXTURE : super.getTextureResource(knight);
      }
   }
}
