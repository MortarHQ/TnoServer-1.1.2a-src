package it.hurts.sskirillss.relics.items.relics.charm;

import it.hurts.sskirillss.relics.entities.SporeEntity;
import it.hurts.sskirillss.relics.init.DataComponentRegistry;
import it.hurts.sskirillss.relics.init.EntityRegistry;
import it.hurts.sskirillss.relics.items.relics.base.RelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourceData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourcesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.GemColor;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.GemShape;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.misc.LootEntries;
import it.hurts.sskirillss.relics.items.relics.base.data.style.BeamsData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.StyleData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.TooltipData;
import it.hurts.sskirillss.relics.utils.MathUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;

public class SporeSackItem extends RelicItem {
   @Override
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("spore_mist")
                     .stat(
                        StatData.builder("amount")
                           .initialValue(3.0, 8.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.15)
                           .formatValue(value -> (int)MathUtils.round(value, 0))
                           .build()
                     )
                     .stat(
                        StatData.builder("damage")
                           .initialValue(0.1, 0.25)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.2)
                           .formatValue(value -> (int)MathUtils.round(value * 100.0, 0))
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(10)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("spore_mist").initialValue(1).gem(GemShape.SQUARE, GemColor.YELLOW).build())
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-10997977).borderBottom(-11518150).textured(true).build())
               .beams(BeamsData.builder().startColor(-16711936).endColor(16776960).build())
               .build()
         )
         .loot(LootData.builder().entry(LootEntries.TROPIC).build())
         .build();
   }

   public boolean isToggled(ItemStack stack) {
      return (Boolean)stack.getOrDefault(DataComponentRegistry.TOGGLED, false);
   }

   public void setToggled(ItemStack stack, boolean isToggled) {
      stack.set(DataComponentRegistry.TOGGLED, isToggled);
   }

   public int getCharges(ItemStack stack) {
      return (Integer)stack.getOrDefault(DataComponentRegistry.CHARGE, 0);
   }

   public void setCharges(ItemStack stack, int charges) {
      stack.set(DataComponentRegistry.CHARGE, charges);
   }

   public void addCharges(ItemStack stack, int charges) {
      this.setCharges(stack, Math.clamp(this.getCharges(stack) + charges, 0, (int)Math.round(this.getStatValue(stack, "spore_mist", "amount"))));
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player) {
         int var18 = this.getCharges(stack);
         boolean isToggled = this.isToggled(stack);
         int time = player.tickCount + slotContext.index() * 10;
         double percentageMedian = 0.5;
         float percentage = player.getHealth() / player.getMaxHealth();
         if (isToggled) {
            if (var18 > 0) {
               double speed = 3.0;
               if (time % speed != 0.0) {
                  return;
               }

               Level level = player.getCommandSenderWorld();
               double cycle = var18 * 1.0;
               double angle = Math.floor(time / speed) % cycle / cycle * 2.0 * Math.PI;
               SporeEntity entity = new SporeEntity((EntityType<? extends ThrowableProjectile>)EntityRegistry.SPORE.get(), level);
               entity.setOwner(player);
               entity.setRelicStack(stack);
               entity.setPos(player.position().add(0.0, player.getBbHeight() / 2.0F, 0.0));
               entity.setDeltaMovement(Math.cos(angle) * 0.5, 0.35F, Math.sin(angle) * 0.5);
               entity.setDamage((float)((player.getMaxHealth() - player.getHealth()) * this.getStatValue(stack, "spore_mist", "damage")));
               level.addFreshEntity(entity);
               level.playSound(null, player.blockPosition(), SoundEvents.PUFFER_FISH_FLOP, SoundSource.MASTER, 1.0F, 1.5F);
               this.addCharges(stack, -1);
            } else if (percentage > percentageMedian) {
               this.setToggled(stack, false);
            }
         } else if (percentage < percentageMedian) {
            this.setToggled(stack, true);
            this.setCharges(stack, (int)Math.round(this.getStatValue(stack, "spore_mist", "amount")));
         }
      }
   }
}
