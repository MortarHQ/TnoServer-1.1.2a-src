package io.github.Memoires.mysticism.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.Memoires.mysticism.registry.dimension.MysticismDimensions;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillClientUtils;
import io.github.manasmods.tensura.ability.SkillUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SkillClientUtils.class)
public class MixinSkillClientUtils {
   @ModifyReturnValue(method = "getGlowColor(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/entity/Entity;)I", at = @At("RETURN"))
   private static int mysticism$getGlowColor(int original, @Local(argsOnly = true) Player player, @Local(argsOnly = true) Entity entity) {
      if ((original == 16089632 || original == 10278389 || original == 16490018) && player.level().dimension() == MysticismDimensions.ELEMENTAL_REALM) {
         return -1;
      } else {
         return !(
               entity instanceof LivingEntity livingEntity
                  && SkillUtils.hasSkillFully(livingEntity, (ManasSkill)UniqueMysticismSkills.RESTRICTED.get())
                  && (original == 5636095 || original == 5416173 || original == 2208948 || original == 10278389)
            )
            ? original
            : -1;
      }
   }
}
