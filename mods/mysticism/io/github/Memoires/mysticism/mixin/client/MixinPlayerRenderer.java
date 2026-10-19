package io.github.Memoires.mysticism.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public class MixinPlayerRenderer {
   @Inject(
      method = "getTextureLocation(Lnet/minecraft/client/player/AbstractClientPlayer;)Lnet/minecraft/resources/ResourceLocation;",
      at = @At("HEAD"),
      cancellable = true
   )
   private void overrideSkin(AbstractClientPlayer target, CallbackInfoReturnable<ResourceLocation> cir) {
      Minecraft mc = Minecraft.getInstance();
      LocalPlayer viewer = mc.player;
      if (viewer != null) {
         if (SkillUtils.isSkillToggled(target, (ManasSkill)UniqueMysticismSkills.FOURTH_WALL.get())) {
            double analysisLevel = viewer.getAttributeValue(TensuraAttributes.ANALYSIS_LEVEL);
            if (!(analysisLevel >= 10.0)) {
               cir.setReturnValue(ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/skill/obfuscation.png"));
            }
         }
      }
   }

   @Inject(
      method = "renderNameTag*(Lnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IF)V",
      at = @At("HEAD"),
      cancellable = true
   )
   private void corruptNameTag(
      AbstractClientPlayer target, Component component, PoseStack poseStack, MultiBufferSource buffer, int packedLight, float partialTick, CallbackInfo ci
   ) {
      Minecraft mc = Minecraft.getInstance();
      LocalPlayer viewer = mc.player;
      if (viewer != null) {
         if (SkillUtils.isSkillToggled(target, (ManasSkill)UniqueMysticismSkills.FOURTH_WALL.get())) {
            double analysisLevel = viewer.getAttributeValue(TensuraAttributes.ANALYSIS_LEVEL);
            if (!(analysisLevel >= 10.0)) {
               ci.cancel();
               Component corrupted = Component.literal("The Ugliest King").withStyle(new ChatFormatting[]{ChatFormatting.OBFUSCATED, ChatFormatting.DARK_GRAY});
               Minecraft.getInstance()
                  .font
                  .drawInBatch(corrupted, 0.0F, 0.0F, 8947848, false, poseStack.last().pose(), buffer, DisplayMode.NORMAL, 0, packedLight);
            }
         }
      }
   }
}
