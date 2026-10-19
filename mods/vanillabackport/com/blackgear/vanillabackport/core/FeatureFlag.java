package com.blackgear.vanillabackport.core;

@FunctionalInterface
public interface FeatureFlag {
   FeatureFlag DEFAULT = () -> true;

   boolean isEnabled();
}
