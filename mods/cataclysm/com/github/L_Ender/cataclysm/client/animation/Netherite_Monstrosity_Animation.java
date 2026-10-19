package com.github.L_Ender.cataclysm.client.animation;

import net.minecraft.client.animation.AnimationDefinition;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Netherite_Monstrosity_Animation {
   public static final AnimationDefinition IDLE;
   public static final AnimationDefinition WALK;
   public static final AnimationDefinition SMASH;
   public static final AnimationDefinition SLEEP;
   public static final AnimationDefinition AWAKE;
   public static final AnimationDefinition PHASE;
   public static final AnimationDefinition FIRE;
   public static final AnimationDefinition SHOULDER_CHECK;
   public static final AnimationDefinition OVERPOWER;
   public static final AnimationDefinition DRAIN;
   public static final AnimationDefinition DEATH;
   public static final AnimationDefinition FLARE_SHOT;

   static {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 0000: ldc 2.25
      // 0002: invokestatic net/minecraft/client/animation/AnimationDefinition$Builder.withLength (F)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0005: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.looping ()Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0008: ldc "upperbody"
      // 000a: new net/minecraft/client/animation/AnimationChannel
      // 000d: dup
      // 000e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0011: bipush 3
      // 0012: anewarray 28
      // 0015: dup
      // 0016: bipush 0
      // 0017: new net/minecraft/client/animation/Keyframe
      // 001a: dup
      // 001b: fconst_0
      // 001c: fconst_0
      // 001d: fconst_0
      // 001e: fconst_0
      // 001f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0022: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0025: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0028: aastore
      // 0029: dup
      // 002a: bipush 1
      // 002b: new net/minecraft/client/animation/Keyframe
      // 002e: dup
      // 002f: ldc 1.1667
      // 0031: ldc 2.5
      // 0033: fconst_0
      // 0034: fconst_0
      // 0035: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0038: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 003b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 003e: aastore
      // 003f: dup
      // 0040: bipush 2
      // 0041: new net/minecraft/client/animation/Keyframe
      // 0044: dup
      // 0045: ldc 2.25
      // 0047: fconst_0
      // 0048: fconst_0
      // 0049: fconst_0
      // 004a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 004d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0050: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0053: aastore
      // 0054: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0057: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 005a: ldc "upperbody"
      // 005c: new net/minecraft/client/animation/AnimationChannel
      // 005f: dup
      // 0060: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0063: bipush 3
      // 0064: anewarray 28
      // 0067: dup
      // 0068: bipush 0
      // 0069: new net/minecraft/client/animation/Keyframe
      // 006c: dup
      // 006d: fconst_0
      // 006e: fconst_0
      // 006f: fconst_0
      // 0070: fconst_0
      // 0071: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0074: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0077: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 007a: aastore
      // 007b: dup
      // 007c: bipush 1
      // 007d: new net/minecraft/client/animation/Keyframe
      // 0080: dup
      // 0081: ldc 0.9583
      // 0083: fconst_0
      // 0084: ldc -2.0
      // 0086: fconst_0
      // 0087: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 008a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 008d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0090: aastore
      // 0091: dup
      // 0092: bipush 2
      // 0093: new net/minecraft/client/animation/Keyframe
      // 0096: dup
      // 0097: ldc 2.25
      // 0099: fconst_0
      // 009a: fconst_0
      // 009b: fconst_0
      // 009c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 009f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00a2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00a5: aastore
      // 00a6: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 00a9: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 00ac: ldc "head"
      // 00ae: new net/minecraft/client/animation/AnimationChannel
      // 00b1: dup
      // 00b2: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 00b5: bipush 3
      // 00b6: anewarray 28
      // 00b9: dup
      // 00ba: bipush 0
      // 00bb: new net/minecraft/client/animation/Keyframe
      // 00be: dup
      // 00bf: fconst_0
      // 00c0: fconst_0
      // 00c1: fconst_0
      // 00c2: fconst_0
      // 00c3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 00c6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00c9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00cc: aastore
      // 00cd: dup
      // 00ce: bipush 1
      // 00cf: new net/minecraft/client/animation/Keyframe
      // 00d2: dup
      // 00d3: ldc 0.9583
      // 00d5: ldc 5.0
      // 00d7: fconst_0
      // 00d8: fconst_0
      // 00d9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 00dc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00df: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00e2: aastore
      // 00e3: dup
      // 00e4: bipush 2
      // 00e5: new net/minecraft/client/animation/Keyframe
      // 00e8: dup
      // 00e9: ldc 2.25
      // 00eb: fconst_0
      // 00ec: fconst_0
      // 00ed: fconst_0
      // 00ee: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 00f1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00f4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00f7: aastore
      // 00f8: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 00fb: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 00fe: ldc "jaw"
      // 0100: new net/minecraft/client/animation/AnimationChannel
      // 0103: dup
      // 0104: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0107: bipush 3
      // 0108: anewarray 28
      // 010b: dup
      // 010c: bipush 0
      // 010d: new net/minecraft/client/animation/Keyframe
      // 0110: dup
      // 0111: fconst_0
      // 0112: fconst_0
      // 0113: fconst_0
      // 0114: fconst_0
      // 0115: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0118: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 011b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 011e: aastore
      // 011f: dup
      // 0120: bipush 1
      // 0121: new net/minecraft/client/animation/Keyframe
      // 0124: dup
      // 0125: ldc 1.1667
      // 0127: ldc 7.5
      // 0129: fconst_0
      // 012a: fconst_0
      // 012b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 012e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0131: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0134: aastore
      // 0135: dup
      // 0136: bipush 2
      // 0137: new net/minecraft/client/animation/Keyframe
      // 013a: dup
      // 013b: ldc 2.25
      // 013d: fconst_0
      // 013e: fconst_0
      // 013f: fconst_0
      // 0140: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0143: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0146: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0149: aastore
      // 014a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 014d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0150: ldc "leftarm"
      // 0152: new net/minecraft/client/animation/AnimationChannel
      // 0155: dup
      // 0156: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0159: bipush 3
      // 015a: anewarray 28
      // 015d: dup
      // 015e: bipush 0
      // 015f: new net/minecraft/client/animation/Keyframe
      // 0162: dup
      // 0163: fconst_0
      // 0164: fconst_0
      // 0165: fconst_0
      // 0166: fconst_0
      // 0167: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 016a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 016d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0170: aastore
      // 0171: dup
      // 0172: bipush 1
      // 0173: new net/minecraft/client/animation/Keyframe
      // 0176: dup
      // 0177: ldc 0.9167
      // 0179: fconst_0
      // 017a: fconst_0
      // 017b: ldc -5.0
      // 017d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0180: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0183: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0186: aastore
      // 0187: dup
      // 0188: bipush 2
      // 0189: new net/minecraft/client/animation/Keyframe
      // 018c: dup
      // 018d: ldc 2.25
      // 018f: fconst_0
      // 0190: fconst_0
      // 0191: fconst_0
      // 0192: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0195: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0198: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 019b: aastore
      // 019c: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 019f: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 01a2: ldc "rightarm"
      // 01a4: new net/minecraft/client/animation/AnimationChannel
      // 01a7: dup
      // 01a8: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 01ab: bipush 3
      // 01ac: anewarray 28
      // 01af: dup
      // 01b0: bipush 0
      // 01b1: new net/minecraft/client/animation/Keyframe
      // 01b4: dup
      // 01b5: fconst_0
      // 01b6: fconst_0
      // 01b7: fconst_0
      // 01b8: fconst_0
      // 01b9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 01bc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01bf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01c2: aastore
      // 01c3: dup
      // 01c4: bipush 1
      // 01c5: new net/minecraft/client/animation/Keyframe
      // 01c8: dup
      // 01c9: ldc 0.9167
      // 01cb: fconst_0
      // 01cc: fconst_0
      // 01cd: ldc 5.0
      // 01cf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 01d2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01d5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01d8: aastore
      // 01d9: dup
      // 01da: bipush 2
      // 01db: new net/minecraft/client/animation/Keyframe
      // 01de: dup
      // 01df: ldc 2.25
      // 01e1: fconst_0
      // 01e2: fconst_0
      // 01e3: fconst_0
      // 01e4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 01e7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01ea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01ed: aastore
      // 01ee: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 01f1: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 01f4: ldc "rightleg"
      // 01f6: new net/minecraft/client/animation/AnimationChannel
      // 01f9: dup
      // 01fa: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 01fd: bipush 1
      // 01fe: anewarray 28
      // 0201: dup
      // 0202: bipush 0
      // 0203: new net/minecraft/client/animation/Keyframe
      // 0206: dup
      // 0207: fconst_0
      // 0208: fconst_0
      // 0209: fconst_0
      // 020a: fconst_0
      // 020b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 020e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0211: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0214: aastore
      // 0215: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0218: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 021b: ldc "leftleg"
      // 021d: new net/minecraft/client/animation/AnimationChannel
      // 0220: dup
      // 0221: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0224: bipush 1
      // 0225: anewarray 28
      // 0228: dup
      // 0229: bipush 0
      // 022a: new net/minecraft/client/animation/Keyframe
      // 022d: dup
      // 022e: fconst_0
      // 022f: fconst_0
      // 0230: fconst_0
      // 0231: fconst_0
      // 0232: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0235: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0238: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 023b: aastore
      // 023c: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 023f: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0242: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.build ()Lnet/minecraft/client/animation/AnimationDefinition;
      // 0245: putstatic com/github/L_Ender/cataclysm/client/animation/Netherite_Monstrosity_Animation.IDLE Lnet/minecraft/client/animation/AnimationDefinition;
      // 0248: ldc 2.2083
      // 024a: invokestatic net/minecraft/client/animation/AnimationDefinition$Builder.withLength (F)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 024d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.looping ()Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0250: ldc "roots"
      // 0252: new net/minecraft/client/animation/AnimationChannel
      // 0255: dup
      // 0256: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0259: bipush 3
      // 025a: anewarray 28
      // 025d: dup
      // 025e: bipush 0
      // 025f: new net/minecraft/client/animation/Keyframe
      // 0262: dup
      // 0263: fconst_0
      // 0264: fconst_0
      // 0265: fconst_0
      // 0266: fconst_0
      // 0267: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 026a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 026d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0270: aastore
      // 0271: dup
      // 0272: bipush 1
      // 0273: new net/minecraft/client/animation/Keyframe
      // 0276: dup
      // 0277: ldc 1.125
      // 0279: fconst_0
      // 027a: fconst_0
      // 027b: fconst_0
      // 027c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 027f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0282: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0285: aastore
      // 0286: dup
      // 0287: bipush 2
      // 0288: new net/minecraft/client/animation/Keyframe
      // 028b: dup
      // 028c: ldc 2.2083
      // 028e: fconst_0
      // 028f: fconst_0
      // 0290: fconst_0
      // 0291: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0294: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0297: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 029a: aastore
      // 029b: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 029e: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 02a1: ldc "roots"
      // 02a3: new net/minecraft/client/animation/AnimationChannel
      // 02a6: dup
      // 02a7: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 02aa: bipush 5
      // 02ab: anewarray 28
      // 02ae: dup
      // 02af: bipush 0
      // 02b0: new net/minecraft/client/animation/Keyframe
      // 02b3: dup
      // 02b4: fconst_0
      // 02b5: fconst_0
      // 02b6: ldc -2.0
      // 02b8: fconst_0
      // 02b9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 02bc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02bf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02c2: aastore
      // 02c3: dup
      // 02c4: bipush 1
      // 02c5: new net/minecraft/client/animation/Keyframe
      // 02c8: dup
      // 02c9: ldc 0.5
      // 02cb: fconst_0
      // 02cc: fconst_0
      // 02cd: fconst_0
      // 02ce: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 02d1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02d4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02d7: aastore
      // 02d8: dup
      // 02d9: bipush 2
      // 02da: new net/minecraft/client/animation/Keyframe
      // 02dd: dup
      // 02de: ldc 1.125
      // 02e0: fconst_0
      // 02e1: ldc -2.0
      // 02e3: fconst_0
      // 02e4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 02e7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02ea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02ed: aastore
      // 02ee: dup
      // 02ef: bipush 3
      // 02f0: new net/minecraft/client/animation/Keyframe
      // 02f3: dup
      // 02f4: ldc 1.6667
      // 02f6: fconst_0
      // 02f7: fconst_0
      // 02f8: fconst_0
      // 02f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 02fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0302: aastore
      // 0303: dup
      // 0304: bipush 4
      // 0305: new net/minecraft/client/animation/Keyframe
      // 0308: dup
      // 0309: ldc 2.2083
      // 030b: fconst_0
      // 030c: ldc -2.0
      // 030e: fconst_0
      // 030f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0312: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0315: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0318: aastore
      // 0319: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 031c: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 031f: ldc "lowerbody"
      // 0321: new net/minecraft/client/animation/AnimationChannel
      // 0324: dup
      // 0325: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0328: bipush 5
      // 0329: anewarray 28
      // 032c: dup
      // 032d: bipush 0
      // 032e: new net/minecraft/client/animation/Keyframe
      // 0331: dup
      // 0332: fconst_0
      // 0333: fconst_0
      // 0334: fconst_0
      // 0335: fconst_0
      // 0336: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0339: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 033c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 033f: aastore
      // 0340: dup
      // 0341: bipush 1
      // 0342: new net/minecraft/client/animation/Keyframe
      // 0345: dup
      // 0346: ldc 0.5417
      // 0348: fconst_0
      // 0349: fconst_0
      // 034a: fconst_0
      // 034b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 034e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0351: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0354: aastore
      // 0355: dup
      // 0356: bipush 2
      // 0357: new net/minecraft/client/animation/Keyframe
      // 035a: dup
      // 035b: ldc 1.125
      // 035d: fconst_0
      // 035e: fconst_0
      // 035f: fconst_0
      // 0360: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0363: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0366: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0369: aastore
      // 036a: dup
      // 036b: bipush 3
      // 036c: new net/minecraft/client/animation/Keyframe
      // 036f: dup
      // 0370: ldc 1.6667
      // 0372: fconst_0
      // 0373: fconst_0
      // 0374: fconst_0
      // 0375: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0378: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 037b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 037e: aastore
      // 037f: dup
      // 0380: bipush 4
      // 0381: new net/minecraft/client/animation/Keyframe
      // 0384: dup
      // 0385: ldc 2.2083
      // 0387: fconst_0
      // 0388: fconst_0
      // 0389: fconst_0
      // 038a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 038d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0390: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0393: aastore
      // 0394: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0397: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 039a: ldc "lowerbody"
      // 039c: new net/minecraft/client/animation/AnimationChannel
      // 039f: dup
      // 03a0: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 03a3: bipush 5
      // 03a4: anewarray 28
      // 03a7: dup
      // 03a8: bipush 0
      // 03a9: new net/minecraft/client/animation/Keyframe
      // 03ac: dup
      // 03ad: fconst_0
      // 03ae: fconst_0
      // 03af: ldc -2.0
      // 03b1: fconst_0
      // 03b2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 03b5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03b8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03bb: aastore
      // 03bc: dup
      // 03bd: bipush 1
      // 03be: new net/minecraft/client/animation/Keyframe
      // 03c1: dup
      // 03c2: ldc 0.5417
      // 03c4: fconst_0
      // 03c5: fconst_0
      // 03c6: fconst_0
      // 03c7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 03ca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03cd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03d0: aastore
      // 03d1: dup
      // 03d2: bipush 2
      // 03d3: new net/minecraft/client/animation/Keyframe
      // 03d6: dup
      // 03d7: ldc 1.125
      // 03d9: fconst_0
      // 03da: ldc -2.0
      // 03dc: fconst_0
      // 03dd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 03e0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03e3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03e6: aastore
      // 03e7: dup
      // 03e8: bipush 3
      // 03e9: new net/minecraft/client/animation/Keyframe
      // 03ec: dup
      // 03ed: ldc 1.6667
      // 03ef: fconst_0
      // 03f0: fconst_0
      // 03f1: fconst_0
      // 03f2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 03f5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03f8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03fb: aastore
      // 03fc: dup
      // 03fd: bipush 4
      // 03fe: new net/minecraft/client/animation/Keyframe
      // 0401: dup
      // 0402: ldc 2.2083
      // 0404: fconst_0
      // 0405: ldc -2.0
      // 0407: fconst_0
      // 0408: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 040b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 040e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0411: aastore
      // 0412: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0415: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0418: ldc "upperbody"
      // 041a: new net/minecraft/client/animation/AnimationChannel
      // 041d: dup
      // 041e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0421: bipush 5
      // 0422: anewarray 28
      // 0425: dup
      // 0426: bipush 0
      // 0427: new net/minecraft/client/animation/Keyframe
      // 042a: dup
      // 042b: fconst_0
      // 042c: fconst_0
      // 042d: ldc -5.0
      // 042f: ldc -5.0
      // 0431: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0434: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0437: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 043a: aastore
      // 043b: dup
      // 043c: bipush 1
      // 043d: new net/minecraft/client/animation/Keyframe
      // 0440: dup
      // 0441: ldc 0.7083
      // 0443: fconst_0
      // 0444: fconst_0
      // 0445: fconst_0
      // 0446: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0449: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 044c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 044f: aastore
      // 0450: dup
      // 0451: bipush 2
      // 0452: new net/minecraft/client/animation/Keyframe
      // 0455: dup
      // 0456: ldc 1.125
      // 0458: ldc -0.329
      // 045a: ldc 7.4928
      // 045c: ldc 4.9785
      // 045e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0461: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0464: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0467: aastore
      // 0468: dup
      // 0469: bipush 3
      // 046a: new net/minecraft/client/animation/Keyframe
      // 046d: dup
      // 046e: ldc 1.8333
      // 0470: fconst_0
      // 0471: fconst_0
      // 0472: fconst_0
      // 0473: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0476: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0479: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 047c: aastore
      // 047d: dup
      // 047e: bipush 4
      // 047f: new net/minecraft/client/animation/Keyframe
      // 0482: dup
      // 0483: ldc 2.2083
      // 0485: fconst_0
      // 0486: ldc -5.0
      // 0488: ldc -5.0
      // 048a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 048d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0490: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0493: aastore
      // 0494: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0497: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 049a: ldc "upperbody"
      // 049c: new net/minecraft/client/animation/AnimationChannel
      // 049f: dup
      // 04a0: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 04a3: bipush 6
      // 04a5: anewarray 28
      // 04a8: dup
      // 04a9: bipush 0
      // 04aa: new net/minecraft/client/animation/Keyframe
      // 04ad: dup
      // 04ae: fconst_0
      // 04af: fconst_0
      // 04b0: ldc -2.0
      // 04b2: fconst_0
      // 04b3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 04b6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04b9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04bc: aastore
      // 04bd: dup
      // 04be: bipush 1
      // 04bf: new net/minecraft/client/animation/Keyframe
      // 04c2: dup
      // 04c3: ldc 0.6667
      // 04c5: fconst_0
      // 04c6: fconst_0
      // 04c7: fconst_0
      // 04c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 04cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04d1: aastore
      // 04d2: dup
      // 04d3: bipush 2
      // 04d4: new net/minecraft/client/animation/Keyframe
      // 04d7: dup
      // 04d8: ldc 1.0417
      // 04da: fconst_0
      // 04db: ldc -2.0
      // 04dd: fconst_0
      // 04de: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 04e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04e7: aastore
      // 04e8: dup
      // 04e9: bipush 3
      // 04ea: new net/minecraft/client/animation/Keyframe
      // 04ed: dup
      // 04ee: ldc 1.4167
      // 04f0: fconst_0
      // 04f1: fconst_0
      // 04f2: fconst_0
      // 04f3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 04f6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04f9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04fc: aastore
      // 04fd: dup
      // 04fe: bipush 4
      // 04ff: new net/minecraft/client/animation/Keyframe
      // 0502: dup
      // 0503: ldc 1.8333
      // 0505: fconst_0
      // 0506: fconst_0
      // 0507: fconst_0
      // 0508: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 050b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 050e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0511: aastore
      // 0512: dup
      // 0513: bipush 5
      // 0514: new net/minecraft/client/animation/Keyframe
      // 0517: dup
      // 0518: ldc 2.2083
      // 051a: fconst_0
      // 051b: ldc -2.0
      // 051d: fconst_0
      // 051e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0521: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0524: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0527: aastore
      // 0528: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 052b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 052e: ldc "head"
      // 0530: new net/minecraft/client/animation/AnimationChannel
      // 0533: dup
      // 0534: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0537: bipush 3
      // 0538: anewarray 28
      // 053b: dup
      // 053c: bipush 0
      // 053d: new net/minecraft/client/animation/Keyframe
      // 0540: dup
      // 0541: fconst_0
      // 0542: ldc 11.847
      // 0544: ldc 7.0494
      // 0546: ldc -8.7386
      // 0548: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 054b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 054e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0551: aastore
      // 0552: dup
      // 0553: bipush 1
      // 0554: new net/minecraft/client/animation/Keyframe
      // 0557: dup
      // 0558: ldc 1.25
      // 055a: ldc 7.4366
      // 055c: ldc -0.9762
      // 055e: ldc 7.4366
      // 0560: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0563: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0566: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0569: aastore
      // 056a: dup
      // 056b: bipush 2
      // 056c: new net/minecraft/client/animation/Keyframe
      // 056f: dup
      // 0570: ldc 2.2083
      // 0572: ldc 11.847
      // 0574: ldc 7.0494
      // 0576: ldc -8.7386
      // 0578: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 057b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 057e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0581: aastore
      // 0582: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0585: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0588: ldc "head"
      // 058a: new net/minecraft/client/animation/AnimationChannel
      // 058d: dup
      // 058e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0591: bipush 5
      // 0592: anewarray 28
      // 0595: dup
      // 0596: bipush 0
      // 0597: new net/minecraft/client/animation/Keyframe
      // 059a: dup
      // 059b: fconst_0
      // 059c: fconst_0
      // 059d: ldc -2.0
      // 059f: fconst_0
      // 05a0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 05a3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05a6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05a9: aastore
      // 05aa: dup
      // 05ab: bipush 1
      // 05ac: new net/minecraft/client/animation/Keyframe
      // 05af: dup
      // 05b0: ldc 0.7917
      // 05b2: fconst_0
      // 05b3: fconst_0
      // 05b4: fconst_0
      // 05b5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 05b8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05bb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05be: aastore
      // 05bf: dup
      // 05c0: bipush 2
      // 05c1: new net/minecraft/client/animation/Keyframe
      // 05c4: dup
      // 05c5: ldc 1.125
      // 05c7: fconst_0
      // 05c8: ldc -2.0
      // 05ca: fconst_0
      // 05cb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 05ce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05d1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05d4: aastore
      // 05d5: dup
      // 05d6: bipush 3
      // 05d7: new net/minecraft/client/animation/Keyframe
      // 05da: dup
      // 05db: ldc 1.9167
      // 05dd: fconst_0
      // 05de: fconst_0
      // 05df: fconst_0
      // 05e0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 05e3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05e6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05e9: aastore
      // 05ea: dup
      // 05eb: bipush 4
      // 05ec: new net/minecraft/client/animation/Keyframe
      // 05ef: dup
      // 05f0: ldc 2.2083
      // 05f2: fconst_0
      // 05f3: ldc -2.0
      // 05f5: fconst_0
      // 05f6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 05f9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05fc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05ff: aastore
      // 0600: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0603: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0606: ldc "jaw"
      // 0608: new net/minecraft/client/animation/AnimationChannel
      // 060b: dup
      // 060c: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 060f: bipush 5
      // 0610: anewarray 28
      // 0613: dup
      // 0614: bipush 0
      // 0615: new net/minecraft/client/animation/Keyframe
      // 0618: dup
      // 0619: fconst_0
      // 061a: ldc 7.5
      // 061c: fconst_0
      // 061d: fconst_0
      // 061e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0621: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0624: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0627: aastore
      // 0628: dup
      // 0629: bipush 1
      // 062a: new net/minecraft/client/animation/Keyframe
      // 062d: dup
      // 062e: ldc 0.625
      // 0630: fconst_0
      // 0631: fconst_0
      // 0632: fconst_0
      // 0633: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0636: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0639: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 063c: aastore
      // 063d: dup
      // 063e: bipush 2
      // 063f: new net/minecraft/client/animation/Keyframe
      // 0642: dup
      // 0643: ldc 0.9167
      // 0645: ldc 7.5
      // 0647: fconst_0
      // 0648: fconst_0
      // 0649: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 064c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 064f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0652: aastore
      // 0653: dup
      // 0654: bipush 3
      // 0655: new net/minecraft/client/animation/Keyframe
      // 0658: dup
      // 0659: ldc 1.9167
      // 065b: fconst_0
      // 065c: fconst_0
      // 065d: fconst_0
      // 065e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0661: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0664: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0667: aastore
      // 0668: dup
      // 0669: bipush 4
      // 066a: new net/minecraft/client/animation/Keyframe
      // 066d: dup
      // 066e: ldc 2.2083
      // 0670: ldc 7.5
      // 0672: fconst_0
      // 0673: fconst_0
      // 0674: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0677: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 067a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 067d: aastore
      // 067e: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0681: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0684: ldc "leftarm"
      // 0686: new net/minecraft/client/animation/AnimationChannel
      // 0689: dup
      // 068a: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 068d: bipush 3
      // 068e: anewarray 28
      // 0691: dup
      // 0692: bipush 0
      // 0693: new net/minecraft/client/animation/Keyframe
      // 0696: dup
      // 0697: fconst_0
      // 0698: ldc -20.0
      // 069a: fconst_0
      // 069b: fconst_0
      // 069c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 069f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06a2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06a5: aastore
      // 06a6: dup
      // 06a7: bipush 1
      // 06a8: new net/minecraft/client/animation/Keyframe
      // 06ab: dup
      // 06ac: ldc 1.125
      // 06ae: ldc 30.0
      // 06b0: fconst_0
      // 06b1: fconst_0
      // 06b2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 06b5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06b8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06bb: aastore
      // 06bc: dup
      // 06bd: bipush 2
      // 06be: new net/minecraft/client/animation/Keyframe
      // 06c1: dup
      // 06c2: ldc 2.2083
      // 06c4: ldc -20.0
      // 06c6: fconst_0
      // 06c7: fconst_0
      // 06c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 06cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06d1: aastore
      // 06d2: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 06d5: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 06d8: ldc "rightarm"
      // 06da: new net/minecraft/client/animation/AnimationChannel
      // 06dd: dup
      // 06de: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 06e1: bipush 3
      // 06e2: anewarray 28
      // 06e5: dup
      // 06e6: bipush 0
      // 06e7: new net/minecraft/client/animation/Keyframe
      // 06ea: dup
      // 06eb: fconst_0
      // 06ec: ldc 30.0
      // 06ee: fconst_0
      // 06ef: fconst_0
      // 06f0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 06f3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06f6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06f9: aastore
      // 06fa: dup
      // 06fb: bipush 1
      // 06fc: new net/minecraft/client/animation/Keyframe
      // 06ff: dup
      // 0700: ldc 1.125
      // 0702: ldc -27.5
      // 0704: fconst_0
      // 0705: fconst_0
      // 0706: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0709: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 070c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 070f: aastore
      // 0710: dup
      // 0711: bipush 2
      // 0712: new net/minecraft/client/animation/Keyframe
      // 0715: dup
      // 0716: ldc 2.2083
      // 0718: ldc 30.0
      // 071a: fconst_0
      // 071b: fconst_0
      // 071c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 071f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0722: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0725: aastore
      // 0726: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0729: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 072c: ldc "rightleg"
      // 072e: new net/minecraft/client/animation/AnimationChannel
      // 0731: dup
      // 0732: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0735: bipush 5
      // 0736: anewarray 28
      // 0739: dup
      // 073a: bipush 0
      // 073b: new net/minecraft/client/animation/Keyframe
      // 073e: dup
      // 073f: fconst_0
      // 0740: ldc -5.0
      // 0742: ldc 7.5
      // 0744: fconst_0
      // 0745: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0748: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 074b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 074e: aastore
      // 074f: dup
      // 0750: bipush 1
      // 0751: new net/minecraft/client/animation/Keyframe
      // 0754: dup
      // 0755: ldc 0.5417
      // 0757: ldc 15.0
      // 0759: fconst_0
      // 075a: fconst_0
      // 075b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 075e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0761: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0764: aastore
      // 0765: dup
      // 0766: bipush 2
      // 0767: new net/minecraft/client/animation/Keyframe
      // 076a: dup
      // 076b: ldc 1.125
      // 076d: ldc 22.5
      // 076f: fconst_0
      // 0770: fconst_0
      // 0771: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0774: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0777: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 077a: aastore
      // 077b: dup
      // 077c: bipush 3
      // 077d: new net/minecraft/client/animation/Keyframe
      // 0780: dup
      // 0781: ldc 1.6667
      // 0783: ldc -5.0
      // 0785: fconst_0
      // 0786: fconst_0
      // 0787: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 078a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 078d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0790: aastore
      // 0791: dup
      // 0792: bipush 4
      // 0793: new net/minecraft/client/animation/Keyframe
      // 0796: dup
      // 0797: ldc 2.2083
      // 0799: ldc -5.0
      // 079b: ldc 7.5
      // 079d: fconst_0
      // 079e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 07a1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07a4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07a7: aastore
      // 07a8: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 07ab: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 07ae: ldc "rightleg"
      // 07b0: new net/minecraft/client/animation/AnimationChannel
      // 07b3: dup
      // 07b4: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 07b7: bipush 5
      // 07b8: anewarray 28
      // 07bb: dup
      // 07bc: bipush 0
      // 07bd: new net/minecraft/client/animation/Keyframe
      // 07c0: dup
      // 07c1: fconst_0
      // 07c2: ldc -0.5
      // 07c4: fconst_0
      // 07c5: ldc -10.4
      // 07c7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 07ca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07cd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07d0: aastore
      // 07d1: dup
      // 07d2: bipush 1
      // 07d3: new net/minecraft/client/animation/Keyframe
      // 07d6: dup
      // 07d7: ldc 0.5417
      // 07d9: fconst_0
      // 07da: fconst_0
      // 07db: fconst_0
      // 07dc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 07df: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07e2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07e5: aastore
      // 07e6: dup
      // 07e7: bipush 2
      // 07e8: new net/minecraft/client/animation/Keyframe
      // 07eb: dup
      // 07ec: ldc 1.125
      // 07ee: fconst_0
      // 07ef: fconst_1
      // 07f0: ldc 4.5
      // 07f2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 07f5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07f8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07fb: aastore
      // 07fc: dup
      // 07fd: bipush 3
      // 07fe: new net/minecraft/client/animation/Keyframe
      // 0801: dup
      // 0802: ldc 1.6667
      // 0804: fconst_0
      // 0805: ldc 7.0
      // 0807: ldc -1.0
      // 0809: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 080c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 080f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0812: aastore
      // 0813: dup
      // 0814: bipush 4
      // 0815: new net/minecraft/client/animation/Keyframe
      // 0818: dup
      // 0819: ldc 2.2083
      // 081b: ldc -0.5
      // 081d: fconst_0
      // 081e: ldc -10.5
      // 0820: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0823: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0826: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0829: aastore
      // 082a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 082d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0830: ldc "leftleg"
      // 0832: new net/minecraft/client/animation/AnimationChannel
      // 0835: dup
      // 0836: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0839: bipush 5
      // 083a: anewarray 28
      // 083d: dup
      // 083e: bipush 0
      // 083f: new net/minecraft/client/animation/Keyframe
      // 0842: dup
      // 0843: fconst_0
      // 0844: ldc 12.5
      // 0846: fconst_0
      // 0847: fconst_0
      // 0848: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 084b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 084e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0851: aastore
      // 0852: dup
      // 0853: bipush 1
      // 0854: new net/minecraft/client/animation/Keyframe
      // 0857: dup
      // 0858: ldc 0.5417
      // 085a: fconst_0
      // 085b: fconst_0
      // 085c: fconst_0
      // 085d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0860: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0863: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0866: aastore
      // 0867: dup
      // 0868: bipush 2
      // 0869: new net/minecraft/client/animation/Keyframe
      // 086c: dup
      // 086d: ldc 1.125
      // 086f: ldc -5.0
      // 0871: ldc -5.0
      // 0873: fconst_0
      // 0874: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0877: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 087a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 087d: aastore
      // 087e: dup
      // 087f: bipush 3
      // 0880: new net/minecraft/client/animation/Keyframe
      // 0883: dup
      // 0884: ldc 1.6667
      // 0886: ldc 15.0
      // 0888: fconst_0
      // 0889: fconst_0
      // 088a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 088d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0890: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0893: aastore
      // 0894: dup
      // 0895: bipush 4
      // 0896: new net/minecraft/client/animation/Keyframe
      // 0899: dup
      // 089a: ldc 2.2083
      // 089c: ldc 12.5
      // 089e: fconst_0
      // 089f: fconst_0
      // 08a0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 08a3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08a6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08a9: aastore
      // 08aa: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 08ad: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 08b0: ldc "leftleg"
      // 08b2: new net/minecraft/client/animation/AnimationChannel
      // 08b5: dup
      // 08b6: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 08b9: bipush 4
      // 08ba: anewarray 28
      // 08bd: dup
      // 08be: bipush 0
      // 08bf: new net/minecraft/client/animation/Keyframe
      // 08c2: dup
      // 08c3: fconst_0
      // 08c4: fconst_0
      // 08c5: fconst_0
      // 08c6: ldc 6.3
      // 08c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 08cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08d1: aastore
      // 08d2: dup
      // 08d3: bipush 1
      // 08d4: new net/minecraft/client/animation/Keyframe
      // 08d7: dup
      // 08d8: ldc 0.5417
      // 08da: fconst_0
      // 08db: ldc 9.5
      // 08dd: ldc -1.0
      // 08df: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 08e2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08e5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08e8: aastore
      // 08e9: dup
      // 08ea: bipush 2
      // 08eb: new net/minecraft/client/animation/Keyframe
      // 08ee: dup
      // 08ef: ldc 1.125
      // 08f1: ldc 0.5
      // 08f3: fconst_0
      // 08f4: ldc -13.0
      // 08f6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 08f9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08fc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08ff: aastore
      // 0900: dup
      // 0901: bipush 3
      // 0902: new net/minecraft/client/animation/Keyframe
      // 0905: dup
      // 0906: ldc 2.2083
      // 0908: fconst_0
      // 0909: fconst_0
      // 090a: ldc 6.3
      // 090c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 090f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0912: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0915: aastore
      // 0916: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0919: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 091c: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.build ()Lnet/minecraft/client/animation/AnimationDefinition;
      // 091f: putstatic com/github/L_Ender/cataclysm/client/animation/Netherite_Monstrosity_Animation.WALK Lnet/minecraft/client/animation/AnimationDefinition;
      // 0922: ldc 2.875
      // 0924: invokestatic net/minecraft/client/animation/AnimationDefinition$Builder.withLength (F)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0927: ldc "roots"
      // 0929: new net/minecraft/client/animation/AnimationChannel
      // 092c: dup
      // 092d: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0930: bipush 5
      // 0931: anewarray 28
      // 0934: dup
      // 0935: bipush 0
      // 0936: new net/minecraft/client/animation/Keyframe
      // 0939: dup
      // 093a: fconst_0
      // 093b: fconst_0
      // 093c: fconst_0
      // 093d: fconst_0
      // 093e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0941: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0944: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0947: aastore
      // 0948: dup
      // 0949: bipush 1
      // 094a: new net/minecraft/client/animation/Keyframe
      // 094d: dup
      // 094e: ldc 0.625
      // 0950: fconst_0
      // 0951: ldc -12.5
      // 0953: fconst_0
      // 0954: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0957: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 095a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 095d: aastore
      // 095e: dup
      // 095f: bipush 2
      // 0960: new net/minecraft/client/animation/Keyframe
      // 0963: dup
      // 0964: ldc 0.9583
      // 0966: fconst_0
      // 0967: fconst_0
      // 0968: fconst_0
      // 0969: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 096c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 096f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0972: aastore
      // 0973: dup
      // 0974: bipush 3
      // 0975: new net/minecraft/client/animation/Keyframe
      // 0978: dup
      // 0979: ldc 1.875
      // 097b: fconst_0
      // 097c: fconst_0
      // 097d: fconst_0
      // 097e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0981: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0984: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0987: aastore
      // 0988: dup
      // 0989: bipush 4
      // 098a: new net/minecraft/client/animation/Keyframe
      // 098d: dup
      // 098e: ldc 2.7917
      // 0990: fconst_0
      // 0991: fconst_0
      // 0992: fconst_0
      // 0993: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0996: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0999: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 099c: aastore
      // 099d: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 09a0: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 09a3: ldc "roots"
      // 09a5: new net/minecraft/client/animation/AnimationChannel
      // 09a8: dup
      // 09a9: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 09ac: bipush 6
      // 09ae: anewarray 28
      // 09b1: dup
      // 09b2: bipush 0
      // 09b3: new net/minecraft/client/animation/Keyframe
      // 09b6: dup
      // 09b7: fconst_0
      // 09b8: fconst_0
      // 09b9: fconst_0
      // 09ba: fconst_0
      // 09bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 09be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09c4: aastore
      // 09c5: dup
      // 09c6: bipush 1
      // 09c7: new net/minecraft/client/animation/Keyframe
      // 09ca: dup
      // 09cb: ldc 0.625
      // 09cd: fconst_0
      // 09ce: fconst_0
      // 09cf: fconst_0
      // 09d0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 09d3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09d6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09d9: aastore
      // 09da: dup
      // 09db: bipush 2
      // 09dc: new net/minecraft/client/animation/Keyframe
      // 09df: dup
      // 09e0: ldc 0.9583
      // 09e2: fconst_0
      // 09e3: ldc -10.0
      // 09e5: fconst_0
      // 09e6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 09e9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09ec: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09ef: aastore
      // 09f0: dup
      // 09f1: bipush 3
      // 09f2: new net/minecraft/client/animation/Keyframe
      // 09f5: dup
      // 09f6: ldc 1.2917
      // 09f8: fconst_0
      // 09f9: ldc -7.0
      // 09fb: fconst_0
      // 09fc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 09ff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a02: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a05: aastore
      // 0a06: dup
      // 0a07: bipush 4
      // 0a08: new net/minecraft/client/animation/Keyframe
      // 0a0b: dup
      // 0a0c: ldc 1.625
      // 0a0e: fconst_0
      // 0a0f: ldc -7.0
      // 0a11: fconst_0
      // 0a12: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a15: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a18: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a1b: aastore
      // 0a1c: dup
      // 0a1d: bipush 5
      // 0a1e: new net/minecraft/client/animation/Keyframe
      // 0a21: dup
      // 0a22: ldc 2.7917
      // 0a24: fconst_0
      // 0a25: fconst_0
      // 0a26: fconst_0
      // 0a27: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a2a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a2d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a30: aastore
      // 0a31: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0a34: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0a37: ldc "lowerbody"
      // 0a39: new net/minecraft/client/animation/AnimationChannel
      // 0a3c: dup
      // 0a3d: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0a40: bipush 3
      // 0a41: anewarray 28
      // 0a44: dup
      // 0a45: bipush 0
      // 0a46: new net/minecraft/client/animation/Keyframe
      // 0a49: dup
      // 0a4a: fconst_0
      // 0a4b: fconst_0
      // 0a4c: fconst_0
      // 0a4d: fconst_0
      // 0a4e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0a51: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a54: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a57: aastore
      // 0a58: dup
      // 0a59: bipush 1
      // 0a5a: new net/minecraft/client/animation/Keyframe
      // 0a5d: dup
      // 0a5e: ldc 1.875
      // 0a60: fconst_0
      // 0a61: fconst_0
      // 0a62: fconst_0
      // 0a63: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0a66: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a69: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a6c: aastore
      // 0a6d: dup
      // 0a6e: bipush 2
      // 0a6f: new net/minecraft/client/animation/Keyframe
      // 0a72: dup
      // 0a73: ldc 2.7917
      // 0a75: fconst_0
      // 0a76: fconst_0
      // 0a77: fconst_0
      // 0a78: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0a7b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a7e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a81: aastore
      // 0a82: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0a85: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0a88: ldc "upperbody"
      // 0a8a: new net/minecraft/client/animation/AnimationChannel
      // 0a8d: dup
      // 0a8e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0a91: bipush 5
      // 0a92: anewarray 28
      // 0a95: dup
      // 0a96: bipush 0
      // 0a97: new net/minecraft/client/animation/Keyframe
      // 0a9a: dup
      // 0a9b: fconst_0
      // 0a9c: fconst_0
      // 0a9d: fconst_0
      // 0a9e: fconst_0
      // 0a9f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0aa2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0aa5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0aa8: aastore
      // 0aa9: dup
      // 0aaa: bipush 1
      // 0aab: new net/minecraft/client/animation/Keyframe
      // 0aae: dup
      // 0aaf: ldc 0.625
      // 0ab1: ldc -27.5
      // 0ab3: fconst_0
      // 0ab4: fconst_0
      // 0ab5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ab8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0abb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0abe: aastore
      // 0abf: dup
      // 0ac0: bipush 2
      // 0ac1: new net/minecraft/client/animation/Keyframe
      // 0ac4: dup
      // 0ac5: ldc 0.9167
      // 0ac7: ldc 52.5
      // 0ac9: fconst_0
      // 0aca: fconst_0
      // 0acb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ace: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ad1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ad4: aastore
      // 0ad5: dup
      // 0ad6: bipush 3
      // 0ad7: new net/minecraft/client/animation/Keyframe
      // 0ada: dup
      // 0adb: ldc 1.875
      // 0add: ldc 52.5
      // 0adf: fconst_0
      // 0ae0: fconst_0
      // 0ae1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ae4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ae7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0aea: aastore
      // 0aeb: dup
      // 0aec: bipush 4
      // 0aed: new net/minecraft/client/animation/Keyframe
      // 0af0: dup
      // 0af1: ldc 2.875
      // 0af3: fconst_0
      // 0af4: fconst_0
      // 0af5: fconst_0
      // 0af6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0af9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0afc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0aff: aastore
      // 0b00: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0b03: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0b06: ldc "upperbody"
      // 0b08: new net/minecraft/client/animation/AnimationChannel
      // 0b0b: dup
      // 0b0c: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0b0f: bipush 6
      // 0b11: anewarray 28
      // 0b14: dup
      // 0b15: bipush 0
      // 0b16: new net/minecraft/client/animation/Keyframe
      // 0b19: dup
      // 0b1a: fconst_0
      // 0b1b: fconst_0
      // 0b1c: fconst_0
      // 0b1d: fconst_0
      // 0b1e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b21: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b24: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b27: aastore
      // 0b28: dup
      // 0b29: bipush 1
      // 0b2a: new net/minecraft/client/animation/Keyframe
      // 0b2d: dup
      // 0b2e: ldc 0.7917
      // 0b30: fconst_0
      // 0b31: fconst_0
      // 0b32: fconst_0
      // 0b33: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b36: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b39: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b3c: aastore
      // 0b3d: dup
      // 0b3e: bipush 2
      // 0b3f: new net/minecraft/client/animation/Keyframe
      // 0b42: dup
      // 0b43: ldc 1.125
      // 0b45: fconst_0
      // 0b46: ldc -2.0
      // 0b48: fconst_0
      // 0b49: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b4c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b4f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b52: aastore
      // 0b53: dup
      // 0b54: bipush 3
      // 0b55: new net/minecraft/client/animation/Keyframe
      // 0b58: dup
      // 0b59: ldc 1.375
      // 0b5b: fconst_0
      // 0b5c: fconst_0
      // 0b5d: fconst_0
      // 0b5e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b61: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b64: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b67: aastore
      // 0b68: dup
      // 0b69: bipush 4
      // 0b6a: new net/minecraft/client/animation/Keyframe
      // 0b6d: dup
      // 0b6e: ldc 1.875
      // 0b70: fconst_0
      // 0b71: fconst_0
      // 0b72: fconst_0
      // 0b73: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b76: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b79: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b7c: aastore
      // 0b7d: dup
      // 0b7e: bipush 5
      // 0b7f: new net/minecraft/client/animation/Keyframe
      // 0b82: dup
      // 0b83: ldc 2.875
      // 0b85: fconst_0
      // 0b86: fconst_0
      // 0b87: fconst_0
      // 0b88: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b8b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b8e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b91: aastore
      // 0b92: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0b95: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0b98: ldc "head"
      // 0b9a: new net/minecraft/client/animation/AnimationChannel
      // 0b9d: dup
      // 0b9e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0ba1: bipush 6
      // 0ba3: anewarray 28
      // 0ba6: dup
      // 0ba7: bipush 0
      // 0ba8: new net/minecraft/client/animation/Keyframe
      // 0bab: dup
      // 0bac: fconst_0
      // 0bad: fconst_0
      // 0bae: fconst_0
      // 0baf: fconst_0
      // 0bb0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0bb3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0bb6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bb9: aastore
      // 0bba: dup
      // 0bbb: bipush 1
      // 0bbc: new net/minecraft/client/animation/Keyframe
      // 0bbf: dup
      // 0bc0: ldc 0.5417
      // 0bc2: ldc -22.5131
      // 0bc4: ldc -4.6691
      // 0bc6: ldc -7.7096
      // 0bc8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0bcb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0bce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bd1: aastore
      // 0bd2: dup
      // 0bd3: bipush 2
      // 0bd4: new net/minecraft/client/animation/Keyframe
      // 0bd7: dup
      // 0bd8: ldc 1.0417
      // 0bda: ldc -37.5
      // 0bdc: fconst_0
      // 0bdd: fconst_0
      // 0bde: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0be1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0be4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0be7: aastore
      // 0be8: dup
      // 0be9: bipush 3
      // 0bea: new net/minecraft/client/animation/Keyframe
      // 0bed: dup
      // 0bee: ldc 1.375
      // 0bf0: ldc -37.5
      // 0bf2: fconst_0
      // 0bf3: fconst_0
      // 0bf4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0bf7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0bfa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bfd: aastore
      // 0bfe: dup
      // 0bff: bipush 4
      // 0c00: new net/minecraft/client/animation/Keyframe
      // 0c03: dup
      // 0c04: ldc 1.875
      // 0c06: ldc -37.5
      // 0c08: fconst_0
      // 0c09: fconst_0
      // 0c0a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c0d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c10: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c13: aastore
      // 0c14: dup
      // 0c15: bipush 5
      // 0c16: new net/minecraft/client/animation/Keyframe
      // 0c19: dup
      // 0c1a: ldc 2.7917
      // 0c1c: fconst_0
      // 0c1d: fconst_0
      // 0c1e: fconst_0
      // 0c1f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c22: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c25: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c28: aastore
      // 0c29: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0c2c: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0c2f: ldc "head"
      // 0c31: new net/minecraft/client/animation/AnimationChannel
      // 0c34: dup
      // 0c35: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0c38: bipush 5
      // 0c39: anewarray 28
      // 0c3c: dup
      // 0c3d: bipush 0
      // 0c3e: new net/minecraft/client/animation/Keyframe
      // 0c41: dup
      // 0c42: fconst_0
      // 0c43: fconst_0
      // 0c44: fconst_0
      // 0c45: fconst_0
      // 0c46: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0c49: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c4c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c4f: aastore
      // 0c50: dup
      // 0c51: bipush 1
      // 0c52: new net/minecraft/client/animation/Keyframe
      // 0c55: dup
      // 0c56: ldc 0.625
      // 0c58: fconst_0
      // 0c59: ldc 5.0
      // 0c5b: fconst_0
      // 0c5c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0c5f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c62: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c65: aastore
      // 0c66: dup
      // 0c67: bipush 2
      // 0c68: new net/minecraft/client/animation/Keyframe
      // 0c6b: dup
      // 0c6c: ldc 1.125
      // 0c6e: fconst_0
      // 0c6f: fconst_2
      // 0c70: fconst_0
      // 0c71: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0c74: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c77: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c7a: aastore
      // 0c7b: dup
      // 0c7c: bipush 3
      // 0c7d: new net/minecraft/client/animation/Keyframe
      // 0c80: dup
      // 0c81: ldc 1.875
      // 0c83: fconst_0
      // 0c84: fconst_2
      // 0c85: fconst_0
      // 0c86: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0c89: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c8c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c8f: aastore
      // 0c90: dup
      // 0c91: bipush 4
      // 0c92: new net/minecraft/client/animation/Keyframe
      // 0c95: dup
      // 0c96: ldc 2.7917
      // 0c98: fconst_0
      // 0c99: fconst_0
      // 0c9a: fconst_0
      // 0c9b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0c9e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ca1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ca4: aastore
      // 0ca5: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0ca8: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0cab: ldc "jaw"
      // 0cad: new net/minecraft/client/animation/AnimationChannel
      // 0cb0: dup
      // 0cb1: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0cb4: bipush 5
      // 0cb5: anewarray 28
      // 0cb8: dup
      // 0cb9: bipush 0
      // 0cba: new net/minecraft/client/animation/Keyframe
      // 0cbd: dup
      // 0cbe: fconst_0
      // 0cbf: fconst_0
      // 0cc0: fconst_0
      // 0cc1: fconst_0
      // 0cc2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0cc5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0cc8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ccb: aastore
      // 0ccc: dup
      // 0ccd: bipush 1
      // 0cce: new net/minecraft/client/animation/Keyframe
      // 0cd1: dup
      // 0cd2: ldc 0.625
      // 0cd4: ldc 2.4621
      // 0cd6: ldc -0.434
      // 0cd8: ldc 9.9907
      // 0cda: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0cdd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ce0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ce3: aastore
      // 0ce4: dup
      // 0ce5: bipush 2
      // 0ce6: new net/minecraft/client/animation/Keyframe
      // 0ce9: dup
      // 0cea: ldc 1.125
      // 0cec: ldc 27.5
      // 0cee: fconst_0
      // 0cef: fconst_0
      // 0cf0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0cf3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0cf6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0cf9: aastore
      // 0cfa: dup
      // 0cfb: bipush 3
      // 0cfc: new net/minecraft/client/animation/Keyframe
      // 0cff: dup
      // 0d00: ldc 1.875
      // 0d02: ldc 27.5
      // 0d04: fconst_0
      // 0d05: fconst_0
      // 0d06: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d09: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d0c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d0f: aastore
      // 0d10: dup
      // 0d11: bipush 4
      // 0d12: new net/minecraft/client/animation/Keyframe
      // 0d15: dup
      // 0d16: ldc 2.7917
      // 0d18: fconst_0
      // 0d19: fconst_0
      // 0d1a: fconst_0
      // 0d1b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d1e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d21: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d24: aastore
      // 0d25: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0d28: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0d2b: ldc "leftarm"
      // 0d2d: new net/minecraft/client/animation/AnimationChannel
      // 0d30: dup
      // 0d31: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0d34: bipush 5
      // 0d35: anewarray 28
      // 0d38: dup
      // 0d39: bipush 0
      // 0d3a: new net/minecraft/client/animation/Keyframe
      // 0d3d: dup
      // 0d3e: fconst_0
      // 0d3f: fconst_0
      // 0d40: fconst_0
      // 0d41: fconst_0
      // 0d42: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d45: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d48: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d4b: aastore
      // 0d4c: dup
      // 0d4d: bipush 1
      // 0d4e: new net/minecraft/client/animation/Keyframe
      // 0d51: dup
      // 0d52: ldc 0.625
      // 0d54: ldc -142.448
      // 0d56: ldc -31.7449
      // 0d58: ldc -3.357
      // 0d5a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d5d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d60: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d63: aastore
      // 0d64: dup
      // 0d65: bipush 2
      // 0d66: new net/minecraft/client/animation/Keyframe
      // 0d69: dup
      // 0d6a: fconst_1
      // 0d6b: ldc -111.3411
      // 0d6d: ldc 9.9509
      // 0d6f: ldc -9.0921
      // 0d71: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d74: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d77: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d7a: aastore
      // 0d7b: dup
      // 0d7c: bipush 3
      // 0d7d: new net/minecraft/client/animation/Keyframe
      // 0d80: dup
      // 0d81: ldc 1.875
      // 0d83: ldc -111.3411
      // 0d85: ldc 9.9509
      // 0d87: ldc -9.0921
      // 0d89: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d8c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d8f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d92: aastore
      // 0d93: dup
      // 0d94: bipush 4
      // 0d95: new net/minecraft/client/animation/Keyframe
      // 0d98: dup
      // 0d99: ldc 2.875
      // 0d9b: fconst_0
      // 0d9c: fconst_0
      // 0d9d: fconst_0
      // 0d9e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0da1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0da4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0da7: aastore
      // 0da8: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0dab: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0dae: ldc "leftarm"
      // 0db0: new net/minecraft/client/animation/AnimationChannel
      // 0db3: dup
      // 0db4: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0db7: bipush 6
      // 0db9: anewarray 28
      // 0dbc: dup
      // 0dbd: bipush 0
      // 0dbe: new net/minecraft/client/animation/Keyframe
      // 0dc1: dup
      // 0dc2: fconst_0
      // 0dc3: fconst_0
      // 0dc4: fconst_0
      // 0dc5: fconst_0
      // 0dc6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0dc9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0dcc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0dcf: aastore
      // 0dd0: dup
      // 0dd1: bipush 1
      // 0dd2: new net/minecraft/client/animation/Keyframe
      // 0dd5: dup
      // 0dd6: ldc 0.625
      // 0dd8: fconst_0
      // 0dd9: ldc 15.0
      // 0ddb: ldc -12.4
      // 0ddd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0de0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0de3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0de6: aastore
      // 0de7: dup
      // 0de8: bipush 2
      // 0de9: new net/minecraft/client/animation/Keyframe
      // 0dec: dup
      // 0ded: ldc 1.0833
      // 0def: fconst_0
      // 0df0: fconst_0
      // 0df1: ldc -20.0
      // 0df3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0df6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0df9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0dfc: aastore
      // 0dfd: dup
      // 0dfe: bipush 3
      // 0dff: new net/minecraft/client/animation/Keyframe
      // 0e02: dup
      // 0e03: ldc 1.3333
      // 0e05: fconst_0
      // 0e06: fconst_0
      // 0e07: ldc -18.0
      // 0e09: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0e0c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e0f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e12: aastore
      // 0e13: dup
      // 0e14: bipush 4
      // 0e15: new net/minecraft/client/animation/Keyframe
      // 0e18: dup
      // 0e19: ldc 1.875
      // 0e1b: fconst_0
      // 0e1c: fconst_0
      // 0e1d: ldc -18.0
      // 0e1f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0e22: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e25: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e28: aastore
      // 0e29: dup
      // 0e2a: bipush 5
      // 0e2b: new net/minecraft/client/animation/Keyframe
      // 0e2e: dup
      // 0e2f: ldc 2.875
      // 0e31: fconst_0
      // 0e32: fconst_0
      // 0e33: fconst_0
      // 0e34: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0e37: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e3a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e3d: aastore
      // 0e3e: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0e41: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0e44: ldc "leftarm2"
      // 0e46: new net/minecraft/client/animation/AnimationChannel
      // 0e49: dup
      // 0e4a: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0e4d: bipush 5
      // 0e4e: anewarray 28
      // 0e51: dup
      // 0e52: bipush 0
      // 0e53: new net/minecraft/client/animation/Keyframe
      // 0e56: dup
      // 0e57: fconst_0
      // 0e58: fconst_0
      // 0e59: fconst_0
      // 0e5a: fconst_0
      // 0e5b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e5e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e61: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e64: aastore
      // 0e65: dup
      // 0e66: bipush 1
      // 0e67: new net/minecraft/client/animation/Keyframe
      // 0e6a: dup
      // 0e6b: ldc 0.625
      // 0e6d: ldc -52.5
      // 0e6f: fconst_0
      // 0e70: fconst_0
      // 0e71: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e74: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e77: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e7a: aastore
      // 0e7b: dup
      // 0e7c: bipush 2
      // 0e7d: new net/minecraft/client/animation/Keyframe
      // 0e80: dup
      // 0e81: ldc 1.0833
      // 0e83: ldc -14.5182
      // 0e85: ldc 4.7511
      // 0e87: ldc 11.5752
      // 0e89: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e8c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e8f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e92: aastore
      // 0e93: dup
      // 0e94: bipush 3
      // 0e95: new net/minecraft/client/animation/Keyframe
      // 0e98: dup
      // 0e99: ldc 1.875
      // 0e9b: ldc -14.5182
      // 0e9d: ldc 4.7511
      // 0e9f: ldc 11.5752
      // 0ea1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ea4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ea7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0eaa: aastore
      // 0eab: dup
      // 0eac: bipush 4
      // 0ead: new net/minecraft/client/animation/Keyframe
      // 0eb0: dup
      // 0eb1: ldc 2.7917
      // 0eb3: fconst_0
      // 0eb4: fconst_0
      // 0eb5: fconst_0
      // 0eb6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0eb9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ebc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ebf: aastore
      // 0ec0: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0ec3: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0ec6: ldc "lefthand"
      // 0ec8: new net/minecraft/client/animation/AnimationChannel
      // 0ecb: dup
      // 0ecc: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0ecf: bipush 3
      // 0ed0: anewarray 28
      // 0ed3: dup
      // 0ed4: bipush 0
      // 0ed5: new net/minecraft/client/animation/Keyframe
      // 0ed8: dup
      // 0ed9: fconst_0
      // 0eda: fconst_0
      // 0edb: fconst_0
      // 0edc: fconst_0
      // 0edd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ee0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ee3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ee6: aastore
      // 0ee7: dup
      // 0ee8: bipush 1
      // 0ee9: new net/minecraft/client/animation/Keyframe
      // 0eec: dup
      // 0eed: ldc 1.875
      // 0eef: fconst_0
      // 0ef0: fconst_0
      // 0ef1: fconst_0
      // 0ef2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ef5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ef8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0efb: aastore
      // 0efc: dup
      // 0efd: bipush 2
      // 0efe: new net/minecraft/client/animation/Keyframe
      // 0f01: dup
      // 0f02: ldc 2.7917
      // 0f04: fconst_0
      // 0f05: fconst_0
      // 0f06: fconst_0
      // 0f07: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f0a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f0d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f10: aastore
      // 0f11: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0f14: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0f17: ldc "leftfinger1"
      // 0f19: new net/minecraft/client/animation/AnimationChannel
      // 0f1c: dup
      // 0f1d: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0f20: bipush 4
      // 0f21: anewarray 28
      // 0f24: dup
      // 0f25: bipush 0
      // 0f26: new net/minecraft/client/animation/Keyframe
      // 0f29: dup
      // 0f2a: fconst_0
      // 0f2b: fconst_0
      // 0f2c: fconst_0
      // 0f2d: fconst_0
      // 0f2e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f31: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f34: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f37: aastore
      // 0f38: dup
      // 0f39: bipush 1
      // 0f3a: new net/minecraft/client/animation/Keyframe
      // 0f3d: dup
      // 0f3e: ldc 0.625
      // 0f40: ldc -12.5
      // 0f42: fconst_0
      // 0f43: ldc 32.5
      // 0f45: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f48: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f4b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f4e: aastore
      // 0f4f: dup
      // 0f50: bipush 2
      // 0f51: new net/minecraft/client/animation/Keyframe
      // 0f54: dup
      // 0f55: ldc 1.875
      // 0f57: ldc -12.5
      // 0f59: fconst_0
      // 0f5a: ldc 32.5
      // 0f5c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f5f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f62: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f65: aastore
      // 0f66: dup
      // 0f67: bipush 3
      // 0f68: new net/minecraft/client/animation/Keyframe
      // 0f6b: dup
      // 0f6c: ldc 2.7917
      // 0f6e: fconst_0
      // 0f6f: fconst_0
      // 0f70: fconst_0
      // 0f71: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f74: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f77: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f7a: aastore
      // 0f7b: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0f7e: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0f81: ldc "leftfinger2"
      // 0f83: new net/minecraft/client/animation/AnimationChannel
      // 0f86: dup
      // 0f87: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0f8a: bipush 4
      // 0f8b: anewarray 28
      // 0f8e: dup
      // 0f8f: bipush 0
      // 0f90: new net/minecraft/client/animation/Keyframe
      // 0f93: dup
      // 0f94: fconst_0
      // 0f95: fconst_0
      // 0f96: fconst_0
      // 0f97: fconst_0
      // 0f98: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f9b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f9e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fa1: aastore
      // 0fa2: dup
      // 0fa3: bipush 1
      // 0fa4: new net/minecraft/client/animation/Keyframe
      // 0fa7: dup
      // 0fa8: ldc 0.625
      // 0faa: ldc 12.5
      // 0fac: fconst_0
      // 0fad: ldc 32.5
      // 0faf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fb2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fb5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fb8: aastore
      // 0fb9: dup
      // 0fba: bipush 2
      // 0fbb: new net/minecraft/client/animation/Keyframe
      // 0fbe: dup
      // 0fbf: ldc 1.875
      // 0fc1: ldc 12.5
      // 0fc3: fconst_0
      // 0fc4: ldc 32.5
      // 0fc6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fc9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fcc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fcf: aastore
      // 0fd0: dup
      // 0fd1: bipush 3
      // 0fd2: new net/minecraft/client/animation/Keyframe
      // 0fd5: dup
      // 0fd6: ldc 2.7917
      // 0fd8: fconst_0
      // 0fd9: fconst_0
      // 0fda: fconst_0
      // 0fdb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fde: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fe1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fe4: aastore
      // 0fe5: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0fe8: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0feb: ldc "leftfinger3"
      // 0fed: new net/minecraft/client/animation/AnimationChannel
      // 0ff0: dup
      // 0ff1: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0ff4: bipush 4
      // 0ff5: anewarray 28
      // 0ff8: dup
      // 0ff9: bipush 0
      // 0ffa: new net/minecraft/client/animation/Keyframe
      // 0ffd: dup
      // 0ffe: fconst_0
      // 0fff: fconst_0
      // 1000: fconst_0
      // 1001: fconst_0
      // 1002: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1005: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1008: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 100b: aastore
      // 100c: dup
      // 100d: bipush 1
      // 100e: new net/minecraft/client/animation/Keyframe
      // 1011: dup
      // 1012: ldc 0.625
      // 1014: fconst_0
      // 1015: fconst_0
      // 1016: ldc -32.5
      // 1018: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 101b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 101e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1021: aastore
      // 1022: dup
      // 1023: bipush 2
      // 1024: new net/minecraft/client/animation/Keyframe
      // 1027: dup
      // 1028: ldc 1.875
      // 102a: fconst_0
      // 102b: fconst_0
      // 102c: ldc -32.5
      // 102e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1031: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1034: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1037: aastore
      // 1038: dup
      // 1039: bipush 3
      // 103a: new net/minecraft/client/animation/Keyframe
      // 103d: dup
      // 103e: ldc 2.7917
      // 1040: fconst_0
      // 1041: fconst_0
      // 1042: fconst_0
      // 1043: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1046: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1049: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 104c: aastore
      // 104d: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1050: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1053: ldc "rightarm"
      // 1055: new net/minecraft/client/animation/AnimationChannel
      // 1058: dup
      // 1059: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 105c: bipush 5
      // 105d: anewarray 28
      // 1060: dup
      // 1061: bipush 0
      // 1062: new net/minecraft/client/animation/Keyframe
      // 1065: dup
      // 1066: fconst_0
      // 1067: fconst_0
      // 1068: fconst_0
      // 1069: fconst_0
      // 106a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 106d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1070: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1073: aastore
      // 1074: dup
      // 1075: bipush 1
      // 1076: new net/minecraft/client/animation/Keyframe
      // 1079: dup
      // 107a: ldc 0.625
      // 107c: ldc -142.5206
      // 107e: ldc 26.4438
      // 1080: ldc -1.9855
      // 1082: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1085: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1088: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 108b: aastore
      // 108c: dup
      // 108d: bipush 2
      // 108e: new net/minecraft/client/animation/Keyframe
      // 1091: dup
      // 1092: fconst_1
      // 1093: ldc -111.3411
      // 1095: ldc -9.9509
      // 1097: ldc 9.0921
      // 1099: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 109c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 109f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10a2: aastore
      // 10a3: dup
      // 10a4: bipush 3
      // 10a5: new net/minecraft/client/animation/Keyframe
      // 10a8: dup
      // 10a9: ldc 1.875
      // 10ab: ldc -111.3411
      // 10ad: ldc -9.9509
      // 10af: ldc 9.0921
      // 10b1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10b4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10b7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10ba: aastore
      // 10bb: dup
      // 10bc: bipush 4
      // 10bd: new net/minecraft/client/animation/Keyframe
      // 10c0: dup
      // 10c1: ldc 2.875
      // 10c3: fconst_0
      // 10c4: fconst_0
      // 10c5: fconst_0
      // 10c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10cf: aastore
      // 10d0: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 10d3: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 10d6: ldc "rightarm"
      // 10d8: new net/minecraft/client/animation/AnimationChannel
      // 10db: dup
      // 10dc: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 10df: bipush 6
      // 10e1: anewarray 28
      // 10e4: dup
      // 10e5: bipush 0
      // 10e6: new net/minecraft/client/animation/Keyframe
      // 10e9: dup
      // 10ea: fconst_0
      // 10eb: fconst_0
      // 10ec: fconst_0
      // 10ed: fconst_0
      // 10ee: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 10f1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10f4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10f7: aastore
      // 10f8: dup
      // 10f9: bipush 1
      // 10fa: new net/minecraft/client/animation/Keyframe
      // 10fd: dup
      // 10fe: ldc 0.625
      // 1100: fconst_0
      // 1101: ldc 15.0
      // 1103: ldc -12.4
      // 1105: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1108: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 110b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 110e: aastore
      // 110f: dup
      // 1110: bipush 2
      // 1111: new net/minecraft/client/animation/Keyframe
      // 1114: dup
      // 1115: ldc 1.0833
      // 1117: fconst_0
      // 1118: fconst_0
      // 1119: ldc -20.0
      // 111b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 111e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1121: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1124: aastore
      // 1125: dup
      // 1126: bipush 3
      // 1127: new net/minecraft/client/animation/Keyframe
      // 112a: dup
      // 112b: ldc 1.3333
      // 112d: fconst_0
      // 112e: fconst_0
      // 112f: ldc -18.0
      // 1131: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1134: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1137: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 113a: aastore
      // 113b: dup
      // 113c: bipush 4
      // 113d: new net/minecraft/client/animation/Keyframe
      // 1140: dup
      // 1141: ldc 1.875
      // 1143: fconst_0
      // 1144: fconst_0
      // 1145: ldc -18.0
      // 1147: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 114a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 114d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1150: aastore
      // 1151: dup
      // 1152: bipush 5
      // 1153: new net/minecraft/client/animation/Keyframe
      // 1156: dup
      // 1157: ldc 2.875
      // 1159: fconst_0
      // 115a: fconst_0
      // 115b: fconst_0
      // 115c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 115f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1162: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1165: aastore
      // 1166: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1169: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 116c: ldc "rightarm2"
      // 116e: new net/minecraft/client/animation/AnimationChannel
      // 1171: dup
      // 1172: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1175: bipush 5
      // 1176: anewarray 28
      // 1179: dup
      // 117a: bipush 0
      // 117b: new net/minecraft/client/animation/Keyframe
      // 117e: dup
      // 117f: fconst_0
      // 1180: fconst_0
      // 1181: fconst_0
      // 1182: fconst_0
      // 1183: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1186: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1189: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 118c: aastore
      // 118d: dup
      // 118e: bipush 1
      // 118f: new net/minecraft/client/animation/Keyframe
      // 1192: dup
      // 1193: ldc 0.625
      // 1195: ldc -57.5
      // 1197: fconst_0
      // 1198: fconst_0
      // 1199: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 119c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 119f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11a2: aastore
      // 11a3: dup
      // 11a4: bipush 2
      // 11a5: new net/minecraft/client/animation/Keyframe
      // 11a8: dup
      // 11a9: ldc 1.0833
      // 11ab: ldc -14.5182
      // 11ad: ldc -4.7511
      // 11af: ldc -11.5752
      // 11b1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11b4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11b7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11ba: aastore
      // 11bb: dup
      // 11bc: bipush 3
      // 11bd: new net/minecraft/client/animation/Keyframe
      // 11c0: dup
      // 11c1: ldc 1.875
      // 11c3: ldc -14.5182
      // 11c5: ldc -4.7511
      // 11c7: ldc -11.5752
      // 11c9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11cc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11cf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11d2: aastore
      // 11d3: dup
      // 11d4: bipush 4
      // 11d5: new net/minecraft/client/animation/Keyframe
      // 11d8: dup
      // 11d9: ldc 2.7917
      // 11db: fconst_0
      // 11dc: fconst_0
      // 11dd: fconst_0
      // 11de: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11e7: aastore
      // 11e8: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 11eb: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 11ee: ldc "righthand"
      // 11f0: new net/minecraft/client/animation/AnimationChannel
      // 11f3: dup
      // 11f4: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 11f7: bipush 3
      // 11f8: anewarray 28
      // 11fb: dup
      // 11fc: bipush 0
      // 11fd: new net/minecraft/client/animation/Keyframe
      // 1200: dup
      // 1201: fconst_0
      // 1202: fconst_0
      // 1203: fconst_0
      // 1204: fconst_0
      // 1205: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1208: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 120b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 120e: aastore
      // 120f: dup
      // 1210: bipush 1
      // 1211: new net/minecraft/client/animation/Keyframe
      // 1214: dup
      // 1215: ldc 1.875
      // 1217: fconst_0
      // 1218: fconst_0
      // 1219: fconst_0
      // 121a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 121d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1220: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1223: aastore
      // 1224: dup
      // 1225: bipush 2
      // 1226: new net/minecraft/client/animation/Keyframe
      // 1229: dup
      // 122a: ldc 2.7917
      // 122c: fconst_0
      // 122d: fconst_0
      // 122e: fconst_0
      // 122f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1232: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1235: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1238: aastore
      // 1239: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 123c: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 123f: ldc "rightfinger1"
      // 1241: new net/minecraft/client/animation/AnimationChannel
      // 1244: dup
      // 1245: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1248: bipush 4
      // 1249: anewarray 28
      // 124c: dup
      // 124d: bipush 0
      // 124e: new net/minecraft/client/animation/Keyframe
      // 1251: dup
      // 1252: fconst_0
      // 1253: fconst_0
      // 1254: fconst_0
      // 1255: fconst_0
      // 1256: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1259: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 125c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 125f: aastore
      // 1260: dup
      // 1261: bipush 1
      // 1262: new net/minecraft/client/animation/Keyframe
      // 1265: dup
      // 1266: ldc 0.625
      // 1268: ldc -12.5
      // 126a: fconst_0
      // 126b: ldc -32.5
      // 126d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1270: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1273: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1276: aastore
      // 1277: dup
      // 1278: bipush 2
      // 1279: new net/minecraft/client/animation/Keyframe
      // 127c: dup
      // 127d: ldc 1.875
      // 127f: ldc -12.5
      // 1281: fconst_0
      // 1282: ldc -32.5
      // 1284: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1287: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 128a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 128d: aastore
      // 128e: dup
      // 128f: bipush 3
      // 1290: new net/minecraft/client/animation/Keyframe
      // 1293: dup
      // 1294: ldc 2.7917
      // 1296: fconst_0
      // 1297: fconst_0
      // 1298: fconst_0
      // 1299: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 129c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 129f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12a2: aastore
      // 12a3: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 12a6: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 12a9: ldc "rightfinger2"
      // 12ab: new net/minecraft/client/animation/AnimationChannel
      // 12ae: dup
      // 12af: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 12b2: bipush 4
      // 12b3: anewarray 28
      // 12b6: dup
      // 12b7: bipush 0
      // 12b8: new net/minecraft/client/animation/Keyframe
      // 12bb: dup
      // 12bc: fconst_0
      // 12bd: fconst_0
      // 12be: fconst_0
      // 12bf: fconst_0
      // 12c0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 12c3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12c6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12c9: aastore
      // 12ca: dup
      // 12cb: bipush 1
      // 12cc: new net/minecraft/client/animation/Keyframe
      // 12cf: dup
      // 12d0: ldc 0.625
      // 12d2: ldc 12.5
      // 12d4: fconst_0
      // 12d5: ldc -32.5
      // 12d7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 12da: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12dd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12e0: aastore
      // 12e1: dup
      // 12e2: bipush 2
      // 12e3: new net/minecraft/client/animation/Keyframe
      // 12e6: dup
      // 12e7: ldc 1.875
      // 12e9: ldc 12.5
      // 12eb: fconst_0
      // 12ec: ldc -32.5
      // 12ee: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 12f1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12f4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12f7: aastore
      // 12f8: dup
      // 12f9: bipush 3
      // 12fa: new net/minecraft/client/animation/Keyframe
      // 12fd: dup
      // 12fe: ldc 2.7917
      // 1300: fconst_0
      // 1301: fconst_0
      // 1302: fconst_0
      // 1303: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1306: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1309: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 130c: aastore
      // 130d: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1310: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1313: ldc "rightfinger3"
      // 1315: new net/minecraft/client/animation/AnimationChannel
      // 1318: dup
      // 1319: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 131c: bipush 4
      // 131d: anewarray 28
      // 1320: dup
      // 1321: bipush 0
      // 1322: new net/minecraft/client/animation/Keyframe
      // 1325: dup
      // 1326: fconst_0
      // 1327: fconst_0
      // 1328: fconst_0
      // 1329: fconst_0
      // 132a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 132d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1330: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1333: aastore
      // 1334: dup
      // 1335: bipush 1
      // 1336: new net/minecraft/client/animation/Keyframe
      // 1339: dup
      // 133a: ldc 0.625
      // 133c: fconst_0
      // 133d: fconst_0
      // 133e: ldc 32.5
      // 1340: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1343: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1346: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1349: aastore
      // 134a: dup
      // 134b: bipush 2
      // 134c: new net/minecraft/client/animation/Keyframe
      // 134f: dup
      // 1350: ldc 1.875
      // 1352: fconst_0
      // 1353: fconst_0
      // 1354: ldc 32.5
      // 1356: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1359: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 135c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 135f: aastore
      // 1360: dup
      // 1361: bipush 3
      // 1362: new net/minecraft/client/animation/Keyframe
      // 1365: dup
      // 1366: ldc 2.7917
      // 1368: fconst_0
      // 1369: fconst_0
      // 136a: fconst_0
      // 136b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 136e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1371: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1374: aastore
      // 1375: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1378: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 137b: ldc "rightleg"
      // 137d: new net/minecraft/client/animation/AnimationChannel
      // 1380: dup
      // 1381: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1384: bipush 5
      // 1385: anewarray 28
      // 1388: dup
      // 1389: bipush 0
      // 138a: new net/minecraft/client/animation/Keyframe
      // 138d: dup
      // 138e: fconst_0
      // 138f: fconst_0
      // 1390: fconst_0
      // 1391: fconst_0
      // 1392: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1395: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1398: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 139b: aastore
      // 139c: dup
      // 139d: bipush 1
      // 139e: new net/minecraft/client/animation/Keyframe
      // 13a1: dup
      // 13a2: ldc 0.5417
      // 13a4: fconst_0
      // 13a5: ldc 17.5
      // 13a7: fconst_0
      // 13a8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 13ab: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13ae: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13b1: aastore
      // 13b2: dup
      // 13b3: bipush 2
      // 13b4: new net/minecraft/client/animation/Keyframe
      // 13b7: dup
      // 13b8: ldc 1.125
      // 13ba: fconst_0
      // 13bb: fconst_0
      // 13bc: fconst_0
      // 13bd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 13c0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13c3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13c6: aastore
      // 13c7: dup
      // 13c8: bipush 3
      // 13c9: new net/minecraft/client/animation/Keyframe
      // 13cc: dup
      // 13cd: ldc 1.875
      // 13cf: fconst_0
      // 13d0: fconst_0
      // 13d1: fconst_0
      // 13d2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 13d5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13d8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13db: aastore
      // 13dc: dup
      // 13dd: bipush 4
      // 13de: new net/minecraft/client/animation/Keyframe
      // 13e1: dup
      // 13e2: ldc 2.7917
      // 13e4: fconst_0
      // 13e5: fconst_0
      // 13e6: fconst_0
      // 13e7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 13ea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13ed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13f0: aastore
      // 13f1: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 13f4: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 13f7: ldc "rightleg"
      // 13f9: new net/minecraft/client/animation/AnimationChannel
      // 13fc: dup
      // 13fd: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1400: bipush 6
      // 1402: anewarray 28
      // 1405: dup
      // 1406: bipush 0
      // 1407: new net/minecraft/client/animation/Keyframe
      // 140a: dup
      // 140b: fconst_0
      // 140c: fconst_0
      // 140d: fconst_0
      // 140e: fconst_0
      // 140f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1412: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1415: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1418: aastore
      // 1419: dup
      // 141a: bipush 1
      // 141b: new net/minecraft/client/animation/Keyframe
      // 141e: dup
      // 141f: ldc 0.6667
      // 1421: fconst_0
      // 1422: fconst_0
      // 1423: ldc -8.0
      // 1425: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1428: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 142b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 142e: aastore
      // 142f: dup
      // 1430: bipush 2
      // 1431: new net/minecraft/client/animation/Keyframe
      // 1434: dup
      // 1435: ldc 0.8333
      // 1437: fconst_0
      // 1438: ldc 7.07
      // 143a: ldc -11.85
      // 143c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 143f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1442: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1445: aastore
      // 1446: dup
      // 1447: bipush 3
      // 1448: new net/minecraft/client/animation/Keyframe
      // 144b: dup
      // 144c: fconst_1
      // 144d: fconst_0
      // 144e: ldc 7.0
      // 1450: ldc -19.0
      // 1452: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1455: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1458: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 145b: aastore
      // 145c: dup
      // 145d: bipush 4
      // 145e: new net/minecraft/client/animation/Keyframe
      // 1461: dup
      // 1462: ldc 1.875
      // 1464: fconst_0
      // 1465: ldc 7.0
      // 1467: ldc -19.0
      // 1469: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 146c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 146f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1472: aastore
      // 1473: dup
      // 1474: bipush 5
      // 1475: new net/minecraft/client/animation/Keyframe
      // 1478: dup
      // 1479: ldc 2.7917
      // 147b: fconst_0
      // 147c: fconst_0
      // 147d: fconst_0
      // 147e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1481: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1484: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1487: aastore
      // 1488: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 148b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 148e: ldc "leftleg"
      // 1490: new net/minecraft/client/animation/AnimationChannel
      // 1493: dup
      // 1494: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1497: bipush 5
      // 1498: anewarray 28
      // 149b: dup
      // 149c: bipush 0
      // 149d: new net/minecraft/client/animation/Keyframe
      // 14a0: dup
      // 14a1: fconst_0
      // 14a2: fconst_0
      // 14a3: fconst_0
      // 14a4: fconst_0
      // 14a5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14a8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14ab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14ae: aastore
      // 14af: dup
      // 14b0: bipush 1
      // 14b1: new net/minecraft/client/animation/Keyframe
      // 14b4: dup
      // 14b5: ldc 0.625
      // 14b7: fconst_0
      // 14b8: ldc -15.0
      // 14ba: fconst_0
      // 14bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14c4: aastore
      // 14c5: dup
      // 14c6: bipush 2
      // 14c7: new net/minecraft/client/animation/Keyframe
      // 14ca: dup
      // 14cb: ldc 0.9583
      // 14cd: ldc 90.0
      // 14cf: fconst_0
      // 14d0: fconst_0
      // 14d1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14d4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14d7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14da: aastore
      // 14db: dup
      // 14dc: bipush 3
      // 14dd: new net/minecraft/client/animation/Keyframe
      // 14e0: dup
      // 14e1: ldc 1.875
      // 14e3: ldc 90.0
      // 14e5: fconst_0
      // 14e6: fconst_0
      // 14e7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14ea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14ed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14f0: aastore
      // 14f1: dup
      // 14f2: bipush 4
      // 14f3: new net/minecraft/client/animation/Keyframe
      // 14f6: dup
      // 14f7: ldc 2.7917
      // 14f9: fconst_0
      // 14fa: fconst_0
      // 14fb: fconst_0
      // 14fc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14ff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1502: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1505: aastore
      // 1506: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1509: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 150c: ldc "leftleg"
      // 150e: new net/minecraft/client/animation/AnimationChannel
      // 1511: dup
      // 1512: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1515: bipush 5
      // 1516: anewarray 28
      // 1519: dup
      // 151a: bipush 0
      // 151b: new net/minecraft/client/animation/Keyframe
      // 151e: dup
      // 151f: fconst_0
      // 1520: fconst_0
      // 1521: fconst_0
      // 1522: fconst_0
      // 1523: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1526: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1529: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 152c: aastore
      // 152d: dup
      // 152e: bipush 1
      // 152f: new net/minecraft/client/animation/Keyframe
      // 1532: dup
      // 1533: ldc 0.625
      // 1535: fconst_0
      // 1536: fconst_0
      // 1537: fconst_0
      // 1538: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 153b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 153e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1541: aastore
      // 1542: dup
      // 1543: bipush 2
      // 1544: new net/minecraft/client/animation/Keyframe
      // 1547: dup
      // 1548: ldc 0.9583
      // 154a: fconst_0
      // 154b: ldc -12.0
      // 154d: ldc 5.0
      // 154f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1552: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1555: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1558: aastore
      // 1559: dup
      // 155a: bipush 3
      // 155b: new net/minecraft/client/animation/Keyframe
      // 155e: dup
      // 155f: ldc 1.875
      // 1561: fconst_0
      // 1562: ldc -12.0
      // 1564: ldc 5.0
      // 1566: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1569: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 156c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 156f: aastore
      // 1570: dup
      // 1571: bipush 4
      // 1572: new net/minecraft/client/animation/Keyframe
      // 1575: dup
      // 1576: ldc 2.7917
      // 1578: fconst_0
      // 1579: fconst_0
      // 157a: fconst_0
      // 157b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 157e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1581: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1584: aastore
      // 1585: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1588: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 158b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.build ()Lnet/minecraft/client/animation/AnimationDefinition;
      // 158e: putstatic com/github/L_Ender/cataclysm/client/animation/Netherite_Monstrosity_Animation.SMASH Lnet/minecraft/client/animation/AnimationDefinition;
      // 1591: fconst_0
      // 1592: invokestatic net/minecraft/client/animation/AnimationDefinition$Builder.withLength (F)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1595: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.looping ()Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1598: ldc "lowerbody"
      // 159a: new net/minecraft/client/animation/AnimationChannel
      // 159d: dup
      // 159e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 15a1: bipush 1
      // 15a2: anewarray 28
      // 15a5: dup
      // 15a6: bipush 0
      // 15a7: new net/minecraft/client/animation/Keyframe
      // 15aa: dup
      // 15ab: fconst_0
      // 15ac: ldc 35.0
      // 15ae: fconst_0
      // 15af: fconst_0
      // 15b0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15b3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15b6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15b9: aastore
      // 15ba: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 15bd: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 15c0: ldc "upperbody"
      // 15c2: new net/minecraft/client/animation/AnimationChannel
      // 15c5: dup
      // 15c6: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 15c9: bipush 1
      // 15ca: anewarray 28
      // 15cd: dup
      // 15ce: bipush 0
      // 15cf: new net/minecraft/client/animation/Keyframe
      // 15d2: dup
      // 15d3: fconst_0
      // 15d4: ldc 12.5
      // 15d6: fconst_0
      // 15d7: fconst_0
      // 15d8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15db: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15de: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15e1: aastore
      // 15e2: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 15e5: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 15e8: ldc "leftarm"
      // 15ea: new net/minecraft/client/animation/AnimationChannel
      // 15ed: dup
      // 15ee: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 15f1: bipush 1
      // 15f2: anewarray 28
      // 15f5: dup
      // 15f6: bipush 0
      // 15f7: new net/minecraft/client/animation/Keyframe
      // 15fa: dup
      // 15fb: fconst_0
      // 15fc: ldc -90.0
      // 15fe: ldc 37.5
      // 1600: ldc -90.0
      // 1602: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1605: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1608: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 160b: aastore
      // 160c: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 160f: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1612: ldc "leftarm"
      // 1614: new net/minecraft/client/animation/AnimationChannel
      // 1617: dup
      // 1618: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 161b: bipush 1
      // 161c: anewarray 28
      // 161f: dup
      // 1620: bipush 0
      // 1621: new net/minecraft/client/animation/Keyframe
      // 1624: dup
      // 1625: fconst_0
      // 1626: ldc 13.0
      // 1628: fconst_0
      // 1629: ldc -7.0
      // 162b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 162e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1631: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1634: aastore
      // 1635: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1638: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 163b: ldc "leftarm2"
      // 163d: new net/minecraft/client/animation/AnimationChannel
      // 1640: dup
      // 1641: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1644: bipush 1
      // 1645: anewarray 28
      // 1648: dup
      // 1649: bipush 0
      // 164a: new net/minecraft/client/animation/Keyframe
      // 164d: dup
      // 164e: fconst_0
      // 164f: ldc 7.5
      // 1651: fconst_0
      // 1652: fconst_0
      // 1653: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1656: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1659: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 165c: aastore
      // 165d: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1660: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1663: ldc "lefthand"
      // 1665: new net/minecraft/client/animation/AnimationChannel
      // 1668: dup
      // 1669: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 166c: bipush 1
      // 166d: anewarray 28
      // 1670: dup
      // 1671: bipush 0
      // 1672: new net/minecraft/client/animation/Keyframe
      // 1675: dup
      // 1676: fconst_0
      // 1677: ldc 5.0
      // 1679: fconst_0
      // 167a: fconst_0
      // 167b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 167e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1681: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1684: aastore
      // 1685: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1688: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 168b: ldc "rightarm"
      // 168d: new net/minecraft/client/animation/AnimationChannel
      // 1690: dup
      // 1691: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1694: bipush 1
      // 1695: anewarray 28
      // 1698: dup
      // 1699: bipush 0
      // 169a: new net/minecraft/client/animation/Keyframe
      // 169d: dup
      // 169e: fconst_0
      // 169f: ldc -90.0
      // 16a1: ldc -37.5
      // 16a3: ldc 90.0
      // 16a5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16a8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16ab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16ae: aastore
      // 16af: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 16b2: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 16b5: ldc "rightarm"
      // 16b7: new net/minecraft/client/animation/AnimationChannel
      // 16ba: dup
      // 16bb: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 16be: bipush 1
      // 16bf: anewarray 28
      // 16c2: dup
      // 16c3: bipush 0
      // 16c4: new net/minecraft/client/animation/Keyframe
      // 16c7: dup
      // 16c8: fconst_0
      // 16c9: ldc -13.0
      // 16cb: fconst_0
      // 16cc: ldc -7.0
      // 16ce: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 16d1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16d4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16d7: aastore
      // 16d8: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 16db: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 16de: ldc "rightarm2"
      // 16e0: new net/minecraft/client/animation/AnimationChannel
      // 16e3: dup
      // 16e4: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 16e7: bipush 1
      // 16e8: anewarray 28
      // 16eb: dup
      // 16ec: bipush 0
      // 16ed: new net/minecraft/client/animation/Keyframe
      // 16f0: dup
      // 16f1: fconst_0
      // 16f2: ldc 7.5
      // 16f4: fconst_0
      // 16f5: fconst_0
      // 16f6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16f9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16fc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16ff: aastore
      // 1700: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1703: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1706: ldc "righthand"
      // 1708: new net/minecraft/client/animation/AnimationChannel
      // 170b: dup
      // 170c: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 170f: bipush 1
      // 1710: anewarray 28
      // 1713: dup
      // 1714: bipush 0
      // 1715: new net/minecraft/client/animation/Keyframe
      // 1718: dup
      // 1719: fconst_0
      // 171a: ldc 5.0
      // 171c: fconst_0
      // 171d: fconst_0
      // 171e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1721: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1724: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1727: aastore
      // 1728: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 172b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 172e: ldc "rightleg"
      // 1730: new net/minecraft/client/animation/AnimationChannel
      // 1733: dup
      // 1734: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1737: bipush 1
      // 1738: anewarray 28
      // 173b: dup
      // 173c: bipush 0
      // 173d: new net/minecraft/client/animation/Keyframe
      // 1740: dup
      // 1741: fconst_0
      // 1742: fconst_0
      // 1743: fconst_0
      // 1744: ldc -7.0
      // 1746: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1749: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 174c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 174f: aastore
      // 1750: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1753: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1756: ldc "leftleg"
      // 1758: new net/minecraft/client/animation/AnimationChannel
      // 175b: dup
      // 175c: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 175f: bipush 1
      // 1760: anewarray 28
      // 1763: dup
      // 1764: bipush 0
      // 1765: new net/minecraft/client/animation/Keyframe
      // 1768: dup
      // 1769: fconst_0
      // 176a: ldc 52.5
      // 176c: fconst_0
      // 176d: fconst_0
      // 176e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1771: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1774: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1777: aastore
      // 1778: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 177b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 177e: ldc "leftleg"
      // 1780: new net/minecraft/client/animation/AnimationChannel
      // 1783: dup
      // 1784: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1787: bipush 1
      // 1788: anewarray 28
      // 178b: dup
      // 178c: bipush 0
      // 178d: new net/minecraft/client/animation/Keyframe
      // 1790: dup
      // 1791: fconst_0
      // 1792: fconst_0
      // 1793: ldc -6.0
      // 1795: fconst_0
      // 1796: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1799: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 179c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 179f: aastore
      // 17a0: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 17a3: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 17a6: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.build ()Lnet/minecraft/client/animation/AnimationDefinition;
      // 17a9: putstatic com/github/L_Ender/cataclysm/client/animation/Netherite_Monstrosity_Animation.SLEEP Lnet/minecraft/client/animation/AnimationDefinition;
      // 17ac: fconst_2
      // 17ad: invokestatic net/minecraft/client/animation/AnimationDefinition$Builder.withLength (F)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 17b0: ldc "lowerbody"
      // 17b2: new net/minecraft/client/animation/AnimationChannel
      // 17b5: dup
      // 17b6: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 17b9: bipush 4
      // 17ba: anewarray 28
      // 17bd: dup
      // 17be: bipush 0
      // 17bf: new net/minecraft/client/animation/Keyframe
      // 17c2: dup
      // 17c3: fconst_0
      // 17c4: ldc 35.0
      // 17c6: fconst_0
      // 17c7: fconst_0
      // 17c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 17cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17d1: aastore
      // 17d2: dup
      // 17d3: bipush 1
      // 17d4: new net/minecraft/client/animation/Keyframe
      // 17d7: dup
      // 17d8: ldc 0.1667
      // 17da: ldc 40.0
      // 17dc: fconst_0
      // 17dd: fconst_0
      // 17de: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 17e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17e7: aastore
      // 17e8: dup
      // 17e9: bipush 2
      // 17ea: new net/minecraft/client/animation/Keyframe
      // 17ed: dup
      // 17ee: ldc 0.4583
      // 17f0: ldc 35.0
      // 17f2: fconst_0
      // 17f3: fconst_0
      // 17f4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 17f7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17fa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17fd: aastore
      // 17fe: dup
      // 17ff: bipush 3
      // 1800: new net/minecraft/client/animation/Keyframe
      // 1803: dup
      // 1804: ldc 1.7083
      // 1806: fconst_0
      // 1807: fconst_0
      // 1808: fconst_0
      // 1809: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 180c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 180f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1812: aastore
      // 1813: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1816: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1819: ldc "upperbody"
      // 181b: new net/minecraft/client/animation/AnimationChannel
      // 181e: dup
      // 181f: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1822: bipush 4
      // 1823: anewarray 28
      // 1826: dup
      // 1827: bipush 0
      // 1828: new net/minecraft/client/animation/Keyframe
      // 182b: dup
      // 182c: fconst_0
      // 182d: ldc 12.5
      // 182f: fconst_0
      // 1830: fconst_0
      // 1831: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1834: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1837: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 183a: aastore
      // 183b: dup
      // 183c: bipush 1
      // 183d: new net/minecraft/client/animation/Keyframe
      // 1840: dup
      // 1841: ldc 0.5417
      // 1843: ldc 12.5
      // 1845: fconst_0
      // 1846: fconst_0
      // 1847: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 184a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 184d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1850: aastore
      // 1851: dup
      // 1852: bipush 2
      // 1853: new net/minecraft/client/animation/Keyframe
      // 1856: dup
      // 1857: ldc 0.7917
      // 1859: ldc 12.5
      // 185b: fconst_0
      // 185c: fconst_0
      // 185d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1860: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1863: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1866: aastore
      // 1867: dup
      // 1868: bipush 3
      // 1869: new net/minecraft/client/animation/Keyframe
      // 186c: dup
      // 186d: ldc 1.75
      // 186f: fconst_0
      // 1870: fconst_0
      // 1871: fconst_0
      // 1872: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1875: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1878: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 187b: aastore
      // 187c: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 187f: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1882: ldc "leftarm"
      // 1884: new net/minecraft/client/animation/AnimationChannel
      // 1887: dup
      // 1888: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 188b: bipush 4
      // 188c: anewarray 28
      // 188f: dup
      // 1890: bipush 0
      // 1891: new net/minecraft/client/animation/Keyframe
      // 1894: dup
      // 1895: fconst_0
      // 1896: ldc -90.0
      // 1898: ldc 37.5
      // 189a: ldc -90.0
      // 189c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 189f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18a2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18a5: aastore
      // 18a6: dup
      // 18a7: bipush 1
      // 18a8: new net/minecraft/client/animation/Keyframe
      // 18ab: dup
      // 18ac: ldc 0.1667
      // 18ae: ldc -90.0
      // 18b0: ldc 32.5
      // 18b2: ldc -90.0
      // 18b4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 18b7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18ba: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18bd: aastore
      // 18be: dup
      // 18bf: bipush 2
      // 18c0: new net/minecraft/client/animation/Keyframe
      // 18c3: dup
      // 18c4: ldc 0.7917
      // 18c6: ldc -90.0
      // 18c8: ldc 37.5
      // 18ca: ldc -90.0
      // 18cc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 18cf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18d2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18d5: aastore
      // 18d6: dup
      // 18d7: bipush 3
      // 18d8: new net/minecraft/client/animation/Keyframe
      // 18db: dup
      // 18dc: ldc 1.7083
      // 18de: fconst_0
      // 18df: fconst_0
      // 18e0: fconst_0
      // 18e1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 18e4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18e7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18ea: aastore
      // 18eb: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 18ee: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 18f1: ldc "leftarm"
      // 18f3: new net/minecraft/client/animation/AnimationChannel
      // 18f6: dup
      // 18f7: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 18fa: bipush 4
      // 18fb: anewarray 28
      // 18fe: dup
      // 18ff: bipush 0
      // 1900: new net/minecraft/client/animation/Keyframe
      // 1903: dup
      // 1904: fconst_0
      // 1905: ldc 13.0
      // 1907: fconst_0
      // 1908: ldc -7.0
      // 190a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 190d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1910: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1913: aastore
      // 1914: dup
      // 1915: bipush 1
      // 1916: new net/minecraft/client/animation/Keyframe
      // 1919: dup
      // 191a: ldc 0.1667
      // 191c: ldc 13.0
      // 191e: ldc -3.0
      // 1920: ldc -10.0
      // 1922: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1925: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1928: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 192b: aastore
      // 192c: dup
      // 192d: bipush 2
      // 192e: new net/minecraft/client/animation/Keyframe
      // 1931: dup
      // 1932: ldc 0.7917
      // 1934: ldc 13.0
      // 1936: fconst_0
      // 1937: ldc -7.0
      // 1939: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 193c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 193f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1942: aastore
      // 1943: dup
      // 1944: bipush 3
      // 1945: new net/minecraft/client/animation/Keyframe
      // 1948: dup
      // 1949: ldc 1.7083
      // 194b: fconst_0
      // 194c: fconst_0
      // 194d: fconst_0
      // 194e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1951: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1954: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1957: aastore
      // 1958: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 195b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 195e: ldc "leftarm2"
      // 1960: new net/minecraft/client/animation/AnimationChannel
      // 1963: dup
      // 1964: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1967: bipush 3
      // 1968: anewarray 28
      // 196b: dup
      // 196c: bipush 0
      // 196d: new net/minecraft/client/animation/Keyframe
      // 1970: dup
      // 1971: fconst_0
      // 1972: ldc 7.5
      // 1974: fconst_0
      // 1975: fconst_0
      // 1976: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1979: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 197c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 197f: aastore
      // 1980: dup
      // 1981: bipush 1
      // 1982: new net/minecraft/client/animation/Keyframe
      // 1985: dup
      // 1986: ldc 0.7917
      // 1988: ldc 7.5
      // 198a: fconst_0
      // 198b: fconst_0
      // 198c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 198f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1992: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1995: aastore
      // 1996: dup
      // 1997: bipush 2
      // 1998: new net/minecraft/client/animation/Keyframe
      // 199b: dup
      // 199c: ldc 1.5833
      // 199e: fconst_0
      // 199f: fconst_0
      // 19a0: fconst_0
      // 19a1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 19a4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19a7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19aa: aastore
      // 19ab: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 19ae: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 19b1: ldc "lefthand"
      // 19b3: new net/minecraft/client/animation/AnimationChannel
      // 19b6: dup
      // 19b7: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 19ba: bipush 3
      // 19bb: anewarray 28
      // 19be: dup
      // 19bf: bipush 0
      // 19c0: new net/minecraft/client/animation/Keyframe
      // 19c3: dup
      // 19c4: fconst_0
      // 19c5: ldc 5.0
      // 19c7: fconst_0
      // 19c8: fconst_0
      // 19c9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 19cc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19cf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19d2: aastore
      // 19d3: dup
      // 19d4: bipush 1
      // 19d5: new net/minecraft/client/animation/Keyframe
      // 19d8: dup
      // 19d9: ldc 0.375
      // 19db: ldc 5.0
      // 19dd: fconst_0
      // 19de: fconst_0
      // 19df: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 19e2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19e5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19e8: aastore
      // 19e9: dup
      // 19ea: bipush 2
      // 19eb: new net/minecraft/client/animation/Keyframe
      // 19ee: dup
      // 19ef: ldc 1.7083
      // 19f1: fconst_0
      // 19f2: fconst_0
      // 19f3: fconst_0
      // 19f4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 19f7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19fa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19fd: aastore
      // 19fe: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1a01: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1a04: ldc "lefthand"
      // 1a06: new net/minecraft/client/animation/AnimationChannel
      // 1a09: dup
      // 1a0a: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1a0d: bipush 5
      // 1a0e: anewarray 28
      // 1a11: dup
      // 1a12: bipush 0
      // 1a13: new net/minecraft/client/animation/Keyframe
      // 1a16: dup
      // 1a17: fconst_0
      // 1a18: fconst_0
      // 1a19: fconst_0
      // 1a1a: fconst_0
      // 1a1b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1a1e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a21: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a24: aastore
      // 1a25: dup
      // 1a26: bipush 1
      // 1a27: new net/minecraft/client/animation/Keyframe
      // 1a2a: dup
      // 1a2b: ldc 0.1667
      // 1a2d: fconst_0
      // 1a2e: ldc 8.0
      // 1a30: fconst_0
      // 1a31: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1a34: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a37: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a3a: aastore
      // 1a3b: dup
      // 1a3c: bipush 2
      // 1a3d: new net/minecraft/client/animation/Keyframe
      // 1a40: dup
      // 1a41: ldc 0.4167
      // 1a43: fconst_0
      // 1a44: ldc 8.0
      // 1a46: fconst_0
      // 1a47: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1a4a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a4d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a50: aastore
      // 1a51: dup
      // 1a52: bipush 3
      // 1a53: new net/minecraft/client/animation/Keyframe
      // 1a56: dup
      // 1a57: ldc 0.6667
      // 1a59: fconst_0
      // 1a5a: fconst_0
      // 1a5b: fconst_0
      // 1a5c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1a5f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a62: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a65: aastore
      // 1a66: dup
      // 1a67: bipush 4
      // 1a68: new net/minecraft/client/animation/Keyframe
      // 1a6b: dup
      // 1a6c: ldc 1.7083
      // 1a6e: fconst_0
      // 1a6f: fconst_0
      // 1a70: fconst_0
      // 1a71: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1a74: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a77: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a7a: aastore
      // 1a7b: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1a7e: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1a81: ldc "rightarm"
      // 1a83: new net/minecraft/client/animation/AnimationChannel
      // 1a86: dup
      // 1a87: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1a8a: bipush 4
      // 1a8b: anewarray 28
      // 1a8e: dup
      // 1a8f: bipush 0
      // 1a90: new net/minecraft/client/animation/Keyframe
      // 1a93: dup
      // 1a94: fconst_0
      // 1a95: ldc -90.0
      // 1a97: ldc -37.5
      // 1a99: ldc 90.0
      // 1a9b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a9e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1aa1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1aa4: aastore
      // 1aa5: dup
      // 1aa6: bipush 1
      // 1aa7: new net/minecraft/client/animation/Keyframe
      // 1aaa: dup
      // 1aab: ldc 0.1667
      // 1aad: ldc -90.0
      // 1aaf: ldc -32.5
      // 1ab1: ldc 90.0
      // 1ab3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ab6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ab9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1abc: aastore
      // 1abd: dup
      // 1abe: bipush 2
      // 1abf: new net/minecraft/client/animation/Keyframe
      // 1ac2: dup
      // 1ac3: ldc 0.7917
      // 1ac5: ldc -90.0
      // 1ac7: ldc -37.5
      // 1ac9: ldc 90.0
      // 1acb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ace: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ad1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ad4: aastore
      // 1ad5: dup
      // 1ad6: bipush 3
      // 1ad7: new net/minecraft/client/animation/Keyframe
      // 1ada: dup
      // 1adb: ldc 1.7083
      // 1add: fconst_0
      // 1ade: fconst_0
      // 1adf: fconst_0
      // 1ae0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ae3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ae6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ae9: aastore
      // 1aea: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1aed: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1af0: ldc "rightarm"
      // 1af2: new net/minecraft/client/animation/AnimationChannel
      // 1af5: dup
      // 1af6: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1af9: bipush 4
      // 1afa: anewarray 28
      // 1afd: dup
      // 1afe: bipush 0
      // 1aff: new net/minecraft/client/animation/Keyframe
      // 1b02: dup
      // 1b03: fconst_0
      // 1b04: ldc -13.0
      // 1b06: fconst_0
      // 1b07: ldc -7.0
      // 1b09: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1b0c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b0f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b12: aastore
      // 1b13: dup
      // 1b14: bipush 1
      // 1b15: new net/minecraft/client/animation/Keyframe
      // 1b18: dup
      // 1b19: ldc 0.1667
      // 1b1b: ldc -13.0
      // 1b1d: ldc -3.0
      // 1b1f: ldc -10.0
      // 1b21: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1b24: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b27: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b2a: aastore
      // 1b2b: dup
      // 1b2c: bipush 2
      // 1b2d: new net/minecraft/client/animation/Keyframe
      // 1b30: dup
      // 1b31: ldc 0.7917
      // 1b33: ldc -13.0
      // 1b35: fconst_0
      // 1b36: ldc -7.0
      // 1b38: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1b3b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b3e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b41: aastore
      // 1b42: dup
      // 1b43: bipush 3
      // 1b44: new net/minecraft/client/animation/Keyframe
      // 1b47: dup
      // 1b48: ldc 1.7083
      // 1b4a: fconst_0
      // 1b4b: fconst_0
      // 1b4c: fconst_0
      // 1b4d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1b50: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b53: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b56: aastore
      // 1b57: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1b5a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1b5d: ldc "rightarm2"
      // 1b5f: new net/minecraft/client/animation/AnimationChannel
      // 1b62: dup
      // 1b63: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1b66: bipush 3
      // 1b67: anewarray 28
      // 1b6a: dup
      // 1b6b: bipush 0
      // 1b6c: new net/minecraft/client/animation/Keyframe
      // 1b6f: dup
      // 1b70: fconst_0
      // 1b71: ldc 7.5
      // 1b73: fconst_0
      // 1b74: fconst_0
      // 1b75: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b78: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b7b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b7e: aastore
      // 1b7f: dup
      // 1b80: bipush 1
      // 1b81: new net/minecraft/client/animation/Keyframe
      // 1b84: dup
      // 1b85: ldc 0.7917
      // 1b87: ldc 7.5
      // 1b89: fconst_0
      // 1b8a: fconst_0
      // 1b8b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b8e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b91: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b94: aastore
      // 1b95: dup
      // 1b96: bipush 2
      // 1b97: new net/minecraft/client/animation/Keyframe
      // 1b9a: dup
      // 1b9b: ldc 1.5833
      // 1b9d: fconst_0
      // 1b9e: fconst_0
      // 1b9f: fconst_0
      // 1ba0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ba3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ba6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ba9: aastore
      // 1baa: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1bad: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1bb0: ldc "righthand"
      // 1bb2: new net/minecraft/client/animation/AnimationChannel
      // 1bb5: dup
      // 1bb6: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1bb9: bipush 3
      // 1bba: anewarray 28
      // 1bbd: dup
      // 1bbe: bipush 0
      // 1bbf: new net/minecraft/client/animation/Keyframe
      // 1bc2: dup
      // 1bc3: fconst_0
      // 1bc4: ldc 5.0
      // 1bc6: fconst_0
      // 1bc7: fconst_0
      // 1bc8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1bcb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1bce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1bd1: aastore
      // 1bd2: dup
      // 1bd3: bipush 1
      // 1bd4: new net/minecraft/client/animation/Keyframe
      // 1bd7: dup
      // 1bd8: ldc 0.375
      // 1bda: ldc 5.0
      // 1bdc: fconst_0
      // 1bdd: fconst_0
      // 1bde: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1be1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1be4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1be7: aastore
      // 1be8: dup
      // 1be9: bipush 2
      // 1bea: new net/minecraft/client/animation/Keyframe
      // 1bed: dup
      // 1bee: ldc 1.7083
      // 1bf0: fconst_0
      // 1bf1: fconst_0
      // 1bf2: fconst_0
      // 1bf3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1bf6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1bf9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1bfc: aastore
      // 1bfd: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1c00: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1c03: ldc "righthand"
      // 1c05: new net/minecraft/client/animation/AnimationChannel
      // 1c08: dup
      // 1c09: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1c0c: bipush 5
      // 1c0d: anewarray 28
      // 1c10: dup
      // 1c11: bipush 0
      // 1c12: new net/minecraft/client/animation/Keyframe
      // 1c15: dup
      // 1c16: fconst_0
      // 1c17: fconst_0
      // 1c18: fconst_0
      // 1c19: fconst_0
      // 1c1a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c1d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c20: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c23: aastore
      // 1c24: dup
      // 1c25: bipush 1
      // 1c26: new net/minecraft/client/animation/Keyframe
      // 1c29: dup
      // 1c2a: ldc 0.1667
      // 1c2c: fconst_0
      // 1c2d: ldc 8.0
      // 1c2f: fconst_0
      // 1c30: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c33: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c36: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c39: aastore
      // 1c3a: dup
      // 1c3b: bipush 2
      // 1c3c: new net/minecraft/client/animation/Keyframe
      // 1c3f: dup
      // 1c40: ldc 0.4167
      // 1c42: fconst_0
      // 1c43: ldc 8.0
      // 1c45: fconst_0
      // 1c46: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c49: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c4c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c4f: aastore
      // 1c50: dup
      // 1c51: bipush 3
      // 1c52: new net/minecraft/client/animation/Keyframe
      // 1c55: dup
      // 1c56: ldc 0.6667
      // 1c58: fconst_0
      // 1c59: fconst_0
      // 1c5a: fconst_0
      // 1c5b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c5e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c61: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c64: aastore
      // 1c65: dup
      // 1c66: bipush 4
      // 1c67: new net/minecraft/client/animation/Keyframe
      // 1c6a: dup
      // 1c6b: ldc 1.7083
      // 1c6d: fconst_0
      // 1c6e: fconst_0
      // 1c6f: fconst_0
      // 1c70: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c73: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c76: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c79: aastore
      // 1c7a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1c7d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1c80: ldc "rightleg"
      // 1c82: new net/minecraft/client/animation/AnimationChannel
      // 1c85: dup
      // 1c86: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1c89: bipush 3
      // 1c8a: anewarray 28
      // 1c8d: dup
      // 1c8e: bipush 0
      // 1c8f: new net/minecraft/client/animation/Keyframe
      // 1c92: dup
      // 1c93: fconst_0
      // 1c94: fconst_0
      // 1c95: fconst_0
      // 1c96: ldc -7.0
      // 1c98: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c9b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c9e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ca1: aastore
      // 1ca2: dup
      // 1ca3: bipush 1
      // 1ca4: new net/minecraft/client/animation/Keyframe
      // 1ca7: dup
      // 1ca8: ldc 0.6667
      // 1caa: fconst_0
      // 1cab: fconst_0
      // 1cac: ldc -7.0
      // 1cae: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1cb1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1cb4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1cb7: aastore
      // 1cb8: dup
      // 1cb9: bipush 2
      // 1cba: new net/minecraft/client/animation/Keyframe
      // 1cbd: dup
      // 1cbe: ldc 1.7083
      // 1cc0: fconst_0
      // 1cc1: fconst_0
      // 1cc2: fconst_0
      // 1cc3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1cc6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1cc9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ccc: aastore
      // 1ccd: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1cd0: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1cd3: ldc "leftleg"
      // 1cd5: new net/minecraft/client/animation/AnimationChannel
      // 1cd8: dup
      // 1cd9: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1cdc: bipush 3
      // 1cdd: anewarray 28
      // 1ce0: dup
      // 1ce1: bipush 0
      // 1ce2: new net/minecraft/client/animation/Keyframe
      // 1ce5: dup
      // 1ce6: fconst_0
      // 1ce7: ldc 52.5
      // 1ce9: fconst_0
      // 1cea: fconst_0
      // 1ceb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1cee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1cf1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1cf4: aastore
      // 1cf5: dup
      // 1cf6: bipush 1
      // 1cf7: new net/minecraft/client/animation/Keyframe
      // 1cfa: dup
      // 1cfb: ldc 0.6667
      // 1cfd: ldc 52.5
      // 1cff: fconst_0
      // 1d00: fconst_0
      // 1d01: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d04: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d07: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d0a: aastore
      // 1d0b: dup
      // 1d0c: bipush 2
      // 1d0d: new net/minecraft/client/animation/Keyframe
      // 1d10: dup
      // 1d11: ldc 1.7083
      // 1d13: fconst_0
      // 1d14: fconst_0
      // 1d15: fconst_0
      // 1d16: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d19: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d1c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d1f: aastore
      // 1d20: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1d23: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1d26: ldc "leftleg"
      // 1d28: new net/minecraft/client/animation/AnimationChannel
      // 1d2b: dup
      // 1d2c: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1d2f: bipush 3
      // 1d30: anewarray 28
      // 1d33: dup
      // 1d34: bipush 0
      // 1d35: new net/minecraft/client/animation/Keyframe
      // 1d38: dup
      // 1d39: fconst_0
      // 1d3a: fconst_0
      // 1d3b: ldc -6.0
      // 1d3d: fconst_0
      // 1d3e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d41: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d44: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d47: aastore
      // 1d48: dup
      // 1d49: bipush 1
      // 1d4a: new net/minecraft/client/animation/Keyframe
      // 1d4d: dup
      // 1d4e: ldc 0.6667
      // 1d50: fconst_0
      // 1d51: ldc -6.0
      // 1d53: fconst_0
      // 1d54: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d57: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d5a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d5d: aastore
      // 1d5e: dup
      // 1d5f: bipush 2
      // 1d60: new net/minecraft/client/animation/Keyframe
      // 1d63: dup
      // 1d64: ldc 1.7083
      // 1d66: fconst_0
      // 1d67: fconst_0
      // 1d68: fconst_0
      // 1d69: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d6c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d6f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d72: aastore
      // 1d73: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1d76: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1d79: ldc "head"
      // 1d7b: new net/minecraft/client/animation/AnimationChannel
      // 1d7e: dup
      // 1d7f: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1d82: bipush 3
      // 1d83: anewarray 28
      // 1d86: dup
      // 1d87: bipush 0
      // 1d88: new net/minecraft/client/animation/Keyframe
      // 1d8b: dup
      // 1d8c: fconst_0
      // 1d8d: fconst_0
      // 1d8e: fconst_0
      // 1d8f: fconst_0
      // 1d90: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d93: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d96: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d99: aastore
      // 1d9a: dup
      // 1d9b: bipush 1
      // 1d9c: new net/minecraft/client/animation/Keyframe
      // 1d9f: dup
      // 1da0: ldc 0.1667
      // 1da2: ldc 15.0
      // 1da4: fconst_0
      // 1da5: fconst_0
      // 1da6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1da9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1dac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1daf: aastore
      // 1db0: dup
      // 1db1: bipush 2
      // 1db2: new net/minecraft/client/animation/Keyframe
      // 1db5: dup
      // 1db6: ldc 0.5833
      // 1db8: fconst_0
      // 1db9: fconst_0
      // 1dba: fconst_0
      // 1dbb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1dbe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1dc1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1dc4: aastore
      // 1dc5: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1dc8: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1dcb: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.build ()Lnet/minecraft/client/animation/AnimationDefinition;
      // 1dce: putstatic com/github/L_Ender/cataclysm/client/animation/Netherite_Monstrosity_Animation.AWAKE Lnet/minecraft/client/animation/AnimationDefinition;
      // 1dd1: ldc 2.6667
      // 1dd3: invokestatic net/minecraft/client/animation/AnimationDefinition$Builder.withLength (F)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1dd6: ldc "roots"
      // 1dd8: new net/minecraft/client/animation/AnimationChannel
      // 1ddb: dup
      // 1ddc: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1ddf: bipush 5
      // 1de0: anewarray 28
      // 1de3: dup
      // 1de4: bipush 0
      // 1de5: new net/minecraft/client/animation/Keyframe
      // 1de8: dup
      // 1de9: fconst_0
      // 1dea: fconst_0
      // 1deb: fconst_0
      // 1dec: fconst_0
      // 1ded: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1df0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1df3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1df6: aastore
      // 1df7: dup
      // 1df8: bipush 1
      // 1df9: new net/minecraft/client/animation/Keyframe
      // 1dfc: dup
      // 1dfd: ldc 0.5417
      // 1dff: fconst_0
      // 1e00: ldc -12.5
      // 1e02: fconst_0
      // 1e03: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1e06: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e09: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e0c: aastore
      // 1e0d: dup
      // 1e0e: bipush 2
      // 1e0f: new net/minecraft/client/animation/Keyframe
      // 1e12: dup
      // 1e13: ldc 0.7917
      // 1e15: fconst_0
      // 1e16: fconst_0
      // 1e17: fconst_0
      // 1e18: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1e1b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e1e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e21: aastore
      // 1e22: dup
      // 1e23: bipush 3
      // 1e24: new net/minecraft/client/animation/Keyframe
      // 1e27: dup
      // 1e28: ldc 1.7083
      // 1e2a: fconst_0
      // 1e2b: fconst_0
      // 1e2c: fconst_0
      // 1e2d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1e30: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e33: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e36: aastore
      // 1e37: dup
      // 1e38: bipush 4
      // 1e39: new net/minecraft/client/animation/Keyframe
      // 1e3c: dup
      // 1e3d: ldc 2.5
      // 1e3f: fconst_0
      // 1e40: fconst_0
      // 1e41: fconst_0
      // 1e42: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1e45: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e48: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e4b: aastore
      // 1e4c: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1e4f: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1e52: ldc "roots"
      // 1e54: new net/minecraft/client/animation/AnimationChannel
      // 1e57: dup
      // 1e58: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1e5b: bipush 6
      // 1e5d: anewarray 28
      // 1e60: dup
      // 1e61: bipush 0
      // 1e62: new net/minecraft/client/animation/Keyframe
      // 1e65: dup
      // 1e66: fconst_0
      // 1e67: fconst_0
      // 1e68: fconst_0
      // 1e69: fconst_0
      // 1e6a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e6d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e70: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e73: aastore
      // 1e74: dup
      // 1e75: bipush 1
      // 1e76: new net/minecraft/client/animation/Keyframe
      // 1e79: dup
      // 1e7a: ldc 0.5417
      // 1e7c: fconst_0
      // 1e7d: fconst_0
      // 1e7e: fconst_0
      // 1e7f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e82: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e85: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e88: aastore
      // 1e89: dup
      // 1e8a: bipush 2
      // 1e8b: new net/minecraft/client/animation/Keyframe
      // 1e8e: dup
      // 1e8f: ldc 0.7917
      // 1e91: fconst_0
      // 1e92: ldc -10.0
      // 1e94: fconst_0
      // 1e95: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e98: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e9b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e9e: aastore
      // 1e9f: dup
      // 1ea0: bipush 3
      // 1ea1: new net/minecraft/client/animation/Keyframe
      // 1ea4: dup
      // 1ea5: ldc 1.125
      // 1ea7: fconst_0
      // 1ea8: ldc -7.0
      // 1eaa: fconst_0
      // 1eab: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1eae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1eb1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1eb4: aastore
      // 1eb5: dup
      // 1eb6: bipush 4
      // 1eb7: new net/minecraft/client/animation/Keyframe
      // 1eba: dup
      // 1ebb: ldc 1.4583
      // 1ebd: fconst_0
      // 1ebe: ldc -7.0
      // 1ec0: fconst_0
      // 1ec1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1ec4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ec7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1eca: aastore
      // 1ecb: dup
      // 1ecc: bipush 5
      // 1ecd: new net/minecraft/client/animation/Keyframe
      // 1ed0: dup
      // 1ed1: ldc 2.5
      // 1ed3: fconst_0
      // 1ed4: fconst_0
      // 1ed5: fconst_0
      // 1ed6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1ed9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1edc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1edf: aastore
      // 1ee0: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1ee3: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1ee6: ldc "lowerbody"
      // 1ee8: new net/minecraft/client/animation/AnimationChannel
      // 1eeb: dup
      // 1eec: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1eef: bipush 3
      // 1ef0: anewarray 28
      // 1ef3: dup
      // 1ef4: bipush 0
      // 1ef5: new net/minecraft/client/animation/Keyframe
      // 1ef8: dup
      // 1ef9: fconst_0
      // 1efa: fconst_0
      // 1efb: fconst_0
      // 1efc: fconst_0
      // 1efd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f00: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f03: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f06: aastore
      // 1f07: dup
      // 1f08: bipush 1
      // 1f09: new net/minecraft/client/animation/Keyframe
      // 1f0c: dup
      // 1f0d: ldc 1.7083
      // 1f0f: fconst_0
      // 1f10: fconst_0
      // 1f11: fconst_0
      // 1f12: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f15: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f18: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f1b: aastore
      // 1f1c: dup
      // 1f1d: bipush 2
      // 1f1e: new net/minecraft/client/animation/Keyframe
      // 1f21: dup
      // 1f22: ldc 2.5
      // 1f24: fconst_0
      // 1f25: fconst_0
      // 1f26: fconst_0
      // 1f27: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f2a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f2d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f30: aastore
      // 1f31: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1f34: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1f37: ldc "upperbody"
      // 1f39: new net/minecraft/client/animation/AnimationChannel
      // 1f3c: dup
      // 1f3d: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1f40: bipush 6
      // 1f42: anewarray 28
      // 1f45: dup
      // 1f46: bipush 0
      // 1f47: new net/minecraft/client/animation/Keyframe
      // 1f4a: dup
      // 1f4b: fconst_0
      // 1f4c: fconst_0
      // 1f4d: fconst_0
      // 1f4e: fconst_0
      // 1f4f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f52: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f55: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f58: aastore
      // 1f59: dup
      // 1f5a: bipush 1
      // 1f5b: new net/minecraft/client/animation/Keyframe
      // 1f5e: dup
      // 1f5f: ldc 0.5417
      // 1f61: ldc -27.5
      // 1f63: fconst_0
      // 1f64: fconst_0
      // 1f65: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f68: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f6b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f6e: aastore
      // 1f6f: dup
      // 1f70: bipush 2
      // 1f71: new net/minecraft/client/animation/Keyframe
      // 1f74: dup
      // 1f75: ldc 0.75
      // 1f77: ldc 25.0
      // 1f79: fconst_0
      // 1f7a: fconst_0
      // 1f7b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f7e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f81: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f84: aastore
      // 1f85: dup
      // 1f86: bipush 3
      // 1f87: new net/minecraft/client/animation/Keyframe
      // 1f8a: dup
      // 1f8b: ldc 1.2083
      // 1f8d: ldc 25.0
      // 1f8f: fconst_0
      // 1f90: fconst_0
      // 1f91: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f94: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f97: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f9a: aastore
      // 1f9b: dup
      // 1f9c: bipush 4
      // 1f9d: new net/minecraft/client/animation/Keyframe
      // 1fa0: dup
      // 1fa1: ldc 1.7083
      // 1fa3: ldc 52.5
      // 1fa5: fconst_0
      // 1fa6: fconst_0
      // 1fa7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1faa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1fad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1fb0: aastore
      // 1fb1: dup
      // 1fb2: bipush 5
      // 1fb3: new net/minecraft/client/animation/Keyframe
      // 1fb6: dup
      // 1fb7: ldc 2.5833
      // 1fb9: fconst_0
      // 1fba: fconst_0
      // 1fbb: fconst_0
      // 1fbc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1fbf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1fc2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1fc5: aastore
      // 1fc6: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1fc9: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1fcc: ldc "upperbody"
      // 1fce: new net/minecraft/client/animation/AnimationChannel
      // 1fd1: dup
      // 1fd2: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1fd5: bipush 6
      // 1fd7: anewarray 28
      // 1fda: dup
      // 1fdb: bipush 0
      // 1fdc: new net/minecraft/client/animation/Keyframe
      // 1fdf: dup
      // 1fe0: fconst_0
      // 1fe1: fconst_0
      // 1fe2: fconst_0
      // 1fe3: fconst_0
      // 1fe4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1fe7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1fea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1fed: aastore
      // 1fee: dup
      // 1fef: bipush 1
      // 1ff0: new net/minecraft/client/animation/Keyframe
      // 1ff3: dup
      // 1ff4: ldc 0.625
      // 1ff6: fconst_0
      // 1ff7: fconst_0
      // 1ff8: fconst_0
      // 1ff9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1ffc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1fff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2002: aastore
      // 2003: dup
      // 2004: bipush 2
      // 2005: new net/minecraft/client/animation/Keyframe
      // 2008: dup
      // 2009: ldc 0.9583
      // 200b: fconst_0
      // 200c: ldc -2.0
      // 200e: fconst_0
      // 200f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2012: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2015: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2018: aastore
      // 2019: dup
      // 201a: bipush 3
      // 201b: new net/minecraft/client/animation/Keyframe
      // 201e: dup
      // 201f: ldc 1.2083
      // 2021: fconst_0
      // 2022: fconst_0
      // 2023: fconst_0
      // 2024: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2027: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 202a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 202d: aastore
      // 202e: dup
      // 202f: bipush 4
      // 2030: new net/minecraft/client/animation/Keyframe
      // 2033: dup
      // 2034: ldc 1.7083
      // 2036: fconst_0
      // 2037: fconst_0
      // 2038: fconst_0
      // 2039: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 203c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 203f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2042: aastore
      // 2043: dup
      // 2044: bipush 5
      // 2045: new net/minecraft/client/animation/Keyframe
      // 2048: dup
      // 2049: ldc 2.5833
      // 204b: fconst_0
      // 204c: fconst_0
      // 204d: fconst_0
      // 204e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2051: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2054: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2057: aastore
      // 2058: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 205b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 205e: ldc "head"
      // 2060: new net/minecraft/client/animation/AnimationChannel
      // 2063: dup
      // 2064: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2067: bipush 7
      // 2069: anewarray 28
      // 206c: dup
      // 206d: bipush 0
      // 206e: new net/minecraft/client/animation/Keyframe
      // 2071: dup
      // 2072: fconst_0
      // 2073: fconst_0
      // 2074: fconst_0
      // 2075: fconst_0
      // 2076: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2079: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 207c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 207f: aastore
      // 2080: dup
      // 2081: bipush 1
      // 2082: new net/minecraft/client/animation/Keyframe
      // 2085: dup
      // 2086: ldc 0.4583
      // 2088: ldc -22.5131
      // 208a: ldc -4.6691
      // 208c: ldc -7.7096
      // 208e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2091: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2094: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2097: aastore
      // 2098: dup
      // 2099: bipush 2
      // 209a: new net/minecraft/client/animation/Keyframe
      // 209d: dup
      // 209e: ldc 0.7917
      // 20a0: ldc -7.5
      // 20a2: fconst_0
      // 20a3: fconst_0
      // 20a4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 20a7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20aa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20ad: aastore
      // 20ae: dup
      // 20af: bipush 3
      // 20b0: new net/minecraft/client/animation/Keyframe
      // 20b3: dup
      // 20b4: ldc 1.1667
      // 20b6: ldc -47.6463
      // 20b8: ldc -15.2879
      // 20ba: ldc 16.9096
      // 20bc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 20bf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20c2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20c5: aastore
      // 20c6: dup
      // 20c7: bipush 4
      // 20c8: new net/minecraft/client/animation/Keyframe
      // 20cb: dup
      // 20cc: ldc 1.4167
      // 20ce: ldc -66.946
      // 20d0: ldc 17.1391
      // 20d2: ldc -16.4022
      // 20d4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 20d7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20da: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20dd: aastore
      // 20de: dup
      // 20df: bipush 5
      // 20e0: new net/minecraft/client/animation/Keyframe
      // 20e3: dup
      // 20e4: ldc 1.7083
      // 20e6: ldc -65.0
      // 20e8: fconst_0
      // 20e9: fconst_0
      // 20ea: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 20ed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20f0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20f3: aastore
      // 20f4: dup
      // 20f5: bipush 6
      // 20f7: new net/minecraft/client/animation/Keyframe
      // 20fa: dup
      // 20fb: ldc 2.5
      // 20fd: fconst_0
      // 20fe: fconst_0
      // 20ff: fconst_0
      // 2100: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2103: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2106: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2109: aastore
      // 210a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 210d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2110: ldc "head"
      // 2112: new net/minecraft/client/animation/AnimationChannel
      // 2115: dup
      // 2116: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2119: bipush 6
      // 211b: anewarray 28
      // 211e: dup
      // 211f: bipush 0
      // 2120: new net/minecraft/client/animation/Keyframe
      // 2123: dup
      // 2124: fconst_0
      // 2125: fconst_0
      // 2126: fconst_0
      // 2127: fconst_0
      // 2128: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 212b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 212e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2131: aastore
      // 2132: dup
      // 2133: bipush 1
      // 2134: new net/minecraft/client/animation/Keyframe
      // 2137: dup
      // 2138: ldc 0.5417
      // 213a: fconst_0
      // 213b: ldc 5.0
      // 213d: fconst_0
      // 213e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2141: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2144: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2147: aastore
      // 2148: dup
      // 2149: bipush 2
      // 214a: new net/minecraft/client/animation/Keyframe
      // 214d: dup
      // 214e: ldc 0.9583
      // 2150: fconst_0
      // 2151: fconst_2
      // 2152: fconst_0
      // 2153: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2156: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2159: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 215c: aastore
      // 215d: dup
      // 215e: bipush 3
      // 215f: new net/minecraft/client/animation/Keyframe
      // 2162: dup
      // 2163: ldc 1.4167
      // 2165: fconst_0
      // 2166: ldc 9.01
      // 2168: fconst_0
      // 2169: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 216c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 216f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2172: aastore
      // 2173: dup
      // 2174: bipush 4
      // 2175: new net/minecraft/client/animation/Keyframe
      // 2178: dup
      // 2179: ldc 1.7083
      // 217b: fconst_0
      // 217c: fconst_2
      // 217d: fconst_0
      // 217e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2181: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2184: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2187: aastore
      // 2188: dup
      // 2189: bipush 5
      // 218a: new net/minecraft/client/animation/Keyframe
      // 218d: dup
      // 218e: ldc 2.5
      // 2190: fconst_0
      // 2191: fconst_0
      // 2192: fconst_0
      // 2193: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2196: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2199: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 219c: aastore
      // 219d: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 21a0: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 21a3: ldc "jaw"
      // 21a5: new net/minecraft/client/animation/AnimationChannel
      // 21a8: dup
      // 21a9: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 21ac: bipush 7
      // 21ae: anewarray 28
      // 21b1: dup
      // 21b2: bipush 0
      // 21b3: new net/minecraft/client/animation/Keyframe
      // 21b6: dup
      // 21b7: fconst_0
      // 21b8: fconst_0
      // 21b9: fconst_0
      // 21ba: fconst_0
      // 21bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 21be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21c4: aastore
      // 21c5: dup
      // 21c6: bipush 1
      // 21c7: new net/minecraft/client/animation/Keyframe
      // 21ca: dup
      // 21cb: ldc 0.5417
      // 21cd: ldc 2.4621
      // 21cf: ldc -0.434
      // 21d1: ldc 9.9907
      // 21d3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 21d6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21d9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21dc: aastore
      // 21dd: dup
      // 21de: bipush 2
      // 21df: new net/minecraft/client/animation/Keyframe
      // 21e2: dup
      // 21e3: ldc 0.9583
      // 21e5: ldc 54.0188
      // 21e7: ldc 9.8466
      // 21e9: ldc 17.4952
      // 21eb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 21ee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21f1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21f4: aastore
      // 21f5: dup
      // 21f6: bipush 3
      // 21f7: new net/minecraft/client/animation/Keyframe
      // 21fa: dup
      // 21fb: ldc 1.25
      // 21fd: ldc 34.121
      // 21ff: ldc -4.5529
      // 2201: ldc -7.5438
      // 2203: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2206: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2209: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 220c: aastore
      // 220d: dup
      // 220e: bipush 4
      // 220f: new net/minecraft/client/animation/Keyframe
      // 2212: dup
      // 2213: ldc 1.5417
      // 2215: ldc 31.9476
      // 2217: ldc 10.8012
      // 2219: ldc 6.2734
      // 221b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 221e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2221: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2224: aastore
      // 2225: dup
      // 2226: bipush 5
      // 2227: new net/minecraft/client/animation/Keyframe
      // 222a: dup
      // 222b: ldc 1.8333
      // 222d: ldc 27.5
      // 222f: fconst_0
      // 2230: fconst_0
      // 2231: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2234: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2237: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 223a: aastore
      // 223b: dup
      // 223c: bipush 6
      // 223e: new net/minecraft/client/animation/Keyframe
      // 2241: dup
      // 2242: ldc 2.5
      // 2244: fconst_0
      // 2245: fconst_0
      // 2246: fconst_0
      // 2247: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 224a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 224d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2250: aastore
      // 2251: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2254: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2257: ldc "leftarm"
      // 2259: new net/minecraft/client/animation/AnimationChannel
      // 225c: dup
      // 225d: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2260: bipush 6
      // 2262: anewarray 28
      // 2265: dup
      // 2266: bipush 0
      // 2267: new net/minecraft/client/animation/Keyframe
      // 226a: dup
      // 226b: fconst_0
      // 226c: fconst_0
      // 226d: fconst_0
      // 226e: fconst_0
      // 226f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2272: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2275: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2278: aastore
      // 2279: dup
      // 227a: bipush 1
      // 227b: new net/minecraft/client/animation/Keyframe
      // 227e: dup
      // 227f: ldc 0.5417
      // 2281: ldc -142.448
      // 2283: ldc -31.7449
      // 2285: ldc -3.357
      // 2287: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 228a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 228d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2290: aastore
      // 2291: dup
      // 2292: bipush 2
      // 2293: new net/minecraft/client/animation/Keyframe
      // 2296: dup
      // 2297: ldc 0.8333
      // 2299: ldc_w -53.2072
      // 229c: ldc_w 24.2886
      // 229f: ldc_w 1.4437
      // 22a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22ab: aastore
      // 22ac: dup
      // 22ad: bipush 3
      // 22ae: new net/minecraft/client/animation/Keyframe
      // 22b1: dup
      // 22b2: ldc 1.1667
      // 22b4: ldc_w -53.2072
      // 22b7: ldc_w 24.2886
      // 22ba: ldc_w 1.4437
      // 22bd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22c0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22c3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22c6: aastore
      // 22c7: dup
      // 22c8: bipush 4
      // 22c9: new net/minecraft/client/animation/Keyframe
      // 22cc: dup
      // 22cd: ldc 1.7083
      // 22cf: ldc -111.3411
      // 22d1: ldc 9.9509
      // 22d3: ldc -9.0921
      // 22d5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22d8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22db: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22de: aastore
      // 22df: dup
      // 22e0: bipush 5
      // 22e1: new net/minecraft/client/animation/Keyframe
      // 22e4: dup
      // 22e5: ldc 2.5833
      // 22e7: fconst_0
      // 22e8: fconst_0
      // 22e9: fconst_0
      // 22ea: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22ed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22f0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22f3: aastore
      // 22f4: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 22f7: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 22fa: ldc "leftarm"
      // 22fc: new net/minecraft/client/animation/AnimationChannel
      // 22ff: dup
      // 2300: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2303: bipush 6
      // 2305: anewarray 28
      // 2308: dup
      // 2309: bipush 0
      // 230a: new net/minecraft/client/animation/Keyframe
      // 230d: dup
      // 230e: fconst_0
      // 230f: fconst_0
      // 2310: fconst_0
      // 2311: fconst_0
      // 2312: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2315: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2318: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 231b: aastore
      // 231c: dup
      // 231d: bipush 1
      // 231e: new net/minecraft/client/animation/Keyframe
      // 2321: dup
      // 2322: ldc 0.5417
      // 2324: fconst_0
      // 2325: ldc 15.0
      // 2327: ldc -12.4
      // 2329: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 232c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 232f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2332: aastore
      // 2333: dup
      // 2334: bipush 2
      // 2335: new net/minecraft/client/animation/Keyframe
      // 2338: dup
      // 2339: ldc 0.9167
      // 233b: fconst_0
      // 233c: ldc_w -9.0
      // 233f: ldc -20.0
      // 2341: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2344: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2347: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 234a: aastore
      // 234b: dup
      // 234c: bipush 3
      // 234d: new net/minecraft/client/animation/Keyframe
      // 2350: dup
      // 2351: ldc 1.1667
      // 2353: fconst_0
      // 2354: ldc_w -9.0
      // 2357: ldc -20.0
      // 2359: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 235c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 235f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2362: aastore
      // 2363: dup
      // 2364: bipush 4
      // 2365: new net/minecraft/client/animation/Keyframe
      // 2368: dup
      // 2369: ldc 1.7083
      // 236b: fconst_0
      // 236c: fconst_0
      // 236d: ldc -18.0
      // 236f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2372: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2375: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2378: aastore
      // 2379: dup
      // 237a: bipush 5
      // 237b: new net/minecraft/client/animation/Keyframe
      // 237e: dup
      // 237f: ldc 2.5833
      // 2381: fconst_0
      // 2382: fconst_0
      // 2383: fconst_0
      // 2384: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2387: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 238a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 238d: aastore
      // 238e: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2391: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2394: ldc "leftarm2"
      // 2396: new net/minecraft/client/animation/AnimationChannel
      // 2399: dup
      // 239a: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 239d: bipush 5
      // 239e: anewarray 28
      // 23a1: dup
      // 23a2: bipush 0
      // 23a3: new net/minecraft/client/animation/Keyframe
      // 23a6: dup
      // 23a7: fconst_0
      // 23a8: fconst_0
      // 23a9: fconst_0
      // 23aa: fconst_0
      // 23ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23b4: aastore
      // 23b5: dup
      // 23b6: bipush 1
      // 23b7: new net/minecraft/client/animation/Keyframe
      // 23ba: dup
      // 23bb: ldc 0.5417
      // 23bd: ldc -52.5
      // 23bf: fconst_0
      // 23c0: fconst_0
      // 23c1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23c4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23c7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23ca: aastore
      // 23cb: dup
      // 23cc: bipush 2
      // 23cd: new net/minecraft/client/animation/Keyframe
      // 23d0: dup
      // 23d1: ldc 0.9167
      // 23d3: ldc_w -51.4145
      // 23d6: ldc_w 18.6346
      // 23d9: ldc_w -9.7203
      // 23dc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23df: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23e2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23e5: aastore
      // 23e6: dup
      // 23e7: bipush 3
      // 23e8: new net/minecraft/client/animation/Keyframe
      // 23eb: dup
      // 23ec: ldc 1.7083
      // 23ee: ldc -14.5182
      // 23f0: ldc 4.7511
      // 23f2: ldc 11.5752
      // 23f4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23f7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23fa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23fd: aastore
      // 23fe: dup
      // 23ff: bipush 4
      // 2400: new net/minecraft/client/animation/Keyframe
      // 2403: dup
      // 2404: ldc 2.5
      // 2406: fconst_0
      // 2407: fconst_0
      // 2408: fconst_0
      // 2409: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 240c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 240f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2412: aastore
      // 2413: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2416: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2419: ldc "lefthand"
      // 241b: new net/minecraft/client/animation/AnimationChannel
      // 241e: dup
      // 241f: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2422: bipush 3
      // 2423: anewarray 28
      // 2426: dup
      // 2427: bipush 0
      // 2428: new net/minecraft/client/animation/Keyframe
      // 242b: dup
      // 242c: fconst_0
      // 242d: fconst_0
      // 242e: fconst_0
      // 242f: fconst_0
      // 2430: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2433: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2436: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2439: aastore
      // 243a: dup
      // 243b: bipush 1
      // 243c: new net/minecraft/client/animation/Keyframe
      // 243f: dup
      // 2440: ldc 1.7083
      // 2442: fconst_0
      // 2443: fconst_0
      // 2444: fconst_0
      // 2445: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2448: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 244b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 244e: aastore
      // 244f: dup
      // 2450: bipush 2
      // 2451: new net/minecraft/client/animation/Keyframe
      // 2454: dup
      // 2455: ldc 2.5
      // 2457: fconst_0
      // 2458: fconst_0
      // 2459: fconst_0
      // 245a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 245d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2460: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2463: aastore
      // 2464: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2467: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 246a: ldc "leftfinger1"
      // 246c: new net/minecraft/client/animation/AnimationChannel
      // 246f: dup
      // 2470: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2473: bipush 4
      // 2474: anewarray 28
      // 2477: dup
      // 2478: bipush 0
      // 2479: new net/minecraft/client/animation/Keyframe
      // 247c: dup
      // 247d: fconst_0
      // 247e: fconst_0
      // 247f: fconst_0
      // 2480: fconst_0
      // 2481: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2484: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2487: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 248a: aastore
      // 248b: dup
      // 248c: bipush 1
      // 248d: new net/minecraft/client/animation/Keyframe
      // 2490: dup
      // 2491: ldc 0.5417
      // 2493: ldc -12.5
      // 2495: fconst_0
      // 2496: ldc 32.5
      // 2498: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 249b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 249e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24a1: aastore
      // 24a2: dup
      // 24a3: bipush 2
      // 24a4: new net/minecraft/client/animation/Keyframe
      // 24a7: dup
      // 24a8: ldc 1.7083
      // 24aa: ldc -12.5
      // 24ac: fconst_0
      // 24ad: ldc 32.5
      // 24af: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24b2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24b5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24b8: aastore
      // 24b9: dup
      // 24ba: bipush 3
      // 24bb: new net/minecraft/client/animation/Keyframe
      // 24be: dup
      // 24bf: ldc 2.5
      // 24c1: fconst_0
      // 24c2: fconst_0
      // 24c3: fconst_0
      // 24c4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24c7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24ca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24cd: aastore
      // 24ce: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 24d1: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 24d4: ldc "leftfinger2"
      // 24d6: new net/minecraft/client/animation/AnimationChannel
      // 24d9: dup
      // 24da: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 24dd: bipush 4
      // 24de: anewarray 28
      // 24e1: dup
      // 24e2: bipush 0
      // 24e3: new net/minecraft/client/animation/Keyframe
      // 24e6: dup
      // 24e7: fconst_0
      // 24e8: fconst_0
      // 24e9: fconst_0
      // 24ea: fconst_0
      // 24eb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24ee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24f1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24f4: aastore
      // 24f5: dup
      // 24f6: bipush 1
      // 24f7: new net/minecraft/client/animation/Keyframe
      // 24fa: dup
      // 24fb: ldc 0.5417
      // 24fd: ldc 12.5
      // 24ff: fconst_0
      // 2500: ldc 32.5
      // 2502: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2505: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2508: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 250b: aastore
      // 250c: dup
      // 250d: bipush 2
      // 250e: new net/minecraft/client/animation/Keyframe
      // 2511: dup
      // 2512: ldc 1.7083
      // 2514: ldc 12.5
      // 2516: fconst_0
      // 2517: ldc 32.5
      // 2519: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 251c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 251f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2522: aastore
      // 2523: dup
      // 2524: bipush 3
      // 2525: new net/minecraft/client/animation/Keyframe
      // 2528: dup
      // 2529: ldc 2.5
      // 252b: fconst_0
      // 252c: fconst_0
      // 252d: fconst_0
      // 252e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2531: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2534: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2537: aastore
      // 2538: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 253b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 253e: ldc "leftfinger3"
      // 2540: new net/minecraft/client/animation/AnimationChannel
      // 2543: dup
      // 2544: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2547: bipush 4
      // 2548: anewarray 28
      // 254b: dup
      // 254c: bipush 0
      // 254d: new net/minecraft/client/animation/Keyframe
      // 2550: dup
      // 2551: fconst_0
      // 2552: fconst_0
      // 2553: fconst_0
      // 2554: fconst_0
      // 2555: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2558: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 255b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 255e: aastore
      // 255f: dup
      // 2560: bipush 1
      // 2561: new net/minecraft/client/animation/Keyframe
      // 2564: dup
      // 2565: ldc 0.5417
      // 2567: fconst_0
      // 2568: fconst_0
      // 2569: ldc -32.5
      // 256b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 256e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2571: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2574: aastore
      // 2575: dup
      // 2576: bipush 2
      // 2577: new net/minecraft/client/animation/Keyframe
      // 257a: dup
      // 257b: ldc 1.7083
      // 257d: fconst_0
      // 257e: fconst_0
      // 257f: ldc -32.5
      // 2581: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2584: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2587: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 258a: aastore
      // 258b: dup
      // 258c: bipush 3
      // 258d: new net/minecraft/client/animation/Keyframe
      // 2590: dup
      // 2591: ldc 2.5
      // 2593: fconst_0
      // 2594: fconst_0
      // 2595: fconst_0
      // 2596: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2599: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 259c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 259f: aastore
      // 25a0: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 25a3: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 25a6: ldc "rightarm"
      // 25a8: new net/minecraft/client/animation/AnimationChannel
      // 25ab: dup
      // 25ac: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 25af: bipush 6
      // 25b1: anewarray 28
      // 25b4: dup
      // 25b5: bipush 0
      // 25b6: new net/minecraft/client/animation/Keyframe
      // 25b9: dup
      // 25ba: fconst_0
      // 25bb: fconst_0
      // 25bc: fconst_0
      // 25bd: fconst_0
      // 25be: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 25c1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25c4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25c7: aastore
      // 25c8: dup
      // 25c9: bipush 1
      // 25ca: new net/minecraft/client/animation/Keyframe
      // 25cd: dup
      // 25ce: ldc 0.5417
      // 25d0: ldc -142.5206
      // 25d2: ldc 26.4438
      // 25d4: ldc -1.9855
      // 25d6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 25d9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25dc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25df: aastore
      // 25e0: dup
      // 25e1: bipush 2
      // 25e2: new net/minecraft/client/animation/Keyframe
      // 25e5: dup
      // 25e6: ldc 0.8333
      // 25e8: ldc_w -47.926
      // 25eb: ldc_w -23.3339
      // 25ee: ldc_w -2.7127
      // 25f1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 25f4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25f7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25fa: aastore
      // 25fb: dup
      // 25fc: bipush 3
      // 25fd: new net/minecraft/client/animation/Keyframe
      // 2600: dup
      // 2601: ldc 1.1667
      // 2603: ldc_w -47.926
      // 2606: ldc_w -23.3339
      // 2609: ldc_w -2.7127
      // 260c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 260f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2612: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2615: aastore
      // 2616: dup
      // 2617: bipush 4
      // 2618: new net/minecraft/client/animation/Keyframe
      // 261b: dup
      // 261c: ldc 1.7083
      // 261e: ldc -111.3411
      // 2620: ldc -9.9509
      // 2622: ldc 9.0921
      // 2624: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2627: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 262a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 262d: aastore
      // 262e: dup
      // 262f: bipush 5
      // 2630: new net/minecraft/client/animation/Keyframe
      // 2633: dup
      // 2634: ldc 2.5833
      // 2636: fconst_0
      // 2637: fconst_0
      // 2638: fconst_0
      // 2639: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 263c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 263f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2642: aastore
      // 2643: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2646: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2649: ldc "rightarm"
      // 264b: new net/minecraft/client/animation/AnimationChannel
      // 264e: dup
      // 264f: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2652: bipush 6
      // 2654: anewarray 28
      // 2657: dup
      // 2658: bipush 0
      // 2659: new net/minecraft/client/animation/Keyframe
      // 265c: dup
      // 265d: fconst_0
      // 265e: fconst_0
      // 265f: fconst_0
      // 2660: fconst_0
      // 2661: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2664: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2667: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 266a: aastore
      // 266b: dup
      // 266c: bipush 1
      // 266d: new net/minecraft/client/animation/Keyframe
      // 2670: dup
      // 2671: ldc 0.5417
      // 2673: fconst_0
      // 2674: ldc 15.0
      // 2676: ldc -12.4
      // 2678: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 267b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 267e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2681: aastore
      // 2682: dup
      // 2683: bipush 2
      // 2684: new net/minecraft/client/animation/Keyframe
      // 2687: dup
      // 2688: ldc 0.9167
      // 268a: ldc 7.0
      // 268c: fconst_0
      // 268d: ldc_w -26.0
      // 2690: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2693: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2696: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2699: aastore
      // 269a: dup
      // 269b: bipush 3
      // 269c: new net/minecraft/client/animation/Keyframe
      // 269f: dup
      // 26a0: ldc 1.1667
      // 26a2: ldc 7.0
      // 26a4: fconst_0
      // 26a5: ldc_w -26.0
      // 26a8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 26ab: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26ae: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26b1: aastore
      // 26b2: dup
      // 26b3: bipush 4
      // 26b4: new net/minecraft/client/animation/Keyframe
      // 26b7: dup
      // 26b8: ldc 1.7083
      // 26ba: fconst_0
      // 26bb: fconst_0
      // 26bc: ldc -18.0
      // 26be: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 26c1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26c4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26c7: aastore
      // 26c8: dup
      // 26c9: bipush 5
      // 26ca: new net/minecraft/client/animation/Keyframe
      // 26cd: dup
      // 26ce: ldc 2.5833
      // 26d0: fconst_0
      // 26d1: fconst_0
      // 26d2: fconst_0
      // 26d3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 26d6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26d9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26dc: aastore
      // 26dd: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 26e0: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 26e3: ldc "rightarm2"
      // 26e5: new net/minecraft/client/animation/AnimationChannel
      // 26e8: dup
      // 26e9: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 26ec: bipush 5
      // 26ed: anewarray 28
      // 26f0: dup
      // 26f1: bipush 0
      // 26f2: new net/minecraft/client/animation/Keyframe
      // 26f5: dup
      // 26f6: fconst_0
      // 26f7: fconst_0
      // 26f8: fconst_0
      // 26f9: fconst_0
      // 26fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 26fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2700: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2703: aastore
      // 2704: dup
      // 2705: bipush 1
      // 2706: new net/minecraft/client/animation/Keyframe
      // 2709: dup
      // 270a: ldc 0.5417
      // 270c: ldc -57.5
      // 270e: fconst_0
      // 270f: fconst_0
      // 2710: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2713: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2716: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2719: aastore
      // 271a: dup
      // 271b: bipush 2
      // 271c: new net/minecraft/client/animation/Keyframe
      // 271f: dup
      // 2720: ldc 0.9167
      // 2722: ldc_w -50.722
      // 2725: ldc_w -17.3307
      // 2728: ldc_w 7.4776
      // 272b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 272e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2731: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2734: aastore
      // 2735: dup
      // 2736: bipush 3
      // 2737: new net/minecraft/client/animation/Keyframe
      // 273a: dup
      // 273b: ldc 1.7083
      // 273d: ldc -14.5182
      // 273f: ldc -4.7511
      // 2741: ldc -11.5752
      // 2743: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2746: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2749: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 274c: aastore
      // 274d: dup
      // 274e: bipush 4
      // 274f: new net/minecraft/client/animation/Keyframe
      // 2752: dup
      // 2753: ldc 2.5
      // 2755: fconst_0
      // 2756: fconst_0
      // 2757: fconst_0
      // 2758: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 275b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 275e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2761: aastore
      // 2762: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2765: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2768: ldc "righthand"
      // 276a: new net/minecraft/client/animation/AnimationChannel
      // 276d: dup
      // 276e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2771: bipush 3
      // 2772: anewarray 28
      // 2775: dup
      // 2776: bipush 0
      // 2777: new net/minecraft/client/animation/Keyframe
      // 277a: dup
      // 277b: fconst_0
      // 277c: fconst_0
      // 277d: fconst_0
      // 277e: fconst_0
      // 277f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2782: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2785: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2788: aastore
      // 2789: dup
      // 278a: bipush 1
      // 278b: new net/minecraft/client/animation/Keyframe
      // 278e: dup
      // 278f: ldc 1.7083
      // 2791: fconst_0
      // 2792: fconst_0
      // 2793: fconst_0
      // 2794: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2797: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 279a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 279d: aastore
      // 279e: dup
      // 279f: bipush 2
      // 27a0: new net/minecraft/client/animation/Keyframe
      // 27a3: dup
      // 27a4: ldc 2.5
      // 27a6: fconst_0
      // 27a7: fconst_0
      // 27a8: fconst_0
      // 27a9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 27ac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27af: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27b2: aastore
      // 27b3: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 27b6: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 27b9: ldc "rightfinger1"
      // 27bb: new net/minecraft/client/animation/AnimationChannel
      // 27be: dup
      // 27bf: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 27c2: bipush 4
      // 27c3: anewarray 28
      // 27c6: dup
      // 27c7: bipush 0
      // 27c8: new net/minecraft/client/animation/Keyframe
      // 27cb: dup
      // 27cc: fconst_0
      // 27cd: fconst_0
      // 27ce: fconst_0
      // 27cf: fconst_0
      // 27d0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 27d3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27d6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27d9: aastore
      // 27da: dup
      // 27db: bipush 1
      // 27dc: new net/minecraft/client/animation/Keyframe
      // 27df: dup
      // 27e0: ldc 0.5417
      // 27e2: ldc -12.5
      // 27e4: fconst_0
      // 27e5: ldc -32.5
      // 27e7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 27ea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27ed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27f0: aastore
      // 27f1: dup
      // 27f2: bipush 2
      // 27f3: new net/minecraft/client/animation/Keyframe
      // 27f6: dup
      // 27f7: ldc 1.7083
      // 27f9: ldc -12.5
      // 27fb: fconst_0
      // 27fc: ldc -32.5
      // 27fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2801: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2804: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2807: aastore
      // 2808: dup
      // 2809: bipush 3
      // 280a: new net/minecraft/client/animation/Keyframe
      // 280d: dup
      // 280e: ldc 2.5
      // 2810: fconst_0
      // 2811: fconst_0
      // 2812: fconst_0
      // 2813: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2816: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2819: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 281c: aastore
      // 281d: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2820: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2823: ldc "rightfinger2"
      // 2825: new net/minecraft/client/animation/AnimationChannel
      // 2828: dup
      // 2829: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 282c: bipush 4
      // 282d: anewarray 28
      // 2830: dup
      // 2831: bipush 0
      // 2832: new net/minecraft/client/animation/Keyframe
      // 2835: dup
      // 2836: fconst_0
      // 2837: fconst_0
      // 2838: fconst_0
      // 2839: fconst_0
      // 283a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 283d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2840: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2843: aastore
      // 2844: dup
      // 2845: bipush 1
      // 2846: new net/minecraft/client/animation/Keyframe
      // 2849: dup
      // 284a: ldc 0.5417
      // 284c: ldc 12.5
      // 284e: fconst_0
      // 284f: ldc -32.5
      // 2851: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2854: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2857: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 285a: aastore
      // 285b: dup
      // 285c: bipush 2
      // 285d: new net/minecraft/client/animation/Keyframe
      // 2860: dup
      // 2861: ldc 1.7083
      // 2863: ldc 12.5
      // 2865: fconst_0
      // 2866: ldc -32.5
      // 2868: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 286b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 286e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2871: aastore
      // 2872: dup
      // 2873: bipush 3
      // 2874: new net/minecraft/client/animation/Keyframe
      // 2877: dup
      // 2878: ldc 2.5
      // 287a: fconst_0
      // 287b: fconst_0
      // 287c: fconst_0
      // 287d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2880: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2883: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2886: aastore
      // 2887: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 288a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 288d: ldc "rightfinger3"
      // 288f: new net/minecraft/client/animation/AnimationChannel
      // 2892: dup
      // 2893: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2896: bipush 4
      // 2897: anewarray 28
      // 289a: dup
      // 289b: bipush 0
      // 289c: new net/minecraft/client/animation/Keyframe
      // 289f: dup
      // 28a0: fconst_0
      // 28a1: fconst_0
      // 28a2: fconst_0
      // 28a3: fconst_0
      // 28a4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 28a7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28aa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28ad: aastore
      // 28ae: dup
      // 28af: bipush 1
      // 28b0: new net/minecraft/client/animation/Keyframe
      // 28b3: dup
      // 28b4: ldc 0.5417
      // 28b6: fconst_0
      // 28b7: fconst_0
      // 28b8: ldc 32.5
      // 28ba: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 28bd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28c0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28c3: aastore
      // 28c4: dup
      // 28c5: bipush 2
      // 28c6: new net/minecraft/client/animation/Keyframe
      // 28c9: dup
      // 28ca: ldc 1.7083
      // 28cc: fconst_0
      // 28cd: fconst_0
      // 28ce: ldc 32.5
      // 28d0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 28d3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28d6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28d9: aastore
      // 28da: dup
      // 28db: bipush 3
      // 28dc: new net/minecraft/client/animation/Keyframe
      // 28df: dup
      // 28e0: ldc 2.5
      // 28e2: fconst_0
      // 28e3: fconst_0
      // 28e4: fconst_0
      // 28e5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 28e8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28eb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28ee: aastore
      // 28ef: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 28f2: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 28f5: ldc "rightleg"
      // 28f7: new net/minecraft/client/animation/AnimationChannel
      // 28fa: dup
      // 28fb: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 28fe: bipush 5
      // 28ff: anewarray 28
      // 2902: dup
      // 2903: bipush 0
      // 2904: new net/minecraft/client/animation/Keyframe
      // 2907: dup
      // 2908: fconst_0
      // 2909: fconst_0
      // 290a: fconst_0
      // 290b: fconst_0
      // 290c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 290f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2912: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2915: aastore
      // 2916: dup
      // 2917: bipush 1
      // 2918: new net/minecraft/client/animation/Keyframe
      // 291b: dup
      // 291c: ldc 0.4583
      // 291e: fconst_0
      // 291f: ldc 17.5
      // 2921: fconst_0
      // 2922: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2925: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2928: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 292b: aastore
      // 292c: dup
      // 292d: bipush 2
      // 292e: new net/minecraft/client/animation/Keyframe
      // 2931: dup
      // 2932: ldc 0.9583
      // 2934: fconst_0
      // 2935: fconst_0
      // 2936: fconst_0
      // 2937: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 293a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 293d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2940: aastore
      // 2941: dup
      // 2942: bipush 3
      // 2943: new net/minecraft/client/animation/Keyframe
      // 2946: dup
      // 2947: ldc 1.7083
      // 2949: fconst_0
      // 294a: fconst_0
      // 294b: fconst_0
      // 294c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 294f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2952: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2955: aastore
      // 2956: dup
      // 2957: bipush 4
      // 2958: new net/minecraft/client/animation/Keyframe
      // 295b: dup
      // 295c: ldc 2.5
      // 295e: fconst_0
      // 295f: fconst_0
      // 2960: fconst_0
      // 2961: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2964: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2967: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 296a: aastore
      // 296b: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 296e: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2971: ldc "rightleg"
      // 2973: new net/minecraft/client/animation/AnimationChannel
      // 2976: dup
      // 2977: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 297a: bipush 6
      // 297c: anewarray 28
      // 297f: dup
      // 2980: bipush 0
      // 2981: new net/minecraft/client/animation/Keyframe
      // 2984: dup
      // 2985: fconst_0
      // 2986: fconst_0
      // 2987: fconst_0
      // 2988: fconst_0
      // 2989: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 298c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 298f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2992: aastore
      // 2993: dup
      // 2994: bipush 1
      // 2995: new net/minecraft/client/animation/Keyframe
      // 2998: dup
      // 2999: ldc 0.5833
      // 299b: fconst_0
      // 299c: fconst_0
      // 299d: ldc -8.0
      // 299f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 29a2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29a5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29a8: aastore
      // 29a9: dup
      // 29aa: bipush 2
      // 29ab: new net/minecraft/client/animation/Keyframe
      // 29ae: dup
      // 29af: ldc 0.6667
      // 29b1: fconst_0
      // 29b2: ldc 7.07
      // 29b4: ldc -11.85
      // 29b6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 29b9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29bc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29bf: aastore
      // 29c0: dup
      // 29c1: bipush 3
      // 29c2: new net/minecraft/client/animation/Keyframe
      // 29c5: dup
      // 29c6: ldc 0.8333
      // 29c8: fconst_0
      // 29c9: ldc 7.0
      // 29cb: ldc -19.0
      // 29cd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 29d0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29d3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29d6: aastore
      // 29d7: dup
      // 29d8: bipush 4
      // 29d9: new net/minecraft/client/animation/Keyframe
      // 29dc: dup
      // 29dd: ldc 1.7083
      // 29df: fconst_0
      // 29e0: ldc 7.0
      // 29e2: ldc -19.0
      // 29e4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 29e7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29ea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29ed: aastore
      // 29ee: dup
      // 29ef: bipush 5
      // 29f0: new net/minecraft/client/animation/Keyframe
      // 29f3: dup
      // 29f4: ldc 2.5
      // 29f6: fconst_0
      // 29f7: fconst_0
      // 29f8: fconst_0
      // 29f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 29fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a02: aastore
      // 2a03: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2a06: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2a09: ldc "leftleg"
      // 2a0b: new net/minecraft/client/animation/AnimationChannel
      // 2a0e: dup
      // 2a0f: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2a12: bipush 5
      // 2a13: anewarray 28
      // 2a16: dup
      // 2a17: bipush 0
      // 2a18: new net/minecraft/client/animation/Keyframe
      // 2a1b: dup
      // 2a1c: fconst_0
      // 2a1d: fconst_0
      // 2a1e: fconst_0
      // 2a1f: fconst_0
      // 2a20: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a23: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a26: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a29: aastore
      // 2a2a: dup
      // 2a2b: bipush 1
      // 2a2c: new net/minecraft/client/animation/Keyframe
      // 2a2f: dup
      // 2a30: ldc 0.5417
      // 2a32: fconst_0
      // 2a33: ldc -15.0
      // 2a35: fconst_0
      // 2a36: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a39: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a3c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a3f: aastore
      // 2a40: dup
      // 2a41: bipush 2
      // 2a42: new net/minecraft/client/animation/Keyframe
      // 2a45: dup
      // 2a46: ldc 0.7917
      // 2a48: ldc 90.0
      // 2a4a: fconst_0
      // 2a4b: fconst_0
      // 2a4c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a4f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a52: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a55: aastore
      // 2a56: dup
      // 2a57: bipush 3
      // 2a58: new net/minecraft/client/animation/Keyframe
      // 2a5b: dup
      // 2a5c: ldc 1.7083
      // 2a5e: ldc 90.0
      // 2a60: fconst_0
      // 2a61: fconst_0
      // 2a62: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a65: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a68: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a6b: aastore
      // 2a6c: dup
      // 2a6d: bipush 4
      // 2a6e: new net/minecraft/client/animation/Keyframe
      // 2a71: dup
      // 2a72: ldc 2.5
      // 2a74: fconst_0
      // 2a75: fconst_0
      // 2a76: fconst_0
      // 2a77: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a7a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a7d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a80: aastore
      // 2a81: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2a84: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2a87: ldc "leftleg"
      // 2a89: new net/minecraft/client/animation/AnimationChannel
      // 2a8c: dup
      // 2a8d: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2a90: bipush 5
      // 2a91: anewarray 28
      // 2a94: dup
      // 2a95: bipush 0
      // 2a96: new net/minecraft/client/animation/Keyframe
      // 2a99: dup
      // 2a9a: fconst_0
      // 2a9b: fconst_0
      // 2a9c: fconst_0
      // 2a9d: fconst_0
      // 2a9e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2aa1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2aa4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2aa7: aastore
      // 2aa8: dup
      // 2aa9: bipush 1
      // 2aaa: new net/minecraft/client/animation/Keyframe
      // 2aad: dup
      // 2aae: ldc 0.5417
      // 2ab0: fconst_0
      // 2ab1: fconst_0
      // 2ab2: fconst_0
      // 2ab3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2ab6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ab9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2abc: aastore
      // 2abd: dup
      // 2abe: bipush 2
      // 2abf: new net/minecraft/client/animation/Keyframe
      // 2ac2: dup
      // 2ac3: ldc 0.7917
      // 2ac5: fconst_0
      // 2ac6: ldc -12.0
      // 2ac8: ldc 5.0
      // 2aca: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2acd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ad0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ad3: aastore
      // 2ad4: dup
      // 2ad5: bipush 3
      // 2ad6: new net/minecraft/client/animation/Keyframe
      // 2ad9: dup
      // 2ada: ldc 1.7083
      // 2adc: fconst_0
      // 2add: ldc -12.0
      // 2adf: ldc 5.0
      // 2ae1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2ae4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ae7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2aea: aastore
      // 2aeb: dup
      // 2aec: bipush 4
      // 2aed: new net/minecraft/client/animation/Keyframe
      // 2af0: dup
      // 2af1: ldc 2.5
      // 2af3: fconst_0
      // 2af4: fconst_0
      // 2af5: fconst_0
      // 2af6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2af9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2afc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2aff: aastore
      // 2b00: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2b03: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2b06: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.build ()Lnet/minecraft/client/animation/AnimationDefinition;
      // 2b09: putstatic com/github/L_Ender/cataclysm/client/animation/Netherite_Monstrosity_Animation.PHASE Lnet/minecraft/client/animation/AnimationDefinition;
      // 2b0c: ldc_w 2.1667
      // 2b0f: invokestatic net/minecraft/client/animation/AnimationDefinition$Builder.withLength (F)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2b12: ldc "roots"
      // 2b14: new net/minecraft/client/animation/AnimationChannel
      // 2b17: dup
      // 2b18: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2b1b: bipush 5
      // 2b1c: anewarray 28
      // 2b1f: dup
      // 2b20: bipush 0
      // 2b21: new net/minecraft/client/animation/Keyframe
      // 2b24: dup
      // 2b25: fconst_0
      // 2b26: fconst_0
      // 2b27: fconst_0
      // 2b28: fconst_0
      // 2b29: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2b2c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b2f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b32: aastore
      // 2b33: dup
      // 2b34: bipush 1
      // 2b35: new net/minecraft/client/animation/Keyframe
      // 2b38: dup
      // 2b39: ldc 0.7083
      // 2b3b: fconst_0
      // 2b3c: fconst_0
      // 2b3d: fconst_0
      // 2b3e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2b41: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b44: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b47: aastore
      // 2b48: dup
      // 2b49: bipush 2
      // 2b4a: new net/minecraft/client/animation/Keyframe
      // 2b4d: dup
      // 2b4e: fconst_1
      // 2b4f: fconst_0
      // 2b50: ldc -2.0
      // 2b52: fconst_0
      // 2b53: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2b56: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b59: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b5c: aastore
      // 2b5d: dup
      // 2b5e: bipush 3
      // 2b5f: new net/minecraft/client/animation/Keyframe
      // 2b62: dup
      // 2b63: ldc 1.4167
      // 2b65: fconst_0
      // 2b66: fconst_0
      // 2b67: fconst_0
      // 2b68: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2b6b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b6e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b71: aastore
      // 2b72: dup
      // 2b73: bipush 4
      // 2b74: new net/minecraft/client/animation/Keyframe
      // 2b77: dup
      // 2b78: ldc_w 2.0833
      // 2b7b: fconst_0
      // 2b7c: fconst_0
      // 2b7d: fconst_0
      // 2b7e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2b81: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b84: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b87: aastore
      // 2b88: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2b8b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2b8e: ldc "lowerbody"
      // 2b90: new net/minecraft/client/animation/AnimationChannel
      // 2b93: dup
      // 2b94: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2b97: bipush 5
      // 2b98: anewarray 28
      // 2b9b: dup
      // 2b9c: bipush 0
      // 2b9d: new net/minecraft/client/animation/Keyframe
      // 2ba0: dup
      // 2ba1: fconst_0
      // 2ba2: fconst_0
      // 2ba3: fconst_0
      // 2ba4: fconst_0
      // 2ba5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2ba8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2bab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2bae: aastore
      // 2baf: dup
      // 2bb0: bipush 1
      // 2bb1: new net/minecraft/client/animation/Keyframe
      // 2bb4: dup
      // 2bb5: ldc 0.8333
      // 2bb7: fconst_0
      // 2bb8: fconst_0
      // 2bb9: fconst_0
      // 2bba: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2bbd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2bc0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2bc3: aastore
      // 2bc4: dup
      // 2bc5: bipush 2
      // 2bc6: new net/minecraft/client/animation/Keyframe
      // 2bc9: dup
      // 2bca: ldc 1.1667
      // 2bcc: fconst_0
      // 2bcd: ldc -3.0
      // 2bcf: fconst_0
      // 2bd0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2bd3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2bd6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2bd9: aastore
      // 2bda: dup
      // 2bdb: bipush 3
      // 2bdc: new net/minecraft/client/animation/Keyframe
      // 2bdf: dup
      // 2be0: ldc 1.4583
      // 2be2: fconst_0
      // 2be3: fconst_0
      // 2be4: fconst_0
      // 2be5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2be8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2beb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2bee: aastore
      // 2bef: dup
      // 2bf0: bipush 4
      // 2bf1: new net/minecraft/client/animation/Keyframe
      // 2bf4: dup
      // 2bf5: ldc_w 2.0833
      // 2bf8: fconst_0
      // 2bf9: fconst_0
      // 2bfa: fconst_0
      // 2bfb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2bfe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c01: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c04: aastore
      // 2c05: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2c08: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2c0b: ldc "upperbody"
      // 2c0d: new net/minecraft/client/animation/AnimationChannel
      // 2c10: dup
      // 2c11: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2c14: bipush 6
      // 2c16: anewarray 28
      // 2c19: dup
      // 2c1a: bipush 0
      // 2c1b: new net/minecraft/client/animation/Keyframe
      // 2c1e: dup
      // 2c1f: fconst_0
      // 2c20: fconst_0
      // 2c21: fconst_0
      // 2c22: fconst_0
      // 2c23: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c26: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c29: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c2c: aastore
      // 2c2d: dup
      // 2c2e: bipush 1
      // 2c2f: new net/minecraft/client/animation/Keyframe
      // 2c32: dup
      // 2c33: ldc 0.5417
      // 2c35: ldc -37.5
      // 2c37: fconst_0
      // 2c38: fconst_0
      // 2c39: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c3c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c3f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c42: aastore
      // 2c43: dup
      // 2c44: bipush 2
      // 2c45: new net/minecraft/client/animation/Keyframe
      // 2c48: dup
      // 2c49: ldc 0.8333
      // 2c4b: ldc -37.5
      // 2c4d: fconst_0
      // 2c4e: fconst_0
      // 2c4f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c52: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c55: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c58: aastore
      // 2c59: dup
      // 2c5a: bipush 3
      // 2c5b: new net/minecraft/client/animation/Keyframe
      // 2c5e: dup
      // 2c5f: ldc 0.9583
      // 2c61: ldc -52.5
      // 2c63: fconst_0
      // 2c64: fconst_0
      // 2c65: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c68: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c6b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c6e: aastore
      // 2c6f: dup
      // 2c70: bipush 4
      // 2c71: new net/minecraft/client/animation/Keyframe
      // 2c74: dup
      // 2c75: ldc 1.4583
      // 2c77: ldc -37.5
      // 2c79: fconst_0
      // 2c7a: fconst_0
      // 2c7b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c7e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c81: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c84: aastore
      // 2c85: dup
      // 2c86: bipush 5
      // 2c87: new net/minecraft/client/animation/Keyframe
      // 2c8a: dup
      // 2c8b: fconst_2
      // 2c8c: fconst_0
      // 2c8d: fconst_0
      // 2c8e: fconst_0
      // 2c8f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c92: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c95: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c98: aastore
      // 2c99: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2c9c: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2c9f: ldc "upperbody"
      // 2ca1: new net/minecraft/client/animation/AnimationChannel
      // 2ca4: dup
      // 2ca5: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2ca8: bipush 6
      // 2caa: anewarray 28
      // 2cad: dup
      // 2cae: bipush 0
      // 2caf: new net/minecraft/client/animation/Keyframe
      // 2cb2: dup
      // 2cb3: fconst_0
      // 2cb4: fconst_0
      // 2cb5: fconst_0
      // 2cb6: fconst_0
      // 2cb7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2cba: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2cbd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2cc0: aastore
      // 2cc1: dup
      // 2cc2: bipush 1
      // 2cc3: new net/minecraft/client/animation/Keyframe
      // 2cc6: dup
      // 2cc7: ldc 0.5417
      // 2cc9: fconst_0
      // 2cca: ldc_w -4.0
      // 2ccd: fconst_0
      // 2cce: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2cd1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2cd4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2cd7: aastore
      // 2cd8: dup
      // 2cd9: bipush 2
      // 2cda: new net/minecraft/client/animation/Keyframe
      // 2cdd: dup
      // 2cde: ldc 0.8333
      // 2ce0: fconst_0
      // 2ce1: ldc_w -4.0
      // 2ce4: fconst_0
      // 2ce5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2ce8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ceb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2cee: aastore
      // 2cef: dup
      // 2cf0: bipush 3
      // 2cf1: new net/minecraft/client/animation/Keyframe
      // 2cf4: dup
      // 2cf5: ldc 1.125
      // 2cf7: fconst_0
      // 2cf8: ldc_w -4.0
      // 2cfb: fconst_0
      // 2cfc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2cff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d02: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d05: aastore
      // 2d06: dup
      // 2d07: bipush 4
      // 2d08: new net/minecraft/client/animation/Keyframe
      // 2d0b: dup
      // 2d0c: ldc 1.4583
      // 2d0e: fconst_0
      // 2d0f: ldc_w -4.0
      // 2d12: fconst_0
      // 2d13: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2d16: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d19: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d1c: aastore
      // 2d1d: dup
      // 2d1e: bipush 5
      // 2d1f: new net/minecraft/client/animation/Keyframe
      // 2d22: dup
      // 2d23: ldc_w 2.0833
      // 2d26: fconst_0
      // 2d27: fconst_0
      // 2d28: fconst_0
      // 2d29: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2d2c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d2f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d32: aastore
      // 2d33: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2d36: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2d39: ldc "head"
      // 2d3b: new net/minecraft/client/animation/AnimationChannel
      // 2d3e: dup
      // 2d3f: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2d42: bipush 5
      // 2d43: anewarray 28
      // 2d46: dup
      // 2d47: bipush 0
      // 2d48: new net/minecraft/client/animation/Keyframe
      // 2d4b: dup
      // 2d4c: fconst_0
      // 2d4d: fconst_0
      // 2d4e: fconst_0
      // 2d4f: fconst_0
      // 2d50: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d53: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d56: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d59: aastore
      // 2d5a: dup
      // 2d5b: bipush 1
      // 2d5c: new net/minecraft/client/animation/Keyframe
      // 2d5f: dup
      // 2d60: ldc 0.75
      // 2d62: fconst_0
      // 2d63: fconst_0
      // 2d64: fconst_0
      // 2d65: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d68: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d6b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d6e: aastore
      // 2d6f: dup
      // 2d70: bipush 2
      // 2d71: new net/minecraft/client/animation/Keyframe
      // 2d74: dup
      // 2d75: ldc 1.0833
      // 2d77: ldc_w -40.0
      // 2d7a: fconst_0
      // 2d7b: fconst_0
      // 2d7c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d7f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d82: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d85: aastore
      // 2d86: dup
      // 2d87: bipush 3
      // 2d88: new net/minecraft/client/animation/Keyframe
      // 2d8b: dup
      // 2d8c: ldc 1.5417
      // 2d8e: ldc_w 20.0
      // 2d91: fconst_0
      // 2d92: fconst_0
      // 2d93: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d96: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d99: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d9c: aastore
      // 2d9d: dup
      // 2d9e: bipush 4
      // 2d9f: new net/minecraft/client/animation/Keyframe
      // 2da2: dup
      // 2da3: ldc_w 2.1667
      // 2da6: fconst_0
      // 2da7: fconst_0
      // 2da8: fconst_0
      // 2da9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2dac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2daf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2db2: aastore
      // 2db3: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2db6: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2db9: ldc "jaw"
      // 2dbb: new net/minecraft/client/animation/AnimationChannel
      // 2dbe: dup
      // 2dbf: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2dc2: bipush 5
      // 2dc3: anewarray 28
      // 2dc6: dup
      // 2dc7: bipush 0
      // 2dc8: new net/minecraft/client/animation/Keyframe
      // 2dcb: dup
      // 2dcc: fconst_0
      // 2dcd: fconst_0
      // 2dce: fconst_0
      // 2dcf: fconst_0
      // 2dd0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2dd3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2dd6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2dd9: aastore
      // 2dda: dup
      // 2ddb: bipush 1
      // 2ddc: new net/minecraft/client/animation/Keyframe
      // 2ddf: dup
      // 2de0: ldc 0.8333
      // 2de2: fconst_0
      // 2de3: fconst_0
      // 2de4: fconst_0
      // 2de5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2de8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2deb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2dee: aastore
      // 2def: dup
      // 2df0: bipush 2
      // 2df1: new net/minecraft/client/animation/Keyframe
      // 2df4: dup
      // 2df5: ldc 1.0417
      // 2df7: ldc_w 62.5
      // 2dfa: fconst_0
      // 2dfb: fconst_0
      // 2dfc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2dff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e02: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e05: aastore
      // 2e06: dup
      // 2e07: bipush 3
      // 2e08: new net/minecraft/client/animation/Keyframe
      // 2e0b: dup
      // 2e0c: ldc 1.4583
      // 2e0e: fconst_0
      // 2e0f: fconst_0
      // 2e10: fconst_0
      // 2e11: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e14: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e17: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e1a: aastore
      // 2e1b: dup
      // 2e1c: bipush 4
      // 2e1d: new net/minecraft/client/animation/Keyframe
      // 2e20: dup
      // 2e21: ldc_w 2.1667
      // 2e24: fconst_0
      // 2e25: fconst_0
      // 2e26: fconst_0
      // 2e27: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e2a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e2d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e30: aastore
      // 2e31: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2e34: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2e37: ldc "leftarm"
      // 2e39: new net/minecraft/client/animation/AnimationChannel
      // 2e3c: dup
      // 2e3d: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2e40: bipush 7
      // 2e42: anewarray 28
      // 2e45: dup
      // 2e46: bipush 0
      // 2e47: new net/minecraft/client/animation/Keyframe
      // 2e4a: dup
      // 2e4b: fconst_0
      // 2e4c: fconst_0
      // 2e4d: fconst_0
      // 2e4e: fconst_0
      // 2e4f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e52: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e55: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e58: aastore
      // 2e59: dup
      // 2e5a: bipush 1
      // 2e5b: new net/minecraft/client/animation/Keyframe
      // 2e5e: dup
      // 2e5f: ldc 0.5417
      // 2e61: ldc 90.0
      // 2e63: ldc -57.5
      // 2e65: ldc -90.0
      // 2e67: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e6a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e6d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e70: aastore
      // 2e71: dup
      // 2e72: bipush 2
      // 2e73: new net/minecraft/client/animation/Keyframe
      // 2e76: dup
      // 2e77: ldc 0.8333
      // 2e79: ldc 90.0
      // 2e7b: ldc -57.5
      // 2e7d: ldc -90.0
      // 2e7f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e82: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e85: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e88: aastore
      // 2e89: dup
      // 2e8a: bipush 3
      // 2e8b: new net/minecraft/client/animation/Keyframe
      // 2e8e: dup
      // 2e8f: fconst_1
      // 2e90: ldc 90.0
      // 2e92: ldc_w -42.5
      // 2e95: ldc -90.0
      // 2e97: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e9a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e9d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ea0: aastore
      // 2ea1: dup
      // 2ea2: bipush 4
      // 2ea3: new net/minecraft/client/animation/Keyframe
      // 2ea6: dup
      // 2ea7: ldc 1.1667
      // 2ea9: ldc 90.0
      // 2eab: ldc_w -42.5
      // 2eae: ldc -90.0
      // 2eb0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2eb3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2eb6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2eb9: aastore
      // 2eba: dup
      // 2ebb: bipush 5
      // 2ebc: new net/minecraft/client/animation/Keyframe
      // 2ebf: dup
      // 2ec0: ldc 1.4583
      // 2ec2: ldc 90.0
      // 2ec4: ldc -57.5
      // 2ec6: ldc -90.0
      // 2ec8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ecb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ece: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ed1: aastore
      // 2ed2: dup
      // 2ed3: bipush 6
      // 2ed5: new net/minecraft/client/animation/Keyframe
      // 2ed8: dup
      // 2ed9: ldc 1.9167
      // 2edb: fconst_0
      // 2edc: fconst_0
      // 2edd: fconst_0
      // 2ede: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ee1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ee4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ee7: aastore
      // 2ee8: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2eeb: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2eee: ldc "leftarm"
      // 2ef0: new net/minecraft/client/animation/AnimationChannel
      // 2ef3: dup
      // 2ef4: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2ef7: bipush 6
      // 2ef9: anewarray 28
      // 2efc: dup
      // 2efd: bipush 0
      // 2efe: new net/minecraft/client/animation/Keyframe
      // 2f01: dup
      // 2f02: fconst_0
      // 2f03: fconst_0
      // 2f04: fconst_0
      // 2f05: fconst_0
      // 2f06: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2f09: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f0c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f0f: aastore
      // 2f10: dup
      // 2f11: bipush 1
      // 2f12: new net/minecraft/client/animation/Keyframe
      // 2f15: dup
      // 2f16: ldc 0.5417
      // 2f18: ldc_w 14.0
      // 2f1b: fconst_0
      // 2f1c: fconst_0
      // 2f1d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2f20: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f23: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f26: aastore
      // 2f27: dup
      // 2f28: bipush 2
      // 2f29: new net/minecraft/client/animation/Keyframe
      // 2f2c: dup
      // 2f2d: ldc 0.8333
      // 2f2f: ldc_w 14.0
      // 2f32: ldc_w -14.0
      // 2f35: fconst_0
      // 2f36: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2f39: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f3c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f3f: aastore
      // 2f40: dup
      // 2f41: bipush 3
      // 2f42: new net/minecraft/client/animation/Keyframe
      // 2f45: dup
      // 2f46: fconst_1
      // 2f47: ldc_w 14.0
      // 2f4a: ldc -20.0
      // 2f4c: ldc 7.0
      // 2f4e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2f51: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f54: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f57: aastore
      // 2f58: dup
      // 2f59: bipush 4
      // 2f5a: new net/minecraft/client/animation/Keyframe
      // 2f5d: dup
      // 2f5e: ldc 1.4583
      // 2f60: ldc_w 14.0
      // 2f63: ldc_w -14.0
      // 2f66: fconst_0
      // 2f67: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2f6a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f6d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f70: aastore
      // 2f71: dup
      // 2f72: bipush 5
      // 2f73: new net/minecraft/client/animation/Keyframe
      // 2f76: dup
      // 2f77: ldc_w 2.0833
      // 2f7a: fconst_0
      // 2f7b: fconst_0
      // 2f7c: fconst_0
      // 2f7d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2f80: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f83: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f86: aastore
      // 2f87: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2f8a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2f8d: ldc "leftarm2"
      // 2f8f: new net/minecraft/client/animation/AnimationChannel
      // 2f92: dup
      // 2f93: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2f96: bipush 5
      // 2f97: anewarray 28
      // 2f9a: dup
      // 2f9b: bipush 0
      // 2f9c: new net/minecraft/client/animation/Keyframe
      // 2f9f: dup
      // 2fa0: fconst_0
      // 2fa1: fconst_0
      // 2fa2: fconst_0
      // 2fa3: fconst_0
      // 2fa4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2fa7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2faa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2fad: aastore
      // 2fae: dup
      // 2faf: bipush 1
      // 2fb0: new net/minecraft/client/animation/Keyframe
      // 2fb3: dup
      // 2fb4: ldc 0.5417
      // 2fb6: ldc 7.5
      // 2fb8: fconst_0
      // 2fb9: fconst_0
      // 2fba: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2fbd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2fc0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2fc3: aastore
      // 2fc4: dup
      // 2fc5: bipush 2
      // 2fc6: new net/minecraft/client/animation/Keyframe
      // 2fc9: dup
      // 2fca: ldc 0.8333
      // 2fcc: ldc 7.5
      // 2fce: fconst_0
      // 2fcf: fconst_0
      // 2fd0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2fd3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2fd6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2fd9: aastore
      // 2fda: dup
      // 2fdb: bipush 3
      // 2fdc: new net/minecraft/client/animation/Keyframe
      // 2fdf: dup
      // 2fe0: ldc 1.375
      // 2fe2: ldc 7.5
      // 2fe4: fconst_0
      // 2fe5: fconst_0
      // 2fe6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2fe9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2fec: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2fef: aastore
      // 2ff0: dup
      // 2ff1: bipush 4
      // 2ff2: new net/minecraft/client/animation/Keyframe
      // 2ff5: dup
      // 2ff6: ldc_w 2.0833
      // 2ff9: fconst_0
      // 2ffa: fconst_0
      // 2ffb: fconst_0
      // 2ffc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2fff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3002: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3005: aastore
      // 3006: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3009: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 300c: ldc "lefthand"
      // 300e: new net/minecraft/client/animation/AnimationChannel
      // 3011: dup
      // 3012: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3015: bipush 5
      // 3016: anewarray 28
      // 3019: dup
      // 301a: bipush 0
      // 301b: new net/minecraft/client/animation/Keyframe
      // 301e: dup
      // 301f: fconst_0
      // 3020: fconst_0
      // 3021: fconst_0
      // 3022: fconst_0
      // 3023: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3026: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3029: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 302c: aastore
      // 302d: dup
      // 302e: bipush 1
      // 302f: new net/minecraft/client/animation/Keyframe
      // 3032: dup
      // 3033: ldc 0.5417
      // 3035: ldc 5.0
      // 3037: fconst_0
      // 3038: fconst_0
      // 3039: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 303c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 303f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3042: aastore
      // 3043: dup
      // 3044: bipush 2
      // 3045: new net/minecraft/client/animation/Keyframe
      // 3048: dup
      // 3049: ldc 0.8333
      // 304b: ldc 5.0
      // 304d: fconst_0
      // 304e: fconst_0
      // 304f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3052: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3055: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3058: aastore
      // 3059: dup
      // 305a: bipush 3
      // 305b: new net/minecraft/client/animation/Keyframe
      // 305e: dup
      // 305f: ldc 1.375
      // 3061: ldc 5.0
      // 3063: fconst_0
      // 3064: fconst_0
      // 3065: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3068: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 306b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 306e: aastore
      // 306f: dup
      // 3070: bipush 4
      // 3071: new net/minecraft/client/animation/Keyframe
      // 3074: dup
      // 3075: ldc_w 2.0833
      // 3078: fconst_0
      // 3079: fconst_0
      // 307a: fconst_0
      // 307b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 307e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3081: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3084: aastore
      // 3085: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3088: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 308b: ldc "lefthand"
      // 308d: new net/minecraft/client/animation/AnimationChannel
      // 3090: dup
      // 3091: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3094: bipush 6
      // 3096: anewarray 28
      // 3099: dup
      // 309a: bipush 0
      // 309b: new net/minecraft/client/animation/Keyframe
      // 309e: dup
      // 309f: fconst_0
      // 30a0: fconst_0
      // 30a1: fconst_0
      // 30a2: fconst_0
      // 30a3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 30a6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30a9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30ac: aastore
      // 30ad: dup
      // 30ae: bipush 1
      // 30af: new net/minecraft/client/animation/Keyframe
      // 30b2: dup
      // 30b3: ldc 0.8333
      // 30b5: fconst_0
      // 30b6: fconst_0
      // 30b7: fconst_0
      // 30b8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 30bb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30be: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30c1: aastore
      // 30c2: dup
      // 30c3: bipush 2
      // 30c4: new net/minecraft/client/animation/Keyframe
      // 30c7: dup
      // 30c8: fconst_1
      // 30c9: fconst_0
      // 30ca: ldc_w 9.0
      // 30cd: fconst_0
      // 30ce: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 30d1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30d4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30d7: aastore
      // 30d8: dup
      // 30d9: bipush 3
      // 30da: new net/minecraft/client/animation/Keyframe
      // 30dd: dup
      // 30de: ldc 1.1667
      // 30e0: fconst_0
      // 30e1: ldc_w 6.74
      // 30e4: fconst_0
      // 30e5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 30e8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30eb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30ee: aastore
      // 30ef: dup
      // 30f0: bipush 4
      // 30f1: new net/minecraft/client/animation/Keyframe
      // 30f4: dup
      // 30f5: ldc 1.375
      // 30f7: fconst_0
      // 30f8: ldc_w 6.74
      // 30fb: fconst_0
      // 30fc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 30ff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3102: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3105: aastore
      // 3106: dup
      // 3107: bipush 5
      // 3108: new net/minecraft/client/animation/Keyframe
      // 310b: dup
      // 310c: ldc_w 2.0833
      // 310f: fconst_0
      // 3110: fconst_0
      // 3111: fconst_0
      // 3112: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3115: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3118: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 311b: aastore
      // 311c: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 311f: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3122: ldc "rightarm"
      // 3124: new net/minecraft/client/animation/AnimationChannel
      // 3127: dup
      // 3128: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 312b: bipush 7
      // 312d: anewarray 28
      // 3130: dup
      // 3131: bipush 0
      // 3132: new net/minecraft/client/animation/Keyframe
      // 3135: dup
      // 3136: fconst_0
      // 3137: fconst_0
      // 3138: fconst_0
      // 3139: fconst_0
      // 313a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 313d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3140: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3143: aastore
      // 3144: dup
      // 3145: bipush 1
      // 3146: new net/minecraft/client/animation/Keyframe
      // 3149: dup
      // 314a: ldc 0.5417
      // 314c: ldc 90.0
      // 314e: ldc_w 57.5
      // 3151: ldc 90.0
      // 3153: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3156: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3159: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 315c: aastore
      // 315d: dup
      // 315e: bipush 2
      // 315f: new net/minecraft/client/animation/Keyframe
      // 3162: dup
      // 3163: ldc 0.8333
      // 3165: ldc 90.0
      // 3167: ldc_w 57.5
      // 316a: ldc 90.0
      // 316c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 316f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3172: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3175: aastore
      // 3176: dup
      // 3177: bipush 3
      // 3178: new net/minecraft/client/animation/Keyframe
      // 317b: dup
      // 317c: fconst_1
      // 317d: ldc 90.0
      // 317f: ldc_w 42.5
      // 3182: ldc 90.0
      // 3184: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3187: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 318a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 318d: aastore
      // 318e: dup
      // 318f: bipush 4
      // 3190: new net/minecraft/client/animation/Keyframe
      // 3193: dup
      // 3194: ldc 1.1667
      // 3196: ldc 90.0
      // 3198: ldc_w 42.5
      // 319b: ldc 90.0
      // 319d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31a0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31a3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31a6: aastore
      // 31a7: dup
      // 31a8: bipush 5
      // 31a9: new net/minecraft/client/animation/Keyframe
      // 31ac: dup
      // 31ad: ldc 1.4583
      // 31af: ldc 90.0
      // 31b1: ldc_w 57.5
      // 31b4: ldc 90.0
      // 31b6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31b9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31bc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31bf: aastore
      // 31c0: dup
      // 31c1: bipush 6
      // 31c3: new net/minecraft/client/animation/Keyframe
      // 31c6: dup
      // 31c7: ldc 1.9167
      // 31c9: fconst_0
      // 31ca: fconst_0
      // 31cb: fconst_0
      // 31cc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31cf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31d2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31d5: aastore
      // 31d6: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 31d9: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 31dc: ldc "rightarm"
      // 31de: new net/minecraft/client/animation/AnimationChannel
      // 31e1: dup
      // 31e2: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 31e5: bipush 6
      // 31e7: anewarray 28
      // 31ea: dup
      // 31eb: bipush 0
      // 31ec: new net/minecraft/client/animation/Keyframe
      // 31ef: dup
      // 31f0: fconst_0
      // 31f1: fconst_0
      // 31f2: fconst_0
      // 31f3: fconst_0
      // 31f4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 31f7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31fa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31fd: aastore
      // 31fe: dup
      // 31ff: bipush 1
      // 3200: new net/minecraft/client/animation/Keyframe
      // 3203: dup
      // 3204: ldc 0.5417
      // 3206: ldc_w -14.0
      // 3209: fconst_0
      // 320a: fconst_0
      // 320b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 320e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3211: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3214: aastore
      // 3215: dup
      // 3216: bipush 2
      // 3217: new net/minecraft/client/animation/Keyframe
      // 321a: dup
      // 321b: ldc 0.8333
      // 321d: ldc_w -14.0
      // 3220: ldc_w -14.0
      // 3223: fconst_0
      // 3224: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3227: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 322a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 322d: aastore
      // 322e: dup
      // 322f: bipush 3
      // 3230: new net/minecraft/client/animation/Keyframe
      // 3233: dup
      // 3234: fconst_1
      // 3235: ldc_w -14.0
      // 3238: ldc_w -21.0
      // 323b: ldc 7.0
      // 323d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3240: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3243: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3246: aastore
      // 3247: dup
      // 3248: bipush 4
      // 3249: new net/minecraft/client/animation/Keyframe
      // 324c: dup
      // 324d: ldc 1.4583
      // 324f: ldc_w -14.0
      // 3252: ldc_w -14.0
      // 3255: fconst_0
      // 3256: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3259: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 325c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 325f: aastore
      // 3260: dup
      // 3261: bipush 5
      // 3262: new net/minecraft/client/animation/Keyframe
      // 3265: dup
      // 3266: ldc_w 2.0833
      // 3269: fconst_0
      // 326a: fconst_0
      // 326b: fconst_0
      // 326c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 326f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3272: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3275: aastore
      // 3276: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3279: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 327c: ldc "rightarm2"
      // 327e: new net/minecraft/client/animation/AnimationChannel
      // 3281: dup
      // 3282: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3285: bipush 5
      // 3286: anewarray 28
      // 3289: dup
      // 328a: bipush 0
      // 328b: new net/minecraft/client/animation/Keyframe
      // 328e: dup
      // 328f: fconst_0
      // 3290: fconst_0
      // 3291: fconst_0
      // 3292: fconst_0
      // 3293: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3296: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3299: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 329c: aastore
      // 329d: dup
      // 329e: bipush 1
      // 329f: new net/minecraft/client/animation/Keyframe
      // 32a2: dup
      // 32a3: ldc 0.5417
      // 32a5: ldc 7.5
      // 32a7: fconst_0
      // 32a8: fconst_0
      // 32a9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 32ac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32af: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32b2: aastore
      // 32b3: dup
      // 32b4: bipush 2
      // 32b5: new net/minecraft/client/animation/Keyframe
      // 32b8: dup
      // 32b9: ldc 0.8333
      // 32bb: ldc 7.5
      // 32bd: fconst_0
      // 32be: fconst_0
      // 32bf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 32c2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32c5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32c8: aastore
      // 32c9: dup
      // 32ca: bipush 3
      // 32cb: new net/minecraft/client/animation/Keyframe
      // 32ce: dup
      // 32cf: ldc 1.375
      // 32d1: ldc 7.5
      // 32d3: fconst_0
      // 32d4: fconst_0
      // 32d5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 32d8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32db: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32de: aastore
      // 32df: dup
      // 32e0: bipush 4
      // 32e1: new net/minecraft/client/animation/Keyframe
      // 32e4: dup
      // 32e5: ldc_w 2.0833
      // 32e8: fconst_0
      // 32e9: fconst_0
      // 32ea: fconst_0
      // 32eb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 32ee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32f1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32f4: aastore
      // 32f5: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 32f8: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 32fb: ldc "righthand"
      // 32fd: new net/minecraft/client/animation/AnimationChannel
      // 3300: dup
      // 3301: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3304: bipush 5
      // 3305: anewarray 28
      // 3308: dup
      // 3309: bipush 0
      // 330a: new net/minecraft/client/animation/Keyframe
      // 330d: dup
      // 330e: fconst_0
      // 330f: fconst_0
      // 3310: fconst_0
      // 3311: fconst_0
      // 3312: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3315: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3318: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 331b: aastore
      // 331c: dup
      // 331d: bipush 1
      // 331e: new net/minecraft/client/animation/Keyframe
      // 3321: dup
      // 3322: ldc 0.5417
      // 3324: ldc 5.0
      // 3326: fconst_0
      // 3327: fconst_0
      // 3328: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 332b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 332e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3331: aastore
      // 3332: dup
      // 3333: bipush 2
      // 3334: new net/minecraft/client/animation/Keyframe
      // 3337: dup
      // 3338: ldc 0.8333
      // 333a: ldc 5.0
      // 333c: fconst_0
      // 333d: fconst_0
      // 333e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3341: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3344: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3347: aastore
      // 3348: dup
      // 3349: bipush 3
      // 334a: new net/minecraft/client/animation/Keyframe
      // 334d: dup
      // 334e: ldc 1.375
      // 3350: ldc 5.0
      // 3352: fconst_0
      // 3353: fconst_0
      // 3354: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3357: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 335a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 335d: aastore
      // 335e: dup
      // 335f: bipush 4
      // 3360: new net/minecraft/client/animation/Keyframe
      // 3363: dup
      // 3364: ldc_w 2.0833
      // 3367: fconst_0
      // 3368: fconst_0
      // 3369: fconst_0
      // 336a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 336d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3370: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3373: aastore
      // 3374: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3377: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 337a: ldc "righthand"
      // 337c: new net/minecraft/client/animation/AnimationChannel
      // 337f: dup
      // 3380: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3383: bipush 6
      // 3385: anewarray 28
      // 3388: dup
      // 3389: bipush 0
      // 338a: new net/minecraft/client/animation/Keyframe
      // 338d: dup
      // 338e: fconst_0
      // 338f: fconst_0
      // 3390: fconst_0
      // 3391: fconst_0
      // 3392: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3395: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3398: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 339b: aastore
      // 339c: dup
      // 339d: bipush 1
      // 339e: new net/minecraft/client/animation/Keyframe
      // 33a1: dup
      // 33a2: ldc 0.8333
      // 33a4: fconst_0
      // 33a5: fconst_0
      // 33a6: fconst_0
      // 33a7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 33aa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33ad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33b0: aastore
      // 33b1: dup
      // 33b2: bipush 2
      // 33b3: new net/minecraft/client/animation/Keyframe
      // 33b6: dup
      // 33b7: fconst_1
      // 33b8: fconst_0
      // 33b9: ldc_w 10.0
      // 33bc: fconst_0
      // 33bd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 33c0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33c3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33c6: aastore
      // 33c7: dup
      // 33c8: bipush 3
      // 33c9: new net/minecraft/client/animation/Keyframe
      // 33cc: dup
      // 33cd: ldc 1.1667
      // 33cf: fconst_0
      // 33d0: ldc_w 7.38
      // 33d3: fconst_0
      // 33d4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 33d7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33da: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33dd: aastore
      // 33de: dup
      // 33df: bipush 4
      // 33e0: new net/minecraft/client/animation/Keyframe
      // 33e3: dup
      // 33e4: ldc 1.375
      // 33e6: fconst_0
      // 33e7: ldc_w 7.38
      // 33ea: fconst_0
      // 33eb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 33ee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33f1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33f4: aastore
      // 33f5: dup
      // 33f6: bipush 5
      // 33f7: new net/minecraft/client/animation/Keyframe
      // 33fa: dup
      // 33fb: ldc_w 2.0833
      // 33fe: fconst_0
      // 33ff: fconst_0
      // 3400: fconst_0
      // 3401: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3404: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3407: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 340a: aastore
      // 340b: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 340e: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3411: ldc "rightleg"
      // 3413: new net/minecraft/client/animation/AnimationChannel
      // 3416: dup
      // 3417: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 341a: bipush 4
      // 341b: anewarray 28
      // 341e: dup
      // 341f: bipush 0
      // 3420: new net/minecraft/client/animation/Keyframe
      // 3423: dup
      // 3424: fconst_0
      // 3425: fconst_0
      // 3426: fconst_0
      // 3427: fconst_0
      // 3428: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 342b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 342e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3431: aastore
      // 3432: dup
      // 3433: bipush 1
      // 3434: new net/minecraft/client/animation/Keyframe
      // 3437: dup
      // 3438: ldc 0.5417
      // 343a: fconst_0
      // 343b: ldc 15.0
      // 343d: fconst_0
      // 343e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3441: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3444: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3447: aastore
      // 3448: dup
      // 3449: bipush 2
      // 344a: new net/minecraft/client/animation/Keyframe
      // 344d: dup
      // 344e: ldc 1.2083
      // 3450: fconst_0
      // 3451: ldc 15.0
      // 3453: fconst_0
      // 3454: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3457: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 345a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 345d: aastore
      // 345e: dup
      // 345f: bipush 3
      // 3460: new net/minecraft/client/animation/Keyframe
      // 3463: dup
      // 3464: ldc_w 2.0833
      // 3467: fconst_0
      // 3468: fconst_0
      // 3469: fconst_0
      // 346a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 346d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3470: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3473: aastore
      // 3474: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3477: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 347a: ldc "rightleg"
      // 347c: new net/minecraft/client/animation/AnimationChannel
      // 347f: dup
      // 3480: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3483: bipush 4
      // 3484: anewarray 28
      // 3487: dup
      // 3488: bipush 0
      // 3489: new net/minecraft/client/animation/Keyframe
      // 348c: dup
      // 348d: fconst_0
      // 348e: fconst_0
      // 348f: fconst_0
      // 3490: fconst_0
      // 3491: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3494: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3497: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 349a: aastore
      // 349b: dup
      // 349c: bipush 1
      // 349d: new net/minecraft/client/animation/Keyframe
      // 34a0: dup
      // 34a1: ldc 0.8333
      // 34a3: fconst_0
      // 34a4: fconst_0
      // 34a5: ldc -5.0
      // 34a7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 34aa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34ad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 34b0: aastore
      // 34b1: dup
      // 34b2: bipush 2
      // 34b3: new net/minecraft/client/animation/Keyframe
      // 34b6: dup
      // 34b7: ldc 1.4583
      // 34b9: fconst_0
      // 34ba: fconst_0
      // 34bb: ldc -5.0
      // 34bd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 34c0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34c3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 34c6: aastore
      // 34c7: dup
      // 34c8: bipush 3
      // 34c9: new net/minecraft/client/animation/Keyframe
      // 34cc: dup
      // 34cd: ldc_w 2.0833
      // 34d0: fconst_0
      // 34d1: fconst_0
      // 34d2: fconst_0
      // 34d3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 34d6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34d9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 34dc: aastore
      // 34dd: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 34e0: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 34e3: ldc "leftleg"
      // 34e5: new net/minecraft/client/animation/AnimationChannel
      // 34e8: dup
      // 34e9: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 34ec: bipush 4
      // 34ed: anewarray 28
      // 34f0: dup
      // 34f1: bipush 0
      // 34f2: new net/minecraft/client/animation/Keyframe
      // 34f5: dup
      // 34f6: fconst_0
      // 34f7: fconst_0
      // 34f8: fconst_0
      // 34f9: fconst_0
      // 34fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 34fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3500: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3503: aastore
      // 3504: dup
      // 3505: bipush 1
      // 3506: new net/minecraft/client/animation/Keyframe
      // 3509: dup
      // 350a: ldc 0.5417
      // 350c: fconst_0
      // 350d: ldc -15.0
      // 350f: fconst_0
      // 3510: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3513: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3516: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3519: aastore
      // 351a: dup
      // 351b: bipush 2
      // 351c: new net/minecraft/client/animation/Keyframe
      // 351f: dup
      // 3520: ldc 1.2083
      // 3522: fconst_0
      // 3523: ldc -15.0
      // 3525: fconst_0
      // 3526: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3529: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 352c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 352f: aastore
      // 3530: dup
      // 3531: bipush 3
      // 3532: new net/minecraft/client/animation/Keyframe
      // 3535: dup
      // 3536: ldc_w 2.0833
      // 3539: fconst_0
      // 353a: fconst_0
      // 353b: fconst_0
      // 353c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 353f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3542: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3545: aastore
      // 3546: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3549: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 354c: ldc "leftleg"
      // 354e: new net/minecraft/client/animation/AnimationChannel
      // 3551: dup
      // 3552: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3555: bipush 4
      // 3556: anewarray 28
      // 3559: dup
      // 355a: bipush 0
      // 355b: new net/minecraft/client/animation/Keyframe
      // 355e: dup
      // 355f: fconst_0
      // 3560: fconst_0
      // 3561: fconst_0
      // 3562: fconst_0
      // 3563: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3566: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3569: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 356c: aastore
      // 356d: dup
      // 356e: bipush 1
      // 356f: new net/minecraft/client/animation/Keyframe
      // 3572: dup
      // 3573: ldc 0.8333
      // 3575: fconst_0
      // 3576: fconst_0
      // 3577: ldc -5.0
      // 3579: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 357c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 357f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3582: aastore
      // 3583: dup
      // 3584: bipush 2
      // 3585: new net/minecraft/client/animation/Keyframe
      // 3588: dup
      // 3589: ldc 1.4583
      // 358b: fconst_0
      // 358c: fconst_0
      // 358d: ldc -5.0
      // 358f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3592: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3595: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3598: aastore
      // 3599: dup
      // 359a: bipush 3
      // 359b: new net/minecraft/client/animation/Keyframe
      // 359e: dup
      // 359f: ldc_w 2.0833
      // 35a2: fconst_0
      // 35a3: fconst_0
      // 35a4: fconst_0
      // 35a5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 35a8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35ab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35ae: aastore
      // 35af: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 35b2: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 35b5: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.build ()Lnet/minecraft/client/animation/AnimationDefinition;
      // 35b8: putstatic com/github/L_Ender/cataclysm/client/animation/Netherite_Monstrosity_Animation.FIRE Lnet/minecraft/client/animation/AnimationDefinition;
      // 35bb: ldc_w 3.5
      // 35be: invokestatic net/minecraft/client/animation/AnimationDefinition$Builder.withLength (F)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 35c1: ldc "roots"
      // 35c3: new net/minecraft/client/animation/AnimationChannel
      // 35c6: dup
      // 35c7: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 35ca: bipush 10
      // 35cc: anewarray 28
      // 35cf: dup
      // 35d0: bipush 0
      // 35d1: new net/minecraft/client/animation/Keyframe
      // 35d4: dup
      // 35d5: fconst_0
      // 35d6: fconst_0
      // 35d7: fconst_0
      // 35d8: fconst_0
      // 35d9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 35dc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35df: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35e2: aastore
      // 35e3: dup
      // 35e4: bipush 1
      // 35e5: new net/minecraft/client/animation/Keyframe
      // 35e8: dup
      // 35e9: ldc_w 0.875
      // 35ec: fconst_0
      // 35ed: fconst_0
      // 35ee: fconst_0
      // 35ef: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 35f2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35f5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35f8: aastore
      // 35f9: dup
      // 35fa: bipush 2
      // 35fb: new net/minecraft/client/animation/Keyframe
      // 35fe: dup
      // 35ff: ldc 1.125
      // 3601: fconst_0
      // 3602: fconst_0
      // 3603: fconst_0
      // 3604: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3607: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 360a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 360d: aastore
      // 360e: dup
      // 360f: bipush 3
      // 3610: new net/minecraft/client/animation/Keyframe
      // 3613: dup
      // 3614: ldc 1.375
      // 3616: fconst_0
      // 3617: fconst_0
      // 3618: fconst_0
      // 3619: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 361c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 361f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3622: aastore
      // 3623: dup
      // 3624: bipush 4
      // 3625: new net/minecraft/client/animation/Keyframe
      // 3628: dup
      // 3629: ldc 1.625
      // 362b: fconst_0
      // 362c: fconst_0
      // 362d: fconst_0
      // 362e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3631: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3634: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3637: aastore
      // 3638: dup
      // 3639: bipush 5
      // 363a: new net/minecraft/client/animation/Keyframe
      // 363d: dup
      // 363e: ldc 1.875
      // 3640: fconst_0
      // 3641: fconst_0
      // 3642: fconst_0
      // 3643: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3646: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3649: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 364c: aastore
      // 364d: dup
      // 364e: bipush 6
      // 3650: new net/minecraft/client/animation/Keyframe
      // 3653: dup
      // 3654: ldc_w 2.125
      // 3657: fconst_0
      // 3658: fconst_0
      // 3659: fconst_0
      // 365a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 365d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3660: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3663: aastore
      // 3664: dup
      // 3665: bipush 7
      // 3667: new net/minecraft/client/animation/Keyframe
      // 366a: dup
      // 366b: ldc_w 2.375
      // 366e: fconst_0
      // 366f: fconst_0
      // 3670: fconst_0
      // 3671: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3674: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3677: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 367a: aastore
      // 367b: dup
      // 367c: bipush 8
      // 367e: new net/minecraft/client/animation/Keyframe
      // 3681: dup
      // 3682: ldc 2.875
      // 3684: fconst_0
      // 3685: ldc -27.5
      // 3687: fconst_0
      // 3688: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 368b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 368e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3691: aastore
      // 3692: dup
      // 3693: bipush 9
      // 3695: new net/minecraft/client/animation/Keyframe
      // 3698: dup
      // 3699: ldc_w 3.5
      // 369c: fconst_0
      // 369d: fconst_0
      // 369e: fconst_0
      // 369f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 36a2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36a5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36a8: aastore
      // 36a9: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 36ac: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 36af: ldc "roots"
      // 36b1: new net/minecraft/client/animation/AnimationChannel
      // 36b4: dup
      // 36b5: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 36b8: bipush 15
      // 36ba: anewarray 28
      // 36bd: dup
      // 36be: bipush 0
      // 36bf: new net/minecraft/client/animation/Keyframe
      // 36c2: dup
      // 36c3: fconst_0
      // 36c4: fconst_0
      // 36c5: fconst_0
      // 36c6: fconst_0
      // 36c7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 36ca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36cd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36d0: aastore
      // 36d1: dup
      // 36d2: bipush 1
      // 36d3: new net/minecraft/client/animation/Keyframe
      // 36d6: dup
      // 36d7: ldc_w 0.875
      // 36da: fconst_0
      // 36db: ldc -2.0
      // 36dd: fconst_0
      // 36de: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 36e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36e7: aastore
      // 36e8: dup
      // 36e9: bipush 2
      // 36ea: new net/minecraft/client/animation/Keyframe
      // 36ed: dup
      // 36ee: fconst_1
      // 36ef: fconst_0
      // 36f0: fconst_0
      // 36f1: fconst_0
      // 36f2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 36f5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36f8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36fb: aastore
      // 36fc: dup
      // 36fd: bipush 3
      // 36fe: new net/minecraft/client/animation/Keyframe
      // 3701: dup
      // 3702: ldc 1.125
      // 3704: fconst_0
      // 3705: ldc -2.0
      // 3707: fconst_0
      // 3708: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 370b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 370e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3711: aastore
      // 3712: dup
      // 3713: bipush 4
      // 3714: new net/minecraft/client/animation/Keyframe
      // 3717: dup
      // 3718: ldc 1.25
      // 371a: fconst_0
      // 371b: fconst_0
      // 371c: fconst_0
      // 371d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3720: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3723: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3726: aastore
      // 3727: dup
      // 3728: bipush 5
      // 3729: new net/minecraft/client/animation/Keyframe
      // 372c: dup
      // 372d: ldc 1.375
      // 372f: fconst_0
      // 3730: ldc -2.0
      // 3732: fconst_0
      // 3733: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3736: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3739: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 373c: aastore
      // 373d: dup
      // 373e: bipush 6
      // 3740: new net/minecraft/client/animation/Keyframe
      // 3743: dup
      // 3744: ldc_w 1.5
      // 3747: fconst_0
      // 3748: fconst_0
      // 3749: fconst_0
      // 374a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 374d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3750: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3753: aastore
      // 3754: dup
      // 3755: bipush 7
      // 3757: new net/minecraft/client/animation/Keyframe
      // 375a: dup
      // 375b: ldc 1.625
      // 375d: fconst_0
      // 375e: ldc -2.0
      // 3760: fconst_0
      // 3761: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3764: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3767: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 376a: aastore
      // 376b: dup
      // 376c: bipush 8
      // 376e: new net/minecraft/client/animation/Keyframe
      // 3771: dup
      // 3772: ldc 1.75
      // 3774: fconst_0
      // 3775: fconst_0
      // 3776: fconst_0
      // 3777: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 377a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 377d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3780: aastore
      // 3781: dup
      // 3782: bipush 9
      // 3784: new net/minecraft/client/animation/Keyframe
      // 3787: dup
      // 3788: ldc 1.875
      // 378a: fconst_0
      // 378b: ldc -2.0
      // 378d: fconst_0
      // 378e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3791: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3794: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3797: aastore
      // 3798: dup
      // 3799: bipush 10
      // 379b: new net/minecraft/client/animation/Keyframe
      // 379e: dup
      // 379f: fconst_2
      // 37a0: fconst_0
      // 37a1: fconst_0
      // 37a2: fconst_0
      // 37a3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 37a6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37a9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37ac: aastore
      // 37ad: dup
      // 37ae: bipush 11
      // 37b0: new net/minecraft/client/animation/Keyframe
      // 37b3: dup
      // 37b4: ldc_w 2.125
      // 37b7: fconst_0
      // 37b8: ldc -2.0
      // 37ba: fconst_0
      // 37bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 37be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37c4: aastore
      // 37c5: dup
      // 37c6: bipush 12
      // 37c8: new net/minecraft/client/animation/Keyframe
      // 37cb: dup
      // 37cc: ldc 2.25
      // 37ce: fconst_0
      // 37cf: fconst_0
      // 37d0: fconst_0
      // 37d1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 37d4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37d7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37da: aastore
      // 37db: dup
      // 37dc: bipush 13
      // 37de: new net/minecraft/client/animation/Keyframe
      // 37e1: dup
      // 37e2: ldc_w 2.375
      // 37e5: fconst_0
      // 37e6: ldc -2.0
      // 37e8: fconst_0
      // 37e9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 37ec: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37ef: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37f2: aastore
      // 37f3: dup
      // 37f4: bipush 14
      // 37f6: new net/minecraft/client/animation/Keyframe
      // 37f9: dup
      // 37fa: ldc_w 3.5
      // 37fd: fconst_0
      // 37fe: fconst_0
      // 37ff: fconst_0
      // 3800: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3803: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3806: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3809: aastore
      // 380a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 380d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3810: ldc "upperbody"
      // 3812: new net/minecraft/client/animation/AnimationChannel
      // 3815: dup
      // 3816: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3819: bipush 7
      // 381b: anewarray 28
      // 381e: dup
      // 381f: bipush 0
      // 3820: new net/minecraft/client/animation/Keyframe
      // 3823: dup
      // 3824: fconst_0
      // 3825: fconst_0
      // 3826: fconst_0
      // 3827: fconst_0
      // 3828: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 382b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 382e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3831: aastore
      // 3832: dup
      // 3833: bipush 1
      // 3834: new net/minecraft/client/animation/Keyframe
      // 3837: dup
      // 3838: ldc 0.5
      // 383a: ldc_w 28.4032
      // 383d: ldc_w -39.8557
      // 3840: ldc_w -22.9098
      // 3843: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3846: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3849: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 384c: aastore
      // 384d: dup
      // 384e: bipush 2
      // 384f: new net/minecraft/client/animation/Keyframe
      // 3852: dup
      // 3853: ldc 0.9167
      // 3855: ldc_w 28.4032
      // 3858: ldc_w -39.8557
      // 385b: ldc_w -22.9098
      // 385e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3861: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3864: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3867: aastore
      // 3868: dup
      // 3869: bipush 3
      // 386a: new net/minecraft/client/animation/Keyframe
      // 386d: dup
      // 386e: ldc 1.625
      // 3870: ldc_w 49.0085
      // 3873: ldc_w -46.3034
      // 3876: ldc_w -44.1462
      // 3879: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 387c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 387f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3882: aastore
      // 3883: dup
      // 3884: bipush 4
      // 3885: new net/minecraft/client/animation/Keyframe
      // 3888: dup
      // 3889: ldc_w 2.375
      // 388c: ldc_w 49.0085
      // 388f: ldc_w -46.3034
      // 3892: ldc_w -44.1462
      // 3895: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3898: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 389b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 389e: aastore
      // 389f: dup
      // 38a0: bipush 5
      // 38a1: new net/minecraft/client/animation/Keyframe
      // 38a4: dup
      // 38a5: ldc_w 2.9167
      // 38a8: ldc_w 53.5827
      // 38ab: ldc_w -49.0814
      // 38ae: ldc_w -50.3268
      // 38b1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 38b4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 38b7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 38ba: aastore
      // 38bb: dup
      // 38bc: bipush 6
      // 38be: new net/minecraft/client/animation/Keyframe
      // 38c1: dup
      // 38c2: ldc_w 3.5
      // 38c5: fconst_0
      // 38c6: fconst_0
      // 38c7: fconst_0
      // 38c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 38cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 38ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 38d1: aastore
      // 38d2: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 38d5: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 38d8: ldc "head"
      // 38da: new net/minecraft/client/animation/AnimationChannel
      // 38dd: dup
      // 38de: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 38e1: bipush 6
      // 38e3: anewarray 28
      // 38e6: dup
      // 38e7: bipush 0
      // 38e8: new net/minecraft/client/animation/Keyframe
      // 38eb: dup
      // 38ec: fconst_0
      // 38ed: fconst_0
      // 38ee: fconst_0
      // 38ef: fconst_0
      // 38f0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 38f3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 38f6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 38f9: aastore
      // 38fa: dup
      // 38fb: bipush 1
      // 38fc: new net/minecraft/client/animation/Keyframe
      // 38ff: dup
      // 3900: ldc 0.7083
      // 3902: ldc_w -24.0065
      // 3905: ldc_w 38.3962
      // 3908: ldc_w -7.3262
      // 390b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 390e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3911: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3914: aastore
      // 3915: dup
      // 3916: bipush 2
      // 3917: new net/minecraft/client/animation/Keyframe
      // 391a: dup
      // 391b: fconst_1
      // 391c: ldc_w -24.0065
      // 391f: ldc_w 38.3962
      // 3922: ldc_w -7.3262
      // 3925: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3928: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 392b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 392e: aastore
      // 392f: dup
      // 3930: bipush 3
      // 3931: new net/minecraft/client/animation/Keyframe
      // 3934: dup
      // 3935: ldc_w 2.375
      // 3938: ldc_w -24.0065
      // 393b: ldc_w 38.3962
      // 393e: ldc_w -7.3262
      // 3941: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3944: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3947: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 394a: aastore
      // 394b: dup
      // 394c: bipush 4
      // 394d: new net/minecraft/client/animation/Keyframe
      // 3950: dup
      // 3951: ldc 2.875
      // 3953: ldc_w -36.5065
      // 3956: ldc_w 38.3962
      // 3959: ldc_w -7.3262
      // 395c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 395f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3962: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3965: aastore
      // 3966: dup
      // 3967: bipush 5
      // 3968: new net/minecraft/client/animation/Keyframe
      // 396b: dup
      // 396c: ldc_w 3.5
      // 396f: fconst_0
      // 3970: fconst_0
      // 3971: fconst_0
      // 3972: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3975: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3978: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 397b: aastore
      // 397c: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 397f: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3982: ldc "head"
      // 3984: new net/minecraft/client/animation/AnimationChannel
      // 3987: dup
      // 3988: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 398b: bipush 14
      // 398d: anewarray 28
      // 3990: dup
      // 3991: bipush 0
      // 3992: new net/minecraft/client/animation/Keyframe
      // 3995: dup
      // 3996: fconst_0
      // 3997: fconst_0
      // 3998: fconst_0
      // 3999: fconst_0
      // 399a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 399d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39a0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39a3: aastore
      // 39a4: dup
      // 39a5: bipush 1
      // 39a6: new net/minecraft/client/animation/Keyframe
      // 39a9: dup
      // 39aa: fconst_1
      // 39ab: fconst_0
      // 39ac: ldc -3.0
      // 39ae: fconst_0
      // 39af: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 39b2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39b5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39b8: aastore
      // 39b9: dup
      // 39ba: bipush 2
      // 39bb: new net/minecraft/client/animation/Keyframe
      // 39be: dup
      // 39bf: ldc 1.125
      // 39c1: fconst_0
      // 39c2: fconst_0
      // 39c3: fconst_0
      // 39c4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 39c7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39ca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39cd: aastore
      // 39ce: dup
      // 39cf: bipush 3
      // 39d0: new net/minecraft/client/animation/Keyframe
      // 39d3: dup
      // 39d4: ldc 1.25
      // 39d6: fconst_0
      // 39d7: ldc -3.0
      // 39d9: fconst_0
      // 39da: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 39dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39e3: aastore
      // 39e4: dup
      // 39e5: bipush 4
      // 39e6: new net/minecraft/client/animation/Keyframe
      // 39e9: dup
      // 39ea: ldc 1.375
      // 39ec: fconst_0
      // 39ed: fconst_0
      // 39ee: fconst_0
      // 39ef: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 39f2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39f5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39f8: aastore
      // 39f9: dup
      // 39fa: bipush 5
      // 39fb: new net/minecraft/client/animation/Keyframe
      // 39fe: dup
      // 39ff: ldc_w 1.5
      // 3a02: fconst_0
      // 3a03: ldc -3.0
      // 3a05: fconst_0
      // 3a06: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3a09: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a0c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a0f: aastore
      // 3a10: dup
      // 3a11: bipush 6
      // 3a13: new net/minecraft/client/animation/Keyframe
      // 3a16: dup
      // 3a17: ldc 1.625
      // 3a19: fconst_0
      // 3a1a: fconst_0
      // 3a1b: fconst_0
      // 3a1c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3a1f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a22: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a25: aastore
      // 3a26: dup
      // 3a27: bipush 7
      // 3a29: new net/minecraft/client/animation/Keyframe
      // 3a2c: dup
      // 3a2d: ldc 1.75
      // 3a2f: fconst_0
      // 3a30: ldc -3.0
      // 3a32: fconst_0
      // 3a33: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3a36: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a39: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a3c: aastore
      // 3a3d: dup
      // 3a3e: bipush 8
      // 3a40: new net/minecraft/client/animation/Keyframe
      // 3a43: dup
      // 3a44: ldc 1.875
      // 3a46: fconst_0
      // 3a47: fconst_0
      // 3a48: fconst_0
      // 3a49: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3a4c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a4f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a52: aastore
      // 3a53: dup
      // 3a54: bipush 9
      // 3a56: new net/minecraft/client/animation/Keyframe
      // 3a59: dup
      // 3a5a: fconst_2
      // 3a5b: fconst_0
      // 3a5c: ldc -3.0
      // 3a5e: fconst_0
      // 3a5f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3a62: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a65: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a68: aastore
      // 3a69: dup
      // 3a6a: bipush 10
      // 3a6c: new net/minecraft/client/animation/Keyframe
      // 3a6f: dup
      // 3a70: ldc_w 2.125
      // 3a73: fconst_0
      // 3a74: fconst_0
      // 3a75: fconst_0
      // 3a76: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3a79: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a7c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a7f: aastore
      // 3a80: dup
      // 3a81: bipush 11
      // 3a83: new net/minecraft/client/animation/Keyframe
      // 3a86: dup
      // 3a87: ldc 2.25
      // 3a89: fconst_0
      // 3a8a: ldc -3.0
      // 3a8c: fconst_0
      // 3a8d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3a90: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a93: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a96: aastore
      // 3a97: dup
      // 3a98: bipush 12
      // 3a9a: new net/minecraft/client/animation/Keyframe
      // 3a9d: dup
      // 3a9e: ldc_w 2.375
      // 3aa1: fconst_0
      // 3aa2: fconst_0
      // 3aa3: fconst_0
      // 3aa4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3aa7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3aaa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3aad: aastore
      // 3aae: dup
      // 3aaf: bipush 13
      // 3ab1: new net/minecraft/client/animation/Keyframe
      // 3ab4: dup
      // 3ab5: ldc_w 3.5
      // 3ab8: fconst_0
      // 3ab9: fconst_0
      // 3aba: fconst_0
      // 3abb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3abe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ac1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ac4: aastore
      // 3ac5: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3ac8: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3acb: ldc "jaw"
      // 3acd: new net/minecraft/client/animation/AnimationChannel
      // 3ad0: dup
      // 3ad1: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3ad4: bipush 7
      // 3ad6: anewarray 28
      // 3ad9: dup
      // 3ada: bipush 0
      // 3adb: new net/minecraft/client/animation/Keyframe
      // 3ade: dup
      // 3adf: fconst_0
      // 3ae0: fconst_0
      // 3ae1: fconst_0
      // 3ae2: fconst_0
      // 3ae3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ae6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ae9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3aec: aastore
      // 3aed: dup
      // 3aee: bipush 1
      // 3aef: new net/minecraft/client/animation/Keyframe
      // 3af2: dup
      // 3af3: ldc 0.625
      // 3af5: ldc 15.0
      // 3af7: fconst_0
      // 3af8: fconst_0
      // 3af9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3afc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3aff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b02: aastore
      // 3b03: dup
      // 3b04: bipush 2
      // 3b05: new net/minecraft/client/animation/Keyframe
      // 3b08: dup
      // 3b09: fconst_1
      // 3b0a: ldc 15.0
      // 3b0c: fconst_0
      // 3b0d: fconst_0
      // 3b0e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3b11: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b14: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b17: aastore
      // 3b18: dup
      // 3b19: bipush 3
      // 3b1a: new net/minecraft/client/animation/Keyframe
      // 3b1d: dup
      // 3b1e: ldc_w 1.5
      // 3b21: ldc_w -6.11
      // 3b24: fconst_0
      // 3b25: fconst_0
      // 3b26: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3b29: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b2c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b2f: aastore
      // 3b30: dup
      // 3b31: bipush 4
      // 3b32: new net/minecraft/client/animation/Keyframe
      // 3b35: dup
      // 3b36: ldc_w 1.9583
      // 3b39: ldc 12.5
      // 3b3b: fconst_0
      // 3b3c: fconst_0
      // 3b3d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3b40: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b43: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b46: aastore
      // 3b47: dup
      // 3b48: bipush 5
      // 3b49: new net/minecraft/client/animation/Keyframe
      // 3b4c: dup
      // 3b4d: ldc_w 2.4167
      // 3b50: ldc_w -6.11
      // 3b53: fconst_0
      // 3b54: fconst_0
      // 3b55: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3b58: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b5b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b5e: aastore
      // 3b5f: dup
      // 3b60: bipush 6
      // 3b62: new net/minecraft/client/animation/Keyframe
      // 3b65: dup
      // 3b66: ldc_w 3.5
      // 3b69: fconst_0
      // 3b6a: fconst_0
      // 3b6b: fconst_0
      // 3b6c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3b6f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b72: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b75: aastore
      // 3b76: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3b79: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3b7c: ldc "leftarm"
      // 3b7e: new net/minecraft/client/animation/AnimationChannel
      // 3b81: dup
      // 3b82: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3b85: bipush 7
      // 3b87: anewarray 28
      // 3b8a: dup
      // 3b8b: bipush 0
      // 3b8c: new net/minecraft/client/animation/Keyframe
      // 3b8f: dup
      // 3b90: fconst_0
      // 3b91: fconst_0
      // 3b92: fconst_0
      // 3b93: fconst_0
      // 3b94: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3b97: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b9a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b9d: aastore
      // 3b9e: dup
      // 3b9f: bipush 1
      // 3ba0: new net/minecraft/client/animation/Keyframe
      // 3ba3: dup
      // 3ba4: ldc 0.5833
      // 3ba6: ldc_w 26.331
      // 3ba9: ldc_w -11.1448
      // 3bac: ldc_w -48.5787
      // 3baf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3bb2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3bb5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3bb8: aastore
      // 3bb9: dup
      // 3bba: bipush 2
      // 3bbb: new net/minecraft/client/animation/Keyframe
      // 3bbe: dup
      // 3bbf: fconst_1
      // 3bc0: ldc_w 26.331
      // 3bc3: ldc_w -11.1448
      // 3bc6: ldc_w -48.5787
      // 3bc9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3bcc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3bcf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3bd2: aastore
      // 3bd3: dup
      // 3bd4: bipush 3
      // 3bd5: new net/minecraft/client/animation/Keyframe
      // 3bd8: dup
      // 3bd9: ldc 1.625
      // 3bdb: ldc_w 26.331
      // 3bde: ldc_w -11.1448
      // 3be1: ldc_w -48.5787
      // 3be4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3be7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3bea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3bed: aastore
      // 3bee: dup
      // 3bef: bipush 4
      // 3bf0: new net/minecraft/client/animation/Keyframe
      // 3bf3: dup
      // 3bf4: ldc_w 2.375
      // 3bf7: ldc_w 26.331
      // 3bfa: ldc_w -11.1448
      // 3bfd: ldc_w -48.5787
      // 3c00: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c03: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c06: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c09: aastore
      // 3c0a: dup
      // 3c0b: bipush 5
      // 3c0c: new net/minecraft/client/animation/Keyframe
      // 3c0f: dup
      // 3c10: ldc 2.875
      // 3c12: ldc_w 1.2246
      // 3c15: ldc_w 3.7662
      // 3c18: ldc_w -46.9397
      // 3c1b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c1e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c21: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c24: aastore
      // 3c25: dup
      // 3c26: bipush 6
      // 3c28: new net/minecraft/client/animation/Keyframe
      // 3c2b: dup
      // 3c2c: ldc_w 3.5
      // 3c2f: fconst_0
      // 3c30: fconst_0
      // 3c31: fconst_0
      // 3c32: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c35: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c38: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c3b: aastore
      // 3c3c: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3c3f: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3c42: ldc "leftarm2"
      // 3c44: new net/minecraft/client/animation/AnimationChannel
      // 3c47: dup
      // 3c48: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3c4b: bipush 6
      // 3c4d: anewarray 28
      // 3c50: dup
      // 3c51: bipush 0
      // 3c52: new net/minecraft/client/animation/Keyframe
      // 3c55: dup
      // 3c56: fconst_0
      // 3c57: fconst_0
      // 3c58: fconst_0
      // 3c59: fconst_0
      // 3c5a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c5d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c60: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c63: aastore
      // 3c64: dup
      // 3c65: bipush 1
      // 3c66: new net/minecraft/client/animation/Keyframe
      // 3c69: dup
      // 3c6a: ldc 0.625
      // 3c6c: ldc_w -60.0
      // 3c6f: fconst_0
      // 3c70: fconst_0
      // 3c71: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c74: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c77: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c7a: aastore
      // 3c7b: dup
      // 3c7c: bipush 2
      // 3c7d: new net/minecraft/client/animation/Keyframe
      // 3c80: dup
      // 3c81: fconst_1
      // 3c82: ldc_w -60.0
      // 3c85: fconst_0
      // 3c86: fconst_0
      // 3c87: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c8a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c8d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c90: aastore
      // 3c91: dup
      // 3c92: bipush 3
      // 3c93: new net/minecraft/client/animation/Keyframe
      // 3c96: dup
      // 3c97: ldc 1.625
      // 3c99: ldc_w -60.0
      // 3c9c: fconst_0
      // 3c9d: fconst_0
      // 3c9e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ca1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ca4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ca7: aastore
      // 3ca8: dup
      // 3ca9: bipush 4
      // 3caa: new net/minecraft/client/animation/Keyframe
      // 3cad: dup
      // 3cae: ldc_w 2.375
      // 3cb1: ldc_w -50.0
      // 3cb4: fconst_0
      // 3cb5: fconst_0
      // 3cb6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3cb9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3cbc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3cbf: aastore
      // 3cc0: dup
      // 3cc1: bipush 5
      // 3cc2: new net/minecraft/client/animation/Keyframe
      // 3cc5: dup
      // 3cc6: ldc_w 3.5
      // 3cc9: fconst_0
      // 3cca: fconst_0
      // 3ccb: fconst_0
      // 3ccc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ccf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3cd2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3cd5: aastore
      // 3cd6: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3cd9: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3cdc: ldc "rightarm"
      // 3cde: new net/minecraft/client/animation/AnimationChannel
      // 3ce1: dup
      // 3ce2: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3ce5: bipush 6
      // 3ce7: anewarray 28
      // 3cea: dup
      // 3ceb: bipush 0
      // 3cec: new net/minecraft/client/animation/Keyframe
      // 3cef: dup
      // 3cf0: fconst_0
      // 3cf1: fconst_0
      // 3cf2: fconst_0
      // 3cf3: fconst_0
      // 3cf4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3cf7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3cfa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3cfd: aastore
      // 3cfe: dup
      // 3cff: bipush 1
      // 3d00: new net/minecraft/client/animation/Keyframe
      // 3d03: dup
      // 3d04: ldc 0.625
      // 3d06: ldc_w -16.7549
      // 3d09: ldc_w -31.4118
      // 3d0c: ldc_w 46.1789
      // 3d0f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d12: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d15: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d18: aastore
      // 3d19: dup
      // 3d1a: bipush 2
      // 3d1b: new net/minecraft/client/animation/Keyframe
      // 3d1e: dup
      // 3d1f: ldc 0.9167
      // 3d21: ldc_w -16.7549
      // 3d24: ldc_w -31.4118
      // 3d27: ldc_w 46.1789
      // 3d2a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d2d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d30: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d33: aastore
      // 3d34: dup
      // 3d35: bipush 3
      // 3d36: new net/minecraft/client/animation/Keyframe
      // 3d39: dup
      // 3d3a: ldc_w 2.375
      // 3d3d: ldc_w -23.7805
      // 3d40: ldc_w -38.3299
      // 3d43: ldc_w 49.7147
      // 3d46: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d49: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d4c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d4f: aastore
      // 3d50: dup
      // 3d51: bipush 4
      // 3d52: new net/minecraft/client/animation/Keyframe
      // 3d55: dup
      // 3d56: ldc_w 2.9583
      // 3d59: ldc_w 10.2463
      // 3d5c: ldc_w 25.2711
      // 3d5f: ldc_w 50.6947
      // 3d62: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d65: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d68: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d6b: aastore
      // 3d6c: dup
      // 3d6d: bipush 5
      // 3d6e: new net/minecraft/client/animation/Keyframe
      // 3d71: dup
      // 3d72: ldc_w 3.5
      // 3d75: fconst_0
      // 3d76: fconst_0
      // 3d77: fconst_0
      // 3d78: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d7b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d7e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d81: aastore
      // 3d82: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3d85: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3d88: ldc "rightarm"
      // 3d8a: new net/minecraft/client/animation/AnimationChannel
      // 3d8d: dup
      // 3d8e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3d91: bipush 5
      // 3d92: anewarray 28
      // 3d95: dup
      // 3d96: bipush 0
      // 3d97: new net/minecraft/client/animation/Keyframe
      // 3d9a: dup
      // 3d9b: fconst_0
      // 3d9c: fconst_0
      // 3d9d: fconst_0
      // 3d9e: fconst_0
      // 3d9f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3da2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3da5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3da8: aastore
      // 3da9: dup
      // 3daa: bipush 1
      // 3dab: new net/minecraft/client/animation/Keyframe
      // 3dae: dup
      // 3daf: ldc 0.5
      // 3db1: fconst_0
      // 3db2: ldc -7.0
      // 3db4: ldc_w -14.0
      // 3db7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3dba: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3dbd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3dc0: aastore
      // 3dc1: dup
      // 3dc2: bipush 2
      // 3dc3: new net/minecraft/client/animation/Keyframe
      // 3dc6: dup
      // 3dc7: ldc_w 2.375
      // 3dca: fconst_0
      // 3dcb: ldc -7.0
      // 3dcd: ldc_w -14.0
      // 3dd0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3dd3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3dd6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3dd9: aastore
      // 3dda: dup
      // 3ddb: bipush 3
      // 3ddc: new net/minecraft/client/animation/Keyframe
      // 3ddf: dup
      // 3de0: ldc_w 3.1667
      // 3de3: fconst_0
      // 3de4: ldc_w -0.54
      // 3de7: ldc_w 10.92
      // 3dea: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3ded: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3df0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3df3: aastore
      // 3df4: dup
      // 3df5: bipush 4
      // 3df6: new net/minecraft/client/animation/Keyframe
      // 3df9: dup
      // 3dfa: ldc_w 3.5
      // 3dfd: fconst_0
      // 3dfe: fconst_0
      // 3dff: fconst_0
      // 3e00: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3e03: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e06: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e09: aastore
      // 3e0a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3e0d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3e10: ldc "rightarm2"
      // 3e12: new net/minecraft/client/animation/AnimationChannel
      // 3e15: dup
      // 3e16: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3e19: bipush 5
      // 3e1a: anewarray 28
      // 3e1d: dup
      // 3e1e: bipush 0
      // 3e1f: new net/minecraft/client/animation/Keyframe
      // 3e22: dup
      // 3e23: fconst_0
      // 3e24: fconst_0
      // 3e25: fconst_0
      // 3e26: fconst_0
      // 3e27: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e2a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e2d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e30: aastore
      // 3e31: dup
      // 3e32: bipush 1
      // 3e33: new net/minecraft/client/animation/Keyframe
      // 3e36: dup
      // 3e37: ldc 0.625
      // 3e39: ldc_w -77.5
      // 3e3c: fconst_0
      // 3e3d: ldc 35.0
      // 3e3f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e42: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e45: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e48: aastore
      // 3e49: dup
      // 3e4a: bipush 2
      // 3e4b: new net/minecraft/client/animation/Keyframe
      // 3e4e: dup
      // 3e4f: ldc_w 2.375
      // 3e52: ldc_w -92.5
      // 3e55: fconst_0
      // 3e56: ldc 35.0
      // 3e58: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e5b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e5e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e61: aastore
      // 3e62: dup
      // 3e63: bipush 3
      // 3e64: new net/minecraft/client/animation/Keyframe
      // 3e67: dup
      // 3e68: ldc 2.875
      // 3e6a: ldc -90.0
      // 3e6c: fconst_0
      // 3e6d: fconst_0
      // 3e6e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e71: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e74: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e77: aastore
      // 3e78: dup
      // 3e79: bipush 4
      // 3e7a: new net/minecraft/client/animation/Keyframe
      // 3e7d: dup
      // 3e7e: ldc_w 3.5
      // 3e81: fconst_0
      // 3e82: fconst_0
      // 3e83: fconst_0
      // 3e84: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e87: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e8a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e8d: aastore
      // 3e8e: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3e91: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3e94: ldc "rightleg"
      // 3e96: new net/minecraft/client/animation/AnimationChannel
      // 3e99: dup
      // 3e9a: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3e9d: bipush 15
      // 3e9f: anewarray 28
      // 3ea2: dup
      // 3ea3: bipush 0
      // 3ea4: new net/minecraft/client/animation/Keyframe
      // 3ea7: dup
      // 3ea8: fconst_0
      // 3ea9: fconst_0
      // 3eaa: fconst_0
      // 3eab: fconst_0
      // 3eac: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3eaf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3eb2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3eb5: aastore
      // 3eb6: dup
      // 3eb7: bipush 1
      // 3eb8: new net/minecraft/client/animation/Keyframe
      // 3ebb: dup
      // 3ebc: ldc_w 0.875
      // 3ebf: ldc -5.0
      // 3ec1: ldc 7.5
      // 3ec3: fconst_0
      // 3ec4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ec7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3eca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ecd: aastore
      // 3ece: dup
      // 3ecf: bipush 2
      // 3ed0: new net/minecraft/client/animation/Keyframe
      // 3ed3: dup
      // 3ed4: fconst_1
      // 3ed5: ldc 15.0
      // 3ed7: fconst_0
      // 3ed8: fconst_0
      // 3ed9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3edc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3edf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ee2: aastore
      // 3ee3: dup
      // 3ee4: bipush 3
      // 3ee5: new net/minecraft/client/animation/Keyframe
      // 3ee8: dup
      // 3ee9: ldc 1.125
      // 3eeb: ldc 22.5
      // 3eed: fconst_0
      // 3eee: fconst_0
      // 3eef: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ef2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ef5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ef8: aastore
      // 3ef9: dup
      // 3efa: bipush 4
      // 3efb: new net/minecraft/client/animation/Keyframe
      // 3efe: dup
      // 3eff: ldc 1.25
      // 3f01: ldc -5.0
      // 3f03: fconst_0
      // 3f04: fconst_0
      // 3f05: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f08: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f0b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f0e: aastore
      // 3f0f: dup
      // 3f10: bipush 5
      // 3f11: new net/minecraft/client/animation/Keyframe
      // 3f14: dup
      // 3f15: ldc 1.375
      // 3f17: ldc -5.0
      // 3f19: ldc 7.5
      // 3f1b: fconst_0
      // 3f1c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f1f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f22: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f25: aastore
      // 3f26: dup
      // 3f27: bipush 6
      // 3f29: new net/minecraft/client/animation/Keyframe
      // 3f2c: dup
      // 3f2d: ldc_w 1.5
      // 3f30: ldc 15.0
      // 3f32: fconst_0
      // 3f33: fconst_0
      // 3f34: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f37: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f3a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f3d: aastore
      // 3f3e: dup
      // 3f3f: bipush 7
      // 3f41: new net/minecraft/client/animation/Keyframe
      // 3f44: dup
      // 3f45: ldc 1.625
      // 3f47: ldc 22.5
      // 3f49: fconst_0
      // 3f4a: fconst_0
      // 3f4b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f4e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f51: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f54: aastore
      // 3f55: dup
      // 3f56: bipush 8
      // 3f58: new net/minecraft/client/animation/Keyframe
      // 3f5b: dup
      // 3f5c: ldc 1.75
      // 3f5e: ldc -5.0
      // 3f60: fconst_0
      // 3f61: fconst_0
      // 3f62: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f65: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f68: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f6b: aastore
      // 3f6c: dup
      // 3f6d: bipush 9
      // 3f6f: new net/minecraft/client/animation/Keyframe
      // 3f72: dup
      // 3f73: ldc 1.875
      // 3f75: ldc -5.0
      // 3f77: ldc 7.5
      // 3f79: fconst_0
      // 3f7a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f7d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f80: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f83: aastore
      // 3f84: dup
      // 3f85: bipush 10
      // 3f87: new net/minecraft/client/animation/Keyframe
      // 3f8a: dup
      // 3f8b: fconst_2
      // 3f8c: ldc 15.0
      // 3f8e: fconst_0
      // 3f8f: fconst_0
      // 3f90: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f93: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f96: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f99: aastore
      // 3f9a: dup
      // 3f9b: bipush 11
      // 3f9d: new net/minecraft/client/animation/Keyframe
      // 3fa0: dup
      // 3fa1: ldc_w 2.125
      // 3fa4: ldc 22.5
      // 3fa6: fconst_0
      // 3fa7: fconst_0
      // 3fa8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3fab: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3fae: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3fb1: aastore
      // 3fb2: dup
      // 3fb3: bipush 12
      // 3fb5: new net/minecraft/client/animation/Keyframe
      // 3fb8: dup
      // 3fb9: ldc 2.25
      // 3fbb: ldc -5.0
      // 3fbd: fconst_0
      // 3fbe: fconst_0
      // 3fbf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3fc2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3fc5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3fc8: aastore
      // 3fc9: dup
      // 3fca: bipush 13
      // 3fcc: new net/minecraft/client/animation/Keyframe
      // 3fcf: dup
      // 3fd0: ldc_w 2.375
      // 3fd3: ldc -5.0
      // 3fd5: ldc 7.5
      // 3fd7: fconst_0
      // 3fd8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3fdb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3fde: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3fe1: aastore
      // 3fe2: dup
      // 3fe3: bipush 14
      // 3fe5: new net/minecraft/client/animation/Keyframe
      // 3fe8: dup
      // 3fe9: ldc_w 3.5
      // 3fec: fconst_0
      // 3fed: fconst_0
      // 3fee: fconst_0
      // 3fef: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ff2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ff5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ff8: aastore
      // 3ff9: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3ffc: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3fff: ldc "rightleg"
      // 4001: new net/minecraft/client/animation/AnimationChannel
      // 4004: dup
      // 4005: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4008: bipush 15
      // 400a: anewarray 28
      // 400d: dup
      // 400e: bipush 0
      // 400f: new net/minecraft/client/animation/Keyframe
      // 4012: dup
      // 4013: fconst_0
      // 4014: fconst_0
      // 4015: fconst_0
      // 4016: fconst_0
      // 4017: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 401a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 401d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4020: aastore
      // 4021: dup
      // 4022: bipush 1
      // 4023: new net/minecraft/client/animation/Keyframe
      // 4026: dup
      // 4027: ldc_w 0.875
      // 402a: ldc -0.5
      // 402c: fconst_0
      // 402d: ldc -10.4
      // 402f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4032: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4035: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4038: aastore
      // 4039: dup
      // 403a: bipush 2
      // 403b: new net/minecraft/client/animation/Keyframe
      // 403e: dup
      // 403f: fconst_1
      // 4040: fconst_0
      // 4041: fconst_0
      // 4042: fconst_0
      // 4043: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4046: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4049: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 404c: aastore
      // 404d: dup
      // 404e: bipush 3
      // 404f: new net/minecraft/client/animation/Keyframe
      // 4052: dup
      // 4053: ldc 1.125
      // 4055: fconst_0
      // 4056: fconst_1
      // 4057: ldc 4.5
      // 4059: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 405c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 405f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4062: aastore
      // 4063: dup
      // 4064: bipush 4
      // 4065: new net/minecraft/client/animation/Keyframe
      // 4068: dup
      // 4069: ldc 1.25
      // 406b: fconst_0
      // 406c: ldc 7.0
      // 406e: ldc -1.0
      // 4070: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4073: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4076: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4079: aastore
      // 407a: dup
      // 407b: bipush 5
      // 407c: new net/minecraft/client/animation/Keyframe
      // 407f: dup
      // 4080: ldc 1.375
      // 4082: ldc -0.5
      // 4084: fconst_0
      // 4085: ldc -10.5
      // 4087: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 408a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 408d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4090: aastore
      // 4091: dup
      // 4092: bipush 6
      // 4094: new net/minecraft/client/animation/Keyframe
      // 4097: dup
      // 4098: ldc_w 1.5
      // 409b: fconst_0
      // 409c: fconst_0
      // 409d: fconst_0
      // 409e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 40a1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 40a4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 40a7: aastore
      // 40a8: dup
      // 40a9: bipush 7
      // 40ab: new net/minecraft/client/animation/Keyframe
      // 40ae: dup
      // 40af: ldc 1.625
      // 40b1: fconst_0
      // 40b2: fconst_1
      // 40b3: ldc 4.5
      // 40b5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 40b8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 40bb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 40be: aastore
      // 40bf: dup
      // 40c0: bipush 8
      // 40c2: new net/minecraft/client/animation/Keyframe
      // 40c5: dup
      // 40c6: ldc 1.75
      // 40c8: fconst_0
      // 40c9: ldc 7.0
      // 40cb: ldc -1.0
      // 40cd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 40d0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 40d3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 40d6: aastore
      // 40d7: dup
      // 40d8: bipush 9
      // 40da: new net/minecraft/client/animation/Keyframe
      // 40dd: dup
      // 40de: ldc 1.875
      // 40e0: ldc -0.5
      // 40e2: fconst_0
      // 40e3: ldc -10.5
      // 40e5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 40e8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 40eb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 40ee: aastore
      // 40ef: dup
      // 40f0: bipush 10
      // 40f2: new net/minecraft/client/animation/Keyframe
      // 40f5: dup
      // 40f6: fconst_2
      // 40f7: fconst_0
      // 40f8: fconst_0
      // 40f9: fconst_0
      // 40fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 40fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4100: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4103: aastore
      // 4104: dup
      // 4105: bipush 11
      // 4107: new net/minecraft/client/animation/Keyframe
      // 410a: dup
      // 410b: ldc_w 2.125
      // 410e: fconst_0
      // 410f: fconst_1
      // 4110: ldc 4.5
      // 4112: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4115: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4118: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 411b: aastore
      // 411c: dup
      // 411d: bipush 12
      // 411f: new net/minecraft/client/animation/Keyframe
      // 4122: dup
      // 4123: ldc 2.25
      // 4125: fconst_0
      // 4126: ldc 7.0
      // 4128: ldc -1.0
      // 412a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 412d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4130: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4133: aastore
      // 4134: dup
      // 4135: bipush 13
      // 4137: new net/minecraft/client/animation/Keyframe
      // 413a: dup
      // 413b: ldc_w 2.375
      // 413e: ldc -0.5
      // 4140: fconst_0
      // 4141: ldc -10.5
      // 4143: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4146: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4149: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 414c: aastore
      // 414d: dup
      // 414e: bipush 14
      // 4150: new net/minecraft/client/animation/Keyframe
      // 4153: dup
      // 4154: ldc_w 3.5
      // 4157: fconst_0
      // 4158: fconst_0
      // 4159: fconst_0
      // 415a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 415d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4160: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4163: aastore
      // 4164: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4167: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 416a: ldc "leftleg"
      // 416c: new net/minecraft/client/animation/AnimationChannel
      // 416f: dup
      // 4170: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4173: bipush 15
      // 4175: anewarray 28
      // 4178: dup
      // 4179: bipush 0
      // 417a: new net/minecraft/client/animation/Keyframe
      // 417d: dup
      // 417e: fconst_0
      // 417f: fconst_0
      // 4180: fconst_0
      // 4181: fconst_0
      // 4182: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4185: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4188: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 418b: aastore
      // 418c: dup
      // 418d: bipush 1
      // 418e: new net/minecraft/client/animation/Keyframe
      // 4191: dup
      // 4192: ldc 0.75
      // 4194: ldc 12.5
      // 4196: fconst_0
      // 4197: fconst_0
      // 4198: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 419b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 419e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 41a1: aastore
      // 41a2: dup
      // 41a3: bipush 2
      // 41a4: new net/minecraft/client/animation/Keyframe
      // 41a7: dup
      // 41a8: ldc_w 0.875
      // 41ab: fconst_0
      // 41ac: fconst_0
      // 41ad: fconst_0
      // 41ae: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 41b1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 41b4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 41b7: aastore
      // 41b8: dup
      // 41b9: bipush 3
      // 41ba: new net/minecraft/client/animation/Keyframe
      // 41bd: dup
      // 41be: fconst_1
      // 41bf: ldc -5.0
      // 41c1: ldc -5.0
      // 41c3: fconst_0
      // 41c4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 41c7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 41ca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 41cd: aastore
      // 41ce: dup
      // 41cf: bipush 4
      // 41d0: new net/minecraft/client/animation/Keyframe
      // 41d3: dup
      // 41d4: ldc 1.125
      // 41d6: ldc 15.0
      // 41d8: fconst_0
      // 41d9: fconst_0
      // 41da: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 41dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 41e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 41e3: aastore
      // 41e4: dup
      // 41e5: bipush 5
      // 41e6: new net/minecraft/client/animation/Keyframe
      // 41e9: dup
      // 41ea: ldc 1.25
      // 41ec: ldc 12.5
      // 41ee: fconst_0
      // 41ef: fconst_0
      // 41f0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 41f3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 41f6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 41f9: aastore
      // 41fa: dup
      // 41fb: bipush 6
      // 41fd: new net/minecraft/client/animation/Keyframe
      // 4200: dup
      // 4201: ldc 1.375
      // 4203: fconst_0
      // 4204: fconst_0
      // 4205: fconst_0
      // 4206: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4209: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 420c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 420f: aastore
      // 4210: dup
      // 4211: bipush 7
      // 4213: new net/minecraft/client/animation/Keyframe
      // 4216: dup
      // 4217: ldc_w 1.5
      // 421a: ldc -5.0
      // 421c: ldc -5.0
      // 421e: fconst_0
      // 421f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4222: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4225: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4228: aastore
      // 4229: dup
      // 422a: bipush 8
      // 422c: new net/minecraft/client/animation/Keyframe
      // 422f: dup
      // 4230: ldc 1.625
      // 4232: ldc 15.0
      // 4234: fconst_0
      // 4235: fconst_0
      // 4236: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4239: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 423c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 423f: aastore
      // 4240: dup
      // 4241: bipush 9
      // 4243: new net/minecraft/client/animation/Keyframe
      // 4246: dup
      // 4247: ldc 1.75
      // 4249: ldc 12.5
      // 424b: fconst_0
      // 424c: fconst_0
      // 424d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4250: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4253: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4256: aastore
      // 4257: dup
      // 4258: bipush 10
      // 425a: new net/minecraft/client/animation/Keyframe
      // 425d: dup
      // 425e: ldc 1.875
      // 4260: fconst_0
      // 4261: fconst_0
      // 4262: fconst_0
      // 4263: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4266: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4269: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 426c: aastore
      // 426d: dup
      // 426e: bipush 11
      // 4270: new net/minecraft/client/animation/Keyframe
      // 4273: dup
      // 4274: fconst_2
      // 4275: ldc -5.0
      // 4277: ldc -5.0
      // 4279: fconst_0
      // 427a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 427d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4280: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4283: aastore
      // 4284: dup
      // 4285: bipush 12
      // 4287: new net/minecraft/client/animation/Keyframe
      // 428a: dup
      // 428b: ldc_w 2.125
      // 428e: ldc 15.0
      // 4290: fconst_0
      // 4291: fconst_0
      // 4292: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4295: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4298: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 429b: aastore
      // 429c: dup
      // 429d: bipush 13
      // 429f: new net/minecraft/client/animation/Keyframe
      // 42a2: dup
      // 42a3: ldc 2.25
      // 42a5: ldc 12.5
      // 42a7: fconst_0
      // 42a8: fconst_0
      // 42a9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 42ac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42af: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42b2: aastore
      // 42b3: dup
      // 42b4: bipush 14
      // 42b6: new net/minecraft/client/animation/Keyframe
      // 42b9: dup
      // 42ba: ldc_w 3.375
      // 42bd: fconst_0
      // 42be: fconst_0
      // 42bf: fconst_0
      // 42c0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 42c3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42c6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42c9: aastore
      // 42ca: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 42cd: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 42d0: ldc "leftleg"
      // 42d2: new net/minecraft/client/animation/AnimationChannel
      // 42d5: dup
      // 42d6: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 42d9: bipush 12
      // 42db: anewarray 28
      // 42de: dup
      // 42df: bipush 0
      // 42e0: new net/minecraft/client/animation/Keyframe
      // 42e3: dup
      // 42e4: fconst_0
      // 42e5: fconst_0
      // 42e6: fconst_0
      // 42e7: fconst_0
      // 42e8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 42eb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42ee: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42f1: aastore
      // 42f2: dup
      // 42f3: bipush 1
      // 42f4: new net/minecraft/client/animation/Keyframe
      // 42f7: dup
      // 42f8: ldc 0.75
      // 42fa: fconst_0
      // 42fb: fconst_0
      // 42fc: ldc 6.3
      // 42fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4301: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4304: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4307: aastore
      // 4308: dup
      // 4309: bipush 2
      // 430a: new net/minecraft/client/animation/Keyframe
      // 430d: dup
      // 430e: ldc_w 0.875
      // 4311: fconst_0
      // 4312: ldc 9.5
      // 4314: ldc -1.0
      // 4316: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4319: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 431c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 431f: aastore
      // 4320: dup
      // 4321: bipush 3
      // 4322: new net/minecraft/client/animation/Keyframe
      // 4325: dup
      // 4326: fconst_1
      // 4327: ldc 0.5
      // 4329: fconst_0
      // 432a: ldc -13.0
      // 432c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 432f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4332: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4335: aastore
      // 4336: dup
      // 4337: bipush 4
      // 4338: new net/minecraft/client/animation/Keyframe
      // 433b: dup
      // 433c: ldc 1.25
      // 433e: fconst_0
      // 433f: fconst_0
      // 4340: ldc 6.3
      // 4342: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4345: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4348: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 434b: aastore
      // 434c: dup
      // 434d: bipush 5
      // 434e: new net/minecraft/client/animation/Keyframe
      // 4351: dup
      // 4352: ldc 1.375
      // 4354: fconst_0
      // 4355: ldc 9.5
      // 4357: ldc -1.0
      // 4359: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 435c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 435f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4362: aastore
      // 4363: dup
      // 4364: bipush 6
      // 4366: new net/minecraft/client/animation/Keyframe
      // 4369: dup
      // 436a: ldc_w 1.5
      // 436d: ldc 0.5
      // 436f: fconst_0
      // 4370: ldc -13.0
      // 4372: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4375: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4378: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 437b: aastore
      // 437c: dup
      // 437d: bipush 7
      // 437f: new net/minecraft/client/animation/Keyframe
      // 4382: dup
      // 4383: ldc 1.75
      // 4385: fconst_0
      // 4386: fconst_0
      // 4387: ldc 6.3
      // 4389: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 438c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 438f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4392: aastore
      // 4393: dup
      // 4394: bipush 8
      // 4396: new net/minecraft/client/animation/Keyframe
      // 4399: dup
      // 439a: ldc 1.875
      // 439c: fconst_0
      // 439d: ldc 9.5
      // 439f: ldc -1.0
      // 43a1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 43a4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43a7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43aa: aastore
      // 43ab: dup
      // 43ac: bipush 9
      // 43ae: new net/minecraft/client/animation/Keyframe
      // 43b1: dup
      // 43b2: fconst_2
      // 43b3: ldc 0.5
      // 43b5: fconst_0
      // 43b6: ldc -13.0
      // 43b8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 43bb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43be: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43c1: aastore
      // 43c2: dup
      // 43c3: bipush 10
      // 43c5: new net/minecraft/client/animation/Keyframe
      // 43c8: dup
      // 43c9: ldc 2.25
      // 43cb: fconst_0
      // 43cc: fconst_0
      // 43cd: ldc 6.3
      // 43cf: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 43d2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43d5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43d8: aastore
      // 43d9: dup
      // 43da: bipush 11
      // 43dc: new net/minecraft/client/animation/Keyframe
      // 43df: dup
      // 43e0: ldc_w 3.375
      // 43e3: fconst_0
      // 43e4: fconst_0
      // 43e5: fconst_0
      // 43e6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 43e9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43ec: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43ef: aastore
      // 43f0: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 43f3: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 43f6: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.build ()Lnet/minecraft/client/animation/AnimationDefinition;
      // 43f9: putstatic com/github/L_Ender/cataclysm/client/animation/Netherite_Monstrosity_Animation.SHOULDER_CHECK Lnet/minecraft/client/animation/AnimationDefinition;
      // 43fc: ldc_w 3.75
      // 43ff: invokestatic net/minecraft/client/animation/AnimationDefinition$Builder.withLength (F)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4402: ldc "roots"
      // 4404: new net/minecraft/client/animation/AnimationChannel
      // 4407: dup
      // 4408: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 440b: bipush 9
      // 440d: anewarray 28
      // 4410: dup
      // 4411: bipush 0
      // 4412: new net/minecraft/client/animation/Keyframe
      // 4415: dup
      // 4416: fconst_0
      // 4417: fconst_0
      // 4418: fconst_0
      // 4419: fconst_0
      // 441a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 441d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4420: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4423: aastore
      // 4424: dup
      // 4425: bipush 1
      // 4426: new net/minecraft/client/animation/Keyframe
      // 4429: dup
      // 442a: ldc 0.375
      // 442c: fconst_0
      // 442d: fconst_0
      // 442e: fconst_0
      // 442f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4432: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4435: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4438: aastore
      // 4439: dup
      // 443a: bipush 2
      // 443b: new net/minecraft/client/animation/Keyframe
      // 443e: dup
      // 443f: ldc 0.5833
      // 4441: fconst_0
      // 4442: ldc -3.0
      // 4444: fconst_0
      // 4445: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4448: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 444b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 444e: aastore
      // 444f: dup
      // 4450: bipush 3
      // 4451: new net/minecraft/client/animation/Keyframe
      // 4454: dup
      // 4455: ldc 1.125
      // 4457: fconst_0
      // 4458: fconst_0
      // 4459: fconst_0
      // 445a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 445d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4460: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4463: aastore
      // 4464: dup
      // 4465: bipush 4
      // 4466: new net/minecraft/client/animation/Keyframe
      // 4469: dup
      // 446a: ldc 1.3333
      // 446c: fconst_0
      // 446d: fconst_0
      // 446e: fconst_0
      // 446f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4472: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4475: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4478: aastore
      // 4479: dup
      // 447a: bipush 5
      // 447b: new net/minecraft/client/animation/Keyframe
      // 447e: dup
      // 447f: ldc 1.5417
      // 4481: fconst_0
      // 4482: ldc -3.0
      // 4484: fconst_0
      // 4485: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4488: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 448b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 448e: aastore
      // 448f: dup
      // 4490: bipush 6
      // 4492: new net/minecraft/client/animation/Keyframe
      // 4495: dup
      // 4496: ldc 1.9167
      // 4498: fconst_0
      // 4499: fconst_0
      // 449a: fconst_0
      // 449b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 449e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44a1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 44a4: aastore
      // 44a5: dup
      // 44a6: bipush 7
      // 44a8: new net/minecraft/client/animation/Keyframe
      // 44ab: dup
      // 44ac: ldc_w 2.5417
      // 44af: fconst_0
      // 44b0: fconst_0
      // 44b1: fconst_0
      // 44b2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 44b5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44b8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 44bb: aastore
      // 44bc: dup
      // 44bd: bipush 8
      // 44bf: new net/minecraft/client/animation/Keyframe
      // 44c2: dup
      // 44c3: ldc_w 3.375
      // 44c6: fconst_0
      // 44c7: fconst_0
      // 44c8: fconst_0
      // 44c9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 44cc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44cf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 44d2: aastore
      // 44d3: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 44d6: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 44d9: ldc "upperbody"
      // 44db: new net/minecraft/client/animation/AnimationChannel
      // 44de: dup
      // 44df: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 44e2: bipush 9
      // 44e4: anewarray 28
      // 44e7: dup
      // 44e8: bipush 0
      // 44e9: new net/minecraft/client/animation/Keyframe
      // 44ec: dup
      // 44ed: fconst_0
      // 44ee: fconst_0
      // 44ef: fconst_0
      // 44f0: fconst_0
      // 44f1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 44f4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44f7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 44fa: aastore
      // 44fb: dup
      // 44fc: bipush 1
      // 44fd: new net/minecraft/client/animation/Keyframe
      // 4500: dup
      // 4501: ldc 0.375
      // 4503: fconst_0
      // 4504: fconst_0
      // 4505: fconst_0
      // 4506: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4509: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 450c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 450f: aastore
      // 4510: dup
      // 4511: bipush 2
      // 4512: new net/minecraft/client/animation/Keyframe
      // 4515: dup
      // 4516: ldc 0.7917
      // 4518: fconst_0
      // 4519: fconst_0
      // 451a: fconst_0
      // 451b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 451e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4521: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4524: aastore
      // 4525: dup
      // 4526: bipush 3
      // 4527: new net/minecraft/client/animation/Keyframe
      // 452a: dup
      // 452b: ldc 1.375
      // 452d: ldc_w 42.5
      // 4530: fconst_0
      // 4531: fconst_0
      // 4532: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4535: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4538: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 453b: aastore
      // 453c: dup
      // 453d: bipush 4
      // 453e: new net/minecraft/client/animation/Keyframe
      // 4541: dup
      // 4542: ldc 1.5833
      // 4544: ldc_w 42.5
      // 4547: fconst_0
      // 4548: fconst_0
      // 4549: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 454c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 454f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4552: aastore
      // 4553: dup
      // 4554: bipush 5
      // 4555: new net/minecraft/client/animation/Keyframe
      // 4558: dup
      // 4559: ldc 1.75
      // 455b: ldc_w -35.0
      // 455e: fconst_0
      // 455f: fconst_0
      // 4560: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4563: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4566: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4569: aastore
      // 456a: dup
      // 456b: bipush 6
      // 456d: new net/minecraft/client/animation/Keyframe
      // 4570: dup
      // 4571: ldc_w 2.2917
      // 4574: ldc_w -35.0
      // 4577: fconst_0
      // 4578: fconst_0
      // 4579: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 457c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 457f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4582: aastore
      // 4583: dup
      // 4584: bipush 7
      // 4586: new net/minecraft/client/animation/Keyframe
      // 4589: dup
      // 458a: ldc_w 2.5417
      // 458d: ldc_w -35.0
      // 4590: fconst_0
      // 4591: fconst_0
      // 4592: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4595: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4598: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 459b: aastore
      // 459c: dup
      // 459d: bipush 8
      // 459f: new net/minecraft/client/animation/Keyframe
      // 45a2: dup
      // 45a3: ldc_w 3.5
      // 45a6: fconst_0
      // 45a7: fconst_0
      // 45a8: fconst_0
      // 45a9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 45ac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 45af: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 45b2: aastore
      // 45b3: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 45b6: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 45b9: ldc "upperbody"
      // 45bb: new net/minecraft/client/animation/AnimationChannel
      // 45be: dup
      // 45bf: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 45c2: bipush 6
      // 45c4: anewarray 28
      // 45c7: dup
      // 45c8: bipush 0
      // 45c9: new net/minecraft/client/animation/Keyframe
      // 45cc: dup
      // 45cd: fconst_0
      // 45ce: fconst_0
      // 45cf: fconst_0
      // 45d0: fconst_0
      // 45d1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 45d4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 45d7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 45da: aastore
      // 45db: dup
      // 45dc: bipush 1
      // 45dd: new net/minecraft/client/animation/Keyframe
      // 45e0: dup
      // 45e1: ldc 0.375
      // 45e3: fconst_0
      // 45e4: fconst_0
      // 45e5: fconst_0
      // 45e6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 45e9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 45ec: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 45ef: aastore
      // 45f0: dup
      // 45f1: bipush 2
      // 45f2: new net/minecraft/client/animation/Keyframe
      // 45f5: dup
      // 45f6: ldc 0.4583
      // 45f8: fconst_0
      // 45f9: ldc -8.0
      // 45fb: fconst_0
      // 45fc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 45ff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4602: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4605: aastore
      // 4606: dup
      // 4607: bipush 3
      // 4608: new net/minecraft/client/animation/Keyframe
      // 460b: dup
      // 460c: ldc 0.9583
      // 460e: fconst_0
      // 460f: ldc -2.0
      // 4611: fconst_0
      // 4612: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4615: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4618: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 461b: aastore
      // 461c: dup
      // 461d: bipush 4
      // 461e: new net/minecraft/client/animation/Keyframe
      // 4621: dup
      // 4622: ldc_w 2.5417
      // 4625: fconst_0
      // 4626: ldc -2.0
      // 4628: fconst_0
      // 4629: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 462c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 462f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4632: aastore
      // 4633: dup
      // 4634: bipush 5
      // 4635: new net/minecraft/client/animation/Keyframe
      // 4638: dup
      // 4639: ldc_w 3.375
      // 463c: fconst_0
      // 463d: fconst_0
      // 463e: fconst_0
      // 463f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4642: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4645: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4648: aastore
      // 4649: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 464c: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 464f: ldc "head"
      // 4651: new net/minecraft/client/animation/AnimationChannel
      // 4654: dup
      // 4655: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4658: bipush 7
      // 465a: anewarray 28
      // 465d: dup
      // 465e: bipush 0
      // 465f: new net/minecraft/client/animation/Keyframe
      // 4662: dup
      // 4663: fconst_0
      // 4664: fconst_0
      // 4665: fconst_0
      // 4666: fconst_0
      // 4667: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 466a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 466d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4670: aastore
      // 4671: dup
      // 4672: bipush 1
      // 4673: new net/minecraft/client/animation/Keyframe
      // 4676: dup
      // 4677: ldc 0.375
      // 4679: fconst_0
      // 467a: fconst_0
      // 467b: fconst_0
      // 467c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 467f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4682: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4685: aastore
      // 4686: dup
      // 4687: bipush 2
      // 4688: new net/minecraft/client/animation/Keyframe
      // 468b: dup
      // 468c: ldc 1.0417
      // 468e: ldc_w -24.113
      // 4691: ldc_w 5.1752
      // 4694: ldc_w 11.3939
      // 4697: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 469a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 469d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 46a0: aastore
      // 46a1: dup
      // 46a2: bipush 3
      // 46a3: new net/minecraft/client/animation/Keyframe
      // 46a6: dup
      // 46a7: ldc 1.375
      // 46a9: ldc_w -35.0
      // 46ac: fconst_0
      // 46ad: fconst_0
      // 46ae: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 46b1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 46b4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 46b7: aastore
      // 46b8: dup
      // 46b9: bipush 4
      // 46ba: new net/minecraft/client/animation/Keyframe
      // 46bd: dup
      // 46be: ldc_w 2.0833
      // 46c1: ldc_w -42.6415
      // 46c4: ldc_w -8.2478
      // 46c7: ldc_w -2.5673
      // 46ca: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 46cd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 46d0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 46d3: aastore
      // 46d4: dup
      // 46d5: bipush 5
      // 46d6: new net/minecraft/client/animation/Keyframe
      // 46d9: dup
      // 46da: ldc_w 2.7083
      // 46dd: ldc_w -42.6415
      // 46e0: ldc_w -8.2478
      // 46e3: ldc_w -2.5673
      // 46e6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 46e9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 46ec: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 46ef: aastore
      // 46f0: dup
      // 46f1: bipush 6
      // 46f3: new net/minecraft/client/animation/Keyframe
      // 46f6: dup
      // 46f7: ldc_w 3.25
      // 46fa: fconst_0
      // 46fb: fconst_0
      // 46fc: fconst_0
      // 46fd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4700: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4703: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4706: aastore
      // 4707: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 470a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 470d: ldc "jaw"
      // 470f: new net/minecraft/client/animation/AnimationChannel
      // 4712: dup
      // 4713: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4716: bipush 8
      // 4718: anewarray 28
      // 471b: dup
      // 471c: bipush 0
      // 471d: new net/minecraft/client/animation/Keyframe
      // 4720: dup
      // 4721: fconst_0
      // 4722: fconst_0
      // 4723: fconst_0
      // 4724: fconst_0
      // 4725: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4728: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 472b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 472e: aastore
      // 472f: dup
      // 4730: bipush 1
      // 4731: new net/minecraft/client/animation/Keyframe
      // 4734: dup
      // 4735: ldc 0.375
      // 4737: fconst_0
      // 4738: fconst_0
      // 4739: fconst_0
      // 473a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 473d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4740: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4743: aastore
      // 4744: dup
      // 4745: bipush 2
      // 4746: new net/minecraft/client/animation/Keyframe
      // 4749: dup
      // 474a: ldc 1.125
      // 474c: ldc_w 8.7664
      // 474f: ldc_w 0.8233
      // 4752: ldc_w -7.4549
      // 4755: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4758: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 475b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 475e: aastore
      // 475f: dup
      // 4760: bipush 3
      // 4761: new net/minecraft/client/animation/Keyframe
      // 4764: dup
      // 4765: ldc 1.7083
      // 4767: ldc_w -1.2336
      // 476a: ldc_w 0.8233
      // 476d: ldc_w -7.4549
      // 4770: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4773: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4776: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4779: aastore
      // 477a: dup
      // 477b: bipush 4
      // 477c: new net/minecraft/client/animation/Keyframe
      // 477f: dup
      // 4780: ldc 1.9167
      // 4782: ldc_w 62.5
      // 4785: fconst_0
      // 4786: fconst_0
      // 4787: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 478a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 478d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4790: aastore
      // 4791: dup
      // 4792: bipush 5
      // 4793: new net/minecraft/client/animation/Keyframe
      // 4796: dup
      // 4797: ldc_w 2.3333
      // 479a: ldc_w 62.5
      // 479d: fconst_0
      // 479e: fconst_0
      // 479f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 47a2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 47a5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 47a8: aastore
      // 47a9: dup
      // 47aa: bipush 6
      // 47ac: new net/minecraft/client/animation/Keyframe
      // 47af: dup
      // 47b0: ldc_w 2.625
      // 47b3: ldc_w 62.5
      // 47b6: fconst_0
      // 47b7: fconst_0
      // 47b8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 47bb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 47be: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 47c1: aastore
      // 47c2: dup
      // 47c3: bipush 7
      // 47c5: new net/minecraft/client/animation/Keyframe
      // 47c8: dup
      // 47c9: ldc_w 3.75
      // 47cc: fconst_0
      // 47cd: fconst_0
      // 47ce: fconst_0
      // 47cf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 47d2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 47d5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 47d8: aastore
      // 47d9: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 47dc: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 47df: ldc "leftarm"
      // 47e1: new net/minecraft/client/animation/AnimationChannel
      // 47e4: dup
      // 47e5: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 47e8: bipush 7
      // 47ea: anewarray 28
      // 47ed: dup
      // 47ee: bipush 0
      // 47ef: new net/minecraft/client/animation/Keyframe
      // 47f2: dup
      // 47f3: fconst_0
      // 47f4: fconst_0
      // 47f5: fconst_0
      // 47f6: fconst_0
      // 47f7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 47fa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 47fd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4800: aastore
      // 4801: dup
      // 4802: bipush 1
      // 4803: new net/minecraft/client/animation/Keyframe
      // 4806: dup
      // 4807: ldc 0.375
      // 4809: fconst_0
      // 480a: fconst_0
      // 480b: fconst_0
      // 480c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 480f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4812: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4815: aastore
      // 4816: dup
      // 4817: bipush 2
      // 4818: new net/minecraft/client/animation/Keyframe
      // 481b: dup
      // 481c: ldc 0.7917
      // 481e: ldc_w 12.4
      // 4821: fconst_0
      // 4822: fconst_0
      // 4823: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4826: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4829: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 482c: aastore
      // 482d: dup
      // 482e: bipush 3
      // 482f: new net/minecraft/client/animation/Keyframe
      // 4832: dup
      // 4833: ldc 1.5833
      // 4835: fconst_0
      // 4836: fconst_0
      // 4837: fconst_0
      // 4838: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 483b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 483e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4841: aastore
      // 4842: dup
      // 4843: bipush 4
      // 4844: new net/minecraft/client/animation/Keyframe
      // 4847: dup
      // 4848: ldc 1.8333
      // 484a: ldc_w -140.0
      // 484d: fconst_0
      // 484e: fconst_0
      // 484f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4852: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4855: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4858: aastore
      // 4859: dup
      // 485a: bipush 5
      // 485b: new net/minecraft/client/animation/Keyframe
      // 485e: dup
      // 485f: ldc_w 2.625
      // 4862: ldc_w -140.0
      // 4865: fconst_0
      // 4866: fconst_0
      // 4867: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 486a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 486d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4870: aastore
      // 4871: dup
      // 4872: bipush 6
      // 4874: new net/minecraft/client/animation/Keyframe
      // 4877: dup
      // 4878: ldc_w 3.5833
      // 487b: fconst_0
      // 487c: fconst_0
      // 487d: fconst_0
      // 487e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4881: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4884: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4887: aastore
      // 4888: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 488b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 488e: ldc "leftarm"
      // 4890: new net/minecraft/client/animation/AnimationChannel
      // 4893: dup
      // 4894: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4897: bipush 7
      // 4899: anewarray 28
      // 489c: dup
      // 489d: bipush 0
      // 489e: new net/minecraft/client/animation/Keyframe
      // 48a1: dup
      // 48a2: fconst_0
      // 48a3: fconst_0
      // 48a4: fconst_0
      // 48a5: fconst_0
      // 48a6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 48a9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 48ac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 48af: aastore
      // 48b0: dup
      // 48b1: bipush 1
      // 48b2: new net/minecraft/client/animation/Keyframe
      // 48b5: dup
      // 48b6: ldc 0.375
      // 48b8: fconst_0
      // 48b9: fconst_0
      // 48ba: fconst_0
      // 48bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 48be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 48c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 48c4: aastore
      // 48c5: dup
      // 48c6: bipush 2
      // 48c7: new net/minecraft/client/animation/Keyframe
      // 48ca: dup
      // 48cb: ldc 0.5417
      // 48cd: fconst_0
      // 48ce: ldc_w -33.0
      // 48d1: ldc_w 3.0
      // 48d4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 48d7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 48da: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 48dd: aastore
      // 48de: dup
      // 48df: bipush 3
      // 48e0: new net/minecraft/client/animation/Keyframe
      // 48e3: dup
      // 48e4: ldc 1.4583
      // 48e6: fconst_0
      // 48e7: ldc_w -32.18
      // 48ea: ldc_w 10.05
      // 48ed: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 48f0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 48f3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 48f6: aastore
      // 48f7: dup
      // 48f8: bipush 4
      // 48f9: new net/minecraft/client/animation/Keyframe
      // 48fc: dup
      // 48fd: ldc 1.875
      // 48ff: fconst_0
      // 4900: ldc_w 18.0
      // 4903: ldc_w -27.0
      // 4906: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4909: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 490c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 490f: aastore
      // 4910: dup
      // 4911: bipush 5
      // 4912: new net/minecraft/client/animation/Keyframe
      // 4915: dup
      // 4916: ldc_w 2.625
      // 4919: fconst_0
      // 491a: ldc_w 18.0
      // 491d: ldc_w -27.0
      // 4920: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4923: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4926: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4929: aastore
      // 492a: dup
      // 492b: bipush 6
      // 492d: new net/minecraft/client/animation/Keyframe
      // 4930: dup
      // 4931: ldc_w 3.4583
      // 4934: fconst_0
      // 4935: fconst_0
      // 4936: fconst_0
      // 4937: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 493a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 493d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4940: aastore
      // 4941: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4944: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4947: ldc "leftarm2"
      // 4949: new net/minecraft/client/animation/AnimationChannel
      // 494c: dup
      // 494d: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4950: bipush 6
      // 4952: anewarray 28
      // 4955: dup
      // 4956: bipush 0
      // 4957: new net/minecraft/client/animation/Keyframe
      // 495a: dup
      // 495b: fconst_0
      // 495c: fconst_0
      // 495d: fconst_0
      // 495e: fconst_0
      // 495f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4962: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4965: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4968: aastore
      // 4969: dup
      // 496a: bipush 1
      // 496b: new net/minecraft/client/animation/Keyframe
      // 496e: dup
      // 496f: ldc 0.375
      // 4971: fconst_0
      // 4972: fconst_0
      // 4973: fconst_0
      // 4974: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4977: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 497a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 497d: aastore
      // 497e: dup
      // 497f: bipush 2
      // 4980: new net/minecraft/client/animation/Keyframe
      // 4983: dup
      // 4984: ldc_w 1.5
      // 4987: ldc_w -42.5
      // 498a: fconst_0
      // 498b: fconst_0
      // 498c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 498f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4992: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4995: aastore
      // 4996: dup
      // 4997: bipush 3
      // 4998: new net/minecraft/client/animation/Keyframe
      // 499b: dup
      // 499c: ldc 2.2083
      // 499e: ldc_w -62.5
      // 49a1: fconst_0
      // 49a2: fconst_0
      // 49a3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 49a6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 49a9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 49ac: aastore
      // 49ad: dup
      // 49ae: bipush 4
      // 49af: new net/minecraft/client/animation/Keyframe
      // 49b2: dup
      // 49b3: ldc_w 2.625
      // 49b6: ldc_w -62.5
      // 49b9: fconst_0
      // 49ba: fconst_0
      // 49bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 49be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 49c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 49c4: aastore
      // 49c5: dup
      // 49c6: bipush 5
      // 49c7: new net/minecraft/client/animation/Keyframe
      // 49ca: dup
      // 49cb: ldc_w 3.4583
      // 49ce: fconst_0
      // 49cf: fconst_0
      // 49d0: fconst_0
      // 49d1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 49d4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 49d7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 49da: aastore
      // 49db: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 49de: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 49e1: ldc "rightarm"
      // 49e3: new net/minecraft/client/animation/AnimationChannel
      // 49e6: dup
      // 49e7: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 49ea: bipush 7
      // 49ec: anewarray 28
      // 49ef: dup
      // 49f0: bipush 0
      // 49f1: new net/minecraft/client/animation/Keyframe
      // 49f4: dup
      // 49f5: fconst_0
      // 49f6: fconst_0
      // 49f7: fconst_0
      // 49f8: fconst_0
      // 49f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 49fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 49ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a02: aastore
      // 4a03: dup
      // 4a04: bipush 1
      // 4a05: new net/minecraft/client/animation/Keyframe
      // 4a08: dup
      // 4a09: ldc 0.375
      // 4a0b: fconst_0
      // 4a0c: fconst_0
      // 4a0d: fconst_0
      // 4a0e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a11: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a14: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a17: aastore
      // 4a18: dup
      // 4a19: bipush 2
      // 4a1a: new net/minecraft/client/animation/Keyframe
      // 4a1d: dup
      // 4a1e: ldc 0.7917
      // 4a20: ldc_w 12.4
      // 4a23: fconst_0
      // 4a24: fconst_0
      // 4a25: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a28: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a2b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a2e: aastore
      // 4a2f: dup
      // 4a30: bipush 3
      // 4a31: new net/minecraft/client/animation/Keyframe
      // 4a34: dup
      // 4a35: ldc 1.5833
      // 4a37: fconst_0
      // 4a38: fconst_0
      // 4a39: fconst_0
      // 4a3a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a3d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a40: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a43: aastore
      // 4a44: dup
      // 4a45: bipush 4
      // 4a46: new net/minecraft/client/animation/Keyframe
      // 4a49: dup
      // 4a4a: ldc 1.8333
      // 4a4c: ldc_w -140.0
      // 4a4f: fconst_0
      // 4a50: fconst_0
      // 4a51: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a54: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a57: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a5a: aastore
      // 4a5b: dup
      // 4a5c: bipush 5
      // 4a5d: new net/minecraft/client/animation/Keyframe
      // 4a60: dup
      // 4a61: ldc_w 2.625
      // 4a64: ldc_w -140.0
      // 4a67: fconst_0
      // 4a68: fconst_0
      // 4a69: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a6c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a6f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a72: aastore
      // 4a73: dup
      // 4a74: bipush 6
      // 4a76: new net/minecraft/client/animation/Keyframe
      // 4a79: dup
      // 4a7a: ldc_w 3.5833
      // 4a7d: fconst_0
      // 4a7e: fconst_0
      // 4a7f: fconst_0
      // 4a80: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a83: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a86: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a89: aastore
      // 4a8a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4a8d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4a90: ldc "rightarm"
      // 4a92: new net/minecraft/client/animation/AnimationChannel
      // 4a95: dup
      // 4a96: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4a99: bipush 7
      // 4a9b: anewarray 28
      // 4a9e: dup
      // 4a9f: bipush 0
      // 4aa0: new net/minecraft/client/animation/Keyframe
      // 4aa3: dup
      // 4aa4: fconst_0
      // 4aa5: fconst_0
      // 4aa6: fconst_0
      // 4aa7: fconst_0
      // 4aa8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4aab: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4aae: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ab1: aastore
      // 4ab2: dup
      // 4ab3: bipush 1
      // 4ab4: new net/minecraft/client/animation/Keyframe
      // 4ab7: dup
      // 4ab8: ldc 0.375
      // 4aba: fconst_0
      // 4abb: fconst_0
      // 4abc: fconst_0
      // 4abd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4ac0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ac3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ac6: aastore
      // 4ac7: dup
      // 4ac8: bipush 2
      // 4ac9: new net/minecraft/client/animation/Keyframe
      // 4acc: dup
      // 4acd: ldc 0.5417
      // 4acf: fconst_0
      // 4ad0: ldc_w -33.0
      // 4ad3: ldc_w 3.0
      // 4ad6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4ad9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4adc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4adf: aastore
      // 4ae0: dup
      // 4ae1: bipush 3
      // 4ae2: new net/minecraft/client/animation/Keyframe
      // 4ae5: dup
      // 4ae6: ldc 1.4583
      // 4ae8: fconst_0
      // 4ae9: ldc_w -31.18
      // 4aec: ldc_w 10.05
      // 4aef: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4af2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4af5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4af8: aastore
      // 4af9: dup
      // 4afa: bipush 4
      // 4afb: new net/minecraft/client/animation/Keyframe
      // 4afe: dup
      // 4aff: ldc 1.875
      // 4b01: fconst_0
      // 4b02: ldc_w 18.0
      // 4b05: ldc_w -27.0
      // 4b08: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4b0b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b0e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b11: aastore
      // 4b12: dup
      // 4b13: bipush 5
      // 4b14: new net/minecraft/client/animation/Keyframe
      // 4b17: dup
      // 4b18: ldc_w 2.625
      // 4b1b: fconst_0
      // 4b1c: ldc_w 18.0
      // 4b1f: ldc_w -27.0
      // 4b22: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4b25: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b28: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b2b: aastore
      // 4b2c: dup
      // 4b2d: bipush 6
      // 4b2f: new net/minecraft/client/animation/Keyframe
      // 4b32: dup
      // 4b33: ldc_w 3.4583
      // 4b36: fconst_0
      // 4b37: fconst_0
      // 4b38: fconst_0
      // 4b39: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4b3c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b3f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b42: aastore
      // 4b43: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4b46: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4b49: ldc "rightarm2"
      // 4b4b: new net/minecraft/client/animation/AnimationChannel
      // 4b4e: dup
      // 4b4f: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4b52: bipush 6
      // 4b54: anewarray 28
      // 4b57: dup
      // 4b58: bipush 0
      // 4b59: new net/minecraft/client/animation/Keyframe
      // 4b5c: dup
      // 4b5d: fconst_0
      // 4b5e: fconst_0
      // 4b5f: fconst_0
      // 4b60: fconst_0
      // 4b61: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b64: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b67: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b6a: aastore
      // 4b6b: dup
      // 4b6c: bipush 1
      // 4b6d: new net/minecraft/client/animation/Keyframe
      // 4b70: dup
      // 4b71: ldc 0.375
      // 4b73: fconst_0
      // 4b74: fconst_0
      // 4b75: fconst_0
      // 4b76: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b79: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b7c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b7f: aastore
      // 4b80: dup
      // 4b81: bipush 2
      // 4b82: new net/minecraft/client/animation/Keyframe
      // 4b85: dup
      // 4b86: ldc_w 1.5
      // 4b89: ldc_w -42.5
      // 4b8c: fconst_0
      // 4b8d: fconst_0
      // 4b8e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b91: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b94: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b97: aastore
      // 4b98: dup
      // 4b99: bipush 3
      // 4b9a: new net/minecraft/client/animation/Keyframe
      // 4b9d: dup
      // 4b9e: ldc 2.2083
      // 4ba0: ldc_w -62.5
      // 4ba3: fconst_0
      // 4ba4: fconst_0
      // 4ba5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ba8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4bab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4bae: aastore
      // 4baf: dup
      // 4bb0: bipush 4
      // 4bb1: new net/minecraft/client/animation/Keyframe
      // 4bb4: dup
      // 4bb5: ldc_w 2.625
      // 4bb8: ldc_w -62.5
      // 4bbb: fconst_0
      // 4bbc: fconst_0
      // 4bbd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4bc0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4bc3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4bc6: aastore
      // 4bc7: dup
      // 4bc8: bipush 5
      // 4bc9: new net/minecraft/client/animation/Keyframe
      // 4bcc: dup
      // 4bcd: ldc_w 3.4583
      // 4bd0: fconst_0
      // 4bd1: fconst_0
      // 4bd2: fconst_0
      // 4bd3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4bd6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4bd9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4bdc: aastore
      // 4bdd: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4be0: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4be3: ldc "rightleg"
      // 4be5: new net/minecraft/client/animation/AnimationChannel
      // 4be8: dup
      // 4be9: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4bec: bipush 6
      // 4bee: anewarray 28
      // 4bf1: dup
      // 4bf2: bipush 0
      // 4bf3: new net/minecraft/client/animation/Keyframe
      // 4bf6: dup
      // 4bf7: fconst_0
      // 4bf8: fconst_0
      // 4bf9: fconst_0
      // 4bfa: fconst_0
      // 4bfb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4bfe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c01: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c04: aastore
      // 4c05: dup
      // 4c06: bipush 1
      // 4c07: new net/minecraft/client/animation/Keyframe
      // 4c0a: dup
      // 4c0b: ldc 0.375
      // 4c0d: fconst_0
      // 4c0e: fconst_0
      // 4c0f: fconst_0
      // 4c10: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c13: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c16: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c19: aastore
      // 4c1a: dup
      // 4c1b: bipush 2
      // 4c1c: new net/minecraft/client/animation/Keyframe
      // 4c1f: dup
      // 4c20: ldc 1.375
      // 4c22: fconst_0
      // 4c23: fconst_0
      // 4c24: fconst_0
      // 4c25: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c28: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c2b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c2e: aastore
      // 4c2f: dup
      // 4c30: bipush 3
      // 4c31: new net/minecraft/client/animation/Keyframe
      // 4c34: dup
      // 4c35: ldc 1.875
      // 4c37: ldc 15.0
      // 4c39: fconst_0
      // 4c3a: fconst_0
      // 4c3b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c3e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c41: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c44: aastore
      // 4c45: dup
      // 4c46: bipush 4
      // 4c47: new net/minecraft/client/animation/Keyframe
      // 4c4a: dup
      // 4c4b: ldc_w 2.625
      // 4c4e: ldc 15.0
      // 4c50: fconst_0
      // 4c51: fconst_0
      // 4c52: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c55: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c58: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c5b: aastore
      // 4c5c: dup
      // 4c5d: bipush 5
      // 4c5e: new net/minecraft/client/animation/Keyframe
      // 4c61: dup
      // 4c62: ldc_w 3.4583
      // 4c65: fconst_0
      // 4c66: fconst_0
      // 4c67: fconst_0
      // 4c68: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c6b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c6e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c71: aastore
      // 4c72: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4c75: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4c78: ldc "rightleg"
      // 4c7a: new net/minecraft/client/animation/AnimationChannel
      // 4c7d: dup
      // 4c7e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4c81: bipush 6
      // 4c83: anewarray 28
      // 4c86: dup
      // 4c87: bipush 0
      // 4c88: new net/minecraft/client/animation/Keyframe
      // 4c8b: dup
      // 4c8c: fconst_0
      // 4c8d: fconst_0
      // 4c8e: fconst_0
      // 4c8f: fconst_0
      // 4c90: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4c93: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c96: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c99: aastore
      // 4c9a: dup
      // 4c9b: bipush 1
      // 4c9c: new net/minecraft/client/animation/Keyframe
      // 4c9f: dup
      // 4ca0: ldc 0.375
      // 4ca2: fconst_0
      // 4ca3: fconst_0
      // 4ca4: fconst_0
      // 4ca5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4ca8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4cab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4cae: aastore
      // 4caf: dup
      // 4cb0: bipush 2
      // 4cb1: new net/minecraft/client/animation/Keyframe
      // 4cb4: dup
      // 4cb5: ldc 1.375
      // 4cb7: fconst_0
      // 4cb8: fconst_0
      // 4cb9: fconst_0
      // 4cba: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4cbd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4cc0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4cc3: aastore
      // 4cc4: dup
      // 4cc5: bipush 3
      // 4cc6: new net/minecraft/client/animation/Keyframe
      // 4cc9: dup
      // 4cca: ldc 1.875
      // 4ccc: fconst_0
      // 4ccd: fconst_0
      // 4cce: ldc_w 9.0
      // 4cd1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4cd4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4cd7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4cda: aastore
      // 4cdb: dup
      // 4cdc: bipush 4
      // 4cdd: new net/minecraft/client/animation/Keyframe
      // 4ce0: dup
      // 4ce1: ldc_w 2.625
      // 4ce4: fconst_0
      // 4ce5: fconst_0
      // 4ce6: ldc_w 9.0
      // 4ce9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4cec: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4cef: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4cf2: aastore
      // 4cf3: dup
      // 4cf4: bipush 5
      // 4cf5: new net/minecraft/client/animation/Keyframe
      // 4cf8: dup
      // 4cf9: ldc_w 3.4583
      // 4cfc: fconst_0
      // 4cfd: fconst_0
      // 4cfe: fconst_0
      // 4cff: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4d02: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d05: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d08: aastore
      // 4d09: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4d0c: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4d0f: ldc "leftleg"
      // 4d11: new net/minecraft/client/animation/AnimationChannel
      // 4d14: dup
      // 4d15: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4d18: bipush 8
      // 4d1a: anewarray 28
      // 4d1d: dup
      // 4d1e: bipush 0
      // 4d1f: new net/minecraft/client/animation/Keyframe
      // 4d22: dup
      // 4d23: fconst_0
      // 4d24: fconst_0
      // 4d25: fconst_0
      // 4d26: fconst_0
      // 4d27: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4d2a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d2d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d30: aastore
      // 4d31: dup
      // 4d32: bipush 1
      // 4d33: new net/minecraft/client/animation/Keyframe
      // 4d36: dup
      // 4d37: ldc 0.375
      // 4d39: fconst_0
      // 4d3a: fconst_0
      // 4d3b: fconst_0
      // 4d3c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4d3f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d42: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d45: aastore
      // 4d46: dup
      // 4d47: bipush 2
      // 4d48: new net/minecraft/client/animation/Keyframe
      // 4d4b: dup
      // 4d4c: ldc 1.0833
      // 4d4e: fconst_0
      // 4d4f: fconst_0
      // 4d50: fconst_0
      // 4d51: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4d54: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d57: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d5a: aastore
      // 4d5b: dup
      // 4d5c: bipush 3
      // 4d5d: new net/minecraft/client/animation/Keyframe
      // 4d60: dup
      // 4d61: ldc 1.3333
      // 4d63: fconst_0
      // 4d64: fconst_0
      // 4d65: fconst_0
      // 4d66: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4d69: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d6c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d6f: aastore
      // 4d70: dup
      // 4d71: bipush 4
      // 4d72: new net/minecraft/client/animation/Keyframe
      // 4d75: dup
      // 4d76: ldc 1.5417
      // 4d78: fconst_0
      // 4d79: ldc 5.0
      // 4d7b: ldc_w -8.68
      // 4d7e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4d81: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d84: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d87: aastore
      // 4d88: dup
      // 4d89: bipush 5
      // 4d8a: new net/minecraft/client/animation/Keyframe
      // 4d8d: dup
      // 4d8e: ldc 1.7083
      // 4d90: fconst_0
      // 4d91: fconst_0
      // 4d92: ldc -13.0
      // 4d94: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4d97: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d9a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d9d: aastore
      // 4d9e: dup
      // 4d9f: bipush 6
      // 4da1: new net/minecraft/client/animation/Keyframe
      // 4da4: dup
      // 4da5: ldc_w 2.625
      // 4da8: fconst_0
      // 4da9: fconst_0
      // 4daa: ldc -13.0
      // 4dac: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4daf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4db2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4db5: aastore
      // 4db6: dup
      // 4db7: bipush 7
      // 4db9: new net/minecraft/client/animation/Keyframe
      // 4dbc: dup
      // 4dbd: ldc_w 3.4583
      // 4dc0: fconst_0
      // 4dc1: fconst_0
      // 4dc2: fconst_0
      // 4dc3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4dc6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4dc9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4dcc: aastore
      // 4dcd: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4dd0: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4dd3: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.build ()Lnet/minecraft/client/animation/AnimationDefinition;
      // 4dd6: putstatic com/github/L_Ender/cataclysm/client/animation/Netherite_Monstrosity_Animation.OVERPOWER Lnet/minecraft/client/animation/AnimationDefinition;
      // 4dd9: ldc_w 2.9583
      // 4ddc: invokestatic net/minecraft/client/animation/AnimationDefinition$Builder.withLength (F)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4ddf: ldc "lowerbody"
      // 4de1: new net/minecraft/client/animation/AnimationChannel
      // 4de4: dup
      // 4de5: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4de8: bipush 5
      // 4de9: anewarray 28
      // 4dec: dup
      // 4ded: bipush 0
      // 4dee: new net/minecraft/client/animation/Keyframe
      // 4df1: dup
      // 4df2: fconst_0
      // 4df3: fconst_0
      // 4df4: fconst_0
      // 4df5: fconst_0
      // 4df6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4df9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4dfc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4dff: aastore
      // 4e00: dup
      // 4e01: bipush 1
      // 4e02: new net/minecraft/client/animation/Keyframe
      // 4e05: dup
      // 4e06: ldc 0.5
      // 4e08: ldc -5.0
      // 4e0a: fconst_0
      // 4e0b: fconst_0
      // 4e0c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e0f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e12: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e15: aastore
      // 4e16: dup
      // 4e17: bipush 2
      // 4e18: new net/minecraft/client/animation/Keyframe
      // 4e1b: dup
      // 4e1c: ldc 0.7917
      // 4e1e: ldc -5.0
      // 4e20: fconst_0
      // 4e21: fconst_0
      // 4e22: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e25: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e28: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e2b: aastore
      // 4e2c: dup
      // 4e2d: bipush 3
      // 4e2e: new net/minecraft/client/animation/Keyframe
      // 4e31: dup
      // 4e32: ldc 2.2083
      // 4e34: ldc -5.0
      // 4e36: fconst_0
      // 4e37: fconst_0
      // 4e38: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e3b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e3e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e41: aastore
      // 4e42: dup
      // 4e43: bipush 4
      // 4e44: new net/minecraft/client/animation/Keyframe
      // 4e47: dup
      // 4e48: ldc_w 2.9583
      // 4e4b: fconst_0
      // 4e4c: fconst_0
      // 4e4d: fconst_0
      // 4e4e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e51: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e54: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e57: aastore
      // 4e58: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4e5b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4e5e: ldc "upperbody"
      // 4e60: new net/minecraft/client/animation/AnimationChannel
      // 4e63: dup
      // 4e64: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4e67: bipush 7
      // 4e69: anewarray 28
      // 4e6c: dup
      // 4e6d: bipush 0
      // 4e6e: new net/minecraft/client/animation/Keyframe
      // 4e71: dup
      // 4e72: fconst_0
      // 4e73: fconst_0
      // 4e74: fconst_0
      // 4e75: fconst_0
      // 4e76: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e79: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e7c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e7f: aastore
      // 4e80: dup
      // 4e81: bipush 1
      // 4e82: new net/minecraft/client/animation/Keyframe
      // 4e85: dup
      // 4e86: ldc 0.5
      // 4e88: ldc -10.0
      // 4e8a: fconst_0
      // 4e8b: fconst_0
      // 4e8c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e8f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e92: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e95: aastore
      // 4e96: dup
      // 4e97: bipush 2
      // 4e98: new net/minecraft/client/animation/Keyframe
      // 4e9b: dup
      // 4e9c: ldc 0.7917
      // 4e9e: ldc_w -17.5
      // 4ea1: fconst_0
      // 4ea2: fconst_0
      // 4ea3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ea6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ea9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4eac: aastore
      // 4ead: dup
      // 4eae: bipush 3
      // 4eaf: new net/minecraft/client/animation/Keyframe
      // 4eb2: dup
      // 4eb3: ldc 0.9167
      // 4eb5: ldc 30.0
      // 4eb7: fconst_0
      // 4eb8: fconst_0
      // 4eb9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ebc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ebf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ec2: aastore
      // 4ec3: dup
      // 4ec4: bipush 4
      // 4ec5: new net/minecraft/client/animation/Keyframe
      // 4ec8: dup
      // 4ec9: ldc 1.2083
      // 4ecb: ldc 30.0
      // 4ecd: fconst_0
      // 4ece: fconst_0
      // 4ecf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ed2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ed5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ed8: aastore
      // 4ed9: dup
      // 4eda: bipush 5
      // 4edb: new net/minecraft/client/animation/Keyframe
      // 4ede: dup
      // 4edf: ldc 2.2083
      // 4ee1: ldc 30.0
      // 4ee3: fconst_0
      // 4ee4: fconst_0
      // 4ee5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ee8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4eeb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4eee: aastore
      // 4eef: dup
      // 4ef0: bipush 6
      // 4ef2: new net/minecraft/client/animation/Keyframe
      // 4ef5: dup
      // 4ef6: ldc_w 2.9583
      // 4ef9: fconst_0
      // 4efa: fconst_0
      // 4efb: fconst_0
      // 4efc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4eff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f02: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f05: aastore
      // 4f06: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4f09: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4f0c: ldc "upperbody"
      // 4f0e: new net/minecraft/client/animation/AnimationChannel
      // 4f11: dup
      // 4f12: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4f15: bipush 8
      // 4f17: anewarray 28
      // 4f1a: dup
      // 4f1b: bipush 0
      // 4f1c: new net/minecraft/client/animation/Keyframe
      // 4f1f: dup
      // 4f20: fconst_0
      // 4f21: fconst_0
      // 4f22: fconst_0
      // 4f23: fconst_0
      // 4f24: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4f27: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f2a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f2d: aastore
      // 4f2e: dup
      // 4f2f: bipush 1
      // 4f30: new net/minecraft/client/animation/Keyframe
      // 4f33: dup
      // 4f34: ldc_w 0.875
      // 4f37: fconst_0
      // 4f38: fconst_0
      // 4f39: fconst_0
      // 4f3a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4f3d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f40: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f43: aastore
      // 4f44: dup
      // 4f45: bipush 2
      // 4f46: new net/minecraft/client/animation/Keyframe
      // 4f49: dup
      // 4f4a: ldc 1.0833
      // 4f4c: fconst_0
      // 4f4d: ldc -2.0
      // 4f4f: fconst_0
      // 4f50: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4f53: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f56: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f59: aastore
      // 4f5a: dup
      // 4f5b: bipush 3
      // 4f5c: new net/minecraft/client/animation/Keyframe
      // 4f5f: dup
      // 4f60: ldc 1.25
      // 4f62: fconst_0
      // 4f63: fconst_0
      // 4f64: fconst_0
      // 4f65: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4f68: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f6b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f6e: aastore
      // 4f6f: dup
      // 4f70: bipush 4
      // 4f71: new net/minecraft/client/animation/Keyframe
      // 4f74: dup
      // 4f75: ldc 1.5417
      // 4f77: fconst_0
      // 4f78: ldc -2.0
      // 4f7a: fconst_0
      // 4f7b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4f7e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f81: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f84: aastore
      // 4f85: dup
      // 4f86: bipush 5
      // 4f87: new net/minecraft/client/animation/Keyframe
      // 4f8a: dup
      // 4f8b: ldc 1.8333
      // 4f8d: fconst_0
      // 4f8e: fconst_0
      // 4f8f: fconst_0
      // 4f90: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4f93: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f96: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f99: aastore
      // 4f9a: dup
      // 4f9b: bipush 6
      // 4f9d: new net/minecraft/client/animation/Keyframe
      // 4fa0: dup
      // 4fa1: ldc 2.2083
      // 4fa3: fconst_0
      // 4fa4: fconst_0
      // 4fa5: fconst_0
      // 4fa6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4fa9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4fac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4faf: aastore
      // 4fb0: dup
      // 4fb1: bipush 7
      // 4fb3: new net/minecraft/client/animation/Keyframe
      // 4fb6: dup
      // 4fb7: ldc_w 2.9583
      // 4fba: fconst_0
      // 4fbb: fconst_0
      // 4fbc: fconst_0
      // 4fbd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4fc0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4fc3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4fc6: aastore
      // 4fc7: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4fca: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4fcd: ldc "head"
      // 4fcf: new net/minecraft/client/animation/AnimationChannel
      // 4fd2: dup
      // 4fd3: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4fd6: bipush 5
      // 4fd7: anewarray 28
      // 4fda: dup
      // 4fdb: bipush 0
      // 4fdc: new net/minecraft/client/animation/Keyframe
      // 4fdf: dup
      // 4fe0: fconst_0
      // 4fe1: fconst_0
      // 4fe2: fconst_0
      // 4fe3: fconst_0
      // 4fe4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4fe7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4fea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4fed: aastore
      // 4fee: dup
      // 4fef: bipush 1
      // 4ff0: new net/minecraft/client/animation/Keyframe
      // 4ff3: dup
      // 4ff4: ldc 0.5
      // 4ff6: ldc_w 20.0
      // 4ff9: fconst_0
      // 4ffa: fconst_0
      // 4ffb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ffe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5001: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5004: aastore
      // 5005: dup
      // 5006: bipush 2
      // 5007: new net/minecraft/client/animation/Keyframe
      // 500a: dup
      // 500b: ldc 1.2083
      // 500d: ldc -10.0
      // 500f: fconst_0
      // 5010: fconst_0
      // 5011: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5014: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5017: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 501a: aastore
      // 501b: dup
      // 501c: bipush 3
      // 501d: new net/minecraft/client/animation/Keyframe
      // 5020: dup
      // 5021: ldc 2.2083
      // 5023: ldc -10.0
      // 5025: fconst_0
      // 5026: fconst_0
      // 5027: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 502a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 502d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5030: aastore
      // 5031: dup
      // 5032: bipush 4
      // 5033: new net/minecraft/client/animation/Keyframe
      // 5036: dup
      // 5037: ldc_w 2.9583
      // 503a: fconst_0
      // 503b: fconst_0
      // 503c: fconst_0
      // 503d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5040: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5043: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5046: aastore
      // 5047: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 504a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 504d: ldc "jaw"
      // 504f: new net/minecraft/client/animation/AnimationChannel
      // 5052: dup
      // 5053: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5056: bipush 7
      // 5058: anewarray 28
      // 505b: dup
      // 505c: bipush 0
      // 505d: new net/minecraft/client/animation/Keyframe
      // 5060: dup
      // 5061: fconst_0
      // 5062: fconst_0
      // 5063: fconst_0
      // 5064: fconst_0
      // 5065: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5068: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 506b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 506e: aastore
      // 506f: dup
      // 5070: bipush 1
      // 5071: new net/minecraft/client/animation/Keyframe
      // 5074: dup
      // 5075: ldc 0.5
      // 5077: ldc -5.0
      // 5079: fconst_0
      // 507a: fconst_0
      // 507b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 507e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5081: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5084: aastore
      // 5085: dup
      // 5086: bipush 2
      // 5087: new net/minecraft/client/animation/Keyframe
      // 508a: dup
      // 508b: ldc 0.7917
      // 508d: ldc -5.0
      // 508f: fconst_0
      // 5090: fconst_0
      // 5091: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5094: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5097: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 509a: aastore
      // 509b: dup
      // 509c: bipush 3
      // 509d: new net/minecraft/client/animation/Keyframe
      // 50a0: dup
      // 50a1: ldc 1.125
      // 50a3: ldc 25.0
      // 50a5: fconst_0
      // 50a6: fconst_0
      // 50a7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 50aa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 50ad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 50b0: aastore
      // 50b1: dup
      // 50b2: bipush 4
      // 50b3: new net/minecraft/client/animation/Keyframe
      // 50b6: dup
      // 50b7: ldc 1.7083
      // 50b9: fconst_0
      // 50ba: fconst_0
      // 50bb: fconst_0
      // 50bc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 50bf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 50c2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 50c5: aastore
      // 50c6: dup
      // 50c7: bipush 5
      // 50c8: new net/minecraft/client/animation/Keyframe
      // 50cb: dup
      // 50cc: ldc 2.2083
      // 50ce: fconst_0
      // 50cf: fconst_0
      // 50d0: fconst_0
      // 50d1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 50d4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 50d7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 50da: aastore
      // 50db: dup
      // 50dc: bipush 6
      // 50de: new net/minecraft/client/animation/Keyframe
      // 50e1: dup
      // 50e2: ldc_w 2.9583
      // 50e5: fconst_0
      // 50e6: fconst_0
      // 50e7: fconst_0
      // 50e8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 50eb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 50ee: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 50f1: aastore
      // 50f2: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 50f5: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 50f8: ldc "leftarm"
      // 50fa: new net/minecraft/client/animation/AnimationChannel
      // 50fd: dup
      // 50fe: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5101: bipush 6
      // 5103: anewarray 28
      // 5106: dup
      // 5107: bipush 0
      // 5108: new net/minecraft/client/animation/Keyframe
      // 510b: dup
      // 510c: fconst_0
      // 510d: fconst_0
      // 510e: fconst_0
      // 510f: fconst_0
      // 5110: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5113: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5116: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5119: aastore
      // 511a: dup
      // 511b: bipush 1
      // 511c: new net/minecraft/client/animation/Keyframe
      // 511f: dup
      // 5120: ldc 0.5
      // 5122: fconst_0
      // 5123: ldc 90.0
      // 5125: fconst_0
      // 5126: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5129: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 512c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 512f: aastore
      // 5130: dup
      // 5131: bipush 2
      // 5132: new net/minecraft/client/animation/Keyframe
      // 5135: dup
      // 5136: ldc 0.7917
      // 5138: fconst_0
      // 5139: ldc 90.0
      // 513b: fconst_0
      // 513c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 513f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5142: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5145: aastore
      // 5146: dup
      // 5147: bipush 3
      // 5148: new net/minecraft/client/animation/Keyframe
      // 514b: dup
      // 514c: ldc 0.9167
      // 514e: ldc -90.0
      // 5150: ldc_w 60.0
      // 5153: ldc -90.0
      // 5155: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5158: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 515b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 515e: aastore
      // 515f: dup
      // 5160: bipush 4
      // 5161: new net/minecraft/client/animation/Keyframe
      // 5164: dup
      // 5165: ldc 2.2083
      // 5167: ldc -90.0
      // 5169: ldc_w 60.0
      // 516c: ldc -90.0
      // 516e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5171: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5174: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5177: aastore
      // 5178: dup
      // 5179: bipush 5
      // 517a: new net/minecraft/client/animation/Keyframe
      // 517d: dup
      // 517e: ldc_w 2.9583
      // 5181: fconst_0
      // 5182: fconst_0
      // 5183: fconst_0
      // 5184: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5187: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 518a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 518d: aastore
      // 518e: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 5191: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5194: ldc "leftarm"
      // 5196: new net/minecraft/client/animation/AnimationChannel
      // 5199: dup
      // 519a: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 519d: bipush 6
      // 519f: anewarray 28
      // 51a2: dup
      // 51a3: bipush 0
      // 51a4: new net/minecraft/client/animation/Keyframe
      // 51a7: dup
      // 51a8: fconst_0
      // 51a9: fconst_0
      // 51aa: fconst_0
      // 51ab: fconst_0
      // 51ac: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 51af: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 51b2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 51b5: aastore
      // 51b6: dup
      // 51b7: bipush 1
      // 51b8: new net/minecraft/client/animation/Keyframe
      // 51bb: dup
      // 51bc: ldc 0.5
      // 51be: ldc_w 6.0
      // 51c1: ldc 5.0
      // 51c3: fconst_0
      // 51c4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 51c7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 51ca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 51cd: aastore
      // 51ce: dup
      // 51cf: bipush 2
      // 51d0: new net/minecraft/client/animation/Keyframe
      // 51d3: dup
      // 51d4: ldc 0.7917
      // 51d6: ldc_w 6.0
      // 51d9: ldc 5.0
      // 51db: fconst_0
      // 51dc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 51df: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 51e2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 51e5: aastore
      // 51e6: dup
      // 51e7: bipush 3
      // 51e8: new net/minecraft/client/animation/Keyframe
      // 51eb: dup
      // 51ec: ldc 0.9583
      // 51ee: ldc 8.0
      // 51f0: ldc -20.0
      // 51f2: ldc_w -25.0
      // 51f5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 51f8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 51fb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 51fe: aastore
      // 51ff: dup
      // 5200: bipush 4
      // 5201: new net/minecraft/client/animation/Keyframe
      // 5204: dup
      // 5205: ldc 2.2083
      // 5207: ldc 8.0
      // 5209: ldc -20.0
      // 520b: ldc_w -25.0
      // 520e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5211: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5214: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5217: aastore
      // 5218: dup
      // 5219: bipush 5
      // 521a: new net/minecraft/client/animation/Keyframe
      // 521d: dup
      // 521e: ldc_w 2.9583
      // 5221: fconst_0
      // 5222: fconst_0
      // 5223: fconst_0
      // 5224: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5227: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 522a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 522d: aastore
      // 522e: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 5231: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5234: ldc "leftarm2"
      // 5236: new net/minecraft/client/animation/AnimationChannel
      // 5239: dup
      // 523a: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 523d: bipush 5
      // 523e: anewarray 28
      // 5241: dup
      // 5242: bipush 0
      // 5243: new net/minecraft/client/animation/Keyframe
      // 5246: dup
      // 5247: fconst_0
      // 5248: fconst_0
      // 5249: fconst_0
      // 524a: fconst_0
      // 524b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 524e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5251: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5254: aastore
      // 5255: dup
      // 5256: bipush 1
      // 5257: new net/minecraft/client/animation/Keyframe
      // 525a: dup
      // 525b: ldc 0.5
      // 525d: ldc 7.5
      // 525f: fconst_0
      // 5260: fconst_0
      // 5261: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5264: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5267: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 526a: aastore
      // 526b: dup
      // 526c: bipush 2
      // 526d: new net/minecraft/client/animation/Keyframe
      // 5270: dup
      // 5271: ldc 0.7917
      // 5273: ldc 7.5
      // 5275: fconst_0
      // 5276: fconst_0
      // 5277: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 527a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 527d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5280: aastore
      // 5281: dup
      // 5282: bipush 3
      // 5283: new net/minecraft/client/animation/Keyframe
      // 5286: dup
      // 5287: ldc 2.2083
      // 5289: ldc 7.5
      // 528b: fconst_0
      // 528c: fconst_0
      // 528d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5290: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5293: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5296: aastore
      // 5297: dup
      // 5298: bipush 4
      // 5299: new net/minecraft/client/animation/Keyframe
      // 529c: dup
      // 529d: ldc_w 2.9583
      // 52a0: fconst_0
      // 52a1: fconst_0
      // 52a2: fconst_0
      // 52a3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 52a6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 52a9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 52ac: aastore
      // 52ad: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 52b0: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 52b3: ldc "leftarm2"
      // 52b5: new net/minecraft/client/animation/AnimationChannel
      // 52b8: dup
      // 52b9: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 52bc: bipush 5
      // 52bd: anewarray 28
      // 52c0: dup
      // 52c1: bipush 0
      // 52c2: new net/minecraft/client/animation/Keyframe
      // 52c5: dup
      // 52c6: fconst_0
      // 52c7: fconst_0
      // 52c8: fconst_0
      // 52c9: fconst_0
      // 52ca: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 52cd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 52d0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 52d3: aastore
      // 52d4: dup
      // 52d5: bipush 1
      // 52d6: new net/minecraft/client/animation/Keyframe
      // 52d9: dup
      // 52da: ldc 0.7917
      // 52dc: fconst_0
      // 52dd: fconst_0
      // 52de: fconst_0
      // 52df: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 52e2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 52e5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 52e8: aastore
      // 52e9: dup
      // 52ea: bipush 2
      // 52eb: new net/minecraft/client/animation/Keyframe
      // 52ee: dup
      // 52ef: fconst_1
      // 52f0: fconst_0
      // 52f1: ldc_w 6.0
      // 52f4: fconst_0
      // 52f5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 52f8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 52fb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 52fe: aastore
      // 52ff: dup
      // 5300: bipush 3
      // 5301: new net/minecraft/client/animation/Keyframe
      // 5304: dup
      // 5305: ldc 2.2083
      // 5307: fconst_0
      // 5308: ldc_w 6.0
      // 530b: fconst_0
      // 530c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 530f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5312: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5315: aastore
      // 5316: dup
      // 5317: bipush 4
      // 5318: new net/minecraft/client/animation/Keyframe
      // 531b: dup
      // 531c: ldc_w 2.9583
      // 531f: fconst_0
      // 5320: fconst_0
      // 5321: fconst_0
      // 5322: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5325: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5328: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 532b: aastore
      // 532c: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 532f: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5332: ldc "lefthand"
      // 5334: new net/minecraft/client/animation/AnimationChannel
      // 5337: dup
      // 5338: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 533b: bipush 5
      // 533c: anewarray 28
      // 533f: dup
      // 5340: bipush 0
      // 5341: new net/minecraft/client/animation/Keyframe
      // 5344: dup
      // 5345: fconst_0
      // 5346: fconst_0
      // 5347: fconst_0
      // 5348: fconst_0
      // 5349: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 534c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 534f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5352: aastore
      // 5353: dup
      // 5354: bipush 1
      // 5355: new net/minecraft/client/animation/Keyframe
      // 5358: dup
      // 5359: ldc 0.5
      // 535b: ldc 5.0
      // 535d: fconst_0
      // 535e: fconst_0
      // 535f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5362: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5365: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5368: aastore
      // 5369: dup
      // 536a: bipush 2
      // 536b: new net/minecraft/client/animation/Keyframe
      // 536e: dup
      // 536f: ldc 0.7917
      // 5371: ldc 5.0
      // 5373: fconst_0
      // 5374: fconst_0
      // 5375: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5378: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 537b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 537e: aastore
      // 537f: dup
      // 5380: bipush 3
      // 5381: new net/minecraft/client/animation/Keyframe
      // 5384: dup
      // 5385: ldc 2.2083
      // 5387: ldc 5.0
      // 5389: fconst_0
      // 538a: fconst_0
      // 538b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 538e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5391: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5394: aastore
      // 5395: dup
      // 5396: bipush 4
      // 5397: new net/minecraft/client/animation/Keyframe
      // 539a: dup
      // 539b: ldc_w 2.9583
      // 539e: fconst_0
      // 539f: fconst_0
      // 53a0: fconst_0
      // 53a1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 53a4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 53a7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 53aa: aastore
      // 53ab: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 53ae: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 53b1: ldc "lefthand"
      // 53b3: new net/minecraft/client/animation/AnimationChannel
      // 53b6: dup
      // 53b7: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 53ba: bipush 6
      // 53bc: anewarray 28
      // 53bf: dup
      // 53c0: bipush 0
      // 53c1: new net/minecraft/client/animation/Keyframe
      // 53c4: dup
      // 53c5: fconst_0
      // 53c6: fconst_0
      // 53c7: fconst_0
      // 53c8: fconst_0
      // 53c9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 53cc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 53cf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 53d2: aastore
      // 53d3: dup
      // 53d4: bipush 1
      // 53d5: new net/minecraft/client/animation/Keyframe
      // 53d8: dup
      // 53d9: ldc 1.1667
      // 53db: fconst_0
      // 53dc: fconst_0
      // 53dd: fconst_0
      // 53de: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 53e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 53e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 53e7: aastore
      // 53e8: dup
      // 53e9: bipush 2
      // 53ea: new net/minecraft/client/animation/Keyframe
      // 53ed: dup
      // 53ee: ldc 1.4167
      // 53f0: fconst_0
      // 53f1: ldc_w 6.0
      // 53f4: fconst_0
      // 53f5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 53f8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 53fb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 53fe: aastore
      // 53ff: dup
      // 5400: bipush 3
      // 5401: new net/minecraft/client/animation/Keyframe
      // 5404: dup
      // 5405: ldc 1.6667
      // 5407: fconst_0
      // 5408: fconst_0
      // 5409: fconst_0
      // 540a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 540d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5410: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5413: aastore
      // 5414: dup
      // 5415: bipush 4
      // 5416: new net/minecraft/client/animation/Keyframe
      // 5419: dup
      // 541a: ldc 2.2083
      // 541c: fconst_0
      // 541d: ldc 5.0
      // 541f: fconst_0
      // 5420: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5423: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5426: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5429: aastore
      // 542a: dup
      // 542b: bipush 5
      // 542c: new net/minecraft/client/animation/Keyframe
      // 542f: dup
      // 5430: ldc_w 2.9583
      // 5433: fconst_0
      // 5434: fconst_0
      // 5435: fconst_0
      // 5436: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5439: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 543c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 543f: aastore
      // 5440: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 5443: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5446: ldc "rightarm"
      // 5448: new net/minecraft/client/animation/AnimationChannel
      // 544b: dup
      // 544c: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 544f: bipush 6
      // 5451: anewarray 28
      // 5454: dup
      // 5455: bipush 0
      // 5456: new net/minecraft/client/animation/Keyframe
      // 5459: dup
      // 545a: fconst_0
      // 545b: fconst_0
      // 545c: fconst_0
      // 545d: fconst_0
      // 545e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5461: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5464: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5467: aastore
      // 5468: dup
      // 5469: bipush 1
      // 546a: new net/minecraft/client/animation/Keyframe
      // 546d: dup
      // 546e: ldc 0.5
      // 5470: fconst_0
      // 5471: ldc -90.0
      // 5473: fconst_0
      // 5474: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5477: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 547a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 547d: aastore
      // 547e: dup
      // 547f: bipush 2
      // 5480: new net/minecraft/client/animation/Keyframe
      // 5483: dup
      // 5484: ldc 0.7917
      // 5486: fconst_0
      // 5487: ldc -90.0
      // 5489: fconst_0
      // 548a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 548d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5490: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5493: aastore
      // 5494: dup
      // 5495: bipush 3
      // 5496: new net/minecraft/client/animation/Keyframe
      // 5499: dup
      // 549a: ldc 0.9167
      // 549c: ldc -90.0
      // 549e: ldc_w -60.0
      // 54a1: ldc 90.0
      // 54a3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 54a6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 54a9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 54ac: aastore
      // 54ad: dup
      // 54ae: bipush 4
      // 54af: new net/minecraft/client/animation/Keyframe
      // 54b2: dup
      // 54b3: ldc 2.2083
      // 54b5: ldc -90.0
      // 54b7: ldc_w -60.0
      // 54ba: ldc 90.0
      // 54bc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 54bf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 54c2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 54c5: aastore
      // 54c6: dup
      // 54c7: bipush 5
      // 54c8: new net/minecraft/client/animation/Keyframe
      // 54cb: dup
      // 54cc: ldc_w 2.9583
      // 54cf: fconst_0
      // 54d0: fconst_0
      // 54d1: fconst_0
      // 54d2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 54d5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 54d8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 54db: aastore
      // 54dc: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 54df: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 54e2: ldc "rightarm"
      // 54e4: new net/minecraft/client/animation/AnimationChannel
      // 54e7: dup
      // 54e8: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 54eb: bipush 6
      // 54ed: anewarray 28
      // 54f0: dup
      // 54f1: bipush 0
      // 54f2: new net/minecraft/client/animation/Keyframe
      // 54f5: dup
      // 54f6: fconst_0
      // 54f7: fconst_0
      // 54f8: fconst_0
      // 54f9: fconst_0
      // 54fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 54fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5500: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5503: aastore
      // 5504: dup
      // 5505: bipush 1
      // 5506: new net/minecraft/client/animation/Keyframe
      // 5509: dup
      // 550a: ldc 0.5
      // 550c: ldc -6.0
      // 550e: ldc 5.0
      // 5510: fconst_0
      // 5511: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5514: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5517: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 551a: aastore
      // 551b: dup
      // 551c: bipush 2
      // 551d: new net/minecraft/client/animation/Keyframe
      // 5520: dup
      // 5521: ldc 0.7917
      // 5523: ldc -6.0
      // 5525: ldc 5.0
      // 5527: fconst_0
      // 5528: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 552b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 552e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5531: aastore
      // 5532: dup
      // 5533: bipush 3
      // 5534: new net/minecraft/client/animation/Keyframe
      // 5537: dup
      // 5538: ldc 0.9583
      // 553a: ldc -8.0
      // 553c: ldc -20.0
      // 553e: ldc_w -25.0
      // 5541: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5544: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5547: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 554a: aastore
      // 554b: dup
      // 554c: bipush 4
      // 554d: new net/minecraft/client/animation/Keyframe
      // 5550: dup
      // 5551: ldc 2.2083
      // 5553: ldc -8.0
      // 5555: ldc -20.0
      // 5557: ldc_w -25.0
      // 555a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 555d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5560: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5563: aastore
      // 5564: dup
      // 5565: bipush 5
      // 5566: new net/minecraft/client/animation/Keyframe
      // 5569: dup
      // 556a: ldc_w 2.9583
      // 556d: fconst_0
      // 556e: fconst_0
      // 556f: fconst_0
      // 5570: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5573: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5576: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5579: aastore
      // 557a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 557d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5580: ldc "rightarm2"
      // 5582: new net/minecraft/client/animation/AnimationChannel
      // 5585: dup
      // 5586: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5589: bipush 5
      // 558a: anewarray 28
      // 558d: dup
      // 558e: bipush 0
      // 558f: new net/minecraft/client/animation/Keyframe
      // 5592: dup
      // 5593: fconst_0
      // 5594: fconst_0
      // 5595: fconst_0
      // 5596: fconst_0
      // 5597: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 559a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 559d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 55a0: aastore
      // 55a1: dup
      // 55a2: bipush 1
      // 55a3: new net/minecraft/client/animation/Keyframe
      // 55a6: dup
      // 55a7: ldc 0.5
      // 55a9: ldc 7.5
      // 55ab: fconst_0
      // 55ac: fconst_0
      // 55ad: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 55b0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 55b3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 55b6: aastore
      // 55b7: dup
      // 55b8: bipush 2
      // 55b9: new net/minecraft/client/animation/Keyframe
      // 55bc: dup
      // 55bd: ldc 0.7917
      // 55bf: ldc 7.5
      // 55c1: fconst_0
      // 55c2: fconst_0
      // 55c3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 55c6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 55c9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 55cc: aastore
      // 55cd: dup
      // 55ce: bipush 3
      // 55cf: new net/minecraft/client/animation/Keyframe
      // 55d2: dup
      // 55d3: ldc 2.2083
      // 55d5: ldc 7.5
      // 55d7: fconst_0
      // 55d8: fconst_0
      // 55d9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 55dc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 55df: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 55e2: aastore
      // 55e3: dup
      // 55e4: bipush 4
      // 55e5: new net/minecraft/client/animation/Keyframe
      // 55e8: dup
      // 55e9: ldc_w 2.9583
      // 55ec: fconst_0
      // 55ed: fconst_0
      // 55ee: fconst_0
      // 55ef: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 55f2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 55f5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 55f8: aastore
      // 55f9: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 55fc: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 55ff: ldc "rightarm2"
      // 5601: new net/minecraft/client/animation/AnimationChannel
      // 5604: dup
      // 5605: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5608: bipush 5
      // 5609: anewarray 28
      // 560c: dup
      // 560d: bipush 0
      // 560e: new net/minecraft/client/animation/Keyframe
      // 5611: dup
      // 5612: fconst_0
      // 5613: fconst_0
      // 5614: fconst_0
      // 5615: fconst_0
      // 5616: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5619: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 561c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 561f: aastore
      // 5620: dup
      // 5621: bipush 1
      // 5622: new net/minecraft/client/animation/Keyframe
      // 5625: dup
      // 5626: ldc 0.7917
      // 5628: fconst_0
      // 5629: fconst_0
      // 562a: fconst_0
      // 562b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 562e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5631: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5634: aastore
      // 5635: dup
      // 5636: bipush 2
      // 5637: new net/minecraft/client/animation/Keyframe
      // 563a: dup
      // 563b: fconst_1
      // 563c: fconst_0
      // 563d: ldc_w 6.0
      // 5640: fconst_0
      // 5641: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5644: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5647: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 564a: aastore
      // 564b: dup
      // 564c: bipush 3
      // 564d: new net/minecraft/client/animation/Keyframe
      // 5650: dup
      // 5651: ldc 2.2083
      // 5653: fconst_0
      // 5654: ldc_w 6.0
      // 5657: fconst_0
      // 5658: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 565b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 565e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5661: aastore
      // 5662: dup
      // 5663: bipush 4
      // 5664: new net/minecraft/client/animation/Keyframe
      // 5667: dup
      // 5668: ldc_w 2.9583
      // 566b: fconst_0
      // 566c: fconst_0
      // 566d: fconst_0
      // 566e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5671: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5674: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5677: aastore
      // 5678: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 567b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 567e: ldc "righthand"
      // 5680: new net/minecraft/client/animation/AnimationChannel
      // 5683: dup
      // 5684: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5687: bipush 5
      // 5688: anewarray 28
      // 568b: dup
      // 568c: bipush 0
      // 568d: new net/minecraft/client/animation/Keyframe
      // 5690: dup
      // 5691: fconst_0
      // 5692: fconst_0
      // 5693: fconst_0
      // 5694: fconst_0
      // 5695: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5698: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 569b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 569e: aastore
      // 569f: dup
      // 56a0: bipush 1
      // 56a1: new net/minecraft/client/animation/Keyframe
      // 56a4: dup
      // 56a5: ldc 0.5
      // 56a7: ldc 5.0
      // 56a9: fconst_0
      // 56aa: fconst_0
      // 56ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 56ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 56b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 56b4: aastore
      // 56b5: dup
      // 56b6: bipush 2
      // 56b7: new net/minecraft/client/animation/Keyframe
      // 56ba: dup
      // 56bb: ldc 0.7917
      // 56bd: ldc 5.0
      // 56bf: fconst_0
      // 56c0: fconst_0
      // 56c1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 56c4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 56c7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 56ca: aastore
      // 56cb: dup
      // 56cc: bipush 3
      // 56cd: new net/minecraft/client/animation/Keyframe
      // 56d0: dup
      // 56d1: ldc 2.2083
      // 56d3: ldc 5.0
      // 56d5: fconst_0
      // 56d6: fconst_0
      // 56d7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 56da: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 56dd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 56e0: aastore
      // 56e1: dup
      // 56e2: bipush 4
      // 56e3: new net/minecraft/client/animation/Keyframe
      // 56e6: dup
      // 56e7: ldc_w 2.9583
      // 56ea: fconst_0
      // 56eb: fconst_0
      // 56ec: fconst_0
      // 56ed: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 56f0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 56f3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 56f6: aastore
      // 56f7: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 56fa: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 56fd: ldc "righthand"
      // 56ff: new net/minecraft/client/animation/AnimationChannel
      // 5702: dup
      // 5703: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5706: bipush 6
      // 5708: anewarray 28
      // 570b: dup
      // 570c: bipush 0
      // 570d: new net/minecraft/client/animation/Keyframe
      // 5710: dup
      // 5711: fconst_0
      // 5712: fconst_0
      // 5713: fconst_0
      // 5714: fconst_0
      // 5715: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5718: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 571b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 571e: aastore
      // 571f: dup
      // 5720: bipush 1
      // 5721: new net/minecraft/client/animation/Keyframe
      // 5724: dup
      // 5725: ldc 1.1667
      // 5727: fconst_0
      // 5728: fconst_0
      // 5729: fconst_0
      // 572a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 572d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5730: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5733: aastore
      // 5734: dup
      // 5735: bipush 2
      // 5736: new net/minecraft/client/animation/Keyframe
      // 5739: dup
      // 573a: ldc 1.4167
      // 573c: fconst_0
      // 573d: ldc_w 6.0
      // 5740: fconst_0
      // 5741: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5744: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5747: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 574a: aastore
      // 574b: dup
      // 574c: bipush 3
      // 574d: new net/minecraft/client/animation/Keyframe
      // 5750: dup
      // 5751: ldc 1.6667
      // 5753: fconst_0
      // 5754: fconst_0
      // 5755: fconst_0
      // 5756: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5759: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 575c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 575f: aastore
      // 5760: dup
      // 5761: bipush 4
      // 5762: new net/minecraft/client/animation/Keyframe
      // 5765: dup
      // 5766: ldc 2.2083
      // 5768: fconst_0
      // 5769: ldc 5.0
      // 576b: fconst_0
      // 576c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 576f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5772: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5775: aastore
      // 5776: dup
      // 5777: bipush 5
      // 5778: new net/minecraft/client/animation/Keyframe
      // 577b: dup
      // 577c: ldc_w 2.9583
      // 577f: fconst_0
      // 5780: fconst_0
      // 5781: fconst_0
      // 5782: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5785: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5788: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 578b: aastore
      // 578c: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 578f: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5792: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.build ()Lnet/minecraft/client/animation/AnimationDefinition;
      // 5795: putstatic com/github/L_Ender/cataclysm/client/animation/Netherite_Monstrosity_Animation.DRAIN Lnet/minecraft/client/animation/AnimationDefinition;
      // 5798: ldc 2.5
      // 579a: invokestatic net/minecraft/client/animation/AnimationDefinition$Builder.withLength (F)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 579d: ldc "roots"
      // 579f: new net/minecraft/client/animation/AnimationChannel
      // 57a2: dup
      // 57a3: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 57a6: bipush 5
      // 57a7: anewarray 28
      // 57aa: dup
      // 57ab: bipush 0
      // 57ac: new net/minecraft/client/animation/Keyframe
      // 57af: dup
      // 57b0: fconst_0
      // 57b1: fconst_0
      // 57b2: fconst_0
      // 57b3: fconst_0
      // 57b4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 57b7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 57ba: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 57bd: aastore
      // 57be: dup
      // 57bf: bipush 1
      // 57c0: new net/minecraft/client/animation/Keyframe
      // 57c3: dup
      // 57c4: ldc 1.0417
      // 57c6: fconst_0
      // 57c7: fconst_0
      // 57c8: fconst_0
      // 57c9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 57cc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 57cf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 57d2: aastore
      // 57d3: dup
      // 57d4: bipush 2
      // 57d5: new net/minecraft/client/animation/Keyframe
      // 57d8: dup
      // 57d9: ldc 1.1667
      // 57db: fconst_0
      // 57dc: ldc_w -1.17
      // 57df: fconst_0
      // 57e0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 57e3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 57e6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 57e9: aastore
      // 57ea: dup
      // 57eb: bipush 3
      // 57ec: new net/minecraft/client/animation/Keyframe
      // 57ef: dup
      // 57f0: ldc 1.3333
      // 57f2: fconst_0
      // 57f3: ldc -18.0
      // 57f5: fconst_0
      // 57f6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 57f9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 57fc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 57ff: aastore
      // 5800: dup
      // 5801: bipush 4
      // 5802: new net/minecraft/client/animation/Keyframe
      // 5805: dup
      // 5806: ldc 1.625
      // 5808: fconst_0
      // 5809: ldc_w -17.0
      // 580c: fconst_0
      // 580d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5810: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5813: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5816: aastore
      // 5817: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 581a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 581d: ldc "upperbody"
      // 581f: new net/minecraft/client/animation/AnimationChannel
      // 5822: dup
      // 5823: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5826: bipush 7
      // 5828: anewarray 28
      // 582b: dup
      // 582c: bipush 0
      // 582d: new net/minecraft/client/animation/Keyframe
      // 5830: dup
      // 5831: fconst_0
      // 5832: fconst_0
      // 5833: fconst_0
      // 5834: fconst_0
      // 5835: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5838: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 583b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 583e: aastore
      // 583f: dup
      // 5840: bipush 1
      // 5841: new net/minecraft/client/animation/Keyframe
      // 5844: dup
      // 5845: ldc 0.1667
      // 5847: ldc 8.0
      // 5849: fconst_0
      // 584a: fconst_0
      // 584b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 584e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5851: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5854: aastore
      // 5855: dup
      // 5856: bipush 2
      // 5857: new net/minecraft/client/animation/Keyframe
      // 585a: dup
      // 585b: ldc 0.6667
      // 585d: ldc_w -7.5095
      // 5860: ldc_w 4.9952
      // 5863: ldc_w -0.2187
      // 5866: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5869: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 586c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 586f: aastore
      // 5870: dup
      // 5871: bipush 3
      // 5872: new net/minecraft/client/animation/Keyframe
      // 5875: dup
      // 5876: ldc_w 0.875
      // 5879: ldc_w -9.7127
      // 587c: ldc_w -1.8627
      // 587f: ldc_w 0.3718
      // 5882: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5885: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5888: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 588b: aastore
      // 588c: dup
      // 588d: bipush 4
      // 588e: new net/minecraft/client/animation/Keyframe
      // 5891: dup
      // 5892: ldc 1.0417
      // 5894: ldc_w -7.5095
      // 5897: ldc_w 4.9952
      // 589a: ldc_w -0.2187
      // 589d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 58a0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 58a3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 58a6: aastore
      // 58a7: dup
      // 58a8: bipush 5
      // 58a9: new net/minecraft/client/animation/Keyframe
      // 58ac: dup
      // 58ad: ldc 1.25
      // 58af: ldc 12.5
      // 58b1: fconst_0
      // 58b2: fconst_0
      // 58b3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 58b6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 58b9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 58bc: aastore
      // 58bd: dup
      // 58be: bipush 6
      // 58c0: new net/minecraft/client/animation/Keyframe
      // 58c3: dup
      // 58c4: ldc_w 1.5
      // 58c7: ldc -10.0
      // 58c9: fconst_0
      // 58ca: fconst_0
      // 58cb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 58ce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 58d1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 58d4: aastore
      // 58d5: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 58d8: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 58db: ldc "upperbody"
      // 58dd: new net/minecraft/client/animation/AnimationChannel
      // 58e0: dup
      // 58e1: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 58e4: bipush 4
      // 58e5: anewarray 28
      // 58e8: dup
      // 58e9: bipush 0
      // 58ea: new net/minecraft/client/animation/Keyframe
      // 58ed: dup
      // 58ee: fconst_0
      // 58ef: fconst_0
      // 58f0: fconst_0
      // 58f1: fconst_0
      // 58f2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 58f5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 58f8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 58fb: aastore
      // 58fc: dup
      // 58fd: bipush 1
      // 58fe: new net/minecraft/client/animation/Keyframe
      // 5901: dup
      // 5902: ldc 1.2083
      // 5904: fconst_0
      // 5905: fconst_0
      // 5906: fconst_0
      // 5907: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 590a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 590d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5910: aastore
      // 5911: dup
      // 5912: bipush 2
      // 5913: new net/minecraft/client/animation/Keyframe
      // 5916: dup
      // 5917: ldc 1.3333
      // 5919: fconst_0
      // 591a: ldc -2.0
      // 591c: fconst_0
      // 591d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5920: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5923: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5926: aastore
      // 5927: dup
      // 5928: bipush 3
      // 5929: new net/minecraft/client/animation/Keyframe
      // 592c: dup
      // 592d: ldc 1.625
      // 592f: fconst_0
      // 5930: fconst_0
      // 5931: fconst_0
      // 5932: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5935: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5938: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 593b: aastore
      // 593c: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 593f: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5942: ldc "head"
      // 5944: new net/minecraft/client/animation/AnimationChannel
      // 5947: dup
      // 5948: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 594b: bipush 6
      // 594d: anewarray 28
      // 5950: dup
      // 5951: bipush 0
      // 5952: new net/minecraft/client/animation/Keyframe
      // 5955: dup
      // 5956: fconst_0
      // 5957: fconst_0
      // 5958: fconst_0
      // 5959: fconst_0
      // 595a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 595d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5960: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5963: aastore
      // 5964: dup
      // 5965: bipush 1
      // 5966: new net/minecraft/client/animation/Keyframe
      // 5969: dup
      // 596a: ldc 0.4583
      // 596c: ldc -7.5
      // 596e: fconst_0
      // 596f: fconst_0
      // 5970: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5973: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5976: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5979: aastore
      // 597a: dup
      // 597b: bipush 2
      // 597c: new net/minecraft/client/animation/Keyframe
      // 597f: dup
      // 5980: ldc_w 0.875
      // 5983: ldc_w -22.5
      // 5986: fconst_0
      // 5987: fconst_0
      // 5988: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 598b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 598e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5991: aastore
      // 5992: dup
      // 5993: bipush 3
      // 5994: new net/minecraft/client/animation/Keyframe
      // 5997: dup
      // 5998: ldc 1.0833
      // 599a: ldc_w -22.5
      // 599d: fconst_0
      // 599e: fconst_0
      // 599f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 59a2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 59a5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 59a8: aastore
      // 59a9: dup
      // 59aa: bipush 4
      // 59ab: new net/minecraft/client/animation/Keyframe
      // 59ae: dup
      // 59af: ldc 1.4583
      // 59b1: ldc_w 23.7256
      // 59b4: ldc_w -9.869
      // 59b7: ldc_w -5.5144
      // 59ba: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 59bd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 59c0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 59c3: aastore
      // 59c4: dup
      // 59c5: bipush 5
      // 59c6: new net/minecraft/client/animation/Keyframe
      // 59c9: dup
      // 59ca: ldc_w 1.7917
      // 59cd: ldc_w 23.7256
      // 59d0: ldc_w -9.869
      // 59d3: ldc_w -5.5144
      // 59d6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 59d9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 59dc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 59df: aastore
      // 59e0: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 59e3: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 59e6: ldc "head"
      // 59e8: new net/minecraft/client/animation/AnimationChannel
      // 59eb: dup
      // 59ec: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 59ef: bipush 4
      // 59f0: anewarray 28
      // 59f3: dup
      // 59f4: bipush 0
      // 59f5: new net/minecraft/client/animation/Keyframe
      // 59f8: dup
      // 59f9: fconst_0
      // 59fa: fconst_0
      // 59fb: fconst_0
      // 59fc: fconst_0
      // 59fd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5a00: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a03: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a06: aastore
      // 5a07: dup
      // 5a08: bipush 1
      // 5a09: new net/minecraft/client/animation/Keyframe
      // 5a0c: dup
      // 5a0d: ldc 1.125
      // 5a0f: fconst_0
      // 5a10: fconst_0
      // 5a11: fconst_0
      // 5a12: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5a15: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a18: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a1b: aastore
      // 5a1c: dup
      // 5a1d: bipush 2
      // 5a1e: new net/minecraft/client/animation/Keyframe
      // 5a21: dup
      // 5a22: ldc 1.4167
      // 5a24: fconst_0
      // 5a25: ldc -3.0
      // 5a27: fconst_0
      // 5a28: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5a2b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a2e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a31: aastore
      // 5a32: dup
      // 5a33: bipush 3
      // 5a34: new net/minecraft/client/animation/Keyframe
      // 5a37: dup
      // 5a38: ldc 1.6667
      // 5a3a: fconst_0
      // 5a3b: ldc -2.0
      // 5a3d: fconst_0
      // 5a3e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5a41: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a44: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a47: aastore
      // 5a48: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 5a4b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5a4e: ldc "jaw"
      // 5a50: new net/minecraft/client/animation/AnimationChannel
      // 5a53: dup
      // 5a54: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5a57: bipush 6
      // 5a59: anewarray 28
      // 5a5c: dup
      // 5a5d: bipush 0
      // 5a5e: new net/minecraft/client/animation/Keyframe
      // 5a61: dup
      // 5a62: fconst_0
      // 5a63: fconst_0
      // 5a64: fconst_0
      // 5a65: fconst_0
      // 5a66: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a69: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a6c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a6f: aastore
      // 5a70: dup
      // 5a71: bipush 1
      // 5a72: new net/minecraft/client/animation/Keyframe
      // 5a75: dup
      // 5a76: ldc_w 0.2083
      // 5a79: ldc_w -5.85
      // 5a7c: fconst_0
      // 5a7d: fconst_0
      // 5a7e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a81: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a84: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a87: aastore
      // 5a88: dup
      // 5a89: bipush 2
      // 5a8a: new net/minecraft/client/animation/Keyframe
      // 5a8d: dup
      // 5a8e: ldc 0.4583
      // 5a90: ldc_w 24.9011
      // 5a93: ldc_w -2.6841
      // 5a96: ldc_w 4.22
      // 5a99: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a9c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a9f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5aa2: aastore
      // 5aa3: dup
      // 5aa4: bipush 3
      // 5aa5: new net/minecraft/client/animation/Keyframe
      // 5aa8: dup
      // 5aa9: ldc 0.8333
      // 5aab: ldc_w 28.3882
      // 5aae: ldc_w -3.2722
      // 5ab1: ldc_w -13.7069
      // 5ab4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5ab7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5aba: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5abd: aastore
      // 5abe: dup
      // 5abf: bipush 4
      // 5ac0: new net/minecraft/client/animation/Keyframe
      // 5ac3: dup
      // 5ac4: ldc 1.2083
      // 5ac6: ldc 25.0
      // 5ac8: fconst_0
      // 5ac9: fconst_0
      // 5aca: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5acd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ad0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ad3: aastore
      // 5ad4: dup
      // 5ad5: bipush 5
      // 5ad6: new net/minecraft/client/animation/Keyframe
      // 5ad9: dup
      // 5ada: ldc 1.5833
      // 5adc: ldc_w 23.7348
      // 5adf: ldc_w 13.7408
      // 5ae2: ldc_w -3.2204
      // 5ae5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5ae8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5aeb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5aee: aastore
      // 5aef: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 5af2: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5af5: ldc "leftarm"
      // 5af7: new net/minecraft/client/animation/AnimationChannel
      // 5afa: dup
      // 5afb: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5afe: bipush 5
      // 5aff: anewarray 28
      // 5b02: dup
      // 5b03: bipush 0
      // 5b04: new net/minecraft/client/animation/Keyframe
      // 5b07: dup
      // 5b08: fconst_0
      // 5b09: fconst_0
      // 5b0a: fconst_0
      // 5b0b: fconst_0
      // 5b0c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5b0f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b12: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b15: aastore
      // 5b16: dup
      // 5b17: bipush 1
      // 5b18: new net/minecraft/client/animation/Keyframe
      // 5b1b: dup
      // 5b1c: ldc 0.5417
      // 5b1e: ldc_w 1.1069
      // 5b21: ldc_w -12.4517
      // 5b24: ldc_w -5.1208
      // 5b27: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5b2a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b2d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b30: aastore
      // 5b31: dup
      // 5b32: bipush 2
      // 5b33: new net/minecraft/client/animation/Keyframe
      // 5b36: dup
      // 5b37: ldc 1.1667
      // 5b39: ldc_w 2.7471
      // 5b3c: ldc_w -12.1991
      // 5b3f: ldc_w -12.7936
      // 5b42: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5b45: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b48: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b4b: aastore
      // 5b4c: dup
      // 5b4d: bipush 3
      // 5b4e: new net/minecraft/client/animation/Keyframe
      // 5b51: dup
      // 5b52: ldc 1.4167
      // 5b54: ldc_w 3.7757
      // 5b57: ldc_w -44.6505
      // 5b5a: ldc_w -14.8681
      // 5b5d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5b60: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b63: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b66: aastore
      // 5b67: dup
      // 5b68: bipush 4
      // 5b69: new net/minecraft/client/animation/Keyframe
      // 5b6c: dup
      // 5b6d: ldc 1.6667
      // 5b6f: ldc_w 3.7757
      // 5b72: ldc_w -44.6505
      // 5b75: ldc_w -14.8681
      // 5b78: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5b7b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b7e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b81: aastore
      // 5b82: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 5b85: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5b88: ldc "leftarm"
      // 5b8a: new net/minecraft/client/animation/AnimationChannel
      // 5b8d: dup
      // 5b8e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5b91: bipush 4
      // 5b92: anewarray 28
      // 5b95: dup
      // 5b96: bipush 0
      // 5b97: new net/minecraft/client/animation/Keyframe
      // 5b9a: dup
      // 5b9b: fconst_0
      // 5b9c: fconst_0
      // 5b9d: fconst_0
      // 5b9e: fconst_0
      // 5b9f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5ba2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ba5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ba8: aastore
      // 5ba9: dup
      // 5baa: bipush 1
      // 5bab: new net/minecraft/client/animation/Keyframe
      // 5bae: dup
      // 5baf: ldc 1.0833
      // 5bb1: fconst_0
      // 5bb2: fconst_0
      // 5bb3: fconst_0
      // 5bb4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5bb7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5bba: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5bbd: aastore
      // 5bbe: dup
      // 5bbf: bipush 2
      // 5bc0: new net/minecraft/client/animation/Keyframe
      // 5bc3: dup
      // 5bc4: ldc 1.375
      // 5bc6: fconst_0
      // 5bc7: ldc_w -21.0
      // 5bca: fconst_0
      // 5bcb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5bce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5bd1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5bd4: aastore
      // 5bd5: dup
      // 5bd6: bipush 3
      // 5bd7: new net/minecraft/client/animation/Keyframe
      // 5bda: dup
      // 5bdb: ldc 1.75
      // 5bdd: fconst_0
      // 5bde: ldc_w -14.0
      // 5be1: fconst_0
      // 5be2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5be5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5be8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5beb: aastore
      // 5bec: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 5bef: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5bf2: ldc "leftarm2"
      // 5bf4: new net/minecraft/client/animation/AnimationChannel
      // 5bf7: dup
      // 5bf8: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5bfb: bipush 7
      // 5bfd: anewarray 28
      // 5c00: dup
      // 5c01: bipush 0
      // 5c02: new net/minecraft/client/animation/Keyframe
      // 5c05: dup
      // 5c06: fconst_0
      // 5c07: fconst_0
      // 5c08: fconst_0
      // 5c09: fconst_0
      // 5c0a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5c0d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c10: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c13: aastore
      // 5c14: dup
      // 5c15: bipush 1
      // 5c16: new net/minecraft/client/animation/Keyframe
      // 5c19: dup
      // 5c1a: ldc_w 0.2917
      // 5c1d: fconst_0
      // 5c1e: fconst_0
      // 5c1f: fconst_0
      // 5c20: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5c23: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c26: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c29: aastore
      // 5c2a: dup
      // 5c2b: bipush 2
      // 5c2c: new net/minecraft/client/animation/Keyframe
      // 5c2f: dup
      // 5c30: ldc 0.625
      // 5c32: ldc_w -22.5
      // 5c35: fconst_0
      // 5c36: fconst_0
      // 5c37: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5c3a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c3d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c40: aastore
      // 5c41: dup
      // 5c42: bipush 3
      // 5c43: new net/minecraft/client/animation/Keyframe
      // 5c46: dup
      // 5c47: ldc 1.125
      // 5c49: ldc_w -22.5
      // 5c4c: fconst_0
      // 5c4d: fconst_0
      // 5c4e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5c51: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c54: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c57: aastore
      // 5c58: dup
      // 5c59: bipush 4
      // 5c5a: new net/minecraft/client/animation/Keyframe
      // 5c5d: dup
      // 5c5e: ldc 1.375
      // 5c60: ldc_w -51.02
      // 5c63: fconst_0
      // 5c64: fconst_0
      // 5c65: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5c68: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c6b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c6e: aastore
      // 5c6f: dup
      // 5c70: bipush 5
      // 5c71: new net/minecraft/client/animation/Keyframe
      // 5c74: dup
      // 5c75: ldc 1.6667
      // 5c77: ldc -27.5
      // 5c79: fconst_0
      // 5c7a: fconst_0
      // 5c7b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5c7e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c81: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c84: aastore
      // 5c85: dup
      // 5c86: bipush 6
      // 5c88: new net/minecraft/client/animation/Keyframe
      // 5c8b: dup
      // 5c8c: ldc 1.875
      // 5c8e: ldc -27.5
      // 5c90: fconst_0
      // 5c91: fconst_0
      // 5c92: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5c95: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c98: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c9b: aastore
      // 5c9c: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 5c9f: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5ca2: ldc "lefthand"
      // 5ca4: new net/minecraft/client/animation/AnimationChannel
      // 5ca7: dup
      // 5ca8: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5cab: bipush 3
      // 5cac: anewarray 28
      // 5caf: dup
      // 5cb0: bipush 0
      // 5cb1: new net/minecraft/client/animation/Keyframe
      // 5cb4: dup
      // 5cb5: fconst_0
      // 5cb6: fconst_0
      // 5cb7: fconst_0
      // 5cb8: fconst_0
      // 5cb9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5cbc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5cbf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5cc2: aastore
      // 5cc3: dup
      // 5cc4: bipush 1
      // 5cc5: new net/minecraft/client/animation/Keyframe
      // 5cc8: dup
      // 5cc9: ldc 1.2083
      // 5ccb: fconst_0
      // 5ccc: fconst_0
      // 5ccd: fconst_0
      // 5cce: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5cd1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5cd4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5cd7: aastore
      // 5cd8: dup
      // 5cd9: bipush 2
      // 5cda: new net/minecraft/client/animation/Keyframe
      // 5cdd: dup
      // 5cde: ldc 1.75
      // 5ce0: ldc -15.0
      // 5ce2: fconst_0
      // 5ce3: fconst_0
      // 5ce4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5ce7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5cea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ced: aastore
      // 5cee: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 5cf1: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5cf4: ldc "leftfinger1"
      // 5cf6: new net/minecraft/client/animation/AnimationChannel
      // 5cf9: dup
      // 5cfa: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5cfd: bipush 5
      // 5cfe: anewarray 28
      // 5d01: dup
      // 5d02: bipush 0
      // 5d03: new net/minecraft/client/animation/Keyframe
      // 5d06: dup
      // 5d07: fconst_0
      // 5d08: fconst_0
      // 5d09: fconst_0
      // 5d0a: fconst_0
      // 5d0b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5d0e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d11: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d14: aastore
      // 5d15: dup
      // 5d16: bipush 1
      // 5d17: new net/minecraft/client/animation/Keyframe
      // 5d1a: dup
      // 5d1b: ldc 0.7083
      // 5d1d: ldc 15.0
      // 5d1f: fconst_0
      // 5d20: ldc_w -25.0
      // 5d23: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5d26: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d29: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d2c: aastore
      // 5d2d: dup
      // 5d2e: bipush 2
      // 5d2f: new net/minecraft/client/animation/Keyframe
      // 5d32: dup
      // 5d33: ldc 1.0833
      // 5d35: ldc 15.0
      // 5d37: fconst_0
      // 5d38: ldc_w -25.0
      // 5d3b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5d3e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d41: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d44: aastore
      // 5d45: dup
      // 5d46: bipush 3
      // 5d47: new net/minecraft/client/animation/Keyframe
      // 5d4a: dup
      // 5d4b: ldc 1.625
      // 5d4d: ldc 17.5
      // 5d4f: fconst_0
      // 5d50: fconst_0
      // 5d51: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5d54: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d57: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d5a: aastore
      // 5d5b: dup
      // 5d5c: bipush 4
      // 5d5d: new net/minecraft/client/animation/Keyframe
      // 5d60: dup
      // 5d61: fconst_2
      // 5d62: ldc 17.5
      // 5d64: fconst_0
      // 5d65: fconst_0
      // 5d66: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5d69: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d6c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d6f: aastore
      // 5d70: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 5d73: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5d76: ldc "leftfinger2"
      // 5d78: new net/minecraft/client/animation/AnimationChannel
      // 5d7b: dup
      // 5d7c: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5d7f: bipush 4
      // 5d80: anewarray 28
      // 5d83: dup
      // 5d84: bipush 0
      // 5d85: new net/minecraft/client/animation/Keyframe
      // 5d88: dup
      // 5d89: fconst_0
      // 5d8a: fconst_0
      // 5d8b: fconst_0
      // 5d8c: fconst_0
      // 5d8d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5d90: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d93: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d96: aastore
      // 5d97: dup
      // 5d98: bipush 1
      // 5d99: new net/minecraft/client/animation/Keyframe
      // 5d9c: dup
      // 5d9d: ldc 0.7083
      // 5d9f: ldc -15.0
      // 5da1: fconst_0
      // 5da2: ldc -27.5
      // 5da4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5da7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5daa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5dad: aastore
      // 5dae: dup
      // 5daf: bipush 2
      // 5db0: new net/minecraft/client/animation/Keyframe
      // 5db3: dup
      // 5db4: ldc 1.0833
      // 5db6: ldc -15.0
      // 5db8: fconst_0
      // 5db9: ldc -27.5
      // 5dbb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5dbe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5dc1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5dc4: aastore
      // 5dc5: dup
      // 5dc6: bipush 3
      // 5dc7: new net/minecraft/client/animation/Keyframe
      // 5dca: dup
      // 5dcb: ldc 1.5417
      // 5dcd: ldc_w 42.2835
      // 5dd0: ldc_w 10.8512
      // 5dd3: ldc_w 30.5305
      // 5dd6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5dd9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ddc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ddf: aastore
      // 5de0: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 5de3: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5de6: ldc "leftfinger3"
      // 5de8: new net/minecraft/client/animation/AnimationChannel
      // 5deb: dup
      // 5dec: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5def: bipush 4
      // 5df0: anewarray 28
      // 5df3: dup
      // 5df4: bipush 0
      // 5df5: new net/minecraft/client/animation/Keyframe
      // 5df8: dup
      // 5df9: fconst_0
      // 5dfa: fconst_0
      // 5dfb: fconst_0
      // 5dfc: fconst_0
      // 5dfd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5e00: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e03: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e06: aastore
      // 5e07: dup
      // 5e08: bipush 1
      // 5e09: new net/minecraft/client/animation/Keyframe
      // 5e0c: dup
      // 5e0d: ldc 0.7083
      // 5e0f: fconst_0
      // 5e10: fconst_0
      // 5e11: ldc 25.0
      // 5e13: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5e16: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e19: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e1c: aastore
      // 5e1d: dup
      // 5e1e: bipush 2
      // 5e1f: new net/minecraft/client/animation/Keyframe
      // 5e22: dup
      // 5e23: ldc 1.0833
      // 5e25: fconst_0
      // 5e26: fconst_0
      // 5e27: ldc 25.0
      // 5e29: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5e2c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e2f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e32: aastore
      // 5e33: dup
      // 5e34: bipush 3
      // 5e35: new net/minecraft/client/animation/Keyframe
      // 5e38: dup
      // 5e39: ldc 1.5417
      // 5e3b: ldc_w 56.7259
      // 5e3e: ldc_w -18.5362
      // 5e41: ldc_w 13.8987
      // 5e44: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5e47: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e4a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e4d: aastore
      // 5e4e: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 5e51: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5e54: ldc "rightarm"
      // 5e56: new net/minecraft/client/animation/AnimationChannel
      // 5e59: dup
      // 5e5a: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5e5d: bipush 5
      // 5e5e: anewarray 28
      // 5e61: dup
      // 5e62: bipush 0
      // 5e63: new net/minecraft/client/animation/Keyframe
      // 5e66: dup
      // 5e67: fconst_0
      // 5e68: fconst_0
      // 5e69: fconst_0
      // 5e6a: fconst_0
      // 5e6b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5e6e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e71: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e74: aastore
      // 5e75: dup
      // 5e76: bipush 1
      // 5e77: new net/minecraft/client/animation/Keyframe
      // 5e7a: dup
      // 5e7b: ldc 0.5417
      // 5e7d: ldc_w 1.1069
      // 5e80: ldc_w 12.4517
      // 5e83: ldc_w 5.1208
      // 5e86: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5e89: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e8c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e8f: aastore
      // 5e90: dup
      // 5e91: bipush 2
      // 5e92: new net/minecraft/client/animation/Keyframe
      // 5e95: dup
      // 5e96: ldc 1.1667
      // 5e98: ldc_w 2.7471
      // 5e9b: ldc_w 12.1991
      // 5e9e: ldc_w 12.7936
      // 5ea1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5ea4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ea7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5eaa: aastore
      // 5eab: dup
      // 5eac: bipush 3
      // 5ead: new net/minecraft/client/animation/Keyframe
      // 5eb0: dup
      // 5eb1: ldc 1.4167
      // 5eb3: ldc_w 3.4887
      // 5eb6: ldc_w 39.6605
      // 5eb9: ldc_w 14.4409
      // 5ebc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5ebf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ec2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ec5: aastore
      // 5ec6: dup
      // 5ec7: bipush 4
      // 5ec8: new net/minecraft/client/animation/Keyframe
      // 5ecb: dup
      // 5ecc: ldc 1.6667
      // 5ece: ldc_w 3.4887
      // 5ed1: ldc_w 39.6605
      // 5ed4: ldc_w 14.4409
      // 5ed7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5eda: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5edd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ee0: aastore
      // 5ee1: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 5ee4: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5ee7: ldc "rightarm"
      // 5ee9: new net/minecraft/client/animation/AnimationChannel
      // 5eec: dup
      // 5eed: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5ef0: bipush 4
      // 5ef1: anewarray 28
      // 5ef4: dup
      // 5ef5: bipush 0
      // 5ef6: new net/minecraft/client/animation/Keyframe
      // 5ef9: dup
      // 5efa: fconst_0
      // 5efb: fconst_0
      // 5efc: fconst_0
      // 5efd: fconst_0
      // 5efe: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5f01: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f04: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f07: aastore
      // 5f08: dup
      // 5f09: bipush 1
      // 5f0a: new net/minecraft/client/animation/Keyframe
      // 5f0d: dup
      // 5f0e: ldc 1.0833
      // 5f10: fconst_0
      // 5f11: fconst_0
      // 5f12: fconst_0
      // 5f13: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5f16: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f19: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f1c: aastore
      // 5f1d: dup
      // 5f1e: bipush 2
      // 5f1f: new net/minecraft/client/animation/Keyframe
      // 5f22: dup
      // 5f23: ldc 1.375
      // 5f25: fconst_0
      // 5f26: ldc_w -21.0
      // 5f29: fconst_0
      // 5f2a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5f2d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f30: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f33: aastore
      // 5f34: dup
      // 5f35: bipush 3
      // 5f36: new net/minecraft/client/animation/Keyframe
      // 5f39: dup
      // 5f3a: ldc 1.75
      // 5f3c: fconst_0
      // 5f3d: ldc_w -17.0
      // 5f40: fconst_0
      // 5f41: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5f44: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f47: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f4a: aastore
      // 5f4b: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 5f4e: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5f51: ldc "rightarm2"
      // 5f53: new net/minecraft/client/animation/AnimationChannel
      // 5f56: dup
      // 5f57: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5f5a: bipush 7
      // 5f5c: anewarray 28
      // 5f5f: dup
      // 5f60: bipush 0
      // 5f61: new net/minecraft/client/animation/Keyframe
      // 5f64: dup
      // 5f65: fconst_0
      // 5f66: fconst_0
      // 5f67: fconst_0
      // 5f68: fconst_0
      // 5f69: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5f6c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f6f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f72: aastore
      // 5f73: dup
      // 5f74: bipush 1
      // 5f75: new net/minecraft/client/animation/Keyframe
      // 5f78: dup
      // 5f79: ldc_w 0.2917
      // 5f7c: fconst_0
      // 5f7d: fconst_0
      // 5f7e: fconst_0
      // 5f7f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5f82: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f85: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f88: aastore
      // 5f89: dup
      // 5f8a: bipush 2
      // 5f8b: new net/minecraft/client/animation/Keyframe
      // 5f8e: dup
      // 5f8f: ldc 0.625
      // 5f91: ldc_w -22.5
      // 5f94: fconst_0
      // 5f95: fconst_0
      // 5f96: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5f99: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f9c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f9f: aastore
      // 5fa0: dup
      // 5fa1: bipush 3
      // 5fa2: new net/minecraft/client/animation/Keyframe
      // 5fa5: dup
      // 5fa6: ldc 1.125
      // 5fa8: ldc_w -22.5
      // 5fab: fconst_0
      // 5fac: fconst_0
      // 5fad: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5fb0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5fb3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5fb6: aastore
      // 5fb7: dup
      // 5fb8: bipush 4
      // 5fb9: new net/minecraft/client/animation/Keyframe
      // 5fbc: dup
      // 5fbd: ldc 1.375
      // 5fbf: ldc_w -53.8
      // 5fc2: fconst_0
      // 5fc3: fconst_0
      // 5fc4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5fc7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5fca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5fcd: aastore
      // 5fce: dup
      // 5fcf: bipush 5
      // 5fd0: new net/minecraft/client/animation/Keyframe
      // 5fd3: dup
      // 5fd4: ldc 1.6667
      // 5fd6: ldc_w -35.0
      // 5fd9: fconst_0
      // 5fda: fconst_0
      // 5fdb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5fde: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5fe1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5fe4: aastore
      // 5fe5: dup
      // 5fe6: bipush 6
      // 5fe8: new net/minecraft/client/animation/Keyframe
      // 5feb: dup
      // 5fec: ldc 1.875
      // 5fee: ldc_w -35.0
      // 5ff1: fconst_0
      // 5ff2: fconst_0
      // 5ff3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5ff6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ff9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ffc: aastore
      // 5ffd: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 6000: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 6003: ldc "righthand"
      // 6005: new net/minecraft/client/animation/AnimationChannel
      // 6008: dup
      // 6009: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 600c: bipush 3
      // 600d: anewarray 28
      // 6010: dup
      // 6011: bipush 0
      // 6012: new net/minecraft/client/animation/Keyframe
      // 6015: dup
      // 6016: fconst_0
      // 6017: fconst_0
      // 6018: fconst_0
      // 6019: fconst_0
      // 601a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 601d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6020: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6023: aastore
      // 6024: dup
      // 6025: bipush 1
      // 6026: new net/minecraft/client/animation/Keyframe
      // 6029: dup
      // 602a: ldc 1.2083
      // 602c: fconst_0
      // 602d: fconst_0
      // 602e: fconst_0
      // 602f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6032: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6035: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6038: aastore
      // 6039: dup
      // 603a: bipush 2
      // 603b: new net/minecraft/client/animation/Keyframe
      // 603e: dup
      // 603f: ldc 1.75
      // 6041: ldc -12.5
      // 6043: fconst_0
      // 6044: fconst_0
      // 6045: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6048: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 604b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 604e: aastore
      // 604f: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 6052: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 6055: ldc "rightfinger1"
      // 6057: new net/minecraft/client/animation/AnimationChannel
      // 605a: dup
      // 605b: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 605e: bipush 5
      // 605f: anewarray 28
      // 6062: dup
      // 6063: bipush 0
      // 6064: new net/minecraft/client/animation/Keyframe
      // 6067: dup
      // 6068: fconst_0
      // 6069: fconst_0
      // 606a: fconst_0
      // 606b: fconst_0
      // 606c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 606f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6072: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6075: aastore
      // 6076: dup
      // 6077: bipush 1
      // 6078: new net/minecraft/client/animation/Keyframe
      // 607b: dup
      // 607c: ldc 0.7083
      // 607e: ldc 15.0
      // 6080: fconst_0
      // 6081: ldc 25.0
      // 6083: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6086: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6089: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 608c: aastore
      // 608d: dup
      // 608e: bipush 2
      // 608f: new net/minecraft/client/animation/Keyframe
      // 6092: dup
      // 6093: ldc 1.0833
      // 6095: ldc 15.0
      // 6097: fconst_0
      // 6098: ldc 25.0
      // 609a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 609d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 60a0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 60a3: aastore
      // 60a4: dup
      // 60a5: bipush 3
      // 60a6: new net/minecraft/client/animation/Keyframe
      // 60a9: dup
      // 60aa: ldc 1.625
      // 60ac: ldc 22.5
      // 60ae: fconst_0
      // 60af: fconst_0
      // 60b0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 60b3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 60b6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 60b9: aastore
      // 60ba: dup
      // 60bb: bipush 4
      // 60bc: new net/minecraft/client/animation/Keyframe
      // 60bf: dup
      // 60c0: fconst_2
      // 60c1: ldc 22.5
      // 60c3: fconst_0
      // 60c4: fconst_0
      // 60c5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 60c8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 60cb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 60ce: aastore
      // 60cf: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 60d2: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 60d5: ldc "rightfinger2"
      // 60d7: new net/minecraft/client/animation/AnimationChannel
      // 60da: dup
      // 60db: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 60de: bipush 4
      // 60df: anewarray 28
      // 60e2: dup
      // 60e3: bipush 0
      // 60e4: new net/minecraft/client/animation/Keyframe
      // 60e7: dup
      // 60e8: fconst_0
      // 60e9: fconst_0
      // 60ea: fconst_0
      // 60eb: fconst_0
      // 60ec: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 60ef: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 60f2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 60f5: aastore
      // 60f6: dup
      // 60f7: bipush 1
      // 60f8: new net/minecraft/client/animation/Keyframe
      // 60fb: dup
      // 60fc: ldc 0.7083
      // 60fe: ldc -15.0
      // 6100: fconst_0
      // 6101: ldc 25.0
      // 6103: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6106: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6109: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 610c: aastore
      // 610d: dup
      // 610e: bipush 2
      // 610f: new net/minecraft/client/animation/Keyframe
      // 6112: dup
      // 6113: ldc 1.0833
      // 6115: ldc -15.0
      // 6117: fconst_0
      // 6118: ldc 25.0
      // 611a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 611d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6120: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6123: aastore
      // 6124: dup
      // 6125: bipush 3
      // 6126: new net/minecraft/client/animation/Keyframe
      // 6129: dup
      // 612a: ldc 1.5417
      // 612c: ldc_w 48.4599
      // 612f: ldc_w -15.026
      // 6132: ldc_w -22.5679
      // 6135: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6138: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 613b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 613e: aastore
      // 613f: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 6142: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 6145: ldc "rightfinger3"
      // 6147: new net/minecraft/client/animation/AnimationChannel
      // 614a: dup
      // 614b: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 614e: bipush 4
      // 614f: anewarray 28
      // 6152: dup
      // 6153: bipush 0
      // 6154: new net/minecraft/client/animation/Keyframe
      // 6157: dup
      // 6158: fconst_0
      // 6159: fconst_0
      // 615a: fconst_0
      // 615b: fconst_0
      // 615c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 615f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6162: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6165: aastore
      // 6166: dup
      // 6167: bipush 1
      // 6168: new net/minecraft/client/animation/Keyframe
      // 616b: dup
      // 616c: ldc 0.7083
      // 616e: fconst_0
      // 616f: fconst_0
      // 6170: ldc_w -25.0
      // 6173: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6176: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6179: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 617c: aastore
      // 617d: dup
      // 617e: bipush 2
      // 617f: new net/minecraft/client/animation/Keyframe
      // 6182: dup
      // 6183: ldc 1.0833
      // 6185: fconst_0
      // 6186: fconst_0
      // 6187: ldc_w -25.0
      // 618a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 618d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6190: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6193: aastore
      // 6194: dup
      // 6195: bipush 3
      // 6196: new net/minecraft/client/animation/Keyframe
      // 6199: dup
      // 619a: ldc 1.5417
      // 619c: ldc_w 40.7307
      // 619f: ldc_w 13.3598
      // 61a2: ldc_w -15.0213
      // 61a5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 61a8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 61ab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 61ae: aastore
      // 61af: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 61b2: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 61b5: ldc "rightleg"
      // 61b7: new net/minecraft/client/animation/AnimationChannel
      // 61ba: dup
      // 61bb: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 61be: bipush 4
      // 61bf: anewarray 28
      // 61c2: dup
      // 61c3: bipush 0
      // 61c4: new net/minecraft/client/animation/Keyframe
      // 61c7: dup
      // 61c8: fconst_0
      // 61c9: fconst_0
      // 61ca: fconst_0
      // 61cb: fconst_0
      // 61cc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 61cf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 61d2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 61d5: aastore
      // 61d6: dup
      // 61d7: bipush 1
      // 61d8: new net/minecraft/client/animation/Keyframe
      // 61db: dup
      // 61dc: ldc 0.6667
      // 61de: fconst_0
      // 61df: ldc 7.5
      // 61e1: fconst_0
      // 61e2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 61e5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 61e8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 61eb: aastore
      // 61ec: dup
      // 61ed: bipush 2
      // 61ee: new net/minecraft/client/animation/Keyframe
      // 61f1: dup
      // 61f2: ldc 1.0417
      // 61f4: fconst_0
      // 61f5: fconst_0
      // 61f6: fconst_0
      // 61f7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 61fa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 61fd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6200: aastore
      // 6201: dup
      // 6202: bipush 3
      // 6203: new net/minecraft/client/animation/Keyframe
      // 6206: dup
      // 6207: ldc 1.375
      // 6209: ldc -90.0
      // 620b: ldc 22.5
      // 620d: fconst_0
      // 620e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6211: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6214: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6217: aastore
      // 6218: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 621b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 621e: ldc "leftleg"
      // 6220: new net/minecraft/client/animation/AnimationChannel
      // 6223: dup
      // 6224: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 6227: bipush 5
      // 6228: anewarray 28
      // 622b: dup
      // 622c: bipush 0
      // 622d: new net/minecraft/client/animation/Keyframe
      // 6230: dup
      // 6231: fconst_0
      // 6232: fconst_0
      // 6233: fconst_0
      // 6234: fconst_0
      // 6235: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6238: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 623b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 623e: aastore
      // 623f: dup
      // 6240: bipush 1
      // 6241: new net/minecraft/client/animation/Keyframe
      // 6244: dup
      // 6245: ldc 0.6667
      // 6247: fconst_0
      // 6248: ldc -7.5
      // 624a: fconst_0
      // 624b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 624e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6251: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6254: aastore
      // 6255: dup
      // 6256: bipush 2
      // 6257: new net/minecraft/client/animation/Keyframe
      // 625a: dup
      // 625b: ldc 1.0417
      // 625d: fconst_0
      // 625e: fconst_0
      // 625f: fconst_0
      // 6260: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6263: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6266: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6269: aastore
      // 626a: dup
      // 626b: bipush 3
      // 626c: new net/minecraft/client/animation/Keyframe
      // 626f: dup
      // 6270: ldc 1.1667
      // 6272: ldc_w -60.0
      // 6275: ldc_w -6.25
      // 6278: fconst_0
      // 6279: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 627c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 627f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6282: aastore
      // 6283: dup
      // 6284: bipush 4
      // 6285: new net/minecraft/client/animation/Keyframe
      // 6288: dup
      // 6289: ldc 1.375
      // 628b: ldc -90.0
      // 628d: ldc -12.5
      // 628f: fconst_0
      // 6290: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6293: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6296: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6299: aastore
      // 629a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 629d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 62a0: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.build ()Lnet/minecraft/client/animation/AnimationDefinition;
      // 62a3: putstatic com/github/L_Ender/cataclysm/client/animation/Netherite_Monstrosity_Animation.DEATH Lnet/minecraft/client/animation/AnimationDefinition;
      // 62a6: ldc_w 3.0
      // 62a9: invokestatic net/minecraft/client/animation/AnimationDefinition$Builder.withLength (F)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 62ac: ldc "roots"
      // 62ae: new net/minecraft/client/animation/AnimationChannel
      // 62b1: dup
      // 62b2: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 62b5: bipush 5
      // 62b6: anewarray 28
      // 62b9: dup
      // 62ba: bipush 0
      // 62bb: new net/minecraft/client/animation/Keyframe
      // 62be: dup
      // 62bf: fconst_0
      // 62c0: fconst_0
      // 62c1: fconst_0
      // 62c2: fconst_0
      // 62c3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 62c6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 62c9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 62cc: aastore
      // 62cd: dup
      // 62ce: bipush 1
      // 62cf: new net/minecraft/client/animation/Keyframe
      // 62d2: dup
      // 62d3: ldc 1.7083
      // 62d5: fconst_0
      // 62d6: fconst_0
      // 62d7: fconst_0
      // 62d8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 62db: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 62de: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 62e1: aastore
      // 62e2: dup
      // 62e3: bipush 2
      // 62e4: new net/minecraft/client/animation/Keyframe
      // 62e7: dup
      // 62e8: fconst_2
      // 62e9: fconst_0
      // 62ea: ldc -7.5
      // 62ec: fconst_0
      // 62ed: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 62f0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 62f3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 62f6: aastore
      // 62f7: dup
      // 62f8: bipush 3
      // 62f9: new net/minecraft/client/animation/Keyframe
      // 62fc: dup
      // 62fd: ldc_w 2.375
      // 6300: fconst_0
      // 6301: fconst_0
      // 6302: fconst_0
      // 6303: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6306: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6309: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 630c: aastore
      // 630d: dup
      // 630e: bipush 4
      // 630f: new net/minecraft/client/animation/Keyframe
      // 6312: dup
      // 6313: ldc_w 3.0
      // 6316: fconst_0
      // 6317: fconst_0
      // 6318: fconst_0
      // 6319: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 631c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 631f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6322: aastore
      // 6323: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 6326: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 6329: ldc "roots"
      // 632b: new net/minecraft/client/animation/AnimationChannel
      // 632e: dup
      // 632f: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 6332: bipush 5
      // 6333: anewarray 28
      // 6336: dup
      // 6337: bipush 0
      // 6338: new net/minecraft/client/animation/Keyframe
      // 633b: dup
      // 633c: fconst_0
      // 633d: fconst_0
      // 633e: fconst_0
      // 633f: fconst_0
      // 6340: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6343: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6346: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6349: aastore
      // 634a: dup
      // 634b: bipush 1
      // 634c: new net/minecraft/client/animation/Keyframe
      // 634f: dup
      // 6350: ldc 0.5
      // 6352: fconst_0
      // 6353: fconst_0
      // 6354: fconst_0
      // 6355: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6358: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 635b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 635e: aastore
      // 635f: dup
      // 6360: bipush 2
      // 6361: new net/minecraft/client/animation/Keyframe
      // 6364: dup
      // 6365: ldc 0.625
      // 6367: fconst_0
      // 6368: ldc -2.0
      // 636a: fconst_0
      // 636b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 636e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6371: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6374: aastore
      // 6375: dup
      // 6376: bipush 3
      // 6377: new net/minecraft/client/animation/Keyframe
      // 637a: dup
      // 637b: ldc 0.9583
      // 637d: fconst_0
      // 637e: fconst_0
      // 637f: fconst_0
      // 6380: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6383: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6386: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6389: aastore
      // 638a: dup
      // 638b: bipush 4
      // 638c: new net/minecraft/client/animation/Keyframe
      // 638f: dup
      // 6390: ldc_w 3.0
      // 6393: fconst_0
      // 6394: fconst_0
      // 6395: fconst_0
      // 6396: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6399: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 639c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 639f: aastore
      // 63a0: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 63a3: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 63a6: ldc "lowerbody"
      // 63a8: new net/minecraft/client/animation/AnimationChannel
      // 63ab: dup
      // 63ac: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 63af: bipush 5
      // 63b0: anewarray 28
      // 63b3: dup
      // 63b4: bipush 0
      // 63b5: new net/minecraft/client/animation/Keyframe
      // 63b8: dup
      // 63b9: fconst_0
      // 63ba: fconst_0
      // 63bb: fconst_0
      // 63bc: fconst_0
      // 63bd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 63c0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 63c3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 63c6: aastore
      // 63c7: dup
      // 63c8: bipush 1
      // 63c9: new net/minecraft/client/animation/Keyframe
      // 63cc: dup
      // 63cd: ldc 1.75
      // 63cf: fconst_0
      // 63d0: fconst_0
      // 63d1: fconst_0
      // 63d2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 63d5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 63d8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 63db: aastore
      // 63dc: dup
      // 63dd: bipush 2
      // 63de: new net/minecraft/client/animation/Keyframe
      // 63e1: dup
      // 63e2: ldc 1.8333
      // 63e4: fconst_0
      // 63e5: fconst_0
      // 63e6: ldc_w 4.0
      // 63e9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 63ec: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 63ef: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 63f2: aastore
      // 63f3: dup
      // 63f4: bipush 3
      // 63f5: new net/minecraft/client/animation/Keyframe
      // 63f8: dup
      // 63f9: ldc_w 2.0417
      // 63fc: fconst_0
      // 63fd: fconst_0
      // 63fe: fconst_0
      // 63ff: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6402: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6405: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6408: aastore
      // 6409: dup
      // 640a: bipush 4
      // 640b: new net/minecraft/client/animation/Keyframe
      // 640e: dup
      // 640f: ldc_w 3.0
      // 6412: fconst_0
      // 6413: fconst_0
      // 6414: fconst_0
      // 6415: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6418: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 641b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 641e: aastore
      // 641f: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 6422: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 6425: ldc "upperbody"
      // 6427: new net/minecraft/client/animation/AnimationChannel
      // 642a: dup
      // 642b: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 642e: bipush 7
      // 6430: anewarray 28
      // 6433: dup
      // 6434: bipush 0
      // 6435: new net/minecraft/client/animation/Keyframe
      // 6438: dup
      // 6439: fconst_0
      // 643a: fconst_0
      // 643b: fconst_0
      // 643c: fconst_0
      // 643d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6440: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6443: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6446: aastore
      // 6447: dup
      // 6448: bipush 1
      // 6449: new net/minecraft/client/animation/Keyframe
      // 644c: dup
      // 644d: ldc_w 0.3333
      // 6450: ldc_w 28.627
      // 6453: ldc_w 15.4697
      // 6456: ldc_w 8.2834
      // 6459: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 645c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 645f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6462: aastore
      // 6463: dup
      // 6464: bipush 2
      // 6465: new net/minecraft/client/animation/Keyframe
      // 6468: dup
      // 6469: ldc 0.75
      // 646b: ldc_w 28.627
      // 646e: ldc_w 15.4697
      // 6471: ldc_w 8.2834
      // 6474: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6477: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 647a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 647d: aastore
      // 647e: dup
      // 647f: bipush 3
      // 6480: new net/minecraft/client/animation/Keyframe
      // 6483: dup
      // 6484: ldc_w 1.7917
      // 6487: ldc_w 28.627
      // 648a: ldc_w 15.4697
      // 648d: ldc_w 8.2834
      // 6490: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6493: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6496: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6499: aastore
      // 649a: dup
      // 649b: bipush 4
      // 649c: new net/minecraft/client/animation/Keyframe
      // 649f: dup
      // 64a0: ldc 1.875
      // 64a2: ldc_w 27.5358
      // 64a5: ldc_w 7.0841
      // 64a8: ldc_w 2.7225
      // 64ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 64ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 64b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 64b4: aastore
      // 64b5: dup
      // 64b6: bipush 5
      // 64b7: new net/minecraft/client/animation/Keyframe
      // 64ba: dup
      // 64bb: ldc 2.25
      // 64bd: ldc_w 28.627
      // 64c0: ldc_w 15.4697
      // 64c3: ldc_w 8.2834
      // 64c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 64c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 64cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 64cf: aastore
      // 64d0: dup
      // 64d1: bipush 6
      // 64d3: new net/minecraft/client/animation/Keyframe
      // 64d6: dup
      // 64d7: ldc_w 3.0
      // 64da: fconst_0
      // 64db: fconst_0
      // 64dc: fconst_0
      // 64dd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 64e0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 64e3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 64e6: aastore
      // 64e7: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 64ea: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 64ed: ldc "head"
      // 64ef: new net/minecraft/client/animation/AnimationChannel
      // 64f2: dup
      // 64f3: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 64f6: bipush 4
      // 64f7: anewarray 28
      // 64fa: dup
      // 64fb: bipush 0
      // 64fc: new net/minecraft/client/animation/Keyframe
      // 64ff: dup
      // 6500: fconst_0
      // 6501: fconst_0
      // 6502: fconst_0
      // 6503: fconst_0
      // 6504: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6507: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 650a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 650d: aastore
      // 650e: dup
      // 650f: bipush 1
      // 6510: new net/minecraft/client/animation/Keyframe
      // 6513: dup
      // 6514: ldc 0.7083
      // 6516: ldc_w -23.2433
      // 6519: ldc_w -10.7008
      // 651c: ldc_w -11.6839
      // 651f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6522: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6525: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6528: aastore
      // 6529: dup
      // 652a: bipush 2
      // 652b: new net/minecraft/client/animation/Keyframe
      // 652e: dup
      // 652f: ldc_w 1.5
      // 6532: ldc_w -23.2433
      // 6535: ldc_w -10.7008
      // 6538: ldc_w -11.6839
      // 653b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 653e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6541: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6544: aastore
      // 6545: dup
      // 6546: bipush 3
      // 6547: new net/minecraft/client/animation/Keyframe
      // 654a: dup
      // 654b: ldc_w 3.0
      // 654e: fconst_0
      // 654f: fconst_0
      // 6550: fconst_0
      // 6551: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6554: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6557: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 655a: aastore
      // 655b: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 655e: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 6561: ldc "jaw"
      // 6563: new net/minecraft/client/animation/AnimationChannel
      // 6566: dup
      // 6567: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 656a: bipush 4
      // 656b: anewarray 28
      // 656e: dup
      // 656f: bipush 0
      // 6570: new net/minecraft/client/animation/Keyframe
      // 6573: dup
      // 6574: fconst_0
      // 6575: fconst_0
      // 6576: fconst_0
      // 6577: fconst_0
      // 6578: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 657b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 657e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6581: aastore
      // 6582: dup
      // 6583: bipush 1
      // 6584: new net/minecraft/client/animation/Keyframe
      // 6587: dup
      // 6588: ldc 1.875
      // 658a: ldc_w 27.4106
      // 658d: ldc_w -2.3064
      // 6590: ldc_w 4.4375
      // 6593: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6596: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6599: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 659c: aastore
      // 659d: dup
      // 659e: bipush 2
      // 659f: new net/minecraft/client/animation/Keyframe
      // 65a2: dup
      // 65a3: ldc_w 2.2917
      // 65a6: fconst_0
      // 65a7: fconst_0
      // 65a8: fconst_0
      // 65a9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 65ac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 65af: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 65b2: aastore
      // 65b3: dup
      // 65b4: bipush 3
      // 65b5: new net/minecraft/client/animation/Keyframe
      // 65b8: dup
      // 65b9: ldc_w 3.0
      // 65bc: fconst_0
      // 65bd: fconst_0
      // 65be: fconst_0
      // 65bf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 65c2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 65c5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 65c8: aastore
      // 65c9: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 65cc: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 65cf: ldc "leftarm"
      // 65d1: new net/minecraft/client/animation/AnimationChannel
      // 65d4: dup
      // 65d5: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 65d8: bipush 6
      // 65da: anewarray 28
      // 65dd: dup
      // 65de: bipush 0
      // 65df: new net/minecraft/client/animation/Keyframe
      // 65e2: dup
      // 65e3: fconst_0
      // 65e4: fconst_0
      // 65e5: fconst_0
      // 65e6: fconst_0
      // 65e7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 65ea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 65ed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 65f0: aastore
      // 65f1: dup
      // 65f2: bipush 1
      // 65f3: new net/minecraft/client/animation/Keyframe
      // 65f6: dup
      // 65f7: ldc 0.625
      // 65f9: ldc_w -89.333
      // 65fc: ldc_w -14.5264
      // 65ff: ldc_w -16.6989
      // 6602: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6605: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6608: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 660b: aastore
      // 660c: dup
      // 660d: bipush 2
      // 660e: new net/minecraft/client/animation/Keyframe
      // 6611: dup
      // 6612: ldc 1.6667
      // 6614: ldc_w -89.333
      // 6617: ldc_w -14.5264
      // 661a: ldc_w -16.6989
      // 661d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6620: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6623: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6626: aastore
      // 6627: dup
      // 6628: bipush 3
      // 6629: new net/minecraft/client/animation/Keyframe
      // 662c: dup
      // 662d: ldc 1.875
      // 662f: ldc_w -96.8523
      // 6632: ldc_w -4.527
      // 6635: ldc_w -16.5828
      // 6638: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 663b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 663e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6641: aastore
      // 6642: dup
      // 6643: bipush 4
      // 6644: new net/minecraft/client/animation/Keyframe
      // 6647: dup
      // 6648: ldc 2.25
      // 664a: ldc_w -89.333
      // 664d: ldc_w -14.5264
      // 6650: ldc_w -16.6989
      // 6653: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6656: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6659: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 665c: aastore
      // 665d: dup
      // 665e: bipush 5
      // 665f: new net/minecraft/client/animation/Keyframe
      // 6662: dup
      // 6663: ldc_w 3.0
      // 6666: fconst_0
      // 6667: fconst_0
      // 6668: fconst_0
      // 6669: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 666c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 666f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6672: aastore
      // 6673: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 6676: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 6679: ldc "leftarm"
      // 667b: new net/minecraft/client/animation/AnimationChannel
      // 667e: dup
      // 667f: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 6682: bipush 5
      // 6683: anewarray 28
      // 6686: dup
      // 6687: bipush 0
      // 6688: new net/minecraft/client/animation/Keyframe
      // 668b: dup
      // 668c: fconst_0
      // 668d: fconst_0
      // 668e: fconst_0
      // 668f: fconst_0
      // 6690: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6693: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6696: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6699: aastore
      // 669a: dup
      // 669b: bipush 1
      // 669c: new net/minecraft/client/animation/Keyframe
      // 669f: dup
      // 66a0: ldc 1.6667
      // 66a2: fconst_0
      // 66a3: fconst_0
      // 66a4: fconst_0
      // 66a5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 66a8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 66ab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 66ae: aastore
      // 66af: dup
      // 66b0: bipush 2
      // 66b1: new net/minecraft/client/animation/Keyframe
      // 66b4: dup
      // 66b5: ldc 1.875
      // 66b7: fconst_0
      // 66b8: fconst_0
      // 66b9: ldc_w 9.0
      // 66bc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 66bf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 66c2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 66c5: aastore
      // 66c6: dup
      // 66c7: bipush 3
      // 66c8: new net/minecraft/client/animation/Keyframe
      // 66cb: dup
      // 66cc: ldc 2.2083
      // 66ce: fconst_0
      // 66cf: fconst_0
      // 66d0: fconst_0
      // 66d1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 66d4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 66d7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 66da: aastore
      // 66db: dup
      // 66dc: bipush 4
      // 66dd: new net/minecraft/client/animation/Keyframe
      // 66e0: dup
      // 66e1: ldc_w 3.0
      // 66e4: fconst_0
      // 66e5: fconst_0
      // 66e6: fconst_0
      // 66e7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 66ea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 66ed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 66f0: aastore
      // 66f1: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 66f4: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 66f7: ldc "leftarm2"
      // 66f9: new net/minecraft/client/animation/AnimationChannel
      // 66fc: dup
      // 66fd: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 6700: bipush 6
      // 6702: anewarray 28
      // 6705: dup
      // 6706: bipush 0
      // 6707: new net/minecraft/client/animation/Keyframe
      // 670a: dup
      // 670b: fconst_0
      // 670c: fconst_0
      // 670d: fconst_0
      // 670e: fconst_0
      // 670f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6712: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6715: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6718: aastore
      // 6719: dup
      // 671a: bipush 1
      // 671b: new net/minecraft/client/animation/Keyframe
      // 671e: dup
      // 671f: ldc_w 0.25
      // 6722: fconst_0
      // 6723: fconst_0
      // 6724: fconst_0
      // 6725: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6728: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 672b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 672e: aastore
      // 672f: dup
      // 6730: bipush 2
      // 6731: new net/minecraft/client/animation/Keyframe
      // 6734: dup
      // 6735: ldc 0.75
      // 6737: ldc_w -7.4864
      // 673a: ldc_w -0.6469
      // 673d: ldc_w -2.4149
      // 6740: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6743: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6746: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6749: aastore
      // 674a: dup
      // 674b: bipush 3
      // 674c: new net/minecraft/client/animation/Keyframe
      // 674f: dup
      // 6750: ldc 1.25
      // 6752: ldc_w -7.4864
      // 6755: ldc_w -0.6469
      // 6758: ldc_w -2.4149
      // 675b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 675e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6761: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6764: aastore
      // 6765: dup
      // 6766: bipush 4
      // 6767: new net/minecraft/client/animation/Keyframe
      // 676a: dup
      // 676b: ldc 2.25
      // 676d: ldc_w -7.4864
      // 6770: ldc_w -0.6469
      // 6773: ldc_w -2.4149
      // 6776: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6779: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 677c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 677f: aastore
      // 6780: dup
      // 6781: bipush 5
      // 6782: new net/minecraft/client/animation/Keyframe
      // 6785: dup
      // 6786: ldc_w 3.0
      // 6789: fconst_0
      // 678a: fconst_0
      // 678b: fconst_0
      // 678c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 678f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6792: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6795: aastore
      // 6796: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 6799: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 679c: ldc "lefthand"
      // 679e: new net/minecraft/client/animation/AnimationChannel
      // 67a1: dup
      // 67a2: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 67a5: bipush 3
      // 67a6: anewarray 28
      // 67a9: dup
      // 67aa: bipush 0
      // 67ab: new net/minecraft/client/animation/Keyframe
      // 67ae: dup
      // 67af: fconst_0
      // 67b0: fconst_0
      // 67b1: fconst_0
      // 67b2: fconst_0
      // 67b3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 67b6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 67b9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 67bc: aastore
      // 67bd: dup
      // 67be: bipush 1
      // 67bf: new net/minecraft/client/animation/Keyframe
      // 67c2: dup
      // 67c3: ldc 0.5
      // 67c5: fconst_0
      // 67c6: fconst_0
      // 67c7: fconst_0
      // 67c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 67cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 67ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 67d1: aastore
      // 67d2: dup
      // 67d3: bipush 2
      // 67d4: new net/minecraft/client/animation/Keyframe
      // 67d7: dup
      // 67d8: ldc_w 3.0
      // 67db: fconst_0
      // 67dc: fconst_0
      // 67dd: fconst_0
      // 67de: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 67e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 67e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 67e7: aastore
      // 67e8: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 67eb: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 67ee: ldc "lefthand"
      // 67f0: new net/minecraft/client/animation/AnimationChannel
      // 67f3: dup
      // 67f4: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 67f7: bipush 5
      // 67f8: anewarray 28
      // 67fb: dup
      // 67fc: bipush 0
      // 67fd: new net/minecraft/client/animation/Keyframe
      // 6800: dup
      // 6801: fconst_0
      // 6802: fconst_0
      // 6803: fconst_0
      // 6804: fconst_0
      // 6805: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6808: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 680b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 680e: aastore
      // 680f: dup
      // 6810: bipush 1
      // 6811: new net/minecraft/client/animation/Keyframe
      // 6814: dup
      // 6815: ldc 1.7083
      // 6817: fconst_0
      // 6818: fconst_0
      // 6819: fconst_0
      // 681a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 681d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6820: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6823: aastore
      // 6824: dup
      // 6825: bipush 2
      // 6826: new net/minecraft/client/animation/Keyframe
      // 6829: dup
      // 682a: ldc 1.875
      // 682c: fconst_0
      // 682d: ldc 7.0
      // 682f: fconst_0
      // 6830: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6833: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6836: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6839: aastore
      // 683a: dup
      // 683b: bipush 3
      // 683c: new net/minecraft/client/animation/Keyframe
      // 683f: dup
      // 6840: ldc_w 2.375
      // 6843: fconst_0
      // 6844: fconst_0
      // 6845: fconst_0
      // 6846: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6849: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 684c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 684f: aastore
      // 6850: dup
      // 6851: bipush 4
      // 6852: new net/minecraft/client/animation/Keyframe
      // 6855: dup
      // 6856: ldc_w 3.0
      // 6859: fconst_0
      // 685a: fconst_0
      // 685b: fconst_0
      // 685c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 685f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6862: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6865: aastore
      // 6866: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 6869: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 686c: ldc_w "l_hand_blast_4"
      // 686f: new net/minecraft/client/animation/AnimationChannel
      // 6872: dup
      // 6873: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 6876: bipush 7
      // 6878: anewarray 28
      // 687b: dup
      // 687c: bipush 0
      // 687d: new net/minecraft/client/animation/Keyframe
      // 6880: dup
      // 6881: fconst_0
      // 6882: fconst_0
      // 6883: fconst_0
      // 6884: fconst_0
      // 6885: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6888: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 688b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 688e: aastore
      // 688f: dup
      // 6890: bipush 1
      // 6891: new net/minecraft/client/animation/Keyframe
      // 6894: dup
      // 6895: ldc_w 0.3333
      // 6898: fconst_0
      // 6899: fconst_0
      // 689a: fconst_0
      // 689b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 689e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 68a1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 68a4: aastore
      // 68a5: dup
      // 68a6: bipush 2
      // 68a7: new net/minecraft/client/animation/Keyframe
      // 68aa: dup
      // 68ab: ldc 0.625
      // 68ad: ldc 25.0
      // 68af: fconst_0
      // 68b0: ldc 25.0
      // 68b2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 68b5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 68b8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 68bb: aastore
      // 68bc: dup
      // 68bd: bipush 3
      // 68be: new net/minecraft/client/animation/Keyframe
      // 68c1: dup
      // 68c2: ldc 1.125
      // 68c4: ldc 25.0
      // 68c6: fconst_0
      // 68c7: ldc 25.0
      // 68c9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 68cc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 68cf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 68d2: aastore
      // 68d3: dup
      // 68d4: bipush 4
      // 68d5: new net/minecraft/client/animation/Keyframe
      // 68d8: dup
      // 68d9: ldc 1.6667
      // 68db: ldc_w 36.9776
      // 68de: ldc_w -5.2483
      // 68e1: ldc_w 36.3608
      // 68e4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 68e7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 68ea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 68ed: aastore
      // 68ee: dup
      // 68ef: bipush 5
      // 68f0: new net/minecraft/client/animation/Keyframe
      // 68f3: dup
      // 68f4: ldc 1.875
      // 68f6: fconst_0
      // 68f7: fconst_0
      // 68f8: fconst_0
      // 68f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 68fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 68ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6902: aastore
      // 6903: dup
      // 6904: bipush 6
      // 6906: new net/minecraft/client/animation/Keyframe
      // 6909: dup
      // 690a: ldc_w 3.0
      // 690d: fconst_0
      // 690e: fconst_0
      // 690f: fconst_0
      // 6910: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6913: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6916: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6919: aastore
      // 691a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 691d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 6920: ldc_w "l_hand_blast_3"
      // 6923: new net/minecraft/client/animation/AnimationChannel
      // 6926: dup
      // 6927: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 692a: bipush 7
      // 692c: anewarray 28
      // 692f: dup
      // 6930: bipush 0
      // 6931: new net/minecraft/client/animation/Keyframe
      // 6934: dup
      // 6935: fconst_0
      // 6936: fconst_0
      // 6937: fconst_0
      // 6938: fconst_0
      // 6939: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 693c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 693f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6942: aastore
      // 6943: dup
      // 6944: bipush 1
      // 6945: new net/minecraft/client/animation/Keyframe
      // 6948: dup
      // 6949: ldc_w 0.3333
      // 694c: fconst_0
      // 694d: fconst_0
      // 694e: fconst_0
      // 694f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6952: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6955: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6958: aastore
      // 6959: dup
      // 695a: bipush 2
      // 695b: new net/minecraft/client/animation/Keyframe
      // 695e: dup
      // 695f: ldc 0.625
      // 6961: ldc 25.0
      // 6963: fconst_0
      // 6964: ldc_w -25.0
      // 6967: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 696a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 696d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6970: aastore
      // 6971: dup
      // 6972: bipush 3
      // 6973: new net/minecraft/client/animation/Keyframe
      // 6976: dup
      // 6977: ldc 1.125
      // 6979: ldc 25.0
      // 697b: fconst_0
      // 697c: ldc_w -25.0
      // 697f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6982: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6985: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6988: aastore
      // 6989: dup
      // 698a: bipush 4
      // 698b: new net/minecraft/client/animation/Keyframe
      // 698e: dup
      // 698f: ldc 1.6667
      // 6991: ldc_w 36.8383
      // 6994: ldc_w 7.5713
      // 6997: ldc_w -34.9753
      // 699a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 699d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 69a0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 69a3: aastore
      // 69a4: dup
      // 69a5: bipush 5
      // 69a6: new net/minecraft/client/animation/Keyframe
      // 69a9: dup
      // 69aa: ldc 1.875
      // 69ac: fconst_0
      // 69ad: fconst_0
      // 69ae: fconst_0
      // 69af: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 69b2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 69b5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 69b8: aastore
      // 69b9: dup
      // 69ba: bipush 6
      // 69bc: new net/minecraft/client/animation/Keyframe
      // 69bf: dup
      // 69c0: ldc_w 3.0
      // 69c3: fconst_0
      // 69c4: fconst_0
      // 69c5: fconst_0
      // 69c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 69c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 69cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 69cf: aastore
      // 69d0: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 69d3: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 69d6: ldc "leftfinger1"
      // 69d8: new net/minecraft/client/animation/AnimationChannel
      // 69db: dup
      // 69dc: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 69df: bipush 4
      // 69e0: anewarray 28
      // 69e3: dup
      // 69e4: bipush 0
      // 69e5: new net/minecraft/client/animation/Keyframe
      // 69e8: dup
      // 69e9: fconst_0
      // 69ea: fconst_0
      // 69eb: fconst_0
      // 69ec: fconst_0
      // 69ed: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 69f0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 69f3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 69f6: aastore
      // 69f7: dup
      // 69f8: bipush 1
      // 69f9: new net/minecraft/client/animation/Keyframe
      // 69fc: dup
      // 69fd: ldc 0.8333
      // 69ff: ldc_w -46.7368
      // 6a02: ldc_w -24.3683
      // 6a05: ldc_w 43.7546
      // 6a08: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6a0b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a0e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a11: aastore
      // 6a12: dup
      // 6a13: bipush 2
      // 6a14: new net/minecraft/client/animation/Keyframe
      // 6a17: dup
      // 6a18: ldc 1.25
      // 6a1a: ldc_w -46.7368
      // 6a1d: ldc_w -24.3683
      // 6a20: ldc_w 43.7546
      // 6a23: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6a26: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a29: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a2c: aastore
      // 6a2d: dup
      // 6a2e: bipush 3
      // 6a2f: new net/minecraft/client/animation/Keyframe
      // 6a32: dup
      // 6a33: ldc_w 3.0
      // 6a36: fconst_0
      // 6a37: fconst_0
      // 6a38: fconst_0
      // 6a39: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6a3c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a3f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a42: aastore
      // 6a43: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 6a46: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 6a49: ldc_w "l_hand_blast_2"
      // 6a4c: new net/minecraft/client/animation/AnimationChannel
      // 6a4f: dup
      // 6a50: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 6a53: bipush 7
      // 6a55: anewarray 28
      // 6a58: dup
      // 6a59: bipush 0
      // 6a5a: new net/minecraft/client/animation/Keyframe
      // 6a5d: dup
      // 6a5e: fconst_0
      // 6a5f: fconst_0
      // 6a60: fconst_0
      // 6a61: fconst_0
      // 6a62: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6a65: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a68: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a6b: aastore
      // 6a6c: dup
      // 6a6d: bipush 1
      // 6a6e: new net/minecraft/client/animation/Keyframe
      // 6a71: dup
      // 6a72: ldc_w 0.3333
      // 6a75: fconst_0
      // 6a76: fconst_0
      // 6a77: fconst_0
      // 6a78: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6a7b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a7e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a81: aastore
      // 6a82: dup
      // 6a83: bipush 2
      // 6a84: new net/minecraft/client/animation/Keyframe
      // 6a87: dup
      // 6a88: ldc 0.625
      // 6a8a: ldc_w -25.0
      // 6a8d: fconst_0
      // 6a8e: ldc_w -25.0
      // 6a91: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6a94: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a97: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a9a: aastore
      // 6a9b: dup
      // 6a9c: bipush 3
      // 6a9d: new net/minecraft/client/animation/Keyframe
      // 6aa0: dup
      // 6aa1: ldc 1.125
      // 6aa3: ldc_w -25.0
      // 6aa6: fconst_0
      // 6aa7: ldc_w -25.0
      // 6aaa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6aad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ab0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6ab3: aastore
      // 6ab4: dup
      // 6ab5: bipush 4
      // 6ab6: new net/minecraft/client/animation/Keyframe
      // 6ab9: dup
      // 6aba: ldc 1.6667
      // 6abc: ldc_w -36.9776
      // 6abf: ldc_w -5.2483
      // 6ac2: ldc_w -36.3608
      // 6ac5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6ac8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6acb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6ace: aastore
      // 6acf: dup
      // 6ad0: bipush 5
      // 6ad1: new net/minecraft/client/animation/Keyframe
      // 6ad4: dup
      // 6ad5: ldc 1.875
      // 6ad7: fconst_0
      // 6ad8: fconst_0
      // 6ad9: fconst_0
      // 6ada: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6add: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ae0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6ae3: aastore
      // 6ae4: dup
      // 6ae5: bipush 6
      // 6ae7: new net/minecraft/client/animation/Keyframe
      // 6aea: dup
      // 6aeb: ldc_w 3.0
      // 6aee: fconst_0
      // 6aef: fconst_0
      // 6af0: fconst_0
      // 6af1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6af4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6af7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6afa: aastore
      // 6afb: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 6afe: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 6b01: ldc "leftfinger2"
      // 6b03: new net/minecraft/client/animation/AnimationChannel
      // 6b06: dup
      // 6b07: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 6b0a: bipush 4
      // 6b0b: anewarray 28
      // 6b0e: dup
      // 6b0f: bipush 0
      // 6b10: new net/minecraft/client/animation/Keyframe
      // 6b13: dup
      // 6b14: fconst_0
      // 6b15: fconst_0
      // 6b16: fconst_0
      // 6b17: fconst_0
      // 6b18: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6b1b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6b1e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6b21: aastore
      // 6b22: dup
      // 6b23: bipush 1
      // 6b24: new net/minecraft/client/animation/Keyframe
      // 6b27: dup
      // 6b28: ldc 0.8333
      // 6b2a: ldc_w 34.2368
      // 6b2d: ldc_w 24.3683
      // 6b30: ldc_w 43.7546
      // 6b33: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6b36: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6b39: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6b3c: aastore
      // 6b3d: dup
      // 6b3e: bipush 2
      // 6b3f: new net/minecraft/client/animation/Keyframe
      // 6b42: dup
      // 6b43: ldc 1.25
      // 6b45: ldc_w 34.2368
      // 6b48: ldc_w 24.3683
      // 6b4b: ldc_w 43.7546
      // 6b4e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6b51: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6b54: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6b57: aastore
      // 6b58: dup
      // 6b59: bipush 3
      // 6b5a: new net/minecraft/client/animation/Keyframe
      // 6b5d: dup
      // 6b5e: ldc_w 3.0
      // 6b61: fconst_0
      // 6b62: fconst_0
      // 6b63: fconst_0
      // 6b64: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6b67: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6b6a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6b6d: aastore
      // 6b6e: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 6b71: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 6b74: ldc_w "l_hand_blast_1"
      // 6b77: new net/minecraft/client/animation/AnimationChannel
      // 6b7a: dup
      // 6b7b: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 6b7e: bipush 7
      // 6b80: anewarray 28
      // 6b83: dup
      // 6b84: bipush 0
      // 6b85: new net/minecraft/client/animation/Keyframe
      // 6b88: dup
      // 6b89: fconst_0
      // 6b8a: fconst_0
      // 6b8b: fconst_0
      // 6b8c: fconst_0
      // 6b8d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6b90: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6b93: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6b96: aastore
      // 6b97: dup
      // 6b98: bipush 1
      // 6b99: new net/minecraft/client/animation/Keyframe
      // 6b9c: dup
      // 6b9d: ldc_w 0.3333
      // 6ba0: fconst_0
      // 6ba1: fconst_0
      // 6ba2: fconst_0
      // 6ba3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6ba6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ba9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6bac: aastore
      // 6bad: dup
      // 6bae: bipush 2
      // 6baf: new net/minecraft/client/animation/Keyframe
      // 6bb2: dup
      // 6bb3: ldc 0.625
      // 6bb5: ldc_w -25.0
      // 6bb8: fconst_0
      // 6bb9: ldc 25.0
      // 6bbb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6bbe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6bc1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6bc4: aastore
      // 6bc5: dup
      // 6bc6: bipush 3
      // 6bc7: new net/minecraft/client/animation/Keyframe
      // 6bca: dup
      // 6bcb: ldc 1.125
      // 6bcd: ldc_w -25.0
      // 6bd0: fconst_0
      // 6bd1: ldc 25.0
      // 6bd3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6bd6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6bd9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6bdc: aastore
      // 6bdd: dup
      // 6bde: bipush 4
      // 6bdf: new net/minecraft/client/animation/Keyframe
      // 6be2: dup
      // 6be3: ldc 1.6667
      // 6be5: ldc_w -36.9776
      // 6be8: ldc_w 5.2483
      // 6beb: ldc_w 36.3608
      // 6bee: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6bf1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6bf4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6bf7: aastore
      // 6bf8: dup
      // 6bf9: bipush 5
      // 6bfa: new net/minecraft/client/animation/Keyframe
      // 6bfd: dup
      // 6bfe: ldc 1.875
      // 6c00: fconst_0
      // 6c01: fconst_0
      // 6c02: fconst_0
      // 6c03: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6c06: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6c09: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6c0c: aastore
      // 6c0d: dup
      // 6c0e: bipush 6
      // 6c10: new net/minecraft/client/animation/Keyframe
      // 6c13: dup
      // 6c14: ldc_w 3.0
      // 6c17: fconst_0
      // 6c18: fconst_0
      // 6c19: fconst_0
      // 6c1a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6c1d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6c20: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6c23: aastore
      // 6c24: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 6c27: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 6c2a: ldc "leftfinger3"
      // 6c2c: new net/minecraft/client/animation/AnimationChannel
      // 6c2f: dup
      // 6c30: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 6c33: bipush 4
      // 6c34: anewarray 28
      // 6c37: dup
      // 6c38: bipush 0
      // 6c39: new net/minecraft/client/animation/Keyframe
      // 6c3c: dup
      // 6c3d: fconst_0
      // 6c3e: fconst_0
      // 6c3f: fconst_0
      // 6c40: fconst_0
      // 6c41: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6c44: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6c47: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6c4a: aastore
      // 6c4b: dup
      // 6c4c: bipush 1
      // 6c4d: new net/minecraft/client/animation/Keyframe
      // 6c50: dup
      // 6c51: ldc_w 0.875
      // 6c54: ldc 30.0
      // 6c56: fconst_0
      // 6c57: ldc_w -62.5
      // 6c5a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6c5d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6c60: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6c63: aastore
      // 6c64: dup
      // 6c65: bipush 2
      // 6c66: new net/minecraft/client/animation/Keyframe
      // 6c69: dup
      // 6c6a: ldc 1.25
      // 6c6c: ldc 30.0
      // 6c6e: fconst_0
      // 6c6f: ldc_w -62.5
      // 6c72: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6c75: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6c78: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6c7b: aastore
      // 6c7c: dup
      // 6c7d: bipush 3
      // 6c7e: new net/minecraft/client/animation/Keyframe
      // 6c81: dup
      // 6c82: ldc_w 3.0
      // 6c85: fconst_0
      // 6c86: fconst_0
      // 6c87: fconst_0
      // 6c88: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6c8b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6c8e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6c91: aastore
      // 6c92: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 6c95: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 6c98: ldc_w "l_cannon"
      // 6c9b: new net/minecraft/client/animation/AnimationChannel
      // 6c9e: dup
      // 6c9f: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 6ca2: bipush 4
      // 6ca3: anewarray 28
      // 6ca6: dup
      // 6ca7: bipush 0
      // 6ca8: new net/minecraft/client/animation/Keyframe
      // 6cab: dup
      // 6cac: fconst_0
      // 6cad: fconst_0
      // 6cae: fconst_0
      // 6caf: fconst_0
      // 6cb0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6cb3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6cb6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6cb9: aastore
      // 6cba: dup
      // 6cbb: bipush 1
      // 6cbc: new net/minecraft/client/animation/Keyframe
      // 6cbf: dup
      // 6cc0: ldc 0.4583
      // 6cc2: fconst_0
      // 6cc3: fconst_0
      // 6cc4: fconst_0
      // 6cc5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6cc8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ccb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6cce: aastore
      // 6ccf: dup
      // 6cd0: bipush 2
      // 6cd1: new net/minecraft/client/animation/Keyframe
      // 6cd4: dup
      // 6cd5: ldc 0.7917
      // 6cd7: fconst_0
      // 6cd8: ldc_w -14.0
      // 6cdb: fconst_0
      // 6cdc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6cdf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ce2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6ce5: aastore
      // 6ce6: dup
      // 6ce7: bipush 3
      // 6ce8: new net/minecraft/client/animation/Keyframe
      // 6ceb: dup
      // 6cec: ldc_w 3.0
      // 6cef: fconst_0
      // 6cf0: fconst_0
      // 6cf1: fconst_0
      // 6cf2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6cf5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6cf8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6cfb: aastore
      // 6cfc: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 6cff: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 6d02: ldc_w "l_cannon"
      // 6d05: new net/minecraft/client/animation/AnimationChannel
      // 6d08: dup
      // 6d09: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 6d0c: bipush 4
      // 6d0d: anewarray 28
      // 6d10: dup
      // 6d11: bipush 0
      // 6d12: new net/minecraft/client/animation/Keyframe
      // 6d15: dup
      // 6d16: fconst_0
      // 6d17: dconst_1
      // 6d18: dconst_1
      // 6d19: dconst_1
      // 6d1a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 6d1d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6d20: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6d23: aastore
      // 6d24: dup
      // 6d25: bipush 1
      // 6d26: new net/minecraft/client/animation/Keyframe
      // 6d29: dup
      // 6d2a: ldc 0.5833
      // 6d2c: dconst_0
      // 6d2d: dconst_0
      // 6d2e: dconst_0
      // 6d2f: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 6d32: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6d35: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6d38: aastore
      // 6d39: dup
      // 6d3a: bipush 2
      // 6d3b: new net/minecraft/client/animation/Keyframe
      // 6d3e: dup
      // 6d3f: ldc_w 2.125
      // 6d42: dconst_0
      // 6d43: dconst_0
      // 6d44: dconst_0
      // 6d45: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 6d48: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6d4b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6d4e: aastore
      // 6d4f: dup
      // 6d50: bipush 3
      // 6d51: new net/minecraft/client/animation/Keyframe
      // 6d54: dup
      // 6d55: ldc 2.25
      // 6d57: dconst_1
      // 6d58: dconst_1
      // 6d59: dconst_1
      // 6d5a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 6d5d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6d60: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6d63: aastore
      // 6d64: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 6d67: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 6d6a: ldc_w "l_core"
      // 6d6d: new net/minecraft/client/animation/AnimationChannel
      // 6d70: dup
      // 6d71: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 6d74: bipush 3
      // 6d75: anewarray 28
      // 6d78: dup
      // 6d79: bipush 0
      // 6d7a: new net/minecraft/client/animation/Keyframe
      // 6d7d: dup
      // 6d7e: fconst_0
      // 6d7f: fconst_0
      // 6d80: fconst_0
      // 6d81: fconst_0
      // 6d82: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6d85: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6d88: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6d8b: aastore
      // 6d8c: dup
      // 6d8d: bipush 1
      // 6d8e: new net/minecraft/client/animation/Keyframe
      // 6d91: dup
      // 6d92: ldc 0.5
      // 6d94: fconst_0
      // 6d95: fconst_0
      // 6d96: fconst_0
      // 6d97: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6d9a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6d9d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6da0: aastore
      // 6da1: dup
      // 6da2: bipush 2
      // 6da3: new net/minecraft/client/animation/Keyframe
      // 6da6: dup
      // 6da7: ldc_w 3.0
      // 6daa: ldc_w 1440.0
      // 6dad: ldc_w 1440.0
      // 6db0: fconst_0
      // 6db1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6db4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6db7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6dba: aastore
      // 6dbb: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 6dbe: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 6dc1: ldc_w "l_core"
      // 6dc4: new net/minecraft/client/animation/AnimationChannel
      // 6dc7: dup
      // 6dc8: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 6dcb: bipush 3
      // 6dcc: anewarray 28
      // 6dcf: dup
      // 6dd0: bipush 0
      // 6dd1: new net/minecraft/client/animation/Keyframe
      // 6dd4: dup
      // 6dd5: fconst_0
      // 6dd6: fconst_0
      // 6dd7: fconst_0
      // 6dd8: fconst_0
      // 6dd9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6ddc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ddf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6de2: aastore
      // 6de3: dup
      // 6de4: bipush 1
      // 6de5: new net/minecraft/client/animation/Keyframe
      // 6de8: dup
      // 6de9: ldc 0.5
      // 6deb: fconst_0
      // 6dec: ldc -5.0
      // 6dee: fconst_0
      // 6def: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6df2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6df5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6df8: aastore
      // 6df9: dup
      // 6dfa: bipush 2
      // 6dfb: new net/minecraft/client/animation/Keyframe
      // 6dfe: dup
      // 6dff: ldc_w 3.0
      // 6e02: fconst_0
      // 6e03: fconst_0
      // 6e04: fconst_0
      // 6e05: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6e08: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6e0b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6e0e: aastore
      // 6e0f: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 6e12: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 6e15: ldc_w "l_core"
      // 6e18: new net/minecraft/client/animation/AnimationChannel
      // 6e1b: dup
      // 6e1c: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 6e1f: bipush 5
      // 6e20: anewarray 28
      // 6e23: dup
      // 6e24: bipush 0
      // 6e25: new net/minecraft/client/animation/Keyframe
      // 6e28: dup
      // 6e29: fconst_0
      // 6e2a: dconst_0
      // 6e2b: dconst_0
      // 6e2c: dconst_0
      // 6e2d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 6e30: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6e33: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6e36: aastore
      // 6e37: dup
      // 6e38: bipush 1
      // 6e39: new net/minecraft/client/animation/Keyframe
      // 6e3c: dup
      // 6e3d: ldc 0.5
      // 6e3f: dconst_1
      // 6e40: dconst_1
      // 6e41: dconst_1
      // 6e42: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 6e45: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6e48: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6e4b: aastore
      // 6e4c: dup
      // 6e4d: bipush 2
      // 6e4e: new net/minecraft/client/animation/Keyframe
      // 6e51: dup
      // 6e52: ldc 1.7083
      // 6e54: ldc2_w 3.0
      // 6e57: ldc2_w 3.0
      // 6e5a: ldc2_w 3.0
      // 6e5d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 6e60: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6e63: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6e66: aastore
      // 6e67: dup
      // 6e68: bipush 3
      // 6e69: new net/minecraft/client/animation/Keyframe
      // 6e6c: dup
      // 6e6d: ldc_w 1.7917
      // 6e70: dconst_0
      // 6e71: dconst_0
      // 6e72: dconst_0
      // 6e73: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 6e76: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6e79: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6e7c: aastore
      // 6e7d: dup
      // 6e7e: bipush 4
      // 6e7f: new net/minecraft/client/animation/Keyframe
      // 6e82: dup
      // 6e83: ldc_w 3.0
      // 6e86: dconst_0
      // 6e87: dconst_0
      // 6e88: dconst_0
      // 6e89: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 6e8c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6e8f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6e92: aastore
      // 6e93: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 6e96: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 6e99: ldc "rightarm"
      // 6e9b: new net/minecraft/client/animation/AnimationChannel
      // 6e9e: dup
      // 6e9f: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 6ea2: bipush 5
      // 6ea3: anewarray 28
      // 6ea6: dup
      // 6ea7: bipush 0
      // 6ea8: new net/minecraft/client/animation/Keyframe
      // 6eab: dup
      // 6eac: fconst_0
      // 6ead: fconst_0
      // 6eae: fconst_0
      // 6eaf: fconst_0
      // 6eb0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6eb3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6eb6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6eb9: aastore
      // 6eba: dup
      // 6ebb: bipush 1
      // 6ebc: new net/minecraft/client/animation/Keyframe
      // 6ebf: dup
      // 6ec0: ldc 0.75
      // 6ec2: ldc_w 15.2207
      // 6ec5: ldc_w 9.6559
      // 6ec8: ldc_w 15.113
      // 6ecb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6ece: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ed1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6ed4: aastore
      // 6ed5: dup
      // 6ed6: bipush 2
      // 6ed7: new net/minecraft/client/animation/Keyframe
      // 6eda: dup
      // 6edb: ldc 1.6667
      // 6edd: ldc_w 21.2006
      // 6ee0: ldc_w 23.222
      // 6ee3: ldc_w 30.5946
      // 6ee6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6ee9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6eec: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6eef: aastore
      // 6ef0: dup
      // 6ef1: bipush 3
      // 6ef2: new net/minecraft/client/animation/Keyframe
      // 6ef5: dup
      // 6ef6: ldc 2.25
      // 6ef8: ldc_w 21.2006
      // 6efb: ldc_w 23.222
      // 6efe: ldc_w 30.5946
      // 6f01: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6f04: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6f07: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6f0a: aastore
      // 6f0b: dup
      // 6f0c: bipush 4
      // 6f0d: new net/minecraft/client/animation/Keyframe
      // 6f10: dup
      // 6f11: ldc_w 3.0
      // 6f14: fconst_0
      // 6f15: fconst_0
      // 6f16: fconst_0
      // 6f17: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6f1a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6f1d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6f20: aastore
      // 6f21: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 6f24: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 6f27: ldc "rightarm2"
      // 6f29: new net/minecraft/client/animation/AnimationChannel
      // 6f2c: dup
      // 6f2d: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 6f30: bipush 4
      // 6f31: anewarray 28
      // 6f34: dup
      // 6f35: bipush 0
      // 6f36: new net/minecraft/client/animation/Keyframe
      // 6f39: dup
      // 6f3a: fconst_0
      // 6f3b: fconst_0
      // 6f3c: fconst_0
      // 6f3d: fconst_0
      // 6f3e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6f41: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6f44: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6f47: aastore
      // 6f48: dup
      // 6f49: bipush 1
      // 6f4a: new net/minecraft/client/animation/Keyframe
      // 6f4d: dup
      // 6f4e: ldc_w 0.3333
      // 6f51: fconst_0
      // 6f52: fconst_0
      // 6f53: fconst_0
      // 6f54: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6f57: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6f5a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6f5d: aastore
      // 6f5e: dup
      // 6f5f: bipush 2
      // 6f60: new net/minecraft/client/animation/Keyframe
      // 6f63: dup
      // 6f64: ldc 0.9167
      // 6f66: ldc_w -0.5373
      // 6f69: ldc_w 16.404
      // 6f6c: ldc_w 23.0456
      // 6f6f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6f72: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6f75: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6f78: aastore
      // 6f79: dup
      // 6f7a: bipush 3
      // 6f7b: new net/minecraft/client/animation/Keyframe
      // 6f7e: dup
      // 6f7f: ldc_w 3.0
      // 6f82: fconst_0
      // 6f83: fconst_0
      // 6f84: fconst_0
      // 6f85: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6f88: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6f8b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6f8e: aastore
      // 6f8f: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 6f92: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 6f95: ldc "righthand"
      // 6f97: new net/minecraft/client/animation/AnimationChannel
      // 6f9a: dup
      // 6f9b: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 6f9e: bipush 5
      // 6f9f: anewarray 28
      // 6fa2: dup
      // 6fa3: bipush 0
      // 6fa4: new net/minecraft/client/animation/Keyframe
      // 6fa7: dup
      // 6fa8: fconst_0
      // 6fa9: fconst_0
      // 6faa: fconst_0
      // 6fab: fconst_0
      // 6fac: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6faf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6fb2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6fb5: aastore
      // 6fb6: dup
      // 6fb7: bipush 1
      // 6fb8: new net/minecraft/client/animation/Keyframe
      // 6fbb: dup
      // 6fbc: ldc 0.5
      // 6fbe: ldc_w -22.5
      // 6fc1: fconst_0
      // 6fc2: fconst_0
      // 6fc3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6fc6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6fc9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6fcc: aastore
      // 6fcd: dup
      // 6fce: bipush 2
      // 6fcf: new net/minecraft/client/animation/Keyframe
      // 6fd2: dup
      // 6fd3: ldc 1.6667
      // 6fd5: ldc_w -23.1277
      // 6fd8: ldc_w 19.6834
      // 6fdb: ldc_w -3.6171
      // 6fde: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6fe1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6fe4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6fe7: aastore
      // 6fe8: dup
      // 6fe9: bipush 3
      // 6fea: new net/minecraft/client/animation/Keyframe
      // 6fed: dup
      // 6fee: ldc 2.25
      // 6ff0: ldc_w -23.1277
      // 6ff3: ldc_w 19.6834
      // 6ff6: ldc_w -3.6171
      // 6ff9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6ffc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6fff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7002: aastore
      // 7003: dup
      // 7004: bipush 4
      // 7005: new net/minecraft/client/animation/Keyframe
      // 7008: dup
      // 7009: ldc_w 3.0
      // 700c: fconst_0
      // 700d: fconst_0
      // 700e: fconst_0
      // 700f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7012: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7015: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7018: aastore
      // 7019: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 701c: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 701f: ldc "rightfinger1"
      // 7021: new net/minecraft/client/animation/AnimationChannel
      // 7024: dup
      // 7025: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 7028: bipush 3
      // 7029: anewarray 28
      // 702c: dup
      // 702d: bipush 0
      // 702e: new net/minecraft/client/animation/Keyframe
      // 7031: dup
      // 7032: fconst_0
      // 7033: fconst_0
      // 7034: fconst_0
      // 7035: fconst_0
      // 7036: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7039: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 703c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 703f: aastore
      // 7040: dup
      // 7041: bipush 1
      // 7042: new net/minecraft/client/animation/Keyframe
      // 7045: dup
      // 7046: ldc_w 1.7917
      // 7049: ldc 30.0
      // 704b: fconst_0
      // 704c: ldc 30.0
      // 704e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7051: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7054: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7057: aastore
      // 7058: dup
      // 7059: bipush 2
      // 705a: new net/minecraft/client/animation/Keyframe
      // 705d: dup
      // 705e: ldc_w 3.0
      // 7061: fconst_0
      // 7062: fconst_0
      // 7063: fconst_0
      // 7064: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7067: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 706a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 706d: aastore
      // 706e: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 7071: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 7074: ldc "rightfinger2"
      // 7076: new net/minecraft/client/animation/AnimationChannel
      // 7079: dup
      // 707a: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 707d: bipush 3
      // 707e: anewarray 28
      // 7081: dup
      // 7082: bipush 0
      // 7083: new net/minecraft/client/animation/Keyframe
      // 7086: dup
      // 7087: fconst_0
      // 7088: fconst_0
      // 7089: fconst_0
      // 708a: fconst_0
      // 708b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 708e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7091: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7094: aastore
      // 7095: dup
      // 7096: bipush 1
      // 7097: new net/minecraft/client/animation/Keyframe
      // 709a: dup
      // 709b: ldc_w 1.7917
      // 709e: ldc_w -30.0
      // 70a1: fconst_0
      // 70a2: ldc 30.0
      // 70a4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 70a7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 70aa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 70ad: aastore
      // 70ae: dup
      // 70af: bipush 2
      // 70b0: new net/minecraft/client/animation/Keyframe
      // 70b3: dup
      // 70b4: ldc_w 3.0
      // 70b7: fconst_0
      // 70b8: fconst_0
      // 70b9: fconst_0
      // 70ba: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 70bd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 70c0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 70c3: aastore
      // 70c4: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 70c7: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 70ca: ldc "rightfinger3"
      // 70cc: new net/minecraft/client/animation/AnimationChannel
      // 70cf: dup
      // 70d0: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 70d3: bipush 3
      // 70d4: anewarray 28
      // 70d7: dup
      // 70d8: bipush 0
      // 70d9: new net/minecraft/client/animation/Keyframe
      // 70dc: dup
      // 70dd: fconst_0
      // 70de: fconst_0
      // 70df: fconst_0
      // 70e0: fconst_0
      // 70e1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 70e4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 70e7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 70ea: aastore
      // 70eb: dup
      // 70ec: bipush 1
      // 70ed: new net/minecraft/client/animation/Keyframe
      // 70f0: dup
      // 70f1: ldc_w 1.7917
      // 70f4: fconst_0
      // 70f5: fconst_0
      // 70f6: ldc_w -30.0
      // 70f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 70fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 70ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7102: aastore
      // 7103: dup
      // 7104: bipush 2
      // 7105: new net/minecraft/client/animation/Keyframe
      // 7108: dup
      // 7109: ldc_w 3.0
      // 710c: fconst_0
      // 710d: fconst_0
      // 710e: fconst_0
      // 710f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7112: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7115: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7118: aastore
      // 7119: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 711c: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 711f: ldc "rightleg"
      // 7121: new net/minecraft/client/animation/AnimationChannel
      // 7124: dup
      // 7125: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 7128: bipush 4
      // 7129: anewarray 28
      // 712c: dup
      // 712d: bipush 0
      // 712e: new net/minecraft/client/animation/Keyframe
      // 7131: dup
      // 7132: fconst_0
      // 7133: fconst_0
      // 7134: fconst_0
      // 7135: fconst_0
      // 7136: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7139: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 713c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 713f: aastore
      // 7140: dup
      // 7141: bipush 1
      // 7142: new net/minecraft/client/animation/Keyframe
      // 7145: dup
      // 7146: ldc 0.5
      // 7148: ldc_w 15.5041
      // 714b: ldc_w 14.4775
      // 714e: ldc_w 3.9671
      // 7151: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7154: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7157: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 715a: aastore
      // 715b: dup
      // 715c: bipush 2
      // 715d: new net/minecraft/client/animation/Keyframe
      // 7160: dup
      // 7161: ldc_w 2.375
      // 7164: ldc_w 15.5041
      // 7167: ldc_w 14.4775
      // 716a: ldc_w 3.9671
      // 716d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7170: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7173: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7176: aastore
      // 7177: dup
      // 7178: bipush 3
      // 7179: new net/minecraft/client/animation/Keyframe
      // 717c: dup
      // 717d: ldc_w 3.0
      // 7180: fconst_0
      // 7181: fconst_0
      // 7182: fconst_0
      // 7183: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7186: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7189: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 718c: aastore
      // 718d: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 7190: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 7193: ldc "rightleg"
      // 7195: new net/minecraft/client/animation/AnimationChannel
      // 7198: dup
      // 7199: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 719c: bipush 4
      // 719d: anewarray 28
      // 71a0: dup
      // 71a1: bipush 0
      // 71a2: new net/minecraft/client/animation/Keyframe
      // 71a5: dup
      // 71a6: fconst_0
      // 71a7: fconst_0
      // 71a8: fconst_0
      // 71a9: fconst_0
      // 71aa: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 71ad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 71b0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 71b3: aastore
      // 71b4: dup
      // 71b5: bipush 1
      // 71b6: new net/minecraft/client/animation/Keyframe
      // 71b9: dup
      // 71ba: ldc 0.5
      // 71bc: fconst_0
      // 71bd: fconst_0
      // 71be: ldc_w 9.0
      // 71c1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 71c4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 71c7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 71ca: aastore
      // 71cb: dup
      // 71cc: bipush 2
      // 71cd: new net/minecraft/client/animation/Keyframe
      // 71d0: dup
      // 71d1: ldc_w 2.375
      // 71d4: fconst_0
      // 71d5: fconst_0
      // 71d6: ldc_w 9.0
      // 71d9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 71dc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 71df: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 71e2: aastore
      // 71e3: dup
      // 71e4: bipush 3
      // 71e5: new net/minecraft/client/animation/Keyframe
      // 71e8: dup
      // 71e9: ldc_w 3.0
      // 71ec: fconst_0
      // 71ed: fconst_0
      // 71ee: fconst_0
      // 71ef: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 71f2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 71f5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 71f8: aastore
      // 71f9: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 71fc: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 71ff: ldc "leftleg"
      // 7201: new net/minecraft/client/animation/AnimationChannel
      // 7204: dup
      // 7205: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 7208: bipush 4
      // 7209: anewarray 28
      // 720c: dup
      // 720d: bipush 0
      // 720e: new net/minecraft/client/animation/Keyframe
      // 7211: dup
      // 7212: fconst_0
      // 7213: fconst_0
      // 7214: fconst_0
      // 7215: fconst_0
      // 7216: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7219: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 721c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 721f: aastore
      // 7220: dup
      // 7221: bipush 1
      // 7222: new net/minecraft/client/animation/Keyframe
      // 7225: dup
      // 7226: ldc 0.5
      // 7228: fconst_0
      // 7229: ldc -5.0
      // 722b: fconst_0
      // 722c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 722f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7232: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7235: aastore
      // 7236: dup
      // 7237: bipush 2
      // 7238: new net/minecraft/client/animation/Keyframe
      // 723b: dup
      // 723c: ldc 2.5
      // 723e: fconst_0
      // 723f: ldc -5.0
      // 7241: fconst_0
      // 7242: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7245: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7248: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 724b: aastore
      // 724c: dup
      // 724d: bipush 3
      // 724e: new net/minecraft/client/animation/Keyframe
      // 7251: dup
      // 7252: ldc_w 3.0
      // 7255: fconst_0
      // 7256: fconst_0
      // 7257: fconst_0
      // 7258: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 725b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 725e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7261: aastore
      // 7262: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 7265: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 7268: ldc "leftleg"
      // 726a: new net/minecraft/client/animation/AnimationChannel
      // 726d: dup
      // 726e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 7271: bipush 5
      // 7272: anewarray 28
      // 7275: dup
      // 7276: bipush 0
      // 7277: new net/minecraft/client/animation/Keyframe
      // 727a: dup
      // 727b: fconst_0
      // 727c: fconst_0
      // 727d: fconst_0
      // 727e: fconst_0
      // 727f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7282: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7285: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7288: aastore
      // 7289: dup
      // 728a: bipush 1
      // 728b: new net/minecraft/client/animation/Keyframe
      // 728e: dup
      // 728f: ldc 0.375
      // 7291: fconst_0
      // 7292: ldc_w 6.0
      // 7295: ldc_w -17.0
      // 7298: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 729b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 729e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 72a1: aastore
      // 72a2: dup
      // 72a3: bipush 2
      // 72a4: new net/minecraft/client/animation/Keyframe
      // 72a7: dup
      // 72a8: ldc 0.5417
      // 72aa: fconst_0
      // 72ab: fconst_0
      // 72ac: ldc_w -17.0
      // 72af: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 72b2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 72b5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 72b8: aastore
      // 72b9: dup
      // 72ba: bipush 3
      // 72bb: new net/minecraft/client/animation/Keyframe
      // 72be: dup
      // 72bf: ldc 2.5
      // 72c1: fconst_0
      // 72c2: fconst_0
      // 72c3: ldc_w -17.0
      // 72c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 72c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 72cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 72cf: aastore
      // 72d0: dup
      // 72d1: bipush 4
      // 72d2: new net/minecraft/client/animation/Keyframe
      // 72d5: dup
      // 72d6: ldc_w 3.0
      // 72d9: fconst_0
      // 72da: fconst_0
      // 72db: fconst_0
      // 72dc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 72df: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 72e2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 72e5: aastore
      // 72e6: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 72e9: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 72ec: ldc "roots"
      // 72ee: new net/minecraft/client/animation/AnimationChannel
      // 72f1: dup
      // 72f2: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 72f5: bipush 21
      // 72f7: anewarray 28
      // 72fa: dup
      // 72fb: bipush 0
      // 72fc: new net/minecraft/client/animation/Keyframe
      // 72ff: dup
      // 7300: fconst_0
      // 7301: ldc_w -0.04
      // 7304: ldc_w -0.38
      // 7307: ldc_w -0.07
      // 730a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 730d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7310: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7313: aastore
      // 7314: dup
      // 7315: bipush 1
      // 7316: new net/minecraft/client/animation/Keyframe
      // 7319: dup
      // 731a: ldc 1.0417
      // 731c: ldc_w -0.04
      // 731f: ldc_w -0.38
      // 7322: ldc_w -0.07
      // 7325: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7328: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 732b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 732e: aastore
      // 732f: dup
      // 7330: bipush 2
      // 7331: new net/minecraft/client/animation/Keyframe
      // 7334: dup
      // 7335: ldc 1.0833
      // 7337: ldc_w -0.04
      // 733a: ldc_w -0.33
      // 733d: fconst_1
      // 733e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7341: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7344: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7347: aastore
      // 7348: dup
      // 7349: bipush 3
      // 734a: new net/minecraft/client/animation/Keyframe
      // 734d: dup
      // 734e: ldc 1.125
      // 7350: ldc_w 0.96
      // 7353: ldc_w -0.28
      // 7356: ldc_w 0.83
      // 7359: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 735c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 735f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7362: aastore
      // 7363: dup
      // 7364: bipush 4
      // 7365: new net/minecraft/client/animation/Keyframe
      // 7368: dup
      // 7369: ldc 1.1667
      // 736b: ldc_w -0.24
      // 736e: ldc_w -0.23
      // 7371: ldc_w 0.66
      // 7374: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7377: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 737a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 737d: aastore
      // 737e: dup
      // 737f: bipush 5
      // 7380: new net/minecraft/client/animation/Keyframe
      // 7383: dup
      // 7384: ldc 1.2083
      // 7386: ldc_w -0.19
      // 7389: ldc_w -0.18
      // 738c: ldc_w -0.51
      // 738f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7392: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7395: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7398: aastore
      // 7399: dup
      // 739a: bipush 6
      // 739c: new net/minecraft/client/animation/Keyframe
      // 739f: dup
      // 73a0: ldc 1.25
      // 73a2: ldc_w -0.14
      // 73a5: ldc_w -0.14
      // 73a8: ldc_w 0.66
      // 73ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 73ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 73b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 73b4: aastore
      // 73b5: dup
      // 73b6: bipush 7
      // 73b8: new net/minecraft/client/animation/Keyframe
      // 73bb: dup
      // 73bc: ldc 1.2917
      // 73be: ldc_w 0.9
      // 73c1: ldc_w -0.1
      // 73c4: ldc_w 0.33
      // 73c7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 73ca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 73cd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 73d0: aastore
      // 73d1: dup
      // 73d2: bipush 8
      // 73d4: new net/minecraft/client/animation/Keyframe
      // 73d7: dup
      // 73d8: ldc 1.3333
      // 73da: ldc_w -0.05
      // 73dd: ldc_w -0.05
      // 73e0: fconst_0
      // 73e1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 73e4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 73e7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 73ea: aastore
      // 73eb: dup
      // 73ec: bipush 9
      // 73ee: new net/minecraft/client/animation/Keyframe
      // 73f1: dup
      // 73f2: ldc 1.375
      // 73f4: fconst_1
      // 73f5: fconst_0
      // 73f6: fconst_0
      // 73f7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 73fa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 73fd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7400: aastore
      // 7401: dup
      // 7402: bipush 10
      // 7404: new net/minecraft/client/animation/Keyframe
      // 7407: dup
      // 7408: ldc 1.4167
      // 740a: ldc_w -0.07
      // 740d: fconst_0
      // 740e: fconst_0
      // 740f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7412: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7415: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7418: aastore
      // 7419: dup
      // 741a: bipush 11
      // 741c: new net/minecraft/client/animation/Keyframe
      // 741f: dup
      // 7420: ldc 1.4583
      // 7422: ldc_w -0.06
      // 7425: fconst_0
      // 7426: fconst_1
      // 7427: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 742a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 742d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7430: aastore
      // 7431: dup
      // 7432: bipush 12
      // 7434: new net/minecraft/client/animation/Keyframe
      // 7437: dup
      // 7438: ldc_w 1.5
      // 743b: ldc_w -0.06
      // 743e: fconst_0
      // 743f: ldc_w -0.08
      // 7442: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7445: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7448: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 744b: aastore
      // 744c: dup
      // 744d: bipush 13
      // 744f: new net/minecraft/client/animation/Keyframe
      // 7452: dup
      // 7453: ldc 1.5417
      // 7455: ldc_w -1.05
      // 7458: fconst_0
      // 7459: ldc_w -0.07
      // 745c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 745f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7462: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7465: aastore
      // 7466: dup
      // 7467: bipush 14
      // 7469: new net/minecraft/client/animation/Keyframe
      // 746c: dup
      // 746d: ldc 1.5833
      // 746f: ldc_w -0.95
      // 7472: fconst_0
      // 7473: ldc_w -1.06
      // 7476: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7479: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 747c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 747f: aastore
      // 7480: dup
      // 7481: bipush 15
      // 7483: new net/minecraft/client/animation/Keyframe
      // 7486: dup
      // 7487: ldc 1.625
      // 7489: ldc_w -0.85
      // 748c: fconst_0
      // 748d: ldc_w 0.05
      // 7490: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7493: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7496: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7499: aastore
      // 749a: dup
      // 749b: bipush 16
      // 749d: new net/minecraft/client/animation/Keyframe
      // 74a0: dup
      // 74a1: ldc 1.6667
      // 74a3: ldc_w 0.24
      // 74a6: fconst_0
      // 74a7: ldc_w 0.04
      // 74aa: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 74ad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 74b0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 74b3: aastore
      // 74b4: dup
      // 74b5: bipush 17
      // 74b7: new net/minecraft/client/animation/Keyframe
      // 74ba: dup
      // 74bb: ldc 1.7083
      // 74bd: ldc_w -1.79
      // 74c0: fconst_0
      // 74c1: ldc_w 0.04
      // 74c4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 74c7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 74ca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 74cd: aastore
      // 74ce: dup
      // 74cf: bipush 18
      // 74d1: new net/minecraft/client/animation/Keyframe
      // 74d4: dup
      // 74d5: fconst_2
      // 74d6: ldc_w -1.62
      // 74d9: ldc_w -0.07
      // 74dc: ldc_w 11.02
      // 74df: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 74e2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 74e5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 74e8: aastore
      // 74e9: dup
      // 74ea: bipush 19
      // 74ec: new net/minecraft/client/animation/Keyframe
      // 74ef: dup
      // 74f0: ldc 2.5
      // 74f2: ldc_w -1.79
      // 74f5: fconst_0
      // 74f6: ldc_w 0.04
      // 74f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 74fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 74ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7502: aastore
      // 7503: dup
      // 7504: bipush 20
      // 7506: new net/minecraft/client/animation/Keyframe
      // 7509: dup
      // 750a: ldc_w 3.0
      // 750d: ldc_w -0.04
      // 7510: ldc_w -0.38
      // 7513: ldc_w -0.07
      // 7516: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7519: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 751c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 751f: aastore
      // 7520: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 7523: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 7526: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.build ()Lnet/minecraft/client/animation/AnimationDefinition;
      // 7529: putstatic com/github/L_Ender/cataclysm/client/animation/Netherite_Monstrosity_Animation.FLARE_SHOT Lnet/minecraft/client/animation/AnimationDefinition;
      // 752c: return
   }
}
