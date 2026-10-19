package io.github.Memoires.mysticism.mixin.abilities;

import io.github.Memoires.mysticism.registry.dimension.MysticismDimensions;
import io.github.manasmods.tensura.ability.SkillClientUtils;
import io.github.manasmods.tensura.ability.SkillClientUtils.XrayType;
import io.github.manasmods.tensura.ability.skill.extra.SenseHeatSourceSkill;
import java.util.function.Function;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SenseHeatSourceSkill.class)
public class MixinSenseHeatSourceSkill {
   @Inject(method = "<init>()V", at = @At("RETURN"))
   private void mysticism$disableInElemental(CallbackInfo ci) {
      for (int i = 0; i < SkillClientUtils.XRAY_TYPES.size(); i++) {
         Function<Player, XrayType> original = (Function<Player, XrayType>)SkillClientUtils.XRAY_TYPES.get(i);
         SkillClientUtils.XRAY_TYPES.set(i, player -> player.level().dimension() == MysticismDimensions.ELEMENTAL_REALM ? null : original.apply(player));
      }
   }
}
