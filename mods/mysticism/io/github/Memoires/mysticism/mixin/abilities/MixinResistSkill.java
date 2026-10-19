package io.github.Memoires.mysticism.mixin.abilities;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.resist.ResistSkill;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.world.TensuraGameRules;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ResistSkill.class)
public class MixinResistSkill {
   @WrapOperation(
      method = "evolveToNullification(Lio/github/manasmods/manascore/skill/api/ManasSkillInstance;Lnet/minecraft/world/entity/LivingEntity;)V",
      at = @At(value = "NEW", target = "Lio/github/manasmods/tensura/ability/TensuraSkillInstance;")
   )
   private TensuraSkillInstance mysticism$evolveToNullification(
      ManasSkill skill, Operation<TensuraSkillInstance> original, @Local(argsOnly = true) LivingEntity entity
   ) {
      TensuraSkillInstance skillInstance = (TensuraSkillInstance)original.call(new Object[]{skill});
      if (SkillUtils.hasSkillFully(entity, (ManasSkill)UniqueMysticismSkills.RESTRICTED.get()) && skill instanceof TensuraSkill tensuraSkill) {
         skillInstance.getOrCreateTag().putBoolean("NoMagiculeCost", true);
      }

      return skillInstance;
   }

   @Inject(
      method = "evolveToNullification(Lio/github/manasmods/manascore/skill/api/ManasSkillInstance;Lnet/minecraft/world/entity/LivingEntity;)V",
      at = @At("TAIL"),
      cancellable = true
   )
   private void mysticism$evolveToNullification(ManasSkillInstance instance, LivingEntity entity, CallbackInfo ci) {
      if (instance instanceof TensuraSkillInstance skillInstance) {
         if (SkillUtils.hasSkillFully(entity, (ManasSkill)UniqueMysticismSkills.RESTRICTED.get())
            && skillInstance.getSkill() instanceof TensuraSkill tensuraSkill) {
            double cost = tensuraSkill.getAcquiringMagiculeCost(skillInstance) * entity.level().getGameRules().getInt(TensuraGameRules.MP_SKILL_COST) / 100.0;
            AttributeInstance aura = entity.getAttribute(TensuraAttributes.MAX_AURA);
            if (aura == null) {
               ci.cancel();
               return;
            }

            if (aura.getValue() <= cost) {
               entity.sendSystemMessage(
                  Component.translatable("mysticism.skill.acquire_failed.ap", new Object[]{tensuraSkill.getChatDisplayName(true)})
                     .withStyle(ChatFormatting.RED)
               );
               ci.cancel();
               return;
            }

            aura.setBaseValue(aura.getBaseValue() - cost);
            IExistence existence = TensuraStorages.getExistenceFrom(entity);
            existence.setAura(existence.getAura() - cost);
            existence.markDirty();
         }
      }
   }
}
