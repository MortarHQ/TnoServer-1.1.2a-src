package it.hurts.sskirillss.relics.items.relics.belt;

import com.google.common.collect.Lists;
import it.hurts.sskirillss.relics.init.ItemRegistry;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.IRenderableCurio;
import it.hurts.sskirillss.relics.items.relics.base.RelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicAttributeModifier;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicSlotModifier;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.misc.LootEntries;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.MathUtils;
import java.util.List;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent.Start;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent.Stop;
import top.theillusivec4.curios.api.SlotContext;

public class DrownedBeltItem extends RelicItem implements IRenderableCurio {
   @Override
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("slots")
                     .requiredPoints(2)
                     .stat(
                        StatData.builder("charm")
                           .initialValue(0.0, 2.0)
                           .upgradeModifier(UpgradeOperation.ADD, 1.0)
                           .formatValue(value -> (int)MathUtils.round(value, 0))
                           .build()
                     )
                     .build()
               )
               .ability(
                  AbilityData.builder("anchor")
                     .stat(
                        StatData.builder("slowness")
                           .initialValue(0.5, 0.25)
                           .upgradeModifier(UpgradeOperation.ADD, -0.05)
                           .formatValue(value -> (int)(MathUtils.round(value, 2) * 100.0))
                           .build()
                     )
                     .stat(
                        StatData.builder("sinking")
                           .initialValue(5.0, 3.0)
                           .upgradeModifier(UpgradeOperation.ADD, -0.1)
                           .formatValue(value -> (int)(MathUtils.round(value, 2) * 100.0))
                           .build()
                     )
                     .build()
               )
               .ability(
                  AbilityData.builder("pressure")
                     .stat(
                        StatData.builder("damage")
                           .initialValue(1.25, 2.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.1)
                           .formatValue(value -> (int)(MathUtils.round(value, 2) * 100.0))
                           .build()
                     )
                     .build()
               )
               .ability(
                  AbilityData.builder("riptide")
                     .stat(
                        StatData.builder("cooldown")
                           .initialValue(10.0, 5.0)
                           .upgradeModifier(UpgradeOperation.ADD, -0.5)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .leveling(new LevelingData(100, 10, 100))
         .loot(LootData.builder().entry(LootEntries.AQUATIC).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player) {
         if (player.isEyeInFluid(FluidTags.WATER) && !player.onGround()) {
            EntityUtils.applyAttribute(player, stack, Attributes.GRAVITY, (float)this.getStatValue(stack, "anchor", "sinking"), Operation.ADD_MULTIPLIED_TOTAL);
         } else {
            EntityUtils.removeAttribute(player, stack, Attributes.GRAVITY, Operation.ADD_MULTIPLIED_TOTAL);
         }
      }
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      EntityUtils.removeAttribute(slotContext.entity(), stack, Attributes.GRAVITY, Operation.ADD_MULTIPLIED_TOTAL);
   }

   @Override
   public RelicSlotModifier getSlotModifiers(ItemStack stack) {
      return RelicSlotModifier.builder().modifier("charm", (int)Math.round(this.getStatValue(stack, "slots", "charm"))).build();
   }

   @Override
   public RelicAttributeModifier getRelicAttributeModifiers(ItemStack stack) {
      return RelicAttributeModifier.builder()
         .attribute(new RelicAttributeModifier.Modifier(NeoForgeMod.SWIM_SPEED, (float)(-this.getStatValue(stack, "anchor", "slowness"))))
         .build();
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public LayerDefinition constructLayerDefinition() {
      MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0.4F), 0.0F);
      PartDefinition bone = mesh.getRoot()
         .addOrReplaceChild(
            "body",
            CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, 9.0F, -2.5F, 9.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, 0.0F, 0.0F)
         );
      bone.addOrReplaceChild(
         "cube_r1",
         CubeListBuilder.create().texOffs(0, 7).addBox(-2.05F, -1.5F, -0.5F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 10.0F, -2.5F, -0.1295F, -0.0378F, 0.0894F)
      );
      return LayerDefinition.create(mesh, 32, 32);
   }

   @Override
   public List<String> headParts() {
      return Lists.newArrayList(new String[]{"body"});
   }

   @EventBusSubscriber(modid = "relics")
   public static class Events {
      @SubscribeEvent
      public static void onEntityHurt(LivingIncomingDamageEvent event) {
         if (event.getSource().getEntity() instanceof Player player && player.isUnderWater() && event.getEntity().isUnderWater()) {
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ItemRegistry.DROWNED_BELT.get());
            if (stack.getItem() instanceof IRelicItem relic) {
               event.setAmount((float)(event.getAmount() * relic.getStatValue(stack, "pressure", "damage")));
            }
         }
      }

      @SubscribeEvent
      public static void onItemUseStart(Start event) {
         ItemStack stack = event.getItem();
         if (event.getEntity() instanceof Player player && stack.getItem() == Items.TRIDENT && player.getCooldowns().isOnCooldown(stack.getItem())) {
            event.setCanceled(true);
         }
      }

      @SubscribeEvent
      public static void onItemUseFinish(Stop event) {
         ItemStack trident = event.getItem();
         if (event.getEntity() instanceof Player player && trident.getItem() == Items.TRIDENT) {
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ItemRegistry.DROWNED_BELT.get());
            if (stack.getItem() instanceof IRelicItem relic) {
               int var8 = trident.getItem().getUseDuration(trident, player) - event.getDuration();
               int enchantment = trident.getEnchantmentLevel(player.level().holderLookup(Registries.ENCHANTMENT).getOrThrow(Enchantments.RIPTIDE));
               if (var8 >= 10 && enchantment > 0) {
                  relic.spreadRelicExperience(player, stack, enchantment);
                  player.getCooldowns().addCooldown(trident.getItem(), (int)Math.round(relic.getStatValue(stack, "riptide", "cooldown") * enchantment * 20.0));
               }
            }
         }
      }
   }
}
