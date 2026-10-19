package io.github.Memoires.mysticism.handler.skill;

import io.github.Memoires.mysticism.bridge.GardenerFoodAPI;
import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.event.InventoryAddWatcher;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.Memoires.mysticism.util.ItemDataUtils;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.storage.TensuraStorages;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemAttributeModifiers.Builder;
import net.minecraft.world.item.component.ItemAttributeModifiers.Entry;

public class GardenerHandler {
   protected static final UniqueMysticismSkillConfig.Gardener CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Gardener;
   protected static final ResourceLocation GARDENER_HOE_DAMAGE = ResourceLocation.fromNamespaceAndPath("mysticism", "gardener_hoe_increase");

   public static void init() {
      GardenerFoodAPI.register();
      InventoryAddWatcher.register(
         (player, itemEntity, itemStack) -> {
            Skills skillList = SkillAPI.getSkillsFrom(player);
            Optional<ManasSkillInstance> gardener = skillList.getSkill((ManasSkill)UniqueMysticismSkills.GARDENER.get());
            ItemStack held = player.getMainHandItem();
            boolean alreadyApplied = ItemDataUtils.getFlag(held, "HoeMastery");
            if (!held.isEmpty() && held.getItem() instanceof HoeItem && !alreadyApplied) {
               ItemAttributeModifiers mods = (ItemAttributeModifiers)held.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
               Optional<Entry> baseOpt = mods.modifiers()
                  .stream()
                  .filter(e -> e.attribute() == Attributes.ATTACK_DAMAGE && e.slot() == EquipmentSlotGroup.MAINHAND)
                  .findFirst();
               if (gardener.isPresent() && TensuraStorages.getAbilityFrom(player).isAbilityInActivePreset(gardener.get().getSkill()) && baseOpt.isPresent()) {
                  held.set(DataComponents.ATTRIBUTE_MODIFIERS, addAmount(held, getDamageFromTier(held), EquipmentSlotGroup.MAINHAND));
                  ItemDataUtils.setFlag(held, "HoeMastery", true);
               } else if (gardener.isEmpty() && alreadyApplied) {
                  held.set(DataComponents.ATTRIBUTE_MODIFIERS, removeAmount(held, EquipmentSlotGroup.MAINHAND));
                  ItemDataUtils.setFlag(held, "HoeMastery", false);
               }
            }
         }
      );
   }

   public static void onInteract(Player player, InteractionHand hand, Entity target) {
      if (!player.level().isClientSide) {
         ItemStack stack = player.getItemInHand(hand);
         if (!stack.isEmpty()) {
            if (stack.has(DataComponents.FOOD)) {
               if (ItemDataUtils.getFlag(stack, "GardenerBlessed")) {
                  if (target instanceof TamableAnimal tame) {
                     boolean isBreedingItem = tame.isFood(stack);
                     boolean isInjured = tame.getHealth() < tame.getMaxHealth();
                     if (isBreedingItem || isInjured) {
                        applyBlessingFromStack(tame, stack);
                        if (!player.isCreative()) {
                           stack.shrink(1);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   static ItemAttributeModifiers addAmount(ItemStack stack, double add, EquipmentSlotGroup slot) {
      ItemAttributeModifiers mods = (ItemAttributeModifiers)stack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
      Builder b = ItemAttributeModifiers.builder();

      for (Entry e : mods.modifiers()) {
         if (slot == EquipmentSlotGroup.MAINHAND) {
            b.add(e.attribute(), e.modifier(), e.slot());
         }
      }

      b.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(GARDENER_HOE_DAMAGE, add, Operation.ADD_VALUE), slot);
      return b.build();
   }

   static ItemAttributeModifiers removeAmount(ItemStack stack, EquipmentSlotGroup slot) {
      ItemAttributeModifiers mods = (ItemAttributeModifiers)stack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
      Builder b = ItemAttributeModifiers.builder();

      for (Entry e : mods.modifiers()) {
         if (!e.modifier().id().equals(GARDENER_HOE_DAMAGE) && slot == EquipmentSlotGroup.MAINHAND) {
            b.add(e.attribute(), e.modifier(), e.slot());
         }
      }

      return b.build();
   }

   static void applyBlessingFromStack(LivingEntity entity, ItemStack source) {
      boolean mastery = ItemDataUtils.getFlag(source, "GardenerMastery");
      if (ItemDataUtils.getFlag(source, "DaytimeBlessing")) {
         Optional<? extends Holder<MobEffect>> effect = parseEffectFromResourceLocation(CONFIG.dawnBlessingEffect);
         effect.ifPresent(
            mobEffectHolder -> entity.addEffect(
               new MobEffectInstance(
                  mobEffectHolder,
                  mastery ? CONFIG.dawnDurationMastered : CONFIG.dawnDuration,
                  (mastery ? CONFIG.dawnEffectLevelMastered : CONFIG.dawnEffectLevel) - 1,
                  false,
                  true,
                  true
               )
            )
         );
      } else if (ItemDataUtils.getFlag(source, "NoonBlessing")) {
         Optional<? extends Holder<MobEffect>> effect = parseEffectFromResourceLocation(CONFIG.noonBlessingEffect);
         effect.ifPresent(
            mobEffectHolder -> entity.addEffect(
               new MobEffectInstance(
                  mobEffectHolder,
                  mastery ? CONFIG.noonDurationMastered : CONFIG.noonDuration,
                  (mastery ? CONFIG.noonEffectLevelMastered : CONFIG.noonEffectLevel) - 1,
                  false,
                  true,
                  true
               )
            )
         );
      } else if (ItemDataUtils.getFlag(source, "NightBlessing")) {
         Optional<? extends Holder<MobEffect>> effect = parseEffectFromResourceLocation(CONFIG.nightBlessingEffect);
         effect.ifPresent(
            mobEffectHolder -> entity.addEffect(
               new MobEffectInstance(
                  mobEffectHolder,
                  mastery ? CONFIG.nightDurationMastered : CONFIG.nightDuration,
                  (mastery ? CONFIG.nightEffectLevelMastered : CONFIG.nightEffectLevel) - 1,
                  false,
                  true,
                  true
               )
            )
         );
      }
   }

   static Optional<? extends Holder<MobEffect>> parseEffectFromResourceLocation(String resource) {
      ResourceLocation id = ResourceLocation.tryParse(resource);
      if (id == null) {
         return Optional.empty();
      }

      ResourceKey<MobEffect> key = ResourceKey.create(Registries.MOB_EFFECT, id);
      return BuiltInRegistries.MOB_EFFECT.getHolder(key);
   }

   static float getDamageFromTier(ItemStack itemStack) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IllegalStateException: Invalid switch case set: [[const(0)], [const(1)], [const(2), const(3)], [const(4)], [const(5)], [const(6)], [const(7)], [const(8)], [const(9)], [const(10)], [const(11)], [null]] for selector of type Lnet/minecraft/world/item/Tier;
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.SwitchHeadExprent.checkExprTypeBounds(SwitchHeadExprent.java:63)
      //   at org.jetbrains.java.decompiler.modules.decompiler.vars.VarTypeProcessor.checkTypeExpr(VarTypeProcessor.java:156)
      //   at org.jetbrains.java.decompiler.modules.decompiler.vars.VarTypeProcessor.checkTypeExprent(VarTypeProcessor.java:137)
      //   at org.jetbrains.java.decompiler.modules.decompiler.vars.VarTypeProcessor.lambda$processVarTypes$2(VarTypeProcessor.java:125)
      //   at org.jetbrains.java.decompiler.modules.decompiler.flow.DirectGraph.iterateExprents(DirectGraph.java:114)
      //   at org.jetbrains.java.decompiler.modules.decompiler.vars.VarTypeProcessor.processVarTypes(VarTypeProcessor.java:125)
      //   at org.jetbrains.java.decompiler.modules.decompiler.vars.VarTypeProcessor.calculateVarTypes(VarTypeProcessor.java:44)
      //   at org.jetbrains.java.decompiler.modules.decompiler.vars.VarVersionsProcessor.setVarVersions(VarVersionsProcessor.java:68)
      //   at org.jetbrains.java.decompiler.modules.decompiler.vars.VarProcessor.setVarVersions(VarProcessor.java:47)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:320)
      //
      // Bytecode:
      // 00: aload 0
      // 01: invokevirtual net/minecraft/world/item/ItemStack.getItem ()Lnet/minecraft/world/item/Item;
      // 04: astore 2
      // 05: aload 2
      // 06: instanceof net/minecraft/world/item/HoeItem
      // 09: ifeq ae
      // 0c: aload 2
      // 0d: checkcast net/minecraft/world/item/HoeItem
      // 10: astore 1
      // 11: aload 1
      // 12: invokevirtual net/minecraft/world/item/HoeItem.getTier ()Lnet/minecraft/world/item/Tier;
      // 15: astore 2
      // 16: aload 2
      // 17: dup
      // 18: invokestatic java/util/Objects.requireNonNull (Ljava/lang/Object;)Ljava/lang/Object;
      // 1b: pop
      // 1c: astore 3
      // 1d: bipush 0
      // 1e: istore 4
      // 20: aload 3
      // 21: iload 4
      // 23: invokedynamic typeSwitch (Ljava/lang/Object;I)I bsm=java/lang/runtime/SwitchBootstraps.typeSwitch (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite; args=[ null.invoke Ljava/lang/Enum$EnumDesc;, null.invoke Ljava/lang/Enum$EnumDesc;, null.invoke Ljava/lang/Enum$EnumDesc;, null.invoke Ljava/lang/Enum$EnumDesc;, null.invoke Ljava/lang/Enum$EnumDesc;, null.invoke Ljava/lang/Enum$EnumDesc;, null.invoke Ljava/lang/Enum$EnumDesc;, null.invoke Ljava/lang/Enum$EnumDesc;, null.invoke Ljava/lang/Enum$EnumDesc;, null.invoke Ljava/lang/Enum$EnumDesc;, null.invoke Ljava/lang/Enum$EnumDesc;, null.invoke Ljava/lang/Enum$EnumDesc; ]
      // 28: tableswitch 130 0 11 64 70 76 76 82 88 94 100 106 112 118 124
      // 68: ldc_w 6.0
      // 6b: goto ad
      // 6e: ldc_w 7.0
      // 71: goto ad
      // 74: ldc_w 9.0
      // 77: goto ad
      // 7a: ldc_w 11.0
      // 7d: goto ad
      // 80: ldc_w 4.0
      // 83: goto ad
      // 86: ldc_w 10.0
      // 89: goto ad
      // 8c: ldc_w 13.0
      // 8f: goto ad
      // 92: ldc_w 15.0
      // 95: goto ad
      // 98: ldc_w 16.0
      // 9b: goto ad
      // 9e: ldc_w 18.0
      // a1: goto ad
      // a4: ldc_w 32.0
      // a7: goto ad
      // aa: ldc_w 5.0
      // ad: freturn
      // ae: fconst_0
      // af: freturn
   }
}
