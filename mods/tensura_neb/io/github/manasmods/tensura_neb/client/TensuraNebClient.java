package io.github.manasmods.tensura_neb.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import io.github.manasmods.tensura.client.entity.human.OtherworlderRenderer;
import io.github.manasmods.tensura_neb.client.entity.CarrionRenderer;
import io.github.manasmods.tensura_neb.client.entity.LuminousRenderer;
import io.github.manasmods.tensura_neb.client.entity.VeldoraRenderer;
import io.github.manasmods.tensura_neb.entity.RimuruCloneEntity;
import io.github.manasmods.tensura_neb.entity.RimuruOgreFightEntity;
import io.github.manasmods.tensura_neb.registry.NebEntityTypes;

public class TensuraNebClient {
   public static void init() {
      registerLivingEntityRenderer();
      EntityModelLayerRegistry.register(LuminousRenderer.Chest.CHEST, () -> LuminousRenderer.CHEST_LAYER);
   }

   public static void registerLivingEntityRenderer() {
      EntityRendererRegistry.register(NebEntityTypes.RIMURU_OGRE_FIGHT, context -> new OtherworlderRenderer<RimuruOgreFightEntity>(context, true) {
         protected void scale(RimuruOgreFightEntity rimuru, PoseStack pMatrixStack, float pPartialTickTime) {
            pMatrixStack.scale(0.75F, 0.75F, 0.75F);
         }
      });
      EntityRendererRegistry.register(NebEntityTypes.RIMURU_CLONE, context -> new OtherworlderRenderer<RimuruCloneEntity>(context, true) {
         protected void scale(RimuruCloneEntity rimuru, PoseStack pMatrixStack, float pPartialTickTime) {
            pMatrixStack.scale(0.75F, 0.75F, 0.75F);
         }
      });
      EntityRendererRegistry.register(NebEntityTypes.CARRION, CarrionRenderer::new);
      EntityRendererRegistry.register(NebEntityTypes.LUMINOUS_VALENTINE, LuminousRenderer::new);
      EntityRendererRegistry.register(NebEntityTypes.VELDORA, VeldoraRenderer::new);
   }
}
