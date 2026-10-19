package fuzs.illagerinvasion.handler;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.config.ServerConfig;
import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import fuzs.puzzleslib.api.event.v1.data.DefaultedFloat;
import fuzs.puzzleslib.api.event.v1.data.DefaultedInt;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class PlatinumTrimHandler {
   public static final String INSIGHT_TRANSLATION_KEY = "trim_material.illagerinvasion.platinum.effect.insight";
   public static final String AGILITY_TRANSLATION_KEY = "trim_material.illagerinvasion.platinum.effect.agility";
   public static final String ENDURANCE_TRANSLATION_KEY = "trim_material.illagerinvasion.platinum.effect.endurance";
   public static final String FEATHERWEIGHT_TRANSLATION_KEY = "trim_material.illagerinvasion.platinum.effect.featherweight";
   public static final Map<Type, String> PLATINUM_TRIM_TRANSLATION_KEYS = Collections.unmodifiableMap(
      (Map<? extends Type, ? extends String>)Util.make(new EnumMap(Type.class), enumMap -> {
         enumMap.put(Type.BOOTS, "trim_material.illagerinvasion.platinum.effect.featherweight");
         enumMap.put(Type.LEGGINGS, "trim_material.illagerinvasion.platinum.effect.endurance");
         enumMap.put(Type.CHESTPLATE, "trim_material.illagerinvasion.platinum.effect.agility");
         enumMap.put(Type.HELMET, "trim_material.illagerinvasion.platinum.effect.insight");
      })
   );

   public static EventResult onBreakSpeed(Player player, BlockState state, DefaultedFloat breakSpeed) {
      if (player.getInventory().getDestroySpeed(state) == 1.0F && hasPlatinumTrim(player, EquipmentSlot.CHEST)) {
         breakSpeed.mapFloat(f -> f * 1.5F);
      }

      return EventResult.PASS;
   }

   public static EventResult onLivingExperienceDrop(LivingEntity entity, @Nullable Player attackingPlayer, DefaultedInt droppedExperience) {
      if (attackingPlayer != null && hasPlatinumTrim(attackingPlayer, EquipmentSlot.HEAD)) {
         droppedExperience.mapDefaultInt(i -> i + (int)Math.max(1.0F, i * 0.2F));
      }

      return EventResult.PASS;
   }

   public static EventResult onFarmlandTrample(Level level, BlockPos pos, BlockState state, float fallDistance, Entity entity) {
      return entity instanceof Player player && hasPlatinumTrim(player, EquipmentSlot.FEET) ? EventResult.INTERRUPT : EventResult.PASS;
   }

   public static boolean hasPlatinumTrim(Player player, EquipmentSlot equipmentSlot) {
      return getPlatinumTrim(player.level(), player.getItemBySlot(equipmentSlot)).isPresent();
   }

   public static Optional<ArmorTrim> getPlatinumTrim(Level level, ItemStack itemStack) {
      if (!((ServerConfig)IllagerInvasion.CONFIG.get(ServerConfig.class)).platinumTrimEffects) {
         return Optional.empty();
      }

      ArmorTrim armorTrim = (ArmorTrim)itemStack.get(DataComponents.TRIM);
      return armorTrim != null && armorTrim.material().is(ModRegistry.PLATINUM_TRIM_MATERIAL) ? Optional.of(armorTrim) : Optional.empty();
   }
}
