package io.github.Memoires.mysticism.mixin.effect;

import io.github.manasmods.tensura.effect.ability.InstantRegenerationEffect;
import io.github.manasmods.tensura.storage.ep.IExistence;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(InstantRegenerationEffect.class)
public interface InstantRegenerationEffectAccessor {
   @Invoker("heal")
   void mysticism$heal(LivingEntity var1, float var2);

   @Invoker("healSHP")
   void mysticism$healSHP(LivingEntity var1, IExistence var2, double var3, double var5, double var7);
}
