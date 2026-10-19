package it.hurts.octostudios.reliquified_twilight_forest.item.relic;

import it.hurts.octostudios.reliquified_twilight_forest.client.event.RenderItemInHandEvent;
import it.hurts.octostudios.reliquified_twilight_forest.data.loot.LootEntries;
import it.hurts.octostudios.reliquified_twilight_forest.init.ItemRegistry;
import it.hurts.octostudios.reliquified_twilight_forest.util.EntitiesButCool;
import it.hurts.octostudios.reliquified_twilight_forest.util.MathButCool;
import it.hurts.sskirillss.relics.items.relics.base.RelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicAttributeModifier;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicAttributeModifier.Modifier;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourceData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourcesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.GemColor;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.GemShape;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootEntry;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags.Items;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Post;
import net.neoforged.neoforge.event.level.BlockEvent.BreakEvent;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;

public class GiantGloveItem extends RelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("oversized_grip")
                     .stat(
                        StatData.builder("multiplier")
                           .initialValue(0.05, 0.15)
                           .upgradeModifier(UpgradeOperation.ADD, 0.035)
                           .formatValue(MathButCool::percentage)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .sources(
                  LevelingSourcesData.builder().source(LevelingSourceData.abilityBuilder("oversized_grip").gem(GemShape.SQUARE, GemColor.BLUE).build()).build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.TROLL}).build())
         .build();
   }

   @Nullable
   public RelicAttributeModifier getRelicAttributeModifiers(ItemStack stack) {
      float multiplier = (float)this.getStatValue(stack, "oversized_grip", "multiplier");
      return RelicAttributeModifier.builder()
         .attribute(new Modifier(Attributes.BLOCK_INTERACTION_RANGE, 2.5F * (1.0F + multiplier), Operation.ADD_VALUE))
         .attribute(new Modifier(Attributes.ENTITY_INTERACTION_RANGE, 2.5F * (1.0F + multiplier), Operation.ADD_VALUE))
         .build();
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      super.onUnequip(slotContext, newStack, stack);
      if (newStack.getItem() != stack.getItem()) {
         removeAttributes(slotContext);
      }
   }

   public String getConfigRoute() {
      return "reliquified_twilight_forest";
   }

   private static void removeAttributes(SlotContext slotContext) {
      ResourceLocation rl = ResourceLocation.fromNamespaceAndPath("reliquified_twilight_forest", "giant_glove_" + slotContext.identifier());
      BuiltInRegistries.ATTRIBUTE.asHolderIdMap().iterator().forEachRemaining(holder -> {
         if (slotContext.entity().getAttributes().hasAttribute(holder)) {
            slotContext.entity().getAttributes().getInstance(holder).removeModifier(rl);
         }
      });
   }

   @EventBusSubscriber(Dist.CLIENT)
   public static class ClientEvents {
      @SubscribeEvent
      public static void renderItem(RenderItemInHandEvent e) {
         ItemStack stack = EntityUtils.findEquippedCurio(e.getEntity(), (Item)ItemRegistry.GIANT_GLOVE.get());
         if (stack.getItem() instanceof GiantGloveItem relic) {
            if (e.getDisplayContext().firstPerson()) {
               float scale = (float)(1.25 + relic.getStatValue(stack, "oversized_grip", "multiplier") * 1.8);
               e.getPoseStack().scale(scale, scale, scale);
               e.getPoseStack().translate(0.0, -0.1, 0.0);
               if (e.getItemStack().is(Items.TOOLS_SHIELD)) {
                  e.getPoseStack().translate(0.1 * (e.getDisplayContext() == ItemDisplayContext.FIRST_PERSON_LEFT_HAND ? -1 : 1), -0.2, 0.0);
               }
            } else {
               float scale = (float)(2.0 + relic.getStatValue(stack, "oversized_grip", "multiplier") * 6.0);
               e.getPoseStack().scale(scale, scale, scale);
               e.getPoseStack().translate(0.0F, 0.0F, -0.075F);
            }
         }
      }
   }

   @EventBusSubscriber
   public static class CommonEvents {
      @SubscribeEvent
      public static void entityHit(Post e) {
         ItemStack stack = EntityUtils.findEquippedCurio(e.getSource().getDirectEntity(), (Item)ItemRegistry.GIANT_GLOVE.get());
         if (!e.getEntity().level().isClientSide
            && stack.getItem() instanceof GiantGloveItem relic
            && e.getSource().getDirectEntity() instanceof LivingEntity source
            && !source.getMainHandItem().isEmpty()) {
            relic.spreadRelicExperience(source, stack, 1);
         }
      }

      @SubscribeEvent
      public static void blockBreak(BreakEvent e) {
         ItemStack stack = EntityUtils.findEquippedCurio(e.getPlayer(), (Item)ItemRegistry.GIANT_GLOVE.get());
         if (!e.getPlayer().level().isClientSide
            && stack.getItem() instanceof GiantGloveItem relic
            && !(e.getState().getDestroySpeed(e.getLevel(), e.getPos()) <= 0.0F)
            && !e.getPlayer().getMainHandItem().isEmpty()) {
            relic.spreadRelicExperience(e.getPlayer(), stack, 1);
         }
      }

      @SubscribeEvent
      public static void playerTick(net.neoforged.neoforge.event.tick.EntityTickEvent.Post e) {
         if (e.getEntity() instanceof Player living && !living.level().isClientSide) {
            List<SlotResult> slots = EntitiesButCool.findEquippedSlots(living, (Item)ItemRegistry.GIANT_GLOVE.get());
            slots.forEach(
               slotResult -> {
                  ItemStack stack = slotResult.stack();
                  if (stack.getItem() instanceof GiantGloveItem relic) {
                     ResourceLocation var6 = ResourceLocation.fromNamespaceAndPath(
                        "reliquified_twilight_forest", "giant_glove_" + slotResult.slotContext().identifier()
                     );
                     GiantGloveItem.removeAttributes(slotResult.slotContext());
                     float multiplier = (float)relic.getStatValue(stack, "oversized_grip", "multiplier");
                     living.getMainHandItem().getAttributeModifiers().forEach(EquipmentSlotGroup.MAINHAND, (attributeHolder, attributeModifier) -> {
                        if (living.getAttribute(attributeHolder) instanceof AttributeInstance instance) {
                           if (instance.getBaseValue() < 0.0) {
                              instance.addOrUpdateTransientModifier(
                                 new AttributeModifier(var6, Math.abs(instance.getValue() * multiplier), Operation.ADD_VALUE)
                              );
                           } else {
                              instance.addOrUpdateTransientModifier(new AttributeModifier(var6, multiplier, Operation.ADD_MULTIPLIED_TOTAL));
                           }
                        }
                     });
                  }
               }
            );
         }
      }
   }
}
