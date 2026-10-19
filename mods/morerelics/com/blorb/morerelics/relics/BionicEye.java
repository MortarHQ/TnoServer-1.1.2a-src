package com.blorb.morerelics.relics;

import com.blorb.morerelics.MoreRelics;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.CastData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastStage;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastType;
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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.SlotContext;

public class BionicEye extends CyberwareBase {
   public BionicEye(Properties properties) {
      super(properties);
   }

   @Override
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("seeker")
                     .stat(
                        StatData.builder("range")
                           .initialValue(12.0, 15.0)
                           .upgradeModifier(UpgradeOperation.ADD, 3.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .stat(
                        StatData.builder("duration")
                           .initialValue(10.0, 15.0)
                           .upgradeModifier(UpgradeOperation.ADD, 5.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .stat(
                        StatData.builder("cooldown")
                           .initialValue(120.0, 100.0)
                           .upgradeModifier(UpgradeOperation.ADD, -10.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(5)
                     .active(CastData.builder().type(CastType.INSTANTANEOUS).build())
                     .requiredLevel(0)
                     .build()
               )
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
               .initialCost(100)
               .step(25)
               .sources(LevelingSourcesData.builder().source(LevelingSourceData.abilityBuilder("seeker").build()).build())
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.SCULK}).build())
         .build();
   }

   public void castActiveAbility(ItemStack stack, Player player, String ability, CastType type, CastStage stage) {
      if (ability.equals("seeker")) {
         if (player.level().isClientSide) {
            return;
         }

         Vec3 center = player.position();
         double radius = this.getStatValue(stack, "seeker", "range");
         boolean playerHasCyber = player.hasEffect(MoreRelics.CYBERPSYCHOSIS.getDelegate());
         AABB area = new AABB(center.x - radius, center.y - radius, center.z - radius, center.x + radius, center.y + radius, center.z + radius);

         for (Entity detectedEntity : player.level().getEntities(player, area)) {
            if (detectedEntity instanceof LivingEntity detectedLiving) {
               detectedLiving.addEffect(new MobEffectInstance(MobEffects.GLOWING, (int)Math.round(this.getStatValue(stack, "seeker", "duration") * 20.0)));
               detectedLiving.addEffect(
                  new MobEffectInstance(
                     MoreRelics.VULNERABILITY, (int)Math.round(this.getStatValue(stack, "seeker", "duration") * 20.0), playerHasCyber ? 1 : 0
                  )
               );
            }
         }

         if (!playerHasCyber) {
            this.setAbilityCooldown(stack, "seeker", (int)this.getStatValue(stack, "seeker", "cooldown") * 20);
         } else {
            this.setAbilityCooldown(stack, "seeker", 20);
         }

         this.spreadRelicExperience(player, stack, 10);
      }
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      super.curioTick(slotContext, stack);
      LivingEntity livingEntity = slotContext.entity();
      if (!livingEntity.level().isClientSide) {
         if (livingEntity.hasEffect(MoreRelics.CYBERPSYCHOSIS.getDelegate())) {
            BionicEye item = (BionicEye)stack.getItem();
            if (item.getAbilityCooldown(stack, "seeker") > 20) {
               item.setAbilityCooldown(stack, "seeker", 20);
            }
         }
      }
   }
}
