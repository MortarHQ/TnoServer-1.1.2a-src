package it.hurts.octostudios.reliquified_lenders_cataclysm.items.relics.hands;

import it.hurts.octostudios.reliquified_lenders_cataclysm.init.RECDataComponentRegistry;
import it.hurts.octostudios.reliquified_lenders_cataclysm.items.base.RECItem;
import it.hurts.octostudios.reliquified_lenders_cataclysm.items.base.data.RECLootEntries;
import it.hurts.octostudios.reliquified_lenders_cataclysm.network.packets.VacuumGloveParticlesPacket;
import it.hurts.octostudios.reliquified_lenders_cataclysm.utils.ItemUtils;
import it.hurts.octostudios.reliquified_lenders_cataclysm.utils.RECMathUtils;
import it.hurts.sskirillss.relics.init.DataComponentRegistry;
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
import it.hurts.sskirillss.relics.items.relics.base.data.loot.misc.LootEntries;
import it.hurts.sskirillss.relics.items.relics.base.data.style.BeamsData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.StyleData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.TooltipData;
import it.hurts.sskirillss.relics.network.NetworkHandler;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.SlotContext;

public class VacuumGloveItem extends RECItem {
   private static final String ABILITY_ID = "vacuum_slowdown";
   private final HashMap<UUID, Vec3> positionsPrev = new HashMap<>();

   @Override
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("vacuum_slowdown")
                     .stat(
                        StatData.builder("slowdown")
                           .initialValue(0.28, 0.34)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.15)
                           .formatValue(RECMathUtils::roundPercents)
                           .build()
                     )
                     .stat(
                        StatData.builder("radius")
                           .initialValue(6.0, 6.5)
                           .upgradeModifier(UpgradeOperation.ADD, 0.35)
                           .formatValue(RECMathUtils::roundInt)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .step(100)
               .maxLevel(10)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("vacuum_slowdown").gem(GemShape.SQUARE, GemColor.PURPLE).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{RECLootEntries.CURSED_PYRAMID, LootEntries.THE_END}).build())
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-138359).borderBottom(-2382751).textured(true).build())
               .beams(BeamsData.builder().startColor(-11202981).endColor(13388501).build())
               .build()
         )
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      LivingEntity entity = slotContext.entity();
      Level level = entity.getCommandSenderWorld();
      if (!level.isClientSide) {
         ArrayList<UUID> slowedEntities = this.getSlowedEntities(stack);

         for (int i = 0; i < slowedEntities.size(); i++) {
            if (((ServerLevel)level).getEntity(slowedEntities.get(i)) instanceof LivingEntity entitySlowed
               && (entity.distanceTo(entitySlowed) > this.getRadiusStat(stack) || entitySlowed.isDeadOrDying())) {
               removeSlowdown(slowedEntities, entitySlowed, stack);
            }
         }

         for (LivingEntity entityOther : getEntitiesInArea(level, entity, this.getRadiusStat(stack))) {
            UUID id = entityOther.getUUID();
            Vec3 posPrev = this.positionsPrev.getOrDefault(id, entityOther.position());
            this.positionsPrev.put(id, entityOther.position());
            Vec3 motion = entityOther.position().subtract(posPrev);
            Vec3 directionVector = entityOther.position().subtract(entity.position()).normalize();
            double distanceCurrent = directionVector.length();
            double distanceNext = entity.position().subtract(entityOther.position().add(motion)).length();
            double dotProduct = motion.normalize().dot(directionVector);
            float entityBaseSpeed = (float)entityOther.getAttributeBaseValue(Attributes.MOVEMENT_SPEED);
            if (dotProduct > 0.0 && distanceNext > distanceCurrent) {
               float modifier = this.getModifierValue(stack, entityBaseSpeed, entity.distanceTo(entityOther));
               ItemUtils.resetMovementAttribute(entityOther, stack, modifier);
               slowedEntities.add(entityOther.getUUID());
               NetworkHandler.sendToClientsTrackingEntityAndSelf(
                  new VacuumGloveParticlesPacket(this.getRadiusStat(stack), entityOther.getId(), entity.getX(), entity.getY(), entity.getZ()), entityOther
               );
            } else {
               removeSlowdown(slowedEntities, entityOther, stack);
            }
         }

         this.setSlowedEntities(stack, slowedEntities);
         if (entity.isDeadOrDying()) {
            this.resetSlowedEntities(level, stack);
         }

         int ticks = (Integer)stack.getOrDefault(DataComponentRegistry.TIME, 0);
         if (ticks % 200 == 0) {
            this.spreadRelicExperience(entity, stack, 1);
         }

         stack.set(DataComponentRegistry.TIME, ticks + 1);
      }
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      super.onUnequip(slotContext, newStack, stack);
      if (newStack.getItem() != stack.getItem()) {
         this.resetSlowedEntities(slotContext.entity().level(), stack);
      }
   }

   private static void removeSlowdown(List<UUID> slowedEntities, LivingEntity entity, ItemStack stack) {
      ItemUtils.removeMovementAttribute(entity, stack);
      slowedEntities.remove(entity.getUUID());
   }

   private static List<LivingEntity> getEntitiesInArea(Level level, LivingEntity entityCenter, float radius) {
      AABB sphereArea = new AABB(entityCenter.blockPosition()).inflate(radius);
      return ItemUtils.getEntitiesInArea(entityCenter, level, sphereArea);
   }

   public float getModifierValue(ItemStack stack, float speed, float distance) {
      float radius = this.getRadiusStat(stack);
      if (distance != 0.0F && speed != 0.0F && !(distance > radius)) {
         float minSpeed = this.getSlowdownStat(stack) * speed;
         float slowdownSpeed = minSpeed + (radius - distance) * (speed - minSpeed) / radius;
         return slowdownSpeed - speed;
      } else {
         return 0.0F;
      }
   }

   private float getSlowdownStat(ItemStack stack) {
      return (float)(1.0 - this.getStatValue(stack, "vacuum_slowdown", "slowdown"));
   }

   public float getRadiusStat(ItemStack stack) {
      return (float)this.getStatValue(stack, "vacuum_slowdown", "radius");
   }

   public ArrayList<UUID> getSlowedEntities(ItemStack stack) {
      return new ArrayList<>((Collection<? extends UUID>)stack.getOrDefault(RECDataComponentRegistry.SLOWED_ENTITIES, new ArrayList()));
   }

   public void setSlowedEntities(ItemStack stack, List<UUID> entities) {
      stack.set(RECDataComponentRegistry.SLOWED_ENTITIES, entities);
   }

   private void resetSlowedEntities(Level level, ItemStack stack) {
      if (!level.isClientSide) {
         for (UUID slowedEntity : this.getSlowedEntities(stack)) {
            if (((ServerLevel)level).getEntity(slowedEntity) instanceof LivingEntity target) {
               ItemUtils.removeMovementAttribute(target, stack);
            }
         }

         stack.set(RECDataComponentRegistry.SLOWED_ENTITIES, new ArrayList());
      }
   }
}
