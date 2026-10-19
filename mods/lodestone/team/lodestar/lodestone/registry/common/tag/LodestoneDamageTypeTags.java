package team.lodestar.lodestone.registry.common.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public class LodestoneDamageTypeTags {
   public static final TagKey<DamageType> IS_MAGIC = common("is_magic");
   public static final TagKey<DamageType> CAN_TRIGGER_MAGIC = common("can_trigger_magic_damage");
   public static final TagKey<DamageType> IGNORES_MAGIC_ATTACK_COOLDOWN_SCALAR = common("ignores_magic_attack_cooldown_scalar");

   public static TagKey<DamageType> common(String path) {
      return TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("c", path));
   }
}
