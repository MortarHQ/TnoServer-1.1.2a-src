package it.hurts.octostudios.reliquified_twilight_forest.item.relic;

import it.hurts.octostudios.reliquified_twilight_forest.api.ExtraDataDamageSource;
import it.hurts.octostudios.reliquified_twilight_forest.data.loot.LootEntries;
import it.hurts.octostudios.reliquified_twilight_forest.init.DamageTypeRegistry;
import it.hurts.octostudios.reliquified_twilight_forest.init.ItemRegistry;
import it.hurts.octostudios.reliquified_twilight_forest.network.ParasiteEvolveParticlePacket;
import it.hurts.octostudios.reliquified_twilight_forest.util.MathButCool;
import it.hurts.sskirillss.relics.init.DataComponentRegistry;
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
import it.hurts.sskirillss.relics.network.packets.PacketItemActivation;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.ParticleUtils;
import java.awt.Color;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Post;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent.Finish;
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import twilightforest.entity.boss.UrGhast;
import twilightforest.init.TFItems;

@EventBusSubscriber
public class Parasite115Item extends RelicItem {
   public static final String INFECTIONS = "reliquified_twilight_forest:infections";

   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("infectious_bloom")
                     .stat(
                        StatData.builder("chance")
                           .initialValue(0.075, 0.125)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.36)
                           .formatValue(MathButCool::percentageAndRoundSingleDigit)
                           .build()
                     )
                     .stat(StatData.builder("max_attacks").initialValue(1.0, 3.0).upgradeModifier(UpgradeOperation.ADD, 0.5).formatValue(Math::round).build())
                     .stat(
                        StatData.builder("damage")
                           .initialValue(0.5, 1.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.5)
                           .formatValue(MathButCool::roundSingleDigit)
                           .build()
                     )
                     .stat(StatData.builder("drops").initialValue(1.0, 3.0).upgradeModifier(UpgradeOperation.ADD, 1.0).formatValue(Math::round).build())
                     .maxLevel(5)
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("infectious_bloom").gem(GemShape.SQUARE, GemColor.RED).build())
                     .build()
               )
               .maxLevel(5)
               .build()
         )
         .style(StyleData.builder().beams((player, stack) -> {
            float ticks = player.tickCount + Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
            float lerp = (float)(Math.sin(ticks / 10.0F) / 2.0 + 0.5);
            float r = Mth.lerp(lerp, 237.0F, 255.0F) / 255.0F;
            float g = Mth.lerp(lerp, 28.0F, 127.0F) / 255.0F;
            float b = Mth.lerp(lerp, 36.0F, 39.0F) / 255.0F;
            return BeamsData.builder().startColor(new Color(r, g, b, 1.0F).getRGB()).endColor(new Color(r, g, b, 0.0F).getRGB()).build();
         }).build())
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.DARK_TOWER}).build())
         .build();
   }

   @SubscribeEvent
   public static void attackEntity(Post e) {
      if (!e.getEntity().level().isClientSide
         && !e.getSource().is(DamageTypeRegistry.INFECTIOUS_BLOOM)
         && e.getSource().getEntity() instanceof LivingEntity livingEntity
         && e.getEntity() != livingEntity
         && !(e.getNewDamage() < 1.0F)) {
         Optional<ICuriosItemHandler> inventory = CuriosApi.getCuriosInventory(livingEntity);
         if (!inventory.isEmpty()) {
            List<SlotResult> foundCurios = inventory.get().findCurios(itemStack -> itemStack.getItem() instanceof Parasite115Item);
            LivingEntity victim = e.getEntity();
            CompoundTag persistentData = victim.getPersistentData();
            CompoundTag infections = persistentData.getCompound("reliquified_twilight_forest:infections");
            foundCurios.forEach(slotResult -> {
               ItemStack stack = slotResult.stack();
               int id = slotResult.slotContext().index();
               String key = livingEntity.getStringUUID() + "+" + id + "+" + slotResult.slotContext().identifier();
               if (stack.getItem() instanceof Parasite115Item relic) {
                  CompoundTag var9 = null;
                  if (infections.contains(key)) {
                     var9 = infections.getCompound(key);
                     int maxAttacks = var9.getInt("max_attacks");
                     var9.putInt("ticks_left", 100 + maxAttacks);
                  } else if (livingEntity.getRandom().nextFloat() < relic.getStatValue(stack, "infectious_bloom", "chance")) {
                     var9 = relic.createBloomTag(stack);
                     infections.put(key, var9);
                     relic.spreadRelicExperience(livingEntity, stack, 1);
                  }

                  if (var9 != null) {
                     infections.put(key, var9);
                  }
               }
            });
            persistentData.put("reliquified_twilight_forest:infections", infections);
         }
      }
   }

   @SubscribeEvent
   public static void effectTick(net.neoforged.neoforge.event.tick.EntityTickEvent.Post e) {
      if (e.getEntity().level() instanceof ServerLevel serverLevel
         && e.getEntity() instanceof LivingEntity livingEntity
         && !livingEntity.getPersistentData().getCompound("reliquified_twilight_forest:infections").isEmpty()) {
         if (e.getEntity().tickCount % 2 == 0) {
            ParticleOptions particle = ParticleUtils.constructSimpleSpark(Color.RED, 0.25F, 10, 0.8F);
            serverLevel.sendParticles(particle, e.getEntity().getRandomX(0.5), e.getEntity().getRandomY(), e.getEntity().getRandomZ(0.5), 1, 0.0, 0.0, 0.0, 0.0);
         }

         CompoundTag infections = livingEntity.getPersistentData().getCompound("reliquified_twilight_forest:infections");
         CompoundTag newInfections = new CompoundTag();
         infections.getAllKeys()
            .forEach(
               key -> {
                  CompoundTag infection = infections.getCompound(key);
                  String[] data = key.split("\\+");
                  Entity owner = serverLevel.getEntity(UUID.fromString(data[0]));
                  double damage = infection.getDouble("damage");
                  byte maxDrops = infection.getByte("max_drops");
                  int ticksLeft = infection.getInt("ticks_left");
                  int maxAttacks = infection.getInt("max_attacks");
                  ticksLeft--;
                  if (ticksLeft <= maxAttacks && ticksLeft > 0) {
                     livingEntity.invulnerableTime = 0;
                     livingEntity.hurt(
                        new ExtraDataDamageSource(
                           livingEntity.level().registryAccess().holderOrThrow(DamageTypeRegistry.INFECTIOUS_BLOOM), owner, owner, maxDrops, data[1], data[2]
                        ),
                        (float)damage
                     );
                  }

                  if (ticksLeft > 0) {
                     infection.putInt("ticks_left", ticksLeft);
                     newInfections.put(key, infection);
                  }
               }
            );
         livingEntity.getPersistentData().put("reliquified_twilight_forest:infections", newInfections);
      }
   }

   public CompoundTag createBloomTag(ItemStack stack) {
      CompoundTag tag = new CompoundTag();
      int attacksLeft = (int)Math.round(this.getStatValue(stack, "infectious_bloom", "max_attacks"));
      tag.putDouble("damage", this.getStatValue(stack, "infectious_bloom", "damage"));
      tag.putInt("max_attacks", attacksLeft);
      tag.putInt("ticks_left", 100 + attacksLeft);
      tag.putByte("max_drops", (byte)Math.round(this.getStatValue(stack, "infectious_bloom", "drops")));
      return tag;
   }

   @SubscribeEvent
   public static void generateDrops(LivingDeathEvent e) {
      if (e.getEntity().level() instanceof ServerLevel serverLevel
         && e.getSource() instanceof ExtraDataDamageSource source
         && source.is(DamageTypeRegistry.INFECTIOUS_BLOOM)) {
         byte maxDrops = (Byte)source.getExtra()[0];
         int index = Integer.parseInt((String)source.getExtra()[1]);
         String identifier = (String)source.getExtra()[2];
         if (source.getEntity() instanceof LivingEntity sourceEntity) {
            ICuriosItemHandler inventory = (ICuriosItemHandler)CuriosApi.getCuriosInventory(sourceEntity).orElse(null);
            if (inventory != null) {
               SlotResult result = (SlotResult)inventory.findCurio(identifier, index).orElse(null);
               if (result != null) {
                  ItemStack stack = result.stack();
                  if (stack.getItem() instanceof Parasite115Item relic) {
                     Vec3 var18 = e.getEntity().position();

                     for (int i = 0; i < e.getEntity().getRandom().nextIntBetweenInclusive(1, maxDrops); i++) {
                        Vec3 dropVec = new Vec3(
                           (e.getEntity().getRandom().nextFloat() - 0.5F) / 3.0F, 0.5, (e.getEntity().getRandom().nextFloat() - 0.5F) / 3.0F
                        );
                        ItemEntity toDrop = new ItemEntity(
                           e.getEntity().level(), var18.x, var18.y, var18.z, TFItems.EXPERIMENT_115.toStack(), dropVec.x, dropVec.y, dropVec.z
                        );
                        toDrop.setNoPickUpDelay();
                        e.getEntity().level().addFreshEntity(toDrop);
                     }

                     if (e.getEntity() instanceof UrGhast && !relic.isEvolved()) {
                        relic.evolve(identifier, index, stack, sourceEntity);
                     }
                  }
               }
            }
         }
      }
   }

   public void evolve(String identifier, int index, ItemStack stack, LivingEntity entity) {
      ItemStack evolvedParasite = ((Parasite116Item)ItemRegistry.PARASITE_116.get()).getDefaultInstance();
      Parasite116Item relic = (Parasite116Item)evolvedParasite.getItem();
      relic.setAbilityComponent(evolvedParasite, "infectious_bloom", this.getAbilityComponent(stack, "infectious_bloom"));
      relic.setLevelingComponent(evolvedParasite, this.getLevelingComponent(stack));

      for (AbilityData ability : this.getAbilitiesData().getAbilities().values()) {
         String abilityID = ability.getId();

         for (StatData stat : ability.getStats().values()) {
            String statID = stat.getId();
            int quality = this.getStatQuality(stack, abilityID, statID);
            double value = relic.getStatValueByQuality(abilityID, statID, quality);
            relic.setStatInitialValue(evolvedParasite, abilityID, statID, value);
         }
      }

      ((ICuriosItemHandler)CuriosApi.getCuriosInventory(entity).get()).setEquippedCurio(identifier, index, evolvedParasite);
      entity.level().playSound(null, entity, SoundEvents.BEACON_POWER_SELECT, SoundSource.PLAYERS, 1.0F, 0.5F);
      entity.level().playSound(null, entity, SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1.0F, 1.5F);
      PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, new ParasiteEvolveParticlePacket(entity.getId()), new CustomPacketPayload[0]);
      if (entity instanceof ServerPlayer player) {
         PacketDistributor.sendToPlayer(player, new PacketItemActivation(evolvedParasite), new CustomPacketPayload[0]);
      }
   }

   public boolean isEvolved() {
      return false;
   }

   public String getConfigRoute() {
      return "reliquified_twilight_forest";
   }

   @SubscribeEvent
   public static void consumeExperiment115(Finish e) {
      if (e.getItem().is(TFItems.EXPERIMENT_115)) {
         nomnomnom(e.getEntity());
      }
   }

   public static void nomnomnom(LivingEntity entity) {
      ItemStack stack = EntityUtils.findEquippedCurio(entity, (Item)ItemRegistry.PARASITE_116.get());
      if (!entity.level().isClientSide && stack.getItem() instanceof Parasite116Item relic && relic.isAbilityUnlocked(stack, "rage_consumption")) {
         int time = (Integer)stack.getOrDefault(DataComponentRegistry.TIME, 0);
         time = (int)Mth.clamp(time + relic.getStatValue(stack, "rage_consumption", "amount_restored"), 0.0, 200.0);
         stack.set(DataComponentRegistry.TIME, time);
         relic.spreadRelicExperience(entity, stack, 1);
      }
   }
}
