package com.tensura_tno.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.tensura_tno.util.RandomUniqueSkillGuard;
import io.github.manasmods.tensura.menu.ReincarnationMenu;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ReincarnationMenu.class, priority = 900)
public class RandomUniqueSkillStextrasGuardMixin {
   @WrapMethod(method = "randomUniqueSkill(Lnet/minecraft/world/entity/player/Player;Z)V", remap = false)
   private static void tno$guardRandomUniqueSkill(Player player, boolean coverEP, Operation<Void> original) {
      RandomUniqueSkillGuard.runGuarded(() -> original.call(new Object[]{player, coverEP}));
   }
}
