package com.bobmowzie.mowziesmobs.server.capability;

import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoModel;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoPlayer;
import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.entity.MowzieGeckoEntity;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;

public class AbilityData implements INBTSerializable<CompoundTag> {
   SortedMap<AbilityType<?, ?>, Ability<?>> abilityInstances = new TreeMap<>();
   Ability<?> activeAbility = null;
   Map<String, Tag> nbtMap = new HashMap<>();
   private boolean needsReinstancing = true;

   public void instanceAbilities(LivingEntity entity) {
      this.setActiveAbility(null);

      for (AbilityType<? extends LivingEntity, ?> abilityType : this.getAbilityTypesOnEntity(entity)) {
         Ability<?> ability = abilityType.makeInstance(entity);
         this.abilityInstances.put(abilityType, ability);
         if (this.nbtMap.containsKey(abilityType.getName())) {
            ability.readNBT(this.nbtMap.get(abilityType.getName()));
         }
      }
   }

   public void activateAbility(LivingEntity entity, AbilityType<?, ?> abilityType) {
      Ability<?> ability = this.abilityInstances.get(abilityType);
      if (ability != null) {
         boolean tryResult = ability.tryAbility();
         if (tryResult) {
            ability.start();
         }
      } else {
         System.out.println("Ability<?>" + abilityType.toString() + " does not exist on mob " + entity.getClass().getSimpleName());
      }
   }

   public void tick(LivingEntity entity) {
      if (this.needsReinstancing) {
         this.instanceAbilities(entity);
         this.needsReinstancing = false;
      }

      for (Ability<?> ability : this.abilityInstances.values()) {
         ability.tick();
      }
   }

   public AbilityType<?, ?>[] getAbilityTypesOnEntity(LivingEntity entity) {
      if (entity instanceof Player) {
         return AbilityHandler.PLAYER_ABILITIES;
      } else {
         return entity instanceof MowzieGeckoEntity ? ((MowzieGeckoEntity)entity).getAbilities() : new AbilityType[0];
      }
   }

   public Map<AbilityType<?, ?>, Ability<?>> getAbilityMap() {
      return this.abilityInstances;
   }

   public Ability<?> getAbilityFromType(AbilityType<?, ?> abilityType) {
      return this.abilityInstances.get(abilityType);
   }

   public Ability<?> getActiveAbility() {
      return this.activeAbility;
   }

   public void setActiveAbility(Ability<?> activeAbility) {
      if (this.getActiveAbility() != null && this.getActiveAbility().isUsing()) {
         this.getActiveAbility().interrupt();
      }

      this.activeAbility = activeAbility;
   }

   public Collection<Ability<?>> getAbilities() {
      return this.abilityInstances.values();
   }

   public boolean attackingPrevented() {
      return this.getActiveAbility() != null && this.getActiveAbility().preventsAttacking();
   }

   public boolean blockBreakingBuildingPrevented() {
      return this.getActiveAbility() != null && this.getActiveAbility().preventsBlockBreakingBuilding();
   }

   public boolean interactingPrevented() {
      return this.getActiveAbility() != null && this.getActiveAbility().preventsInteracting();
   }

   public boolean itemUsePrevented(ItemStack itemStack) {
      return this.getActiveAbility() != null && this.getActiveAbility().preventsItemUse(itemStack);
   }

   public <E extends GeoEntity> PlayState animationPredicate(AnimationState<E> e, GeckoPlayer.Perspective perspective) {
      return this.getActiveAbility().animationPredicate(e, perspective);
   }

   public void codeAnimations(MowzieGeoModel<? extends GeoEntity> model, float partialTick) {
      this.getActiveAbility().codeAnimations(model, partialTick);
   }

   public CompoundTag serializeNBT(@NotNull Provider lookup) {
      CompoundTag compound = new CompoundTag();

      for (Entry<AbilityType<?, ?>, Ability<?>> abilityEntry : this.getAbilityMap().entrySet()) {
         CompoundTag nbt = abilityEntry.getValue().writeNBT();
         if (!nbt.isEmpty()) {
            compound.put(abilityEntry.getKey().getName(), nbt);
         }
      }

      return compound;
   }

   public void deserializeNBT(@NotNull Provider lookup, CompoundTag nbt) {
      this.nbtMap.clear();

      for (String abilityName : nbt.getAllKeys()) {
         this.nbtMap.put(abilityName, nbt.get(abilityName));
      }

      this.needsReinstancing = true;
   }
}
