package net.unusual.block_factorys_bosses.item;

import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.unusual.block_factorys_bosses.client.renderer.WarriorSwordItemRenderer;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;

@EventBusSubscriber
public class WarriorSwordItem extends AnimatedSwordItem {
   private static final Tier TOOL_TIER = new Tier() {
      public int getUses() {
         return 300;
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

   public WarriorSwordItem() {
      super(TOOL_TIER, new Properties().attributes(SwordItem.createAttributes(TOOL_TIER, 6.0F, -2.3F)));
   }

   @Override
   public int getAttackDelay() {
      return 14;
   }

   @Override
   public SoundEvent getSwingSound() {
      return (SoundEvent)BossesRiseSounds.WARRIOR_SWORD_SWING.value();
   }

   @SubscribeEvent
   public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
      final Minecraft instance = Minecraft.getInstance();
      event.registerItem(new AnimatedSwordItem.AnimatedSwordItemExtension() {
         final WarriorSwordItemRenderer rendererInstance = new WarriorSwordItemRenderer(instance.getBlockEntityRenderDispatcher(), instance.getEntityModels());

         @Nonnull
         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return this.rendererInstance;
         }
      }, new Item[]{(Item)BossesRiseItems.WARRIOR_SWORD.get()});
   }
}
