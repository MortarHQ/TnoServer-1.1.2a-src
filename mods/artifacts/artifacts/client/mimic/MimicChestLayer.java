package artifacts.client.mimic;

import artifacts.Artifacts;
import artifacts.client.mimic.model.MimicChestLayerModel;
import artifacts.client.mimic.model.MimicModel;
import artifacts.entity.MimicEntity;
import artifacts.integration.lootr.LootrCompat;
import artifacts.platform.PlatformServices;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;

public class MimicChestLayer extends RenderLayer<MimicEntity, MimicModel> {
   public static final List<String> QUARK_CHEST_MATERIALS = Arrays.asList(
      "oak", "spruce", "birch", "cherry", "jungle", "acacia", "dark_oak", "warped", "crimson", "azalea", "blossom", "mangrove", "bamboo"
   );
   private final MimicChestLayerModel chestModel;
   public final Material vanillaChestMaterial;
   public final List<Material> chestMaterials;
   public final List<Material> lootrMaterials;

   public MimicChestLayer(RenderLayerParent<MimicEntity, MimicModel> entityRenderer, EntityModelSet modelSet) {
      super(entityRenderer);
      this.chestModel = new MimicChestLayerModel(modelSet.bakeLayer(MimicChestLayerModel.LAYER_LOCATION));
      this.chestMaterials = new ArrayList<>();
      this.lootrMaterials = new ArrayList<>();
      boolean isChristmas = isChristmas();
      this.vanillaChestMaterial = isChristmas ? Sheets.CHEST_XMAS_LOCATION : Sheets.CHEST_LOCATION;
      if (isChristmas) {
         this.chestMaterials.add(this.vanillaChestMaterial);
      } else {
         this.chestMaterials.add(this.vanillaChestMaterial);
         addQuarkMaterials(this.chestMaterials, "normal");
         if (PlatformServices.platformHelper.isModLoaded("lootr")) {
            this.lootrMaterials.add(createMaterial("lootr", "chest"));
            addQuarkMaterials(this.lootrMaterials, "lootr_normal");
         }
      }
   }

   private static boolean isChristmas() {
      Calendar calendar = Calendar.getInstance();
      return calendar.get(2) == 11 && calendar.get(5) >= 24 && calendar.get(5) <= 26 || calendar.get(2) == 3 && calendar.get(5) == 1;
   }

   private static void addQuarkMaterials(List<Material> chestMaterials, String chestVariant) {
      if (PlatformServices.platformHelper.isModLoaded("quark")) {
         for (String chestMaterial : QUARK_CHEST_MATERIALS) {
            chestMaterials.add(createMaterial("quark", String.format("quark_variant_chests/%s/%s", chestMaterial, chestVariant)));
         }
      }
   }

   private static Material createMaterial(String modId, String location) {
      ResourceLocation chestAtlas = ResourceLocation.parse("textures/atlas/chest.png");
      return new Material(chestAtlas, ResourceLocation.fromNamespaceAndPath(modId, location));
   }

   public void render(
      PoseStack matrixStack,
      MultiBufferSource buffer,
      int packedLight,
      MimicEntity mimic,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (!mimic.isInvisible()) {
         matrixStack.pushPose();
         matrixStack.mulPose(Axis.XP.rotationDegrees(180.0F));
         matrixStack.translate(-0.5, -1.5, -0.5);
         ((MimicModel)this.getParentModel()).copyPropertiesTo(this.chestModel);
         this.chestModel.prepareMobModel(mimic, limbSwing, limbSwingAmount, partialTicks);
         this.chestModel.setupAnim(mimic, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         VertexConsumer builder = this.getChestMaterial(mimic).buffer(buffer, RenderType::entityCutout);
         this.chestModel.renderToBuffer(matrixStack, builder, packedLight, LivingEntityRenderer.getOverlayCoords(mimic, 0.0F), -1);
         matrixStack.popPose();
      }
   }

   private Material getChestMaterial(MimicEntity mimic) {
      if (!Artifacts.CONFIG.client.useModdedMimicTextures.get()) {
         return this.vanillaChestMaterial;
      }

      List<Material> materials = !this.lootrMaterials.isEmpty() && !LootrCompat.useVanillaTextures() ? this.lootrMaterials : this.chestMaterials;
      return materials.size() == 1 ? materials.getFirst() : materials.get((int)(Math.abs(mimic.getUUID().getMostSignificantBits()) % materials.size()));
   }
}
