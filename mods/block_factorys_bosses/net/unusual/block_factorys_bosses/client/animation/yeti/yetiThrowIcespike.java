package net.unusual.block_factorys_bosses.client.animation.yeti;

import net.minecraft.client.animation.AnimationDefinition;

public class yetiThrowIcespike {
   public static final AnimationDefinition THROW_ICESPIKE;

   static {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 0000: ldc 2.83
      // 0002: invokestatic net/minecraft/client/animation/AnimationDefinition$Builder.withLength (F)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0005: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.looping ()Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0008: ldc "CTR_leg_left.002"
      // 000a: new net/minecraft/client/animation/AnimationChannel
      // 000d: dup
      // 000e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0011: bipush 12
      // 0013: anewarray 28
      // 0016: dup
      // 0017: bipush 0
      // 0018: new net/minecraft/client/animation/Keyframe
      // 001b: dup
      // 001c: ldc 1.33
      // 001e: ldc 2.113
      // 0020: fconst_0
      // 0021: ldc -13.058
      // 0023: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0026: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0029: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 002c: aastore
      // 002d: dup
      // 002e: bipush 1
      // 002f: new net/minecraft/client/animation/Keyframe
      // 0032: dup
      // 0033: ldc 1.38
      // 0035: ldc 3.015
      // 0037: fconst_0
      // 0038: ldc -12.079
      // 003a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 003d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0040: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0043: aastore
      // 0044: dup
      // 0045: bipush 2
      // 0046: new net/minecraft/client/animation/Keyframe
      // 0049: dup
      // 004a: ldc 1.46
      // 004c: ldc 6.982
      // 004e: fconst_0
      // 004f: ldc -7.773
      // 0051: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0054: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0057: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 005a: aastore
      // 005b: dup
      // 005c: bipush 3
      // 005d: new net/minecraft/client/animation/Keyframe
      // 0060: dup
      // 0061: ldc 1.5
      // 0063: ldc 7.884
      // 0065: fconst_0
      // 0066: ldc -6.794
      // 0068: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 006b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 006e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0071: aastore
      // 0072: dup
      // 0073: bipush 4
      // 0074: new net/minecraft/client/animation/Keyframe
      // 0077: dup
      // 0078: ldc 2.25
      // 007a: ldc 7.884
      // 007c: fconst_0
      // 007d: ldc -6.794
      // 007f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0082: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0085: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0088: aastore
      // 0089: dup
      // 008a: bipush 5
      // 008b: new net/minecraft/client/animation/Keyframe
      // 008e: dup
      // 008f: ldc 2.29
      // 0091: ldc 7.564
      // 0093: fconst_0
      // 0094: ldc -7.141
      // 0096: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0099: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 009c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 009f: aastore
      // 00a0: dup
      // 00a1: bipush 6
      // 00a3: new net/minecraft/client/animation/Keyframe
      // 00a6: dup
      // 00a7: ldc 2.33
      // 00a9: ldc 6.74
      // 00ab: fconst_0
      // 00ac: ldc -8.036
      // 00ae: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 00b1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00b4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00b7: aastore
      // 00b8: dup
      // 00b9: bipush 7
      // 00bb: new net/minecraft/client/animation/Keyframe
      // 00be: dup
      // 00bf: ldc 2.38
      // 00c1: ldc 5.612
      // 00c3: fconst_0
      // 00c4: ldc -9.259
      // 00c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 00c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00cf: aastore
      // 00d0: dup
      // 00d1: bipush 8
      // 00d3: new net/minecraft/client/animation/Keyframe
      // 00d6: dup
      // 00d7: ldc 2.42
      // 00d9: ldc 4.384
      // 00db: fconst_0
      // 00dc: ldc -10.593
      // 00de: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 00e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00e7: aastore
      // 00e8: dup
      // 00e9: bipush 9
      // 00eb: new net/minecraft/client/animation/Keyframe
      // 00ee: dup
      // 00ef: ldc 2.46
      // 00f1: ldc 3.257
      // 00f3: fconst_0
      // 00f4: ldc -11.816
      // 00f6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 00f9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00fc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00ff: aastore
      // 0100: dup
      // 0101: bipush 10
      // 0103: new net/minecraft/client/animation/Keyframe
      // 0106: dup
      // 0107: ldc 2.5
      // 0109: ldc 2.433
      // 010b: fconst_0
      // 010c: ldc -12.711
      // 010e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0111: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0114: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0117: aastore
      // 0118: dup
      // 0119: bipush 11
      // 011b: new net/minecraft/client/animation/Keyframe
      // 011e: dup
      // 011f: ldc 2.54
      // 0121: ldc 2.113
      // 0123: fconst_0
      // 0124: ldc -13.058
      // 0126: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0129: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 012c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 012f: aastore
      // 0130: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0133: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0136: ldc "CTR_hips"
      // 0138: new net/minecraft/client/animation/AnimationChannel
      // 013b: dup
      // 013c: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 013f: bipush 66
      // 0141: anewarray 28
      // 0144: dup
      // 0145: bipush 0
      // 0146: new net/minecraft/client/animation/Keyframe
      // 0149: dup
      // 014a: fconst_0
      // 014b: ldc -0.727
      // 014d: ldc 0.028
      // 014f: ldc -4.943
      // 0151: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0154: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0157: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 015a: aastore
      // 015b: dup
      // 015c: bipush 1
      // 015d: new net/minecraft/client/animation/Keyframe
      // 0160: dup
      // 0161: ldc 0.04
      // 0163: ldc -0.812
      // 0165: ldc 0.303
      // 0167: ldc -4.807
      // 0169: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 016c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 016f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0172: aastore
      // 0173: dup
      // 0174: bipush 2
      // 0175: new net/minecraft/client/animation/Keyframe
      // 0178: dup
      // 0179: ldc 0.08
      // 017b: ldc -1.083
      // 017d: ldc 1.201
      // 017f: ldc -4.371
      // 0181: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0184: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0187: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 018a: aastore
      // 018b: dup
      // 018c: bipush 3
      // 018d: new net/minecraft/client/animation/Keyframe
      // 0190: dup
      // 0191: ldc 0.12
      // 0193: ldc -1.554
      // 0195: ldc 2.849
      // 0197: ldc -3.584
      // 0199: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 019c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 019f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01a2: aastore
      // 01a3: dup
      // 01a4: bipush 4
      // 01a5: new net/minecraft/client/animation/Keyframe
      // 01a8: dup
      // 01a9: ldc 0.17
      // 01ab: ldc -2.209
      // 01ad: ldc 5.405
      // 01af: ldc -2.389
      // 01b1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 01b4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01b7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01ba: aastore
      // 01bb: dup
      // 01bc: bipush 5
      // 01bd: new net/minecraft/client/animation/Keyframe
      // 01c0: dup
      // 01c1: ldc 0.21
      // 01c3: ldc -2.984
      // 01c5: ldc 9.044
      // 01c7: ldc -0.72
      // 01c9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 01cc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01cf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01d2: aastore
      // 01d3: dup
      // 01d4: bipush 6
      // 01d6: new net/minecraft/client/animation/Keyframe
      // 01d9: dup
      // 01da: ldc 0.25
      // 01dc: ldc -3.728
      // 01de: ldc 13.924
      // 01e0: ldc 1.477
      // 01e2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 01e5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01e8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01eb: aastore
      // 01ec: dup
      // 01ed: bipush 7
      // 01ef: new net/minecraft/client/animation/Keyframe
      // 01f2: dup
      // 01f3: ldc 0.29
      // 01f5: ldc -4.185
      // 01f7: ldc 20.103
      // 01f9: ldc 4.21
      // 01fb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 01fe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0201: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0204: aastore
      // 0205: dup
      // 0206: bipush 8
      // 0208: new net/minecraft/client/animation/Keyframe
      // 020b: dup
      // 020c: ldc 0.33
      // 020e: ldc -4.027
      // 0210: ldc 27.401
      // 0212: ldc 7.39
      // 0214: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0217: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 021a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 021d: aastore
      // 021e: dup
      // 021f: bipush 9
      // 0221: new net/minecraft/client/animation/Keyframe
      // 0224: dup
      // 0225: ldc 0.38
      // 0227: ldc -3.035
      // 0229: ldc 35.311
      // 022b: ldc 10.838
      // 022d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0230: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0233: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0236: aastore
      // 0237: dup
      // 0238: bipush 10
      // 023a: new net/minecraft/client/animation/Keyframe
      // 023d: dup
      // 023e: ldc 0.42
      // 0240: ldc -1.279
      // 0242: ldc 43.153
      // 0244: ldc 14.339
      // 0246: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0249: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 024c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 024f: aastore
      // 0250: dup
      // 0251: bipush 11
      // 0253: new net/minecraft/client/animation/Keyframe
      // 0256: dup
      // 0257: ldc 0.46
      // 0259: ldc 0.887
      // 025b: ldc 50.388
      // 025d: ldc 17.659
      // 025f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0262: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0265: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0268: aastore
      // 0269: dup
      // 026a: bipush 12
      // 026c: new net/minecraft/client/animation/Keyframe
      // 026f: dup
      // 0270: ldc 0.5
      // 0272: ldc 2.927
      // 0274: ldc 55.598
      // 0276: ldc 20.429
      // 0278: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 027b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 027e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0281: aastore
      // 0282: dup
      // 0283: bipush 13
      // 0285: new net/minecraft/client/animation/Keyframe
      // 0288: dup
      // 0289: ldc 0.54
      // 028b: ldc 4.021
      // 028d: ldc 58.051
      // 028f: ldc 21.867
      // 0291: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0294: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0297: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 029a: aastore
      // 029b: dup
      // 029c: bipush 14
      // 029e: new net/minecraft/client/animation/Keyframe
      // 02a1: dup
      // 02a2: ldc 0.58
      // 02a4: ldc 3.818
      // 02a6: ldc 58.426
      // 02a8: ldc 21.681
      // 02aa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02ad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02b0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02b3: aastore
      // 02b4: dup
      // 02b5: bipush 15
      // 02b7: new net/minecraft/client/animation/Keyframe
      // 02ba: dup
      // 02bb: ldc 0.62
      // 02bd: ldc 2.785
      // 02bf: ldc 57.33
      // 02c1: ldc 20.435
      // 02c3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02c6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02c9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02cc: aastore
      // 02cd: dup
      // 02ce: bipush 16
      // 02d0: new net/minecraft/client/animation/Keyframe
      // 02d3: dup
      // 02d4: ldc 0.67
      // 02d6: ldc 1.637
      // 02d8: ldc 55.355
      // 02da: ldc 18.958
      // 02dc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02df: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02e2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02e5: aastore
      // 02e6: dup
      // 02e7: bipush 17
      // 02e9: new net/minecraft/client/animation/Keyframe
      // 02ec: dup
      // 02ed: ldc 0.71
      // 02ef: ldc 0.833
      // 02f1: ldc 53.117
      // 02f3: ldc 17.829
      // 02f5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02f8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02fb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02fe: aastore
      // 02ff: dup
      // 0300: bipush 18
      // 0302: new net/minecraft/client/animation/Keyframe
      // 0305: dup
      // 0306: ldc 0.75
      // 0308: ldc 0.551
      // 030a: ldc 51.249
      // 030c: ldc 17.334
      // 030e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0311: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0314: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0317: aastore
      // 0318: dup
      // 0319: bipush 19
      // 031b: new net/minecraft/client/animation/Keyframe
      // 031e: dup
      // 031f: ldc 0.79
      // 0321: ldc 0.887
      // 0323: ldc 50.388
      // 0325: ldc 17.659
      // 0327: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 032a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 032d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0330: aastore
      // 0331: dup
      // 0332: bipush 20
      // 0334: new net/minecraft/client/animation/Keyframe
      // 0337: dup
      // 0338: ldc 0.83
      // 033a: ldc 1.314
      // 033c: ldc 50.976
      // 033e: ldc 18.218
      // 0340: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0343: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0346: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0349: aastore
      // 034a: dup
      // 034b: bipush 21
      // 034d: new net/minecraft/client/animation/Keyframe
      // 0350: dup
      // 0351: ldc 0.88
      // 0353: ldc 1.263
      // 0355: ldc 52.854
      // 0357: ldc 18.347
      // 0359: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 035c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 035f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0362: aastore
      // 0363: dup
      // 0364: bipush 22
      // 0366: new net/minecraft/client/animation/Keyframe
      // 0369: dup
      // 036a: ldc 0.92
      // 036c: ldc 0.762
      // 036e: ldc 55.748
      // 0370: ldc 18.127
      // 0372: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0375: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0378: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 037b: aastore
      // 037c: dup
      // 037d: bipush 23
      // 037f: new net/minecraft/client/animation/Keyframe
      // 0382: dup
      // 0383: ldc 0.96
      // 0385: ldc -0.234
      // 0387: ldc 59.381
      // 0389: ldc 17.572
      // 038b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 038e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0391: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0394: aastore
      // 0395: dup
      // 0396: bipush 24
      // 0398: new net/minecraft/client/animation/Keyframe
      // 039b: dup
      // 039c: fconst_1
      // 039d: ldc -1.839
      // 039f: ldc 63.471
      // 03a1: ldc 16.61
      // 03a3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03a6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03a9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03ac: aastore
      // 03ad: dup
      // 03ae: bipush 25
      // 03b0: new net/minecraft/client/animation/Keyframe
      // 03b3: dup
      // 03b4: ldc 1.04
      // 03b6: ldc -4.313
      // 03b8: ldc 67.725
      // 03ba: ldc 14.995
      // 03bc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03bf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03c2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03c5: aastore
      // 03c6: dup
      // 03c7: bipush 26
      // 03c9: new net/minecraft/client/animation/Keyframe
      // 03cc: dup
      // 03cd: ldc 1.08
      // 03cf: ldc -8.275
      // 03d1: ldc 71.843
      // 03d3: ldc 12.085
      // 03d5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03d8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03db: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03de: aastore
      // 03df: dup
      // 03e0: bipush 27
      // 03e2: new net/minecraft/client/animation/Keyframe
      // 03e5: dup
      // 03e6: ldc 1.12
      // 03e8: ldc -15.466
      // 03ea: ldc 75.502
      // 03ec: ldc 6.085
      // 03ee: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03f1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03f4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03f7: aastore
      // 03f8: dup
      // 03f9: bipush 28
      // 03fb: new net/minecraft/client/animation/Keyframe
      // 03fe: dup
      // 03ff: ldc 1.17
      // 0401: ldc -31.494
      // 0403: ldc 78.317
      // 0405: ldc -8.697
      // 0407: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 040a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 040d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0410: aastore
      // 0411: dup
      // 0412: bipush 29
      // 0414: new net/minecraft/client/animation/Keyframe
      // 0417: dup
      // 0418: ldc 1.21
      // 041a: ldc -67.515
      // 041c: ldc 79.74
      // 041e: ldc -43.519
      // 0420: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0423: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0426: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0429: aastore
      // 042a: dup
      // 042b: bipush 30
      // 042d: new net/minecraft/client/animation/Keyframe
      // 0430: dup
      // 0431: ldc 1.25
      // 0433: ldc -92.641
      // 0435: ldc 79.902
      // 0437: ldc -67.59
      // 0439: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 043c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 043f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0442: aastore
      // 0443: dup
      // 0444: bipush 31
      // 0446: new net/minecraft/client/animation/Keyframe
      // 0449: dup
      // 044a: ldc 1.29
      // 044c: ldc -33.476
      // 044e: ldc 56.52
      // 0450: ldc -7.851
      // 0452: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0455: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0458: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 045b: aastore
      // 045c: dup
      // 045d: bipush 32
      // 045f: new net/minecraft/client/animation/Keyframe
      // 0462: dup
      // 0463: ldc 1.33
      // 0465: ldc -31.529
      // 0467: ldc 2.893
      // 0469: ldc -5.689
      // 046b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 046e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0471: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0474: aastore
      // 0475: dup
      // 0476: bipush 33
      // 0478: new net/minecraft/client/animation/Keyframe
      // 047b: dup
      // 047c: ldc 1.38
      // 047e: ldc -31.517
      // 0480: ldc -51.522
      // 0482: ldc -5.376
      // 0484: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0487: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 048a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 048d: aastore
      // 048e: dup
      // 048f: bipush 34
      // 0491: new net/minecraft/client/animation/Keyframe
      // 0494: dup
      // 0495: ldc 1.42
      // 0497: ldc -32.643
      // 0499: ldc -78.057
      // 049b: ldc -4.69
      // 049d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04a0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04a3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04a6: aastore
      // 04a7: dup
      // 04a8: bipush 35
      // 04aa: new net/minecraft/client/animation/Keyframe
      // 04ad: dup
      // 04ae: ldc 1.46
      // 04b0: ldc -34.694
      // 04b2: ldc -80.395
      // 04b4: ldc -3.27
      // 04b6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04b9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04bc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04bf: aastore
      // 04c0: dup
      // 04c1: bipush 36
      // 04c3: new net/minecraft/client/animation/Keyframe
      // 04c6: dup
      // 04c7: ldc 1.5
      // 04c9: ldc -36.582
      // 04cb: ldc -81.585
      // 04cd: ldc -1.989
      // 04cf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04d2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04d5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04d8: aastore
      // 04d9: dup
      // 04da: bipush 37
      // 04dc: new net/minecraft/client/animation/Keyframe
      // 04df: dup
      // 04e0: ldc 1.54
      // 04e2: ldc -37.894
      // 04e4: ldc -82.016
      // 04e6: ldc -1.132
      // 04e8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04eb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04ee: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04f1: aastore
      // 04f2: dup
      // 04f3: bipush 38
      // 04f5: new net/minecraft/client/animation/Keyframe
      // 04f8: dup
      // 04f9: ldc 1.58
      // 04fb: ldc -38.366
      // 04fd: ldc -82.075
      // 04ff: ldc -0.837
      // 0501: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0504: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0507: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 050a: aastore
      // 050b: dup
      // 050c: bipush 39
      // 050e: new net/minecraft/client/animation/Keyframe
      // 0511: dup
      // 0512: ldc 1.62
      // 0514: ldc -38.087
      // 0516: ldc -80.818
      // 0518: ldc -1.104
      // 051a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 051d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0520: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0523: aastore
      // 0524: dup
      // 0525: bipush 40
      // 0527: new net/minecraft/client/animation/Keyframe
      // 052a: dup
      // 052b: ldc 1.67
      // 052d: ldc -37.669
      // 052f: ldc -78.549
      // 0531: ldc -1.5
      // 0533: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0536: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0539: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 053c: aastore
      // 053d: dup
      // 053e: bipush 41
      // 0540: new net/minecraft/client/animation/Keyframe
      // 0543: dup
      // 0544: ldc 1.71
      // 0546: ldc -37.432
      // 0548: ldc -76.993
      // 054a: ldc -1.722
      // 054c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 054f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0552: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0555: aastore
      // 0556: dup
      // 0557: bipush 42
      // 0559: new net/minecraft/client/animation/Keyframe
      // 055c: dup
      // 055d: ldc 1.75
      // 055f: ldc -37.269
      // 0561: ldc -75.78
      // 0563: ldc -1.873
      // 0565: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0568: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 056b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 056e: aastore
      // 056f: dup
      // 0570: bipush 43
      // 0572: new net/minecraft/client/animation/Keyframe
      // 0575: dup
      // 0576: ldc 1.79
      // 0578: ldc_w -37.059
      // 057b: ldc_w -74.015
      // 057e: ldc_w -2.066
      // 0581: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0584: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0587: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 058a: aastore
      // 058b: dup
      // 058c: bipush 44
      // 058e: new net/minecraft/client/animation/Keyframe
      // 0591: dup
      // 0592: ldc_w 1.83
      // 0595: ldc_w -36.254
      // 0598: ldc_w -70.988
      // 059b: ldc_w -2.321
      // 059e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 05a1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05a4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05a7: aastore
      // 05a8: dup
      // 05a9: bipush 45
      // 05ab: new net/minecraft/client/animation/Keyframe
      // 05ae: dup
      // 05af: ldc_w 1.88
      // 05b2: ldc_w -34.504
      // 05b5: ldc_w -66.735
      // 05b8: ldc_w -2.573
      // 05bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 05be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05c4: aastore
      // 05c5: dup
      // 05c6: bipush 46
      // 05c8: new net/minecraft/client/animation/Keyframe
      // 05cb: dup
      // 05cc: ldc_w 1.92
      // 05cf: ldc_w -31.997
      // 05d2: ldc_w -61.477
      // 05d5: ldc_w -2.787
      // 05d8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 05db: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05de: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05e1: aastore
      // 05e2: dup
      // 05e3: bipush 47
      // 05e5: new net/minecraft/client/animation/Keyframe
      // 05e8: dup
      // 05e9: ldc_w 1.96
      // 05ec: ldc_w -28.9
      // 05ef: ldc_w -55.438
      // 05f2: ldc_w -2.953
      // 05f5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 05f8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05fb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05fe: aastore
      // 05ff: dup
      // 0600: bipush 48
      // 0602: new net/minecraft/client/animation/Keyframe
      // 0605: dup
      // 0606: fconst_2
      // 0607: ldc_w -25.365
      // 060a: ldc_w -48.84
      // 060d: ldc_w -3.073
      // 0610: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0613: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0616: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0619: aastore
      // 061a: dup
      // 061b: bipush 49
      // 061d: new net/minecraft/client/animation/Keyframe
      // 0620: dup
      // 0621: ldc_w 2.04
      // 0624: ldc_w -21.542
      // 0627: ldc_w -41.906
      // 062a: ldc_w -3.152
      // 062d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0630: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0633: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0636: aastore
      // 0637: dup
      // 0638: bipush 50
      // 063a: new net/minecraft/client/animation/Keyframe
      // 063d: dup
      // 063e: ldc_w 2.08
      // 0641: ldc_w -17.58
      // 0644: ldc_w -34.859
      // 0647: ldc_w -3.198
      // 064a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 064d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0650: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0653: aastore
      // 0654: dup
      // 0655: bipush 51
      // 0657: new net/minecraft/client/animation/Keyframe
      // 065a: dup
      // 065b: ldc_w 2.12
      // 065e: ldc_w -13.629
      // 0661: ldc_w -27.922
      // 0664: ldc_w -3.216
      // 0667: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 066a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 066d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0670: aastore
      // 0671: dup
      // 0672: bipush 52
      // 0674: new net/minecraft/client/animation/Keyframe
      // 0677: dup
      // 0678: ldc_w 2.17
      // 067b: ldc_w -9.844
      // 067e: ldc_w -21.316
      // 0681: ldc_w -3.212
      // 0684: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0687: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 068a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 068d: aastore
      // 068e: dup
      // 068f: bipush 53
      // 0691: new net/minecraft/client/animation/Keyframe
      // 0694: dup
      // 0695: ldc_w 2.21
      // 0698: ldc_w -6.38
      // 069b: ldc_w -15.265
      // 069e: ldc_w -3.191
      // 06a1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 06a4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06a7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06aa: aastore
      // 06ab: dup
      // 06ac: bipush 54
      // 06ae: new net/minecraft/client/animation/Keyframe
      // 06b1: dup
      // 06b2: ldc 2.25
      // 06b4: ldc_w -3.396
      // 06b7: ldc_w -9.991
      // 06ba: ldc_w -3.161
      // 06bd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 06c0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06c3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06c6: aastore
      // 06c7: dup
      // 06c8: bipush 55
      // 06ca: new net/minecraft/client/animation/Keyframe
      // 06cd: dup
      // 06ce: ldc 2.29
      // 06d0: ldc_w -1.055
      // 06d3: ldc_w -5.716
      // 06d6: ldc_w -3.128
      // 06d9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 06dc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06df: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06e2: aastore
      // 06e3: dup
      // 06e4: bipush 56
      // 06e6: new net/minecraft/client/animation/Keyframe
      // 06e9: dup
      // 06ea: ldc 2.33
      // 06ec: ldc_w 0.48
      // 06ef: ldc_w -2.664
      // 06f2: ldc_w -3.102
      // 06f5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 06f8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06fb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06fe: aastore
      // 06ff: dup
      // 0700: bipush 57
      // 0702: new net/minecraft/client/animation/Keyframe
      // 0705: dup
      // 0706: ldc 2.38
      // 0708: ldc_w 1.043
      // 070b: ldc_w -1.057
      // 070e: ldc_w -3.089
      // 0711: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0714: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0717: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 071a: aastore
      // 071b: dup
      // 071c: bipush 58
      // 071e: new net/minecraft/client/animation/Keyframe
      // 0721: dup
      // 0722: ldc 2.42
      // 0724: ldc_w 0.864
      // 0727: ldc_w -0.431
      // 072a: ldc_w -3.331
      // 072d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0730: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0733: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0736: aastore
      // 0737: dup
      // 0738: bipush 59
      // 073a: new net/minecraft/client/animation/Keyframe
      // 073d: dup
      // 073e: ldc 2.46
      // 0740: ldc_w 0.391
      // 0743: ldc_w -0.108
      // 0746: ldc_w -3.892
      // 0749: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 074c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 074f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0752: aastore
      // 0753: dup
      // 0754: bipush 60
      // 0756: new net/minecraft/client/animation/Keyframe
      // 0759: dup
      // 075a: ldc 2.5
      // 075c: ldc_w -0.199
      // 075f: ldc_w 0.011
      // 0762: ldc_w -4.515
      // 0765: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0768: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 076b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 076e: aastore
      // 076f: dup
      // 0770: bipush 61
      // 0772: new net/minecraft/client/animation/Keyframe
      // 0775: dup
      // 0776: ldc 2.54
      // 0778: ldc -0.727
      // 077a: ldc 0.028
      // 077c: ldc -4.943
      // 077e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0781: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0784: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0787: aastore
      // 0788: dup
      // 0789: bipush 62
      // 078b: new net/minecraft/client/animation/Keyframe
      // 078e: dup
      // 078f: ldc_w 2.58
      // 0792: ldc_w -1.0
      // 0795: ldc 0.028
      // 0797: ldc_w -5.1
      // 079a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 079d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07a0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07a3: aastore
      // 07a4: dup
      // 07a5: bipush 63
      // 07a7: new net/minecraft/client/animation/Keyframe
      // 07aa: dup
      // 07ab: ldc_w 2.62
      // 07ae: ldc_w -1.017
      // 07b1: ldc 0.028
      // 07b3: ldc_w -5.122
      // 07b6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 07b9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07bc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07bf: aastore
      // 07c0: dup
      // 07c1: bipush 64
      // 07c3: new net/minecraft/client/animation/Keyframe
      // 07c6: dup
      // 07c7: ldc_w 2.75
      // 07ca: ldc_w -0.664
      // 07cd: ldc 0.028
      // 07cf: ldc_w -4.891
      // 07d2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 07d5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07d8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07db: aastore
      // 07dc: dup
      // 07dd: bipush 65
      // 07df: new net/minecraft/client/animation/Keyframe
      // 07e2: dup
      // 07e3: ldc 2.83
      // 07e5: ldc -0.727
      // 07e7: ldc 0.028
      // 07e9: ldc -4.943
      // 07eb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 07ee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07f1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07f4: aastore
      // 07f5: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 07f8: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 07fb: ldc "CTR_hips"
      // 07fd: new net/minecraft/client/animation/AnimationChannel
      // 0800: dup
      // 0801: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0804: bipush 65
      // 0806: anewarray 28
      // 0809: dup
      // 080a: bipush 0
      // 080b: new net/minecraft/client/animation/Keyframe
      // 080e: dup
      // 080f: fconst_0
      // 0810: fconst_0
      // 0811: fconst_0
      // 0812: fconst_0
      // 0813: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0816: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0819: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 081c: aastore
      // 081d: dup
      // 081e: bipush 1
      // 081f: new net/minecraft/client/animation/Keyframe
      // 0822: dup
      // 0823: ldc 0.04
      // 0825: ldc_w 0.083
      // 0828: ldc_w -0.018
      // 082b: ldc_w 0.117
      // 082e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0831: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0834: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0837: aastore
      // 0838: dup
      // 0839: bipush 2
      // 083a: new net/minecraft/client/animation/Keyframe
      // 083d: dup
      // 083e: ldc 0.08
      // 0840: ldc_w 0.351
      // 0843: ldc_w -0.075
      // 0846: ldc_w 0.495
      // 0849: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 084c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 084f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0852: aastore
      // 0853: dup
      // 0854: bipush 3
      // 0855: new net/minecraft/client/animation/Keyframe
      // 0858: dup
      // 0859: ldc 0.12
      // 085b: ldc_w 0.837
      // 085e: ldc_w -0.181
      // 0861: ldc_w 1.179
      // 0864: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0867: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 086a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 086d: aastore
      // 086e: dup
      // 086f: bipush 4
      // 0870: new net/minecraft/client/animation/Keyframe
      // 0873: dup
      // 0874: ldc 0.17
      // 0876: ldc_w 1.575
      // 0879: ldc_w -0.346
      // 087c: ldc_w 2.219
      // 087f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0882: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0885: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0888: aastore
      // 0889: dup
      // 088a: bipush 5
      // 088b: new net/minecraft/client/animation/Keyframe
      // 088e: dup
      // 088f: ldc 0.21
      // 0891: ldc_w 2.593
      // 0894: ldc_w -0.582
      // 0897: ldc_w 3.657
      // 089a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 089d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08a0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08a3: aastore
      // 08a4: dup
      // 08a5: bipush 6
      // 08a7: new net/minecraft/client/animation/Keyframe
      // 08aa: dup
      // 08ab: ldc 0.25
      // 08ad: ldc_w 3.89
      // 08b0: ldc_w -0.901
      // 08b3: ldc_w 5.494
      // 08b6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 08b9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08bc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08bf: aastore
      // 08c0: dup
      // 08c1: bipush 7
      // 08c3: new net/minecraft/client/animation/Keyframe
      // 08c6: dup
      // 08c7: ldc 0.29
      // 08c9: ldc_w 5.396
      // 08cc: ldc_w -1.298
      // 08cf: ldc_w 7.633
      // 08d2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 08d5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08d8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08db: aastore
      // 08dc: dup
      // 08dd: bipush 8
      // 08df: new net/minecraft/client/animation/Keyframe
      // 08e2: dup
      // 08e3: ldc 0.33
      // 08e5: ldc_w 6.927
      // 08e8: ldc_w -1.751
      // 08eb: ldc_w 9.821
      // 08ee: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 08f1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08f4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08f7: aastore
      // 08f8: dup
      // 08f9: bipush 9
      // 08fb: new net/minecraft/client/animation/Keyframe
      // 08fe: dup
      // 08ff: ldc 0.38
      // 0901: ldc_w 8.232
      // 0904: ldc_w -2.21
      // 0907: ldc_w 11.706
      // 090a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 090d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0910: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0913: aastore
      // 0914: dup
      // 0915: bipush 10
      // 0917: new net/minecraft/client/animation/Keyframe
      // 091a: dup
      // 091b: ldc 0.42
      // 091d: ldc_w 9.123
      // 0920: ldc_w -2.628
      // 0923: ldc_w 13.021
      // 0926: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0929: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 092c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 092f: aastore
      // 0930: dup
      // 0931: bipush 11
      // 0933: new net/minecraft/client/animation/Keyframe
      // 0936: dup
      // 0937: ldc 0.46
      // 0939: ldc_w 9.55
      // 093c: ldc_w -2.979
      // 093f: ldc_w 13.69
      // 0942: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0945: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0948: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 094b: aastore
      // 094c: dup
      // 094d: bipush 12
      // 094f: new net/minecraft/client/animation/Keyframe
      // 0952: dup
      // 0953: ldc 0.5
      // 0955: ldc_w 9.709
      // 0958: ldc_w -3.216
      // 095b: ldc_w 13.974
      // 095e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0961: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0964: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0967: aastore
      // 0968: dup
      // 0969: bipush 13
      // 096b: new net/minecraft/client/animation/Keyframe
      // 096e: dup
      // 096f: ldc 0.54
      // 0971: ldc_w 9.789
      // 0974: ldc_w -3.323
      // 0977: ldc_w 14.117
      // 097a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 097d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0980: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0983: aastore
      // 0984: dup
      // 0985: bipush 14
      // 0987: new net/minecraft/client/animation/Keyframe
      // 098a: dup
      // 098b: ldc 0.58
      // 098d: ldc_w 9.802
      // 0990: ldc_w -3.334
      // 0993: ldc_w 14.144
      // 0996: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0999: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 099c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 099f: aastore
      // 09a0: dup
      // 09a1: bipush 15
      // 09a3: new net/minecraft/client/animation/Keyframe
      // 09a6: dup
      // 09a7: ldc 0.62
      // 09a9: ldc_w 9.767
      // 09ac: ldc_w -3.28
      // 09af: ldc_w 14.082
      // 09b2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 09b5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09b8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09bb: aastore
      // 09bc: dup
      // 09bd: bipush 16
      // 09bf: new net/minecraft/client/animation/Keyframe
      // 09c2: dup
      // 09c3: ldc 0.67
      // 09c5: ldc_w 9.703
      // 09c8: ldc_w -3.19
      // 09cb: ldc_w 13.967
      // 09ce: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 09d1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09d4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09d7: aastore
      // 09d8: dup
      // 09d9: bipush 17
      // 09db: new net/minecraft/client/animation/Keyframe
      // 09de: dup
      // 09df: ldc 0.71
      // 09e1: ldc_w 9.632
      // 09e4: ldc_w -3.092
      // 09e7: ldc_w 13.838
      // 09ea: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 09ed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09f0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09f3: aastore
      // 09f4: dup
      // 09f5: bipush 18
      // 09f7: new net/minecraft/client/animation/Keyframe
      // 09fa: dup
      // 09fb: ldc 0.75
      // 09fd: ldc_w 9.573
      // 0a00: ldc_w -3.012
      // 0a03: ldc_w 13.733
      // 0a06: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a09: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a0c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a0f: aastore
      // 0a10: dup
      // 0a11: bipush 19
      // 0a13: new net/minecraft/client/animation/Keyframe
      // 0a16: dup
      // 0a17: ldc 0.79
      // 0a19: ldc_w 9.55
      // 0a1c: ldc_w -2.979
      // 0a1f: ldc_w 13.69
      // 0a22: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a25: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a28: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a2b: aastore
      // 0a2c: dup
      // 0a2d: bipush 20
      // 0a2f: new net/minecraft/client/animation/Keyframe
      // 0a32: dup
      // 0a33: ldc 0.83
      // 0a35: ldc_w 9.471
      // 0a38: ldc_w -2.877
      // 0a3b: ldc_w 13.69
      // 0a3e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a41: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a44: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a47: aastore
      // 0a48: dup
      // 0a49: bipush 21
      // 0a4b: new net/minecraft/client/animation/Keyframe
      // 0a4e: dup
      // 0a4f: ldc 0.88
      // 0a51: ldc_w 9.254
      // 0a54: ldc_w -2.596
      // 0a57: ldc_w 13.689
      // 0a5a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a5d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a60: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a63: aastore
      // 0a64: dup
      // 0a65: bipush 22
      // 0a67: new net/minecraft/client/animation/Keyframe
      // 0a6a: dup
      // 0a6b: ldc 0.92
      // 0a6d: ldc_w 8.93
      // 0a70: ldc_w -2.177
      // 0a73: ldc_w 13.687
      // 0a76: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a79: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a7c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a7f: aastore
      // 0a80: dup
      // 0a81: bipush 23
      // 0a83: new net/minecraft/client/animation/Keyframe
      // 0a86: dup
      // 0a87: ldc 0.96
      // 0a89: ldc_w 8.529
      // 0a8c: ldc_w -1.659
      // 0a8f: ldc_w 13.681
      // 0a92: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a95: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a98: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a9b: aastore
      // 0a9c: dup
      // 0a9d: bipush 24
      // 0a9f: new net/minecraft/client/animation/Keyframe
      // 0aa2: dup
      // 0aa3: fconst_1
      // 0aa4: ldc_w 8.082
      // 0aa7: ldc_w -1.082
      // 0aaa: ldc_w 13.673
      // 0aad: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0ab0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ab3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ab6: aastore
      // 0ab7: dup
      // 0ab8: bipush 25
      // 0aba: new net/minecraft/client/animation/Keyframe
      // 0abd: dup
      // 0abe: ldc 1.08
      // 0ac0: ldc_w 7.174
      // 0ac3: ldc_w 0.093
      // 0ac6: ldc_w 13.643
      // 0ac9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0acc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0acf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ad2: aastore
      // 0ad3: dup
      // 0ad4: bipush 26
      // 0ad6: new net/minecraft/client/animation/Keyframe
      // 0ad9: dup
      // 0ada: ldc 1.12
      // 0adc: ldc_w 6.773
      // 0adf: ldc_w 0.611
      // 0ae2: ldc_w 13.62
      // 0ae5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0ae8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0aeb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0aee: aastore
      // 0aef: dup
      // 0af0: bipush 27
      // 0af2: new net/minecraft/client/animation/Keyframe
      // 0af5: dup
      // 0af6: ldc 1.17
      // 0af8: ldc_w 6.449
      // 0afb: ldc_w 1.029
      // 0afe: ldc_w 13.59
      // 0b01: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b04: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b07: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b0a: aastore
      // 0b0b: dup
      // 0b0c: bipush 28
      // 0b0e: new net/minecraft/client/animation/Keyframe
      // 0b11: dup
      // 0b12: ldc 1.21
      // 0b14: ldc_w 6.232
      // 0b17: ldc_w 1.31
      // 0b1a: ldc_w 13.553
      // 0b1d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b20: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b23: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b26: aastore
      // 0b27: dup
      // 0b28: bipush 29
      // 0b2a: new net/minecraft/client/animation/Keyframe
      // 0b2d: dup
      // 0b2e: ldc 1.25
      // 0b30: ldc_w 6.153
      // 0b33: ldc_w 1.412
      // 0b36: ldc_w 13.507
      // 0b39: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b3c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b3f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b42: aastore
      // 0b43: dup
      // 0b44: bipush 30
      // 0b46: new net/minecraft/client/animation/Keyframe
      // 0b49: dup
      // 0b4a: ldc 1.29
      // 0b4c: ldc_w 6.164
      // 0b4f: ldc_w 0.786
      // 0b52: ldc_w 10.925
      // 0b55: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b58: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b5b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b5e: aastore
      // 0b5f: dup
      // 0b60: bipush 31
      // 0b62: new net/minecraft/client/animation/Keyframe
      // 0b65: dup
      // 0b66: ldc 1.33
      // 0b68: ldc_w 6.189
      // 0b6b: ldc_w -0.684
      // 0b6e: ldc_w 4.715
      // 0b71: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b74: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b77: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b7a: aastore
      // 0b7b: dup
      // 0b7c: bipush 32
      // 0b7e: new net/minecraft/client/animation/Keyframe
      // 0b81: dup
      // 0b82: ldc 1.38
      // 0b84: ldc_w 6.213
      // 0b87: ldc_w -2.387
      // 0b8a: ldc_w -2.965
      // 0b8d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b90: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b93: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b96: aastore
      // 0b97: dup
      // 0b98: bipush 33
      // 0b9a: new net/minecraft/client/animation/Keyframe
      // 0b9d: dup
      // 0b9e: ldc 1.42
      // 0ba0: ldc_w 6.224
      // 0ba3: ldc_w -3.711
      // 0ba6: ldc_w -9.96
      // 0ba9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0bac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0baf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bb2: aastore
      // 0bb3: dup
      // 0bb4: bipush 34
      // 0bb6: new net/minecraft/client/animation/Keyframe
      // 0bb9: dup
      // 0bba: ldc 1.46
      // 0bbc: ldc_w 6.224
      // 0bbf: ldc_w -4.428
      // 0bc2: ldc_w -14.586
      // 0bc5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0bc8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0bcb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bce: aastore
      // 0bcf: dup
      // 0bd0: bipush 35
      // 0bd2: new net/minecraft/client/animation/Keyframe
      // 0bd5: dup
      // 0bd6: ldc 1.5
      // 0bd8: ldc_w 6.224
      // 0bdb: ldc_w -4.796
      // 0bde: ldc_w -17.05
      // 0be1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0be4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0be7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bea: aastore
      // 0beb: dup
      // 0bec: bipush 36
      // 0bee: new net/minecraft/client/animation/Keyframe
      // 0bf1: dup
      // 0bf2: ldc 1.54
      // 0bf4: ldc_w 6.224
      // 0bf7: ldc_w -4.932
      // 0bfa: ldc_w -18.03
      // 0bfd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0c00: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c03: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c06: aastore
      // 0c07: dup
      // 0c08: bipush 37
      // 0c0a: new net/minecraft/client/animation/Keyframe
      // 0c0d: dup
      // 0c0e: ldc 1.58
      // 0c10: ldc_w 6.224
      // 0c13: ldc_w -4.951
      // 0c16: ldc_w -18.206
      // 0c19: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0c1c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c1f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c22: aastore
      // 0c23: dup
      // 0c24: bipush 38
      // 0c26: new net/minecraft/client/animation/Keyframe
      // 0c29: dup
      // 0c2a: ldc 1.75
      // 0c2c: ldc_w 6.224
      // 0c2f: ldc_w -4.951
      // 0c32: ldc_w -18.131
      // 0c35: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0c38: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c3b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c3e: aastore
      // 0c3f: dup
      // 0c40: bipush 39
      // 0c42: new net/minecraft/client/animation/Keyframe
      // 0c45: dup
      // 0c46: ldc 1.79
      // 0c48: ldc_w 6.224
      // 0c4b: ldc_w -4.951
      // 0c4e: ldc_w -18.128
      // 0c51: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0c54: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c57: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c5a: aastore
      // 0c5b: dup
      // 0c5c: bipush 40
      // 0c5e: new net/minecraft/client/animation/Keyframe
      // 0c61: dup
      // 0c62: ldc_w 1.83
      // 0c65: ldc_w 6.166
      // 0c68: ldc_w -4.841
      // 0c6b: ldc_w -18.118
      // 0c6e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0c71: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c74: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c77: aastore
      // 0c78: dup
      // 0c79: bipush 41
      // 0c7b: new net/minecraft/client/animation/Keyframe
      // 0c7e: dup
      // 0c7f: ldc_w 1.88
      // 0c82: ldc_w 6.0
      // 0c85: ldc_w -4.533
      // 0c88: ldc_w -18.056
      // 0c8b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0c8e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c91: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c94: aastore
      // 0c95: dup
      // 0c96: bipush 42
      // 0c98: new net/minecraft/client/animation/Keyframe
      // 0c9b: dup
      // 0c9c: ldc_w 1.92
      // 0c9f: ldc_w 5.74
      // 0ca2: ldc_w -4.059
      // 0ca5: ldc_w -17.887
      // 0ca8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0cab: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0cae: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0cb1: aastore
      // 0cb2: dup
      // 0cb3: bipush 43
      // 0cb5: new net/minecraft/client/animation/Keyframe
      // 0cb8: dup
      // 0cb9: ldc_w 1.96
      // 0cbc: ldc_w 5.401
      // 0cbf: ldc_w -3.454
      // 0cc2: ldc_w -17.56
      // 0cc5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0cc8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ccb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0cce: aastore
      // 0ccf: dup
      // 0cd0: bipush 44
      // 0cd2: new net/minecraft/client/animation/Keyframe
      // 0cd5: dup
      // 0cd6: fconst_2
      // 0cd7: ldc_w 4.997
      // 0cda: ldc_w -2.749
      // 0cdd: ldc_w -17.019
      // 0ce0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0ce3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ce6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ce9: aastore
      // 0cea: dup
      // 0ceb: bipush 45
      // 0ced: new net/minecraft/client/animation/Keyframe
      // 0cf0: dup
      // 0cf1: ldc_w 2.04
      // 0cf4: ldc_w 4.542
      // 0cf7: ldc_w -1.978
      // 0cfa: ldc_w -16.212
      // 0cfd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0d00: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d03: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d06: aastore
      // 0d07: dup
      // 0d08: bipush 46
      // 0d0a: new net/minecraft/client/animation/Keyframe
      // 0d0d: dup
      // 0d0e: ldc_w 2.08
      // 0d11: ldc_w 4.05
      // 0d14: ldc_w -1.174
      // 0d17: ldc_w -14.647
      // 0d1a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0d1d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d20: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d23: aastore
      // 0d24: dup
      // 0d25: bipush 47
      // 0d27: new net/minecraft/client/animation/Keyframe
      // 0d2a: dup
      // 0d2b: ldc_w 2.12
      // 0d2e: ldc_w 3.536
      // 0d31: ldc_w -0.371
      // 0d34: ldc_w -12.11
      // 0d37: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0d3a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d3d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d40: aastore
      // 0d41: dup
      // 0d42: bipush 48
      // 0d44: new net/minecraft/client/animation/Keyframe
      // 0d47: dup
      // 0d48: ldc_w 2.17
      // 0d4b: ldc_w 3.013
      // 0d4e: ldc_w 0.4
      // 0d51: ldc_w -8.964
      // 0d54: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0d57: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d5a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d5d: aastore
      // 0d5e: dup
      // 0d5f: bipush 49
      // 0d61: new net/minecraft/client/animation/Keyframe
      // 0d64: dup
      // 0d65: ldc_w 2.21
      // 0d68: ldc_w 2.497
      // 0d6b: ldc_w 1.105
      // 0d6e: ldc_w -5.574
      // 0d71: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0d74: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d77: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d7a: aastore
      // 0d7b: dup
      // 0d7c: bipush 50
      // 0d7e: new net/minecraft/client/animation/Keyframe
      // 0d81: dup
      // 0d82: ldc 2.25
      // 0d84: fconst_2
      // 0d85: ldc_w 1.711
      // 0d88: ldc_w -2.303
      // 0d8b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0d8e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d91: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d94: aastore
      // 0d95: dup
      // 0d96: bipush 51
      // 0d98: new net/minecraft/client/animation/Keyframe
      // 0d9b: dup
      // 0d9c: ldc 2.29
      // 0d9e: ldc_w 1.537
      // 0da1: ldc_w 2.184
      // 0da4: ldc_w 0.483
      // 0da7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0daa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0dad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0db0: aastore
      // 0db1: dup
      // 0db2: bipush 52
      // 0db4: new net/minecraft/client/animation/Keyframe
      // 0db7: dup
      // 0db8: ldc 2.33
      // 0dba: ldc_w 1.122
      // 0dbd: ldc_w 2.492
      // 0dc0: ldc_w 2.422
      // 0dc3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0dc6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0dc9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0dcc: aastore
      // 0dcd: dup
      // 0dce: bipush 53
      // 0dd0: new net/minecraft/client/animation/Keyframe
      // 0dd3: dup
      // 0dd4: ldc 2.38
      // 0dd6: ldc_w 0.77
      // 0dd9: ldc_w 2.602
      // 0ddc: ldc_w 3.15
      // 0ddf: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0de2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0de5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0de8: aastore
      // 0de9: dup
      // 0dea: bipush 54
      // 0dec: new net/minecraft/client/animation/Keyframe
      // 0def: dup
      // 0df0: ldc 2.42
      // 0df2: ldc_w 0.491
      // 0df5: ldc_w 2.315
      // 0df8: ldc_w 2.814
      // 0dfb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0dfe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e01: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e04: aastore
      // 0e05: dup
      // 0e06: bipush 55
      // 0e08: new net/minecraft/client/animation/Keyframe
      // 0e0b: dup
      // 0e0c: ldc 2.46
      // 0e0e: ldc_w 0.276
      // 0e11: ldc_w 1.629
      // 0e14: ldc_w 1.994
      // 0e17: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0e1a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e1d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e20: aastore
      // 0e21: dup
      // 0e22: bipush 56
      // 0e24: new net/minecraft/client/animation/Keyframe
      // 0e27: dup
      // 0e28: ldc 2.5
      // 0e2a: ldc_w 0.116
      // 0e2d: ldc_w 0.777
      // 0e30: ldc_w 0.963
      // 0e33: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0e36: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e39: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e3c: aastore
      // 0e3d: dup
      // 0e3e: bipush 57
      // 0e40: new net/minecraft/client/animation/Keyframe
      // 0e43: dup
      // 0e44: ldc 2.54
      // 0e46: fconst_0
      // 0e47: fconst_0
      // 0e48: fconst_0
      // 0e49: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0e4c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e4f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e52: aastore
      // 0e53: dup
      // 0e54: bipush 58
      // 0e56: new net/minecraft/client/animation/Keyframe
      // 0e59: dup
      // 0e5a: ldc_w 2.58
      // 0e5d: ldc_w -0.047
      // 0e60: ldc_w -0.313
      // 0e63: ldc_w -0.473
      // 0e66: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0e69: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e6c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e6f: aastore
      // 0e70: dup
      // 0e71: bipush 59
      // 0e73: new net/minecraft/client/animation/Keyframe
      // 0e76: dup
      // 0e77: ldc_w 2.62
      // 0e7a: ldc_w -0.029
      // 0e7d: ldc_w -0.218
      // 0e80: ldc_w -0.39
      // 0e83: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0e86: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e89: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e8c: aastore
      // 0e8d: dup
      // 0e8e: bipush 60
      // 0e90: new net/minecraft/client/animation/Keyframe
      // 0e93: dup
      // 0e94: ldc_w 2.67
      // 0e97: fconst_0
      // 0e98: fconst_0
      // 0e99: fconst_0
      // 0e9a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0e9d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ea0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ea3: aastore
      // 0ea4: dup
      // 0ea5: bipush 61
      // 0ea7: new net/minecraft/client/animation/Keyframe
      // 0eaa: dup
      // 0eab: ldc_w 2.71
      // 0eae: ldc_w 0.008
      // 0eb1: ldc_w 0.108
      // 0eb4: ldc_w 0.26
      // 0eb7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0eba: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ebd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ec0: aastore
      // 0ec1: dup
      // 0ec2: bipush 62
      // 0ec4: new net/minecraft/client/animation/Keyframe
      // 0ec7: dup
      // 0ec8: ldc_w 2.75
      // 0ecb: ldc_w 0.007
      // 0ece: ldc_w 0.095
      // 0ed1: ldc_w 0.231
      // 0ed4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0ed7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0eda: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0edd: aastore
      // 0ede: dup
      // 0edf: bipush 63
      // 0ee1: new net/minecraft/client/animation/Keyframe
      // 0ee4: dup
      // 0ee5: ldc_w 2.79
      // 0ee8: ldc_w 0.002
      // 0eeb: ldc_w 0.036
      // 0eee: ldc_w 0.086
      // 0ef1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0ef4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ef7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0efa: aastore
      // 0efb: dup
      // 0efc: bipush 64
      // 0efe: new net/minecraft/client/animation/Keyframe
      // 0f01: dup
      // 0f02: ldc 2.83
      // 0f04: fconst_0
      // 0f05: fconst_0
      // 0f06: fconst_0
      // 0f07: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0f0a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f0d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f10: aastore
      // 0f11: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0f14: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0f17: ldc_w "CTR_chest"
      // 0f1a: new net/minecraft/client/animation/AnimationChannel
      // 0f1d: dup
      // 0f1e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0f21: bipush 60
      // 0f23: anewarray 28
      // 0f26: dup
      // 0f27: bipush 0
      // 0f28: new net/minecraft/client/animation/Keyframe
      // 0f2b: dup
      // 0f2c: fconst_0
      // 0f2d: ldc_w 0.692
      // 0f30: ldc_w 4.888
      // 0f33: ldc_w 0.106
      // 0f36: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f39: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f3c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f3f: aastore
      // 0f40: dup
      // 0f41: bipush 1
      // 0f42: new net/minecraft/client/animation/Keyframe
      // 0f45: dup
      // 0f46: ldc 0.04
      // 0f48: ldc_w 0.698
      // 0f4b: ldc_w 4.239
      // 0f4e: ldc_w 0.198
      // 0f51: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f54: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f57: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f5a: aastore
      // 0f5b: dup
      // 0f5c: bipush 2
      // 0f5d: new net/minecraft/client/animation/Keyframe
      // 0f60: dup
      // 0f61: ldc 0.08
      // 0f63: ldc 0.71
      // 0f65: ldc_w 2.485
      // 0f68: ldc_w 0.468
      // 0f6b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f6e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f71: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f74: aastore
      // 0f75: dup
      // 0f76: bipush 3
      // 0f77: new net/minecraft/client/animation/Keyframe
      // 0f7a: dup
      // 0f7b: ldc 0.12
      // 0f7d: ldc_w 0.708
      // 0f80: ldc_w -0.083
      // 0f83: ldc_w 0.916
      // 0f86: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f89: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f8c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f8f: aastore
      // 0f90: dup
      // 0f91: bipush 4
      // 0f92: new net/minecraft/client/animation/Keyframe
      // 0f95: dup
      // 0f96: ldc 0.17
      // 0f98: ldc_w 0.663
      // 0f9b: ldc_w -3.174
      // 0f9e: ldc_w 1.541
      // 0fa1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fa4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fa7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0faa: aastore
      // 0fab: dup
      // 0fac: bipush 5
      // 0fad: new net/minecraft/client/animation/Keyframe
      // 0fb0: dup
      // 0fb1: ldc 0.21
      // 0fb3: ldc_w 0.553
      // 0fb6: ldc_w -6.496
      // 0fb9: ldc_w 2.34
      // 0fbc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fbf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fc2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fc5: aastore
      // 0fc6: dup
      // 0fc7: bipush 6
      // 0fc9: new net/minecraft/client/animation/Keyframe
      // 0fcc: dup
      // 0fcd: ldc 0.25
      // 0fcf: ldc_w 0.364
      // 0fd2: ldc_w -9.757
      // 0fd5: ldc_w 3.309
      // 0fd8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fdb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fde: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fe1: aastore
      // 0fe2: dup
      // 0fe3: bipush 7
      // 0fe5: new net/minecraft/client/animation/Keyframe
      // 0fe8: dup
      // 0fe9: ldc 0.29
      // 0feb: ldc_w 0.104
      // 0fee: ldc_w -12.669
      // 0ff1: ldc_w 4.43
      // 0ff4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ff7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ffa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ffd: aastore
      // 0ffe: dup
      // 0fff: bipush 8
      // 1001: new net/minecraft/client/animation/Keyframe
      // 1004: dup
      // 1005: ldc 0.33
      // 1007: ldc_w -0.186
      // 100a: ldc_w -14.947
      // 100d: ldc_w 5.675
      // 1010: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1013: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1016: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1019: aastore
      // 101a: dup
      // 101b: bipush 9
      // 101d: new net/minecraft/client/animation/Keyframe
      // 1020: dup
      // 1021: ldc 0.38
      // 1023: ldc_w -0.431
      // 1026: ldc_w -16.315
      // 1029: ldc_w 6.997
      // 102c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 102f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1032: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1035: aastore
      // 1036: dup
      // 1037: bipush 10
      // 1039: new net/minecraft/client/animation/Keyframe
      // 103c: dup
      // 103d: ldc 0.42
      // 103f: ldc_w -0.52
      // 1042: ldc_w -16.506
      // 1045: ldc_w 8.343
      // 1048: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 104b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 104e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1051: aastore
      // 1052: dup
      // 1053: bipush 11
      // 1055: new net/minecraft/client/animation/Keyframe
      // 1058: dup
      // 1059: ldc 0.46
      // 105b: ldc_w -0.307
      // 105e: ldc_w -15.267
      // 1061: ldc_w 9.664
      // 1064: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1067: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 106a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 106d: aastore
      // 106e: dup
      // 106f: bipush 12
      // 1071: new net/minecraft/client/animation/Keyframe
      // 1074: dup
      // 1075: ldc 0.5
      // 1077: ldc_w -0.164
      // 107a: ldc_w -13.482
      // 107d: ldc_w 11.019
      // 1080: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1083: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1086: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1089: aastore
      // 108a: dup
      // 108b: bipush 13
      // 108d: new net/minecraft/client/animation/Keyframe
      // 1090: dup
      // 1091: ldc 0.54
      // 1093: ldc_w -0.521
      // 1096: ldc_w -12.225
      // 1099: ldc_w 12.401
      // 109c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 109f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10a2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10a5: aastore
      // 10a6: dup
      // 10a7: bipush 14
      // 10a9: new net/minecraft/client/animation/Keyframe
      // 10ac: dup
      // 10ad: ldc 0.58
      // 10af: ldc_w -1.309
      // 10b2: ldc_w -11.514
      // 10b5: ldc_w 13.631
      // 10b8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10bb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10be: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10c1: aastore
      // 10c2: dup
      // 10c3: bipush 15
      // 10c5: new net/minecraft/client/animation/Keyframe
      // 10c8: dup
      // 10c9: ldc 0.62
      // 10cb: ldc_w -2.447
      // 10ce: ldc_w -11.342
      // 10d1: ldc_w 14.456
      // 10d4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10d7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10da: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10dd: aastore
      // 10de: dup
      // 10df: bipush 16
      // 10e1: new net/minecraft/client/animation/Keyframe
      // 10e4: dup
      // 10e5: ldc 0.67
      // 10e7: ldc_w -3.826
      // 10ea: ldc_w -11.682
      // 10ed: ldc_w 14.596
      // 10f0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10f3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10f6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10f9: aastore
      // 10fa: dup
      // 10fb: bipush 17
      // 10fd: new net/minecraft/client/animation/Keyframe
      // 1100: dup
      // 1101: ldc 0.71
      // 1103: ldc_w -5.293
      // 1106: ldc_w -12.488
      // 1109: ldc_w 13.853
      // 110c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 110f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1112: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1115: aastore
      // 1116: dup
      // 1117: bipush 18
      // 1119: new net/minecraft/client/animation/Keyframe
      // 111c: dup
      // 111d: ldc 0.75
      // 111f: ldc_w -6.663
      // 1122: ldc_w -13.706
      // 1125: ldc_w 12.181
      // 1128: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 112b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 112e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1131: aastore
      // 1132: dup
      // 1133: bipush 19
      // 1135: new net/minecraft/client/animation/Keyframe
      // 1138: dup
      // 1139: ldc 0.79
      // 113b: ldc_w -7.731
      // 113e: ldc_w -15.267
      // 1141: ldc_w 9.664
      // 1144: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1147: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 114a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 114d: aastore
      // 114e: dup
      // 114f: bipush 20
      // 1151: new net/minecraft/client/animation/Keyframe
      // 1154: dup
      // 1155: ldc 0.83
      // 1157: ldc_w -8.195
      // 115a: ldc_w -16.726
      // 115d: ldc_w 6.236
      // 1160: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1163: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1166: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1169: aastore
      // 116a: dup
      // 116b: bipush 21
      // 116d: new net/minecraft/client/animation/Keyframe
      // 1170: dup
      // 1171: ldc 0.88
      // 1173: ldc_w -7.992
      // 1176: ldc_w -17.674
      // 1179: ldc_w 1.903
      // 117c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 117f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1182: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1185: aastore
      // 1186: dup
      // 1187: bipush 22
      // 1189: new net/minecraft/client/animation/Keyframe
      // 118c: dup
      // 118d: ldc 0.92
      // 118f: ldc_w -7.329
      // 1192: ldc_w -18.082
      // 1195: ldc_w -3.076
      // 1198: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 119b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 119e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11a1: aastore
      // 11a2: dup
      // 11a3: bipush 23
      // 11a5: new net/minecraft/client/animation/Keyframe
      // 11a8: dup
      // 11a9: ldc 0.96
      // 11ab: ldc_w -6.451
      // 11ae: ldc_w -17.94
      // 11b1: ldc_w -8.423
      // 11b4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11b7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11ba: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11bd: aastore
      // 11be: dup
      // 11bf: bipush 24
      // 11c1: new net/minecraft/client/animation/Keyframe
      // 11c4: dup
      // 11c5: fconst_1
      // 11c6: ldc_w -5.615
      // 11c9: ldc_w -17.277
      // 11cc: ldc_w -13.86
      // 11cf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11d2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11d5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11d8: aastore
      // 11d9: dup
      // 11da: bipush 25
      // 11dc: new net/minecraft/client/animation/Keyframe
      // 11df: dup
      // 11e0: ldc 1.04
      // 11e2: ldc_w -5.056
      // 11e5: ldc_w -16.163
      // 11e8: ldc_w -19.112
      // 11eb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11ee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11f1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11f4: aastore
      // 11f5: dup
      // 11f6: bipush 26
      // 11f8: new net/minecraft/client/animation/Keyframe
      // 11fb: dup
      // 11fc: ldc 1.08
      // 11fe: ldc_w -4.966
      // 1201: ldc_w -14.696
      // 1204: ldc_w -23.924
      // 1207: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 120a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 120d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1210: aastore
      // 1211: dup
      // 1212: bipush 27
      // 1214: new net/minecraft/client/animation/Keyframe
      // 1217: dup
      // 1218: ldc 1.12
      // 121a: ldc_w -5.475
      // 121d: ldc_w -12.983
      // 1220: ldc_w -28.057
      // 1223: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1226: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1229: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 122c: aastore
      // 122d: dup
      // 122e: bipush 28
      // 1230: new net/minecraft/client/animation/Keyframe
      // 1233: dup
      // 1234: ldc 1.17
      // 1236: ldc_w -6.649
      // 1239: ldc_w -11.119
      // 123c: ldc_w -31.285
      // 123f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1242: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1245: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1248: aastore
      // 1249: dup
      // 124a: bipush 29
      // 124c: new net/minecraft/client/animation/Keyframe
      // 124f: dup
      // 1250: ldc 1.21
      // 1252: ldc_w -8.497
      // 1255: ldc_w -9.164
      // 1258: ldc_w -33.384
      // 125b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 125e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1261: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1264: aastore
      // 1265: dup
      // 1266: bipush 30
      // 1268: new net/minecraft/client/animation/Keyframe
      // 126b: dup
      // 126c: ldc 1.25
      // 126e: ldc_w -10.973
      // 1271: ldc_w -7.134
      // 1274: ldc_w -34.127
      // 1277: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 127a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 127d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1280: aastore
      // 1281: dup
      // 1282: bipush 31
      // 1284: new net/minecraft/client/animation/Keyframe
      // 1287: dup
      // 1288: ldc 1.29
      // 128a: ldc_w -28.789
      // 128d: ldc_w 19.338
      // 1290: ldc_w 13.953
      // 1293: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1296: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1299: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 129c: aastore
      // 129d: dup
      // 129e: bipush 32
      // 12a0: new net/minecraft/client/animation/Keyframe
      // 12a3: dup
      // 12a4: ldc 1.33
      // 12a6: ldc_w -10.067
      // 12a9: ldc_w 9.04
      // 12ac: ldc_w 81.165
      // 12af: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 12b2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12b5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12b8: aastore
      // 12b9: dup
      // 12ba: bipush 33
      // 12bc: new net/minecraft/client/animation/Keyframe
      // 12bf: dup
      // 12c0: ldc 1.38
      // 12c2: ldc_w -6.805
      // 12c5: ldc_w -18.77
      // 12c8: ldc_w 63.696
      // 12cb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 12ce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12d1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12d4: aastore
      // 12d5: dup
      // 12d6: bipush 34
      // 12d8: new net/minecraft/client/animation/Keyframe
      // 12db: dup
      // 12dc: ldc 1.42
      // 12de: ldc_w -6.874
      // 12e1: ldc_w -11.095
      // 12e4: ldc_w 47.619
      // 12e7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 12ea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12ed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12f0: aastore
      // 12f1: dup
      // 12f2: bipush 35
      // 12f4: new net/minecraft/client/animation/Keyframe
      // 12f7: dup
      // 12f8: ldc 1.79
      // 12fa: ldc_w -6.874
      // 12fd: ldc_w -11.095
      // 1300: ldc_w 47.619
      // 1303: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1306: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1309: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 130c: aastore
      // 130d: dup
      // 130e: bipush 36
      // 1310: new net/minecraft/client/animation/Keyframe
      // 1313: dup
      // 1314: ldc_w 1.83
      // 1317: ldc_w -6.641
      // 131a: ldc_w -11.532
      // 131d: ldc_w 48.46
      // 1320: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1323: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1326: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1329: aastore
      // 132a: dup
      // 132b: bipush 37
      // 132d: new net/minecraft/client/animation/Keyframe
      // 1330: dup
      // 1331: ldc_w 1.88
      // 1334: ldc_w -6.051
      // 1337: ldc_w -12.658
      // 133a: ldc_w 50.901
      // 133d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1340: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1343: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1346: aastore
      // 1347: dup
      // 1348: bipush 38
      // 134a: new net/minecraft/client/animation/Keyframe
      // 134d: dup
      // 134e: ldc_w 1.92
      // 1351: ldc_w -5.328
      // 1354: ldc_w -14.037
      // 1357: ldc_w 54.839
      // 135a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 135d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1360: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1363: aastore
      // 1364: dup
      // 1365: bipush 39
      // 1367: new net/minecraft/client/animation/Keyframe
      // 136a: dup
      // 136b: ldc_w 1.96
      // 136e: ldc_w -4.686
      // 1371: ldc_w -15.059
      // 1374: ldc_w 60.112
      // 1377: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 137a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 137d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1380: aastore
      // 1381: dup
      // 1382: bipush 40
      // 1384: new net/minecraft/client/animation/Keyframe
      // 1387: dup
      // 1388: fconst_2
      // 1389: ldc_w -4.201
      // 138c: ldc_w -15.021
      // 138f: ldc_w 66.389
      // 1392: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1395: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1398: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 139b: aastore
      // 139c: dup
      // 139d: bipush 41
      // 139f: new net/minecraft/client/animation/Keyframe
      // 13a2: dup
      // 13a3: ldc_w 2.04
      // 13a6: ldc_w -3.732
      // 13a9: ldc_w -13.263
      // 13ac: ldc_w 73.074
      // 13af: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 13b2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13b5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13b8: aastore
      // 13b9: dup
      // 13ba: bipush 42
      // 13bc: new net/minecraft/client/animation/Keyframe
      // 13bf: dup
      // 13c0: ldc_w 2.08
      // 13c3: ldc_w -2.971
      // 13c6: ldc_w -9.354
      // 13c9: ldc_w 79.318
      // 13cc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 13cf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13d2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13d5: aastore
      // 13d6: dup
      // 13d7: bipush 43
      // 13d9: new net/minecraft/client/animation/Keyframe
      // 13dc: dup
      // 13dd: ldc_w 2.12
      // 13e0: ldc_w -1.666
      // 13e3: ldc_w -3.241
      // 13e6: ldc_w 84.183
      // 13e9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 13ec: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13ef: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13f2: aastore
      // 13f3: dup
      // 13f4: bipush 44
      // 13f6: new net/minecraft/client/animation/Keyframe
      // 13f9: dup
      // 13fa: ldc_w 2.17
      // 13fd: ldc_w 0.115
      // 1400: ldc_w 4.718
      // 1403: ldc_w 86.792
      // 1406: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1409: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 140c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 140f: aastore
      // 1410: dup
      // 1411: bipush 45
      // 1413: new net/minecraft/client/animation/Keyframe
      // 1416: dup
      // 1417: ldc_w 2.21
      // 141a: ldc_w 1.831
      // 141d: ldc_w 13.797
      // 1420: ldc_w 86.337
      // 1423: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1426: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1429: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 142c: aastore
      // 142d: dup
      // 142e: bipush 46
      // 1430: new net/minecraft/client/animation/Keyframe
      // 1433: dup
      // 1434: ldc 2.25
      // 1436: ldc_w 2.468
      // 1439: ldc_w 22.928
      // 143c: ldc_w 81.959
      // 143f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1442: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1445: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1448: aastore
      // 1449: dup
      // 144a: bipush 47
      // 144c: new net/minecraft/client/animation/Keyframe
      // 144f: dup
      // 1450: ldc 2.29
      // 1452: ldc_w 0.799
      // 1455: ldc_w 30.656
      // 1458: ldc_w 72.806
      // 145b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 145e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1461: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1464: aastore
      // 1465: dup
      // 1466: bipush 48
      // 1468: new net/minecraft/client/animation/Keyframe
      // 146b: dup
      // 146c: ldc 2.33
      // 146e: ldc_w -3.647
      // 1471: ldc_w 35.215
      // 1474: ldc_w 58.854
      // 1477: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 147a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 147d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1480: aastore
      // 1481: dup
      // 1482: bipush 49
      // 1484: new net/minecraft/client/animation/Keyframe
      // 1487: dup
      // 1488: ldc 2.38
      // 148a: ldc_w -9.037
      // 148d: ldc_w 35.21
      // 1490: ldc_w 42.373
      // 1493: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1496: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1499: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 149c: aastore
      // 149d: dup
      // 149e: bipush 50
      // 14a0: new net/minecraft/client/animation/Keyframe
      // 14a3: dup
      // 14a4: ldc 2.42
      // 14a6: ldc_w -12.001
      // 14a9: ldc_w 30.704
      // 14ac: ldc_w 27.143
      // 14af: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14b2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14b5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14b8: aastore
      // 14b9: dup
      // 14ba: bipush 51
      // 14bc: new net/minecraft/client/animation/Keyframe
      // 14bf: dup
      // 14c0: ldc 2.46
      // 14c2: ldc_w -10.862
      // 14c5: ldc_w 23.13
      // 14c8: ldc_w 15.239
      // 14cb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14ce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14d1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14d4: aastore
      // 14d5: dup
      // 14d6: bipush 52
      // 14d8: new net/minecraft/client/animation/Keyframe
      // 14db: dup
      // 14dc: ldc 2.5
      // 14de: ldc_w -6.19
      // 14e1: ldc_w 14.12
      // 14e4: ldc_w 6.506
      // 14e7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14ea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14ed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14f0: aastore
      // 14f1: dup
      // 14f2: bipush 53
      // 14f4: new net/minecraft/client/animation/Keyframe
      // 14f7: dup
      // 14f8: ldc 2.54
      // 14fa: ldc_w 0.692
      // 14fd: ldc_w 4.888
      // 1500: ldc_w 0.106
      // 1503: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1506: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1509: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 150c: aastore
      // 150d: dup
      // 150e: bipush 54
      // 1510: new net/minecraft/client/animation/Keyframe
      // 1513: dup
      // 1514: ldc_w 2.58
      // 1517: ldc_w 5.415
      // 151a: ldc_w -0.483
      // 151d: ldc_w -3.12
      // 1520: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1523: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1526: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1529: aastore
      // 152a: dup
      // 152b: bipush 55
      // 152d: new net/minecraft/client/animation/Keyframe
      // 1530: dup
      // 1531: ldc_w 2.62
      // 1534: ldc_w 5.373
      // 1537: ldc_w -0.442
      // 153a: ldc_w -3.192
      // 153d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1540: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1543: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1546: aastore
      // 1547: dup
      // 1548: bipush 56
      // 154a: new net/minecraft/client/animation/Keyframe
      // 154d: dup
      // 154e: ldc_w 2.67
      // 1551: ldc_w 2.971
      // 1554: ldc_w 2.22
      // 1557: ldc_w -1.65
      // 155a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 155d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1560: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1563: aastore
      // 1564: dup
      // 1565: bipush 57
      // 1567: new net/minecraft/client/animation/Keyframe
      // 156a: dup
      // 156b: ldc_w 2.71
      // 156e: ldc_w 0.692
      // 1571: ldc_w 4.888
      // 1574: ldc_w 0.106
      // 1577: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 157a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 157d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1580: aastore
      // 1581: dup
      // 1582: bipush 58
      // 1584: new net/minecraft/client/animation/Keyframe
      // 1587: dup
      // 1588: ldc_w 2.75
      // 158b: ldc_w 0.033
      // 158e: ldc_w 5.691
      // 1591: ldc_w 0.718
      // 1594: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1597: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 159a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 159d: aastore
      // 159e: dup
      // 159f: bipush 59
      // 15a1: new net/minecraft/client/animation/Keyframe
      // 15a4: dup
      // 15a5: ldc 2.83
      // 15a7: ldc_w 0.692
      // 15aa: ldc_w 4.888
      // 15ad: ldc_w 0.106
      // 15b0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15b3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15b6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15b9: aastore
      // 15ba: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 15bd: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 15c0: ldc_w "CTR_head"
      // 15c3: new net/minecraft/client/animation/AnimationChannel
      // 15c6: dup
      // 15c7: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 15ca: bipush 69
      // 15cc: anewarray 28
      // 15cf: dup
      // 15d0: bipush 0
      // 15d1: new net/minecraft/client/animation/Keyframe
      // 15d4: dup
      // 15d5: fconst_0
      // 15d6: ldc_w 0.412
      // 15d9: ldc_w -4.079
      // 15dc: ldc_w -4.432
      // 15df: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15e2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15e5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15e8: aastore
      // 15e9: dup
      // 15ea: bipush 1
      // 15eb: new net/minecraft/client/animation/Keyframe
      // 15ee: dup
      // 15ef: ldc 0.04
      // 15f1: ldc_w 0.728
      // 15f4: ldc_w -3.192
      // 15f7: ldc_w -4.132
      // 15fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1600: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1603: aastore
      // 1604: dup
      // 1605: bipush 2
      // 1606: new net/minecraft/client/animation/Keyframe
      // 1609: dup
      // 160a: ldc 0.08
      // 160c: ldc_w 1.673
      // 160f: ldc_w -0.79
      // 1612: ldc_w -3.199
      // 1615: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1618: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 161b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 161e: aastore
      // 161f: dup
      // 1620: bipush 3
      // 1621: new net/minecraft/client/animation/Keyframe
      // 1624: dup
      // 1625: ldc 0.12
      // 1627: ldc_w 3.307
      // 162a: ldc_w 2.727
      // 162d: ldc_w -1.558
      // 1630: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1633: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1636: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1639: aastore
      // 163a: dup
      // 163b: bipush 4
      // 163c: new net/minecraft/client/animation/Keyframe
      // 163f: dup
      // 1640: ldc 0.17
      // 1642: ldc_w 5.737
      // 1645: ldc_w 6.93
      // 1648: ldc_w 0.925
      // 164b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 164e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1651: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1654: aastore
      // 1655: dup
      // 1656: bipush 5
      // 1657: new net/minecraft/client/animation/Keyframe
      // 165a: dup
      // 165b: ldc 0.21
      // 165d: ldc_w 9.075
      // 1660: ldc_w 11.347
      // 1663: ldc_w 4.443
      // 1666: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1669: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 166c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 166f: aastore
      // 1670: dup
      // 1671: bipush 6
      // 1673: new net/minecraft/client/animation/Keyframe
      // 1676: dup
      // 1677: ldc 0.25
      // 1679: ldc_w 13.359
      // 167c: ldc_w 15.465
      // 167f: ldc_w 9.174
      // 1682: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1685: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1688: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 168b: aastore
      // 168c: dup
      // 168d: bipush 7
      // 168f: new net/minecraft/client/animation/Keyframe
      // 1692: dup
      // 1693: ldc 0.29
      // 1695: ldc_w 18.432
      // 1698: ldc_w 18.781
      // 169b: ldc_w 15.115
      // 169e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16a1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16a4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16a7: aastore
      // 16a8: dup
      // 16a9: bipush 8
      // 16ab: new net/minecraft/client/animation/Keyframe
      // 16ae: dup
      // 16af: ldc 0.33
      // 16b1: ldc_w 23.846
      // 16b4: ldc_w 20.705
      // 16b7: ldc_w 21.96
      // 16ba: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16bd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16c0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16c3: aastore
      // 16c4: dup
      // 16c5: bipush 9
      // 16c7: new net/minecraft/client/animation/Keyframe
      // 16ca: dup
      // 16cb: ldc 0.38
      // 16cd: ldc_w 29.009
      // 16d0: ldc_w 21.247
      // 16d3: ldc_w 29.097
      // 16d6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16d9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16dc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16df: aastore
      // 16e0: dup
      // 16e1: bipush 10
      // 16e3: new net/minecraft/client/animation/Keyframe
      // 16e6: dup
      // 16e7: ldc 0.42
      // 16e9: ldc_w 33.275
      // 16ec: ldc_w 20.947
      // 16ef: ldc_w 35.813
      // 16f2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16f5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16f8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16fb: aastore
      // 16fc: dup
      // 16fd: bipush 11
      // 16ff: new net/minecraft/client/animation/Keyframe
      // 1702: dup
      // 1703: ldc 0.46
      // 1705: ldc_w 36.072
      // 1708: ldc_w 20.41
      // 170b: ldc_w 41.742
      // 170e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1711: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1714: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1717: aastore
      // 1718: dup
      // 1719: bipush 12
      // 171b: new net/minecraft/client/animation/Keyframe
      // 171e: dup
      // 171f: ldc 0.5
      // 1721: ldc_w 37.371
      // 1724: ldc_w 19.75
      // 1727: ldc_w 45.62
      // 172a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 172d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1730: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1733: aastore
      // 1734: dup
      // 1735: bipush 13
      // 1737: new net/minecraft/client/animation/Keyframe
      // 173a: dup
      // 173b: ldc 0.54
      // 173d: ldc_w 37.806
      // 1740: ldc_w 18.822
      // 1743: ldc_w 46.695
      // 1746: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1749: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 174c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 174f: aastore
      // 1750: dup
      // 1751: bipush 14
      // 1753: new net/minecraft/client/animation/Keyframe
      // 1756: dup
      // 1757: ldc 0.58
      // 1759: ldc_w 37.848
      // 175c: ldc_w 17.793
      // 175f: ldc_w 45.815
      // 1762: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1765: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1768: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 176b: aastore
      // 176c: dup
      // 176d: bipush 15
      // 176f: new net/minecraft/client/animation/Keyframe
      // 1772: dup
      // 1773: ldc 0.62
      // 1775: ldc_w 37.858
      // 1778: ldc_w 16.786
      // 177b: ldc_w 43.911
      // 177e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1781: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1784: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1787: aastore
      // 1788: dup
      // 1789: bipush 16
      // 178b: new net/minecraft/client/animation/Keyframe
      // 178e: dup
      // 178f: ldc 0.67
      // 1791: ldc_w 38.082
      // 1794: ldc_w 15.924
      // 1797: ldc_w 41.925
      // 179a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 179d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17a0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17a3: aastore
      // 17a4: dup
      // 17a5: bipush 17
      // 17a7: new net/minecraft/client/animation/Keyframe
      // 17aa: dup
      // 17ab: ldc 0.71
      // 17ad: ldc_w 38.667
      // 17b0: ldc_w 15.309
      // 17b3: ldc_w 40.703
      // 17b6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 17b9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17bc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17bf: aastore
      // 17c0: dup
      // 17c1: bipush 18
      // 17c3: new net/minecraft/client/animation/Keyframe
      // 17c6: dup
      // 17c7: ldc 0.75
      // 17c9: ldc_w 39.707
      // 17cc: ldc_w 14.992
      // 17cf: ldc_w 40.917
      // 17d2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 17d5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17d8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17db: aastore
      // 17dc: dup
      // 17dd: bipush 19
      // 17df: new net/minecraft/client/animation/Keyframe
      // 17e2: dup
      // 17e3: ldc 0.79
      // 17e5: ldc_w 41.293
      // 17e8: ldc_w 14.958
      // 17eb: ldc_w 43.103
      // 17ee: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 17f1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17f4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17f7: aastore
      // 17f8: dup
      // 17f9: bipush 20
      // 17fb: new net/minecraft/client/animation/Keyframe
      // 17fe: dup
      // 17ff: ldc 0.83
      // 1801: ldc_w 42.912
      // 1804: ldc_w 14.904
      // 1807: ldc_w 46.839
      // 180a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 180d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1810: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1813: aastore
      // 1814: dup
      // 1815: bipush 21
      // 1817: new net/minecraft/client/animation/Keyframe
      // 181a: dup
      // 181b: ldc 0.88
      // 181d: ldc_w 43.944
      // 1820: ldc_w 14.578
      // 1823: ldc_w 51.22
      // 1826: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1829: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 182c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 182f: aastore
      // 1830: dup
      // 1831: bipush 22
      // 1833: new net/minecraft/client/animation/Keyframe
      // 1836: dup
      // 1837: ldc 0.92
      // 1839: ldc_w 44.37
      // 183c: ldc_w 13.987
      // 183f: ldc_w 56.011
      // 1842: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1845: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1848: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 184b: aastore
      // 184c: dup
      // 184d: bipush 23
      // 184f: new net/minecraft/client/animation/Keyframe
      // 1852: dup
      // 1853: ldc 0.96
      // 1855: ldc_w 44.166
      // 1858: ldc_w 13.135
      // 185b: ldc_w 60.994
      // 185e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1861: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1864: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1867: aastore
      // 1868: dup
      // 1869: bipush 24
      // 186b: new net/minecraft/client/animation/Keyframe
      // 186e: dup
      // 186f: fconst_1
      // 1870: ldc_w 43.288
      // 1873: ldc_w 12.018
      // 1876: ldc_w 65.963
      // 1879: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 187c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 187f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1882: aastore
      // 1883: dup
      // 1884: bipush 25
      // 1886: new net/minecraft/client/animation/Keyframe
      // 1889: dup
      // 188a: ldc 1.04
      // 188c: ldc_w 41.655
      // 188f: ldc_w 10.622
      // 1892: ldc_w 70.716
      // 1895: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1898: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 189b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 189e: aastore
      // 189f: dup
      // 18a0: bipush 26
      // 18a2: new net/minecraft/client/animation/Keyframe
      // 18a5: dup
      // 18a6: ldc 1.08
      // 18a8: ldc_w 39.144
      // 18ab: ldc_w 8.922
      // 18ae: ldc_w 75.059
      // 18b1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 18b4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18b7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18ba: aastore
      // 18bb: dup
      // 18bc: bipush 27
      // 18be: new net/minecraft/client/animation/Keyframe
      // 18c1: dup
      // 18c2: ldc 1.12
      // 18c4: ldc_w 35.596
      // 18c7: ldc_w 6.882
      // 18ca: ldc_w 78.799
      // 18cd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 18d0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18d3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18d6: aastore
      // 18d7: dup
      // 18d8: bipush 28
      // 18da: new net/minecraft/client/animation/Keyframe
      // 18dd: dup
      // 18de: ldc 1.17
      // 18e0: ldc_w 30.838
      // 18e3: ldc_w 4.452
      // 18e6: ldc_w 81.74
      // 18e9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 18ec: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18ef: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18f2: aastore
      // 18f3: dup
      // 18f4: bipush 29
      // 18f6: new net/minecraft/client/animation/Keyframe
      // 18f9: dup
      // 18fa: ldc 1.21
      // 18fc: ldc_w 24.697
      // 18ff: ldc_w 1.578
      // 1902: ldc_w 83.687
      // 1905: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1908: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 190b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 190e: aastore
      // 190f: dup
      // 1910: bipush 30
      // 1912: new net/minecraft/client/animation/Keyframe
      // 1915: dup
      // 1916: ldc 1.25
      // 1918: ldc_w 17.035
      // 191b: ldc_w -1.796
      // 191e: ldc_w 84.439
      // 1921: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1924: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1927: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 192a: aastore
      // 192b: dup
      // 192c: bipush 31
      // 192e: new net/minecraft/client/animation/Keyframe
      // 1931: dup
      // 1932: ldc 1.29
      // 1934: ldc_w 15.729
      // 1937: ldc_w -27.759
      // 193a: ldc_w 10.948
      // 193d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1940: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1943: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1946: aastore
      // 1947: dup
      // 1948: bipush 32
      // 194a: new net/minecraft/client/animation/Keyframe
      // 194d: dup
      // 194e: ldc 1.33
      // 1950: ldc_w 17.234
      // 1953: ldc_w 3.389
      // 1956: ldc_w -86.389
      // 1959: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 195c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 195f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1962: aastore
      // 1963: dup
      // 1964: bipush 33
      // 1966: new net/minecraft/client/animation/Keyframe
      // 1969: dup
      // 196a: ldc 1.38
      // 196c: ldc_w -4.729
      // 196f: ldc_w 0.57
      // 1972: ldc_w -97.171
      // 1975: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1978: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 197b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 197e: aastore
      // 197f: dup
      // 1980: bipush 34
      // 1982: new net/minecraft/client/animation/Keyframe
      // 1985: dup
      // 1986: ldc 1.42
      // 1988: ldc_w 12.911
      // 198b: ldc_w 1.686
      // 198e: ldc_w -74.406
      // 1991: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1994: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1997: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 199a: aastore
      // 199b: dup
      // 199c: bipush 35
      // 199e: new net/minecraft/client/animation/Keyframe
      // 19a1: dup
      // 19a2: ldc 1.46
      // 19a4: ldc_w 24.71
      // 19a7: ldc_w 3.336
      // 19aa: ldc_w -44.283
      // 19ad: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 19b0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19b3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19b6: aastore
      // 19b7: dup
      // 19b8: bipush 36
      // 19ba: new net/minecraft/client/animation/Keyframe
      // 19bd: dup
      // 19be: ldc 1.5
      // 19c0: ldc_w 35.032
      // 19c3: ldc_w 4.632
      // 19c6: ldc_w -19.692
      // 19c9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 19cc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19cf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19d2: aastore
      // 19d3: dup
      // 19d4: bipush 37
      // 19d6: new net/minecraft/client/animation/Keyframe
      // 19d9: dup
      // 19da: ldc 1.54
      // 19dc: ldc_w 39.621
      // 19df: ldc_w 5.057
      // 19e2: ldc_w -7.223
      // 19e5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 19e8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19eb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19ee: aastore
      // 19ef: dup
      // 19f0: bipush 38
      // 19f2: new net/minecraft/client/animation/Keyframe
      // 19f5: dup
      // 19f6: ldc 1.58
      // 19f8: ldc_w 39.771
      // 19fb: ldc_w 5.006
      // 19fe: ldc_w -4.726
      // 1a01: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a04: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a07: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a0a: aastore
      // 1a0b: dup
      // 1a0c: bipush 39
      // 1a0e: new net/minecraft/client/animation/Keyframe
      // 1a11: dup
      // 1a12: ldc 1.62
      // 1a14: ldc_w 39.841
      // 1a17: ldc_w 5.1
      // 1a1a: ldc_w -3.104
      // 1a1d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a20: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a23: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a26: aastore
      // 1a27: dup
      // 1a28: bipush 40
      // 1a2a: new net/minecraft/client/animation/Keyframe
      // 1a2d: dup
      // 1a2e: ldc 1.67
      // 1a30: ldc_w 39.906
      // 1a33: ldc_w 5.183
      // 1a36: ldc_w -1.632
      // 1a39: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a3c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a3f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a42: aastore
      // 1a43: dup
      // 1a44: bipush 41
      // 1a46: new net/minecraft/client/animation/Keyframe
      // 1a49: dup
      // 1a4a: ldc 1.71
      // 1a4c: ldc_w 39.881
      // 1a4f: ldc_w 5.152
      // 1a52: ldc_w -2.192
      // 1a55: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a58: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a5b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a5e: aastore
      // 1a5f: dup
      // 1a60: bipush 42
      // 1a62: new net/minecraft/client/animation/Keyframe
      // 1a65: dup
      // 1a66: ldc 1.75
      // 1a68: ldc_w 39.8
      // 1a6b: ldc_w 5.045
      // 1a6e: ldc_w -4.057
      // 1a71: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a74: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a77: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a7a: aastore
      // 1a7b: dup
      // 1a7c: bipush 43
      // 1a7e: new net/minecraft/client/animation/Keyframe
      // 1a81: dup
      // 1a82: ldc 1.79
      // 1a84: ldc_w 39.709
      // 1a87: ldc_w 4.916
      // 1a8a: ldc_w -6.246
      // 1a8d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a90: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a93: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a96: aastore
      // 1a97: dup
      // 1a98: bipush 44
      // 1a9a: new net/minecraft/client/animation/Keyframe
      // 1a9d: dup
      // 1a9e: ldc_w 1.83
      // 1aa1: ldc_w 38.988
      // 1aa4: ldc_w 5.575
      // 1aa7: ldc_w -8.928
      // 1aaa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1aad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ab0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ab3: aastore
      // 1ab4: dup
      // 1ab5: bipush 45
      // 1ab7: new net/minecraft/client/animation/Keyframe
      // 1aba: dup
      // 1abb: ldc_w 1.88
      // 1abe: ldc_w 37.044
      // 1ac1: ldc_w 7.427
      // 1ac4: ldc_w -12.971
      // 1ac7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1aca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1acd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ad0: aastore
      // 1ad1: dup
      // 1ad2: bipush 46
      // 1ad4: new net/minecraft/client/animation/Keyframe
      // 1ad7: dup
      // 1ad8: ldc_w 1.92
      // 1adb: ldc_w 34.073
      // 1ade: ldc_w 9.753
      // 1ae1: ldc_w -18.529
      // 1ae4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ae7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1aea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1aed: aastore
      // 1aee: dup
      // 1aef: bipush 47
      // 1af1: new net/minecraft/client/animation/Keyframe
      // 1af4: dup
      // 1af5: ldc_w 1.96
      // 1af8: ldc_w 30.463
      // 1afb: ldc_w 11.701
      // 1afe: ldc_w -25.624
      // 1b01: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b04: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b07: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b0a: aastore
      // 1b0b: dup
      // 1b0c: bipush 48
      // 1b0e: new net/minecraft/client/animation/Keyframe
      // 1b11: dup
      // 1b12: fconst_2
      // 1b13: ldc_w 26.861
      // 1b16: ldc_w 12.465
      // 1b19: ldc_w -33.963
      // 1b1c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b1f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b22: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b25: aastore
      // 1b26: dup
      // 1b27: bipush 49
      // 1b29: new net/minecraft/client/animation/Keyframe
      // 1b2c: dup
      // 1b2d: ldc_w 2.04
      // 1b30: ldc_w 24.141
      // 1b33: ldc_w 11.527
      // 1b36: ldc_w -42.877
      // 1b39: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b3c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b3f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b42: aastore
      // 1b43: dup
      // 1b44: bipush 50
      // 1b46: new net/minecraft/client/animation/Keyframe
      // 1b49: dup
      // 1b4a: ldc_w 2.08
      // 1b4d: ldc_w 23.153
      // 1b50: ldc_w 8.83
      // 1b53: ldc_w -51.51
      // 1b56: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b59: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b5c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b5f: aastore
      // 1b60: dup
      // 1b61: bipush 51
      // 1b63: new net/minecraft/client/animation/Keyframe
      // 1b66: dup
      // 1b67: ldc_w 2.12
      // 1b6a: ldc_w 24.367
      // 1b6d: ldc_w 4.771
      // 1b70: ldc_w -59.005
      // 1b73: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b76: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b79: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b7c: aastore
      // 1b7d: dup
      // 1b7e: bipush 52
      // 1b80: new net/minecraft/client/animation/Keyframe
      // 1b83: dup
      // 1b84: ldc_w 2.17
      // 1b87: ldc_w 27.618
      // 1b8a: ldc_w 0.014
      // 1b8d: ldc_w -64.376
      // 1b90: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b93: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b96: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b99: aastore
      // 1b9a: dup
      // 1b9b: bipush 53
      // 1b9d: new net/minecraft/client/animation/Keyframe
      // 1ba0: dup
      // 1ba1: ldc_w 2.21
      // 1ba4: ldc_w 32.011
      // 1ba7: ldc_w -4.994
      // 1baa: ldc_w -66.751
      // 1bad: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1bb0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1bb3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1bb6: aastore
      // 1bb7: dup
      // 1bb8: bipush 54
      // 1bba: new net/minecraft/client/animation/Keyframe
      // 1bbd: dup
      // 1bbe: ldc 2.25
      // 1bc0: ldc_w 36.098
      // 1bc3: ldc_w -10.165
      // 1bc6: ldc_w -65.42
      // 1bc9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1bcc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1bcf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1bd2: aastore
      // 1bd3: dup
      // 1bd4: bipush 55
      // 1bd6: new net/minecraft/client/animation/Keyframe
      // 1bd9: dup
      // 1bda: ldc 2.29
      // 1bdc: ldc_w 38.154
      // 1bdf: ldc_w -15.429
      // 1be2: ldc_w -59.823
      // 1be5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1be8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1beb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1bee: aastore
      // 1bef: dup
      // 1bf0: bipush 56
      // 1bf2: new net/minecraft/client/animation/Keyframe
      // 1bf5: dup
      // 1bf6: ldc 2.33
      // 1bf8: ldc_w 36.539
      // 1bfb: ldc_w -20.115
      // 1bfe: ldc_w -49.538
      // 1c01: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c04: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c07: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c0a: aastore
      // 1c0b: dup
      // 1c0c: bipush 57
      // 1c0e: new net/minecraft/client/animation/Keyframe
      // 1c11: dup
      // 1c12: ldc 2.38
      // 1c14: ldc_w 27.666
      // 1c17: ldc_w -21.969
      // 1c1a: ldc_w -35.231
      // 1c1d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c20: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c23: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c26: aastore
      // 1c27: dup
      // 1c28: bipush 58
      // 1c2a: new net/minecraft/client/animation/Keyframe
      // 1c2d: dup
      // 1c2e: ldc 2.42
      // 1c30: ldc_w 13.713
      // 1c33: ldc_w -19.124
      // 1c36: ldc_w -20.254
      // 1c39: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c3c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c3f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c42: aastore
      // 1c43: dup
      // 1c44: bipush 59
      // 1c46: new net/minecraft/client/animation/Keyframe
      // 1c49: dup
      // 1c4a: ldc 2.46
      // 1c4c: ldc_w 2.364
      // 1c4f: ldc_w -12.557
      // 1c52: ldc_w -8.445
      // 1c55: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c58: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c5b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c5e: aastore
      // 1c5f: dup
      // 1c60: bipush 60
      // 1c62: new net/minecraft/client/animation/Keyframe
      // 1c65: dup
      // 1c66: ldc 2.5
      // 1c68: ldc_w -3.04
      // 1c6b: ldc_w -4.758
      // 1c6e: ldc_w -2.105
      // 1c71: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c74: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c77: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c7a: aastore
      // 1c7b: dup
      // 1c7c: bipush 61
      // 1c7e: new net/minecraft/client/animation/Keyframe
      // 1c81: dup
      // 1c82: ldc 2.54
      // 1c84: ldc_w -5.461
      // 1c87: ldc_w 2.703
      // 1c8a: ldc_w -0.696
      // 1c8d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c90: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c93: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c96: aastore
      // 1c97: dup
      // 1c98: bipush 62
      // 1c9a: new net/minecraft/client/animation/Keyframe
      // 1c9d: dup
      // 1c9e: ldc_w 2.58
      // 1ca1: ldc_w -3.935
      // 1ca4: ldc_w 4.88
      // 1ca7: ldc_w -2.89
      // 1caa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1cad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1cb0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1cb3: aastore
      // 1cb4: dup
      // 1cb5: bipush 63
      // 1cb7: new net/minecraft/client/animation/Keyframe
      // 1cba: dup
      // 1cbb: ldc_w 2.62
      // 1cbe: ldc_w 2.335
      // 1cc1: ldc_w 0.792
      // 1cc4: ldc_w -6.213
      // 1cc7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1cca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ccd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1cd0: aastore
      // 1cd1: dup
      // 1cd2: bipush 64
      // 1cd4: new net/minecraft/client/animation/Keyframe
      // 1cd7: dup
      // 1cd8: ldc_w 2.67
      // 1cdb: ldc_w 10.012
      // 1cde: ldc_w -5.138
      // 1ce1: ldc_w -9.69
      // 1ce4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ce7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1cea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ced: aastore
      // 1cee: dup
      // 1cef: bipush 65
      // 1cf1: new net/minecraft/client/animation/Keyframe
      // 1cf4: dup
      // 1cf5: ldc_w 2.71
      // 1cf8: ldc_w 14.903
      // 1cfb: ldc_w -9.286
      // 1cfe: ldc_w -12.06
      // 1d01: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d04: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d07: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d0a: aastore
      // 1d0b: dup
      // 1d0c: bipush 66
      // 1d0e: new net/minecraft/client/animation/Keyframe
      // 1d11: dup
      // 1d12: ldc_w 2.75
      // 1d15: ldc_w 11.911
      // 1d18: ldc_w -8.98
      // 1d1b: ldc_w -10.482
      // 1d1e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d21: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d24: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d27: aastore
      // 1d28: dup
      // 1d29: bipush 67
      // 1d2b: new net/minecraft/client/animation/Keyframe
      // 1d2e: dup
      // 1d2f: ldc_w 2.79
      // 1d32: ldc_w 4.489
      // 1d35: ldc_w -5.983
      // 1d38: ldc_w -6.573
      // 1d3b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d3e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d41: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d44: aastore
      // 1d45: dup
      // 1d46: bipush 68
      // 1d48: new net/minecraft/client/animation/Keyframe
      // 1d4b: dup
      // 1d4c: ldc 2.83
      // 1d4e: ldc_w 0.412
      // 1d51: ldc_w -4.079
      // 1d54: ldc_w -4.432
      // 1d57: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d5a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d5d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d60: aastore
      // 1d61: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1d64: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1d67: ldc_w "CTR_head"
      // 1d6a: new net/minecraft/client/animation/AnimationChannel
      // 1d6d: dup
      // 1d6e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1d71: bipush 14
      // 1d73: anewarray 28
      // 1d76: dup
      // 1d77: bipush 0
      // 1d78: new net/minecraft/client/animation/Keyframe
      // 1d7b: dup
      // 1d7c: ldc 1.33
      // 1d7e: fconst_0
      // 1d7f: fconst_0
      // 1d80: fconst_0
      // 1d81: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d84: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d87: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d8a: aastore
      // 1d8b: dup
      // 1d8c: bipush 1
      // 1d8d: new net/minecraft/client/animation/Keyframe
      // 1d90: dup
      // 1d91: ldc 1.38
      // 1d93: ldc_w -0.026
      // 1d96: ldc_w -0.131
      // 1d99: ldc_w -0.235
      // 1d9c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d9f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1da2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1da5: aastore
      // 1da6: dup
      // 1da7: bipush 2
      // 1da8: new net/minecraft/client/animation/Keyframe
      // 1dab: dup
      // 1dac: ldc 1.42
      // 1dae: ldc_w -0.086
      // 1db1: ldc_w -0.443
      // 1db4: ldc_w -0.796
      // 1db7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1dba: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1dbd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1dc0: aastore
      // 1dc1: dup
      // 1dc2: bipush 3
      // 1dc3: new net/minecraft/client/animation/Keyframe
      // 1dc6: dup
      // 1dc7: ldc 1.46
      // 1dc9: ldc_w -0.159
      // 1dcc: ldc_w -0.815
      // 1dcf: ldc_w -1.466
      // 1dd2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1dd5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1dd8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ddb: aastore
      // 1ddc: dup
      // 1ddd: bipush 4
      // 1dde: new net/minecraft/client/animation/Keyframe
      // 1de1: dup
      // 1de2: ldc 1.5
      // 1de4: ldc_w -0.22
      // 1de7: ldc_w -1.126
      // 1dea: ldc_w -2.028
      // 1ded: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1df0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1df3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1df6: aastore
      // 1df7: dup
      // 1df8: bipush 5
      // 1df9: new net/minecraft/client/animation/Keyframe
      // 1dfc: dup
      // 1dfd: ldc 1.54
      // 1dff: ldc_w -0.245
      // 1e02: ldc_w -1.257
      // 1e05: ldc_w -2.263
      // 1e08: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e0b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e0e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e11: aastore
      // 1e12: dup
      // 1e13: bipush 6
      // 1e15: new net/minecraft/client/animation/Keyframe
      // 1e18: dup
      // 1e19: ldc 1.71
      // 1e1b: ldc_w -0.245
      // 1e1e: ldc_w -1.257
      // 1e21: ldc_w -2.263
      // 1e24: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e27: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e2a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e2d: aastore
      // 1e2e: dup
      // 1e2f: bipush 7
      // 1e31: new net/minecraft/client/animation/Keyframe
      // 1e34: dup
      // 1e35: ldc 1.75
      // 1e37: ldc_w -0.237
      // 1e3a: ldc_w -1.214
      // 1e3d: ldc_w -2.185
      // 1e40: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e43: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e46: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e49: aastore
      // 1e4a: dup
      // 1e4b: bipush 8
      // 1e4d: new net/minecraft/client/animation/Keyframe
      // 1e50: dup
      // 1e51: ldc 1.79
      // 1e53: ldc_w -0.214
      // 1e56: ldc_w -1.098
      // 1e59: ldc_w -1.977
      // 1e5c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e5f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e62: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e65: aastore
      // 1e66: dup
      // 1e67: bipush 9
      // 1e69: new net/minecraft/client/animation/Keyframe
      // 1e6c: dup
      // 1e6d: ldc_w 1.88
      // 1e70: ldc_w -0.143
      // 1e73: ldc_w -0.733
      // 1e76: ldc_w -1.319
      // 1e79: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e7c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e7f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e82: aastore
      // 1e83: dup
      // 1e84: bipush 10
      // 1e86: new net/minecraft/client/animation/Keyframe
      // 1e89: dup
      // 1e8a: ldc_w 1.96
      // 1e8d: ldc_w -0.064
      // 1e90: ldc_w -0.326
      // 1e93: ldc_w -0.586
      // 1e96: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e99: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e9c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e9f: aastore
      // 1ea0: dup
      // 1ea1: bipush 11
      // 1ea3: new net/minecraft/client/animation/Keyframe
      // 1ea6: dup
      // 1ea7: fconst_2
      // 1ea8: ldc_w -0.031
      // 1eab: ldc_w -0.159
      // 1eae: ldc_w -0.285
      // 1eb1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1eb4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1eb7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1eba: aastore
      // 1ebb: dup
      // 1ebc: bipush 12
      // 1ebe: new net/minecraft/client/animation/Keyframe
      // 1ec1: dup
      // 1ec2: ldc_w 2.04
      // 1ec5: ldc_w -0.009
      // 1ec8: ldc_w -0.043
      // 1ecb: ldc_w -0.077
      // 1ece: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1ed1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ed4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ed7: aastore
      // 1ed8: dup
      // 1ed9: bipush 13
      // 1edb: new net/minecraft/client/animation/Keyframe
      // 1ede: dup
      // 1edf: ldc_w 2.08
      // 1ee2: fconst_0
      // 1ee3: fconst_0
      // 1ee4: fconst_0
      // 1ee5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1ee8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1eeb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1eee: aastore
      // 1eef: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1ef2: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1ef5: ldc_w "CTR_shoulde1r_left"
      // 1ef8: new net/minecraft/client/animation/AnimationChannel
      // 1efb: dup
      // 1efc: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1eff: bipush 11
      // 1f01: anewarray 28
      // 1f04: dup
      // 1f05: bipush 0
      // 1f06: new net/minecraft/client/animation/Keyframe
      // 1f09: dup
      // 1f0a: ldc 1.29
      // 1f0c: fconst_0
      // 1f0d: fconst_0
      // 1f0e: fconst_0
      // 1f0f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f12: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f15: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f18: aastore
      // 1f19: dup
      // 1f1a: bipush 1
      // 1f1b: new net/minecraft/client/animation/Keyframe
      // 1f1e: dup
      // 1f1f: ldc 1.33
      // 1f21: ldc_w -0.446
      // 1f24: ldc_w 1.507
      // 1f27: ldc_w -0.171
      // 1f2a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f2d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f30: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f33: aastore
      // 1f34: dup
      // 1f35: bipush 2
      // 1f36: new net/minecraft/client/animation/Keyframe
      // 1f39: dup
      // 1f3a: ldc 1.38
      // 1f3c: ldc_w -0.892
      // 1f3f: ldc_w 3.013
      // 1f42: ldc_w -0.342
      // 1f45: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f48: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f4b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f4e: aastore
      // 1f4f: dup
      // 1f50: bipush 3
      // 1f51: new net/minecraft/client/animation/Keyframe
      // 1f54: dup
      // 1f55: ldc 1.58
      // 1f57: ldc_w -0.879
      // 1f5a: ldc_w 2.969
      // 1f5d: ldc_w -0.337
      // 1f60: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f63: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f66: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f69: aastore
      // 1f6a: dup
      // 1f6b: bipush 4
      // 1f6c: new net/minecraft/client/animation/Keyframe
      // 1f6f: dup
      // 1f70: ldc_w 1.88
      // 1f73: ldc_w -0.718
      // 1f76: ldc_w 2.425
      // 1f79: ldc_w -0.275
      // 1f7c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f7f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f82: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f85: aastore
      // 1f86: dup
      // 1f87: bipush 5
      // 1f88: new net/minecraft/client/animation/Keyframe
      // 1f8b: dup
      // 1f8c: ldc_w 2.04
      // 1f8f: ldc_w -0.526
      // 1f92: ldc_w 1.779
      // 1f95: ldc_w -0.202
      // 1f98: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f9b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f9e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1fa1: aastore
      // 1fa2: dup
      // 1fa3: bipush 6
      // 1fa5: new net/minecraft/client/animation/Keyframe
      // 1fa8: dup
      // 1fa9: ldc_w 2.21
      // 1fac: ldc_w -0.237
      // 1faf: ldc_w 0.801
      // 1fb2: ldc_w -0.091
      // 1fb5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1fb8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1fbb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1fbe: aastore
      // 1fbf: dup
      // 1fc0: bipush 7
      // 1fc2: new net/minecraft/client/animation/Keyframe
      // 1fc5: dup
      // 1fc6: ldc 2.29
      // 1fc8: ldc_w -0.172
      // 1fcb: ldc_w 0.582
      // 1fce: ldc_w -0.066
      // 1fd1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1fd4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1fd7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1fda: aastore
      // 1fdb: dup
      // 1fdc: bipush 8
      // 1fde: new net/minecraft/client/animation/Keyframe
      // 1fe1: dup
      // 1fe2: ldc_w 2.58
      // 1fe5: ldc_w -0.163
      // 1fe8: ldc_w 0.55
      // 1feb: ldc_w -0.062
      // 1fee: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ff1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ff4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ff7: aastore
      // 1ff8: dup
      // 1ff9: bipush 9
      // 1ffb: new net/minecraft/client/animation/Keyframe
      // 1ffe: dup
      // 1fff: ldc_w 2.79
      // 2002: ldc_w -0.009
      // 2005: ldc_w 0.032
      // 2008: ldc_w -0.003
      // 200b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 200e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2011: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2014: aastore
      // 2015: dup
      // 2016: bipush 10
      // 2018: new net/minecraft/client/animation/Keyframe
      // 201b: dup
      // 201c: ldc 2.83
      // 201e: fconst_0
      // 201f: fconst_0
      // 2020: fconst_0
      // 2021: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2024: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2027: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 202a: aastore
      // 202b: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 202e: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2031: ldc_w "CTR_shoulde1r_left"
      // 2034: new net/minecraft/client/animation/AnimationChannel
      // 2037: dup
      // 2038: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 203b: bipush 65
      // 203d: anewarray 28
      // 2040: dup
      // 2041: bipush 0
      // 2042: new net/minecraft/client/animation/Keyframe
      // 2045: dup
      // 2046: fconst_0
      // 2047: ldc_w -0.112
      // 204a: ldc_w -0.19
      // 204d: ldc_w -0.487
      // 2050: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2053: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2056: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2059: aastore
      // 205a: dup
      // 205b: bipush 1
      // 205c: new net/minecraft/client/animation/Keyframe
      // 205f: dup
      // 2060: ldc 0.04
      // 2062: ldc_w -0.043
      // 2065: ldc_w -0.187
      // 2068: ldc_w -0.678
      // 206b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 206e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2071: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2074: aastore
      // 2075: dup
      // 2076: bipush 2
      // 2077: new net/minecraft/client/animation/Keyframe
      // 207a: dup
      // 207b: ldc 0.08
      // 207d: ldc_w 0.146
      // 2080: ldc_w -0.177
      // 2083: ldc_w -1.208
      // 2086: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2089: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 208c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 208f: aastore
      // 2090: dup
      // 2091: bipush 3
      // 2092: new net/minecraft/client/animation/Keyframe
      // 2095: dup
      // 2096: ldc 0.17
      // 2098: ldc_w 0.78
      // 209b: ldc_w -0.114
      // 209e: ldc_w -3.03
      // 20a1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 20a4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20a7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20aa: aastore
      // 20ab: dup
      // 20ac: bipush 4
      // 20ad: new net/minecraft/client/animation/Keyframe
      // 20b0: dup
      // 20b1: ldc 0.21
      // 20b3: ldc 1.17
      // 20b5: ldc_w -0.051
      // 20b8: ldc_w -4.197
      // 20bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 20be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20c4: aastore
      // 20c5: dup
      // 20c6: bipush 5
      // 20c7: new net/minecraft/client/animation/Keyframe
      // 20ca: dup
      // 20cb: ldc 0.25
      // 20cd: ldc_w 1.574
      // 20d0: ldc 0.04
      // 20d2: ldc_w -5.448
      // 20d5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 20d8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20db: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20de: aastore
      // 20df: dup
      // 20e0: bipush 6
      // 20e2: new net/minecraft/client/animation/Keyframe
      // 20e5: dup
      // 20e6: ldc 0.29
      // 20e8: ldc_w 1.964
      // 20eb: ldc_w 0.163
      // 20ee: ldc_w -6.722
      // 20f1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 20f4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20f7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20fa: aastore
      // 20fb: dup
      // 20fc: bipush 7
      // 20fe: new net/minecraft/client/animation/Keyframe
      // 2101: dup
      // 2102: ldc 0.33
      // 2104: ldc_w 2.314
      // 2107: ldc_w 0.324
      // 210a: ldc_w -7.954
      // 210d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2110: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2113: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2116: aastore
      // 2117: dup
      // 2118: bipush 8
      // 211a: new net/minecraft/client/animation/Keyframe
      // 211d: dup
      // 211e: ldc 0.38
      // 2120: ldc_w 2.597
      // 2123: ldc_w 0.528
      // 2126: ldc_w -9.082
      // 2129: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 212c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 212f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2132: aastore
      // 2133: dup
      // 2134: bipush 9
      // 2136: new net/minecraft/client/animation/Keyframe
      // 2139: dup
      // 213a: ldc 0.42
      // 213c: ldc_w 2.787
      // 213f: ldc_w 0.778
      // 2142: ldc_w -10.041
      // 2145: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2148: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 214b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 214e: aastore
      // 214f: dup
      // 2150: bipush 10
      // 2152: new net/minecraft/client/animation/Keyframe
      // 2155: dup
      // 2156: ldc 0.46
      // 2158: ldc_w 2.856
      // 215b: ldc_w 1.082
      // 215e: ldc_w -10.768
      // 2161: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2164: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2167: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 216a: aastore
      // 216b: dup
      // 216c: bipush 11
      // 216e: new net/minecraft/client/animation/Keyframe
      // 2171: dup
      // 2172: ldc 0.5
      // 2174: ldc_w 2.803
      // 2177: ldc_w 1.437
      // 217a: ldc_w -11.223
      // 217d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2180: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2183: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2186: aastore
      // 2187: dup
      // 2188: bipush 12
      // 218a: new net/minecraft/client/animation/Keyframe
      // 218d: dup
      // 218e: ldc 0.54
      // 2190: ldc_w 2.664
      // 2193: ldc_w 1.826
      // 2196: ldc_w -11.461
      // 2199: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 219c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 219f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21a2: aastore
      // 21a3: dup
      // 21a4: bipush 13
      // 21a6: new net/minecraft/client/animation/Keyframe
      // 21a9: dup
      // 21aa: ldc 0.58
      // 21ac: ldc_w 2.467
      // 21af: ldc_w 2.222
      // 21b2: ldc_w -11.556
      // 21b5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 21b8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21bb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21be: aastore
      // 21bf: dup
      // 21c0: bipush 14
      // 21c2: new net/minecraft/client/animation/Keyframe
      // 21c5: dup
      // 21c6: ldc 0.62
      // 21c8: ldc_w 2.241
      // 21cb: ldc_w 2.601
      // 21ce: ldc_w -11.588
      // 21d1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 21d4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21d7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21da: aastore
      // 21db: dup
      // 21dc: bipush 15
      // 21de: new net/minecraft/client/animation/Keyframe
      // 21e1: dup
      // 21e2: ldc 0.67
      // 21e4: ldc_w 2.015
      // 21e7: ldc_w 2.94
      // 21ea: ldc_w -11.631
      // 21ed: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 21f0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21f3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21f6: aastore
      // 21f7: dup
      // 21f8: bipush 16
      // 21fa: new net/minecraft/client/animation/Keyframe
      // 21fd: dup
      // 21fe: ldc 0.71
      // 2200: ldc_w 1.818
      // 2203: ldc_w 3.212
      // 2206: ldc_w -11.763
      // 2209: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 220c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 220f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2212: aastore
      // 2213: dup
      // 2214: bipush 17
      // 2216: new net/minecraft/client/animation/Keyframe
      // 2219: dup
      // 221a: ldc 0.75
      // 221c: ldc_w 1.678
      // 221f: ldc_w 3.393
      // 2222: ldc_w -12.062
      // 2225: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2228: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 222b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 222e: aastore
      // 222f: dup
      // 2230: bipush 18
      // 2232: new net/minecraft/client/animation/Keyframe
      // 2235: dup
      // 2236: ldc 0.79
      // 2238: ldc_w 1.626
      // 223b: ldc_w 3.459
      // 223e: ldc_w -12.603
      // 2241: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2244: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2247: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 224a: aastore
      // 224b: dup
      // 224c: bipush 19
      // 224e: new net/minecraft/client/animation/Keyframe
      // 2251: dup
      // 2252: ldc 0.83
      // 2254: ldc_w 2.309
      // 2257: ldc_w 2.652
      // 225a: ldc_w -13.806
      // 225d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2260: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2263: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2266: aastore
      // 2267: dup
      // 2268: bipush 20
      // 226a: new net/minecraft/client/animation/Keyframe
      // 226d: dup
      // 226e: ldc 0.88
      // 2270: ldc_w 3.58
      // 2273: ldc_w 0.817
      // 2276: ldc_w -15.771
      // 2279: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 227c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 227f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2282: aastore
      // 2283: dup
      // 2284: bipush 21
      // 2286: new net/minecraft/client/animation/Keyframe
      // 2289: dup
      // 228a: ldc 0.92
      // 228c: ldc_w 4.264
      // 228f: ldc_w -1.163
      // 2292: ldc_w -18.094
      // 2295: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2298: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 229b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 229e: aastore
      // 229f: dup
      // 22a0: bipush 22
      // 22a2: new net/minecraft/client/animation/Keyframe
      // 22a5: dup
      // 22a6: ldc 0.96
      // 22a8: ldc_w 4.199
      // 22ab: ldc_w -2.713
      // 22ae: ldc_w -20.43
      // 22b1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 22b4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22b7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22ba: aastore
      // 22bb: dup
      // 22bc: bipush 23
      // 22be: new net/minecraft/client/animation/Keyframe
      // 22c1: dup
      // 22c2: fconst_1
      // 22c3: ldc_w 4.018
      // 22c6: ldc_w -3.878
      // 22c9: ldc_w -22.658
      // 22cc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 22cf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22d2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22d5: aastore
      // 22d6: dup
      // 22d7: bipush 24
      // 22d9: new net/minecraft/client/animation/Keyframe
      // 22dc: dup
      // 22dd: ldc 1.04
      // 22df: ldc_w 3.737
      // 22e2: ldc_w -4.712
      // 22e5: ldc_w -24.715
      // 22e8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 22eb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22ee: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22f1: aastore
      // 22f2: dup
      // 22f3: bipush 25
      // 22f5: new net/minecraft/client/animation/Keyframe
      // 22f8: dup
      // 22f9: ldc 1.08
      // 22fb: ldc_w 3.373
      // 22fe: ldc_w -5.272
      // 2301: ldc_w -26.539
      // 2304: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2307: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 230a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 230d: aastore
      // 230e: dup
      // 230f: bipush 26
      // 2311: new net/minecraft/client/animation/Keyframe
      // 2314: dup
      // 2315: ldc 1.12
      // 2317: ldc_w 2.945
      // 231a: ldc_w -5.611
      // 231d: ldc_w -28.068
      // 2320: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2323: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2326: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2329: aastore
      // 232a: dup
      // 232b: bipush 27
      // 232d: new net/minecraft/client/animation/Keyframe
      // 2330: dup
      // 2331: ldc 1.17
      // 2333: ldc_w 2.468
      // 2336: ldc_w -5.785
      // 2339: ldc_w -29.237
      // 233c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 233f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2342: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2345: aastore
      // 2346: dup
      // 2347: bipush 28
      // 2349: new net/minecraft/client/animation/Keyframe
      // 234c: dup
      // 234d: ldc 1.21
      // 234f: ldc_w 1.96
      // 2352: ldc_w -5.85
      // 2355: ldc_w -29.985
      // 2358: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 235b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 235e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2361: aastore
      // 2362: dup
      // 2363: bipush 29
      // 2365: new net/minecraft/client/animation/Keyframe
      // 2368: dup
      // 2369: ldc 1.25
      // 236b: ldc_w 1.439
      // 236e: ldc_w -5.859
      // 2371: ldc_w -30.248
      // 2374: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2377: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 237a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 237d: aastore
      // 237e: dup
      // 237f: bipush 30
      // 2381: new net/minecraft/client/animation/Keyframe
      // 2384: dup
      // 2385: ldc 1.29
      // 2387: ldc_w 1.091
      // 238a: ldc_w -5.846
      // 238d: ldc_w -19.904
      // 2390: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2393: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2396: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2399: aastore
      // 239a: dup
      // 239b: bipush 31
      // 239d: new net/minecraft/client/animation/Keyframe
      // 23a0: dup
      // 23a1: ldc 1.33
      // 23a3: ldc_w 1.102
      // 23a6: ldc_w -4.813
      // 23a9: ldc_w -19.971
      // 23ac: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 23af: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23b2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23b5: aastore
      // 23b6: dup
      // 23b7: bipush 32
      // 23b9: new net/minecraft/client/animation/Keyframe
      // 23bc: dup
      // 23bd: ldc 1.38
      // 23bf: ldc_w 1.112
      // 23c2: ldc_w -3.798
      // 23c5: ldc_w -20.039
      // 23c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 23cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23d1: aastore
      // 23d2: dup
      // 23d3: bipush 33
      // 23d5: new net/minecraft/client/animation/Keyframe
      // 23d8: dup
      // 23d9: ldc 1.54
      // 23db: ldc_w 1.112
      // 23de: ldc_w -3.798
      // 23e1: ldc_w -20.039
      // 23e4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 23e7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23ea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23ed: aastore
      // 23ee: dup
      // 23ef: bipush 34
      // 23f1: new net/minecraft/client/animation/Keyframe
      // 23f4: dup
      // 23f5: ldc 1.58
      // 23f7: ldc_w 1.426
      // 23fa: ldc_w -3.823
      // 23fd: ldc_w -20.991
      // 2400: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2403: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2406: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2409: aastore
      // 240a: dup
      // 240b: bipush 35
      // 240d: new net/minecraft/client/animation/Keyframe
      // 2410: dup
      // 2411: ldc 1.62
      // 2413: ldc_w 2.009
      // 2416: ldc_w -3.871
      // 2419: ldc_w -22.759
      // 241c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 241f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2422: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2425: aastore
      // 2426: dup
      // 2427: bipush 36
      // 2429: new net/minecraft/client/animation/Keyframe
      // 242c: dup
      // 242d: ldc 1.67
      // 242f: ldc_w 2.323
      // 2432: ldc_w -3.897
      // 2435: ldc_w -23.711
      // 2438: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 243b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 243e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2441: aastore
      // 2442: dup
      // 2443: bipush 37
      // 2445: new net/minecraft/client/animation/Keyframe
      // 2448: dup
      // 2449: ldc 1.71
      // 244b: ldc_w 2.314
      // 244e: ldc_w -3.895
      // 2451: ldc_w -23.623
      // 2454: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2457: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 245a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 245d: aastore
      // 245e: dup
      // 245f: bipush 38
      // 2461: new net/minecraft/client/animation/Keyframe
      // 2464: dup
      // 2465: ldc 1.75
      // 2467: ldc_w 2.297
      // 246a: ldc_w -3.881
      // 246d: ldc_w -23.46
      // 2470: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2473: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2476: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2479: aastore
      // 247a: dup
      // 247b: bipush 39
      // 247d: new net/minecraft/client/animation/Keyframe
      // 2480: dup
      // 2481: ldc 1.79
      // 2483: ldc_w 2.287
      // 2486: ldc_w -3.843
      // 2489: ldc_w -23.372
      // 248c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 248f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2492: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2495: aastore
      // 2496: dup
      // 2497: bipush 40
      // 2499: new net/minecraft/client/animation/Keyframe
      // 249c: dup
      // 249d: ldc_w 1.83
      // 24a0: ldc_w 2.376
      // 24a3: ldc_w -3.706
      // 24a6: ldc_w -23.912
      // 24a9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 24ac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24af: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24b2: aastore
      // 24b3: dup
      // 24b4: bipush 41
      // 24b6: new net/minecraft/client/animation/Keyframe
      // 24b9: dup
      // 24ba: ldc_w 1.88
      // 24bd: ldc_w 2.604
      // 24c0: ldc_w -3.42
      // 24c3: ldc_w -25.304
      // 24c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 24c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24cf: aastore
      // 24d0: dup
      // 24d1: bipush 42
      // 24d3: new net/minecraft/client/animation/Keyframe
      // 24d6: dup
      // 24d7: ldc_w 1.92
      // 24da: ldc_w 2.916
      // 24dd: ldc_w -3.011
      // 24e0: ldc_w -27.207
      // 24e3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 24e6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24e9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24ec: aastore
      // 24ed: dup
      // 24ee: bipush 43
      // 24f0: new net/minecraft/client/animation/Keyframe
      // 24f3: dup
      // 24f4: ldc_w 1.96
      // 24f7: ldc_w 3.255
      // 24fa: ldc_w -2.503
      // 24fd: ldc_w -29.28
      // 2500: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2503: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2506: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2509: aastore
      // 250a: dup
      // 250b: bipush 44
      // 250d: new net/minecraft/client/animation/Keyframe
      // 2510: dup
      // 2511: fconst_2
      // 2512: ldc_w 3.567
      // 2515: ldc_w -1.924
      // 2518: ldc_w -31.183
      // 251b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 251e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2521: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2524: aastore
      // 2525: dup
      // 2526: bipush 45
      // 2528: new net/minecraft/client/animation/Keyframe
      // 252b: dup
      // 252c: ldc_w 2.04
      // 252f: ldc_w 3.795
      // 2532: ldc_w -1.298
      // 2535: ldc_w -32.575
      // 2538: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 253b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 253e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2541: aastore
      // 2542: dup
      // 2543: bipush 46
      // 2545: new net/minecraft/client/animation/Keyframe
      // 2548: dup
      // 2549: ldc_w 2.08
      // 254c: ldc_w 3.884
      // 254f: ldc_w -0.651
      // 2552: ldc_w -33.114
      // 2555: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2558: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 255b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 255e: aastore
      // 255f: dup
      // 2560: bipush 47
      // 2562: new net/minecraft/client/animation/Keyframe
      // 2565: dup
      // 2566: ldc_w 2.12
      // 2569: ldc_w 3.479
      // 256c: ldc_w 0.006
      // 256f: ldc_w -30.885
      // 2572: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2575: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2578: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 257b: aastore
      // 257c: dup
      // 257d: bipush 48
      // 257f: new net/minecraft/client/animation/Keyframe
      // 2582: dup
      // 2583: ldc_w 2.17
      // 2586: ldc_w 2.515
      // 2589: ldc_w 0.713
      // 258c: ldc_w -25.57
      // 258f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2592: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2595: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2598: aastore
      // 2599: dup
      // 259a: bipush 49
      // 259c: new net/minecraft/client/animation/Keyframe
      // 259f: dup
      // 25a0: ldc_w 2.21
      // 25a3: ldc_w 1.364
      // 25a6: ldc_w 1.528
      // 25a9: ldc_w -19.226
      // 25ac: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 25af: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25b2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25b5: aastore
      // 25b6: dup
      // 25b7: bipush 50
      // 25b9: new net/minecraft/client/animation/Keyframe
      // 25bc: dup
      // 25bd: ldc 2.25
      // 25bf: ldc_w 0.4
      // 25c2: ldc_w 2.504
      // 25c5: ldc_w -13.911
      // 25c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 25cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25d1: aastore
      // 25d2: dup
      // 25d3: bipush 51
      // 25d5: new net/minecraft/client/animation/Keyframe
      // 25d8: dup
      // 25d9: ldc 2.29
      // 25db: ldc_w -0.004
      // 25de: ldc_w 3.698
      // 25e1: ldc_w -11.682
      // 25e4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 25e7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25ea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25ed: aastore
      // 25ee: dup
      // 25ef: bipush 52
      // 25f1: new net/minecraft/client/animation/Keyframe
      // 25f4: dup
      // 25f5: ldc 2.33
      // 25f7: ldc_w 0.619
      // 25fa: ldc_w 5.0
      // 25fd: ldc_w -14.09
      // 2600: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2603: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2606: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2609: aastore
      // 260a: dup
      // 260b: bipush 53
      // 260d: new net/minecraft/client/animation/Keyframe
      // 2610: dup
      // 2611: ldc 2.38
      // 2613: ldc_w 1.451
      // 2616: ldc_w 5.642
      // 2619: ldc_w -16.498
      // 261c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 261f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2622: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2625: aastore
      // 2626: dup
      // 2627: bipush 54
      // 2629: new net/minecraft/client/animation/Keyframe
      // 262c: dup
      // 262d: ldc 2.42
      // 262f: ldc_w 1.774
      // 2632: ldc_w 3.382
      // 2635: ldc_w -15.456
      // 2638: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 263b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 263e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2641: aastore
      // 2642: dup
      // 2643: bipush 55
      // 2645: new net/minecraft/client/animation/Keyframe
      // 2648: dup
      // 2649: ldc 2.46
      // 264b: ldc_w 1.94
      // 264e: ldc_w -1.59
      // 2651: ldc_w -12.9
      // 2654: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2657: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 265a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 265d: aastore
      // 265e: dup
      // 265f: bipush 56
      // 2661: new net/minecraft/client/animation/Keyframe
      // 2664: dup
      // 2665: ldc 2.5
      // 2667: ldc_w 2.001
      // 266a: ldc_w -6.562
      // 266d: ldc_w -9.682
      // 2670: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2673: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2676: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2679: aastore
      // 267a: dup
      // 267b: bipush 57
      // 267d: new net/minecraft/client/animation/Keyframe
      // 2680: dup
      // 2681: ldc 2.54
      // 2683: ldc_w 2.01
      // 2686: ldc_w -8.822
      // 2689: ldc_w -6.657
      // 268c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 268f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2692: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2695: aastore
      // 2696: dup
      // 2697: bipush 58
      // 2699: new net/minecraft/client/animation/Keyframe
      // 269c: dup
      // 269d: ldc_w 2.58
      // 26a0: ldc_w 1.892
      // 26a3: ldc_w -8.343
      // 26a6: ldc_w -4.373
      // 26a9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 26ac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26af: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26b2: aastore
      // 26b3: dup
      // 26b4: bipush 59
      // 26b6: new net/minecraft/client/animation/Keyframe
      // 26b9: dup
      // 26ba: ldc_w 2.62
      // 26bd: ldc_w 1.589
      // 26c0: ldc_w -7.11
      // 26c3: ldc_w -2.736
      // 26c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 26c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26cf: aastore
      // 26d0: dup
      // 26d1: bipush 60
      // 26d3: new net/minecraft/client/animation/Keyframe
      // 26d6: dup
      // 26d7: ldc_w 2.67
      // 26da: ldc_w 1.175
      // 26dd: ldc_w -5.424
      // 26e0: ldc_w -1.638
      // 26e3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 26e6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26e9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26ec: aastore
      // 26ed: dup
      // 26ee: bipush 61
      // 26f0: new net/minecraft/client/animation/Keyframe
      // 26f3: dup
      // 26f4: ldc_w 2.71
      // 26f7: ldc_w 0.723
      // 26fa: ldc_w -3.587
      // 26fd: ldc_w -0.973
      // 2700: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2703: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2706: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2709: aastore
      // 270a: dup
      // 270b: bipush 62
      // 270d: new net/minecraft/client/animation/Keyframe
      // 2710: dup
      // 2711: ldc_w 2.75
      // 2714: ldc_w 0.308
      // 2717: ldc_w -1.901
      // 271a: ldc_w -0.631
      // 271d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2720: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2723: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2726: aastore
      // 2727: dup
      // 2728: bipush 63
      // 272a: new net/minecraft/client/animation/Keyframe
      // 272d: dup
      // 272e: ldc_w 2.79
      // 2731: ldc_w 0.005
      // 2734: ldc_w -0.668
      // 2737: ldc_w -0.505
      // 273a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 273d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2740: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2743: aastore
      // 2744: dup
      // 2745: bipush 64
      // 2747: new net/minecraft/client/animation/Keyframe
      // 274a: dup
      // 274b: ldc 2.83
      // 274d: ldc_w -0.112
      // 2750: ldc_w -0.19
      // 2753: ldc_w -0.487
      // 2756: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2759: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 275c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 275f: aastore
      // 2760: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2763: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2766: ldc_w "CTR_upperarm_left"
      // 2769: new net/minecraft/client/animation/AnimationChannel
      // 276c: dup
      // 276d: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2770: bipush 69
      // 2772: anewarray 28
      // 2775: dup
      // 2776: bipush 0
      // 2777: new net/minecraft/client/animation/Keyframe
      // 277a: dup
      // 277b: fconst_0
      // 277c: ldc_w -1.331
      // 277f: ldc_w -1.925
      // 2782: ldc_w -0.448
      // 2785: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2788: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 278b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 278e: aastore
      // 278f: dup
      // 2790: bipush 1
      // 2791: new net/minecraft/client/animation/Keyframe
      // 2794: dup
      // 2795: ldc 0.04
      // 2797: ldc_w -0.711
      // 279a: ldc_w -2.816
      // 279d: ldc_w -0.242
      // 27a0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 27a3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27a6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27a9: aastore
      // 27aa: dup
      // 27ab: bipush 2
      // 27ac: new net/minecraft/client/animation/Keyframe
      // 27af: dup
      // 27b0: ldc 0.08
      // 27b2: ldc_w 0.905
      // 27b5: ldc_w -5.254
      // 27b8: ldc_w 0.161
      // 27bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 27be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27c4: aastore
      // 27c5: dup
      // 27c6: bipush 3
      // 27c7: new net/minecraft/client/animation/Keyframe
      // 27ca: dup
      // 27cb: ldc 0.12
      // 27cd: ldc_w 3.147
      // 27d0: ldc_w -8.949
      // 27d3: ldc_w 0.254
      // 27d6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 27d9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27dc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27df: aastore
      // 27e0: dup
      // 27e1: bipush 4
      // 27e2: new net/minecraft/client/animation/Keyframe
      // 27e5: dup
      // 27e6: ldc 0.17
      // 27e8: ldc_w 5.71
      // 27eb: ldc_w -13.514
      // 27ee: ldc_w -0.553
      // 27f1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 27f4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27f7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27fa: aastore
      // 27fb: dup
      // 27fc: bipush 5
      // 27fd: new net/minecraft/client/animation/Keyframe
      // 2800: dup
      // 2801: ldc 0.21
      // 2803: ldc_w 8.379
      // 2806: ldc_w -18.364
      // 2809: ldc_w -2.733
      // 280c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 280f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2812: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2815: aastore
      // 2816: dup
      // 2817: bipush 6
      // 2819: new net/minecraft/client/animation/Keyframe
      // 281c: dup
      // 281d: ldc 0.25
      // 281f: ldc_w 10.879
      // 2822: ldc_w -22.783
      // 2825: ldc_w -6.324
      // 2828: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 282b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 282e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2831: aastore
      // 2832: dup
      // 2833: bipush 7
      // 2835: new net/minecraft/client/animation/Keyframe
      // 2838: dup
      // 2839: ldc 0.29
      // 283b: ldc_w 12.698
      // 283e: ldc_w -26.151
      // 2841: ldc_w -10.669
      // 2844: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2847: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 284a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 284d: aastore
      // 284e: dup
      // 284f: bipush 8
      // 2851: new net/minecraft/client/animation/Keyframe
      // 2854: dup
      // 2855: ldc 0.33
      // 2857: ldc_w 13.179
      // 285a: ldc_w -28.07
      // 285d: ldc_w -14.385
      // 2860: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2863: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2866: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2869: aastore
      // 286a: dup
      // 286b: bipush 9
      // 286d: new net/minecraft/client/animation/Keyframe
      // 2870: dup
      // 2871: ldc 0.38
      // 2873: ldc_w 11.904
      // 2876: ldc_w -28.225
      // 2879: ldc_w -15.918
      // 287c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 287f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2882: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2885: aastore
      // 2886: dup
      // 2887: bipush 10
      // 2889: new net/minecraft/client/animation/Keyframe
      // 288c: dup
      // 288d: ldc 0.42
      // 288f: ldc_w 8.923
      // 2892: ldc_w -26.254
      // 2895: ldc_w -14.343
      // 2898: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 289b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 289e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28a1: aastore
      // 28a2: dup
      // 28a3: bipush 11
      // 28a5: new net/minecraft/client/animation/Keyframe
      // 28a8: dup
      // 28a9: ldc 0.46
      // 28ab: ldc_w 4.644
      // 28ae: ldc_w -21.673
      // 28b1: ldc_w -9.563
      // 28b4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 28b7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28ba: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28bd: aastore
      // 28be: dup
      // 28bf: bipush 12
      // 28c1: new net/minecraft/client/animation/Keyframe
      // 28c4: dup
      // 28c5: ldc 0.5
      // 28c7: ldc_w 1.349
      // 28ca: ldc_w -17.386
      // 28cd: ldc_w -5.132
      // 28d0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 28d3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28d6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28d9: aastore
      // 28da: dup
      // 28db: bipush 13
      // 28dd: new net/minecraft/client/animation/Keyframe
      // 28e0: dup
      // 28e1: ldc 0.54
      // 28e3: ldc_w -0.113
      // 28e6: ldc_w -16.297
      // 28e9: ldc_w -3.234
      // 28ec: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 28ef: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28f2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28f5: aastore
      // 28f6: dup
      // 28f7: bipush 14
      // 28f9: new net/minecraft/client/animation/Keyframe
      // 28fc: dup
      // 28fd: ldc 0.58
      // 28ff: ldc_w -0.32
      // 2902: ldc_w -17.62
      // 2905: ldc_w -3.006
      // 2908: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 290b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 290e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2911: aastore
      // 2912: dup
      // 2913: bipush 15
      // 2915: new net/minecraft/client/animation/Keyframe
      // 2918: dup
      // 2919: ldc 0.62
      // 291b: ldc_w 0.51
      // 291e: ldc_w -20.339
      // 2921: ldc_w -3.89
      // 2924: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2927: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 292a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 292d: aastore
      // 292e: dup
      // 292f: bipush 16
      // 2931: new net/minecraft/client/animation/Keyframe
      // 2934: dup
      // 2935: ldc 0.67
      // 2937: ldc_w 2.085
      // 293a: ldc_w -23.289
      // 293d: ldc_w -5.311
      // 2940: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2943: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2946: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2949: aastore
      // 294a: dup
      // 294b: bipush 17
      // 294d: new net/minecraft/client/animation/Keyframe
      // 2950: dup
      // 2951: ldc 0.71
      // 2953: ldc_w 3.825
      // 2956: ldc_w -25.352
      // 2959: ldc_w -6.53
      // 295c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 295f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2962: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2965: aastore
      // 2966: dup
      // 2967: bipush 18
      // 2969: new net/minecraft/client/animation/Keyframe
      // 296c: dup
      // 296d: ldc 0.75
      // 296f: ldc_w 4.939
      // 2972: ldc_w -25.654
      // 2975: ldc_w -6.683
      // 2978: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 297b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 297e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2981: aastore
      // 2982: dup
      // 2983: bipush 19
      // 2985: new net/minecraft/client/animation/Keyframe
      // 2988: dup
      // 2989: ldc 0.79
      // 298b: ldc_w 4.864
      // 298e: ldc_w -23.604
      // 2991: ldc_w -5.138
      // 2994: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2997: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 299a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 299d: aastore
      // 299e: dup
      // 299f: bipush 20
      // 29a1: new net/minecraft/client/animation/Keyframe
      // 29a4: dup
      // 29a5: ldc 0.83
      // 29a7: ldc_w 4.713
      // 29aa: ldc_w -19.416
      // 29ad: ldc_w -2.955
      // 29b0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 29b3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29b6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29b9: aastore
      // 29ba: dup
      // 29bb: bipush 21
      // 29bd: new net/minecraft/client/animation/Keyframe
      // 29c0: dup
      // 29c1: ldc 0.88
      // 29c3: ldc_w 4.941
      // 29c6: ldc_w -13.638
      // 29c9: ldc_w -0.421
      // 29cc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 29cf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29d2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29d5: aastore
      // 29d6: dup
      // 29d7: bipush 22
      // 29d9: new net/minecraft/client/animation/Keyframe
      // 29dc: dup
      // 29dd: ldc 0.92
      // 29df: ldc_w 4.882
      // 29e2: ldc_w -6.653
      // 29e5: ldc_w 3.184
      // 29e8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 29eb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29ee: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29f1: aastore
      // 29f2: dup
      // 29f3: bipush 23
      // 29f5: new net/minecraft/client/animation/Keyframe
      // 29f8: dup
      // 29f9: ldc 0.96
      // 29fb: ldc_w 4.983
      // 29fe: ldc_w 0.962
      // 2a01: ldc_w 7.478
      // 2a04: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a07: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a0a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a0d: aastore
      // 2a0e: dup
      // 2a0f: bipush 24
      // 2a11: new net/minecraft/client/animation/Keyframe
      // 2a14: dup
      // 2a15: fconst_1
      // 2a16: ldc_w 5.928
      // 2a19: ldc_w 8.624
      // 2a1c: ldc_w 11.844
      // 2a1f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a22: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a25: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a28: aastore
      // 2a29: dup
      // 2a2a: bipush 25
      // 2a2c: new net/minecraft/client/animation/Keyframe
      // 2a2f: dup
      // 2a30: ldc 1.04
      // 2a32: ldc_w 7.428
      // 2a35: ldc_w 15.627
      // 2a38: ldc_w 15.921
      // 2a3b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a3e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a41: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a44: aastore
      // 2a45: dup
      // 2a46: bipush 26
      // 2a48: new net/minecraft/client/animation/Keyframe
      // 2a4b: dup
      // 2a4c: ldc 1.08
      // 2a4e: ldc_w 8.983
      // 2a51: ldc_w 21.479
      // 2a54: ldc_w 19.374
      // 2a57: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a5a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a5d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a60: aastore
      // 2a61: dup
      // 2a62: bipush 27
      // 2a64: new net/minecraft/client/animation/Keyframe
      // 2a67: dup
      // 2a68: ldc 1.12
      // 2a6a: ldc_w 10.02
      // 2a6d: ldc_w 25.906
      // 2a70: ldc_w 21.915
      // 2a73: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a76: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a79: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a7c: aastore
      // 2a7d: dup
      // 2a7e: bipush 28
      // 2a80: new net/minecraft/client/animation/Keyframe
      // 2a83: dup
      // 2a84: ldc 1.17
      // 2a86: ldc_w 10.119
      // 2a89: ldc_w 28.851
      // 2a8c: ldc_w 23.576
      // 2a8f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a92: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a95: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a98: aastore
      // 2a99: dup
      // 2a9a: bipush 29
      // 2a9c: new net/minecraft/client/animation/Keyframe
      // 2a9f: dup
      // 2aa0: ldc 1.21
      // 2aa2: ldc_w 9.519
      // 2aa5: ldc_w 30.667
      // 2aa8: ldc_w 25.67
      // 2aab: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2aae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ab1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ab4: aastore
      // 2ab5: dup
      // 2ab6: bipush 30
      // 2ab8: new net/minecraft/client/animation/Keyframe
      // 2abb: dup
      // 2abc: ldc 1.25
      // 2abe: ldc_w 9.268
      // 2ac1: ldc_w 32.239
      // 2ac4: ldc_w 30.971
      // 2ac7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2aca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2acd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ad0: aastore
      // 2ad1: dup
      // 2ad2: bipush 31
      // 2ad4: new net/minecraft/client/animation/Keyframe
      // 2ad7: dup
      // 2ad8: ldc 1.29
      // 2ada: ldc_w -87.443
      // 2add: ldc_w -56.234
      // 2ae0: ldc_w 95.891
      // 2ae3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ae6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ae9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2aec: aastore
      // 2aed: dup
      // 2aee: bipush 32
      // 2af0: new net/minecraft/client/animation/Keyframe
      // 2af3: dup
      // 2af4: ldc 1.33
      // 2af6: ldc_w -173.79
      // 2af9: ldc_w -115.945
      // 2afc: ldc_w 167.001
      // 2aff: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b02: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b05: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b08: aastore
      // 2b09: dup
      // 2b0a: bipush 33
      // 2b0c: new net/minecraft/client/animation/Keyframe
      // 2b0f: dup
      // 2b10: ldc 1.38
      // 2b12: ldc_w -219.946
      // 2b15: ldc_w -177.995
      // 2b18: ldc_w 169.656
      // 2b1b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b1e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b21: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b24: aastore
      // 2b25: dup
      // 2b26: bipush 34
      // 2b28: new net/minecraft/client/animation/Keyframe
      // 2b2b: dup
      // 2b2c: ldc 1.42
      // 2b2e: ldc_w -234.485
      // 2b31: ldc_w -173.665
      // 2b34: ldc_w 158.51
      // 2b37: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b3a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b3d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b40: aastore
      // 2b41: dup
      // 2b42: bipush 35
      // 2b44: new net/minecraft/client/animation/Keyframe
      // 2b47: dup
      // 2b48: ldc 1.46
      // 2b4a: ldc_w -236.942
      // 2b4d: ldc_w -176.329
      // 2b50: ldc_w 158.375
      // 2b53: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b56: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b59: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b5c: aastore
      // 2b5d: dup
      // 2b5e: bipush 36
      // 2b60: new net/minecraft/client/animation/Keyframe
      // 2b63: dup
      // 2b64: ldc 1.5
      // 2b66: ldc_w -237.847
      // 2b69: ldc_w -181.275
      // 2b6c: ldc_w 158.364
      // 2b6f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b72: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b75: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b78: aastore
      // 2b79: dup
      // 2b7a: bipush 37
      // 2b7c: new net/minecraft/client/animation/Keyframe
      // 2b7f: dup
      // 2b80: ldc 1.54
      // 2b82: ldc_w -237.977
      // 2b85: ldc_w -183.939
      // 2b88: ldc_w 158.229
      // 2b8b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b8e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b91: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b94: aastore
      // 2b95: dup
      // 2b96: bipush 38
      // 2b98: new net/minecraft/client/animation/Keyframe
      // 2b9b: dup
      // 2b9c: ldc 1.58
      // 2b9e: ldc_w -237.577
      // 2ba1: ldc_w -183.87
      // 2ba4: ldc_w 156.418
      // 2ba7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2baa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2bad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2bb0: aastore
      // 2bb1: dup
      // 2bb2: bipush 39
      // 2bb4: new net/minecraft/client/animation/Keyframe
      // 2bb7: dup
      // 2bb8: ldc 1.62
      // 2bba: ldc_w -231.998
      // 2bbd: ldc_w -180.839
      // 2bc0: ldc_w 158.276
      // 2bc3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2bc6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2bc9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2bcc: aastore
      // 2bcd: dup
      // 2bce: bipush 40
      // 2bd0: new net/minecraft/client/animation/Keyframe
      // 2bd3: dup
      // 2bd4: ldc 1.67
      // 2bd6: ldc_w -220.686
      // 2bd9: ldc_w -173.009
      // 2bdc: ldc_w 169.753
      // 2bdf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2be2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2be5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2be8: aastore
      // 2be9: dup
      // 2bea: bipush 41
      // 2bec: new net/minecraft/client/animation/Keyframe
      // 2bef: dup
      // 2bf0: ldc 1.71
      // 2bf2: ldc_w -202.874
      // 2bf5: ldc_w -157.178
      // 2bf8: ldc_w 189.562
      // 2bfb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2bfe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c01: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c04: aastore
      // 2c05: dup
      // 2c06: bipush 42
      // 2c08: new net/minecraft/client/animation/Keyframe
      // 2c0b: dup
      // 2c0c: ldc 1.75
      // 2c0e: ldc_w -194.916
      // 2c11: ldc_w -141.945
      // 2c14: ldc_w 210.786
      // 2c17: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c1a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c1d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c20: aastore
      // 2c21: dup
      // 2c22: bipush 43
      // 2c24: new net/minecraft/client/animation/Keyframe
      // 2c27: dup
      // 2c28: ldc 1.79
      // 2c2a: ldc_w -204.926
      // 2c2d: ldc_w -135.723
      // 2c30: ldc_w 227.691
      // 2c33: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c36: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c39: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c3c: aastore
      // 2c3d: dup
      // 2c3e: bipush 44
      // 2c40: new net/minecraft/client/animation/Keyframe
      // 2c43: dup
      // 2c44: ldc_w 1.83
      // 2c47: ldc_w -216.17
      // 2c4a: ldc_w -134.928
      // 2c4d: ldc_w 238.391
      // 2c50: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c53: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c56: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c59: aastore
      // 2c5a: dup
      // 2c5b: bipush 45
      // 2c5d: new net/minecraft/client/animation/Keyframe
      // 2c60: dup
      // 2c61: ldc_w 1.88
      // 2c64: ldc_w -218.36
      // 2c67: ldc_w -133.748
      // 2c6a: ldc_w 241.27
      // 2c6d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c70: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c73: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c76: aastore
      // 2c77: dup
      // 2c78: bipush 46
      // 2c7a: new net/minecraft/client/animation/Keyframe
      // 2c7d: dup
      // 2c7e: ldc_w 1.92
      // 2c81: ldc_w -215.574
      // 2c84: ldc_w -130.705
      // 2c87: ldc_w 239.597
      // 2c8a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c8d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c90: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c93: aastore
      // 2c94: dup
      // 2c95: bipush 47
      // 2c97: new net/minecraft/client/animation/Keyframe
      // 2c9a: dup
      // 2c9b: ldc_w 1.96
      // 2c9e: ldc_w -214.054
      // 2ca1: ldc_w -125.899
      // 2ca4: ldc_w 238.812
      // 2ca7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2caa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2cad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2cb0: aastore
      // 2cb1: dup
      // 2cb2: bipush 48
      // 2cb4: new net/minecraft/client/animation/Keyframe
      // 2cb7: dup
      // 2cb8: fconst_2
      // 2cb9: ldc_w -215.429
      // 2cbc: ldc_w -120.1
      // 2cbf: ldc_w 240.651
      // 2cc2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2cc5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2cc8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ccb: aastore
      // 2ccc: dup
      // 2ccd: bipush 49
      // 2ccf: new net/minecraft/client/animation/Keyframe
      // 2cd2: dup
      // 2cd3: ldc_w 2.04
      // 2cd6: ldc_w -222.493
      // 2cd9: ldc_w -114.823
      // 2cdc: ldc_w 248.095
      // 2cdf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ce2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ce5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ce8: aastore
      // 2ce9: dup
      // 2cea: bipush 50
      // 2cec: new net/minecraft/client/animation/Keyframe
      // 2cef: dup
      // 2cf0: ldc_w 2.08
      // 2cf3: ldc_w -236.717
      // 2cf6: ldc_w -112.75
      // 2cf9: ldc_w 262.962
      // 2cfc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2cff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d02: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d05: aastore
      // 2d06: dup
      // 2d07: bipush 51
      // 2d09: new net/minecraft/client/animation/Keyframe
      // 2d0c: dup
      // 2d0d: ldc_w 2.12
      // 2d10: ldc_w -245.666
      // 2d13: ldc_w -122.042
      // 2d16: ldc_w 263.118
      // 2d19: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d1c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d1f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d22: aastore
      // 2d23: dup
      // 2d24: bipush 52
      // 2d26: new net/minecraft/client/animation/Keyframe
      // 2d29: dup
      // 2d2a: ldc_w 2.17
      // 2d2d: ldc_w -240.642
      // 2d30: ldc_w -130.233
      // 2d33: ldc_w 240.052
      // 2d36: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d39: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d3c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d3f: aastore
      // 2d40: dup
      // 2d41: bipush 53
      // 2d43: new net/minecraft/client/animation/Keyframe
      // 2d46: dup
      // 2d47: ldc_w 2.21
      // 2d4a: ldc_w -229.549
      // 2d4d: ldc_w -131.458
      // 2d50: ldc_w 214.099
      // 2d53: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d56: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d59: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d5c: aastore
      // 2d5d: dup
      // 2d5e: bipush 54
      // 2d60: new net/minecraft/client/animation/Keyframe
      // 2d63: dup
      // 2d64: ldc 2.25
      // 2d66: ldc_w -224.372
      // 2d69: ldc_w -131.664
      // 2d6c: ldc_w 205.239
      // 2d6f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d72: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d75: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d78: aastore
      // 2d79: dup
      // 2d7a: bipush 55
      // 2d7c: new net/minecraft/client/animation/Keyframe
      // 2d7f: dup
      // 2d80: ldc 2.29
      // 2d82: ldc_w -219.645
      // 2d85: ldc_w -126.706
      // 2d88: ldc_w 206.36
      // 2d8b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d8e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d91: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d94: aastore
      // 2d95: dup
      // 2d96: bipush 56
      // 2d98: new net/minecraft/client/animation/Keyframe
      // 2d9b: dup
      // 2d9c: ldc 2.33
      // 2d9e: ldc_w -208.438
      // 2da1: ldc_w -122.042
      // 2da4: ldc_w 197.047
      // 2da7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2daa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2dad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2db0: aastore
      // 2db1: dup
      // 2db2: bipush 57
      // 2db4: new net/minecraft/client/animation/Keyframe
      // 2db7: dup
      // 2db8: ldc 2.38
      // 2dba: ldc_w -205.303
      // 2dbd: ldc_w -128.944
      // 2dc0: ldc_w 191.979
      // 2dc3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2dc6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2dc9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2dcc: aastore
      // 2dcd: dup
      // 2dce: bipush 58
      // 2dd0: new net/minecraft/client/animation/Keyframe
      // 2dd3: dup
      // 2dd4: ldc 2.42
      // 2dd6: ldc_w -206.71
      // 2dd9: ldc_w -142.413
      // 2ddc: ldc_w 192.387
      // 2ddf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2de2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2de5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2de8: aastore
      // 2de9: dup
      // 2dea: bipush 59
      // 2dec: new net/minecraft/client/animation/Keyframe
      // 2def: dup
      // 2df0: ldc 2.46
      // 2df2: ldc_w -201.811
      // 2df5: ldc_w -152.029
      // 2df8: ldc_w 188.236
      // 2dfb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2dfe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e01: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e04: aastore
      // 2e05: dup
      // 2e06: bipush 60
      // 2e08: new net/minecraft/client/animation/Keyframe
      // 2e0b: dup
      // 2e0c: ldc 2.5
      // 2e0e: ldc_w -192.205
      // 2e11: ldc_w -158.182
      // 2e14: ldc_w 180.907
      // 2e17: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e1a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e1d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e20: aastore
      // 2e21: dup
      // 2e22: bipush 61
      // 2e24: new net/minecraft/client/animation/Keyframe
      // 2e27: dup
      // 2e28: ldc 2.54
      // 2e2a: ldc_w -182.249
      // 2e2d: ldc_w -163.65
      // 2e30: ldc_w 173.381
      // 2e33: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e36: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e39: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e3c: aastore
      // 2e3d: dup
      // 2e3e: bipush 62
      // 2e40: new net/minecraft/client/animation/Keyframe
      // 2e43: dup
      // 2e44: ldc_w 2.58
      // 2e47: ldc_w -176.354
      // 2e4a: ldc_w -166.4
      // 2e4d: ldc_w 168.425
      // 2e50: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e53: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e56: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e59: aastore
      // 2e5a: dup
      // 2e5b: bipush 63
      // 2e5d: new net/minecraft/client/animation/Keyframe
      // 2e60: dup
      // 2e61: ldc_w 2.62
      // 2e64: ldc_w -175.018
      // 2e67: ldc_w -165.798
      // 2e6a: ldc_w 167.181
      // 2e6d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e70: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e73: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e76: aastore
      // 2e77: dup
      // 2e78: bipush 64
      // 2e7a: new net/minecraft/client/animation/Keyframe
      // 2e7d: dup
      // 2e7e: ldc_w 2.67
      // 2e81: ldc_w -176.834
      // 2e84: ldc_w -164.569
      // 2e87: ldc_w 170.214
      // 2e8a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e8d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e90: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e93: aastore
      // 2e94: dup
      // 2e95: bipush 65
      // 2e97: new net/minecraft/client/animation/Keyframe
      // 2e9a: dup
      // 2e9b: ldc_w 2.71
      // 2e9e: ldc_w -179.329
      // 2ea1: ldc_w -163.728
      // 2ea4: ldc_w 174.252
      // 2ea7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2eaa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ead: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2eb0: aastore
      // 2eb1: dup
      // 2eb2: bipush 66
      // 2eb4: new net/minecraft/client/animation/Keyframe
      // 2eb7: dup
      // 2eb8: ldc_w 2.75
      // 2ebb: ldc_w -180.768
      // 2ebe: ldc_w -164.328
      // 2ec1: ldc_w 177.211
      // 2ec4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ec7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2eca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ecd: aastore
      // 2ece: dup
      // 2ecf: bipush 67
      // 2ed1: new net/minecraft/client/animation/Keyframe
      // 2ed4: dup
      // 2ed5: ldc_w 2.79
      // 2ed8: ldc_w -181.279
      // 2edb: ldc_w -165.701
      // 2ede: ldc_w 178.975
      // 2ee1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ee4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ee7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2eea: aastore
      // 2eeb: dup
      // 2eec: bipush 68
      // 2eee: new net/minecraft/client/animation/Keyframe
      // 2ef1: dup
      // 2ef2: ldc 2.83
      // 2ef4: ldc_w -181.331
      // 2ef7: ldc_w -166.447
      // 2efa: ldc_w 179.552
      // 2efd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f00: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f03: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f06: aastore
      // 2f07: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2f0a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2f0d: ldc_w "CTR_lowerarm_left"
      // 2f10: new net/minecraft/client/animation/AnimationChannel
      // 2f13: dup
      // 2f14: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2f17: bipush 66
      // 2f19: anewarray 28
      // 2f1c: dup
      // 2f1d: bipush 0
      // 2f1e: new net/minecraft/client/animation/Keyframe
      // 2f21: dup
      // 2f22: fconst_0
      // 2f23: ldc_w 0.989
      // 2f26: ldc_w -2.325
      // 2f29: ldc_w -0.565
      // 2f2c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f2f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f32: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f35: aastore
      // 2f36: dup
      // 2f37: bipush 1
      // 2f38: new net/minecraft/client/animation/Keyframe
      // 2f3b: dup
      // 2f3c: ldc 0.04
      // 2f3e: ldc_w 1.256
      // 2f41: ldc_w -2.931
      // 2f44: ldc_w -0.723
      // 2f47: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f4a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f4d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f50: aastore
      // 2f51: dup
      // 2f52: bipush 2
      // 2f53: new net/minecraft/client/animation/Keyframe
      // 2f56: dup
      // 2f57: ldc 0.08
      // 2f59: ldc_w 1.981
      // 2f5c: ldc_w -4.521
      // 2f5f: ldc_w -1.164
      // 2f62: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f65: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f68: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f6b: aastore
      // 2f6c: dup
      // 2f6d: bipush 3
      // 2f6e: new net/minecraft/client/animation/Keyframe
      // 2f71: dup
      // 2f72: ldc 0.12
      // 2f74: ldc_w 2.986
      // 2f77: ldc_w -6.611
      // 2f7a: ldc_w -1.8
      // 2f7d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f80: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f83: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f86: aastore
      // 2f87: dup
      // 2f88: bipush 4
      // 2f89: new net/minecraft/client/animation/Keyframe
      // 2f8c: dup
      // 2f8d: ldc 0.17
      // 2f8f: ldc_w 4.025
      // 2f92: ldc_w -8.633
      // 2f95: ldc_w -2.486
      // 2f98: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f9b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f9e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2fa1: aastore
      // 2fa2: dup
      // 2fa3: bipush 5
      // 2fa4: new net/minecraft/client/animation/Keyframe
      // 2fa7: dup
      // 2fa8: ldc 0.21
      // 2faa: ldc_w 4.765
      // 2fad: ldc_w -9.993
      // 2fb0: ldc_w -2.991
      // 2fb3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2fb6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2fb9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2fbc: aastore
      // 2fbd: dup
      // 2fbe: bipush 6
      // 2fc0: new net/minecraft/client/animation/Keyframe
      // 2fc3: dup
      // 2fc4: ldc 0.25
      // 2fc6: ldc_w 4.887
      // 2fc9: ldc_w -10.211
      // 2fcc: ldc_w -3.075
      // 2fcf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2fd2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2fd5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2fd8: aastore
      // 2fd9: dup
      // 2fda: bipush 7
      // 2fdc: new net/minecraft/client/animation/Keyframe
      // 2fdf: dup
      // 2fe0: ldc 0.29
      // 2fe2: ldc_w 4.225
      // 2fe5: ldc_w -9.008
      // 2fe8: ldc_w -2.622
      // 2feb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2fee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ff1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ff4: aastore
      // 2ff5: dup
      // 2ff6: bipush 8
      // 2ff8: new net/minecraft/client/animation/Keyframe
      // 2ffb: dup
      // 2ffc: ldc 0.33
      // 2ffe: ldc_w 2.979
      // 3001: ldc_w -6.597
      // 3004: ldc_w -1.796
      // 3007: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 300a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 300d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3010: aastore
      // 3011: dup
      // 3012: bipush 9
      // 3014: new net/minecraft/client/animation/Keyframe
      // 3017: dup
      // 3018: ldc 0.38
      // 301a: ldc_w 1.752
      // 301d: ldc -4.027
      // 301f: ldc_w -1.023
      // 3022: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3025: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3028: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 302b: aastore
      // 302c: dup
      // 302d: bipush 10
      // 302f: new net/minecraft/client/animation/Keyframe
      // 3032: dup
      // 3033: ldc 0.42
      // 3035: ldc_w 1.307
      // 3038: ldc_w -3.044
      // 303b: ldc_w -0.754
      // 303e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3041: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3044: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3047: aastore
      // 3048: dup
      // 3049: bipush 11
      // 304b: new net/minecraft/client/animation/Keyframe
      // 304e: dup
      // 304f: ldc 0.46
      // 3051: ldc_w 2.402
      // 3054: ldc_w -5.412
      // 3057: ldc_w -1.427
      // 305a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 305d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3060: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3063: aastore
      // 3064: dup
      // 3065: bipush 12
      // 3067: new net/minecraft/client/animation/Keyframe
      // 306a: dup
      // 306b: ldc 0.5
      // 306d: ldc_w 3.946
      // 3070: ldc_w -8.483
      // 3073: ldc_w -2.433
      // 3076: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3079: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 307c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 307f: aastore
      // 3080: dup
      // 3081: bipush 13
      // 3083: new net/minecraft/client/animation/Keyframe
      // 3086: dup
      // 3087: ldc 0.54
      // 3089: ldc_w 4.551
      // 308c: ldc_w -9.607
      // 308f: ldc_w -2.844
      // 3092: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3095: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3098: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 309b: aastore
      // 309c: dup
      // 309d: bipush 14
      // 309f: new net/minecraft/client/animation/Keyframe
      // 30a2: dup
      // 30a3: ldc 0.58
      // 30a5: ldc_w 4.338
      // 30a8: ldc_w -9.217
      // 30ab: ldc_w -2.698
      // 30ae: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 30b1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30b4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30b7: aastore
      // 30b8: dup
      // 30b9: bipush 15
      // 30bb: new net/minecraft/client/animation/Keyframe
      // 30be: dup
      // 30bf: ldc 0.62
      // 30c1: ldc_w 3.467
      // 30c4: ldc_w -7.563
      // 30c7: ldc_w -2.114
      // 30ca: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 30cd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30d0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30d3: aastore
      // 30d4: dup
      // 30d5: bipush 16
      // 30d7: new net/minecraft/client/animation/Keyframe
      // 30da: dup
      // 30db: ldc 0.67
      // 30dd: ldc_w 2.244
      // 30e0: ldc_w -5.081
      // 30e3: ldc_w -1.328
      // 30e6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 30e9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30ec: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30ef: aastore
      // 30f0: dup
      // 30f1: bipush 17
      // 30f3: new net/minecraft/client/animation/Keyframe
      // 30f6: dup
      // 30f7: ldc 0.71
      // 30f9: ldc_w 1.068
      // 30fc: ldc_w -2.505
      // 30ff: ldc_w -0.612
      // 3102: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3105: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3108: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 310b: aastore
      // 310c: dup
      // 310d: bipush 18
      // 310f: new net/minecraft/client/animation/Keyframe
      // 3112: dup
      // 3113: ldc 0.75
      // 3115: ldc_w 0.306
      // 3118: ldc_w -0.734
      // 311b: ldc_w -0.171
      // 311e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3121: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3124: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3127: aastore
      // 3128: dup
      // 3129: bipush 19
      // 312b: new net/minecraft/client/animation/Keyframe
      // 312e: dup
      // 312f: ldc 0.79
      // 3131: ldc_w 0.158
      // 3134: ldc_w -0.38
      // 3137: ldc_w -0.088
      // 313a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 313d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3140: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3143: aastore
      // 3144: dup
      // 3145: bipush 20
      // 3147: new net/minecraft/client/animation/Keyframe
      // 314a: dup
      // 314b: ldc 0.83
      // 314d: ldc_w -0.364
      // 3150: ldc_w -1.084
      // 3153: ldc_w -0.009
      // 3156: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3159: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 315c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 315f: aastore
      // 3160: dup
      // 3161: bipush 21
      // 3163: new net/minecraft/client/animation/Keyframe
      // 3166: dup
      // 3167: ldc 0.88
      // 3169: ldc_w -1.698
      // 316c: ldc_w -2.724
      // 316f: ldc_w 0.232
      // 3172: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3175: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3178: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 317b: aastore
      // 317c: dup
      // 317d: bipush 22
      // 317f: new net/minecraft/client/animation/Keyframe
      // 3182: dup
      // 3183: ldc 0.92
      // 3185: ldc_w -3.378
      // 3188: ldc_w -4.758
      // 318b: ldc_w 0.582
      // 318e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3191: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3194: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3197: aastore
      // 3198: dup
      // 3199: bipush 23
      // 319b: new net/minecraft/client/animation/Keyframe
      // 319e: dup
      // 319f: ldc 0.96
      // 31a1: ldc_w -5.241
      // 31a4: ldc_w -5.866
      // 31a7: ldc_w 1.094
      // 31aa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31ad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31b0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31b3: aastore
      // 31b4: dup
      // 31b5: bipush 24
      // 31b7: new net/minecraft/client/animation/Keyframe
      // 31ba: dup
      // 31bb: fconst_1
      // 31bc: ldc_w -6.993
      // 31bf: ldc_w -5.497
      // 31c2: ldc_w 1.639
      // 31c5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31c8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31cb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31ce: aastore
      // 31cf: dup
      // 31d0: bipush 25
      // 31d2: new net/minecraft/client/animation/Keyframe
      // 31d5: dup
      // 31d6: ldc 1.04
      // 31d8: ldc_w -8.456
      // 31db: ldc_w -3.137
      // 31de: ldc_w 2.097
      // 31e1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31e4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31e7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31ea: aastore
      // 31eb: dup
      // 31ec: bipush 26
      // 31ee: new net/minecraft/client/animation/Keyframe
      // 31f1: dup
      // 31f2: ldc 1.08
      // 31f4: ldc_w -9.525
      // 31f7: ldc_w 1.311
      // 31fa: ldc_w 2.379
      // 31fd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3200: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3203: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3206: aastore
      // 3207: dup
      // 3208: bipush 27
      // 320a: new net/minecraft/client/animation/Keyframe
      // 320d: dup
      // 320e: ldc 1.12
      // 3210: ldc_w -10.202
      // 3213: ldc_w 7.538
      // 3216: ldc_w 2.479
      // 3219: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 321c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 321f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3222: aastore
      // 3223: dup
      // 3224: bipush 28
      // 3226: new net/minecraft/client/animation/Keyframe
      // 3229: dup
      // 322a: ldc 1.17
      // 322c: ldc_w -10.564
      // 322f: ldc_w 14.526
      // 3232: ldc_w 2.454
      // 3235: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3238: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 323b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 323e: aastore
      // 323f: dup
      // 3240: bipush 29
      // 3242: new net/minecraft/client/animation/Keyframe
      // 3245: dup
      // 3246: ldc 1.21
      // 3248: ldc_w -10.713
      // 324b: ldc_w 18.972
      // 324e: ldc_w 2.378
      // 3251: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3254: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3257: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 325a: aastore
      // 325b: dup
      // 325c: bipush 30
      // 325e: new net/minecraft/client/animation/Keyframe
      // 3261: dup
      // 3262: ldc 1.25
      // 3264: ldc_w -10.763
      // 3267: ldc_w 14.965
      // 326a: ldc_w 2.312
      // 326d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3270: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3273: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3276: aastore
      // 3277: dup
      // 3278: bipush 31
      // 327a: new net/minecraft/client/animation/Keyframe
      // 327d: dup
      // 327e: ldc 1.29
      // 3280: ldc_w -10.786
      // 3283: ldc_w 23.486
      // 3286: ldc_w 2.289
      // 3289: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 328c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 328f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3292: aastore
      // 3293: dup
      // 3294: bipush 32
      // 3296: new net/minecraft/client/animation/Keyframe
      // 3299: dup
      // 329a: ldc 1.33
      // 329c: ldc_w -10.786
      // 329f: ldc_w 20.299
      // 32a2: ldc_w 2.289
      // 32a5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 32a8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32ab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32ae: aastore
      // 32af: dup
      // 32b0: bipush 33
      // 32b2: new net/minecraft/client/animation/Keyframe
      // 32b5: dup
      // 32b6: ldc 1.38
      // 32b8: ldc_w -10.786
      // 32bb: ldc_w 7.221
      // 32be: ldc_w 2.289
      // 32c1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 32c4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32c7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32ca: aastore
      // 32cb: dup
      // 32cc: bipush 34
      // 32ce: new net/minecraft/client/animation/Keyframe
      // 32d1: dup
      // 32d2: ldc 1.54
      // 32d4: ldc_w -10.786
      // 32d7: ldc_w 7.221
      // 32da: ldc_w 2.289
      // 32dd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 32e0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32e3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32e6: aastore
      // 32e7: dup
      // 32e8: bipush 35
      // 32ea: new net/minecraft/client/animation/Keyframe
      // 32ed: dup
      // 32ee: ldc 1.58
      // 32f0: ldc_w -10.458
      // 32f3: ldc_w 10.768
      // 32f6: ldc_w 2.459
      // 32f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 32fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3302: aastore
      // 3303: dup
      // 3304: bipush 36
      // 3306: new net/minecraft/client/animation/Keyframe
      // 3309: dup
      // 330a: ldc 1.62
      // 330c: ldc_w -9.69
      // 330f: ldc_w 17.353
      // 3312: ldc_w 2.769
      // 3315: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3318: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 331b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 331e: aastore
      // 331f: dup
      // 3320: bipush 37
      // 3322: new net/minecraft/client/animation/Keyframe
      // 3325: dup
      // 3326: ldc 1.67
      // 3328: ldc_w -8.018
      // 332b: ldc_w 17.128
      // 332e: ldc_w 2.766
      // 3331: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3334: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3337: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 333a: aastore
      // 333b: dup
      // 333c: bipush 38
      // 333e: new net/minecraft/client/animation/Keyframe
      // 3341: dup
      // 3342: ldc 1.71
      // 3344: ldc_w -5.517
      // 3347: ldc_w 12.456
      // 334a: ldc_w 2.257
      // 334d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3350: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3353: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3356: aastore
      // 3357: dup
      // 3358: bipush 39
      // 335a: new net/minecraft/client/animation/Keyframe
      // 335d: dup
      // 335e: ldc 1.75
      // 3360: ldc_w -2.6
      // 3363: ldc_w 6.278
      // 3366: ldc_w 1.271
      // 3369: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 336c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 336f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3372: aastore
      // 3373: dup
      // 3374: bipush 40
      // 3376: new net/minecraft/client/animation/Keyframe
      // 3379: dup
      // 337a: ldc 1.79
      // 337c: ldc_w -0.086
      // 337f: ldc_w 0.207
      // 3382: ldc_w 0.048
      // 3385: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3388: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 338b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 338e: aastore
      // 338f: dup
      // 3390: bipush 41
      // 3392: new net/minecraft/client/animation/Keyframe
      // 3395: dup
      // 3396: ldc_w 1.83
      // 3399: ldc_w 1.813
      // 339c: ldc_w -4.16
      // 339f: ldc_w -1.061
      // 33a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 33a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33ab: aastore
      // 33ac: dup
      // 33ad: bipush 42
      // 33af: new net/minecraft/client/animation/Keyframe
      // 33b2: dup
      // 33b3: ldc_w 1.88
      // 33b6: ldc_w 2.132
      // 33b9: ldc_w -4.843
      // 33bc: ldc_w -1.257
      // 33bf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 33c2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33c5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33c8: aastore
      // 33c9: dup
      // 33ca: bipush 43
      // 33cc: new net/minecraft/client/animation/Keyframe
      // 33cf: dup
      // 33d0: ldc_w 1.92
      // 33d3: ldc_w 1.216
      // 33d6: ldc_w -2.841
      // 33d9: ldc_w -0.7
      // 33dc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 33df: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33e2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33e5: aastore
      // 33e6: dup
      // 33e7: bipush 44
      // 33e9: new net/minecraft/client/animation/Keyframe
      // 33ec: dup
      // 33ed: ldc_w 1.96
      // 33f0: ldc_w 0.111
      // 33f3: ldc_w -0.268
      // 33f6: ldc_w -0.062
      // 33f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 33fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3402: aastore
      // 3403: dup
      // 3404: bipush 45
      // 3406: new net/minecraft/client/animation/Keyframe
      // 3409: dup
      // 340a: fconst_2
      // 340b: ldc_w -0.872
      // 340e: ldc_w 2.167
      // 3411: ldc_w 0.47
      // 3414: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3417: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 341a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 341d: aastore
      // 341e: dup
      // 341f: bipush 46
      // 3421: new net/minecraft/client/animation/Keyframe
      // 3424: dup
      // 3425: ldc_w 2.04
      // 3428: ldc_w -1.378
      // 342b: ldc_w 3.478
      // 342e: ldc_w 0.73
      // 3431: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3434: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3437: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 343a: aastore
      // 343b: dup
      // 343c: bipush 47
      // 343e: new net/minecraft/client/animation/Keyframe
      // 3441: dup
      // 3442: ldc_w 2.08
      // 3445: ldc_w -0.776
      // 3448: ldc_w 1.923
      // 344b: ldc 0.42
      // 344d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3450: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3453: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3456: aastore
      // 3457: dup
      // 3458: bipush 48
      // 345a: new net/minecraft/client/animation/Keyframe
      // 345d: dup
      // 345e: ldc_w 2.12
      // 3461: ldc_w 9.765
      // 3464: ldc_w -17.663
      // 3467: ldc_w -6.688
      // 346a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 346d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3470: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3473: aastore
      // 3474: dup
      // 3475: bipush 49
      // 3477: new net/minecraft/client/animation/Keyframe
      // 347a: dup
      // 347b: ldc_w 2.17
      // 347e: ldc_w 33.985
      // 3481: ldc_w -34.577
      // 3484: ldc_w -27.929
      // 3487: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 348a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 348d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3490: aastore
      // 3491: dup
      // 3492: bipush 50
      // 3494: new net/minecraft/client/animation/Keyframe
      // 3497: dup
      // 3498: ldc_w 2.21
      // 349b: ldc_w 47.542
      // 349e: ldc_w -38.054
      // 34a1: ldc_w -40.682
      // 34a4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 34a7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34aa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 34ad: aastore
      // 34ae: dup
      // 34af: bipush 51
      // 34b1: new net/minecraft/client/animation/Keyframe
      // 34b4: dup
      // 34b5: ldc 2.25
      // 34b7: ldc_w 65.703
      // 34ba: ldc_w -40.204
      // 34bd: ldc_w -58.049
      // 34c0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 34c3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34c6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 34c9: aastore
      // 34ca: dup
      // 34cb: bipush 52
      // 34cd: new net/minecraft/client/animation/Keyframe
      // 34d0: dup
      // 34d1: ldc 2.29
      // 34d3: ldc_w 82.815
      // 34d6: ldc_w -40.598
      // 34d9: ldc_w -74.515
      // 34dc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 34df: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34e2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 34e5: aastore
      // 34e6: dup
      // 34e7: bipush 53
      // 34e9: new net/minecraft/client/animation/Keyframe
      // 34ec: dup
      // 34ed: ldc 2.33
      // 34ef: ldc_w 75.452
      // 34f2: ldc_w -40.595
      // 34f5: ldc_w -67.426
      // 34f8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 34fb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34fe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3501: aastore
      // 3502: dup
      // 3503: bipush 54
      // 3505: new net/minecraft/client/animation/Keyframe
      // 3508: dup
      // 3509: ldc 2.38
      // 350b: ldc_w 78.244
      // 350e: ldc_w -40.625
      // 3511: ldc_w -70.114
      // 3514: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3517: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 351a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 351d: aastore
      // 351e: dup
      // 351f: bipush 55
      // 3521: new net/minecraft/client/animation/Keyframe
      // 3524: dup
      // 3525: ldc 2.42
      // 3527: ldc_w 73.582
      // 352a: ldc_w -40.555
      // 352d: ldc_w -65.626
      // 3530: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3533: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3536: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3539: aastore
      // 353a: dup
      // 353b: bipush 56
      // 353d: new net/minecraft/client/animation/Keyframe
      // 3540: dup
      // 3541: ldc 2.46
      // 3543: ldc_w 40.668
      // 3546: ldc_w -36.565
      // 3549: ldc_w -34.181
      // 354c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 354f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3552: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3555: aastore
      // 3556: dup
      // 3557: bipush 57
      // 3559: new net/minecraft/client/animation/Keyframe
      // 355c: dup
      // 355d: ldc 2.5
      // 355f: ldc_w 14.986
      // 3562: ldc_w -23.463
      // 3565: ldc_w -10.933
      // 3568: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 356b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 356e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3571: aastore
      // 3572: dup
      // 3573: bipush 58
      // 3575: new net/minecraft/client/animation/Keyframe
      // 3578: dup
      // 3579: ldc 2.54
      // 357b: ldc_w 3.34
      // 357e: ldc_w -7.315
      // 3581: ldc_w -2.031
      // 3584: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3587: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 358a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 358d: aastore
      // 358e: dup
      // 358f: bipush 59
      // 3591: new net/minecraft/client/animation/Keyframe
      // 3594: dup
      // 3595: ldc_w 2.58
      // 3598: ldc_w -2.089
      // 359b: ldc_w 5.379
      // 359e: ldc_w 1.077
      // 35a1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 35a4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35a7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35aa: aastore
      // 35ab: dup
      // 35ac: bipush 60
      // 35ae: new net/minecraft/client/animation/Keyframe
      // 35b1: dup
      // 35b2: ldc_w 2.62
      // 35b5: ldc_w -4.079
      // 35b8: ldc_w 11.101
      // 35bb: ldc_w 1.934
      // 35be: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 35c1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35c4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35c7: aastore
      // 35c8: dup
      // 35c9: bipush 61
      // 35cb: new net/minecraft/client/animation/Keyframe
      // 35ce: dup
      // 35cf: ldc_w 2.67
      // 35d2: ldc_w -3.089
      // 35d5: ldc_w 8.185
      // 35d8: ldc_w 1.53
      // 35db: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 35de: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35e1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35e4: aastore
      // 35e5: dup
      // 35e6: bipush 62
      // 35e8: new net/minecraft/client/animation/Keyframe
      // 35eb: dup
      // 35ec: ldc_w 2.71
      // 35ef: ldc_w -1.192
      // 35f2: ldc_w 2.992
      // 35f5: ldc_w 0.636
      // 35f8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 35fb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35fe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3601: aastore
      // 3602: dup
      // 3603: bipush 63
      // 3605: new net/minecraft/client/animation/Keyframe
      // 3608: dup
      // 3609: ldc_w 2.75
      // 360c: ldc_w 0.149
      // 360f: ldc_w -0.361
      // 3612: ldc_w -0.083
      // 3615: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3618: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 361b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 361e: aastore
      // 361f: dup
      // 3620: bipush 64
      // 3622: new net/minecraft/client/animation/Keyframe
      // 3625: dup
      // 3626: ldc_w 2.79
      // 3629: ldc_w 0.822
      // 362c: ldc_w -1.944
      // 362f: ldc_w -0.468
      // 3632: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3635: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3638: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 363b: aastore
      // 363c: dup
      // 363d: bipush 65
      // 363f: new net/minecraft/client/animation/Keyframe
      // 3642: dup
      // 3643: ldc 2.83
      // 3645: ldc_w 0.989
      // 3648: ldc_w -2.325
      // 364b: ldc_w -0.565
      // 364e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3651: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3654: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3657: aastore
      // 3658: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 365b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 365e: ldc_w "CTR_upperarm_left.001"
      // 3661: new net/minecraft/client/animation/AnimationChannel
      // 3664: dup
      // 3665: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3668: bipush 69
      // 366a: anewarray 28
      // 366d: dup
      // 366e: bipush 0
      // 366f: new net/minecraft/client/animation/Keyframe
      // 3672: dup
      // 3673: fconst_0
      // 3674: ldc_w 2.163
      // 3677: ldc_w -22.491
      // 367a: ldc_w -1.837
      // 367d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3680: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3683: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3686: aastore
      // 3687: dup
      // 3688: bipush 1
      // 3689: new net/minecraft/client/animation/Keyframe
      // 368c: dup
      // 368d: ldc 0.04
      // 368f: ldc_w 2.194
      // 3692: ldc_w -22.291
      // 3695: ldc_w -1.875
      // 3698: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 369b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 369e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36a1: aastore
      // 36a2: dup
      // 36a3: bipush 2
      // 36a4: new net/minecraft/client/animation/Keyframe
      // 36a7: dup
      // 36a8: ldc 0.08
      // 36aa: ldc_w 2.254
      // 36ad: ldc_w -21.525
      // 36b0: ldc_w -1.976
      // 36b3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 36b6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36b9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36bc: aastore
      // 36bd: dup
      // 36be: bipush 3
      // 36bf: new net/minecraft/client/animation/Keyframe
      // 36c2: dup
      // 36c3: ldc 0.12
      // 36c5: ldc_w 2.258
      // 36c8: ldc_w -19.743
      // 36cb: ldc_w -2.11
      // 36ce: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 36d1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36d4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36d7: aastore
      // 36d8: dup
      // 36d9: bipush 4
      // 36da: new net/minecraft/client/animation/Keyframe
      // 36dd: dup
      // 36de: ldc 0.17
      // 36e0: ldc_w 2.067
      // 36e3: ldc_w -16.437
      // 36e6: ldc_w -2.261
      // 36e9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 36ec: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36ef: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36f2: aastore
      // 36f3: dup
      // 36f4: bipush 5
      // 36f5: new net/minecraft/client/animation/Keyframe
      // 36f8: dup
      // 36f9: ldc 0.21
      // 36fb: ldc_w 1.436
      // 36fe: ldc_w -11.353
      // 3701: ldc_w -2.539
      // 3704: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3707: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 370a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 370d: aastore
      // 370e: dup
      // 370f: bipush 6
      // 3711: new net/minecraft/client/animation/Keyframe
      // 3714: dup
      // 3715: ldc 0.25
      // 3717: ldc_w -0.513
      // 371a: ldc_w -4.899
      // 371d: ldc_w -3.805
      // 3720: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3723: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3726: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3729: aastore
      // 372a: dup
      // 372b: bipush 7
      // 372d: new net/minecraft/client/animation/Keyframe
      // 3730: dup
      // 3731: ldc 0.29
      // 3733: ldc_w -84.841
      // 3736: ldc_w 1.406
      // 3739: ldc_w -87.275
      // 373c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 373f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3742: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3745: aastore
      // 3746: dup
      // 3747: bipush 8
      // 3749: new net/minecraft/client/animation/Keyframe
      // 374c: dup
      // 374d: ldc 0.33
      // 374f: ldc_w -173.886
      // 3752: ldc_w -3.533
      // 3755: ldc_w -175.553
      // 3758: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 375b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 375e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3761: aastore
      // 3762: dup
      // 3763: bipush 9
      // 3765: new net/minecraft/client/animation/Keyframe
      // 3768: dup
      // 3769: ldc 0.38
      // 376b: ldc_w -175.57
      // 376e: ldc_w -6.408
      // 3771: ldc_w -176.761
      // 3774: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3777: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 377a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 377d: aastore
      // 377e: dup
      // 377f: bipush 10
      // 3781: new net/minecraft/client/animation/Keyframe
      // 3784: dup
      // 3785: ldc 0.42
      // 3787: ldc_w -175.655
      // 378a: ldc_w -6.519
      // 378d: ldc_w -176.718
      // 3790: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3793: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3796: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3799: aastore
      // 379a: dup
      // 379b: bipush 11
      // 379d: new net/minecraft/client/animation/Keyframe
      // 37a0: dup
      // 37a1: ldc 0.46
      // 37a3: ldc_w -174.252
      // 37a6: ldc_w -3.871
      // 37a9: ldc_w -175.56
      // 37ac: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 37af: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37b2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37b5: aastore
      // 37b6: dup
      // 37b7: bipush 12
      // 37b9: new net/minecraft/client/animation/Keyframe
      // 37bc: dup
      // 37bd: ldc 0.5
      // 37bf: ldc_w -170.473
      // 37c2: ldc_w -1.182
      // 37c5: ldc_w -172.105
      // 37c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 37cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37d1: aastore
      // 37d2: dup
      // 37d3: bipush 13
      // 37d5: new net/minecraft/client/animation/Keyframe
      // 37d8: dup
      // 37d9: ldc 0.54
      // 37db: ldc_w -168.736
      // 37de: ldc_w -0.641
      // 37e1: ldc_w -170.448
      // 37e4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 37e7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37ea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37ed: aastore
      // 37ee: dup
      // 37ef: bipush 14
      // 37f1: new net/minecraft/client/animation/Keyframe
      // 37f4: dup
      // 37f5: ldc 0.58
      // 37f7: ldc_w -171.469
      // 37fa: ldc_w -1.617
      // 37fd: ldc_w -173.072
      // 3800: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3803: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3806: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3809: aastore
      // 380a: dup
      // 380b: bipush 15
      // 380d: new net/minecraft/client/animation/Keyframe
      // 3810: dup
      // 3811: ldc 0.62
      // 3813: ldc_w -173.923
      // 3816: ldc_w -3.479
      // 3819: ldc_w -175.307
      // 381c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 381f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3822: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3825: aastore
      // 3826: dup
      // 3827: bipush 16
      // 3829: new net/minecraft/client/animation/Keyframe
      // 382c: dup
      // 382d: ldc 0.67
      // 382f: ldc_w -175.235
      // 3832: ldc_w -5.514
      // 3835: ldc_w -176.383
      // 3838: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 383b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 383e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3841: aastore
      // 3842: dup
      // 3843: bipush 17
      // 3845: new net/minecraft/client/animation/Keyframe
      // 3848: dup
      // 3849: ldc 0.71
      // 384b: ldc_w -175.851
      // 384e: ldc_w -7.022
      // 3851: ldc_w -176.824
      // 3854: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3857: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 385a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 385d: aastore
      // 385e: dup
      // 385f: bipush 18
      // 3861: new net/minecraft/client/animation/Keyframe
      // 3864: dup
      // 3865: ldc 0.75
      // 3867: ldc_w -175.984
      // 386a: ldc_w -7.402
      // 386d: ldc_w -176.899
      // 3870: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3873: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3876: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3879: aastore
      // 387a: dup
      // 387b: bipush 19
      // 387d: new net/minecraft/client/animation/Keyframe
      // 3880: dup
      // 3881: ldc 0.79
      // 3883: ldc_w -175.584
      // 3886: ldc_w -6.259
      // 3889: ldc_w -176.59
      // 388c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 388f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3892: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3895: aastore
      // 3896: dup
      // 3897: bipush 20
      // 3899: new net/minecraft/client/animation/Keyframe
      // 389c: dup
      // 389d: ldc 0.83
      // 389f: ldc_w -174.926
      // 38a2: ldc_w -4.833
      // 38a5: ldc_w -176.007
      // 38a8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 38ab: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 38ae: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 38b1: aastore
      // 38b2: dup
      // 38b3: bipush 21
      // 38b5: new net/minecraft/client/animation/Keyframe
      // 38b8: dup
      // 38b9: ldc 0.88
      // 38bb: ldc_w -174.032
      // 38be: ldc_w -3.499
      // 38c1: ldc_w -175.098
      // 38c4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 38c7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 38ca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 38cd: aastore
      // 38ce: dup
      // 38cf: bipush 22
      // 38d1: new net/minecraft/client/animation/Keyframe
      // 38d4: dup
      // 38d5: ldc 0.92
      // 38d7: ldc_w -170.618
      // 38da: ldc_w -1.21
      // 38dd: ldc_w -171.667
      // 38e0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 38e3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 38e6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 38e9: aastore
      // 38ea: dup
      // 38eb: bipush 23
      // 38ed: new net/minecraft/client/animation/Keyframe
      // 38f0: dup
      // 38f1: ldc 0.96
      // 38f3: ldc_w -249.062
      // 38f6: ldc_w 2.227
      // 38f9: ldc_w -250.055
      // 38fc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 38ff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3902: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3905: aastore
      // 3906: dup
      // 3907: bipush 24
      // 3909: new net/minecraft/client/animation/Keyframe
      // 390c: dup
      // 390d: fconst_1
      // 390e: ldc_w -185.99
      // 3911: ldc_w 5.009
      // 3914: ldc_w -186.849
      // 3917: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 391a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 391d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3920: aastore
      // 3921: dup
      // 3922: bipush 25
      // 3924: new net/minecraft/client/animation/Keyframe
      // 3927: dup
      // 3928: ldc 1.04
      // 392a: ldc_w -183.028
      // 392d: ldc_w 7.317
      // 3930: ldc_w -183.687
      // 3933: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3936: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3939: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 393c: aastore
      // 393d: dup
      // 393e: bipush 26
      // 3940: new net/minecraft/client/animation/Keyframe
      // 3943: dup
      // 3944: ldc 1.08
      // 3946: ldc_w -182.462
      // 3949: ldc_w 8.401
      // 394c: ldc_w -182.899
      // 394f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3952: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3955: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3958: aastore
      // 3959: dup
      // 395a: bipush 27
      // 395c: new net/minecraft/client/animation/Keyframe
      // 395f: dup
      // 3960: ldc 1.12
      // 3962: ldc_w -182.831
      // 3965: ldc_w 7.998
      // 3968: ldc_w -183.077
      // 396b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 396e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3971: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3974: aastore
      // 3975: dup
      // 3976: bipush 28
      // 3978: new net/minecraft/client/animation/Keyframe
      // 397b: dup
      // 397c: ldc 1.17
      // 397e: ldc_w -184.402
      // 3981: ldc_w 6.314
      // 3984: ldc_w -184.516
      // 3987: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 398a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 398d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3990: aastore
      // 3991: dup
      // 3992: bipush 29
      // 3994: new net/minecraft/client/animation/Keyframe
      // 3997: dup
      // 3998: ldc 1.21
      // 399a: ldc_w -187.248
      // 399d: ldc_w 4.788
      // 39a0: ldc_w -187.296
      // 39a3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 39a6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39a9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39ac: aastore
      // 39ad: dup
      // 39ae: bipush 30
      // 39b0: new net/minecraft/client/animation/Keyframe
      // 39b3: dup
      // 39b4: ldc 1.25
      // 39b6: ldc_w -184.777
      // 39b9: ldc_w 6.074
      // 39bc: ldc_w -184.8
      // 39bf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 39c2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39c5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39c8: aastore
      // 39c9: dup
      // 39ca: bipush 31
      // 39cc: new net/minecraft/client/animation/Keyframe
      // 39cf: dup
      // 39d0: ldc 1.29
      // 39d2: ldc_w -179.234
      // 39d5: ldc_w -52.213
      // 39d8: ldc_w -177.899
      // 39db: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 39de: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39e1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39e4: aastore
      // 39e5: dup
      // 39e6: bipush 32
      // 39e8: new net/minecraft/client/animation/Keyframe
      // 39eb: dup
      // 39ec: ldc 1.33
      // 39ee: ldc_w -198.043
      // 39f1: ldc_w 31.337
      // 39f4: ldc_w -164.029
      // 39f7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 39fa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39fd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a00: aastore
      // 3a01: dup
      // 3a02: bipush 33
      // 3a04: new net/minecraft/client/animation/Keyframe
      // 3a07: dup
      // 3a08: ldc 1.38
      // 3a0a: ldc_w -115.397
      // 3a0d: ldc_w 28.151
      // 3a10: ldc_w -235.303
      // 3a13: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a16: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a19: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a1c: aastore
      // 3a1d: dup
      // 3a1e: bipush 34
      // 3a20: new net/minecraft/client/animation/Keyframe
      // 3a23: dup
      // 3a24: ldc 1.42
      // 3a26: ldc_w -149.348
      // 3a29: ldc_w 65.065
      // 3a2c: ldc_w -207.534
      // 3a2f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a32: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a35: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a38: aastore
      // 3a39: dup
      // 3a3a: bipush 35
      // 3a3c: new net/minecraft/client/animation/Keyframe
      // 3a3f: dup
      // 3a40: ldc 1.46
      // 3a42: ldc_w -176.346
      // 3a45: ldc_w 74.853
      // 3a48: ldc_w -182.115
      // 3a4b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a4e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a51: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a54: aastore
      // 3a55: dup
      // 3a56: bipush 36
      // 3a58: new net/minecraft/client/animation/Keyframe
      // 3a5b: dup
      // 3a5c: ldc 1.5
      // 3a5e: ldc_w -174.622
      // 3a61: ldc_w 88.309
      // 3a64: ldc_w -183.679
      // 3a67: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a6a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a6d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a70: aastore
      // 3a71: dup
      // 3a72: bipush 37
      // 3a74: new net/minecraft/client/animation/Keyframe
      // 3a77: dup
      // 3a78: ldc 1.54
      // 3a7a: ldc_w -174.565
      // 3a7d: ldc_w 95.057
      // 3a80: ldc_w -183.705
      // 3a83: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a86: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a89: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a8c: aastore
      // 3a8d: dup
      // 3a8e: bipush 38
      // 3a90: new net/minecraft/client/animation/Keyframe
      // 3a93: dup
      // 3a94: ldc 1.58
      // 3a96: ldc_w -176.959
      // 3a99: ldc_w 96.007
      // 3a9c: ldc_w -181.392
      // 3a9f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3aa2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3aa5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3aa8: aastore
      // 3aa9: dup
      // 3aaa: bipush 39
      // 3aac: new net/minecraft/client/animation/Keyframe
      // 3aaf: dup
      // 3ab0: ldc 1.62
      // 3ab2: ldc_w -188.624
      // 3ab5: ldc_w 88.226
      // 3ab8: ldc_w -170.24
      // 3abb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3abe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ac1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ac4: aastore
      // 3ac5: dup
      // 3ac6: bipush 40
      // 3ac8: new net/minecraft/client/animation/Keyframe
      // 3acb: dup
      // 3acc: ldc 1.67
      // 3ace: ldc_w -198.731
      // 3ad1: ldc_w 64.96
      // 3ad4: ldc_w -161.134
      // 3ad7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ada: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3add: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ae0: aastore
      // 3ae1: dup
      // 3ae2: bipush 41
      // 3ae4: new net/minecraft/client/animation/Keyframe
      // 3ae7: dup
      // 3ae8: ldc 1.71
      // 3aea: ldc_w -201.663
      // 3aed: ldc_w 34.185
      // 3af0: ldc_w -160.463
      // 3af3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3af6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3af9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3afc: aastore
      // 3afd: dup
      // 3afe: bipush 42
      // 3b00: new net/minecraft/client/animation/Keyframe
      // 3b03: dup
      // 3b04: ldc 1.75
      // 3b06: ldc_w -197.683
      // 3b09: ldc_w 21.124
      // 3b0c: ldc_w -166.33
      // 3b0f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3b12: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b15: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b18: aastore
      // 3b19: dup
      // 3b1a: bipush 43
      // 3b1c: new net/minecraft/client/animation/Keyframe
      // 3b1f: dup
      // 3b20: ldc 1.79
      // 3b22: ldc_w -188.536
      // 3b25: ldc_w 24.902
      // 3b28: ldc_w -173.057
      // 3b2b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3b2e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b31: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b34: aastore
      // 3b35: dup
      // 3b36: bipush 44
      // 3b38: new net/minecraft/client/animation/Keyframe
      // 3b3b: dup
      // 3b3c: ldc_w 1.83
      // 3b3f: ldc_w -180.051
      // 3b42: ldc_w 30.043
      // 3b45: ldc_w -179.721
      // 3b48: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3b4b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b4e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b51: aastore
      // 3b52: dup
      // 3b53: bipush 45
      // 3b55: new net/minecraft/client/animation/Keyframe
      // 3b58: dup
      // 3b59: ldc_w 1.88
      // 3b5c: ldc_w -176.813
      // 3b5f: ldc_w 31.96
      // 3b62: ldc_w -182.413
      // 3b65: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3b68: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b6b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b6e: aastore
      // 3b6f: dup
      // 3b70: bipush 46
      // 3b72: new net/minecraft/client/animation/Keyframe
      // 3b75: dup
      // 3b76: ldc_w 1.92
      // 3b79: ldc_w -176.879
      // 3b7c: ldc_w 31.714
      // 3b7f: ldc_w -182.366
      // 3b82: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3b85: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b88: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b8b: aastore
      // 3b8c: dup
      // 3b8d: bipush 47
      // 3b8f: new net/minecraft/client/animation/Keyframe
      // 3b92: dup
      // 3b93: ldc_w 1.96
      // 3b96: ldc_w -177.03
      // 3b99: ldc_w 30.379
      // 3b9c: ldc_w -182.291
      // 3b9f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ba2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ba5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ba8: aastore
      // 3ba9: dup
      // 3baa: bipush 48
      // 3bac: new net/minecraft/client/animation/Keyframe
      // 3baf: dup
      // 3bb0: fconst_2
      // 3bb1: ldc_w -177.212
      // 3bb4: ldc_w 27.934
      // 3bb7: ldc_w -182.254
      // 3bba: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3bbd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3bc0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3bc3: aastore
      // 3bc4: dup
      // 3bc5: bipush 49
      // 3bc7: new net/minecraft/client/animation/Keyframe
      // 3bca: dup
      // 3bcb: ldc_w 2.04
      // 3bce: ldc_w -177.327
      // 3bd1: ldc_w 24.568
      // 3bd4: ldc_w -182.358
      // 3bd7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3bda: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3bdd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3be0: aastore
      // 3be1: dup
      // 3be2: bipush 50
      // 3be4: new net/minecraft/client/animation/Keyframe
      // 3be7: dup
      // 3be8: ldc_w 2.08
      // 3beb: ldc_w -177.215
      // 3bee: ldc_w 20.168
      // 3bf1: ldc_w -182.807
      // 3bf4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3bf7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3bfa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3bfd: aastore
      // 3bfe: dup
      // 3bff: bipush 51
      // 3c01: new net/minecraft/client/animation/Keyframe
      // 3c04: dup
      // 3c05: ldc_w 2.12
      // 3c08: ldc_w -173.279
      // 3c0b: ldc_w 25.752
      // 3c0e: ldc_w -185.641
      // 3c11: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c14: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c17: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c1a: aastore
      // 3c1b: dup
      // 3c1c: bipush 52
      // 3c1e: new net/minecraft/client/animation/Keyframe
      // 3c21: dup
      // 3c22: ldc_w 2.17
      // 3c25: ldc_w -167.284
      // 3c28: ldc_w 29.081
      // 3c2b: ldc_w -190.628
      // 3c2e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c31: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c34: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c37: aastore
      // 3c38: dup
      // 3c39: bipush 53
      // 3c3b: new net/minecraft/client/animation/Keyframe
      // 3c3e: dup
      // 3c3f: ldc_w 2.21
      // 3c42: ldc_w -168.131
      // 3c45: ldc_w 13.432
      // 3c48: ldc_w -189.92
      // 3c4b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c4e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c51: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c54: aastore
      // 3c55: dup
      // 3c56: bipush 54
      // 3c58: new net/minecraft/client/animation/Keyframe
      // 3c5b: dup
      // 3c5c: ldc 2.25
      // 3c5e: ldc_w -169.257
      // 3c61: ldc_w -2.478
      // 3c64: ldc_w -186.076
      // 3c67: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c6a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c6d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c70: aastore
      // 3c71: dup
      // 3c72: bipush 55
      // 3c74: new net/minecraft/client/animation/Keyframe
      // 3c77: dup
      // 3c78: ldc 2.29
      // 3c7a: ldc_w -174.243
      // 3c7d: ldc_w -16.948
      // 3c80: ldc_w -182.106
      // 3c83: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c86: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c89: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c8c: aastore
      // 3c8d: dup
      // 3c8e: bipush 56
      // 3c90: new net/minecraft/client/animation/Keyframe
      // 3c93: dup
      // 3c94: ldc 2.33
      // 3c96: ldc_w -178.389
      // 3c99: ldc_w -29.308
      // 3c9c: ldc_w -178.069
      // 3c9f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ca2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ca5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ca8: aastore
      // 3ca9: dup
      // 3caa: bipush 57
      // 3cac: new net/minecraft/client/animation/Keyframe
      // 3caf: dup
      // 3cb0: ldc 2.38
      // 3cb2: ldc_w -179.451
      // 3cb5: ldc_w -29.086
      // 3cb8: ldc_w -176.833
      // 3cbb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3cbe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3cc1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3cc4: aastore
      // 3cc5: dup
      // 3cc6: bipush 58
      // 3cc8: new net/minecraft/client/animation/Keyframe
      // 3ccb: dup
      // 3ccc: ldc 2.42
      // 3cce: ldc_w -177.906
      // 3cd1: ldc_w -16.466
      // 3cd4: ldc_w -177.374
      // 3cd7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3cda: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3cdd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ce0: aastore
      // 3ce1: dup
      // 3ce2: bipush 59
      // 3ce4: new net/minecraft/client/animation/Keyframe
      // 3ce7: dup
      // 3ce8: ldc 2.46
      // 3cea: ldc_w -173.312
      // 3ced: ldc_w -3.055
      // 3cf0: ldc_w -175.533
      // 3cf3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3cf6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3cf9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3cfc: aastore
      // 3cfd: dup
      // 3cfe: bipush 60
      // 3d00: new net/minecraft/client/animation/Keyframe
      // 3d03: dup
      // 3d04: ldc 2.5
      // 3d06: ldc_w -180.389
      // 3d09: ldc_w 8.181
      // 3d0c: ldc_w -184.083
      // 3d0f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d12: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d15: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d18: aastore
      // 3d19: dup
      // 3d1a: bipush 61
      // 3d1c: new net/minecraft/client/animation/Keyframe
      // 3d1f: dup
      // 3d20: ldc 2.54
      // 3d22: ldc_w -178.552
      // 3d25: ldc_w 16.81
      // 3d28: ldc_w -182.211
      // 3d2b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d2e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d31: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d34: aastore
      // 3d35: dup
      // 3d36: bipush 62
      // 3d38: new net/minecraft/client/animation/Keyframe
      // 3d3b: dup
      // 3d3c: ldc_w 2.58
      // 3d3f: ldc_w -178.813
      // 3d42: ldc_w 19.76
      // 3d45: ldc_w -181.603
      // 3d48: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d4b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d4e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d51: aastore
      // 3d52: dup
      // 3d53: bipush 63
      // 3d55: new net/minecraft/client/animation/Keyframe
      // 3d58: dup
      // 3d59: ldc_w 2.62
      // 3d5c: ldc_w -179.215
      // 3d5f: ldc_w 18.76
      // 3d62: ldc_w -181.421
      // 3d65: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d68: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d6b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d6e: aastore
      // 3d6f: dup
      // 3d70: bipush 64
      // 3d72: new net/minecraft/client/animation/Keyframe
      // 3d75: dup
      // 3d76: ldc_w 2.67
      // 3d79: ldc_w -179.044
      // 3d7c: ldc_w 18.938
      // 3d7f: ldc_w -181.528
      // 3d82: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d85: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d88: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d8b: aastore
      // 3d8c: dup
      // 3d8d: bipush 65
      // 3d8f: new net/minecraft/client/animation/Keyframe
      // 3d92: dup
      // 3d93: ldc_w 2.71
      // 3d96: ldc_w -178.663
      // 3d99: ldc_w 20.011
      // 3d9c: ldc_w -181.691
      // 3d9f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3da2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3da5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3da8: aastore
      // 3da9: dup
      // 3daa: bipush 66
      // 3dac: new net/minecraft/client/animation/Keyframe
      // 3daf: dup
      // 3db0: ldc_w 2.75
      // 3db3: ldc_w -178.266
      // 3db6: ldc_w 22.293
      // 3db9: ldc_w -181.773
      // 3dbc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3dbf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3dc2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3dc5: aastore
      // 3dc6: dup
      // 3dc7: bipush 67
      // 3dc9: new net/minecraft/client/animation/Keyframe
      // 3dcc: dup
      // 3dcd: ldc_w 2.79
      // 3dd0: ldc_w -177.956
      // 3dd3: ldc_w 24.866
      // 3dd6: ldc_w -181.819
      // 3dd9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ddc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ddf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3de2: aastore
      // 3de3: dup
      // 3de4: bipush 68
      // 3de6: new net/minecraft/client/animation/Keyframe
      // 3de9: dup
      // 3dea: ldc 2.83
      // 3dec: ldc_w -177.837
      // 3def: ldc_w 26.071
      // 3df2: ldc_w -181.837
      // 3df5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3df8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3dfb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3dfe: aastore
      // 3dff: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3e02: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3e05: ldc_w "CTR_upperarm_left.001"
      // 3e08: new net/minecraft/client/animation/AnimationChannel
      // 3e0b: dup
      // 3e0c: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3e0f: bipush 55
      // 3e11: anewarray 28
      // 3e14: dup
      // 3e15: bipush 0
      // 3e16: new net/minecraft/client/animation/Keyframe
      // 3e19: dup
      // 3e1a: fconst_0
      // 3e1b: dconst_1
      // 3e1c: dconst_1
      // 3e1d: ldc2_w 1.7860000133514404
      // 3e20: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3e23: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e26: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e29: aastore
      // 3e2a: dup
      // 3e2b: bipush 1
      // 3e2c: new net/minecraft/client/animation/Keyframe
      // 3e2f: dup
      // 3e30: ldc 0.04
      // 3e32: dconst_1
      // 3e33: dconst_1
      // 3e34: ldc2_w 1.7610000371932983
      // 3e37: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3e3a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e3d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e40: aastore
      // 3e41: dup
      // 3e42: bipush 2
      // 3e43: new net/minecraft/client/animation/Keyframe
      // 3e46: dup
      // 3e47: ldc 0.08
      // 3e49: dconst_1
      // 3e4a: dconst_1
      // 3e4b: ldc2_w 1.6950000524520874
      // 3e4e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3e51: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e54: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e57: aastore
      // 3e58: dup
      // 3e59: bipush 3
      // 3e5a: new net/minecraft/client/animation/Keyframe
      // 3e5d: dup
      // 3e5e: ldc 0.12
      // 3e60: dconst_1
      // 3e61: dconst_1
      // 3e62: ldc2_w 1.6059999465942383
      // 3e65: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3e68: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e6b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e6e: aastore
      // 3e6f: dup
      // 3e70: bipush 4
      // 3e71: new net/minecraft/client/animation/Keyframe
      // 3e74: dup
      // 3e75: ldc 0.21
      // 3e77: dconst_1
      // 3e78: dconst_1
      // 3e79: ldc2_w 1.4450000524520874
      // 3e7c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3e7f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e82: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e85: aastore
      // 3e86: dup
      // 3e87: bipush 5
      // 3e88: new net/minecraft/client/animation/Keyframe
      // 3e8b: dup
      // 3e8c: ldc 0.25
      // 3e8e: dconst_1
      // 3e8f: dconst_1
      // 3e90: ldc2_w 1.409999966621399
      // 3e93: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3e96: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e99: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e9c: aastore
      // 3e9d: dup
      // 3e9e: bipush 6
      // 3ea0: new net/minecraft/client/animation/Keyframe
      // 3ea3: dup
      // 3ea4: ldc 0.29
      // 3ea6: dconst_1
      // 3ea7: dconst_1
      // 3ea8: ldc2_w 1.4199999570846558
      // 3eab: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3eae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3eb1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3eb4: aastore
      // 3eb5: dup
      // 3eb6: bipush 7
      // 3eb8: new net/minecraft/client/animation/Keyframe
      // 3ebb: dup
      // 3ebc: ldc 0.38
      // 3ebe: dconst_1
      // 3ebf: dconst_1
      // 3ec0: ldc2_w 1.5379999876022339
      // 3ec3: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3ec6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ec9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ecc: aastore
      // 3ecd: dup
      // 3ece: bipush 8
      // 3ed0: new net/minecraft/client/animation/Keyframe
      // 3ed3: dup
      // 3ed4: ldc 0.42
      // 3ed6: dconst_1
      // 3ed7: dconst_1
      // 3ed8: ldc2_w 1.5980000495910645
      // 3edb: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3ede: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ee1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ee4: aastore
      // 3ee5: dup
      // 3ee6: bipush 9
      // 3ee8: new net/minecraft/client/animation/Keyframe
      // 3eeb: dup
      // 3eec: ldc 0.46
      // 3eee: dconst_1
      // 3eef: dconst_1
      // 3ef0: ldc2_w 1.621999979019165
      // 3ef3: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3ef6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ef9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3efc: aastore
      // 3efd: dup
      // 3efe: bipush 10
      // 3f00: new net/minecraft/client/animation/Keyframe
      // 3f03: dup
      // 3f04: ldc 0.62
      // 3f06: dconst_1
      // 3f07: dconst_1
      // 3f08: ldc2_w 1.6089999675750732
      // 3f0b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3f0e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f11: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f14: aastore
      // 3f15: dup
      // 3f16: bipush 11
      // 3f18: new net/minecraft/client/animation/Keyframe
      // 3f1b: dup
      // 3f1c: ldc 0.79
      // 3f1e: dconst_1
      // 3f1f: dconst_1
      // 3f20: ldc2_w 1.6419999599456787
      // 3f23: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3f26: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f29: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f2c: aastore
      // 3f2d: dup
      // 3f2e: bipush 12
      // 3f30: new net/minecraft/client/animation/Keyframe
      // 3f33: dup
      // 3f34: ldc 0.83
      // 3f36: dconst_1
      // 3f37: dconst_1
      // 3f38: ldc2_w 1.6349999904632568
      // 3f3b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3f3e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f41: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f44: aastore
      // 3f45: dup
      // 3f46: bipush 13
      // 3f48: new net/minecraft/client/animation/Keyframe
      // 3f4b: dup
      // 3f4c: ldc 0.88
      // 3f4e: dconst_1
      // 3f4f: dconst_1
      // 3f50: ldc2_w 1.5959999561309814
      // 3f53: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3f56: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f59: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f5c: aastore
      // 3f5d: dup
      // 3f5e: bipush 14
      // 3f60: new net/minecraft/client/animation/Keyframe
      // 3f63: dup
      // 3f64: fconst_1
      // 3f65: dconst_1
      // 3f66: dconst_1
      // 3f67: ldc2_w 1.4479999542236328
      // 3f6a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3f6d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f70: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f73: aastore
      // 3f74: dup
      // 3f75: bipush 15
      // 3f77: new net/minecraft/client/animation/Keyframe
      // 3f7a: dup
      // 3f7b: ldc 1.04
      // 3f7d: dconst_1
      // 3f7e: dconst_1
      // 3f7f: ldc2_w 1.4129999876022339
      // 3f82: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3f85: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f88: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f8b: aastore
      // 3f8c: dup
      // 3f8d: bipush 16
      // 3f8f: new net/minecraft/client/animation/Keyframe
      // 3f92: dup
      // 3f93: ldc 1.08
      // 3f95: dconst_1
      // 3f96: dconst_1
      // 3f97: ldc2_w 1.3910000324249268
      // 3f9a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3f9d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3fa0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3fa3: aastore
      // 3fa4: dup
      // 3fa5: bipush 17
      // 3fa7: new net/minecraft/client/animation/Keyframe
      // 3faa: dup
      // 3fab: ldc 1.12
      // 3fad: dconst_1
      // 3fae: dconst_1
      // 3faf: ldc2_w 1.3819999694824219
      // 3fb2: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3fb5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3fb8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3fbb: aastore
      // 3fbc: dup
      // 3fbd: bipush 18
      // 3fbf: new net/minecraft/client/animation/Keyframe
      // 3fc2: dup
      // 3fc3: ldc 1.17
      // 3fc5: dconst_1
      // 3fc6: dconst_1
      // 3fc7: ldc2_w 1.3819999694824219
      // 3fca: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3fcd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3fd0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3fd3: aastore
      // 3fd4: dup
      // 3fd5: bipush 19
      // 3fd7: new net/minecraft/client/animation/Keyframe
      // 3fda: dup
      // 3fdb: ldc 1.21
      // 3fdd: dconst_1
      // 3fde: dconst_1
      // 3fdf: ldc2_w 1.36899995803833
      // 3fe2: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3fe5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3fe8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3feb: aastore
      // 3fec: dup
      // 3fed: bipush 20
      // 3fef: new net/minecraft/client/animation/Keyframe
      // 3ff2: dup
      // 3ff3: ldc 1.25
      // 3ff5: dconst_1
      // 3ff6: dconst_1
      // 3ff7: ldc2_w 1.3020000457763672
      // 3ffa: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3ffd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4000: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4003: aastore
      // 4004: dup
      // 4005: bipush 21
      // 4007: new net/minecraft/client/animation/Keyframe
      // 400a: dup
      // 400b: ldc 1.29
      // 400d: dconst_1
      // 400e: dconst_1
      // 400f: ldc2_w 0.9070000052452087
      // 4012: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4015: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4018: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 401b: aastore
      // 401c: dup
      // 401d: bipush 22
      // 401f: new net/minecraft/client/animation/Keyframe
      // 4022: dup
      // 4023: ldc 1.33
      // 4025: dconst_1
      // 4026: dconst_1
      // 4027: ldc2_w 1.309999942779541
      // 402a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 402d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4030: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4033: aastore
      // 4034: dup
      // 4035: bipush 23
      // 4037: new net/minecraft/client/animation/Keyframe
      // 403a: dup
      // 403b: ldc 1.38
      // 403d: dconst_1
      // 403e: dconst_1
      // 403f: ldc2_w 1.5199999809265137
      // 4042: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4045: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4048: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 404b: aastore
      // 404c: dup
      // 404d: bipush 24
      // 404f: new net/minecraft/client/animation/Keyframe
      // 4052: dup
      // 4053: ldc 1.42
      // 4055: dconst_1
      // 4056: dconst_1
      // 4057: ldc2_w 0.8379999995231628
      // 405a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 405d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4060: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4063: aastore
      // 4064: dup
      // 4065: bipush 25
      // 4067: new net/minecraft/client/animation/Keyframe
      // 406a: dup
      // 406b: ldc 1.46
      // 406d: dconst_1
      // 406e: dconst_1
      // 406f: ldc2_w 1.1629999876022339
      // 4072: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4075: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4078: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 407b: aastore
      // 407c: dup
      // 407d: bipush 26
      // 407f: new net/minecraft/client/animation/Keyframe
      // 4082: dup
      // 4083: ldc 1.54
      // 4085: dconst_1
      // 4086: dconst_1
      // 4087: ldc2_w 1.1749999523162842
      // 408a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 408d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4090: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4093: aastore
      // 4094: dup
      // 4095: bipush 27
      // 4097: new net/minecraft/client/animation/Keyframe
      // 409a: dup
      // 409b: ldc 1.62
      // 409d: dconst_1
      // 409e: dconst_1
      // 409f: ldc2_w 1.1480000019073486
      // 40a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 40a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 40a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 40ab: aastore
      // 40ac: dup
      // 40ad: bipush 28
      // 40af: new net/minecraft/client/animation/Keyframe
      // 40b2: dup
      // 40b3: ldc 1.67
      // 40b5: dconst_1
      // 40b6: dconst_1
      // 40b7: ldc2_w 1.215999960899353
      // 40ba: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 40bd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 40c0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 40c3: aastore
      // 40c4: dup
      // 40c5: bipush 29
      // 40c7: new net/minecraft/client/animation/Keyframe
      // 40ca: dup
      // 40cb: ldc 1.71
      // 40cd: dconst_1
      // 40ce: dconst_1
      // 40cf: ldc2_w 1.4229999780654907
      // 40d2: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 40d5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 40d8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 40db: aastore
      // 40dc: dup
      // 40dd: bipush 30
      // 40df: new net/minecraft/client/animation/Keyframe
      // 40e2: dup
      // 40e3: ldc 1.75
      // 40e5: dconst_1
      // 40e6: dconst_1
      // 40e7: ldc2_w 1.5520000457763672
      // 40ea: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 40ed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 40f0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 40f3: aastore
      // 40f4: dup
      // 40f5: bipush 31
      // 40f7: new net/minecraft/client/animation/Keyframe
      // 40fa: dup
      // 40fb: ldc 1.79
      // 40fd: dconst_1
      // 40fe: dconst_1
      // 40ff: ldc2_w 1.5149999856948853
      // 4102: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4105: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4108: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 410b: aastore
      // 410c: dup
      // 410d: bipush 32
      // 410f: new net/minecraft/client/animation/Keyframe
      // 4112: dup
      // 4113: ldc_w 1.83
      // 4116: dconst_1
      // 4117: dconst_1
      // 4118: ldc2_w 1.4609999656677246
      // 411b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 411e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4121: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4124: aastore
      // 4125: dup
      // 4126: bipush 33
      // 4128: new net/minecraft/client/animation/Keyframe
      // 412b: dup
      // 412c: ldc_w 1.88
      // 412f: dconst_1
      // 4130: dconst_1
      // 4131: ldc2_w 1.4119999408721924
      // 4134: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4137: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 413a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 413d: aastore
      // 413e: dup
      // 413f: bipush 34
      // 4141: new net/minecraft/client/animation/Keyframe
      // 4144: dup
      // 4145: ldc_w 1.92
      // 4148: dconst_1
      // 4149: dconst_1
      // 414a: ldc2_w 1.3480000495910645
      // 414d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4150: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4153: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4156: aastore
      // 4157: dup
      // 4158: bipush 35
      // 415a: new net/minecraft/client/animation/Keyframe
      // 415d: dup
      // 415e: ldc_w 1.96
      // 4161: dconst_1
      // 4162: dconst_1
      // 4163: ldc2_w 1.25600004196167
      // 4166: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4169: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 416c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 416f: aastore
      // 4170: dup
      // 4171: bipush 36
      // 4173: new net/minecraft/client/animation/Keyframe
      // 4176: dup
      // 4177: fconst_2
      // 4178: dconst_1
      // 4179: dconst_1
      // 417a: ldc2_w 1.1390000581741333
      // 417d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4180: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4183: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4186: aastore
      // 4187: dup
      // 4188: bipush 37
      // 418a: new net/minecraft/client/animation/Keyframe
      // 418d: dup
      // 418e: ldc_w 2.04
      // 4191: dconst_1
      // 4192: dconst_1
      // 4193: ldc2_w 1.0019999742507935
      // 4196: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4199: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 419c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 419f: aastore
      // 41a0: dup
      // 41a1: bipush 38
      // 41a3: new net/minecraft/client/animation/Keyframe
      // 41a6: dup
      // 41a7: ldc_w 2.08
      // 41aa: dconst_1
      // 41ab: dconst_1
      // 41ac: ldc2_w 0.8460000157356262
      // 41af: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 41b2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 41b5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 41b8: aastore
      // 41b9: dup
      // 41ba: bipush 39
      // 41bc: new net/minecraft/client/animation/Keyframe
      // 41bf: dup
      // 41c0: ldc_w 2.12
      // 41c3: dconst_1
      // 41c4: dconst_1
      // 41c5: ldc2_w 0.6019999980926514
      // 41c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 41cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 41ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 41d1: aastore
      // 41d2: dup
      // 41d3: bipush 40
      // 41d5: new net/minecraft/client/animation/Keyframe
      // 41d8: dup
      // 41d9: ldc_w 2.17
      // 41dc: dconst_1
      // 41dd: dconst_1
      // 41de: ldc2_w 0.4230000078678131
      // 41e1: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 41e4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 41e7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 41ea: aastore
      // 41eb: dup
      // 41ec: bipush 41
      // 41ee: new net/minecraft/client/animation/Keyframe
      // 41f1: dup
      // 41f2: ldc_w 2.21
      // 41f5: dconst_1
      // 41f6: dconst_1
      // 41f7: ldc2_w 0.3970000147819519
      // 41fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 41fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4200: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4203: aastore
      // 4204: dup
      // 4205: bipush 42
      // 4207: new net/minecraft/client/animation/Keyframe
      // 420a: dup
      // 420b: ldc 2.25
      // 420d: dconst_1
      // 420e: dconst_1
      // 420f: ldc2_w 0.4350000023841858
      // 4212: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4215: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4218: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 421b: aastore
      // 421c: dup
      // 421d: bipush 43
      // 421f: new net/minecraft/client/animation/Keyframe
      // 4222: dup
      // 4223: ldc 2.29
      // 4225: dconst_1
      // 4226: dconst_1
      // 4227: ldc2_w 0.5989999771118164
      // 422a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 422d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4230: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4233: aastore
      // 4234: dup
      // 4235: bipush 44
      // 4237: new net/minecraft/client/animation/Keyframe
      // 423a: dup
      // 423b: ldc 2.33
      // 423d: dconst_1
      // 423e: dconst_1
      // 423f: ldc2_w 0.9169999957084656
      // 4242: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4245: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4248: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 424b: aastore
      // 424c: dup
      // 424d: bipush 45
      // 424f: new net/minecraft/client/animation/Keyframe
      // 4252: dup
      // 4253: ldc 2.38
      // 4255: dconst_1
      // 4256: dconst_1
      // 4257: ldc2_w 1.1419999599456787
      // 425a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 425d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4260: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4263: aastore
      // 4264: dup
      // 4265: bipush 46
      // 4267: new net/minecraft/client/animation/Keyframe
      // 426a: dup
      // 426b: ldc 2.46
      // 426d: dconst_1
      // 426e: dconst_1
      // 426f: ldc2_w 1.2569999694824219
      // 4272: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4275: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4278: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 427b: aastore
      // 427c: dup
      // 427d: bipush 47
      // 427f: new net/minecraft/client/animation/Keyframe
      // 4282: dup
      // 4283: ldc 2.5
      // 4285: dconst_1
      // 4286: dconst_1
      // 4287: ldc2_w 1.3930000066757202
      // 428a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 428d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4290: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4293: aastore
      // 4294: dup
      // 4295: bipush 48
      // 4297: new net/minecraft/client/animation/Keyframe
      // 429a: dup
      // 429b: ldc 2.54
      // 429d: dconst_1
      // 429e: dconst_1
      // 429f: ldc2_w 1.6150000095367432
      // 42a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 42a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42ab: aastore
      // 42ac: dup
      // 42ad: bipush 49
      // 42af: new net/minecraft/client/animation/Keyframe
      // 42b2: dup
      // 42b3: ldc_w 2.58
      // 42b6: dconst_1
      // 42b7: dconst_1
      // 42b8: ldc2_w 1.8029999732971191
      // 42bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 42be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42c4: aastore
      // 42c5: dup
      // 42c6: bipush 50
      // 42c8: new net/minecraft/client/animation/Keyframe
      // 42cb: dup
      // 42cc: ldc_w 2.62
      // 42cf: dconst_1
      // 42d0: dconst_1
      // 42d1: ldc2_w 1.8669999837875366
      // 42d4: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 42d7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42da: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42dd: aastore
      // 42de: dup
      // 42df: bipush 51
      // 42e1: new net/minecraft/client/animation/Keyframe
      // 42e4: dup
      // 42e5: ldc_w 2.67
      // 42e8: dconst_1
      // 42e9: dconst_1
      // 42ea: ldc2_w 1.8289999961853027
      // 42ed: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 42f0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42f3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42f6: aastore
      // 42f7: dup
      // 42f8: bipush 52
      // 42fa: new net/minecraft/client/animation/Keyframe
      // 42fd: dup
      // 42fe: ldc_w 2.71
      // 4301: dconst_1
      // 4302: dconst_1
      // 4303: ldc2_w 1.774999976158142
      // 4306: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4309: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 430c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 430f: aastore
      // 4310: dup
      // 4311: bipush 53
      // 4313: new net/minecraft/client/animation/Keyframe
      // 4316: dup
      // 4317: ldc_w 2.75
      // 431a: dconst_1
      // 431b: dconst_1
      // 431c: ldc2_w 1.7619999647140503
      // 431f: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4322: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4325: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4328: aastore
      // 4329: dup
      // 432a: bipush 54
      // 432c: new net/minecraft/client/animation/Keyframe
      // 432f: dup
      // 4330: ldc 2.83
      // 4332: dconst_1
      // 4333: dconst_1
      // 4334: ldc2_w 1.7860000133514404
      // 4337: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 433a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 433d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4340: aastore
      // 4341: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4344: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4347: ldc_w "CTR_shoulder_right"
      // 434a: new net/minecraft/client/animation/AnimationChannel
      // 434d: dup
      // 434e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4351: bipush 53
      // 4353: anewarray 28
      // 4356: dup
      // 4357: bipush 0
      // 4358: new net/minecraft/client/animation/Keyframe
      // 435b: dup
      // 435c: fconst_0
      // 435d: ldc_w 0.016
      // 4360: ldc_w -1.1
      // 4363: ldc_w -0.172
      // 4366: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4369: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 436c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 436f: aastore
      // 4370: dup
      // 4371: bipush 1
      // 4372: new net/minecraft/client/animation/Keyframe
      // 4375: dup
      // 4376: ldc 0.04
      // 4378: ldc_w 0.06
      // 437b: ldc_w -0.871
      // 437e: ldc_w -0.215
      // 4381: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4384: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4387: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 438a: aastore
      // 438b: dup
      // 438c: bipush 2
      // 438d: new net/minecraft/client/animation/Keyframe
      // 4390: dup
      // 4391: ldc 0.08
      // 4393: ldc_w 0.181
      // 4396: ldc_w -0.244
      // 4399: ldc_w -0.332
      // 439c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 439f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43a2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43a5: aastore
      // 43a6: dup
      // 43a7: bipush 3
      // 43a8: new net/minecraft/client/animation/Keyframe
      // 43ab: dup
      // 43ac: ldc 0.17
      // 43ae: ldc_w 0.585
      // 43b1: ldc_w 1.851
      // 43b4: ldc_w -0.722
      // 43b7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 43ba: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43bd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43c0: aastore
      // 43c1: dup
      // 43c2: bipush 4
      // 43c3: new net/minecraft/client/animation/Keyframe
      // 43c6: dup
      // 43c7: ldc 0.21
      // 43c9: ldc 0.833
      // 43cb: ldc_w 3.142
      // 43ce: ldc_w -0.962
      // 43d1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 43d4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43d7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43da: aastore
      // 43db: dup
      // 43dc: bipush 5
      // 43dd: new net/minecraft/client/animation/Keyframe
      // 43e0: dup
      // 43e1: ldc 0.25
      // 43e3: ldc_w 1.091
      // 43e6: ldc_w 4.477
      // 43e9: ldc_w -1.211
      // 43ec: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 43ef: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43f2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43f5: aastore
      // 43f6: dup
      // 43f7: bipush 6
      // 43f9: new net/minecraft/client/animation/Keyframe
      // 43fc: dup
      // 43fd: ldc 0.29
      // 43ff: ldc_w 1.339
      // 4402: ldc_w 5.768
      // 4405: ldc_w -1.451
      // 4408: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 440b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 440e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4411: aastore
      // 4412: dup
      // 4413: bipush 7
      // 4415: new net/minecraft/client/animation/Keyframe
      // 4418: dup
      // 4419: ldc 0.33
      // 441b: ldc_w 1.562
      // 441e: ldc_w 6.926
      // 4421: ldc_w -1.667
      // 4424: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4427: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 442a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 442d: aastore
      // 442e: dup
      // 442f: bipush 8
      // 4431: new net/minecraft/client/animation/Keyframe
      // 4434: dup
      // 4435: ldc 0.38
      // 4437: ldc_w 1.743
      // 443a: ldc_w 7.863
      // 443d: ldc_w -1.841
      // 4440: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4443: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4446: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4449: aastore
      // 444a: dup
      // 444b: bipush 9
      // 444d: new net/minecraft/client/animation/Keyframe
      // 4450: dup
      // 4451: ldc 0.42
      // 4453: ldc_w 1.864
      // 4456: ldc_w 8.49
      // 4459: ldc_w -1.958
      // 445c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 445f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4462: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4465: aastore
      // 4466: dup
      // 4467: bipush 10
      // 4469: new net/minecraft/client/animation/Keyframe
      // 446c: dup
      // 446d: ldc 0.46
      // 446f: ldc_w 1.908
      // 4472: ldc_w 8.719
      // 4475: ldc_w -2.001
      // 4478: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 447b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 447e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4481: aastore
      // 4482: dup
      // 4483: bipush 11
      // 4485: new net/minecraft/client/animation/Keyframe
      // 4488: dup
      // 4489: ldc 0.71
      // 448b: ldc_w 1.908
      // 448e: ldc_w 8.719
      // 4491: ldc_w -2.001
      // 4494: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4497: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 449a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 449d: aastore
      // 449e: dup
      // 449f: bipush 12
      // 44a1: new net/minecraft/client/animation/Keyframe
      // 44a4: dup
      // 44a5: ldc 0.75
      // 44a7: ldc_w 1.956
      // 44aa: ldc_w 8.782
      // 44ad: ldc_w -1.793
      // 44b0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 44b3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44b6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 44b9: aastore
      // 44ba: dup
      // 44bb: bipush 13
      // 44bd: new net/minecraft/client/animation/Keyframe
      // 44c0: dup
      // 44c1: ldc 0.83
      // 44c3: ldc_w 2.169
      // 44c6: ldc_w 9.059
      // 44c9: ldc_w -0.877
      // 44cc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 44cf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44d2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 44d5: aastore
      // 44d6: dup
      // 44d7: bipush 14
      // 44d9: new net/minecraft/client/animation/Keyframe
      // 44dc: dup
      // 44dd: ldc 0.88
      // 44df: ldc_w 2.217
      // 44e2: ldc_w 9.122
      // 44e5: ldc_w -0.669
      // 44e8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 44eb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44ee: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 44f1: aastore
      // 44f2: dup
      // 44f3: bipush 15
      // 44f5: new net/minecraft/client/animation/Keyframe
      // 44f8: dup
      // 44f9: ldc 0.92
      // 44fb: ldc_w 0.785
      // 44fe: ldc_w 7.822
      // 4501: ldc_w -2.059
      // 4504: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4507: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 450a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 450d: aastore
      // 450e: dup
      // 450f: bipush 16
      // 4511: new net/minecraft/client/animation/Keyframe
      // 4514: dup
      // 4515: ldc 0.96
      // 4517: ldc_w -0.647
      // 451a: ldc_w 5.227
      // 451d: ldc_w -4.595
      // 4520: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4523: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4526: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4529: aastore
      // 452a: dup
      // 452b: bipush 17
      // 452d: new net/minecraft/client/animation/Keyframe
      // 4530: dup
      // 4531: fconst_1
      // 4532: ldc_w -0.638
      // 4535: ldc_w 2.959
      // 4538: ldc_w -6.313
      // 453b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 453e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4541: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4544: aastore
      // 4545: dup
      // 4546: bipush 18
      // 4548: new net/minecraft/client/animation/Keyframe
      // 454b: dup
      // 454c: ldc 1.04
      // 454e: ldc_w -0.578
      // 4551: ldc_w 1.274
      // 4554: ldc_w -7.212
      // 4557: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 455a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 455d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4560: aastore
      // 4561: dup
      // 4562: bipush 19
      // 4564: new net/minecraft/client/animation/Keyframe
      // 4567: dup
      // 4568: ldc 1.08
      // 456a: ldc_w -0.414
      // 456d: ldc_w 0.085
      // 4570: ldc_w -7.784
      // 4573: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4576: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4579: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 457c: aastore
      // 457d: dup
      // 457e: bipush 20
      // 4580: new net/minecraft/client/animation/Keyframe
      // 4583: dup
      // 4584: ldc 1.12
      // 4586: ldc_w -0.095
      // 4589: ldc_w -0.691
      // 458c: ldc_w -8.518
      // 458f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4592: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4595: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4598: aastore
      // 4599: dup
      // 459a: bipush 21
      // 459c: new net/minecraft/client/animation/Keyframe
      // 459f: dup
      // 45a0: ldc 1.17
      // 45a2: ldc_w 0.43
      // 45a5: ldc_w -1.14
      // 45a8: ldc_w -9.905
      // 45ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 45ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 45b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 45b4: aastore
      // 45b5: dup
      // 45b6: bipush 22
      // 45b8: new net/minecraft/client/animation/Keyframe
      // 45bb: dup
      // 45bc: ldc 1.21
      // 45be: ldc_w 1.214
      // 45c1: ldc_w -1.347
      // 45c4: ldc_w -12.435
      // 45c7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 45ca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 45cd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 45d0: aastore
      // 45d1: dup
      // 45d2: bipush 23
      // 45d4: new net/minecraft/client/animation/Keyframe
      // 45d7: dup
      // 45d8: ldc 1.25
      // 45da: ldc_w 2.308
      // 45dd: ldc_w -1.398
      // 45e0: ldc_w -16.6
      // 45e3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 45e6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 45e9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 45ec: aastore
      // 45ed: dup
      // 45ee: bipush 24
      // 45f0: new net/minecraft/client/animation/Keyframe
      // 45f3: dup
      // 45f4: ldc 1.29
      // 45f6: ldc_w 5.89
      // 45f9: ldc_w 11.871
      // 45fc: ldc_w -23.35
      // 45ff: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4602: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4605: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4608: aastore
      // 4609: dup
      // 460a: bipush 25
      // 460c: new net/minecraft/client/animation/Keyframe
      // 460f: dup
      // 4610: ldc 1.33
      // 4612: ldc_w 5.289
      // 4615: ldc_w 8.106
      // 4618: ldc_w -19.681
      // 461b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 461e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4621: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4624: aastore
      // 4625: dup
      // 4626: bipush 26
      // 4628: new net/minecraft/client/animation/Keyframe
      // 462b: dup
      // 462c: ldc 1.38
      // 462e: ldc_w 4.117
      // 4631: ldc_w 1.498
      // 4634: ldc_w -16.013
      // 4637: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 463a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 463d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4640: aastore
      // 4641: dup
      // 4642: bipush 27
      // 4644: new net/minecraft/client/animation/Keyframe
      // 4647: dup
      // 4648: ldc 1.42
      // 464a: ldc_w 3.417
      // 464d: ldc_w -1.595
      // 4650: ldc_w -16.916
      // 4653: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4656: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4659: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 465c: aastore
      // 465d: dup
      // 465e: bipush 28
      // 4660: new net/minecraft/client/animation/Keyframe
      // 4663: dup
      // 4664: ldc 1.79
      // 4666: ldc_w 3.417
      // 4669: ldc_w -1.595
      // 466c: ldc_w -16.916
      // 466f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4672: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4675: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4678: aastore
      // 4679: dup
      // 467a: bipush 29
      // 467c: new net/minecraft/client/animation/Keyframe
      // 467f: dup
      // 4680: ldc_w 1.83
      // 4683: ldc_w 3.533
      // 4686: ldc_w -1.819
      // 4689: ldc_w -16.252
      // 468c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 468f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4692: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4695: aastore
      // 4696: dup
      // 4697: bipush 30
      // 4699: new net/minecraft/client/animation/Keyframe
      // 469c: dup
      // 469d: ldc_w 1.88
      // 46a0: ldc_w 3.836
      // 46a3: ldc_w -2.41
      // 46a6: ldc_w -14.502
      // 46a9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 46ac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 46af: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 46b2: aastore
      // 46b3: dup
      // 46b4: bipush 31
      // 46b6: new net/minecraft/client/animation/Keyframe
      // 46b9: dup
      // 46ba: ldc_w 1.92
      // 46bd: ldc_w 4.265
      // 46c0: ldc_w -3.245
      // 46c3: ldc_w -12.028
      // 46c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 46c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 46cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 46cf: aastore
      // 46d0: dup
      // 46d1: bipush 32
      // 46d3: new net/minecraft/client/animation/Keyframe
      // 46d6: dup
      // 46d7: fconst_2
      // 46d8: ldc_w 5.249
      // 46db: ldc_w -5.159
      // 46de: ldc_w -6.355
      // 46e1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 46e4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 46e7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 46ea: aastore
      // 46eb: dup
      // 46ec: bipush 33
      // 46ee: new net/minecraft/client/animation/Keyframe
      // 46f1: dup
      // 46f2: ldc_w 2.04
      // 46f5: ldc_w 5.678
      // 46f8: ldc_w -5.994
      // 46fb: ldc_w -3.881
      // 46fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4701: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4704: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4707: aastore
      // 4708: dup
      // 4709: bipush 34
      // 470b: new net/minecraft/client/animation/Keyframe
      // 470e: dup
      // 470f: ldc_w 2.08
      // 4712: ldc_w 5.982
      // 4715: ldc_w -6.585
      // 4718: ldc_w -2.131
      // 471b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 471e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4721: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4724: aastore
      // 4725: dup
      // 4726: bipush 35
      // 4728: new net/minecraft/client/animation/Keyframe
      // 472b: dup
      // 472c: ldc_w 2.12
      // 472f: ldc_w 6.097
      // 4732: ldc_w -6.809
      // 4735: ldc_w -1.467
      // 4738: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 473b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 473e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4741: aastore
      // 4742: dup
      // 4743: bipush 36
      // 4745: new net/minecraft/client/animation/Keyframe
      // 4748: dup
      // 4749: ldc_w 2.17
      // 474c: ldc_w 5.962
      // 474f: ldc_w -6.795
      // 4752: ldc_w -2.352
      // 4755: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4758: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 475b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 475e: aastore
      // 475f: dup
      // 4760: bipush 37
      // 4762: new net/minecraft/client/animation/Keyframe
      // 4765: dup
      // 4766: ldc_w 2.21
      // 4769: ldc_w 5.573
      // 476c: ldc_w -6.7
      // 476f: ldc_w -4.462
      // 4772: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4775: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4778: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 477b: aastore
      // 477c: dup
      // 477d: bipush 38
      // 477f: new net/minecraft/client/animation/Keyframe
      // 4782: dup
      // 4783: ldc 2.25
      // 4785: ldc_w 4.958
      // 4788: ldc_w -6.44
      // 478b: ldc_w -6.98
      // 478e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4791: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4794: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4797: aastore
      // 4798: dup
      // 4799: bipush 39
      // 479b: new net/minecraft/client/animation/Keyframe
      // 479e: dup
      // 479f: ldc 2.29
      // 47a1: ldc_w 4.143
      // 47a4: ldc_w -5.935
      // 47a7: ldc_w -9.09
      // 47aa: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 47ad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 47b0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 47b3: aastore
      // 47b4: dup
      // 47b5: bipush 40
      // 47b7: new net/minecraft/client/animation/Keyframe
      // 47ba: dup
      // 47bb: ldc 2.33
      // 47bd: ldc_w 3.155
      // 47c0: ldc_w -5.102
      // 47c3: ldc_w -9.975
      // 47c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 47c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 47cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 47cf: aastore
      // 47d0: dup
      // 47d1: bipush 41
      // 47d3: new net/minecraft/client/animation/Keyframe
      // 47d6: dup
      // 47d7: ldc 2.38
      // 47d9: ldc_w 2.041
      // 47dc: ldc_w -3.596
      // 47df: ldc_w -9.943
      // 47e2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 47e5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 47e8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 47eb: aastore
      // 47ec: dup
      // 47ed: bipush 42
      // 47ef: new net/minecraft/client/animation/Keyframe
      // 47f2: dup
      // 47f3: ldc 2.42
      // 47f5: ldc_w 0.925
      // 47f8: ldc_w -1.485
      // 47fb: ldc_w -9.715
      // 47fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4801: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4804: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4807: aastore
      // 4808: dup
      // 4809: bipush 43
      // 480b: new net/minecraft/client/animation/Keyframe
      // 480e: dup
      // 480f: ldc 2.46
      // 4811: ldc_w -0.051
      // 4814: ldc_w 0.691
      // 4817: ldc_w -9.097
      // 481a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 481d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4820: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4823: aastore
      // 4824: dup
      // 4825: bipush 44
      // 4827: new net/minecraft/client/animation/Keyframe
      // 482a: dup
      // 482b: ldc 2.5
      // 482d: ldc_w -0.742
      // 4830: ldc_w 2.393
      // 4833: ldc_w -7.893
      // 4836: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4839: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 483c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 483f: aastore
      // 4840: dup
      // 4841: bipush 45
      // 4843: new net/minecraft/client/animation/Keyframe
      // 4846: dup
      // 4847: ldc 2.54
      // 4849: ldc_w -1.004
      // 484c: ldc_w 3.08
      // 484f: ldc_w -5.909
      // 4852: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4855: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4858: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 485b: aastore
      // 485c: dup
      // 485d: bipush 46
      // 485f: new net/minecraft/client/animation/Keyframe
      // 4862: dup
      // 4863: ldc_w 2.58
      // 4866: ldc_w 0.801
      // 4869: ldc_w 1.812
      // 486c: ldc_w -1.756
      // 486f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4872: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4875: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4878: aastore
      // 4879: dup
      // 487a: bipush 47
      // 487c: new net/minecraft/client/animation/Keyframe
      // 487f: dup
      // 4880: ldc_w 2.62
      // 4883: ldc_w 2.606
      // 4886: ldc_w 0.164
      // 4889: ldc_w 1.176
      // 488c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 488f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4892: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4895: aastore
      // 4896: dup
      // 4897: bipush 48
      // 4899: new net/minecraft/client/animation/Keyframe
      // 489c: dup
      // 489d: ldc_w 2.67
      // 48a0: ldc_w 2.337
      // 48a3: ldc_w -0.453
      // 48a6: ldc_w 1.036
      // 48a9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 48ac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 48af: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 48b2: aastore
      // 48b3: dup
      // 48b4: bipush 49
      // 48b6: new net/minecraft/client/animation/Keyframe
      // 48b9: dup
      // 48ba: ldc_w 2.71
      // 48bd: ldc_w 1.695
      // 48c0: ldc_w -0.827
      // 48c3: ldc_w 0.702
      // 48c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 48c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 48cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 48cf: aastore
      // 48d0: dup
      // 48d1: bipush 50
      // 48d3: new net/minecraft/client/animation/Keyframe
      // 48d6: dup
      // 48d7: ldc_w 2.75
      // 48da: ldc_w 0.928
      // 48dd: ldc_w -1.019
      // 48e0: ldc_w 0.302
      // 48e3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 48e6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 48e9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 48ec: aastore
      // 48ed: dup
      // 48ee: bipush 51
      // 48f0: new net/minecraft/client/animation/Keyframe
      // 48f3: dup
      // 48f4: ldc_w 2.79
      // 48f7: ldc_w 0.286
      // 48fa: ldc_w -1.09
      // 48fd: ldc_w -0.032
      // 4900: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4903: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4906: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4909: aastore
      // 490a: dup
      // 490b: bipush 52
      // 490d: new net/minecraft/client/animation/Keyframe
      // 4910: dup
      // 4911: ldc 2.83
      // 4913: ldc_w 0.016
      // 4916: ldc_w -1.1
      // 4919: ldc_w -0.172
      // 491c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 491f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4922: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4925: aastore
      // 4926: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4929: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 492c: ldc_w "CTR_upperarm_right"
      // 492f: new net/minecraft/client/animation/AnimationChannel
      // 4932: dup
      // 4933: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4936: bipush 59
      // 4938: anewarray 28
      // 493b: dup
      // 493c: bipush 0
      // 493d: new net/minecraft/client/animation/Keyframe
      // 4940: dup
      // 4941: fconst_0
      // 4942: ldc_w 0.839
      // 4945: ldc_w -2.513
      // 4948: ldc_w 0.452
      // 494b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 494e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4951: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4954: aastore
      // 4955: dup
      // 4956: bipush 1
      // 4957: new net/minecraft/client/animation/Keyframe
      // 495a: dup
      // 495b: ldc 0.04
      // 495d: ldc_w 0.392
      // 4960: ldc_w -2.716
      // 4963: ldc_w -1.06
      // 4966: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4969: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 496c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 496f: aastore
      // 4970: dup
      // 4971: bipush 2
      // 4972: new net/minecraft/client/animation/Keyframe
      // 4975: dup
      // 4976: ldc 0.08
      // 4978: ldc_w -0.809
      // 497b: ldc_w -3.262
      // 497e: ldc_w -4.794
      // 4981: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4984: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4987: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 498a: aastore
      // 498b: dup
      // 498c: bipush 3
      // 498d: new net/minecraft/client/animation/Keyframe
      // 4990: dup
      // 4991: ldc 0.12
      // 4993: ldc_w -2.606
      // 4996: ldc_w -4.137
      // 4999: ldc_w -9.573
      // 499c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 499f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 49a2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 49a5: aastore
      // 49a6: dup
      // 49a7: bipush 4
      // 49a8: new net/minecraft/client/animation/Keyframe
      // 49ab: dup
      // 49ac: ldc 0.33
      // 49ae: ldc_w -15.9408
      // 49b1: ldc_w 11.0124
      // 49b4: ldc_w -9.2388
      // 49b7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 49ba: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 49bd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 49c0: aastore
      // 49c1: dup
      // 49c2: bipush 5
      // 49c3: new net/minecraft/client/animation/Keyframe
      // 49c6: dup
      // 49c7: ldc 0.5
      // 49c9: ldc_w -31.401
      // 49cc: ldc_w 18.698
      // 49cf: ldc_w -23.348
      // 49d2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 49d5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 49d8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 49db: aastore
      // 49dc: dup
      // 49dd: bipush 6
      // 49df: new net/minecraft/client/animation/Keyframe
      // 49e2: dup
      // 49e3: ldc 0.54
      // 49e5: ldc_w -35.194
      // 49e8: ldc_w 26.135
      // 49eb: ldc_w -25.934
      // 49ee: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 49f1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 49f4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 49f7: aastore
      // 49f8: dup
      // 49f9: bipush 7
      // 49fb: new net/minecraft/client/animation/Keyframe
      // 49fe: dup
      // 49ff: ldc 0.58
      // 4a01: ldc_w -38.054
      // 4a04: ldc_w 29.914
      // 4a07: ldc_w -29.151
      // 4a0a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a0d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a10: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a13: aastore
      // 4a14: dup
      // 4a15: bipush 8
      // 4a17: new net/minecraft/client/animation/Keyframe
      // 4a1a: dup
      // 4a1b: ldc 0.62
      // 4a1d: ldc_w -38.176
      // 4a20: ldc_w 29.868
      // 4a23: ldc_w -30.808
      // 4a26: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a29: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a2c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a2f: aastore
      // 4a30: dup
      // 4a31: bipush 9
      // 4a33: new net/minecraft/client/animation/Keyframe
      // 4a36: dup
      // 4a37: ldc 0.67
      // 4a39: ldc_w -35.127
      // 4a3c: ldc_w 26.393
      // 4a3f: ldc_w -29.745
      // 4a42: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a45: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a48: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a4b: aastore
      // 4a4c: dup
      // 4a4d: bipush 10
      // 4a4f: new net/minecraft/client/animation/Keyframe
      // 4a52: dup
      // 4a53: ldc 0.71
      // 4a55: ldc_w -30.785
      // 4a58: ldc_w 20.933
      // 4a5b: ldc_w -26.995
      // 4a5e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a61: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a64: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a67: aastore
      // 4a68: dup
      // 4a69: bipush 11
      // 4a6b: new net/minecraft/client/animation/Keyframe
      // 4a6e: dup
      // 4a6f: ldc 0.75
      // 4a71: ldc_w -26.813
      // 4a74: ldc_w 15.9
      // 4a77: ldc_w -23.647
      // 4a7a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a7d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a80: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a83: aastore
      // 4a84: dup
      // 4a85: bipush 12
      // 4a87: new net/minecraft/client/animation/Keyframe
      // 4a8a: dup
      // 4a8b: ldc 0.79
      // 4a8d: ldc_w -25.163
      // 4a90: ldc_w 15.62
      // 4a93: ldc_w -21.274
      // 4a96: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a99: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a9c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a9f: aastore
      // 4aa0: dup
      // 4aa1: bipush 13
      // 4aa3: new net/minecraft/client/animation/Keyframe
      // 4aa6: dup
      // 4aa7: ldc 0.83
      // 4aa9: ldc_w -23.932
      // 4aac: ldc_w 20.556
      // 4aaf: ldc_w -19.313
      // 4ab2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ab5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ab8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4abb: aastore
      // 4abc: dup
      // 4abd: bipush 14
      // 4abf: new net/minecraft/client/animation/Keyframe
      // 4ac2: dup
      // 4ac3: ldc 0.88
      // 4ac5: ldc_w -20.796
      // 4ac8: ldc_w 26.712
      // 4acb: ldc_w -15.328
      // 4ace: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ad1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ad4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ad7: aastore
      // 4ad8: dup
      // 4ad9: bipush 15
      // 4adb: new net/minecraft/client/animation/Keyframe
      // 4ade: dup
      // 4adf: ldc 0.92
      // 4ae1: ldc_w -17.621
      // 4ae4: ldc_w 33.609
      // 4ae7: ldc_w -9.065
      // 4aea: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4aed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4af0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4af3: aastore
      // 4af4: dup
      // 4af5: bipush 16
      // 4af7: new net/minecraft/client/animation/Keyframe
      // 4afa: dup
      // 4afb: ldc 0.96
      // 4afd: ldc_w -14.238
      // 4b00: ldc_w 43.083
      // 4b03: ldc_w 1.829
      // 4b06: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b09: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b0c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b0f: aastore
      // 4b10: dup
      // 4b11: bipush 17
      // 4b13: new net/minecraft/client/animation/Keyframe
      // 4b16: dup
      // 4b17: fconst_1
      // 4b18: ldc_w -17.16
      // 4b1b: ldc_w 50.947
      // 4b1e: ldc_w 7.628
      // 4b21: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b24: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b27: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b2a: aastore
      // 4b2b: dup
      // 4b2c: bipush 18
      // 4b2e: new net/minecraft/client/animation/Keyframe
      // 4b31: dup
      // 4b32: ldc 1.04
      // 4b34: ldc_w -34.193
      // 4b37: ldc_w 52.189
      // 4b3a: ldc_w 1.53
      // 4b3d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b40: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b43: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b46: aastore
      // 4b47: dup
      // 4b48: bipush 19
      // 4b4a: new net/minecraft/client/animation/Keyframe
      // 4b4d: dup
      // 4b4e: ldc 1.08
      // 4b50: ldc_w -51.502
      // 4b53: ldc_w 48.131
      // 4b56: ldc_w -1.109
      // 4b59: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b5c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b5f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b62: aastore
      // 4b63: dup
      // 4b64: bipush 20
      // 4b66: new net/minecraft/client/animation/Keyframe
      // 4b69: dup
      // 4b6a: ldc 1.12
      // 4b6c: ldc_w -66.178
      // 4b6f: ldc_w 41.542
      // 4b72: ldc_w 1.806
      // 4b75: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b78: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b7b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b7e: aastore
      // 4b7f: dup
      // 4b80: bipush 21
      // 4b82: new net/minecraft/client/animation/Keyframe
      // 4b85: dup
      // 4b86: ldc 1.17
      // 4b88: ldc_w -79.145
      // 4b8b: ldc_w 35.207
      // 4b8e: ldc_w 7.566
      // 4b91: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b94: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b97: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b9a: aastore
      // 4b9b: dup
      // 4b9c: bipush 22
      // 4b9e: new net/minecraft/client/animation/Keyframe
      // 4ba1: dup
      // 4ba2: ldc 1.21
      // 4ba4: ldc_w -90.844
      // 4ba7: ldc_w 30.834
      // 4baa: ldc_w 13.044
      // 4bad: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4bb0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4bb3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4bb6: aastore
      // 4bb7: dup
      // 4bb8: bipush 23
      // 4bba: new net/minecraft/client/animation/Keyframe
      // 4bbd: dup
      // 4bbe: ldc 1.25
      // 4bc0: ldc_w -100.755
      // 4bc3: ldc_w 29.289
      // 4bc6: ldc_w 15.45
      // 4bc9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4bcc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4bcf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4bd2: aastore
      // 4bd3: dup
      // 4bd4: bipush 24
      // 4bd6: new net/minecraft/client/animation/Keyframe
      // 4bd9: dup
      // 4bda: ldc 1.29
      // 4bdc: ldc_w -106.233
      // 4bdf: ldc_w -53.707
      // 4be2: ldc_w -1.926
      // 4be5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4be8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4beb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4bee: aastore
      // 4bef: dup
      // 4bf0: bipush 25
      // 4bf2: new net/minecraft/client/animation/Keyframe
      // 4bf5: dup
      // 4bf6: ldc 1.33
      // 4bf8: ldc_w -45.166
      // 4bfb: ldc_w -45.488
      // 4bfe: ldc_w -48.08
      // 4c01: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c04: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c07: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c0a: aastore
      // 4c0b: dup
      // 4c0c: bipush 26
      // 4c0e: new net/minecraft/client/animation/Keyframe
      // 4c11: dup
      // 4c12: ldc 1.38
      // 4c14: ldc_w -95.858
      // 4c17: ldc_w -62.496
      // 4c1a: ldc_w -3.389
      // 4c1d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c20: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c23: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c26: aastore
      // 4c27: dup
      // 4c28: bipush 27
      // 4c2a: new net/minecraft/client/animation/Keyframe
      // 4c2d: dup
      // 4c2e: ldc 1.42
      // 4c30: ldc_w -89.244
      // 4c33: ldc_w -7.866
      // 4c36: ldc_w 7.943
      // 4c39: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c3c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c3f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c42: aastore
      // 4c43: dup
      // 4c44: bipush 28
      // 4c46: new net/minecraft/client/animation/Keyframe
      // 4c49: dup
      // 4c4a: ldc 1.46
      // 4c4c: ldc_w -90.895
      // 4c4f: ldc_w 15.755
      // 4c52: ldc_w 7.482
      // 4c55: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c58: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c5b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c5e: aastore
      // 4c5f: dup
      // 4c60: bipush 29
      // 4c62: new net/minecraft/client/animation/Keyframe
      // 4c65: dup
      // 4c66: ldc 1.5
      // 4c68: ldc_w -94.833
      // 4c6b: ldc_w 30.082
      // 4c6e: ldc_w 6.382
      // 4c71: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c74: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c77: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c7a: aastore
      // 4c7b: dup
      // 4c7c: bipush 30
      // 4c7e: new net/minecraft/client/animation/Keyframe
      // 4c81: dup
      // 4c82: ldc 1.54
      // 4c84: ldc_w -99.533
      // 4c87: ldc_w 37.439
      // 4c8a: ldc_w 5.069
      // 4c8d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c90: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c93: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c96: aastore
      // 4c97: dup
      // 4c98: bipush 31
      // 4c9a: new net/minecraft/client/animation/Keyframe
      // 4c9d: dup
      // 4c9e: ldc 1.58
      // 4ca0: ldc_w -103.47
      // 4ca3: ldc_w 40.15
      // 4ca6: ldc_w 3.969
      // 4ca9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4cac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4caf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4cb2: aastore
      // 4cb3: dup
      // 4cb4: bipush 32
      // 4cb6: new net/minecraft/client/animation/Keyframe
      // 4cb9: dup
      // 4cba: ldc 1.62
      // 4cbc: ldc_w -105.122
      // 4cbf: ldc_w 40.537
      // 4cc2: ldc_w 3.508
      // 4cc5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4cc8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ccb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4cce: aastore
      // 4ccf: dup
      // 4cd0: bipush 33
      // 4cd2: new net/minecraft/client/animation/Keyframe
      // 4cd5: dup
      // 4cd6: ldc 1.79
      // 4cd8: ldc_w -105.122
      // 4cdb: ldc_w 40.537
      // 4cde: ldc_w 3.508
      // 4ce1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ce4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ce7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4cea: aastore
      // 4ceb: dup
      // 4cec: bipush 34
      // 4cee: new net/minecraft/client/animation/Keyframe
      // 4cf1: dup
      // 4cf2: ldc_w 1.83
      // 4cf5: ldc_w -104.129
      // 4cf8: ldc_w 40.136
      // 4cfb: ldc_w 3.35
      // 4cfe: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d01: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d04: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d07: aastore
      // 4d08: dup
      // 4d09: bipush 35
      // 4d0b: new net/minecraft/client/animation/Keyframe
      // 4d0e: dup
      // 4d0f: ldc_w 1.88
      // 4d12: ldc_w -101.306
      // 4d15: ldc_w 38.995
      // 4d18: ldc_w 2.902
      // 4d1b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d1e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d21: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d24: aastore
      // 4d25: dup
      // 4d26: bipush 36
      // 4d28: new net/minecraft/client/animation/Keyframe
      // 4d2b: dup
      // 4d2c: ldc_w 1.92
      // 4d2f: ldc_w -96.879
      // 4d32: ldc_w 37.207
      // 4d35: ldc_w 2.199
      // 4d38: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d3b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d3e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d41: aastore
      // 4d42: dup
      // 4d43: bipush 37
      // 4d45: new net/minecraft/client/animation/Keyframe
      // 4d48: dup
      // 4d49: ldc_w 1.96
      // 4d4c: ldc_w -91.079
      // 4d4f: ldc_w 34.864
      // 4d52: ldc_w 1.278
      // 4d55: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d58: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d5b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d5e: aastore
      // 4d5f: dup
      // 4d60: bipush 38
      // 4d62: new net/minecraft/client/animation/Keyframe
      // 4d65: dup
      // 4d66: fconst_2
      // 4d67: ldc_w -84.134
      // 4d6a: ldc_w 32.058
      // 4d6d: ldc_w 0.176
      // 4d70: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d73: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d76: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d79: aastore
      // 4d7a: dup
      // 4d7b: bipush 39
      // 4d7d: new net/minecraft/client/animation/Keyframe
      // 4d80: dup
      // 4d81: ldc_w 2.04
      // 4d84: ldc_w -76.273
      // 4d87: ldc_w 28.882
      // 4d8a: ldc_w -1.072
      // 4d8d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d90: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d93: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d96: aastore
      // 4d97: dup
      // 4d98: bipush 40
      // 4d9a: new net/minecraft/client/animation/Keyframe
      // 4d9d: dup
      // 4d9e: ldc_w 2.08
      // 4da1: ldc_w -66.512
      // 4da4: ldc_w 22.571
      // 4da7: ldc_w -1.498
      // 4daa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4dad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4db0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4db3: aastore
      // 4db4: dup
      // 4db5: bipush 41
      // 4db7: new net/minecraft/client/animation/Keyframe
      // 4dba: dup
      // 4dbb: ldc_w 2.12
      // 4dbe: ldc_w -55.246
      // 4dc1: ldc_w 11.496
      // 4dc4: ldc_w -1.799
      // 4dc7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4dca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4dcd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4dd0: aastore
      // 4dd1: dup
      // 4dd2: bipush 42
      // 4dd4: new net/minecraft/client/animation/Keyframe
      // 4dd7: dup
      // 4dd8: ldc_w 2.17
      // 4ddb: ldc_w -43.673
      // 4dde: ldc_w -2.013
      // 4de1: ldc_w -3.61
      // 4de4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4de7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4dea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ded: aastore
      // 4dee: dup
      // 4def: bipush 43
      // 4df1: new net/minecraft/client/animation/Keyframe
      // 4df4: dup
      // 4df5: ldc_w 2.21
      // 4df8: ldc_w -31.857
      // 4dfb: ldc_w -15.015
      // 4dfe: ldc_w -7.152
      // 4e01: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e04: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e07: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e0a: aastore
      // 4e0b: dup
      // 4e0c: bipush 44
      // 4e0e: new net/minecraft/client/animation/Keyframe
      // 4e11: dup
      // 4e12: ldc 2.25
      // 4e14: ldc_w -15.793
      // 4e17: ldc_w -26.043
      // 4e1a: ldc_w -16.727
      // 4e1d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e20: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e23: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e26: aastore
      // 4e27: dup
      // 4e28: bipush 45
      // 4e2a: new net/minecraft/client/animation/Keyframe
      // 4e2d: dup
      // 4e2e: ldc 2.29
      // 4e30: ldc_w 0.424
      // 4e33: ldc_w -30.398
      // 4e36: ldc_w -27.952
      // 4e39: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e3c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e3f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e42: aastore
      // 4e43: dup
      // 4e44: bipush 46
      // 4e46: new net/minecraft/client/animation/Keyframe
      // 4e49: dup
      // 4e4a: ldc 2.33
      // 4e4c: ldc_w 10.79
      // 4e4f: ldc_w -29.0
      // 4e52: ldc_w -33.051
      // 4e55: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e58: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e5b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e5e: aastore
      // 4e5f: dup
      // 4e60: bipush 47
      // 4e62: new net/minecraft/client/animation/Keyframe
      // 4e65: dup
      // 4e66: ldc 2.38
      // 4e68: ldc_w 15.827
      // 4e6b: ldc_w -23.935
      // 4e6e: ldc_w -33.299
      // 4e71: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e74: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e77: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e7a: aastore
      // 4e7b: dup
      // 4e7c: bipush 48
      // 4e7e: new net/minecraft/client/animation/Keyframe
      // 4e81: dup
      // 4e82: ldc 2.42
      // 4e84: ldc_w 15.878
      // 4e87: ldc_w -17.497
      // 4e8a: ldc_w -29.874
      // 4e8d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e90: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e93: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e96: aastore
      // 4e97: dup
      // 4e98: bipush 49
      // 4e9a: new net/minecraft/client/animation/Keyframe
      // 4e9d: dup
      // 4e9e: ldc 2.46
      // 4ea0: ldc_w 11.757
      // 4ea3: ldc_w -11.992
      // 4ea6: ldc_w -22.836
      // 4ea9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4eac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4eaf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4eb2: aastore
      // 4eb3: dup
      // 4eb4: bipush 50
      // 4eb6: new net/minecraft/client/animation/Keyframe
      // 4eb9: dup
      // 4eba: ldc 2.5
      // 4ebc: ldc_w 5.548
      // 4ebf: ldc_w -7.505
      // 4ec2: ldc_w -16.236
      // 4ec5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ec8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ecb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ece: aastore
      // 4ecf: dup
      // 4ed0: bipush 51
      // 4ed2: new net/minecraft/client/animation/Keyframe
      // 4ed5: dup
      // 4ed6: ldc 2.54
      // 4ed8: ldc_w -1.031
      // 4edb: ldc_w -4.045
      // 4ede: ldc_w -14.536
      // 4ee1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ee4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ee7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4eea: aastore
      // 4eeb: dup
      // 4eec: bipush 52
      // 4eee: new net/minecraft/client/animation/Keyframe
      // 4ef1: dup
      // 4ef2: ldc_w 2.58
      // 4ef5: ldc_w -5.34
      // 4ef8: ldc_w -2.041
      // 4efb: ldc_w -11.162
      // 4efe: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f01: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f04: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f07: aastore
      // 4f08: dup
      // 4f09: bipush 53
      // 4f0b: new net/minecraft/client/animation/Keyframe
      // 4f0e: dup
      // 4f0f: ldc_w 2.62
      // 4f12: ldc_w -5.608
      // 4f15: ldc_w -1.047
      // 4f18: ldc_w -5.374
      // 4f1b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f1e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f21: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f24: aastore
      // 4f25: dup
      // 4f26: bipush 54
      // 4f28: new net/minecraft/client/animation/Keyframe
      // 4f2b: dup
      // 4f2c: ldc_w 2.67
      // 4f2f: ldc_w -3.075
      // 4f32: ldc_w -1.321
      // 4f35: ldc_w -1.537
      // 4f38: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f3b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f3e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f41: aastore
      // 4f42: dup
      // 4f43: bipush 55
      // 4f45: new net/minecraft/client/animation/Keyframe
      // 4f48: dup
      // 4f49: ldc_w 2.71
      // 4f4c: ldc_w -0.348
      // 4f4f: ldc_w -2.206
      // 4f52: ldc_w 2.083
      // 4f55: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f58: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f5b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f5e: aastore
      // 4f5f: dup
      // 4f60: bipush 56
      // 4f62: new net/minecraft/client/animation/Keyframe
      // 4f65: dup
      // 4f66: ldc_w 2.75
      // 4f69: ldc_w 0.836
      // 4f6c: ldc_w -2.74
      // 4f6f: ldc_w 2.659
      // 4f72: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f75: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f78: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f7b: aastore
      // 4f7c: dup
      // 4f7d: bipush 57
      // 4f7f: new net/minecraft/client/animation/Keyframe
      // 4f82: dup
      // 4f83: ldc_w 2.79
      // 4f86: ldc_w 0.957
      // 4f89: ldc_w -2.67
      // 4f8c: ldc_w 1.293
      // 4f8f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f92: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f95: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f98: aastore
      // 4f99: dup
      // 4f9a: bipush 58
      // 4f9c: new net/minecraft/client/animation/Keyframe
      // 4f9f: dup
      // 4fa0: ldc 2.83
      // 4fa2: ldc_w 0.839
      // 4fa5: ldc_w -2.513
      // 4fa8: ldc_w 0.452
      // 4fab: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4fae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4fb1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4fb4: aastore
      // 4fb5: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4fb8: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4fbb: ldc_w "CTR_upperarm_right"
      // 4fbe: new net/minecraft/client/animation/AnimationChannel
      // 4fc1: dup
      // 4fc2: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4fc5: bipush 3
      // 4fc6: anewarray 28
      // 4fc9: dup
      // 4fca: bipush 0
      // 4fcb: new net/minecraft/client/animation/Keyframe
      // 4fce: dup
      // 4fcf: fconst_0
      // 4fd0: fconst_0
      // 4fd1: fconst_0
      // 4fd2: fconst_0
      // 4fd3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4fd6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4fd9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4fdc: aastore
      // 4fdd: dup
      // 4fde: bipush 1
      // 4fdf: new net/minecraft/client/animation/Keyframe
      // 4fe2: dup
      // 4fe3: ldc 0.33
      // 4fe5: fconst_0
      // 4fe6: ldc_w 3.7
      // 4fe9: fconst_0
      // 4fea: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4fed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ff0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ff3: aastore
      // 4ff4: dup
      // 4ff5: bipush 2
      // 4ff6: new net/minecraft/client/animation/Keyframe
      // 4ff9: dup
      // 4ffa: ldc_w 0.51
      // 4ffd: fconst_0
      // 4ffe: fconst_0
      // 4fff: fconst_0
      // 5000: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5003: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5006: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5009: aastore
      // 500a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 500d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5010: ldc_w "CTR_lowerarm_right"
      // 5013: new net/minecraft/client/animation/AnimationChannel
      // 5016: dup
      // 5017: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 501a: bipush 64
      // 501c: anewarray 28
      // 501f: dup
      // 5020: bipush 0
      // 5021: new net/minecraft/client/animation/Keyframe
      // 5024: dup
      // 5025: fconst_0
      // 5026: ldc_w -0.021
      // 5029: ldc_w -1.724
      // 502c: ldc_w -0.014
      // 502f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5032: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5035: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5038: aastore
      // 5039: dup
      // 503a: bipush 1
      // 503b: new net/minecraft/client/animation/Keyframe
      // 503e: dup
      // 503f: ldc 0.04
      // 5041: ldc_w -0.006
      // 5044: ldc_w -0.491
      // 5047: ldc_w -0.004
      // 504a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 504d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5050: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5053: aastore
      // 5054: dup
      // 5055: bipush 2
      // 5056: new net/minecraft/client/animation/Keyframe
      // 5059: dup
      // 505a: ldc 0.08
      // 505c: ldc_w 0.031
      // 505f: ldc_w 2.261
      // 5062: ldc_w 0.019
      // 5065: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5068: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 506b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 506e: aastore
      // 506f: dup
      // 5070: bipush 3
      // 5071: new net/minecraft/client/animation/Keyframe
      // 5074: dup
      // 5075: ldc 0.12
      // 5077: ldc_w 0.074
      // 507a: ldc_w 5.204
      // 507d: ldc_w 0.048
      // 5080: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5083: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5086: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5089: aastore
      // 508a: dup
      // 508b: bipush 4
      // 508c: new net/minecraft/client/animation/Keyframe
      // 508f: dup
      // 5090: ldc 0.17
      // 5092: ldc_w 0.111
      // 5095: ldc_w 7.572
      // 5098: ldc_w 0.075
      // 509b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 509e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 50a1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 50a4: aastore
      // 50a5: dup
      // 50a6: bipush 5
      // 50a7: new net/minecraft/client/animation/Keyframe
      // 50aa: dup
      // 50ab: ldc 0.21
      // 50ad: ldc_w 0.141
      // 50b0: ldc_w 9.291
      // 50b3: ldc_w 0.096
      // 50b6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 50b9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 50bc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 50bf: aastore
      // 50c0: dup
      // 50c1: bipush 6
      // 50c3: new net/minecraft/client/animation/Keyframe
      // 50c6: dup
      // 50c7: ldc 0.33
      // 50c9: ldc_w 0.0818
      // 50cc: ldc_w -3.3623
      // 50cf: ldc_w -0.0522
      // 50d2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 50d5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 50d8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 50db: aastore
      // 50dc: dup
      // 50dd: bipush 7
      // 50df: new net/minecraft/client/animation/Keyframe
      // 50e2: dup
      // 50e3: ldc 0.46
      // 50e5: ldc_w 0.318
      // 50e8: ldc_w 17.613
      // 50eb: ldc_w 0.237
      // 50ee: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 50f1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 50f4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 50f7: aastore
      // 50f8: dup
      // 50f9: bipush 8
      // 50fb: new net/minecraft/client/animation/Keyframe
      // 50fe: dup
      // 50ff: ldc 0.5
      // 5101: ldc_w 0.262
      // 5104: ldc_w 15.306
      // 5107: ldc_w 0.191
      // 510a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 510d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5110: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5113: aastore
      // 5114: dup
      // 5115: bipush 9
      // 5117: new net/minecraft/client/animation/Keyframe
      // 511a: dup
      // 511b: ldc 0.54
      // 511d: ldc_w 0.203
      // 5120: ldc_w 12.607
      // 5123: ldc_w 0.144
      // 5126: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5129: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 512c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 512f: aastore
      // 5130: dup
      // 5131: bipush 10
      // 5133: new net/minecraft/client/animation/Keyframe
      // 5136: dup
      // 5137: ldc 0.58
      // 5139: ldc_w 0.157
      // 513c: ldc_w 10.225
      // 513f: ldc_w 0.109
      // 5142: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5145: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5148: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 514b: aastore
      // 514c: dup
      // 514d: bipush 11
      // 514f: new net/minecraft/client/animation/Keyframe
      // 5152: dup
      // 5153: ldc 0.62
      // 5155: ldc_w 0.121
      // 5158: ldc_w 8.172
      // 515b: ldc_w 0.082
      // 515e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5161: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5164: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5167: aastore
      // 5168: dup
      // 5169: bipush 12
      // 516b: new net/minecraft/client/animation/Keyframe
      // 516e: dup
      // 516f: ldc 0.71
      // 5171: ldc_w 0.069
      // 5174: ldc_w 4.878
      // 5177: ldc_w 0.045
      // 517a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 517d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5180: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5183: aastore
      // 5184: dup
      // 5185: bipush 13
      // 5187: new net/minecraft/client/animation/Keyframe
      // 518a: dup
      // 518b: ldc 0.75
      // 518d: ldc_w 0.045
      // 5190: ldc_w 3.261
      // 5193: ldc 0.028
      // 5195: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5198: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 519b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 519e: aastore
      // 519f: dup
      // 51a0: bipush 14
      // 51a2: new net/minecraft/client/animation/Keyframe
      // 51a5: dup
      // 51a6: ldc 0.79
      // 51a8: ldc_w 0.019
      // 51ab: ldc_w 1.406
      // 51ae: ldc_w 0.011
      // 51b1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 51b4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 51b7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 51ba: aastore
      // 51bb: dup
      // 51bc: bipush 15
      // 51be: new net/minecraft/client/animation/Keyframe
      // 51c1: dup
      // 51c2: ldc 0.83
      // 51c4: ldc_w -1.302
      // 51c7: ldc_w -1.489
      // 51ca: ldc_w -0.235
      // 51cd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 51d0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 51d3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 51d6: aastore
      // 51d7: dup
      // 51d8: bipush 16
      // 51da: new net/minecraft/client/animation/Keyframe
      // 51dd: dup
      // 51de: ldc 0.88
      // 51e0: ldc_w -2.636
      // 51e3: ldc_w -6.463
      // 51e6: ldc_w 0.005
      // 51e9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 51ec: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 51ef: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 51f2: aastore
      // 51f3: dup
      // 51f4: bipush 17
      // 51f6: new net/minecraft/client/animation/Keyframe
      // 51f9: dup
      // 51fa: ldc 0.92
      // 51fc: ldc_w -2.923
      // 51ff: ldc_w -10.405
      // 5202: ldc_w 0.893
      // 5205: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5208: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 520b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 520e: aastore
      // 520f: dup
      // 5210: bipush 18
      // 5212: new net/minecraft/client/animation/Keyframe
      // 5215: dup
      // 5216: ldc 0.96
      // 5218: ldc_w -1.944
      // 521b: ldc_w -20.729
      // 521e: ldc_w 2.385
      // 5221: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5224: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5227: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 522a: aastore
      // 522b: dup
      // 522c: bipush 19
      // 522e: new net/minecraft/client/animation/Keyframe
      // 5231: dup
      // 5232: fconst_1
      // 5233: ldc_w -1.286
      // 5236: ldc_w -26.799
      // 5239: ldc_w 3.775
      // 523c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 523f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5242: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5245: aastore
      // 5246: dup
      // 5247: bipush 20
      // 5249: new net/minecraft/client/animation/Keyframe
      // 524c: dup
      // 524d: ldc 1.04
      // 524f: ldc_w -1.606
      // 5252: ldc_w -19.181
      // 5255: ldc_w 4.95
      // 5258: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 525b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 525e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5261: aastore
      // 5262: dup
      // 5263: bipush 21
      // 5265: new net/minecraft/client/animation/Keyframe
      // 5268: dup
      // 5269: ldc 1.08
      // 526b: ldc_w -1.746
      // 526e: ldc_w -10.0
      // 5271: ldc_w 6.076
      // 5274: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5277: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 527a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 527d: aastore
      // 527e: dup
      // 527f: bipush 22
      // 5281: new net/minecraft/client/animation/Keyframe
      // 5284: dup
      // 5285: ldc 1.12
      // 5287: ldc_w -1.61
      // 528a: ldc_w -0.259
      // 528d: ldc_w 7.145
      // 5290: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5293: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5296: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5299: aastore
      // 529a: dup
      // 529b: bipush 23
      // 529d: new net/minecraft/client/animation/Keyframe
      // 52a0: dup
      // 52a1: ldc 1.17
      // 52a3: ldc_w -1.191
      // 52a6: ldc_w 8.643
      // 52a9: ldc_w 8.159
      // 52ac: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 52af: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 52b2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 52b5: aastore
      // 52b6: dup
      // 52b7: bipush 24
      // 52b9: new net/minecraft/client/animation/Keyframe
      // 52bc: dup
      // 52bd: ldc 1.21
      // 52bf: ldc_w -0.644
      // 52c2: ldc_w 15.117
      // 52c5: ldc_w 9.015
      // 52c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 52cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 52ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 52d1: aastore
      // 52d2: dup
      // 52d3: bipush 25
      // 52d5: new net/minecraft/client/animation/Keyframe
      // 52d8: dup
      // 52d9: ldc 1.25
      // 52db: ldc_w -0.363
      // 52de: ldc_w 17.579
      // 52e1: ldc_w 9.391
      // 52e4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 52e7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 52ea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 52ed: aastore
      // 52ee: dup
      // 52ef: bipush 26
      // 52f1: new net/minecraft/client/animation/Keyframe
      // 52f4: dup
      // 52f5: ldc 1.29
      // 52f7: ldc_w -0.363
      // 52fa: ldc_w -40.156
      // 52fd: ldc_w 9.391
      // 5300: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5303: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5306: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5309: aastore
      // 530a: dup
      // 530b: bipush 27
      // 530d: new net/minecraft/client/animation/Keyframe
      // 5310: dup
      // 5311: ldc 1.33
      // 5313: ldc_w -0.363
      // 5316: ldc_w -49.494
      // 5319: ldc_w 9.391
      // 531c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 531f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5322: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5325: aastore
      // 5326: dup
      // 5327: bipush 28
      // 5329: new net/minecraft/client/animation/Keyframe
      // 532c: dup
      // 532d: ldc 1.38
      // 532f: ldc_w -0.363
      // 5332: ldc_w -50.828
      // 5335: ldc_w 9.391
      // 5338: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 533b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 533e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5341: aastore
      // 5342: dup
      // 5343: bipush 29
      // 5345: new net/minecraft/client/animation/Keyframe
      // 5348: dup
      // 5349: ldc 1.42
      // 534b: ldc_w -0.363
      // 534e: ldc_w -46.421
      // 5351: ldc_w 9.391
      // 5354: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5357: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 535a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 535d: aastore
      // 535e: dup
      // 535f: bipush 30
      // 5361: new net/minecraft/client/animation/Keyframe
      // 5364: dup
      // 5365: ldc 1.46
      // 5367: ldc_w -0.363
      // 536a: ldc_w -34.731
      // 536d: ldc_w 9.391
      // 5370: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5373: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5376: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5379: aastore
      // 537a: dup
      // 537b: bipush 31
      // 537d: new net/minecraft/client/animation/Keyframe
      // 5380: dup
      // 5381: ldc 1.5
      // 5383: ldc_w -0.363
      // 5386: ldc_w -18.375
      // 5389: ldc_w 9.391
      // 538c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 538f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5392: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5395: aastore
      // 5396: dup
      // 5397: bipush 32
      // 5399: new net/minecraft/client/animation/Keyframe
      // 539c: dup
      // 539d: ldc 1.54
      // 539f: ldc_w -0.363
      // 53a2: ldc_w -0.058
      // 53a5: ldc_w 9.391
      // 53a8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 53ab: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 53ae: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 53b1: aastore
      // 53b2: dup
      // 53b3: bipush 33
      // 53b5: new net/minecraft/client/animation/Keyframe
      // 53b8: dup
      // 53b9: ldc 1.58
      // 53bb: ldc_w -0.363
      // 53be: ldc_w 17.514
      // 53c1: ldc_w 9.391
      // 53c4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 53c7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 53ca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 53cd: aastore
      // 53ce: dup
      // 53cf: bipush 34
      // 53d1: new net/minecraft/client/animation/Keyframe
      // 53d4: dup
      // 53d5: ldc 1.62
      // 53d7: ldc_w -0.363
      // 53da: ldc_w 31.634
      // 53dd: ldc_w 9.391
      // 53e0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 53e3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 53e6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 53e9: aastore
      // 53ea: dup
      // 53eb: bipush 35
      // 53ed: new net/minecraft/client/animation/Keyframe
      // 53f0: dup
      // 53f1: ldc 1.67
      // 53f3: ldc_w -0.363
      // 53f6: ldc_w 39.597
      // 53f9: ldc_w 9.391
      // 53fc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 53ff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5402: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5405: aastore
      // 5406: dup
      // 5407: bipush 36
      // 5409: new net/minecraft/client/animation/Keyframe
      // 540c: dup
      // 540d: ldc 1.71
      // 540f: ldc_w -0.363
      // 5412: ldc_w 42.399
      // 5415: ldc_w 9.391
      // 5418: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 541b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 541e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5421: aastore
      // 5422: dup
      // 5423: bipush 37
      // 5425: new net/minecraft/client/animation/Keyframe
      // 5428: dup
      // 5429: ldc 1.75
      // 542b: ldc_w -0.363
      // 542e: ldc_w 43.432
      // 5431: ldc_w 9.391
      // 5434: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5437: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 543a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 543d: aastore
      // 543e: dup
      // 543f: bipush 38
      // 5441: new net/minecraft/client/animation/Keyframe
      // 5444: dup
      // 5445: ldc 1.79
      // 5447: ldc_w -0.363
      // 544a: ldc_w 43.579
      // 544d: ldc_w 9.391
      // 5450: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5453: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5456: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5459: aastore
      // 545a: dup
      // 545b: bipush 39
      // 545d: new net/minecraft/client/animation/Keyframe
      // 5460: dup
      // 5461: ldc_w 1.83
      // 5464: ldc_w -0.939
      // 5467: ldc_w 42.895
      // 546a: ldc_w 8.8
      // 546d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5470: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5473: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5476: aastore
      // 5477: dup
      // 5478: bipush 40
      // 547a: new net/minecraft/client/animation/Keyframe
      // 547d: dup
      // 547e: ldc_w 1.88
      // 5481: ldc_w -2.141
      // 5484: ldc_w 40.944
      // 5487: ldc_w 7.55
      // 548a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 548d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5490: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5493: aastore
      // 5494: dup
      // 5495: bipush 41
      // 5497: new net/minecraft/client/animation/Keyframe
      // 549a: dup
      // 549b: ldc_w 1.92
      // 549e: ldc_w -3.275
      // 54a1: ldc_w 37.881
      // 54a4: ldc_w 6.323
      // 54a7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 54aa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 54ad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 54b0: aastore
      // 54b1: dup
      // 54b2: bipush 42
      // 54b4: new net/minecraft/client/animation/Keyframe
      // 54b7: dup
      // 54b8: ldc_w 1.96
      // 54bb: ldc_w -4.091
      // 54be: ldc_w 33.861
      // 54c1: ldc_w 5.356
      // 54c4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 54c7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 54ca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 54cd: aastore
      // 54ce: dup
      // 54cf: bipush 43
      // 54d1: new net/minecraft/client/animation/Keyframe
      // 54d4: dup
      // 54d5: fconst_2
      // 54d6: ldc_w -4.588
      // 54d9: ldc_w 29.047
      // 54dc: ldc_w 4.63
      // 54df: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 54e2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 54e5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 54e8: aastore
      // 54e9: dup
      // 54ea: bipush 44
      // 54ec: new net/minecraft/client/animation/Keyframe
      // 54ef: dup
      // 54f0: ldc_w 2.04
      // 54f3: ldc_w -4.826
      // 54f6: ldc_w 23.597
      // 54f9: ldc_w 4.069
      // 54fc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 54ff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5502: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5505: aastore
      // 5506: dup
      // 5507: bipush 45
      // 5509: new net/minecraft/client/animation/Keyframe
      // 550c: dup
      // 550d: ldc_w 2.08
      // 5510: ldc_w -4.675
      // 5513: ldc_w 16.569
      // 5516: ldc_w 3.76
      // 5519: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 551c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 551f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5522: aastore
      // 5523: dup
      // 5524: bipush 46
      // 5526: new net/minecraft/client/animation/Keyframe
      // 5529: dup
      // 552a: ldc_w 2.12
      // 552d: ldc_w -4.188
      // 5530: ldc_w 7.739
      // 5533: ldc_w 3.596
      // 5536: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5539: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 553c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 553f: aastore
      // 5540: dup
      // 5541: bipush 47
      // 5543: new net/minecraft/client/animation/Keyframe
      // 5546: dup
      // 5547: ldc_w 2.17
      // 554a: ldc_w -3.567
      // 554d: ldc_w -1.757
      // 5550: ldc_w 3.377
      // 5553: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5556: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5559: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 555c: aastore
      // 555d: dup
      // 555e: bipush 48
      // 5560: new net/minecraft/client/animation/Keyframe
      // 5563: dup
      // 5564: ldc_w 2.21
      // 5567: ldc_w -2.897
      // 556a: ldc_w -10.975
      // 556d: ldc_w 3.041
      // 5570: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5573: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5576: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5579: aastore
      // 557a: dup
      // 557b: bipush 49
      // 557d: new net/minecraft/client/animation/Keyframe
      // 5580: dup
      // 5581: ldc 2.25
      // 5583: ldc_w -3.387
      // 5586: ldc_w -8.838
      // 5589: ldc_w 2.105
      // 558c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 558f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5592: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5595: aastore
      // 5596: dup
      // 5597: bipush 50
      // 5599: new net/minecraft/client/animation/Keyframe
      // 559c: dup
      // 559d: ldc 2.29
      // 559f: ldc_w -4.673
      // 55a2: ldc -0.812
      // 55a4: ldc_w 0.72
      // 55a7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 55aa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 55ad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 55b0: aastore
      // 55b1: dup
      // 55b2: bipush 51
      // 55b4: new net/minecraft/client/animation/Keyframe
      // 55b7: dup
      // 55b8: ldc 2.33
      // 55ba: ldc_w -5.847
      // 55bd: ldc_w 5.932
      // 55c0: ldc_w -0.7
      // 55c3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 55c6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 55c9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 55cc: aastore
      // 55cd: dup
      // 55ce: bipush 52
      // 55d0: new net/minecraft/client/animation/Keyframe
      // 55d3: dup
      // 55d4: ldc 2.38
      // 55d6: ldc_w -6.849
      // 55d9: ldc_w 12.66
      // 55dc: ldc_w -2.136
      // 55df: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 55e2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 55e5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 55e8: aastore
      // 55e9: dup
      // 55ea: bipush 53
      // 55ec: new net/minecraft/client/animation/Keyframe
      // 55ef: dup
      // 55f0: ldc 2.42
      // 55f2: ldc_w -6.365
      // 55f5: ldc_w 15.978
      // 55f8: ldc_w -2.588
      // 55fb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 55fe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5601: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5604: aastore
      // 5605: dup
      // 5606: bipush 54
      // 5608: new net/minecraft/client/animation/Keyframe
      // 560b: dup
      // 560c: ldc 2.46
      // 560e: ldc_w -3.798
      // 5611: ldc_w 13.879
      // 5614: ldc_w -1.529
      // 5617: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 561a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 561d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5620: aastore
      // 5621: dup
      // 5622: bipush 55
      // 5624: new net/minecraft/client/animation/Keyframe
      // 5627: dup
      // 5628: ldc 2.5
      // 562a: ldc_w -1.187
      // 562d: ldc_w 14.657
      // 5630: ldc_w -0.466
      // 5633: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5636: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5639: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 563c: aastore
      // 563d: dup
      // 563e: bipush 56
      // 5640: new net/minecraft/client/animation/Keyframe
      // 5643: dup
      // 5644: ldc 2.54
      // 5646: ldc_w 0.631
      // 5649: ldc_w 26.735
      // 564c: ldc_w 0.512
      // 564f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5652: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5655: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5658: aastore
      // 5659: dup
      // 565a: bipush 57
      // 565c: new net/minecraft/client/animation/Keyframe
      // 565f: dup
      // 5660: ldc_w 2.58
      // 5663: ldc_w 0.696
      // 5666: ldc_w 28.071
      // 5669: ldc_w 0.572
      // 566c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 566f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5672: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5675: aastore
      // 5676: dup
      // 5677: bipush 58
      // 5679: new net/minecraft/client/animation/Keyframe
      // 567c: dup
      // 567d: ldc_w 2.62
      // 5680: ldc_w 0.274
      // 5683: ldc_w 15.826
      // 5686: ldc_w 0.201
      // 5689: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 568c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 568f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5692: aastore
      // 5693: dup
      // 5694: bipush 59
      // 5696: new net/minecraft/client/animation/Keyframe
      // 5699: dup
      // 569a: ldc_w 2.67
      // 569d: ldc_w 0.071
      // 56a0: ldc_w 5.051
      // 56a3: ldc_w 0.046
      // 56a6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 56a9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 56ac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 56af: aastore
      // 56b0: dup
      // 56b1: bipush 60
      // 56b3: new net/minecraft/client/animation/Keyframe
      // 56b6: dup
      // 56b7: ldc_w 2.71
      // 56ba: ldc_w -0.07
      // 56bd: ldc_w -5.843
      // 56c0: ldc_w -0.042
      // 56c3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 56c6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 56c9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 56cc: aastore
      // 56cd: dup
      // 56ce: bipush 61
      // 56d0: new net/minecraft/client/animation/Keyframe
      // 56d3: dup
      // 56d4: ldc_w 2.75
      // 56d7: ldc_w -0.093
      // 56da: ldc_w -7.943
      // 56dd: ldc_w -0.054
      // 56e0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 56e3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 56e6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 56e9: aastore
      // 56ea: dup
      // 56eb: bipush 62
      // 56ed: new net/minecraft/client/animation/Keyframe
      // 56f0: dup
      // 56f1: ldc_w 2.79
      // 56f4: ldc_w -0.051
      // 56f7: ldc_w -4.226
      // 56fa: ldc_w -0.032
      // 56fd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5700: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5703: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5706: aastore
      // 5707: dup
      // 5708: bipush 63
      // 570a: new net/minecraft/client/animation/Keyframe
      // 570d: dup
      // 570e: ldc 2.83
      // 5710: ldc_w -0.021
      // 5713: ldc_w -1.724
      // 5716: ldc_w -0.014
      // 5719: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 571c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 571f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5722: aastore
      // 5723: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 5726: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5729: ldc_w "CTR_upperarm_right.001"
      // 572c: new net/minecraft/client/animation/AnimationChannel
      // 572f: dup
      // 5730: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5733: bipush 69
      // 5735: anewarray 28
      // 5738: dup
      // 5739: bipush 0
      // 573a: new net/minecraft/client/animation/Keyframe
      // 573d: dup
      // 573e: fconst_0
      // 573f: ldc_w 0.168
      // 5742: ldc_w 17.864
      // 5745: ldc_w 2.048
      // 5748: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 574b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 574e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5751: aastore
      // 5752: dup
      // 5753: bipush 1
      // 5754: new net/minecraft/client/animation/Keyframe
      // 5757: dup
      // 5758: ldc 0.04
      // 575a: ldc_w 0.243
      // 575d: ldc_w 19.096
      // 5760: ldc_w 2.134
      // 5763: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5766: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5769: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 576c: aastore
      // 576d: dup
      // 576e: bipush 2
      // 576f: new net/minecraft/client/animation/Keyframe
      // 5772: dup
      // 5773: ldc 0.08
      // 5775: ldc_w 0.44
      // 5778: ldc_w 22.049
      // 577b: ldc_w 2.35
      // 577e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5781: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5784: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5787: aastore
      // 5788: dup
      // 5789: bipush 3
      // 578a: new net/minecraft/client/animation/Keyframe
      // 578d: dup
      // 578e: ldc 0.12
      // 5790: ldc_w 0.709
      // 5793: ldc_w 25.6
      // 5796: ldc_w 2.634
      // 5799: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 579c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 579f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 57a2: aastore
      // 57a3: dup
      // 57a4: bipush 4
      // 57a5: new net/minecraft/client/animation/Keyframe
      // 57a8: dup
      // 57a9: ldc 0.17
      // 57ab: ldc_w 0.997
      // 57ae: ldc_w 28.759
      // 57b1: ldc 2.927
      // 57b3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 57b6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 57b9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 57bc: aastore
      // 57bd: dup
      // 57be: bipush 5
      // 57bf: new net/minecraft/client/animation/Keyframe
      // 57c2: dup
      // 57c3: ldc 0.21
      // 57c5: ldc_w 1.256
      // 57c8: ldc_w 30.764
      // 57cb: ldc_w 3.182
      // 57ce: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 57d1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 57d4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 57d7: aastore
      // 57d8: dup
      // 57d9: bipush 6
      // 57db: new net/minecraft/client/animation/Keyframe
      // 57de: dup
      // 57df: ldc 0.25
      // 57e1: ldc_w 1.466
      // 57e4: ldc_w 31.106
      // 57e7: ldc_w 3.375
      // 57ea: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 57ed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 57f0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 57f3: aastore
      // 57f4: dup
      // 57f5: bipush 7
      // 57f7: new net/minecraft/client/animation/Keyframe
      // 57fa: dup
      // 57fb: ldc 0.29
      // 57fd: ldc_w 1.623
      // 5800: ldc_w 29.535
      // 5803: ldc_w 3.496
      // 5806: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5809: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 580c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 580f: aastore
      // 5810: dup
      // 5811: bipush 8
      // 5813: new net/minecraft/client/animation/Keyframe
      // 5816: dup
      // 5817: ldc 0.33
      // 5819: ldc_w 1.718
      // 581c: ldc_w 26.136
      // 581f: ldc_w 3.53
      // 5822: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5825: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5828: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 582b: aastore
      // 582c: dup
      // 582d: bipush 9
      // 582f: new net/minecraft/client/animation/Keyframe
      // 5832: dup
      // 5833: ldc 0.38
      // 5835: ldc_w 1.721
      // 5838: ldc_w 21.495
      // 583b: ldc_w 3.442
      // 583e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5841: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5844: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5847: aastore
      // 5848: dup
      // 5849: bipush 10
      // 584b: new net/minecraft/client/animation/Keyframe
      // 584e: dup
      // 584f: ldc 0.42
      // 5851: ldc_w 1.601
      // 5854: ldc_w 16.673
      // 5857: ldc_w 3.205
      // 585a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 585d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5860: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5863: aastore
      // 5864: dup
      // 5865: bipush 11
      // 5867: new net/minecraft/client/animation/Keyframe
      // 586a: dup
      // 586b: ldc 0.46
      // 586d: ldc_w 1.34
      // 5870: ldc_w 12.492
      // 5873: ldc_w 2.821
      // 5876: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5879: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 587c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 587f: aastore
      // 5880: dup
      // 5881: bipush 12
      // 5883: new net/minecraft/client/animation/Keyframe
      // 5886: dup
      // 5887: ldc 0.5
      // 5889: ldc_w 1.146
      // 588c: ldc_w 14.299
      // 588f: ldc_w 2.749
      // 5892: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5895: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5898: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 589b: aastore
      // 589c: dup
      // 589d: bipush 13
      // 589f: new net/minecraft/client/animation/Keyframe
      // 58a2: dup
      // 58a3: ldc 0.54
      // 58a5: ldc_w 1.156
      // 58a8: ldc_w 22.337
      // 58ab: ldc_w 2.973
      // 58ae: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 58b1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 58b4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 58b7: aastore
      // 58b8: dup
      // 58b9: bipush 14
      // 58bb: new net/minecraft/client/animation/Keyframe
      // 58be: dup
      // 58bf: ldc 0.58
      // 58c1: ldc_w 1.208
      // 58c4: ldc_w 29.864
      // 58c7: ldc_w 3.129
      // 58ca: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 58cd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 58d0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 58d3: aastore
      // 58d4: dup
      // 58d5: bipush 15
      // 58d7: new net/minecraft/client/animation/Keyframe
      // 58da: dup
      // 58db: ldc 0.62
      // 58dd: ldc_w 1.188
      // 58e0: ldc_w 33.228
      // 58e3: ldc_w 3.146
      // 58e6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 58e9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 58ec: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 58ef: aastore
      // 58f0: dup
      // 58f1: bipush 16
      // 58f3: new net/minecraft/client/animation/Keyframe
      // 58f6: dup
      // 58f7: ldc 0.67
      // 58f9: ldc_w 1.078
      // 58fc: ldc_w 30.85
      // 58ff: ldc_w 3.023
      // 5902: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5905: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5908: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 590b: aastore
      // 590c: dup
      // 590d: bipush 17
      // 590f: new net/minecraft/client/animation/Keyframe
      // 5912: dup
      // 5913: ldc 0.71
      // 5915: ldc_w 0.91
      // 5918: ldc_w 23.257
      // 591b: ldc_w 2.775
      // 591e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5921: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5924: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5927: aastore
      // 5928: dup
      // 5929: bipush 18
      // 592b: new net/minecraft/client/animation/Keyframe
      // 592e: dup
      // 592f: ldc 0.75
      // 5931: ldc_w 0.729
      // 5934: ldc_w 12.33
      // 5937: ldc_w 2.339
      // 593a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 593d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5940: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5943: aastore
      // 5944: dup
      // 5945: bipush 19
      // 5947: new net/minecraft/client/animation/Keyframe
      // 594a: dup
      // 594b: ldc 0.79
      // 594d: ldc_w 0.844
      // 5950: ldc_w 4.42
      // 5953: ldc_w 1.73
      // 5956: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5959: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 595c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 595f: aastore
      // 5960: dup
      // 5961: bipush 20
      // 5963: new net/minecraft/client/animation/Keyframe
      // 5966: dup
      // 5967: ldc 0.83
      // 5969: ldc_w 1.11
      // 596c: ldc_w -0.382
      // 596f: ldc_w -0.406
      // 5972: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5975: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5978: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 597b: aastore
      // 597c: dup
      // 597d: bipush 21
      // 597f: new net/minecraft/client/animation/Keyframe
      // 5982: dup
      // 5983: ldc 0.88
      // 5985: ldc_w -2.553
      // 5988: ldc_w -7.364
      // 598b: ldc_w -0.52
      // 598e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5991: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5994: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5997: aastore
      // 5998: dup
      // 5999: bipush 22
      // 599b: new net/minecraft/client/animation/Keyframe
      // 599e: dup
      // 599f: ldc 0.92
      // 59a1: ldc_w -4.638
      // 59a4: ldc_w -18.483
      // 59a7: ldc_w -2.913
      // 59aa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 59ad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 59b0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 59b3: aastore
      // 59b4: dup
      // 59b5: bipush 23
      // 59b7: new net/minecraft/client/animation/Keyframe
      // 59ba: dup
      // 59bb: ldc 0.96
      // 59bd: ldc_w -7.46
      // 59c0: ldc_w -31.605
      // 59c3: ldc_w -5.818
      // 59c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 59c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 59cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 59cf: aastore
      // 59d0: dup
      // 59d1: bipush 24
      // 59d3: new net/minecraft/client/animation/Keyframe
      // 59d6: dup
      // 59d7: fconst_1
      // 59d8: ldc_w -11.69
      // 59db: ldc_w -35.555
      // 59de: ldc_w -10.419
      // 59e1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 59e4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 59e7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 59ea: aastore
      // 59eb: dup
      // 59ec: bipush 25
      // 59ee: new net/minecraft/client/animation/Keyframe
      // 59f1: dup
      // 59f2: ldc 1.04
      // 59f4: ldc_w -18.296
      // 59f7: ldc_w -32.01
      // 59fa: ldc_w -17.946
      // 59fd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a00: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a03: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a06: aastore
      // 5a07: dup
      // 5a08: bipush 26
      // 5a0a: new net/minecraft/client/animation/Keyframe
      // 5a0d: dup
      // 5a0e: ldc 1.08
      // 5a10: ldc_w -45.088
      // 5a13: ldc_w -34.254
      // 5a16: ldc_w -47.15
      // 5a19: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a1c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a1f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a22: aastore
      // 5a23: dup
      // 5a24: bipush 27
      // 5a26: new net/minecraft/client/animation/Keyframe
      // 5a29: dup
      // 5a2a: ldc 1.12
      // 5a2c: ldc_w -96.884
      // 5a2f: ldc_w -11.096
      // 5a32: ldc_w -115.943
      // 5a35: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a38: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a3b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a3e: aastore
      // 5a3f: dup
      // 5a40: bipush 28
      // 5a42: new net/minecraft/client/animation/Keyframe
      // 5a45: dup
      // 5a46: ldc 1.17
      // 5a48: ldc_w -119.447
      // 5a4b: ldc_w 17.237
      // 5a4e: ldc_w -107.258
      // 5a51: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a54: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a57: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a5a: aastore
      // 5a5b: dup
      // 5a5c: bipush 29
      // 5a5e: new net/minecraft/client/animation/Keyframe
      // 5a61: dup
      // 5a62: ldc 1.21
      // 5a64: ldc_w -121.845
      // 5a67: ldc_w 35.826
      // 5a6a: ldc_w -114.884
      // 5a6d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a70: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a73: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a76: aastore
      // 5a77: dup
      // 5a78: bipush 30
      // 5a7a: new net/minecraft/client/animation/Keyframe
      // 5a7d: dup
      // 5a7e: ldc 1.25
      // 5a80: ldc_w -124.41
      // 5a83: ldc_w 44.908
      // 5a86: ldc_w -118.544
      // 5a89: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a8c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a8f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a92: aastore
      // 5a93: dup
      // 5a94: bipush 31
      // 5a96: new net/minecraft/client/animation/Keyframe
      // 5a99: dup
      // 5a9a: ldc 1.29
      // 5a9c: ldc_w -144.146
      // 5a9f: ldc_w -30.766
      // 5aa2: ldc_w -145.944
      // 5aa5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5aa8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5aab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5aae: aastore
      // 5aaf: dup
      // 5ab0: bipush 32
      // 5ab2: new net/minecraft/client/animation/Keyframe
      // 5ab5: dup
      // 5ab6: ldc 1.33
      // 5ab8: ldc_w -170.982
      // 5abb: ldc_w 20.601
      // 5abe: ldc_w -166.775
      // 5ac1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5ac4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ac7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5aca: aastore
      // 5acb: dup
      // 5acc: bipush 33
      // 5ace: new net/minecraft/client/animation/Keyframe
      // 5ad1: dup
      // 5ad2: ldc 1.38
      // 5ad4: ldc_w -177.668
      // 5ad7: ldc_w 41.53
      // 5ada: ldc_w -175.005
      // 5add: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5ae0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ae3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ae6: aastore
      // 5ae7: dup
      // 5ae8: bipush 34
      // 5aea: new net/minecraft/client/animation/Keyframe
      // 5aed: dup
      // 5aee: ldc 1.42
      // 5af0: ldc_w -178.429
      // 5af3: ldc_w 90.777
      // 5af6: ldc_w -176.026
      // 5af9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5afc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5aff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b02: aastore
      // 5b03: dup
      // 5b04: bipush 35
      // 5b06: new net/minecraft/client/animation/Keyframe
      // 5b09: dup
      // 5b0a: ldc 1.46
      // 5b0c: ldc_w -177.98
      // 5b0f: ldc_w 111.502
      // 5b12: ldc_w -175.605
      // 5b15: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5b18: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b1b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b1e: aastore
      // 5b1f: dup
      // 5b20: bipush 36
      // 5b22: new net/minecraft/client/animation/Keyframe
      // 5b25: dup
      // 5b26: ldc 1.5
      // 5b28: ldc_w -180.487
      // 5b2b: ldc_w 124.483
      // 5b2e: ldc_w -178.172
      // 5b31: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5b34: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b37: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b3a: aastore
      // 5b3b: dup
      // 5b3c: bipush 37
      // 5b3e: new net/minecraft/client/animation/Keyframe
      // 5b41: dup
      // 5b42: ldc 1.54
      // 5b44: ldc_w -186.052
      // 5b47: ldc_w 131.025
      // 5b4a: ldc_w -183.818
      // 5b4d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5b50: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b53: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b56: aastore
      // 5b57: dup
      // 5b58: bipush 38
      // 5b5a: new net/minecraft/client/animation/Keyframe
      // 5b5d: dup
      // 5b5e: ldc 1.58
      // 5b60: ldc_w -192.147
      // 5b63: ldc_w 132.834
      // 5b66: ldc_w -189.991
      // 5b69: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5b6c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b6f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b72: aastore
      // 5b73: dup
      // 5b74: bipush 39
      // 5b76: new net/minecraft/client/animation/Keyframe
      // 5b79: dup
      // 5b7a: ldc 1.62
      // 5b7c: ldc_w -195.839
      // 5b7f: ldc_w 131.786
      // 5b82: ldc_w -193.734
      // 5b85: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5b88: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b8b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b8e: aastore
      // 5b8f: dup
      // 5b90: bipush 40
      // 5b92: new net/minecraft/client/animation/Keyframe
      // 5b95: dup
      // 5b96: ldc 1.67
      // 5b98: ldc_w -197.307
      // 5b9b: ldc_w 130.07
      // 5b9e: ldc_w -195.23
      // 5ba1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5ba4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ba7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5baa: aastore
      // 5bab: dup
      // 5bac: bipush 41
      // 5bae: new net/minecraft/client/animation/Keyframe
      // 5bb1: dup
      // 5bb2: ldc 1.71
      // 5bb4: ldc_w -198.179
      // 5bb7: ldc_w 128.891
      // 5bba: ldc_w -196.119
      // 5bbd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5bc0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5bc3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5bc6: aastore
      // 5bc7: dup
      // 5bc8: bipush 42
      // 5bca: new net/minecraft/client/animation/Keyframe
      // 5bcd: dup
      // 5bce: ldc 1.75
      // 5bd0: ldc_w -198.791
      // 5bd3: ldc_w 127.974
      // 5bd6: ldc_w -196.745
      // 5bd9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5bdc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5bdf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5be2: aastore
      // 5be3: dup
      // 5be4: bipush 43
      // 5be6: new net/minecraft/client/animation/Keyframe
      // 5be9: dup
      // 5bea: ldc 1.79
      // 5bec: ldc_w -199.587
      // 5bef: ldc_w 126.645
      // 5bf2: ldc_w -197.56
      // 5bf5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5bf8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5bfb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5bfe: aastore
      // 5bff: dup
      // 5c00: bipush 44
      // 5c02: new net/minecraft/client/animation/Keyframe
      // 5c05: dup
      // 5c06: ldc_w 1.83
      // 5c09: ldc_w -197.184
      // 5c0c: ldc_w 125.327
      // 5c0f: ldc_w -195.129
      // 5c12: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5c15: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c18: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c1b: aastore
      // 5c1c: dup
      // 5c1d: bipush 45
      // 5c1f: new net/minecraft/client/animation/Keyframe
      // 5c22: dup
      // 5c23: ldc_w 1.88
      // 5c26: ldc_w -188.994
      // 5c29: ldc_w 124.116
      // 5c2c: ldc_w -186.816
      // 5c2f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5c32: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c35: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c38: aastore
      // 5c39: dup
      // 5c3a: bipush 46
      // 5c3c: new net/minecraft/client/animation/Keyframe
      // 5c3f: dup
      // 5c40: ldc_w 1.92
      // 5c43: ldc_w -177.073
      // 5c46: ldc_w 121.119
      // 5c49: ldc_w -174.698
      // 5c4c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5c4f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c52: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c55: aastore
      // 5c56: dup
      // 5c57: bipush 47
      // 5c59: new net/minecraft/client/animation/Keyframe
      // 5c5c: dup
      // 5c5d: ldc_w 1.96
      // 5c60: ldc_w -164.522
      // 5c63: ldc_w 114.839
      // 5c66: ldc_w -161.896
      // 5c69: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5c6c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c6f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c72: aastore
      // 5c73: dup
      // 5c74: bipush 48
      // 5c76: new net/minecraft/client/animation/Keyframe
      // 5c79: dup
      // 5c7a: fconst_2
      // 5c7b: ldc_w -154.069
      // 5c7e: ldc_w 105.407
      // 5c81: ldc_w -151.152
      // 5c84: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5c87: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c8a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c8d: aastore
      // 5c8e: dup
      // 5c8f: bipush 49
      // 5c91: new net/minecraft/client/animation/Keyframe
      // 5c94: dup
      // 5c95: ldc_w 2.04
      // 5c98: ldc_w -146.755
      // 5c9b: ldc_w 94.284
      // 5c9e: ldc_w -143.523
      // 5ca1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5ca4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ca7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5caa: aastore
      // 5cab: dup
      // 5cac: bipush 50
      // 5cae: new net/minecraft/client/animation/Keyframe
      // 5cb1: dup
      // 5cb2: ldc_w 2.08
      // 5cb5: ldc_w -143.351
      // 5cb8: ldc_w 80.89
      // 5cbb: ldc_w -139.769
      // 5cbe: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5cc1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5cc4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5cc7: aastore
      // 5cc8: dup
      // 5cc9: bipush 51
      // 5ccb: new net/minecraft/client/animation/Keyframe
      // 5cce: dup
      // 5ccf: ldc_w 2.12
      // 5cd2: ldc_w -144.044
      // 5cd5: ldc_w 65.607
      // 5cd8: ldc_w -140.065
      // 5cdb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5cde: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ce1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ce4: aastore
      // 5ce5: dup
      // 5ce6: bipush 52
      // 5ce8: new net/minecraft/client/animation/Keyframe
      // 5ceb: dup
      // 5cec: ldc_w 2.17
      // 5cef: ldc_w -147.513
      // 5cf2: ldc_w 50.138
      // 5cf5: ldc_w -143.074
      // 5cf8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5cfb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5cfe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d01: aastore
      // 5d02: dup
      // 5d03: bipush 53
      // 5d05: new net/minecraft/client/animation/Keyframe
      // 5d08: dup
      // 5d09: ldc_w 2.21
      // 5d0c: ldc_w -152.671
      // 5d0f: ldc_w 34.95
      // 5d12: ldc_w -147.607
      // 5d15: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5d18: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d1b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d1e: aastore
      // 5d1f: dup
      // 5d20: bipush 54
      // 5d22: new net/minecraft/client/animation/Keyframe
      // 5d25: dup
      // 5d26: ldc 2.25
      // 5d28: ldc_w -158.823
      // 5d2b: ldc_w 15.844
      // 5d2e: ldc_w -151.476
      // 5d31: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5d34: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d37: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d3a: aastore
      // 5d3b: dup
      // 5d3c: bipush 55
      // 5d3e: new net/minecraft/client/animation/Keyframe
      // 5d41: dup
      // 5d42: ldc 2.29
      // 5d44: ldc_w -141.01
      // 5d47: ldc_w -3.587
      // 5d4a: ldc_w -179.927
      // 5d4d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5d50: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d53: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d56: aastore
      // 5d57: dup
      // 5d58: bipush 56
      // 5d5a: new net/minecraft/client/animation/Keyframe
      // 5d5d: dup
      // 5d5e: ldc 2.33
      // 5d60: ldc_w -165.949
      // 5d63: ldc_w -18.416
      // 5d66: ldc_w -166.788
      // 5d69: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5d6c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d6f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d72: aastore
      // 5d73: dup
      // 5d74: bipush 57
      // 5d76: new net/minecraft/client/animation/Keyframe
      // 5d79: dup
      // 5d7a: ldc 2.38
      // 5d7c: ldc_w -172.327
      // 5d7f: ldc_w -29.353
      // 5d82: ldc_w -171.23
      // 5d85: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5d88: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d8b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d8e: aastore
      // 5d8f: dup
      // 5d90: bipush 58
      // 5d92: new net/minecraft/client/animation/Keyframe
      // 5d95: dup
      // 5d96: ldc 2.42
      // 5d98: ldc_w -176.61
      // 5d9b: ldc_w -34.693
      // 5d9e: ldc_w -174.894
      // 5da1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5da4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5da7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5daa: aastore
      // 5dab: dup
      // 5dac: bipush 59
      // 5dae: new net/minecraft/client/animation/Keyframe
      // 5db1: dup
      // 5db2: ldc 2.46
      // 5db4: ldc_w -178.81
      // 5db7: ldc_w -33.246
      // 5dba: ldc_w -176.89
      // 5dbd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5dc0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5dc3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5dc6: aastore
      // 5dc7: dup
      // 5dc8: bipush 60
      // 5dca: new net/minecraft/client/animation/Keyframe
      // 5dcd: dup
      // 5dce: ldc 2.5
      // 5dd0: ldc_w -179.217
      // 5dd3: ldc_w -30.577
      // 5dd6: ldc_w -177.283
      // 5dd9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5ddc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ddf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5de2: aastore
      // 5de3: dup
      // 5de4: bipush 61
      // 5de6: new net/minecraft/client/animation/Keyframe
      // 5de9: dup
      // 5dea: ldc 2.54
      // 5dec: ldc_w -178.553
      // 5def: ldc_w -33.195
      // 5df2: ldc_w -176.66
      // 5df5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5df8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5dfb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5dfe: aastore
      // 5dff: dup
      // 5e00: bipush 62
      // 5e02: new net/minecraft/client/animation/Keyframe
      // 5e05: dup
      // 5e06: ldc_w 2.58
      // 5e09: ldc_w -178.442
      // 5e0c: ldc_w -34.508
      // 5e0f: ldc_w -176.545
      // 5e12: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5e15: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e18: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e1b: aastore
      // 5e1c: dup
      // 5e1d: bipush 63
      // 5e1f: new net/minecraft/client/animation/Keyframe
      // 5e22: dup
      // 5e23: ldc_w 2.62
      // 5e26: ldc_w -179.026
      // 5e29: ldc_w -30.478
      // 5e2c: ldc_w -177.116
      // 5e2f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5e32: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e35: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e38: aastore
      // 5e39: dup
      // 5e3a: bipush 64
      // 5e3c: new net/minecraft/client/animation/Keyframe
      // 5e3f: dup
      // 5e40: ldc_w 2.67
      // 5e43: ldc_w -179.539
      // 5e46: ldc_w -25.312
      // 5e49: ldc_w -177.637
      // 5e4c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5e4f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e52: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e55: aastore
      // 5e56: dup
      // 5e57: bipush 65
      // 5e59: new net/minecraft/client/animation/Keyframe
      // 5e5c: dup
      // 5e5d: ldc_w 2.71
      // 5e60: ldc_w -179.975
      // 5e63: ldc_w -20.17
      // 5e66: ldc_w -178.096
      // 5e69: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5e6c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e6f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e72: aastore
      // 5e73: dup
      // 5e74: bipush 66
      // 5e76: new net/minecraft/client/animation/Keyframe
      // 5e79: dup
      // 5e7a: ldc_w 2.75
      // 5e7d: ldc_w -180.052
      // 5e80: ldc_w -19.009
      // 5e83: ldc_w -178.184
      // 5e86: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5e89: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e8c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e8f: aastore
      // 5e90: dup
      // 5e91: bipush 67
      // 5e93: new net/minecraft/client/animation/Keyframe
      // 5e96: dup
      // 5e97: ldc_w 2.79
      // 5e9a: ldc_w -179.923
      // 5e9d: ldc_w -20.456
      // 5ea0: ldc_w -178.047
      // 5ea3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5ea6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ea9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5eac: aastore
      // 5ead: dup
      // 5eae: bipush 68
      // 5eb0: new net/minecraft/client/animation/Keyframe
      // 5eb3: dup
      // 5eb4: ldc 2.83
      // 5eb6: ldc_w -179.832
      // 5eb9: ldc_w -21.444
      // 5ebc: ldc_w -177.952
      // 5ebf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5ec2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ec5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ec8: aastore
      // 5ec9: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 5ecc: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5ecf: ldc_w "CTR_upperarm_right.001"
      // 5ed2: new net/minecraft/client/animation/AnimationChannel
      // 5ed5: dup
      // 5ed6: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5ed9: bipush 59
      // 5edb: anewarray 28
      // 5ede: dup
      // 5edf: bipush 0
      // 5ee0: new net/minecraft/client/animation/Keyframe
      // 5ee3: dup
      // 5ee4: fconst_0
      // 5ee5: dconst_1
      // 5ee6: dconst_1
      // 5ee7: ldc2_w 1.6740000247955322
      // 5eea: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5eed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ef0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ef3: aastore
      // 5ef4: dup
      // 5ef5: bipush 1
      // 5ef6: new net/minecraft/client/animation/Keyframe
      // 5ef9: dup
      // 5efa: ldc 0.04
      // 5efc: dconst_1
      // 5efd: dconst_1
      // 5efe: ldc2_w 1.6480000019073486
      // 5f01: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5f04: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f07: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f0a: aastore
      // 5f0b: dup
      // 5f0c: bipush 2
      // 5f0d: new net/minecraft/client/animation/Keyframe
      // 5f10: dup
      // 5f11: ldc 0.08
      // 5f13: dconst_1
      // 5f14: dconst_1
      // 5f15: ldc2_w 1.5809999704360962
      // 5f18: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5f1b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f1e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f21: aastore
      // 5f22: dup
      // 5f23: bipush 3
      // 5f24: new net/minecraft/client/animation/Keyframe
      // 5f27: dup
      // 5f28: ldc 0.12
      // 5f2a: dconst_1
      // 5f2b: dconst_1
      // 5f2c: ldc2_w 1.4900000095367432
      // 5f2f: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5f32: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f35: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f38: aastore
      // 5f39: dup
      // 5f3a: bipush 4
      // 5f3b: new net/minecraft/client/animation/Keyframe
      // 5f3e: dup
      // 5f3f: ldc 0.17
      // 5f41: dconst_1
      // 5f42: dconst_1
      // 5f43: ldc2_w 1.3880000114440918
      // 5f46: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5f49: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f4c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f4f: aastore
      // 5f50: dup
      // 5f51: bipush 5
      // 5f52: new net/minecraft/client/animation/Keyframe
      // 5f55: dup
      // 5f56: ldc 0.29
      // 5f58: dconst_1
      // 5f59: dconst_1
      // 5f5a: ldc2_w 1.0720000267028809
      // 5f5d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5f60: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f63: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f66: aastore
      // 5f67: dup
      // 5f68: bipush 6
      // 5f6a: new net/minecraft/client/animation/Keyframe
      // 5f6d: dup
      // 5f6e: ldc 0.33
      // 5f70: dconst_1
      // 5f71: dconst_1
      // 5f72: ldc2_w 0.9810000061988831
      // 5f75: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5f78: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f7b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f7e: aastore
      // 5f7f: dup
      // 5f80: bipush 7
      // 5f82: new net/minecraft/client/animation/Keyframe
      // 5f85: dup
      // 5f86: ldc 0.38
      // 5f88: dconst_1
      // 5f89: dconst_1
      // 5f8a: ldc2_w 0.9200000166893005
      // 5f8d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5f90: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f93: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f96: aastore
      // 5f97: dup
      // 5f98: bipush 8
      // 5f9a: new net/minecraft/client/animation/Keyframe
      // 5f9d: dup
      // 5f9e: ldc 0.42
      // 5fa0: dconst_1
      // 5fa1: dconst_1
      // 5fa2: ldc2_w 0.9049999713897705
      // 5fa5: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5fa8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5fab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5fae: aastore
      // 5faf: dup
      // 5fb0: bipush 9
      // 5fb2: new net/minecraft/client/animation/Keyframe
      // 5fb5: dup
      // 5fb6: ldc 0.46
      // 5fb8: dconst_1
      // 5fb9: dconst_1
      // 5fba: ldc2_w 0.9490000009536743
      // 5fbd: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5fc0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5fc3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5fc6: aastore
      // 5fc7: dup
      // 5fc8: bipush 10
      // 5fca: new net/minecraft/client/animation/Keyframe
      // 5fcd: dup
      // 5fce: ldc 0.5
      // 5fd0: dconst_1
      // 5fd1: dconst_1
      // 5fd2: ldc2_w 1.034000039100647
      // 5fd5: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5fd8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5fdb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5fde: aastore
      // 5fdf: dup
      // 5fe0: bipush 11
      // 5fe2: new net/minecraft/client/animation/Keyframe
      // 5fe5: dup
      // 5fe6: ldc 0.54
      // 5fe8: dconst_1
      // 5fe9: dconst_1
      // 5fea: ldc2_w 1.159999966621399
      // 5fed: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5ff0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ff3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ff6: aastore
      // 5ff7: dup
      // 5ff8: bipush 12
      // 5ffa: new net/minecraft/client/animation/Keyframe
      // 5ffd: dup
      // 5ffe: ldc 0.58
      // 6000: dconst_1
      // 6001: dconst_1
      // 6002: ldc2_w 1.3179999589920044
      // 6005: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 6008: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 600b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 600e: aastore
      // 600f: dup
      // 6010: bipush 13
      // 6012: new net/minecraft/client/animation/Keyframe
      // 6015: dup
      // 6016: ldc 0.62
      // 6018: dconst_1
      // 6019: dconst_1
      // 601a: ldc2_w 1.4149999618530273
      // 601d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 6020: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6023: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6026: aastore
      // 6027: dup
      // 6028: bipush 14
      // 602a: new net/minecraft/client/animation/Keyframe
      // 602d: dup
      // 602e: ldc 0.67
      // 6030: dconst_1
      // 6031: dconst_1
      // 6032: ldc2_w 1.350000023841858
      // 6035: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 6038: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 603b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 603e: aastore
      // 603f: dup
      // 6040: bipush 15
      // 6042: new net/minecraft/client/animation/Keyframe
      // 6045: dup
      // 6046: ldc 0.71
      // 6048: dconst_1
      // 6049: dconst_1
      // 604a: ldc2_w 1.187000036239624
      // 604d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 6050: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6053: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6056: aastore
      // 6057: dup
      // 6058: bipush 16
      // 605a: new net/minecraft/client/animation/Keyframe
      // 605d: dup
      // 605e: ldc 0.75
      // 6060: dconst_1
      // 6061: dconst_1
      // 6062: ldc2_w 1.055999994277954
      // 6065: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 6068: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 606b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 606e: aastore
      // 606f: dup
      // 6070: bipush 17
      // 6072: new net/minecraft/client/animation/Keyframe
      // 6075: dup
      // 6076: ldc 0.79
      // 6078: dconst_1
      // 6079: dconst_1
      // 607a: ldc2_w 1.0069999694824219
      // 607d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 6080: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6083: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6086: aastore
      // 6087: dup
      // 6088: bipush 18
      // 608a: new net/minecraft/client/animation/Keyframe
      // 608d: dup
      // 608e: ldc 0.88
      // 6090: dconst_1
      // 6091: dconst_1
      // 6092: ldc2_w 0.9440000057220459
      // 6095: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 6098: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 609b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 609e: aastore
      // 609f: dup
      // 60a0: bipush 19
      // 60a2: new net/minecraft/client/animation/Keyframe
      // 60a5: dup
      // 60a6: ldc 0.96
      // 60a8: dconst_1
      // 60a9: dconst_1
      // 60aa: ldc2_w 0.7829999923706055
      // 60ad: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 60b0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 60b3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 60b6: aastore
      // 60b7: dup
      // 60b8: bipush 20
      // 60ba: new net/minecraft/client/animation/Keyframe
      // 60bd: dup
      // 60be: fconst_1
      // 60bf: dconst_1
      // 60c0: dconst_1
      // 60c1: ldc2_w 0.6460000276565552
      // 60c4: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 60c7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 60ca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 60cd: aastore
      // 60ce: dup
      // 60cf: bipush 21
      // 60d1: new net/minecraft/client/animation/Keyframe
      // 60d4: dup
      // 60d5: ldc 1.04
      // 60d7: dconst_1
      // 60d8: dconst_1
      // 60d9: ldc2_w 0.38199999928474426
      // 60dc: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 60df: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 60e2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 60e5: aastore
      // 60e6: dup
      // 60e7: bipush 22
      // 60e9: new net/minecraft/client/animation/Keyframe
      // 60ec: dup
      // 60ed: ldc 1.08
      // 60ef: dconst_1
      // 60f0: dconst_1
      // 60f1: ldc2_w 0.0860000029206276
      // 60f4: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 60f7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 60fa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 60fd: aastore
      // 60fe: dup
      // 60ff: bipush 23
      // 6101: new net/minecraft/client/animation/Keyframe
      // 6104: dup
      // 6105: ldc 1.12
      // 6107: dconst_1
      // 6108: dconst_1
      // 6109: ldc2_w 0.328000009059906
      // 610c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 610f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6112: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6115: aastore
      // 6116: dup
      // 6117: bipush 24
      // 6119: new net/minecraft/client/animation/Keyframe
      // 611c: dup
      // 611d: ldc 1.21
      // 611f: dconst_1
      // 6120: dconst_1
      // 6121: ldc2_w 0.9570000171661377
      // 6124: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 6127: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 612a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 612d: aastore
      // 612e: dup
      // 612f: bipush 25
      // 6131: new net/minecraft/client/animation/Keyframe
      // 6134: dup
      // 6135: ldc 1.25
      // 6137: dconst_1
      // 6138: dconst_1
      // 6139: ldc2_w 1.1059999465942383
      // 613c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 613f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6142: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6145: aastore
      // 6146: dup
      // 6147: bipush 26
      // 6149: new net/minecraft/client/animation/Keyframe
      // 614c: dup
      // 614d: ldc 1.29
      // 614f: dconst_1
      // 6150: dconst_1
      // 6151: ldc2_w 0.8960000276565552
      // 6154: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 6157: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 615a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 615d: aastore
      // 615e: dup
      // 615f: bipush 27
      // 6161: new net/minecraft/client/animation/Keyframe
      // 6164: dup
      // 6165: ldc 1.33
      // 6167: dconst_1
      // 6168: dconst_1
      // 6169: ldc2_w 2.5390000343322754
      // 616c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 616f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6172: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6175: aastore
      // 6176: dup
      // 6177: bipush 28
      // 6179: new net/minecraft/client/animation/Keyframe
      // 617c: dup
      // 617d: ldc 1.38
      // 617f: dconst_1
      // 6180: dconst_1
      // 6181: ldc2_w 3.130000114440918
      // 6184: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 6187: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 618a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 618d: aastore
      // 618e: dup
      // 618f: bipush 29
      // 6191: new net/minecraft/client/animation/Keyframe
      // 6194: dup
      // 6195: ldc 1.42
      // 6197: dconst_1
      // 6198: dconst_1
      // 6199: ldc2_w 3.5369999408721924
      // 619c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 619f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 61a2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 61a5: aastore
      // 61a6: dup
      // 61a7: bipush 30
      // 61a9: new net/minecraft/client/animation/Keyframe
      // 61ac: dup
      // 61ad: ldc 1.46
      // 61af: dconst_1
      // 61b0: dconst_1
      // 61b1: ldc2_w 3.5380001068115234
      // 61b4: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 61b7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 61ba: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 61bd: aastore
      // 61be: dup
      // 61bf: bipush 31
      // 61c1: new net/minecraft/client/animation/Keyframe
      // 61c4: dup
      // 61c5: ldc 1.5
      // 61c7: dconst_1
      // 61c8: dconst_1
      // 61c9: ldc2_w 3.4690001010894775
      // 61cc: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 61cf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 61d2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 61d5: aastore
      // 61d6: dup
      // 61d7: bipush 32
      // 61d9: new net/minecraft/client/animation/Keyframe
      // 61dc: dup
      // 61dd: ldc 1.54
      // 61df: dconst_1
      // 61e0: dconst_1
      // 61e1: ldc2_w 3.4100000858306885
      // 61e4: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 61e7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 61ea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 61ed: aastore
      // 61ee: dup
      // 61ef: bipush 33
      // 61f1: new net/minecraft/client/animation/Keyframe
      // 61f4: dup
      // 61f5: ldc 1.58
      // 61f7: dconst_1
      // 61f8: dconst_1
      // 61f9: ldc2_w 3.38100004196167
      // 61fc: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 61ff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6202: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6205: aastore
      // 6206: dup
      // 6207: bipush 34
      // 6209: new net/minecraft/client/animation/Keyframe
      // 620c: dup
      // 620d: ldc 1.62
      // 620f: dconst_1
      // 6210: dconst_1
      // 6211: ldc2_w 3.3889999389648438
      // 6214: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 6217: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 621a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 621d: aastore
      // 621e: dup
      // 621f: bipush 35
      // 6221: new net/minecraft/client/animation/Keyframe
      // 6224: dup
      // 6225: ldc 1.79
      // 6227: dconst_1
      // 6228: dconst_1
      // 6229: ldc2_w 3.4579999446868896
      // 622c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 622f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6232: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6235: aastore
      // 6236: dup
      // 6237: bipush 36
      // 6239: new net/minecraft/client/animation/Keyframe
      // 623c: dup
      // 623d: ldc_w 1.88
      // 6240: dconst_1
      // 6241: dconst_1
      // 6242: ldc2_w 3.5420000553131104
      // 6245: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 6248: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 624b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 624e: aastore
      // 624f: dup
      // 6250: bipush 37
      // 6252: new net/minecraft/client/animation/Keyframe
      // 6255: dup
      // 6256: ldc_w 1.92
      // 6259: dconst_1
      // 625a: dconst_1
      // 625b: ldc2_w 3.5959999561309814
      // 625e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 6261: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6264: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6267: aastore
      // 6268: dup
      // 6269: bipush 38
      // 626b: new net/minecraft/client/animation/Keyframe
      // 626e: dup
      // 626f: ldc_w 1.96
      // 6272: dconst_1
      // 6273: dconst_1
      // 6274: ldc2_w 3.6389999389648438
      // 6277: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 627a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 627d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6280: aastore
      // 6281: dup
      // 6282: bipush 39
      // 6284: new net/minecraft/client/animation/Keyframe
      // 6287: dup
      // 6288: fconst_2
      // 6289: dconst_1
      // 628a: dconst_1
      // 628b: ldc2_w 3.6559998989105225
      // 628e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 6291: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6294: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6297: aastore
      // 6298: dup
      // 6299: bipush 40
      // 629b: new net/minecraft/client/animation/Keyframe
      // 629e: dup
      // 629f: ldc_w 2.04
      // 62a2: dconst_1
      // 62a3: dconst_1
      // 62a4: ldc2_w 3.63700008392334
      // 62a7: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 62aa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 62ad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 62b0: aastore
      // 62b1: dup
      // 62b2: bipush 41
      // 62b4: new net/minecraft/client/animation/Keyframe
      // 62b7: dup
      // 62b8: ldc_w 2.08
      // 62bb: dconst_1
      // 62bc: dconst_1
      // 62bd: ldc2_w 3.5769999027252197
      // 62c0: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 62c3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 62c6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 62c9: aastore
      // 62ca: dup
      // 62cb: bipush 42
      // 62cd: new net/minecraft/client/animation/Keyframe
      // 62d0: dup
      // 62d1: ldc_w 2.12
      // 62d4: dconst_1
      // 62d5: dconst_1
      // 62d6: ldc2_w 3.4570000171661377
      // 62d9: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 62dc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 62df: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 62e2: aastore
      // 62e3: dup
      // 62e4: bipush 43
      // 62e6: new net/minecraft/client/animation/Keyframe
      // 62e9: dup
      // 62ea: ldc_w 2.17
      // 62ed: dconst_1
      // 62ee: dconst_1
      // 62ef: ldc2_w 3.259000062942505
      // 62f2: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 62f5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 62f8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 62fb: aastore
      // 62fc: dup
      // 62fd: bipush 44
      // 62ff: new net/minecraft/client/animation/Keyframe
      // 6302: dup
      // 6303: ldc_w 2.21
      // 6306: dconst_1
      // 6307: dconst_1
      // 6308: ldc2_w 2.9860000610351562
      // 630b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 630e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6311: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6314: aastore
      // 6315: dup
      // 6316: bipush 45
      // 6318: new net/minecraft/client/animation/Keyframe
      // 631b: dup
      // 631c: ldc 2.25
      // 631e: dconst_1
      // 631f: dconst_1
      // 6320: ldc2_w 2.6070001125335693
      // 6323: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 6326: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6329: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 632c: aastore
      // 632d: dup
      // 632e: bipush 46
      // 6330: new net/minecraft/client/animation/Keyframe
      // 6333: dup
      // 6334: ldc 2.29
      // 6336: dconst_1
      // 6337: dconst_1
      // 6338: ldc2_w 2.2090001106262207
      // 633b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 633e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6341: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6344: aastore
      // 6345: dup
      // 6346: bipush 47
      // 6348: new net/minecraft/client/animation/Keyframe
      // 634b: dup
      // 634c: ldc 2.33
      // 634e: dconst_1
      // 634f: dconst_1
      // 6350: ldc2_w 1.906999945640564
      // 6353: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 6356: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6359: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 635c: aastore
      // 635d: dup
      // 635e: bipush 48
      // 6360: new net/minecraft/client/animation/Keyframe
      // 6363: dup
      // 6364: ldc 2.38
      // 6366: dconst_1
      // 6367: dconst_1
      // 6368: ldc2_w 1.718000054359436
      // 636b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 636e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6371: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6374: aastore
      // 6375: dup
      // 6376: bipush 49
      // 6378: new net/minecraft/client/animation/Keyframe
      // 637b: dup
      // 637c: ldc 2.42
      // 637e: dconst_1
      // 637f: dconst_1
      // 6380: ldc2_w 1.6260000467300415
      // 6383: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 6386: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6389: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 638c: aastore
      // 638d: dup
      // 638e: bipush 50
      // 6390: new net/minecraft/client/animation/Keyframe
      // 6393: dup
      // 6394: ldc 2.5
      // 6396: dconst_1
      // 6397: dconst_1
      // 6398: ldc2_w 1.5759999752044678
      // 639b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 639e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 63a1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 63a4: aastore
      // 63a5: dup
      // 63a6: bipush 51
      // 63a8: new net/minecraft/client/animation/Keyframe
      // 63ab: dup
      // 63ac: ldc 2.54
      // 63ae: dconst_1
      // 63af: dconst_1
      // 63b0: ldc2_w 1.5010000467300415
      // 63b3: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 63b6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 63b9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 63bc: aastore
      // 63bd: dup
      // 63be: bipush 52
      // 63c0: new net/minecraft/client/animation/Keyframe
      // 63c3: dup
      // 63c4: ldc_w 2.58
      // 63c7: dconst_1
      // 63c8: dconst_1
      // 63c9: ldc2_w 1.5019999742507935
      // 63cc: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 63cf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 63d2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 63d5: aastore
      // 63d6: dup
      // 63d7: bipush 53
      // 63d9: new net/minecraft/client/animation/Keyframe
      // 63dc: dup
      // 63dd: ldc_w 2.62
      // 63e0: dconst_1
      // 63e1: dconst_1
      // 63e2: ldc2_w 1.5809999704360962
      // 63e5: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 63e8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 63eb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 63ee: aastore
      // 63ef: dup
      // 63f0: bipush 54
      // 63f2: new net/minecraft/client/animation/Keyframe
      // 63f5: dup
      // 63f6: ldc_w 2.67
      // 63f9: dconst_1
      // 63fa: dconst_1
      // 63fb: ldc2_w 1.6410000324249268
      // 63fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 6401: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6404: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6407: aastore
      // 6408: dup
      // 6409: bipush 55
      // 640b: new net/minecraft/client/animation/Keyframe
      // 640e: dup
      // 640f: ldc_w 2.71
      // 6412: dconst_1
      // 6413: dconst_1
      // 6414: ldc2_w 1.7079999446868896
      // 6417: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 641a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 641d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6420: aastore
      // 6421: dup
      // 6422: bipush 56
      // 6424: new net/minecraft/client/animation/Keyframe
      // 6427: dup
      // 6428: ldc_w 2.75
      // 642b: dconst_1
      // 642c: dconst_1
      // 642d: ldc2_w 1.7200000286102295
      // 6430: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 6433: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6436: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6439: aastore
      // 643a: dup
      // 643b: bipush 57
      // 643d: new net/minecraft/client/animation/Keyframe
      // 6440: dup
      // 6441: ldc_w 2.79
      // 6444: dconst_1
      // 6445: dconst_1
      // 6446: ldc2_w 1.6920000314712524
      // 6449: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 644c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 644f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6452: aastore
      // 6453: dup
      // 6454: bipush 58
      // 6456: new net/minecraft/client/animation/Keyframe
      // 6459: dup
      // 645a: ldc 2.83
      // 645c: dconst_1
      // 645d: dconst_1
      // 645e: ldc2_w 1.6740000247955322
      // 6461: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 6464: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6467: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 646a: aastore
      // 646b: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 646e: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 6471: ldc_w "CTR_leg_left"
      // 6474: new net/minecraft/client/animation/AnimationChannel
      // 6477: dup
      // 6478: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 647b: bipush 66
      // 647d: anewarray 28
      // 6480: dup
      // 6481: bipush 0
      // 6482: new net/minecraft/client/animation/Keyframe
      // 6485: dup
      // 6486: fconst_0
      // 6487: ldc_w -177.066
      // 648a: ldc_w 52.262
      // 648d: ldc_w 182.934
      // 6490: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6493: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6496: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6499: aastore
      // 649a: dup
      // 649b: bipush 1
      // 649c: new net/minecraft/client/animation/Keyframe
      // 649f: dup
      // 64a0: ldc 0.08
      // 64a2: ldc_w -177.159
      // 64a5: ldc_w 52.231
      // 64a8: ldc_w 182.841
      // 64ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 64ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 64b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 64b4: aastore
      // 64b5: dup
      // 64b6: bipush 2
      // 64b7: new net/minecraft/client/animation/Keyframe
      // 64ba: dup
      // 64bb: ldc 0.17
      // 64bd: ldc_w -177.612
      // 64c0: ldc_w 52.19
      // 64c3: ldc_w 182.388
      // 64c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 64c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 64cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 64cf: aastore
      // 64d0: dup
      // 64d1: bipush 3
      // 64d2: new net/minecraft/client/animation/Keyframe
      // 64d5: dup
      // 64d6: ldc 0.21
      // 64d8: ldc_w -178.17
      // 64db: ldc_w 52.223
      // 64de: ldc_w 181.83
      // 64e1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 64e4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 64e7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 64ea: aastore
      // 64eb: dup
      // 64ec: bipush 4
      // 64ed: new net/minecraft/client/animation/Keyframe
      // 64f0: dup
      // 64f1: ldc 0.25
      // 64f3: ldc_w -179.172
      // 64f6: ldc_w 52.233
      // 64f9: ldc_w 180.828
      // 64fc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 64ff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6502: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6505: aastore
      // 6506: dup
      // 6507: bipush 5
      // 6508: new net/minecraft/client/animation/Keyframe
      // 650b: dup
      // 650c: ldc 0.29
      // 650e: ldc_w -180.827
      // 6511: ldc_w 51.866
      // 6514: ldc_w 179.173
      // 6517: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 651a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 651d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6520: aastore
      // 6521: dup
      // 6522: bipush 6
      // 6524: new net/minecraft/client/animation/Keyframe
      // 6527: dup
      // 6528: ldc 0.33
      // 652a: ldc_w -183.169
      // 652d: ldc_w 50.298
      // 6530: ldc_w 176.831
      // 6533: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6536: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6539: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 653c: aastore
      // 653d: dup
      // 653e: bipush 7
      // 6540: new net/minecraft/client/animation/Keyframe
      // 6543: dup
      // 6544: ldc 0.38
      // 6546: ldc_w -185.775
      // 6549: ldc_w 46.562
      // 654c: ldc_w 174.225
      // 654f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6552: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6555: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6558: aastore
      // 6559: dup
      // 655a: bipush 8
      // 655c: new net/minecraft/client/animation/Keyframe
      // 655f: dup
      // 6560: ldc 0.42
      // 6562: ldc_w -187.838
      // 6565: ldc_w 40.475
      // 6568: ldc_w 172.162
      // 656b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 656e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6571: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6574: aastore
      // 6575: dup
      // 6576: bipush 9
      // 6578: new net/minecraft/client/animation/Keyframe
      // 657b: dup
      // 657c: ldc 0.46
      // 657e: ldc_w -188.712
      // 6581: ldc_w 32.946
      // 6584: ldc_w 171.288
      // 6587: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 658a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 658d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6590: aastore
      // 6591: dup
      // 6592: bipush 10
      // 6594: new net/minecraft/client/animation/Keyframe
      // 6597: dup
      // 6598: ldc 0.5
      // 659a: ldc_w -188.714
      // 659d: ldc_w 27.033
      // 65a0: ldc_w 171.285
      // 65a3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 65a6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 65a9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 65ac: aastore
      // 65ad: dup
      // 65ae: bipush 11
      // 65b0: new net/minecraft/client/animation/Keyframe
      // 65b3: dup
      // 65b4: ldc 0.54
      // 65b6: ldc_w -188.574
      // 65b9: ldc_w 24.362
      // 65bc: ldc_w 171.425
      // 65bf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 65c2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 65c5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 65c8: aastore
      // 65c9: dup
      // 65ca: bipush 12
      // 65cc: new net/minecraft/client/animation/Keyframe
      // 65cf: dup
      // 65d0: ldc 0.58
      // 65d2: ldc_w -188.456
      // 65d5: ldc_w 24.091
      // 65d8: ldc_w 171.544
      // 65db: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 65de: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 65e1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 65e4: aastore
      // 65e5: dup
      // 65e6: bipush 13
      // 65e8: new net/minecraft/client/animation/Keyframe
      // 65eb: dup
      // 65ec: ldc 0.62
      // 65ee: ldc_w -188.394
      // 65f1: ldc_w 25.45
      // 65f4: ldc_w 171.604
      // 65f7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 65fa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 65fd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6600: aastore
      // 6601: dup
      // 6602: bipush 14
      // 6604: new net/minecraft/client/animation/Keyframe
      // 6607: dup
      // 6608: ldc 0.67
      // 660a: ldc_w -188.379
      // 660d: ldc_w 27.72
      // 6610: ldc_w 171.621
      // 6613: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6616: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6619: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 661c: aastore
      // 661d: dup
      // 661e: bipush 15
      // 6620: new net/minecraft/client/animation/Keyframe
      // 6623: dup
      // 6624: ldc 0.71
      // 6626: ldc_w -188.405
      // 6629: ldc_w 30.198
      // 662c: ldc_w 171.595
      // 662f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6632: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6635: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6638: aastore
      // 6639: dup
      // 663a: bipush 16
      // 663c: new net/minecraft/client/animation/Keyframe
      // 663f: dup
      // 6640: ldc 0.75
      // 6642: ldc_w -188.499
      // 6645: ldc_w 32.18
      // 6648: ldc_w 171.502
      // 664b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 664e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6651: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6654: aastore
      // 6655: dup
      // 6656: bipush 17
      // 6658: new net/minecraft/client/animation/Keyframe
      // 665b: dup
      // 665c: ldc 0.79
      // 665e: ldc_w -188.712
      // 6661: ldc_w 32.946
      // 6664: ldc_w 171.288
      // 6667: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 666a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 666d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6670: aastore
      // 6671: dup
      // 6672: bipush 18
      // 6674: new net/minecraft/client/animation/Keyframe
      // 6677: dup
      // 6678: ldc 0.83
      // 667a: ldc_w -188.823
      // 667d: ldc_w 32.339
      // 6680: ldc_w 171.177
      // 6683: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6686: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6689: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 668c: aastore
      // 668d: dup
      // 668e: bipush 19
      // 6690: new net/minecraft/client/animation/Keyframe
      // 6693: dup
      // 6694: ldc 0.88
      // 6696: ldc_w -188.583
      // 6699: ldc_w 30.845
      // 669c: ldc_w 171.417
      // 669f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 66a2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 66a5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 66a8: aastore
      // 66a9: dup
      // 66aa: bipush 20
      // 66ac: new net/minecraft/client/animation/Keyframe
      // 66af: dup
      // 66b0: ldc 0.92
      // 66b2: ldc_w -188.013
      // 66b5: ldc_w 28.694
      // 66b8: ldc_w 171.987
      // 66bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 66be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 66c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 66c4: aastore
      // 66c5: dup
      // 66c6: bipush 21
      // 66c8: new net/minecraft/client/animation/Keyframe
      // 66cb: dup
      // 66cc: ldc 0.96
      // 66ce: ldc_w -187.143
      // 66d1: ldc_w 26.108
      // 66d4: ldc_w 172.857
      // 66d7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 66da: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 66dd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 66e0: aastore
      // 66e1: dup
      // 66e2: bipush 22
      // 66e4: new net/minecraft/client/animation/Keyframe
      // 66e7: dup
      // 66e8: fconst_1
      // 66e9: ldc_w -186.027
      // 66ec: ldc_w 23.285
      // 66ef: ldc_w 173.973
      // 66f2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 66f5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 66f8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 66fb: aastore
      // 66fc: dup
      // 66fd: bipush 23
      // 66ff: new net/minecraft/client/animation/Keyframe
      // 6702: dup
      // 6703: ldc 1.08
      // 6705: ldc_w -183.39
      // 6708: ldc_w 17.568
      // 670b: ldc_w 176.61
      // 670e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6711: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6714: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6717: aastore
      // 6718: dup
      // 6719: bipush 24
      // 671b: new net/minecraft/client/animation/Keyframe
      // 671e: dup
      // 671f: ldc 1.12
      // 6721: ldc_w -182.091
      // 6724: ldc_w 14.937
      // 6727: ldc_w 177.909
      // 672a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 672d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6730: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6733: aastore
      // 6734: dup
      // 6735: bipush 25
      // 6737: new net/minecraft/client/animation/Keyframe
      // 673a: dup
      // 673b: ldc 1.17
      // 673d: ldc_w -180.981
      // 6740: ldc_w 12.607
      // 6743: ldc_w 179.019
      // 6746: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6749: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 674c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 674f: aastore
      // 6750: dup
      // 6751: bipush 26
      // 6753: new net/minecraft/client/animation/Keyframe
      // 6756: dup
      // 6757: ldc 1.21
      // 6759: ldc_w -180.206
      // 675c: ldc_w 10.682
      // 675f: ldc_w 179.794
      // 6762: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6765: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6768: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 676b: aastore
      // 676c: dup
      // 676d: bipush 27
      // 676f: new net/minecraft/client/animation/Keyframe
      // 6772: dup
      // 6773: ldc 1.25
      // 6775: ldc_w -179.916
      // 6778: ldc_w 9.274
      // 677b: ldc_w 180.084
      // 677e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6781: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6784: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6787: aastore
      // 6788: dup
      // 6789: bipush 28
      // 678b: new net/minecraft/client/animation/Keyframe
      // 678e: dup
      // 678f: ldc 1.29
      // 6791: ldc_w -179.849
      // 6794: ldc_w 23.064
      // 6797: ldc_w 180.151
      // 679a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 679d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 67a0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 67a3: aastore
      // 67a4: dup
      // 67a5: bipush 29
      // 67a7: new net/minecraft/client/animation/Keyframe
      // 67aa: dup
      // 67ab: ldc 1.33
      // 67ad: ldc_w -170.241
      // 67b0: ldc_w 42.794
      // 67b3: ldc_w 189.759
      // 67b6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 67b9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 67bc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 67bf: aastore
      // 67c0: dup
      // 67c1: bipush 30
      // 67c3: new net/minecraft/client/animation/Keyframe
      // 67c6: dup
      // 67c7: ldc 1.38
      // 67c9: ldc_w -162.628
      // 67cc: ldc_w 38.328
      // 67cf: ldc_w 197.372
      // 67d2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 67d5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 67d8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 67db: aastore
      // 67dc: dup
      // 67dd: bipush 31
      // 67df: new net/minecraft/client/animation/Keyframe
      // 67e2: dup
      // 67e3: ldc 1.42
      // 67e5: ldc_w -166.705
      // 67e8: ldc_w 35.447
      // 67eb: ldc_w 193.295
      // 67ee: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 67f1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 67f4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 67f7: aastore
      // 67f8: dup
      // 67f9: bipush 32
      // 67fb: new net/minecraft/client/animation/Keyframe
      // 67fe: dup
      // 67ff: ldc 1.46
      // 6801: ldc_w -175.096
      // 6804: ldc_w 31.886
      // 6807: ldc_w 184.904
      // 680a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 680d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6810: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6813: aastore
      // 6814: dup
      // 6815: bipush 33
      // 6817: new net/minecraft/client/animation/Keyframe
      // 681a: dup
      // 681b: ldc 1.5
      // 681d: ldc_w -179.832
      // 6820: ldc_w 30.267
      // 6823: ldc_w 180.168
      // 6826: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6829: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 682c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 682f: aastore
      // 6830: dup
      // 6831: bipush 34
      // 6833: new net/minecraft/client/animation/Keyframe
      // 6836: dup
      // 6837: ldc 1.54
      // 6839: ldc_w -181.242
      // 683c: ldc_w 29.439
      // 683f: ldc_w 178.758
      // 6842: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6845: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6848: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 684b: aastore
      // 684c: dup
      // 684d: bipush 35
      // 684f: new net/minecraft/client/animation/Keyframe
      // 6852: dup
      // 6853: ldc 1.58
      // 6855: ldc_w -181.517
      // 6858: ldc_w 29.17
      // 685b: ldc_w 178.482
      // 685e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6861: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6864: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6867: aastore
      // 6868: dup
      // 6869: bipush 36
      // 686b: new net/minecraft/client/animation/Keyframe
      // 686e: dup
      // 686f: ldc 1.62
      // 6871: ldc_w -181.801
      // 6874: ldc_w 27.745
      // 6877: ldc_w 178.199
      // 687a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 687d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6880: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6883: aastore
      // 6884: dup
      // 6885: bipush 37
      // 6887: new net/minecraft/client/animation/Keyframe
      // 688a: dup
      // 688b: ldc 1.67
      // 688d: ldc_w -182.267
      // 6890: ldc_w 25.146
      // 6893: ldc_w 177.732
      // 6896: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6899: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 689c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 689f: aastore
      // 68a0: dup
      // 68a1: bipush 38
      // 68a3: new net/minecraft/client/animation/Keyframe
      // 68a6: dup
      // 68a7: ldc 1.71
      // 68a9: ldc_w -182.555
      // 68ac: ldc_w 23.349
      // 68af: ldc_w 177.445
      // 68b2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 68b5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 68b8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 68bb: aastore
      // 68bc: dup
      // 68bd: bipush 39
      // 68bf: new net/minecraft/client/animation/Keyframe
      // 68c2: dup
      // 68c3: ldc 1.75
      // 68c5: ldc_w -182.768
      // 68c8: ldc_w 21.937
      // 68cb: ldc_w 177.233
      // 68ce: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 68d1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 68d4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 68d7: aastore
      // 68d8: dup
      // 68d9: bipush 40
      // 68db: new net/minecraft/client/animation/Keyframe
      // 68de: dup
      // 68df: ldc 1.79
      // 68e1: ldc_w -183.058
      // 68e4: ldc_w 19.866
      // 68e7: ldc_w 176.941
      // 68ea: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 68ed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 68f0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 68f3: aastore
      // 68f4: dup
      // 68f5: bipush 41
      // 68f7: new net/minecraft/client/animation/Keyframe
      // 68fa: dup
      // 68fb: ldc_w 1.83
      // 68fe: ldc_w -183.513
      // 6901: ldc_w 17.088
      // 6904: ldc_w 176.487
      // 6907: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 690a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 690d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6910: aastore
      // 6911: dup
      // 6912: bipush 42
      // 6914: new net/minecraft/client/animation/Keyframe
      // 6917: dup
      // 6918: ldc_w 1.88
      // 691b: ldc_w -183.986
      // 691e: ldc_w 14.389
      // 6921: ldc_w 176.014
      // 6924: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6927: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 692a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 692d: aastore
      // 692e: dup
      // 692f: bipush 43
      // 6931: new net/minecraft/client/animation/Keyframe
      // 6934: dup
      // 6935: ldc_w 1.92
      // 6938: ldc_w -184.258
      // 693b: ldc_w 12.029
      // 693e: ldc_w 175.742
      // 6941: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6944: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6947: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 694a: aastore
      // 694b: dup
      // 694c: bipush 44
      // 694e: new net/minecraft/client/animation/Keyframe
      // 6951: dup
      // 6952: ldc_w 1.96
      // 6955: ldc_w -184.186
      // 6958: ldc_w 10.311
      // 695b: ldc_w 175.814
      // 695e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6961: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6964: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6967: aastore
      // 6968: dup
      // 6969: bipush 45
      // 696b: new net/minecraft/client/animation/Keyframe
      // 696e: dup
      // 696f: fconst_2
      // 6970: ldc_w -183.737
      // 6973: ldc_w 9.516
      // 6976: ldc_w 176.263
      // 6979: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 697c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 697f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6982: aastore
      // 6983: dup
      // 6984: bipush 46
      // 6986: new net/minecraft/client/animation/Keyframe
      // 6989: dup
      // 698a: ldc_w 2.04
      // 698d: ldc_w -182.978
      // 6990: ldc_w 9.844
      // 6993: ldc_w 177.022
      // 6996: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6999: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 699c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 699f: aastore
      // 69a0: dup
      // 69a1: bipush 47
      // 69a3: new net/minecraft/client/animation/Keyframe
      // 69a6: dup
      // 69a7: ldc_w 2.08
      // 69aa: ldc_w -181.871
      // 69ad: ldc_w 12.137
      // 69b0: ldc_w 178.129
      // 69b3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 69b6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 69b9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 69bc: aastore
      // 69bd: dup
      // 69be: bipush 48
      // 69c0: new net/minecraft/client/animation/Keyframe
      // 69c3: dup
      // 69c4: ldc_w 2.12
      // 69c7: ldc_w -180.708
      // 69ca: ldc_w 16.751
      // 69cd: ldc_w 179.292
      // 69d0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 69d3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 69d6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 69d9: aastore
      // 69da: dup
      // 69db: bipush 49
      // 69dd: new net/minecraft/client/animation/Keyframe
      // 69e0: dup
      // 69e1: ldc_w 2.17
      // 69e4: ldc_w -179.914
      // 69e7: ldc_w 22.962
      // 69ea: ldc_w 180.086
      // 69ed: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 69f0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 69f3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 69f6: aastore
      // 69f7: dup
      // 69f8: bipush 50
      // 69fa: new net/minecraft/client/animation/Keyframe
      // 69fd: dup
      // 69fe: ldc_w 2.21
      // 6a01: ldc_w -179.699
      // 6a04: ldc_w 29.896
      // 6a07: ldc_w 180.301
      // 6a0a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6a0d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a10: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a13: aastore
      // 6a14: dup
      // 6a15: bipush 51
      // 6a17: new net/minecraft/client/animation/Keyframe
      // 6a1a: dup
      // 6a1b: ldc 2.25
      // 6a1d: ldc_w -180.07
      // 6a20: ldc_w 36.569
      // 6a23: ldc_w 179.93
      // 6a26: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6a29: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a2c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a2f: aastore
      // 6a30: dup
      // 6a31: bipush 52
      // 6a33: new net/minecraft/client/animation/Keyframe
      // 6a36: dup
      // 6a37: ldc 2.29
      // 6a39: ldc_w -180.546
      // 6a3c: ldc_w 42.824
      // 6a3f: ldc_w 179.454
      // 6a42: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6a45: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a48: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a4b: aastore
      // 6a4c: dup
      // 6a4d: bipush 53
      // 6a4f: new net/minecraft/client/animation/Keyframe
      // 6a52: dup
      // 6a53: ldc 2.33
      // 6a55: ldc_w -180.73
      // 6a58: ldc_w 48.485
      // 6a5b: ldc_w 179.27
      // 6a5e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6a61: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a64: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a67: aastore
      // 6a68: dup
      // 6a69: bipush 54
      // 6a6b: new net/minecraft/client/animation/Keyframe
      // 6a6e: dup
      // 6a6f: ldc 2.38
      // 6a71: ldc_w -180.482
      // 6a74: ldc_w 52.409
      // 6a77: ldc_w 179.518
      // 6a7a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6a7d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a80: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a83: aastore
      // 6a84: dup
      // 6a85: bipush 55
      // 6a87: new net/minecraft/client/animation/Keyframe
      // 6a8a: dup
      // 6a8b: ldc 2.42
      // 6a8d: ldc_w -179.695
      // 6a90: ldc_w 54.335
      // 6a93: ldc_w 180.305
      // 6a96: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6a99: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a9c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a9f: aastore
      // 6aa0: dup
      // 6aa1: bipush 56
      // 6aa3: new net/minecraft/client/animation/Keyframe
      // 6aa6: dup
      // 6aa7: ldc 2.46
      // 6aa9: ldc_w -178.608
      // 6aac: ldc_w 54.92
      // 6aaf: ldc_w 181.392
      // 6ab2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6ab5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ab8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6abb: aastore
      // 6abc: dup
      // 6abd: bipush 57
      // 6abf: new net/minecraft/client/animation/Keyframe
      // 6ac2: dup
      // 6ac3: ldc 2.5
      // 6ac5: ldc_w -177.594
      // 6ac8: ldc_w 54.213
      // 6acb: ldc_w 182.406
      // 6ace: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6ad1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ad4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6ad7: aastore
      // 6ad8: dup
      // 6ad9: bipush 58
      // 6adb: new net/minecraft/client/animation/Keyframe
      // 6ade: dup
      // 6adf: ldc 2.54
      // 6ae1: ldc_w -177.066
      // 6ae4: ldc_w 52.262
      // 6ae7: ldc_w 182.934
      // 6aea: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6aed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6af0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6af3: aastore
      // 6af4: dup
      // 6af5: bipush 59
      // 6af7: new net/minecraft/client/animation/Keyframe
      // 6afa: dup
      // 6afb: ldc_w 2.58
      // 6afe: ldc_w -176.958
      // 6b01: ldc_w 50.812
      // 6b04: ldc_w 183.042
      // 6b07: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6b0a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6b0d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6b10: aastore
      // 6b11: dup
      // 6b12: bipush 60
      // 6b14: new net/minecraft/client/animation/Keyframe
      // 6b17: dup
      // 6b18: ldc_w 2.62
      // 6b1b: ldc_w -176.931
      // 6b1e: ldc_w 51.028
      // 6b21: ldc_w 183.069
      // 6b24: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6b27: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6b2a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6b2d: aastore
      // 6b2e: dup
      // 6b2f: bipush 61
      // 6b31: new net/minecraft/client/animation/Keyframe
      // 6b34: dup
      // 6b35: ldc_w 2.67
      // 6b38: ldc_w -176.999
      // 6b3b: ldc_w 52.121
      // 6b3e: ldc_w 183.001
      // 6b41: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6b44: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6b47: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6b4a: aastore
      // 6b4b: dup
      // 6b4c: bipush 62
      // 6b4e: new net/minecraft/client/animation/Keyframe
      // 6b51: dup
      // 6b52: ldc_w 2.71
      // 6b55: ldc_w -177.101
      // 6b58: ldc_w 52.894
      // 6b5b: ldc_w 182.899
      // 6b5e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6b61: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6b64: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6b67: aastore
      // 6b68: dup
      // 6b69: bipush 63
      // 6b6b: new net/minecraft/client/animation/Keyframe
      // 6b6e: dup
      // 6b6f: ldc_w 2.75
      // 6b72: ldc_w -177.129
      // 6b75: ldc_w 52.877
      // 6b78: ldc_w 182.871
      // 6b7b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6b7e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6b81: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6b84: aastore
      // 6b85: dup
      // 6b86: bipush 64
      // 6b88: new net/minecraft/client/animation/Keyframe
      // 6b8b: dup
      // 6b8c: ldc_w 2.79
      // 6b8f: ldc_w -177.094
      // 6b92: ldc_w 52.499
      // 6b95: ldc_w 182.906
      // 6b98: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6b9b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6b9e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6ba1: aastore
      // 6ba2: dup
      // 6ba3: bipush 65
      // 6ba5: new net/minecraft/client/animation/Keyframe
      // 6ba8: dup
      // 6ba9: ldc 2.83
      // 6bab: ldc_w -177.066
      // 6bae: ldc_w 52.262
      // 6bb1: ldc_w 182.934
      // 6bb4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6bb7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6bba: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6bbd: aastore
      // 6bbe: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 6bc1: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 6bc4: ldc_w "CTR_leg_left"
      // 6bc7: new net/minecraft/client/animation/AnimationChannel
      // 6bca: dup
      // 6bcb: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 6bce: bipush 69
      // 6bd0: anewarray 28
      // 6bd3: dup
      // 6bd4: bipush 0
      // 6bd5: new net/minecraft/client/animation/Keyframe
      // 6bd8: dup
      // 6bd9: fconst_0
      // 6bda: ldc_w 0.878
      // 6bdd: ldc_w -1.018
      // 6be0: ldc_w -0.451
      // 6be3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6be6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6be9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6bec: aastore
      // 6bed: dup
      // 6bee: bipush 1
      // 6bef: new net/minecraft/client/animation/Keyframe
      // 6bf2: dup
      // 6bf3: ldc 0.04
      // 6bf5: ldc_w 0.87
      // 6bf8: ldc_w -0.975
      // 6bfb: ldc_w -0.431
      // 6bfe: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6c01: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6c04: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6c07: aastore
      // 6c08: dup
      // 6c09: bipush 2
      // 6c0a: new net/minecraft/client/animation/Keyframe
      // 6c0d: dup
      // 6c0e: ldc 0.08
      // 6c10: ldc_w 0.841
      // 6c13: ldc_w -0.838
      // 6c16: ldc_w -0.365
      // 6c19: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6c1c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6c1f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6c22: aastore
      // 6c23: dup
      // 6c24: bipush 3
      // 6c25: new net/minecraft/client/animation/Keyframe
      // 6c28: dup
      // 6c29: ldc 0.12
      // 6c2b: ldc_w 0.782
      // 6c2e: ldc_w -0.596
      // 6c31: ldc_w -0.248
      // 6c34: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6c37: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6c3a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6c3d: aastore
      // 6c3e: dup
      // 6c3f: bipush 4
      // 6c40: new net/minecraft/client/animation/Keyframe
      // 6c43: dup
      // 6c44: ldc 0.17
      // 6c46: ldc_w 0.671
      // 6c49: ldc_w -0.247
      // 6c4c: ldc_w -0.073
      // 6c4f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6c52: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6c55: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6c58: aastore
      // 6c59: dup
      // 6c5a: bipush 5
      // 6c5b: new net/minecraft/client/animation/Keyframe
      // 6c5e: dup
      // 6c5f: ldc 0.21
      // 6c61: ldc_w 0.469
      // 6c64: ldc_w 0.199
      // 6c67: ldc_w 0.161
      // 6c6a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6c6d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6c70: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6c73: aastore
      // 6c74: dup
      // 6c75: bipush 6
      // 6c77: new net/minecraft/client/animation/Keyframe
      // 6c7a: dup
      // 6c7b: ldc 0.25
      // 6c7d: ldc_w 0.107
      // 6c80: ldc_w 0.705
      // 6c83: ldc_w 0.43
      // 6c86: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6c89: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6c8c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6c8f: aastore
      // 6c90: dup
      // 6c91: bipush 7
      // 6c93: new net/minecraft/client/animation/Keyframe
      // 6c96: dup
      // 6c97: ldc 0.29
      // 6c99: ldc_w -0.504
      // 6c9c: ldc_w 1.202
      // 6c9f: ldc_w 0.648
      // 6ca2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6ca5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ca8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6cab: aastore
      // 6cac: dup
      // 6cad: bipush 8
      // 6caf: new net/minecraft/client/animation/Keyframe
      // 6cb2: dup
      // 6cb3: ldc 0.33
      // 6cb5: ldc_w -1.391
      // 6cb8: ldc_w 1.604
      // 6cbb: ldc_w 0.618
      // 6cbe: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6cc1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6cc4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6cc7: aastore
      // 6cc8: dup
      // 6cc9: bipush 9
      // 6ccb: new net/minecraft/client/animation/Keyframe
      // 6cce: dup
      // 6ccf: ldc 0.38
      // 6cd1: ldc_w -2.388
      // 6cd4: ldc_w 1.843
      // 6cd7: ldc_w 0.104
      // 6cda: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6cdd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ce0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6ce3: aastore
      // 6ce4: dup
      // 6ce5: bipush 10
      // 6ce7: new net/minecraft/client/animation/Keyframe
      // 6cea: dup
      // 6ceb: ldc 0.42
      // 6ced: ldc_w -3.138
      // 6cf0: ldc_w 1.888
      // 6cf3: ldc_w -0.947
      // 6cf6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6cf9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6cfc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6cff: aastore
      // 6d00: dup
      // 6d01: bipush 11
      // 6d03: new net/minecraft/client/animation/Keyframe
      // 6d06: dup
      // 6d07: ldc 0.46
      // 6d09: ldc_w -3.337
      // 6d0c: ldc_w 1.724
      // 6d0f: ldc_w -2.304
      // 6d12: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6d15: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6d18: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6d1b: aastore
      // 6d1c: dup
      // 6d1d: bipush 12
      // 6d1f: new net/minecraft/client/animation/Keyframe
      // 6d22: dup
      // 6d23: ldc 0.5
      // 6d25: ldc_w -3.155
      // 6d28: ldc_w 1.417
      // 6d2b: ldc_w -3.328
      // 6d2e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6d31: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6d34: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6d37: aastore
      // 6d38: dup
      // 6d39: bipush 13
      // 6d3b: new net/minecraft/client/animation/Keyframe
      // 6d3e: dup
      // 6d3f: ldc 0.54
      // 6d41: ldc_w -2.988
      // 6d44: ldc_w 1.199
      // 6d47: ldc_w -3.762
      // 6d4a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6d4d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6d50: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6d53: aastore
      // 6d54: dup
      // 6d55: bipush 14
      // 6d57: new net/minecraft/client/animation/Keyframe
      // 6d5a: dup
      // 6d5b: ldc 0.58
      // 6d5d: ldc_w -2.918
      // 6d60: ldc_w 1.149
      // 6d63: ldc_w -3.803
      // 6d66: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6d69: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6d6c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6d6f: aastore
      // 6d70: dup
      // 6d71: bipush 15
      // 6d73: new net/minecraft/client/animation/Keyframe
      // 6d76: dup
      // 6d77: ldc 0.62
      // 6d79: ldc_w -2.938
      // 6d7c: ldc_w 1.234
      // 6d7f: ldc_w -3.587
      // 6d82: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6d85: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6d88: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6d8b: aastore
      // 6d8c: dup
      // 6d8d: bipush 16
      // 6d8f: new net/minecraft/client/animation/Keyframe
      // 6d92: dup
      // 6d93: ldc 0.67
      // 6d95: ldc_w -3.011
      // 6d98: ldc_w 1.385
      // 6d9b: ldc_w -3.217
      // 6d9e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6da1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6da4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6da7: aastore
      // 6da8: dup
      // 6da9: bipush 17
      // 6dab: new net/minecraft/client/animation/Keyframe
      // 6dae: dup
      // 6daf: ldc 0.71
      // 6db1: ldc_w -3.106
      // 6db4: ldc_w 1.538
      // 6db7: ldc_w -2.797
      // 6dba: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6dbd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6dc0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6dc3: aastore
      // 6dc4: dup
      // 6dc5: bipush 18
      // 6dc7: new net/minecraft/client/animation/Keyframe
      // 6dca: dup
      // 6dcb: ldc 0.75
      // 6dcd: ldc_w -3.212
      // 6dd0: ldc_w 1.655
      // 6dd3: ldc_w -2.448
      // 6dd6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6dd9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ddc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6ddf: aastore
      // 6de0: dup
      // 6de1: bipush 19
      // 6de3: new net/minecraft/client/animation/Keyframe
      // 6de6: dup
      // 6de7: ldc 0.79
      // 6de9: ldc_w -3.337
      // 6dec: ldc_w 1.724
      // 6def: ldc_w -2.304
      // 6df2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6df5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6df8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6dfb: aastore
      // 6dfc: dup
      // 6dfd: bipush 20
      // 6dff: new net/minecraft/client/animation/Keyframe
      // 6e02: dup
      // 6e03: ldc 0.83
      // 6e05: ldc_w -3.326
      // 6e08: ldc_w 1.553
      // 6e0b: ldc_w -2.285
      // 6e0e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6e11: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6e14: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6e17: aastore
      // 6e18: dup
      // 6e19: bipush 21
      // 6e1b: new net/minecraft/client/animation/Keyframe
      // 6e1e: dup
      // 6e1f: ldc 0.88
      // 6e21: ldc_w -3.047
      // 6e24: ldc_w 1.008
      // 6e27: ldc_w -2.206
      // 6e2a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6e2d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6e30: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6e33: aastore
      // 6e34: dup
      // 6e35: bipush 22
      // 6e37: new net/minecraft/client/animation/Keyframe
      // 6e3a: dup
      // 6e3b: ldc 0.92
      // 6e3d: ldc_w -2.568
      // 6e40: ldc_w 0.162
      // 6e43: ldc_w -2.027
      // 6e46: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6e49: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6e4c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6e4f: aastore
      // 6e50: dup
      // 6e51: bipush 23
      // 6e53: new net/minecraft/client/animation/Keyframe
      // 6e56: dup
      // 6e57: ldc 0.96
      // 6e59: ldc_w -1.972
      // 6e5c: ldc_w -0.914
      // 6e5f: ldc_w -1.715
      // 6e62: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6e65: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6e68: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6e6b: aastore
      // 6e6c: dup
      // 6e6d: bipush 24
      // 6e6f: new net/minecraft/client/animation/Keyframe
      // 6e72: dup
      // 6e73: fconst_1
      // 6e74: ldc_w -1.349
      // 6e77: ldc_w -2.141
      // 6e7a: ldc_w -1.256
      // 6e7d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6e80: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6e83: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6e86: aastore
      // 6e87: dup
      // 6e88: bipush 25
      // 6e8a: new net/minecraft/client/animation/Keyframe
      // 6e8d: dup
      // 6e8e: ldc 1.04
      // 6e90: ldc_w -0.778
      // 6e93: ldc_w -3.43
      // 6e96: ldc_w -0.671
      // 6e99: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6e9c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6e9f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6ea2: aastore
      // 6ea3: dup
      // 6ea4: bipush 26
      // 6ea6: new net/minecraft/client/animation/Keyframe
      // 6ea9: dup
      // 6eaa: ldc 1.08
      // 6eac: ldc_w -0.316
      // 6eaf: ldc_w -4.683
      // 6eb2: ldc_w -0.009
      // 6eb5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6eb8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ebb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6ebe: aastore
      // 6ebf: dup
      // 6ec0: bipush 27
      // 6ec2: new net/minecraft/client/animation/Keyframe
      // 6ec5: dup
      // 6ec6: ldc 1.12
      // 6ec8: ldc_w 0.015
      // 6ecb: ldc_w -5.805
      // 6ece: ldc_w 0.651
      // 6ed1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6ed4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ed7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6eda: aastore
      // 6edb: dup
      // 6edc: bipush 28
      // 6ede: new net/minecraft/client/animation/Keyframe
      // 6ee1: dup
      // 6ee2: ldc 1.17
      // 6ee4: ldc_w 0.218
      // 6ee7: ldc_w -6.706
      // 6eea: ldc_w 1.216
      // 6eed: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6ef0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ef3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6ef6: aastore
      // 6ef7: dup
      // 6ef8: bipush 29
      // 6efa: new net/minecraft/client/animation/Keyframe
      // 6efd: dup
      // 6efe: ldc 1.21
      // 6f00: ldc_w 0.314
      // 6f03: ldc_w -7.31
      // 6f06: ldc_w 1.584
      // 6f09: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6f0c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6f0f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6f12: aastore
      // 6f13: dup
      // 6f14: bipush 30
      // 6f16: new net/minecraft/client/animation/Keyframe
      // 6f19: dup
      // 6f1a: ldc 1.25
      // 6f1c: ldc_w 0.322
      // 6f1f: ldc_w -7.548
      // 6f22: ldc_w 1.655
      // 6f25: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6f28: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6f2b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6f2e: aastore
      // 6f2f: dup
      // 6f30: bipush 31
      // 6f32: new net/minecraft/client/animation/Keyframe
      // 6f35: dup
      // 6f36: ldc 1.29
      // 6f38: ldc_w 0.166
      // 6f3b: ldc_w -5.217
      // 6f3e: ldc_w 1.563
      // 6f41: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6f44: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6f47: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6f4a: aastore
      // 6f4b: dup
      // 6f4c: bipush 32
      // 6f4e: new net/minecraft/client/animation/Keyframe
      // 6f51: dup
      // 6f52: ldc 1.33
      // 6f54: ldc_w 2.711
      // 6f57: ldc_w -4.446
      // 6f5a: ldc_w 2.162
      // 6f5d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6f60: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6f63: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6f66: aastore
      // 6f67: dup
      // 6f68: bipush 33
      // 6f6a: new net/minecraft/client/animation/Keyframe
      // 6f6d: dup
      // 6f6e: ldc 1.38
      // 6f70: ldc_w 5.408
      // 6f73: ldc_w -4.415
      // 6f76: ldc_w -0.091
      // 6f79: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6f7c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6f7f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6f82: aastore
      // 6f83: dup
      // 6f84: bipush 34
      // 6f86: new net/minecraft/client/animation/Keyframe
      // 6f89: dup
      // 6f8a: ldc 1.42
      // 6f8c: ldc_w 6.249
      // 6f8f: ldc_w -1.346
      // 6f92: ldc_w -1.795
      // 6f95: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6f98: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6f9b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6f9e: aastore
      // 6f9f: dup
      // 6fa0: bipush 35
      // 6fa2: new net/minecraft/client/animation/Keyframe
      // 6fa5: dup
      // 6fa6: ldc 1.46
      // 6fa8: ldc_w 4.175
      // 6fab: ldc_w 0.857
      // 6fae: ldc_w -3.624
      // 6fb1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6fb4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6fb7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6fba: aastore
      // 6fbb: dup
      // 6fbc: bipush 36
      // 6fbe: new net/minecraft/client/animation/Keyframe
      // 6fc1: dup
      // 6fc2: ldc 1.5
      // 6fc4: ldc_w 2.558
      // 6fc7: ldc_w 0.864
      // 6fca: ldc_w -4.512
      // 6fcd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6fd0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6fd3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6fd6: aastore
      // 6fd7: dup
      // 6fd8: bipush 37
      // 6fda: new net/minecraft/client/animation/Keyframe
      // 6fdd: dup
      // 6fde: ldc 1.54
      // 6fe0: ldc_w 2.003
      // 6fe3: ldc_w 0.748
      // 6fe6: ldc_w -4.68
      // 6fe9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6fec: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6fef: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6ff2: aastore
      // 6ff3: dup
      // 6ff4: bipush 38
      // 6ff6: new net/minecraft/client/animation/Keyframe
      // 6ff9: dup
      // 6ffa: ldc 1.58
      // 6ffc: ldc_w 1.897
      // 6fff: ldc_w 0.713
      // 7002: ldc_w -4.735
      // 7005: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7008: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 700b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 700e: aastore
      // 700f: dup
      // 7010: bipush 39
      // 7012: new net/minecraft/client/animation/Keyframe
      // 7015: dup
      // 7016: ldc 1.62
      // 7018: ldc_w 1.786
      // 701b: ldc_w 0.696
      // 701e: ldc_w -5.02
      // 7021: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7024: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7027: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 702a: aastore
      // 702b: dup
      // 702c: bipush 40
      // 702e: new net/minecraft/client/animation/Keyframe
      // 7031: dup
      // 7032: ldc 1.67
      // 7034: ldc_w 1.612
      // 7037: ldc 0.62
      // 7039: ldc_w -5.539
      // 703c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 703f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7042: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7045: aastore
      // 7046: dup
      // 7047: bipush 41
      // 7049: new net/minecraft/client/animation/Keyframe
      // 704c: dup
      // 704d: ldc 1.71
      // 704f: ldc_w 1.514
      // 7052: ldc_w 0.537
      // 7055: ldc_w -5.894
      // 7058: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 705b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 705e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7061: aastore
      // 7062: dup
      // 7063: bipush 42
      // 7065: new net/minecraft/client/animation/Keyframe
      // 7068: dup
      // 7069: ldc 1.75
      // 706b: ldc_w 1.448
      // 706e: ldc_w 0.453
      // 7071: ldc_w -6.17
      // 7074: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7077: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 707a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 707d: aastore
      // 707e: dup
      // 707f: bipush 43
      // 7081: new net/minecraft/client/animation/Keyframe
      // 7084: dup
      // 7085: ldc 1.79
      // 7087: ldc_w 1.368
      // 708a: ldc_w 0.299
      // 708d: ldc_w -6.567
      // 7090: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7093: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7096: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7099: aastore
      // 709a: dup
      // 709b: bipush 44
      // 709d: new net/minecraft/client/animation/Keyframe
      // 70a0: dup
      // 70a1: ldc_w 1.83
      // 70a4: ldc_w 1.26
      // 70a7: ldc_w -0.05
      // 70aa: ldc_w -7.045
      // 70ad: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 70b0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 70b3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 70b6: aastore
      // 70b7: dup
      // 70b8: bipush 45
      // 70ba: new net/minecraft/client/animation/Keyframe
      // 70bd: dup
      // 70be: ldc_w 1.88
      // 70c1: ldc_w 1.194
      // 70c4: ldc_w -0.62
      // 70c7: ldc_w -7.394
      // 70ca: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 70cd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 70d0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 70d3: aastore
      // 70d4: dup
      // 70d5: bipush 46
      // 70d7: new net/minecraft/client/animation/Keyframe
      // 70da: dup
      // 70db: ldc_w 1.92
      // 70de: ldc_w 1.248
      // 70e1: ldc_w -1.352
      // 70e4: ldc_w -7.544
      // 70e7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 70ea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 70ed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 70f0: aastore
      // 70f1: dup
      // 70f2: bipush 47
      // 70f4: new net/minecraft/client/animation/Keyframe
      // 70f7: dup
      // 70f8: ldc_w 1.96
      // 70fb: ldc_w 1.437
      // 70fe: ldc_w -2.165
      // 7101: ldc_w -7.457
      // 7104: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7107: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 710a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 710d: aastore
      // 710e: dup
      // 710f: bipush 48
      // 7111: new net/minecraft/client/animation/Keyframe
      // 7114: dup
      // 7115: fconst_2
      // 7116: ldc_w 1.713
      // 7119: ldc_w -2.982
      // 711c: ldc_w -7.143
      // 711f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7122: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7125: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7128: aastore
      // 7129: dup
      // 712a: bipush 49
      // 712c: new net/minecraft/client/animation/Keyframe
      // 712f: dup
      // 7130: ldc_w 2.04
      // 7133: ldc_w 1.993
      // 7136: ldc_w -3.738
      // 7139: ldc_w -6.661
      // 713c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 713f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7142: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7145: aastore
      // 7146: dup
      // 7147: bipush 50
      // 7149: new net/minecraft/client/animation/Keyframe
      // 714c: dup
      // 714d: ldc_w 2.08
      // 7150: ldc_w 2.172
      // 7153: ldc_w -4.125
      // 7156: ldc_w -6.083
      // 7159: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 715c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 715f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7162: aastore
      // 7163: dup
      // 7164: bipush 51
      // 7166: new net/minecraft/client/animation/Keyframe
      // 7169: dup
      // 716a: ldc_w 2.12
      // 716d: ldc_w 2.186
      // 7170: ldc_w -4.023
      // 7173: ldc_w -5.447
      // 7176: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7179: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 717c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 717f: aastore
      // 7180: dup
      // 7181: bipush 52
      // 7183: new net/minecraft/client/animation/Keyframe
      // 7186: dup
      // 7187: ldc_w 2.17
      // 718a: ldc_w 2.062
      // 718d: ldc_w -3.68
      // 7190: ldc_w -4.726
      // 7193: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7196: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7199: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 719c: aastore
      // 719d: dup
      // 719e: bipush 53
      // 71a0: new net/minecraft/client/animation/Keyframe
      // 71a3: dup
      // 71a4: ldc_w 2.21
      // 71a7: ldc_w 1.827
      // 71aa: ldc_w -3.353
      // 71ad: ldc_w -3.935
      // 71b0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 71b3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 71b6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 71b9: aastore
      // 71ba: dup
      // 71bb: bipush 54
      // 71bd: new net/minecraft/client/animation/Keyframe
      // 71c0: dup
      // 71c1: ldc 2.25
      // 71c3: ldc_w 1.517
      // 71c6: ldc_w -3.221
      // 71c9: ldc_w -3.182
      // 71cc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 71cf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 71d2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 71d5: aastore
      // 71d6: dup
      // 71d7: bipush 55
      // 71d9: new net/minecraft/client/animation/Keyframe
      // 71dc: dup
      // 71dd: ldc 2.29
      // 71df: ldc_w 1.216
      // 71e2: ldc_w -3.189
      // 71e5: ldc_w -2.506
      // 71e8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 71eb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 71ee: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 71f1: aastore
      // 71f2: dup
      // 71f3: bipush 56
      // 71f5: new net/minecraft/client/animation/Keyframe
      // 71f8: dup
      // 71f9: ldc 2.33
      // 71fb: ldc_w 0.985
      // 71fe: ldc_w -3.17
      // 7201: ldc_w -1.91
      // 7204: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7207: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 720a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 720d: aastore
      // 720e: dup
      // 720f: bipush 57
      // 7211: new net/minecraft/client/animation/Keyframe
      // 7214: dup
      // 7215: ldc 2.38
      // 7217: ldc_w 0.848
      // 721a: ldc_w -3.129
      // 721d: ldc_w -1.487
      // 7220: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7223: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7226: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7229: aastore
      // 722a: dup
      // 722b: bipush 58
      // 722d: new net/minecraft/client/animation/Keyframe
      // 7230: dup
      // 7231: ldc 2.42
      // 7233: ldc 0.79
      // 7235: ldc_w -2.853
      // 7238: ldc_w -1.148
      // 723b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 723e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7241: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7244: aastore
      // 7245: dup
      // 7246: bipush 59
      // 7248: new net/minecraft/client/animation/Keyframe
      // 724b: dup
      // 724c: ldc 2.46
      // 724e: ldc_w 0.793
      // 7251: ldc_w -2.317
      // 7254: ldc_w -0.797
      // 7257: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 725a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 725d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7260: aastore
      // 7261: dup
      // 7262: bipush 60
      // 7264: new net/minecraft/client/animation/Keyframe
      // 7267: dup
      // 7268: ldc 2.5
      // 726a: ldc_w 0.844
      // 726d: ldc_w -1.655
      // 7270: ldc_w -0.525
      // 7273: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7276: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7279: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 727c: aastore
      // 727d: dup
      // 727e: bipush 61
      // 7280: new net/minecraft/client/animation/Keyframe
      // 7283: dup
      // 7284: ldc 2.54
      // 7286: ldc_w 0.878
      // 7289: ldc_w -1.018
      // 728c: ldc_w -0.451
      // 728f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7292: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7295: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7298: aastore
      // 7299: dup
      // 729a: bipush 62
      // 729c: new net/minecraft/client/animation/Keyframe
      // 729f: dup
      // 72a0: ldc_w 2.58
      // 72a3: ldc_w 0.886
      // 72a6: ldc_w -0.763
      // 72a9: ldc_w -0.526
      // 72ac: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 72af: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 72b2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 72b5: aastore
      // 72b6: dup
      // 72b7: bipush 63
      // 72b9: new net/minecraft/client/animation/Keyframe
      // 72bc: dup
      // 72bd: ldc_w 2.62
      // 72c0: ldc_w 0.899
      // 72c3: ldc_w -0.856
      // 72c6: ldc_w -0.54
      // 72c9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 72cc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 72cf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 72d2: aastore
      // 72d3: dup
      // 72d4: bipush 64
      // 72d6: new net/minecraft/client/animation/Keyframe
      // 72d9: dup
      // 72da: ldc_w 2.67
      // 72dd: ldc_w 0.897
      // 72e0: ldc_w -1.048
      // 72e3: ldc_w -0.485
      // 72e6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 72e9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 72ec: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 72ef: aastore
      // 72f0: dup
      // 72f1: bipush 65
      // 72f3: new net/minecraft/client/animation/Keyframe
      // 72f6: dup
      // 72f7: ldc_w 2.71
      // 72fa: ldc_w 0.878
      // 72fd: ldc_w -1.13
      // 7300: ldc_w -0.423
      // 7303: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7306: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7309: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 730c: aastore
      // 730d: dup
      // 730e: bipush 66
      // 7310: new net/minecraft/client/animation/Keyframe
      // 7313: dup
      // 7314: ldc_w 2.75
      // 7317: ldc_w 0.87
      // 731a: ldc -1.104
      // 731c: ldc_w -0.411
      // 731f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7322: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7325: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7328: aastore
      // 7329: dup
      // 732a: bipush 67
      // 732c: new net/minecraft/client/animation/Keyframe
      // 732f: dup
      // 7330: ldc_w 2.79
      // 7333: ldc_w 0.874
      // 7336: ldc_w -1.048
      // 7339: ldc_w -0.434
      // 733c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 733f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7342: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7345: aastore
      // 7346: dup
      // 7347: bipush 68
      // 7349: new net/minecraft/client/animation/Keyframe
      // 734c: dup
      // 734d: ldc 2.83
      // 734f: ldc_w 0.878
      // 7352: ldc_w -1.018
      // 7355: ldc_w -0.451
      // 7358: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 735b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 735e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7361: aastore
      // 7362: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 7365: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 7368: ldc_w "CTR_leg_right"
      // 736b: new net/minecraft/client/animation/AnimationChannel
      // 736e: dup
      // 736f: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 7372: bipush 68
      // 7374: anewarray 28
      // 7377: dup
      // 7378: bipush 0
      // 7379: new net/minecraft/client/animation/Keyframe
      // 737c: dup
      // 737d: fconst_0
      // 737e: ldc_w -2.789
      // 7381: ldc_w 0.901
      // 7384: ldc_w 2.789
      // 7387: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 738a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 738d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7390: aastore
      // 7391: dup
      // 7392: bipush 1
      // 7393: new net/minecraft/client/animation/Keyframe
      // 7396: dup
      // 7397: ldc 0.08
      // 7399: ldc_w -2.752
      // 739c: ldc_w 0.924
      // 739f: ldc_w 2.752
      // 73a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 73a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 73a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 73ab: aastore
      // 73ac: dup
      // 73ad: bipush 2
      // 73ae: new net/minecraft/client/animation/Keyframe
      // 73b1: dup
      // 73b2: ldc 0.12
      // 73b4: ldc_w -2.683
      // 73b7: ldc_w 0.389
      // 73ba: ldc_w 2.683
      // 73bd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 73c0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 73c3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 73c6: aastore
      // 73c7: dup
      // 73c8: bipush 3
      // 73c9: new net/minecraft/client/animation/Keyframe
      // 73cc: dup
      // 73cd: ldc 0.17
      // 73cf: ldc_w -2.471
      // 73d2: ldc_w -1.17
      // 73d5: ldc_w 2.471
      // 73d8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 73db: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 73de: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 73e1: aastore
      // 73e2: dup
      // 73e3: bipush 4
      // 73e4: new net/minecraft/client/animation/Keyframe
      // 73e7: dup
      // 73e8: ldc 0.21
      // 73ea: ldc_w -1.876
      // 73ed: ldc_w -4.387
      // 73f0: ldc_w 1.876
      // 73f3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 73f6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 73f9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 73fc: aastore
      // 73fd: dup
      // 73fe: bipush 5
      // 73ff: new net/minecraft/client/animation/Keyframe
      // 7402: dup
      // 7403: ldc 0.25
      // 7405: ldc_w 0.658
      // 7408: ldc_w 1.783
      // 740b: ldc_w -0.658
      // 740e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7411: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7414: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7417: aastore
      // 7418: dup
      // 7419: bipush 6
      // 741b: new net/minecraft/client/animation/Keyframe
      // 741e: dup
      // 741f: ldc 0.29
      // 7421: ldc_w 3.344
      // 7424: ldc_w 19.86
      // 7427: ldc_w -3.344
      // 742a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 742d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7430: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7433: aastore
      // 7434: dup
      // 7435: bipush 7
      // 7437: new net/minecraft/client/animation/Keyframe
      // 743a: dup
      // 743b: ldc 0.33
      // 743d: ldc_w 3.802
      // 7440: ldc_w 34.447
      // 7443: ldc_w -3.802
      // 7446: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7449: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 744c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 744f: aastore
      // 7450: dup
      // 7451: bipush 8
      // 7453: new net/minecraft/client/animation/Keyframe
      // 7456: dup
      // 7457: ldc 0.38
      // 7459: ldc_w 3.438
      // 745c: ldc_w 37.736
      // 745f: ldc_w -3.438
      // 7462: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7465: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7468: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 746b: aastore
      // 746c: dup
      // 746d: bipush 9
      // 746f: new net/minecraft/client/animation/Keyframe
      // 7472: dup
      // 7473: ldc 0.42
      // 7475: ldc_w 4.038
      // 7478: ldc_w 33.553
      // 747b: ldc_w -4.038
      // 747e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7481: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7484: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7487: aastore
      // 7488: dup
      // 7489: bipush 10
      // 748b: new net/minecraft/client/animation/Keyframe
      // 748e: dup
      // 748f: ldc 0.46
      // 7491: ldc_w 4.637
      // 7494: ldc_w 29.582
      // 7497: ldc_w -4.638
      // 749a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 749d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 74a0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 74a3: aastore
      // 74a4: dup
      // 74a5: bipush 11
      // 74a7: new net/minecraft/client/animation/Keyframe
      // 74aa: dup
      // 74ab: ldc 0.5
      // 74ad: ldc_w 5.071
      // 74b0: ldc_w 26.62
      // 74b3: ldc_w -5.071
      // 74b6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 74b9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 74bc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 74bf: aastore
      // 74c0: dup
      // 74c1: bipush 12
      // 74c3: new net/minecraft/client/animation/Keyframe
      // 74c6: dup
      // 74c7: ldc 0.54
      // 74c9: ldc_w 5.316
      // 74cc: ldc_w 25.202
      // 74cf: ldc_w -5.316
      // 74d2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 74d5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 74d8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 74db: aastore
      // 74dc: dup
      // 74dd: bipush 13
      // 74df: new net/minecraft/client/animation/Keyframe
      // 74e2: dup
      // 74e3: ldc 0.58
      // 74e5: ldc_w 5.265
      // 74e8: ldc_w 25.044
      // 74eb: ldc_w -5.265
      // 74ee: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 74f1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 74f4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 74f7: aastore
      // 74f8: dup
      // 74f9: bipush 14
      // 74fb: new net/minecraft/client/animation/Keyframe
      // 74fe: dup
      // 74ff: ldc 0.62
      // 7501: ldc_w 5.014
      // 7504: ldc_w 25.773
      // 7507: ldc_w -5.014
      // 750a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 750d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7510: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7513: aastore
      // 7514: dup
      // 7515: bipush 15
      // 7517: new net/minecraft/client/animation/Keyframe
      // 751a: dup
      // 751b: ldc 0.67
      // 751d: ldc_w 4.72
      // 7520: ldc_w 26.989
      // 7523: ldc_w -4.72
      // 7526: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7529: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 752c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 752f: aastore
      // 7530: dup
      // 7531: bipush 16
      // 7533: new net/minecraft/client/animation/Keyframe
      // 7536: dup
      // 7537: ldc 0.71
      // 7539: ldc_w 4.52
      // 753c: ldc_w 28.282
      // 753f: ldc_w -4.52
      // 7542: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7545: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7548: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 754b: aastore
      // 754c: dup
      // 754d: bipush 17
      // 754f: new net/minecraft/client/animation/Keyframe
      // 7552: dup
      // 7553: ldc 0.75
      // 7555: ldc_w 4.485
      // 7558: ldc_w 29.263
      // 755b: ldc_w -4.485
      // 755e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7561: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7564: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7567: aastore
      // 7568: dup
      // 7569: bipush 18
      // 756b: new net/minecraft/client/animation/Keyframe
      // 756e: dup
      // 756f: ldc 0.79
      // 7571: ldc_w 4.637
      // 7574: ldc_w 29.582
      // 7577: ldc_w -4.638
      // 757a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 757d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7580: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7583: aastore
      // 7584: dup
      // 7585: bipush 19
      // 7587: new net/minecraft/client/animation/Keyframe
      // 758a: dup
      // 758b: ldc 0.83
      // 758d: ldc_w 4.783
      // 7590: ldc_w 29.249
      // 7593: ldc_w -4.783
      // 7596: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7599: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 759c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 759f: aastore
      // 75a0: dup
      // 75a1: bipush 20
      // 75a3: new net/minecraft/client/animation/Keyframe
      // 75a6: dup
      // 75a7: ldc 0.88
      // 75a9: ldc_w 4.74
      // 75ac: ldc_w 28.544
      // 75af: ldc_w -4.741
      // 75b2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 75b5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 75b8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 75bb: aastore
      // 75bc: dup
      // 75bd: bipush 21
      // 75bf: new net/minecraft/client/animation/Keyframe
      // 75c2: dup
      // 75c3: ldc 0.92
      // 75c5: ldc_w 4.549
      // 75c8: ldc_w 27.546
      // 75cb: ldc_w -4.549
      // 75ce: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 75d1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 75d4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 75d7: aastore
      // 75d8: dup
      // 75d9: bipush 22
      // 75db: new net/minecraft/client/animation/Keyframe
      // 75de: dup
      // 75df: ldc 0.96
      // 75e1: ldc_w 3.188
      // 75e4: ldc_w 27.001
      // 75e7: ldc_w -3.188
      // 75ea: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 75ed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 75f0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 75f3: aastore
      // 75f4: dup
      // 75f5: bipush 23
      // 75f7: new net/minecraft/client/animation/Keyframe
      // 75fa: dup
      // 75fb: fconst_1
      // 75fc: ldc_w 0.09
      // 75ff: ldc_w 26.943
      // 7602: ldc_w -0.09
      // 7605: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7608: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 760b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 760e: aastore
      // 760f: dup
      // 7610: bipush 24
      // 7612: new net/minecraft/client/animation/Keyframe
      // 7615: dup
      // 7616: ldc 1.04
      // 7618: ldc_w -3.937
      // 761b: ldc_w 26.532
      // 761e: ldc_w 3.937
      // 7621: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7624: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7627: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 762a: aastore
      // 762b: dup
      // 762c: bipush 25
      // 762e: new net/minecraft/client/animation/Keyframe
      // 7631: dup
      // 7632: ldc 1.08
      // 7634: ldc_w -8.064
      // 7637: ldc_w 25.327
      // 763a: ldc_w 8.063
      // 763d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7640: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7643: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7646: aastore
      // 7647: dup
      // 7648: bipush 26
      // 764a: new net/minecraft/client/animation/Keyframe
      // 764d: dup
      // 764e: ldc 1.12
      // 7650: ldc_w -11.583
      // 7653: ldc_w 23.358
      // 7656: ldc_w 11.583
      // 7659: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 765c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 765f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7662: aastore
      // 7663: dup
      // 7664: bipush 27
      // 7666: new net/minecraft/client/animation/Keyframe
      // 7669: dup
      // 766a: ldc 1.17
      // 766c: ldc_w -13.997
      // 766f: ldc_w 20.978
      // 7672: ldc_w 13.997
      // 7675: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7678: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 767b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 767e: aastore
      // 767f: dup
      // 7680: bipush 28
      // 7682: new net/minecraft/client/animation/Keyframe
      // 7685: dup
      // 7686: ldc 1.21
      // 7688: ldc_w -14.951
      // 768b: ldc_w 18.638
      // 768e: ldc_w 14.953
      // 7691: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7694: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7697: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 769a: aastore
      // 769b: dup
      // 769c: bipush 29
      // 769e: new net/minecraft/client/animation/Keyframe
      // 76a1: dup
      // 76a2: ldc 1.25
      // 76a4: ldc_w -14.966
      // 76a7: ldc_w 16.732
      // 76aa: ldc_w 14.966
      // 76ad: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 76b0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 76b3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 76b6: aastore
      // 76b7: dup
      // 76b8: bipush 30
      // 76ba: new net/minecraft/client/animation/Keyframe
      // 76bd: dup
      // 76be: ldc 1.29
      // 76c0: ldc_w -16.333
      // 76c3: ldc_w 42.576
      // 76c6: ldc_w 16.333
      // 76c9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 76cc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 76cf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 76d2: aastore
      // 76d3: dup
      // 76d4: bipush 31
      // 76d6: new net/minecraft/client/animation/Keyframe
      // 76d9: dup
      // 76da: ldc 1.33
      // 76dc: ldc_w 1.412
      // 76df: ldc_w 79.898
      // 76e2: ldc_w -1.412
      // 76e5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 76e8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 76eb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 76ee: aastore
      // 76ef: dup
      // 76f0: bipush 32
      // 76f2: new net/minecraft/client/animation/Keyframe
      // 76f5: dup
      // 76f6: ldc 1.38
      // 76f8: ldc_w 26.507
      // 76fb: ldc_w 67.471
      // 76fe: ldc_w -26.507
      // 7701: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7704: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7707: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 770a: aastore
      // 770b: dup
      // 770c: bipush 33
      // 770e: new net/minecraft/client/animation/Keyframe
      // 7711: dup
      // 7712: ldc 1.42
      // 7714: ldc_w 31.216
      // 7717: ldc_w 51.432
      // 771a: ldc_w -31.216
      // 771d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7720: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7723: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7726: aastore
      // 7727: dup
      // 7728: bipush 34
      // 772a: new net/minecraft/client/animation/Keyframe
      // 772d: dup
      // 772e: ldc 1.46
      // 7730: ldc_w 24.583
      // 7733: ldc_w 53.039
      // 7736: ldc_w -24.583
      // 7739: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 773c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 773f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7742: aastore
      // 7743: dup
      // 7744: bipush 35
      // 7746: new net/minecraft/client/animation/Keyframe
      // 7749: dup
      // 774a: ldc 1.5
      // 774c: ldc_w 10.327
      // 774f: ldc_w 54.58
      // 7752: ldc_w -10.327
      // 7755: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7758: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 775b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 775e: aastore
      // 775f: dup
      // 7760: bipush 36
      // 7762: new net/minecraft/client/animation/Keyframe
      // 7765: dup
      // 7766: ldc 1.54
      // 7768: ldc_w 1.424
      // 776b: ldc_w 53.267
      // 776e: ldc_w -1.424
      // 7771: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7774: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7777: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 777a: aastore
      // 777b: dup
      // 777c: bipush 37
      // 777e: new net/minecraft/client/animation/Keyframe
      // 7781: dup
      // 7782: ldc 1.58
      // 7784: ldc_w 1.713
      // 7787: ldc_w 53.607
      // 778a: ldc_w -1.712
      // 778d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7790: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7793: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7796: aastore
      // 7797: dup
      // 7798: bipush 38
      // 779a: new net/minecraft/client/animation/Keyframe
      // 779d: dup
      // 779e: ldc 1.62
      // 77a0: ldc_w 1.398
      // 77a3: ldc_w 52.754
      // 77a6: ldc_w -1.398
      // 77a9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 77ac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 77af: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 77b2: aastore
      // 77b3: dup
      // 77b4: bipush 39
      // 77b6: new net/minecraft/client/animation/Keyframe
      // 77b9: dup
      // 77ba: ldc 1.67
      // 77bc: ldc_w 0.855
      // 77bf: ldc_w 51.141
      // 77c2: ldc_w -0.855
      // 77c5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 77c8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 77cb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 77ce: aastore
      // 77cf: dup
      // 77d0: bipush 40
      // 77d2: new net/minecraft/client/animation/Keyframe
      // 77d5: dup
      // 77d6: ldc 1.71
      // 77d8: ldc_w 0.503
      // 77db: ldc_w 49.982
      // 77de: ldc_w -0.503
      // 77e1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 77e4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 77e7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 77ea: aastore
      // 77eb: dup
      // 77ec: bipush 41
      // 77ee: new net/minecraft/client/animation/Keyframe
      // 77f1: dup
      // 77f2: ldc 1.75
      // 77f4: ldc_w 0.248
      // 77f7: ldc_w 49.056
      // 77fa: ldc_w -0.248
      // 77fd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7800: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7803: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7806: aastore
      // 7807: dup
      // 7808: bipush 42
      // 780a: new net/minecraft/client/animation/Keyframe
      // 780d: dup
      // 780e: ldc 1.79
      // 7810: ldc_w -0.09
      // 7813: ldc_w 47.674
      // 7816: ldc_w 0.09
      // 7819: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 781c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 781f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7822: aastore
      // 7823: dup
      // 7824: bipush 43
      // 7826: new net/minecraft/client/animation/Keyframe
      // 7829: dup
      // 782a: ldc_w 1.83
      // 782d: ldc_w -0.563
      // 7830: ldc_w 44.406
      // 7833: ldc_w 0.563
      // 7836: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7839: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 783c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 783f: aastore
      // 7840: dup
      // 7841: bipush 44
      // 7843: new net/minecraft/client/animation/Keyframe
      // 7846: dup
      // 7847: ldc_w 1.88
      // 784a: ldc_w -1.09
      // 784d: ldc_w 38.482
      // 7850: ldc_w 1.09
      // 7853: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7856: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7859: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 785c: aastore
      // 785d: dup
      // 785e: bipush 45
      // 7860: new net/minecraft/client/animation/Keyframe
      // 7863: dup
      // 7864: ldc_w 1.92
      // 7867: ldc_w -1.576
      // 786a: ldc_w 30.217
      // 786d: ldc_w 1.576
      // 7870: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7873: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7876: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7879: aastore
      // 787a: dup
      // 787b: bipush 46
      // 787d: new net/minecraft/client/animation/Keyframe
      // 7880: dup
      // 7881: ldc_w 1.96
      // 7884: ldc_w -1.872
      // 7887: ldc_w 20.065
      // 788a: ldc_w 1.872
      // 788d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7890: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7893: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7896: aastore
      // 7897: dup
      // 7898: bipush 47
      // 789a: new net/minecraft/client/animation/Keyframe
      // 789d: dup
      // 789e: fconst_2
      // 789f: ldc_w -1.849
      // 78a2: ldc_w 8.649
      // 78a5: ldc_w 1.849
      // 78a8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 78ab: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 78ae: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 78b1: aastore
      // 78b2: dup
      // 78b3: bipush 48
      // 78b5: new net/minecraft/client/animation/Keyframe
      // 78b8: dup
      // 78b9: ldc_w 2.04
      // 78bc: ldc_w 0.129
      // 78bf: ldc_w 6.004
      // 78c2: ldc_w -0.129
      // 78c5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 78c8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 78cb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 78ce: aastore
      // 78cf: dup
      // 78d0: bipush 49
      // 78d2: new net/minecraft/client/animation/Keyframe
      // 78d5: dup
      // 78d6: ldc_w 2.08
      // 78d9: ldc_w 2.692
      // 78dc: ldc_w 16.692
      // 78df: ldc_w -2.692
      // 78e2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 78e5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 78e8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 78eb: aastore
      // 78ec: dup
      // 78ed: bipush 50
      // 78ef: new net/minecraft/client/animation/Keyframe
      // 78f2: dup
      // 78f3: ldc_w 2.12
      // 78f6: ldc_w 3.022
      // 78f9: ldc_w 28.999
      // 78fc: ldc_w -3.022
      // 78ff: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7902: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7905: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7908: aastore
      // 7909: dup
      // 790a: bipush 51
      // 790c: new net/minecraft/client/animation/Keyframe
      // 790f: dup
      // 7910: ldc_w 2.17
      // 7913: ldc_w 1.546
      // 7916: ldc_w 31.522
      // 7919: ldc_w -1.546
      // 791c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 791f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7922: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7925: aastore
      // 7926: dup
      // 7927: bipush 52
      // 7929: new net/minecraft/client/animation/Keyframe
      // 792c: dup
      // 792d: ldc_w 2.21
      // 7930: ldc_w -0.199
      // 7933: ldc_w 24.009
      // 7936: ldc_w 0.199
      // 7939: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 793c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 793f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7942: aastore
      // 7943: dup
      // 7944: bipush 53
      // 7946: new net/minecraft/client/animation/Keyframe
      // 7949: dup
      // 794a: ldc 2.25
      // 794c: ldc_w -1.305
      // 794f: ldc_w 11.785
      // 7952: ldc_w 1.305
      // 7955: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7958: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 795b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 795e: aastore
      // 795f: dup
      // 7960: bipush 54
      // 7962: new net/minecraft/client/animation/Keyframe
      // 7965: dup
      // 7966: ldc 2.29
      // 7968: ldc_w -1.663
      // 796b: ldc 1.12
      // 796d: ldc_w 1.663
      // 7970: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7973: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7976: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7979: aastore
      // 797a: dup
      // 797b: bipush 55
      // 797d: new net/minecraft/client/animation/Keyframe
      // 7980: dup
      // 7981: ldc 2.33
      // 7983: ldc_w -1.547
      // 7986: ldc_w -6.278
      // 7989: ldc_w 1.546
      // 798c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 798f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7992: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7995: aastore
      // 7996: dup
      // 7997: bipush 56
      // 7999: new net/minecraft/client/animation/Keyframe
      // 799c: dup
      // 799d: ldc 2.38
      // 799f: ldc_w -1.342
      // 79a2: ldc_w -9.086
      // 79a5: ldc_w 1.342
      // 79a8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 79ab: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 79ae: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 79b1: aastore
      // 79b2: dup
      // 79b3: bipush 57
      // 79b5: new net/minecraft/client/animation/Keyframe
      // 79b8: dup
      // 79b9: ldc 2.42
      // 79bb: ldc_w -1.453
      // 79be: ldc_w -8.169
      // 79c1: ldc_w 1.453
      // 79c4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 79c7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 79ca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 79cd: aastore
      // 79ce: dup
      // 79cf: bipush 58
      // 79d1: new net/minecraft/client/animation/Keyframe
      // 79d4: dup
      // 79d5: ldc 2.46
      // 79d7: ldc_w -1.893
      // 79da: ldc_w -5.64
      // 79dd: ldc_w 1.893
      // 79e0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 79e3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 79e6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 79e9: aastore
      // 79ea: dup
      // 79eb: bipush 59
      // 79ed: new net/minecraft/client/animation/Keyframe
      // 79f0: dup
      // 79f1: ldc 2.5
      // 79f3: ldc_w -2.42
      // 79f6: ldc_w -2.301
      // 79f9: ldc 2.42
      // 79fb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 79fe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7a01: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7a04: aastore
      // 7a05: dup
      // 7a06: bipush 60
      // 7a08: new net/minecraft/client/animation/Keyframe
      // 7a0b: dup
      // 7a0c: ldc 2.54
      // 7a0e: ldc_w -2.789
      // 7a11: ldc_w 0.901
      // 7a14: ldc_w 2.789
      // 7a17: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7a1a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7a1d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7a20: aastore
      // 7a21: dup
      // 7a22: bipush 61
      // 7a24: new net/minecraft/client/animation/Keyframe
      // 7a27: dup
      // 7a28: ldc_w 2.58
      // 7a2b: ldc_w -2.919
      // 7a2e: ldc_w 2.466
      // 7a31: ldc_w 2.919
      // 7a34: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7a37: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7a3a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7a3d: aastore
      // 7a3e: dup
      // 7a3f: bipush 62
      // 7a41: new net/minecraft/client/animation/Keyframe
      // 7a44: dup
      // 7a45: ldc_w 2.62
      // 7a48: ldc_w -2.928
      // 7a4b: ldc_w 2.234
      // 7a4e: ldc_w 2.928
      // 7a51: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7a54: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7a57: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7a5a: aastore
      // 7a5b: dup
      // 7a5c: bipush 63
      // 7a5e: new net/minecraft/client/animation/Keyframe
      // 7a61: dup
      // 7a62: ldc_w 2.67
      // 7a65: ldc_w -2.849
      // 7a68: ldc_w 1.075
      // 7a6b: ldc 2.849
      // 7a6d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7a70: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7a73: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7a76: aastore
      // 7a77: dup
      // 7a78: bipush 64
      // 7a7a: new net/minecraft/client/animation/Keyframe
      // 7a7d: dup
      // 7a7e: ldc_w 2.71
      // 7a81: ldc_w -2.754
      // 7a84: ldc_w 0.249
      // 7a87: ldc_w 2.754
      // 7a8a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7a8d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7a90: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7a93: aastore
      // 7a94: dup
      // 7a95: bipush 65
      // 7a97: new net/minecraft/client/animation/Keyframe
      // 7a9a: dup
      // 7a9b: ldc_w 2.75
      // 7a9e: ldc_w -2.73
      // 7aa1: ldc_w 0.254
      // 7aa4: ldc_w 2.73
      // 7aa7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7aaa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7aad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7ab0: aastore
      // 7ab1: dup
      // 7ab2: bipush 66
      // 7ab4: new net/minecraft/client/animation/Keyframe
      // 7ab7: dup
      // 7ab8: ldc_w 2.79
      // 7abb: ldc_w -2.763
      // 7abe: ldc_w 0.65
      // 7ac1: ldc_w 2.763
      // 7ac4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7ac7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7aca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7acd: aastore
      // 7ace: dup
      // 7acf: bipush 67
      // 7ad1: new net/minecraft/client/animation/Keyframe
      // 7ad4: dup
      // 7ad5: ldc 2.83
      // 7ad7: ldc_w -2.789
      // 7ada: ldc_w 0.901
      // 7add: ldc_w 2.789
      // 7ae0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7ae3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7ae6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7ae9: aastore
      // 7aea: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 7aed: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 7af0: ldc_w "CTR_leg_right"
      // 7af3: new net/minecraft/client/animation/AnimationChannel
      // 7af6: dup
      // 7af7: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 7afa: bipush 68
      // 7afc: anewarray 28
      // 7aff: dup
      // 7b00: bipush 0
      // 7b01: new net/minecraft/client/animation/Keyframe
      // 7b04: dup
      // 7b05: fconst_0
      // 7b06: ldc_w 0.698
      // 7b09: ldc_w 0.987
      // 7b0c: ldc_w 0.254
      // 7b0f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7b12: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7b15: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7b18: aastore
      // 7b19: dup
      // 7b1a: bipush 1
      // 7b1b: new net/minecraft/client/animation/Keyframe
      // 7b1e: dup
      // 7b1f: ldc 0.04
      // 7b21: ldc_w 0.673
      // 7b24: ldc_w 1.07
      // 7b27: ldc_w 0.072
      // 7b2a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7b2d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7b30: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7b33: aastore
      // 7b34: dup
      // 7b35: bipush 2
      // 7b36: new net/minecraft/client/animation/Keyframe
      // 7b39: dup
      // 7b3a: ldc 0.08
      // 7b3c: ldc_w 0.603
      // 7b3f: ldc_w 1.297
      // 7b42: ldc_w -0.403
      // 7b45: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7b48: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7b4b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7b4e: aastore
      // 7b4f: dup
      // 7b50: bipush 3
      // 7b51: new net/minecraft/client/animation/Keyframe
      // 7b54: dup
      // 7b55: ldc 0.12
      // 7b57: ldc_w 0.491
      // 7b5a: ldc_w 1.625
      // 7b5d: ldc_w -1.046
      // 7b60: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7b63: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7b66: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7b69: aastore
      // 7b6a: dup
      // 7b6b: bipush 4
      // 7b6c: new net/minecraft/client/animation/Keyframe
      // 7b6f: dup
      // 7b70: ldc 0.17
      // 7b72: ldc_w 0.306
      // 7b75: ldc_w 1.981
      // 7b78: ldc_w -1.702
      // 7b7b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7b7e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7b81: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7b84: aastore
      // 7b85: dup
      // 7b86: bipush 5
      // 7b87: new net/minecraft/client/animation/Keyframe
      // 7b8a: dup
      // 7b8b: ldc 0.21
      // 7b8d: ldc_w -0.044
      // 7b90: ldc_w 2.216
      // 7b93: ldc_w -2.187
      // 7b96: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7b99: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7b9c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7b9f: aastore
      // 7ba0: dup
      // 7ba1: bipush 6
      // 7ba3: new net/minecraft/client/animation/Keyframe
      // 7ba6: dup
      // 7ba7: ldc 0.25
      // 7ba9: ldc_w -1.089
      // 7bac: ldc_w 2.737
      // 7baf: ldc_w -4.287
      // 7bb2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7bb5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7bb8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7bbb: aastore
      // 7bbc: dup
      // 7bbd: bipush 7
      // 7bbf: new net/minecraft/client/animation/Keyframe
      // 7bc2: dup
      // 7bc3: ldc 0.29
      // 7bc5: ldc_w -2.224
      // 7bc8: ldc_w 1.478
      // 7bcb: ldc_w -8.603
      // 7bce: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7bd1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7bd4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7bd7: aastore
      // 7bd8: dup
      // 7bd9: bipush 8
      // 7bdb: new net/minecraft/client/animation/Keyframe
      // 7bde: dup
      // 7bdf: ldc 0.33
      // 7be1: ldc_w -2.438
      // 7be4: ldc_w -1.849
      // 7be7: ldc_w -11.137
      // 7bea: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7bed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7bf0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7bf3: aastore
      // 7bf4: dup
      // 7bf5: bipush 9
      // 7bf7: new net/minecraft/client/animation/Keyframe
      // 7bfa: dup
      // 7bfb: ldc 0.38
      // 7bfd: ldc_w -2.555
      // 7c00: ldc_w -2.403
      // 7c03: ldc_w -12.298
      // 7c06: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7c09: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7c0c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7c0f: aastore
      // 7c10: dup
      // 7c11: bipush 10
      // 7c13: new net/minecraft/client/animation/Keyframe
      // 7c16: dup
      // 7c17: ldc 0.42
      // 7c19: ldc_w -2.941
      // 7c1c: ldc_w -0.47
      // 7c1f: ldc_w -13.039
      // 7c22: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7c25: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7c28: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7c2b: aastore
      // 7c2c: dup
      // 7c2d: bipush 11
      // 7c2f: new net/minecraft/client/animation/Keyframe
      // 7c32: dup
      // 7c33: ldc 0.46
      // 7c35: ldc_w -3.277
      // 7c38: ldc_w 0.999
      // 7c3b: ldc_w -13.014
      // 7c3e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7c41: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7c44: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7c47: aastore
      // 7c48: dup
      // 7c49: bipush 12
      // 7c4b: new net/minecraft/client/animation/Keyframe
      // 7c4e: dup
      // 7c4f: ldc 0.5
      // 7c51: ldc_w -3.503
      // 7c54: ldc_w 1.901
      // 7c57: ldc_w -12.663
      // 7c5a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7c5d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7c60: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7c63: aastore
      // 7c64: dup
      // 7c65: bipush 13
      // 7c67: new net/minecraft/client/animation/Keyframe
      // 7c6a: dup
      // 7c6b: ldc 0.54
      // 7c6d: ldc_w -3.63
      // 7c70: ldc_w 2.295
      // 7c73: ldc_w -12.46
      // 7c76: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7c79: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7c7c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7c7f: aastore
      // 7c80: dup
      // 7c81: bipush 14
      // 7c83: new net/minecraft/client/animation/Keyframe
      // 7c86: dup
      // 7c87: ldc 0.58
      // 7c89: ldc_w -3.622
      // 7c8c: ldc_w 2.388
      // 7c8f: ldc_w -12.442
      // 7c92: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7c95: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7c98: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7c9b: aastore
      // 7c9c: dup
      // 7c9d: bipush 15
      // 7c9f: new net/minecraft/client/animation/Keyframe
      // 7ca2: dup
      // 7ca3: ldc 0.62
      // 7ca5: ldc_w -3.519
      // 7ca8: ldc_w 2.268
      // 7cab: ldc_w -12.559
      // 7cae: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7cb1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7cb4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7cb7: aastore
      // 7cb8: dup
      // 7cb9: bipush 16
      // 7cbb: new net/minecraft/client/animation/Keyframe
      // 7cbe: dup
      // 7cbf: ldc 0.67
      // 7cc1: ldc_w -3.389
      // 7cc4: ldc_w 1.982
      // 7cc7: ldc_w -12.738
      // 7cca: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7ccd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7cd0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7cd3: aastore
      // 7cd4: dup
      // 7cd5: bipush 17
      // 7cd7: new net/minecraft/client/animation/Keyframe
      // 7cda: dup
      // 7cdb: ldc 0.71
      // 7cdd: ldc_w -3.289
      // 7ce0: ldc_w 1.599
      // 7ce3: ldc_w -12.903
      // 7ce6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7ce9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7cec: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7cef: aastore
      // 7cf0: dup
      // 7cf1: bipush 18
      // 7cf3: new net/minecraft/client/animation/Keyframe
      // 7cf6: dup
      // 7cf7: ldc 0.75
      // 7cf9: ldc_w -3.247
      // 7cfc: ldc_w 1.228
      // 7cff: ldc_w -13.003
      // 7d02: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7d05: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7d08: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7d0b: aastore
      // 7d0c: dup
      // 7d0d: bipush 19
      // 7d0f: new net/minecraft/client/animation/Keyframe
      // 7d12: dup
      // 7d13: ldc 0.79
      // 7d15: ldc_w -3.277
      // 7d18: ldc_w 0.999
      // 7d1b: ldc_w -13.014
      // 7d1e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7d21: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7d24: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7d27: aastore
      // 7d28: dup
      // 7d29: bipush 20
      // 7d2b: new net/minecraft/client/animation/Keyframe
      // 7d2e: dup
      // 7d2f: ldc 0.83
      // 7d31: ldc_w -3.305
      // 7d34: ldc_w 0.923
      // 7d37: ldc_w -12.924
      // 7d3a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7d3d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7d40: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7d43: aastore
      // 7d44: dup
      // 7d45: bipush 21
      // 7d47: new net/minecraft/client/animation/Keyframe
      // 7d4a: dup
      // 7d4b: ldc 0.88
      // 7d4d: ldc_w -3.263
      // 7d50: ldc_w 0.906
      // 7d53: ldc_w -12.73
      // 7d56: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7d59: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7d5c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7d5f: aastore
      // 7d60: dup
      // 7d61: bipush 22
      // 7d63: new net/minecraft/client/animation/Keyframe
      // 7d66: dup
      // 7d67: ldc 0.92
      // 7d69: ldc_w -3.164
      // 7d6c: ldc_w 0.912
      // 7d6f: ldc_w -12.429
      // 7d72: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7d75: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7d78: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7d7b: aastore
      // 7d7c: dup
      // 7d7d: bipush 23
      // 7d7f: new net/minecraft/client/animation/Keyframe
      // 7d82: dup
      // 7d83: ldc 0.96
      // 7d85: ldc_w -2.695
      // 7d88: ldc_w 0.743
      // 7d8b: ldc_w -12.095
      // 7d8e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7d91: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7d94: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7d97: aastore
      // 7d98: dup
      // 7d99: bipush 24
      // 7d9b: new net/minecraft/client/animation/Keyframe
      // 7d9e: dup
      // 7d9f: fconst_1
      // 7da0: ldc_w -1.77
      // 7da3: ldc_w 0.207
      // 7da6: ldc_w -11.699
      // 7da9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7dac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7daf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7db2: aastore
      // 7db3: dup
      // 7db4: bipush 25
      // 7db6: new net/minecraft/client/animation/Keyframe
      // 7db9: dup
      // 7dba: ldc 1.04
      // 7dbc: ldc_w -0.819
      // 7dbf: ldc_w -0.786
      // 7dc2: ldc_w -11.107
      // 7dc5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7dc8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7dcb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7dce: aastore
      // 7dcf: dup
      // 7dd0: bipush 26
      // 7dd2: new net/minecraft/client/animation/Keyframe
      // 7dd5: dup
      // 7dd6: ldc 1.08
      // 7dd8: ldc_w -0.248
      // 7ddb: ldc_w -2.155
      // 7dde: ldc_w -10.295
      // 7de1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7de4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7de7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7dea: aastore
      // 7deb: dup
      // 7dec: bipush 27
      // 7dee: new net/minecraft/client/animation/Keyframe
      // 7df1: dup
      // 7df2: ldc 1.12
      // 7df4: ldc_w -0.206
      // 7df7: ldc_w -3.585
      // 7dfa: ldc_w -9.344
      // 7dfd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7e00: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7e03: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7e06: aastore
      // 7e07: dup
      // 7e08: bipush 28
      // 7e0a: new net/minecraft/client/animation/Keyframe
      // 7e0d: dup
      // 7e0e: ldc 1.17
      // 7e10: ldc_w -0.494
      // 7e13: ldc_w -4.696
      // 7e16: ldc_w -8.339
      // 7e19: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7e1c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7e1f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7e22: aastore
      // 7e23: dup
      // 7e24: bipush 29
      // 7e26: new net/minecraft/client/animation/Keyframe
      // 7e29: dup
      // 7e2a: ldc 1.21
      // 7e2c: ldc_w -0.716
      // 7e2f: ldc_w -5.175
      // 7e32: ldc_w -7.275
      // 7e35: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7e38: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7e3b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7e3e: aastore
      // 7e3f: dup
      // 7e40: bipush 30
      // 7e42: new net/minecraft/client/animation/Keyframe
      // 7e45: dup
      // 7e46: ldc 1.25
      // 7e48: ldc_w -0.818
      // 7e4b: ldc_w -5.284
      // 7e4e: ldc_w -6.09
      // 7e51: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7e54: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7e57: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7e5a: aastore
      // 7e5b: dup
      // 7e5c: bipush 31
      // 7e5e: new net/minecraft/client/animation/Keyframe
      // 7e61: dup
      // 7e62: ldc 1.29
      // 7e64: ldc_w -2.238
      // 7e67: ldc_w -7.164
      // 7e6a: ldc_w -4.913
      // 7e6d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7e70: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7e73: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7e76: aastore
      // 7e77: dup
      // 7e78: bipush 32
      // 7e7a: new net/minecraft/client/animation/Keyframe
      // 7e7d: dup
      // 7e7e: ldc 1.33
      // 7e80: ldc_w -2.521
      // 7e83: ldc_w -7.136
      // 7e86: ldc_w -3.592
      // 7e89: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7e8c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7e8f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7e92: aastore
      // 7e93: dup
      // 7e94: bipush 33
      // 7e96: new net/minecraft/client/animation/Keyframe
      // 7e99: dup
      // 7e9a: ldc 1.38
      // 7e9c: ldc_w -2.889
      // 7e9f: ldc_w -7.117
      // 7ea2: ldc_w -2.345
      // 7ea5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7ea8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7eab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7eae: aastore
      // 7eaf: dup
      // 7eb0: bipush 34
      // 7eb2: new net/minecraft/client/animation/Keyframe
      // 7eb5: dup
      // 7eb6: ldc 1.42
      // 7eb8: ldc_w -3.258
      // 7ebb: ldc_w -7.107
      // 7ebe: ldc_w -1.297
      // 7ec1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7ec4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7ec7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7eca: aastore
      // 7ecb: dup
      // 7ecc: bipush 35
      // 7ece: new net/minecraft/client/animation/Keyframe
      // 7ed1: dup
      // 7ed2: ldc 1.46
      // 7ed4: ldc_w -3.541
      // 7ed7: ldc_w -7.102
      // 7eda: ldc_w -0.576
      // 7edd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7ee0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7ee3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7ee6: aastore
      // 7ee7: dup
      // 7ee8: bipush 36
      // 7eea: new net/minecraft/client/animation/Keyframe
      // 7eed: dup
      // 7eee: ldc 1.5
      // 7ef0: ldc_w -5.427
      // 7ef3: ldc_w -1.718
      // 7ef6: ldc_w -4.287
      // 7ef9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7efc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7eff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7f02: aastore
      // 7f03: dup
      // 7f04: bipush 37
      // 7f06: new net/minecraft/client/animation/Keyframe
      // 7f09: dup
      // 7f0a: ldc 1.54
      // 7f0c: ldc_w -3.464
      // 7f0f: ldc_w -0.148
      // 7f12: ldc_w -5.03
      // 7f15: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7f18: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7f1b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7f1e: aastore
      // 7f1f: dup
      // 7f20: bipush 38
      // 7f22: new net/minecraft/client/animation/Keyframe
      // 7f25: dup
      // 7f26: ldc 1.58
      // 7f28: ldc_w -3.542
      // 7f2b: ldc_w -0.211
      // 7f2e: ldc_w -5.047
      // 7f31: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7f34: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7f37: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7f3a: aastore
      // 7f3b: dup
      // 7f3c: bipush 39
      // 7f3e: new net/minecraft/client/animation/Keyframe
      // 7f41: dup
      // 7f42: ldc 1.62
      // 7f44: ldc_w -3.453
      // 7f47: ldc_w 0.023
      // 7f4a: ldc_w -5.054
      // 7f4d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7f50: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7f53: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7f56: aastore
      // 7f57: dup
      // 7f58: bipush 40
      // 7f5a: new net/minecraft/client/animation/Keyframe
      // 7f5d: dup
      // 7f5e: ldc 1.67
      // 7f60: ldc_w -3.287
      // 7f63: ldc_w 0.445
      // 7f66: ldc_w -5.036
      // 7f69: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7f6c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7f6f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7f72: aastore
      // 7f73: dup
      // 7f74: bipush 41
      // 7f76: new net/minecraft/client/animation/Keyframe
      // 7f79: dup
      // 7f7a: ldc 1.71
      // 7f7c: ldc_w -3.171
      // 7f7f: ldc_w 0.732
      // 7f82: ldc_w -5.0
      // 7f85: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7f88: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7f8b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7f8e: aastore
      // 7f8f: dup
      // 7f90: bipush 42
      // 7f92: new net/minecraft/client/animation/Keyframe
      // 7f95: dup
      // 7f96: ldc 1.75
      // 7f98: ldc_w -3.082
      // 7f9b: ldc_w 0.953
      // 7f9e: ldc_w -4.959
      // 7fa1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7fa4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7fa7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7faa: aastore
      // 7fab: dup
      // 7fac: bipush 43
      // 7fae: new net/minecraft/client/animation/Keyframe
      // 7fb1: dup
      // 7fb2: ldc 1.79
      // 7fb4: ldc_w -2.958
      // 7fb7: ldc_w 1.268
      // 7fba: ldc_w -4.879
      // 7fbd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7fc0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7fc3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7fc6: aastore
      // 7fc7: dup
      // 7fc8: bipush 44
      // 7fca: new net/minecraft/client/animation/Keyframe
      // 7fcd: dup
      // 7fce: ldc_w 1.83
      // 7fd1: ldc_w -2.768
      // 7fd4: ldc_w 1.81
      // 7fd7: ldc_w -4.538
      // 7fda: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7fdd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7fe0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7fe3: aastore
      // 7fe4: dup
      // 7fe5: bipush 45
      // 7fe7: new net/minecraft/client/animation/Keyframe
      // 7fea: dup
      // 7feb: ldc_w 1.88
      // 7fee: ldc_w -2.508
      // 7ff1: ldc_w 2.485
      // 7ff4: ldc_w -3.707
      // 7ff7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7ffa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7ffd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 8000: aastore
      // 8001: dup
      // 8002: bipush 46
      // 8004: new net/minecraft/client/animation/Keyframe
      // 8007: dup
      // 8008: ldc_w 1.92
      // 800b: ldc_w -2.208
      // 800e: ldc_w 3.027
      // 8011: ldc_w -2.354
      // 8014: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 8017: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 801a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 801d: aastore
      // 801e: dup
      // 801f: bipush 47
      // 8021: new net/minecraft/client/animation/Keyframe
      // 8024: dup
      // 8025: ldc_w 1.96
      // 8028: ldc_w -1.97
      // 802b: ldc_w 3.116
      // 802e: ldc_w -0.602
      // 8031: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 8034: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 8037: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 803a: aastore
      // 803b: dup
      // 803c: bipush 48
      // 803e: new net/minecraft/client/animation/Keyframe
      // 8041: dup
      // 8042: fconst_2
      // 8043: ldc_w -1.883
      // 8046: ldc_w 2.494
      // 8049: ldc_w 1.216
      // 804c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 804f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 8052: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 8055: aastore
      // 8056: dup
      // 8057: bipush 49
      // 8059: new net/minecraft/client/animation/Keyframe
      // 805c: dup
      // 805d: ldc_w 2.04
      // 8060: ldc_w -2.3
      // 8063: ldc_w 2.801
      // 8066: ldc_w 1.595
      // 8069: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 806c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 806f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 8072: aastore
      // 8073: dup
      // 8074: bipush 50
      // 8076: new net/minecraft/client/animation/Keyframe
      // 8079: dup
      // 807a: ldc_w 2.08
      // 807d: ldc_w -2.916
      // 8080: ldc_w 3.364
      // 8083: ldc_w -0.315
      // 8086: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 8089: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 808c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 808f: aastore
      // 8090: dup
      // 8091: bipush 51
      // 8093: new net/minecraft/client/animation/Keyframe
      // 8096: dup
      // 8097: ldc_w 2.12
      // 809a: ldc_w -2.681
      // 809d: ldc_w 0.934
      // 80a0: ldc_w -2.132
      // 80a3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 80a6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 80a9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 80ac: aastore
      // 80ad: dup
      // 80ae: bipush 52
      // 80b0: new net/minecraft/client/animation/Keyframe
      // 80b3: dup
      // 80b4: ldc_w 2.17
      // 80b7: ldc_w -2.107
      // 80ba: ldc_w -3.242
      // 80bd: ldc_w -1.637
      // 80c0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 80c3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 80c6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 80c9: aastore
      // 80ca: dup
      // 80cb: bipush 53
      // 80cd: new net/minecraft/client/animation/Keyframe
      // 80d0: dup
      // 80d1: ldc_w 2.21
      // 80d4: ldc_w -1.741
      // 80d7: ldc_w -4.316
      // 80da: ldc_w -1.261
      // 80dd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 80e0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 80e3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 80e6: aastore
      // 80e7: dup
      // 80e8: bipush 54
      // 80ea: new net/minecraft/client/animation/Keyframe
      // 80ed: dup
      // 80ee: ldc 2.25
      // 80f0: ldc_w -1.204
      // 80f3: ldc_w -2.611
      // 80f6: ldc_w -0.821
      // 80f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 80fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 80ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 8102: aastore
      // 8103: dup
      // 8104: bipush 55
      // 8106: new net/minecraft/client/animation/Keyframe
      // 8109: dup
      // 810a: ldc 2.29
      // 810c: ldc_w -0.8
      // 810f: ldc_w -1.852
      // 8112: ldc_w -0.104
      // 8115: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 8118: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 811b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 811e: aastore
      // 811f: dup
      // 8120: bipush 56
      // 8122: new net/minecraft/client/animation/Keyframe
      // 8125: dup
      // 8126: ldc 2.33
      // 8128: ldc_w -0.548
      // 812b: ldc_w -1.685
      // 812e: ldc_w 0.371
      // 8131: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 8134: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 8137: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 813a: aastore
      // 813b: dup
      // 813c: bipush 57
      // 813e: new net/minecraft/client/animation/Keyframe
      // 8141: dup
      // 8142: ldc 2.38
      // 8144: ldc_w -0.359
      // 8147: ldc_w -1.632
      // 814a: ldc_w 0.464
      // 814d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 8150: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 8153: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 8156: aastore
      // 8157: dup
      // 8158: bipush 58
      // 815a: new net/minecraft/client/animation/Keyframe
      // 815d: dup
      // 815e: ldc 2.42
      // 8160: ldc_w -0.122
      // 8163: ldc_w -1.255
      // 8166: ldc_w 0.439
      // 8169: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 816c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 816f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 8172: aastore
      // 8173: dup
      // 8174: bipush 59
      // 8176: new net/minecraft/client/animation/Keyframe
      // 8179: dup
      // 817a: ldc 2.46
      // 817c: ldc_w 0.18
      // 817f: ldc_w -0.517
      // 8182: ldc_w 0.457
      // 8185: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 8188: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 818b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 818e: aastore
      // 818f: dup
      // 8190: bipush 60
      // 8192: new net/minecraft/client/animation/Keyframe
      // 8195: dup
      // 8196: ldc 2.5
      // 8198: ldc_w 0.484
      // 819b: ldc_w 0.315
      // 819e: ldc_w 0.399
      // 81a1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 81a4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 81a7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 81aa: aastore
      // 81ab: dup
      // 81ac: bipush 61
      // 81ae: new net/minecraft/client/animation/Keyframe
      // 81b1: dup
      // 81b2: ldc 2.54
      // 81b4: ldc_w 0.698
      // 81b7: ldc_w 0.987
      // 81ba: ldc_w 0.254
      // 81bd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 81c0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 81c3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 81c6: aastore
      // 81c7: dup
      // 81c8: bipush 62
      // 81ca: new net/minecraft/client/animation/Keyframe
      // 81cd: dup
      // 81ce: ldc_w 2.58
      // 81d1: ldc_w 0.765
      // 81d4: ldc_w 1.223
      // 81d7: ldc_w 0.14
      // 81da: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 81dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 81e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 81e3: aastore
      // 81e4: dup
      // 81e5: bipush 63
      // 81e7: new net/minecraft/client/animation/Keyframe
      // 81ea: dup
      // 81eb: ldc_w 2.62
      // 81ee: ldc_w 0.755
      // 81f1: ldc_w 1.151
      // 81f4: ldc_w 0.147
      // 81f7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 81fa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 81fd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 8200: aastore
      // 8201: dup
      // 8202: bipush 64
      // 8204: new net/minecraft/client/animation/Keyframe
      // 8207: dup
      // 8208: ldc_w 2.67
      // 820b: ldc_w 0.714
      // 820e: ldc_w 1.005
      // 8211: ldc_w 0.239
      // 8214: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 8217: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 821a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 821d: aastore
      // 821e: dup
      // 821f: bipush 65
      // 8221: new net/minecraft/client/animation/Keyframe
      // 8224: dup
      // 8225: ldc_w 2.71
      // 8228: ldc_w 0.682
      // 822b: ldc_w 0.927
      // 822e: ldc_w 0.312
      // 8231: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 8234: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 8237: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 823a: aastore
      // 823b: dup
      // 823c: bipush 66
      // 823e: new net/minecraft/client/animation/Keyframe
      // 8241: dup
      // 8242: ldc_w 2.75
      // 8245: ldc_w 0.676
      // 8248: ldc_w 0.926
      // 824b: ldc_w 0.31
      // 824e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 8251: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 8254: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 8257: aastore
      // 8258: dup
      // 8259: bipush 67
      // 825b: new net/minecraft/client/animation/Keyframe
      // 825e: dup
      // 825f: ldc 2.83
      // 8261: ldc_w 0.698
      // 8264: ldc_w 0.987
      // 8267: ldc_w 0.254
      // 826a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 826d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 8270: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 8273: aastore
      // 8274: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 8277: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 827a: ldc_w "CTR_leg_right.002"
      // 827d: new net/minecraft/client/animation/AnimationChannel
      // 8280: dup
      // 8281: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 8284: bipush 24
      // 8286: anewarray 28
      // 8289: dup
      // 828a: bipush 0
      // 828b: new net/minecraft/client/animation/Keyframe
      // 828e: dup
      // 828f: ldc 0.21
      // 8291: ldc_w -2.707
      // 8294: fconst_0
      // 8295: ldc_w -2.155
      // 8298: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 829b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 829e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 82a1: aastore
      // 82a2: dup
      // 82a3: bipush 1
      // 82a4: new net/minecraft/client/animation/Keyframe
      // 82a7: dup
      // 82a8: ldc 0.25
      // 82aa: ldc_w -0.428
      // 82ad: fconst_0
      // 82ae: ldc_w 1.498
      // 82b1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 82b4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 82b7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 82ba: aastore
      // 82bb: dup
      // 82bc: bipush 2
      // 82bd: new net/minecraft/client/animation/Keyframe
      // 82c0: dup
      // 82c1: ldc 0.33
      // 82c3: ldc_w 9.598
      // 82c6: fconst_0
      // 82c7: ldc 17.572
      // 82c9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 82cc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 82cf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 82d2: aastore
      // 82d3: dup
      // 82d4: bipush 3
      // 82d5: new net/minecraft/client/animation/Keyframe
      // 82d8: dup
      // 82d9: ldc 0.38
      // 82db: ldc_w 11.877
      // 82de: fconst_0
      // 82df: ldc_w 21.225
      // 82e2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 82e5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 82e8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 82eb: aastore
      // 82ec: dup
      // 82ed: bipush 4
      // 82ee: new net/minecraft/client/animation/Keyframe
      // 82f1: dup
      // 82f2: ldc 0.92
      // 82f4: ldc_w 11.877
      // 82f7: fconst_0
      // 82f8: ldc_w 21.225
      // 82fb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 82fe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 8301: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 8304: aastore
      // 8305: dup
      // 8306: bipush 5
      // 8307: new net/minecraft/client/animation/Keyframe
      // 830a: dup
      // 830b: ldc 0.96
      // 830d: ldc_w 11.877
      // 8310: fconst_0
      // 8311: ldc_w 22.102
      // 8314: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 8317: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 831a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 831d: aastore
      // 831e: dup
      // 831f: bipush 6
      // 8321: new net/minecraft/client/animation/Keyframe
      // 8324: dup
      // 8325: fconst_1
      // 8326: ldc_w 11.877
      // 8329: fconst_0
      // 832a: ldc_w 24.366
      // 832d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 8330: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 8333: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 8336: aastore
      // 8337: dup
      // 8338: bipush 7
      // 833a: new net/minecraft/client/animation/Keyframe
      // 833d: dup
      // 833e: ldc 1.04
      // 8340: ldc_w 11.877
      // 8343: fconst_0
      // 8344: ldc_w 27.461
      // 8347: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 834a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 834d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 8350: aastore
      // 8351: dup
      // 8352: bipush 8
      // 8354: new net/minecraft/client/animation/Keyframe
      // 8357: dup
      // 8358: ldc 1.08
      // 835a: ldc_w 11.877
      // 835d: fconst_0
      // 835e: ldc_w 30.833
      // 8361: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 8364: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 8367: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 836a: aastore
      // 836b: dup
      // 836c: bipush 9
      // 836e: new net/minecraft/client/animation/Keyframe
      // 8371: dup
      // 8372: ldc 1.12
      // 8374: ldc_w 11.877
      // 8377: fconst_0
      // 8378: ldc_w 33.928
      // 837b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 837e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 8381: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 8384: aastore
      // 8385: dup
      // 8386: bipush 10
      // 8388: new net/minecraft/client/animation/Keyframe
      // 838b: dup
      // 838c: ldc 1.17
      // 838e: ldc_w 11.877
      // 8391: fconst_0
      // 8392: ldc_w 36.191
      // 8395: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 8398: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 839b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 839e: aastore
      // 839f: dup
      // 83a0: bipush 11
      // 83a2: new net/minecraft/client/animation/Keyframe
      // 83a5: dup
      // 83a6: ldc 1.21
      // 83a8: ldc_w 11.877
      // 83ab: fconst_0
      // 83ac: ldc_w 37.069
      // 83af: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 83b2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 83b5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 83b8: aastore
      // 83b9: dup
      // 83ba: bipush 12
      // 83bc: new net/minecraft/client/animation/Keyframe
      // 83bf: dup
      // 83c0: ldc 1.33
      // 83c2: ldc_w 11.877
      // 83c5: fconst_0
      // 83c6: ldc_w 37.069
      // 83c9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 83cc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 83cf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 83d2: aastore
      // 83d3: dup
      // 83d4: bipush 13
      // 83d6: new net/minecraft/client/animation/Keyframe
      // 83d9: dup
      // 83da: ldc 1.38
      // 83dc: ldc_w 11.292
      // 83df: fconst_0
      // 83e0: ldc_w 29.134
      // 83e3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 83e6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 83e9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 83ec: aastore
      // 83ed: dup
      // 83ee: bipush 14
      // 83f0: new net/minecraft/client/animation/Keyframe
      // 83f3: dup
      // 83f4: ldc 1.42
      // 83f6: ldc_w 9.899
      // 83f9: fconst_0
      // 83fa: ldc_w 10.212
      // 83fd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 8400: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 8403: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 8406: aastore
      // 8407: dup
      // 8408: bipush 15
      // 840a: new net/minecraft/client/animation/Keyframe
      // 840d: dup
      // 840e: ldc 1.46
      // 8410: ldc_w 8.237
      // 8413: fconst_0
      // 8414: ldc_w -12.373
      // 8417: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 841a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 841d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 8420: aastore
      // 8421: dup
      // 8422: bipush 16
      // 8424: new net/minecraft/client/animation/Keyframe
      // 8427: dup
      // 8428: ldc 1.5
      // 842a: ldc_w 6.843
      // 842d: fconst_0
      // 842e: ldc_w -31.296
      // 8431: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 8434: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 8437: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 843a: aastore
      // 843b: dup
      // 843c: bipush 17
      // 843e: new net/minecraft/client/animation/Keyframe
      // 8441: dup
      // 8442: ldc 1.54
      // 8444: ldc_w 6.259
      // 8447: fconst_0
      // 8448: ldc_w -39.231
      // 844b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 844e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 8451: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 8454: aastore
      // 8455: dup
      // 8456: bipush 18
      // 8458: new net/minecraft/client/animation/Keyframe
      // 845b: dup
      // 845c: fconst_2
      // 845d: ldc_w 6.259
      // 8460: fconst_0
      // 8461: ldc_w -39.231
      // 8464: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 8467: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 846a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 846d: aastore
      // 846e: dup
      // 846f: bipush 19
      // 8471: new net/minecraft/client/animation/Keyframe
      // 8474: dup
      // 8475: ldc_w 2.04
      // 8478: ldc_w 5.254
      // 847b: ldc_w 0.752
      // 847e: ldc_w -35.5
      // 8481: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 8484: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 8487: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 848a: aastore
      // 848b: dup
      // 848c: bipush 20
      // 848e: new net/minecraft/client/animation/Keyframe
      // 8491: dup
      // 8492: ldc_w 2.08
      // 8495: ldc_w 2.907
      // 8498: ldc_w 2.148
      // 849b: ldc_w -26.518
      // 849e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 84a1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 84a4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 84a7: aastore
      // 84a8: dup
      // 84a9: bipush 21
      // 84ab: new net/minecraft/client/animation/Keyframe
      // 84ae: dup
      // 84af: ldc_w 2.12
      // 84b2: ldc_w 0.219
      // 84b5: ldc_w 2.899
      // 84b8: ldc_w -15.602
      // 84bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 84be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 84c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 84c4: aastore
      // 84c5: dup
      // 84c6: bipush 22
      // 84c8: new net/minecraft/client/animation/Keyframe
      // 84cb: dup
      // 84cc: ldc_w 2.17
      // 84cf: ldc_w -1.875
      // 84d2: ldc_w 1.45
      // 84d5: ldc_w -6.184
      // 84d8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 84db: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 84de: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 84e1: aastore
      // 84e2: dup
      // 84e3: bipush 23
      // 84e5: new net/minecraft/client/animation/Keyframe
      // 84e8: dup
      // 84e9: ldc_w 2.21
      // 84ec: ldc_w -2.707
      // 84ef: fconst_0
      // 84f0: ldc_w -2.155
      // 84f3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 84f6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 84f9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 84fc: aastore
      // 84fd: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 8500: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 8503: ldc_w "icespike"
      // 8506: new net/minecraft/client/animation/AnimationChannel
      // 8509: dup
      // 850a: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 850d: bipush 7
      // 850f: anewarray 28
      // 8512: dup
      // 8513: bipush 0
      // 8514: new net/minecraft/client/animation/Keyframe
      // 8517: dup
      // 8518: ldc 0.29
      // 851a: fconst_0
      // 851b: ldc_w -127.5
      // 851e: fconst_0
      // 851f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 8522: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 8525: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 8528: aastore
      // 8529: dup
      // 852a: bipush 1
      // 852b: new net/minecraft/client/animation/Keyframe
      // 852e: dup
      // 852f: ldc 0.42
      // 8531: fconst_0
      // 8532: ldc_w -87.5
      // 8535: fconst_0
      // 8536: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 8539: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 853c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 853f: aastore
      // 8540: dup
      // 8541: bipush 2
      // 8542: new net/minecraft/client/animation/Keyframe
      // 8545: dup
      // 8546: ldc_w 0.49
      // 8549: fconst_0
      // 854a: ldc_w -81.5
      // 854d: fconst_0
      // 854e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 8551: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 8554: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 8557: aastore
      // 8558: dup
      // 8559: bipush 3
      // 855a: new net/minecraft/client/animation/Keyframe
      // 855d: dup
      // 855e: ldc_w 0.64
      // 8561: fconst_0
      // 8562: ldc_w -91.82
      // 8565: fconst_0
      // 8566: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 8569: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 856c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 856f: aastore
      // 8570: dup
      // 8571: bipush 4
      // 8572: new net/minecraft/client/animation/Keyframe
      // 8575: dup
      // 8576: ldc 1.17
      // 8578: fconst_0
      // 8579: ldc_w -87.5
      // 857c: fconst_0
      // 857d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 8580: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 8583: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 8586: aastore
      // 8587: dup
      // 8588: bipush 5
      // 8589: new net/minecraft/client/animation/Keyframe
      // 858c: dup
      // 858d: ldc 1.33
      // 858f: fconst_0
      // 8590: ldc_w -87.5
      // 8593: fconst_0
      // 8594: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 8597: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 859a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 859d: aastore
      // 859e: dup
      // 859f: bipush 6
      // 85a1: new net/minecraft/client/animation/Keyframe
      // 85a4: dup
      // 85a5: ldc_w 1.331
      // 85a8: fconst_0
      // 85a9: ldc_w -105.5
      // 85ac: fconst_0
      // 85ad: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 85b0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 85b3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 85b6: aastore
      // 85b7: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 85ba: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 85bd: ldc_w "icespike"
      // 85c0: new net/minecraft/client/animation/AnimationChannel
      // 85c3: dup
      // 85c4: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 85c7: bipush 4
      // 85c8: anewarray 28
      // 85cb: dup
      // 85cc: bipush 0
      // 85cd: new net/minecraft/client/animation/Keyframe
      // 85d0: dup
      // 85d1: ldc 0.29
      // 85d3: ldc_w -3.4
      // 85d6: fconst_0
      // 85d7: ldc_w 5.1
      // 85da: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 85dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 85e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 85e3: aastore
      // 85e4: dup
      // 85e5: bipush 1
      // 85e6: new net/minecraft/client/animation/Keyframe
      // 85e9: dup
      // 85ea: ldc 0.42
      // 85ec: fconst_0
      // 85ed: fconst_0
      // 85ee: ldc_w 4.2
      // 85f1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 85f4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 85f7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 85fa: aastore
      // 85fb: dup
      // 85fc: bipush 2
      // 85fd: new net/minecraft/client/animation/Keyframe
      // 8600: dup
      // 8601: ldc 1.33
      // 8603: fconst_0
      // 8604: fconst_0
      // 8605: ldc_w 4.2
      // 8608: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 860b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 860e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 8611: aastore
      // 8612: dup
      // 8613: bipush 3
      // 8614: new net/minecraft/client/animation/Keyframe
      // 8617: dup
      // 8618: ldc_w 1.331
      // 861b: ldc_w 14.7
      // 861e: fconst_0
      // 861f: ldc_w 13.2
      // 8622: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 8625: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 8628: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 862b: aastore
      // 862c: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 862f: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 8632: ldc_w "icespike"
      // 8635: new net/minecraft/client/animation/AnimationChannel
      // 8638: dup
      // 8639: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 863c: bipush 6
      // 863e: anewarray 28
      // 8641: dup
      // 8642: bipush 0
      // 8643: new net/minecraft/client/animation/Keyframe
      // 8646: dup
      // 8647: ldc 0.29
      // 8649: ldc2_w 0.5
      // 864c: ldc2_w 0.5
      // 864f: ldc2_w 0.5
      // 8652: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 8655: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 8658: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 865b: aastore
      // 865c: dup
      // 865d: bipush 1
      // 865e: new net/minecraft/client/animation/Keyframe
      // 8661: dup
      // 8662: ldc 0.38
      // 8664: ldc2_w 0.5
      // 8667: ldc2_w 0.5
      // 866a: ldc2_w 0.5
      // 866d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 8670: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 8673: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 8676: aastore
      // 8677: dup
      // 8678: bipush 2
      // 8679: new net/minecraft/client/animation/Keyframe
      // 867c: dup
      // 867d: ldc_w 1.32
      // 8680: ldc2_w 2.0
      // 8683: ldc2_w 2.0
      // 8686: ldc2_w 2.0
      // 8689: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 868c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 868f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 8692: aastore
      // 8693: dup
      // 8694: bipush 3
      // 8695: new net/minecraft/client/animation/Keyframe
      // 8698: dup
      // 8699: ldc_w 1.321
      // 869c: ldc2_w 2.0
      // 869f: ldc2_w 2.0
      // 86a2: ldc2_w 2.0
      // 86a5: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 86a8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 86ab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 86ae: aastore
      // 86af: dup
      // 86b0: bipush 4
      // 86b1: new net/minecraft/client/animation/Keyframe
      // 86b4: dup
      // 86b5: ldc 1.33
      // 86b7: ldc2_w 0.5
      // 86ba: ldc2_w 0.5
      // 86bd: ldc2_w 0.5
      // 86c0: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 86c3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 86c6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 86c9: aastore
      // 86ca: dup
      // 86cb: bipush 5
      // 86cc: new net/minecraft/client/animation/Keyframe
      // 86cf: dup
      // 86d0: ldc_w 1.331
      // 86d3: ldc2_w 0.5
      // 86d6: ldc2_w 0.5
      // 86d9: ldc2_w 0.5
      // 86dc: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 86df: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 86e2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 86e5: aastore
      // 86e6: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 86e9: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 86ec: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.build ()Lnet/minecraft/client/animation/AnimationDefinition;
      // 86ef: putstatic net/unusual/block_factorys_bosses/client/animation/yeti/yetiThrowIcespike.THROW_ICESPIKE Lnet/minecraft/client/animation/AnimationDefinition;
      // 86f2: return
   }
}
