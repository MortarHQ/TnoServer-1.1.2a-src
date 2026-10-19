package com.faboslav.friendsandfoes.common.entity.animation.animator.loader;

import org.joml.Vector3f;

@FunctionalInterface
public interface AnimationKeyframeTarget {
   Vector3f apply(float var1, float var2, float var3);
}
