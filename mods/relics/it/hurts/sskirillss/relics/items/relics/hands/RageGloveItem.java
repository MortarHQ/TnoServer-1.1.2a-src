package it.hurts.sskirillss.relics.items.relics.hands;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.hurts.sskirillss.relics.client.models.items.CurioModel;
import it.hurts.sskirillss.relics.client.models.items.SidedCurioModel;
import it.hurts.sskirillss.relics.client.models.items.SidedFPRCurioModel;
import it.hurts.sskirillss.relics.init.DataComponentRegistry;
import it.hurts.sskirillss.relics.init.EffectRegistry;
import it.hurts.sskirillss.relics.init.ItemRegistry;
import it.hurts.sskirillss.relics.init.SoundRegistry;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.IRenderableCurio;
import it.hurts.sskirillss.relics.items.relics.base.RelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.CastData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastStage;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastType;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.misc.LootEntries;
import it.hurts.sskirillss.relics.network.NetworkHandler;
import it.hurts.sskirillss.relics.network.packets.PacketPlayerMotion;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.ParticleUtils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class RageGloveItem extends RelicItem implements IRenderableCurio {
   @Override
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("rage")
                     .maxLevel(10)
                     .stat(
                        StatData.builder("incoming_damage")
                           .initialValue(0.05, 0.025)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.05)
                           .formatValue(value -> MathUtils.round(MathUtils.round(value, 3) * 100.0, 3))
                           .build()
                     )
                     .stat(
                        StatData.builder("dealt_damage")
                           .initialValue(0.025, 0.075)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.1)
                           .formatValue(value -> MathUtils.round(MathUtils.round(value, 3) * 100.0, 3))
                           .build()
                     )
                     .stat(
                        StatData.builder("duration")
                           .initialValue(2.0, 4.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.1)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .build()
               )
               .ability(
                  AbilityData.builder("phlebotomy")
                     .requiredLevel(5)
                     .maxLevel(10)
                     .stat(
                        StatData.builder("heal")
                           .initialValue(1.0E-4, 2.5E-4)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.1)
                           .formatValue(value -> MathUtils.round(MathUtils.round(value, 5) * 20.0, 5))
                           .build()
                     )
                     .stat(
                        StatData.builder("movement_speed")
                           .initialValue(0.01, 0.025)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.1)
                           .formatValue(value -> MathUtils.round(MathUtils.round(value, 3) * 100.0, 3))
                           .build()
                     )
                     .stat(
                        StatData.builder("attack_speed")
                           .initialValue(0.005, 0.01)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.05)
                           .formatValue(value -> MathUtils.round(MathUtils.round(value, 3) * 100.0, 3))
                           .build()
                     )
                     .build()
               )
               .ability(
                  AbilityData.builder("spurt")
                     .requiredLevel(10)
                     .maxLevel(10)
                     .active(CastData.builder().type(CastType.INSTANTANEOUS).build())
                     .stat(
                        StatData.builder("damage")
                           .initialValue(0.1, 0.25)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.1)
                           .formatValue(value -> MathUtils.round(value, 2))
                           .build()
                     )
                     .stat(
                        StatData.builder("distance")
                           .initialValue(3.0, 8.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.3)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .stat(
                        StatData.builder("cooldown")
                           .initialValue(20.0, 15.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_TOTAL, -0.075)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .leveling(new LevelingData(100, 20, 100))
         .loot(LootData.builder().entry(LootEntries.NETHER_LIKE, LootEntries.THE_NETHER).build())
         .build();
   }

   @Override
   public void castActiveAbility(ItemStack stack, Player player, String ability, CastType type, CastStage stage) {
      Level level = player.getCommandSenderWorld();
      RandomSource random = level.getRandom();
      if (ability.equals("spurt")) {
         int stacks = (Integer)stack.getOrDefault(DataComponentRegistry.CHARGE, 0);
         double maxDistance = this.getStatValue(stack, "spurt", "distance");
         Vec3 view = player.getViewVector(0.0F);
         Vec3 eyeVec = player.getEyePosition(0.0F);
         BlockHitResult ray = level.clip(
            new ClipContext(eyeVec, eyeVec.add(view.x * maxDistance, view.y * maxDistance, view.z * maxDistance), Block.COLLIDER, Fluid.NONE, player)
         );
         Vec3 current = player.position();
         Vec3 target = ray.getLocation();
         int distance = (int)Math.ceil(current.distanceTo(target));
         if (distance <= 0) {
            return;
         }

         Vec3 motion = player.getDeltaMovement().add(target.subtract(current).normalize());
         player.teleportTo(target.x, target.y, target.z);
         if (!level.isClientSide()) {
            NetworkHandler.sendToClient(new PacketPlayerMotion(motion.x, motion.y, motion.z), (ServerPlayer)player);
            this.setAbilityCooldown(stack, "spurt", (int)Math.round(this.getStatValue(stack, "spurt", "cooldown") * 20.0));
         }

         player.fallDistance = 0.0F;
         level.playSound(null, player.blockPosition(), (SoundEvent)SoundRegistry.SPURT.get(), SoundSource.MASTER, 1.0F, 0.75F + random.nextFloat() * 0.5F);
         Vec3 start = current.add(0.0, 1.0, 0.0);
         Vec3 end = target.add(0.0, 1.0, 0.0);
         Vec3 delta = end.subtract(start);
         Vec3 dir = delta.normalize();

         for (int i = 0; i < distance * 20; i++) {
            double progress = i * delta.length() / (distance * 20);
            level.addParticle(
               ParticleUtils.constructSimpleSpark(new Color(255, 60 + random.nextInt(60), 0), 0.2F + random.nextFloat() * 0.5F, 60 + random.nextInt(60), 0.95F),
               start.x + dir.x * progress,
               start.y + dir.y * progress,
               start.z + dir.z * progress,
               0.0,
               MathUtils.randomFloat(random) * 0.075F,
               0.0
            );
         }

         List<LivingEntity> targets = new ArrayList<>();

         for (int i = 0; i < distance; i++) {
            double progress = i * delta.length() / distance;

            for (LivingEntity entity : level.getEntitiesOfClass(
               LivingEntity.class,
               new AABB(new BlockPos((int)(start.x + dir.x * progress), (int)(start.y + dir.y * progress), (int)(start.z + dir.z * progress)))
                  .inflate(0.5, 1.0, 0.5)
            )) {
               if (!entity.getStringUUID().equals(player.getStringUUID()) && !entity.isDeadOrDying()) {
                  targets.add(entity);
               }
            }
         }

         if (!targets.isEmpty()) {
            EntityUtils.resetAttribute(player, stack, Attributes.ATTACK_SPEED, 2.1474836E9F, Operation.ADD_MULTIPLIED_BASE);
            EntityUtils.resetAttribute(
               player, stack, Attributes.ATTACK_DAMAGE, (float)(this.getStatValue(stack, "spurt", "damage") * stacks), Operation.ADD_VALUE
            );

            for (LivingEntity entity : targets) {
               if (entity.invulnerableTime <= 0 && !EntityUtils.isAlliedTo(player, entity)) {
                  player.attack(entity);
                  this.spreadRelicExperience(player, stack, 1);
                  entity.addEffect(new MobEffectInstance(EffectRegistry.BLEEDING, 100, 0));
                  entity.setRemainingFireTicks(100);
               }
            }

            EntityUtils.removeAttribute(player, stack, Attributes.ATTACK_DAMAGE, Operation.ADD_VALUE);
            EntityUtils.removeAttribute(player, stack, Attributes.ATTACK_SPEED, Operation.ADD_MULTIPLIED_BASE);
         }

         stack.set(DataComponentRegistry.CHARGE, 0);
      }
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player) {
         if (this.isAbilityUnlocked(stack, "phlebotomy")) {
            float percentage = 100.0F - player.getHealth() / player.getMaxHealth() * 100.0F;
            player.heal((float)this.getStatValue(stack, "phlebotomy", "heal") * percentage);
            EntityUtils.resetAttribute(
               player,
               stack,
               Attributes.ATTACK_SPEED,
               (float)(this.getStatValue(stack, "phlebotomy", "attack_speed") * percentage),
               Operation.ADD_MULTIPLIED_TOTAL
            );
            EntityUtils.resetAttribute(
               player,
               stack,
               Attributes.MOVEMENT_SPEED,
               (float)(this.getStatValue(stack, "phlebotomy", "movement_speed") * percentage),
               Operation.ADD_MULTIPLIED_TOTAL
            );
         }

         if (this.isAbilityUnlocked(stack, "rage")) {
            int stacks = (Integer)stack.getOrDefault(DataComponentRegistry.CHARGE, 0);
            if (stacks > 0) {
               int time = (Integer)stack.getOrDefault(DataComponentRegistry.TIME, 0);
               if (time > 0) {
                  stack.set(DataComponentRegistry.TIME, --time);
               } else {
                  stack.set(DataComponentRegistry.CHARGE, 0);
               }
            }
         }
      }
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      if (slotContext.entity() instanceof Player player && stack.getItem() != newStack.getItem()) {
         EntityUtils.removeAttribute(player, stack, Attributes.ATTACK_SPEED, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.removeAttribute(player, stack, Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_TOTAL);
         stack.set(DataComponentRegistry.CHARGE, 0);
         stack.set(DataComponentRegistry.TIME, 0);
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public CurioModel getModel(ItemStack stack) {
      return new SidedFPRCurioModel(stack.getItem());
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
      mesh.getRoot()
         .addOrReplaceChild(
            "right_arm",
            CubeListBuilder.create()
               .texOffs(0, 0)
               .mirror()
               .addBox(-4.0F, 6.0F, -2.5F, 3.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
               .mirror(false)
               .texOffs(0, 13)
               .mirror()
               .addBox(-1.0F, 6.0F, -2.5F, 3.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
               .mirror(false)
               .texOffs(0, 0)
               .mirror()
               .addBox(-4.5F, 8.0F, -0.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
               .mirror(false),
            PartPose.offset(-3.0F, 2.0F, -0.5F)
         );
      mesh.getRoot()
         .addOrReplaceChild(
            "left_arm",
            CubeListBuilder.create()
               .texOffs(0, 0)
               .addBox(1.0F, 6.0F, -2.5F, 3.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
               .texOffs(0, 13)
               .addBox(-2.0F, 6.0F, -2.5F, 3.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
               .texOffs(0, 0)
               .addBox(3.5F, 8.0F, -0.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
            PartPose.offset(5.0F, 2.0F, -0.5F)
         );
      return LayerDefinition.create(mesh, 32, 32);
   }

   @Override
   public List<String> bodyParts() {
      return Lists.newArrayList(new String[]{"right_arm", "left_arm"});
   }

   @EventBusSubscriber(modid = "relics")
   public static class Events {
      @SubscribeEvent
      public static void onLivingHurt(LivingIncomingDamageEvent event) {
         if (event.getSource().getDirectEntity() instanceof Player player) {
            if (!(event.getSource().getEntity() instanceof Player)) {
               return;
            }

            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ItemRegistry.RAGE_GLOVE.get());
            if (!(stack.getItem() instanceof IRelicItem relic)) {
               return;
            }

            if (relic.isAbilityUnlocked(stack, "rage")) {
               int stacks = (Integer)stack.getOrDefault(DataComponentRegistry.CHARGE, 0);
               stack.set(DataComponentRegistry.CHARGE, ++stacks);
               stack.set(DataComponentRegistry.TIME, (int)Math.round(relic.getStatValue(stack, "rage", "duration") * 20.0));
               relic.spreadRelicExperience(player, stack, 1);
               event.setAmount((float)(event.getAmount() + event.getAmount() * (stacks * relic.getStatValue(stack, "rage", "dealt_damage"))));
            }
         } else if (event.getEntity() instanceof Player player) {
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ItemRegistry.RAGE_GLOVE.get());
            if (!(stack.getItem() instanceof IRelicItem relic)) {
               return;
            }

            if (relic.isAbilityUnlocked(stack, "rage")) {
               int stacks = (Integer)stack.getOrDefault(DataComponentRegistry.CHARGE, 0);
               if (stacks <= 0) {
                  return;
               }

               event.setAmount((float)(event.getAmount() + event.getAmount() * (stacks * relic.getStatValue(stack, "rage", "incoming_damage"))));
            }
         }
      }
   }
}
