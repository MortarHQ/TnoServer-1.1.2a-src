package com.faboslav.variantsandventures.common.mixin;

import com.faboslav.variantsandventures.common.api.ZombieApi;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ZombieRenderer.class)
public abstract class ZombieRendererMixin extends ZombieAbstractZombieRendererMixin<Zombie, ZombieModel<Zombie>> {
   protected ZombieRendererMixin(Context context) {
      super(context);
   }

   @Override
   public boolean variantsandventures$isShaking(LivingEntity entity, Operation<Boolean> original) {
      return ((ZombieApi)entity).variantsandventures$isShaking();
   }
}
