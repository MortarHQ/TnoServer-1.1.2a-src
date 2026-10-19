package com.trbeyond.tensura_better_subs.network.c2s;

import com.trbeyond.tensura_better_subs.BetterSubs;
import com.trbeyond.tensura_better_subs.ability.skill.IFoodChain;
import com.trbeyond.tensura_better_subs.ability.skill.SkillGranting;
import com.trbeyond.tensura_better_subs.entity.IExpandedSkillAcquisition;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import io.github.manasmods.tensura.event.TensuraSkillEvents;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillPlunderEvent;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.HoverEvent.Action;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;

public record SubordinateSkillGrantingPacket(UUID entityUUID, ResourceLocation ability, ResourceLocation skillGrantingAbility) implements CustomPacketPayload {
   public static final Type<SubordinateSkillGrantingPacket> TYPE = new Type(
      ResourceLocation.fromNamespaceAndPath("tensura_better_subs", "subordinate_skill_grant")
   );
   public static final StreamCodec<FriendlyByteBuf, SubordinateSkillGrantingPacket> STREAM_CODEC = CustomPacketPayload.codec(
      SubordinateSkillGrantingPacket::encode, SubordinateSkillGrantingPacket::new
   );

   public SubordinateSkillGrantingPacket(FriendlyByteBuf buf) {
      this(buf.readUUID(), buf.readResourceLocation(), buf.readResourceLocation());
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeUUID(this.entityUUID);
      buf.writeResourceLocation(this.ability);
      buf.writeResourceLocation(this.skillGrantingAbility);
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.SERVER) {
         context.queue(
            () -> {
               ServerPlayer player = (ServerPlayer)context.getPlayer();
               if (player != null) {
                  SkillStorage storage = SkillAPI.getSkillsFrom(player);
                  Entity entity = player.serverLevel().getEntity(this.entityUUID);
                  if (player.getType().is(TensuraEntityTags.NO_SKILL_PLUNDER)) {
                     return;
                  }

                  if (!(entity instanceof LivingEntity subordinate)) {
                     return;
                  }

                  if (!SubordinateHelper.isSubordinate(player, subordinate)) {
                     return;
                  }

                  if (BetterSubs.getSkillExpansionFrom(subordinate) == null) {
                     return;
                  }

                  Optional<ManasSkillInstance> optionalGrantingAbility = storage.getSkill(this.skillGrantingAbility);
                  Optional<ManasSkillInstance> optionalAbility = storage.getSkill(this.ability);
                  if (optionalGrantingAbility.isEmpty() || optionalAbility.isEmpty()) {
                     return;
                  }

                  ManasSkillInstance skillGrantingInstance = optionalGrantingAbility.get();
                  ManasSkillInstance abilityInstance = optionalAbility.get();
                  if (!(skillGrantingInstance.getSkill() instanceof IFoodChain foodChain)) {
                     return;
                  }

                  if (!SkillGranting.isValidSkillInstance(abilityInstance, player)) {
                     return;
                  }

                  if (skillGrantingInstance.onCoolDown(foodChain.getProvideMode())) {
                     return;
                  }

                  Changeable<ManasSkill> changeable = Changeable.of(abilityInstance.getSkill());
                  if (((SkillPlunderEvent)TensuraSkillEvents.SKILL_PLUNDER.invoker()).plunder(player, subordinate, false, changeable).isFalse()) {
                     return;
                  }

                  abilityInstance = (ManasSkillInstance)storage.getSkill((ManasSkill)changeable.get()).orElse(null);
                  if (abilityInstance == null) {
                     return;
                  }

                  double grantingCost = SkillGranting.getAcquisitionCost(abilityInstance, player);
                  double ownerMagiculeCost = grantingCost * SkillGranting.CONFIG.ownerCostMultiplier;
                  AttributeInstance magicule = subordinate.getAttribute(TensuraAttributes.MAX_MAGICULE);
                  if (magicule == null) {
                     return;
                  }

                  if (magicule.getValue() <= grantingCost) {
                     return;
                  }

                  TextColor color = skillGrantingInstance.getSkill() instanceof Skill skill
                     ? skill.getColoredName().getStyle().getColor()
                     : TextColor.fromRgb(ChatFormatting.AQUA.getColor());
                  Component skillDisplay = skillGrantingInstance.getSkill().getChatDisplayName(false);
                  Component grantSource = Component.translatable("tensura_better_subs.skill_source.food_chain")
                     .withStyle(Style.EMPTY.withColor(color).withHoverEvent(new HoverEvent(Action.SHOW_TEXT, skillDisplay)));
                  if (!EnergyHelper.isOutOfEnergy(player, 0.0, ownerMagiculeCost)
                     && IExpandedSkillAcquisition.grantSkill(subordinate, abilityInstance.copy(), grantSource)) {
                     subordinate.level()
                        .playSound(
                           null, subordinate.getX(), subordinate.getY(), subordinate.getZ(), TensuraSoundEvents.BUFF_ACTIVATE, SoundSource.PLAYERS, 1.0F, 1.0F
                        );
                     subordinate.addEffect(new MobEffectInstance(MobEffects.GLOWING, 40, 0, false, false, false));
                     TensuraParticleHelper.addServerParticlesAroundSelf(subordinate, ParticleTypes.TOTEM_OF_UNDYING, 2.0);
                     TensuraParticleHelper.addServerParticlesAroundSelf(subordinate, ParticleTypes.FLASH, 1.0);
                     skillGrantingInstance.setCoolDown(SkillGranting.CONFIG.skillGrantCooldown, foodChain.getProvideMode());
                     skillGrantingInstance.markDirty();
                     storage.checkAndMarkDirty(skillGrantingInstance);
                  }
               }
            }
         );
      }
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
