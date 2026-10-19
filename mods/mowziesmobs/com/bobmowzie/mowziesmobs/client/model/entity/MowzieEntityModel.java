package com.bobmowzie.mowziesmobs.client.model.entity;

import com.bobmowzie.mowziesmobs.client.model.tools.MMModelAnimator;
import com.bobmowzie.mowziesmobs.server.entity.MowzieLLibraryEntity;
import com.ilexiconn.llibrary.client.model.tools.AdvancedModelBase;
import com.ilexiconn.llibrary.client.model.tools.BasicModelRenderer;
import java.util.function.Function;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public abstract class MowzieEntityModel<T extends MowzieLLibraryEntity> extends AdvancedModelBase<T> {
   protected final MMModelAnimator animator = MMModelAnimator.create();

   protected MowzieEntityModel() {
   }

   protected MowzieEntityModel(Function<ResourceLocation, RenderType> renderTypeFunction) {
      super(renderTypeFunction);
   }

   public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      float delta = ageInTicks - entityIn.tickCount;
      this.animator.update(entityIn, delta);
      this.animate(entityIn, limbSwing, limbSwingAmount, netHeadYaw, headPitch, delta);
   }

   private T cast(Entity entity) {
      return (T)entity;
   }

   protected abstract void animate(T var1, float var2, float var3, float var4, float var5, float var6);

   protected static void setRotateAngle(BasicModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
