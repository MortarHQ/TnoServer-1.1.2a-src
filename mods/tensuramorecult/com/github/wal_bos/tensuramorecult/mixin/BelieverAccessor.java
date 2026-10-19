package com.github.wal_bos.tensuramorecult.mixin;

import com.benji.netherman.common.entity.AzazelEntity;
import com.benji.netherman.common.entity.BelieverEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = BelieverEntity.class, remap = false)
public interface BelieverAccessor {
   @Accessor("currentAzazel")
   AzazelEntity tensuramorecult$getAzazel();
}
