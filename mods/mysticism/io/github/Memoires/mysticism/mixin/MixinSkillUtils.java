package io.github.Memoires.mysticism.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.Memoires.mysticism.config.effect.EffectConfig;
import io.github.Memoires.mysticism.registry.dimension.MysticismDimensions;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.storage.TensuraStorages;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SkillUtils.class)
public class MixinSkillUtils {
   private static final EffectConfig.BrimstoneFlames CONFIG = ((EffectConfig)ConfigRegistry.getConfig(EffectConfig.class)).BrimstoneFlames;

   @ModifyReturnValue(method = "canBlockSoundDetect(Lnet/minecraft/world/entity/Entity;)Z", at = @At("RETURN"))
   private static boolean mysticism$canBlockSoundDetect(boolean original, Entity entity) {
      if (entity instanceof Player player
         && TensuraStorages.getAbilityFrom(player).isAbilityInActivePreset((ManasSkill)UniqueMysticismSkills.HIDDEN_RULER.get())) {
         original = true;
      }

      return original;
   }

   @ModifyReturnValue(method = "inSpiritualWorld(Lnet/minecraft/resources/ResourceKey;)Z", at = @At("RETURN"))
   private static boolean mysticism$inSpiritualWorld(boolean original, ResourceKey<Level> dimension) {
      if (dimension == MysticismDimensions.ELEMENTAL_REALM) {
         original = true;
      }

      return original;
   }
}
