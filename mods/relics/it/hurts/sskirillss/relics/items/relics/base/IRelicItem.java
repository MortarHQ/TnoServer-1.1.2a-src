package it.hurts.sskirillss.relics.items.relics.base;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import it.hurts.sskirillss.relics.api.events.leveling.ExperienceAddEvent;
import it.hurts.sskirillss.relics.components.AbilitiesComponent;
import it.hurts.sskirillss.relics.components.AbilityComponent;
import it.hurts.sskirillss.relics.components.AbilityExtenderComponent;
import it.hurts.sskirillss.relics.components.DataComponent;
import it.hurts.sskirillss.relics.components.LevelingComponent;
import it.hurts.sskirillss.relics.components.LockComponent;
import it.hurts.sskirillss.relics.components.ResearchComponent;
import it.hurts.sskirillss.relics.components.StatComponent;
import it.hurts.sskirillss.relics.config.data.RelicConfigData;
import it.hurts.sskirillss.relics.entities.RelicExperienceOrbEntity;
import it.hurts.sskirillss.relics.init.DataComponentRegistry;
import it.hurts.sskirillss.relics.init.EntityRegistry;
import it.hurts.sskirillss.relics.init.RegistryRegistry;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicAttributeModifier;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicSlotModifier;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicStorage;
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
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootData;
import it.hurts.sskirillss.relics.items.relics.base.data.research.ResearchData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.StyleData;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.MathUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

public interface IRelicItem {
   @Nullable
   default Item getItem() {
      return this instanceof Item item ? item : null;
   }

   RelicData constructDefaultRelicData();

   String getConfigRoute();

   @Nullable
   default RelicConfigData constructDefaultConfigData(@NotNull RelicConfigData config) {
      return config;
   }

   default void castActiveAbility(ItemStack stack, Player player, String ability, CastType type, CastStage stage) {
   }

   default void tickActiveAbilitySelection(ItemStack stack, Player player, String ability) {
   }

   @Nullable
   default RelicAttributeModifier getRelicAttributeModifiers(ItemStack stack) {
      return RelicAttributeModifier.builder().build();
   }

   @Nullable
   default RelicSlotModifier getSlotModifiers(ItemStack stack) {
      return RelicSlotModifier.builder().build();
   }

   default RelicData getRelicData() {
      if (!RelicStorage.RELICS.containsKey(this)) {
         RelicStorage.RELICS.put(this, this.constructDefaultRelicData());
      }

      return RelicStorage.RELICS.get(this);
   }

   default void setRelicData(RelicData data) {
      RelicStorage.RELICS.put(this, data);
   }

   default AbilitiesData getAbilitiesData() {
      return this.getRelicData().getAbilities();
   }

   default AbilityData getAbilityData(String ability) {
      return this.getAbilitiesData().getAbilities().get(ability);
   }

   default ResearchData getResearchData(String ability) {
      return this.getAbilityData(ability).getResearchData();
   }

   default StatData getStatData(String ability, String stat) {
      return this.getAbilityData(ability).getStats().get(stat);
   }

   default LevelingData getLevelingData() {
      return this.getRelicData().getLeveling();
   }

   default LevelingSourcesData getLevelingSourcesData() {
      return this.getLevelingData().getSources();
   }

   default LevelingSourceData getLevelingSourceData(String source) {
      return this.getLevelingSourcesData().getSources().get(source);
   }

   default boolean isLevelingSourceUnlocked(ItemStack stack, String source) {
      LevelingSourceData data = this.getLevelingSourceData(source);
      String ability = data.getRequiredAbility();
      return this.isLevelingSourceEnabled(stack, source)
         && this.getRelicLevel(stack) >= data.getRequiredLevel()
         && (ability.isEmpty() || this.isAbilityUnlocked(stack, ability));
   }

   default boolean isLevelingSourceEnabled(ItemStack stack, String source) {
      LevelingSourceData data = this.getLevelingSourceData(source);
      String ability = data.getRequiredAbility();
      return data.getRequiredAbility().isEmpty() || this.isAbilityEnabled(stack, ability);
   }

   default int getLevelingSourceValue(ItemStack stack, String source) {
      LevelingSourceData data = this.getLevelingSourceData(source);
      return data.getInitialValue();
   }

   default int getLevelingSourceLevel(ItemStack stack, String source) {
      return 1;
   }

   default LootData getLootData() {
      return this.getRelicData().getLoot();
   }

   default StyleData getStyleData() {
      return this.getRelicData().getStyle();
   }

   default int getMaxQuality() {
      return 10;
   }

   default int getStatQuality(ItemStack stack, String ability, String stat) {
      StatData statData = this.getStatData(ability, stat);
      if (statData == null) {
         return 0;
      } else {
         Function<Double, ? extends Number> format = statData.getFormatValue();
         double initial = format.apply(this.getStatInitialValue(stack, ability, stat)).doubleValue();
         double min = format.apply((Double)statData.getInitialValue().getKey()).doubleValue();
         double max = format.apply((Double)statData.getInitialValue().getValue()).doubleValue();
         if (min == max) {
            return this.getMaxQuality();
         } else if (initial == min) {
            return 0;
         } else {
            return initial == max
               ? this.getMaxQuality()
               : Mth.clamp((int)Math.round((initial - min) / ((max - min) / this.getMaxQuality())), 1, this.getMaxQuality() - 1);
         }
      }
   }

   default double getStatValueByQuality(String ability, String stat, int quality) {
      StatData statData = this.getStatData(ability, stat);
      if (statData == null) {
         return 0.0;
      }

      double min = (Double)statData.getInitialValue().getKey();
      double max = (Double)statData.getInitialValue().getValue();
      return min == max ? max : MathUtils.round(min + (max - min) / this.getMaxQuality() * quality, 5);
   }

   default int getAbilityQuality(ItemStack stack, String ability) {
      Map<String, StatData> stats = this.getAbilityData(ability).getStats();
      if (stats.isEmpty()) {
         return this.getMaxQuality();
      }

      double sum = 0.0;

      for (String stat : stats.keySet()) {
         sum += this.getStatQuality(stack, ability, stat);
      }

      sum = (int)Math.floor(sum / stats.size());
      int min = 0;
      int max = this.getMaxQuality();
      if (sum == min) {
         return min;
      } else {
         return sum == max ? max : (int)Mth.clamp(sum, min + 1, max - 1);
      }
   }

   default int getRelicQuality(ItemStack stack) {
      Map<String, AbilityData> abilities = this.getAbilitiesData().getAbilities();
      if (abilities.isEmpty()) {
         return 0;
      }

      int size = abilities.size();
      double sum = 0.0;

      for (Entry<String, AbilityData> entry : abilities.entrySet()) {
         String ability = entry.getKey();
         if (this.canBeUpgraded(stack, ability) && this.isAbilityUnlocked(stack, ability)) {
            sum += this.getAbilityQuality(stack, entry.getKey());
         } else {
            size--;
         }
      }

      sum = (int)Math.floor(sum / size);
      int min = 0;
      int max = this.getMaxQuality();
      if (sum == min) {
         return min;
      } else {
         return sum == max ? max : (int)Mth.clamp(sum, min + 1, max - 1);
      }
   }

   default int getRelicLevelingPoints(ItemStack stack) {
      return this.getLevelingComponent(stack).points();
   }

   default void setRelicLevelingPoints(ItemStack stack, int amount) {
      this.setLevelingComponent(stack, this.getLevelingComponent(stack).toBuilder().points(Math.max(0, amount)).build());
   }

   default void addRelicLevelingPoints(ItemStack stack, int amount) {
      this.setRelicLevelingPoints(stack, this.getRelicLevelingPoints(stack) + amount);
   }

   default int getRelicLevel(ItemStack stack) {
      return this.getLevelingComponent(stack).level();
   }

   default void setRelicLevel(ItemStack stack, int level) {
      this.setLevelingComponent(stack, this.getLevelingComponent(stack).toBuilder().level(Math.max(0, level)).build());
   }

   default void addRelicLevel(ItemStack stack, int amount) {
      if (amount > 0) {
         this.addRelicLevelingPoints(stack, Mth.clamp(amount, 0, this.getLevelingData().getMaxLevel() - this.getRelicLevel(stack)));
      }

      this.setRelicLevel(stack, this.getRelicLevel(stack) + amount);
   }

   default int getMaxLuck() {
      return 100;
   }

   default double getLuckModifier() {
      return 1.15;
   }

   default int getRelicLuck(ItemStack stack) {
      return this.getLevelingComponent(stack).luck();
   }

   default void setRelicLuck(ItemStack stack, int amount) {
      this.setLevelingComponent(stack, this.getLevelingComponent(stack).toBuilder().luck(Mth.clamp(amount, 0, this.getMaxLuck())).build());
   }

   default void addRelicLuck(ItemStack stack, int amount) {
      this.setRelicLuck(stack, this.getRelicLuck(stack) + amount);
   }

   default int getRelicExperience(ItemStack stack) {
      return this.getLevelingComponent(stack).experience();
   }

   default void setRelicExperience(ItemStack stack, int experience) {
      this.setLevelingComponent(
         stack,
         this.getLevelingComponent(stack)
            .toBuilder()
            .experience(Math.clamp(experience, 0, this.getTotalRelicExperienceForLevel(this.getRelicLevel(stack) + 1)))
            .build()
      );
   }

   default boolean addRelicExperience(ItemStack stack, int amount) {
      return this.addRelicExperience(null, stack, amount);
   }

   default boolean addRelicExperience(@Nullable LivingEntity entity, ItemStack stack, int amount) {
      ExperienceAddEvent event = new ExperienceAddEvent(entity instanceof LivingEntity ? entity : null, stack, amount);
      NeoForge.EVENT_BUS.post(event);
      if (event.isCanceled()) {
         return false;
      }

      int currentExperience = this.getRelicExperience(stack);
      int currentLevel = this.getRelicLevel(stack);
      int toAdd = event.getAmount();
      if (toAdd == 0) {
         return false;
      }

      int resultLevel = currentLevel;
      int resultExperience = 0;

      for (int maxLevel = this.getLevelingData().getMaxLevel(); toAdd > 0 && resultLevel < maxLevel; currentExperience = 0) {
         int requiredExperience = this.getTotalRelicExperienceBetweenLevels(resultLevel, resultLevel + 1);
         int diff = requiredExperience - currentExperience;
         if (toAdd < diff) {
            resultExperience = currentExperience + toAdd;
            break;
         }

         toAdd -= diff;
         resultLevel++;
      }

      this.setRelicExperience(stack, resultExperience);
      if (currentLevel != resultLevel) {
         this.setRelicLevel(stack, resultLevel);
         this.addRelicLevelingPoints(stack, resultLevel - currentLevel);
      }

      return true;
   }

   default void spreadRelicExperience(@Nullable LivingEntity entity, ItemStack stack, int experience) {
      this.spreadRelicExperience(entity, stack, experience, 0.25);
   }

   default void spreadRelicExperience(@Nullable LivingEntity entity, ItemStack stack, int experience, double percentage) {
      boolean isMaxLevel = this.isRelicMaxLevel(stack);
      int toSpread = isMaxLevel ? 0 : (int)Math.ceil(experience * percentage);
      if (!isMaxLevel) {
         this.addRelicExperience(entity, stack, experience);
      }

      if (toSpread > 0 && entity != null) {
         List<ItemStack> relics = RegistryRegistry.RELIC_CONTAINER_REGISTRY
            .entrySet()
            .stream()
            .map(Entry::getValue)
            .flatMap(source -> source.gatherRelics().apply(entity).stream())
            .filter(entry -> entry.getItem() instanceof IRelicItem relicx && !relicx.isRelicMaxLevel(entry) && !stack.equals(entry))
            .toList();
         if (!relics.isEmpty()) {
            ItemStack relicStack = relics.get(entity.level().getRandom().nextInt(relics.size()));
            if (relicStack.getItem() instanceof IRelicItem relic) {
               relic.addRelicExperience(entity, relicStack, toSpread);
            }
         }
      }
   }

   default void dropRelicExperience(Level level, Vec3 pos, int amount) {
      if (amount > 0) {
         RandomSource random = level.getRandom();
         int orbs = Math.max(amount / RelicExperienceOrbEntity.getMaxExperience(), random.nextInt(amount) + 1);

         for (int i = 0; i < orbs; i++) {
            RelicExperienceOrbEntity orb = new RelicExperienceOrbEntity(
               (EntityType<? extends RelicExperienceOrbEntity>)EntityRegistry.RELIC_EXPERIENCE_ORB.get(), level
            );
            orb.setPos(pos);
            orb.setExperience(amount / orbs);
            orb.setDeltaMovement((-1.0F + 2.0F * random.nextFloat()) * 0.15F, 0.1F + random.nextFloat() * 0.2F, (-1.0F + 2.0F * random.nextFloat()) * 0.15F);
            level.addFreshEntity(orb);
         }
      }
   }

   default int getRelicExperienceLeftForLevelUp(ItemStack stack, int level) {
      int currentLevel = this.getRelicLevel(stack);
      return this.getTotalRelicExperienceBetweenLevels(currentLevel, level) - this.getRelicExperience(stack);
   }

   @Deprecated(forRemoval = true)
   default boolean isSomethingWrongWithLevelingPoints(ItemStack stack) {
      int current = this.getRelicLevelingPoints(stack);

      for (AbilityData data : this.getAbilitiesData().getAbilities().values()) {
         current += this.getAbilityComponent(stack, data.getId()).points() * data.getRequiredPoints();
      }

      return current != this.getRelicLevel(stack);
   }

   default int getTotalRelicExperienceBetweenLevels(int from, int to) {
      return this.getTotalRelicExperienceForLevel(to) - this.getTotalRelicExperienceForLevel(from);
   }

   default int getTotalRelicExperienceForLevel(int level) {
      if (level <= 0) {
         return 0;
      }

      LevelingData levelingData = this.getLevelingData();
      if (levelingData == null) {
         return 0;
      }

      int result = levelingData.getInitialCost();

      for (int i = 1; i < level; i++) {
         result += levelingData.getInitialCost() + levelingData.getStep() * i;
      }

      return result;
   }

   default int getRelicLevelFromExperience(int experience) {
      int result = 0;

      int amount;
      do {
         amount = this.getTotalRelicExperienceForLevel(++result);
      } while (amount <= experience);

      return result - 1;
   }

   default boolean isRelicMaxLevel(ItemStack stack) {
      return this.getRelicLevel(stack) >= this.getLevelingData().getMaxLevel();
   }

   default boolean isRelicMaxQuality(ItemStack stack) {
      return this.getRelicQuality(stack) >= this.getMaxQuality();
   }

   default boolean isRelicFlawless(ItemStack stack) {
      return this.isRelicMaxLevel(stack)
         && this.getAbilitiesData()
            .getAbilities()
            .keySet()
            .stream()
            .filter(ability -> this.isAbilityEnabled(stack, ability))
            .allMatch(ability -> this.isAbilityFlawless(stack, ability));
   }

   default boolean isAbilityMaxLevel(ItemStack stack, String ability) {
      return this.getAbilityLevel(stack, ability) >= this.getAbilityMaxLevel(stack, ability);
   }

   default boolean isAbilityMaxQuality(ItemStack stack, String ability) {
      return this.getAbilityQuality(stack, ability) >= this.getMaxQuality();
   }

   default boolean isAbilityFlawless(ItemStack stack, String ability) {
      return this.isAbilityUnlocked(stack, ability) && this.isAbilityMaxQuality(stack, ability);
   }

   default CastData getAbilityCastData(String ability) {
      return this.getAbilityData(ability).getCastData();
   }

   default Map<String, Pair<PredicateType, BiFunction<Player, ItemStack, Boolean>>> getAbilityPredicates(String ability) {
      return this.getAbilityCastData(ability).getPredicates();
   }

   default Map<String, BiFunction<Player, ItemStack, Boolean>> getAbilityPredicates(String ability, PredicateType type) {
      return this.getAbilityPredicates(ability)
         .entrySet()
         .stream()
         .filter(entry -> entry.getValue().getKey() == type)
         .collect(Collectors.toMap(Entry::getKey, entry -> (BiFunction<Player, ItemStack, Boolean>)entry.getValue().getValue()));
   }

   default boolean testAbilityPredicate(Player player, ItemStack stack, String ability, String predicate) {
      return (Boolean)((BiFunction)this.getAbilityPredicates(ability).get(predicate).getValue()).apply(player, stack);
   }

   default boolean testAbilityPredicates(Player player, ItemStack stack, String ability, PredicateType type) {
      for (Entry<String, BiFunction<Player, ItemStack, Boolean>> entry : this.getAbilityPredicates(ability, type).entrySet()) {
         if (!this.testAbilityPredicate(player, stack, ability, entry.getKey())) {
            return false;
         }
      }

      return true;
   }

   default DataComponent getDataComponent(ItemStack stack) {
      return (DataComponent)stack.getOrDefault(DataComponentRegistry.DATA, DataComponent.EMPTY);
   }

   default void setDataComponent(ItemStack stack, DataComponent component) {
      stack.set(DataComponentRegistry.DATA, component);
   }

   default LevelingComponent getLevelingComponent(ItemStack stack) {
      return this.getDataComponent(stack).leveling();
   }

   default void setLevelingComponent(ItemStack stack, LevelingComponent component) {
      this.setDataComponent(stack, this.getDataComponent(stack).toBuilder().leveling(component).build());
   }

   default AbilitiesComponent getAbilitiesComponent(ItemStack stack) {
      return this.getDataComponent(stack).abilities();
   }

   default void setAbilitiesComponent(ItemStack stack, AbilitiesComponent component) {
      this.setDataComponent(stack, this.getDataComponent(stack).toBuilder().abilities(component).build());
   }

   default AbilityComponent getAbilityComponent(ItemStack stack, String ability) {
      AbilitiesComponent abilitiesComponent = this.getAbilitiesComponent(stack);
      AbilityComponent abilityComponent = abilitiesComponent.abilities().get(ability);
      AbilityData abilityData = this.getAbilityData(ability);
      if (abilityComponent != null) {
         return abilityComponent;
      }

      if (abilityData != null) {
         AbilityComponent.AbilityComponentBuilder builder = AbilityComponent.EMPTY.toBuilder();
         if (abilityData.getCastData().getType() == CastType.TOGGLEABLE) {
            builder.extender(AbilityExtenderComponent.EMPTY.toBuilder().ticking(true).build());
         }

         if (this.isEnoughLevel(stack, ability)) {
            builder.lock(LockComponent.EMPTY.toBuilder().unlocks(this.getMaxLockUnlocks()).build());
         }

         abilityComponent = builder.build();
         this.setAbilitiesComponent(stack, abilitiesComponent.toBuilder().ability(ability, abilityComponent).build());
         return abilityComponent;
      } else {
         return null;
      }
   }

   default void setAbilityComponent(ItemStack stack, String ability, AbilityComponent component) {
      this.setAbilitiesComponent(stack, this.getAbilitiesComponent(stack).toBuilder().ability(ability, component).build());
   }

   default AbilityExtenderComponent getAbilityExtenderComponent(ItemStack stack, String ability) {
      return this.getAbilityComponent(stack, ability).extender();
   }

   default void setAbilityExtenderComponent(ItemStack stack, String ability, AbilityExtenderComponent component) {
      this.setAbilityComponent(stack, ability, this.getAbilityComponent(stack, ability).toBuilder().extender(component).build());
   }

   default LockComponent getLockComponent(ItemStack stack, String ability) {
      return this.getAbilityComponent(stack, ability).lock();
   }

   default void setLockComponent(ItemStack stack, String ability, LockComponent component) {
      this.setAbilityComponent(stack, ability, this.getAbilityComponent(stack, ability).toBuilder().lock(component).build());
   }

   default int getMaxLockUnlocks() {
      return 5;
   }

   default int getLockUnlocks(ItemStack stack, String ability) {
      return this.getLockComponent(stack, ability).unlocks();
   }

   default void setLockUnlocks(ItemStack stack, String ability, int unlocks) {
      this.setLockComponent(stack, ability, this.getLockComponent(stack, ability).toBuilder().unlocks(Mth.clamp(unlocks, 0, this.getMaxLockUnlocks())).build());
   }

   default void addLockUnlocks(ItemStack stack, String ability, int unlocks) {
      this.setLockUnlocks(stack, ability, this.getLockUnlocks(stack, ability) + unlocks);
   }

   default boolean isLockUnlocked(ItemStack stack, String ability) {
      return this.getLockUnlocks(stack, ability) >= this.getMaxLockUnlocks();
   }

   default ResearchComponent getResearchComponent(ItemStack stack, String ability) {
      return this.getAbilityComponent(stack, ability).research();
   }

   default void setResearchComponent(ItemStack stack, String ability, ResearchComponent component) {
      this.setAbilityComponent(stack, ability, this.getAbilityComponent(stack, ability).toBuilder().research(component).build());
   }

   default Multimap<Integer, Integer> getResearchLinks(ItemStack stack, String ability) {
      return this.getResearchComponent(stack, ability)
         .links()
         .entrySet()
         .stream()
         .collect(
            MultimapBuilder.hashKeys().arrayListValues()::build,
            (multimap, entry) -> multimap.putAll(Integer.parseInt(entry.getKey()), entry.getValue()),
            Multimap::putAll
         );
   }

   default void addResearchLink(ItemStack stack, String ability, int from, int to) {
      Multimap<Integer, Integer> links = this.getResearchLinks(stack, ability);
      links.put(from, to);
      this.setResearchComponent(
         stack,
         ability,
         this.getResearchComponent(stack, ability)
            .toBuilder()
            .links(
               links.asMap()
                  .entrySet()
                  .stream()
                  .collect(Collectors.toMap(entry -> String.valueOf(entry.getKey()), entry -> new ArrayList<>((Collection<? extends Integer>)entry.getValue())))
            )
            .build()
      );
   }

   default void removeResearchLink(ItemStack stack, String ability, int from, int to) {
      Multimap<Integer, Integer> links = this.getResearchLinks(stack, ability);
      links.remove(from, to);
      this.setResearchComponent(
         stack,
         ability,
         this.getResearchComponent(stack, ability)
            .toBuilder()
            .links(
               links.asMap()
                  .entrySet()
                  .stream()
                  .collect(Collectors.toMap(entry -> String.valueOf(entry.getKey()), entry -> new ArrayList<>((Collection<? extends Integer>)entry.getValue())))
            )
            .build()
      );
   }

   default boolean isAbilityResearched(ItemStack stack, String ability) {
      return this.getResearchData(ability).getStars().isEmpty() || this.getResearchComponent(stack, ability).researched();
   }

   default void setAbilityResearched(ItemStack stack, String ability, boolean researched) {
      this.setResearchComponent(stack, ability, this.getResearchComponent(stack, ability).toBuilder().researched(researched).build());
   }

   default Multimap<Integer, Integer> getCorrectResearchLinks(ItemStack stack, String ability) {
      Multimap<Integer, Integer> schema = this.getResearchData(ability).getLinks();
      Multimap<Integer, Integer> links = this.getResearchLinks(stack, ability);
      if (schema.isEmpty()) {
         return LinkedHashMultimap.create();
      }

      Set<Pair<Integer, Integer>> bidirectionalSchema = schema.entries()
         .stream()
         .flatMap(entry -> Stream.of(Pair.of((Integer)entry.getKey(), (Integer)entry.getValue()), Pair.of((Integer)entry.getValue(), (Integer)entry.getKey())))
         .collect(Collectors.toSet());
      return links.entries()
         .stream()
         .filter(
            entry -> bidirectionalSchema.contains(Pair.of((Integer)entry.getKey(), (Integer)entry.getValue()))
               || bidirectionalSchema.contains(Pair.of((Integer)entry.getValue(), (Integer)entry.getKey()))
         )
         .collect(LinkedHashMultimap::create, (map, entry) -> map.put((Integer)entry.getKey(), (Integer)entry.getValue()), Multimap::putAll);
   }

   default Multimap<Integer, Integer> getIncorrectResearchLinks(ItemStack stack, String ability) {
      Multimap<Integer, Integer> schema = this.getResearchData(ability).getLinks();
      Multimap<Integer, Integer> links = this.getResearchLinks(stack, ability);
      if (schema.isEmpty()) {
         return LinkedHashMultimap.create();
      }

      Set<Pair<Integer, Integer>> bidirectionalSchema = schema.entries()
         .stream()
         .flatMap(entry -> Stream.of(Pair.of((Integer)entry.getKey(), (Integer)entry.getValue()), Pair.of((Integer)entry.getValue(), (Integer)entry.getKey())))
         .collect(Collectors.toSet());
      return links.entries()
         .stream()
         .filter(
            entry -> !bidirectionalSchema.contains(Pair.of((Integer)entry.getKey(), (Integer)entry.getValue()))
               && !bidirectionalSchema.contains(Pair.of((Integer)entry.getValue(), (Integer)entry.getKey()))
         )
         .collect(LinkedHashMultimap::create, (map, entry) -> map.put((Integer)entry.getKey(), (Integer)entry.getValue()), Multimap::putAll);
   }

   default double testAbilityResearchPercentage(ItemStack stack, String ability) {
      Multimap<Integer, Integer> schema = this.getResearchData(ability).getLinks();
      Multimap<Integer, Integer> links = this.getResearchLinks(stack, ability);
      if (schema.isEmpty()) {
         return 0.0;
      }

      Set<Pair<Integer, Integer>> bidirectionalSchema = schema.entries()
         .stream()
         .flatMap(entry -> Stream.of(Pair.of((Integer)entry.getKey(), (Integer)entry.getValue()), Pair.of((Integer)entry.getValue(), (Integer)entry.getKey())))
         .collect(Collectors.toSet());
      long matchingLinks = links.entries()
         .stream()
         .filter(
            entry -> bidirectionalSchema.contains(Pair.of((Integer)entry.getKey(), (Integer)entry.getValue()))
               || bidirectionalSchema.contains(Pair.of((Integer)entry.getValue(), (Integer)entry.getKey()))
         )
         .count();
      return (double)matchingLinks / schema.size();
   }

   default boolean testAbilityResearch(ItemStack stack, String ability) {
      return this.testAbilityResearchPercentage(stack, ability) >= 1.0;
   }

   default int getResearchHintPlayerExperienceCost(String ability) {
      return 50;
   }

   default StatComponent getStatComponent(ItemStack stack, String ability, String stat) {
      AbilityComponent abilityComponent = this.getAbilityComponent(stack, ability);
      StatComponent statComponent = abilityComponent.stats().get(stat);
      StatData statData = this.getStatData(ability, stat);
      if (statComponent != null) {
         return statComponent;
      } else if (statData != null) {
         statComponent = StatComponent.EMPTY
            .toBuilder()
            .initialValue(
               MathUtils.round(
                  MathUtils.randomBetween(new Random(), (Double)statData.getInitialValue().getKey(), (Double)statData.getInitialValue().getValue()), 5
               )
            )
            .build();
         this.setAbilityComponent(stack, ability, abilityComponent.toBuilder().stat(stat, statComponent).build());
         return statComponent;
      } else {
         return null;
      }
   }

   default void setStatComponent(ItemStack stack, String ability, String stat, StatComponent component) {
      this.setAbilityComponent(stack, ability, this.getAbilityComponent(stack, ability).toBuilder().stat(stat, component).build());
   }

   default double getStatInitialValue(ItemStack stack, String ability, String stat) {
      return this.getStatComponent(stack, ability, stat).initialValue();
   }

   default void setStatInitialValue(ItemStack stack, String ability, String stat, double value) {
      this.setStatComponent(stack, ability, stat, this.getStatComponent(stack, ability, stat).toBuilder().initialValue(value).build());
   }

   default void addStatInitialValue(ItemStack stack, String ability, String stat, double value) {
      this.setStatInitialValue(stack, ability, stat, this.getStatInitialValue(stack, ability, stat) + value);
   }

   default int getAbilityLevel(ItemStack stack, String ability) {
      return this.getAbilityComponent(stack, ability).points();
   }

   default int getAbilityMaxLevel(ItemStack stack, String ability) {
      return this.getAbilityData(ability).getMaxLevel();
   }

   default void setAbilityLevel(ItemStack stack, String ability, int points) {
      this.setAbilityComponent(stack, ability, this.getAbilityComponent(stack, ability).toBuilder().points(points).build());
   }

   default void addAbilityLevel(ItemStack stack, String ability, int points) {
      this.setAbilityLevel(stack, ability, this.getAbilityLevel(stack, ability) + points);
   }

   default AbilityComponent randomizeAbilityStats(ItemStack stack, String ability, int luck) {
      Map<String, StatData> stats = this.getAbilityData(ability).getStats();
      Random random = new Random();

      double targetQuality;
      do {
         int maxQuality = this.getMaxQuality();
         int maxLuck = this.getMaxLuck();
         double randomValue = random.nextDouble() * 2.0 - 1.0;
         double modifier = this.getLuckModifier();
         double bias = (luck - maxLuck / 2.0) / maxLuck * modifier;
         double biasedValue = Math.tanh(randomValue + bias);
         double weightedRandom = Math.floor((biasedValue + 1.0) / 2.0 * (maxQuality + 1.0));
         targetQuality = Mth.clamp(weightedRandom, 0.0, maxQuality);
      } while (targetQuality == this.getAbilityQuality(stack, ability));

      double sumQuality = 0.0;
      Map<String, Double> generatedQualities = new HashMap<>();

      for (String stat : stats.keySet()) {
         double randomQuality = MathUtils.randomBetween(random, 0, this.getMaxQuality());
         generatedQualities.put(stat, randomQuality);
         sumQuality += randomQuality;
      }

      double currentAverageQuality = sumQuality / stats.size();

      while (Math.abs(currentAverageQuality - targetQuality) > 0.01) {
         if (currentAverageQuality < targetQuality) {
            String minStat = generatedQualities.entrySet().stream().min(Entry.comparingByValue()).get().getKey();
            double increment = Math.min((targetQuality - currentAverageQuality) * stats.size(), this.getMaxQuality() - generatedQualities.get(minStat));
            generatedQualities.put(minStat, generatedQualities.get(minStat) + increment);
         } else if (currentAverageQuality > targetQuality) {
            String maxStat = generatedQualities.entrySet().stream().max(Entry.comparingByValue()).get().getKey();
            double decrement = Math.min((currentAverageQuality - targetQuality) * stats.size(), generatedQualities.get(maxStat));
            generatedQualities.put(maxStat, generatedQualities.get(maxStat) - decrement);
         }

         sumQuality = generatedQualities.values().stream().mapToDouble(Double::doubleValue).sum();
         currentAverageQuality = sumQuality / stats.size();
      }

      for (Entry<String, Double> entry : generatedQualities.entrySet()) {
         this.randomizeStat(stack, ability, entry.getKey(), (int)Math.round(entry.getValue()));
      }

      return this.getAbilityComponent(stack, ability);
   }

   default StatComponent randomizeStat(ItemStack stack, String ability, String stat, int quality) {
      StatData entry = this.getStatData(ability, stat);
      double minValue = (Double)entry.getInitialValue().getKey();
      double maxValue = (Double)entry.getInitialValue().getValue();
      double diff = maxValue - minValue;
      double result = minValue + diff * ((double)quality / this.getMaxQuality());
      this.setStatInitialValue(stack, ability, stat, result);
      return this.getStatComponent(stack, ability, stat);
   }

   default StatComponent randomizeStat(ItemStack stack, String ability, String stat) {
      return this.randomizeStat(stack, ability, stat, new Random().nextInt(this.getMaxQuality() + 1));
   }

   default double getRelativeStatValue(String ability, String stat, double value, int points) {
      StatData data = this.getStatData(ability, stat);
      double result = 0.0;
      if (data == null) {
         return result;
      }

      Double step = (Double)data.getUpgradeModifier().getValue();
      switch ((UpgradeOperation)data.getUpgradeModifier().getKey()) {
         case ADD:
            result = value + points * step;
            break;
         case MULTIPLY_BASE:
            result = value + value * step * points;
            break;
         case MULTIPLY_TOTAL:
            result = value * Math.pow(step + 1.0, points);
      }

      Pair<Double, Double> threshold = data.getThresholdValue();
      return MathUtils.round(Mth.clamp(result, (Double)threshold.getKey(), (Double)threshold.getValue()), 5);
   }

   default double getStatValue(ItemStack stack, String ability, String stat, int points) {
      return this.getRelativeStatValue(ability, stat, this.getStatInitialValue(stack, ability, stat), points);
   }

   default double getStatValue(ItemStack stack, String ability, String stat) {
      return this.getStatValue(stack, ability, stat, this.getAbilityLevel(stack, ability));
   }

   default boolean isEnoughLevel(ItemStack stack, String ability) {
      return this.getRelicLevel(stack) >= this.getAbilityData(ability).getRequiredLevel();
   }

   default boolean isAbilityEnabled(ItemStack stack, String ability) {
      return true;
   }

   default boolean isAbilityUnlocked(ItemStack stack, String ability) {
      return this.isAbilityEnabled(stack, ability)
         && this.isEnoughLevel(stack, ability)
         && this.isLockUnlocked(stack, ability)
         && this.isAbilityResearched(stack, ability);
   }

   default boolean hasUnlockedUpgradeableAbility(ItemStack stack) {
      return this.getAbilitiesData()
         .getAbilities()
         .keySet()
         .stream()
         .anyMatch(ability -> this.canBeUpgraded(stack, ability) && this.isAbilityUnlocked(stack, ability));
   }

   default boolean hasUnlockedAbility(ItemStack stack) {
      return this.getAbilitiesData().getAbilities().keySet().stream().anyMatch(ability -> this.isAbilityUnlocked(stack, ability));
   }

   default boolean canPlayerUseAbility(Player player, ItemStack stack, String ability) {
      return this.isAbilityUnlocked(stack, ability)
         && this.testAbilityPredicates(player, stack, ability, PredicateType.CAST)
         && this.getAbilityCooldown(stack, ability) <= 0;
   }

   default boolean canPlayerSeeAbility(Player player, ItemStack stack, String ability) {
      return this.testAbilityPredicates(player, stack, ability, PredicateType.VISIBILITY);
   }

   default boolean mayUnlock(ItemStack stack, String ability) {
      return this.isEnoughLevel(stack, ability) && !this.isLockUnlocked(stack, ability);
   }

   default boolean mayResearch(ItemStack stack, String ability) {
      return this.isEnoughLevel(stack, ability) && this.isLockUnlocked(stack, ability) && !this.isAbilityResearched(stack, ability);
   }

   default int getUpgradePlayerExperienceCost(ItemStack stack, String ability) {
      return (this.getAbilityLevel(stack, ability) + 1) * 50;
   }

   default boolean canBeUpgraded(ItemStack stack, String ability) {
      return this.getAbilityMaxLevel(stack, ability) > 0 && !this.getAbilityData(ability).getStats().isEmpty();
   }

   default boolean mayUpgrade(ItemStack stack, String ability) {
      AbilityData entry = this.getAbilityData(ability);
      return this.canBeUpgraded(stack, ability)
         && !this.isAbilityMaxLevel(stack, ability)
         && this.getRelicLevelingPoints(stack) >= entry.getRequiredPoints()
         && this.isAbilityUnlocked(stack, ability);
   }

   default boolean mayPlayerUpgrade(Player player, ItemStack stack, String ability) {
      return this.mayUpgrade(stack, ability) && EntityUtils.getPlayerTotalExperience(player) >= this.getUpgradePlayerExperienceCost(stack, ability);
   }

   default boolean upgrade(Player player, ItemStack stack, String ability) {
      if (!this.mayPlayerUpgrade(player, stack, ability)) {
         return false;
      }

      player.giveExperiencePoints(-this.getUpgradePlayerExperienceCost(stack, ability));
      this.setAbilityLevel(stack, ability, this.getAbilityLevel(stack, ability) + 1);
      this.addRelicLevelingPoints(stack, -this.getAbilityData(ability).getRequiredPoints());
      return true;
   }

   default int getRerollPlayerExperienceCost(ItemStack stack, String ability) {
      return this.getRelicLuck(stack) * 5 + 50;
   }

   default boolean mayReroll(ItemStack stack, String ability) {
      return !this.getAbilityData(ability).getStats().isEmpty() && this.isAbilityUnlocked(stack, ability);
   }

   default boolean mayPlayerReroll(Player player, ItemStack stack, String ability) {
      return this.mayReroll(stack, ability) && EntityUtils.getPlayerTotalExperience(player) >= this.getRerollPlayerExperienceCost(stack, ability);
   }

   default boolean reroll(Player player, ItemStack stack, String ability) {
      if (!this.mayPlayerReroll(player, stack, ability)) {
         return false;
      }

      player.giveExperiencePoints(-this.getRerollPlayerExperienceCost(stack, ability));
      int prevQuality = this.getAbilityQuality(stack, ability);
      this.randomizeAbilityStats(stack, ability, this.getRelicLuck(stack));
      int newQuality = this.getAbilityQuality(stack, ability);
      if (newQuality < prevQuality) {
         this.addRelicLuck(stack, (int)Math.ceil((prevQuality - newQuality) / 2.0));
      }

      return true;
   }

   default int getResetPlayerExperienceCost(ItemStack stack, String ability) {
      return this.getAbilityLevel(stack, ability) * 250;
   }

   default boolean mayReset(ItemStack stack, String ability) {
      return this.getAbilityLevel(stack, ability) > 0 && this.isAbilityUnlocked(stack, ability);
   }

   default boolean mayPlayerReset(Player player, ItemStack stack, String ability) {
      return !this.getAbilityData(ability).getStats().isEmpty()
         && this.mayReset(stack, ability)
         && EntityUtils.getPlayerTotalExperience(player) >= this.getResetPlayerExperienceCost(stack, ability);
   }

   default boolean reset(Player player, ItemStack stack, String ability) {
      if (!this.mayPlayerReset(player, stack, ability)) {
         return false;
      }

      player.giveExperiencePoints(-this.getResetPlayerExperienceCost(stack, ability));
      this.addRelicLevelingPoints(stack, this.getAbilityLevel(stack, ability) * this.getAbilityData(ability).getRequiredPoints());
      this.setAbilityLevel(stack, ability, 0);
      return true;
   }

   default int getAbilityCooldownCap(ItemStack stack, String ability) {
      return this.getAbilityExtenderComponent(stack, ability).cooldownCap();
   }

   default void setAbilityCooldownCap(ItemStack stack, String ability, int amount) {
      this.setAbilityExtenderComponent(stack, ability, this.getAbilityExtenderComponent(stack, ability).toBuilder().cooldownCap(amount).build());
   }

   default int getAbilityCooldown(ItemStack stack, String ability) {
      return this.getAbilityExtenderComponent(stack, ability).cooldown();
   }

   default void setAbilityCooldown(ItemStack stack, String ability, int amount) {
      this.setAbilityExtenderComponent(
         stack, ability, this.getAbilityExtenderComponent(stack, ability).toBuilder().cooldownCap(amount).cooldown(amount).build()
      );
   }

   default void addAbilityCooldown(ItemStack stack, String ability, int amount) {
      this.setAbilityExtenderComponent(
         stack, ability, this.getAbilityExtenderComponent(stack, ability).toBuilder().cooldown(this.getAbilityCooldown(stack, ability) + amount).build()
      );
   }

   default void setAbilityTicking(ItemStack stack, String ability, boolean ticking) {
      this.setAbilityExtenderComponent(stack, ability, this.getAbilityExtenderComponent(stack, ability).toBuilder().ticking(ticking).build());
   }

   default boolean isAbilityTicking(ItemStack stack, String ability) {
      return this.isAbilityUnlocked(stack, ability) && this.getAbilityExtenderComponent(stack, ability).ticking();
   }

   default boolean isAbilityOnCooldown(ItemStack stack, String ability) {
      return this.getAbilityCooldown(stack, ability) > 0;
   }

   default boolean isAbilityUpgradeEnabled(ItemStack stack, String ability) {
      return this.isAbilityUnlocked(stack, ability) && !this.getAbilityData(ability).getStats().isEmpty();
   }

   default boolean isAbilityRerollEnabled(ItemStack stack, String ability) {
      return this.isAbilityUnlocked(stack, ability) && !this.getAbilityData(ability).getStats().isEmpty();
   }

   default boolean isAbilityResetEnabled(ItemStack stack, String ability) {
      return this.isAbilityUnlocked(stack, ability) && !this.getAbilityData(ability).getStats().isEmpty();
   }
}
