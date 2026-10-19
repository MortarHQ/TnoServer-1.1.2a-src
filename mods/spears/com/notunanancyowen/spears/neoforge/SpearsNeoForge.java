package com.notunanancyowen.spears.neoforge;

import com.notunanancyowen.spears.Spears;
import com.notunanancyowen.spears.SpearsClient;
import com.notunanancyowen.spears.components.AttackRange;
import com.notunanancyowen.spears.components.KineticWeapon;
import com.notunanancyowen.spears.components.PiercingWeapon;
import com.notunanancyowen.spears.components.SwingAnimation;
import com.notunanancyowen.spears.components.UseEffects;
import com.notunanancyowen.spears.criteria.SpearedMobs;
import com.notunanancyowen.spears.packets.PlayerStabC2SPacket;
import com.notunanancyowen.spears.packets.TriggerStabEffectsC2SPacket;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforge.registries.DeferredRegister.DataComponents;

@Mod("spears")
public final class SpearsNeoForge {
   private static final DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, "minecraft");
   private static final DataComponents ENCHANTMENT_COMPONENTS = DeferredRegister.createDataComponents(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, "minecraft");
   private static final Supplier<DataComponentType<UseEffects>> USE_EFFECTS = DATA_COMPONENTS.registerComponentType(
      "use_effects", builder -> builder.persistent(UseEffects.CODEC).networkSynchronized(UseEffects.PACKET_CODEC)
   );
   private static final Supplier<DataComponentType<AttackRange>> ATTACK_RANGE = DATA_COMPONENTS.registerComponentType(
      "attack_range", builder -> builder.persistent(AttackRange.CODEC).networkSynchronized(AttackRange.PACKET_CODEC)
   );
   private static final Supplier<DataComponentType<KineticWeapon>> KINETIC_WEAPON = DATA_COMPONENTS.registerComponentType(
      "kinetic_weapon", builder -> builder.persistent(KineticWeapon.CODEC).networkSynchronized(KineticWeapon.PACKET_CODEC)
   );
   private static final Supplier<DataComponentType<SwingAnimation>> SWING_ANIMATION = DATA_COMPONENTS.registerComponentType(
      "swing_animation", builder -> builder.persistent(SwingAnimation.CODEC).networkSynchronized(SwingAnimation.PACKET_CODEC)
   );
   private static final Supplier<DataComponentType<PiercingWeapon>> PIERCING_WEAPON = DATA_COMPONENTS.registerComponentType(
      "piercing_weapon", builder -> builder.persistent(PiercingWeapon.CODEC).networkSynchronized(PiercingWeapon.PACKET_CODEC)
   );
   private static final Supplier<DataComponentType<Float>> MINIMUM_ATTACK_CHARGE = DATA_COMPONENTS.registerComponentType(
      "minimum_attack_charge", builder -> builder.persistent(Spears.rangedInclusiveFloat(0.0F, 1.0F)).networkSynchronized(ByteBufCodecs.FLOAT)
   );
   private static final Supplier<DataComponentType<List<ConditionalEffect<EnchantmentEntityEffect>>>> POST_PIERCING_ATTACK = ENCHANTMENT_COMPONENTS.registerComponentType(
      "post_piercing_attack",
      builder -> builder.persistent(ConditionalEffect.codec(EnchantmentEntityEffect.CODEC, LootContextParamSets.ENCHANTED_DAMAGE).listOf())
   );

   public SpearsNeoForge(IEventBus modEventBus, ModContainer modContainer) {
      Spears.hasBetterCombat = ModList.get().isLoaded("bettercombat");
      Spears.init();
      NeoForge.EVENT_BUS.register(this);
      DATA_COMPONENTS.register(modEventBus);
      ENCHANTMENT_COMPONENTS.register(modEventBus);
      modEventBus.addListener(this::registerStuff);
      modEventBus.addListener(this::modifyComponents);
      modEventBus.addListener(this::buildContents);
      modEventBus.addListener(this::registerPayloads);
      modContainer.registerConfig(Type.COMMON, SpearsConfig.SPEC);
   }

   private void registerStuff(RegisterEvent event) {
      event.register(Registries.DATA_COMPONENT_TYPE, registry -> {
         Spears.USE_EFFECTS = USE_EFFECTS.get();
         Spears.ATTACK_RANGE = ATTACK_RANGE.get();
         Spears.KINETIC_WEAPON = KINETIC_WEAPON.get();
         Spears.SWING_ANIMATION = SWING_ANIMATION.get();
         Spears.PIERCING_WEAPON = PIERCING_WEAPON.get();
         Spears.MINIMUM_ATTACK_CHARGE = MINIMUM_ATTACK_CHARGE.get();
      });
      event.register(
         Registries.SOUND_EVENT,
         registry -> {
            registry.register(
               ResourceLocation.withDefaultNamespace("item.spear.attack"),
               Spears.SPEAR_ATTACK = SoundEvent.createVariableRangeEvent(ResourceLocation.withDefaultNamespace("item.spear.attack"))
            );
            registry.register(
               ResourceLocation.withDefaultNamespace("item.spear.use"),
               Spears.SPEAR_USE = SoundEvent.createVariableRangeEvent(ResourceLocation.withDefaultNamespace("item.spear.use"))
            );
            registry.register(
               ResourceLocation.withDefaultNamespace("item.spear.hit"),
               Spears.SPEAR_HIT = SoundEvent.createVariableRangeEvent(ResourceLocation.withDefaultNamespace("item.spear.hit"))
            );
            registry.register(
               ResourceLocation.withDefaultNamespace("item.spear_wood.attack"),
               Spears.SPEAR_WOOD_ATTACK = SoundEvent.createVariableRangeEvent(ResourceLocation.withDefaultNamespace("item.spear_wood.attack"))
            );
            registry.register(
               ResourceLocation.withDefaultNamespace("item.spear_wood.hit"),
               Spears.SPEAR_WOOD_HIT = SoundEvent.createVariableRangeEvent(ResourceLocation.withDefaultNamespace("item.spear_wood.hit"))
            );
            registry.register(
               ResourceLocation.withDefaultNamespace("item.spear_wood.use"),
               Spears.SPEAR_WOOD_USE = SoundEvent.createVariableRangeEvent(ResourceLocation.withDefaultNamespace("item.spear_wood.use"))
            );
            registry.register(
               ResourceLocation.withDefaultNamespace("item.spear.lunge"),
               Spears.SPEAR_LUNGE = SoundEvent.createVariableRangeEvent(ResourceLocation.withDefaultNamespace("item.spear.lunge"))
            );
         }
      );
      event.register(
         Registries.ITEM,
         registry -> {
            registry.register(
               ResourceLocation.withDefaultNamespace("wooden_spear"),
               Spears.WOODEN_SPEAR = Spears.registerSpearRaw(Tiers.WOOD, 0.65F, 0.7F, 0.75F, 5.0F, 14.0F, 6.0F, 5.1F, 15.0F, 4.6F)
            );
            registry.register(
               ResourceLocation.withDefaultNamespace("stone_spear"),
               Spears.STONE_SPEAR = Spears.registerSpearRaw(Tiers.STONE, 0.75F, 0.82F, 0.7F, 4.5F, 10.0F, 5.5F, 5.1F, 13.75F, 4.6F)
            );
            registry.register(
               ResourceLocation.withDefaultNamespace("golden_spear"),
               Spears.GOLDEN_SPEAR = Spears.registerSpearRaw(Tiers.GOLD, 0.95F, 0.7F, 0.7F, 3.5F, 10.0F, 5.5F, 5.1F, 13.75F, 4.6F)
            );
            registry.register(
               ResourceLocation.withDefaultNamespace("iron_spear"),
               Spears.IRON_SPEAR = Spears.registerSpearRaw(Tiers.IRON, 0.95F, 0.95F, 0.6F, 2.5F, 8.0F, 4.5F, 5.1F, 11.25F, 4.6F)
            );
            registry.register(
               ResourceLocation.withDefaultNamespace("diamond_spear"),
               Spears.DIAMOND_SPEAR = Spears.registerSpearRaw(Tiers.DIAMOND, 1.05F, 1.075F, 0.5F, 3.0F, 7.5F, 4.0F, 5.1F, 10.0F, 4.6F)
            );
            registry.register(
               ResourceLocation.withDefaultNamespace("netherite_spear"),
               Spears.NETHERITE_SPEAR = Spears.registerSpearRaw(Tiers.NETHERITE, 1.15F, 1.2F, 0.4F, 2.5F, 7.0F, 3.5F, 5.1F, 8.75F, 4.6F)
            );
            if (ModList.get().isLoaded("copperagebackport") && Spears.COPPER_SPEAR == null) {
               registry.register(ResourceLocation.withDefaultNamespace("copper_spear"), Spears.tryLoadCopperSpear(true, true));
            }

            if (ModList.get().isLoaded("oreganized") && Spears.ELECTRUM_SPEAR == null) {
               registry.register(ResourceLocation.fromNamespaceAndPath("oreganized", "electrum_spear"), Spears.tryLoadElectrumSpear(true, true));
            }

            if (ModList.get().isLoaded("dragonloot") && Spears.DRAGON_SPEAR == null) {
               registry.register(ResourceLocation.fromNamespaceAndPath("dragonloot", "dragon_spear"), Spears.tryLoadDragonSpear(true, true, "dragonloot"));
            } else if (ModList.get().isLoaded("ender_dragon_loot") && Spears.DRAGON_SPEAR == null) {
               registry.register(
                  ResourceLocation.fromNamespaceAndPath("ender_dragon_loot", "dragon_spear"), Spears.tryLoadDragonSpear(true, true, "ender_dragon_loot")
               );
            }

            if (ModList.get().isLoaded("enderitemod") && Spears.ENDERITE_SPEAR == null) {
               try {
                  registry.register(ResourceLocation.fromNamespaceAndPath("enderitemod", "enderite_spear"), Spears.tryLoadEnderiteSpear(true, true));
               } catch (Throwable var2) {
               }
            }
         }
      );
      event.register(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, registry -> Spears.POST_PIERCING_ATTACK = POST_PIERCING_ATTACK.get());
      event.register(
         Registries.TRIGGER_TYPE, registry -> registry.register(ResourceLocation.withDefaultNamespace("spear_mobs"), Spears.SPEARED_MOBS = new SpearedMobs())
      );
   }

   private void modifyComponents(ModifyDefaultComponentsEvent event) {
      if (Spears.makeConfig().getOrDefault("tridents_use_spear_attack_animation", false)) {
         event.modifyMatching(
            item -> item instanceof TridentItem,
            builder -> builder.set(PIERCING_WEAPON.get(), new PiercingWeapon(0.2F, true, false, Optional.of(SoundEvents.TRIDENT_THROW), Optional.empty()))
               .set(SWING_ANIMATION.get(), new SwingAnimation(20, "stab"))
               .set(MINIMUM_ATTACK_CHARGE.get(), 0.9F)
               .set(ATTACK_RANGE.get(), new AttackRange(0.0F, 4.0F))
         );
      }
   }

   private void buildContents(BuildCreativeModeTabContentsEvent itemGroup) {
      if (itemGroup.getTabKey() == CreativeModeTabs.COMBAT) {
         itemGroup.insertAfter(Items.NETHERITE_SWORD.getDefaultInstance(), Spears.WOODEN_SPEAR.getDefaultInstance(), TabVisibility.PARENT_AND_SEARCH_TABS);
         itemGroup.insertAfter(Spears.WOODEN_SPEAR.getDefaultInstance(), Spears.STONE_SPEAR.getDefaultInstance(), TabVisibility.PARENT_AND_SEARCH_TABS);
         if (Spears.COPPER_SPEAR != null) {
            itemGroup.insertAfter(Spears.STONE_SPEAR.getDefaultInstance(), Spears.COPPER_SPEAR.getDefaultInstance(), TabVisibility.PARENT_AND_SEARCH_TABS);
            itemGroup.insertAfter(Spears.COPPER_SPEAR.getDefaultInstance(), Spears.IRON_SPEAR.getDefaultInstance(), TabVisibility.PARENT_AND_SEARCH_TABS);
         } else {
            itemGroup.insertAfter(Spears.STONE_SPEAR.getDefaultInstance(), Spears.IRON_SPEAR.getDefaultInstance(), TabVisibility.PARENT_AND_SEARCH_TABS);
         }

         itemGroup.insertAfter(Spears.IRON_SPEAR.getDefaultInstance(), Spears.GOLDEN_SPEAR.getDefaultInstance(), TabVisibility.PARENT_AND_SEARCH_TABS);
         if (Spears.ELECTRUM_SPEAR != null) {
            itemGroup.insertAfter(Spears.GOLDEN_SPEAR.getDefaultInstance(), Spears.ELECTRUM_SPEAR.getDefaultInstance(), TabVisibility.PARENT_AND_SEARCH_TABS);
            itemGroup.insertAfter(Spears.ELECTRUM_SPEAR.getDefaultInstance(), Spears.DIAMOND_SPEAR.getDefaultInstance(), TabVisibility.PARENT_AND_SEARCH_TABS);
         } else {
            itemGroup.insertAfter(Spears.GOLDEN_SPEAR.getDefaultInstance(), Spears.DIAMOND_SPEAR.getDefaultInstance(), TabVisibility.PARENT_AND_SEARCH_TABS);
         }

         itemGroup.insertAfter(Spears.DIAMOND_SPEAR.getDefaultInstance(), Spears.NETHERITE_SPEAR.getDefaultInstance(), TabVisibility.PARENT_AND_SEARCH_TABS);
      }

      if (ModList.get().isLoaded("dragonloot")
         && Spears.DRAGON_SPEAR != null
         && BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("dragonloot", "dragon_sword")) instanceof Item i) {
         try {
            itemGroup.insertAfter(i.getDefaultInstance(), Spears.DRAGON_SPEAR.getDefaultInstance(), TabVisibility.PARENT_AND_SEARCH_TABS);
         } catch (Throwable var4) {
         }
      }

      if (Spears.ENDERITE_SPEAR != null && itemGroup.getTabKey().registry().equals(ResourceLocation.fromNamespaceAndPath("enderitemod", "enderite_group"))) {
         itemGroup.accept(Spears.ENDERITE_SPEAR);
      }
   }

   private void registerPayloads(RegisterPayloadHandlersEvent event) {
      PayloadRegistrar registrar = event.registrar("1");
      registrar.playToServer(PlayerStabC2SPacket.ID, PlayerStabC2SPacket.PACKET_CODEC, (packet, context) -> {
         if (packet.getEntity(context.player().level()) instanceof ServerPlayer sp) {
            if (sp.getMainHandItem().get(Spears.PIERCING_WEAPON) instanceof PiercingWeapon pw) {
               pw.stab(sp, EquipmentSlot.MAINHAND);
            }

            sp.resetAttackStrengthTicker();
         }
      });
      registrar.playToServer(TriggerStabEffectsC2SPacket.ID, TriggerStabEffectsC2SPacket.PACKET_CODEC, (packet, context) -> {
         if (packet.toggle() && context.player() instanceof ServerPlayer s) {
            packet.trigger(s);
         }
      });
   }

   @SubscribeEvent
   public void onServerStarting(ServerStartingEvent event) {
   }

   @EventBusSubscriber(modid = "spears", value = Dist.CLIENT)
   public static class ClientModEvents {
      @SubscribeEvent
      public static void onClientSetup(FMLClientSetupEvent event) {
         SpearsClient.sendPacketUniversal = payload -> {
            PacketDistributor.sendToServer(payload, new CustomPacketPayload[0]);
            return true;
         };
      }
   }
}
