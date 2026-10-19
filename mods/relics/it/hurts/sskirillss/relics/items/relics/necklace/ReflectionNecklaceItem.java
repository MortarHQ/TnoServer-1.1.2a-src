package it.hurts.sskirillss.relics.items.relics.necklace;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.hurts.sskirillss.relics.client.models.items.CurioModel;
import it.hurts.sskirillss.relics.entities.StalactiteEntity;
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
import it.hurts.sskirillss.relics.items.relics.base.data.style.StyleData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.TooltipData;
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
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class ReflectionNecklaceItem extends RelicItem implements IRenderableCurio {
   @Override
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("explode")
                     .stat(
                        StatData.builder("capacity")
                           .initialValue(20.0, 60.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.35)
                           .formatValue(value -> (int)MathUtils.round(value, 0))
                           .build()
                     )
                     .stat(
                        StatData.builder("damage")
                           .initialValue(0.25, 0.5)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.5)
                           .formatValue(value -> MathUtils.round(value, 2))
                           .build()
                     )
                     .stat(
                        StatData.builder("stun")
                           .initialValue(0.05, 0.25)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.25)
                           .formatValue(value -> MathUtils.round(value, 2))
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .leveling(new LevelingData(100, 10, 200))
         .style(StyleData.builder().tooltip(TooltipData.builder().borderTop(-16729345).borderBottom(-16740183).textured(true).build()).build())
         .loot(LootData.builder().entry(LootEntries.NETHER_LIKE, LootEntries.THE_NETHER).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player && player.tickCount % 20 == 0) {
         int time = (Integer)stack.getOrDefault(DataComponentRegistry.TIME, 0);
         double charge = ((Integer)stack.getOrDefault(DataComponentRegistry.CHARGE, 0)).intValue();
         if (time > 0 && charge < this.getStatValue(stack, "explode", "capacity")) {
            stack.set(DataComponentRegistry.TIME, --time);
         } else if (charge > 0.0) {
            Level level = player.level();
            RandomSource random = player.getRandom();
            float size = (float)(Math.log(charge) * 0.6F);
            float speed = (float)(0.35F + charge * 0.001F);

            for (float i = -size; i <= size; i++) {
               for (float j = -size; j <= size; j++) {
                  for (float k = -size; k <= size; k++) {
                     double d3 = j + (random.nextDouble() - random.nextDouble());
                     double d4 = i + (random.nextDouble() - random.nextDouble());
                     double d5 = k + (random.nextDouble() - random.nextDouble());
                     double d6 = (double)Mth.sqrt((float)(d3 * d3 + d4 * d4 + d5 * d5)) / speed + random.nextGaussian();
                     Vec3 motion = new Vec3(d3 / d6, d4 / d6, d5 / d6);
                     float mul = player.getBbHeight() / 1.5F;
                     Vec3 pos = player.position().add(0.0, mul, 0.0).add(motion.normalize().multiply(mul, mul, mul));
                     if (!level.getBlockState(new BlockPos((int)pos.x, (int)pos.y, (int)pos.z)).blocksMotion()) {
                        StalactiteEntity stalactite = new StalactiteEntity(
                           level,
                           (float)(charge * this.getStatValue(stack, "explode", "damage")),
                           (float)(charge * this.getStatValue(stack, "explode", "stun"))
                        );
                        stalactite.setOwner(player);
                        stalactite.setPos(pos);
                        stalactite.setDeltaMovement(motion);
                        level.addFreshEntity(stalactite);
                     }
                  }
               }
            }

            this.spreadRelicExperience(player, stack, (int)Math.floor(charge / 10.0));
            stack.set(DataComponentRegistry.CHARGE, 0);
            stack.set(DataComponentRegistry.TIME, 0);
         }
      }
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
      CurioModel model = this.getModel(stack);
      matrixStack.pushPose();
      LivingEntity entity = slotContext.entity();
      model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
      model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      ICurioRenderer.translateIfSneaking(matrixStack, entity);
      ICurioRenderer.rotateIfSneaking(matrixStack, entity);
      ICurioRenderer.followBodyRotations(entity, new HumanoidModel[]{model});
      VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, RenderType.armorCutoutNoCull(this.getTexture(stack)), stack.hasFoil());
      matrixStack.scale(0.5F, 0.5F, 0.5F);
      model.renderToBuffer(matrixStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY);
      matrixStack.scale(2.0F, 2.0F, 2.0F);
      matrixStack.popPose();
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public LayerDefinition constructLayerDefinition() {
      MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0.4F), 0.0F);
      PartDefinition bone = mesh.getRoot()
         .addOrReplaceChild(
            "body",
            CubeListBuilder.create()
               .texOffs(0, 0)
               .addBox(-8.0F, 0.0F, -4.15F, 16.0F, 7.0F, 8.0F, new CubeDeformation(0.5F))
               .texOffs(0, 0)
               .addBox(-1.5F, 5.125F, -5.15F, 3.0F, 5.0F, 1.0F, new CubeDeformation(-0.075F))
               .texOffs(0, 15)
               .addBox(-1.0F, 6.375F, -5.775F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.25F)),
            PartPose.offset(0.0F, 0.0F, 0.0F)
         );
      bone.addOrReplaceChild(
         "cube_r1",
         CubeListBuilder.create().texOffs(6, 15).addBox(-0.675F, -1.325F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 10.475F, -4.65F, 0.0F, 0.0F, -0.7854F)
      );
      return LayerDefinition.create(mesh, 64, 64);
   }

   @Override
   public List<String> headParts() {
      return Lists.newArrayList(new String[]{"body"});
   }

   @EventBusSubscriber(modid = "relics")
   public static class ReflectionNecklaceServerEvents {
      @SubscribeEvent
      public static void onEntityHurt(LivingIncomingDamageEvent event) {
         if (event.getEntity() instanceof Player) {
            ItemStack stack = EntityUtils.findEquippedCurio(event.getEntity(), (Item)ItemRegistry.REFLECTION_NECKLACE.get());
            if (stack.getItem() instanceof IRelicItem relic) {
               double var7 = ((Integer)stack.getOrDefault(DataComponentRegistry.CHARGE, 0)).intValue();
               double capacity = relic.getStatValue(stack, "explode", "capacity");
               if (var7 < capacity) {
                  stack.set(DataComponentRegistry.CHARGE, (int)Math.min(capacity, var7 + event.getAmount()));
                  stack.set(DataComponentRegistry.TIME, 5);
               }
            }
         }
      }
   }
}
