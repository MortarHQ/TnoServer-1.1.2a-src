package com.notunanancyowen.spears.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.notunanancyowen.spears.Spears;
import com.notunanancyowen.spears.dataholders.SpearUser;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootParams.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.EntityHitResult;

public record PiercingWeapon(
   float hitboxMargin, boolean dealsKnockback, boolean dismounts, Optional<Holder<SoundEvent>> sound, Optional<Holder<SoundEvent>> hitSound
) {
   public static final Codec<PiercingWeapon> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            Spears.rangedInclusiveFloat(0.0F, 1.0F).optionalFieldOf("hitbox_margin", 0.3F).forGetter(PiercingWeapon::hitboxMargin),
            Codec.BOOL.optionalFieldOf("deals_knockback", true).forGetter(PiercingWeapon::dealsKnockback),
            Codec.BOOL.optionalFieldOf("dismounts", false).forGetter(PiercingWeapon::dismounts),
            SoundEvent.CODEC.optionalFieldOf("sound").forGetter(PiercingWeapon::sound),
            SoundEvent.CODEC.optionalFieldOf("hit_sound").forGetter(PiercingWeapon::hitSound)
         )
         .apply(instance, PiercingWeapon::new)
   );
   public static final StreamCodec<RegistryFriendlyByteBuf, PiercingWeapon> PACKET_CODEC = StreamCodec.composite(
      ByteBufCodecs.FLOAT,
      PiercingWeapon::hitboxMargin,
      ByteBufCodecs.BOOL,
      PiercingWeapon::dealsKnockback,
      ByteBufCodecs.BOOL,
      PiercingWeapon::dismounts,
      SoundEvent.STREAM_CODEC.apply(ByteBufCodecs::optional),
      PiercingWeapon::sound,
      SoundEvent.STREAM_CODEC.apply(ByteBufCodecs::optional),
      PiercingWeapon::hitSound,
      PiercingWeapon::new
   );

   public void playSound(Entity entity) {
      if (entity instanceof Player p) {
         this.sound
            .ifPresent(
               sound -> entity.getCommandSenderWorld().playSound(p, entity.getX(), entity.getY(), entity.getZ(), sound, entity.getSoundSource(), 1.0F, 1.0F)
            );
      }
   }

   public void playHitSound(Entity entity) {
      this.hitSound
         .ifPresent(
            sound -> entity.getCommandSenderWorld().playSound(null, entity.getX(), entity.getY(), entity.getZ(), sound, entity.getSoundSource(), 1.0F, 1.0F)
         );
   }

   public static boolean canHit(Entity attacker, Entity target) {
      return target.canBeHitByProjectile() && !target.isInvulnerable() && target.isAlive()
         ? !(target instanceof Player playerEntity && attacker instanceof Player playerEntity2 && !playerEntity2.canHarmPlayer(playerEntity))
            && !attacker.isPassengerOfSameVehicle(target)
         : false;
   }

   public boolean stab(LivingEntity attacker, EquipmentSlot slot) {
      float f = (float)attacker.getAttributeValue(Attributes.ATTACK_DAMAGE);
      boolean bl = false;
      float minReach = 0.0F;
      float maxReach = 3.0F;
      ItemStack stack = attacker.getItemBySlot(slot);
      if (stack.get(Spears.ATTACK_RANGE) instanceof AttackRange(float reachMin, float reachMax)) {
         minReach = reachMin;
         maxReach = reachMax;
      }

      if (attacker instanceof SpearUser s) {
         for (EntityHitResult entityHitResult : Spears.collectPiercingCollisions(
            attacker, minReach, maxReach, this.hitboxMargin, entity -> canHit(attacker, entity)
         )) {
            bl |= s.pierce(slot, entityHitResult.getEntity(), f, true, this.dealsKnockback, this.dismounts);
         }
      }

      if (!stack.isEmpty()
         && (stack.is(Spears.CAN_LUNGE_WHILE_GLIDING) || Spears.config.getOrDefault("can_use_lunge_while_gliding", false) || !attacker.isFallFlying())
         && (Spears.config.getOrDefault("can_use_lunge_while_in_water", false) || !attacker.isInWater())
         && (!(Spears.config.getOrDefault("lunge_hunger_cutoff", true) && attacker instanceof Player player) || player.getFoodData().getFoodLevel() > 5)
         && attacker.level() instanceof ServerLevel server) {
         ItemEnchantments itemEnchantmentsComponent = (ItemEnchantments)stack.get(DataComponents.ENCHANTMENTS);
         if (itemEnchantmentsComponent != null && !itemEnchantmentsComponent.isEmpty()) {
            EnchantedItemInUse enchantmentEffectContext = new EnchantedItemInUse(stack, slot, attacker);

            for (Entry<Holder<Enchantment>> entry : itemEnchantmentsComponent.entrySet()) {
               Holder<Enchantment> registryEntry = (Holder<Enchantment>)entry.getKey();
               int lvl = EnchantmentHelper.getItemEnchantmentLevel(registryEntry, stack);
               if (((Enchantment)registryEntry.value()).matchingSlot(slot)) {
                  for (ConditionalEffect<EnchantmentEntityEffect> enchantmentEffectEntry : ((Enchantment)registryEntry.value())
                     .getEffects(Spears.POST_PIERCING_ATTACK)) {
                     LootParams lootContextParameterSet = new Builder(server)
                        .withParameter(LootContextParams.THIS_ENTITY, attacker)
                        .withParameter(LootContextParams.ENCHANTMENT_LEVEL, lvl)
                        .withParameter(LootContextParams.ORIGIN, attacker.position())
                        .create(LootContextParamSets.ENCHANTED_ENTITY);
                     if (enchantmentEffectEntry.matches(
                        new net.minecraft.world.level.storage.loot.LootContext.Builder(lootContextParameterSet).create(Optional.empty())
                     )) {
                        ((EnchantmentEntityEffect)enchantmentEffectEntry.effect()).apply(server, lvl, enchantmentEffectContext, attacker, attacker.position());
                     }
                  }
               }
            }
         }
      }

      if (bl) {
         this.playHitSound(attacker);
      }

      this.playSound(attacker);
      attacker.swing(InteractionHand.MAIN_HAND, false);
      return bl;
   }
}
