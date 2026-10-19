package net.unusual.block_factorys_bosses.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.client.model.ModelCrate1;
import net.unusual.block_factorys_bosses.client.model.ModelCrate2;
import net.unusual.block_factorys_bosses.client.model.ModelCrate3;
import net.unusual.block_factorys_bosses.client.model.ModelCrate4;
import net.unusual.block_factorys_bosses.client.model.ModelCrate5;
import net.unusual.block_factorys_bosses.client.model.ModelCrate6;
import net.unusual.block_factorys_bosses.client.model.ModelCrate7;
import net.unusual.block_factorys_bosses.client.model.ModelCrate8;
import net.unusual.block_factorys_bosses.entity.decoration.CratePileEntity;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CrateEntityRenderer<T extends EntityModel<CratePileEntity>>
   extends EntityRenderer<CratePileEntity>
   implements RenderLayerParent<CratePileEntity, T> {
   private static final ResourceLocation TEXTURE = BossesRise.prefix("textures/entities/crate.png");
   private final EntityModel<CratePileEntity> crate1;
   private final EntityModel<CratePileEntity> crate2;
   private final EntityModel<CratePileEntity> crate3;
   private final EntityModel<CratePileEntity> crate4;
   private final EntityModel<CratePileEntity> crate5;
   private final EntityModel<CratePileEntity> crate6;
   private final EntityModel<CratePileEntity> crate7;
   private final EntityModel<CratePileEntity> crate8;

   public CrateEntityRenderer(Context context) {
      super(context);
      this.crate1 = new ModelCrate1(context.bakeLayer(ModelCrate1.LAYER_LOCATION));
      this.crate2 = new ModelCrate2(context.bakeLayer(ModelCrate2.LAYER_LOCATION));
      this.crate3 = new ModelCrate3(context.bakeLayer(ModelCrate3.LAYER_LOCATION));
      this.crate4 = new ModelCrate4(context.bakeLayer(ModelCrate4.LAYER_LOCATION));
      this.crate5 = new ModelCrate5(context.bakeLayer(ModelCrate5.LAYER_LOCATION));
      this.crate6 = new ModelCrate6(context.bakeLayer(ModelCrate6.LAYER_LOCATION));
      this.crate7 = new ModelCrate7(context.bakeLayer(ModelCrate7.LAYER_LOCATION));
      this.crate8 = new ModelCrate8(context.bakeLayer(ModelCrate8.LAYER_LOCATION));
   }

   public T getModel() {
      return (T)this.crate1;
   }

   public T getModel(CratePileEntity pile) {
      return (T)(switch (pile.getCrateCount()) {
         case 1 -> this.crate1;
         case 2 -> this.crate2;
         case 3 -> this.crate3;
         case 4 -> this.crate4;
         case 5 -> this.crate5;
         case 6 -> this.crate6;
         case 7 -> this.crate7;
         default -> this.crate8;
      });
   }

   public ResourceLocation getTextureLocation(CratePileEntity entity) {
      return TEXTURE;
   }

   public void render(CratePileEntity crate, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
      VertexConsumer vb = bufferIn.getBuffer(RenderType.entityCutout(TEXTURE));
      poseStack.pushPose();
      poseStack.translate(0.0F, 1.5F, 0.0F);
      poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
      poseStack.mulPose(Axis.YP.rotationDegrees(crate.getYRot()));
      float hurtTime = crate.getHurtTime() - partialTicks;
      float damage = crate.getDamage() - partialTicks;
      if (damage < 0.0F) {
         damage = 0.0F;
      }

      if (hurtTime > 0.0F) {
         poseStack.mulPose(Axis.YP.rotationDegrees(Mth.sin(hurtTime) * hurtTime * damage / 25.0F * crate.getHurtDir()));
      }

      this.getModel(crate).setupAnim(crate, 0.0F, 0.0F, 0.0F, crate.getYRot(), crate.getXRot());
      this.getModel(crate).renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY);
      poseStack.popPose();
      super.render(crate, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
   }
}
