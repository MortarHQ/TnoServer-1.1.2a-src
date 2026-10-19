package com.github.wal_bos.moreskills.ability.skill.unique;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.enchantment.TensuraEnchantmentHelper;
import io.github.manasmods.tensura.enchantment.TensuraEnchantments;
import io.github.manasmods.tensura.item.TensuraToolTiers;
import io.github.manasmods.tensura.item.weapon.TwoHandedSwordItem;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class ExterminationBladeItem extends TwoHandedSwordItem {
   public static final String TAG_EP = "DurabilityEP";
   private static final String TAG_BLADE = "tms_extermination_blade";

   public ExterminationBladeItem() {
      super(TensuraToolTiers.PURE_MAGISTEEL, 4, -2.6F, 1.0, -1.0, 0.0, 0.0, 2, -2.8F, 1.0, -1.0, 0.0, 0.0, new Properties().stacksTo(1).fireResistant());
   }

   private static MoreSkillsConfigs.AdaptabilityConfig cfg() {
      return MoreSkillsConfigs.GRAND.adaptability;
   }

   public static void markAsBlade(ItemStack stack) {
      CompoundTag tag = getCustomData(stack);
      tag.putBoolean("tms_extermination_blade", true);
      setCustomData(stack, tag);
   }

   public static boolean isBlade(ItemStack stack) {
      CompoundTag tag = getCustomData(stack);
      return tag.getBoolean("tms_extermination_blade");
   }

   public static void ensureEp(ItemStack stack) {
      CompoundTag tag = getCustomData(stack);
      if (!tag.contains("DurabilityEP")) {
         tag.putDouble("DurabilityEP", (Double)cfg().bladeMaxEp.get());
         setCustomData(stack, tag);
      }
   }

   public static boolean hasEnoughEp(ItemStack stack, double cost) {
      ensureEp(stack);
      return getCustomData(stack).getDouble("DurabilityEP") >= cost;
   }

   public static void consumeEp(ItemStack stack, double cost) {
      CompoundTag tag = getCustomData(stack);
      double ep = tag.getDouble("DurabilityEP");
      tag.putDouble("DurabilityEP", Math.max(0.0, ep - cost));
      setCustomData(stack, tag);
   }

   public static void applyBladeName(ItemStack stack) {
      stack.set(DataComponents.CUSTOM_NAME, Component.literal("Extermination Blade").withStyle(ChatFormatting.LIGHT_PURPLE));
   }

   public static void applyBladeEnchantments(Level level, ItemStack stack, boolean mastered) {
      Holder<Enchantment> severance = TensuraEnchantmentHelper.getEnchantment(level, TensuraEnchantments.SEVERANCE);
      Holder<Enchantment> tsukumogami = TensuraEnchantmentHelper.getEnchantment(level, TensuraEnchantments.TSUKUMOGAMI);
      Holder<Enchantment> unbreaking = TensuraEnchantmentHelper.getEnchantment(level, Enchantments.UNBREAKING);
      Holder<Enchantment> sharpness = TensuraEnchantmentHelper.getEnchantment(level, Enchantments.SHARPNESS);
      if (severance != null) {
         stack.enchant(severance, mastered ? (Integer)cfg().bladeSeveranceLevelMastered.get() : (Integer)cfg().bladeSeveranceLevel.get());
      }

      if (tsukumogami != null) {
         stack.enchant(tsukumogami, mastered ? (Integer)cfg().bladeTsukumogamiLevelMastered.get() : (Integer)cfg().bladeTsukumogamiLevel.get());
      }

      if (unbreaking != null) {
         stack.enchant(unbreaking, (Integer)cfg().bladeUnbreakingLevel.get());
      }

      if (sharpness != null) {
         stack.enchant(sharpness, mastered ? (Integer)cfg().bladeSharpnessLevelMastered.get() : (Integer)cfg().bladeSharpnessLevel.get());
      }
   }

   public boolean onDroppedByPlayer(ItemStack stack, Player player) {
      return !isBlade(stack);
   }

   public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
      if (isBlade(stack)) {
         entity.setExtendedLifetime();
         entity.setInvulnerable(true);
      }

      return false;
   }

   public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
      ensureEp(stack);
      super.inventoryTick(stack, level, entity, slot, selected);
   }

   public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      ensureEp(stack);
      boolean adapted = false;
      if (attacker instanceof ServerPlayer player) {
         adapted = AdaptabilitySkill.getRoot(player).getBoolean("severImmune");
      }

      double cost = adapted ? (Double)cfg().bladeHitCostAdapted.get() : (Double)cfg().bladeHitCost.get();
      if (hasEnoughEp(stack, cost)) {
         float baseAttackDamage = (float)attacker.getAttributeValue(Attributes.ATTACK_DAMAGE);
         float epScale = (float)Math.min(
            (Double)cfg().bladeBonusEpCap.get(), getCustomData(stack).getDouble("DurabilityEP") / (Double)cfg().bladeBonusEpDivisor.get()
         );
         float bonus = ((Double)cfg().bladeBonusBase.get()).floatValue()
            + epScale
            + baseAttackDamage * ((Double)cfg().bladeBonusAttackDamagePercent.get()).floatValue();
         if (adapted) {
            bonus *= ((Double)cfg().bladeAdaptedBonusMultiplier.get()).floatValue();
         }

         float cap = ((Double)cfg().bladeBonusCap.get()).floatValue();
         if (bonus > cap) {
            bonus = cap;
         }

         int oldInvulnerableTime = target.invulnerableTime;
         target.invulnerableTime = 0;
         target.hurt(TensuraDamageTypes.getEntityDamageSource(attacker.level(), DamageTypes.MOB_ATTACK, attacker), bonus);
         target.invulnerableTime = Math.max(oldInvulnerableTime, target.invulnerableTime);
         consumeEp(stack, cost);
      }

      return super.hurtEnemy(stack, target, attacker);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      ensureEp(stack);
      tooltip.add(Component.literal("Adaptive severance blade").withStyle(ChatFormatting.LIGHT_PURPLE));
      tooltip.add(
         Component.literal("EP: " + (int)getCustomData(stack).getDouble("DurabilityEP") + " / " + (int)((Double)cfg().bladeMaxEp.get()).doubleValue())
            .withStyle(ChatFormatting.AQUA)
      );
      super.appendHoverText(stack, context, tooltip, flag);
   }

   private static CompoundTag getCustomData(ItemStack stack) {
      CustomData data = (CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
      return data.copyTag();
   }

   private static void setCustomData(ItemStack stack, CompoundTag tag) {
      if (tag.isEmpty()) {
         stack.remove(DataComponents.CUSTOM_DATA);
      } else {
         stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
      }
   }
}
