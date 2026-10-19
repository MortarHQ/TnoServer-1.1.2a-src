package team.creative.itemphysiclite;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.creative.creativecore.CreativeCore;
import team.creative.creativecore.ICreativeLoader;
import team.creative.creativecore.client.ClientLoader;
import team.creative.creativecore.common.config.holder.CreativeConfigRegistry;
import team.creative.creativecore.common.config.sync.ConfigSynchronization;
import team.creative.itemphysiclite.mixin.EntityAccessor;

@Mod(value = "itemphysiclite", dist = Dist.CLIENT)
public class ItemPhysicLite implements ClientLoader {
   public static final Logger LOGGER = LogManager.getLogger("itemphysiclite");
   public static final String MODID = "itemphysiclite";
   private static Minecraft mc = Minecraft.getInstance();
   public static ItemPhysicLiteConfig CONFIG;
   public static long lastTickTime;
   private static final double RANDOM_Y_OFFSET_SCALE = 0.007957747154594767;

   public static boolean render(
      ItemEntity entity,
      float entityYaw,
      float partialTicks,
      PoseStack pose,
      MultiBufferSource buffer,
      int packedLight,
      ItemRenderer itemRenderer,
      RandomSource rand
   ) {
      if (entity.getAge() == 0) {
         return false;
      }

      pose.pushPose();
      ItemStack itemstack = entity.getItem();
      rand.setSeed(itemstack.isEmpty() ? 187L : Item.getId(itemstack.getItem()) + itemstack.getDamageValue());
      BakedModel bakedmodel = itemRenderer.getModel(itemstack, entity.level(), (LivingEntity)null, entity.getId());
      boolean flag = bakedmodel.isGui3d();
      int j = getModelCount(itemstack);
      float rotateBy = (float)(System.nanoTime() - lastTickTime) / 2.0E8F * CONFIG.rotateSpeed;
      if (mc.isPaused()) {
         rotateBy = 0.0F;
      }

      Vec3 motionMultiplier = getStuckSpeedMultiplier(entity);
      if (motionMultiplier != null && motionMultiplier.lengthSqr() > 0.0) {
         rotateBy = (float)(rotateBy * (motionMultiplier.x * 0.2));
      }

      pose.mulPose(Axis.XP.rotation((float) (Math.PI / 2)));
      pose.mulPose(Axis.ZP.rotation(entity.getYRot()));
      boolean applyEffects = entity.getAge() != 0 && (flag || mc.options != null);
      if (applyEffects) {
         if (flag) {
            if (!entity.onGround()) {
               rotateBy *= 2.0F;
               Fluid fluid = getFluid(entity);
               if (fluid == null) {
                  fluid = getFluid(entity, true);
               }

               if (fluid != null) {
                  rotateBy /= 1.0F + getViscosity(fluid, entity.level());
               }

               entity.setXRot(entity.getXRot() + rotateBy);
            }
         } else if (entity != null && !Double.isNaN(entity.getX()) && !Double.isNaN(entity.getY()) && !Double.isNaN(entity.getZ()) && entity.level() != null) {
            if (entity.onGround()) {
               if (!flag) {
                  entity.setXRot(0.0F);
               }
            } else {
               rotateBy *= 2.0F;
               Fluid fluid = getFluid(entity);
               if (fluid != null) {
                  rotateBy /= 1.0F + getViscosity(fluid, entity.level());
               }

               entity.setXRot(entity.getXRot() + rotateBy);
            }
         }

         if (flag) {
            pose.translate(0.0, -0.2, -0.08);
         } else if (!CONFIG.blockRequireOffset.is(entity.level().getBlockState(entity.blockPosition()))
            && !CONFIG.blockBelowRequireOffset.is(entity.level().getBlockState(entity.blockPosition().below()))) {
            pose.translate(0.0, 0.0, -0.04 - entity.bobOffs * 0.007957747154594767);
         } else {
            pose.translate(0.0, 0.0, -0.14 - entity.bobOffs * 0.007957747154594767);
         }

         double height = 0.2;
         if (flag) {
            pose.translate(0.0, height, 0.0);
         }

         pose.mulPose(Axis.YP.rotation(entity.getXRot()));
         if (flag) {
            pose.translate(0.0, -height, 0.0);
         }
      }

      if (!flag) {
         float f7 = -0.0F * (j - 1) * 0.5F;
         float f8 = -0.0F * (j - 1) * 0.5F;
         float f9 = -0.09375F * (j - 1) * 0.5F;
         pose.translate(f7, f8, f9);
      }

      for (int k = 0; k < j; k++) {
         pose.pushPose();
         if (k > 0 && flag) {
            float f11 = (rand.nextFloat() * 2.0F - 1.0F) * 0.15F;
            float f13 = (rand.nextFloat() * 2.0F - 1.0F) * 0.15F;
            float f10 = (rand.nextFloat() * 2.0F - 1.0F) * 0.15F;
            pose.translate(f11, f13, f10);
         }

         itemRenderer.render(itemstack, ItemDisplayContext.GROUND, false, pose, buffer, packedLight, OverlayTexture.NO_OVERLAY, bakedmodel);
         pose.popPose();
         if (!flag) {
            pose.translate(0.0, 0.0, 0.09375);
         }
      }

      pose.popPose();
      return true;
   }

   public static Fluid getFluid(ItemEntity item) {
      return getFluid(item, false);
   }

   public static Fluid getFluid(ItemEntity item, boolean below) {
      if (item.level() == null) {
         return null;
      }

      double d0 = item.position().y;
      BlockPos pos = item.blockPosition();
      if (below) {
         pos = pos.below();
      }

      FluidState state = item.level().getFluidState(pos);
      Fluid fluid = state.getType();
      if (fluid == null || fluid.getTickDelay(item.level()) == 0) {
         return null;
      }

      if (below) {
         return fluid;
      }

      double filled = state.getHeight(item.level(), pos);
      return d0 - pos.getY() - 0.2 <= filled ? fluid : null;
   }

   public static int getModelCount(ItemStack stack) {
      if (stack.getCount() > 48) {
         return 5;
      } else if (stack.getCount() > 32) {
         return 4;
      } else if (stack.getCount() > 16) {
         return 3;
      } else {
         return stack.getCount() > 1 ? 2 : 1;
      }
   }

   public static Vec3 getStuckSpeedMultiplier(Entity entity) {
      return ((EntityAccessor)entity).getStuckSpeedMultiplier();
   }

   public static float getViscosity(Fluid fluid, Level level) {
      return fluid == null ? 0.0F : CreativeCore.loader().getFluidViscosityMultiplier(fluid, level);
   }

   public ItemPhysicLite() {
      ICreativeLoader loader = CreativeCore.loader();
      loader.registerClient(this);
   }

   public void onInitializeClient() {
      ICreativeLoader loader = CreativeCore.loader();
      loader.registerClientRenderGui(x -> lastTickTime = System.nanoTime());
      CreativeConfigRegistry.ROOT.registerValue("itemphysiclite", CONFIG = new ItemPhysicLiteConfig(), ConfigSynchronization.CLIENT, false);
   }
}
