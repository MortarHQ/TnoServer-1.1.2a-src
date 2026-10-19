package io.github.Memoires.mysticism.network.c2s;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.registry.skill.IntrinsicMysticismSkills;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.Memoires.mysticism.registry.sound.MysticismSoundEvents;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.skill.CommonSkills;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import java.util.Optional;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public record RequestButcheringEffects(
   int entityId, boolean head, boolean torso, boolean leftArm, boolean rightArm, boolean leftLeg, boolean rightLeg, int targetId
) implements CustomPacketPayload {
   private static final UniqueMysticismSkillConfig.Butcher CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Butcher;
   public static final Type<RequestButcheringEffects> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("mysticism", "request_butchering_effects"));
   public static final StreamCodec<FriendlyByteBuf, RequestButcheringEffects> STREAM_CODEC = CustomPacketPayload.codec(
      RequestButcheringEffects::encode, RequestButcheringEffects::new
   );

   public RequestButcheringEffects(FriendlyByteBuf buf) {
      this(buf.readInt(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readInt());
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeInt(this.entityId);
      buf.writeBoolean(this.head);
      buf.writeBoolean(this.torso);
      buf.writeBoolean(this.leftArm);
      buf.writeBoolean(this.rightArm);
      buf.writeBoolean(this.leftLeg);
      buf.writeBoolean(this.rightLeg);
      buf.writeInt(this.targetId);
   }

   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.SERVER) {
         context.queue(
            () -> {
               Player player = context.getPlayer();
               if (player != null) {
                  Optional<ManasSkillInstance> skillInstance = SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)UniqueMysticismSkills.BUTCHER.get());
                  if (skillInstance.isPresent()) {
                     ManasSkillInstance instance = skillInstance.get();
                     Entity entity = player.level().getEntity(this.targetId);
                     if (instance.onCoolDown(2)) {
                        return;
                     }

                     if (!(entity instanceof LivingEntity livingTarget)) {
                        return;
                     }
                     int duration = switch (this.getRegenerationLevel(livingTarget)) {
                        case 1 -> 2400;
                        case 2 -> 1200;
                        case 3 -> 300;
                        default -> -1;
                     };

                     float damageMultiplier = switch (this.getRegenerationLevel(livingTarget)) {
                        case 1 -> 0.9F;
                        case 2 -> 0.75F;
                        case 3 -> 0.5F;
                        default -> 1.0F;
                     };
                     if (this.head) {
                        livingTarget.addEffect(
                           new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.INSANITY), duration, 0, true, true, true), player
                        );
                        livingTarget.addEffect(
                           new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.TRUE_BLINDNESS), duration, 0, true, true, true), player
                        );
                        livingTarget.addEffect(new MobEffectInstance(MobEffects.CONFUSION, duration, 0, true, true, true), player);
                     }

                     if (this.torso) {
                        ResourceKey<DamageType> type = TensuraDamageTypes.HEART_EAT;
                        DamageSource source = ((TensuraSkill)instance.getSkill()).createSource(instance, player, type, 2);
                        livingTarget.hurt(source, livingTarget.getMaxHealth() * damageMultiplier);
                        player.heal(livingTarget.getHealth() * damageMultiplier);
                     }

                     if (this.leftArm || this.rightArm) {
                        int amplifier = this.leftArm && this.rightArm ? 1 : 0;
                        livingTarget.addEffect(
                           new MobEffectInstance(TensuraMobEffects.getReference(MysticismMobEffects.SEVERED_ARM), duration, amplifier, true, true, true),
                           player
                        );
                     }

                     if (this.leftLeg || this.rightLeg) {
                        int amplifier = this.leftLeg && this.rightLeg ? 1 : 0;
                        livingTarget.addEffect(
                           new MobEffectInstance(TensuraMobEffects.getReference(MysticismMobEffects.SEVERED_LEG), duration, amplifier, true, true, true),
                           player
                        );
                     }

                     instance.addMasteryPoint(player);
                     instance.setCoolDown(CONFIG.血淋林的爱Cooldown, 2);
                     livingTarget.level()
                        .playSound(
                           null,
                           livingTarget.getX(),
                           livingTarget.getY(),
                           livingTarget.getZ(),
                           (SoundEvent)MysticismSoundEvents.BUTCHER_GUI_CONFIRM.get(),
                           SoundSource.PLAYERS,
                           1.0F,
                           1.0F
                        );
                     instance.getOrCreateTag().remove("LastHitTarget");
                     instance.getOrCreateTag().putInt("BloodyLoveCharges", instance.getOrCreateTag().getInt("BloodyLoveCharges") - 1);
                     instance.getOrCreateTag().getCompound("Marks").remove(livingTarget.getUUID().toString());
                  }
               }
            }
         );
      }
   }

   int getRegenerationLevel(LivingEntity target) {
      if (!SkillUtils.isSkillToggled(target, (ManasSkill)ExtraSkills.INFINITE_REGENERATION.get())
         && (
            !SkillUtils.isSkillToggled(target, (ManasSkill)IntrinsicMysticismSkills.TENACITY.get())
               || !SkillUtils.isSkillMastered(target, (ManasSkill)IntrinsicMysticismSkills.TENACITY.get())
         )) {
         if (SkillUtils.isSkillToggled(target, (ManasSkill)ExtraSkills.ULTRASPEED_REGENERATION.get())
            || SkillUtils.isSkillToggled(target, (ManasSkill)UniqueSkills.SURVIVOR.get())
            || SkillUtils.isSkillToggled(target, (ManasSkill)IntrinsicMysticismSkills.TENACITY.get())) {
            return 2;
         } else {
            return SkillUtils.isSkillToggled(target, (ManasSkill)CommonSkills.SELF_REGENERATION.get()) ? 1 : 0;
         }
      } else {
         return 3;
      }
   }
}
