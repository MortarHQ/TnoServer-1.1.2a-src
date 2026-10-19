package com.tensura_tno.mixin.client;

import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "io.github.manasmods.tensura.ability.skill.extra.SenseHeatSourceSkill", remap = false)
public class SenseHeatSourceCollisionShapeMixin {
   @Redirect(
      method = "<init>",
      at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0, remap = false),
      require = 0,
      remap = false
   )
   private static boolean tensuraTno$skipNativeFullCubeProvider(List<Object> ignored, Object provider) {
      return true;
   }
}
