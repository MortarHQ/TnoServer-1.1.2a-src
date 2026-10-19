package it.hurts.octostudios.reliquified_twilight_forest.item.relic;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.hurts.octostudios.reliquified_twilight_forest.init.ItemRegistry;
import it.hurts.sskirillss.relics.client.models.items.CurioModel;
import it.hurts.sskirillss.relics.client.models.items.SidedCurioModel;
import it.hurts.sskirillss.relics.init.DataComponentRegistry;
import it.hurts.sskirillss.relics.items.relics.base.IRenderableCurio;
import it.hurts.sskirillss.relics.items.relics.base.RelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourceData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourcesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.GemColor;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.GemShape;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.items.relics.base.data.style.BeamsData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.StyleData;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

@EventBusSubscriber
public class MinotaurHoofItem extends RelicItem implements IRenderableCurio {
   private static final ResourceLocation MOVEMENT_MODIFIER = ResourceLocation.fromNamespaceAndPath("reliquified_twilight_forest", "momentum_rush");
   private static final int MAX_TIME = 60;

   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("momentum_rush")
                     .stat(
                        StatData.builder("max_speed_multiplier")
                           .initialValue(0.25, 0.3F)
                           .formatValue(value -> MathUtils.round(value * 100.0, 1))
                           .upgradeModifier(UpgradeOperation.ADD, 0.1F)
                           .build()
                     )
                     .stat(
                        StatData.builder("damage_reduction")
                           .initialValue(0.25, 0.3F)
                           .formatValue(value -> MathUtils.round(value * 100.0, 1))
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.125)
                           .build()
                     )
                     .stat(
                        StatData.builder("damage")
                           .initialValue(1.0, 2.0)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .upgradeModifier(UpgradeOperation.ADD, 1.0)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .step(125)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("momentum_rush").gem(GemShape.SQUARE, GemColor.PURPLE).build())
                     .build()
               )
               .build()
         )
         .style(StyleData.builder().beams(BeamsData.builder().startColor(-2080756).endColor(8929548).build()).build())
         .build();
   }

   public int getTime(ItemStack stack) {
      return (Integer)stack.getOrDefault(DataComponentRegistry.TIME, 0);
   }

   public void setTime(ItemStack stack, int time) {
      stack.set(DataComponentRegistry.TIME, Math.clamp(time, 0, 60));
   }

   public void addTime(ItemStack stack, int time) {
      this.setTime(stack, this.getTime(stack) + time);
   }

   public boolean isActive(ItemStack stack) {
      return this.getTime(stack) == 60;
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (stack.getItem() instanceof MinotaurHoofItem relic && slotContext.entity() instanceof Player player) {
         Level level = player.getCommandSenderWorld();
         if (!level.isClientSide()) {
            AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
            AttributeInstance knockbackResistance = player.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
            AttributeInstance stepHeight = player.getAttribute(Attributes.STEP_HEIGHT);
            if (movementSpeed != null && knockbackResistance != null && stepHeight != null) {
               double maxSpeedMultiplier = relic.getStatValue(stack, "momentum_rush", "max_speed_multiplier");
               int time = this.getTime(stack);
               this.addTime(stack, player.isSprinting() ? 1 : -1);
               if (time != 0) {
                  movementSpeed.addOrUpdateTransientModifier(
                     new AttributeModifier(MOVEMENT_MODIFIER, maxSpeedMultiplier * time / 60.0, Operation.ADD_MULTIPLIED_BASE)
                  );
                  knockbackResistance.addOrUpdateTransientModifier(new AttributeModifier(MOVEMENT_MODIFIER, time / 60.0F, Operation.ADD_VALUE));
                  stepHeight.addOrUpdateTransientModifier(new AttributeModifier(MOVEMENT_MODIFIER, this.isActive(stack) ? 0.55 : 0.0, Operation.ADD_VALUE));
                  if (relic.isActive(stack)) {
                     for (LivingEntity entity : level.getEntitiesOfClass(
                        LivingEntity.class, player.getBoundingBox().inflate(0.025), target -> !target.getStringUUID().equals(player.getStringUUID())
                     )) {
                        if (entity.hurt(player.damageSources().playerAttack(player), (float)relic.getStatValue(stack, "momentum_rush", "damage"))) {
                           relic.spreadRelicExperience(player, stack, 1);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      super.onUnequip(slotContext, newStack, stack);
      if (newStack.getItem() != stack.getItem()) {
         this.setTime(stack, 0);
         resetAttributes(slotContext.entity());
      }
   }

   private static void resetAttributes(LivingEntity livingEntity) {
      AttributeInstance movementSpeed = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
      AttributeInstance knockbackResistance = livingEntity.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
      AttributeInstance stepHeight = livingEntity.getAttribute(Attributes.STEP_HEIGHT);
      if (movementSpeed != null && knockbackResistance != null && stepHeight != null) {
         if (movementSpeed.hasModifier(MOVEMENT_MODIFIER)) {
            movementSpeed.removeModifier(MOVEMENT_MODIFIER);
         }

         if (knockbackResistance.hasModifier(MOVEMENT_MODIFIER)) {
            knockbackResistance.removeModifier(MOVEMENT_MODIFIER);
         }

         if (stepHeight.hasModifier(MOVEMENT_MODIFIER)) {
            stepHeight.removeModifier(MOVEMENT_MODIFIER);
         }
      }
   }

   @SubscribeEvent
   public static void onDamageTaken(LivingIncomingDamageEvent e) {
      ItemStack stack = EntityUtils.findEquippedCurio(e.getEntity(), (Item)ItemRegistry.MINOTAUR_HOOF.get());
      if (stack.getItem() instanceof MinotaurHoofItem relic) {
         double var5 = e.getAmount() * Mth.clamp(1.0 - relic.getStatValue(stack, "momentum_rush", "damage_reduction"), 0.0, 1.0);
         if (relic.isActive(stack)) {
            e.setAmount((float)var5);
         }
      }
   }

   public String getConfigRoute() {
      return "reliquified_twilight_forest";
   }

   @OnlyIn(Dist.CLIENT)
   public CurioModel getModel(ItemStack stack) {
      return new SidedCurioModel(stack.getItem());
   }

   @OnlyIn(Dist.CLIENT)
   public List<String> bodyParts() {
      return List.of("right_leg", "left_leg");
   }

   @OnlyIn(Dist.CLIENT)
   public LayerDefinition constructLayerDefinition() {
      MeshDefinition meshdefinition = HumanoidModel.createMesh(new CubeDeformation(0.4F), 0.0F);
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition right_leg = partdefinition.addOrReplaceChild(
         "right_leg",
         CubeListBuilder.create().texOffs(16, 0).addBox(-2.0F, 10.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.5F)),
         PartPose.offset(-1.9F, 12.0F, 0.0F)
      );
      PartDefinition left_leg = partdefinition.addOrReplaceChild(
         "left_leg",
         CubeListBuilder.create().texOffs(16, 0).addBox(-2.0F, 10.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.5F)),
         PartPose.offset(-1.9F, 12.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 32, 32);
   }

   @OnlyIn(Dist.CLIENT)
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
         matrixStack.translate(0.0F, 0.0F, 0.0F);
         matrixStack.scale(1.0047F, 1.0047F, 1.0047F);
         sidedModel.renderToBuffer(matrixStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY);
         matrixStack.popPose();
      }
   }
}
