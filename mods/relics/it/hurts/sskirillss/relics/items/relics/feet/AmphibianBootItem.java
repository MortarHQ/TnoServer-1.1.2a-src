package it.hurts.sskirillss.relics.items.relics.feet;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.hurts.sskirillss.relics.client.models.items.CurioModel;
import it.hurts.sskirillss.relics.client.models.items.SidedCurioModel;
import it.hurts.sskirillss.relics.init.DataComponentRegistry;
import it.hurts.sskirillss.relics.init.ItemRegistry;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.IRenderableCurio;
import it.hurts.sskirillss.relics.items.relics.base.RelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
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
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.living.LivingBreatheEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class AmphibianBootItem extends RelicItem implements IRenderableCurio {
   @Override
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("swimming")
                     .stat(
                        StatData.builder("speed")
                           .initialValue(0.005, 0.01)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.25)
                           .formatValue(value -> MathUtils.round(value * 100.0 * 4.0, 1))
                           .build()
                     )
                     .stat(
                        StatData.builder("duration")
                           .initialValue(15.0, 35.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.2)
                           .formatValue(value -> MathUtils.round(value / 5.0, 1))
                           .build()
                     )
                     .build()
               )
               .ability(
                  AbilityData.builder("slipping")
                     .stat(
                        StatData.builder("speed")
                           .initialValue(0.005, 0.01)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.15)
                           .formatValue(value -> MathUtils.round(value * 100.0 * 4.0, 1))
                           .build()
                     )
                     .stat(
                        StatData.builder("duration")
                           .initialValue(15.0, 25.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.15)
                           .formatValue(value -> MathUtils.round(value / 5.0, 1))
                           .build()
                     )
                     .build()
               )
               .ability(
                  AbilityData.builder("gills")
                     .stat(
                        StatData.builder("chance")
                           .initialValue(0.01, 0.1)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.35)
                           .formatValue(value -> MathUtils.round(value * 100.0, 1))
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
         int var7 = (Integer)stack.getOrDefault(DataComponentRegistry.CHARGE, 0);
         boolean isSwimming = false;
         boolean isSlipping = false;
         if (player.isSwimming()) {
            isSwimming = true;
            if (player.tickCount % 20 == 0) {
               this.spreadRelicExperience(player, stack, 1);
            }

            if (var7 < this.getStatValue(stack, "swimming", "duration")) {
               stack.set(DataComponentRegistry.CHARGE, var7 + 1);
            }
         }

         EntityUtils.removeAttribute(player, stack, NeoForgeMod.SWIM_SPEED, Operation.ADD_MULTIPLIED_TOTAL);
         if (var7 > 0) {
            EntityUtils.applyAttribute(
               player, stack, NeoForgeMod.SWIM_SPEED, (float)(var7 * this.getStatValue(stack, "swimming", "speed")), Operation.ADD_MULTIPLIED_TOTAL
            );
         }

         if (player.isSprinting()
            && player.level().isRainingAt(player.blockPosition())
            && !player.isShiftKeyDown()
            && !player.isInWater()
            && !player.isInLava()) {
            isSlipping = true;
            if (player.tickCount % 20 == 0) {
               this.spreadRelicExperience(player, stack, 1);
            }

            if (var7 < this.getStatValue(stack, "slipping", "duration") && player.tickCount % 4 == 0) {
               stack.set(DataComponentRegistry.CHARGE, var7 + 1);
            }
         }

         if (!isSwimming && !isSlipping && var7 > 0) {
            stack.set(DataComponentRegistry.CHARGE, --var7);
         }

         EntityUtils.removeAttribute(player, stack, Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_TOTAL);
         if (var7 > 0) {
            EntityUtils.applyAttribute(
               player, stack, Attributes.MOVEMENT_SPEED, (float)(var7 * this.getStatValue(stack, "slipping", "speed")), Operation.ADD_MULTIPLIED_TOTAL
            );
            EntityUtils.applyAttribute(player, stack, Attributes.STEP_HEIGHT, 0.6F, Operation.ADD_VALUE);
         } else {
            EntityUtils.removeAttribute(player, stack, Attributes.STEP_HEIGHT, Operation.ADD_VALUE);
         }
      }
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      if (stack.getItem() != newStack.getItem()) {
         EntityUtils.removeAttribute(slotContext.entity(), stack, NeoForgeMod.SWIM_SPEED, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.removeAttribute(slotContext.entity(), stack, Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.removeAttribute(slotContext.entity(), stack, Attributes.STEP_HEIGHT, Operation.ADD_VALUE);
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public CurioModel getModel(ItemStack stack) {
      return new SidedCurioModel(stack.getItem());
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public <T extends LivingEntity, M extends EntityModel<T>> void render(
      ItemStack stack,
      SlotContext slotContext,
      PoseStack matrixStack,
      RenderLayerParent<T, M> renderLayerParent,
      MultiBufferSource renderTypeBuffer,
      int light,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (this.getModel(stack) instanceof SidedCurioModel sidedModel) {
         sidedModel.setSlot(slotContext.index());
         matrixStack.pushPose();
         LivingEntity entity = slotContext.entity();
         sidedModel.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
         sidedModel.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         ICurioRenderer.followBodyRotations(entity, new HumanoidModel[]{sidedModel});
         VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(
            renderTypeBuffer, RenderType.armorCutoutNoCull(this.getTexture(stack)), stack.hasFoil()
         );
         matrixStack.translate(0.0F, 0.0F, -0.025F);
         sidedModel.renderToBuffer(matrixStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY);
         matrixStack.popPose();
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public LayerDefinition constructLayerDefinition() {
      MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0.4F), 0.0F);
      PartDefinition rightLeg = mesh.getRoot()
         .addOrReplaceChild(
            "right_leg",
            CubeListBuilder.create()
               .texOffs(0, 9)
               .addBox(-2.9F, 5.5F, -2.5F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
               .texOffs(0, 1)
               .addBox(-2.9F, 5.5F, -2.5F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.175F))
               .texOffs(18, 9)
               .addBox(-2.9F, 9.5F, -4.5F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
            PartPose.offset(1.9F, 12.0F, 0.5F)
         );
      rightLeg.addOrReplaceChild(
         "cube_r1",
         CubeListBuilder.create().texOffs(0, -1).addBox(1.5F, 6.5F, 4.4F, 0.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.3927F, 0.0F)
      );
      rightLeg.addOrReplaceChild(
         "cube_r2",
         CubeListBuilder.create().texOffs(0, -1).mirror().addBox(-1.325F, 6.5F, 4.2F, 0.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.3927F, 0.0F)
      );
      PartDefinition leftLeg = mesh.getRoot()
         .addOrReplaceChild(
            "left_leg",
            CubeListBuilder.create()
               .texOffs(0, 9)
               .addBox(-2.9F, 5.5F, -2.5F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
               .texOffs(0, 1)
               .addBox(-2.9F, 5.5F, -2.5F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.175F))
               .texOffs(18, 9)
               .addBox(-2.9F, 9.5F, -4.5F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
            PartPose.offset(1.9F, 12.0F, 0.5F)
         );
      leftLeg.addOrReplaceChild(
         "cube_r1",
         CubeListBuilder.create().texOffs(0, -1).addBox(1.5F, 6.5F, 4.4F, 0.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.3927F, 0.0F)
      );
      leftLeg.addOrReplaceChild(
         "cube_r2",
         CubeListBuilder.create().texOffs(0, -1).mirror().addBox(-1.325F, 6.5F, 4.2F, 0.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.3927F, 0.0F)
      );
      return LayerDefinition.create(mesh, 64, 64);
   }

   @Override
   public List<String> bodyParts() {
      return Lists.newArrayList(new String[]{"right_leg", "left_leg"});
   }

   @EventBusSubscriber
   public static class Events {
      @SubscribeEvent
      public static void onLivingBreath(LivingBreatheEvent event) {
         LivingEntity entity = event.getEntity();
         ItemStack stack = EntityUtils.findEquippedCurio(entity, (Item)ItemRegistry.AMPHIBIAN_BOOT.get());
         if (stack.getItem() instanceof IRelicItem relic) {
            double var6 = relic.getStatValue(stack, "gills", "chance");
            if (event.getConsumeAirAmount() > 0 && entity.getRandom().nextDouble() <= var6) {
               event.setConsumeAirAmount(0);
            }
         }
      }
   }
}
