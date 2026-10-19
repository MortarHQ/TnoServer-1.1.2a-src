package com.faboslav.variantsandventures.common.mixin;

import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.world.entity.monster.Zombie;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractZombieRenderer.class)
public abstract class ZombieAbstractZombieRendererMixin<T extends Zombie, M extends ZombieModel<T>> extends ZombieHumanoidMobRendererMixin<T, M> {
   protected ZombieAbstractZombieRendererMixin(Context context) {
      super(context);
   }
}
