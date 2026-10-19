package dev.shadowako.trlagbegon.mixin;

import io.github.manasmods.tensura.entity.template.subclass.IGiantMob;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = IGiantMob.class, remap = false)
public interface IGiantMobTrLagMixin {
}
