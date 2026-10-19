package it.hurts.octostudios.reliquified_twilight_forest.item.relic;

import it.hurts.octostudios.reliquified_twilight_forest.data.loot.LootEntries;
import it.hurts.octostudios.reliquified_twilight_forest.init.DamageTypeRegistry;
import it.hurts.octostudios.reliquified_twilight_forest.init.ItemRegistry;
import it.hurts.octostudios.reliquified_twilight_forest.network.ExecutionEffectPacket;
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
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootEntry;
import it.hurts.sskirillss.relics.items.relics.base.data.style.BeamsData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.StyleData;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.MathUtils;
import java.awt.Color;
import java.util.Map;
import java.util.Objects;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Pre;
import net.neoforged.neoforge.network.PacketDistributor;
import twilightforest.entity.passive.TinyBird;
import twilightforest.entity.passive.TinyBirdVariant;
import twilightforest.init.TFEntities;
import twilightforest.init.custom.TinyBirdVariants;

@EventBusSubscriber
public class TwilightFeatherItem extends RelicItem {
   public static final Map<ResourceKey<TinyBirdVariant>, Color> VARIANTS = Map.of(
      TinyBirdVariants.RED,
      new Color(231, 70, 70, 255),
      TinyBirdVariants.BLUE,
      new Color(59, 52, 232, 255),
      TinyBirdVariants.GOLD,
      new Color(255, 202, 73, 255),
      TinyBirdVariants.BROWN,
      new Color(79, 50, 34, 255)
   );
   private static final Object[] keys = VARIANTS.keySet().toArray();

   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("execution")
                     .stat(
                        StatData.builder("chance")
                           .initialValue(0.005, 0.01)
                           .formatValue(value -> MathUtils.round(value * 100.0, 1))
                           .upgradeModifier(UpgradeOperation.ADD, 0.004)
                           .build()
                     )
                     .maxLevel(10)
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .maxLevel(10)
               .sources(
                  LevelingSourcesData.builder().source(LevelingSourceData.abilityBuilder("execution").gem(GemShape.SQUARE, GemColor.PURPLE).build()).build()
               )
               .build()
         )
         .style(StyleData.builder().beams(BeamsData.builder().startColor(-12255375).endColor(990227).build()).build())
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.TREE_CACHE}).build())
         .build();
   }

   @SubscribeEvent
   public static void onDamage(Pre e) {
      LivingEntity victim = e.getEntity();
      if (!victim.level().isClientSide
         && !Objects.equals(e.getSource().typeHolder().getKey(), DamageTypeRegistry.EXECUTION)
         && e.getSource().getEntity() instanceof LivingEntity source
         && source != victim
         && !(victim.getHealth() > source.getMaxHealth())) {
         for (ItemStack stack : EntityUtils.findEquippedCurios(source, (Item)ItemRegistry.TWILIGHT_FEATHER.get())) {
            if (stack.getItem() instanceof TwilightFeatherItem relic
               && source.isAlive()
               && !(source.getRandom().nextDouble() > relic.getStatValue(stack, "execution", "chance"))
               && hasPerformedExecution(source, victim)) {
               e.setNewDamage(0.0F);
               relic.spreadRelicExperience(source, stack, 1);
               break;
            }
         }
      }
   }

   public static boolean hasPerformedExecution(LivingEntity source, LivingEntity victim) {
      if (!victim.hurt(new DamageSource(victim.level().damageSources().damageTypes.getHolderOrThrow(DamageTypeRegistry.EXECUTION), source), 99999.0F)) {
         return false;
      }

      victim.deathTime = 19;
      if (victim instanceof TinyBird) {
         return false;
      }

      TinyBird birb = new TinyBird((EntityType)TFEntities.TINY_BIRD.get(), victim.level());
      ResourceKey<TinyBirdVariant> variant = (ResourceKey<TinyBirdVariant>)keys[birb.getRandom().nextInt(keys.length)];
      Color color = VARIANTS.get(variant);
      birb.setVariant(victim.level().registryAccess().holderOrThrow(variant));
      birb.setPos(victim.getEyePosition());
      birb.setDeltaMovement(victim.getDeltaMovement());
      victim.level().addFreshEntity(birb);
      victim.level().playSound(null, birb, SoundEvents.BEACON_DEACTIVATE, SoundSource.NEUTRAL, 1.0F, 0.8F);
      PacketDistributor.sendToPlayersTrackingEntityAndSelf(victim, new ExecutionEffectPacket(victim.getId(), color), new CustomPacketPayload[0]);
      return true;
   }

   public String getConfigRoute() {
      return "reliquified_twilight_forest";
   }
}
