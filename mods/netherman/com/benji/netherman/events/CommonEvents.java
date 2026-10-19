package com.benji.netherman.events;

import com.benji.netherman.common.entity.AzazelEntity;
import com.benji.netherman.common.entity.AzazelGuideBookEntity;
import com.benji.netherman.common.entity.AzazelHumanEntity;
import com.benji.netherman.common.entity.BelieverEntity;
import com.benji.netherman.common.entity.BelieverVillagerEntity;
import com.benji.netherman.common.entity.BellGuardianEntity;
import com.benji.netherman.common.entity.BlacksmithEntity;
import com.benji.netherman.common.entity.DoctorEntity;
import com.benji.netherman.common.entity.GhastlyEntity;
import com.benji.netherman.common.entity.GildedGolemEntity;
import com.benji.netherman.common.entity.GuardianEntity;
import com.benji.netherman.common.entity.LaserEntity;
import com.benji.netherman.common.entity.ManipulatorEntity;
import com.benji.netherman.common.entity.PiglinPrisonerEntity;
import com.benji.netherman.common.entity.StatueBossunitEntity;
import com.benji.netherman.common.entity.StatueEntity;
import com.benji.netherman.common.entity.TotemusPuzzleEntity;
import com.benji.netherman.common.entity.TraderEntity;
import com.benji.netherman.common.entity.VillagerPrisonerEntity;
import com.benji.netherman.common.entity.WelcomerEntity;
import com.benji.netherman.init.ModBlocks;
import com.benji.netherman.init.ModEntities;
import com.benji.netherman.init.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = "netherman")
public class CommonEvents {
   @SubscribeEvent
   public static void addCreative(BuildCreativeModeTabContentsEvent event) {
      if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS || event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
         event.accept(ModBlocks.NETHER_SPAWNER);
         event.accept(ModBlocks.GRAND_DOOR);
         event.accept(ModBlocks.BLACKSTONE_COLUMN);
         event.accept(ModBlocks.ENTRANCE);
         event.accept(ModBlocks.CRIMSON_WEB);
         event.accept(ModBlocks.TRAPHIVE);
         event.accept(ModBlocks.VOIDMID);
         event.accept(ModBlocks.VOIDCORNER);
         event.accept(ModBlocks.AZAZEL_TROPHY);
         event.accept(ModBlocks.VOIDMIDCORNER);
         event.accept(ModBlocks.VOIDMIDNETHER);
         event.accept(ModBlocks.VOIDCORNERNETHER);
         event.accept(ModBlocks.MOSAIC_CHURCH);
         event.accept(ModBlocks.STATUE_STAND);
         event.accept(ModBlocks.VOIDMID_CAVE);
         event.accept(ModBlocks.VOIDCORNER_CAVE);
         event.accept(ModBlocks.VOIDMIDCORNER_CAVE);
         event.accept(ModBlocks.SAMSONIT_EYE);
         event.accept(ModBlocks.SAMSONIT_BELL);
         event.accept(ModBlocks.LOCKER_NETHER);
         event.accept(ModBlocks.SAMSONIT_KEY);
         event.accept(ModBlocks.TOTEMUS);
         event.accept(ModBlocks.EYE);
         event.accept(ModBlocks.A_PUZZLE);
         event.accept(ModBlocks.Z_PUZZLE);
         event.accept(ModBlocks.E_PUZZLE);
         event.accept(ModBlocks.L_PUZZLE);
         event.accept(ModBlocks.LABYRINTH_TELEPORT);
         event.accept(ModBlocks.ALTAR);
         event.accept(ModBlocks.VOIDMIDCORNERNETHER);
         event.accept(ModBlocks.SAMSONIT);
         event.accept(ModBlocks.SAMSONIT_BRICKS);
         event.accept(ModBlocks.SAMSONIT_TILES);
         event.accept(ModBlocks.POLISHED_SAMSONIT);
         event.accept(ModBlocks.COBBLED_SAMSONIT);
         event.accept(ModBlocks.CHISELED_SAMSONIT);
         event.accept(ModBlocks.COBBLED_SAMSONIT_SLAB);
         event.accept(ModBlocks.COBBLED_SAMSONIT_STAIRS);
         event.accept(ModBlocks.COBBLED_SAMSONIT_WALL);
         event.accept(ModBlocks.POLISHED_SAMSONIT_SLAB);
         event.accept(ModBlocks.POLISHED_SAMSONIT_STAIRS);
         event.accept(ModBlocks.POLISHED_SAMSONIT_WALL);
         event.accept(ModBlocks.SAMSONIT_BRICKS_SLAB);
         event.accept(ModBlocks.SAMSONIT_BRICKS_STAIRS);
         event.accept(ModBlocks.SAMSONIT_BRICKS_WALL);
      }

      if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
         event.accept(ModItems.CRIMSON_HONEY_BOTTLE);
      }

      if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
         event.accept(ModItems.GILDED_GOLEM_SPAWN_EGG);
         event.accept(ModItems.GHASTLY_SPAWN_EGG);
         event.accept(ModItems.GUARDIAN_SPAWN_EGG);
         event.accept(ModItems.BELIEVER_SPAWN_EGG);
         event.accept(ModItems.BLACKSMITH_SPAWN_EGG);
         event.accept(ModItems.DOCTOR_SPAWN_EGG);
         event.accept(ModItems.BELIEVER_VILLAGER_SPAWN_EGG);
         event.accept(ModItems.MANIPULATOR_SPAWN_EGG);
         event.accept(ModItems.STATUE_BOSSUNIT_SPAWN_EGG);
         event.accept(ModItems.STATUE_SPAWN_EGG);
         event.accept(ModItems.TRADER_SPAWN_EGG);
         event.accept(ModItems.AZAZEL_SPAWN_EGG);
      }

      if (event.getTabKey() == CreativeModeTabs.COMBAT || event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
         event.accept(ModItems.MANIPULATOR_STICK);
         event.accept(ModItems.CRIMSON_ARROW);
         event.accept(ModItems.CHANCE_TOTEM);
         event.accept(ModItems.NOTE);
         event.accept(ModItems.AZAZEL_GUIDE_BOOK);
      }

      if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
         event.accept(ModBlocks.BLACKSTONE_PLANT);
         event.accept(ModBlocks.BLACKSTONE_AXON);
         event.accept(ModBlocks.CRIMSON_HONEY_BLOCK);
         event.accept(ModBlocks.GHASTLY_NEST);
         event.accept(ModBlocks.POINTED_BLACKSTONE);
      }
   }

   @SubscribeEvent
   public static void registerAttributes(EntityAttributeCreationEvent event) {
      event.put((EntityType)ModEntities.AZAZEL_GUIDE_BOOK.get(), AzazelGuideBookEntity.createAttributes().build());
      event.put((EntityType)ModEntities.TOTEMUS_PUZZLE.get(), TotemusPuzzleEntity.createAttributes().build());
      event.put((EntityType)ModEntities.GILDED_GOLEM.get(), GildedGolemEntity.createAttributes().build());
      event.put((EntityType)ModEntities.AZAZEL_HUMAN.get(), AzazelHumanEntity.createAttributes().build());
      event.put((EntityType)ModEntities.AZAZEL.get(), AzazelEntity.createAttributes().build());
      event.put((EntityType)ModEntities.BELL_GUARDIAN.get(), BellGuardianEntity.createAttributes().build());
      event.put((EntityType)ModEntities.LASER.get(), LaserEntity.createAttributes().build());
      event.put((EntityType)ModEntities.STATUE_BOSSUNIT.get(), StatueBossunitEntity.createAttributes().build());
      event.put((EntityType)ModEntities.BLACKSMITH.get(), BlacksmithEntity.createAttributes().build());
      event.put((EntityType)ModEntities.DOCTOR.get(), DoctorEntity.createAttributes().build());
      event.put((EntityType)ModEntities.TRADER.get(), TraderEntity.createAttributes().build());
      event.put((EntityType)ModEntities.STATUE.get(), StatueEntity.createAttributes().build());
      event.put((EntityType)ModEntities.BELIEVER.get(), BelieverEntity.createAttributes().build());
      event.put((EntityType)ModEntities.BELIEVER_VILLAGER.get(), BelieverVillagerEntity.createAttributes().build());
      event.put((EntityType)ModEntities.PIGLIN_PRISONER.get(), PiglinPrisonerEntity.createAttributes().build());
      event.put((EntityType)ModEntities.VILLAGER_PRISONER.get(), VillagerPrisonerEntity.createAttributes().build());
      event.put((EntityType)ModEntities.MANIPULATOR.get(), ManipulatorEntity.createAttributes().build());
      event.put((EntityType)ModEntities.WELCOMER.get(), WelcomerEntity.createAttributes().build());
      event.put((EntityType)ModEntities.GHASTLY.get(), GhastlyEntity.createAttributes().build());
      event.put((EntityType)ModEntities.GUARDIAN.get(), GuardianEntity.createAttributes().build());
   }
}
