package it.hurts.octostudios.reliquified_twilight_forest.item.relic;

import it.hurts.octostudios.reliquified_twilight_forest.entity.projectile.HydraFireEntity;
import it.hurts.octostudios.reliquified_twilight_forest.init.EntityRegistry;
import it.hurts.octostudios.reliquified_twilight_forest.init.ItemRegistry;
import it.hurts.octostudios.reliquified_twilight_forest.util.MathButCool;
import it.hurts.sskirillss.relics.items.relics.base.RelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicSlotModifier;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourceData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourcesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.GemColor;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.GemShape;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.items.relics.base.data.style.BeamsData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.StyleData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.TooltipData;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Post;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

@EventBusSubscriber
public class HydraHeartItem extends RelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("hydra_fire")
                     .stat(
                        StatData.builder("chance")
                           .initialValue(0.15, 0.2)
                           .formatValue(MathButCool::percentage)
                           .upgradeModifier(UpgradeOperation.ADD, 0.12)
                           .build()
                     )
                     .stat(
                        StatData.builder("damage")
                           .initialValue(1.0, 2.0)
                           .formatValue(MathButCool::roundSingleDigit)
                           .upgradeModifier(UpgradeOperation.ADD, 0.8)
                           .build()
                     )
                     .stat(
                        StatData.builder("lifetime")
                           .initialValue(80.0, 100.0)
                           .formatValue(MathButCool::ticksToSecondsAndRoundSingleDigit)
                           .upgradeModifier(UpgradeOperation.ADD, 20.0)
                           .build()
                     )
                     .maxLevel(5)
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .sources(
                  LevelingSourcesData.builder().source(LevelingSourceData.abilityBuilder("hydra_fire").gem(GemShape.SQUARE, GemColor.ORANGE).build()).build()
               )
               .initialCost(100)
               .step(125)
               .maxLevel(5)
               .build()
         )
         .style(
            StyleData.builder()
               .beams(BeamsData.builder().startColor(-13044742).endColor(1582438).build())
               .tooltip(TooltipData.builder().textured(true).borderTop(-13948620).borderBottom(-14540507).build())
               .build()
         )
         .build();
   }

   @Nullable
   public RelicSlotModifier getSlotModifiers(ItemStack stack) {
      return stack.getItem() instanceof HydraHeartItem relic ? super.getSlotModifiers(stack) : super.getSlotModifiers(stack);
   }

   public boolean canEquip(SlotContext slotContext, ItemStack stack) {
      return super.canEquip(slotContext, stack);
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      LivingEntity livingEntity = slotContext.entity();
      if (!livingEntity.level().isClientSide) {
         ;
      }
   }

   @SubscribeEvent
   public static void onLivingHurt(Post e) {
      LivingEntity entity = e.getEntity();
      if (!entity.level().isClientSide && entity.isAlive()) {
         for (ItemStack stack : EntityUtils.findEquippedCurios(entity, (Item)ItemRegistry.HYDRA_HEART.get())) {
            if (stack.getItem() instanceof HydraHeartItem relic && !(entity.getRandom().nextFloat() > relic.getStatValue(stack, "hydra_fire", "chance"))) {
               HydraFireEntity fire = new HydraFireEntity((EntityType<? extends ThrowableProjectile>)EntityRegistry.HYDRA_FIRE.get(), entity.level());
               fire.setAge((int)Math.round(relic.getStatValue(stack, "hydra_fire", "lifetime")));
               fire.setPos(entity.getEyePosition());
               fire.setRelicStack(stack);
               fire.setDeltaMovement((fire.getRandom().nextFloat() - 0.5F) * 0.1F, 0.4F, (fire.getRandom().nextFloat() - 0.5F) * 0.1F);
               fire.setOwner(e.getEntity());
               entity.level().addFreshEntity(fire);
            }
         }
      }
   }

   public String getConfigRoute() {
      return "reliquified_twilight_forest";
   }
}
