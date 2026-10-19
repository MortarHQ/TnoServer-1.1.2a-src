package it.hurts.sskirillss.relics.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import it.hurts.sskirillss.relics.api.events.common.TooltipDisplayEvent;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.BeamsData;
import it.hurts.sskirillss.relics.utils.RenderUtils;
import it.hurts.sskirillss.relics.utils.data.GUIRenderer;
import it.hurts.sskirillss.relics.utils.data.SpriteAnchor;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.event.RenderTooltipEvent.Pre;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Vector2ic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(GuiGraphics.class)
public class GuiGraphicsMixin {
   @Unique
   private static final ResourceLocation UPGRADE_TEXTURE = ResourceLocation.fromNamespaceAndPath("relics", "textures/gui/item_state/upgrade.png");
   @Unique
   private static final ResourceLocation RESEARCH_TEXTURE = ResourceLocation.fromNamespaceAndPath("relics", "textures/gui/item_state/research.png");

   @Inject(
      method = "renderTooltipInternal",
      at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V", shift = Shift.AFTER),
      locals = LocalCapture.CAPTURE_FAILSOFT
   )
   public void onTooltipRender(
      Font font,
      List<ClientTooltipComponent> tooltip,
      int x,
      int y,
      ClientTooltipPositioner positioner,
      CallbackInfo info,
      Pre event,
      int width,
      int height,
      int postWidth,
      int postHeight,
      Vector2ic postPos
   ) {
      if (!tooltip.isEmpty()) {
         NeoForge.EVENT_BUS.post(new TooltipDisplayEvent(event.getItemStack(), (GuiGraphics)this, postWidth, postHeight, postPos.x(), postPos.y()));
      }
   }

   @Inject(
      method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z", shift = Shift.AFTER)
   )
   public void renderItemDecorations(Font font, ItemStack stack, int x, int y, String text, CallbackInfo ci) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null && stack.getItem() instanceof IRelicItem relic) {
         GuiGraphics guiGraphics = (GuiGraphics)this;
         PoseStack poseStack = guiGraphics.pose();
         float partialTicks = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
         float time = stack.getItem().hashCode() / 1000.0F + player.tickCount + partialTicks;
         float color = (float)(1.0 + Math.sin(time * 0.45F) * 0.1F);
         boolean renderedUpgradeIcon = false;
         boolean renderedResearchIcon = false;

         for (AbilityData ability : relic.getAbilitiesData().getAbilities().values()) {
            if (!renderedUpgradeIcon && relic.mayUpgrade(stack, ability.getId())) {
               poseStack.pushPose();
               poseStack.translate(x + 9, y, 200.0F);
               poseStack.translate(3.5F, 3.5F, 0.0F);
               poseStack.mulPose(Axis.ZP.rotation((float)(Math.sin(time * 0.35F) * 0.15F)));
               poseStack.translate(-3.5F, -3.5F, 0.0F);
               GUIRenderer.begin(UPGRADE_TEXTURE, poseStack).color(color, color, color, 1.0F).anchor(SpriteAnchor.TOP_LEFT).end();
               poseStack.popPose();
               renderedUpgradeIcon = true;
            }

            if (!renderedResearchIcon && (relic.mayResearch(stack, ability.getId()) || relic.mayUnlock(stack, ability.getId()))) {
               poseStack.pushPose();
               poseStack.translate(x + 9, y + 9, 200.0F);
               poseStack.translate(Math.sin(time * 0.25F) * 0.75, Math.cos(time * 0.25F) * 0.75, 0.0);
               RenderSystem.enableBlend();
               GUIRenderer.begin(RESEARCH_TEXTURE, poseStack).color(color, color, color, 1.0F).anchor(SpriteAnchor.TOP_LEFT).end();
               RenderSystem.disableBlend();
               poseStack.popPose();
               renderedResearchIcon = true;
            }
         }
      }
   }

   @Inject(
      method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;IIII)V",
      at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V", shift = Shift.AFTER)
   )
   public void renderItem(LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int guiOffset, CallbackInfo ci) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null && stack.getItem() instanceof IRelicItem relic) {
         GuiGraphics guiGraphics = (GuiGraphics)this;
         PoseStack poseStack = guiGraphics.pose();
         float partialTicks = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
         float time = seed + player.tickCount + partialTicks;
         if (relic.isRelicFlawless(stack)) {
            BeamsData data = relic.getStyleData().getBeams().apply(player, stack);
            int beams = 8;

            for (int i = 0; i < beams; i++) {
               float angle = (float)(i * 2.0F * Math.PI / beams);
               poseStack.pushPose();
               poseStack.mulPose(Axis.ZP.rotation(angle));
               poseStack.mulPose(Axis.ZP.rotation(time * 0.025F));
               double length = 0.85F + (i % 2 == 0 ? Math.sin(time * 0.25F) : Math.cos(time * 0.25F)) * 0.1F;
               RenderUtils.renderFlatBeam(guiGraphics, partialTicks, (float)length, 0.45F, data.getStartColor(), data.getEndColor());
               poseStack.popPose();
            }
         }
      }
   }
}
