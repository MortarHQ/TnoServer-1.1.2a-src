package com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.attachments.instances.fdmodel;

import com.finderfeed.fdlib.systems.FDRegistries;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.attachments.BaseModelAttachmentData;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.attachments.ModelAttachment;
import com.finderfeed.fdlib.systems.bedrock.models.FDModel;
import com.finderfeed.fdlib.systems.bedrock.models.FDModelInfo;
import com.finderfeed.fdlib.systems.render_types.FDRenderType;
import com.finderfeed.fdlib.util.FDColor;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.UnknownNullability;

public class FDModelAttachment implements ModelAttachment<FDModelAttachment, FDModelAttachmentData> {
   private FDModelAttachmentData data;
   private FDModel fdModel;

   protected FDModelAttachment() {
   }

   public FDModelAttachment(FDModelAttachmentData fdModelAttachmentData) {
      this.data = fdModelAttachmentData;
      this.fdModel = new FDModel(fdModelAttachmentData.getFdModelInfo());
   }

   public FDModel getFdModel() {
      return this.fdModel;
   }

   public FDColor getColor() {
      return this.data.getColor();
   }

   public FDRenderType getRenderType() {
      return this.data.getRenderType();
   }

   public ResourceLocation getTexture() {
      return this.data.getTexture();
   }

   public BaseModelAttachmentData getData() {
      return this.data.getBaseModelAttachmentData();
   }

   public FDModelAttachmentData attachmentData() {
      return this.data;
   }

   public @UnknownNullability CompoundTag serializeNBT(Provider provider) {
      CompoundTag tag = new CompoundTag();
      ResourceLocation id = FDRegistries.MODELS.getKey(this.data.getFdModelInfo());
      tag.putString("modelInfo", id.toString());
      tag.put("data", this.data.getBaseModelAttachmentData().serializeNBT(provider));
      ResourceLocation rtid = FDRegistries.RENDER_TYPE.getKey(this.data.getRenderType());
      tag.putString("renderType", rtid.toString());
      tag.putString("texture", this.data.getTexture().toString());
      FDColor fdColor = this.data.getColor();
      tag.putInt("color", fdColor.encode());
      return tag;
   }

   public void deserializeNBT(Provider provider, CompoundTag nbt) {
      ResourceLocation location = ResourceLocation.parse(nbt.getString("modelInfo"));
      FDModelInfo info = (FDModelInfo)FDRegistries.MODELS.get(location);
      BaseModelAttachmentData baseModelAttachmentData = new BaseModelAttachmentData();
      baseModelAttachmentData.deserializeNBT(provider, nbt.getCompound("data"));
      ResourceLocation texture = ResourceLocation.parse(nbt.getString("texture"));
      ResourceLocation renderTypeLocation = ResourceLocation.parse(nbt.getString("renderType"));
      FDRenderType fdRenderType = (FDRenderType)FDRegistries.RENDER_TYPE.get(renderTypeLocation);
      FDColor color = FDColor.decode(nbt.getInt("color"));
      this.data = new FDModelAttachmentData(baseModelAttachmentData, fdRenderType, info, texture, color);
      this.fdModel = new FDModel(info);
   }
}
