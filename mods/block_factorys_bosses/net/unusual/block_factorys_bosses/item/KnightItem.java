package net.unusual.block_factorys_bosses.item;

import java.util.Collections;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial.Layer;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemAttributeModifiers.Builder;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.client.model.Modelknight_armor;
import net.unusual.block_factorys_bosses.init.BossesRiseArmorMaterials;
import net.unusual.block_factorys_bosses.init.BossesRiseAttributes;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.util.ItemAttributeModifiersUtil;

@EventBusSubscriber
public abstract class KnightItem extends ArmorItem {
   @SubscribeEvent
   public static void registerItemExtensions(RegisterClientExtensionsEvent event) {
      event.registerItem(
         new IClientItemExtensions() {
            public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
               HumanoidModel armorModel = new HumanoidModel(
                  new ModelPart(
                     Collections.emptyList(),
                     Map.of(
                        "head",
                        (new Modelknight_armor(Minecraft.getInstance().getEntityModels().bakeLayer(Modelknight_armor.LAYER_LOCATION))).head2,
                        "hat",
                        new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                        "body",
                        new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                        "right_arm",
                        new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                        "left_arm",
                        new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                        "right_leg",
                        new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                        "left_leg",
                        new ModelPart(Collections.emptyList(), Collections.emptyMap())
                     )
                  )
               );
               armorModel.crouching = living.isShiftKeyDown();
               armorModel.riding = defaultModel.riding;
               armorModel.young = living.isBaby();
               return armorModel;
            }
         },
         new Item[]{(Item)BossesRiseItems.KNIGHT_HELMET.get()}
      );
      event.registerItem(
         new IClientItemExtensions() {
            @OnlyIn(Dist.CLIENT)
            public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
               HumanoidModel armorModel = new HumanoidModel(
                  new ModelPart(
                     Collections.emptyList(),
                     Map.of(
                        "body",
                        (new Modelknight_armor(Minecraft.getInstance().getEntityModels().bakeLayer(Modelknight_armor.LAYER_LOCATION))).torso,
                        "left_arm",
                        (new Modelknight_armor(Minecraft.getInstance().getEntityModels().bakeLayer(Modelknight_armor.LAYER_LOCATION))).left_arm,
                        "right_arm",
                        (new Modelknight_armor(Minecraft.getInstance().getEntityModels().bakeLayer(Modelknight_armor.LAYER_LOCATION))).right_arm,
                        "head",
                        new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                        "hat",
                        new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                        "right_leg",
                        new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                        "left_leg",
                        new ModelPart(Collections.emptyList(), Collections.emptyMap())
                     )
                  )
               );
               armorModel.crouching = living.isShiftKeyDown();
               armorModel.riding = defaultModel.riding;
               armorModel.young = living.isBaby();
               return armorModel;
            }
         },
         new Item[]{(Item)BossesRiseItems.KNIGHT_CHESTPLATE.get()}
      );
      event.registerItem(
         new IClientItemExtensions() {
            @OnlyIn(Dist.CLIENT)
            public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
               HumanoidModel armorModel = new HumanoidModel(
                  new ModelPart(
                     Collections.emptyList(),
                     Map.of(
                        "left_leg",
                        (new Modelknight_armor(Minecraft.getInstance().getEntityModels().bakeLayer(Modelknight_armor.LAYER_LOCATION))).left_pants,
                        "right_leg",
                        (new Modelknight_armor(Minecraft.getInstance().getEntityModels().bakeLayer(Modelknight_armor.LAYER_LOCATION))).right_leg,
                        "head",
                        new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                        "hat",
                        new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                        "body",
                        new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                        "right_arm",
                        new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                        "left_arm",
                        new ModelPart(Collections.emptyList(), Collections.emptyMap())
                     )
                  )
               );
               armorModel.crouching = living.isShiftKeyDown();
               armorModel.riding = defaultModel.riding;
               armorModel.young = living.isBaby();
               return armorModel;
            }
         },
         new Item[]{(Item)BossesRiseItems.KNIGHT_LEGGINGS.get()}
      );
      event.registerItem(
         new IClientItemExtensions() {
            @OnlyIn(Dist.CLIENT)
            public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
               HumanoidModel armorModel = new HumanoidModel(
                  new ModelPart(
                     Collections.emptyList(),
                     Map.of(
                        "left_leg",
                        (new Modelknight_armor(Minecraft.getInstance().getEntityModels().bakeLayer(Modelknight_armor.LAYER_LOCATION))).left_boots,
                        "right_leg",
                        (new Modelknight_armor(Minecraft.getInstance().getEntityModels().bakeLayer(Modelknight_armor.LAYER_LOCATION))).right_boots,
                        "head",
                        new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                        "hat",
                        new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                        "body",
                        new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                        "right_arm",
                        new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                        "left_arm",
                        new ModelPart(Collections.emptyList(), Collections.emptyMap())
                     )
                  )
               );
               armorModel.crouching = living.isShiftKeyDown();
               armorModel.riding = defaultModel.riding;
               armorModel.young = living.isBaby();
               return armorModel;
            }
         },
         new Item[]{(Item)BossesRiseItems.KNIGHT_BOOTS.get()}
      );
   }

   public KnightItem(Type type, Properties properties) {
      super(BossesRiseArmorMaterials.KNIGHT_ARMOR, type, properties);
   }

   public ItemAttributeModifiers getDefaultAttributeModifiers() {
      Builder builder = createAttributesBuilder(this.getEquipmentSlot());
      ItemAttributeModifiersUtil.addAll(builder, super.getDefaultAttributeModifiers());
      return builder.build();
   }

   public static ItemAttributeModifiers createAttributes(EquipmentSlot slot) {
      return createAttributesBuilder(slot).build();
   }

   public static Builder createAttributesBuilder(EquipmentSlot slot) {
      Builder builder = ItemAttributeModifiers.builder();
      builder.add(
         BossesRiseAttributes.ROLL_COUNT.getDelegate(),
         new AttributeModifier(BossesRise.prefix("effect.roll." + slot.getSerializedName()), 1.0, Operation.ADD_VALUE),
         EquipmentSlotGroup.bySlot(slot)
      );
      return builder;
   }

   public static class Boots extends KnightItem {
      public Boots() {
         super(Type.BOOTS, new Properties().durability(Type.BOOTS.getDurability(30)));
      }

      public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, Layer layer, boolean innerModel) {
         return BossesRise.prefix("textures/entities/knight_armor.png");
      }
   }

   public static class Chestplate extends KnightItem {
      public Chestplate() {
         super(Type.CHESTPLATE, new Properties().durability(Type.CHESTPLATE.getDurability(30)));
      }

      public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, Layer layer, boolean innerModel) {
         return BossesRise.prefix("textures/entities/knight_armor.png");
      }
   }

   public static class Helmet extends KnightItem {
      public Helmet() {
         super(Type.HELMET, new Properties().durability(Type.HELMET.getDurability(30)));
      }

      public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, Layer layer, boolean innerModel) {
         return BossesRise.prefix("textures/entities/knight_armor.png");
      }
   }

   public static class Leggings extends KnightItem {
      public Leggings() {
         super(Type.LEGGINGS, new Properties().durability(Type.LEGGINGS.getDurability(30)));
      }

      public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, Layer layer, boolean innerModel) {
         return BossesRise.prefix("textures/entities/knight_armor.png");
      }
   }
}
