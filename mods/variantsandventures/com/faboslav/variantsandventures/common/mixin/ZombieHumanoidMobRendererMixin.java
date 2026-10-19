package com.faboslav.variantsandventures.common.mixin;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(HumanoidMobRenderer.class)
public abstract class ZombieHumanoidMobRendererMixin<T extends Mob, M extends HumanoidModel<T>> extends ZombieMobRendererMixin<T, M> {
   protected ZombieHumanoidMobRendererMixin(Context context) {
      super(context);
   }
}
