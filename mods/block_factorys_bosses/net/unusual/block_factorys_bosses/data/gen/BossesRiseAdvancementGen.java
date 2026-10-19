package net.unusual.block_factorys_bosses.data.gen;

import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.Advancement.Builder;
import net.minecraft.advancements.AdvancementRequirements.Strategy;
import net.minecraft.advancements.critereon.KilledTrigger.TriggerInstance;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.AdvancementProvider.AdvancementGenerator;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.data.util.BossesRiseLangProvider;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.init.BossesRiseTags;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BossesRiseAdvancementGen implements AdvancementGenerator {
   public void generate(Provider provider, Consumer<AdvancementHolder> consumer, ExistingFileHelper existingFileHelper) {
      AdvancementHolder root = this.create(
         "Bosses' Rise",
         "Die fighting a Boss",
         builder -> builder.display(
               this.advancementIcon(3),
               this.makeComponent("die_boss", "title"),
               this.makeComponent("die_boss", "desc"),
               BossesRise.prefix("textures/screens/blackstone.png"),
               AdvancementType.TASK,
               true,
               false,
               false
            )
            .addCriterion(
               "die_to_boss",
               TriggerInstance.entityKilledPlayer(net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of(BossesRiseTags.EntityTypes.BF_BOSS))
            )
            .save(consumer, "block_factorys_bosses:die_boss")
      );
      AdvancementHolder yeti = this.create(
         "Lost in the Snow",
         "Defeat Skor, the Yeti",
         builder -> builder.parent(root)
            .display(
               this.advancementIcon(9),
               this.makeComponent("kill_yeti", "title"),
               this.makeComponent("kill_yeti", "desc"),
               null,
               AdvancementType.TASK,
               true,
               true,
               false
            )
            .addCriterion(
               "kill",
               TriggerInstance.playerKilledEntity(
                  net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of((EntityType)BossesRiseEntities.YETI.get())
               )
            )
            .rewards(net.minecraft.advancements.AdvancementRewards.Builder.experience(100))
            .save(consumer, "block_factorys_bosses:kill_yeti")
      );
      this.create(
         "King of the Hill",
         "Kill Skor without getting hit",
         builder -> builder.parent(yeti)
            .display(
               this.advancementIcon(10),
               this.makeComponent("no_hit_yeti", "title"),
               this.makeComponent("no_hit_yeti", "desc"),
               null,
               AdvancementType.CHALLENGE,
               true,
               true,
               false
            )
            .addCriterion(
               "no_hit_yeti_0", CriteriaTriggers.IMPOSSIBLE.createCriterion(new net.minecraft.advancements.critereon.ImpossibleTrigger.TriggerInstance())
            )
            .rewards(net.minecraft.advancements.AdvancementRewards.Builder.experience(500))
            .save(consumer, "block_factorys_bosses:no_hit_yeti")
      );
      AdvancementHolder dragon = this.create(
         "Dragon Slayer",
         "Defeat Ashlord, the Infernal Dragon",
         builder -> builder.parent(root)
            .display(
               this.advancementIcon(-1),
               this.makeComponent("kill_dragon", "title"),
               this.makeComponent("kill_dragon", "desc"),
               null,
               AdvancementType.TASK,
               true,
               true,
               false
            )
            .addCriterion(
               "kill",
               TriggerInstance.playerKilledEntity(
                  net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of((EntityType)BossesRiseEntities.INFERNAL_DRAGON.get())
               )
            )
            .rewards(net.minecraft.advancements.AdvancementRewards.Builder.experience(100))
            .save(consumer, "block_factorys_bosses:kill_dragon")
      );
      this.create(
         "Dragon Hunter",
         "Kill the Infernal Dragon without getting hit",
         builder -> builder.parent(dragon)
            .display(
               this.advancementIcon(0),
               this.makeComponent("no_hit_dragon", "title"),
               this.makeComponent("no_hit_dragon", "desc"),
               null,
               AdvancementType.CHALLENGE,
               true,
               true,
               false
            )
            .addCriterion(
               "no_hit_dragon_0", CriteriaTriggers.IMPOSSIBLE.createCriterion(new net.minecraft.advancements.critereon.ImpossibleTrigger.TriggerInstance())
            )
            .rewards(net.minecraft.advancements.AdvancementRewards.Builder.experience(500))
            .save(consumer, "block_factorys_bosses:no_hit_dragon")
      );
      AdvancementHolder knight = this.create(
         "Back Into Hell!",
         "Defeat Helvar, the Underworld Knight",
         builder -> builder.parent(root)
            .display(
               this.advancementIcon(1),
               this.makeComponent("kill_underworld_knight", "title"),
               this.makeComponent("kill_underworld_knight", "desc"),
               null,
               AdvancementType.TASK,
               true,
               true,
               false
            )
            .addCriterion(
               "kill",
               TriggerInstance.playerKilledEntity(
                  net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of((EntityType)BossesRiseEntities.UNDERWORLD_KNIGHT.get())
               )
            )
            .rewards(net.minecraft.advancements.AdvancementRewards.Builder.experience(100))
            .save(consumer, "block_factorys_bosses:kill_underworld_knight")
      );
      this.create(
         "Beyond Death",
         "Kill the Underworld Knight without getting hit",
         builder -> builder.parent(knight)
            .display(
               this.advancementIcon(2),
               this.makeComponent("no_hit_underworld_knight", "title"),
               this.makeComponent("no_hit_underworld_knight", "desc"),
               null,
               AdvancementType.CHALLENGE,
               true,
               true,
               false
            )
            .addCriterion(
               "no_hit_underworld_knight_0",
               CriteriaTriggers.IMPOSSIBLE.createCriterion(new net.minecraft.advancements.critereon.ImpossibleTrigger.TriggerInstance())
            )
            .rewards(net.minecraft.advancements.AdvancementRewards.Builder.experience(500))
            .save(consumer, "block_factorys_bosses:no_hit_underworld_knight")
      );
      AdvancementHolder sandworm = this.create(
         "The Beast Below",
         "Defeat Sirok, the Sandworm",
         builder -> builder.parent(root)
            .display(
               this.advancementIcon(7),
               this.makeComponent("kill_sandworm", "title"),
               this.makeComponent("kill_sandworm", "desc"),
               null,
               AdvancementType.TASK,
               true,
               true,
               false
            )
            .addCriterion(
               "kill",
               TriggerInstance.playerKilledEntity(
                  net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of((EntityType)BossesRiseEntities.SANDWORM.get())
               )
            )
            .rewards(net.minecraft.advancements.AdvancementRewards.Builder.experience(100))
            .save(consumer, "block_factorys_bosses:kill_sandworm")
      );
      this.create(
         "Desert Power",
         "Kill Sirok, the Sandworm, without getting hit",
         builder -> builder.parent(sandworm)
            .display(
               this.advancementIcon(8),
               this.makeComponent("no_hit_sandworm", "title"),
               this.makeComponent("no_hit_sandworm", "desc"),
               null,
               AdvancementType.CHALLENGE,
               true,
               true,
               false
            )
            .addCriterion(
               "no_hit_sandworm_0", CriteriaTriggers.IMPOSSIBLE.createCriterion(new net.minecraft.advancements.critereon.ImpossibleTrigger.TriggerInstance())
            )
            .rewards(net.minecraft.advancements.AdvancementRewards.Builder.experience(500))
            .save(consumer, "block_factorys_bosses:no_hit_sandworm")
      );
      AdvancementHolder kraken = this.create(
         "Terror of the Depths",
         "Defeat Nerakyss, the Kraken",
         builder -> builder.parent(root)
            .display(
               this.advancementIcon(11),
               this.makeComponent("kill_kraken", "title"),
               this.makeComponent("kill_kraken", "desc"),
               null,
               AdvancementType.TASK,
               true,
               true,
               false
            )
            .addCriterion(
               "kill",
               TriggerInstance.playerKilledEntity(
                  net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of((EntityType)BossesRiseEntities.KRAKEN.get())
               )
            )
            .rewards(net.minecraft.advancements.AdvancementRewards.Builder.experience(100))
            .save(consumer, "block_factorys_bosses:kill_kraken")
      );
      this.create(
         "Wraith of the Tide",
         "Kill Nerakyss, the Kraken, without getting hit",
         builder -> builder.parent(kraken)
            .display(
               this.advancementIcon(12),
               this.makeComponent("no_hit_kraken", "title"),
               this.makeComponent("no_hit_kraken", "desc"),
               null,
               AdvancementType.CHALLENGE,
               true,
               true,
               false
            )
            .addCriterion(
               "no_hit_kraken_0", CriteriaTriggers.IMPOSSIBLE.createCriterion(new net.minecraft.advancements.critereon.ImpossibleTrigger.TriggerInstance())
            )
            .rewards(net.minecraft.advancements.AdvancementRewards.Builder.experience(500))
            .save(consumer, "block_factorys_bosses:no_hit_kraken")
      );
      AdvancementHolder overclocker = this.create(
         "Overclocker",
         "Defeat a Boss under a minute",
         builder -> builder.parent(root)
            .display(
               this.advancementIcon(6),
               this.makeComponent("kill_boss_under_minute", "title"),
               this.makeComponent("kill_boss_under_minute", "desc"),
               null,
               AdvancementType.CHALLENGE,
               true,
               true,
               false
            )
            .addCriterion(
               "kill_boss_under_minute_0",
               CriteriaTriggers.IMPOSSIBLE.createCriterion(new net.minecraft.advancements.critereon.ImpossibleTrigger.TriggerInstance())
            )
            .rewards(net.minecraft.advancements.AdvancementRewards.Builder.experience(200))
            .save(consumer, "block_factorys_bosses:kill_boss_under_minute")
      );
      this.create(
         "Unbound",
         "Defeat all Bosses",
         builder -> builder.parent(overclocker)
            .display(
               this.advancementIcon(4),
               this.makeComponent("kill_all_bosses", "title"),
               this.makeComponent("kill_all_bosses", "desc"),
               null,
               AdvancementType.CHALLENGE,
               true,
               true,
               false
            )
            .requirements(Strategy.AND)
            .addCriterion(
               "kill_yeti",
               TriggerInstance.playerKilledEntity(
                  net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of((EntityType)BossesRiseEntities.YETI.get())
               )
            )
            .addCriterion(
               "kill_dragon",
               TriggerInstance.playerKilledEntity(
                  net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of((EntityType)BossesRiseEntities.INFERNAL_DRAGON.get())
               )
            )
            .addCriterion(
               "kill_knight",
               TriggerInstance.playerKilledEntity(
                  net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of((EntityType)BossesRiseEntities.UNDERWORLD_KNIGHT.get())
               )
            )
            .addCriterion(
               "kill_worm",
               TriggerInstance.playerKilledEntity(
                  net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of((EntityType)BossesRiseEntities.SANDWORM.get())
               )
            )
            .addCriterion(
               "kill_kraken",
               TriggerInstance.playerKilledEntity(
                  net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().of((EntityType)BossesRiseEntities.KRAKEN.get())
               )
            )
            .rewards(net.minecraft.advancements.AdvancementRewards.Builder.experience(1000))
            .save(consumer, "block_factorys_bosses:kill_all_bosses")
      );
   }

   private AdvancementHolder create(String name, String description, Function<Builder, AdvancementHolder> builder) {
      AdvancementHolder advancement = builder.apply(Builder.advancement());
      DisplayInfo display = (DisplayInfo)advancement.value().display().orElse(null);
      if (display != null) {
         BossesRiseLangProvider.AUTO_GENERATOR.put(display.getTitle().getString(), name);
         BossesRiseLangProvider.AUTO_GENERATOR.put(display.getDescription().getString(), description);
      }

      return advancement;
   }

   private MutableComponent makeComponent(String name, String path) {
      return Component.translatable("advancement." + name + "." + path);
   }

   private ItemStack advancementIcon(int i) {
      ItemStack stack = ((Item)BossesRiseItems.ACHIEVEMENT_ICONS.get()).getDefaultInstance();
      stack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(i));
      return stack;
   }
}
