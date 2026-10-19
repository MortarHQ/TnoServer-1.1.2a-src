package it.hurts.octostudios.reliquified_twilight_forest.item.relic;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.hurts.octostudios.reliquified_twilight_forest.init.ItemRegistry;
import it.hurts.octostudios.reliquified_twilight_forest.network.ScaledCloakWallClimbPacket;
import it.hurts.octostudios.reliquified_twilight_forest.util.MathButCool;
import it.hurts.sskirillss.relics.client.models.items.CurioModel;
import it.hurts.sskirillss.relics.init.DataComponentRegistry;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
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
import it.hurts.sskirillss.relics.items.relics.base.data.style.TooltipData;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import java.util.List;
import net.minecraft.client.Minecraft;
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
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

@EventBusSubscriber
public class ScaledCloakItem extends RelicItem implements IRenderableCurio {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("wall_crawler")
                     .stat(
                        StatData.builder("max_time")
                           .initialValue(60.0, 100.0)
                           .upgradeModifier(UpgradeOperation.ADD, 40.0)
                           .formatValue(MathButCool::ticksToSecondsAndRoundSingleDigit)
                           .build()
                     )
                     .maxLevel(10)
                     .build()
               )
               .ability(
                  AbilityData.builder("elusive_stare")
                     .requiredLevel(5)
                     .requiredPoints(2)
                     .stat(
                        StatData.builder("chance")
                           .initialValue(0.1, 0.25)
                           .upgradeModifier(UpgradeOperation.ADD, 0.1)
                           .formatValue(MathButCool::percentage)
                           .build()
                     )
                     .maxLevel(10)
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("wall_crawler").gem(GemShape.SQUARE, GemColor.YELLOW).build())
                     .source(LevelingSourceData.abilityBuilder("elusive_stare").gem(GemShape.SQUARE, GemColor.ORANGE).build())
                     .build()
               )
               .maxLevel(15)
               .build()
         )
         .style(
            StyleData.builder()
               .beams(BeamsData.builder().startColor(-16066048).endColor(85803).build())
               .tooltip(TooltipData.builder().borderTop(-12172211).borderBottom(-13028028).textured(true).build())
               .build()
         )
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      LivingEntity entity = slotContext.entity();
      Level level = entity.level();
      int time = (Integer)stack.getOrDefault(DataComponentRegistry.TIME, 0);
      boolean isColliding = false;
      if (level.isClientSide && entity == Minecraft.getInstance().player && stack.getItem() instanceof IRelicItem relic) {
         if (entity.horizontalCollision) {
            isColliding = true;
            Vec3 deltaMovement = entity.getDeltaMovement();
            float deltaY = time > 0 ? 0.1F : -0.07F;
            entity.setDeltaMovement(deltaMovement.x, deltaY, deltaMovement.z);
         }

         PacketDistributor.sendToServer(new ScaledCloakWallClimbPacket(isColliding), new CustomPacketPayload[0]);
      }
   }

   @SubscribeEvent
   public static void onDamage(LivingIncomingDamageEvent e) {
      LivingEntity entity = e.getEntity();
      Entity attacker = e.getSource().getEntity();
      EntityHitResult result = getEntityLookingAt(entity, 100.0);
      ItemStack stack = EntityUtils.findEquippedCurio(entity, (Item)ItemRegistry.SCALED_CLOAK.get());
      if (!entity.level().isClientSide
         && result != null
         && result.getEntity() == attacker
         && stack.getItem() instanceof ScaledCloakItem relic
         && !(entity.getRandom().nextFloat() > relic.getStatValue(stack, "elusive_stare", "chance"))) {
         relic.spreadRelicExperience(entity, stack, 1);
         entity.level().playSound(null, entity, SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.NEUTRAL, 0.6F, 1.35F);
         e.setCanceled(true);
      }
   }

   public static EntityHitResult getEntityLookingAt(LivingEntity livingEntity, double maxDistance) {
      Vec3 eyePosition = livingEntity.getEyePosition(1.0F);
      Vec3 lookVector = livingEntity.getViewVector(1.0F);
      Vec3 reachEnd = eyePosition.add(lookVector.scale(maxDistance));
      return ProjectileUtil.getEntityHitResult(
         livingEntity,
         eyePosition,
         reachEnd,
         livingEntity.getBoundingBox().expandTowards(lookVector.scale(maxDistance)).inflate(1.0),
         entity -> entity instanceof LivingEntity && entity.isAlive() && livingEntity.hasLineOfSight(entity) && entity != livingEntity,
         maxDistance * maxDistance
      );
   }

   public String getConfigRoute() {
      return "reliquified_twilight_forest";
   }

   @OnlyIn(Dist.CLIENT)
   public List<String> headParts() {
      return List.of("head");
   }

   @OnlyIn(Dist.CLIENT)
   public List<String> bodyParts() {
      return List.of("right_arm", "left_arm", "body");
   }

   @OnlyIn(Dist.CLIENT)
   public LayerDefinition constructLayerDefinition() {
      MeshDefinition meshdefinition = HumanoidModel.createMesh(new CubeDeformation(0.4F), 0.0F);
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition left_arm = partdefinition.addOrReplaceChild(
         "left_arm",
         CubeListBuilder.create().texOffs(11, 37).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.3F)).mirror(false),
         PartPose.offset(5.0F, 2.0F, 0.0F)
      );
      PartDefinition right_arm = partdefinition.addOrReplaceChild(
         "right_arm",
         CubeListBuilder.create().texOffs(11, 37).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.3F)),
         PartPose.offset(-5.0F, 2.0F, 0.0F)
      );
      PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -0.1913F, -0.0619F));
      PartDefinition cube_r1 = head.addOrReplaceChild(
         "cube_r1",
         CubeListBuilder.create().texOffs(36, 10).addBox(-2.5F, 0.0F, -3.0F, 7.0F, 3.0F, 5.0F, new CubeDeformation(0.05F)),
         PartPose.offsetAndRotation(-1.1F, 0.0F, 3.95F, -0.7854F, 0.0F, 0.0F)
      );
      PartDefinition body = partdefinition.addOrReplaceChild(
         "body",
         CubeListBuilder.create().texOffs(38, 41).addBox(-4.5F, 0.0F, -1.0F, 9.0F, 19.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 0.0F, 0.001F)
      );
      return LayerDefinition.create(meshdefinition, 64, 64);
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
      CurioModel model = this.getModel(stack);
      matrixStack.pushPose();
      LivingEntity entity = slotContext.entity();
      ICurioRenderer.followBodyRotations(entity, new HumanoidModel[]{model});
      model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
      model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, RenderType.entityCutoutNoCull(this.getTexture(stack)), stack.hasFoil());
      model.renderToBuffer(matrixStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY);
      matrixStack.popPose();
   }
}
