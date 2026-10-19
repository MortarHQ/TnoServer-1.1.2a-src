package com.blorb.morerelics.relics;

import com.blorb.morerelics.MoreRelics;
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
import java.util.OptionalDouble;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import top.theillusivec4.curios.api.SlotContext;

@EventBusSubscriber
public class GravitumGlove extends MoreRelicBase {
   public GravitumGlove(Properties properties) {
   }

   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("all_in")
                     .stat(
                        StatData.builder("damage_boost")
                           .initialValue(1.4, 1.7)
                           .upgradeModifier(UpgradeOperation.ADD, 0.06)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(5)
                     .requiredLevel(0)
                     .build()
               )
               .ability(
                  AbilityData.builder("bonk")
                     .stat(
                        StatData.builder("chance")
                           .initialValue(3.0, 5.0)
                           .upgradeModifier(UpgradeOperation.ADD, 1.25)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .stat(
                        StatData.builder("duration")
                           .initialValue(4.0, 6.0)
                           .upgradeModifier(UpgradeOperation.ADD, 2.5)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(4)
                     .requiredLevel(5)
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .maxLevel(9)
               .initialCost(90)
               .step(20)
               .sources(LevelingSourcesData.builder().source(LevelingSourceData.abilityBuilder("all_in").initialValue(1).build()).build())
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.END_LIKE, LootEntries.THE_END, LootEntries.SCULK}).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      LivingEntity livingEntity = slotContext.entity();
      if (!livingEntity.level().isClientSide) {
         EntityUtils.resetAttribute(livingEntity, stack, Attributes.ATTACK_SPEED, -0.667F, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.resetAttribute(
            livingEntity, stack, Attributes.ATTACK_DAMAGE, (float)this.getStatValue(stack, "all_in", "damage_boost") - 1.0F, Operation.ADD_MULTIPLIED_TOTAL
         );
      }
   }

   @Override
   public boolean canEquipMultipleCopies() {
      return false;
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      LivingEntity livingEntity = slotContext.entity();
      if (stack.getItem() != newStack.getItem()) {
         EntityUtils.removeAttribute(livingEntity, stack, Attributes.ATTACK_DAMAGE, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.removeAttribute(livingEntity, stack, Attributes.ATTACK_SPEED, Operation.ADD_MULTIPLIED_TOTAL);
      }
   }

   @SubscribeEvent
   public static void onPlayerAttack(LivingIncomingDamageEvent event) {
      if (event.getSource().getEntity() instanceof Player player && !player.level().isClientSide) {
         OptionalDouble effectSeconds = EntityUtils.findEquippedCurios(player, (Item)MoreRelics.GRAVITON_GLOVE.get()).stream().mapToDouble(stack -> {
            GravitumGlove item = (GravitumGlove)stack.getItem();
            item.spreadRelicExperience(player, stack, 2);
            if (!item.isAbilityUnlocked(stack, "bonk")) {
               return 0.0;
            }

            if (event.getOriginalAmount() < 20.0F) {
               return 0.0;
            }

            double perch = item.getStatValue(stack, "bonk", "chance");
            Random rand = new Random();
            return rand.nextFloat(0.0F, 100.0F) < perch ? item.getStatValue(stack, "bonk", "duration") : 0.0;
         }).max();
         if (!effectSeconds.isEmpty()) {
            double seconds = effectSeconds.getAsDouble();
            if (seconds > 0.0) {
               event.setAmount(event.getAmount() * 2.0F);
               LivingEntity victim = event.getEntity();
               ServerLevel sevLevel = (ServerLevel)victim.level();
               spawnSmashAttackParticles(sevLevel, victim.getOnPos(), 300);
               victim.level().playSound(null, victim.position().x, victim.position().y, victim.position().z, MoreRelics.BONK, SoundSource.MASTER, 1.0F, 1.0F);
               MobEffectInstance dazed = new MobEffectInstance(MoreRelics.DAZED, (int)(seconds * 20.0));
               event.getEntity().addEffect(dazed);
            }
         }
      }
   }

   public static void spawnSmashAttackParticles(ServerLevel level, BlockPos pos, int power) {
      Vec3 vec3 = pos.getCenter().add(0.0, 0.5, 0.0);
      BlockParticleOption blockparticleoption = new BlockParticleOption(ParticleTypes.DUST_PILLAR, level.getBlockState(pos));

      for (int i = 0; i < power / 3.0F; i++) {
         double d0 = vec3.x + level.getRandom().nextGaussian() / 2.0;
         double d1 = vec3.y;
         double d2 = vec3.z + level.getRandom().nextGaussian() / 2.0;
         double d3 = level.getRandom().nextGaussian() * 0.2F;
         double d4 = level.getRandom().nextGaussian() * 0.2F;
         double d5 = level.getRandom().nextGaussian() * 0.2F;
         level.sendParticles(blockparticleoption, d0, d1, d2, 1, d3, d4, d5, 0.0);
      }

      for (int j = 0; j < power / 1.5F; j++) {
         double d6 = vec3.x + 1.5 * Math.cos(j) + level.getRandom().nextGaussian() / 2.0;
         double d7 = vec3.y;
         double d8 = vec3.z + 1.5 * Math.sin(j) + level.getRandom().nextGaussian() / 2.0;
         double d9 = level.getRandom().nextGaussian() * 0.05F;
         double d10 = level.getRandom().nextGaussian() * 0.05F;
         double d11 = level.getRandom().nextGaussian() * 0.05F;
         level.sendParticles(blockparticleoption, d6, d7, d8, 1, d9, d10, d11, 0.0);
      }
   }
}
