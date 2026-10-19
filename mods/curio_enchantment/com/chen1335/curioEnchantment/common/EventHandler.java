package com.chen1335.curioEnchantment.common;

import com.chen1335.curioEnchantment.CurioEnchantment;
import com.chen1335.curioEnchantment.API.objects.AttachmentTypes;
import com.chen1335.curioEnchantment.API.objects.DataComponentTypes;
import com.chen1335.curioEnchantment.API.objects.enchantmentEffects.CurioEnchantmentAttributeEffect;
import com.chen1335.curioEnchantment.common.attachmentTypes.PlayerData;
import com.chen1335.curioEnchantment.data.enchantments.IronsSpellBooksEnchantments;
import io.redspace.ironsspellbooks.api.events.ModifySpellLevelEvent;
import io.redspace.ironsspellbooks.api.events.SpellOnCastEvent;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.item.SpellBook;
import io.redspace.ironsspellbooks.registries.ComponentRegistry;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.CommonHooks;
import top.theillusivec4.curios.api.event.CurioAttributeModifierEvent;

public class EventHandler {
   @EventBusSubscriber(modid = "curio_enchantment")
   public static class GAME {
      @SubscribeEvent
      public static void curioAttributeEvent(CurioAttributeModifierEvent event) {
         ItemStack itemStack = event.getItemStack();
         RegistryLookup<Enchantment> lookup = CommonHooks.resolveLookup(Registries.ENCHANTMENT);
         if (lookup != null) {
            if (CurioEnchantment.IRONS_SPELL_BOOKS_LOADED && itemStack.getItem() instanceof SpellBook) {
               Optional<Reference<Enchantment>> knowledgeHolder = lookup.get(IronsSpellBooksEnchantments.KNOWLEDGE);
               if (knowledgeHolder.isPresent() && itemStack.getEnchantmentLevel((Holder)knowledgeHolder.get()) > 0) {
                  ISpellContainer container = (ISpellContainer)itemStack.get(ComponentRegistry.SPELL_CONTAINER);
                  if (container != null) {
                     event.addModifier(
                        AttributeRegistry.MAX_MANA,
                        new AttributeModifier(CurioEnchantment.id("knowledge_enchantment"), 5 * container.getActiveSpellCount(), Operation.ADD_VALUE)
                     );
                     event.addModifier(
                        AttributeRegistry.SPELL_POWER,
                        new AttributeModifier(
                           CurioEnchantment.id("knowledge_enchantment"), 0.01 * container.getActiveSpellCount(), Operation.ADD_MULTIPLIED_BASE
                        )
                     );
                  }
               }
            }

            EnchantmentHelper.runIterationOnItem(
               itemStack,
               (enchantment, level) -> ((Enchantment)enchantment.value())
                  .getEffects((DataComponentType)DataComponentTypes.CURIO_ATTRIBUTE.value())
                  .forEach(
                     curioEnchantmentAttributeEffect -> {
                        CurioEnchantmentAttributeEffect effect = (CurioEnchantmentAttributeEffect)curioEnchantmentAttributeEffect.effect();
                        event.addModifier(
                           effect.attribute(),
                           new AttributeModifier(
                              ResourceLocation.fromNamespaceAndPath(
                                 "curio_enchantment",
                                 Objects.requireNonNull(enchantment.getKey()).location().getPath()
                                    + "_enchantment_"
                                    + event.getSlotContext().identifier().toString().replace(":", "_")
                                    + "_"
                                    + event.getSlotContext().index()
                              ),
                              effect.amount().calculate(level),
                              effect.operation()
                           )
                        );
                     }
                  )
            );
         }
      }

      public static class IronsSpellBooksEvents {
         @SubscribeEvent
         public static void SpellOnCastEvent(SpellOnCastEvent event) {
            Player player = event.getEntity();
            AttributeInstance spellPower = player.getAttribute(AttributeRegistry.SPELL_POWER);
            if (spellPower != null) {
               spellPower.removeModifier(AttributeModifierResourceLocations.VARIOUS_ENCHANTMENT);
               spellPower.removeModifier(AttributeModifierResourceLocations.DEVOTED_ENCHANTMENT);
               if (event.getCastSource().equals(CastSource.SPELLBOOK)) {
                  ItemStack itemStack = Utils.getPlayerSpellbookStack(player);
                  if (itemStack != null) {
                     PlayerData playerData = (PlayerData)player.getData(AttachmentTypes.PLAYER_DATA);
                     RegistryLookup<Enchantment> enchantmentLookup = event.getEntity().level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
                     Optional<Reference<Enchantment>> devotedHolder = enchantmentLookup.get(IronsSpellBooksEnchantments.DEVOTED);
                     if (devotedHolder.isPresent()) {
                        int devotedLevel = itemStack.getEnchantmentLevel((Holder)devotedHolder.get());
                        if (devotedLevel > 0) {
                           if (!Objects.equals(playerData.lastCastSpell, event.getSpellId()) && !Objects.equals(playerData.lastCastSpell, "")) {
                              playerData.lastCastSpell = event.getSpellId();
                              playerData.sameCastSpellCount = 0;
                           } else {
                              playerData.lastCastSpell = event.getSpellId();
                              playerData.sameCastSpellCount = Math.min(playerData.sameCastSpellCount + 1, devotedLevel);
                              spellPower.addTransientModifier(
                                 new AttributeModifier(
                                    AttributeModifierResourceLocations.DEVOTED_ENCHANTMENT, 0.15 * playerData.sameCastSpellCount, Operation.ADD_MULTIPLIED_BASE
                                 )
                              );
                           }
                        }
                     }

                     Optional<Reference<Enchantment>> variousHolder = enchantmentLookup.get(IronsSpellBooksEnchantments.VARIOUS);
                     if (variousHolder.isPresent()) {
                        int variousLevel = itemStack.getEnchantmentLevel((Holder)variousHolder.get());
                        if (variousLevel > 0 && !Objects.equals(playerData.lastCastSpell, event.getSpellId())) {
                           playerData.lastCastSpell = event.getSpellId();
                           spellPower.addTransientModifier(
                              new AttributeModifier(AttributeModifierResourceLocations.VARIOUS_ENCHANTMENT, 0.05 * variousLevel, Operation.ADD_MULTIPLIED_BASE)
                           );
                        }
                     }
                  }
               }
            }
         }

         @SubscribeEvent
         public static void ModifySpellLevelEvent(ModifySpellLevelEvent event) {
            RegistryLookup<Enchantment> lookup = CommonHooks.resolveLookup(Registries.ENCHANTMENT);
            if (event.getEntity() instanceof Player player && lookup != null) {
               ItemStack itemStack = Utils.getPlayerSpellbookStack(player);
               if (itemStack != null) {
                  Optional<Reference<Enchantment>> ancientWisdomHolder = lookup.get(IronsSpellBooksEnchantments.ANCIENT_WISDOM);
                  if (ancientWisdomHolder.isPresent()) {
                     int ancientWisdomLevel = itemStack.getEnchantmentLevel((Holder)ancientWisdomHolder.get());
                     event.addLevels(ancientWisdomLevel);
                  }
               }
            }
         }
      }
   }
}
