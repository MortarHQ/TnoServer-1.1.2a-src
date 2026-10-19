package com.ilexiconn.llibrary.client.model.tools;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.Function;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public abstract class BasicModelBase<T extends Entity> extends EntityModel<T> {
   public int textureWidth = 64;
   public int textureHeight = 32;
   public final List<BasicModelRenderer> boxList = Lists.newArrayList();

   protected BasicModelBase() {
      this(RenderType::entityCutoutNoCull);
   }

   protected BasicModelBase(Function<ResourceLocation, RenderType> p_102613_) {
      super(p_102613_);
   }

   public void accept(BasicModelRenderer modelRenderer) {
      this.boxList.add(modelRenderer);
   }

   public abstract void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6);

   public void prepareMobModel(T p_102614_, float p_102615_, float p_102616_, float p_102617_) {
   }
}
