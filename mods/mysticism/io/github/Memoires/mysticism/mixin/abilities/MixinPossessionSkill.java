package io.github.Memoires.mysticism.mixin.abilities;

import io.github.Memoires.mysticism.registry.race.MysticismRaces;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.race.api.Races;
import io.github.manasmods.tensura.ability.skill.intrinsic.PossessionSkill;
import io.github.manasmods.tensura.entity.human.golem.BoneGolemEntity;
import java.util.Optional;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PossessionSkill.class)
public class MixinPossessionSkill {
   @Inject(
      method = "copyStatsAndSkills(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/player/Player;DD)V",
      at = @At("HEAD"),
      cancellable = true
   )
   private static void copyStatsAndSkills(LivingEntity target, Player owner, double maxAttack, double maxHealth, CallbackInfo ci) {
      Races races = RaceAPI.getRaceFrom(owner);
      Optional<ManasRaceInstance> optional = races.getRace();
      if (optional.isEmpty()) {
         ci.cancel();
      } else {
         ManasRaceInstance originalRace = optional.get();
         if (target instanceof BoneGolemEntity
            && (
               originalRace.getRace() == MysticismRaces.GREATER_DOLL.get()
                  || originalRace.getRace() == MysticismRaces.ARCH_DOLL.get()
                  || originalRace.getRace() == MysticismRaces.TENGU.get()
            )) {
            ci.cancel();
         }
      }
   }
}
