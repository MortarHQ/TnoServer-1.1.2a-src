package it.hurts.sskirillss.relics.items.relics.necklace;

import it.hurts.sskirillss.relics.entities.DeathEssenceEntity;
import it.hurts.sskirillss.relics.entities.LifeEssenceEntity;
import it.hurts.sskirillss.relics.init.DataComponentRegistry;
import it.hurts.sskirillss.relics.init.EffectRegistry;
import it.hurts.sskirillss.relics.init.EntityRegistry;
import it.hurts.sskirillss.relics.init.ItemRegistry;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.RelicItem;
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
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.GemColor;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.GemShape;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.misc.LootEntries;
import it.hurts.sskirillss.relics.items.relics.base.data.research.ResearchData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.BeamsData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.StyleData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.TooltipData;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.MathUtils;
import java.util.Locale;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class HolyLocketItem extends RelicItem {
   private static final int MAX_TARGETS = 10;

   @Override
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("faith")
                     .active(CastData.builder().type(CastType.INSTANTANEOUS).build())
                     .icon((player, stack, ability) -> ability + "_" + this.getMode(stack).name().toLowerCase(Locale.ROOT))
                     .stat(
                        StatData.builder("health")
                           .initialValue(0.1, 0.25)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.1)
                           .formatValue(value -> (int)MathUtils.round(value * 100.0, 0))
                           .build()
                     )
                     .stat(
                        StatData.builder("damage")
                           .initialValue(0.25, 0.75)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.5)
                           .formatValue(value -> (int)MathUtils.round(value * 100.0, 0))
                           .build()
                     )
                     .stat(
                        StatData.builder("radius")
                           .initialValue(5.0, 10.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.25)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 13, 5)
                           .star(1, 6, 8)
                           .star(2, 10, 12)
                           .star(3, 4, 13)
                           .star(4, 18, 13)
                           .star(5, 8, 16)
                           .star(6, 14, 16)
                           .star(7, 5, 20)
                           .star(8, 17, 20)
                           .star(9, 11, 24)
                           .star(10, 8, 28)
                           .star(11, 14, 28)
                           .link(0, 2)
                           .link(0, 4)
                           .link(1, 2)
                           .link(1, 3)
                           .link(3, 5)
                           .link(4, 6)
                           .link(5, 6)
                           .link(3, 7)
                           .link(4, 8)
                           .link(7, 9)
                           .link(8, 9)
                           .link(9, 10)
                           .link(9, 11)
                           .link(10, 11)
                           .build()
                     )
                     .build()
               )
               .ability(
                  AbilityData.builder("penitence")
                     .requiredLevel(5)
                     .stat(
                        StatData.builder("amount")
                           .initialValue(0.25, 0.5)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.3)
                           .formatValue(value -> (int)MathUtils.round(value * 100.0, 0))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 7, 12)
                           .star(1, 15, 12)
                           .star(2, 6, 19)
                           .star(3, 16, 19)
                           .star(4, 9, 26)
                           .star(5, 13, 26)
                           .link(0, 1)
                           .link(0, 2)
                           .link(1, 3)
                           .link(2, 4)
                           .link(3, 5)
                           .link(4, 5)
                           .build()
                     )
                     .build()
               )
               .ability(
                  AbilityData.builder("ascension")
                     .requiredLevel(10)
                     .requiredPoints(3)
                     .maxLevel(5)
                     .stat(
                        StatData.builder("max_duration")
                           .initialValue(7.5, 15.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_TOTAL, 0.3195)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .stat(
                        StatData.builder("duration")
                           .initialValue(0.5, 1.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.2)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 11, 27)
                           .star(1, 3, 19)
                           .star(2, 3, 4)
                           .star(3, 11, 17)
                           .star(4, 6, 13)
                           .star(5, 11, 13)
                           .star(6, 16, 13)
                           .star(7, 19, 19)
                           .star(8, 19, 4)
                           .link(0, 1)
                           .link(0, 3)
                           .link(0, 7)
                           .link(1, 2)
                           .link(2, 8)
                           .link(7, 8)
                           .link(3, 4)
                           .link(3, 5)
                           .link(3, 6)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(20)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("faith").initialValue(1).gem(GemShape.SQUARE, GemColor.ORANGE).build())
                     .source(LevelingSourceData.abilityBuilder("penitence").initialValue(1).gem(GemShape.SQUARE, GemColor.ORANGE).build())
                     .source(LevelingSourceData.abilityBuilder("ascension").initialValue(1).gem(GemShape.SQUARE, GemColor.ORANGE).build())
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(
                  (player, stack) -> this.getMode(stack) == HolyLocketItem.Mode.HOLINESS
                     ? TooltipData.builder().borderTop(-3454452).borderBottom(-6540535).textured(true).icon("holy_locket_holiness").build()
                     : TooltipData.builder().borderTop(-12039087).borderBottom(-12039087).textured(true).icon("holy_locket_wickedness").build()
               )
               .beams(
                  (player, stack) -> this.getMode(stack) == HolyLocketItem.Mode.HOLINESS
                     ? BeamsData.builder().startColor(-256).endColor(16711680).build()
                     : BeamsData.builder().startColor(-16711681).endColor(255).build()
               )
               .build()
         )
         .loot(LootData.builder().entry(LootEntries.DESERT).build())
         .build();
   }

   public HolyLocketItem.Mode getMode(ItemStack stack) {
      return HolyLocketItem.Mode.byIndex((Integer)stack.getOrDefault(DataComponentRegistry.MODE, HolyLocketItem.Mode.HOLINESS.getIndex()));
   }

   public void setMode(ItemStack stack, HolyLocketItem.Mode mode) {
      stack.set(DataComponentRegistry.MODE, mode.getIndex());
   }

   public void cycleMode(ItemStack stack, int steps) {
      this.setMode(stack, this.getMode(stack).cycle(steps));
   }

   @Override
   public void castActiveAbility(ItemStack stack, Player player, String ability, CastType type, CastStage stage) {
      if (ability.equals("faith") && stage == CastStage.END) {
         this.cycleMode(stack, 1);
      }
   }

   @EventBusSubscriber
   public static class HolyLocketEvents {
      @SubscribeEvent
      public static void onLivingDeath(LivingDeathEvent event) {
         if (event.getSource().getEntity() instanceof Player player) {
            for (ItemStack stack : EntityUtils.findEquippedCurios(player, (Item)ItemRegistry.HOLY_LOCKET.get())) {
               if (stack.getItem() instanceof IRelicItem relic && relic.canPlayerUseAbility(player, stack, "ascension")) {
                  MobEffectInstance effect = player.getEffect(EffectRegistry.IMMORTALITY);
                  int duration = effect == null ? 0 : effect.getDuration();
                  int maxDuration = (int)(relic.getStatValue(stack, "ascension", "max_duration") * 20.0);
                  if (duration < maxDuration) {
                     player.addEffect(
                        new MobEffectInstance(
                           EffectRegistry.IMMORTALITY, (int)Math.min(relic.getStatValue(stack, "ascension", "duration") * 20.0 + duration, maxDuration)
                        )
                     );
                     relic.spreadRelicExperience(player, stack, 1);
                  }
               }
            }
         }
      }

      @SubscribeEvent
      public static void onLivingHeal(LivingHealEvent event) {
         LivingEntity entity = event.getEntity();
         Level level = entity.level();
         AABB box = entity.getBoundingBox();
         BlockPos min = BlockPos.containing(Math.floor(box.minX) - 1.0, Math.floor(box.minY), Math.floor(box.minZ) - 1.0);
         BlockPos max = BlockPos.containing(Math.floor(box.maxX) + 1.0, Math.floor(entity.getEyeY()) + 1.0, Math.floor(box.maxZ) + 1.0);
         if (level.hasChunksAt(min, max)) {
            float amount = event.getAmount();
            if (!(amount <= 0.5F)) {
               RelicItem item = (RelicItem)ItemRegistry.HOLY_LOCKET.get();
               double maxDistance = item.getRelativeStatValue(
                  "faith", "radius", (Double)item.getStatData("faith", "radius").getInitialValue().getValue(), item.getLevelingData().getMaxLevel()
               );
               RandomSource random = level.getRandom();
               int targets = 0;

               for (Player player : EntityUtils.gatherPotentialTargets(entity, Player.class, maxDistance).toList()) {
                  if (!player.getStringUUID().equals(entity.getStringUUID())) {
                     targets++;

                     for (ItemStack stack : EntityUtils.findEquippedCurios(player, (Item)ItemRegistry.HOLY_LOCKET.get())) {
                        if (stack.getItem() instanceof HolyLocketItem relic
                           && relic.getMode(stack) == HolyLocketItem.Mode.HOLINESS
                           && relic.canPlayerUseAbility(player, stack, "faith")
                           && !(entity.position().distanceTo(player.position()) > relic.getStatValue(stack, "faith", "radius"))) {
                           float heal = (float)(amount * relic.getStatValue(stack, "faith", "health"));
                           LifeEssenceEntity essence = new LifeEssenceEntity((EntityType<? extends LifeEssenceEntity>)EntityRegistry.LIFE_ESSENCE.get(), level);
                           essence.setHeal(heal);
                           essence.setOwner(player);
                           essence.setTarget(player);
                           essence.setPos(entity.getEyePosition());
                           essence.setDeltaMovement(MathUtils.randomFloat(random), random.nextFloat(), MathUtils.randomFloat(random));
                           level.addFreshEntity(essence);
                           event.setAmount(amount - heal);
                           relic.spreadRelicExperience(player, stack, 1);
                        }
                     }

                     if (targets >= 10) {
                        break;
                     }
                  }
               }

               if (entity instanceof Player player && player.getHealth() < player.getMaxHealth()) {
                  for (ItemStack stack : EntityUtils.findEquippedCurios(player, (Item)ItemRegistry.HOLY_LOCKET.get())) {
                     if (stack.getItem() instanceof HolyLocketItem relic
                        && relic.getMode(stack) == HolyLocketItem.Mode.WICKEDNESS
                        && relic.canPlayerUseAbility(player, stack, "faith")) {
                        targets = 0;

                        for (LivingEntity target : EntityUtils.gatherPotentialTargets(player, LivingEntity.class, relic.getStatValue(stack, "faith", "radius"))
                           .toList()) {
                           if (!player.getStringUUID().equals(target.getStringUUID())) {
                              targets++;
                              DeathEssenceEntity essence = new DeathEssenceEntity(
                                 (EntityType<? extends DeathEssenceEntity>)EntityRegistry.DEATH_ESSENCE.get(), level
                              );
                              essence.setOwner(player);
                              essence.setTarget(target);
                              essence.setPos(player.getEyePosition());
                              essence.setDamage((float)(event.getAmount() * relic.getStatValue(stack, "faith", "damage")));
                              essence.setDeltaMovement(MathUtils.randomFloat(random), random.nextFloat(), MathUtils.randomFloat(random));
                              level.addFreshEntity(essence);
                              relic.spreadRelicExperience(player, stack, 1);
                              if (targets >= 10) {
                                 break;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      @SubscribeEvent
      public static void onLivingDamage(LivingIncomingDamageEvent event) {
         LivingEntity entity = event.getEntity();
         if (entity.isInvertedHealAndHarm() && event.getSource().getEntity() instanceof Player player) {
            for (ItemStack stack : EntityUtils.findEquippedCurios(player, (Item)ItemRegistry.HOLY_LOCKET.get())) {
               if (stack.getItem() instanceof HolyLocketItem relic && relic.canPlayerUseAbility(player, stack, "penitence")) {
                  float amount = event.getAmount();
                  if (amount >= 1.0F && !entity.isOnFire()) {
                     relic.spreadRelicExperience(player, stack, 1);
                  }

                  event.setAmount((float)(amount + amount * relic.getStatValue(stack, "penitence", "amount")));
                  entity.igniteForSeconds(10.0F);
               }
            }
         }
      }
   }

   public enum Mode {
      HOLINESS(1),
      WICKEDNESS(2);

      private final int index;

      public static HolyLocketItem.Mode byIndex(int index) {
         for (HolyLocketItem.Mode mode : values()) {
            if (mode.getIndex() == index) {
               return mode;
            }
         }

         throw new IllegalArgumentException();
      }

      public HolyLocketItem.Mode cycle(int steps) {
         HolyLocketItem.Mode[] modes = values();
         int index = (this.ordinal() + steps) % modes.length;
         if (index < 0) {
            index += modes.length;
         }

         return modes[index];
      }

      public int getIndex() {
         return this.index;
      }

      Mode(final int index) {
         this.index = index;
      }
   }
}
