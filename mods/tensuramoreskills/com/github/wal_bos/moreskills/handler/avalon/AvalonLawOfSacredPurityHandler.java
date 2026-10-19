package com.github.wal_bos.moreskills.handler.avalon;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.AvalonLawSacredPurityVfxPayload;
import com.github.wal_bos.moreskills.support.RoyalSkillSupport;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.unique.CookSkill;
import io.github.manasmods.tensura.data.TensuraTags.MobEffects;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.storage.effect.EffectStorage;
import java.util.ArrayList;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

final class AvalonLawOfSacredPurityHandler implements AvalonLawHandler {
   @Override
   public String name() {
      return "Law of Sacred Purity";
   }

   @Override
   public void sendVfx(ServerLevel level, LivingEntity owner, float radius, int durationTicks, int variant) {
      AvalonLawSacredPurityVfxPayload.send(level, owner, null, owner.position(), owner.position(), radius, durationTicks, variant);
   }

   @Override
   public void onActivate(ManasSkillInstance instance, LivingEntity owner) {
      cleanse(owner);

      for (LivingEntity ally : RoyalSkillSupport.alliesInRange(owner, (Double)MoreSkillsConfigs.GRAND.avalon.royalLawRadius.get())) {
         cleanse(ally);
      }
   }

   @Override
   public void tick(ManasSkillInstance instance, LivingEntity owner) {
      if (owner.tickCount % 40 == 0) {
         cleanse(owner);

         for (LivingEntity ally : RoyalSkillSupport.alliesInRange(owner, (Double)MoreSkillsConfigs.GRAND.avalon.royalLawRadius.get())) {
            cleanse(ally);
         }
      }
   }

   private static void cleanse(LivingEntity target) {
      EffectStorage.clearSeverance(target);
      CookSkill.removeCookedHP(target);
      TensuraMobEffect.removePredicateEffect(target, cleansingPredicate());
      ResourceLocation scale = ((ResourceKey)Attributes.SCALE.unwrapKey().orElseThrow()).location();

      for (Attribute attribute : BuiltInRegistries.ATTRIBUTE) {
         ResourceLocation id = BuiltInRegistries.ATTRIBUTE.getKey(attribute);
         if (id != null && !id.equals(scale)) {
            Holder<Attribute> holder = BuiltInRegistries.ATTRIBUTE.wrapAsHolder(attribute);
            AttributeInstance instance = target.getAttribute(holder);
            if (instance != null) {
               for (AttributeModifier modifier : new ArrayList(instance.getModifiers())) {
                  if (modifier.amount() < 0.0) {
                     instance.removeModifier(modifier.id());
                  }
               }
            }
         }
      }
   }

   private static Predicate<Holder<MobEffect>> cleansingPredicate() {
      TagKey<MobEffect> law = MobEffects.AFFECTED_BY_LAW_MANIPULATION;
      return effect -> effect.equals(TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_INTERFERENCE)) || effect.is(law);
   }
}
