package com.blamejared.crafttweaker.api.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/NameTagResult")
@ZenCodeType.Name("crafttweaker.api.entity.NameTagResult")
public class NameTagResult {
   @Nullable
   private Boolean result;
   private Component content;
   private final Component originalContent;

   public NameTagResult(@Nullable Boolean result, Component content, Component originalContent) {
      this.result = result;
      this.content = content;
      this.originalContent = originalContent;
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("content")
   public Component getContent() {
      return this.content;
   }

   @ZenCodeType.Method
   @ZenCodeType.Setter("content")
   public void setContent(Component content) {
      this.content = content;
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("originalContent")
   public Component getOriginalContent() {
      return this.originalContent;
   }

   @ZenCodeType.Method
   public void alwaysRender() {
      this.result = true;
   }

   @ZenCodeType.Method
   public void noRender() {
      this.result = false;
   }

   @ZenCodeType.Method
   public void setDefault() {
      this.result = null;
   }

   @Nullable
   public Boolean getResult() {
      return this.result;
   }
}
