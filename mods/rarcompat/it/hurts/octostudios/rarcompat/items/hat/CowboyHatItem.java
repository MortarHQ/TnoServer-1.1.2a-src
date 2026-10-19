package it.hurts.octostudios.rarcompat.items.hat;

import artifacts.registry.ModItems;
import it.hurts.octostudios.rarcompat.items.WearableRelicItem;
import it.hurts.sskirillss.relics.init.DataComponentRegistry;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.CastData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastStage;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastType;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.PredicateType;
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
import it.hurts.sskirillss.relics.items.relics.base.data.research.ResearchData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.BeamsData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.StyleData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.TooltipData;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.ParticleUtils;
import java.awt.Color;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityMountEvent;
import top.theillusivec4.curios.api.SlotContext;

public class CowboyHatItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("cowboy")
                     .stat(
                        StatData.builder("speed")
                           .initialValue(0.2, 0.3)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.15)
                           .formatValue(value -> (int)MathUtils.round(value * 100.0, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 5, 8)
                           .star(1, 7, 22)
                           .star(2, 16, 22)
                           .star(3, 19, 18)
                           .star(4, 12, 17)
                           .link(4, 0)
                           .link(4, 1)
                           .link(4, 2)
                           .link(4, 3)
                           .build()
                     )
                     .build()
               )
               .ability(
                  AbilityData.builder("overlord")
                     .active(
                        CastData.builder()
                           .type(CastType.INSTANTANEOUS)
                           .predicate(
                              "overlord",
                              PredicateType.CAST,
                              (player, stack) -> EntityUtils.rayTraceEntity(
                                    player,
                                    entity -> entity instanceof Mob
                                       && this.checkMob(entity, EnderDragon.class, WitherBoss.class, Warden.class, ElderGuardian.class)
                                       && !player.isPassenger(),
                                    player.getAttribute(Attributes.ENTITY_INTERACTION_RANGE).getValue()
                                 )
                                 != null
                           )
                           .build()
                     )
                     .requiredLevel(5)
                     .stat(
                        StatData.builder("time")
                           .initialValue(2.0, 5.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.15)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 9, 29)
                           .star(1, 16, 21)
                           .star(2, 15, 16)
                           .star(3, 9, 20)
                           .star(4, 3, 15)
                           .star(5, 9, 10)
                           .star(6, 14, 5)
                           .star(7, 19, 8)
                           .star(8, 19, 18)
                           .link(0, 1)
                           .link(1, 2)
                           .link(2, 3)
                           .link(3, 4)
                           .link(4, 5)
                           .link(5, 6)
                           .link(6, 7)
                           .link(2, 8)
                           .link(7, 2)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-11065324).borderBottom(-12110298).build())
               .beams(BeamsData.builder().startColor(-10334930).endColor(4726548).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(15)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("cowboy").initialValue(1).gem(GemShape.SQUARE, GemColor.ORANGE).build())
                     .source(LevelingSourceData.abilityBuilder("overlord").initialValue(1).gem(GemShape.SQUARE, GemColor.BLUE).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.VILLAGE, LootEntries.SAVANNA}).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player
         && player.getRootVehicle() instanceof Mob beingMounted
         && this.getToggled(stack)
         && this.checkMob(player, EnderDragon.class, WitherBoss.class, Warden.class, ElderGuardian.class)) {
         if (!this.isAbilityOnCooldown(stack, "overlord") && this.isAbilityUnlocked(stack, "overlord")) {
            RandomSource random = player.getRandom();
            Level level = player.getCommandSenderWorld();
            if (this.getTime(stack) >= this.getStatValue(stack, "overlord", "time") * 20.0) {
               player.stopRiding();
               player.playSound(SoundEvents.WOOL_HIT, 1.0F, 0.9F + player.getRandom().nextFloat() * 0.2F);
               this.setTime(stack, 0);

               for (int i = 0; i < 50; i++) {
                  level.addParticle(
                     ParticleUtils.constructSimpleSpark(
                        new Color(150 + random.nextInt(106), 50 + random.nextInt(100), 50 + random.nextInt(100)), 0.5F, 60, 0.95F
                     ),
                     player.getX(),
                     player.getY() + 1.0,
                     player.getZ(),
                     (random.nextDouble() - 0.5) * 3.0,
                     random.nextDouble() * 1.5,
                     (random.nextDouble() - 0.5) * 3.0
                  );
               }
            } else {
               if (level.isClientSide()
                  && player instanceof LocalPlayer localPlayer
                  && localPlayer.input.jumping
                  && beingMounted.onGround()
                  && !this.isWaterOrFlyingMob(beingMounted)) {
                  beingMounted.addDeltaMovement(new Vec3(0.0, 0.8, 0.0));
               }

               Vec3 knownMovement = beingMounted.getKnownMovement();
               if ((knownMovement.x != 0.0 || knownMovement.z != 0.0) && random.nextFloat() <= 0.25F && player.tickCount % 20 == 0) {
                  this.spreadRelicExperience(player, stack, 1);
               }

               if (this.isAbilityUnlocked(stack, "cowboy")) {
                  this.changeAttributes(beingMounted, stack, true, Attributes.MOVEMENT_SPEED, Attributes.JUMP_STRENGTH, Attributes.SAFE_FALL_DISTANCE);
               }

               this.addTime(stack, 1);
            }
         } else {
            player.stopRiding();
         }
      }
   }

   public void castActiveAbility(ItemStack stack, Player player, String ability, CastType type, CastStage stage) {
      if (!player.getCommandSenderWorld().isClientSide() && ability.equals("overlord")) {
         EntityHitResult result = EntityUtils.rayTraceEntity(
            player, entity -> entity instanceof Mob, player.getAttribute(Attributes.ENTITY_INTERACTION_RANGE).getValue()
         );
         if (result != null) {
            this.setToggled(stack, true);
            this.spreadRelicExperience(player, stack, 1);
            player.startRiding(result.getEntity());
         }
      }
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      if (slotContext.entity() instanceof Player player && newStack.getItem() != stack.getItem() && player.getRootVehicle() instanceof Mob mob) {
         this.changeAttributes(mob, stack, false, Attributes.MOVEMENT_SPEED, Attributes.JUMP_STRENGTH, Attributes.SAFE_FALL_DISTANCE);
         if (!(player.getControlledVehicle() instanceof Mob)) {
            player.stopRiding();
         }
      }
   }

   @SafeVarargs
   public final void changeAttributes(Mob beingMounted, ItemStack stack, boolean flag, Holder<Attribute>... attributeHolder) {
      for (Holder<Attribute> attributes : attributeHolder) {
         if (flag) {
            EntityUtils.applyAttribute(beingMounted, stack, attributes, (float)this.getStatValue(stack, "cowboy", "speed"), Operation.ADD_MULTIPLIED_BASE);
         } else {
            EntityUtils.removeAttribute(beingMounted, stack, attributes, Operation.ADD_MULTIPLIED_BASE);
         }
      }
   }

   @SafeVarargs
   private boolean checkMob(Entity entity, Class<? extends Mob>... mobClasses) {
      for (Class<? extends Mob> mobClass : mobClasses) {
         if (mobClass.isInstance(entity)) {
            return false;
         }
      }

      return true;
   }

   public boolean isWaterOrFlyingMob(Mob mounted) {
      return mounted instanceof FlyingAnimal || mounted instanceof FlyingMob || mounted instanceof WaterAnimal || mounted instanceof AmbientCreature;
   }

   public void addTime(ItemStack stack, int val) {
      this.setTime(stack, this.getTime(stack) + val);
   }

   public int getTime(ItemStack stack) {
      return (Integer)stack.getOrDefault(DataComponentRegistry.TIME, 0);
   }

   public void setTime(ItemStack stack, int val) {
      stack.set(DataComponentRegistry.TIME, Math.max(val, 0));
   }

   public void setToggled(ItemStack stack, boolean val) {
      stack.set(DataComponentRegistry.TOGGLED, val);
   }

   public boolean getToggled(ItemStack stack) {
      return (Boolean)stack.getOrDefault(DataComponentRegistry.TOGGLED, false);
   }

   @EventBusSubscriber
   public static class CowboyEvent {
      @SubscribeEvent
      public static void onEntityMount(EntityMountEvent event) {
         if (event.getEntity() instanceof Player player) {
            ItemStack var6 = EntityUtils.findEquippedCurio(player, (Item)ModItems.COWBOY_HAT.value());
            if (!player.getCommandSenderWorld().isClientSide()
               && var6.getItem() instanceof CowboyHatItem relic
               && event.isDismounting()
               && event.getEntityBeingMounted() instanceof Mob mount
               && relic.getToggled(var6)) {
               relic.addAbilityCooldown(var6, "overlord", 600);
               relic.setTime(var6, 0);
               relic.setToggled(var6, false);
               relic.changeAttributes(mount, var6, false, Attributes.MOVEMENT_SPEED, Attributes.JUMP_STRENGTH, Attributes.SAFE_FALL_DISTANCE);
            }
         }
      }
   }
}
