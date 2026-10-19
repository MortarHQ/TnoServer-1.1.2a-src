package net.unusual.block_factorys_bosses.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderArmEvent;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.client.model.Modelknight_arm;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;

@EventBusSubscriber(Dist.CLIENT)
public class KnightFirstPersonRenderer {
   private static boolean rendererActive = false;

   public static void clientTick() {
      Minecraft mc = Minecraft.getInstance();
      rendererActive = mc.player != null && BossesRiseItems.KNIGHT_CHESTPLATE.get() == mc.player.getItemBySlot(EquipmentSlot.CHEST).getItem();
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void onRenderPlayerHand(RenderArmEvent event) {
      if (rendererActive) {
         Minecraft mc = Minecraft.getInstance();
         LocalPlayer player = mc.player;
         MultiBufferSource buffer = event.getMultiBufferSource();
         if (mc.getEntityRenderDispatcher().getRenderer(player) instanceof PlayerRenderer pr) {
            Modelknight_arm var8 = new Modelknight_arm(Minecraft.getInstance().getEntityModels().bakeLayer(Modelknight_arm.LAYER_LOCATION));
            ((PlayerModel)pr.getModel()).copyPropertiesTo(var8);
            ResourceLocation armor_location = BossesRise.prefix("textures/entities/knight_arm.png");
            var8.setupAnim(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            ModelPart armPart = event.getArm() == HumanoidArm.LEFT ? var8.l_arm : var8.r_arm;
            armPart.zRot = 0.125F;
            armPart.render(
               event.getPoseStack(), buffer.getBuffer(RenderType.entityCutoutNoCull(armor_location)), event.getPackedLight(), OverlayTexture.NO_OVERLAY
            );
            event.setCanceled(true);
         }
      }
   }
}
