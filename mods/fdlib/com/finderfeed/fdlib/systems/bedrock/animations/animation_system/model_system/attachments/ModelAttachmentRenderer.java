package com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.attachments;

import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.ModelSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;

public abstract class ModelAttachmentRenderer<T extends ModelAttachment<T, ?>> {
   public abstract void render(
      T var1, ModelAttachmentRenderContext var2, ModelSystem var3, PoseStack var4, MultiBufferSource var5, float var6, int var7, int var8
   );
}
