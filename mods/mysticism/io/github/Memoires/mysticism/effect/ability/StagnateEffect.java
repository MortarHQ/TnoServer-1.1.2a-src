package io.github.Memoires.mysticism.effect.ability;

import io.github.Memoires.mysticism.storage.MysticismStorages;
import io.github.manasmods.manascore.attribute.api.ManasCoreAttributes;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import java.awt.Color;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;

public class StagnateEffect extends TensuraMobEffect {
   protected static final ResourceLocation STAGNATE = ResourceLocation.fromNamespaceAndPath("mysticism", "stagnate");

   public StagnateEffect() {
      super(MobEffectCategory.BENEFICIAL, new Color(255, 181, 0, 255).getRGB());
      this.addAttributeModifier(Attributes.MOVEMENT_SPEED, STAGNATE, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.ATTACK_DAMAGE, STAGNATE, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.ATTACK_SPEED, STAGNATE, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.JUMP_STRENGTH, STAGNATE, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE, STAGNATE, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.JUMP_STRENGTH, STAGNATE, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.FOLLOW_RANGE, STAGNATE, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(ManasCoreAttributes.SWIM_SPEED_MULTIPLIER, STAGNATE, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(ManasCoreAttributes.GLIDE_SPEED_MULTIPLIER, STAGNATE, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
   }

   public void onEffectRemoved(LivingEntity entity, MobEffectInstance instance) {
      if (entity instanceof Mob mob) {
         mob.setNoAi(false);
      }
   }

   public void onEffectAdded(LivingEntity livingEntity, int i) {
      if (livingEntity instanceof Player player) {
         MysticismStorages.getPlayerDataFrom(player).setPosition(player.position());
      }
   }
}
