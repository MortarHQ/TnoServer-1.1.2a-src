package io.github.Memoires.mysticism.effect.ability;

import io.github.Memoires.mysticism.config.MysticismGeneralConfig;
import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.manasmods.manascore.attribute.api.ManasCoreAttributes;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import java.awt.Color;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.network.protocol.game.ClientboundPlayerLookAtPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class FixationEffect extends TensuraMobEffect {
   protected static final UniqueMysticismSkillConfig.Coalescence CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(
         UniqueMysticismSkillConfig.class
      ))
      .Coalescence;
   protected static final MysticismGeneralConfig.General GENERAL = ((MysticismGeneralConfig)ConfigRegistry.getConfig(MysticismGeneralConfig.class)).General;
   public static final ResourceLocation FIXATION = ResourceLocation.fromNamespaceAndPath("mysticism", "fixation");
   private static final Map<Integer, FixationEffect.Rot> LOCKS = new ConcurrentHashMap<>();

   public FixationEffect() {
      super(MobEffectCategory.NEUTRAL, new Color(25, 70, 255, 255).getRGB());
      this.addAttributeModifier(Attributes.ATTACK_SPEED, FIXATION, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.MOVEMENT_SPEED, FIXATION, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.JUMP_STRENGTH, FIXATION, -10.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.BLOCK_BREAK_SPEED, FIXATION, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(ManasCoreAttributes.SWIM_SPEED_MULTIPLIER, FIXATION, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, FIXATION, 1.0, Operation.ADD_VALUE);
   }

   public boolean applyEffectTick(LivingEntity e, int amplifier) {
      if (e.level().isClientSide) {
         return true;
      }

      if (e instanceof Player p && p.getAbilities().mayfly && p.getAbilities().flying) {
         p.getAbilities().setFlyingSpeed(0.0F);
      }

      FixationEffect.Rot rot = LOCKS.computeIfAbsent(e.getId(), id -> new FixationEffect.Rot(e.getYRot(), e.getXRot()));
      float yaw = rot.yaw();
      float pitch = rot.pitch();
      e.setYRot(yaw);
      e.setXRot(pitch);
      e.setYHeadRot(yaw);
      e.yBodyRot = yaw;
      e.yHeadRotO = yaw;
      e.yBodyRotO = yaw;
      e.xRotO = pitch;
      e.yRotO = yaw;
      if (e instanceof ServerPlayer sp) {
         Vec3 eye = sp.getEyePosition();
         Vec3 look = eye.add(Vec3.directionFromRotation(pitch, yaw));
         sp.connection.send(new ClientboundPlayerLookAtPacket(Anchor.EYES, look.x, look.y, look.z));
      }

      if (e instanceof ServerPlayer sp) {
         sp.getAbilities().flying = false;
      }

      return true;
   }

   public void onEffectRemoved(LivingEntity entity, MobEffectInstance instance) {
      if (entity instanceof Player p && p.getAbilities().mayfly) {
         p.getAbilities().setFlyingSpeed((float)GENERAL.flightSpeed);
      }
   }

   public static boolean tryApply(LivingEntity e, MobEffectInstance instance) {
      if (e.hasEffect(instance.getEffect())) {
         return false;
      }

      Optional<ManasSkillInstance> antiSkillInstance = SkillAPI.getSkillsFrom(e).getSkill((ManasSkill)UniqueSkills.ANTI_SKILL.get());
      if (antiSkillInstance.isPresent()) {
         return false;
      }

      e.addEffect(instance);
      return true;
   }

   public record Rot(float yaw, float pitch) {
   }
}
