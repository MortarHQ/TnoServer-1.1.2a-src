package com.blorb.morerelics.relics;

import com.blorb.morerelics.Config;
import com.blorb.morerelics.MoreRelics;
import com.blorb.morerelics.dataComponents.DataComponentHandler;
import com.blorb.morerelics.dataComponents.IntegerCounter;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourceData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourcesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootEntry;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.misc.LootEntries;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent.Applicable;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent.Applicable.Result;
import top.theillusivec4.curios.api.SlotContext;

@EventBusSubscriber
public class VertebraX extends CyberwareBase {
   public static boolean currentlyInPotionEvent = false;

   public VertebraX(Properties properties) {
      super(properties);
   }

   @Override
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(AbilityData.builder("inoculation").maxLevel(0).requiredLevel(0).build())
               .ability(
                  AbilityData.builder("fortification")
                     .stat(
                        StatData.builder("reduction")
                           .initialValue(12.0, 20.0)
                           .upgradeModifier(UpgradeOperation.ADD, 4.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .stat(
                        StatData.builder("increase")
                           .initialValue(15.0, 25.0)
                           .upgradeModifier(UpgradeOperation.ADD, 5.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(5)
                     .requiredLevel(3)
                     .build()
               )
               .ability(AbilityData.builder("acquired_immunity").maxLevel(0).requiredLevel(5).build())
               .ability(
                  AbilityData.builder("cyberpsychosis")
                     .stat(
                        StatData.builder("chance")
                           .initialValue(3.0, 5.0)
                           .upgradeModifier(UpgradeOperation.ADD, 1.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .stat(
                        StatData.builder("duration")
                           .initialValue(4.0, 7.0)
                           .upgradeModifier(UpgradeOperation.ADD, 2.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(3)
                     .requiredLevel(5)
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .maxLevel(8)
               .initialCost(120)
               .step(30)
               .sources(LevelingSourcesData.builder().source(LevelingSourceData.abilityBuilder("inoculation").build()).build())
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.THE_END}).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (!slotContext.entity().level().isClientSide) {
         IntegerCounter counter = DataComponentHandler.getIntegerCounter(stack);
         int poisonApplyCounter = counter.current();
         if (poisonApplyCounter > 0) {
            poisonApplyCounter--;
         } else {
            if (this.isAbilityUnlocked(stack, "inoculation")) {
               MobEffectInstance poisonEffect = new MobEffectInstance(MobEffects.POISON, 100);
               slotContext.entity().addEffect(poisonEffect);
               this.spreadRelicExperience(null, stack, 1);
            }

            poisonApplyCounter = 50;
         }

         stack.set(DataComponentHandler.INT_COUNTER, counter.getUpdatedCounter(poisonApplyCounter));
      }
   }

   @SubscribeEvent
   public static void EffectAppliedEvent(Applicable event) {
      if (event.getEntity() instanceof Player player && !player.level().isClientSide && !currentlyInPotionEvent) {
         currentlyInPotionEvent = true;
         float[] reductionAmount = new float[]{0.0F};
         float[] amplifierReductionChance = new float[]{0.0F};
         boolean[] canTransformPoison = new boolean[]{false};
         int hasCyber = player.hasEffect(MoreRelics.CYBERPSYCHOSIS.getDelegate()) ? 1 : 0;
         EntityUtils.findEquippedCurios(player, (Item)MoreRelics.VERTEBRAX.get()).forEach(stack -> {
            VertebraX item = (VertebraX)stack.getItem();
            if (item.isAbilityUnlocked(stack, "fortification")) {
               reductionAmount[0] += (float)item.getStatValue(stack, "fortification", "reduction") * (hasCyber * 0.5F + 1.0F);
               amplifierReductionChance[0] += (float)item.getStatValue(stack, "fortification", "increase");
            }

            if (item.isAbilityUnlocked(stack, "acquired_immunity")) {
               canTransformPoison[0] = true;
            }
         });
         MobEffectInstance instance = event.getEffectInstance();
         if (modifyEffect(player, instance, canTransformPoison, amplifierReductionChance, reductionAmount, hasCyber)) {
            event.setResult(Result.DO_NOT_APPLY);
         }

         currentlyInPotionEvent = false;
      }
   }

   public static boolean modifyEffect(
      LivingEntity player, MobEffectInstance instance, boolean[] canTransformPoison, float[] amplifierReductionChance, float[] reductionAmount, int hasCyber
   ) {
      if (((List)Config.VERTEBRAX_EFFECT_EXCLUDE.get()).contains(instance.getEffect().getRegisteredName())) {
         return false;
      }

      if (canTransformPoison[0] && instance.is(MobEffects.POISON)) {
         transformPoisonToRegeneration(player, instance);
      } else {
         if (!(reductionAmount[0] > 0.0F) || ((MobEffect)instance.getEffect().value()).getCategory() != MobEffectCategory.HARMFUL) {
            return false;
         }

         reduceEffectDuration(player, instance, reductionAmount[0], amplifierReductionChance[0], hasCyber);
      }

      return true;
   }

   private static void transformPoisonToRegeneration(LivingEntity player, MobEffectInstance poisonInstance) {
      int originalDuration = poisonInstance.getDuration();
      MobEffectInstance regenEffect = new MobEffectInstance(
         MobEffects.REGENERATION,
         originalDuration,
         poisonInstance.getAmplifier(),
         poisonInstance.isAmbient(),
         poisonInstance.isVisible(),
         poisonInstance.showIcon()
      );
      player.addEffect(regenEffect);
   }

   private static void reduceEffectDuration(
      LivingEntity player, MobEffectInstance instance, float reductionPercent, float amplifierReductionChance, int hasCyber
   ) {
      int originalDuration = instance.getDuration();
      Random rand = new Random();
      boolean doReduceAmplifier = rand.nextFloat() < amplifierReductionChance / 100.0F;
      int effectiveReduction = (int)(instance.getAmplifier() == 0 && doReduceAmplifier ? reductionPercent * 1.2F : reductionPercent);
      int newAmplifier = Math.max(instance.getAmplifier() - (doReduceAmplifier ? 1 : 0), 0);
      int reducedDuration = (int)(originalDuration * (1.0F - effectiveReduction / 100.0F));
      MobEffectInstance newInstance = new MobEffectInstance(
         instance.getEffect(), reducedDuration, newAmplifier, instance.isAmbient(), instance.isVisible(), instance.showIcon()
      );
      player.addEffect(newInstance);
      if (doReduceAmplifier) {
         String effectID = instance.getEffect().getRegisteredName();
         String oppositeID = Config.getOpposites().get(effectID);
         if (oppositeID != null) {
            Optional<Reference<MobEffect>> oppositeEffect = BuiltInRegistries.MOB_EFFECT.getHolder(ResourceLocation.parse(oppositeID));
            if (oppositeEffect.isPresent() && ((MobEffect)oppositeEffect.get().value()).isBeneficial()) {
               MobEffectInstance opposite = new MobEffectInstance(
                  (Holder)oppositeEffect.get(), Math.min(originalDuration / 2, 6000), hasCyber, instance.isAmbient(), instance.isVisible(), instance.showIcon()
               );
               player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BREWING_STAND_BREW, SoundSource.PLAYERS, 0.8F, 1.6F);
               player.addEffect(opposite);
            }
         }
      }
   }
}
