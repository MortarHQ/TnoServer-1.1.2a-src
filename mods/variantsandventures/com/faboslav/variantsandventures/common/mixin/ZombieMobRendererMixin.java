package com.faboslav.variantsandventures.common.mixin;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MobRenderer.class)
public abstract class ZombieMobRendererMixin<T extends Mob, M extends EntityModel<T>> extends ZombieLivingEntityRendererMixin<T, M> {
   protected ZombieMobRendererMixin(Context context) {
      super(context);
   }
}
