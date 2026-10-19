package com.almostreliable.lootjs.kube.wrappers;

import com.almostreliable.lootjs.util.Utils;
import dev.latvian.mods.kubejs.script.ConsoleJS;
import dev.latvian.mods.kubejs.script.KubeJSContext;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.type.RecordTypeInfo;
import dev.latvian.mods.rhino.type.TypeInfo;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.advancements.critereon.MobEffectsPredicate;
import net.minecraft.advancements.critereon.MobEffectsPredicate.MobEffectInstancePredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

public class MobEffectsPredicateWrapper {
   private static final RecordTypeInfo EIP_TYPE_INFO = (RecordTypeInfo)TypeInfo.of(MobEffectInstancePredicate.class);

   public static MobEffectsPredicate of(Context cx, Object o) {
      if (o instanceof MobEffectsPredicate m) {
         return m;
      } else {
         RegistryLookup<MobEffect> allEffects = ((KubeJSContext)cx).getRegistries().access().lookupOrThrow(Registries.MOB_EFFECT);
         Map<Holder<MobEffect>, MobEffectInstancePredicate> effectMap = new HashMap<>();

         for (Object obj : Utils.listOrThrow(o)) {
            try {
               Map<String, Object> map = Utils.mapOrThrow(obj);
               if (!map.containsKey("id")) {
                  throw new RuntimeException("Expected effect id key");
               }

               String effectId = map.get("id").toString();
               Reference<MobEffect> effect = allEffects.getOrThrow(ResourceKey.create(Registries.MOB_EFFECT, ResourceLocation.parse(effectId)));
               MobEffectInstancePredicate instance = (MobEffectInstancePredicate)EIP_TYPE_INFO.wrap(cx, map.get("duration"), EIP_TYPE_INFO);
               effectMap.put(effect, instance);
            } catch (Exception error) {
               ConsoleJS.SERVER.error(error);
            }
         }

         return new MobEffectsPredicate(effectMap);
      }
   }
}
