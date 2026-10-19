package com.notunanancyowen.spears.packets;

import com.notunanancyowen.spears.Spears;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
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

public record TriggerStabEffectsC2SPacket(boolean toggle) implements CustomPacketPayload {
   public static final StreamCodec<FriendlyByteBuf, TriggerStabEffectsC2SPacket> PACKET_CODEC = StreamCodec.composite(
      ByteBufCodecs.BOOL, TriggerStabEffectsC2SPacket::toggle, TriggerStabEffectsC2SPacket::new
   );
   public static final Type<TriggerStabEffectsC2SPacket> ID = new Type(ResourceLocation.fromNamespaceAndPath("spears", "send_stab_effects_to_server"));

   public void trigger(ServerPlayer me) {
      InteractionHand hand = me.swingingArm == null ? InteractionHand.MAIN_HAND : me.swingingArm;
      ItemStack stack = me.getItemInHand(hand);
      if (!stack.isEmpty() && me.level() instanceof ServerLevel server) {
         ItemEnchantments itemEnchantmentsComponent = (ItemEnchantments)stack.get(DataComponents.ENCHANTMENTS);
         if (itemEnchantmentsComponent != null && !itemEnchantmentsComponent.isEmpty()) {
            EnchantedItemInUse enchantmentEffectContext = new EnchantedItemInUse(
               stack, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND, me
            );

            for (Entry<Holder<Enchantment>> entry : itemEnchantmentsComponent.entrySet()) {
               Holder<Enchantment> registryEntry = (Holder<Enchantment>)entry.getKey();
               int lvl = EnchantmentHelper.getItemEnchantmentLevel(registryEntry, stack);
               if (((Enchantment)registryEntry.value()).matchingSlot(hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND)) {
                  for (ConditionalEffect<EnchantmentEntityEffect> enchantmentEffectEntry : ((Enchantment)registryEntry.value())
                     .getEffects(Spears.POST_PIERCING_ATTACK)) {
                     LootParams lootContextParameterSet = new Builder(server)
                        .withParameter(LootContextParams.THIS_ENTITY, me)
                        .withParameter(LootContextParams.ENCHANTMENT_LEVEL, lvl)
                        .withParameter(LootContextParams.ORIGIN, me.position())
                        .create(LootContextParamSets.ENCHANTED_ENTITY);
                     if (enchantmentEffectEntry.matches(
                        new net.minecraft.world.level.storage.loot.LootContext.Builder(lootContextParameterSet).create(Optional.empty())
                     )) {
                        ((EnchantmentEntityEffect)enchantmentEffectEntry.effect()).apply(server, lvl, enchantmentEffectContext, me, me.position());
                     }
                  }
               }
            }
         }
      }
   }

   public Type<? extends CustomPacketPayload> type() {
      return ID;
   }
}
