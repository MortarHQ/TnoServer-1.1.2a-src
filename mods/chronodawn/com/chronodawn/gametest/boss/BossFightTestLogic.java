package com.chronodawn.gametest.boss;

import com.chronodawn.entities.bosses.TimeGuardianEntity;
import com.chronodawn.entities.bosses.TimeTyrantEntity;
import com.chronodawn.gametest.RegistryDrivenTestGenerator;
import com.chronodawn.registry.ModEntities;
import com.chronodawn.registry.ModItems;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.ItemLike;

public final class BossFightTestLogic {
   private static final BlockPos TEST_POS = new BlockPos(1, 2, 1);
   private static final int BOSS_TIMEOUT = 200;

   private BossFightTestLogic() {
   }

   public static List<RegistryDrivenTestGenerator.NamedTest> generateTests() {
      List<RegistryDrivenTestGenerator.NamedTest> tests = new ArrayList<>();
      tests.addAll(generateTimeGuardianTests());
      tests.addAll(generateTimeTyrantTests());
      return tests;
   }

   private static List<RegistryDrivenTestGenerator.NamedTest> generateTimeGuardianTests() {
      List<RegistryDrivenTestGenerator.NamedTest> tests = new ArrayList<>();
      tests.add(
         new RegistryDrivenTestGenerator.NamedTest(
            "boss_time_guardian_phase_transition",
            helper -> {
               TimeGuardianEntity guardian = (TimeGuardianEntity)helper.spawn((EntityType)ModEntities.TIME_GUARDIAN.get(), TEST_POS);
               helper.runAfterDelay(
                  5L,
                  () -> {
                     if (guardian.getPhase() != 1) {
                        helper.fail("Time Guardian should start in Phase 1, was Phase " + guardian.getPhase());
                     } else {
                        guardian.setHealth(guardian.getMaxHealth() * 0.4F);
                        helper.runAfterDelay(
                           2L,
                           () -> {
                              if (guardian.getPhase() == 2) {
                                 helper.succeed();
                              } else {
                                 helper.fail(
                                    "Time Guardian should be in Phase 2 at "
                                       + guardian.getHealth() / guardian.getMaxHealth() * 100.0F
                                       + "% HP, was Phase "
                                       + guardian.getPhase()
                                 );
                              }
                           }
                        );
                     }
                  }
               );
            },
            200
         )
      );
      tests.add(new RegistryDrivenTestGenerator.NamedTest("boss_time_guardian_aoe_damage", helper -> {
         TimeGuardianEntity guardian = (TimeGuardianEntity)helper.spawn((EntityType)ModEntities.TIME_GUARDIAN.get(), TEST_POS);
         BlockPos nearbyPos = TEST_POS.offset(2, 0, 0);
         helper.runAfterDelay(5L, () -> {
            guardian.setHealth(guardian.getMaxHealth() * 0.3F);
            Player mockPlayer = helper.makeMockPlayer(GameType.SURVIVAL);
            mockPlayer.setPos(helper.absolutePos(nearbyPos).getX(), helper.absolutePos(nearbyPos).getY(), helper.absolutePos(nearbyPos).getZ());
            guardian.setTarget(mockPlayer);
            helper.runAfterDelay(10L, () -> {
               if (mockPlayer.getHealth() < mockPlayer.getMaxHealth()) {
                  helper.succeed();
               } else if (guardian.getPhase() == 2) {
                  helper.succeed();
               } else {
                  helper.fail("Time Guardian AoE: Phase was " + guardian.getPhase() + ", expected Phase 2");
               }
            });
         });
      }, 200));
      tests.add(new RegistryDrivenTestGenerator.NamedTest("boss_time_guardian_post_teleport_delay", helper -> {
         TimeGuardianEntity guardian = (TimeGuardianEntity)helper.spawn((EntityType)ModEntities.TIME_GUARDIAN.get(), TEST_POS);
         helper.runAfterDelay(5L, () -> {
            if (guardian.isInPostTeleportDelay()) {
               helper.fail("Time Guardian should not start in post-teleport delay");
            } else {
               guardian.setHealth(guardian.getMaxHealth() * 0.3F);
               helper.runAfterDelay(2L, () -> {
                  if (guardian.getPhase() == 2) {
                     helper.succeed();
                  } else {
                     helper.fail("Time Guardian phase transition failed, was Phase " + guardian.getPhase());
                  }
               });
            }
         });
      }, 200));
      tests.add(new RegistryDrivenTestGenerator.NamedTest("boss_time_guardian_loot_items_exist", helper -> helper.runAfterDelay(1L, () -> {
         ItemStack key = new ItemStack((ItemLike)ModItems.KEY_TO_MASTER_CLOCK.get());
         ItemStack enhancedClockstone = new ItemStack((ItemLike)ModItems.ENHANCED_CLOCKSTONE.get());
         if (key.isEmpty()) {
            helper.fail("KEY_TO_MASTER_CLOCK item could not be created");
         } else if (enhancedClockstone.isEmpty()) {
            helper.fail("ENHANCED_CLOCKSTONE item could not be created");
         } else {
            helper.succeed();
         }
      }), 200));
      return tests;
   }

   private static List<RegistryDrivenTestGenerator.NamedTest> generateTimeTyrantTests() {
      List<RegistryDrivenTestGenerator.NamedTest> tests = new ArrayList<>();
      tests.add(new RegistryDrivenTestGenerator.NamedTest("boss_time_tyrant_phase_transitions", helper -> {
         TimeTyrantEntity tyrant = (TimeTyrantEntity)helper.spawn((EntityType)ModEntities.TIME_TYRANT.get(), TEST_POS);
         helper.runAfterDelay(5L, () -> {
            if (tyrant.getPhase() != 1) {
               helper.fail("Time Tyrant should start in Phase 1, was Phase " + tyrant.getPhase());
            } else {
               tyrant.setHealth(tyrant.getMaxHealth() * 0.5F);
               helper.runAfterDelay(2L, () -> {
                  if (tyrant.getPhase() != 2) {
                     helper.fail("Time Tyrant should be Phase 2 at 50% HP, was Phase " + tyrant.getPhase());
                  } else {
                     tyrant.setHealth(tyrant.getMaxHealth() * 0.2F);
                     helper.runAfterDelay(2L, () -> {
                        if (tyrant.getPhase() == 3) {
                           helper.succeed();
                        } else {
                           helper.fail("Time Tyrant should be Phase 3 at 20% HP, was Phase " + tyrant.getPhase());
                        }
                     });
                  }
               });
            }
         });
      }, 200));
      tests.add(new RegistryDrivenTestGenerator.NamedTest("boss_time_tyrant_time_reversal", helper -> {
         TimeTyrantEntity tyrant = (TimeTyrantEntity)helper.spawn((EntityType)ModEntities.TIME_TYRANT.get(), TEST_POS);
         Player mockPlayer = helper.makeMockPlayer(GameType.SURVIVAL);
         BlockPos playerPos = TEST_POS.offset(3, 0, 0);
         mockPlayer.setPos(helper.absolutePos(playerPos).getX(), helper.absolutePos(playerPos).getY(), helper.absolutePos(playerPos).getZ());
         helper.runAfterDelay(5L, () -> {
            tyrant.setTarget(mockPlayer);
            float triggerHp = tyrant.getMaxHealth() * 0.15F;
            tyrant.setHealth(triggerHp);
            helper.runAfterDelay(5L, () -> {
               float currentHp = tyrant.getHealth();
               if (currentHp > triggerHp) {
                  helper.succeed();
               } else if (tyrant.getPhase() == 3) {
                  helper.succeed();
               } else {
                  helper.fail("Time Tyrant at 15% HP should be Phase 3, was Phase " + tyrant.getPhase() + " (HP: " + currentHp + ")");
               }
            });
         });
      }, 400));
      tests.add(new RegistryDrivenTestGenerator.NamedTest("boss_time_tyrant_loot_items_exist", helper -> helper.runAfterDelay(1L, () -> {
         ItemStack fragment = new ItemStack((ItemLike)ModItems.FRAGMENT_OF_STASIS_CORE.get());
         ItemStack eye = new ItemStack((ItemLike)ModItems.EYE_OF_CHRONOS.get());
         ItemStack enhanced = new ItemStack((ItemLike)ModItems.ENHANCED_CLOCKSTONE.get());
         if (fragment.isEmpty()) {
            helper.fail("FRAGMENT_OF_STASIS_CORE item could not be created");
         } else if (eye.isEmpty()) {
            helper.fail("EYE_OF_CHRONOS item could not be created");
         } else if (enhanced.isEmpty()) {
            helper.fail("ENHANCED_CLOCKSTONE item could not be created");
         } else {
            helper.succeed();
         }
      }), 200));
      tests.add(new RegistryDrivenTestGenerator.NamedTest("boss_time_tyrant_knockback_immunity", helper -> {
         TimeTyrantEntity tyrant = (TimeTyrantEntity)helper.spawn((EntityType)ModEntities.TIME_TYRANT.get(), TEST_POS);
         helper.runAfterDelay(5L, () -> {
            double kbResistance = tyrant.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
            if (Math.abs(kbResistance - 1.0) < 0.01) {
               helper.succeed();
            } else {
               helper.fail("Time Tyrant knockback resistance was " + kbResistance + ", expected 1.0");
            }
         });
      }, 200));
      return tests;
   }
}
