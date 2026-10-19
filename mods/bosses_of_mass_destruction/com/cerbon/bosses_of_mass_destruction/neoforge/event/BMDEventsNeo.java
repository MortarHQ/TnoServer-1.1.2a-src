package com.cerbon.bosses_of_mass_destruction.neoforge.event;

import com.cerbon.bosses_of_mass_destruction.block.BMDBlocks;
import com.cerbon.bosses_of_mass_destruction.entity.BMDEntities;
import com.cerbon.bosses_of_mass_destruction.item.BMDCreativeModeTabs;
import com.cerbon.bosses_of_mass_destruction.item.BMDItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = "bosses_of_mass_destruction", bus = Bus.MOD)
public class BMDEventsNeo {
   @SubscribeEvent
   public static void addCreativeTab(@NotNull BuildCreativeModeTabContentsEvent event) {
      if (event.getTab() == BMDCreativeModeTabs.BOSSES_OF_MASS_DESTRUCTION.get()) {
         event.accept((ItemLike)BMDItems.MOB_WARD.get());
         event.accept((ItemLike)BMDBlocks.MONOLITH_BLOCK.get());
         event.accept((ItemLike)BMDBlocks.LEVITATION_BLOCK.get());
         event.accept((ItemLike)BMDBlocks.VOID_LILY_BLOCK.get());
         event.accept((ItemLike)BMDItems.SOUL_STAR.get());
         event.accept((ItemLike)BMDItems.ANCIENT_ANIMA.get());
         event.accept((ItemLike)BMDItems.BLAZING_EYE.get());
         event.accept((ItemLike)BMDItems.OBSIDIAN_HEART.get());
         event.accept((ItemLike)BMDItems.EARTHDIVE_SPEAR.get());
         event.accept((ItemLike)BMDItems.VOID_THORN.get());
         event.accept((ItemLike)BMDItems.CRYSTAL_FRUIT.get());
         event.accept((ItemLike)BMDItems.CHARGED_ENDER_PEARL.get());
         event.accept((ItemLike)BMDItems.BRIMSTONE_NECTAR.get());
      }
   }

   @SubscribeEvent
   public static void createAttributes(EntityAttributeCreationEvent event) {
      event.put(
         (EntityType)BMDEntities.LICH.get(),
         Mob.createMobAttributes()
            .add(Attributes.FLYING_SPEED, 5.0)
            .add(Attributes.MAX_HEALTH, BMDEntities.mobConfig.lichConfig.health)
            .add(Attributes.FOLLOW_RANGE, 64.0)
            .add(Attributes.ATTACK_DAMAGE, BMDEntities.mobConfig.lichConfig.missile.damage)
            .build()
      );
      event.put(
         (EntityType)BMDEntities.OBSIDILITH.get(),
         Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, BMDEntities.mobConfig.obsidilithConfig.health)
            .add(Attributes.FOLLOW_RANGE, 32.0)
            .add(Attributes.ATTACK_DAMAGE, BMDEntities.mobConfig.obsidilithConfig.attack)
            .add(Attributes.KNOCKBACK_RESISTANCE, 10.0)
            .add(Attributes.ARMOR, BMDEntities.mobConfig.obsidilithConfig.armor)
            .build()
      );
      event.put(
         (EntityType)BMDEntities.GAUNTLET.get(),
         Mob.createMobAttributes()
            .add(Attributes.FLYING_SPEED, 4.0)
            .add(Attributes.FOLLOW_RANGE, 48.0)
            .add(Attributes.MAX_HEALTH, BMDEntities.mobConfig.gauntletConfig.health)
            .add(Attributes.KNOCKBACK_RESISTANCE, 10.0)
            .add(Attributes.ATTACK_DAMAGE, BMDEntities.mobConfig.gauntletConfig.attack)
            .add(Attributes.ARMOR, BMDEntities.mobConfig.gauntletConfig.armor)
            .build()
      );
      event.put(
         (EntityType)BMDEntities.VOID_BLOSSOM.get(),
         Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, BMDEntities.mobConfig.voidBlossomConfig.health)
            .add(Attributes.FOLLOW_RANGE, 32.0)
            .add(Attributes.ATTACK_DAMAGE, BMDEntities.mobConfig.voidBlossomConfig.attack)
            .add(Attributes.KNOCKBACK_RESISTANCE, 10.0)
            .add(Attributes.ARMOR, BMDEntities.mobConfig.voidBlossomConfig.armor)
            .build()
      );
   }
}
