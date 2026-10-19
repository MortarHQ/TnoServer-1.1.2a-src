package com.shin.tensura_kumodesu.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.shin.tensura_kumodesu.entity.model.CustomTensuraModelFormat;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.tensura.entity.human.CloneEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class OniHornLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
   private final Map<ManasRace, CustomTensuraModelFormat<T>> modelMap = new HashMap<>();
   private final Map<ManasRace, ResourceLocation> textureMap = new HashMap<>();
   private final Map<ManasRace, float[]> scaleMap = new HashMap<>();

   public OniHornLayer(RenderLayerParent<T, M> parent) {
      super(parent);
      Minecraft mc = Minecraft.getInstance();
      EntityModelSet modelSet = mc.getEntityModels();
   }

   public void render(
      @NotNull PoseStack poseStack,
      @NotNull MultiBufferSource buffer,
      int light,
      T entity,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      ItemStack itemstack = entity.getItemBySlot(EquipmentSlot.MAINHAND);
      Optional<ManasRace> raceOpt = Optional.empty();
      Player player = null;
      if (entity instanceof Player) {
         raceOpt = RaceAPI.getRaceFrom(entity).getRace().map(ManasRaceInstance::getRace);
         player = (Player)entity;
      } else if (entity instanceof CloneEntity) {
         raceOpt = RaceAPI.getRaceFrom(Objects.requireNonNull(((CloneEntity)entity).getOwner())).getRace().map(ManasRaceInstance::getRace);
         player = (Player)((CloneEntity)entity).getOwner();
      }

      if (!raceOpt.isEmpty()) {
         ManasRace race = raceOpt.get();
         ResourceLocation texture = null;
         CustomTensuraModelFormat<T> model = this.modelMap.get(race);
         texture = this.textureMap.get(race);
         if (model != null && player != null) {
            poseStack.pushPose();
            poseStack.translate(0.0, 1.5 - entity.getScale() * 1.5, 0.0);
            poseStack.scale(entity.getScale(), entity.getScale(), entity.getScale());
            ((HumanoidModel)this.getParentModel()).copyPropertiesTo((EntityModel)model);
            model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, this.getParentModel());
            VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.entityCutoutNoCull(texture), itemstack.hasFoil());
            model.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F, player);
            poseStack.popPose();
         }
      }
   }
}
