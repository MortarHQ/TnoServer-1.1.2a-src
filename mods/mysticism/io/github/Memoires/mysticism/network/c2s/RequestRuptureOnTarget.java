package io.github.Memoires.mysticism.network.c2s;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.Memoires.mysticism.storage.MysticismStorages;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import java.util.List;
import java.util.Optional;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public record RequestRuptureOnTarget(int entityId, List<Integer> targetIds) implements CustomPacketPayload {
   private static final UniqueMysticismSkillConfig.Butcher CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Butcher;
   public static final Type<RequestRuptureOnTarget> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("mysticism", "request_rupture_on_target"));
   public static final StreamCodec<FriendlyByteBuf, RequestRuptureOnTarget> STREAM_CODEC = CustomPacketPayload.codec(
      RequestRuptureOnTarget::encode, RequestRuptureOnTarget::new
   );

   public RequestRuptureOnTarget(FriendlyByteBuf buf) {
      this(buf.readInt(), buf.readList(FriendlyByteBuf::readVarInt));
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeInt(this.entityId);
      buf.writeCollection(this.targetIds, FriendlyByteBuf::writeVarInt);
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
                     if (instance.onCoolDown(1)) {
                        return;
                     }

                     for (int targetId : this.targetIds) {
                        if (!(player.level().getEntity(targetId) instanceof LivingEntity livingTarget)) {
                           return;
                        }

                        livingTarget.addEffect(
                           new MobEffectInstance(TensuraMobEffects.getReference(MysticismMobEffects.RUPTURING), -1, 1, true, true, true), player
                        );
                        MysticismStorages.getPlayerDataFrom(player).clearMarks(livingTarget.getUUID());
                     }

                     instance.addMasteryPoint(player);
                     instance.setCoolDown(CONFIG.ruptureCooldownMode ? CONFIG.ruptureCooldown * this.targetIds.size() : CONFIG.ruptureCooldown, 1);
                  }
               }
            }
         );
      }
   }
}
