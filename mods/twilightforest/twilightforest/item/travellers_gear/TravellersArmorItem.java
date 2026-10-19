package twilightforest.item.travellers_gear;

import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial.Layer;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemAttributeModifiers.Builder;
import net.minecraft.world.item.component.ItemAttributeModifiers.Entry;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.util.ConcatenatedListView;
import org.jetbrains.annotations.NotNull;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.TFModelLayers;
import twilightforest.client.model.armor.TFArmorModel;
import twilightforest.client.model.armor.TravellersWingsModel;
import twilightforest.client.renderer.armor.TFArmorRenderer;
import twilightforest.init.TFArmorMaterials;
import twilightforest.init.TFAttributeModifiers;
import twilightforest.init.TFDataAttachments;
import twilightforest.init.TFDataComponents;
import twilightforest.init.TFItems;
import twilightforest.init.custom.TravellersModifiersManager;
import twilightforest.item.travellers_gear.modifiers.TravellersModifiable;
import twilightforest.item.travellers_gear.modifiers.TravellersModifier;

public class TravellersArmorItem extends ArmorItem implements TravellersModifiable {
   private static final MutableComponent GLOVES_TOOLTIP = Component.translatable("item.twilightforest.travellers_gloves.desc").withStyle(ChatFormatting.GRAY);
   private final int insertableModifierSlots;
   @Nullable
   private ItemAttributeModifiers attributeModifiers;

   public TravellersArmorItem(Type equipmentType, Properties properties, int insertableModifierSlots) {
      super(TFArmorMaterials.TRAVELLERS, equipmentType, properties.component(TFDataComponents.IS_TRAVELLERS_GEAR, Unit.INSTANCE));
      this.insertableModifierSlots = insertableModifierSlots;
      this.attributeModifiers = (ItemAttributeModifiers)this.components().get(DataComponents.ATTRIBUTE_MODIFIERS);
      if (this.attributeModifiers != null) {
         for (Entry modifier : this.getDefaultAttributeModifiers().modifiers()) {
            this.attributeModifiers = this.attributeModifiers.withModifierAdded(modifier.attribute(), modifier.modifier(), modifier.slot());
         }
      }
   }

   public TravellersArmorItem(Type equipmentType, Properties properties, int insertableModifierSlots, int durability) {
      this(equipmentType, properties.durability(equipmentType.getDurability(durability)), insertableModifierSlots);
   }

   @NotNull
   public Component getName(@NotNull ItemStack stack) {
      return (Component)(isTravellersArmorAndBroken(stack)
         ? Component.translatable(this.getDescriptionId(stack)).append(Component.translatable("travellers_gear.broken").withStyle(ChatFormatting.GRAY))
         : super.getName(stack));
   }

   @NotNull
   public ItemAttributeModifiers getDefaultAttributeModifiers() {
      return this.attributeModifiers == null ? super.getDefaultAttributeModifiers() : this.attributeModifiers;
   }

   @Nullable
   public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, Layer layer, boolean innerModel) {
      return !innerModel && entity.getData(TFDataAttachments.IS_USING_GOGGLES_ZOOM_MODIFIER)
         ? TwilightForestMod.prefix("textures/models/armor/travellers_layer_1_down.png")
         : super.getArmorTexture(stack, entity, slot, layer, innerModel);
   }

   public static Properties gogglesProperties(Properties properties) {
      return properties.attributes(defaultArmorProperties(Type.HELMET).build()).component(TFDataComponents.ZOOM_ABILITY_MODIFIER, 0.3F);
   }

   public static Properties chestProperties(Properties properties) {
      return properties.component(TFDataComponents.TRAVELLERS_HAS_CHESTPLATE, Unit.INSTANCE)
         .component(TFDataComponents.SWIFT_SWIM, Unit.INSTANCE)
         .attributes(
            defaultArmorProperties(Type.CHESTPLATE)
               .add(Attributes.WATER_MOVEMENT_EFFICIENCY, TFAttributeModifiers.TRAVELLERS_SWIFT_SWIM, EquipmentSlotGroup.CHEST)
               .build()
         );
   }

   public static Properties glovesProperties(Properties properties) {
      return properties.component(TFDataComponents.TRAVELLERS_HAS_GLOVES, Unit.INSTANCE);
   }

   public static Properties wingsProperties(Properties properties) {
      return properties.attributes(defaultArmorProperties(Type.LEGGINGS).build())
         .component(TFDataComponents.TRAVELLERS_HAS_WINGS, Unit.INSTANCE)
         .component(TFDataComponents.HIGH_JUMP_AMPLIFIER, 1);
   }

   public static Properties bootsProperties(Properties properties) {
      return properties.component(TFDataComponents.TRAVELLERS_HAS_BOOTS, Unit.INSTANCE)
         .component(TFDataComponents.HIGH_STEP, Unit.INSTANCE)
         .attributes(defaultArmorProperties(Type.BOOTS).add(Attributes.STEP_HEIGHT, TFAttributeModifiers.TRAVELLERS_HIGH_STEP, EquipmentSlotGroup.FEET).build());
   }

   public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags) {
      super.appendHoverText(stack, context, tooltip, flags);
      Provider registries = context.registries();
      if (registries != null) {
         List<Reference<TravellersModifier>> abilityModifiers = TravellersModifiersManager.findAllAbilityModifiers(registries, stack);

         for (Reference<TravellersModifier> travellersModifierReference : abilityModifiers) {
            tooltip.add(
               Component.translatable(
                     "travellers_gear.ability",
                     new Object[]{TravellersModifiersManager.getModifierTooltipComponent(travellersModifierReference).withStyle(ChatFormatting.GRAY)}
                  )
                  .withStyle(ChatFormatting.GOLD)
            );
         }

         List<Reference<TravellersModifier>> insertableModifiers = TravellersModifiersManager.findAllInsertableModifiers(registries, stack);

         for (Reference<TravellersModifier> modifier : insertableModifiers) {
            tooltip.add(Component.literal("- ").append(TravellersModifiersManager.getModifierTooltipComponent(modifier).withStyle(ChatFormatting.GRAY)));
            if (flags.hasShiftDown()) {
               for (Component description : ((TravellersModifier)modifier.value()).getDescription()) {
                  tooltip.add(
                     Component.literal("").append(Component.translatable("travellers_gear.info_indent").withStyle(ChatFormatting.BOLD)).append(description)
                  );
               }
            }
         }

         for (int i = insertableModifiers.size(); i < this.getModifierSlots(); i++) {
            tooltip.add(Component.literal("- ").append(Component.translatable("travellers_gear.modifier.empty").withStyle(ChatFormatting.DARK_GRAY)));
         }

         if (TFItems.TRAVELLERS_GLOVES.get() == this) {
            tooltip.add(GLOVES_TOOLTIP);
         }

         if (!flags.hasShiftDown()) {
            ConcatenatedListView<Reference<TravellersModifier>> modifiers = ConcatenatedListView.of(new List[]{abilityModifiers, insertableModifiers});
            boolean hasHiddenDescriptions = modifiers.stream()
               .map(Holder::value)
               .map(TravellersModifier::getDescription)
               .anyMatch(Predicate.not(List::isEmpty));
            if (hasHiddenDescriptions) {
               tooltip.add(
                  Component.translatable("travellers_gear.shift_info", new Object[]{Component.literal("Shift").withStyle(ChatFormatting.YELLOW)})
                     .withStyle(ChatFormatting.WHITE)
               );
            }
         }
      }
   }

   public boolean isEnchantable(@NotNull ItemStack stack) {
      return false;
   }

   public boolean isBookEnchantable(@NotNull ItemStack stack, @NotNull ItemStack book) {
      return true;
   }

   public boolean isPrimaryItemFor(@NotNull ItemStack stack, @NotNull Holder<Enchantment> enchantment) {
      return false;
   }

   public boolean supportsEnchantment(@NotNull ItemStack stack, @NotNull Holder<Enchantment> enchantment) {
      return false;
   }

   public boolean isRepairable(@NotNull ItemStack stack) {
      return false;
   }

   public boolean canWalkOnPowderedSnow(ItemStack stack, @NotNull LivingEntity wearer) {
      return stack.is(TFItems.TRAVELLERS_BOOTS);
   }

   public static boolean isTravellersArmorAndBroken(ItemStack stack) {
      return stack.has(TFDataComponents.IS_TRAVELLERS_GEAR) && stack.isDamageableItem() && stack.getMaxDamage() - 1 <= stack.getDamageValue();
   }

   public static Builder defaultArmorProperties(Type type) {
      int defense = ((ArmorMaterial)TFArmorMaterials.TRAVELLERS.value()).getDefense(type);
      float toughness = ((ArmorMaterial)TFArmorMaterials.TRAVELLERS.value()).toughness();
      Builder defaultArmorModifiers = ItemAttributeModifiers.builder();
      EquipmentSlotGroup equipmentslotgroup = EquipmentSlotGroup.bySlot(type.getSlot());
      ResourceLocation resourcelocation = ResourceLocation.withDefaultNamespace("armor." + type.getName());
      defaultArmorModifiers.add(Attributes.ARMOR, new AttributeModifier(resourcelocation, defense, Operation.ADD_VALUE), equipmentslotgroup);
      defaultArmorModifiers.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(resourcelocation, toughness, Operation.ADD_VALUE), equipmentslotgroup);
      float knockbackResistance = ((ArmorMaterial)TFArmorMaterials.TRAVELLERS.value()).knockbackResistance();
      if (knockbackResistance > 0.0F) {
         defaultArmorModifiers.add(
            Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(resourcelocation, knockbackResistance, Operation.ADD_VALUE), equipmentslotgroup
         );
      }

      return defaultArmorModifiers;
   }

   @Override
   public int getModifierSlots() {
      return this.insertableModifierSlots;
   }

   public boolean makesPiglinsNeutral(@NotNull ItemStack stack, @NotNull LivingEntity wearer) {
      return this == TFItems.TRAVELLERS_GOGGLES.get() || stack.has(TFDataComponents.TRAVELLERS_HAS_WINGS);
   }

   public static final class ArmorRender extends TFArmorRenderer {
      public ArmorRender() {
         super(
            TFModelLayers.TRAVELLERS_ARMOR_HELMET,
            TFModelLayers.TRAVELLERS_ARMOR_CHEST_GLOVES,
            TFModelLayers.TRAVELLERS_ARMOR_CHEST_GLOVES_SLIM,
            TFModelLayers.TRAVELLERS_ARMOR_LEGGINGS,
            TFModelLayers.TRAVELLERS_ARMOR_BOOTS
         );
      }

      @NotNull
      public HumanoidModel<?> getHumanoidArmorModel(@NotNull LivingEntity living, @NotNull ItemStack stack, EquipmentSlot slot, @NotNull HumanoidModel<?> model) {
         ModelPart root = switch (slot) {
            case HEAD -> this.getModelPart(TFModelLayers.TRAVELLERS_ARMOR_HELMET);
            case CHEST -> {
               ModelPart chestLayer = this.getModelPart(
                  this.isModelSlim(living) ? TFModelLayers.TRAVELLERS_ARMOR_CHEST_GLOVES_SLIM : TFModelLayers.TRAVELLERS_ARMOR_CHEST_GLOVES
               );
               chestLayer.getAllParts().forEach(part -> part.skipDraw = true);
               boolean hasChestplate = stack.has(TFDataComponents.TRAVELLERS_HAS_CHESTPLATE);
               boolean hasGloves = stack.has(TFDataComponents.TRAVELLERS_HAS_GLOVES);
               chestLayer.getChild("body").skipDraw = !hasChestplate;
               chestLayer.getChild("left_arm").skipDraw = !hasGloves;
               chestLayer.getChild("right_arm").skipDraw = !hasGloves;
               yield chestLayer;
            }
            case LEGS -> {
               ModelPart leggingsLayer = this.getModelPart(TFModelLayers.TRAVELLERS_ARMOR_LEGGINGS);
               leggingsLayer.getAllParts().forEach(part -> part.skipDraw = true);
               boolean hasWings = stack.has(TFDataComponents.TRAVELLERS_HAS_WINGS);
               boolean hasBelt = stack.has(TFDataComponents.TRAVELLERS_HAS_BELT)
                  || TravellersModifiersManager.hasTravellersModifier(living.registryAccess(), stack, TravellersModifiersManager.SWAP_HOTBAR_MODIFIER);
               TravellersWingsModel.skipBelt(leggingsLayer, !hasBelt);
               TravellersWingsModel.skipWings(leggingsLayer, !hasWings);
               yield leggingsLayer;
            }
            case FEET -> this.getModelPart(TFModelLayers.TRAVELLERS_ARMOR_BOOTS);
            default -> throw new IllegalArgumentException(
               "Unexpected slot: " + slot + ": " + stack + ". Please report to https://github.com/TeamTwilight/twilightforest/issues"
            );
         };
         return (HumanoidModel<?>)(slot == EquipmentSlot.LEGS ? new TravellersWingsModel(root) : new TFArmorModel(root));
      }

      public void setupModelAnimations(
         @NotNull LivingEntity livingEntity,
         @NotNull ItemStack itemStack,
         @NotNull EquipmentSlot equipmentSlot,
         @NotNull Model model,
         float limbSwing,
         float limbSwingAmount,
         float partialTick,
         float ageInTicks,
         float netHeadYaw,
         float headPitch
      ) {
         if (model instanceof TravellersWingsModel wingsModel) {
            wingsModel.setupModelAnimations(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         }
      }

      private boolean isModelSlim(LivingEntity entity) {
         return entity instanceof AbstractClientPlayer player ? player.getSkin().model().equals(net.minecraft.client.resources.PlayerSkin.Model.SLIM) : false;
      }
   }
}
