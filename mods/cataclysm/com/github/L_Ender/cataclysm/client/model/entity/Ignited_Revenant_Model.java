package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ignited_Revenant_Entity;
import com.github.L_Ender.lionfishapi.client.model.Animations.ModelAnimator;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;

public class Ignited_Revenant_Model extends AdvancedEntityModel<Ignited_Revenant_Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox body;
   private final AdvancedModelBox guardingring;
   private final AdvancedModelBox guardingring2;
   private final AdvancedModelBox shieldjoint;
   private final AdvancedModelBox shield;
   private final AdvancedModelBox right_parts;
   private final AdvancedModelBox left_parts;
   private final AdvancedModelBox spike_right;
   private final AdvancedModelBox spike_left;
   private final AdvancedModelBox shieldjoint2;
   private final AdvancedModelBox shield2;
   private final AdvancedModelBox right_parts2;
   private final AdvancedModelBox left_parts2;
   private final AdvancedModelBox spike_right2;
   private final AdvancedModelBox spike_left2;
   private final AdvancedModelBox shieldjoint3;
   private final AdvancedModelBox shield3;
   private final AdvancedModelBox right_parts3;
   private final AdvancedModelBox left_parts3;
   private final AdvancedModelBox spike_right3;
   private final AdvancedModelBox spike_left3;
   private final AdvancedModelBox shieldjoint4;
   private final AdvancedModelBox shield4;
   private final AdvancedModelBox right_parts4;
   private final AdvancedModelBox left_parts4;
   private final AdvancedModelBox spike_right4;
   private final AdvancedModelBox spike_left4;
   private final AdvancedModelBox center;
   private final AdvancedModelBox head;
   private final AdvancedModelBox jaw;
   private final AdvancedModelBox skull;
   private final AdvancedModelBox helmet;
   private final AdvancedModelBox right_horn;
   private final AdvancedModelBox left_horn;
   private ModelAnimator animator;

   public Ignited_Revenant_Model() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.body = new AdvancedModelBox(this);
      this.body.setRotationPoint(0.0F, -6.0F, 0.0F);
      this.root.addChild(this.body);
      this.guardingring = new AdvancedModelBox(this);
      this.guardingring.setRotationPoint(0.0F, -15.0F, 0.0F);
      this.body.addChild(this.guardingring);
      this.guardingring2 = new AdvancedModelBox(this);
      this.guardingring2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.guardingring.addChild(this.guardingring2);
      this.shieldjoint = new AdvancedModelBox(this);
      this.shieldjoint.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.guardingring2.addChild(this.shieldjoint);
      this.setRotationAngle(this.shieldjoint, 0.0F, -0.7854F, 0.0F);
      this.shield = new AdvancedModelBox(this);
      this.shield.setRotationPoint(0.0F, -4.8F, -12.3F);
      this.shieldjoint.addChild(this.shield);
      this.setRotationAngle(this.shield, -0.2182F, 0.0F, 0.0F);
      this.shield.setTextureOffset(33, 0).addBox(-3.5F, -3.0F, -1.0F, 7.0F, 21.0F, 1.0F, 0.0F, false);
      this.shield.setTextureOffset(69, 61).addBox(-4.0F, -5.0F, -1.5F, 8.0F, 5.0F, 2.0F, 0.0F, false);
      this.shield.setTextureOffset(63, 15).addBox(-4.0F, 15.0F, -1.25F, 8.0F, 5.0F, 2.0F, 0.0F, false);
      this.shield.setTextureOffset(34, 66).addBox(-3.0F, 2.0F, -1.5F, 6.0F, 12.0F, 0.0F, 0.0F, false);
      this.right_parts = new AdvancedModelBox(this);
      this.right_parts.setRotationPoint(-3.5F, 4.0F, 0.5F);
      this.shield.addChild(this.right_parts);
      this.setRotationAngle(this.right_parts, 0.0436F, 0.0436F, -0.0873F);
      this.right_parts.setTextureOffset(60, 23).addBox(-4.0F, -7.0F, -1.0F, 5.0F, 21.0F, 1.0F, 0.0F, false);
      this.right_parts.setTextureOffset(50, 0).addBox(-4.0F, -7.0F, -1.0F, 5.0F, 21.0F, 1.0F, 0.3F, false);
      this.right_parts.setTextureOffset(72, 0).addBox(-4.25F, -8.5F, -1.5F, 7.0F, 4.0F, 2.0F, 0.0F, false);
      this.right_parts.setTextureOffset(72, 0).addBox(-4.25F, 11.5F, -1.5F, 7.0F, 4.0F, 2.0F, 0.0F, false);
      this.left_parts = new AdvancedModelBox(this);
      this.left_parts.setRotationPoint(3.5F, 4.0F, 0.5F);
      this.shield.addChild(this.left_parts);
      this.setRotationAngle(this.left_parts, 0.0436F, -0.0436F, 0.0873F);
      this.left_parts.setTextureOffset(47, 43).addBox(-1.0F, -7.0F, -1.0F, 5.0F, 21.0F, 1.0F, 0.0F, false);
      this.left_parts.setTextureOffset(34, 43).addBox(-1.0F, -7.0F, -1.0F, 5.0F, 21.0F, 1.0F, 0.3F, false);
      this.left_parts.setTextureOffset(70, 46).addBox(-2.75F, -8.5F, -1.5F, 7.0F, 4.0F, 2.0F, 0.0F, false);
      this.left_parts.setTextureOffset(69, 69).addBox(-2.75F, 11.5F, -1.5F, 7.0F, 4.0F, 2.0F, 0.0F, false);
      this.spike_right = new AdvancedModelBox(this);
      this.spike_right.setRotationPoint(4.0F, 6.5F, -1.0F);
      this.shield.addChild(this.spike_right);
      this.setRotationAngle(this.spike_right, 0.0F, -0.3491F, 0.0F);
      this.spike_right.setTextureOffset(63, 0).addBox(0.0F, -12.5F, -8.0F, 0.0F, 6.0F, 8.0F, 0.0F, false);
      this.spike_right.setTextureOffset(61, 46).addBox(0.0F, 8.5F, -8.0F, 0.0F, 6.0F, 8.0F, 0.0F, false);
      this.spike_left = new AdvancedModelBox(this);
      this.spike_left.setRotationPoint(-4.0F, 6.5F, -1.0F);
      this.shield.addChild(this.spike_left);
      this.setRotationAngle(this.spike_left, 0.0F, 0.3491F, 0.0F);
      this.spike_left.setTextureOffset(0, 61).addBox(0.0F, -12.5F, -8.0F, 0.0F, 6.0F, 8.0F, 0.0F, false);
      this.spike_left.setTextureOffset(52, 58).addBox(0.0F, 8.5F, -8.0F, 0.0F, 6.0F, 8.0F, 0.0F, false);
      this.shieldjoint2 = new AdvancedModelBox(this);
      this.shieldjoint2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.guardingring2.addChild(this.shieldjoint2);
      this.setRotationAngle(this.shieldjoint2, 0.0F, -2.3562F, 0.0F);
      this.shield2 = new AdvancedModelBox(this);
      this.shield2.setRotationPoint(0.0F, -4.8F, -12.3F);
      this.shieldjoint2.addChild(this.shield2);
      this.setRotationAngle(this.shield2, -0.2182F, 0.0F, 0.0F);
      this.shield2.setTextureOffset(33, 0).addBox(-3.5F, -3.0F, -1.0F, 7.0F, 21.0F, 1.0F, 0.0F, false);
      this.shield2.setTextureOffset(69, 61).addBox(-4.0F, -5.0F, -1.5F, 8.0F, 5.0F, 2.0F, 0.0F, false);
      this.shield2.setTextureOffset(63, 15).addBox(-4.0F, 15.0F, -1.25F, 8.0F, 5.0F, 2.0F, 0.0F, false);
      this.shield2.setTextureOffset(34, 66).addBox(-3.0F, 2.0F, -1.5F, 6.0F, 12.0F, 0.0F, 0.0F, false);
      this.right_parts2 = new AdvancedModelBox(this);
      this.right_parts2.setRotationPoint(-3.5F, 4.0F, 0.5F);
      this.shield2.addChild(this.right_parts2);
      this.setRotationAngle(this.right_parts2, 0.0436F, 0.0436F, -0.0873F);
      this.right_parts2.setTextureOffset(60, 23).addBox(-4.0F, -7.0F, -1.0F, 5.0F, 21.0F, 1.0F, 0.0F, false);
      this.right_parts2.setTextureOffset(50, 0).addBox(-4.0F, -7.0F, -1.0F, 5.0F, 21.0F, 1.0F, 0.3F, false);
      this.right_parts2.setTextureOffset(72, 0).addBox(-4.25F, -8.5F, -1.5F, 7.0F, 4.0F, 2.0F, 0.0F, false);
      this.right_parts2.setTextureOffset(72, 0).addBox(-4.25F, 11.5F, -1.5F, 7.0F, 4.0F, 2.0F, 0.0F, false);
      this.left_parts2 = new AdvancedModelBox(this);
      this.left_parts2.setRotationPoint(3.5F, 4.0F, 0.5F);
      this.shield2.addChild(this.left_parts2);
      this.setRotationAngle(this.left_parts2, 0.0436F, -0.0436F, 0.0873F);
      this.left_parts2.setTextureOffset(47, 43).addBox(-1.0F, -7.0F, -1.0F, 5.0F, 21.0F, 1.0F, 0.0F, false);
      this.left_parts2.setTextureOffset(34, 43).addBox(-1.0F, -7.0F, -1.0F, 5.0F, 21.0F, 1.0F, 0.3F, false);
      this.left_parts2.setTextureOffset(70, 46).addBox(-2.75F, -8.5F, -1.5F, 7.0F, 4.0F, 2.0F, 0.0F, false);
      this.left_parts2.setTextureOffset(69, 69).addBox(-2.75F, 11.5F, -1.5F, 7.0F, 4.0F, 2.0F, 0.0F, false);
      this.spike_right2 = new AdvancedModelBox(this);
      this.spike_right2.setRotationPoint(4.0F, 6.5F, -1.0F);
      this.shield2.addChild(this.spike_right2);
      this.setRotationAngle(this.spike_right2, 0.0F, -0.3491F, 0.0F);
      this.spike_right2.setTextureOffset(63, 0).addBox(0.0F, -12.5F, -8.0F, 0.0F, 6.0F, 8.0F, 0.0F, false);
      this.spike_right2.setTextureOffset(61, 46).addBox(0.0F, 8.5F, -8.0F, 0.0F, 6.0F, 8.0F, 0.0F, false);
      this.spike_left2 = new AdvancedModelBox(this);
      this.spike_left2.setRotationPoint(-4.0F, 6.5F, -1.0F);
      this.shield2.addChild(this.spike_left2);
      this.setRotationAngle(this.spike_left2, 0.0F, 0.3491F, 0.0F);
      this.spike_left2.setTextureOffset(0, 61).addBox(0.0F, -12.5F, -8.0F, 0.0F, 6.0F, 8.0F, 0.0F, false);
      this.spike_left2.setTextureOffset(52, 58).addBox(0.0F, 8.5F, -8.0F, 0.0F, 6.0F, 8.0F, 0.0F, false);
      this.shieldjoint3 = new AdvancedModelBox(this);
      this.shieldjoint3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.guardingring2.addChild(this.shieldjoint3);
      this.setRotationAngle(this.shieldjoint3, 0.0F, 2.3562F, 0.0F);
      this.shield3 = new AdvancedModelBox(this);
      this.shield3.setRotationPoint(0.0F, -4.8F, -12.3F);
      this.shieldjoint3.addChild(this.shield3);
      this.setRotationAngle(this.shield3, -0.2182F, 0.0F, 0.0F);
      this.shield3.setTextureOffset(33, 0).addBox(-3.5F, -3.0F, -1.0F, 7.0F, 21.0F, 1.0F, 0.0F, false);
      this.shield3.setTextureOffset(69, 61).addBox(-4.0F, -5.0F, -1.5F, 8.0F, 5.0F, 2.0F, 0.0F, false);
      this.shield3.setTextureOffset(63, 15).addBox(-4.0F, 15.0F, -1.25F, 8.0F, 5.0F, 2.0F, 0.0F, false);
      this.shield3.setTextureOffset(34, 66).addBox(-3.0F, 2.0F, -1.5F, 6.0F, 12.0F, 0.0F, 0.0F, false);
      this.right_parts3 = new AdvancedModelBox(this);
      this.right_parts3.setRotationPoint(-3.5F, 4.0F, 0.5F);
      this.shield3.addChild(this.right_parts3);
      this.setRotationAngle(this.right_parts3, 0.0436F, 0.0436F, -0.0873F);
      this.right_parts3.setTextureOffset(60, 23).addBox(-4.0F, -7.0F, -1.0F, 5.0F, 21.0F, 1.0F, 0.0F, false);
      this.right_parts3.setTextureOffset(50, 0).addBox(-4.0F, -7.0F, -1.0F, 5.0F, 21.0F, 1.0F, 0.3F, false);
      this.right_parts3.setTextureOffset(72, 0).addBox(-4.25F, -8.5F, -1.5F, 7.0F, 4.0F, 2.0F, 0.0F, false);
      this.right_parts3.setTextureOffset(72, 0).addBox(-4.25F, 11.5F, -1.5F, 7.0F, 4.0F, 2.0F, 0.0F, false);
      this.left_parts3 = new AdvancedModelBox(this);
      this.left_parts3.setRotationPoint(3.5F, 4.0F, 0.5F);
      this.shield3.addChild(this.left_parts3);
      this.setRotationAngle(this.left_parts3, 0.0436F, -0.0436F, 0.0873F);
      this.left_parts3.setTextureOffset(47, 43).addBox(-1.0F, -7.0F, -1.0F, 5.0F, 21.0F, 1.0F, 0.0F, false);
      this.left_parts3.setTextureOffset(34, 43).addBox(-1.0F, -7.0F, -1.0F, 5.0F, 21.0F, 1.0F, 0.3F, false);
      this.left_parts3.setTextureOffset(70, 46).addBox(-2.75F, -8.5F, -1.5F, 7.0F, 4.0F, 2.0F, 0.0F, false);
      this.left_parts3.setTextureOffset(69, 69).addBox(-2.75F, 11.5F, -1.5F, 7.0F, 4.0F, 2.0F, 0.0F, false);
      this.spike_right3 = new AdvancedModelBox(this);
      this.spike_right3.setRotationPoint(4.0F, 6.5F, -1.0F);
      this.shield3.addChild(this.spike_right3);
      this.setRotationAngle(this.spike_right3, 0.0F, -0.3491F, 0.0F);
      this.spike_right3.setTextureOffset(63, 0).addBox(0.0F, -12.5F, -8.0F, 0.0F, 6.0F, 8.0F, 0.0F, false);
      this.spike_right3.setTextureOffset(61, 46).addBox(0.0F, 8.5F, -8.0F, 0.0F, 6.0F, 8.0F, 0.0F, false);
      this.spike_left3 = new AdvancedModelBox(this);
      this.spike_left3.setRotationPoint(-4.0F, 6.5F, -1.0F);
      this.shield3.addChild(this.spike_left3);
      this.setRotationAngle(this.spike_left3, 0.0F, 0.3491F, 0.0F);
      this.spike_left3.setTextureOffset(0, 61).addBox(0.0F, -12.5F, -8.0F, 0.0F, 6.0F, 8.0F, 0.0F, false);
      this.spike_left3.setTextureOffset(52, 58).addBox(0.0F, 8.5F, -8.0F, 0.0F, 6.0F, 8.0F, 0.0F, false);
      this.shieldjoint4 = new AdvancedModelBox(this);
      this.shieldjoint4.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.guardingring2.addChild(this.shieldjoint4);
      this.setRotationAngle(this.shieldjoint4, 0.0F, 0.7854F, 0.0F);
      this.shield4 = new AdvancedModelBox(this);
      this.shield4.setRotationPoint(0.0F, -4.8F, -12.3F);
      this.shieldjoint4.addChild(this.shield4);
      this.setRotationAngle(this.shield4, -0.2182F, 0.0F, 0.0F);
      this.shield4.setTextureOffset(33, 0).addBox(-3.5F, -3.0F, -1.0F, 7.0F, 21.0F, 1.0F, 0.0F, false);
      this.shield4.setTextureOffset(69, 61).addBox(-4.0F, -5.0F, -1.5F, 8.0F, 5.0F, 2.0F, 0.0F, false);
      this.shield4.setTextureOffset(63, 15).addBox(-4.0F, 15.0F, -1.25F, 8.0F, 5.0F, 2.0F, 0.0F, false);
      this.shield4.setTextureOffset(34, 66).addBox(-3.0F, 2.0F, -1.5F, 6.0F, 12.0F, 0.0F, 0.0F, false);
      this.right_parts4 = new AdvancedModelBox(this);
      this.right_parts4.setRotationPoint(-3.5F, 4.0F, 0.5F);
      this.shield4.addChild(this.right_parts4);
      this.setRotationAngle(this.right_parts4, 0.0436F, 0.0436F, -0.0873F);
      this.right_parts4.setTextureOffset(60, 23).addBox(-4.0F, -7.0F, -1.0F, 5.0F, 21.0F, 1.0F, 0.0F, false);
      this.right_parts4.setTextureOffset(50, 0).addBox(-4.0F, -7.0F, -1.0F, 5.0F, 21.0F, 1.0F, 0.3F, false);
      this.right_parts4.setTextureOffset(72, 0).addBox(-4.25F, -8.5F, -1.5F, 7.0F, 4.0F, 2.0F, 0.0F, false);
      this.right_parts4.setTextureOffset(72, 0).addBox(-4.25F, 11.5F, -1.5F, 7.0F, 4.0F, 2.0F, 0.0F, false);
      this.left_parts4 = new AdvancedModelBox(this);
      this.left_parts4.setRotationPoint(3.5F, 4.0F, 0.5F);
      this.shield4.addChild(this.left_parts4);
      this.setRotationAngle(this.left_parts4, 0.0436F, -0.0436F, 0.0873F);
      this.left_parts4.setTextureOffset(47, 43).addBox(-1.0F, -7.0F, -1.0F, 5.0F, 21.0F, 1.0F, 0.0F, false);
      this.left_parts4.setTextureOffset(34, 43).addBox(-1.0F, -7.0F, -1.0F, 5.0F, 21.0F, 1.0F, 0.3F, false);
      this.left_parts4.setTextureOffset(70, 46).addBox(-2.75F, -8.5F, -1.5F, 7.0F, 4.0F, 2.0F, 0.0F, false);
      this.left_parts4.setTextureOffset(69, 69).addBox(-2.75F, 11.5F, -1.5F, 7.0F, 4.0F, 2.0F, 0.0F, false);
      this.spike_right4 = new AdvancedModelBox(this);
      this.spike_right4.setRotationPoint(4.0F, 6.5F, -1.0F);
      this.shield4.addChild(this.spike_right4);
      this.setRotationAngle(this.spike_right4, 0.0F, -0.3491F, 0.0F);
      this.spike_right4.setTextureOffset(63, 0).addBox(0.0F, -12.5F, -8.0F, 0.0F, 6.0F, 8.0F, 0.0F, false);
      this.spike_right4.setTextureOffset(61, 46).addBox(0.0F, 8.5F, -8.0F, 0.0F, 6.0F, 8.0F, 0.0F, false);
      this.spike_left4 = new AdvancedModelBox(this);
      this.spike_left4.setRotationPoint(-4.0F, 6.5F, -1.0F);
      this.shield4.addChild(this.spike_left4);
      this.setRotationAngle(this.spike_left4, 0.0F, 0.3491F, 0.0F);
      this.spike_left4.setTextureOffset(0, 61).addBox(0.0F, -12.5F, -8.0F, 0.0F, 6.0F, 8.0F, 0.0F, false);
      this.spike_left4.setTextureOffset(52, 58).addBox(0.0F, 8.5F, -8.0F, 0.0F, 6.0F, 8.0F, 0.0F, false);
      this.center = new AdvancedModelBox(this);
      this.center.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.body.addChild(this.center);
      this.center.setTextureOffset(17, 43).addBox(-2.0F, -22.0F, -2.0F, 4.0F, 22.0F, 4.0F, 0.0F, false);
      this.center.setTextureOffset(0, 34).addBox(-2.0F, -22.0F, -2.0F, 4.0F, 22.0F, 4.0F, 0.3F, false);
      this.head = new AdvancedModelBox(this);
      this.head.setRotationPoint(0.0F, -26.0F, 0.0F);
      this.center.addChild(this.head);
      this.jaw = new AdvancedModelBox(this);
      this.jaw.setRotationPoint(0.0F, 2.0F, 4.0F);
      this.head.addChild(this.jaw);
      this.jaw.setTextureOffset(0, 0).addBox(-4.0F, -6.0F, -8.0F, 8.0F, 8.0F, 8.0F, -0.1F, false);
      this.skull = new AdvancedModelBox(this);
      this.skull.setRotationPoint(0.0F, 2.0F, 4.0F);
      this.head.addChild(this.skull);
      this.skull.setTextureOffset(25, 26).addBox(-4.0F, -5.999F, -8.0436F, 8.0F, 8.0F, 8.0F, 0.0F, false);
      this.helmet = new AdvancedModelBox(this);
      this.helmet.setRotationPoint(0.0F, -1.999F, -4.0436F);
      this.skull.addChild(this.helmet);
      this.helmet.setTextureOffset(0, 17).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.3F, false);
      this.right_horn = new AdvancedModelBox(this);
      this.right_horn.setRotationPoint(-4.0F, -3.5F, -3.5F);
      this.helmet.addChild(this.right_horn);
      this.setRotationAngle(this.right_horn, 0.4363F, 0.0F, 0.0F);
      this.right_horn.setTextureOffset(0, 0).addBox(-1.3F, -5.5F, -0.5F, 1.0F, 6.0F, 1.0F, 0.0F, false);
      this.left_horn = new AdvancedModelBox(this);
      this.left_horn.setRotationPoint(4.3F, -3.5F, -3.5F);
      this.helmet.addChild(this.left_horn);
      this.setRotationAngle(this.left_horn, 0.4363F, 0.0F, 0.0F);
      this.left_horn.setTextureOffset(0, 17).addBox(0.0F, -2.5F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, false);
      this.animator = ModelAnimator.create();
      this.updateDefaultPose();
   }

   public void animate(Ignited_Revenant_Entity entity, float f, float f1, float f2, float f3, float f4) {
      this.resetToDefaultPose();
      this.animator.update(entity);
      this.animator.setAnimation(Ignited_Revenant_Entity.ASH_BREATH_ATTACK);
      if (!entity.getIsAnger()) {
         this.animator.startKeyframe(15);
         this.animator.rotate(this.guardingring, (float)Math.toRadians(-5.0), 0.0F, 0.0F);
         this.animator.rotate(this.center, (float)Math.toRadians(-12.5), 0.0F, 0.0F);
         this.animator.rotate(this.head, (float)Math.toRadians(-25.0), 0.0F, 0.0F);
         this.animator.endKeyframe();
         this.animator.setStaticKeyframe(8);
         this.animator.startKeyframe(5);
         this.animator.rotate(this.guardingring, (float)Math.toRadians(20.0), 0.0F, 0.0F);
         this.animator.rotate(this.center, (float)Math.toRadians(25.0), 0.0F, 0.0F);
         this.animator.rotate(this.head, (float)Math.toRadians(-15.0), 0.0F, 0.0F);
         this.animator.move(this.skull, 0.0F, -3.0F, 0.0F);
         this.animator.rotate(this.skull, (float)Math.toRadians(-22.5), 0.0F, 0.0F);
         this.animator.endKeyframe();
         this.animator.setStaticKeyframe(15);
         this.animator.resetKeyframe(10);
      } else {
         this.animator.startKeyframe(15);
         this.animator.rotate(this.root, (float)Math.toRadians(-12.5), 0.0F, 0.0F);
         this.animator.rotate(this.center, (float)Math.toRadians(-12.5), 0.0F, 0.0F);
         this.animator.rotate(this.head, (float)Math.toRadians(-25.0), 0.0F, 0.0F);
         this.animator.rotate(this.shield, (float)Math.toRadians(47.5), 0.0F, 0.0F);
         this.animator.rotate(this.shield2, (float)Math.toRadians(47.5), 0.0F, 0.0F);
         this.animator.rotate(this.shield3, (float)Math.toRadians(47.5), 0.0F, 0.0F);
         this.animator.rotate(this.shield4, (float)Math.toRadians(47.5), 0.0F, 0.0F);
         this.animator.rotate(this.skull, (float)Math.toRadians(17.5), 0.0F, 0.0F);
         this.animator.rotate(this.jaw, (float)Math.toRadians(-10.0), 0.0F, 0.0F);
         this.animator.endKeyframe();
         this.animator.setStaticKeyframe(8);
         this.animator.startKeyframe(5);
         this.animator.rotate(this.guardingring, (float)Math.toRadians(25.0), 0.0F, 0.0F);
         this.animator.rotate(this.center, (float)Math.toRadians(25.0), 0.0F, 0.0F);
         this.animator.rotate(this.head, (float)Math.toRadians(-15.0), 0.0F, 0.0F);
         this.animator.move(this.skull, 0.0F, -3.0F, 0.0F);
         this.animator.rotate(this.skull, (float)Math.toRadians(5.0), 0.0F, 0.0F);
         this.animator.rotate(this.shield, (float)Math.toRadians(47.5), 0.0F, 0.0F);
         this.animator.rotate(this.shield2, (float)Math.toRadians(47.5), 0.0F, 0.0F);
         this.animator.rotate(this.shield3, (float)Math.toRadians(47.5), 0.0F, 0.0F);
         this.animator.rotate(this.shield4, (float)Math.toRadians(47.5), 0.0F, 0.0F);
         this.animator.rotate(this.jaw, (float)Math.toRadians(-10.0), 0.0F, 0.0F);
         this.animator.endKeyframe();
         this.animator.setStaticKeyframe(15);
         this.animator.resetKeyframe(10);
      }

      this.animator.setAnimation(Ignited_Revenant_Entity.BONE_STORM_ATTACK);
      if (!entity.getIsAnger()) {
         this.animator.startKeyframe(4);
         this.animator.rotate(this.shield, (float)Math.toRadians(-57.5), 0.0F, 0.0F);
         this.animator.rotate(this.shield2, (float)Math.toRadians(-57.5), 0.0F, 0.0F);
         this.animator.rotate(this.shield3, (float)Math.toRadians(-57.5), 0.0F, 0.0F);
         this.animator.rotate(this.shield4, (float)Math.toRadians(-57.5), 0.0F, 0.0F);
         this.animator.move(this.skull, 0.0F, -3.0F, 0.0F);
         this.animator.endKeyframe();
         this.animator.setStaticKeyframe(30);
         this.animator.resetKeyframe(15);
      } else {
         this.animator.startKeyframe(4);
         this.animator.rotate(this.root, (float)Math.toRadians(-12.5), 0.0F, 0.0F);
         this.animator.rotate(this.guardingring, (float)Math.toRadians(5.0), 0.0F, 0.0F);
         this.animator.rotate(this.shield, (float)Math.toRadians(-10.0), 0.0F, 0.0F);
         this.animator.rotate(this.shield2, (float)Math.toRadians(-10.0), 0.0F, 0.0F);
         this.animator.rotate(this.shield3, (float)Math.toRadians(-10.0), 0.0F, 0.0F);
         this.animator.rotate(this.shield4, (float)Math.toRadians(-10.0), 0.0F, 0.0F);
         this.animator.rotate(this.jaw, (float)Math.toRadians(-10.0), 0.0F, 0.0F);
         this.animator.move(this.skull, 0.0F, -3.0F, 0.0F);
         this.animator.endKeyframe();
         this.animator.setStaticKeyframe(30);
         this.animator.resetKeyframe(15);
      }
   }

   public void setupAnim(Ignited_Revenant_Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.animate(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      float idleSpeed = 0.1F;
      float idleDegree = 1.0F;
      float walkSpeed = 0.5F;
      float walkDegree = 1.0F;
      this.faceTarget(netHeadYaw, headPitch, 1.0F, new AdvancedModelBox[]{this.head});
      this.bob(this.root, idleSpeed, idleDegree * 3.0F, false, ageInTicks, 1.0F);
      this.bob(this.shield, idleSpeed, idleDegree, false, ageInTicks, 1.0F);
      this.bob(this.shield2, idleSpeed, idleDegree, false, ageInTicks, 1.0F);
      this.bob(this.shield3, idleSpeed, idleDegree, false, ageInTicks, 1.0F);
      this.bob(this.shield4, idleSpeed, idleDegree, false, ageInTicks, 1.0F);
      float spin = 0.05F;
      if (entityIn.getIsAnger() && entityIn.getAnimation() == Ignited_Revenant_Entity.NO_ANIMATION) {
         spin = 0.5F;
      }

      this.guardingring2.rotateAngleY += ageInTicks * spin;
      this.shield.rotationPointY = this.shield.rotationPointY + Mth.cos(ageInTicks * 0.1F);
      this.shield4.rotationPointY = this.shield4.rotationPointY + Mth.cos(ageInTicks * 0.1F);
      this.shield2.rotationPointY = this.shield2.rotationPointY - Mth.cos(ageInTicks * 0.1F);
      this.shield3.rotationPointY = this.shield3.rotationPointY - Mth.cos(ageInTicks * 0.1F);
      float partialTick = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
      float angerProgress = entityIn.prevangerProgress + (entityIn.angerProgress - entityIn.prevangerProgress) * partialTick;
      this.progressRotationPrev(this.root, angerProgress, (float)Math.toRadians(12.5), 0.0F, 0.0F, 5.0F);
      this.progressRotationPrev(this.guardingring, angerProgress, (float)Math.toRadians(-5.0), 0.0F, 0.0F, 5.0F);
      this.progressRotationPrev(this.shield, angerProgress, (float)Math.toRadians(-47.5), 0.0F, 0.0F, 5.0F);
      this.progressRotationPrev(this.shield2, angerProgress, (float)Math.toRadians(-47.5), 0.0F, 0.0F, 5.0F);
      this.progressRotationPrev(this.shield3, angerProgress, (float)Math.toRadians(-47.5), 0.0F, 0.0F, 5.0F);
      this.progressRotationPrev(this.shield4, angerProgress, (float)Math.toRadians(-47.5), 0.0F, 0.0F, 5.0F);
      this.progressRotationPrev(this.skull, angerProgress, (float)Math.toRadians(-17.5), 0.0F, 0.0F, 5.0F);
      this.progressRotationPrev(this.jaw, angerProgress, (float)Math.toRadians(10.0), 0.0F, 0.0F, 5.0F);
      this.shield.showModel = entityIn.getShieldDurability() < 1;
      this.shield2.showModel = entityIn.getShieldDurability() < 2;
      this.shield3.showModel = entityIn.getShieldDurability() < 3;
      this.shield4.showModel = entityIn.getShieldDurability() < 4;
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(
         this.root,
         this.head,
         this.jaw,
         this.helmet,
         this.skull,
         this.body,
         this.guardingring,
         this.guardingring2,
         this.shield,
         this.shieldjoint,
         this.shield2,
         this.shieldjoint2,
         new AdvancedModelBox[]{this.shield3, this.shieldjoint3, this.shield4, this.shieldjoint4, this.center}
      );
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }
}
