package com.blorb.morerelics.relics;

import com.blorb.morerelics.MoreRelics;
import com.blorb.morerelics.dataComponents.BoolHolder;
import com.blorb.morerelics.dataComponents.DataComponentHandler;
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
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import top.theillusivec4.curios.api.SlotContext;

@EventBusSubscriber
public class OpalNecklace extends MoreRelicBase {
   public OpalNecklace(Properties properties) {
   }

   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("bubble_shield")
                     .stat(
                        StatData.builder("cooldown")
                           .initialValue(300.0, 200.0)
                           .upgradeModifier(UpgradeOperation.ADD, -20.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(5)
                     .requiredLevel(0)
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .maxLevel(5)
               .initialCost(120)
               .step(20)
               .sources(LevelingSourcesData.builder().source(LevelingSourceData.abilityBuilder("bubble_shield").build()).build())
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.DESERT, LootEntries.FROST}).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      BoolHolder holder = DataComponentHandler.getBoolHolder(stack);
      boolean onCooldown = this.isAbilityOnCooldown(stack, "bubble_shield");
      boolean notified = holder.getBool();
      if (onCooldown != notified) {
         if (!onCooldown) {
            Component message = Component.literal("Bubble Shield is ready to use again.").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(16766720)));
            slotContext.entity().sendSystemMessage(message);
         }

         holder.setBool(onCooldown);
         stack.set(DataComponentHandler.BOOL_HOLDER.get(), holder);
      }
   }

   @SubscribeEvent
   public static void onDamageTaken(LivingIncomingDamageEvent event) {
      LivingEntity player = event.getEntity();
      if (!player.level().isClientSide) {
         if (!(player.invulnerableTime > 10.0F) || event.getSource().is(DamageTypeTags.BYPASSES_COOLDOWN) || !(event.getAmount() <= player.lastHurt)) {
            int block = EntityUtils.findEquippedCurios(player, (Item)MoreRelics.OPAL_NECKLACE.get()).stream().mapToInt(stack -> {
               OpalNecklace item = (OpalNecklace)stack.getItem();
               if (item.isAbilityUnlocked(stack, "bubble_shield") && !item.isAbilityOnCooldown(stack, "bubble_shield")) {
                  item.setAbilityCooldown(stack, "bubble_shield", (int)(item.getStatValue(stack, "bubble_shield", "cooldown") * 20.0));
                  item.spreadRelicExperience(player, stack, 5);
                  return 1;
               } else {
                  return 0;
               }
            }).sum();
            if (block >= 1) {
               player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.AMETHYST_CLUSTER_BREAK, SoundSource.MASTER, 0.8F, 0.6F);
               player.invulnerableTime = 20;
               player.hurtDuration = 10;
               player.hurtTime = player.hurtDuration;
               event.setCanceled(true);
            }
         }
      }
   }
}
