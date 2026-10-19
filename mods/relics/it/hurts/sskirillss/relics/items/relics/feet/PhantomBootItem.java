package it.hurts.sskirillss.relics.items.relics.feet;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.hurts.sskirillss.relics.blocks.PhantomBlock;
import it.hurts.sskirillss.relics.client.models.items.CurioModel;
import it.hurts.sskirillss.relics.client.models.items.SidedCurioModel;
import it.hurts.sskirillss.relics.init.BlockRegistry;
import it.hurts.sskirillss.relics.init.DataComponentRegistry;
import it.hurts.sskirillss.relics.items.relics.base.IRenderableCurio;
import it.hurts.sskirillss.relics.items.relics.base.RelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.CastData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastStage;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastType;
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
import it.hurts.sskirillss.relics.items.relics.base.data.loot.misc.LootEntries;
import it.hurts.sskirillss.relics.items.relics.base.data.research.ResearchData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.StyleData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.TooltipData;
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.WorldUtils;
import java.util.List;
import java.util.stream.IntStream;
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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector2f;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class PhantomBootItem extends RelicItem implements IRenderableCurio {
   @Override
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("bridge")
                     .active(CastData.builder().type(CastType.TOGGLEABLE).build())
                     .stat(
                        StatData.builder("duration")
                           .initialValue(0.25, 1.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.5)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 11, 8)
                           .star(1, 7, 13)
                           .star(2, 15, 14)
                           .star(3, 11, 21)
                           .star(4, 3, 29)
                           .star(5, 19, 29)
                           .link(0, 1)
                           .link(0, 2)
                           .link(0, 3)
                           .link(3, 4)
                           .link(3, 5)
                           .link(4, 5)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(10)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("bridge").initialValue(1).gem(GemShape.SQUARE, GemColor.PURPLE).build())
                     .build()
               )
               .build()
         )
         .style(StyleData.builder().tooltip(TooltipData.builder().borderTop(-10812015).borderBottom(-12779416).textured(true).build()).build())
         .loot(LootData.builder().entry(LootEntries.THE_END, LootEntries.END_LIKE).build())
         .build();
   }

   @Override
   public void castActiveAbility(ItemStack stack, Player player, String ability, CastType type, CastStage stage) {
      if (ability.equals("bridge") && stage == CastStage.START && this.isToggled(stack)) {
         Vec3 motion = player.getDeltaMovement();
         if (motion.y <= -0.5) {
            player.setDeltaMovement(motion.x, -(motion.y / 1.5), motion.z);
            BlockState state = ((PhantomBlock)BlockRegistry.PHANTOM_BLOCK.get()).defaultBlockState();
            state.getBlock().fallOn(player.level(), state, player.blockPosition(), player, player.fallDistance);
         }
      }
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player) {
         Level level = player.getCommandSenderWorld();
         if (!level.isClientSide()) {
            PhantomBlock block = (PhantomBlock)BlockRegistry.PHANTOM_BLOCK.get();
            boolean onBridge = level.getBlockState(player.blockPosition().atY((int)Math.floor(WorldUtils.getGroundHeight(player, player.position(), 8))))
                     .getBlock()
                  == block
               || player.isColliding(player.blockPosition(), block.defaultBlockState());
            int time = this.getTime(stack);
            if (this.isToggled(stack)) {
               if (onBridge) {
                  if (player.getKnownMovement().multiply(1.0, 0.0, 1.0).length() > 0.0) {
                     if (time > 0) {
                        this.addTime(stack, -1);
                     }
                  } else if (time < this.getMaxTime(stack)) {
                     this.addTime(stack, 1);
                  } else {
                     this.setToggled(stack, false);
                  }
               } else if (time > 0) {
                  this.addTime(stack, -1);
               }

               if (!player.isShiftKeyDown() && this.isAbilityTicking(stack, "bridge")) {
                  Vec3 horizontalMotion = player.getKnownMovement().multiply(1.0, 0.0, 1.0);
                  float motionSpeed = (float)horizontalMotion.length();
                  float minorRadius = 1.5F;
                  float minSpeedThreshold = 0.1F;
                  float lengthFactor = 1.5F;
                  float majorRadius = motionSpeed < minSpeedThreshold ? minorRadius : minorRadius + motionSpeed * lengthFactor;
                  Vector2f direction = motionSpeed < minSpeedThreshold
                     ? new Vector2f(1.0F, 0.0F)
                     : new Vector2f((float)horizontalMotion.x, (float)horizontalMotion.z).normalize();
                  Vec3 center = motionSpeed < minSpeedThreshold
                     ? player.position()
                     : player.position().add(direction.x * majorRadius, 0.0, direction.y * majorRadius);
                  Vector2f perpendicularDirection = new Vector2f(-direction.y, direction.x);
                  float step = 0.15F;
                  int baseY = (int)Math.floor(player.getY()) - 1;
                  int stepsCount = (int)(2.0F / step) + 1;
                  IntStream.range(0, stepsCount)
                     .boxed()
                     .flatMap(i -> {
                        float alpha = -1.0F + i.intValue() * step;
                        return IntStream.range(0, stepsCount).mapToObj(j -> new float[]{alpha, -1.0F + j * step});
                     })
                     .filter(pair -> pair[0] * pair[0] + pair[1] * pair[1] <= 1.0F)
                     .forEach(
                        pair -> {
                           double worldX = center.x() + pair[0] * majorRadius * direction.x + pair[1] * minorRadius * perpendicularDirection.x;
                           double worldZ = center.z() + pair[0] * majorRadius * direction.y + pair[1] * minorRadius * perpendicularDirection.y;
                           BlockPos blockPos = new BlockPos((int)Math.floor(worldX), baseY, (int)Math.floor(worldZ));
                           if (level.isEmptyBlock(blockPos)
                              && level.setBlockAndUpdate(blockPos, ((PhantomBlock)BlockRegistry.PHANTOM_BLOCK.get()).defaultBlockState())
                              && level.getRandom().nextInt(10) == 0) {
                              this.spreadRelicExperience(player, stack, 1);
                           }
                        }
                     );
               }
            } else if (player.onGround() && !onBridge) {
               this.setToggled(stack, true);
            }
         }
      }
   }

   public int getMaxTime(ItemStack stack) {
      return (int)Math.round(this.getStatValue(stack, "bridge", "duration") * 20.0);
   }

   public int getTime(ItemStack stack) {
      return (Integer)stack.getOrDefault(DataComponentRegistry.TIME, 0);
   }

   public void setTime(ItemStack stack, int time) {
      stack.set(DataComponentRegistry.TIME, Mth.clamp(time, 0, this.getMaxTime(stack)));
   }

   public void addTime(ItemStack stack, int time) {
      this.setTime(stack, this.getTime(stack) + time);
   }

   public boolean isToggled(ItemStack stack) {
      return (Boolean)stack.getOrDefault(DataComponentRegistry.TOGGLED, true);
   }

   public void setToggled(ItemStack stack, boolean toggled) {
      stack.set(DataComponentRegistry.TOGGLED, toggled);
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
         sidedModel.renderToBuffer(matrixStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY);
         matrixStack.popPose();
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public LayerDefinition constructLayerDefinition() {
      MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0.4F), 0.0F);
      PartDefinition right_leg = mesh.getRoot()
         .addOrReplaceChild(
            "right_leg",
            CubeListBuilder.create()
               .texOffs(0, 0)
               .addBox(-2.5F, 5.25F, -2.5F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
               .texOffs(20, 0)
               .addBox(-2.5F, 8.25F, -5.5F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
               .texOffs(20, 7)
               .addBox(-2.5F, 8.25F, -5.5F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.305F))
               .texOffs(0, 12)
               .addBox(-2.5F, 5.25F, -2.5F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.3F)),
            PartPose.offset(-1.0F, 11.75F, 2.5F)
         );
      PartDefinition left_leg = mesh.getRoot()
         .addOrReplaceChild(
            "left_leg",
            CubeListBuilder.create()
               .texOffs(0, 0)
               .addBox(-2.5F, 5.25F, -2.5F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
               .texOffs(20, 0)
               .addBox(-2.5F, 8.25F, -5.5F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
               .texOffs(20, 7)
               .addBox(-2.5F, 8.25F, -5.5F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.305F))
               .texOffs(0, 12)
               .addBox(-2.5F, 5.25F, -2.5F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.3F)),
            PartPose.offset(-1.0F, 11.75F, 2.5F)
         );
      return LayerDefinition.create(mesh, 64, 64);
   }

   @Override
   public List<String> bodyParts() {
      return Lists.newArrayList(new String[]{"right_leg", "left_leg"});
   }
}
