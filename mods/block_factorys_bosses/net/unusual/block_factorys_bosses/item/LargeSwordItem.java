package net.unusual.block_factorys_bosses.item;

import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.unusual.block_factorys_bosses.client.renderer.LargeSwordItemRenderer;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;

@EventBusSubscriber
public class LargeSwordItem extends AnimatedSwordItem {
   private static final Tier TOOL_TIER = new Tier() {
      public int getUses() {
         return 400;
      }

      public float getSpeed() {
         return 4.0F;
      }

      public float getAttackDamageBonus() {
         return 0.0F;
      }

      public TagKey<Block> getIncorrectBlocksForDrops() {
         return BlockTags.INCORRECT_FOR_IRON_TOOL;
      }

      public int getEnchantmentValue() {
         return 2;
      }

      public Ingredient getRepairIngredient() {
         return Ingredient.of(new ItemLike[]{Items.IRON_INGOT});
      }
   };

   public LargeSwordItem() {
      super(
         TOOL_TIER,
         new Properties()
            .attributes(
               ItemAttributeModifiers.builder()
                  .add(
                     Attributes.ENTITY_INTERACTION_RANGE,
                     new AttributeModifier(ResourceLocation.withDefaultNamespace("base_entity_interaction_range"), 1.5, Operation.ADD_VALUE),
                     EquipmentSlotGroup.MAINHAND
                  )
                  .add(
                     Attributes.ATTACK_DAMAGE,
                     new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 11.0F + TOOL_TIER.getAttackDamageBonus(), Operation.ADD_VALUE),
                     EquipmentSlotGroup.MAINHAND
                  )
                  .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -3.3F, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                  .build()
            )
      );
   }

   @Override
   public int getAttackDelay() {
      return 14;
   }

   @Override
   public SoundEvent getSwingSound() {
      return (SoundEvent)BossesRiseSounds.LARGE_SWORD_SWING.value();
   }

   @SubscribeEvent
   public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
      final Minecraft instance = Minecraft.getInstance();
      event.registerItem(new AnimatedSwordItem.AnimatedSwordItemExtension() {
         final LargeSwordItemRenderer rendererInstance = new LargeSwordItemRenderer(instance.getBlockEntityRenderDispatcher(), instance.getEntityModels());

         @Nonnull
         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return this.rendererInstance;
         }
      }, new Item[]{(Item)BossesRiseItems.LARGE_SWORD.get()});
   }
}
