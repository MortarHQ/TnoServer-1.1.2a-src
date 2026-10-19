package artifacts.integration.accessories;

import artifacts.client.item.renderer.ArtifactRenderer;
import artifacts.client.item.renderer.GloveArtifactRenderer;
import artifacts.equipment.client.EquipmentRenderingHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import java.util.function.Supplier;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class AccessoriesRenderingHandler implements EquipmentRenderingHandler {
   @Override
   public void registerArtifactRenderer(Item item, Supplier<ArtifactRenderer> rendererSupplier) {
      AccessoriesRendererRegistry.registerRenderer(item, () -> new AccessoriesRenderingHandler.ArtifactAccessoryRenderer(rendererSupplier.get()));
   }

   @Nullable
   @Override
   public ArtifactRenderer getArtifactRenderer(Item item) {
      return AccessoriesRendererRegistry.getRender(item) instanceof AccessoriesRenderingHandler.ArtifactAccessoryRenderer artifactAccessoryRenderer
         ? artifactAccessoryRenderer.renderer()
         : null;
   }

   @Override
   public void renderArm(PoseStack matrixStack, MultiBufferSource buffer, int light, AbstractClientPlayer player, HumanoidArm side) {
   }

   public record ArtifactAccessoryRenderer(ArtifactRenderer renderer) implements AccessoryRenderer {
      public <M extends LivingEntity> void render(
         ItemStack stack,
         SlotReference reference,
         PoseStack matrices,
         EntityModel<M> model,
         MultiBufferSource multiBufferSource,
         int light,
         float limbSwing,
         float limbSwingAmount,
         float partialTicks,
         float ageInTicks,
         float netHeadYaw,
         float headPitch
      ) {
         this.renderer
            .renderVisible(
               stack,
               reference.entity(),
               reference.slot(),
               matrices,
               multiBufferSource,
               light,
               limbSwing,
               limbSwingAmount,
               partialTicks,
               ageInTicks,
               netHeadYaw,
               headPitch
            );
      }

      public <M extends LivingEntity> void renderOnFirstPerson(
         HumanoidArm side, ItemStack stack, SlotReference reference, PoseStack matrices, EntityModel<M> model, MultiBufferSource multiBufferSource, int light
      ) {
         if (reference.entity() instanceof LocalPlayer player) {
            InteractionHand hand = side == player.getMainArm() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
            GloveArtifactRenderer gloveRenderer = GloveArtifactRenderer.getGloveRenderer(stack);
            if (gloveRenderer != null && reference.slot() % 2 == (hand == InteractionHand.MAIN_HAND ? 0 : 1)) {
               gloveRenderer.renderFirstPersonArm(matrices, multiBufferSource, light, (AbstractClientPlayer)reference.entity(), side, stack.hasFoil());
            }
         }
      }
   }
}
