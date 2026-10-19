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
import java.util.Random;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import top.theillusivec4.curios.api.SlotContext;

@EventBusSubscriber
public class ShieldWeaveCape extends MoreRelicBase {
   public ShieldWeaveCape(Properties properties) {
   }

   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("impact_shield")
                     .stat(
                        StatData.builder("flat_chance")
                           .initialValue(3.0, 6.0)
                           .upgradeModifier(UpgradeOperation.ADD, 0.8)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .stat(
                        StatData.builder("per_armor")
                           .initialValue(0.15, 0.3)
                           .upgradeModifier(UpgradeOperation.ADD, 0.04)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(5)
                     .requiredLevel(0)
                     .build()
               )
               .ability(
                  AbilityData.builder("dynamic_thread")
                     .stat(
                        StatData.builder("perch")
                           .initialValue(4.0, 8.0)
                           .upgradeModifier(UpgradeOperation.ADD, 1.5)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(5)
                     .requiredLevel(5)
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .maxLevel(10)
               .initialCost(100)
               .step(50)
               .sources(LevelingSourcesData.builder().source(LevelingSourceData.abilityBuilder("impact_shield").build()).build())
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.CAVE, LootEntries.TAIGA, LootEntries.MOUNTAIN, LootEntries.OVERWORLD}).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      LivingEntity livingEntity = slotContext.entity();
      if (!livingEntity.level().isClientSide) {
         if (this.isAbilityUnlocked(stack, "dynamic_thread")) {
            EntityUtils.applyAttribute(
               livingEntity, stack, Attributes.ARMOR, (float)this.getStatValue(stack, "dynamic_thread", "perch") / 100.0F, Operation.ADD_MULTIPLIED_TOTAL
            );
         } else {
            EntityUtils.removeAttribute(livingEntity, stack, Attributes.ARMOR, Operation.ADD_MULTIPLIED_TOTAL);
         }
      }
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      LivingEntity livingEntity = slotContext.entity();
      if (stack.getItem() != newStack.getItem()) {
         EntityUtils.removeAttribute(livingEntity, stack, Attributes.ARMOR, Operation.ADD_MULTIPLIED_TOTAL);
      }
   }

   @SubscribeEvent
   public static void onDamageTaken(LivingIncomingDamageEvent event) {
      if (event.getEntity() instanceof Player player && !player.level().isClientSide) {
         if (!(player.invulnerableTime > 10.0F) || event.getSource().is(DamageTypeTags.BYPASSES_COOLDOWN) || !(event.getAmount() <= player.lastHurt)) {
            int block = EntityUtils.findEquippedCurios(player, (Item)MoreRelics.SHIELDWEAVE_CAPE.get()).stream().mapToInt(stack -> {
               ShieldWeaveCape item = (ShieldWeaveCape)stack.getItem();
               if (!item.isAbilityUnlocked(stack, "impact_shield")) {
                  return 0;
               }

               double flat = item.getStatValue(stack, "impact_shield", "flat_chance");
               double per = item.getStatValue(stack, "impact_shield", "per_armor");
               Random rand = new Random();
               double blockChance = flat + per * player.getArmorValue();
               if (blockChance > 33.0) {
                  blockChance = 33.0;
               }

               int willBlock = blockChance > rand.nextFloat(100.0F) ? 1 : 0;
               if (willBlock == 1) {
                  item.spreadRelicExperience(player, stack, 2);
               }

               return willBlock;
            }).sum();
            if (block >= 1) {
               player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.IRON_GOLEM_REPAIR, SoundSource.MASTER, 0.8F, 0.8F);
               player.invulnerableTime = 20;
               player.hurtDuration = 10;
               player.hurtTime = player.hurtDuration;
               event.setCanceled(true);
            }
         }
      }
   }
}
