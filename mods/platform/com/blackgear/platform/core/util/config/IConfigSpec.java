package com.blackgear.platform.core.util.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.UnmodifiableConfig;

public interface IConfigSpec<T extends IConfigSpec<T>> extends UnmodifiableConfig {
   default T self() {
      return (T)this;
   }

   void setConfig(CommentedConfig var1);

   boolean isCorrecting();

   boolean isCorrect(CommentedConfig var1);

   int correct(CommentedConfig var1);

   void afterReload();
}
