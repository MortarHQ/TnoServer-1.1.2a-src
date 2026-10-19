package artifacts.item;

import artifacts.Artifacts;
import artifacts.component.ability.AttributeModifiers;
import artifacts.component.ability.EnchantmentLevelModifiers;
import artifacts.component.ability.EntityCondition;
import artifacts.component.ability.SimpleAbility;
import artifacts.component.ability.mobeffect.EquipmentMobEffects;
import artifacts.component.ability.mobeffect.MobEffectProvider;
import artifacts.config.value.Value;
import artifacts.platform.PlatformServices;
import artifacts.registry.ModDataComponents;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.enchantment.Enchantment;

public class WearableArtifactItem extends Item {
   public WearableArtifactItem(Properties properties) {
      super(properties);
   }

   public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
      if (Artifacts.CONFIG.client.showTooltips.get()
         && !PlatformServices.platformHelper.isModLoaded("curios")
         && !PlatformServices.platformHelper.isModLoaded("trinkets")
         && !PlatformServices.platformHelper.isModLoaded("accessories")) {
         list.add(
            Component.translatable("%s.tooltip.missing_dependency".formatted("artifacts"))
               .withStyle(new ChatFormatting[]{ChatFormatting.RED, ChatFormatting.BOLD})
         );
      } else {
         super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
      }
   }

   public static class Builder {
      private final String itemName;
      private final Properties properties = new Properties();
      private final List<AttributeModifiers.Entry> attributes = new ArrayList<>();
      private final List<EnchantmentLevelModifiers.Entry> enchantments = new ArrayList<>();

      public Builder(String itemName) {
         this.itemName = itemName;
         this.equipSound(SoundEvents.ARMOR_EQUIP_GENERIC);
      }

      public WearableArtifactItem.Builder equipSound(SoundEvent equipSound) {
         return this.equipSound(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(equipSound));
      }

      public WearableArtifactItem.Builder equipSound(Holder<SoundEvent> equipSound) {
         this.properties.component(ModDataComponents.EQUIP_SOUND.get(), (SoundEvent)equipSound.value());
         return this;
      }

      public WearableArtifactItem.Builder addAttributeModifier(Holder<Attribute> attribute, Value<Double> amount, Operation operation) {
         return this.addAttributeModifier(attribute, amount, operation, true);
      }

      public WearableArtifactItem.Builder addAttributeModifier(Holder<Attribute> attribute, Value<Double> amount, Operation operation, boolean ignoreCooldown) {
         this.attributes
            .add(
               new AttributeModifiers.Entry(
                  attribute,
                  amount,
                  operation,
                  Artifacts.id(this.itemName + "/" + ((ResourceKey)attribute.unwrapKey().orElseThrow()).location().getPath()),
                  ignoreCooldown
               )
            );
         return this;
      }

      public WearableArtifactItem.Builder mobEffect(Holder<MobEffect> effect, Value<Integer> level, Value<Integer> duration, EntityCondition condition) {
         return this.component(
            ModDataComponents.MOB_EFFECTS.get(),
            new EquipmentMobEffects(
               List.of(new EquipmentMobEffects.Entry(new MobEffectProvider(effect, level, duration, Value.of(false), Value.of(true), condition)))
            )
         );
      }

      public WearableArtifactItem.Builder increasesEnchantment(ResourceKey<Enchantment> enchantment, Value<Integer> amount) {
         this.enchantments.add(new EnchantmentLevelModifiers.Entry(enchantment, amount));
         return this;
      }

      public WearableArtifactItem.Builder component(DataComponentType<Unit> type) {
         return this.component(type, Unit.INSTANCE);
      }

      public WearableArtifactItem.Builder component(DataComponentType<SimpleAbility> type, Value<Boolean> enabled) {
         return this.component(type, new SimpleAbility(enabled));
      }

      public <T> WearableArtifactItem.Builder component(DataComponentType<T> type, T component) {
         this.properties.component(type, component);
         return this;
      }

      public WearableArtifactItem.Builder properties(Consumer<Properties> consumer) {
         consumer.accept(this.properties);
         return this;
      }

      public WearableArtifactItem build() {
         this.properties.stacksTo(1).rarity(Rarity.RARE).fireResistant();
         if (!this.attributes.isEmpty()) {
            this.properties.component(ModDataComponents.ATTRIBUTE_MODIFIERS.get(), new AttributeModifiers(this.attributes));
         }

         if (!this.enchantments.isEmpty()) {
            this.properties.component(ModDataComponents.ENCHANTMENT_LEVEL_MODIFIERS.get(), new EnchantmentLevelModifiers(this.enchantments));
         }

         return new WearableArtifactItem(this.properties);
      }
   }
}
