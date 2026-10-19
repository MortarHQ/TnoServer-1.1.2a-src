package net.unusual.block_factorys_bosses.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.world.entity.Entity;
import net.unusual.block_factorys_bosses.BossesRise;

public class ModelCustomModel<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BossesRise.prefix("model_custom_model"), "main");
   public final ModelPart pile;
   public final ModelPart skeleton2;
   public final ModelPart torso2;
   public final ModelPart head3;
   public final ModelPart right_arm2;
   public final ModelPart left_arm2;
   public final ModelPart skeleton3;
   public final ModelPart torso3;
   public final ModelPart right_arm3;
   public final ModelPart skeleton4;
   public final ModelPart torso4;
   public final ModelPart head4;
   public final ModelPart skeleton5;
   public final ModelPart torso5;
   public final ModelPart head5;
   public final ModelPart right_arm4;
   public final ModelPart skeleton7;
   public final ModelPart torso7;
   public final ModelPart head6;
   public final ModelPart skeleton8;
   public final ModelPart torso8;
   public final ModelPart right_arm5;
   public final ModelPart skeleton9;
   public final ModelPart left_leg4;
   public final ModelPart torso9;
   public final ModelPart left_arm3;
   public final ModelPart skeleton10;
   public final ModelPart torso10;
   public final ModelPart head7;
   public final ModelPart skeleton11;
   public final ModelPart torso11;
   public final ModelPart right_arm6;
   public final ModelPart skeleton12;
   public final ModelPart torso12;
   public final ModelPart head8;
   public final ModelPart skeleton13;
   public final ModelPart torso13;
   public final ModelPart left_arm4;
   public final ModelPart skeleton14;
   public final ModelPart torso14;
   public final ModelPart head9;
   public final ModelPart right_arm7;
   public final ModelPart left_arm5;
   public final ModelPart skeleton6;
   public final ModelPart left_leg3;
   public final ModelPart right_leg3;
   public final ModelPart torso6;
   public final ModelPart skeleton15;
   public final ModelPart torso15;
   public final ModelPart head10;
   public final ModelPart right_arm8;
   public final ModelPart left_arm6;
   public final ModelPart skeleton16;
   public final ModelPart torso16;
   public final ModelPart head11;
   public final ModelPart skeleton17;
   public final ModelPart torso17;
   public final ModelPart right_arm9;
   public final ModelPart skeleton18;
   public final ModelPart torso18;
   public final ModelPart skeleton19;
   public final ModelPart left_leg2;
   public final ModelPart right_leg2;
   public final ModelPart torso19;
   public final ModelPart head12;
   public final ModelPart skeleton20;
   public final ModelPart torso20;
   public final ModelPart skeleton21;
   public final ModelPart torso21;
   public final ModelPart right_arm10;
   public final ModelPart left_arm7;
   public final ModelPart skeleton22;
   public final ModelPart torso22;
   public final ModelPart head13;
   public final ModelPart head21;
   public final ModelPart skeleton23;
   public final ModelPart torso23;
   public final ModelPart left_arm9;
   public final ModelPart skeleton24;
   public final ModelPart torso24;
   public final ModelPart skeleton25;
   public final ModelPart torso25;
   public final ModelPart head14;
   public final ModelPart right_arm11;
   public final ModelPart left_arm8;
   public final ModelPart skeleton26;
   public final ModelPart torso26;
   public final ModelPart skeleton27;
   public final ModelPart torso27;
   public final ModelPart head15;
   public final ModelPart right_arm12;
   public final ModelPart left_arm10;
   public final ModelPart skeleton28;
   public final ModelPart torso28;
   public final ModelPart right_arm13;
   public final ModelPart left_arm11;
   public final ModelPart skeleton29;
   public final ModelPart torso29;
   public final ModelPart head16;
   public final ModelPart right_arm14;
   public final ModelPart skeleton30;
   public final ModelPart torso30;
   public final ModelPart head17;
   public final ModelPart right_arm15;
   public final ModelPart left_arm12;
   public final ModelPart skeleton31;
   public final ModelPart torso31;
   public final ModelPart head18;
   public final ModelPart left_arm13;
   public final ModelPart skeleton32;
   public final ModelPart torso32;
   public final ModelPart left_arm14;
   public final ModelPart skeleton33;
   public final ModelPart torso33;
   public final ModelPart head19;
   public final ModelPart skeleton34;
   public final ModelPart torso34;
   public final ModelPart skeleton35;
   public final ModelPart torso35;
   public final ModelPart right_arm16;
   public final ModelPart left_arm15;
   public final ModelPart skeleton36;
   public final ModelPart torso36;
   public final ModelPart head20;
   public final ModelPart skeleton37;
   public final ModelPart torso37;
   public final ModelPart skeleton38;
   public final ModelPart torso38;
   public final ModelPart head22;
   public final ModelPart right_arm17;
   public final ModelPart skeleton39;
   public final ModelPart torso39;
   public final ModelPart head23;
   public final ModelPart right_arm18;
   public final ModelPart left_arm16;
   public final ModelPart skeleton40;
   public final ModelPart torso40;
   public final ModelPart head24;
   public final ModelPart left_arm17;
   public final ModelPart skeleton41;
   public final ModelPart torso41;
   public final ModelPart skeleton46;
   public final ModelPart torso46;
   public final ModelPart right_arm21;
   public final ModelPart skeleton42;
   public final ModelPart torso42;
   public final ModelPart skeleton43;
   public final ModelPart torso43;
   public final ModelPart skeleton44;
   public final ModelPart torso44;
   public final ModelPart head25;
   public final ModelPart right_arm19;
   public final ModelPart left_arm18;
   public final ModelPart skeleton45;
   public final ModelPart torso45;
   public final ModelPart head26;
   public final ModelPart right_arm20;
   public final ModelPart left_arm19;
   public final ModelPart skeleton49;
   public final ModelPart torso49;
   public final ModelPart skeleton47;
   public final ModelPart torso47;
   public final ModelPart head28;
   public final ModelPart right_arm22;
   public final ModelPart skeleton48;
   public final ModelPart torso48;
   public final ModelPart head29;
   public final ModelPart right_arm23;
   public final ModelPart left_arm20;
   public final ModelPart skeleton50;
   public final ModelPart torso50;
   public final ModelPart head30;
   public final ModelPart right_arm24;
   public final ModelPart head27;
   public final ModelPart skeleton52;
   public final ModelPart torso52;
   public final ModelPart right_arm25;
   public final ModelPart skeleton53;
   public final ModelPart torso53;
   public final ModelPart left_arm21;
   public final ModelPart skeleton54;
   public final ModelPart left_leg5;
   public final ModelPart right_leg4;
   public final ModelPart torso54;
   public final ModelPart head31;
   public final ModelPart left_arm22;
   public final ModelPart skeleton55;
   public final ModelPart torso55;
   public final ModelPart head32;
   public final ModelPart skeleton60;
   public final ModelPart torso60;
   public final ModelPart skeleton57;
   public final ModelPart torso57;
   public final ModelPart head33;
   public final ModelPart left_arm23;
   public final ModelPart skeleton58;
   public final ModelPart torso58;
   public final ModelPart skeleton56;
   public final ModelPart torso56;
   public final ModelPart skeleton59;
   public final ModelPart torso59;
   public final ModelPart head34;
   public final ModelPart head35;
   public final ModelPart skeleton61;
   public final ModelPart torso61;
   public final ModelPart skeleton62;
   public final ModelPart torso62;
   public final ModelPart left_arm24;
   public final ModelPart skeleton63;
   public final ModelPart torso63;
   public final ModelPart skeleton65;
   public final ModelPart torso65;
   public final ModelPart right_arm27;
   public final ModelPart head36;
   public final ModelPart skeleton64;
   public final ModelPart torso64;
   public final ModelPart right_arm26;
   public final ModelPart skeleton66;
   public final ModelPart left_leg6;
   public final ModelPart right_leg5;
   public final ModelPart torso66;
   public final ModelPart head38;
   public final ModelPart right_arm28;
   public final ModelPart left_arm25;
   public final ModelPart head37;
   public final ModelPart skeleton67;
   public final ModelPart left_leg7;
   public final ModelPart right_leg6;
   public final ModelPart torso67;
   public final ModelPart head39;
   public final ModelPart right_arm29;
   public final ModelPart left_arm26;
   public final ModelPart head40;
   public final ModelPart skeleton68;
   public final ModelPart torso68;
   public final ModelPart left_arm27;
   public final ModelPart head41;
   public final ModelPart skeleton69;
   public final ModelPart left_leg8;
   public final ModelPart right_leg7;
   public final ModelPart torso69;
   public final ModelPart head42;
   public final ModelPart right_arm30;
   public final ModelPart left_arm28;
   public final ModelPart skeleton51;
   public final ModelPart torso51;
   public final ModelPart skeleton71;
   public final ModelPart torso71;
   public final ModelPart skeleton72;
   public final ModelPart left_leg9;
   public final ModelPart right_leg8;
   public final ModelPart torso72;
   public final ModelPart head43;
   public final ModelPart right_arm32;
   public final ModelPart head44;
   public final ModelPart skeleton73;
   public final ModelPart torso73;
   public final ModelPart right_arm33;
   public final ModelPart head45;
   public final ModelPart skeleton74;
   public final ModelPart torso74;
   public final ModelPart head46;
   public final ModelPart right_arm34;
   public final ModelPart left_arm29;
   public final ModelPart skeleton75;
   public final ModelPart torso75;
   public final ModelPart head47;
   public final ModelPart right_arm35;
   public final ModelPart left_arm30;
   public final ModelPart skeleton76;
   public final ModelPart torso76;
   public final ModelPart head48;
   public final ModelPart right_arm36;
   public final ModelPart left_arm31;
   public final ModelPart skeleton77;
   public final ModelPart left_leg10;
   public final ModelPart right_leg9;
   public final ModelPart torso77;
   public final ModelPart head49;
   public final ModelPart right_arm37;
   public final ModelPart left_arm32;
   public final ModelPart skeleton70;
   public final ModelPart torso70;
   public final ModelPart right_arm31;
   public final ModelPart skeleton78;
   public final ModelPart torso78;
   public final ModelPart head50;
   public final ModelPart skeleton79;
   public final ModelPart torso79;
   public final ModelPart head51;

   public ModelCustomModel(ModelPart root) {
      this.pile = root.getChild("pile");
      this.skeleton2 = this.pile.getChild("skeleton2");
      this.torso2 = this.skeleton2.getChild("torso2");
      this.head3 = this.torso2.getChild("head3");
      this.right_arm2 = this.torso2.getChild("right_arm2");
      this.left_arm2 = this.torso2.getChild("left_arm2");
      this.skeleton3 = this.pile.getChild("skeleton3");
      this.torso3 = this.skeleton3.getChild("torso3");
      this.right_arm3 = this.torso3.getChild("right_arm3");
      this.skeleton4 = this.pile.getChild("skeleton4");
      this.torso4 = this.skeleton4.getChild("torso4");
      this.head4 = this.torso4.getChild("head4");
      this.skeleton5 = this.pile.getChild("skeleton5");
      this.torso5 = this.skeleton5.getChild("torso5");
      this.head5 = this.torso5.getChild("head5");
      this.right_arm4 = this.torso5.getChild("right_arm4");
      this.skeleton7 = this.pile.getChild("skeleton7");
      this.torso7 = this.skeleton7.getChild("torso7");
      this.head6 = this.torso7.getChild("head6");
      this.skeleton8 = this.pile.getChild("skeleton8");
      this.torso8 = this.skeleton8.getChild("torso8");
      this.right_arm5 = this.torso8.getChild("right_arm5");
      this.skeleton9 = this.pile.getChild("skeleton9");
      this.left_leg4 = this.skeleton9.getChild("left_leg4");
      this.torso9 = this.skeleton9.getChild("torso9");
      this.left_arm3 = this.torso9.getChild("left_arm3");
      this.skeleton10 = this.pile.getChild("skeleton10");
      this.torso10 = this.skeleton10.getChild("torso10");
      this.head7 = this.torso10.getChild("head7");
      this.skeleton11 = this.pile.getChild("skeleton11");
      this.torso11 = this.skeleton11.getChild("torso11");
      this.right_arm6 = this.torso11.getChild("right_arm6");
      this.skeleton12 = this.pile.getChild("skeleton12");
      this.torso12 = this.skeleton12.getChild("torso12");
      this.head8 = this.torso12.getChild("head8");
      this.skeleton13 = this.pile.getChild("skeleton13");
      this.torso13 = this.skeleton13.getChild("torso13");
      this.left_arm4 = this.torso13.getChild("left_arm4");
      this.skeleton14 = this.pile.getChild("skeleton14");
      this.torso14 = this.skeleton14.getChild("torso14");
      this.head9 = this.torso14.getChild("head9");
      this.right_arm7 = this.torso14.getChild("right_arm7");
      this.left_arm5 = this.torso14.getChild("left_arm5");
      this.skeleton6 = this.pile.getChild("skeleton6");
      this.left_leg3 = this.skeleton6.getChild("left_leg3");
      this.right_leg3 = this.skeleton6.getChild("right_leg3");
      this.torso6 = this.skeleton6.getChild("torso6");
      this.skeleton15 = this.pile.getChild("skeleton15");
      this.torso15 = this.skeleton15.getChild("torso15");
      this.head10 = this.torso15.getChild("head10");
      this.right_arm8 = this.torso15.getChild("right_arm8");
      this.left_arm6 = this.torso15.getChild("left_arm6");
      this.skeleton16 = this.pile.getChild("skeleton16");
      this.torso16 = this.skeleton16.getChild("torso16");
      this.head11 = this.torso16.getChild("head11");
      this.skeleton17 = this.pile.getChild("skeleton17");
      this.torso17 = this.skeleton17.getChild("torso17");
      this.right_arm9 = this.torso17.getChild("right_arm9");
      this.skeleton18 = this.pile.getChild("skeleton18");
      this.torso18 = this.skeleton18.getChild("torso18");
      this.skeleton19 = this.pile.getChild("skeleton19");
      this.left_leg2 = this.skeleton19.getChild("left_leg2");
      this.right_leg2 = this.skeleton19.getChild("right_leg2");
      this.torso19 = this.skeleton19.getChild("torso19");
      this.head12 = this.torso19.getChild("head12");
      this.skeleton20 = this.pile.getChild("skeleton20");
      this.torso20 = this.skeleton20.getChild("torso20");
      this.skeleton21 = this.pile.getChild("skeleton21");
      this.torso21 = this.skeleton21.getChild("torso21");
      this.right_arm10 = this.torso21.getChild("right_arm10");
      this.left_arm7 = this.torso21.getChild("left_arm7");
      this.skeleton22 = this.pile.getChild("skeleton22");
      this.torso22 = this.skeleton22.getChild("torso22");
      this.head13 = this.torso22.getChild("head13");
      this.head21 = this.pile.getChild("head21");
      this.skeleton23 = this.pile.getChild("skeleton23");
      this.torso23 = this.skeleton23.getChild("torso23");
      this.left_arm9 = this.torso23.getChild("left_arm9");
      this.skeleton24 = this.pile.getChild("skeleton24");
      this.torso24 = this.skeleton24.getChild("torso24");
      this.skeleton25 = this.pile.getChild("skeleton25");
      this.torso25 = this.skeleton25.getChild("torso25");
      this.head14 = this.torso25.getChild("head14");
      this.right_arm11 = this.torso25.getChild("right_arm11");
      this.left_arm8 = this.torso25.getChild("left_arm8");
      this.skeleton26 = this.pile.getChild("skeleton26");
      this.torso26 = this.skeleton26.getChild("torso26");
      this.skeleton27 = this.pile.getChild("skeleton27");
      this.torso27 = this.skeleton27.getChild("torso27");
      this.head15 = this.torso27.getChild("head15");
      this.right_arm12 = this.torso27.getChild("right_arm12");
      this.left_arm10 = this.torso27.getChild("left_arm10");
      this.skeleton28 = this.pile.getChild("skeleton28");
      this.torso28 = this.skeleton28.getChild("torso28");
      this.right_arm13 = this.torso28.getChild("right_arm13");
      this.left_arm11 = this.torso28.getChild("left_arm11");
      this.skeleton29 = this.pile.getChild("skeleton29");
      this.torso29 = this.skeleton29.getChild("torso29");
      this.head16 = this.torso29.getChild("head16");
      this.right_arm14 = this.torso29.getChild("right_arm14");
      this.skeleton30 = this.pile.getChild("skeleton30");
      this.torso30 = this.skeleton30.getChild("torso30");
      this.head17 = this.torso30.getChild("head17");
      this.right_arm15 = this.torso30.getChild("right_arm15");
      this.left_arm12 = this.torso30.getChild("left_arm12");
      this.skeleton31 = this.pile.getChild("skeleton31");
      this.torso31 = this.skeleton31.getChild("torso31");
      this.head18 = this.torso31.getChild("head18");
      this.left_arm13 = this.torso31.getChild("left_arm13");
      this.skeleton32 = this.pile.getChild("skeleton32");
      this.torso32 = this.skeleton32.getChild("torso32");
      this.left_arm14 = this.skeleton32.getChild("left_arm14");
      this.skeleton33 = this.pile.getChild("skeleton33");
      this.torso33 = this.skeleton33.getChild("torso33");
      this.head19 = this.torso33.getChild("head19");
      this.skeleton34 = this.pile.getChild("skeleton34");
      this.torso34 = this.skeleton34.getChild("torso34");
      this.skeleton35 = this.pile.getChild("skeleton35");
      this.torso35 = this.skeleton35.getChild("torso35");
      this.right_arm16 = this.torso35.getChild("right_arm16");
      this.left_arm15 = this.torso35.getChild("left_arm15");
      this.skeleton36 = this.pile.getChild("skeleton36");
      this.torso36 = this.skeleton36.getChild("torso36");
      this.head20 = this.torso36.getChild("head20");
      this.skeleton37 = this.pile.getChild("skeleton37");
      this.torso37 = this.skeleton37.getChild("torso37");
      this.skeleton38 = this.pile.getChild("skeleton38");
      this.torso38 = this.skeleton38.getChild("torso38");
      this.head22 = this.torso38.getChild("head22");
      this.right_arm17 = this.torso38.getChild("right_arm17");
      this.skeleton39 = this.pile.getChild("skeleton39");
      this.torso39 = this.skeleton39.getChild("torso39");
      this.head23 = this.torso39.getChild("head23");
      this.right_arm18 = this.torso39.getChild("right_arm18");
      this.left_arm16 = this.torso39.getChild("left_arm16");
      this.skeleton40 = this.pile.getChild("skeleton40");
      this.torso40 = this.skeleton40.getChild("torso40");
      this.head24 = this.torso40.getChild("head24");
      this.left_arm17 = this.torso40.getChild("left_arm17");
      this.skeleton41 = this.pile.getChild("skeleton41");
      this.torso41 = this.skeleton41.getChild("torso41");
      this.skeleton46 = this.pile.getChild("skeleton46");
      this.torso46 = this.skeleton46.getChild("torso46");
      this.right_arm21 = this.torso46.getChild("right_arm21");
      this.skeleton42 = this.pile.getChild("skeleton42");
      this.torso42 = this.skeleton42.getChild("torso42");
      this.skeleton43 = this.pile.getChild("skeleton43");
      this.torso43 = this.skeleton43.getChild("torso43");
      this.skeleton44 = this.pile.getChild("skeleton44");
      this.torso44 = this.skeleton44.getChild("torso44");
      this.head25 = this.torso44.getChild("head25");
      this.right_arm19 = this.torso44.getChild("right_arm19");
      this.left_arm18 = this.torso44.getChild("left_arm18");
      this.skeleton45 = this.pile.getChild("skeleton45");
      this.torso45 = this.skeleton45.getChild("torso45");
      this.head26 = this.torso45.getChild("head26");
      this.right_arm20 = this.torso45.getChild("right_arm20");
      this.left_arm19 = this.torso45.getChild("left_arm19");
      this.skeleton49 = this.pile.getChild("skeleton49");
      this.torso49 = this.skeleton49.getChild("torso49");
      this.skeleton47 = this.pile.getChild("skeleton47");
      this.torso47 = this.skeleton47.getChild("torso47");
      this.head28 = this.torso47.getChild("head28");
      this.right_arm22 = this.torso47.getChild("right_arm22");
      this.skeleton48 = this.pile.getChild("skeleton48");
      this.torso48 = this.skeleton48.getChild("torso48");
      this.head29 = this.torso48.getChild("head29");
      this.right_arm23 = this.torso48.getChild("right_arm23");
      this.left_arm20 = this.torso48.getChild("left_arm20");
      this.skeleton50 = this.pile.getChild("skeleton50");
      this.torso50 = this.skeleton50.getChild("torso50");
      this.head30 = this.torso50.getChild("head30");
      this.right_arm24 = this.torso50.getChild("right_arm24");
      this.head27 = this.pile.getChild("head27");
      this.skeleton52 = this.pile.getChild("skeleton52");
      this.torso52 = this.skeleton52.getChild("torso52");
      this.right_arm25 = this.torso52.getChild("right_arm25");
      this.skeleton53 = this.pile.getChild("skeleton53");
      this.torso53 = this.skeleton53.getChild("torso53");
      this.left_arm21 = this.skeleton53.getChild("left_arm21");
      this.skeleton54 = this.pile.getChild("skeleton54");
      this.left_leg5 = this.skeleton54.getChild("left_leg5");
      this.right_leg4 = this.skeleton54.getChild("right_leg4");
      this.torso54 = this.skeleton54.getChild("torso54");
      this.head31 = this.torso54.getChild("head31");
      this.left_arm22 = this.torso54.getChild("left_arm22");
      this.skeleton55 = this.pile.getChild("skeleton55");
      this.torso55 = this.skeleton55.getChild("torso55");
      this.head32 = this.pile.getChild("head32");
      this.skeleton60 = this.pile.getChild("skeleton60");
      this.torso60 = this.skeleton60.getChild("torso60");
      this.skeleton57 = this.pile.getChild("skeleton57");
      this.torso57 = this.skeleton57.getChild("torso57");
      this.head33 = this.torso57.getChild("head33");
      this.left_arm23 = this.torso57.getChild("left_arm23");
      this.skeleton58 = this.pile.getChild("skeleton58");
      this.torso58 = this.skeleton58.getChild("torso58");
      this.skeleton56 = this.pile.getChild("skeleton56");
      this.torso56 = this.skeleton56.getChild("torso56");
      this.skeleton59 = this.pile.getChild("skeleton59");
      this.torso59 = this.skeleton59.getChild("torso59");
      this.head34 = this.pile.getChild("head34");
      this.head35 = this.pile.getChild("head35");
      this.skeleton61 = this.pile.getChild("skeleton61");
      this.torso61 = this.skeleton61.getChild("torso61");
      this.skeleton62 = this.pile.getChild("skeleton62");
      this.torso62 = this.skeleton62.getChild("torso62");
      this.left_arm24 = this.skeleton62.getChild("left_arm24");
      this.skeleton63 = this.pile.getChild("skeleton63");
      this.torso63 = this.skeleton63.getChild("torso63");
      this.skeleton65 = this.pile.getChild("skeleton65");
      this.torso65 = this.skeleton65.getChild("torso65");
      this.right_arm27 = this.torso65.getChild("right_arm27");
      this.head36 = this.pile.getChild("head36");
      this.skeleton64 = this.pile.getChild("skeleton64");
      this.torso64 = this.skeleton64.getChild("torso64");
      this.right_arm26 = this.torso64.getChild("right_arm26");
      this.skeleton66 = this.pile.getChild("skeleton66");
      this.left_leg6 = this.skeleton66.getChild("left_leg6");
      this.right_leg5 = this.skeleton66.getChild("right_leg5");
      this.torso66 = this.skeleton66.getChild("torso66");
      this.head38 = this.torso66.getChild("head38");
      this.right_arm28 = this.torso66.getChild("right_arm28");
      this.left_arm25 = this.torso66.getChild("left_arm25");
      this.head37 = this.pile.getChild("head37");
      this.skeleton67 = this.pile.getChild("skeleton67");
      this.left_leg7 = this.skeleton67.getChild("left_leg7");
      this.right_leg6 = this.skeleton67.getChild("right_leg6");
      this.torso67 = this.skeleton67.getChild("torso67");
      this.head39 = this.torso67.getChild("head39");
      this.right_arm29 = this.torso67.getChild("right_arm29");
      this.left_arm26 = this.torso67.getChild("left_arm26");
      this.head40 = this.pile.getChild("head40");
      this.skeleton68 = this.pile.getChild("skeleton68");
      this.torso68 = this.skeleton68.getChild("torso68");
      this.left_arm27 = this.torso68.getChild("left_arm27");
      this.head41 = this.pile.getChild("head41");
      this.skeleton69 = this.pile.getChild("skeleton69");
      this.left_leg8 = this.skeleton69.getChild("left_leg8");
      this.right_leg7 = this.skeleton69.getChild("right_leg7");
      this.torso69 = this.skeleton69.getChild("torso69");
      this.head42 = this.torso69.getChild("head42");
      this.right_arm30 = this.torso69.getChild("right_arm30");
      this.left_arm28 = this.torso69.getChild("left_arm28");
      this.skeleton51 = this.pile.getChild("skeleton51");
      this.torso51 = this.skeleton51.getChild("torso51");
      this.skeleton71 = this.pile.getChild("skeleton71");
      this.torso71 = this.skeleton71.getChild("torso71");
      this.skeleton72 = this.pile.getChild("skeleton72");
      this.left_leg9 = this.skeleton72.getChild("left_leg9");
      this.right_leg8 = this.skeleton72.getChild("right_leg8");
      this.torso72 = this.skeleton72.getChild("torso72");
      this.head43 = this.torso72.getChild("head43");
      this.right_arm32 = this.torso72.getChild("right_arm32");
      this.head44 = this.pile.getChild("head44");
      this.skeleton73 = this.pile.getChild("skeleton73");
      this.torso73 = this.skeleton73.getChild("torso73");
      this.right_arm33 = this.torso73.getChild("right_arm33");
      this.head45 = this.pile.getChild("head45");
      this.skeleton74 = this.pile.getChild("skeleton74");
      this.torso74 = this.skeleton74.getChild("torso74");
      this.head46 = this.torso74.getChild("head46");
      this.right_arm34 = this.torso74.getChild("right_arm34");
      this.left_arm29 = this.torso74.getChild("left_arm29");
      this.skeleton75 = this.pile.getChild("skeleton75");
      this.torso75 = this.skeleton75.getChild("torso75");
      this.head47 = this.torso75.getChild("head47");
      this.right_arm35 = this.torso75.getChild("right_arm35");
      this.left_arm30 = this.torso75.getChild("left_arm30");
      this.skeleton76 = this.pile.getChild("skeleton76");
      this.torso76 = this.skeleton76.getChild("torso76");
      this.head48 = this.torso76.getChild("head48");
      this.right_arm36 = this.torso76.getChild("right_arm36");
      this.left_arm31 = this.torso76.getChild("left_arm31");
      this.skeleton77 = this.pile.getChild("skeleton77");
      this.left_leg10 = this.skeleton77.getChild("left_leg10");
      this.right_leg9 = this.skeleton77.getChild("right_leg9");
      this.torso77 = this.skeleton77.getChild("torso77");
      this.head49 = this.torso77.getChild("head49");
      this.right_arm37 = this.torso77.getChild("right_arm37");
      this.left_arm32 = this.torso77.getChild("left_arm32");
      this.skeleton70 = this.pile.getChild("skeleton70");
      this.torso70 = this.skeleton70.getChild("torso70");
      this.right_arm31 = this.torso70.getChild("right_arm31");
      this.skeleton78 = this.pile.getChild("skeleton78");
      this.torso78 = this.skeleton78.getChild("torso78");
      this.head50 = this.torso78.getChild("head50");
      this.skeleton79 = this.pile.getChild("skeleton79");
      this.torso79 = this.skeleton79.getChild("torso79");
      this.head51 = this.torso79.getChild("head51");
   }

   public static LayerDefinition createBodyLayer() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.OutOfMemoryError: Java heap space
      //   at org.jetbrains.java.decompiler.util.collections.FastSparseSetFactory$FastSparseSet.getCopy(FastSparseSetFactory.java:95)
      //   at org.jetbrains.java.decompiler.util.collections.SFormsFastMapDirect.getCopy(SFormsFastMapDirect.java:67)
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SSAUConstructorSparseEx.updateLiveMap(SSAUConstructorSparseEx.java:269)
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SSAUConstructorSparseEx.onAssignment(SSAUConstructorSparseEx.java:262)
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SFormsConstructor.updateVarExprent(SFormsConstructor.java:217)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.AssignmentExprent.processSforms(AssignmentExprent.java:312)
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SFormsConstructor.ssaStatements(SFormsConstructor.java:127)
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SSAUConstructorSparseEx.splitVariables(SSAUConstructorSparseEx.java:45)
      //   at org.jetbrains.java.decompiler.modules.decompiler.StackVarsProcessor.simplifyStackVars(StackVarsProcessor.java:67)
      //   at org.jetbrains.java.decompiler.modules.decompiler.StackVarsProcessor.simplifyStackVars(StackVarsProcessor.java:43)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:238)
      //
      // Bytecode:
      // 0000: new net/minecraft/client/model/geom/builders/MeshDefinition
      // 0003: dup
      // 0004: invokespecial net/minecraft/client/model/geom/builders/MeshDefinition.<init> ()V
      // 0007: astore 0
      // 0008: aload 0
      // 0009: invokevirtual net/minecraft/client/model/geom/builders/MeshDefinition.getRoot ()Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 000c: astore 1
      // 000d: aload 1
      // 000e: ldc "pile"
      // 0010: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0013: ldc_w -3.3714
      // 0016: ldc_w 24.0
      // 0019: ldc_w 4.8318
      // 001c: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 001f: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0022: astore 2
      // 0023: aload 2
      // 0024: ldc "skeleton2"
      // 0026: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0029: ldc_w 41.8787
      // 002c: ldc_w 0.1234
      // 002f: ldc_w -32.9113
      // 0032: ldc_w 1.3264
      // 0035: ldc_w -0.7703
      // 0038: ldc_w 0.1719
      // 003b: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 003e: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0041: astore 3
      // 0042: aload 3
      // 0043: ldc "torso2"
      // 0045: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0048: bipush 30
      // 004a: bipush 37
      // 004c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 004f: ldc_w -2.0
      // 0052: ldc_w -21.9833
      // 0055: ldc_w -2.0
      // 0058: ldc_w 4.0
      // 005b: ldc_w 22.0
      // 005e: ldc_w 3.0
      // 0061: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0064: dup
      // 0065: fconst_0
      // 0066: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0069: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 006c: bipush 0
      // 006d: bipush 37
      // 006f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0072: ldc_w -5.0
      // 0075: ldc_w -5.9833
      // 0078: ldc_w -4.0
      // 007b: ldc_w 10.0
      // 007e: ldc_w 6.0
      // 0081: ldc_w 5.0
      // 0084: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0087: dup
      // 0088: fconst_0
      // 0089: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 008c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 008f: bipush 0
      // 0090: bipush 0
      // 0091: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0094: ldc_w -6.0
      // 0097: ldc_w -17.9833
      // 009a: ldc_w -6.0
      // 009d: ldc_w 12.0
      // 00a0: ldc_w 12.0
      // 00a3: ldc_w 7.0
      // 00a6: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 00a9: dup
      // 00aa: fconst_0
      // 00ab: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 00ae: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 00b1: fconst_0
      // 00b2: ldc_w 7.15
      // 00b5: ldc_w 1.7778
      // 00b8: ldc_w -0.7854
      // 00bb: fconst_0
      // 00bc: fconst_0
      // 00bd: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 00c0: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 00c3: astore 4
      // 00c5: aload 4
      // 00c7: ldc "head3"
      // 00c9: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 00cc: bipush 0
      // 00cd: bipush 19
      // 00cf: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 00d2: ldc_w -5.0
      // 00d5: ldc_w -8.75
      // 00d8: ldc_w -7.25
      // 00db: ldc_w 10.0
      // 00de: ldc_w 9.0
      // 00e1: ldc_w 9.0
      // 00e4: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 00e7: dup
      // 00e8: fconst_0
      // 00e9: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 00ec: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 00ef: bipush 38
      // 00f1: bipush 25
      // 00f3: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 00f6: ldc_w -3.0
      // 00f9: ldc_w 0.25
      // 00fc: ldc_w -7.25
      // 00ff: ldc_w 6.0
      // 0102: ldc_w 4.0
      // 0105: ldc_w 4.0
      // 0108: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 010b: dup
      // 010c: fconst_0
      // 010d: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0110: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0113: fconst_0
      // 0114: ldc_w -22.2333
      // 0117: ldc_w -0.75
      // 011a: ldc_w -0.7459
      // 011d: ldc_w 0.274
      // 0120: ldc_w 0.2849
      // 0123: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0126: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0129: astore 5
      // 012b: aload 4
      // 012d: ldc "right_arm2"
      // 012f: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0132: bipush 38
      // 0134: bipush 0
      // 0135: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0138: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 013b: ldc_w -1.5
      // 013e: ldc_w -3.0
      // 0141: ldc_w -1.5
      // 0144: ldc_w 3.0
      // 0147: ldc_w 22.0
      // 014a: ldc_w 3.0
      // 014d: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0150: dup
      // 0151: fconst_0
      // 0152: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0155: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0158: bipush 0
      // 0159: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 015c: ldc_w -8.6481
      // 015f: ldc_w -13.2724
      // 0162: ldc_w -4.2087
      // 0165: ldc_w -1.9923
      // 0168: ldc_w 0.3614
      // 016b: ldc_w -0.1572
      // 016e: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0171: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0174: astore 6
      // 0176: aload 4
      // 0178: ldc "left_arm2"
      // 017a: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 017d: bipush 38
      // 017f: bipush 0
      // 0180: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0183: ldc_w -1.5
      // 0186: ldc_w -3.0
      // 0189: ldc_w -1.5
      // 018c: ldc_w 3.0
      // 018f: ldc_w 22.0
      // 0192: ldc_w 3.0
      // 0195: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0198: dup
      // 0199: fconst_0
      // 019a: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 019d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 01a0: ldc_w 6.7168
      // 01a3: ldc_w -19.4873
      // 01a6: ldc_w -0.183
      // 01a9: ldc_w -1.1781
      // 01ac: fconst_0
      // 01ad: ldc_w -0.3927
      // 01b0: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 01b3: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 01b6: astore 7
      // 01b8: aload 2
      // 01b9: ldc "skeleton3"
      // 01bb: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 01be: ldc_w -11.1563
      // 01c1: ldc_w -5.0776
      // 01c4: ldc_w -49.1818
      // 01c7: ldc_w 1.0082
      // 01ca: ldc_w -0.5236
      // 01cd: ldc_w -2.5261
      // 01d0: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 01d3: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 01d6: astore 8
      // 01d8: aload 8
      // 01da: ldc "torso3"
      // 01dc: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 01df: bipush 30
      // 01e1: bipush 37
      // 01e3: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 01e6: ldc_w -2.0
      // 01e9: ldc_w -21.9833
      // 01ec: ldc_w -2.0
      // 01ef: ldc_w 4.0
      // 01f2: ldc_w 22.0
      // 01f5: ldc_w 3.0
      // 01f8: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 01fb: dup
      // 01fc: fconst_0
      // 01fd: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0200: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0203: bipush 0
      // 0204: bipush 37
      // 0206: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0209: ldc_w -5.0
      // 020c: ldc_w -5.9833
      // 020f: ldc_w -4.0
      // 0212: ldc_w 10.0
      // 0215: ldc_w 6.0
      // 0218: ldc_w 5.0
      // 021b: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 021e: dup
      // 021f: fconst_0
      // 0220: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0223: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0226: bipush 0
      // 0227: bipush 0
      // 0228: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 022b: ldc_w -6.0
      // 022e: ldc_w -17.9833
      // 0231: ldc_w -6.0
      // 0234: ldc_w 12.0
      // 0237: ldc_w 12.0
      // 023a: ldc_w 7.0
      // 023d: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0240: dup
      // 0241: fconst_0
      // 0242: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0245: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0248: ldc_w 0.5284
      // 024b: ldc_w 8.0618
      // 024e: ldc_w -0.1944
      // 0251: ldc_w -0.0436
      // 0254: ldc_w -0.2618
      // 0257: fconst_0
      // 0258: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 025b: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 025e: astore 9
      // 0260: aload 9
      // 0262: ldc "right_arm3"
      // 0264: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0267: bipush 38
      // 0269: bipush 0
      // 026a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 026d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0270: ldc_w -1.5
      // 0273: ldc_w -3.0
      // 0276: ldc_w -1.5
      // 0279: ldc_w 3.0
      // 027c: ldc_w 22.0
      // 027f: ldc_w 3.0
      // 0282: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0285: dup
      // 0286: fconst_0
      // 0287: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 028a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 028d: bipush 0
      // 028e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0291: ldc_w -8.705
      // 0294: ldc_w -16.4194
      // 0297: ldc_w -1.1969
      // 029a: ldc_w -0.3902
      // 029d: ldc_w -0.5202
      // 02a0: ldc_w 2.1251
      // 02a3: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 02a6: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 02a9: astore 10
      // 02ab: aload 2
      // 02ac: ldc "skeleton4"
      // 02ae: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 02b1: ldc_w 25.0
      // 02b4: ldc_w 7.8333
      // 02b7: ldc_w -55.7778
      // 02ba: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 02bd: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 02c0: astore 11
      // 02c2: aload 11
      // 02c4: ldc "torso4"
      // 02c6: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 02c9: fconst_0
      // 02ca: ldc_w 8.15
      // 02cd: ldc_w 1.7778
      // 02d0: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 02d3: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 02d6: astore 12
      // 02d8: aload 12
      // 02da: ldc "head4"
      // 02dc: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 02df: bipush 0
      // 02e0: bipush 19
      // 02e2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 02e5: ldc_w -5.0
      // 02e8: ldc_w -8.75
      // 02eb: ldc_w -7.25
      // 02ee: ldc_w 10.0
      // 02f1: ldc_w 9.0
      // 02f4: ldc_w 9.0
      // 02f7: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 02fa: dup
      // 02fb: fconst_0
      // 02fc: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 02ff: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0302: bipush 38
      // 0304: bipush 25
      // 0306: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0309: ldc_w -3.0
      // 030c: ldc_w 0.25
      // 030f: ldc_w -7.25
      // 0312: ldc_w 6.0
      // 0315: ldc_w 4.0
      // 0318: ldc_w 4.0
      // 031b: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 031e: dup
      // 031f: fconst_0
      // 0320: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0323: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0326: ldc_w -3.0
      // 0329: ldc_w -21.2333
      // 032c: ldc_w -6.75
      // 032f: ldc_w 1.2023
      // 0332: ldc_w -0.5102
      // 0335: ldc_w -0.0208
      // 0338: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 033b: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 033e: astore 13
      // 0340: aload 2
      // 0341: ldc "skeleton5"
      // 0343: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0346: ldc_w -8.0
      // 0349: ldc_w -20.1667
      // 034c: ldc_w -31.7778
      // 034f: ldc_w 0.48
      // 0352: fconst_0
      // 0353: ldc_w -0.5236
      // 0356: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0359: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 035c: astore 14
      // 035e: aload 14
      // 0360: ldc "torso5"
      // 0362: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0365: bipush 30
      // 0367: bipush 37
      // 0369: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 036c: ldc_w -2.0
      // 036f: ldc_w -21.9833
      // 0372: ldc_w -2.0
      // 0375: ldc_w 4.0
      // 0378: ldc_w 22.0
      // 037b: ldc_w 3.0
      // 037e: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0381: dup
      // 0382: fconst_0
      // 0383: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0386: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0389: bipush 0
      // 038a: bipush 37
      // 038c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 038f: ldc_w -5.0
      // 0392: ldc_w -5.9833
      // 0395: ldc_w -4.0
      // 0398: ldc_w 10.0
      // 039b: ldc_w 6.0
      // 039e: ldc_w 5.0
      // 03a1: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 03a4: dup
      // 03a5: fconst_0
      // 03a6: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 03a9: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 03ac: bipush 0
      // 03ad: bipush 0
      // 03ae: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 03b1: ldc_w -6.0
      // 03b4: ldc_w -17.9833
      // 03b7: ldc_w -6.0
      // 03ba: ldc_w 12.0
      // 03bd: ldc_w 12.0
      // 03c0: ldc_w 7.0
      // 03c3: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 03c6: dup
      // 03c7: fconst_0
      // 03c8: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 03cb: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 03ce: fconst_0
      // 03cf: ldc_w 7.15
      // 03d2: ldc_w 1.7778
      // 03d5: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 03d8: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 03db: astore 15
      // 03dd: aload 15
      // 03df: ldc "head5"
      // 03e1: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 03e4: bipush 0
      // 03e5: bipush 19
      // 03e7: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 03ea: ldc_w -5.0
      // 03ed: ldc_w -8.75
      // 03f0: ldc_w -7.25
      // 03f3: ldc_w 10.0
      // 03f6: ldc_w 9.0
      // 03f9: ldc_w 9.0
      // 03fc: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 03ff: dup
      // 0400: fconst_0
      // 0401: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0404: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0407: bipush 38
      // 0409: bipush 25
      // 040b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 040e: ldc_w -3.0
      // 0411: ldc_w 0.25
      // 0414: ldc_w -7.25
      // 0417: ldc_w 6.0
      // 041a: ldc_w 4.0
      // 041d: ldc_w 4.0
      // 0420: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0423: dup
      // 0424: fconst_0
      // 0425: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0428: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 042b: fconst_0
      // 042c: ldc_w -21.3095
      // 042f: ldc_w -0.3673
      // 0432: ldc_w -0.7656
      // 0435: ldc_w 0.6215
      // 0438: ldc_w 0.5905
      // 043b: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 043e: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0441: astore 16
      // 0443: aload 15
      // 0445: ldc "right_arm4"
      // 0447: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 044a: bipush 38
      // 044c: bipush 0
      // 044d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0450: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0453: ldc_w -1.5
      // 0456: ldc_w -3.0
      // 0459: ldc_w -1.5
      // 045c: ldc_w 3.0
      // 045f: ldc_w 22.0
      // 0462: ldc_w 3.0
      // 0465: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0468: dup
      // 0469: fconst_0
      // 046a: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 046d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0470: bipush 0
      // 0471: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0474: ldc_w -9.2575
      // 0477: ldc_w -15.9354
      // 047a: ldc_w -0.4311
      // 047d: ldc_w 0.0457
      // 0480: ldc_w 0.7165
      // 0483: ldc_w 2.0973
      // 0486: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0489: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 048c: astore 17
      // 048e: aload 2
      // 048f: ldc "skeleton7"
      // 0491: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0494: ldc_w 58.0
      // 0497: ldc_w -33.1667
      // 049a: ldc_w 14.2222
      // 049d: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 04a0: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 04a3: astore 18
      // 04a5: aload 18
      // 04a7: ldc "torso7"
      // 04a9: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 04ac: fconst_0
      // 04ad: ldc_w 7.15
      // 04b0: ldc_w 1.7778
      // 04b3: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 04b6: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 04b9: astore 19
      // 04bb: aload 19
      // 04bd: ldc "head6"
      // 04bf: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 04c2: bipush 0
      // 04c3: bipush 19
      // 04c5: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 04c8: ldc_w -5.0
      // 04cb: ldc_w -8.75
      // 04ce: ldc_w -7.25
      // 04d1: ldc_w 10.0
      // 04d4: ldc_w 9.0
      // 04d7: ldc_w 9.0
      // 04da: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 04dd: dup
      // 04de: fconst_0
      // 04df: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 04e2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 04e5: bipush 38
      // 04e7: bipush 25
      // 04e9: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 04ec: ldc_w -3.0
      // 04ef: ldc_w 0.25
      // 04f2: ldc_w -7.25
      // 04f5: ldc_w 6.0
      // 04f8: ldc_w 4.0
      // 04fb: ldc_w 4.0
      // 04fe: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0501: dup
      // 0502: fconst_0
      // 0503: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0506: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0509: fconst_1
      // 050a: ldc_w -19.2333
      // 050d: ldc_w -0.75
      // 0510: ldc_w -0.3927
      // 0513: ldc_w -0.7854
      // 0516: fconst_0
      // 0517: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 051a: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 051d: astore 20
      // 051f: aload 2
      // 0520: ldc "skeleton8"
      // 0522: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0525: ldc_w 82.0
      // 0528: ldc_w -29.1667
      // 052b: ldc_w -2.7778
      // 052e: fconst_0
      // 052f: ldc_w -1.1781
      // 0532: ldc_w 0.3927
      // 0535: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0538: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 053b: astore 21
      // 053d: aload 21
      // 053f: ldc "torso8"
      // 0541: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0544: bipush 30
      // 0546: bipush 37
      // 0548: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 054b: ldc_w -2.0
      // 054e: ldc_w -21.9833
      // 0551: ldc_w -2.0
      // 0554: ldc_w 4.0
      // 0557: ldc_w 22.0
      // 055a: ldc_w 3.0
      // 055d: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0560: dup
      // 0561: fconst_0
      // 0562: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0565: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0568: bipush 0
      // 0569: bipush 37
      // 056b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 056e: ldc_w -5.0
      // 0571: ldc_w -5.9833
      // 0574: ldc_w -4.0
      // 0577: ldc_w 10.0
      // 057a: ldc_w 6.0
      // 057d: ldc_w 5.0
      // 0580: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0583: dup
      // 0584: fconst_0
      // 0585: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0588: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 058b: bipush 0
      // 058c: bipush 0
      // 058d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0590: ldc_w -6.0
      // 0593: ldc_w -17.9833
      // 0596: ldc_w -6.0
      // 0599: ldc_w 12.0
      // 059c: ldc_w 12.0
      // 059f: ldc_w 7.0
      // 05a2: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 05a5: dup
      // 05a6: fconst_0
      // 05a7: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 05aa: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 05ad: ldc_w 0.065
      // 05b0: ldc_w 36.3043
      // 05b3: ldc_w 1.6209
      // 05b6: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 05b9: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 05bc: astore 22
      // 05be: aload 22
      // 05c0: ldc "right_arm5"
      // 05c2: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 05c5: bipush 38
      // 05c7: bipush 0
      // 05c8: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 05cb: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 05ce: ldc_w -1.5
      // 05d1: ldc_w -3.0
      // 05d4: ldc_w -1.5
      // 05d7: ldc_w 3.0
      // 05da: ldc_w 22.0
      // 05dd: ldc_w 3.0
      // 05e0: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 05e3: dup
      // 05e4: fconst_0
      // 05e5: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 05e8: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 05eb: bipush 0
      // 05ec: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 05ef: ldc_w -7.2426
      // 05f2: ldc_w -5.5204
      // 05f5: ldc_w -1.1213
      // 05f8: ldc_w -2.0456
      // 05fb: ldc_w 0.7911
      // 05fe: ldc_w -0.5756
      // 0601: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0604: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0607: astore 23
      // 0609: aload 2
      // 060a: ldc "skeleton9"
      // 060c: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 060f: ldc_w -44.0
      // 0612: ldc_w -8.1667
      // 0615: ldc_w -15.7778
      // 0618: ldc_w -1.3526
      // 061b: ldc_w 0.7854
      // 061e: fconst_0
      // 061f: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0622: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0625: astore 24
      // 0627: aload 24
      // 0629: ldc "left_leg4"
      // 062b: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 062e: bipush 38
      // 0630: bipush 0
      // 0631: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0634: ldc_w -1.5
      // 0637: fconst_0
      // 0638: ldc_w -1.5
      // 063b: ldc_w 3.0
      // 063e: ldc_w 22.0
      // 0641: ldc_w 3.0
      // 0644: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0647: dup
      // 0648: fconst_0
      // 0649: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 064c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 064f: ldc_w 3.2071
      // 0652: ldc_w 6.4763
      // 0655: ldc_w 1.4308
      // 0658: fconst_0
      // 0659: fconst_0
      // 065a: ldc_w -0.48
      // 065d: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0660: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0663: astore 25
      // 0665: aload 24
      // 0667: ldc "torso9"
      // 0669: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 066c: fconst_0
      // 066d: ldc_w 6.0019
      // 0670: ldc_w -0.9939
      // 0673: ldc_w 2.3562
      // 0676: fconst_0
      // 0677: fconst_0
      // 0678: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 067b: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 067e: astore 26
      // 0680: aload 26
      // 0682: ldc_w "cube_r1"
      // 0685: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0688: bipush 0
      // 0689: bipush 0
      // 068a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 068d: ldc_w -7.401
      // 0690: ldc_w -10.2787
      // 0693: ldc_w -4.8605
      // 0696: ldc_w 12.0
      // 0699: ldc_w 12.0
      // 069c: ldc_w 7.0
      // 069f: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 06a2: dup
      // 06a3: fconst_0
      // 06a4: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 06a7: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 06aa: bipush 0
      // 06ab: bipush 37
      // 06ad: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 06b0: ldc_w -6.401
      // 06b3: ldc_w 1.7213
      // 06b6: ldc_w -2.8605
      // 06b9: ldc_w 10.0
      // 06bc: ldc_w 6.0
      // 06bf: ldc_w 5.0
      // 06c2: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 06c5: dup
      // 06c6: fconst_0
      // 06c7: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 06ca: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 06cd: bipush 30
      // 06cf: bipush 37
      // 06d1: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 06d4: ldc_w -3.401
      // 06d7: ldc_w -14.2787
      // 06da: ldc_w -0.8605
      // 06dd: ldc_w 4.0
      // 06e0: ldc_w 22.0
      // 06e3: ldc_w 3.0
      // 06e6: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 06e9: dup
      // 06ea: fconst_0
      // 06eb: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 06ee: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 06f1: fconst_0
      // 06f2: ldc_w -7.7637
      // 06f5: ldc_w -1.3232
      // 06f8: fconst_0
      // 06f9: ldc_w -0.9163
      // 06fc: fconst_0
      // 06fd: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0700: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0703: astore 27
      // 0705: aload 26
      // 0707: ldc "left_arm3"
      // 0709: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 070c: bipush 38
      // 070e: bipush 0
      // 070f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0712: ldc_w -1.5
      // 0715: ldc_w -3.0
      // 0718: ldc_w -1.5
      // 071b: ldc_w 3.0
      // 071e: ldc_w 22.0
      // 0721: ldc_w 3.0
      // 0724: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0727: dup
      // 0728: fconst_0
      // 0729: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 072c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 072f: ldc_w 8.9142
      // 0732: ldc_w -12.9114
      // 0735: ldc_w -1.8066
      // 0738: ldc_w -0.9108
      // 073b: ldc_w -0.3035
      // 073e: ldc_w -1.1039
      // 0741: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0744: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0747: astore 28
      // 0749: aload 2
      // 074a: ldc "skeleton10"
      // 074c: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 074f: ldc_w -70.0
      // 0752: ldc_w 8.8333
      // 0755: ldc_w -12.7778
      // 0758: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 075b: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 075e: astore 29
      // 0760: aload 29
      // 0762: ldc "torso10"
      // 0764: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0767: fconst_0
      // 0768: ldc_w 7.15
      // 076b: ldc_w 1.7778
      // 076e: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0771: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0774: astore 30
      // 0776: aload 30
      // 0778: ldc "head7"
      // 077a: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 077d: bipush 0
      // 077e: bipush 19
      // 0780: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0783: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0786: ldc_w -5.0
      // 0789: ldc_w -8.75
      // 078c: ldc_w -7.25
      // 078f: ldc_w 10.0
      // 0792: ldc_w 9.0
      // 0795: ldc_w 9.0
      // 0798: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 079b: dup
      // 079c: fconst_0
      // 079d: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 07a0: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 07a3: bipush 0
      // 07a4: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 07a7: bipush 38
      // 07a9: bipush 25
      // 07ab: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 07ae: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 07b1: ldc_w -3.0
      // 07b4: ldc_w 0.25
      // 07b7: ldc_w -7.25
      // 07ba: ldc_w 6.0
      // 07bd: ldc_w 4.0
      // 07c0: ldc_w 4.0
      // 07c3: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 07c6: dup
      // 07c7: fconst_0
      // 07c8: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 07cb: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 07ce: bipush 0
      // 07cf: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 07d2: ldc_w 3.0
      // 07d5: ldc_w -17.2333
      // 07d8: ldc_w -6.75
      // 07db: ldc_w -0.3685
      // 07de: ldc_w 0.5102
      // 07e1: ldc_w 0.0208
      // 07e4: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 07e7: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 07ea: astore 31
      // 07ec: aload 2
      // 07ed: ldc "skeleton11"
      // 07ef: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 07f2: ldc_w 28.0
      // 07f5: ldc_w -55.1667
      // 07f8: ldc_w -17.7778
      // 07fb: ldc_w 1.5708
      // 07fe: ldc_w 0.3927
      // 0801: ldc_w -3.1416
      // 0804: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0807: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 080a: astore 32
      // 080c: aload 32
      // 080e: ldc "torso11"
      // 0810: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0813: bipush 30
      // 0815: bipush 37
      // 0817: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 081a: ldc_w -2.0
      // 081d: ldc_w -21.9833
      // 0820: ldc_w -2.0
      // 0823: ldc_w 4.0
      // 0826: ldc_w 22.0
      // 0829: ldc_w 3.0
      // 082c: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 082f: dup
      // 0830: fconst_0
      // 0831: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0834: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0837: bipush 0
      // 0838: bipush 37
      // 083a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 083d: ldc_w -5.0
      // 0840: ldc_w -5.9833
      // 0843: ldc_w -4.0
      // 0846: ldc_w 10.0
      // 0849: ldc_w 6.0
      // 084c: ldc_w 5.0
      // 084f: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0852: dup
      // 0853: fconst_0
      // 0854: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0857: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 085a: bipush 0
      // 085b: bipush 0
      // 085c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 085f: ldc_w -6.0
      // 0862: ldc_w -17.9833
      // 0865: ldc_w -6.0
      // 0868: ldc_w 12.0
      // 086b: ldc_w 12.0
      // 086e: ldc_w 7.0
      // 0871: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0874: dup
      // 0875: fconst_0
      // 0876: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0879: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 087c: ldc_w -0.6997
      // 087f: ldc_w 3.2657
      // 0882: ldc_w -0.4418
      // 0885: ldc_w -0.1745
      // 0888: fconst_0
      // 0889: fconst_0
      // 088a: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 088d: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0890: astore 33
      // 0892: aload 33
      // 0894: ldc "right_arm6"
      // 0896: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0899: bipush 38
      // 089b: bipush 0
      // 089c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 089f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 08a2: ldc_w -1.5
      // 08a5: ldc_w -3.0
      // 08a8: ldc_w -1.5
      // 08ab: ldc_w 3.0
      // 08ae: ldc_w 22.0
      // 08b1: ldc_w 3.0
      // 08b4: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 08b7: dup
      // 08b8: fconst_0
      // 08b9: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 08bc: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 08bf: bipush 0
      // 08c0: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 08c3: ldc_w -7.2758
      // 08c6: ldc_w -18.6107
      // 08c9: ldc_w 1.9067
      // 08cc: ldc_w 0.6776
      // 08cf: ldc_w -0.274
      // 08d2: ldc_w 2.7093
      // 08d5: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 08d8: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 08db: astore 34
      // 08dd: aload 2
      // 08de: ldc "skeleton12"
      // 08e0: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 08e3: ldc_w 33.0
      // 08e6: ldc_w -56.1667
      // 08e9: ldc_w 10.2222
      // 08ec: fconst_0
      // 08ed: ldc_w 0.7854
      // 08f0: fconst_0
      // 08f1: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 08f4: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 08f7: astore 35
      // 08f9: aload 35
      // 08fb: ldc "torso12"
      // 08fd: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0900: fconst_0
      // 0901: ldc_w 8.15
      // 0904: ldc_w 1.7778
      // 0907: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 090a: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 090d: astore 36
      // 090f: aload 36
      // 0911: ldc "head8"
      // 0913: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0916: bipush 0
      // 0917: bipush 19
      // 0919: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 091c: ldc_w -5.0
      // 091f: ldc_w -8.75
      // 0922: ldc_w -7.25
      // 0925: ldc_w 10.0
      // 0928: ldc_w 9.0
      // 092b: ldc_w 9.0
      // 092e: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0931: dup
      // 0932: fconst_0
      // 0933: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0936: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0939: bipush 38
      // 093b: bipush 25
      // 093d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0940: ldc_w -3.0
      // 0943: ldc_w 0.25
      // 0946: ldc_w -7.25
      // 0949: ldc_w 6.0
      // 094c: ldc_w 4.0
      // 094f: ldc_w 4.0
      // 0952: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0955: dup
      // 0956: fconst_0
      // 0957: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 095a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 095d: ldc_w -3.0
      // 0960: ldc_w -17.2333
      // 0963: ldc_w -6.75
      // 0966: ldc_w -0.3685
      // 0969: ldc_w -0.5102
      // 096c: ldc_w -0.0208
      // 096f: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0972: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0975: astore 37
      // 0977: aload 2
      // 0978: ldc "skeleton13"
      // 097a: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 097d: ldc_w 159.0
      // 0980: ldc_w -29.1667
      // 0983: ldc_w -2.7778
      // 0986: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0989: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 098c: astore 38
      // 098e: aload 38
      // 0990: ldc "torso13"
      // 0992: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0995: ldc_w -151.0
      // 0998: ldc_w 13.15
      // 099b: ldc_w -65.2222
      // 099e: ldc_w -1.1781
      // 09a1: fconst_0
      // 09a2: fconst_0
      // 09a3: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 09a6: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 09a9: astore 39
      // 09ab: aload 39
      // 09ad: ldc "left_arm4"
      // 09af: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 09b2: bipush 38
      // 09b4: bipush 0
      // 09b5: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 09b8: ldc_w -2.2856
      // 09bb: ldc_w 4.8209
      // 09be: ldc_w -1.035
      // 09c1: ldc_w 3.0
      // 09c4: ldc_w 22.0
      // 09c7: ldc_w 3.0
      // 09ca: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 09cd: dup
      // 09ce: fconst_0
      // 09cf: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 09d2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 09d5: ldc_w 13.5
      // 09d8: ldc_w -7.1111
      // 09db: ldc_w 5.4396
      // 09de: ldc_w 0.5331
      // 09e1: ldc_w -0.5296
      // 09e4: ldc_w 0.9154
      // 09e7: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 09ea: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 09ed: astore 40
      // 09ef: aload 2
      // 09f0: ldc "skeleton14"
      // 09f2: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 09f5: ldc_w -54.8787
      // 09f8: ldc_w -1.8766
      // 09fb: ldc_w 16.0887
      // 09fe: ldc_w -1.3143
      // 0a01: ldc_w 1.2493
      // 0a04: ldc_w -2.7425
      // 0a07: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0a0a: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0a0d: astore 41
      // 0a0f: aload 41
      // 0a11: ldc "torso14"
      // 0a13: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0a16: bipush 30
      // 0a18: bipush 37
      // 0a1a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0a1d: ldc_w -2.0
      // 0a20: ldc_w -21.9833
      // 0a23: ldc_w -2.0
      // 0a26: ldc_w 4.0
      // 0a29: ldc_w 22.0
      // 0a2c: ldc_w 3.0
      // 0a2f: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0a32: dup
      // 0a33: fconst_0
      // 0a34: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0a37: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0a3a: bipush 0
      // 0a3b: bipush 37
      // 0a3d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0a40: ldc_w -5.0
      // 0a43: ldc_w -5.9833
      // 0a46: ldc_w -4.0
      // 0a49: ldc_w 10.0
      // 0a4c: ldc_w 6.0
      // 0a4f: ldc_w 5.0
      // 0a52: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0a55: dup
      // 0a56: fconst_0
      // 0a57: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0a5a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0a5d: bipush 0
      // 0a5e: bipush 0
      // 0a5f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0a62: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0a65: ldc_w -6.0
      // 0a68: ldc_w -17.9833
      // 0a6b: ldc_w -6.0
      // 0a6e: ldc_w 12.0
      // 0a71: ldc_w 12.0
      // 0a74: ldc_w 7.0
      // 0a77: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0a7a: dup
      // 0a7b: fconst_0
      // 0a7c: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0a7f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0a82: bipush 0
      // 0a83: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0a86: fconst_0
      // 0a87: ldc_w 7.15
      // 0a8a: ldc_w 1.7778
      // 0a8d: ldc_w -0.7854
      // 0a90: fconst_0
      // 0a91: fconst_0
      // 0a92: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0a95: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0a98: astore 42
      // 0a9a: aload 42
      // 0a9c: ldc "head9"
      // 0a9e: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0aa1: bipush 0
      // 0aa2: bipush 19
      // 0aa4: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0aa7: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0aaa: ldc_w -5.0
      // 0aad: ldc_w -8.75
      // 0ab0: ldc_w -7.25
      // 0ab3: ldc_w 10.0
      // 0ab6: ldc_w 9.0
      // 0ab9: ldc_w 9.0
      // 0abc: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0abf: dup
      // 0ac0: fconst_0
      // 0ac1: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0ac4: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0ac7: bipush 0
      // 0ac8: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0acb: bipush 38
      // 0acd: bipush 25
      // 0acf: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0ad2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0ad5: ldc_w -3.0
      // 0ad8: ldc_w 0.25
      // 0adb: ldc_w -7.25
      // 0ade: ldc_w 6.0
      // 0ae1: ldc_w 4.0
      // 0ae4: ldc_w 4.0
      // 0ae7: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0aea: dup
      // 0aeb: fconst_0
      // 0aec: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0aef: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0af2: bipush 0
      // 0af3: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0af6: fconst_0
      // 0af7: ldc_w -22.2333
      // 0afa: ldc_w -0.75
      // 0afd: ldc_w -0.7459
      // 0b00: ldc_w -0.274
      // 0b03: ldc_w -0.2849
      // 0b06: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0b09: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0b0c: astore 43
      // 0b0e: aload 42
      // 0b10: ldc "right_arm7"
      // 0b12: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0b15: bipush 38
      // 0b17: bipush 0
      // 0b18: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0b1b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0b1e: ldc_w -1.5
      // 0b21: ldc_w -3.0
      // 0b24: ldc_w -1.5
      // 0b27: ldc_w 3.0
      // 0b2a: ldc_w 22.0
      // 0b2d: ldc_w 3.0
      // 0b30: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0b33: dup
      // 0b34: fconst_0
      // 0b35: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0b38: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0b3b: bipush 0
      // 0b3c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0b3f: ldc_w 9.9046
      // 0b42: ldc_w -17.1122
      // 0b45: ldc_w -3.4777
      // 0b48: ldc_w -1.9923
      // 0b4b: ldc_w -0.3614
      // 0b4e: ldc_w 0.1572
      // 0b51: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0b54: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0b57: astore 44
      // 0b59: aload 42
      // 0b5b: ldc "left_arm5"
      // 0b5d: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0b60: bipush 38
      // 0b62: bipush 0
      // 0b63: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0b66: ldc_w -1.5
      // 0b69: ldc_w -3.0
      // 0b6c: ldc_w -1.5
      // 0b6f: ldc_w 3.0
      // 0b72: ldc_w 22.0
      // 0b75: ldc_w 3.0
      // 0b78: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0b7b: dup
      // 0b7c: fconst_0
      // 0b7d: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0b80: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0b83: ldc_w -6.7168
      // 0b86: ldc_w -19.4873
      // 0b89: ldc_w -0.183
      // 0b8c: ldc_w -1.1781
      // 0b8f: fconst_0
      // 0b90: ldc_w 0.3927
      // 0b93: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0b96: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0b99: astore 45
      // 0b9b: aload 2
      // 0b9c: ldc "skeleton6"
      // 0b9e: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0ba1: ldc_w -13.0
      // 0ba4: ldc_w -49.1667
      // 0ba7: ldc_w -32.7778
      // 0baa: ldc_w 0.3927
      // 0bad: ldc_w -0.7854
      // 0bb0: fconst_0
      // 0bb1: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0bb4: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0bb7: astore 46
      // 0bb9: aload 46
      // 0bbb: ldc "left_leg3"
      // 0bbd: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0bc0: bipush 38
      // 0bc2: bipush 0
      // 0bc3: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0bc6: ldc_w -0.9265
      // 0bc9: ldc_w 1.2695
      // 0bcc: ldc_w -1.7439
      // 0bcf: ldc_w 3.0
      // 0bd2: ldc_w 22.0
      // 0bd5: ldc_w 3.0
      // 0bd8: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0bdb: dup
      // 0bdc: fconst_0
      // 0bdd: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0be0: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0be3: ldc_w 9.5711
      // 0be6: ldc_w 5.4502
      // 0be9: ldc_w -13.3186
      // 0bec: ldc_w -1.8665
      // 0bef: ldc_w 0.1202
      // 0bf2: ldc_w -0.8881
      // 0bf5: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0bf8: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0bfb: astore 47
      // 0bfd: aload 46
      // 0bff: ldc "right_leg3"
      // 0c01: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0c04: ldc_w -4.6213
      // 0c07: ldc_w 5.8137
      // 0c0a: ldc_w -1.9886
      // 0c0d: ldc_w -0.2427
      // 0c10: ldc_w 0.4197
      // 0c13: ldc_w 0.2342
      // 0c16: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0c19: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0c1c: astore 48
      // 0c1e: aload 46
      // 0c20: ldc "torso6"
      // 0c22: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0c25: bipush 30
      // 0c27: bipush 37
      // 0c29: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0c2c: ldc_w -2.0
      // 0c2f: ldc_w -21.9833
      // 0c32: ldc_w -2.0
      // 0c35: ldc_w 4.0
      // 0c38: ldc_w 22.0
      // 0c3b: ldc_w 3.0
      // 0c3e: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0c41: dup
      // 0c42: fconst_0
      // 0c43: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0c46: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0c49: bipush 0
      // 0c4a: bipush 37
      // 0c4c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0c4f: ldc_w -5.0
      // 0c52: ldc_w -5.9833
      // 0c55: ldc_w -4.0
      // 0c58: ldc_w 10.0
      // 0c5b: ldc_w 6.0
      // 0c5e: ldc_w 5.0
      // 0c61: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0c64: dup
      // 0c65: fconst_0
      // 0c66: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0c69: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0c6c: bipush 0
      // 0c6d: bipush 0
      // 0c6e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0c71: ldc_w -6.0
      // 0c74: ldc_w -17.9833
      // 0c77: ldc_w -6.0
      // 0c7a: ldc_w 12.0
      // 0c7d: ldc_w 12.0
      // 0c80: ldc_w 7.0
      // 0c83: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0c86: dup
      // 0c87: fconst_0
      // 0c88: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0c8b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0c8e: ldc_w 10.6066
      // 0c91: ldc_w 3.8564
      // 0c94: ldc_w -11.3999
      // 0c97: ldc_w -1.2774
      // 0c9a: ldc_w -0.6347
      // 0c9d: ldc_w -0.4949
      // 0ca0: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0ca3: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0ca6: astore 49
      // 0ca8: aload 2
      // 0ca9: ldc "skeleton15"
      // 0cab: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0cae: fconst_2
      // 0caf: ldc_w -7.1667
      // 0cb2: ldc_w -47.7778
      // 0cb5: ldc_w 0.3927
      // 0cb8: ldc_w -0.7854
      // 0cbb: fconst_0
      // 0cbc: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0cbf: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0cc2: astore 50
      // 0cc4: aload 50
      // 0cc6: ldc "torso15"
      // 0cc8: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0ccb: bipush 30
      // 0ccd: bipush 37
      // 0ccf: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0cd2: ldc_w -2.0
      // 0cd5: ldc_w -21.9833
      // 0cd8: ldc_w -2.0
      // 0cdb: ldc_w 4.0
      // 0cde: ldc_w 22.0
      // 0ce1: ldc_w 3.0
      // 0ce4: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0ce7: dup
      // 0ce8: fconst_0
      // 0ce9: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0cec: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0cef: bipush 0
      // 0cf0: bipush 37
      // 0cf2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0cf5: ldc_w -5.0
      // 0cf8: ldc_w -5.9833
      // 0cfb: ldc_w -4.0
      // 0cfe: ldc_w 10.0
      // 0d01: ldc_w 6.0
      // 0d04: ldc_w 5.0
      // 0d07: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0d0a: dup
      // 0d0b: fconst_0
      // 0d0c: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0d0f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0d12: bipush 0
      // 0d13: bipush 0
      // 0d14: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0d17: ldc_w -6.0
      // 0d1a: ldc_w -17.9833
      // 0d1d: ldc_w -6.0
      // 0d20: ldc_w 12.0
      // 0d23: ldc_w 12.0
      // 0d26: ldc_w 7.0
      // 0d29: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0d2c: dup
      // 0d2d: fconst_0
      // 0d2e: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0d31: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0d34: fconst_0
      // 0d35: ldc_w 7.15
      // 0d38: ldc_w 1.7778
      // 0d3b: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0d3e: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0d41: astore 51
      // 0d43: aload 51
      // 0d45: ldc "head10"
      // 0d47: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0d4a: bipush 0
      // 0d4b: bipush 19
      // 0d4d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0d50: ldc_w -5.0
      // 0d53: ldc_w -8.75
      // 0d56: ldc_w -7.25
      // 0d59: ldc_w 10.0
      // 0d5c: ldc_w 9.0
      // 0d5f: ldc_w 9.0
      // 0d62: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0d65: dup
      // 0d66: fconst_0
      // 0d67: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0d6a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0d6d: bipush 38
      // 0d6f: bipush 25
      // 0d71: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0d74: ldc_w -3.0
      // 0d77: ldc_w 0.25
      // 0d7a: ldc_w -7.25
      // 0d7d: ldc_w 6.0
      // 0d80: ldc_w 4.0
      // 0d83: ldc_w 4.0
      // 0d86: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0d89: dup
      // 0d8a: fconst_0
      // 0d8b: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0d8e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0d91: fconst_0
      // 0d92: ldc_w -23.0767
      // 0d95: ldc_w -1.2873
      // 0d98: ldc_w -1.0472
      // 0d9b: ldc_w -0.2291
      // 0d9e: ldc_w 0.3747
      // 0da1: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0da4: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0da7: astore 52
      // 0da9: aload 51
      // 0dab: ldc "right_arm8"
      // 0dad: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0db0: bipush 38
      // 0db2: bipush 0
      // 0db3: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0db6: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0db9: ldc_w -1.5
      // 0dbc: ldc_w -3.0
      // 0dbf: ldc_w -1.5
      // 0dc2: ldc_w 3.0
      // 0dc5: ldc_w 22.0
      // 0dc8: ldc_w 3.0
      // 0dcb: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0dce: dup
      // 0dcf: fconst_0
      // 0dd0: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0dd3: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0dd6: bipush 0
      // 0dd7: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0dda: ldc_w -6.7929
      // 0ddd: ldc_w -16.6904
      // 0de0: ldc_w 0.2654
      // 0de3: ldc_w -0.3927
      // 0de6: fconst_0
      // 0de7: ldc_w 0.3927
      // 0dea: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0ded: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0df0: astore 53
      // 0df2: aload 51
      // 0df4: ldc "left_arm6"
      // 0df6: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0df9: bipush 38
      // 0dfb: bipush 0
      // 0dfc: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0dff: ldc_w -1.5
      // 0e02: ldc_w -3.0
      // 0e05: ldc_w -1.5
      // 0e08: ldc_w 3.0
      // 0e0b: ldc_w 22.0
      // 0e0e: ldc_w 3.0
      // 0e11: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0e14: dup
      // 0e15: fconst_0
      // 0e16: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0e19: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0e1c: ldc_w 7.5
      // 0e1f: ldc_w -14.9833
      // 0e22: ldc_w -0.5
      // 0e25: ldc_w -0.9599
      // 0e28: fconst_0
      // 0e29: ldc_w -0.3927
      // 0e2c: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0e2f: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0e32: astore 54
      // 0e34: aload 2
      // 0e35: ldc "skeleton16"
      // 0e37: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0e3a: ldc_w -4.0
      // 0e3d: ldc_w -4.1667
      // 0e40: ldc_w -38.7778
      // 0e43: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0e46: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0e49: astore 55
      // 0e4b: aload 55
      // 0e4d: ldc "torso16"
      // 0e4f: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0e52: fconst_0
      // 0e53: ldc_w 8.15
      // 0e56: ldc_w 1.7778
      // 0e59: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0e5c: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0e5f: astore 56
      // 0e61: aload 56
      // 0e63: ldc "head11"
      // 0e65: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0e68: bipush 0
      // 0e69: bipush 19
      // 0e6b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0e6e: ldc_w -5.0
      // 0e71: ldc_w -8.75
      // 0e74: ldc_w -7.25
      // 0e77: ldc_w 10.0
      // 0e7a: ldc_w 9.0
      // 0e7d: ldc_w 9.0
      // 0e80: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0e83: dup
      // 0e84: fconst_0
      // 0e85: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0e88: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0e8b: bipush 38
      // 0e8d: bipush 25
      // 0e8f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0e92: ldc_w -3.0
      // 0e95: ldc_w 0.25
      // 0e98: ldc_w -7.25
      // 0e9b: ldc_w 6.0
      // 0e9e: ldc_w 4.0
      // 0ea1: ldc_w 4.0
      // 0ea4: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0ea7: dup
      // 0ea8: fconst_0
      // 0ea9: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0eac: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0eaf: ldc_w -2.3411
      // 0eb2: ldc_w -18.4758
      // 0eb5: ldc_w -6.8986
      // 0eb8: ldc_w -0.3419
      // 0ebb: ldc_w 0.4649
      // 0ebe: ldc_w -0.3491
      // 0ec1: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0ec4: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0ec7: astore 57
      // 0ec9: aload 2
      // 0eca: ldc "skeleton17"
      // 0ecc: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0ecf: ldc_w -26.2513
      // 0ed2: ldc_w -3.1576
      // 0ed5: ldc_w -44.1622
      // 0ed8: ldc_w 0.4818
      // 0edb: ldc_w 0.9412
      // 0ede: ldc_w -0.1026
      // 0ee1: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0ee4: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0ee7: astore 58
      // 0ee9: aload 58
      // 0eeb: ldc "torso17"
      // 0eed: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0ef0: bipush 30
      // 0ef2: bipush 37
      // 0ef4: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0ef7: ldc_w -2.0
      // 0efa: ldc_w -21.9833
      // 0efd: ldc_w -2.0
      // 0f00: ldc_w 4.0
      // 0f03: ldc_w 22.0
      // 0f06: ldc_w 3.0
      // 0f09: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0f0c: dup
      // 0f0d: fconst_0
      // 0f0e: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0f11: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0f14: bipush 0
      // 0f15: bipush 37
      // 0f17: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0f1a: ldc_w -5.0
      // 0f1d: ldc_w -5.9833
      // 0f20: ldc_w -4.0
      // 0f23: ldc_w 10.0
      // 0f26: ldc_w 6.0
      // 0f29: ldc_w 5.0
      // 0f2c: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0f2f: dup
      // 0f30: fconst_0
      // 0f31: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0f34: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0f37: bipush 0
      // 0f38: bipush 0
      // 0f39: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0f3c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0f3f: ldc_w -6.0
      // 0f42: ldc_w -17.9833
      // 0f45: ldc_w -6.0
      // 0f48: ldc_w 12.0
      // 0f4b: ldc_w 12.0
      // 0f4e: ldc_w 7.0
      // 0f51: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0f54: dup
      // 0f55: fconst_0
      // 0f56: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0f59: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0f5c: bipush 0
      // 0f5d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0f60: fconst_0
      // 0f61: ldc_w 7.15
      // 0f64: ldc_w 1.7778
      // 0f67: ldc_w -0.7854
      // 0f6a: fconst_0
      // 0f6b: fconst_0
      // 0f6c: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0f6f: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0f72: astore 59
      // 0f74: aload 59
      // 0f76: ldc "right_arm9"
      // 0f78: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0f7b: bipush 38
      // 0f7d: bipush 0
      // 0f7e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0f81: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0f84: ldc_w -1.5
      // 0f87: ldc_w -3.0
      // 0f8a: ldc_w -1.5
      // 0f8d: ldc_w 3.0
      // 0f90: ldc_w 22.0
      // 0f93: ldc_w 3.0
      // 0f96: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0f99: dup
      // 0f9a: fconst_0
      // 0f9b: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0f9e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0fa1: bipush 0
      // 0fa2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0fa5: ldc_w 9.4147
      // 0fa8: ldc_w -16.337
      // 0fab: ldc_w -4.0662
      // 0fae: ldc_w -1.9923
      // 0fb1: ldc_w -0.3614
      // 0fb4: ldc_w 0.1572
      // 0fb7: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0fba: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0fbd: astore 60
      // 0fbf: aload 2
      // 0fc0: ldc "skeleton18"
      // 0fc2: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0fc5: ldc_w -36.0
      // 0fc8: ldc_w -3.1667
      // 0fcb: ldc_w -42.7778
      // 0fce: ldc_w 0.6699
      // 0fd1: ldc_w -0.3775
      // 0fd4: ldc_w 0.3719
      // 0fd7: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0fda: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0fdd: astore 61
      // 0fdf: aload 61
      // 0fe1: ldc "torso18"
      // 0fe3: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0fe6: bipush 30
      // 0fe8: bipush 37
      // 0fea: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0fed: ldc_w -2.0
      // 0ff0: ldc_w -21.9833
      // 0ff3: ldc_w -2.0
      // 0ff6: ldc_w 4.0
      // 0ff9: ldc_w 22.0
      // 0ffc: ldc_w 3.0
      // 0fff: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1002: dup
      // 1003: fconst_0
      // 1004: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1007: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 100a: bipush 0
      // 100b: bipush 37
      // 100d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1010: ldc_w -5.0
      // 1013: ldc_w -5.9833
      // 1016: ldc_w -4.0
      // 1019: ldc_w 10.0
      // 101c: ldc_w 6.0
      // 101f: ldc_w 5.0
      // 1022: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1025: dup
      // 1026: fconst_0
      // 1027: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 102a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 102d: bipush 0
      // 102e: bipush 0
      // 102f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1032: ldc_w -6.0
      // 1035: ldc_w -17.9833
      // 1038: ldc_w -6.0
      // 103b: ldc_w 12.0
      // 103e: ldc_w 12.0
      // 1041: ldc_w 7.0
      // 1044: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1047: dup
      // 1048: fconst_0
      // 1049: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 104c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 104f: ldc_w -0.866
      // 1052: ldc_w 7.6481
      // 1055: ldc_w 1.8214
      // 1058: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 105b: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 105e: astore 62
      // 1060: aload 2
      // 1061: ldc_w "skeleton19"
      // 1064: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1067: ldc_w -38.0
      // 106a: ldc_w -8.1667
      // 106d: ldc_w -31.7778
      // 1070: ldc_w -0.6703
      // 1073: ldc_w 0.7933
      // 1076: ldc_w 0.2952
      // 1079: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 107c: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 107f: astore 63
      // 1081: aload 63
      // 1083: ldc_w "left_leg2"
      // 1086: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1089: bipush 38
      // 108b: bipush 0
      // 108c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 108f: ldc_w -1.5
      // 1092: fconst_0
      // 1093: ldc_w -1.5
      // 1096: ldc_w 3.0
      // 1099: ldc_w 22.0
      // 109c: ldc_w 3.0
      // 109f: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 10a2: dup
      // 10a3: fconst_0
      // 10a4: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 10a7: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 10aa: ldc_w 2.5
      // 10ad: ldc_w 7.1667
      // 10b0: ldc_w 1.2778
      // 10b3: ldc_w 2.3957
      // 10b6: ldc_w 0.274
      // 10b9: ldc_w 0.2849
      // 10bc: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 10bf: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 10c2: astore 64
      // 10c4: aload 63
      // 10c6: ldc_w "right_leg2"
      // 10c9: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 10cc: bipush 38
      // 10ce: bipush 0
      // 10cf: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 10d2: ldc_w -1.5
      // 10d5: fconst_0
      // 10d6: ldc_w -1.5
      // 10d9: ldc_w 3.0
      // 10dc: ldc_w 22.0
      // 10df: ldc_w 3.0
      // 10e2: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 10e5: dup
      // 10e6: fconst_0
      // 10e7: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 10ea: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 10ed: ldc_w -2.5
      // 10f0: ldc_w 7.1667
      // 10f3: ldc_w 1.2778
      // 10f6: ldc_w 2.3957
      // 10f9: ldc_w -0.274
      // 10fc: ldc_w -0.2849
      // 10ff: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1102: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1105: astore 65
      // 1107: aload 63
      // 1109: ldc_w "torso19"
      // 110c: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 110f: bipush 30
      // 1111: bipush 37
      // 1113: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1116: ldc_w -2.0
      // 1119: ldc_w -21.9833
      // 111c: ldc_w -2.0
      // 111f: ldc_w 4.0
      // 1122: ldc_w 22.0
      // 1125: ldc_w 3.0
      // 1128: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 112b: dup
      // 112c: fconst_0
      // 112d: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1130: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1133: bipush 0
      // 1134: bipush 37
      // 1136: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1139: ldc_w -5.0
      // 113c: ldc_w -5.9833
      // 113f: ldc_w -4.0
      // 1142: ldc_w 10.0
      // 1145: ldc_w 6.0
      // 1148: ldc_w 5.0
      // 114b: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 114e: dup
      // 114f: fconst_0
      // 1150: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1153: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1156: bipush 0
      // 1157: bipush 0
      // 1158: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 115b: ldc_w -6.0
      // 115e: ldc_w -17.9833
      // 1161: ldc_w -6.0
      // 1164: ldc_w 12.0
      // 1167: ldc_w 12.0
      // 116a: ldc_w 7.0
      // 116d: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1170: dup
      // 1171: fconst_0
      // 1172: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1175: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1178: fconst_0
      // 1179: ldc_w 7.15
      // 117c: ldc_w 1.7778
      // 117f: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1182: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1185: astore 66
      // 1187: aload 66
      // 1189: ldc_w "head12"
      // 118c: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 118f: bipush 0
      // 1190: bipush 19
      // 1192: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1195: ldc_w -5.0
      // 1198: ldc_w -8.75
      // 119b: ldc_w -7.25
      // 119e: ldc_w 10.0
      // 11a1: ldc_w 9.0
      // 11a4: ldc_w 9.0
      // 11a7: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 11aa: dup
      // 11ab: fconst_0
      // 11ac: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 11af: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 11b2: bipush 38
      // 11b4: bipush 25
      // 11b6: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 11b9: ldc_w -3.0
      // 11bc: ldc_w 0.25
      // 11bf: ldc_w -7.25
      // 11c2: ldc_w 6.0
      // 11c5: ldc_w 4.0
      // 11c8: ldc_w 4.0
      // 11cb: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 11ce: dup
      // 11cf: fconst_0
      // 11d0: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 11d3: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 11d6: fconst_0
      // 11d7: ldc_w -22.9404
      // 11da: ldc_w -4.0429
      // 11dd: ldc_w 0.6139
      // 11e0: ldc_w 0.3998
      // 11e3: ldc_w -0.1566
      // 11e6: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 11e9: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 11ec: astore 67
      // 11ee: aload 2
      // 11ef: ldc_w "skeleton20"
      // 11f2: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 11f5: ldc_w 35.0
      // 11f8: ldc_w -3.1667
      // 11fb: ldc_w -51.7778
      // 11fe: ldc_w -0.7481
      // 1201: ldc_w -0.9193
      // 1204: ldc_w -0.5298
      // 1207: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 120a: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 120d: astore 68
      // 120f: aload 68
      // 1211: ldc_w "torso20"
      // 1214: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1217: bipush 30
      // 1219: bipush 37
      // 121b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 121e: ldc_w -2.0
      // 1221: ldc_w -21.9833
      // 1224: ldc_w -2.0
      // 1227: ldc_w 4.0
      // 122a: ldc_w 22.0
      // 122d: ldc_w 3.0
      // 1230: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1233: dup
      // 1234: fconst_0
      // 1235: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1238: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 123b: bipush 0
      // 123c: bipush 37
      // 123e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1241: ldc_w -5.0
      // 1244: ldc_w -5.9833
      // 1247: ldc_w -4.0
      // 124a: ldc_w 10.0
      // 124d: ldc_w 6.0
      // 1250: ldc_w 5.0
      // 1253: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1256: dup
      // 1257: fconst_0
      // 1258: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 125b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 125e: bipush 0
      // 125f: bipush 0
      // 1260: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1263: ldc_w -6.0
      // 1266: ldc_w -17.9833
      // 1269: ldc_w -6.0
      // 126c: ldc_w 12.0
      // 126f: ldc_w 12.0
      // 1272: ldc_w 7.0
      // 1275: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1278: dup
      // 1279: fconst_0
      // 127a: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 127d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1280: ldc_w -0.866
      // 1283: ldc_w 7.6481
      // 1286: ldc_w 1.8214
      // 1289: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 128c: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 128f: astore 69
      // 1291: aload 2
      // 1292: ldc_w "skeleton21"
      // 1295: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1298: ldc_w 26.0
      // 129b: ldc_w -13.1667
      // 129e: ldc_w -32.7778
      // 12a1: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 12a4: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 12a7: astore 70
      // 12a9: aload 70
      // 12ab: ldc_w "torso21"
      // 12ae: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 12b1: bipush 30
      // 12b3: bipush 37
      // 12b5: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 12b8: ldc_w -2.0
      // 12bb: ldc_w -21.9833
      // 12be: ldc_w -2.0
      // 12c1: ldc_w 4.0
      // 12c4: ldc_w 22.0
      // 12c7: ldc_w 3.0
      // 12ca: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 12cd: dup
      // 12ce: fconst_0
      // 12cf: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 12d2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 12d5: bipush 0
      // 12d6: bipush 37
      // 12d8: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 12db: ldc_w -5.0
      // 12de: ldc_w -5.9833
      // 12e1: ldc_w -4.0
      // 12e4: ldc_w 10.0
      // 12e7: ldc_w 6.0
      // 12ea: ldc_w 5.0
      // 12ed: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 12f0: dup
      // 12f1: fconst_0
      // 12f2: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 12f5: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 12f8: bipush 0
      // 12f9: bipush 0
      // 12fa: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 12fd: ldc_w -6.0
      // 1300: ldc_w -17.9833
      // 1303: ldc_w -6.0
      // 1306: ldc_w 12.0
      // 1309: ldc_w 12.0
      // 130c: ldc_w 7.0
      // 130f: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1312: dup
      // 1313: fconst_0
      // 1314: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1317: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 131a: fconst_0
      // 131b: ldc_w 7.15
      // 131e: ldc_w 1.7778
      // 1321: ldc_w 0.7974
      // 1324: ldc_w 0.1536
      // 1327: ldc_w 0.1555
      // 132a: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 132d: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1330: astore 71
      // 1332: aload 71
      // 1334: ldc_w "right_arm10"
      // 1337: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 133a: bipush 38
      // 133c: bipush 0
      // 133d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1340: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1343: ldc_w -1.5
      // 1346: ldc_w -3.0
      // 1349: ldc_w -1.5
      // 134c: ldc_w 3.0
      // 134f: ldc_w 22.0
      // 1352: ldc_w 3.0
      // 1355: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1358: dup
      // 1359: fconst_0
      // 135a: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 135d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1360: bipush 0
      // 1361: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1364: ldc_w -7.7111
      // 1367: ldc_w -14.2762
      // 136a: ldc_w -4.1682
      // 136d: ldc_w -0.6109
      // 1370: fconst_0
      // 1371: fconst_0
      // 1372: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1375: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1378: astore 72
      // 137a: aload 71
      // 137c: ldc_w "left_arm7"
      // 137f: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1382: bipush 38
      // 1384: bipush 0
      // 1385: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1388: ldc_w -1.5
      // 138b: ldc_w -3.0
      // 138e: ldc_w -1.5
      // 1391: ldc_w 3.0
      // 1394: ldc_w 22.0
      // 1397: ldc_w 3.0
      // 139a: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 139d: dup
      // 139e: fconst_0
      // 139f: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 13a2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 13a5: ldc_w 7.5
      // 13a8: ldc_w -14.9833
      // 13ab: ldc_w -0.5
      // 13ae: ldc_w -1.1278
      // 13b1: ldc_w 0.0639
      // 13b4: ldc_w -2.107
      // 13b7: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 13ba: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 13bd: astore 73
      // 13bf: aload 2
      // 13c0: ldc_w "skeleton22"
      // 13c3: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 13c6: ldc_w 33.3316
      // 13c9: ldc_w -19.0412
      // 13cc: ldc_w -24.3485
      // 13cf: ldc_w -0.1592
      // 13d2: ldc_w -0.3138
      // 13d5: ldc_w 0.7571
      // 13d8: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 13db: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 13de: astore 74
      // 13e0: aload 74
      // 13e2: ldc_w "torso22"
      // 13e5: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 13e8: bipush 30
      // 13ea: bipush 37
      // 13ec: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 13ef: ldc_w -2.0
      // 13f2: ldc_w -21.9833
      // 13f5: ldc_w -2.0
      // 13f8: ldc_w 4.0
      // 13fb: ldc_w 22.0
      // 13fe: ldc_w 3.0
      // 1401: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1404: dup
      // 1405: fconst_0
      // 1406: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1409: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 140c: bipush 0
      // 140d: bipush 37
      // 140f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1412: ldc_w -5.0
      // 1415: ldc_w -5.9833
      // 1418: ldc_w -4.0
      // 141b: ldc_w 10.0
      // 141e: ldc_w 6.0
      // 1421: ldc_w 5.0
      // 1424: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1427: dup
      // 1428: fconst_0
      // 1429: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 142c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 142f: bipush 0
      // 1430: bipush 0
      // 1431: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1434: ldc_w -6.0
      // 1437: ldc_w -17.9833
      // 143a: ldc_w -6.0
      // 143d: ldc_w 12.0
      // 1440: ldc_w 12.0
      // 1443: ldc_w 7.0
      // 1446: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1449: dup
      // 144a: fconst_0
      // 144b: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 144e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1451: fconst_0
      // 1452: ldc_w 7.15
      // 1455: ldc_w 1.7778
      // 1458: ldc_w 0.9553
      // 145b: ldc_w 0.5236
      // 145e: ldc_w 0.6155
      // 1461: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1464: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1467: astore 75
      // 1469: aload 75
      // 146b: ldc_w "head13"
      // 146e: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1471: bipush 0
      // 1472: bipush 19
      // 1474: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1477: ldc_w -5.0
      // 147a: ldc_w -8.75
      // 147d: ldc_w -7.25
      // 1480: ldc_w 10.0
      // 1483: ldc_w 9.0
      // 1486: ldc_w 9.0
      // 1489: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 148c: dup
      // 148d: fconst_0
      // 148e: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1491: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1494: bipush 38
      // 1496: bipush 25
      // 1498: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 149b: ldc_w -3.0
      // 149e: ldc_w 0.25
      // 14a1: ldc_w -7.25
      // 14a4: ldc_w 6.0
      // 14a7: ldc_w 4.0
      // 14aa: ldc_w 4.0
      // 14ad: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 14b0: dup
      // 14b1: fconst_0
      // 14b2: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 14b5: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 14b8: fconst_0
      // 14b9: ldc_w -22.9404
      // 14bc: ldc_w -5.6998
      // 14bf: ldc_w 1.9581
      // 14c2: ldc_w -0.0665
      // 14c5: ldc_w 0.1615
      // 14c8: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 14cb: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 14ce: astore 76
      // 14d0: aload 2
      // 14d1: ldc_w "head21"
      // 14d4: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 14d7: bipush 0
      // 14d8: bipush 19
      // 14da: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 14dd: ldc_w -5.0
      // 14e0: ldc_w -8.75
      // 14e3: ldc_w -7.25
      // 14e6: ldc_w 10.0
      // 14e9: ldc_w 9.0
      // 14ec: ldc_w 9.0
      // 14ef: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 14f2: dup
      // 14f3: fconst_0
      // 14f4: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 14f7: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 14fa: bipush 38
      // 14fc: bipush 25
      // 14fe: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1501: ldc_w -3.0
      // 1504: ldc_w 0.25
      // 1507: ldc_w -7.25
      // 150a: ldc_w 6.0
      // 150d: ldc_w 4.0
      // 1510: ldc_w 4.0
      // 1513: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1516: dup
      // 1517: fconst_0
      // 1518: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 151b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 151e: ldc_w -48.8959
      // 1521: ldc_w -35.2257
      // 1524: ldc_w 4.1138
      // 1527: ldc_w -1.8428
      // 152a: ldc_w -0.3613
      // 152d: ldc_w -2.8714
      // 1530: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1533: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1536: astore 77
      // 1538: aload 2
      // 1539: ldc_w "skeleton23"
      // 153c: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 153f: ldc_w 20.8081
      // 1542: ldc_w -14.8209
      // 1545: ldc_w -40.053
      // 1548: ldc_w 0.4818
      // 154b: ldc_w -0.9412
      // 154e: ldc_w 0.1026
      // 1551: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1554: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1557: astore 78
      // 1559: aload 78
      // 155b: ldc_w "torso23"
      // 155e: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1561: bipush 30
      // 1563: bipush 37
      // 1565: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1568: ldc_w -2.0
      // 156b: ldc_w -21.9833
      // 156e: ldc_w -2.0
      // 1571: ldc_w 4.0
      // 1574: ldc_w 22.0
      // 1577: ldc_w 3.0
      // 157a: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 157d: dup
      // 157e: fconst_0
      // 157f: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1582: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1585: bipush 0
      // 1586: bipush 37
      // 1588: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 158b: ldc_w -5.0
      // 158e: ldc_w -5.9833
      // 1591: ldc_w -4.0
      // 1594: ldc_w 10.0
      // 1597: ldc_w 6.0
      // 159a: ldc_w 5.0
      // 159d: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 15a0: dup
      // 15a1: fconst_0
      // 15a2: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 15a5: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 15a8: bipush 0
      // 15a9: bipush 0
      // 15aa: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 15ad: ldc_w -6.0
      // 15b0: ldc_w -17.9833
      // 15b3: ldc_w -6.0
      // 15b6: ldc_w 12.0
      // 15b9: ldc_w 12.0
      // 15bc: ldc_w 7.0
      // 15bf: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 15c2: dup
      // 15c3: fconst_0
      // 15c4: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 15c7: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 15ca: fconst_0
      // 15cb: ldc_w 7.15
      // 15ce: ldc_w 1.7778
      // 15d1: ldc_w -0.7854
      // 15d4: fconst_0
      // 15d5: fconst_0
      // 15d6: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 15d9: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 15dc: astore 79
      // 15de: aload 79
      // 15e0: ldc_w "left_arm9"
      // 15e3: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 15e6: bipush 38
      // 15e8: bipush 0
      // 15e9: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 15ec: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 15ef: ldc_w -1.5
      // 15f2: ldc_w -3.0
      // 15f5: ldc_w -1.5
      // 15f8: ldc_w 3.0
      // 15fb: ldc_w 22.0
      // 15fe: ldc_w 3.0
      // 1601: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1604: dup
      // 1605: fconst_0
      // 1606: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1609: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 160c: bipush 0
      // 160d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1610: ldc_w -9.4147
      // 1613: ldc_w -16.337
      // 1616: ldc_w -4.0662
      // 1619: ldc_w -1.9923
      // 161c: ldc_w 0.3614
      // 161f: ldc_w -0.1572
      // 1622: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1625: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1628: astore 80
      // 162a: aload 2
      // 162b: ldc_w "skeleton24"
      // 162e: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1631: ldc_w 61.0
      // 1634: ldc_w -5.1667
      // 1637: ldc_w -26.7778
      // 163a: ldc_w -0.8377
      // 163d: ldc_w -0.4522
      // 1640: ldc_w 0.202
      // 1643: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1646: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1649: astore 81
      // 164b: aload 81
      // 164d: ldc_w "torso24"
      // 1650: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1653: bipush 30
      // 1655: bipush 37
      // 1657: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 165a: ldc_w -2.0
      // 165d: ldc_w -21.9833
      // 1660: ldc_w -2.0
      // 1663: ldc_w 4.0
      // 1666: ldc_w 22.0
      // 1669: ldc_w 3.0
      // 166c: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 166f: dup
      // 1670: fconst_0
      // 1671: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1674: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1677: bipush 0
      // 1678: bipush 37
      // 167a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 167d: ldc_w -5.0
      // 1680: ldc_w -5.9833
      // 1683: ldc_w -4.0
      // 1686: ldc_w 10.0
      // 1689: ldc_w 6.0
      // 168c: ldc_w 5.0
      // 168f: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1692: dup
      // 1693: fconst_0
      // 1694: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1697: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 169a: bipush 0
      // 169b: bipush 0
      // 169c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 169f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 16a2: ldc_w -6.0
      // 16a5: ldc_w -17.9833
      // 16a8: ldc_w -6.0
      // 16ab: ldc_w 12.0
      // 16ae: ldc_w 12.0
      // 16b1: ldc_w 7.0
      // 16b4: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 16b7: dup
      // 16b8: fconst_0
      // 16b9: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 16bc: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 16bf: bipush 0
      // 16c0: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 16c3: fconst_0
      // 16c4: ldc_w 7.15
      // 16c7: ldc_w 1.7778
      // 16ca: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 16cd: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 16d0: astore 82
      // 16d2: aload 2
      // 16d3: ldc_w "skeleton25"
      // 16d6: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 16d9: ldc_w 60.0
      // 16dc: ldc_w -7.1667
      // 16df: ldc_w -7.7778
      // 16e2: ldc_w 2.5506
      // 16e5: ldc_w -1.0637
      // 16e8: ldc_w -2.3262
      // 16eb: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 16ee: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 16f1: astore 83
      // 16f3: aload 83
      // 16f5: ldc_w "torso25"
      // 16f8: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 16fb: bipush 30
      // 16fd: bipush 37
      // 16ff: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1702: ldc_w -2.0
      // 1705: ldc_w -21.9833
      // 1708: ldc_w -2.0
      // 170b: ldc_w 4.0
      // 170e: ldc_w 22.0
      // 1711: ldc_w 3.0
      // 1714: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1717: dup
      // 1718: fconst_0
      // 1719: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 171c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 171f: bipush 0
      // 1720: bipush 37
      // 1722: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1725: ldc_w -5.0
      // 1728: ldc_w -5.9833
      // 172b: ldc_w -4.0
      // 172e: ldc_w 10.0
      // 1731: ldc_w 6.0
      // 1734: ldc_w 5.0
      // 1737: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 173a: dup
      // 173b: fconst_0
      // 173c: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 173f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1742: bipush 0
      // 1743: bipush 0
      // 1744: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1747: ldc_w -6.0
      // 174a: ldc_w -17.9833
      // 174d: ldc_w -6.0
      // 1750: ldc_w 12.0
      // 1753: ldc_w 12.0
      // 1756: ldc_w 7.0
      // 1759: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 175c: dup
      // 175d: fconst_0
      // 175e: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1761: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1764: fconst_0
      // 1765: ldc_w 7.15
      // 1768: ldc_w 1.7778
      // 176b: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 176e: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1771: astore 84
      // 1773: aload 84
      // 1775: ldc_w "head14"
      // 1778: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 177b: bipush 0
      // 177c: bipush 19
      // 177e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1781: ldc_w -5.0
      // 1784: ldc_w -8.75
      // 1787: ldc_w -7.25
      // 178a: ldc_w 10.0
      // 178d: ldc_w 9.0
      // 1790: ldc_w 9.0
      // 1793: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1796: dup
      // 1797: fconst_0
      // 1798: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 179b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 179e: bipush 38
      // 17a0: bipush 25
      // 17a2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 17a5: ldc_w -3.0
      // 17a8: ldc_w 0.25
      // 17ab: ldc_w -7.25
      // 17ae: ldc_w 6.0
      // 17b1: ldc_w 4.0
      // 17b4: ldc_w 4.0
      // 17b7: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 17ba: dup
      // 17bb: fconst_0
      // 17bc: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 17bf: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 17c2: fconst_0
      // 17c3: ldc_w -23.0767
      // 17c6: ldc_w -1.2873
      // 17c9: ldc_w -1.0472
      // 17cc: ldc_w -0.2291
      // 17cf: ldc_w 0.3747
      // 17d2: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 17d5: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 17d8: astore 85
      // 17da: aload 84
      // 17dc: ldc_w "right_arm11"
      // 17df: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 17e2: bipush 38
      // 17e4: bipush 0
      // 17e5: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 17e8: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 17eb: ldc_w -1.5
      // 17ee: ldc_w -3.0
      // 17f1: ldc_w -1.5
      // 17f4: ldc_w 3.0
      // 17f7: ldc_w 22.0
      // 17fa: ldc_w 3.0
      // 17fd: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1800: dup
      // 1801: fconst_0
      // 1802: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1805: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1808: bipush 0
      // 1809: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 180c: ldc_w -5.7322
      // 180f: ldc_w -19.4621
      // 1812: ldc_w 0.7047
      // 1815: ldc_w -0.5499
      // 1818: ldc_w 0.3614
      // 181b: ldc_w 1.542
      // 181e: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1821: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1824: astore 86
      // 1826: aload 84
      // 1828: ldc_w "left_arm8"
      // 182b: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 182e: bipush 38
      // 1830: bipush 0
      // 1831: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1834: ldc_w -1.5
      // 1837: ldc_w -3.0
      // 183a: ldc_w -1.5
      // 183d: ldc_w 3.0
      // 1840: ldc_w 22.0
      // 1843: ldc_w 3.0
      // 1846: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1849: dup
      // 184a: fconst_0
      // 184b: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 184e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1851: ldc_w 8.8004
      // 1854: ldc_w -14.4569
      // 1857: ldc_w -7.1356
      // 185a: ldc_w -0.9599
      // 185d: fconst_0
      // 185e: ldc_w -0.3927
      // 1861: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1864: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1867: astore 87
      // 1869: aload 2
      // 186a: ldc_w "skeleton26"
      // 186d: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1870: ldc_w 56.8437
      // 1873: ldc_w -19.0776
      // 1876: ldc_w -24.1818
      // 1879: ldc_w -0.3746
      // 187c: ldc_w -1.0514
      // 187f: ldc_w -0.8355
      // 1882: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1885: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1888: astore 88
      // 188a: aload 88
      // 188c: ldc_w "torso26"
      // 188f: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1892: bipush 30
      // 1894: bipush 37
      // 1896: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1899: ldc_w -2.0
      // 189c: ldc_w -21.9833
      // 189f: ldc_w -2.0
      // 18a2: ldc_w 4.0
      // 18a5: ldc_w 22.0
      // 18a8: ldc_w 3.0
      // 18ab: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 18ae: dup
      // 18af: fconst_0
      // 18b0: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 18b3: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 18b6: bipush 0
      // 18b7: bipush 37
      // 18b9: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 18bc: ldc_w -5.0
      // 18bf: ldc_w -5.9833
      // 18c2: ldc_w -4.0
      // 18c5: ldc_w 10.0
      // 18c8: ldc_w 6.0
      // 18cb: ldc_w 5.0
      // 18ce: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 18d1: dup
      // 18d2: fconst_0
      // 18d3: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 18d6: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 18d9: bipush 0
      // 18da: bipush 0
      // 18db: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 18de: ldc_w -6.0
      // 18e1: ldc_w -17.9833
      // 18e4: ldc_w -6.0
      // 18e7: ldc_w 12.0
      // 18ea: ldc_w 12.0
      // 18ed: ldc_w 7.0
      // 18f0: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 18f3: dup
      // 18f4: fconst_0
      // 18f5: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 18f8: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 18fb: ldc_w 0.5284
      // 18fe: ldc_w 8.0618
      // 1901: ldc_w -0.1944
      // 1904: ldc_w -0.0436
      // 1907: ldc_w -0.2618
      // 190a: fconst_0
      // 190b: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 190e: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1911: astore 89
      // 1913: aload 2
      // 1914: ldc_w "skeleton27"
      // 1917: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 191a: ldc_w -54.0
      // 191d: ldc_w -2.1667
      // 1920: ldc_w -10.7778
      // 1923: ldc_w -1.5708
      // 1926: ldc_w 1.0908
      // 1929: ldc_w 2.7925
      // 192c: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 192f: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1932: astore 90
      // 1934: aload 90
      // 1936: ldc_w "torso27"
      // 1939: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 193c: bipush 30
      // 193e: bipush 37
      // 1940: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1943: ldc_w -2.0
      // 1946: ldc_w -21.9833
      // 1949: ldc_w -2.0
      // 194c: ldc_w 4.0
      // 194f: ldc_w 22.0
      // 1952: ldc_w 3.0
      // 1955: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1958: dup
      // 1959: fconst_0
      // 195a: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 195d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1960: bipush 0
      // 1961: bipush 37
      // 1963: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1966: ldc_w -5.0
      // 1969: ldc_w -5.9833
      // 196c: ldc_w -4.0
      // 196f: ldc_w 10.0
      // 1972: ldc_w 6.0
      // 1975: ldc_w 5.0
      // 1978: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 197b: dup
      // 197c: fconst_0
      // 197d: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1980: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1983: bipush 0
      // 1984: bipush 0
      // 1985: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1988: ldc_w -6.0
      // 198b: ldc_w -17.9833
      // 198e: ldc_w -6.0
      // 1991: ldc_w 12.0
      // 1994: ldc_w 12.0
      // 1997: ldc_w 7.0
      // 199a: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 199d: dup
      // 199e: fconst_0
      // 199f: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 19a2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 19a5: fconst_0
      // 19a6: ldc_w 7.15
      // 19a9: ldc_w 1.7778
      // 19ac: ldc_w -0.3655
      // 19af: ldc_w -0.147
      // 19b2: ldc_w -0.3655
      // 19b5: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 19b8: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 19bb: astore 91
      // 19bd: aload 91
      // 19bf: ldc_w "head15"
      // 19c2: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 19c5: bipush 0
      // 19c6: bipush 19
      // 19c8: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 19cb: ldc_w -5.0
      // 19ce: ldc_w -8.75
      // 19d1: ldc_w -7.25
      // 19d4: ldc_w 10.0
      // 19d7: ldc_w 9.0
      // 19da: ldc_w 9.0
      // 19dd: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 19e0: dup
      // 19e1: fconst_0
      // 19e2: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 19e5: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 19e8: bipush 38
      // 19ea: bipush 25
      // 19ec: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 19ef: ldc_w -3.0
      // 19f2: ldc_w 0.25
      // 19f5: ldc_w -7.25
      // 19f8: ldc_w 6.0
      // 19fb: ldc_w 4.0
      // 19fe: ldc_w 4.0
      // 1a01: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1a04: dup
      // 1a05: fconst_0
      // 1a06: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1a09: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1a0c: fconst_1
      // 1a0d: ldc_w -22.0092
      // 1a10: ldc_w -3.9043
      // 1a13: ldc_w -0.5299
      // 1a16: ldc_w 0.7119
      // 1a19: ldc_w -0.3655
      // 1a1c: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1a1f: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1a22: astore 92
      // 1a24: aload 91
      // 1a26: ldc_w "right_arm12"
      // 1a29: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1a2c: bipush 38
      // 1a2e: bipush 0
      // 1a2f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1a32: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1a35: ldc_w -1.5
      // 1a38: ldc_w -3.0
      // 1a3b: ldc_w -1.5
      // 1a3e: ldc_w 3.0
      // 1a41: ldc_w 22.0
      // 1a44: ldc_w 3.0
      // 1a47: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1a4a: dup
      // 1a4b: fconst_0
      // 1a4c: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1a4f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1a52: bipush 0
      // 1a53: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1a56: ldc_w -8.5051
      // 1a59: ldc_w -18.931
      // 1a5c: ldc_w -1.536
      // 1a5f: ldc_w -0.2849
      // 1a62: ldc_w 0.274
      // 1a65: ldc_w 1.1386
      // 1a68: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1a6b: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1a6e: astore 93
      // 1a70: aload 91
      // 1a72: ldc_w "left_arm10"
      // 1a75: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1a78: bipush 38
      // 1a7a: bipush 0
      // 1a7b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1a7e: ldc_w -1.5
      // 1a81: ldc_w -3.0
      // 1a84: ldc_w -1.5
      // 1a87: ldc_w 3.0
      // 1a8a: ldc_w 22.0
      // 1a8d: ldc_w 3.0
      // 1a90: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1a93: dup
      // 1a94: fconst_0
      // 1a95: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1a98: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1a9b: ldc_w 7.5
      // 1a9e: ldc_w -13.0699
      // 1aa1: ldc_w -5.1194
      // 1aa4: ldc_w 0.1104
      // 1aa7: ldc_w 0.274
      // 1aaa: ldc_w -0.7459
      // 1aad: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1ab0: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1ab3: astore 94
      // 1ab5: aload 2
      // 1ab6: ldc_w "skeleton28"
      // 1ab9: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1abc: ldc_w -46.8747
      // 1abf: ldc_w -16.9502
      // 1ac2: ldc_w 0.8011
      // 1ac5: ldc_w -0.2182
      // 1ac8: ldc_w 1.1781
      // 1acb: fconst_0
      // 1acc: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1acf: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1ad2: astore 95
      // 1ad4: aload 95
      // 1ad6: ldc_w "torso28"
      // 1ad9: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1adc: bipush 30
      // 1ade: bipush 37
      // 1ae0: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1ae3: ldc_w -2.0
      // 1ae6: ldc_w -21.9833
      // 1ae9: ldc_w -2.0
      // 1aec: ldc_w 4.0
      // 1aef: ldc_w 22.0
      // 1af2: ldc_w 3.0
      // 1af5: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1af8: dup
      // 1af9: fconst_0
      // 1afa: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1afd: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1b00: bipush 0
      // 1b01: bipush 37
      // 1b03: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1b06: ldc_w -5.0
      // 1b09: ldc_w -5.9833
      // 1b0c: ldc_w -4.0
      // 1b0f: ldc_w 10.0
      // 1b12: ldc_w 6.0
      // 1b15: ldc_w 5.0
      // 1b18: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1b1b: dup
      // 1b1c: fconst_0
      // 1b1d: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1b20: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1b23: bipush 0
      // 1b24: bipush 0
      // 1b25: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1b28: ldc_w -6.0
      // 1b2b: ldc_w -17.9833
      // 1b2e: ldc_w -6.0
      // 1b31: ldc_w 12.0
      // 1b34: ldc_w 12.0
      // 1b37: ldc_w 7.0
      // 1b3a: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1b3d: dup
      // 1b3e: fconst_0
      // 1b3f: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1b42: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1b45: fconst_0
      // 1b46: ldc_w 7.15
      // 1b49: ldc_w 1.7778
      // 1b4c: ldc_w 0.7974
      // 1b4f: ldc_w 0.1536
      // 1b52: ldc_w 0.1555
      // 1b55: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1b58: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1b5b: astore 96
      // 1b5d: aload 96
      // 1b5f: ldc_w "right_arm13"
      // 1b62: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1b65: bipush 38
      // 1b67: bipush 0
      // 1b68: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1b6b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1b6e: ldc_w -1.5
      // 1b71: ldc_w -3.0
      // 1b74: ldc_w -1.5
      // 1b77: ldc_w 3.0
      // 1b7a: ldc_w 22.0
      // 1b7d: ldc_w 3.0
      // 1b80: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1b83: dup
      // 1b84: fconst_0
      // 1b85: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1b88: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1b8b: bipush 0
      // 1b8c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1b8f: ldc_w -7.7111
      // 1b92: ldc_w -14.2762
      // 1b95: ldc_w -4.1682
      // 1b98: ldc_w -0.6109
      // 1b9b: fconst_0
      // 1b9c: fconst_0
      // 1b9d: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1ba0: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1ba3: astore 97
      // 1ba5: aload 96
      // 1ba7: ldc_w "left_arm11"
      // 1baa: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1bad: bipush 38
      // 1baf: bipush 0
      // 1bb0: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1bb3: ldc_w -1.5
      // 1bb6: ldc_w -3.0
      // 1bb9: ldc_w -1.5
      // 1bbc: ldc_w 3.0
      // 1bbf: ldc_w 22.0
      // 1bc2: ldc_w 3.0
      // 1bc5: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1bc8: dup
      // 1bc9: fconst_0
      // 1bca: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1bcd: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1bd0: ldc_w 7.5
      // 1bd3: ldc_w -14.9833
      // 1bd6: ldc_w -0.5
      // 1bd9: ldc_w -1.1239
      // 1bdc: ldc_w 0.1426
      // 1bdf: ldc_w -2.0693
      // 1be2: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1be5: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1be8: astore 98
      // 1bea: aload 2
      // 1beb: ldc_w "skeleton29"
      // 1bee: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1bf1: ldc_w -42.7329
      // 1bf4: ldc_w -5.0273
      // 1bf7: ldc_w -16.6819
      // 1bfa: ldc_w 1.3963
      // 1bfd: ldc_w 1.0908
      // 1c00: fconst_0
      // 1c01: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1c04: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1c07: astore 99
      // 1c09: aload 99
      // 1c0b: ldc_w "torso29"
      // 1c0e: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1c11: bipush 30
      // 1c13: bipush 37
      // 1c15: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1c18: ldc_w -2.0
      // 1c1b: ldc_w -21.9833
      // 1c1e: ldc_w -2.0
      // 1c21: ldc_w 4.0
      // 1c24: ldc_w 22.0
      // 1c27: ldc_w 3.0
      // 1c2a: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1c2d: dup
      // 1c2e: fconst_0
      // 1c2f: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1c32: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1c35: bipush 0
      // 1c36: bipush 37
      // 1c38: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1c3b: ldc_w -5.0
      // 1c3e: ldc_w -5.9833
      // 1c41: ldc_w -4.0
      // 1c44: ldc_w 10.0
      // 1c47: ldc_w 6.0
      // 1c4a: ldc_w 5.0
      // 1c4d: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1c50: dup
      // 1c51: fconst_0
      // 1c52: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1c55: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1c58: bipush 0
      // 1c59: bipush 0
      // 1c5a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1c5d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1c60: ldc_w -6.0
      // 1c63: ldc_w -17.9833
      // 1c66: ldc_w -6.0
      // 1c69: ldc_w 12.0
      // 1c6c: ldc_w 12.0
      // 1c6f: ldc_w 7.0
      // 1c72: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1c75: dup
      // 1c76: fconst_0
      // 1c77: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1c7a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1c7d: bipush 0
      // 1c7e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1c81: fconst_0
      // 1c82: ldc_w 7.15
      // 1c85: ldc_w 1.7778
      // 1c88: ldc_w -0.7854
      // 1c8b: fconst_0
      // 1c8c: fconst_0
      // 1c8d: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1c90: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1c93: astore 100
      // 1c95: aload 100
      // 1c97: ldc_w "head16"
      // 1c9a: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1c9d: bipush 0
      // 1c9e: bipush 19
      // 1ca0: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1ca3: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1ca6: ldc_w -5.0
      // 1ca9: ldc_w -8.75
      // 1cac: ldc_w -7.25
      // 1caf: ldc_w 10.0
      // 1cb2: ldc_w 9.0
      // 1cb5: ldc_w 9.0
      // 1cb8: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1cbb: dup
      // 1cbc: fconst_0
      // 1cbd: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1cc0: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1cc3: bipush 0
      // 1cc4: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1cc7: bipush 38
      // 1cc9: bipush 25
      // 1ccb: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1cce: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1cd1: ldc_w -3.0
      // 1cd4: ldc_w 0.25
      // 1cd7: ldc_w -7.25
      // 1cda: ldc_w 6.0
      // 1cdd: ldc_w 4.0
      // 1ce0: ldc_w 4.0
      // 1ce3: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1ce6: dup
      // 1ce7: fconst_0
      // 1ce8: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1ceb: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1cee: bipush 0
      // 1cef: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1cf2: ldc_w 0.5
      // 1cf5: ldc_w -21.7625
      // 1cf8: ldc_w -1.9862
      // 1cfb: ldc_w 0.4622
      // 1cfe: ldc_w 0.4409
      // 1d01: ldc_w 0.0488
      // 1d04: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1d07: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1d0a: astore 101
      // 1d0c: aload 100
      // 1d0e: ldc_w "right_arm14"
      // 1d11: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1d14: bipush 38
      // 1d16: bipush 0
      // 1d17: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1d1a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1d1d: ldc_w -1.5
      // 1d20: ldc_w -3.0
      // 1d23: ldc_w -1.5
      // 1d26: ldc_w 3.0
      // 1d29: ldc_w 22.0
      // 1d2c: ldc_w 3.0
      // 1d2f: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1d32: dup
      // 1d33: fconst_0
      // 1d34: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1d37: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1d3a: bipush 0
      // 1d3b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1d3e: ldc_w 8.6038
      // 1d41: ldc_w -18.1116
      // 1d44: ldc_w -5.1875
      // 1d47: ldc_w -2.369
      // 1d4a: ldc_w -0.5195
      // 1d4d: ldc_w 0.3237
      // 1d50: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1d53: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1d56: astore 102
      // 1d58: aload 2
      // 1d59: ldc_w "skeleton30"
      // 1d5c: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1d5f: ldc_w 59.8267
      // 1d62: ldc_w 0.931
      // 1d65: ldc_w 10.1912
      // 1d68: ldc_w -1.4472
      // 1d6b: ldc_w -0.8626
      // 1d6e: ldc_w 2.8924
      // 1d71: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1d74: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1d77: astore 103
      // 1d79: aload 103
      // 1d7b: ldc_w "torso30"
      // 1d7e: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1d81: bipush 30
      // 1d83: bipush 37
      // 1d85: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1d88: ldc_w -2.0
      // 1d8b: ldc_w -21.9833
      // 1d8e: ldc_w -2.0
      // 1d91: ldc_w 4.0
      // 1d94: ldc_w 22.0
      // 1d97: ldc_w 3.0
      // 1d9a: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1d9d: dup
      // 1d9e: fconst_0
      // 1d9f: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1da2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1da5: bipush 0
      // 1da6: bipush 37
      // 1da8: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1dab: ldc_w -5.0
      // 1dae: ldc_w -5.9833
      // 1db1: ldc_w -4.0
      // 1db4: ldc_w 10.0
      // 1db7: ldc_w 6.0
      // 1dba: ldc_w 5.0
      // 1dbd: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1dc0: dup
      // 1dc1: fconst_0
      // 1dc2: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1dc5: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1dc8: bipush 0
      // 1dc9: bipush 0
      // 1dca: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1dcd: ldc_w -6.0
      // 1dd0: ldc_w -17.9833
      // 1dd3: ldc_w -6.0
      // 1dd6: ldc_w 12.0
      // 1dd9: ldc_w 12.0
      // 1ddc: ldc_w 7.0
      // 1ddf: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1de2: dup
      // 1de3: fconst_0
      // 1de4: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1de7: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1dea: fconst_0
      // 1deb: ldc_w 7.15
      // 1dee: ldc_w 1.7778
      // 1df1: ldc_w -0.7854
      // 1df4: fconst_0
      // 1df5: fconst_0
      // 1df6: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1df9: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1dfc: astore 104
      // 1dfe: aload 104
      // 1e00: ldc_w "head17"
      // 1e03: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1e06: bipush 0
      // 1e07: bipush 19
      // 1e09: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1e0c: ldc_w -5.0
      // 1e0f: ldc_w -8.75
      // 1e12: ldc_w -7.25
      // 1e15: ldc_w 10.0
      // 1e18: ldc_w 9.0
      // 1e1b: ldc_w 9.0
      // 1e1e: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1e21: dup
      // 1e22: fconst_0
      // 1e23: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1e26: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1e29: bipush 38
      // 1e2b: bipush 25
      // 1e2d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1e30: ldc_w -3.0
      // 1e33: ldc_w 0.25
      // 1e36: ldc_w -7.25
      // 1e39: ldc_w 6.0
      // 1e3c: ldc_w 4.0
      // 1e3f: ldc_w 4.0
      // 1e42: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1e45: dup
      // 1e46: fconst_0
      // 1e47: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1e4a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1e4d: ldc_w -0.7654
      // 1e50: ldc_w -22.341
      // 1e53: ldc_w -3.4708
      // 1e56: ldc_w -0.7459
      // 1e59: ldc_w 0.274
      // 1e5c: ldc_w 0.2849
      // 1e5f: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1e62: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1e65: astore 105
      // 1e67: aload 104
      // 1e69: ldc_w "right_arm15"
      // 1e6c: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1e6f: bipush 38
      // 1e71: bipush 0
      // 1e72: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1e75: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1e78: ldc_w -1.5
      // 1e7b: ldc_w -3.0
      // 1e7e: ldc_w -1.5
      // 1e81: ldc_w 3.0
      // 1e84: ldc_w 22.0
      // 1e87: ldc_w 3.0
      // 1e8a: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1e8d: dup
      // 1e8e: fconst_0
      // 1e8f: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1e92: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1e95: bipush 0
      // 1e96: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1e99: ldc_w -8.9807
      // 1e9c: ldc_w -19.1064
      // 1e9f: ldc_w -3.0658
      // 1ea2: ldc_w -1.9923
      // 1ea5: ldc_w 0.3614
      // 1ea8: ldc_w -0.1572
      // 1eab: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1eae: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1eb1: astore 106
      // 1eb3: aload 104
      // 1eb5: ldc_w "left_arm12"
      // 1eb8: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1ebb: bipush 38
      // 1ebd: bipush 0
      // 1ebe: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1ec1: ldc_w -1.5
      // 1ec4: ldc_w -3.0
      // 1ec7: ldc_w -1.5
      // 1eca: ldc_w 3.0
      // 1ecd: ldc_w 22.0
      // 1ed0: ldc_w 3.0
      // 1ed3: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1ed6: dup
      // 1ed7: fconst_0
      // 1ed8: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1edb: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1ede: ldc_w 6.7168
      // 1ee1: ldc_w -19.4873
      // 1ee4: ldc_w -0.183
      // 1ee7: ldc_w -1.1781
      // 1eea: fconst_0
      // 1eeb: ldc_w -0.3927
      // 1eee: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1ef1: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1ef4: astore 107
      // 1ef6: aload 2
      // 1ef7: ldc_w "skeleton31"
      // 1efa: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1efd: ldc_w 27.2067
      // 1f00: ldc_w -32.209
      // 1f03: ldc_w -34.8172
      // 1f06: ldc_w -0.8082
      // 1f09: ldc_w 0.369
      // 1f0c: ldc_w 0.0585
      // 1f0f: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1f12: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1f15: astore 108
      // 1f17: aload 108
      // 1f19: ldc_w "torso31"
      // 1f1c: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1f1f: bipush 30
      // 1f21: bipush 37
      // 1f23: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1f26: ldc_w -2.0
      // 1f29: ldc_w -21.9833
      // 1f2c: ldc_w -2.0
      // 1f2f: ldc_w 4.0
      // 1f32: ldc_w 22.0
      // 1f35: ldc_w 3.0
      // 1f38: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1f3b: dup
      // 1f3c: fconst_0
      // 1f3d: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1f40: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1f43: bipush 0
      // 1f44: bipush 37
      // 1f46: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1f49: ldc_w -5.0
      // 1f4c: ldc_w -5.9833
      // 1f4f: ldc_w -4.0
      // 1f52: ldc_w 10.0
      // 1f55: ldc_w 6.0
      // 1f58: ldc_w 5.0
      // 1f5b: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1f5e: dup
      // 1f5f: fconst_0
      // 1f60: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1f63: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1f66: bipush 0
      // 1f67: bipush 0
      // 1f68: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1f6b: ldc_w -6.0
      // 1f6e: ldc_w -17.9833
      // 1f71: ldc_w -6.0
      // 1f74: ldc_w 12.0
      // 1f77: ldc_w 12.0
      // 1f7a: ldc_w 7.0
      // 1f7d: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1f80: dup
      // 1f81: fconst_0
      // 1f82: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1f85: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1f88: fconst_0
      // 1f89: ldc_w 7.15
      // 1f8c: ldc_w 1.7778
      // 1f8f: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1f92: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1f95: astore 109
      // 1f97: aload 109
      // 1f99: ldc_w "head18"
      // 1f9c: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1f9f: bipush 0
      // 1fa0: bipush 19
      // 1fa2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1fa5: ldc_w -5.0
      // 1fa8: ldc_w -8.75
      // 1fab: ldc_w -7.25
      // 1fae: ldc_w 10.0
      // 1fb1: ldc_w 9.0
      // 1fb4: ldc_w 9.0
      // 1fb7: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1fba: dup
      // 1fbb: fconst_0
      // 1fbc: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1fbf: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1fc2: bipush 38
      // 1fc4: bipush 25
      // 1fc6: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1fc9: ldc_w -3.0
      // 1fcc: ldc_w 0.25
      // 1fcf: ldc_w -7.25
      // 1fd2: ldc_w 6.0
      // 1fd5: ldc_w 4.0
      // 1fd8: ldc_w 4.0
      // 1fdb: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1fde: dup
      // 1fdf: fconst_0
      // 1fe0: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1fe3: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1fe6: ldc_w 2.4327
      // 1fe9: ldc_w -21.9411
      // 1fec: ldc_w -10.1362
      // 1fef: ldc_w 1.9567
      // 1ff2: ldc_w -0.35
      // 1ff5: ldc_w 0.283
      // 1ff8: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1ffb: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1ffe: astore 110
      // 2000: aload 109
      // 2002: ldc_w "left_arm13"
      // 2005: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2008: bipush 38
      // 200a: bipush 0
      // 200b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 200e: ldc_w -1.5
      // 2011: ldc_w -3.0
      // 2014: ldc_w -1.5
      // 2017: ldc_w 3.0
      // 201a: ldc_w 22.0
      // 201d: ldc_w 3.0
      // 2020: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2023: dup
      // 2024: fconst_0
      // 2025: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2028: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 202b: ldc_w 7.8265
      // 202e: ldc_w -14.3561
      // 2031: ldc_w 0.2071
      // 2034: ldc_w 0.8926
      // 2037: ldc_w 0.2143
      // 203a: ldc_w -0.6993
      // 203d: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 2040: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2043: astore 111
      // 2045: aload 2
      // 2046: ldc_w "skeleton32"
      // 2049: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 204c: ldc_w 43.7613
      // 204f: ldc_w -35.2873
      // 2052: ldc_w -24.3044
      // 2055: ldc_w 1.035
      // 2058: ldc_w 0.7202
      // 205b: ldc_w 2.996
      // 205e: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 2061: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2064: astore 112
      // 2066: aload 112
      // 2068: ldc_w "torso32"
      // 206b: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 206e: bipush 30
      // 2070: bipush 37
      // 2072: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2075: ldc_w -2.0
      // 2078: ldc_w -21.9833
      // 207b: ldc_w -2.0
      // 207e: ldc_w 4.0
      // 2081: ldc_w 22.0
      // 2084: ldc_w 3.0
      // 2087: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 208a: dup
      // 208b: fconst_0
      // 208c: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 208f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2092: bipush 0
      // 2093: bipush 37
      // 2095: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2098: ldc_w -5.0
      // 209b: ldc_w -5.9833
      // 209e: ldc_w -4.0
      // 20a1: ldc_w 10.0
      // 20a4: ldc_w 6.0
      // 20a7: ldc_w 5.0
      // 20aa: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 20ad: dup
      // 20ae: fconst_0
      // 20af: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 20b2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 20b5: bipush 0
      // 20b6: bipush 0
      // 20b7: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 20ba: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 20bd: ldc_w -6.0
      // 20c0: ldc_w -17.9833
      // 20c3: ldc_w -6.0
      // 20c6: ldc_w 12.0
      // 20c9: ldc_w 12.0
      // 20cc: ldc_w 7.0
      // 20cf: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 20d2: dup
      // 20d3: fconst_0
      // 20d4: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 20d7: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 20da: bipush 0
      // 20db: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 20de: fconst_0
      // 20df: ldc_w 7.15
      // 20e2: ldc_w 1.7778
      // 20e5: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 20e8: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 20eb: astore 113
      // 20ed: aload 112
      // 20ef: ldc_w "left_arm14"
      // 20f2: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 20f5: bipush 38
      // 20f7: bipush 0
      // 20f8: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 20fb: ldc_w -1.5
      // 20fe: ldc_w -3.0
      // 2101: ldc_w -1.5
      // 2104: ldc_w 3.0
      // 2107: ldc_w 22.0
      // 210a: ldc_w 3.0
      // 210d: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2110: dup
      // 2111: fconst_0
      // 2112: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2115: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2118: ldc_w -7.6146
      // 211b: ldc_w -9.2985
      // 211e: ldc_w -0.1777
      // 2121: ldc_w 2.7489
      // 2124: fconst_0
      // 2125: ldc_w 0.0873
      // 2128: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 212b: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 212e: astore 114
      // 2130: aload 2
      // 2131: ldc_w "skeleton33"
      // 2134: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2137: ldc_w 7.0
      // 213a: ldc_w -28.1667
      // 213d: ldc_w -34.7778
      // 2140: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 2143: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2146: astore 115
      // 2148: aload 115
      // 214a: ldc_w "torso33"
      // 214d: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2150: fconst_0
      // 2151: ldc_w 7.15
      // 2154: ldc_w 1.7778
      // 2157: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 215a: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 215d: astore 116
      // 215f: aload 116
      // 2161: ldc_w "head19"
      // 2164: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2167: bipush 0
      // 2168: bipush 19
      // 216a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 216d: ldc_w -5.0
      // 2170: ldc_w -8.75
      // 2173: ldc_w -7.25
      // 2176: ldc_w 10.0
      // 2179: ldc_w 9.0
      // 217c: ldc_w 9.0
      // 217f: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2182: dup
      // 2183: fconst_0
      // 2184: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2187: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 218a: bipush 38
      // 218c: bipush 25
      // 218e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2191: ldc_w -3.0
      // 2194: ldc_w 0.25
      // 2197: ldc_w -7.25
      // 219a: ldc_w 6.0
      // 219d: ldc_w 4.0
      // 21a0: ldc_w 4.0
      // 21a3: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 21a6: dup
      // 21a7: fconst_0
      // 21a8: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 21ab: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 21ae: ldc_w 1.9115
      // 21b1: ldc_w -20.5602
      // 21b4: ldc_w -4.215
      // 21b7: ldc_w -0.0493
      // 21ba: ldc_w 0.3381
      // 21bd: ldc_w -0.2087
      // 21c0: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 21c3: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 21c6: astore 117
      // 21c8: aload 2
      // 21c9: ldc_w "skeleton34"
      // 21cc: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 21cf: ldc_w 1.7974
      // 21d2: ldc_w -22.9334
      // 21d5: ldc_w -38.9145
      // 21d8: ldc_w -0.4013
      // 21db: ldc_w -0.4522
      // 21de: ldc_w 0.202
      // 21e1: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 21e4: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 21e7: astore 118
      // 21e9: aload 118
      // 21eb: ldc_w "torso34"
      // 21ee: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 21f1: bipush 30
      // 21f3: bipush 37
      // 21f5: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 21f8: ldc_w -2.0
      // 21fb: ldc_w -21.9833
      // 21fe: ldc_w -2.0
      // 2201: ldc_w 4.0
      // 2204: ldc_w 22.0
      // 2207: ldc_w 3.0
      // 220a: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 220d: dup
      // 220e: fconst_0
      // 220f: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2212: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2215: bipush 0
      // 2216: bipush 37
      // 2218: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 221b: ldc_w -5.0
      // 221e: ldc_w -5.9833
      // 2221: ldc_w -4.0
      // 2224: ldc_w 10.0
      // 2227: ldc_w 6.0
      // 222a: ldc_w 5.0
      // 222d: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2230: dup
      // 2231: fconst_0
      // 2232: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2235: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2238: bipush 0
      // 2239: bipush 0
      // 223a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 223d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2240: ldc_w -6.0
      // 2243: ldc_w -17.9833
      // 2246: ldc_w -6.0
      // 2249: ldc_w 12.0
      // 224c: ldc_w 12.0
      // 224f: ldc_w 7.0
      // 2252: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2255: dup
      // 2256: fconst_0
      // 2257: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 225a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 225d: bipush 0
      // 225e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2261: fconst_0
      // 2262: ldc_w 7.15
      // 2265: ldc_w 1.7778
      // 2268: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 226b: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 226e: astore 119
      // 2270: aload 2
      // 2271: ldc_w "skeleton35"
      // 2274: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2277: ldc_w -46.0
      // 227a: ldc_w -27.1667
      // 227d: ldc_w -7.7778
      // 2280: ldc_w 1.8115
      // 2283: ldc_w 0.6653
      // 2286: ldc_w -0.0174
      // 2289: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 228c: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 228f: astore 120
      // 2291: aload 120
      // 2293: ldc_w "torso35"
      // 2296: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2299: bipush 30
      // 229b: bipush 37
      // 229d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 22a0: ldc_w -2.0
      // 22a3: ldc_w -21.9833
      // 22a6: ldc_w -2.0
      // 22a9: ldc_w 4.0
      // 22ac: ldc_w 22.0
      // 22af: ldc_w 3.0
      // 22b2: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 22b5: dup
      // 22b6: fconst_0
      // 22b7: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 22ba: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 22bd: bipush 0
      // 22be: bipush 37
      // 22c0: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 22c3: ldc_w -5.0
      // 22c6: ldc_w -5.9833
      // 22c9: ldc_w -4.0
      // 22cc: ldc_w 10.0
      // 22cf: ldc_w 6.0
      // 22d2: ldc_w 5.0
      // 22d5: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 22d8: dup
      // 22d9: fconst_0
      // 22da: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 22dd: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 22e0: bipush 0
      // 22e1: bipush 0
      // 22e2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 22e5: ldc_w -6.0
      // 22e8: ldc_w -17.9833
      // 22eb: ldc_w -6.0
      // 22ee: ldc_w 12.0
      // 22f1: ldc_w 12.0
      // 22f4: ldc_w 7.0
      // 22f7: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 22fa: dup
      // 22fb: fconst_0
      // 22fc: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 22ff: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2302: fconst_0
      // 2303: ldc_w 7.15
      // 2306: ldc_w 1.7778
      // 2309: ldc_w -0.3655
      // 230c: ldc_w -0.147
      // 230f: ldc_w -0.3655
      // 2312: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 2315: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2318: astore 121
      // 231a: aload 121
      // 231c: ldc_w "right_arm16"
      // 231f: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2322: bipush 38
      // 2324: bipush 0
      // 2325: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2328: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 232b: ldc_w -1.5
      // 232e: ldc_w -3.0
      // 2331: ldc_w -1.5
      // 2334: ldc_w 3.0
      // 2337: ldc_w 22.0
      // 233a: ldc_w 3.0
      // 233d: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2340: dup
      // 2341: fconst_0
      // 2342: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2345: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2348: bipush 0
      // 2349: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 234c: ldc_w -8.5051
      // 234f: ldc_w -18.931
      // 2352: ldc_w -1.536
      // 2355: ldc_w -0.1388
      // 2358: ldc_w 0.13
      // 235b: ldc_w 0.3737
      // 235e: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 2361: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2364: astore 122
      // 2366: aload 121
      // 2368: ldc_w "left_arm15"
      // 236b: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 236e: bipush 38
      // 2370: bipush 0
      // 2371: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2374: ldc_w -1.5
      // 2377: ldc_w -3.0
      // 237a: ldc_w -1.5
      // 237d: ldc_w 3.0
      // 2380: ldc_w 22.0
      // 2383: ldc_w 3.0
      // 2386: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2389: dup
      // 238a: fconst_0
      // 238b: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 238e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2391: ldc_w 9.3188
      // 2394: ldc_w -16.6253
      // 2397: ldc_w -2.6595
      // 239a: ldc_w -0.5005
      // 239d: ldc_w 0.274
      // 23a0: ldc_w -0.7459
      // 23a3: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 23a6: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 23a9: astore 123
      // 23ab: aload 2
      // 23ac: ldc_w "skeleton36"
      // 23af: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 23b2: ldc_w -31.0
      // 23b5: ldc_w -26.1667
      // 23b8: ldc_w -10.7778
      // 23bb: ldc_w 1.6931
      // 23be: ldc_w 0.2162
      // 23c1: ldc_w 0.012
      // 23c4: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 23c7: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 23ca: astore 124
      // 23cc: aload 124
      // 23ce: ldc_w "torso36"
      // 23d1: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 23d4: bipush 30
      // 23d6: bipush 37
      // 23d8: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 23db: ldc_w -2.0
      // 23de: ldc_w -21.9833
      // 23e1: ldc_w -2.0
      // 23e4: ldc_w 4.0
      // 23e7: ldc_w 22.0
      // 23ea: ldc_w 3.0
      // 23ed: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 23f0: dup
      // 23f1: fconst_0
      // 23f2: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 23f5: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 23f8: bipush 0
      // 23f9: bipush 37
      // 23fb: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 23fe: ldc_w -5.0
      // 2401: ldc_w -5.9833
      // 2404: ldc_w -4.0
      // 2407: ldc_w 10.0
      // 240a: ldc_w 6.0
      // 240d: ldc_w 5.0
      // 2410: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2413: dup
      // 2414: fconst_0
      // 2415: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2418: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 241b: bipush 0
      // 241c: bipush 0
      // 241d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2420: ldc_w -6.0
      // 2423: ldc_w -17.9833
      // 2426: ldc_w -6.0
      // 2429: ldc_w 12.0
      // 242c: ldc_w 12.0
      // 242f: ldc_w 7.0
      // 2432: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2435: dup
      // 2436: fconst_0
      // 2437: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 243a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 243d: fconst_0
      // 243e: ldc_w 7.15
      // 2441: ldc_w 1.7778
      // 2444: ldc_w -0.3655
      // 2447: ldc_w -0.147
      // 244a: ldc_w -0.3655
      // 244d: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 2450: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2453: astore 125
      // 2455: aload 125
      // 2457: ldc_w "head20"
      // 245a: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 245d: bipush 0
      // 245e: bipush 19
      // 2460: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2463: ldc_w -5.0
      // 2466: ldc_w -8.75
      // 2469: ldc_w -7.25
      // 246c: ldc_w 10.0
      // 246f: ldc_w 9.0
      // 2472: ldc_w 9.0
      // 2475: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2478: dup
      // 2479: fconst_0
      // 247a: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 247d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2480: bipush 38
      // 2482: bipush 25
      // 2484: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2487: ldc_w -3.0
      // 248a: ldc_w 0.25
      // 248d: ldc_w -7.25
      // 2490: ldc_w 6.0
      // 2493: ldc_w 4.0
      // 2496: ldc_w 4.0
      // 2499: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 249c: dup
      // 249d: fconst_0
      // 249e: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 24a1: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 24a4: fconst_1
      // 24a5: ldc_w -22.0092
      // 24a8: ldc_w -3.9043
      // 24ab: ldc_w -1.3856
      // 24ae: ldc_w 0.411
      // 24b1: ldc_w 0.0408
      // 24b4: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 24b7: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 24ba: astore 126
      // 24bc: aload 2
      // 24bd: ldc_w "skeleton37"
      // 24c0: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 24c3: ldc_w 62.4571
      // 24c6: ldc_w -21.4461
      // 24c9: ldc_w 4.7971
      // 24cc: ldc_w 2.2337
      // 24cf: ldc_w -0.7875
      // 24d2: ldc_w 3.0771
      // 24d5: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 24d8: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 24db: astore 127
      // 24dd: aload 127
      // 24df: ldc_w "torso37"
      // 24e2: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 24e5: bipush 30
      // 24e7: bipush 37
      // 24e9: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 24ec: ldc_w -2.0
      // 24ef: ldc_w -21.9833
      // 24f2: ldc_w -2.0
      // 24f5: ldc_w 4.0
      // 24f8: ldc_w 22.0
      // 24fb: ldc_w 3.0
      // 24fe: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2501: dup
      // 2502: fconst_0
      // 2503: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2506: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2509: bipush 0
      // 250a: bipush 37
      // 250c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 250f: ldc_w -5.0
      // 2512: ldc_w -5.9833
      // 2515: ldc_w -4.0
      // 2518: ldc_w 10.0
      // 251b: ldc_w 6.0
      // 251e: ldc_w 5.0
      // 2521: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2524: dup
      // 2525: fconst_0
      // 2526: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2529: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 252c: bipush 0
      // 252d: bipush 0
      // 252e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2531: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2534: ldc_w -6.0
      // 2537: ldc_w -17.9833
      // 253a: ldc_w -6.0
      // 253d: ldc_w 12.0
      // 2540: ldc_w 12.0
      // 2543: ldc_w 7.0
      // 2546: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2549: dup
      // 254a: fconst_0
      // 254b: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 254e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2551: bipush 0
      // 2552: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2555: fconst_0
      // 2556: ldc_w 7.15
      // 2559: ldc_w 1.7778
      // 255c: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 255f: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2562: astore 128
      // 2564: aload 2
      // 2565: ldc_w "skeleton38"
      // 2568: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 256b: ldc_w 58.2258
      // 256e: ldc_w -16.7618
      // 2571: ldc_w 15.6909
      // 2574: ldc_w 1.3963
      // 2577: ldc_w -1.4835
      // 257a: ldc_w -0.0873
      // 257d: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 2580: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2583: astore 129
      // 2585: aload 129
      // 2587: ldc_w "torso38"
      // 258a: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 258d: bipush 30
      // 258f: bipush 37
      // 2591: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2594: ldc_w -2.0
      // 2597: ldc_w -21.9833
      // 259a: ldc_w -2.0
      // 259d: ldc_w 4.0
      // 25a0: ldc_w 22.0
      // 25a3: ldc_w 3.0
      // 25a6: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 25a9: dup
      // 25aa: fconst_0
      // 25ab: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 25ae: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 25b1: bipush 0
      // 25b2: bipush 37
      // 25b4: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 25b7: ldc_w -5.0
      // 25ba: ldc_w -5.9833
      // 25bd: ldc_w -4.0
      // 25c0: ldc_w 10.0
      // 25c3: ldc_w 6.0
      // 25c6: ldc_w 5.0
      // 25c9: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 25cc: dup
      // 25cd: fconst_0
      // 25ce: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 25d1: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 25d4: bipush 0
      // 25d5: bipush 0
      // 25d6: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 25d9: ldc_w -6.0
      // 25dc: ldc_w -17.9833
      // 25df: ldc_w -6.0
      // 25e2: ldc_w 12.0
      // 25e5: ldc_w 12.0
      // 25e8: ldc_w 7.0
      // 25eb: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 25ee: dup
      // 25ef: fconst_0
      // 25f0: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 25f3: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 25f6: fconst_0
      // 25f7: ldc_w 7.8571
      // 25fa: ldc_w 2.4849
      // 25fd: ldc_w -0.7854
      // 2600: fconst_0
      // 2601: fconst_0
      // 2602: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 2605: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2608: astore 130
      // 260a: aload 130
      // 260c: ldc_w "head22"
      // 260f: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2612: bipush 0
      // 2613: bipush 19
      // 2615: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2618: ldc_w -5.0
      // 261b: ldc_w -8.75
      // 261e: ldc_w -7.25
      // 2621: ldc_w 10.0
      // 2624: ldc_w 9.0
      // 2627: ldc_w 9.0
      // 262a: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 262d: dup
      // 262e: fconst_0
      // 262f: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2632: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2635: bipush 38
      // 2637: bipush 25
      // 2639: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 263c: ldc_w -3.0
      // 263f: ldc_w 0.25
      // 2642: ldc_w -7.25
      // 2645: ldc_w 6.0
      // 2648: ldc_w 4.0
      // 264b: ldc_w 4.0
      // 264e: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2651: dup
      // 2652: fconst_0
      // 2653: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2656: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2659: ldc_w -2.9422
      // 265c: ldc_w -22.1709
      // 265f: ldc_w -4.6071
      // 2662: ldc_w 1.0533
      // 2665: ldc_w -0.3079
      // 2668: ldc_w -0.1982
      // 266b: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 266e: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2671: astore 131
      // 2673: aload 130
      // 2675: ldc_w "right_arm17"
      // 2678: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 267b: bipush 38
      // 267d: bipush 0
      // 267e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2681: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2684: ldc_w -1.5
      // 2687: ldc_w -3.0
      // 268a: ldc_w -1.5
      // 268d: ldc_w 3.0
      // 2690: ldc_w 22.0
      // 2693: ldc_w 3.0
      // 2696: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2699: dup
      // 269a: fconst_0
      // 269b: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 269e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 26a1: bipush 0
      // 26a2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 26a5: ldc_w -8.6038
      // 26a8: ldc_w -18.1116
      // 26ab: ldc_w -5.1874
      // 26ae: ldc_w -2.369
      // 26b1: ldc_w 0.5195
      // 26b4: ldc_w -0.3237
      // 26b7: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 26ba: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 26bd: astore 132
      // 26bf: aload 2
      // 26c0: ldc_w "skeleton39"
      // 26c3: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 26c6: ldc_w 54.5186
      // 26c9: ldc_w -36.362
      // 26cc: ldc_w -10.2057
      // 26cf: ldc_w -1.3936
      // 26d2: ldc_w -0.1719
      // 26d5: ldc_w -0.0306
      // 26d8: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 26db: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 26de: astore 133
      // 26e0: aload 133
      // 26e2: ldc_w "torso39"
      // 26e5: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 26e8: bipush 30
      // 26ea: bipush 37
      // 26ec: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 26ef: ldc_w -2.0
      // 26f2: ldc_w -21.9833
      // 26f5: ldc_w -2.0
      // 26f8: ldc_w 4.0
      // 26fb: ldc_w 22.0
      // 26fe: ldc_w 3.0
      // 2701: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2704: dup
      // 2705: fconst_0
      // 2706: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2709: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 270c: bipush 0
      // 270d: bipush 37
      // 270f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2712: ldc_w -5.0
      // 2715: ldc_w -5.9833
      // 2718: ldc_w -4.0
      // 271b: ldc_w 10.0
      // 271e: ldc_w 6.0
      // 2721: ldc_w 5.0
      // 2724: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2727: dup
      // 2728: fconst_0
      // 2729: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 272c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 272f: bipush 0
      // 2730: bipush 0
      // 2731: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2734: ldc_w -6.0
      // 2737: ldc_w -17.9833
      // 273a: ldc_w -6.0
      // 273d: ldc_w 12.0
      // 2740: ldc_w 12.0
      // 2743: ldc_w 7.0
      // 2746: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2749: dup
      // 274a: fconst_0
      // 274b: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 274e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2751: fconst_0
      // 2752: ldc_w 7.15
      // 2755: ldc_w 1.7778
      // 2758: ldc_w 0.0456
      // 275b: ldc_w -0.2974
      // 275e: ldc_w 0.0466
      // 2761: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 2764: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2767: astore 134
      // 2769: aload 134
      // 276b: ldc_w "head23"
      // 276e: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2771: bipush 0
      // 2772: bipush 19
      // 2774: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2777: ldc_w -5.0
      // 277a: ldc_w -8.75
      // 277d: ldc_w -7.25
      // 2780: ldc_w 10.0
      // 2783: ldc_w 9.0
      // 2786: ldc_w 9.0
      // 2789: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 278c: dup
      // 278d: fconst_0
      // 278e: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2791: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2794: bipush 38
      // 2796: bipush 25
      // 2798: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 279b: ldc_w -3.0
      // 279e: ldc_w 0.25
      // 27a1: ldc_w -7.25
      // 27a4: ldc_w 6.0
      // 27a7: ldc_w 4.0
      // 27aa: ldc_w 4.0
      // 27ad: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 27b0: dup
      // 27b1: fconst_0
      // 27b2: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 27b5: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 27b8: ldc_w 2.801
      // 27bb: ldc_w -22.9562
      // 27be: ldc_w -2.955
      // 27c1: ldc_w 2.691
      // 27c4: ldc_w -0.1273
      // 27c7: ldc_w -2.2171
      // 27ca: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 27cd: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 27d0: astore 135
      // 27d2: aload 134
      // 27d4: ldc_w "right_arm18"
      // 27d7: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 27da: bipush 38
      // 27dc: bipush 0
      // 27dd: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 27e0: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 27e3: ldc_w -1.5
      // 27e6: ldc_w -3.0
      // 27e9: ldc_w -1.5
      // 27ec: ldc_w 3.0
      // 27ef: ldc_w 22.0
      // 27f2: ldc_w 3.0
      // 27f5: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 27f8: dup
      // 27f9: fconst_0
      // 27fa: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 27fd: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2800: bipush 0
      // 2801: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2804: ldc_w -7.5
      // 2807: ldc_w -14.9833
      // 280a: ldc_w -0.5
      // 280d: ldc_w 0.3677
      // 2810: ldc_w 0.2308
      // 2813: ldc_w 0.7197
      // 2816: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 2819: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 281c: astore 136
      // 281e: aload 134
      // 2820: ldc_w "left_arm16"
      // 2823: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2826: bipush 38
      // 2828: bipush 0
      // 2829: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 282c: ldc_w -1.5
      // 282f: ldc_w -3.0
      // 2832: ldc_w -1.5
      // 2835: ldc_w 3.0
      // 2838: ldc_w 22.0
      // 283b: ldc_w 3.0
      // 283e: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2841: dup
      // 2842: fconst_0
      // 2843: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2846: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2849: ldc_w 7.6584
      // 284c: ldc_w -15.3925
      // 284f: ldc_w -2.4513
      // 2852: ldc_w 0.1663
      // 2855: ldc_w -0.2071
      // 2858: ldc_w -0.7879
      // 285b: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 285e: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2861: astore 137
      // 2863: aload 2
      // 2864: ldc_w "skeleton40"
      // 2867: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 286a: ldc_w 51.2778
      // 286d: ldc_w -8.5502
      // 2870: ldc_w 24.5876
      // 2873: ldc_w -2.5172
      // 2876: ldc_w -0.6348
      // 2879: ldc_w 2.7893
      // 287c: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 287f: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2882: astore 138
      // 2884: aload 138
      // 2886: ldc_w "torso40"
      // 2889: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 288c: bipush 30
      // 288e: bipush 37
      // 2890: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2893: ldc_w -2.0
      // 2896: ldc_w -21.9833
      // 2899: ldc_w -2.0
      // 289c: ldc_w 4.0
      // 289f: ldc_w 22.0
      // 28a2: ldc_w 3.0
      // 28a5: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 28a8: dup
      // 28a9: fconst_0
      // 28aa: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 28ad: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 28b0: bipush 0
      // 28b1: bipush 37
      // 28b3: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 28b6: ldc_w -5.0
      // 28b9: ldc_w -5.9833
      // 28bc: ldc_w -4.0
      // 28bf: ldc_w 10.0
      // 28c2: ldc_w 6.0
      // 28c5: ldc_w 5.0
      // 28c8: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 28cb: dup
      // 28cc: fconst_0
      // 28cd: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 28d0: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 28d3: bipush 0
      // 28d4: bipush 0
      // 28d5: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 28d8: ldc_w -6.0
      // 28db: ldc_w -17.9833
      // 28de: ldc_w -6.0
      // 28e1: ldc_w 12.0
      // 28e4: ldc_w 12.0
      // 28e7: ldc_w 7.0
      // 28ea: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 28ed: dup
      // 28ee: fconst_0
      // 28ef: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 28f2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 28f5: fconst_0
      // 28f6: ldc_w 7.15
      // 28f9: ldc_w 1.7778
      // 28fc: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 28ff: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2902: astore 139
      // 2904: aload 139
      // 2906: ldc_w "head24"
      // 2909: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 290c: bipush 0
      // 290d: bipush 19
      // 290f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2912: ldc_w -5.0
      // 2915: ldc_w -8.75
      // 2918: ldc_w -7.25
      // 291b: ldc_w 10.0
      // 291e: ldc_w 9.0
      // 2921: ldc_w 9.0
      // 2924: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2927: dup
      // 2928: fconst_0
      // 2929: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 292c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 292f: bipush 38
      // 2931: bipush 25
      // 2933: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2936: ldc_w -3.0
      // 2939: ldc_w 0.25
      // 293c: ldc_w -7.25
      // 293f: ldc_w 6.0
      // 2942: ldc_w 4.0
      // 2945: ldc_w 4.0
      // 2948: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 294b: dup
      // 294c: fconst_0
      // 294d: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2950: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2953: fconst_0
      // 2954: ldc_w -23.0767
      // 2957: ldc_w -1.2873
      // 295a: ldc_w -0.6002
      // 295d: ldc_w -0.9396
      // 2960: ldc_w -0.3626
      // 2963: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 2966: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2969: astore 140
      // 296b: aload 139
      // 296d: ldc_w "left_arm17"
      // 2970: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2973: bipush 38
      // 2975: bipush 0
      // 2976: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2979: ldc_w -1.5
      // 297c: ldc_w -3.0
      // 297f: ldc_w -1.5
      // 2982: ldc_w 3.0
      // 2985: ldc_w 22.0
      // 2988: ldc_w 3.0
      // 298b: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 298e: dup
      // 298f: fconst_0
      // 2990: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2993: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2996: ldc_w 7.5
      // 2999: ldc_w -14.9833
      // 299c: ldc_w -0.5
      // 299f: ldc_w -0.9435
      // 29a2: ldc_w -0.2136
      // 29a5: ldc_w -0.5452
      // 29a8: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 29ab: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 29ae: astore 141
      // 29b0: aload 2
      // 29b1: ldc_w "skeleton41"
      // 29b4: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 29b7: ldc_w 43.0528
      // 29ba: ldc_w -43.3447
      // 29bd: ldc_w -12.3834
      // 29c0: ldc_w 1.467
      // 29c3: ldc_w 0.5781
      // 29c6: ldc_w -3.0788
      // 29c9: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 29cc: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 29cf: astore 142
      // 29d1: aload 142
      // 29d3: ldc_w "torso41"
      // 29d6: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 29d9: bipush 30
      // 29db: bipush 37
      // 29dd: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 29e0: ldc_w -2.0
      // 29e3: ldc_w -21.9833
      // 29e6: ldc_w -2.0
      // 29e9: ldc_w 4.0
      // 29ec: ldc_w 22.0
      // 29ef: ldc_w 3.0
      // 29f2: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 29f5: dup
      // 29f6: fconst_0
      // 29f7: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 29fa: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 29fd: bipush 0
      // 29fe: bipush 37
      // 2a00: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2a03: ldc_w -5.0
      // 2a06: ldc_w -5.9833
      // 2a09: ldc_w -4.0
      // 2a0c: ldc_w 10.0
      // 2a0f: ldc_w 6.0
      // 2a12: ldc_w 5.0
      // 2a15: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2a18: dup
      // 2a19: fconst_0
      // 2a1a: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2a1d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2a20: bipush 0
      // 2a21: bipush 0
      // 2a22: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2a25: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2a28: ldc_w -6.0
      // 2a2b: ldc_w -17.9833
      // 2a2e: ldc_w -6.0
      // 2a31: ldc_w 12.0
      // 2a34: ldc_w 12.0
      // 2a37: ldc_w 7.0
      // 2a3a: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2a3d: dup
      // 2a3e: fconst_0
      // 2a3f: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2a42: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2a45: bipush 0
      // 2a46: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2a49: fconst_0
      // 2a4a: ldc_w 7.15
      // 2a4d: ldc_w 1.7778
      // 2a50: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 2a53: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2a56: astore 143
      // 2a58: aload 2
      // 2a59: ldc_w "skeleton46"
      // 2a5c: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2a5f: ldc_w 37.0
      // 2a62: ldc_w -29.1667
      // 2a65: ldc_w 46.2222
      // 2a68: ldc_w -2.6797
      // 2a6b: ldc_w -0.5766
      // 2a6e: ldc_w 2.966
      // 2a71: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 2a74: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2a77: astore 144
      // 2a79: aload 144
      // 2a7b: ldc_w "torso46"
      // 2a7e: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2a81: bipush 30
      // 2a83: bipush 37
      // 2a85: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2a88: ldc_w -2.0
      // 2a8b: ldc_w -21.9833
      // 2a8e: ldc_w -2.0
      // 2a91: ldc_w 4.0
      // 2a94: ldc_w 22.0
      // 2a97: ldc_w 3.0
      // 2a9a: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2a9d: dup
      // 2a9e: fconst_0
      // 2a9f: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2aa2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2aa5: bipush 0
      // 2aa6: bipush 37
      // 2aa8: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2aab: ldc_w -5.0
      // 2aae: ldc_w -5.9833
      // 2ab1: ldc_w -4.0
      // 2ab4: ldc_w 10.0
      // 2ab7: ldc_w 6.0
      // 2aba: ldc_w 5.0
      // 2abd: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2ac0: dup
      // 2ac1: fconst_0
      // 2ac2: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2ac5: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2ac8: bipush 0
      // 2ac9: bipush 0
      // 2aca: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2acd: ldc_w -6.0
      // 2ad0: ldc_w -17.9833
      // 2ad3: ldc_w -6.0
      // 2ad6: ldc_w 12.0
      // 2ad9: ldc_w 12.0
      // 2adc: ldc_w 7.0
      // 2adf: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2ae2: dup
      // 2ae3: fconst_0
      // 2ae4: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2ae7: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2aea: ldc_w 0.065
      // 2aed: ldc_w 36.3043
      // 2af0: ldc_w 1.6209
      // 2af3: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 2af6: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2af9: astore 145
      // 2afb: aload 145
      // 2afd: ldc_w "right_arm21"
      // 2b00: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2b03: bipush 38
      // 2b05: bipush 0
      // 2b06: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2b09: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2b0c: ldc_w -1.5
      // 2b0f: ldc_w -3.0
      // 2b12: ldc_w -1.5
      // 2b15: ldc_w 3.0
      // 2b18: ldc_w 22.0
      // 2b1b: ldc_w 3.0
      // 2b1e: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2b21: dup
      // 2b22: fconst_0
      // 2b23: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2b26: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2b29: bipush 0
      // 2b2a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2b2d: ldc_w -7.2426
      // 2b30: ldc_w -5.5204
      // 2b33: ldc_w -1.1213
      // 2b36: ldc_w -2.0456
      // 2b39: ldc_w 0.7911
      // 2b3c: ldc_w -0.5756
      // 2b3f: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 2b42: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2b45: astore 146
      // 2b47: aload 2
      // 2b48: ldc_w "skeleton42"
      // 2b4b: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2b4e: ldc_w -27.3085
      // 2b51: ldc_w -37.3738
      // 2b54: ldc_w -17.072
      // 2b57: ldc_w -2.0038
      // 2b5a: ldc_w 0.0566
      // 2b5d: ldc_w 0.072
      // 2b60: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 2b63: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2b66: astore 147
      // 2b68: aload 147
      // 2b6a: ldc_w "torso42"
      // 2b6d: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2b70: bipush 30
      // 2b72: bipush 37
      // 2b74: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2b77: ldc_w -2.0
      // 2b7a: ldc_w -21.9833
      // 2b7d: ldc_w -2.0
      // 2b80: ldc_w 4.0
      // 2b83: ldc_w 22.0
      // 2b86: ldc_w 3.0
      // 2b89: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2b8c: dup
      // 2b8d: fconst_0
      // 2b8e: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2b91: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2b94: bipush 0
      // 2b95: bipush 37
      // 2b97: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2b9a: ldc_w -5.0
      // 2b9d: ldc_w -5.9833
      // 2ba0: ldc_w -4.0
      // 2ba3: ldc_w 10.0
      // 2ba6: ldc_w 6.0
      // 2ba9: ldc_w 5.0
      // 2bac: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2baf: dup
      // 2bb0: fconst_0
      // 2bb1: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2bb4: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2bb7: bipush 0
      // 2bb8: bipush 0
      // 2bb9: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2bbc: ldc_w -6.0
      // 2bbf: ldc_w -17.9833
      // 2bc2: ldc_w -6.0
      // 2bc5: ldc_w 12.0
      // 2bc8: ldc_w 12.0
      // 2bcb: ldc_w 7.0
      // 2bce: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2bd1: dup
      // 2bd2: fconst_0
      // 2bd3: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2bd6: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2bd9: fconst_0
      // 2bda: ldc_w 7.15
      // 2bdd: ldc_w 1.7778
      // 2be0: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 2be3: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2be6: astore 148
      // 2be8: aload 2
      // 2be9: ldc_w "skeleton43"
      // 2bec: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2bef: ldc_w -19.2404
      // 2bf2: ldc_w -25.9233
      // 2bf5: ldc_w -33.5286
      // 2bf8: ldc_w 0.5313
      // 2bfb: ldc_w -0.5063
      // 2bfe: ldc_w -0.0183
      // 2c01: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 2c04: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2c07: astore 149
      // 2c09: aload 149
      // 2c0b: ldc_w "torso43"
      // 2c0e: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2c11: bipush 30
      // 2c13: bipush 37
      // 2c15: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2c18: ldc_w -2.0
      // 2c1b: ldc_w -21.9833
      // 2c1e: ldc_w -2.0
      // 2c21: ldc_w 4.0
      // 2c24: ldc_w 22.0
      // 2c27: ldc_w 3.0
      // 2c2a: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2c2d: dup
      // 2c2e: fconst_0
      // 2c2f: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2c32: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2c35: bipush 0
      // 2c36: bipush 37
      // 2c38: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2c3b: ldc_w -5.0
      // 2c3e: ldc_w -5.9833
      // 2c41: ldc_w -4.0
      // 2c44: ldc_w 10.0
      // 2c47: ldc_w 6.0
      // 2c4a: ldc_w 5.0
      // 2c4d: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2c50: dup
      // 2c51: fconst_0
      // 2c52: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2c55: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2c58: bipush 0
      // 2c59: bipush 0
      // 2c5a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2c5d: ldc_w -6.0
      // 2c60: ldc_w -17.9833
      // 2c63: ldc_w -6.0
      // 2c66: ldc_w 12.0
      // 2c69: ldc_w 12.0
      // 2c6c: ldc_w 7.0
      // 2c6f: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2c72: dup
      // 2c73: fconst_0
      // 2c74: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2c77: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2c7a: fconst_0
      // 2c7b: ldc_w 7.15
      // 2c7e: ldc_w 1.7778
      // 2c81: ldc_w -0.7854
      // 2c84: fconst_0
      // 2c85: fconst_0
      // 2c86: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 2c89: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2c8c: astore 150
      // 2c8e: aload 2
      // 2c8f: ldc_w "skeleton44"
      // 2c92: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2c95: ldc_w -3.1733
      // 2c98: ldc_w 0.931
      // 2c9b: ldc_w 40.1912
      // 2c9e: ldc_w -1.7488
      // 2ca1: ldc_w 0.5965
      // 2ca4: ldc_w 2.9464
      // 2ca7: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 2caa: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2cad: astore 151
      // 2caf: aload 151
      // 2cb1: ldc_w "torso44"
      // 2cb4: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2cb7: bipush 30
      // 2cb9: bipush 37
      // 2cbb: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2cbe: ldc_w -2.0
      // 2cc1: ldc_w -21.9833
      // 2cc4: ldc_w -2.0
      // 2cc7: ldc_w 4.0
      // 2cca: ldc_w 22.0
      // 2ccd: ldc_w 3.0
      // 2cd0: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2cd3: dup
      // 2cd4: fconst_0
      // 2cd5: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2cd8: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2cdb: bipush 0
      // 2cdc: bipush 37
      // 2cde: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2ce1: ldc_w -5.0
      // 2ce4: ldc_w -5.9833
      // 2ce7: ldc_w -4.0
      // 2cea: ldc_w 10.0
      // 2ced: ldc_w 6.0
      // 2cf0: ldc_w 5.0
      // 2cf3: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2cf6: dup
      // 2cf7: fconst_0
      // 2cf8: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2cfb: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2cfe: bipush 0
      // 2cff: bipush 0
      // 2d00: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2d03: ldc_w -6.0
      // 2d06: ldc_w -17.9833
      // 2d09: ldc_w -6.0
      // 2d0c: ldc_w 12.0
      // 2d0f: ldc_w 12.0
      // 2d12: ldc_w 7.0
      // 2d15: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2d18: dup
      // 2d19: fconst_0
      // 2d1a: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2d1d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2d20: fconst_0
      // 2d21: ldc_w 7.15
      // 2d24: ldc_w 1.7778
      // 2d27: ldc_w -0.7854
      // 2d2a: fconst_0
      // 2d2b: fconst_0
      // 2d2c: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 2d2f: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2d32: astore 152
      // 2d34: aload 152
      // 2d36: ldc_w "head25"
      // 2d39: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2d3c: bipush 0
      // 2d3d: bipush 19
      // 2d3f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2d42: ldc_w -5.0
      // 2d45: ldc_w -8.75
      // 2d48: ldc_w -7.25
      // 2d4b: ldc_w 10.0
      // 2d4e: ldc_w 9.0
      // 2d51: ldc_w 9.0
      // 2d54: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2d57: dup
      // 2d58: fconst_0
      // 2d59: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2d5c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2d5f: bipush 38
      // 2d61: bipush 25
      // 2d63: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2d66: ldc_w -3.0
      // 2d69: ldc_w 0.25
      // 2d6c: ldc_w -7.25
      // 2d6f: ldc_w 6.0
      // 2d72: ldc_w 4.0
      // 2d75: ldc_w 4.0
      // 2d78: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2d7b: dup
      // 2d7c: fconst_0
      // 2d7d: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2d80: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2d83: ldc_w -0.7654
      // 2d86: ldc_w -22.341
      // 2d89: ldc_w -3.4708
      // 2d8c: ldc_w -0.7459
      // 2d8f: ldc_w 0.274
      // 2d92: ldc_w 0.2849
      // 2d95: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 2d98: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2d9b: astore 153
      // 2d9d: aload 152
      // 2d9f: ldc_w "right_arm19"
      // 2da2: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2da5: bipush 38
      // 2da7: bipush 0
      // 2da8: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2dab: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2dae: ldc_w -1.5
      // 2db1: ldc_w -3.0
      // 2db4: ldc_w -1.5
      // 2db7: ldc_w 3.0
      // 2dba: ldc_w 22.0
      // 2dbd: ldc_w 3.0
      // 2dc0: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2dc3: dup
      // 2dc4: fconst_0
      // 2dc5: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2dc8: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2dcb: bipush 0
      // 2dcc: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2dcf: ldc_w -8.9807
      // 2dd2: ldc_w -19.1064
      // 2dd5: ldc_w -3.0658
      // 2dd8: ldc_w -1.9923
      // 2ddb: ldc_w 0.3614
      // 2dde: ldc_w -0.1572
      // 2de1: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 2de4: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2de7: astore 154
      // 2de9: aload 152
      // 2deb: ldc_w "left_arm18"
      // 2dee: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2df1: bipush 38
      // 2df3: bipush 0
      // 2df4: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2df7: ldc_w -1.5
      // 2dfa: ldc_w -3.0
      // 2dfd: ldc_w -1.5
      // 2e00: ldc_w 3.0
      // 2e03: ldc_w 22.0
      // 2e06: ldc_w 3.0
      // 2e09: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2e0c: dup
      // 2e0d: fconst_0
      // 2e0e: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2e11: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2e14: ldc_w 6.7168
      // 2e17: ldc_w -19.4873
      // 2e1a: ldc_w -0.183
      // 2e1d: ldc_w -1.1781
      // 2e20: fconst_0
      // 2e21: ldc_w -0.3927
      // 2e24: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 2e27: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2e2a: astore 155
      // 2e2c: aload 2
      // 2e2d: ldc_w "skeleton45"
      // 2e30: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2e33: ldc_w 8.5186
      // 2e36: ldc_w -2.362
      // 2e39: ldc_w 36.7943
      // 2e3c: ldc_w -1.5915
      // 2e3f: ldc_w 0.2662
      // 2e42: ldc_w -0.4329
      // 2e45: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 2e48: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2e4b: astore 156
      // 2e4d: aload 156
      // 2e4f: ldc_w "torso45"
      // 2e52: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2e55: bipush 30
      // 2e57: bipush 37
      // 2e59: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2e5c: ldc_w -2.0
      // 2e5f: ldc_w -21.9833
      // 2e62: ldc_w -2.0
      // 2e65: ldc_w 4.0
      // 2e68: ldc_w 22.0
      // 2e6b: ldc_w 3.0
      // 2e6e: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2e71: dup
      // 2e72: fconst_0
      // 2e73: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2e76: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2e79: bipush 0
      // 2e7a: bipush 37
      // 2e7c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2e7f: ldc_w -5.0
      // 2e82: ldc_w -5.9833
      // 2e85: ldc_w -4.0
      // 2e88: ldc_w 10.0
      // 2e8b: ldc_w 6.0
      // 2e8e: ldc_w 5.0
      // 2e91: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2e94: dup
      // 2e95: fconst_0
      // 2e96: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2e99: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2e9c: bipush 0
      // 2e9d: bipush 0
      // 2e9e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2ea1: ldc_w -6.0
      // 2ea4: ldc_w -17.9833
      // 2ea7: ldc_w -6.0
      // 2eaa: ldc_w 12.0
      // 2ead: ldc_w 12.0
      // 2eb0: ldc_w 7.0
      // 2eb3: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2eb6: dup
      // 2eb7: fconst_0
      // 2eb8: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2ebb: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2ebe: fconst_0
      // 2ebf: ldc_w 7.15
      // 2ec2: ldc_w 1.7778
      // 2ec5: ldc_w 0.0456
      // 2ec8: ldc_w -0.2974
      // 2ecb: ldc_w 0.0466
      // 2ece: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 2ed1: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2ed4: astore 157
      // 2ed6: aload 157
      // 2ed8: ldc_w "head26"
      // 2edb: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2ede: bipush 0
      // 2edf: bipush 19
      // 2ee1: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2ee4: ldc_w -5.0
      // 2ee7: ldc_w -8.75
      // 2eea: ldc_w -7.25
      // 2eed: ldc_w 10.0
      // 2ef0: ldc_w 9.0
      // 2ef3: ldc_w 9.0
      // 2ef6: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2ef9: dup
      // 2efa: fconst_0
      // 2efb: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2efe: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2f01: bipush 38
      // 2f03: bipush 25
      // 2f05: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2f08: ldc_w -3.0
      // 2f0b: ldc_w 0.25
      // 2f0e: ldc_w -7.25
      // 2f11: ldc_w 6.0
      // 2f14: ldc_w 4.0
      // 2f17: ldc_w 4.0
      // 2f1a: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2f1d: dup
      // 2f1e: fconst_0
      // 2f1f: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2f22: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2f25: ldc_w 2.801
      // 2f28: ldc_w -22.9562
      // 2f2b: ldc_w -2.955
      // 2f2e: ldc_w -3.0249
      // 2f31: ldc_w -0.1273
      // 2f34: ldc_w -2.2171
      // 2f37: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 2f3a: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2f3d: astore 158
      // 2f3f: aload 157
      // 2f41: ldc_w "right_arm20"
      // 2f44: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2f47: bipush 38
      // 2f49: bipush 0
      // 2f4a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2f4d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2f50: ldc_w -1.5
      // 2f53: ldc_w -3.0
      // 2f56: ldc_w -1.5
      // 2f59: ldc_w 3.0
      // 2f5c: ldc_w 22.0
      // 2f5f: ldc_w 3.0
      // 2f62: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2f65: dup
      // 2f66: fconst_0
      // 2f67: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2f6a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2f6d: bipush 0
      // 2f6e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2f71: ldc_w -7.5
      // 2f74: ldc_w -14.9833
      // 2f77: ldc_w -0.5
      // 2f7a: ldc_w 0.3677
      // 2f7d: ldc_w 0.2308
      // 2f80: ldc_w 0.7197
      // 2f83: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 2f86: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2f89: astore 159
      // 2f8b: aload 157
      // 2f8d: ldc_w "left_arm19"
      // 2f90: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2f93: bipush 38
      // 2f95: bipush 0
      // 2f96: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2f99: ldc_w -1.5
      // 2f9c: ldc_w -3.0
      // 2f9f: ldc_w -1.5
      // 2fa2: ldc_w 3.0
      // 2fa5: ldc_w 22.0
      // 2fa8: ldc_w 3.0
      // 2fab: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 2fae: dup
      // 2faf: fconst_0
      // 2fb0: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 2fb3: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2fb6: ldc_w 7.6584
      // 2fb9: ldc_w -15.3925
      // 2fbc: ldc_w -2.4513
      // 2fbf: ldc_w 0.1663
      // 2fc2: ldc_w -0.2071
      // 2fc5: ldc_w -0.7879
      // 2fc8: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 2fcb: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2fce: astore 160
      // 2fd0: aload 2
      // 2fd1: ldc_w "skeleton49"
      // 2fd4: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2fd7: ldc_w 37.5186
      // 2fda: ldc_w -18.362
      // 2fdd: ldc_w 30.7943
      // 2fe0: ldc_w 1.7173
      // 2fe3: ldc_w -0.7003
      // 2fe6: ldc_w -3.1021
      // 2fe9: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 2fec: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 2fef: astore 161
      // 2ff1: aload 161
      // 2ff3: ldc_w "torso49"
      // 2ff6: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 2ff9: bipush 30
      // 2ffb: bipush 37
      // 2ffd: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3000: ldc_w -2.0
      // 3003: ldc_w -21.9833
      // 3006: ldc_w -2.0
      // 3009: ldc_w 4.0
      // 300c: ldc_w 22.0
      // 300f: ldc_w 3.0
      // 3012: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3015: dup
      // 3016: fconst_0
      // 3017: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 301a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 301d: bipush 0
      // 301e: bipush 37
      // 3020: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3023: ldc_w -5.0
      // 3026: ldc_w -5.9833
      // 3029: ldc_w -4.0
      // 302c: ldc_w 10.0
      // 302f: ldc_w 6.0
      // 3032: ldc_w 5.0
      // 3035: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3038: dup
      // 3039: fconst_0
      // 303a: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 303d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3040: bipush 0
      // 3041: bipush 0
      // 3042: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3045: ldc_w -6.0
      // 3048: ldc_w -17.9833
      // 304b: ldc_w -6.0
      // 304e: ldc_w 12.0
      // 3051: ldc_w 12.0
      // 3054: ldc_w 7.0
      // 3057: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 305a: dup
      // 305b: fconst_0
      // 305c: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 305f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3062: fconst_0
      // 3063: ldc_w 7.15
      // 3066: ldc_w 1.7778
      // 3069: ldc_w 0.7111
      // 306c: ldc_w -0.1691
      // 306f: ldc_w -0.1882
      // 3072: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3075: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 3078: astore 162
      // 307a: aload 2
      // 307b: ldc_w "skeleton47"
      // 307e: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3081: ldc_w 21.7148
      // 3084: ldc_w -12.1441
      // 3087: ldc_w 34.8516
      // 308a: ldc_w -0.48
      // 308d: fconst_0
      // 308e: ldc_w -0.5236
      // 3091: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3094: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 3097: astore 163
      // 3099: aload 163
      // 309b: ldc_w "torso47"
      // 309e: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 30a1: bipush 30
      // 30a3: bipush 37
      // 30a5: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 30a8: ldc_w -2.0
      // 30ab: ldc_w -21.9833
      // 30ae: ldc_w -1.0
      // 30b1: ldc_w 4.0
      // 30b4: ldc_w 22.0
      // 30b7: ldc_w 3.0
      // 30ba: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 30bd: dup
      // 30be: fconst_0
      // 30bf: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 30c2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 30c5: bipush 0
      // 30c6: bipush 37
      // 30c8: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 30cb: ldc_w -5.0
      // 30ce: ldc_w -5.9833
      // 30d1: ldc_w -1.0
      // 30d4: ldc_w 10.0
      // 30d7: ldc_w 6.0
      // 30da: ldc_w 5.0
      // 30dd: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 30e0: dup
      // 30e1: fconst_0
      // 30e2: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 30e5: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 30e8: bipush 0
      // 30e9: bipush 0
      // 30ea: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 30ed: ldc_w -6.0
      // 30f0: ldc_w -17.9833
      // 30f3: ldc_w -1.0
      // 30f6: ldc_w 12.0
      // 30f9: ldc_w 12.0
      // 30fc: ldc_w 7.0
      // 30ff: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3102: dup
      // 3103: fconst_0
      // 3104: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3107: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 310a: fconst_0
      // 310b: ldc_w 7.15
      // 310e: ldc_w -1.7778
      // 3111: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3114: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 3117: astore 164
      // 3119: aload 164
      // 311b: ldc_w "head28"
      // 311e: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3121: bipush 0
      // 3122: bipush 19
      // 3124: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3127: ldc_w -5.0
      // 312a: ldc_w -8.75
      // 312d: ldc_w -1.75
      // 3130: ldc_w 10.0
      // 3133: ldc_w 9.0
      // 3136: ldc_w 9.0
      // 3139: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 313c: dup
      // 313d: fconst_0
      // 313e: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3141: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3144: bipush 38
      // 3146: bipush 25
      // 3148: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 314b: ldc_w -3.0
      // 314e: ldc_w 0.25
      // 3151: ldc_w -1.75
      // 3154: ldc_w 6.0
      // 3157: ldc_w 4.0
      // 315a: ldc_w 4.0
      // 315d: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3160: dup
      // 3161: fconst_0
      // 3162: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3165: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3168: ldc_w 1.6289
      // 316b: ldc_w -22.7938
      // 316e: ldc_w 8.8835
      // 3171: ldc_w 2.3273
      // 3174: ldc_w -0.16
      // 3177: ldc_w -2.6452
      // 317a: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 317d: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 3180: astore 165
      // 3182: aload 164
      // 3184: ldc_w "right_arm22"
      // 3187: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 318a: bipush 38
      // 318c: bipush 0
      // 318d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3190: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3193: ldc_w -1.5
      // 3196: ldc_w -3.0
      // 3199: ldc_w -1.5
      // 319c: ldc_w 3.0
      // 319f: ldc_w 22.0
      // 31a2: ldc_w 3.0
      // 31a5: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 31a8: dup
      // 31a9: fconst_0
      // 31aa: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 31ad: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 31b0: bipush 0
      // 31b1: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 31b4: ldc_w -9.2575
      // 31b7: ldc_w -15.9354
      // 31ba: ldc_w 0.4311
      // 31bd: ldc_w 1.1128
      // 31c0: ldc_w -0.5228
      // 31c3: ldc_w 0.8576
      // 31c6: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 31c9: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 31cc: astore 166
      // 31ce: aload 2
      // 31cf: ldc_w "skeleton48"
      // 31d2: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 31d5: ldc_w -20.4814
      // 31d8: ldc_w -2.362
      // 31db: ldc_w 32.7943
      // 31de: ldc_w -0.1831
      // 31e1: ldc_w -1.1388
      // 31e4: ldc_w -1.3123
      // 31e7: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 31ea: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 31ed: astore 167
      // 31ef: aload 167
      // 31f1: ldc_w "torso48"
      // 31f4: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 31f7: bipush 30
      // 31f9: bipush 37
      // 31fb: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 31fe: ldc_w -2.0
      // 3201: ldc_w -21.9833
      // 3204: ldc_w -2.0
      // 3207: ldc_w 4.0
      // 320a: ldc_w 22.0
      // 320d: ldc_w 3.0
      // 3210: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3213: dup
      // 3214: fconst_0
      // 3215: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3218: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 321b: bipush 0
      // 321c: bipush 37
      // 321e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3221: ldc_w -5.0
      // 3224: ldc_w -5.9833
      // 3227: ldc_w -4.0
      // 322a: ldc_w 10.0
      // 322d: ldc_w 6.0
      // 3230: ldc_w 5.0
      // 3233: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3236: dup
      // 3237: fconst_0
      // 3238: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 323b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 323e: bipush 0
      // 323f: bipush 0
      // 3240: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3243: ldc_w -6.0
      // 3246: ldc_w -17.9833
      // 3249: ldc_w -6.0
      // 324c: ldc_w 12.0
      // 324f: ldc_w 12.0
      // 3252: ldc_w 7.0
      // 3255: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3258: dup
      // 3259: fconst_0
      // 325a: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 325d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3260: fconst_0
      // 3261: ldc_w 7.15
      // 3264: ldc_w 1.7778
      // 3267: ldc_w 0.0456
      // 326a: ldc_w -0.2974
      // 326d: ldc_w 0.0466
      // 3270: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3273: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 3276: astore 168
      // 3278: aload 168
      // 327a: ldc_w "head29"
      // 327d: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3280: bipush 0
      // 3281: bipush 19
      // 3283: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3286: ldc_w -5.0
      // 3289: ldc_w -8.75
      // 328c: ldc_w -7.25
      // 328f: ldc_w 10.0
      // 3292: ldc_w 9.0
      // 3295: ldc_w 9.0
      // 3298: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 329b: dup
      // 329c: fconst_0
      // 329d: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 32a0: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 32a3: bipush 38
      // 32a5: bipush 25
      // 32a7: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 32aa: ldc_w -3.0
      // 32ad: ldc_w 0.25
      // 32b0: ldc_w -7.25
      // 32b3: ldc_w 6.0
      // 32b6: ldc_w 4.0
      // 32b9: ldc_w 4.0
      // 32bc: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 32bf: dup
      // 32c0: fconst_0
      // 32c1: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 32c4: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 32c7: ldc_w 2.801
      // 32ca: ldc_w -22.9562
      // 32cd: ldc_w -2.955
      // 32d0: ldc_w -3.0249
      // 32d3: ldc_w -0.1273
      // 32d6: ldc_w -2.2171
      // 32d9: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 32dc: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 32df: astore 169
      // 32e1: aload 168
      // 32e3: ldc_w "right_arm23"
      // 32e6: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 32e9: bipush 38
      // 32eb: bipush 0
      // 32ec: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 32ef: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 32f2: ldc_w -1.5
      // 32f5: ldc_w -3.0
      // 32f8: ldc_w -1.5
      // 32fb: ldc_w 3.0
      // 32fe: ldc_w 22.0
      // 3301: ldc_w 3.0
      // 3304: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3307: dup
      // 3308: fconst_0
      // 3309: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 330c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 330f: bipush 0
      // 3310: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3313: ldc_w -7.5
      // 3316: ldc_w -14.9833
      // 3319: ldc_w -0.5
      // 331c: ldc_w 0.3677
      // 331f: ldc_w 0.2308
      // 3322: ldc_w 0.7197
      // 3325: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3328: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 332b: astore 170
      // 332d: aload 168
      // 332f: ldc_w "left_arm20"
      // 3332: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3335: ldc_w 7.6584
      // 3338: ldc_w -15.3925
      // 333b: ldc_w -2.4513
      // 333e: ldc_w 0.1663
      // 3341: ldc_w -0.2071
      // 3344: ldc_w -0.7879
      // 3347: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 334a: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 334d: astore 171
      // 334f: aload 2
      // 3350: ldc_w "skeleton50"
      // 3353: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3356: fconst_1
      // 3357: ldc_w -14.1667
      // 335a: ldc_w 35.2222
      // 335d: ldc_w -2.6689
      // 3360: ldc_w 0.8589
      // 3363: ldc_w -2.7858
      // 3366: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3369: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 336c: astore 172
      // 336e: aload 172
      // 3370: ldc_w "torso50"
      // 3373: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3376: bipush 30
      // 3378: bipush 37
      // 337a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 337d: ldc_w -2.0
      // 3380: ldc_w -21.9833
      // 3383: ldc_w -2.0
      // 3386: ldc_w 4.0
      // 3389: ldc_w 22.0
      // 338c: ldc_w 3.0
      // 338f: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3392: dup
      // 3393: fconst_0
      // 3394: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3397: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 339a: bipush 0
      // 339b: bipush 37
      // 339d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 33a0: ldc_w -5.0
      // 33a3: ldc_w -5.9833
      // 33a6: ldc_w -4.0
      // 33a9: ldc_w 10.0
      // 33ac: ldc_w 6.0
      // 33af: ldc_w 5.0
      // 33b2: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 33b5: dup
      // 33b6: fconst_0
      // 33b7: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 33ba: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 33bd: bipush 0
      // 33be: bipush 0
      // 33bf: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 33c2: ldc_w -6.0
      // 33c5: ldc_w -17.9833
      // 33c8: ldc_w -6.0
      // 33cb: ldc_w 12.0
      // 33ce: ldc_w 12.0
      // 33d1: ldc_w 7.0
      // 33d4: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 33d7: dup
      // 33d8: fconst_0
      // 33d9: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 33dc: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 33df: ldc_w -3.1731
      // 33e2: ldc_w 5.65
      // 33e5: ldc_w -1.921
      // 33e8: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 33eb: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 33ee: astore 173
      // 33f0: aload 173
      // 33f2: ldc_w "head30"
      // 33f5: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 33f8: bipush 0
      // 33f9: bipush 19
      // 33fb: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 33fe: ldc_w -5.0
      // 3401: ldc_w -8.75
      // 3404: ldc_w -7.25
      // 3407: ldc_w 10.0
      // 340a: ldc_w 9.0
      // 340d: ldc_w 9.0
      // 3410: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3413: dup
      // 3414: fconst_0
      // 3415: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3418: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 341b: bipush 38
      // 341d: bipush 25
      // 341f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3422: ldc_w -3.0
      // 3425: ldc_w 0.25
      // 3428: ldc_w -7.25
      // 342b: ldc_w 6.0
      // 342e: ldc_w 4.0
      // 3431: ldc_w 4.0
      // 3434: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3437: dup
      // 3438: fconst_0
      // 3439: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 343c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 343f: ldc_w -5.2997
      // 3442: ldc_w -23.3914
      // 3445: ldc_w -4.439
      // 3448: ldc_w 0.7533
      // 344b: ldc_w 0.5912
      // 344e: ldc_w -0.5938
      // 3451: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3454: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 3457: astore 174
      // 3459: aload 173
      // 345b: ldc_w "right_arm24"
      // 345e: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3461: bipush 38
      // 3463: bipush 0
      // 3464: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3467: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 346a: ldc_w -1.5
      // 346d: ldc_w -3.0
      // 3470: ldc_w -1.5
      // 3473: ldc_w 3.0
      // 3476: ldc_w 22.0
      // 3479: ldc_w 3.0
      // 347c: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 347f: dup
      // 3480: fconst_0
      // 3481: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3484: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3487: bipush 0
      // 3488: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 348b: ldc_w -9.2575
      // 348e: ldc_w -15.9354
      // 3491: ldc_w -0.4311
      // 3494: ldc_w 0.2727
      // 3497: ldc_w 0.5449
      // 349a: ldc_w 1.2048
      // 349d: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 34a0: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 34a3: astore 175
      // 34a5: aload 2
      // 34a6: ldc_w "head27"
      // 34a9: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 34ac: bipush 0
      // 34ad: bipush 19
      // 34af: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 34b2: ldc_w -5.0
      // 34b5: ldc_w -8.75
      // 34b8: ldc_w -7.25
      // 34bb: ldc_w 10.0
      // 34be: ldc_w 9.0
      // 34c1: ldc_w 9.0
      // 34c4: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 34c7: dup
      // 34c8: fconst_0
      // 34c9: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 34cc: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 34cf: bipush 38
      // 34d1: bipush 25
      // 34d3: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 34d6: ldc_w -3.0
      // 34d9: ldc_w 0.25
      // 34dc: ldc_w -7.25
      // 34df: ldc_w 6.0
      // 34e2: ldc_w 4.0
      // 34e5: ldc_w 4.0
      // 34e8: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 34eb: dup
      // 34ec: fconst_0
      // 34ed: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 34f0: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 34f3: ldc_w 68.0
      // 34f6: ldc_w -1.25
      // 34f9: ldc_w -42.75
      // 34fc: ldc_w -0.3685
      // 34ff: ldc_w -0.5102
      // 3502: ldc_w -0.0208
      // 3505: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3508: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 350b: astore 176
      // 350d: aload 2
      // 350e: ldc_w "skeleton52"
      // 3511: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3514: ldc_w -16.7329
      // 3517: ldc_w -5.0273
      // 351a: ldc_w 31.3181
      // 351d: ldc_w -1.7453
      // 3520: ldc_w 0.7418
      // 3523: ldc_w -3.1416
      // 3526: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3529: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 352c: astore 177
      // 352e: aload 177
      // 3530: ldc_w "torso52"
      // 3533: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3536: bipush 30
      // 3538: bipush 37
      // 353a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 353d: ldc_w -2.0
      // 3540: ldc_w -21.9833
      // 3543: ldc_w -2.0
      // 3546: ldc_w 4.0
      // 3549: ldc_w 22.0
      // 354c: ldc_w 3.0
      // 354f: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3552: dup
      // 3553: fconst_0
      // 3554: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3557: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 355a: bipush 0
      // 355b: bipush 37
      // 355d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3560: ldc_w -5.0
      // 3563: ldc_w -5.9833
      // 3566: ldc_w -4.0
      // 3569: ldc_w 10.0
      // 356c: ldc_w 6.0
      // 356f: ldc_w 5.0
      // 3572: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3575: dup
      // 3576: fconst_0
      // 3577: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 357a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 357d: bipush 0
      // 357e: bipush 0
      // 357f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3582: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3585: ldc_w -6.0
      // 3588: ldc_w -17.9833
      // 358b: ldc_w -6.0
      // 358e: ldc_w 12.0
      // 3591: ldc_w 12.0
      // 3594: ldc_w 7.0
      // 3597: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 359a: dup
      // 359b: fconst_0
      // 359c: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 359f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 35a2: bipush 0
      // 35a3: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 35a6: ldc_w 2.9491
      // 35a9: ldc_w 4.4887
      // 35ac: ldc_w 1.3085
      // 35af: ldc_w -0.7698
      // 35b2: ldc_w 0.3614
      // 35b5: ldc_w -0.3876
      // 35b8: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 35bb: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 35be: astore 178
      // 35c0: aload 178
      // 35c2: ldc_w "right_arm25"
      // 35c5: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 35c8: bipush 38
      // 35ca: bipush 0
      // 35cb: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 35ce: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 35d1: ldc_w -1.5
      // 35d4: ldc_w -3.0
      // 35d7: ldc_w -1.5
      // 35da: ldc_w 3.0
      // 35dd: ldc_w 22.0
      // 35e0: ldc_w 3.0
      // 35e3: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 35e6: dup
      // 35e7: fconst_0
      // 35e8: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 35eb: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 35ee: bipush 0
      // 35ef: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 35f2: ldc_w 8.6038
      // 35f5: ldc_w -18.1116
      // 35f8: ldc_w -5.1875
      // 35fb: ldc_w -2.1912
      // 35fe: ldc_w -0.4259
      // 3601: ldc_w 0.2208
      // 3604: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3607: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 360a: astore 179
      // 360c: aload 2
      // 360d: ldc_w "skeleton53"
      // 3610: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3613: ldc_w 49.7613
      // 3616: ldc_w -35.2873
      // 3619: ldc_w 18.6956
      // 361c: ldc_w -1.2892
      // 361f: ldc_w 0.9018
      // 3622: ldc_w 0.2929
      // 3625: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3628: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 362b: astore 180
      // 362d: aload 180
      // 362f: ldc_w "torso53"
      // 3632: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3635: bipush 30
      // 3637: bipush 37
      // 3639: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 363c: ldc_w -2.0
      // 363f: ldc_w -21.9833
      // 3642: ldc_w -2.0
      // 3645: ldc_w 4.0
      // 3648: ldc_w 22.0
      // 364b: ldc_w 3.0
      // 364e: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3651: dup
      // 3652: fconst_0
      // 3653: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3656: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3659: bipush 0
      // 365a: bipush 37
      // 365c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 365f: ldc_w -5.0
      // 3662: ldc_w -5.9833
      // 3665: ldc_w -4.0
      // 3668: ldc_w 10.0
      // 366b: ldc_w 6.0
      // 366e: ldc_w 5.0
      // 3671: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3674: dup
      // 3675: fconst_0
      // 3676: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3679: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 367c: bipush 0
      // 367d: bipush 0
      // 367e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3681: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3684: ldc_w -6.0
      // 3687: ldc_w -17.9833
      // 368a: ldc_w -6.0
      // 368d: ldc_w 12.0
      // 3690: ldc_w 12.0
      // 3693: ldc_w 7.0
      // 3696: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3699: dup
      // 369a: fconst_0
      // 369b: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 369e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 36a1: bipush 0
      // 36a2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 36a5: fconst_0
      // 36a6: ldc_w 7.15
      // 36a9: ldc_w 1.7778
      // 36ac: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 36af: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 36b2: astore 181
      // 36b4: aload 180
      // 36b6: ldc_w "left_arm21"
      // 36b9: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 36bc: bipush 38
      // 36be: bipush 0
      // 36bf: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 36c2: ldc_w -1.5
      // 36c5: ldc_w -3.0
      // 36c8: ldc_w -1.5
      // 36cb: ldc_w 3.0
      // 36ce: ldc_w 22.0
      // 36d1: ldc_w 3.0
      // 36d4: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 36d7: dup
      // 36d8: fconst_0
      // 36d9: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 36dc: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 36df: ldc_w -7.6146
      // 36e2: ldc_w -9.2985
      // 36e5: ldc_w -0.1777
      // 36e8: ldc_w 2.7489
      // 36eb: fconst_0
      // 36ec: ldc_w 0.0873
      // 36ef: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 36f2: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 36f5: astore 182
      // 36f7: aload 2
      // 36f8: ldc_w "skeleton54"
      // 36fb: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 36fe: ldc_w 36.4948
      // 3701: ldc_w -32.7037
      // 3704: ldc_w 30.7275
      // 3707: ldc_w 1.9635
      // 370a: ldc_w 0.7854
      // 370d: ldc_w 3.1416
      // 3710: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3713: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 3716: astore 183
      // 3718: aload 183
      // 371a: ldc_w "left_leg5"
      // 371d: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3720: bipush 38
      // 3722: bipush 0
      // 3723: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3726: ldc_w -1.5
      // 3729: fconst_0
      // 372a: ldc_w -1.5
      // 372d: ldc_w 3.0
      // 3730: ldc_w 22.0
      // 3733: ldc_w 3.0
      // 3736: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3739: dup
      // 373a: fconst_0
      // 373b: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 373e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3741: ldc_w 2.3782
      // 3744: ldc_w 7.0215
      // 3747: ldc_w -1.8788
      // 374a: ldc_w 0.2618
      // 374d: fconst_0
      // 374e: ldc_w -0.6981
      // 3751: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3754: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 3757: astore 184
      // 3759: aload 183
      // 375b: ldc_w "right_leg4"
      // 375e: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3761: bipush 38
      // 3763: bipush 0
      // 3764: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3767: ldc_w -1.5
      // 376a: fconst_0
      // 376b: ldc_w -1.5
      // 376e: ldc_w 3.0
      // 3771: ldc_w 22.0
      // 3774: ldc_w 3.0
      // 3777: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 377a: dup
      // 377b: fconst_0
      // 377c: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 377f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3782: ldc_w -1.7385
      // 3785: ldc_w 7.9412
      // 3788: ldc_w -0.9177
      // 378b: ldc_w 0.6545
      // 378e: fconst_0
      // 378f: ldc_w 0.3927
      // 3792: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3795: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 3798: astore 185
      // 379a: aload 183
      // 379c: ldc_w "torso54"
      // 379f: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 37a2: fconst_0
      // 37a3: ldc_w 7.15
      // 37a6: ldc_w 1.7778
      // 37a9: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 37ac: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 37af: astore 186
      // 37b1: aload 186
      // 37b3: ldc_w "cube_r2"
      // 37b6: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 37b9: bipush 0
      // 37ba: bipush 0
      // 37bb: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 37be: ldc_w -7.0
      // 37c1: ldc_w -10.5
      // 37c4: ldc_w -6.0
      // 37c7: ldc_w 12.0
      // 37ca: ldc_w 12.0
      // 37cd: ldc_w 7.0
      // 37d0: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 37d3: dup
      // 37d4: fconst_0
      // 37d5: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 37d8: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 37db: bipush 0
      // 37dc: bipush 37
      // 37de: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 37e1: ldc_w -6.0
      // 37e4: ldc_w 1.5
      // 37e7: ldc_w -4.0
      // 37ea: ldc_w 10.0
      // 37ed: ldc_w 6.0
      // 37f0: ldc_w 5.0
      // 37f3: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 37f6: dup
      // 37f7: fconst_0
      // 37f8: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 37fb: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 37fe: bipush 30
      // 3800: bipush 37
      // 3802: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3805: ldc_w -3.0
      // 3808: ldc_w -14.5
      // 380b: ldc_w -2.0
      // 380e: ldc_w 4.0
      // 3811: ldc_w 22.0
      // 3814: ldc_w 3.0
      // 3817: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 381a: dup
      // 381b: fconst_0
      // 381c: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 381f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3822: fconst_0
      // 3823: ldc_w -7.4833
      // 3826: ldc_w -2.0
      // 3829: ldc_w -0.0894
      // 382c: ldc_w 0.3829
      // 382f: ldc_w -0.2355
      // 3832: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3835: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 3838: astore 187
      // 383a: aload 186
      // 383c: ldc_w "head31"
      // 383f: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3842: bipush 0
      // 3843: bipush 19
      // 3845: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3848: ldc_w -5.0
      // 384b: ldc_w -8.75
      // 384e: ldc_w -7.25
      // 3851: ldc_w 10.0
      // 3854: ldc_w 9.0
      // 3857: ldc_w 9.0
      // 385a: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 385d: dup
      // 385e: fconst_0
      // 385f: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3862: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3865: bipush 38
      // 3867: bipush 25
      // 3869: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 386c: ldc_w -3.0
      // 386f: ldc_w 0.25
      // 3872: ldc_w -7.25
      // 3875: ldc_w 6.0
      // 3878: ldc_w 4.0
      // 387b: ldc_w 4.0
      // 387e: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3881: dup
      // 3882: fconst_0
      // 3883: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3886: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3889: fconst_0
      // 388a: ldc_w -24.3547
      // 388d: ldc_w -2.8713
      // 3890: ldc_w 0.7098
      // 3893: ldc_w 0.8511
      // 3896: ldc_w 0.2363
      // 3899: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 389c: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 389f: astore 188
      // 38a1: aload 186
      // 38a3: ldc_w "left_arm22"
      // 38a6: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 38a9: bipush 38
      // 38ab: bipush 0
      // 38ac: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 38af: ldc_w -1.5
      // 38b2: ldc_w -3.0
      // 38b5: ldc_w -1.5
      // 38b8: ldc_w 3.0
      // 38bb: ldc_w 22.0
      // 38be: ldc_w 3.0
      // 38c1: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 38c4: dup
      // 38c5: fconst_0
      // 38c6: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 38c9: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 38cc: ldc_w 1.5
      // 38cf: ldc_w -14.9833
      // 38d2: ldc_w -10.5
      // 38d5: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 38d8: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 38db: astore 189
      // 38dd: aload 2
      // 38de: ldc_w "skeleton55"
      // 38e1: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 38e4: ldc_w 9.0
      // 38e7: ldc_w -33.1667
      // 38ea: ldc_w 47.2222
      // 38ed: ldc_w 2.1466
      // 38f0: ldc_w 0.886
      // 38f3: ldc_w -2.8806
      // 38f6: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 38f9: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 38fc: astore 190
      // 38fe: aload 190
      // 3900: ldc_w "torso55"
      // 3903: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3906: bipush 30
      // 3908: bipush 37
      // 390a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 390d: ldc_w -2.0
      // 3910: ldc_w -21.9833
      // 3913: ldc_w -2.0
      // 3916: ldc_w 4.0
      // 3919: ldc_w 22.0
      // 391c: ldc_w 3.0
      // 391f: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3922: dup
      // 3923: fconst_0
      // 3924: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3927: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 392a: bipush 0
      // 392b: bipush 37
      // 392d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3930: ldc_w -5.0
      // 3933: ldc_w -5.9833
      // 3936: ldc_w -4.0
      // 3939: ldc_w 10.0
      // 393c: ldc_w 6.0
      // 393f: ldc_w 5.0
      // 3942: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3945: dup
      // 3946: fconst_0
      // 3947: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 394a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 394d: bipush 0
      // 394e: bipush 0
      // 394f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3952: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3955: ldc_w -6.0
      // 3958: ldc_w -17.9833
      // 395b: ldc_w -6.0
      // 395e: ldc_w 12.0
      // 3961: ldc_w 12.0
      // 3964: ldc_w 7.0
      // 3967: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 396a: dup
      // 396b: fconst_0
      // 396c: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 396f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3972: bipush 0
      // 3973: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3976: fconst_0
      // 3977: ldc_w 7.15
      // 397a: ldc_w 1.7778
      // 397d: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3980: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 3983: astore 191
      // 3985: aload 2
      // 3986: ldc_w "head32"
      // 3989: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 398c: bipush 0
      // 398d: bipush 19
      // 398f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3992: ldc_w -5.0
      // 3995: ldc_w -8.75
      // 3998: ldc_w -7.25
      // 399b: ldc_w 10.0
      // 399e: ldc_w 9.0
      // 39a1: ldc_w 9.0
      // 39a4: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 39a7: dup
      // 39a8: fconst_0
      // 39a9: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 39ac: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 39af: bipush 38
      // 39b1: bipush 25
      // 39b3: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 39b6: ldc_w -3.0
      // 39b9: ldc_w 0.25
      // 39bc: ldc_w -7.25
      // 39bf: ldc_w 6.0
      // 39c2: ldc_w 4.0
      // 39c5: ldc_w 4.0
      // 39c8: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 39cb: dup
      // 39cc: fconst_0
      // 39cd: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 39d0: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 39d3: ldc_w -43.8959
      // 39d6: ldc_w -21.2257
      // 39d9: ldc_w 25.1138
      // 39dc: ldc_w -1.2944
      // 39df: ldc_w 0.8268
      // 39e2: ldc_w -2.9879
      // 39e5: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 39e8: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 39eb: astore 192
      // 39ed: aload 2
      // 39ee: ldc_w "skeleton60"
      // 39f1: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 39f4: ldc_w -9.0
      // 39f7: ldc_w -31.1667
      // 39fa: ldc_w 43.2222
      // 39fd: ldc_w -3.0414
      // 3a00: ldc_w 0.9013
      // 3a03: ldc_w -2.2097
      // 3a06: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3a09: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 3a0c: astore 193
      // 3a0e: aload 193
      // 3a10: ldc_w "torso60"
      // 3a13: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3a16: bipush 30
      // 3a18: bipush 37
      // 3a1a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3a1d: ldc_w -2.0
      // 3a20: ldc_w -21.9833
      // 3a23: ldc_w -2.0
      // 3a26: ldc_w 4.0
      // 3a29: ldc_w 22.0
      // 3a2c: ldc_w 3.0
      // 3a2f: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3a32: dup
      // 3a33: fconst_0
      // 3a34: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3a37: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3a3a: bipush 0
      // 3a3b: bipush 37
      // 3a3d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3a40: ldc_w -5.0
      // 3a43: ldc_w -5.9833
      // 3a46: ldc_w -4.0
      // 3a49: ldc_w 10.0
      // 3a4c: ldc_w 6.0
      // 3a4f: ldc_w 5.0
      // 3a52: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3a55: dup
      // 3a56: fconst_0
      // 3a57: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3a5a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3a5d: bipush 0
      // 3a5e: bipush 0
      // 3a5f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3a62: ldc_w -6.0
      // 3a65: ldc_w -17.9833
      // 3a68: ldc_w -6.0
      // 3a6b: ldc_w 12.0
      // 3a6e: ldc_w 12.0
      // 3a71: ldc_w 7.0
      // 3a74: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3a77: dup
      // 3a78: fconst_0
      // 3a79: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3a7c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3a7f: ldc_w -0.866
      // 3a82: ldc_w 7.6481
      // 3a85: ldc_w 1.8214
      // 3a88: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3a8b: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 3a8e: astore 194
      // 3a90: aload 2
      // 3a91: ldc_w "skeleton57"
      // 3a94: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3a97: ldc_w -32.7222
      // 3a9a: ldc_w -18.5501
      // 3a9d: ldc_w 28.5876
      // 3aa0: ldc_w -2.501
      // 3aa3: ldc_w 0.4609
      // 3aa6: ldc_w -2.822
      // 3aa9: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3aac: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 3aaf: astore 195
      // 3ab1: aload 195
      // 3ab3: ldc_w "torso57"
      // 3ab6: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3ab9: bipush 30
      // 3abb: bipush 37
      // 3abd: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3ac0: ldc_w -2.0
      // 3ac3: ldc_w -21.9833
      // 3ac6: ldc_w -2.0
      // 3ac9: ldc_w 4.0
      // 3acc: ldc_w 22.0
      // 3acf: ldc_w 3.0
      // 3ad2: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3ad5: dup
      // 3ad6: fconst_0
      // 3ad7: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3ada: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3add: bipush 0
      // 3ade: bipush 37
      // 3ae0: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3ae3: ldc_w -5.0
      // 3ae6: ldc_w -5.9833
      // 3ae9: ldc_w -4.0
      // 3aec: ldc_w 10.0
      // 3aef: ldc_w 6.0
      // 3af2: ldc_w 5.0
      // 3af5: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3af8: dup
      // 3af9: fconst_0
      // 3afa: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3afd: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3b00: bipush 0
      // 3b01: bipush 0
      // 3b02: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3b05: ldc_w -6.0
      // 3b08: ldc_w -17.9833
      // 3b0b: ldc_w -6.0
      // 3b0e: ldc_w 12.0
      // 3b11: ldc_w 12.0
      // 3b14: ldc_w 7.0
      // 3b17: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3b1a: dup
      // 3b1b: fconst_0
      // 3b1c: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3b1f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3b22: fconst_0
      // 3b23: ldc_w 7.15
      // 3b26: ldc_w 1.7778
      // 3b29: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3b2c: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 3b2f: astore 196
      // 3b31: aload 196
      // 3b33: ldc_w "head33"
      // 3b36: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3b39: bipush 0
      // 3b3a: bipush 19
      // 3b3c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3b3f: ldc_w -5.0
      // 3b42: ldc_w -8.75
      // 3b45: ldc_w -7.25
      // 3b48: ldc_w 10.0
      // 3b4b: ldc_w 9.0
      // 3b4e: ldc_w 9.0
      // 3b51: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3b54: dup
      // 3b55: fconst_0
      // 3b56: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3b59: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3b5c: bipush 38
      // 3b5e: bipush 25
      // 3b60: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3b63: ldc_w -3.0
      // 3b66: ldc_w 0.25
      // 3b69: ldc_w -7.25
      // 3b6c: ldc_w 6.0
      // 3b6f: ldc_w 4.0
      // 3b72: ldc_w 4.0
      // 3b75: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3b78: dup
      // 3b79: fconst_0
      // 3b7a: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3b7d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3b80: ldc_w 1.7006
      // 3b83: ldc_w -23.0776
      // 3b86: ldc_w -2.3399
      // 3b89: ldc_w 0.5435
      // 3b8c: ldc_w 0.355
      // 3b8f: ldc_w 0.3487
      // 3b92: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3b95: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 3b98: astore 197
      // 3b9a: aload 196
      // 3b9c: ldc_w "left_arm23"
      // 3b9f: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3ba2: bipush 38
      // 3ba4: bipush 0
      // 3ba5: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3ba8: ldc_w -1.5
      // 3bab: ldc_w -3.0
      // 3bae: ldc_w -1.5
      // 3bb1: ldc_w 3.0
      // 3bb4: ldc_w 22.0
      // 3bb7: ldc_w 3.0
      // 3bba: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3bbd: dup
      // 3bbe: fconst_0
      // 3bbf: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3bc2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3bc5: ldc_w 7.5
      // 3bc8: ldc_w -14.9833
      // 3bcb: ldc_w -0.5
      // 3bce: ldc_w -1.249
      // 3bd1: ldc_w -0.2136
      // 3bd4: ldc_w -0.5452
      // 3bd7: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3bda: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 3bdd: astore 198
      // 3bdf: aload 2
      // 3be0: ldc_w "skeleton58"
      // 3be3: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3be6: ldc_w -56.0
      // 3be9: ldc_w -25.1667
      // 3bec: ldc_w 14.2222
      // 3bef: ldc_w -2.6328
      // 3bf2: ldc_w -0.6426
      // 3bf5: ldc_w -2.3895
      // 3bf8: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3bfb: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 3bfe: astore 199
      // 3c00: aload 199
      // 3c02: ldc_w "torso58"
      // 3c05: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3c08: bipush 30
      // 3c0a: bipush 37
      // 3c0c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3c0f: ldc_w -2.0
      // 3c12: ldc_w -21.9833
      // 3c15: ldc_w -2.0
      // 3c18: ldc_w 4.0
      // 3c1b: ldc_w 22.0
      // 3c1e: ldc_w 3.0
      // 3c21: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3c24: dup
      // 3c25: fconst_0
      // 3c26: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3c29: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3c2c: bipush 0
      // 3c2d: bipush 37
      // 3c2f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3c32: ldc_w -5.0
      // 3c35: ldc_w -5.9833
      // 3c38: ldc_w -4.0
      // 3c3b: ldc_w 10.0
      // 3c3e: ldc_w 6.0
      // 3c41: ldc_w 5.0
      // 3c44: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3c47: dup
      // 3c48: fconst_0
      // 3c49: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3c4c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3c4f: bipush 0
      // 3c50: bipush 0
      // 3c51: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3c54: ldc_w -6.0
      // 3c57: ldc_w -17.9833
      // 3c5a: ldc_w -6.0
      // 3c5d: ldc_w 12.0
      // 3c60: ldc_w 12.0
      // 3c63: ldc_w 7.0
      // 3c66: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3c69: dup
      // 3c6a: fconst_0
      // 3c6b: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3c6e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3c71: ldc_w -0.866
      // 3c74: ldc_w 7.6481
      // 3c77: ldc_w 1.8214
      // 3c7a: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3c7d: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 3c80: astore 200
      // 3c82: aload 2
      // 3c83: ldc_w "skeleton56"
      // 3c86: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3c89: ldc_w 24.3999
      // 3c8c: ldc_w -42.1943
      // 3c8f: ldc_w 44.031
      // 3c92: ldc_w 2.123
      // 3c95: ldc_w -1.0139
      // 3c98: ldc_w 2.8691
      // 3c9b: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3c9e: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 3ca1: astore 201
      // 3ca3: aload 201
      // 3ca5: ldc_w "torso56"
      // 3ca8: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3cab: bipush 30
      // 3cad: bipush 37
      // 3caf: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3cb2: ldc_w -2.0
      // 3cb5: ldc_w -21.9833
      // 3cb8: ldc_w -2.0
      // 3cbb: ldc_w 4.0
      // 3cbe: ldc_w 22.0
      // 3cc1: ldc_w 3.0
      // 3cc4: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3cc7: dup
      // 3cc8: fconst_0
      // 3cc9: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3ccc: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3ccf: bipush 0
      // 3cd0: bipush 37
      // 3cd2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3cd5: ldc_w -5.0
      // 3cd8: ldc_w -5.9833
      // 3cdb: ldc_w -4.0
      // 3cde: ldc_w 10.0
      // 3ce1: ldc_w 6.0
      // 3ce4: ldc_w 5.0
      // 3ce7: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3cea: dup
      // 3ceb: fconst_0
      // 3cec: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3cef: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3cf2: bipush 0
      // 3cf3: bipush 0
      // 3cf4: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3cf7: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3cfa: ldc_w -6.0
      // 3cfd: ldc_w -17.9833
      // 3d00: ldc_w -6.0
      // 3d03: ldc_w 12.0
      // 3d06: ldc_w 12.0
      // 3d09: ldc_w 7.0
      // 3d0c: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3d0f: dup
      // 3d10: fconst_0
      // 3d11: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3d14: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3d17: bipush 0
      // 3d18: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3d1b: fconst_0
      // 3d1c: ldc_w 7.15
      // 3d1f: ldc_w 1.7778
      // 3d22: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3d25: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 3d28: astore 202
      // 3d2a: aload 2
      // 3d2b: ldc_w "skeleton59"
      // 3d2e: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3d31: ldc_w -46.0
      // 3d34: ldc_w -32.1667
      // 3d37: ldc_w 24.2222
      // 3d3a: ldc_w 2.6349
      // 3d3d: ldc_w 0.006
      // 3d40: ldc_w -2.6899
      // 3d43: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3d46: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 3d49: astore 203
      // 3d4b: aload 203
      // 3d4d: ldc_w "torso59"
      // 3d50: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3d53: bipush 30
      // 3d55: bipush 37
      // 3d57: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3d5a: ldc_w -2.0
      // 3d5d: ldc_w -21.9833
      // 3d60: ldc_w -2.0
      // 3d63: ldc_w 4.0
      // 3d66: ldc_w 22.0
      // 3d69: ldc_w 3.0
      // 3d6c: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3d6f: dup
      // 3d70: fconst_0
      // 3d71: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3d74: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3d77: bipush 0
      // 3d78: bipush 37
      // 3d7a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3d7d: ldc_w -5.0
      // 3d80: ldc_w -5.9833
      // 3d83: ldc_w -4.0
      // 3d86: ldc_w 10.0
      // 3d89: ldc_w 6.0
      // 3d8c: ldc_w 5.0
      // 3d8f: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3d92: dup
      // 3d93: fconst_0
      // 3d94: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3d97: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3d9a: bipush 0
      // 3d9b: bipush 0
      // 3d9c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3d9f: ldc_w -6.0
      // 3da2: ldc_w -17.9833
      // 3da5: ldc_w -6.0
      // 3da8: ldc_w 12.0
      // 3dab: ldc_w 12.0
      // 3dae: ldc_w 7.0
      // 3db1: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3db4: dup
      // 3db5: fconst_0
      // 3db6: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3db9: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3dbc: ldc_w -0.866
      // 3dbf: ldc_w 7.6481
      // 3dc2: ldc_w 1.8214
      // 3dc5: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3dc8: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 3dcb: astore 204
      // 3dcd: aload 2
      // 3dce: ldc_w "head34"
      // 3dd1: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3dd4: bipush 0
      // 3dd5: bipush 19
      // 3dd7: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3dda: ldc_w -5.0
      // 3ddd: ldc_w -8.75
      // 3de0: ldc_w -7.25
      // 3de3: ldc_w 10.0
      // 3de6: ldc_w 9.0
      // 3de9: ldc_w 9.0
      // 3dec: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3def: dup
      // 3df0: fconst_0
      // 3df1: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3df4: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3df7: bipush 38
      // 3df9: bipush 25
      // 3dfb: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3dfe: ldc_w -3.0
      // 3e01: ldc_w 0.25
      // 3e04: ldc_w -7.25
      // 3e07: ldc_w 6.0
      // 3e0a: ldc_w 4.0
      // 3e0d: ldc_w 4.0
      // 3e10: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3e13: dup
      // 3e14: fconst_0
      // 3e15: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3e18: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3e1b: ldc_w 3.3196
      // 3e1e: ldc_w -44.1682
      // 3e21: ldc_w 35.6171
      // 3e24: ldc_w -3.0249
      // 3e27: ldc_w -0.1273
      // 3e2a: ldc_w -2.2171
      // 3e2d: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3e30: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 3e33: astore 205
      // 3e35: aload 2
      // 3e36: ldc_w "head35"
      // 3e39: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3e3c: bipush 0
      // 3e3d: bipush 19
      // 3e3f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3e42: ldc_w -5.0
      // 3e45: ldc_w -8.75
      // 3e48: ldc_w -7.25
      // 3e4b: ldc_w 10.0
      // 3e4e: ldc_w 9.0
      // 3e51: ldc_w 9.0
      // 3e54: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3e57: dup
      // 3e58: fconst_0
      // 3e59: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3e5c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3e5f: bipush 38
      // 3e61: bipush 25
      // 3e63: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3e66: ldc_w -3.0
      // 3e69: ldc_w 0.25
      // 3e6c: ldc_w -7.25
      // 3e6f: ldc_w 6.0
      // 3e72: ldc_w 4.0
      // 3e75: ldc_w 4.0
      // 3e78: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3e7b: dup
      // 3e7c: fconst_0
      // 3e7d: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3e80: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3e83: ldc_w -24.6804
      // 3e86: ldc_w -23.1682
      // 3e89: ldc_w 40.6171
      // 3e8c: ldc_w -0.2198
      // 3e8f: ldc_w -0.6586
      // 3e92: ldc_w 0.1038
      // 3e95: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3e98: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 3e9b: astore 206
      // 3e9d: aload 2
      // 3e9e: ldc_w "skeleton61"
      // 3ea1: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3ea4: ldc_w -20.5346
      // 3ea7: ldc_w -37.6351
      // 3eaa: ldc_w 37.3853
      // 3ead: ldc_w 1.7436
      // 3eb0: ldc_w -0.7994
      // 3eb3: ldc_w 2.7398
      // 3eb6: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3eb9: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 3ebc: astore 207
      // 3ebe: aload 207
      // 3ec0: ldc_w "torso61"
      // 3ec3: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3ec6: bipush 0
      // 3ec7: bipush 37
      // 3ec9: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3ecc: ldc_w -5.0
      // 3ecf: ldc_w -5.9833
      // 3ed2: ldc_w -4.0
      // 3ed5: ldc_w 10.0
      // 3ed8: ldc_w 6.0
      // 3edb: ldc_w 5.0
      // 3ede: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3ee1: dup
      // 3ee2: fconst_0
      // 3ee3: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3ee6: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3ee9: bipush 0
      // 3eea: bipush 0
      // 3eeb: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3eee: ldc_w -6.0
      // 3ef1: ldc_w -17.9833
      // 3ef4: ldc_w -6.0
      // 3ef7: ldc_w 12.0
      // 3efa: ldc_w 12.0
      // 3efd: ldc_w 7.0
      // 3f00: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3f03: dup
      // 3f04: fconst_0
      // 3f05: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3f08: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3f0b: bipush 30
      // 3f0d: bipush 37
      // 3f0f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3f12: ldc_w -2.0
      // 3f15: ldc_w -21.9833
      // 3f18: ldc_w -2.0
      // 3f1b: ldc_w 4.0
      // 3f1e: ldc_w 22.0
      // 3f21: ldc_w 3.0
      // 3f24: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3f27: dup
      // 3f28: fconst_0
      // 3f29: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3f2c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3f2f: ldc_w -0.866
      // 3f32: ldc_w 7.6481
      // 3f35: ldc_w 1.8214
      // 3f38: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3f3b: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 3f3e: astore 208
      // 3f40: aload 2
      // 3f41: ldc_w "skeleton62"
      // 3f44: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3f47: ldc_w 27.5419
      // 3f4a: ldc_w -46.4687
      // 3f4d: ldc_w 28.1585
      // 3f50: ldc_w -1.6061
      // 3f53: ldc_w 0.8802
      // 3f56: ldc_w -0.1652
      // 3f59: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3f5c: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 3f5f: astore 209
      // 3f61: aload 209
      // 3f63: ldc_w "torso62"
      // 3f66: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3f69: bipush 30
      // 3f6b: bipush 37
      // 3f6d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3f70: ldc_w -2.0
      // 3f73: ldc_w -21.9833
      // 3f76: ldc_w -2.0
      // 3f79: ldc_w 4.0
      // 3f7c: ldc_w 22.0
      // 3f7f: ldc_w 3.0
      // 3f82: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3f85: dup
      // 3f86: fconst_0
      // 3f87: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3f8a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3f8d: bipush 0
      // 3f8e: bipush 37
      // 3f90: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3f93: ldc_w -5.0
      // 3f96: ldc_w -5.9833
      // 3f99: ldc_w -4.0
      // 3f9c: ldc_w 10.0
      // 3f9f: ldc_w 6.0
      // 3fa2: ldc_w 5.0
      // 3fa5: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3fa8: dup
      // 3fa9: fconst_0
      // 3faa: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3fad: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3fb0: bipush 0
      // 3fb1: bipush 0
      // 3fb2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3fb5: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3fb8: ldc_w -6.0
      // 3fbb: ldc_w -17.9833
      // 3fbe: ldc_w -6.0
      // 3fc1: ldc_w 12.0
      // 3fc4: ldc_w 12.0
      // 3fc7: ldc_w 7.0
      // 3fca: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 3fcd: dup
      // 3fce: fconst_0
      // 3fcf: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 3fd2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3fd5: bipush 0
      // 3fd6: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3fd9: fconst_0
      // 3fda: ldc_w 7.15
      // 3fdd: ldc_w 1.7778
      // 3fe0: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 3fe3: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 3fe6: astore 210
      // 3fe8: aload 209
      // 3fea: ldc_w "left_arm24"
      // 3fed: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3ff0: bipush 38
      // 3ff2: bipush 0
      // 3ff3: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 3ff6: ldc_w -1.5
      // 3ff9: ldc_w -3.0
      // 3ffc: ldc_w -1.5
      // 3fff: ldc_w 3.0
      // 4002: ldc_w 22.0
      // 4005: ldc_w 3.0
      // 4008: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 400b: dup
      // 400c: fconst_0
      // 400d: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4010: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4013: ldc_w -7.6146
      // 4016: ldc_w -9.2985
      // 4019: ldc_w -0.1777
      // 401c: ldc_w 2.7489
      // 401f: fconst_0
      // 4020: ldc_w 0.0873
      // 4023: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4026: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 4029: astore 211
      // 402b: aload 2
      // 402c: ldc_w "skeleton63"
      // 402f: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4032: ldc_w 21.4654
      // 4035: ldc_w -23.6351
      // 4038: ldc_w 30.3853
      // 403b: ldc_w 3.0461
      // 403e: ldc_w -0.2459
      // 4041: ldc_w 2.2061
      // 4044: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4047: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 404a: astore 212
      // 404c: aload 212
      // 404e: ldc_w "torso63"
      // 4051: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4054: bipush 0
      // 4055: bipush 37
      // 4057: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 405a: ldc_w -5.0
      // 405d: ldc_w -5.9833
      // 4060: ldc_w -4.0
      // 4063: ldc_w 10.0
      // 4066: ldc_w 6.0
      // 4069: ldc_w 5.0
      // 406c: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 406f: dup
      // 4070: fconst_0
      // 4071: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4074: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4077: bipush 0
      // 4078: bipush 0
      // 4079: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 407c: ldc_w -6.0
      // 407f: ldc_w -17.9833
      // 4082: ldc_w -6.0
      // 4085: ldc_w 12.0
      // 4088: ldc_w 12.0
      // 408b: ldc_w 7.0
      // 408e: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4091: dup
      // 4092: fconst_0
      // 4093: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4096: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4099: bipush 30
      // 409b: bipush 37
      // 409d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 40a0: ldc_w -2.0
      // 40a3: ldc_w -21.9833
      // 40a6: ldc_w -2.0
      // 40a9: ldc_w 4.0
      // 40ac: ldc_w 22.0
      // 40af: ldc_w 3.0
      // 40b2: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 40b5: dup
      // 40b6: fconst_0
      // 40b7: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 40ba: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 40bd: ldc_w -0.866
      // 40c0: ldc_w 7.6481
      // 40c3: ldc_w 1.8214
      // 40c6: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 40c9: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 40cc: astore 213
      // 40ce: aload 2
      // 40cf: ldc_w "skeleton65"
      // 40d2: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 40d5: ldc_w 21.0
      // 40d8: ldc_w -55.1667
      // 40db: ldc_w -6.7778
      // 40de: ldc_w 1.5708
      // 40e1: ldc_w -0.3927
      // 40e4: ldc_w -3.1416
      // 40e7: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 40ea: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 40ed: astore 214
      // 40ef: aload 214
      // 40f1: ldc_w "torso65"
      // 40f4: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 40f7: bipush 30
      // 40f9: bipush 37
      // 40fb: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 40fe: ldc_w -2.0
      // 4101: ldc_w -21.9833
      // 4104: ldc_w -2.0
      // 4107: ldc_w 4.0
      // 410a: ldc_w 22.0
      // 410d: ldc_w 3.0
      // 4110: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4113: dup
      // 4114: fconst_0
      // 4115: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4118: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 411b: bipush 0
      // 411c: bipush 37
      // 411e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4121: ldc_w -5.0
      // 4124: ldc_w -5.9833
      // 4127: ldc_w -4.0
      // 412a: ldc_w 10.0
      // 412d: ldc_w 6.0
      // 4130: ldc_w 5.0
      // 4133: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4136: dup
      // 4137: fconst_0
      // 4138: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 413b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 413e: bipush 0
      // 413f: bipush 0
      // 4140: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4143: ldc_w -6.0
      // 4146: ldc_w -17.9833
      // 4149: ldc_w -6.0
      // 414c: ldc_w 12.0
      // 414f: ldc_w 12.0
      // 4152: ldc_w 7.0
      // 4155: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4158: dup
      // 4159: fconst_0
      // 415a: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 415d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4160: ldc_w -0.6997
      // 4163: ldc_w 3.2657
      // 4166: ldc_w -0.4418
      // 4169: ldc_w -0.1745
      // 416c: fconst_0
      // 416d: fconst_0
      // 416e: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4171: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 4174: astore 215
      // 4176: aload 215
      // 4178: ldc_w "right_arm27"
      // 417b: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 417e: bipush 38
      // 4180: bipush 0
      // 4181: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4184: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4187: ldc_w -1.5
      // 418a: ldc_w -3.0
      // 418d: ldc_w -1.5
      // 4190: ldc_w 3.0
      // 4193: ldc_w 22.0
      // 4196: ldc_w 3.0
      // 4199: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 419c: dup
      // 419d: fconst_0
      // 419e: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 41a1: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 41a4: bipush 0
      // 41a5: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 41a8: ldc_w -7.2758
      // 41ab: ldc_w -18.6107
      // 41ae: ldc_w 1.9067
      // 41b1: ldc_w 0.6776
      // 41b4: ldc_w -0.274
      // 41b7: ldc_w 2.7093
      // 41ba: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 41bd: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 41c0: astore 216
      // 41c2: aload 2
      // 41c3: ldc_w "head36"
      // 41c6: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 41c9: bipush 0
      // 41ca: bipush 19
      // 41cc: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 41cf: ldc_w -5.0
      // 41d2: ldc_w -8.75
      // 41d5: ldc_w -7.25
      // 41d8: ldc_w 10.0
      // 41db: ldc_w 9.0
      // 41de: ldc_w 9.0
      // 41e1: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 41e4: dup
      // 41e5: fconst_0
      // 41e6: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 41e9: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 41ec: bipush 38
      // 41ee: bipush 25
      // 41f0: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 41f3: ldc_w -3.0
      // 41f6: ldc_w 0.25
      // 41f9: ldc_w -7.25
      // 41fc: ldc_w 6.0
      // 41ff: ldc_w 4.0
      // 4202: ldc_w 4.0
      // 4205: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4208: dup
      // 4209: fconst_0
      // 420a: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 420d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4210: ldc_w 36.3196
      // 4213: ldc_w -54.1682
      // 4216: ldc_w -17.3829
      // 4219: ldc_w -3.0422
      // 421c: ldc_w -0.9456
      // 421f: ldc_w -1.893
      // 4222: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4225: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 4228: astore 217
      // 422a: aload 2
      // 422b: ldc_w "skeleton64"
      // 422e: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4231: ldc_w 24.0
      // 4234: ldc_w -58.1667
      // 4237: ldc_w 1.2222
      // 423a: ldc_w 1.7453
      // 423d: ldc_w 0.829
      // 4240: ldc_w -3.1416
      // 4243: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4246: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 4249: astore 218
      // 424b: aload 218
      // 424d: ldc_w "torso64"
      // 4250: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4253: bipush 30
      // 4255: bipush 37
      // 4257: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 425a: ldc_w -2.0
      // 425d: ldc_w -21.9833
      // 4260: ldc_w -2.0
      // 4263: ldc_w 4.0
      // 4266: ldc_w 22.0
      // 4269: ldc_w 3.0
      // 426c: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 426f: dup
      // 4270: fconst_0
      // 4271: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4274: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4277: bipush 0
      // 4278: bipush 37
      // 427a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 427d: ldc_w -5.0
      // 4280: ldc_w -5.9833
      // 4283: ldc_w -4.0
      // 4286: ldc_w 10.0
      // 4289: ldc_w 6.0
      // 428c: ldc_w 5.0
      // 428f: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4292: dup
      // 4293: fconst_0
      // 4294: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4297: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 429a: bipush 0
      // 429b: bipush 0
      // 429c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 429f: ldc_w -6.0
      // 42a2: ldc_w -17.9833
      // 42a5: ldc_w -6.0
      // 42a8: ldc_w 12.0
      // 42ab: ldc_w 12.0
      // 42ae: ldc_w 7.0
      // 42b1: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 42b4: dup
      // 42b5: fconst_0
      // 42b6: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 42b9: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 42bc: ldc_w -0.6997
      // 42bf: ldc_w 3.2657
      // 42c2: ldc_w -0.4418
      // 42c5: ldc_w -0.1745
      // 42c8: fconst_0
      // 42c9: fconst_0
      // 42ca: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 42cd: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 42d0: astore 219
      // 42d2: aload 219
      // 42d4: ldc_w "right_arm26"
      // 42d7: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 42da: bipush 38
      // 42dc: bipush 0
      // 42dd: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 42e0: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 42e3: ldc_w -1.5
      // 42e6: ldc_w -3.0
      // 42e9: ldc_w -1.5
      // 42ec: ldc_w 3.0
      // 42ef: ldc_w 22.0
      // 42f2: ldc_w 3.0
      // 42f5: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 42f8: dup
      // 42f9: fconst_0
      // 42fa: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 42fd: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4300: bipush 0
      // 4301: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4304: ldc_w -7.2758
      // 4307: ldc_w -18.6107
      // 430a: ldc_w 1.9067
      // 430d: ldc_w 0.6776
      // 4310: ldc_w -0.274
      // 4313: ldc_w 2.7093
      // 4316: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4319: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 431c: astore 220
      // 431e: aload 2
      // 431f: ldc_w "skeleton66"
      // 4322: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4325: ldc_w -3.0
      // 4328: ldc_w -51.1667
      // 432b: ldc_w -20.7778
      // 432e: ldc_w 1.5669
      // 4331: ldc_w 1.2806
      // 4334: ldc_w 3.0401
      // 4337: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 433a: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 433d: astore 221
      // 433f: aload 221
      // 4341: ldc_w "left_leg6"
      // 4344: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4347: bipush 38
      // 4349: bipush 0
      // 434a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 434d: ldc_w -1.5
      // 4350: fconst_0
      // 4351: ldc_w -1.5
      // 4354: ldc_w 3.0
      // 4357: ldc_w 22.0
      // 435a: ldc_w 3.0
      // 435d: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4360: dup
      // 4361: fconst_0
      // 4362: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4365: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4368: ldc_w 2.5
      // 436b: ldc_w 7.1667
      // 436e: ldc_w 1.2778
      // 4371: ldc_w 0.4451
      // 4374: ldc_w -0.484
      // 4377: ldc_w -1.0109
      // 437a: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 437d: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 4380: astore 222
      // 4382: aload 221
      // 4384: ldc_w "right_leg5"
      // 4387: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 438a: bipush 38
      // 438c: bipush 0
      // 438d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4390: ldc_w -1.5
      // 4393: fconst_0
      // 4394: ldc_w -1.5
      // 4397: ldc_w 3.0
      // 439a: ldc_w 22.0
      // 439d: ldc_w 3.0
      // 43a0: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 43a3: dup
      // 43a4: fconst_0
      // 43a5: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 43a8: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 43ab: ldc_w -2.5
      // 43ae: ldc_w 7.1667
      // 43b1: ldc_w 1.2778
      // 43b4: ldc_w 0.5236
      // 43b7: fconst_0
      // 43b8: ldc_w 0.3491
      // 43bb: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 43be: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 43c1: astore 223
      // 43c3: aload 221
      // 43c5: ldc_w "torso66"
      // 43c8: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 43cb: fconst_0
      // 43cc: ldc_w 7.15
      // 43cf: ldc_w 1.7778
      // 43d2: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 43d5: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 43d8: astore 224
      // 43da: aload 224
      // 43dc: ldc_w "cube_r3"
      // 43df: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 43e2: bipush 0
      // 43e3: bipush 0
      // 43e4: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 43e7: ldc_w -6.0036
      // 43ea: ldc_w -10.5116
      // 43ed: ldc_w -4.1242
      // 43f0: ldc_w 12.0
      // 43f3: ldc_w 12.0
      // 43f6: ldc_w 7.0
      // 43f9: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 43fc: dup
      // 43fd: fconst_0
      // 43fe: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4401: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4404: bipush 0
      // 4405: bipush 37
      // 4407: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 440a: ldc_w -5.0036
      // 440d: ldc_w 1.4884
      // 4410: ldc_w -2.1242
      // 4413: ldc_w 10.0
      // 4416: ldc_w 6.0
      // 4419: ldc_w 5.0
      // 441c: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 441f: dup
      // 4420: fconst_0
      // 4421: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4424: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4427: bipush 30
      // 4429: bipush 37
      // 442b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 442e: ldc_w -2.0036
      // 4431: ldc_w -14.5116
      // 4434: ldc_w -0.1242
      // 4437: ldc_w 4.0
      // 443a: ldc_w 22.0
      // 443d: ldc_w 3.0
      // 4440: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4443: dup
      // 4444: fconst_0
      // 4445: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4448: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 444b: ldc_w 0.0036
      // 444e: ldc_w -7.4717
      // 4451: ldc_w -1.8759
      // 4454: fconst_0
      // 4455: ldc_w -0.3054
      // 4458: fconst_0
      // 4459: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 445c: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 445f: astore 225
      // 4461: aload 224
      // 4463: ldc_w "head38"
      // 4466: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4469: bipush 0
      // 446a: bipush 19
      // 446c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 446f: ldc_w -5.0
      // 4472: ldc_w -8.75
      // 4475: ldc_w -7.25
      // 4478: ldc_w 10.0
      // 447b: ldc_w 9.0
      // 447e: ldc_w 9.0
      // 4481: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4484: dup
      // 4485: fconst_0
      // 4486: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4489: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 448c: bipush 38
      // 448e: bipush 25
      // 4490: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4493: ldc_w -3.0
      // 4496: ldc_w 0.25
      // 4499: ldc_w -7.25
      // 449c: ldc_w 6.0
      // 449f: ldc_w 4.0
      // 44a2: ldc_w 4.0
      // 44a5: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 44a8: dup
      // 44a9: fconst_0
      // 44aa: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 44ad: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 44b0: fconst_0
      // 44b1: ldc_w -22.2333
      // 44b4: ldc_w -0.75
      // 44b7: ldc_w -0.829
      // 44ba: fconst_0
      // 44bb: fconst_0
      // 44bc: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 44bf: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 44c2: astore 226
      // 44c4: aload 224
      // 44c6: ldc_w "right_arm28"
      // 44c9: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 44cc: bipush 38
      // 44ce: bipush 0
      // 44cf: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 44d2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 44d5: ldc_w -1.5
      // 44d8: ldc_w -3.0
      // 44db: ldc_w -1.5
      // 44de: ldc_w 3.0
      // 44e1: ldc_w 22.0
      // 44e4: ldc_w 3.0
      // 44e7: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 44ea: dup
      // 44eb: fconst_0
      // 44ec: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 44ef: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 44f2: bipush 0
      // 44f3: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 44f6: ldc_w -8.5452
      // 44f9: ldc_w -14.9768
      // 44fc: ldc_w -3.4846
      // 44ff: fconst_0
      // 4500: fconst_0
      // 4501: ldc_w 0.5672
      // 4504: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4507: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 450a: astore 227
      // 450c: aload 224
      // 450e: ldc_w "left_arm25"
      // 4511: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4514: bipush 38
      // 4516: bipush 0
      // 4517: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 451a: ldc_w -1.5
      // 451d: ldc_w -3.0
      // 4520: ldc_w -1.5
      // 4523: ldc_w 3.0
      // 4526: ldc_w 22.0
      // 4529: ldc_w 3.0
      // 452c: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 452f: dup
      // 4530: fconst_0
      // 4531: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4534: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4537: ldc_w 9.4164
      // 453a: ldc_w -15.5557
      // 453d: ldc_w -0.5022
      // 4540: ldc_w 0.1841
      // 4543: ldc_w -0.7769
      // 4546: ldc_w -1.0025
      // 4549: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 454c: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 454f: astore 228
      // 4551: aload 2
      // 4552: ldc_w "head37"
      // 4555: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4558: bipush 0
      // 4559: bipush 19
      // 455b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 455e: ldc_w -5.0
      // 4561: ldc_w -8.75
      // 4564: ldc_w -7.25
      // 4567: ldc_w 10.0
      // 456a: ldc_w 9.0
      // 456d: ldc_w 9.0
      // 4570: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4573: dup
      // 4574: fconst_0
      // 4575: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4578: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 457b: bipush 38
      // 457d: bipush 25
      // 457f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4582: ldc_w -3.0
      // 4585: ldc_w 0.25
      // 4588: ldc_w -7.25
      // 458b: ldc_w 6.0
      // 458e: ldc_w 4.0
      // 4591: ldc_w 4.0
      // 4594: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4597: dup
      // 4598: fconst_0
      // 4599: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 459c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 459f: ldc_w 10.3196
      // 45a2: ldc_w -38.1682
      // 45a5: ldc_w -31.3829
      // 45a8: ldc_w -3.0422
      // 45ab: ldc_w -0.9456
      // 45ae: ldc_w -1.893
      // 45b1: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 45b4: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 45b7: astore 229
      // 45b9: aload 2
      // 45ba: ldc_w "skeleton67"
      // 45bd: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 45c0: ldc_w -20.0
      // 45c3: ldc_w -38.1667
      // 45c6: ldc_w 10.2222
      // 45c9: ldc_w 1.5686
      // 45cc: ldc_w -1.032
      // 45cf: ldc_w 3.0457
      // 45d2: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 45d5: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 45d8: astore 230
      // 45da: aload 230
      // 45dc: ldc_w "left_leg7"
      // 45df: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 45e2: bipush 38
      // 45e4: bipush 0
      // 45e5: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 45e8: ldc_w -1.5
      // 45eb: fconst_0
      // 45ec: ldc_w -1.5
      // 45ef: ldc_w 3.0
      // 45f2: ldc_w 22.0
      // 45f5: ldc_w 3.0
      // 45f8: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 45fb: dup
      // 45fc: fconst_0
      // 45fd: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4600: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4603: ldc_w 2.5
      // 4606: ldc_w 7.1667
      // 4609: ldc_w 1.2778
      // 460c: ldc_w 0.4451
      // 460f: ldc_w -0.484
      // 4612: ldc_w -1.0109
      // 4615: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4618: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 461b: astore 231
      // 461d: aload 230
      // 461f: ldc_w "right_leg6"
      // 4622: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4625: bipush 38
      // 4627: bipush 0
      // 4628: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 462b: ldc_w -1.5
      // 462e: fconst_0
      // 462f: ldc_w -1.5
      // 4632: ldc_w 3.0
      // 4635: ldc_w 22.0
      // 4638: ldc_w 3.0
      // 463b: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 463e: dup
      // 463f: fconst_0
      // 4640: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4643: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4646: ldc_w -2.5
      // 4649: ldc_w 7.1667
      // 464c: ldc_w 1.2778
      // 464f: ldc_w 0.5236
      // 4652: fconst_0
      // 4653: ldc_w 0.3491
      // 4656: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4659: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 465c: astore 232
      // 465e: aload 230
      // 4660: ldc_w "torso67"
      // 4663: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4666: fconst_0
      // 4667: ldc_w 7.15
      // 466a: ldc_w 1.7778
      // 466d: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4670: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 4673: astore 233
      // 4675: aload 233
      // 4677: ldc_w "cube_r4"
      // 467a: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 467d: bipush 0
      // 467e: bipush 0
      // 467f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4682: ldc_w -6.0036
      // 4685: ldc_w -10.5116
      // 4688: ldc_w -4.1242
      // 468b: ldc_w 12.0
      // 468e: ldc_w 12.0
      // 4691: ldc_w 7.0
      // 4694: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4697: dup
      // 4698: fconst_0
      // 4699: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 469c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 469f: bipush 0
      // 46a0: bipush 37
      // 46a2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 46a5: ldc_w -5.0036
      // 46a8: ldc_w 1.4884
      // 46ab: ldc_w -2.1242
      // 46ae: ldc_w 10.0
      // 46b1: ldc_w 6.0
      // 46b4: ldc_w 5.0
      // 46b7: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 46ba: dup
      // 46bb: fconst_0
      // 46bc: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 46bf: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 46c2: bipush 30
      // 46c4: bipush 37
      // 46c6: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 46c9: ldc_w -2.0036
      // 46cc: ldc_w -14.5116
      // 46cf: ldc_w -0.1242
      // 46d2: ldc_w 4.0
      // 46d5: ldc_w 22.0
      // 46d8: ldc_w 3.0
      // 46db: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 46de: dup
      // 46df: fconst_0
      // 46e0: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 46e3: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 46e6: ldc_w 0.0036
      // 46e9: ldc_w -7.4717
      // 46ec: ldc_w -1.8758
      // 46ef: fconst_0
      // 46f0: ldc_w -0.3054
      // 46f3: fconst_0
      // 46f4: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 46f7: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 46fa: astore 234
      // 46fc: aload 233
      // 46fe: ldc_w "head39"
      // 4701: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4704: bipush 0
      // 4705: bipush 19
      // 4707: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 470a: ldc_w -5.0
      // 470d: ldc_w -8.75
      // 4710: ldc_w -7.25
      // 4713: ldc_w 10.0
      // 4716: ldc_w 9.0
      // 4719: ldc_w 9.0
      // 471c: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 471f: dup
      // 4720: fconst_0
      // 4721: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4724: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4727: bipush 38
      // 4729: bipush 25
      // 472b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 472e: ldc_w -3.0
      // 4731: ldc_w 0.25
      // 4734: ldc_w -7.25
      // 4737: ldc_w 6.0
      // 473a: ldc_w 4.0
      // 473d: ldc_w 4.0
      // 4740: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4743: dup
      // 4744: fconst_0
      // 4745: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4748: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 474b: ldc_w 0.8092
      // 474e: ldc_w -21.6359
      // 4751: ldc_w -1.7441
      // 4754: ldc_w -0.829
      // 4757: ldc_w 0.6107
      // 475a: ldc_w -0.1156
      // 475d: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4760: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 4763: astore 235
      // 4765: aload 233
      // 4767: ldc_w "right_arm29"
      // 476a: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 476d: bipush 38
      // 476f: bipush 0
      // 4770: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4773: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4776: ldc_w -1.5
      // 4779: ldc_w -3.0
      // 477c: ldc_w -1.5
      // 477f: ldc_w 3.0
      // 4782: ldc_w 22.0
      // 4785: ldc_w 3.0
      // 4788: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 478b: dup
      // 478c: fconst_0
      // 478d: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4790: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4793: bipush 0
      // 4794: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4797: ldc_w -8.5452
      // 479a: ldc_w -14.9768
      // 479d: ldc_w -3.4845
      // 47a0: fconst_0
      // 47a1: fconst_0
      // 47a2: ldc_w 0.5672
      // 47a5: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 47a8: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 47ab: astore 236
      // 47ad: aload 233
      // 47af: ldc_w "left_arm26"
      // 47b2: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 47b5: bipush 38
      // 47b7: bipush 0
      // 47b8: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 47bb: ldc_w -1.5
      // 47be: ldc_w -3.0
      // 47c1: ldc_w -1.5
      // 47c4: ldc_w 3.0
      // 47c7: ldc_w 22.0
      // 47ca: ldc_w 3.0
      // 47cd: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 47d0: dup
      // 47d1: fconst_0
      // 47d2: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 47d5: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 47d8: ldc_w 9.4164
      // 47db: ldc_w -15.5557
      // 47de: ldc_w -0.5022
      // 47e1: ldc_w 0.1841
      // 47e4: ldc_w -0.7769
      // 47e7: ldc_w -1.0025
      // 47ea: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 47ed: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 47f0: astore 237
      // 47f2: aload 2
      // 47f3: ldc_w "head40"
      // 47f6: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 47f9: bipush 0
      // 47fa: bipush 19
      // 47fc: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 47ff: ldc_w -5.0
      // 4802: ldc_w -8.75
      // 4805: ldc_w -7.25
      // 4808: ldc_w 10.0
      // 480b: ldc_w 9.0
      // 480e: ldc_w 9.0
      // 4811: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4814: dup
      // 4815: fconst_0
      // 4816: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4819: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 481c: bipush 38
      // 481e: bipush 25
      // 4820: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4823: ldc_w -3.0
      // 4826: ldc_w 0.25
      // 4829: ldc_w -7.25
      // 482c: ldc_w 6.0
      // 482f: ldc_w 4.0
      // 4832: ldc_w 4.0
      // 4835: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4838: dup
      // 4839: fconst_0
      // 483a: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 483d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4840: ldc_w -30.6804
      // 4843: ldc_w -44.1682
      // 4846: ldc_w 18.6171
      // 4849: ldc_w -3.0422
      // 484c: ldc_w -0.9456
      // 484f: ldc_w -1.893
      // 4852: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4855: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 4858: astore 238
      // 485a: aload 2
      // 485b: ldc_w "skeleton68"
      // 485e: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4861: ldc_w -8.0
      // 4864: ldc_w -45.1667
      // 4867: ldc_w 35.2222
      // 486a: ldc_w 1.591
      // 486d: ldc_w -0.1874
      // 4870: ldc_w 2.8734
      // 4873: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4876: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 4879: astore 239
      // 487b: aload 239
      // 487d: ldc_w "torso68"
      // 4880: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4883: bipush 30
      // 4885: bipush 37
      // 4887: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 488a: ldc_w -2.0
      // 488d: ldc_w -21.9833
      // 4890: ldc_w -2.0
      // 4893: ldc_w 4.0
      // 4896: ldc_w 22.0
      // 4899: ldc_w 3.0
      // 489c: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 489f: dup
      // 48a0: fconst_0
      // 48a1: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 48a4: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 48a7: bipush 0
      // 48a8: bipush 37
      // 48aa: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 48ad: ldc_w -5.0
      // 48b0: ldc_w -5.9833
      // 48b3: ldc_w -4.0
      // 48b6: ldc_w 10.0
      // 48b9: ldc_w 6.0
      // 48bc: ldc_w 5.0
      // 48bf: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 48c2: dup
      // 48c3: fconst_0
      // 48c4: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 48c7: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 48ca: bipush 0
      // 48cb: bipush 0
      // 48cc: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 48cf: ldc_w -6.0
      // 48d2: ldc_w -17.9833
      // 48d5: ldc_w -6.0
      // 48d8: ldc_w 12.0
      // 48db: ldc_w 12.0
      // 48de: ldc_w 7.0
      // 48e1: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 48e4: dup
      // 48e5: fconst_0
      // 48e6: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 48e9: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 48ec: ldc_w -0.866
      // 48ef: ldc_w 7.6481
      // 48f2: ldc_w 1.8214
      // 48f5: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 48f8: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 48fb: astore 240
      // 48fd: aload 240
      // 48ff: ldc_w "left_arm27"
      // 4902: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4905: bipush 38
      // 4907: bipush 0
      // 4908: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 490b: ldc_w -1.5
      // 490e: ldc_w -3.0
      // 4911: ldc_w -1.5
      // 4914: ldc_w 3.0
      // 4917: ldc_w 22.0
      // 491a: ldc_w 3.0
      // 491d: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4920: dup
      // 4921: fconst_0
      // 4922: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4925: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4928: ldc_w 7.6959
      // 492b: ldc_w -0.0974
      // 492e: ldc_w 2.2421
      // 4931: ldc_w 0.8897
      // 4934: ldc_w -0.5727
      // 4937: ldc_w -1.6272
      // 493a: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 493d: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 4940: astore 241
      // 4942: aload 2
      // 4943: ldc_w "head41"
      // 4946: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4949: bipush 0
      // 494a: bipush 19
      // 494c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 494f: ldc_w -5.0
      // 4952: ldc_w -8.75
      // 4955: ldc_w -7.25
      // 4958: ldc_w 10.0
      // 495b: ldc_w 9.0
      // 495e: ldc_w 9.0
      // 4961: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4964: dup
      // 4965: fconst_0
      // 4966: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4969: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 496c: bipush 38
      // 496e: bipush 25
      // 4970: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4973: ldc_w -3.0
      // 4976: ldc_w 0.25
      // 4979: ldc_w -7.25
      // 497c: ldc_w 6.0
      // 497f: ldc_w 4.0
      // 4982: ldc_w 4.0
      // 4985: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4988: dup
      // 4989: fconst_0
      // 498a: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 498d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4990: ldc_w 44.3196
      // 4993: ldc_w -51.1682
      // 4996: ldc_w 3.6171
      // 4999: ldc_w 0.1312
      // 499c: ldc_w -1.229
      // 499f: ldc_w 1.4936
      // 49a2: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 49a5: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 49a8: astore 242
      // 49aa: aload 2
      // 49ab: ldc_w "skeleton69"
      // 49ae: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 49b1: ldc_w -3.0
      // 49b4: ldc_w -55.1667
      // 49b7: ldc_w 8.2222
      // 49ba: ldc_w 1.5692
      // 49bd: ldc_w 0.8006
      // 49c0: ldc_w 3.0427
      // 49c3: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 49c6: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 49c9: astore 243
      // 49cb: aload 243
      // 49cd: ldc_w "left_leg8"
      // 49d0: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 49d3: bipush 38
      // 49d5: bipush 0
      // 49d6: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 49d9: ldc_w -1.5
      // 49dc: fconst_0
      // 49dd: ldc_w -1.5
      // 49e0: ldc_w 3.0
      // 49e3: ldc_w 22.0
      // 49e6: ldc_w 3.0
      // 49e9: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 49ec: dup
      // 49ed: fconst_0
      // 49ee: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 49f1: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 49f4: ldc_w 2.5
      // 49f7: ldc_w 7.1667
      // 49fa: ldc_w 1.2778
      // 49fd: ldc_w 0.4451
      // 4a00: ldc_w -0.484
      // 4a03: ldc_w -1.0109
      // 4a06: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4a09: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 4a0c: astore 244
      // 4a0e: aload 243
      // 4a10: ldc_w "right_leg7"
      // 4a13: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4a16: bipush 38
      // 4a18: bipush 0
      // 4a19: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4a1c: ldc_w -1.5
      // 4a1f: fconst_0
      // 4a20: ldc_w -1.5
      // 4a23: ldc_w 3.0
      // 4a26: ldc_w 22.0
      // 4a29: ldc_w 3.0
      // 4a2c: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4a2f: dup
      // 4a30: fconst_0
      // 4a31: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4a34: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4a37: ldc_w -2.5
      // 4a3a: ldc_w 7.1667
      // 4a3d: ldc_w 1.2778
      // 4a40: ldc_w 0.3288
      // 4a43: ldc_w 0.1509
      // 4a46: ldc_w 0.0825
      // 4a49: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4a4c: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 4a4f: astore 245
      // 4a51: aload 243
      // 4a53: ldc_w "torso69"
      // 4a56: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4a59: fconst_0
      // 4a5a: ldc_w 7.15
      // 4a5d: ldc_w 1.7778
      // 4a60: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4a63: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 4a66: astore 246
      // 4a68: aload 246
      // 4a6a: ldc_w "cube_r5"
      // 4a6d: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4a70: bipush 0
      // 4a71: bipush 0
      // 4a72: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4a75: ldc_w -6.0036
      // 4a78: ldc_w -10.5116
      // 4a7b: ldc_w -4.1242
      // 4a7e: ldc_w 12.0
      // 4a81: ldc_w 12.0
      // 4a84: ldc_w 7.0
      // 4a87: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4a8a: dup
      // 4a8b: fconst_0
      // 4a8c: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4a8f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4a92: bipush 0
      // 4a93: bipush 37
      // 4a95: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4a98: ldc_w -5.0036
      // 4a9b: ldc_w 1.4884
      // 4a9e: ldc_w -2.1242
      // 4aa1: ldc_w 10.0
      // 4aa4: ldc_w 6.0
      // 4aa7: ldc_w 5.0
      // 4aaa: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4aad: dup
      // 4aae: fconst_0
      // 4aaf: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4ab2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4ab5: bipush 30
      // 4ab7: bipush 37
      // 4ab9: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4abc: ldc_w -2.0036
      // 4abf: ldc_w -14.5116
      // 4ac2: ldc_w -0.1242
      // 4ac5: ldc_w 4.0
      // 4ac8: ldc_w 22.0
      // 4acb: ldc_w 3.0
      // 4ace: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4ad1: dup
      // 4ad2: fconst_0
      // 4ad3: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4ad6: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4ad9: ldc_w 0.0036
      // 4adc: ldc_w -7.4717
      // 4adf: ldc_w -1.8758
      // 4ae2: fconst_0
      // 4ae3: ldc_w -0.3054
      // 4ae6: fconst_0
      // 4ae7: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4aea: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 4aed: astore 247
      // 4aef: aload 246
      // 4af1: ldc_w "head42"
      // 4af4: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4af7: bipush 0
      // 4af8: bipush 19
      // 4afa: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4afd: ldc_w -5.0
      // 4b00: ldc_w -8.75
      // 4b03: ldc_w -7.25
      // 4b06: ldc_w 10.0
      // 4b09: ldc_w 9.0
      // 4b0c: ldc_w 9.0
      // 4b0f: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4b12: dup
      // 4b13: fconst_0
      // 4b14: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4b17: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4b1a: bipush 38
      // 4b1c: bipush 25
      // 4b1e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4b21: ldc_w -3.0
      // 4b24: ldc_w 0.25
      // 4b27: ldc_w -7.25
      // 4b2a: ldc_w 6.0
      // 4b2d: ldc_w 4.0
      // 4b30: ldc_w 4.0
      // 4b33: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4b36: dup
      // 4b37: fconst_0
      // 4b38: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4b3b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4b3e: fconst_0
      // 4b3f: ldc_w -22.2333
      // 4b42: ldc_w -0.75
      // 4b45: ldc_w -0.829
      // 4b48: fconst_0
      // 4b49: fconst_0
      // 4b4a: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4b4d: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 4b50: astore 248
      // 4b52: aload 246
      // 4b54: ldc_w "right_arm30"
      // 4b57: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4b5a: bipush 38
      // 4b5c: bipush 0
      // 4b5d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4b60: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4b63: ldc_w -1.5
      // 4b66: ldc_w -3.0
      // 4b69: ldc_w -1.5
      // 4b6c: ldc_w 3.0
      // 4b6f: ldc_w 22.0
      // 4b72: ldc_w 3.0
      // 4b75: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4b78: dup
      // 4b79: fconst_0
      // 4b7a: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4b7d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4b80: bipush 0
      // 4b81: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4b84: ldc_w -8.5452
      // 4b87: ldc_w -14.9768
      // 4b8a: ldc_w -3.4846
      // 4b8d: ldc_w 0.4363
      // 4b90: fconst_0
      // 4b91: ldc_w 0.5672
      // 4b94: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4b97: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 4b9a: astore 249
      // 4b9c: aload 246
      // 4b9e: ldc_w "left_arm28"
      // 4ba1: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4ba4: bipush 38
      // 4ba6: bipush 0
      // 4ba7: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4baa: ldc_w -1.5
      // 4bad: ldc_w -3.0
      // 4bb0: ldc_w -1.5
      // 4bb3: ldc_w 3.0
      // 4bb6: ldc_w 22.0
      // 4bb9: ldc_w 3.0
      // 4bbc: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4bbf: dup
      // 4bc0: fconst_0
      // 4bc1: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4bc4: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4bc7: ldc_w 10.7144
      // 4bca: ldc_w -17.0868
      // 4bcd: ldc_w -2.4949
      // 4bd0: ldc_w 0.3015
      // 4bd3: ldc_w -0.5482
      // 4bd6: ldc_w -1.6698
      // 4bd9: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4bdc: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 4bdf: astore 250
      // 4be1: aload 2
      // 4be2: ldc_w "skeleton51"
      // 4be5: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4be8: ldc_w -44.0
      // 4beb: ldc_w -3.1667
      // 4bee: ldc_w 30.2222
      // 4bf1: ldc_w 0.3905
      // 4bf4: ldc_w -0.8515
      // 4bf7: ldc_w 0.5383
      // 4bfa: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4bfd: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 4c00: astore 251
      // 4c02: aload 251
      // 4c04: ldc_w "torso51"
      // 4c07: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4c0a: bipush 30
      // 4c0c: bipush 37
      // 4c0e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4c11: ldc_w -2.0
      // 4c14: ldc_w -21.9833
      // 4c17: ldc_w -2.0
      // 4c1a: ldc_w 4.0
      // 4c1d: ldc_w 22.0
      // 4c20: ldc_w 3.0
      // 4c23: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4c26: dup
      // 4c27: fconst_0
      // 4c28: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4c2b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4c2e: bipush 0
      // 4c2f: bipush 37
      // 4c31: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4c34: ldc_w -5.0
      // 4c37: ldc_w -5.9833
      // 4c3a: ldc_w -4.0
      // 4c3d: ldc_w 10.0
      // 4c40: ldc_w 6.0
      // 4c43: ldc_w 5.0
      // 4c46: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4c49: dup
      // 4c4a: fconst_0
      // 4c4b: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4c4e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4c51: bipush 0
      // 4c52: bipush 0
      // 4c53: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4c56: ldc_w -6.0
      // 4c59: ldc_w -17.9833
      // 4c5c: ldc_w -6.0
      // 4c5f: ldc_w 12.0
      // 4c62: ldc_w 12.0
      // 4c65: ldc_w 7.0
      // 4c68: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4c6b: dup
      // 4c6c: fconst_0
      // 4c6d: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4c70: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4c73: ldc_w -0.866
      // 4c76: ldc_w 7.6481
      // 4c79: ldc_w 1.8214
      // 4c7c: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4c7f: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 4c82: astore 252
      // 4c84: aload 2
      // 4c85: ldc_w "skeleton71"
      // 4c88: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4c8b: ldc_w 41.0
      // 4c8e: ldc_w -50.1667
      // 4c91: ldc_w 23.2222
      // 4c94: ldc_w 1.8567
      // 4c97: ldc_w 0.6496
      // 4c9a: ldc_w 2.9163
      // 4c9d: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4ca0: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 4ca3: astore 253
      // 4ca5: aload 253
      // 4ca7: ldc_w "torso71"
      // 4caa: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4cad: bipush 30
      // 4caf: bipush 37
      // 4cb1: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4cb4: ldc_w -2.0
      // 4cb7: ldc_w -21.9833
      // 4cba: ldc_w -2.0
      // 4cbd: ldc_w 4.0
      // 4cc0: ldc_w 22.0
      // 4cc3: ldc_w 3.0
      // 4cc6: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4cc9: dup
      // 4cca: fconst_0
      // 4ccb: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4cce: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4cd1: bipush 0
      // 4cd2: bipush 37
      // 4cd4: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4cd7: ldc_w -5.0
      // 4cda: ldc_w -5.9833
      // 4cdd: ldc_w -4.0
      // 4ce0: ldc_w 10.0
      // 4ce3: ldc_w 6.0
      // 4ce6: ldc_w 5.0
      // 4ce9: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4cec: dup
      // 4ced: fconst_0
      // 4cee: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4cf1: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4cf4: bipush 0
      // 4cf5: bipush 0
      // 4cf6: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4cf9: ldc_w -6.0
      // 4cfc: ldc_w -17.9833
      // 4cff: ldc_w -6.0
      // 4d02: ldc_w 12.0
      // 4d05: ldc_w 12.0
      // 4d08: ldc_w 7.0
      // 4d0b: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4d0e: dup
      // 4d0f: fconst_0
      // 4d10: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4d13: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4d16: ldc_w -0.6997
      // 4d19: ldc_w 3.2657
      // 4d1c: ldc_w -0.4418
      // 4d1f: ldc_w -0.1745
      // 4d22: fconst_0
      // 4d23: fconst_0
      // 4d24: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4d27: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 4d2a: astore 254
      // 4d2c: aload 2
      // 4d2d: ldc_w "skeleton72"
      // 4d30: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4d33: ldc_w 34.0
      // 4d36: ldc_w -60.1667
      // 4d39: ldc_w 16.2222
      // 4d3c: ldc_w 1.5692
      // 4d3f: ldc_w 0.8006
      // 4d42: ldc_w 3.0427
      // 4d45: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4d48: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 4d4b: astore 255
      // 4d4d: aload 255
      // 4d4f: ldc_w "left_leg9"
      // 4d52: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4d55: bipush 38
      // 4d57: bipush 0
      // 4d58: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4d5b: ldc_w -1.5
      // 4d5e: fconst_0
      // 4d5f: ldc_w -1.5
      // 4d62: ldc_w 3.0
      // 4d65: ldc_w 22.0
      // 4d68: ldc_w 3.0
      // 4d6b: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4d6e: dup
      // 4d6f: fconst_0
      // 4d70: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4d73: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4d76: ldc_w 2.5
      // 4d79: ldc_w 7.1667
      // 4d7c: ldc_w 1.2778
      // 4d7f: ldc_w 0.3106
      // 4d82: ldc_w -0.7222
      // 4d85: ldc_w 0.058
      // 4d88: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4d8b: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 4d8e: wide astore 256
      // 4d92: aload 255
      // 4d94: ldc_w "right_leg8"
      // 4d97: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4d9a: bipush 38
      // 4d9c: bipush 0
      // 4d9d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4da0: ldc_w -1.5
      // 4da3: fconst_0
      // 4da4: ldc_w -1.5
      // 4da7: ldc_w 3.0
      // 4daa: ldc_w 22.0
      // 4dad: ldc_w 3.0
      // 4db0: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4db3: dup
      // 4db4: fconst_0
      // 4db5: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4db8: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4dbb: ldc_w -2.5
      // 4dbe: ldc_w 7.1667
      // 4dc1: ldc_w 1.2778
      // 4dc4: ldc_w 0.3234
      // 4dc7: ldc_w -0.1624
      // 4dca: ldc_w 0.9472
      // 4dcd: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4dd0: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 4dd3: wide astore 257
      // 4dd7: aload 255
      // 4dd9: ldc_w "torso72"
      // 4ddc: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4ddf: fconst_0
      // 4de0: ldc_w 7.15
      // 4de3: ldc_w 1.7778
      // 4de6: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4de9: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 4dec: wide astore 258
      // 4df0: wide aload 258
      // 4df4: ldc_w "cube_r6"
      // 4df7: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4dfa: bipush 0
      // 4dfb: bipush 0
      // 4dfc: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4dff: ldc_w -6.0036
      // 4e02: ldc_w -10.5116
      // 4e05: ldc_w -4.1242
      // 4e08: ldc_w 12.0
      // 4e0b: ldc_w 12.0
      // 4e0e: ldc_w 7.0
      // 4e11: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4e14: dup
      // 4e15: fconst_0
      // 4e16: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4e19: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4e1c: bipush 0
      // 4e1d: bipush 37
      // 4e1f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4e22: ldc_w -5.0036
      // 4e25: ldc_w 1.4884
      // 4e28: ldc_w -2.1242
      // 4e2b: ldc_w 10.0
      // 4e2e: ldc_w 6.0
      // 4e31: ldc_w 5.0
      // 4e34: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4e37: dup
      // 4e38: fconst_0
      // 4e39: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4e3c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4e3f: bipush 30
      // 4e41: bipush 37
      // 4e43: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4e46: ldc_w -2.0036
      // 4e49: ldc_w -14.5116
      // 4e4c: ldc_w -0.1242
      // 4e4f: ldc_w 4.0
      // 4e52: ldc_w 22.0
      // 4e55: ldc_w 3.0
      // 4e58: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4e5b: dup
      // 4e5c: fconst_0
      // 4e5d: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4e60: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4e63: ldc_w 0.0036
      // 4e66: ldc_w -7.4717
      // 4e69: ldc_w -1.8758
      // 4e6c: ldc_w -0.0411
      // 4e6f: ldc_w -0.3027
      // 4e72: ldc_w 0.1372
      // 4e75: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4e78: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 4e7b: wide astore 259
      // 4e7f: wide aload 258
      // 4e83: ldc_w "head43"
      // 4e86: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4e89: bipush 0
      // 4e8a: bipush 19
      // 4e8c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4e8f: ldc_w -5.0
      // 4e92: ldc_w -8.75
      // 4e95: ldc_w -7.25
      // 4e98: ldc_w 10.0
      // 4e9b: ldc_w 9.0
      // 4e9e: ldc_w 9.0
      // 4ea1: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4ea4: dup
      // 4ea5: fconst_0
      // 4ea6: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4ea9: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4eac: bipush 38
      // 4eae: bipush 25
      // 4eb0: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4eb3: ldc_w -3.0
      // 4eb6: ldc_w 0.25
      // 4eb9: ldc_w -7.25
      // 4ebc: ldc_w 6.0
      // 4ebf: ldc_w 4.0
      // 4ec2: ldc_w 4.0
      // 4ec5: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4ec8: dup
      // 4ec9: fconst_0
      // 4eca: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4ecd: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4ed0: fconst_0
      // 4ed1: ldc_w -22.2333
      // 4ed4: ldc_w -0.75
      // 4ed7: ldc_w -0.829
      // 4eda: fconst_0
      // 4edb: fconst_0
      // 4edc: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4edf: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 4ee2: wide astore 260
      // 4ee6: wide aload 258
      // 4eea: ldc_w "right_arm32"
      // 4eed: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4ef0: bipush 38
      // 4ef2: bipush 0
      // 4ef3: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4ef6: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4ef9: ldc_w -1.5
      // 4efc: ldc_w -3.0
      // 4eff: ldc_w -1.5
      // 4f02: ldc_w 3.0
      // 4f05: ldc_w 22.0
      // 4f08: ldc_w 3.0
      // 4f0b: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4f0e: dup
      // 4f0f: fconst_0
      // 4f10: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4f13: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4f16: bipush 0
      // 4f17: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4f1a: ldc_w -8.5452
      // 4f1d: ldc_w -14.9768
      // 4f20: ldc_w -3.4845
      // 4f23: ldc_w 0.4363
      // 4f26: fconst_0
      // 4f27: ldc_w 0.5672
      // 4f2a: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4f2d: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 4f30: wide astore 261
      // 4f34: aload 2
      // 4f35: ldc_w "head44"
      // 4f38: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4f3b: bipush 0
      // 4f3c: bipush 19
      // 4f3e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4f41: ldc_w -5.0
      // 4f44: ldc_w -8.75
      // 4f47: ldc_w -7.25
      // 4f4a: ldc_w 10.0
      // 4f4d: ldc_w 9.0
      // 4f50: ldc_w 9.0
      // 4f53: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4f56: dup
      // 4f57: fconst_0
      // 4f58: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4f5b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4f5e: bipush 38
      // 4f60: bipush 25
      // 4f62: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4f65: ldc_w -3.0
      // 4f68: ldc_w 0.25
      // 4f6b: ldc_w -7.25
      // 4f6e: ldc_w 6.0
      // 4f71: ldc_w 4.0
      // 4f74: ldc_w 4.0
      // 4f77: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4f7a: dup
      // 4f7b: fconst_0
      // 4f7c: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4f7f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4f82: ldc_w -12.6804
      // 4f85: ldc_w -47.1682
      // 4f88: ldc_w -10.3829
      // 4f8b: ldc_w -3.0422
      // 4f8e: ldc_w -0.9456
      // 4f91: ldc_w -1.893
      // 4f94: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4f97: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 4f9a: wide astore 262
      // 4f9e: aload 2
      // 4f9f: ldc_w "skeleton73"
      // 4fa2: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4fa5: ldc_w 7.0
      // 4fa8: ldc_w -44.1667
      // 4fab: ldc_w 27.2222
      // 4fae: ldc_w 2.1197
      // 4fb1: ldc_w -0.4178
      // 4fb4: ldc_w 2.7264
      // 4fb7: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 4fba: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 4fbd: wide astore 263
      // 4fc1: wide aload 263
      // 4fc5: ldc_w "torso73"
      // 4fc8: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4fcb: bipush 30
      // 4fcd: bipush 37
      // 4fcf: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4fd2: ldc_w -2.0
      // 4fd5: ldc_w -21.9833
      // 4fd8: ldc_w -2.0
      // 4fdb: ldc_w 4.0
      // 4fde: ldc_w 22.0
      // 4fe1: ldc_w 3.0
      // 4fe4: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 4fe7: dup
      // 4fe8: fconst_0
      // 4fe9: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 4fec: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4fef: bipush 0
      // 4ff0: bipush 37
      // 4ff2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 4ff5: ldc_w -5.0
      // 4ff8: ldc_w -5.9833
      // 4ffb: ldc_w -4.0
      // 4ffe: ldc_w 10.0
      // 5001: ldc_w 6.0
      // 5004: ldc_w 5.0
      // 5007: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 500a: dup
      // 500b: fconst_0
      // 500c: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 500f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5012: bipush 0
      // 5013: bipush 0
      // 5014: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5017: ldc_w -6.0
      // 501a: ldc_w -17.9833
      // 501d: ldc_w -6.0
      // 5020: ldc_w 12.0
      // 5023: ldc_w 12.0
      // 5026: ldc_w 7.0
      // 5029: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 502c: dup
      // 502d: fconst_0
      // 502e: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 5031: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5034: ldc_w -0.6997
      // 5037: ldc_w 3.2657
      // 503a: ldc_w -0.4418
      // 503d: ldc_w -0.1745
      // 5040: fconst_0
      // 5041: fconst_0
      // 5042: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 5045: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 5048: wide astore 264
      // 504c: wide aload 264
      // 5050: ldc_w "right_arm33"
      // 5053: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5056: bipush 38
      // 5058: bipush 0
      // 5059: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 505c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 505f: ldc_w -1.5
      // 5062: ldc_w -3.0
      // 5065: ldc_w -1.5
      // 5068: ldc_w 3.0
      // 506b: ldc_w 22.0
      // 506e: ldc_w 3.0
      // 5071: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 5074: dup
      // 5075: fconst_0
      // 5076: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 5079: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 507c: bipush 0
      // 507d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5080: ldc_w -7.2758
      // 5083: ldc_w -18.6107
      // 5086: ldc_w 1.9067
      // 5089: ldc_w 0.6776
      // 508c: ldc_w -0.274
      // 508f: ldc_w 2.7093
      // 5092: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 5095: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 5098: wide astore 265
      // 509c: aload 2
      // 509d: ldc_w "head45"
      // 50a0: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 50a3: bipush 0
      // 50a4: bipush 19
      // 50a6: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 50a9: ldc_w -5.0
      // 50ac: ldc_w -8.75
      // 50af: ldc_w -7.25
      // 50b2: ldc_w 10.0
      // 50b5: ldc_w 9.0
      // 50b8: ldc_w 9.0
      // 50bb: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 50be: dup
      // 50bf: fconst_0
      // 50c0: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 50c3: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 50c6: bipush 38
      // 50c8: bipush 25
      // 50ca: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 50cd: ldc_w -3.0
      // 50d0: ldc_w 0.25
      // 50d3: ldc_w -7.25
      // 50d6: ldc_w 6.0
      // 50d9: ldc_w 4.0
      // 50dc: ldc_w 4.0
      // 50df: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 50e2: dup
      // 50e3: fconst_0
      // 50e4: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 50e7: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 50ea: ldc_w -9.6804
      // 50ed: ldc_w -44.1682
      // 50f0: ldc_w 5.6171
      // 50f3: ldc_w -1.1902
      // 50f6: ldc_w -0.9231
      // 50f9: ldc_w 2.1334
      // 50fc: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 50ff: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 5102: wide astore 266
      // 5106: aload 2
      // 5107: ldc_w "skeleton74"
      // 510a: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 510d: fconst_2
      // 510e: ldc_w -7.1667
      // 5111: ldc_w -17.7778
      // 5114: ldc_w 0.3927
      // 5117: ldc_w 0.2182
      // 511a: fconst_0
      // 511b: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 511e: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 5121: wide astore 267
      // 5125: wide aload 267
      // 5129: ldc_w "torso74"
      // 512c: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 512f: bipush 30
      // 5131: bipush 37
      // 5133: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5136: ldc_w -2.0
      // 5139: ldc_w -21.9833
      // 513c: ldc_w -2.0
      // 513f: ldc_w 4.0
      // 5142: ldc_w 22.0
      // 5145: ldc_w 3.0
      // 5148: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 514b: dup
      // 514c: fconst_0
      // 514d: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 5150: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5153: bipush 0
      // 5154: bipush 37
      // 5156: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5159: ldc_w -5.0
      // 515c: ldc_w -5.9833
      // 515f: ldc_w -4.0
      // 5162: ldc_w 10.0
      // 5165: ldc_w 6.0
      // 5168: ldc_w 5.0
      // 516b: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 516e: dup
      // 516f: fconst_0
      // 5170: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 5173: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5176: bipush 0
      // 5177: bipush 0
      // 5178: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 517b: ldc_w -6.0
      // 517e: ldc_w -17.9833
      // 5181: ldc_w -6.0
      // 5184: ldc_w 12.0
      // 5187: ldc_w 12.0
      // 518a: ldc_w 7.0
      // 518d: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 5190: dup
      // 5191: fconst_0
      // 5192: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 5195: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5198: fconst_0
      // 5199: ldc_w 7.15
      // 519c: ldc_w 1.7778
      // 519f: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 51a2: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 51a5: wide astore 268
      // 51a9: wide aload 268
      // 51ad: ldc_w "head46"
      // 51b0: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 51b3: bipush 0
      // 51b4: bipush 19
      // 51b6: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 51b9: ldc_w -5.0
      // 51bc: ldc_w -8.75
      // 51bf: ldc_w -7.25
      // 51c2: ldc_w 10.0
      // 51c5: ldc_w 9.0
      // 51c8: ldc_w 9.0
      // 51cb: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 51ce: dup
      // 51cf: fconst_0
      // 51d0: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 51d3: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 51d6: bipush 38
      // 51d8: bipush 25
      // 51da: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 51dd: ldc_w -3.0
      // 51e0: ldc_w 0.25
      // 51e3: ldc_w -7.25
      // 51e6: ldc_w 6.0
      // 51e9: ldc_w 4.0
      // 51ec: ldc_w 4.0
      // 51ef: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 51f2: dup
      // 51f3: fconst_0
      // 51f4: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 51f7: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 51fa: fconst_0
      // 51fb: ldc_w -23.0767
      // 51fe: ldc_w -1.2873
      // 5201: ldc_w -1.0472
      // 5204: ldc_w -0.2291
      // 5207: ldc_w 0.3747
      // 520a: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 520d: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 5210: wide astore 269
      // 5214: wide aload 268
      // 5218: ldc_w "right_arm34"
      // 521b: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 521e: bipush 38
      // 5220: bipush 0
      // 5221: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5224: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5227: ldc_w -1.5
      // 522a: ldc_w -3.0
      // 522d: ldc_w -1.5
      // 5230: ldc_w 3.0
      // 5233: ldc_w 22.0
      // 5236: ldc_w 3.0
      // 5239: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 523c: dup
      // 523d: fconst_0
      // 523e: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 5241: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5244: bipush 0
      // 5245: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5248: ldc_w -6.7929
      // 524b: ldc_w -16.6904
      // 524e: ldc_w 0.2654
      // 5251: ldc_w -0.3927
      // 5254: fconst_0
      // 5255: ldc_w 0.3927
      // 5258: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 525b: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 525e: wide astore 270
      // 5262: wide aload 268
      // 5266: ldc_w "left_arm29"
      // 5269: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 526c: bipush 38
      // 526e: bipush 0
      // 526f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5272: ldc_w -1.5
      // 5275: ldc_w -3.0
      // 5278: ldc_w -1.5
      // 527b: ldc_w 3.0
      // 527e: ldc_w 22.0
      // 5281: ldc_w 3.0
      // 5284: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 5287: dup
      // 5288: fconst_0
      // 5289: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 528c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 528f: ldc_w 7.5
      // 5292: ldc_w -14.9833
      // 5295: ldc_w -0.5
      // 5298: ldc_w -0.9599
      // 529b: fconst_0
      // 529c: ldc_w -0.3927
      // 529f: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 52a2: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 52a5: wide astore 271
      // 52a9: aload 2
      // 52aa: ldc_w "skeleton75"
      // 52ad: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 52b0: ldc_w 24.0
      // 52b3: ldc_w -13.1667
      // 52b6: ldc_w -8.7778
      // 52b9: ldc_w 0.3927
      // 52bc: ldc_w -0.7854
      // 52bf: fconst_0
      // 52c0: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 52c3: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 52c6: wide astore 272
      // 52ca: wide aload 272
      // 52ce: ldc_w "torso75"
      // 52d1: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 52d4: bipush 30
      // 52d6: bipush 37
      // 52d8: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 52db: ldc_w -2.0
      // 52de: ldc_w -21.9833
      // 52e1: ldc_w -2.0
      // 52e4: ldc_w 4.0
      // 52e7: ldc_w 22.0
      // 52ea: ldc_w 3.0
      // 52ed: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 52f0: dup
      // 52f1: fconst_0
      // 52f2: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 52f5: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 52f8: bipush 0
      // 52f9: bipush 37
      // 52fb: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 52fe: ldc_w -5.0
      // 5301: ldc_w -5.9833
      // 5304: ldc_w -4.0
      // 5307: ldc_w 10.0
      // 530a: ldc_w 6.0
      // 530d: ldc_w 5.0
      // 5310: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 5313: dup
      // 5314: fconst_0
      // 5315: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 5318: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 531b: bipush 0
      // 531c: bipush 0
      // 531d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5320: ldc_w -6.0
      // 5323: ldc_w -17.9833
      // 5326: ldc_w -6.0
      // 5329: ldc_w 12.0
      // 532c: ldc_w 12.0
      // 532f: ldc_w 7.0
      // 5332: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 5335: dup
      // 5336: fconst_0
      // 5337: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 533a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 533d: fconst_0
      // 533e: ldc_w 7.15
      // 5341: ldc_w 1.7778
      // 5344: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 5347: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 534a: wide astore 273
      // 534e: wide aload 273
      // 5352: ldc_w "head47"
      // 5355: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5358: bipush 0
      // 5359: bipush 19
      // 535b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 535e: ldc_w -5.0
      // 5361: ldc_w -8.75
      // 5364: ldc_w -7.25
      // 5367: ldc_w 10.0
      // 536a: ldc_w 9.0
      // 536d: ldc_w 9.0
      // 5370: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 5373: dup
      // 5374: fconst_0
      // 5375: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 5378: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 537b: bipush 38
      // 537d: bipush 25
      // 537f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5382: ldc_w -3.0
      // 5385: ldc_w 0.25
      // 5388: ldc_w -7.25
      // 538b: ldc_w 6.0
      // 538e: ldc_w 4.0
      // 5391: ldc_w 4.0
      // 5394: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 5397: dup
      // 5398: fconst_0
      // 5399: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 539c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 539f: fconst_0
      // 53a0: ldc_w -23.0767
      // 53a3: ldc_w -1.2873
      // 53a6: ldc_w -1.0472
      // 53a9: ldc_w -0.2291
      // 53ac: ldc_w 0.3747
      // 53af: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 53b2: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 53b5: wide astore 274
      // 53b9: wide aload 273
      // 53bd: ldc_w "right_arm35"
      // 53c0: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 53c3: bipush 38
      // 53c5: bipush 0
      // 53c6: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 53c9: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 53cc: ldc_w -1.5
      // 53cf: ldc_w -3.0
      // 53d2: ldc_w -1.5
      // 53d5: ldc_w 3.0
      // 53d8: ldc_w 22.0
      // 53db: ldc_w 3.0
      // 53de: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 53e1: dup
      // 53e2: fconst_0
      // 53e3: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 53e6: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 53e9: bipush 0
      // 53ea: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 53ed: ldc_w -6.7929
      // 53f0: ldc_w -16.6904
      // 53f3: ldc_w 0.2654
      // 53f6: ldc_w -0.3927
      // 53f9: fconst_0
      // 53fa: ldc_w 0.3927
      // 53fd: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 5400: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 5403: wide astore 275
      // 5407: wide aload 273
      // 540b: ldc_w "left_arm30"
      // 540e: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5411: bipush 38
      // 5413: bipush 0
      // 5414: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5417: ldc_w -1.5
      // 541a: ldc_w -3.0
      // 541d: ldc_w -1.5
      // 5420: ldc_w 3.0
      // 5423: ldc_w 22.0
      // 5426: ldc_w 3.0
      // 5429: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 542c: dup
      // 542d: fconst_0
      // 542e: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 5431: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5434: ldc_w 7.5
      // 5437: ldc_w -14.9833
      // 543a: ldc_w -0.5
      // 543d: ldc_w -0.9599
      // 5440: fconst_0
      // 5441: ldc_w -0.3927
      // 5444: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 5447: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 544a: wide astore 276
      // 544e: aload 2
      // 544f: ldc_w "skeleton76"
      // 5452: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5455: ldc_w -26.0
      // 5458: ldc_w -7.1667
      // 545b: ldc_w 3.2222
      // 545e: ldc_w -2.7489
      // 5461: ldc_w 0.829
      // 5464: ldc_w -3.1416
      // 5467: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 546a: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 546d: wide astore 277
      // 5471: wide aload 277
      // 5475: ldc_w "torso76"
      // 5478: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 547b: bipush 30
      // 547d: bipush 37
      // 547f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5482: ldc_w -2.0
      // 5485: ldc_w -21.9833
      // 5488: ldc_w -2.0
      // 548b: ldc_w 4.0
      // 548e: ldc_w 22.0
      // 5491: ldc_w 3.0
      // 5494: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 5497: dup
      // 5498: fconst_0
      // 5499: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 549c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 549f: bipush 0
      // 54a0: bipush 37
      // 54a2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 54a5: ldc_w -5.0
      // 54a8: ldc_w -5.9833
      // 54ab: ldc_w -4.0
      // 54ae: ldc_w 10.0
      // 54b1: ldc_w 6.0
      // 54b4: ldc_w 5.0
      // 54b7: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 54ba: dup
      // 54bb: fconst_0
      // 54bc: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 54bf: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 54c2: bipush 0
      // 54c3: bipush 0
      // 54c4: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 54c7: ldc_w -6.0
      // 54ca: ldc_w -17.9833
      // 54cd: ldc_w -6.0
      // 54d0: ldc_w 12.0
      // 54d3: ldc_w 12.0
      // 54d6: ldc_w 7.0
      // 54d9: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 54dc: dup
      // 54dd: fconst_0
      // 54de: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 54e1: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 54e4: fconst_0
      // 54e5: ldc_w 7.15
      // 54e8: ldc_w 1.7778
      // 54eb: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 54ee: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 54f1: wide astore 278
      // 54f5: wide aload 278
      // 54f9: ldc_w "head48"
      // 54fc: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 54ff: bipush 0
      // 5500: bipush 19
      // 5502: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5505: ldc_w -5.0
      // 5508: ldc_w -8.75
      // 550b: ldc_w -7.25
      // 550e: ldc_w 10.0
      // 5511: ldc_w 9.0
      // 5514: ldc_w 9.0
      // 5517: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 551a: dup
      // 551b: fconst_0
      // 551c: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 551f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5522: bipush 38
      // 5524: bipush 25
      // 5526: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5529: ldc_w -3.0
      // 552c: ldc_w 0.25
      // 552f: ldc_w -7.25
      // 5532: ldc_w 6.0
      // 5535: ldc_w 4.0
      // 5538: ldc_w 4.0
      // 553b: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 553e: dup
      // 553f: fconst_0
      // 5540: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 5543: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5546: fconst_0
      // 5547: ldc_w -23.0767
      // 554a: ldc_w -1.2873
      // 554d: ldc_w -1.0472
      // 5550: ldc_w -0.2291
      // 5553: ldc_w 0.3747
      // 5556: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 5559: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 555c: wide astore 279
      // 5560: wide aload 278
      // 5564: ldc_w "right_arm36"
      // 5567: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 556a: bipush 38
      // 556c: bipush 0
      // 556d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5570: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5573: ldc_w -1.5
      // 5576: ldc_w -3.0
      // 5579: ldc_w -1.5
      // 557c: ldc_w 3.0
      // 557f: ldc_w 22.0
      // 5582: ldc_w 3.0
      // 5585: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 5588: dup
      // 5589: fconst_0
      // 558a: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 558d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5590: bipush 0
      // 5591: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5594: ldc_w -6.7929
      // 5597: ldc_w -16.6904
      // 559a: ldc_w 0.2654
      // 559d: ldc_w -0.3927
      // 55a0: fconst_0
      // 55a1: ldc_w 0.3927
      // 55a4: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 55a7: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 55aa: wide astore 280
      // 55ae: wide aload 278
      // 55b2: ldc_w "left_arm31"
      // 55b5: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 55b8: bipush 38
      // 55ba: bipush 0
      // 55bb: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 55be: ldc_w -1.5
      // 55c1: ldc_w -3.0
      // 55c4: ldc_w -1.5
      // 55c7: ldc_w 3.0
      // 55ca: ldc_w 22.0
      // 55cd: ldc_w 3.0
      // 55d0: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 55d3: dup
      // 55d4: fconst_0
      // 55d5: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 55d8: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 55db: ldc_w 7.5
      // 55de: ldc_w -14.9833
      // 55e1: ldc_w -0.5
      // 55e4: ldc_w -0.9599
      // 55e7: fconst_0
      // 55e8: ldc_w -0.3927
      // 55eb: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 55ee: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 55f1: wide astore 281
      // 55f5: aload 2
      // 55f6: ldc_w "skeleton77"
      // 55f9: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 55fc: fconst_1
      // 55fd: ldc_w -39.1667
      // 5600: ldc_w 5.2222
      // 5603: ldc_w 1.2752
      // 5606: ldc_w 1.4944
      // 5609: ldc_w 2.7516
      // 560c: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 560f: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 5612: wide astore 282
      // 5616: wide aload 282
      // 561a: ldc_w "left_leg10"
      // 561d: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5620: bipush 38
      // 5622: bipush 0
      // 5623: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5626: ldc_w -1.5
      // 5629: fconst_0
      // 562a: ldc_w -1.5
      // 562d: ldc_w 3.0
      // 5630: ldc_w 22.0
      // 5633: ldc_w 3.0
      // 5636: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 5639: dup
      // 563a: fconst_0
      // 563b: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 563e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5641: ldc_w 2.5
      // 5644: ldc_w 7.1667
      // 5647: ldc_w 1.2778
      // 564a: ldc_w 0.4451
      // 564d: ldc_w -0.484
      // 5650: ldc_w -1.0109
      // 5653: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 5656: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 5659: wide astore 283
      // 565d: wide aload 282
      // 5661: ldc_w "right_leg9"
      // 5664: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5667: bipush 38
      // 5669: bipush 0
      // 566a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 566d: ldc_w -1.5
      // 5670: fconst_0
      // 5671: ldc_w -1.5
      // 5674: ldc_w 3.0
      // 5677: ldc_w 22.0
      // 567a: ldc_w 3.0
      // 567d: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 5680: dup
      // 5681: fconst_0
      // 5682: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 5685: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5688: ldc_w -2.5
      // 568b: ldc_w 7.1667
      // 568e: ldc_w 1.2778
      // 5691: ldc_w 0.5236
      // 5694: fconst_0
      // 5695: ldc_w 0.3491
      // 5698: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 569b: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 569e: wide astore 284
      // 56a2: wide aload 282
      // 56a6: ldc_w "torso77"
      // 56a9: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 56ac: fconst_0
      // 56ad: ldc_w 7.15
      // 56b0: ldc_w 1.7778
      // 56b3: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 56b6: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 56b9: wide astore 285
      // 56bd: wide aload 285
      // 56c1: ldc_w "cube_r7"
      // 56c4: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 56c7: bipush 0
      // 56c8: bipush 0
      // 56c9: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 56cc: ldc_w -6.0036
      // 56cf: ldc_w -10.5116
      // 56d2: ldc_w -4.1242
      // 56d5: ldc_w 12.0
      // 56d8: ldc_w 12.0
      // 56db: ldc_w 7.0
      // 56de: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 56e1: dup
      // 56e2: fconst_0
      // 56e3: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 56e6: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 56e9: bipush 0
      // 56ea: bipush 37
      // 56ec: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 56ef: ldc_w -5.0036
      // 56f2: ldc_w 1.4884
      // 56f5: ldc_w -2.1242
      // 56f8: ldc_w 10.0
      // 56fb: ldc_w 6.0
      // 56fe: ldc_w 5.0
      // 5701: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 5704: dup
      // 5705: fconst_0
      // 5706: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 5709: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 570c: bipush 30
      // 570e: bipush 37
      // 5710: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5713: ldc_w -2.0036
      // 5716: ldc_w -14.5116
      // 5719: ldc_w -0.1242
      // 571c: ldc_w 4.0
      // 571f: ldc_w 22.0
      // 5722: ldc_w 3.0
      // 5725: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 5728: dup
      // 5729: fconst_0
      // 572a: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 572d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5730: ldc_w 0.0036
      // 5733: ldc_w -7.4717
      // 5736: ldc_w -1.8758
      // 5739: fconst_0
      // 573a: ldc_w -0.3054
      // 573d: fconst_0
      // 573e: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 5741: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 5744: wide astore 286
      // 5748: wide aload 285
      // 574c: ldc_w "head49"
      // 574f: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5752: bipush 0
      // 5753: bipush 19
      // 5755: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5758: ldc_w -5.0
      // 575b: ldc_w -8.75
      // 575e: ldc_w -7.25
      // 5761: ldc_w 10.0
      // 5764: ldc_w 9.0
      // 5767: ldc_w 9.0
      // 576a: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 576d: dup
      // 576e: fconst_0
      // 576f: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 5772: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5775: bipush 38
      // 5777: bipush 25
      // 5779: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 577c: ldc_w -3.0
      // 577f: ldc_w 0.25
      // 5782: ldc_w -7.25
      // 5785: ldc_w 6.0
      // 5788: ldc_w 4.0
      // 578b: ldc_w 4.0
      // 578e: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 5791: dup
      // 5792: fconst_0
      // 5793: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 5796: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5799: fconst_0
      // 579a: ldc_w -22.2333
      // 579d: ldc_w -0.75
      // 57a0: ldc_w -0.829
      // 57a3: fconst_0
      // 57a4: fconst_0
      // 57a5: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 57a8: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 57ab: wide astore 287
      // 57af: wide aload 285
      // 57b3: ldc_w "right_arm37"
      // 57b6: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 57b9: bipush 38
      // 57bb: bipush 0
      // 57bc: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 57bf: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 57c2: ldc_w -1.5
      // 57c5: ldc_w -3.0
      // 57c8: ldc_w -1.5
      // 57cb: ldc_w 3.0
      // 57ce: ldc_w 22.0
      // 57d1: ldc_w 3.0
      // 57d4: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 57d7: dup
      // 57d8: fconst_0
      // 57d9: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 57dc: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 57df: bipush 0
      // 57e0: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 57e3: ldc_w -8.5452
      // 57e6: ldc_w -14.9768
      // 57e9: ldc_w -3.4846
      // 57ec: fconst_0
      // 57ed: fconst_0
      // 57ee: ldc_w 0.5672
      // 57f1: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 57f4: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 57f7: wide astore 288
      // 57fb: wide aload 285
      // 57ff: ldc_w "left_arm32"
      // 5802: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5805: bipush 38
      // 5807: bipush 0
      // 5808: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 580b: ldc_w -1.5
      // 580e: ldc_w -3.0
      // 5811: ldc_w -1.5
      // 5814: ldc_w 3.0
      // 5817: ldc_w 22.0
      // 581a: ldc_w 3.0
      // 581d: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 5820: dup
      // 5821: fconst_0
      // 5822: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 5825: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5828: ldc_w 9.4164
      // 582b: ldc_w -15.5557
      // 582e: ldc_w -0.5022
      // 5831: ldc_w 0.1841
      // 5834: ldc_w -0.7769
      // 5837: ldc_w -1.0025
      // 583a: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 583d: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 5840: wide astore 289
      // 5844: aload 2
      // 5845: ldc_w "skeleton70"
      // 5848: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 584b: ldc_w 17.0
      // 584e: ldc_w -58.1667
      // 5851: ldc_w 6.2222
      // 5854: ldc_w -1.3716
      // 5857: ldc_w 0.2848
      // 585a: ldc_w -0.0263
      // 585d: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 5860: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 5863: wide astore 290
      // 5867: wide aload 290
      // 586b: ldc_w "torso70"
      // 586e: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5871: bipush 30
      // 5873: bipush 37
      // 5875: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5878: ldc_w -2.0
      // 587b: ldc_w -21.9833
      // 587e: ldc_w -2.0
      // 5881: ldc_w 4.0
      // 5884: ldc_w 22.0
      // 5887: ldc_w 3.0
      // 588a: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 588d: dup
      // 588e: fconst_0
      // 588f: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 5892: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5895: bipush 0
      // 5896: bipush 37
      // 5898: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 589b: ldc_w -5.0
      // 589e: ldc_w -5.9833
      // 58a1: ldc_w -4.0
      // 58a4: ldc_w 10.0
      // 58a7: ldc_w 6.0
      // 58aa: ldc_w 5.0
      // 58ad: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 58b0: dup
      // 58b1: fconst_0
      // 58b2: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 58b5: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 58b8: bipush 0
      // 58b9: bipush 0
      // 58ba: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 58bd: ldc_w -6.0
      // 58c0: ldc_w -17.9833
      // 58c3: ldc_w -6.0
      // 58c6: ldc_w 12.0
      // 58c9: ldc_w 12.0
      // 58cc: ldc_w 7.0
      // 58cf: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 58d2: dup
      // 58d3: fconst_0
      // 58d4: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 58d7: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 58da: ldc_w -0.6997
      // 58dd: ldc_w 3.2657
      // 58e0: ldc_w -0.4418
      // 58e3: ldc_w -0.1745
      // 58e6: fconst_0
      // 58e7: fconst_0
      // 58e8: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 58eb: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 58ee: wide astore 291
      // 58f2: wide aload 291
      // 58f6: ldc_w "right_arm31"
      // 58f9: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 58fc: bipush 38
      // 58fe: bipush 0
      // 58ff: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5902: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5905: ldc_w -1.5
      // 5908: ldc_w -3.0
      // 590b: ldc_w -1.5
      // 590e: ldc_w 3.0
      // 5911: ldc_w 22.0
      // 5914: ldc_w 3.0
      // 5917: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 591a: dup
      // 591b: fconst_0
      // 591c: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 591f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5922: bipush 0
      // 5923: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5926: ldc_w -7.2758
      // 5929: ldc_w -18.6107
      // 592c: ldc_w 1.9067
      // 592f: ldc_w 0.5031
      // 5932: ldc_w -0.274
      // 5935: ldc_w 2.7093
      // 5938: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 593b: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 593e: wide astore 292
      // 5942: aload 2
      // 5943: ldc_w "skeleton78"
      // 5946: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5949: ldc_w 30.0
      // 594c: ldc_w -39.1667
      // 594f: ldc_w 16.2222
      // 5952: ldc_w 1.645
      // 5955: ldc_w 0.5836
      // 5958: ldc_w 3.1068
      // 595b: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 595e: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 5961: wide astore 293
      // 5965: wide aload 293
      // 5969: ldc_w "torso78"
      // 596c: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 596f: fconst_0
      // 5970: ldc_w 7.15
      // 5973: ldc_w 1.7778
      // 5976: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 5979: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 597c: wide astore 294
      // 5980: wide aload 294
      // 5984: ldc_w "cube_r8"
      // 5987: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 598a: bipush 0
      // 598b: bipush 0
      // 598c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 598f: ldc_w -6.0036
      // 5992: ldc_w -10.5116
      // 5995: ldc_w -4.1242
      // 5998: ldc_w 12.0
      // 599b: ldc_w 12.0
      // 599e: ldc_w 7.0
      // 59a1: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 59a4: dup
      // 59a5: fconst_0
      // 59a6: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 59a9: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 59ac: bipush 0
      // 59ad: bipush 37
      // 59af: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 59b2: ldc_w -5.0036
      // 59b5: ldc_w 1.4884
      // 59b8: ldc_w -2.1242
      // 59bb: ldc_w 10.0
      // 59be: ldc_w 6.0
      // 59c1: ldc_w 5.0
      // 59c4: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 59c7: dup
      // 59c8: fconst_0
      // 59c9: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 59cc: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 59cf: bipush 30
      // 59d1: bipush 37
      // 59d3: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 59d6: ldc_w -2.0036
      // 59d9: ldc_w -14.5116
      // 59dc: ldc_w -0.1242
      // 59df: ldc_w 4.0
      // 59e2: ldc_w 22.0
      // 59e5: ldc_w 3.0
      // 59e8: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 59eb: dup
      // 59ec: fconst_0
      // 59ed: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 59f0: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 59f3: ldc_w 0.0036
      // 59f6: ldc_w -7.4717
      // 59f9: ldc_w -1.8758
      // 59fc: fconst_0
      // 59fd: ldc_w -0.3054
      // 5a00: fconst_0
      // 5a01: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 5a04: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 5a07: wide astore 295
      // 5a0b: wide aload 294
      // 5a0f: ldc_w "head50"
      // 5a12: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5a15: bipush 0
      // 5a16: bipush 19
      // 5a18: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5a1b: ldc_w -5.0
      // 5a1e: ldc_w -8.75
      // 5a21: ldc_w -7.25
      // 5a24: ldc_w 10.0
      // 5a27: ldc_w 9.0
      // 5a2a: ldc_w 9.0
      // 5a2d: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 5a30: dup
      // 5a31: fconst_0
      // 5a32: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 5a35: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5a38: bipush 38
      // 5a3a: bipush 25
      // 5a3c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5a3f: ldc_w -3.0
      // 5a42: ldc_w 0.25
      // 5a45: ldc_w -7.25
      // 5a48: ldc_w 6.0
      // 5a4b: ldc_w 4.0
      // 5a4e: ldc_w 4.0
      // 5a51: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 5a54: dup
      // 5a55: fconst_0
      // 5a56: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 5a59: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5a5c: fconst_0
      // 5a5d: ldc_w -22.2333
      // 5a60: ldc_w -0.75
      // 5a63: ldc_w -0.829
      // 5a66: fconst_0
      // 5a67: fconst_0
      // 5a68: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 5a6b: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 5a6e: wide astore 296
      // 5a72: aload 2
      // 5a73: ldc_w "skeleton79"
      // 5a76: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5a79: ldc_w 26.0
      // 5a7c: ldc_w -15.1667
      // 5a7f: ldc_w 24.2222
      // 5a82: ldc_w 1.645
      // 5a85: ldc_w 0.5836
      // 5a88: ldc_w 3.1068
      // 5a8b: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 5a8e: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 5a91: wide astore 297
      // 5a95: wide aload 297
      // 5a99: ldc_w "torso79"
      // 5a9c: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5a9f: fconst_0
      // 5aa0: ldc_w 7.15
      // 5aa3: ldc_w 1.7778
      // 5aa6: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 5aa9: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 5aac: wide astore 298
      // 5ab0: wide aload 298
      // 5ab4: ldc_w "cube_r9"
      // 5ab7: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5aba: bipush 0
      // 5abb: bipush 0
      // 5abc: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5abf: ldc_w -6.0036
      // 5ac2: ldc_w -10.5116
      // 5ac5: ldc_w -4.1242
      // 5ac8: ldc_w 12.0
      // 5acb: ldc_w 12.0
      // 5ace: ldc_w 7.0
      // 5ad1: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 5ad4: dup
      // 5ad5: fconst_0
      // 5ad6: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 5ad9: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5adc: bipush 0
      // 5add: bipush 37
      // 5adf: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5ae2: ldc_w -5.0036
      // 5ae5: ldc_w 1.4884
      // 5ae8: ldc_w -2.1242
      // 5aeb: ldc_w 10.0
      // 5aee: ldc_w 6.0
      // 5af1: ldc_w 5.0
      // 5af4: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 5af7: dup
      // 5af8: fconst_0
      // 5af9: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 5afc: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5aff: bipush 30
      // 5b01: bipush 37
      // 5b03: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5b06: ldc_w -2.0036
      // 5b09: ldc_w -14.5116
      // 5b0c: ldc_w -0.1242
      // 5b0f: ldc_w 4.0
      // 5b12: ldc_w 22.0
      // 5b15: ldc_w 3.0
      // 5b18: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 5b1b: dup
      // 5b1c: fconst_0
      // 5b1d: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 5b20: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5b23: ldc_w 0.0036
      // 5b26: ldc_w -7.4717
      // 5b29: ldc_w -1.8758
      // 5b2c: fconst_0
      // 5b2d: ldc_w -0.3054
      // 5b30: fconst_0
      // 5b31: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 5b34: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 5b37: wide astore 299
      // 5b3b: wide aload 298
      // 5b3f: ldc_w "head51"
      // 5b42: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5b45: bipush 0
      // 5b46: bipush 19
      // 5b48: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5b4b: ldc_w -5.0
      // 5b4e: ldc_w -8.75
      // 5b51: ldc_w -7.25
      // 5b54: ldc_w 10.0
      // 5b57: ldc_w 9.0
      // 5b5a: ldc_w 9.0
      // 5b5d: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 5b60: dup
      // 5b61: fconst_0
      // 5b62: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 5b65: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5b68: bipush 38
      // 5b6a: bipush 25
      // 5b6c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5b6f: ldc_w -3.0
      // 5b72: ldc_w 0.25
      // 5b75: ldc_w -7.25
      // 5b78: ldc_w 6.0
      // 5b7b: ldc_w 4.0
      // 5b7e: ldc_w 4.0
      // 5b81: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 5b84: dup
      // 5b85: fconst_0
      // 5b86: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 5b89: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 5b8c: fconst_0
      // 5b8d: ldc_w -22.2333
      // 5b90: ldc_w -0.75
      // 5b93: ldc_w -0.829
      // 5b96: fconst_0
      // 5b97: fconst_0
      // 5b98: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 5b9b: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 5b9e: wide astore 300
      // 5ba2: aload 0
      // 5ba3: bipush 64
      // 5ba5: bipush 64
      // 5ba7: invokestatic net/minecraft/client/model/geom/builders/LayerDefinition.create (Lnet/minecraft/client/model/geom/builders/MeshDefinition;II)Lnet/minecraft/client/model/geom/builders/LayerDefinition;
      // 5baa: areturn
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
      this.pile.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }
}
