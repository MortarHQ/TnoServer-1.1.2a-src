package net.unusual.block_factorys_bosses.client.renderer;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderArmEvent;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.client.AnimationTickHolder;
import net.unusual.block_factorys_bosses.client.model.Modelchestplate;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;

@EventBusSubscriber(Dist.CLIENT)
public class DragonBonesFirstPersonRenderer {
   private static boolean rendererActive = false;
   private static List<Item> disallowedItems = new ArrayList<>();

   public static void clientTick() {
      Minecraft mc = Minecraft.getInstance();
      Item heldItem = mc.player.getMainHandItem().getItem();
      Item offHandItem = mc.player.getOffhandItem().getItem();
      if (!disallowedItems.contains(heldItem) && !disallowedItems.contains(offHandItem)) {
         rendererActive = mc.player != null && BossesRiseItems.DRAGON_BONES_CHESTPLATE.get() == mc.player.getItemBySlot(EquipmentSlot.CHEST).getItem();
      } else {
         rendererActive = false;
      }
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void onRenderPlayerHand(RenderArmEvent event) {
      if (rendererActive) {
         if (disallowedItems.isEmpty()) {
            disallowedItems = List.of((Item)BossesRiseItems.SANDWORM_GAUNTLET.get(), (Item)BossesRiseItems.ICE_GAUNTLET.get());
         }

         Minecraft mc = Minecraft.getInstance();
         LocalPlayer player = mc.player;
         MultiBufferSource buffer = event.getMultiBufferSource();
         if (mc.getEntityRenderDispatcher().getRenderer(player) instanceof PlayerRenderer pr) {
            Modelchestplate var9 = new Modelchestplate(Minecraft.getInstance().getEntityModels().bakeLayer(Modelchestplate.LAYER_LOCATION));
            ((PlayerModel)pr.getModel()).copyPropertiesTo(var9);
            int index = Mth.floor(Math.round(AnimationTickHolder.getRenderTime()) % 40 * 0.4F);
            ResourceLocation armor_location = BossesRise.prefix("textures/entities/dragon_chestplate/dragon_chestplate_" + index + ".png");
            var9.setupAnim(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            ModelPart armPart = event.getArm() == HumanoidArm.LEFT ? var9.leftArm : var9.rightArm;
            armPart.zRot = 0.125F;
            armPart.render(
               event.getPoseStack(), buffer.getBuffer(RenderType.entityCutoutNoCull(armor_location)), event.getPackedLight(), OverlayTexture.NO_OVERLAY
            );
         }
      }
   }
}
