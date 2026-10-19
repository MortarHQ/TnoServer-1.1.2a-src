package com.teamresourceful.resourcefullib.common.lib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.ApiStatus.Internal;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
@Internal
public @interface PlatformOverride {
   String FABRIC = "fabric";
   String NEOFORGE = "neoforge";

   String value();
}
