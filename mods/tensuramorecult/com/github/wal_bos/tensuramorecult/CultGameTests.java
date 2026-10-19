package com.github.wal_bos.tensuramorecult;

import com.benji.netherman.common.entity.AzazelEntity;
import com.benji.netherman.common.entity.AzazelHumanEntity;
import com.benji.netherman.common.entity.BelieverEntity;
import com.benji.netherman.init.ModEntities;
import com.benji.netherman.init.ModItems;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.item.misc.TensuraDataComponents;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.UUID;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("tensuramorecult")
@PrefixGameTestTemplate(false)
public final class CultGameTests {
   @GameTest(template = "empty", timeoutTicks = 250)
   public static void compatibility(GameTestHelper helper) {
      AzazelEntity head = (AzazelEntity)helper.spawn((EntityType)ModEntities.AZAZEL.get(), 3, 2, 3);
      AzazelHumanEntity human = (AzazelHumanEntity)helper.spawn((EntityType)ModEntities.AZAZEL_HUMAN.get(), 12, 2, 12);
      BelieverEntity believer = (BelieverEntity)helper.spawn((EntityType)ModEntities.BELIEVER.get(), 5, 2, 3);
      head.setNoAi(true);
      human.setNoAi(true);
      believer.setNoAi(true);
      helper.runAfterDelay(5L, () -> {
         close(helper, head.getMaxHealth(), 5000.0);
         close(helper, human.getMaxHealth(), 10000.0);
         close(helper, head.getAttributeValue(Attributes.ATTACK_DAMAGE), 120.0);
         close(helper, human.getAttributeValue(Attributes.ATTACK_DAMAGE), 180.0);
         close(helper, EnergyHelper.getBaseMaxEP(head), 1000000.0);
         close(helper, EnergyHelper.getBaseMaxEP(human), 2000000.0);
         check(helper, SkillAPI.getSkillsFrom(human).getSkill((ManasSkill)UniqueSkills.GREED.get()).isPresent());
         check(helper, TensuraStorages.getAbilityFrom(human).isAbilityInActivePreset((ManasSkill)UniqueSkills.PRIDE.get()));
         check(helper, SkillAPI.getSkillsFrom(head).getSkill((ManasSkill)ExtraSkills.ULTRASPEED_REGENERATION.get()).isEmpty());
         check(helper, SkillAPI.getSkillsFrom(human).getSkill((ManasSkill)ExtraSkills.ULTRASPEED_REGENERATION.get()).isEmpty());
         head.setHealth(2500.0F);
         CultCombat.applyAttributes(head);
         CultCombat.applyAttributes(head);
         close(helper, head.getMaxHealth(), 5000.0);
         close(helper, head.getHealth(), 2500.0);
         CompoundTag saved = new CompoundTag();
         head.save(saved);
         AzazelEntity restored = (AzazelEntity)((EntityType)ModEntities.AZAZEL.get()).create(helper.getLevel());
         check(helper, restored != null);
         restored.load(saved);
         restored.setUUID(UUID.randomUUID());
         CultCombat.applyAttributes(restored);
         close(helper, restored.getMaxHealth(), 5000.0);
         close(helper, restored.getHealth(), 2500.0);
         ItemStack spear = new ItemStack((ItemLike)ModItems.AZAZEL_SPEAR.get());
         spear.set((DataComponentType)TensuraDataComponents.MAX_EP.get(), 0.0);
         ItemEntity drop = new ItemEntity(helper.getLevel(), head.getX(), head.getY(), head.getZ(), spear);
         helper.getLevel().addFreshEntity(drop);
         check(helper, (Double)spear.getOrDefault((DataComponentType)TensuraDataComponents.MAX_EP.get(), 0.0) > 0.0);
         check(helper, (Double)spear.getOrDefault((DataComponentType)TensuraDataComponents.EP_DURABILITY.get(), 0.0) > 0.0);
         double[] attack = new double[]{0.0};
         spear.forEachModifier(EquipmentSlot.MAINHAND, (attribute, modifier) -> {
            if (attribute.equals(Attributes.ATTACK_DAMAGE) && modifier.operation() == Operation.ADD_VALUE) {
               attack[0] += modifier.amount();
            }
         });
         check(helper, attack[0] >= 90.0);
         spear.set((DataComponentType)TensuraDataComponents.EP.get(), 1234567.0);
         CultEquipment.initialize(helper.getLevel(), spear);
         close(helper, (Double)spear.getOrDefault((DataComponentType)TensuraDataComponents.EP.get(), 0.0), 1234567.0);
         ItemStack helmet = new ItemStack((ItemLike)ModItems.AZAZEL_HELMET.get());
         helmet.setDamageValue(helmet.getMaxDamage() / 2);
         double wear = (double)helmet.getDamageValue() / helmet.getMaxDamage();
         ItemStack benchmark = new ItemStack((ItemLike)BuiltInRegistries.ITEM.get(ResourceLocation.parse("tensura:hihiirokane_helmet")));
         CultEquipment.initialize(helper.getLevel(), helmet);
         check(helper, helmet.getMaxDamage() >= benchmark.getMaxDamage());
         check(helper, Math.abs((double)helmet.getDamageValue() / helmet.getMaxDamage() - wear) < 0.005);
      });
      helper.runAfterDelay(85L, () -> {
         MobEffectInstance effect = head.getEffect(TensuraMobEffects.getReference(TensuraMobEffects.INSTANT_REGENERATION));
         check(helper, effect != null && effect.getAmplifier() == 1);
         believer.discard();
      });
      helper.runAfterDelay(200L, () -> {
         MobEffectInstance effect = head.getEffect(TensuraMobEffects.getReference(TensuraMobEffects.INSTANT_REGENERATION));
         check(helper, effect == null || effect.getAmplifier() == 0);
         head.getEntityData().set(AzazelEntity.ATTACK_STATE, 9);
         head.setHealth(1.0F);
         head.setHealth(0.0F);
         check(helper, head.isAlive() && head.getHealth() > 0.0F);
         human.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 100);
         human.setHealth(1.0F);
         human.setHealth(0.0F);
         check(helper, human.isAlive() && human.getHealth() > 0.0F);
         helper.succeed();
      });
   }

   private static void check(GameTestHelper helper, boolean condition) {
      helper.assertTrue(condition, "");
   }

   private static void close(GameTestHelper helper, double actual, double expected) {
      helper.assertTrue(Math.abs(actual - expected) < 0.01, "");
   }

   private CultGameTests() {
   }
}
