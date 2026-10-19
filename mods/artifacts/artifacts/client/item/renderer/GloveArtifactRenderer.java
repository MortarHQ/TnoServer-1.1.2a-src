package artifacts.client.item.renderer;

import artifacts.client.item.model.ArmsModel;
import artifacts.equipment.client.EquipmentRenderingManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.function.Function;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.PlayerSkin.Model;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class GloveArtifactRenderer implements ArtifactRenderer {
   private final ResourceLocation wideTexture;
   private final ResourceLocation slimTexture;
   private final ArmsModel wideModel;
   private final ArmsModel slimModel;

   public GloveArtifactRenderer(String name, Function<Boolean, ArmsModel> model) {
      this(ArtifactRenderer.getTexturePath(name, "%s_wide".formatted(name)), ArtifactRenderer.getTexturePath(name, "%s_slim".formatted(name)), model);
   }

   public GloveArtifactRenderer(String wideTexture, String slimTexture, Function<Boolean, ArmsModel> model) {
      this(ArtifactRenderer.getTexturePath(wideTexture), ArtifactRenderer.getTexturePath(slimTexture), model);
   }

   public GloveArtifactRenderer(ResourceLocation wideTexture, ResourceLocation slimTexture, Function<Boolean, ArmsModel> model) {
      this.wideTexture = wideTexture;
      this.slimTexture = slimTexture;
      this.wideModel = model.apply(false);
      this.slimModel = model.apply(true);
   }

   @Nullable
   public static GloveArtifactRenderer getGloveRenderer(ItemStack stack) {
      return !stack.isEmpty() && EquipmentRenderingManager.getArtifactRenderer(stack.getItem()) instanceof GloveArtifactRenderer gloveRenderer
         ? gloveRenderer
         : null;
   }

   protected ResourceLocation getTexture(boolean hasSlimArms) {
      return hasSlimArms ? this.slimTexture : this.wideTexture;
   }

   protected ArmsModel getModel(boolean hasSlimArms) {
      return hasSlimArms ? this.slimModel : this.wideModel;
   }

   protected static boolean hasSlimArms(Entity entity) {
      return entity instanceof AbstractClientPlayer player && player.getSkin().model() == Model.SLIM;
   }

   @Override
   public void render(
      ItemStack stack,
      LivingEntity entity,
      int slotIndex,
      PoseStack poseStack,
      MultiBufferSource multiBufferSource,
      int light,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      boolean hasSlimArms = hasSlimArms(entity);
      ArmsModel model = this.getModel(hasSlimArms);
      InteractionHand hand = slotIndex % 2 == 0 ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
      HumanoidArm handSide = hand == InteractionHand.MAIN_HAND ? entity.getMainArm() : entity.getMainArm().getOpposite();
      model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
      ArtifactRenderer.followBodyRotations(entity, model);
      this.renderArm(model, poseStack, multiBufferSource, handSide, light, hasSlimArms, stack.hasFoil());
   }

   protected void renderArm(
      ArmsModel model, PoseStack matrixStack, MultiBufferSource buffer, HumanoidArm handSide, int light, boolean hasSlimArms, boolean hasFoil
   ) {
      RenderType renderType = model.renderType(this.getTexture(hasSlimArms));
      VertexConsumer vertexBuilder = ItemRenderer.getFoilBuffer(buffer, renderType, false, hasFoil);
      model.renderArm(handSide, matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, -1);
   }

   public final void renderFirstPersonArm(
      PoseStack matrixStack, MultiBufferSource buffer, int light, AbstractClientPlayer player, HumanoidArm side, boolean hasFoil
   ) {
      if (!player.isSpectator()) {
         boolean hasSlimArms = hasSlimArms(player);
         ArmsModel model = this.getModel(hasSlimArms);
         ModelPart arm = side == HumanoidArm.LEFT ? model.leftArm : model.rightArm;
         model.setAllVisible(false);
         arm.visible = true;
         model.crouching = false;
         model.attackTime = model.swimAmount = 0.0F;
         model.setupAnim(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
         arm.xRot = 0.0F;
         this.renderFirstPersonArm(model, arm, matrixStack, buffer, light, hasSlimArms, hasFoil);
      }
   }

   protected void renderFirstPersonArm(
      ArmsModel model, ModelPart arm, PoseStack matrixStack, MultiBufferSource buffer, int light, boolean hasSlimArms, boolean hasFoil
   ) {
      RenderType renderType = model.renderType(this.getTexture(hasSlimArms));
      VertexConsumer builder = ItemRenderer.getFoilBuffer(buffer, renderType, false, hasFoil);
      arm.render(matrixStack, builder, light, OverlayTexture.NO_OVERLAY);
   }
}
