package com.bobmowzie.mowziesmobs.client.render.item;

import com.bobmowzie.mowziesmobs.client.model.armor.ModelGeomancerArmor;
import com.bobmowzie.mowziesmobs.client.render.entity.MowzieGeoArmorRenderer;
import com.bobmowzie.mowziesmobs.server.item.ItemGeomancerArmor;
import net.minecraft.world.entity.EquipmentSlot;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;

public class RenderGeomancerArmor extends MowzieGeoArmorRenderer<ItemGeomancerArmor> {
   protected GeoBone beads = null;
   protected GeoBone belt = null;
   protected GeoBone robe = null;

   public RenderGeomancerArmor() {
      super(new ModelGeomancerArmor());
   }

   protected void grabRelevantBones(BakedGeoModel bakedModel) {
      super.grabRelevantBones(bakedModel);
      this.beads = (GeoBone)this.model.getBone("prayer_beads").orElse(null);
      this.belt = (GeoBone)this.model.getBone("belt").orElse(null);
      this.robe = (GeoBone)this.model.getBone("robes").orElse(null);
   }

   public void setAllVisible(boolean pVisible) {
      super.setAllVisible(pVisible);
      this.setBoneVisible(this.beads, false);
      this.setBoneVisible(this.belt, false);
      this.setBoneVisible(this.robe, false);
   }

   protected void applyBoneVisibilityBySlot(EquipmentSlot currentSlot) {
      this.setAllVisible(false);
      switch (currentSlot) {
         case HEAD:
            this.setBoneVisible(this.getBodyBone(this.model), true);
            this.setBoneVisible(this.beads, true);
            this.setBoneVisible(this.getHeadBone(this.model), true);
            break;
         case CHEST:
            this.setBoneVisible(this.getBodyBone(this.model), true);
            this.setBoneVisible(this.robe, true);
            this.setBoneVisible(this.getRightArmBone(this.model), true);
            this.setBoneVisible(this.getLeftArmBone(this.model), true);
            break;
         case LEGS:
            this.setBoneVisible(this.getBodyBone(this.model), true);
            this.setBoneVisible(this.belt, true);
            this.setBoneVisible(this.getRightLegBone(this.model), true);
            this.setBoneVisible(this.getLeftLegBone(this.model), true);
            break;
         case FEET:
            this.setBoneVisible(this.getRightBootBone(this.model), true);
            this.setBoneVisible(this.getLeftBootBone(this.model), true);
      }
   }
}
