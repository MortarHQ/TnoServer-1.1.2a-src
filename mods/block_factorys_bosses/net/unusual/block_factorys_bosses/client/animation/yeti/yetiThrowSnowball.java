package net.unusual.block_factorys_bosses.client.animation.yeti;

import net.minecraft.client.animation.AnimationDefinition;

public class yetiThrowSnowball {
   public static final AnimationDefinition THROW_SNOWBALL;

   static {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 0000: ldc 2.54
      // 0002: invokestatic net/minecraft/client/animation/AnimationDefinition$Builder.withLength (F)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0005: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.looping ()Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0008: ldc "CTR_leg_left.002"
      // 000a: new net/minecraft/client/animation/AnimationChannel
      // 000d: dup
      // 000e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0011: bipush 15
      // 0013: anewarray 28
      // 0016: dup
      // 0017: bipush 0
      // 0018: new net/minecraft/client/animation/Keyframe
      // 001b: dup
      // 001c: ldc 0.29
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
      // 0033: ldc 0.33
      // 0035: ldc 2.668
      // 0037: fconst_0
      // 0038: ldc -6.772
      // 003a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 003d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0040: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0043: aastore
      // 0044: dup
      // 0045: bipush 2
      // 0046: new net/minecraft/client/animation/Keyframe
      // 0049: dup
      // 004a: ldc 0.38
      // 004c: ldc 3.7
      // 004e: fconst_0
      // 004f: ldc 4.902
      // 0051: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0054: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0057: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 005a: aastore
      // 005b: dup
      // 005c: bipush 3
      // 005d: new net/minecraft/client/animation/Keyframe
      // 0060: dup
      // 0061: ldc 0.42
      // 0063: ldc 4.255
      // 0065: fconst_0
      // 0066: ldc 11.187
      // 0068: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 006b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 006e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0071: aastore
      // 0072: dup
      // 0073: bipush 4
      // 0074: new net/minecraft/client/animation/Keyframe
      // 0077: dup
      // 0078: ldc 0.92
      // 007a: ldc 4.255
      // 007c: fconst_0
      // 007d: ldc 11.187
      // 007f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0082: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0085: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0088: aastore
      // 0089: dup
      // 008a: bipush 5
      // 008b: new net/minecraft/client/animation/Keyframe
      // 008e: dup
      // 008f: fconst_1
      // 0090: ldc 3.685
      // 0092: fconst_0
      // 0093: ldc -36.264
      // 0095: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0098: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 009b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 009e: aastore
      // 009f: dup
      // 00a0: bipush 6
      // 00a2: new net/minecraft/client/animation/Keyframe
      // 00a5: dup
      // 00a6: fconst_2
      // 00a7: ldc 3.685
      // 00a9: fconst_0
      // 00aa: ldc -36.264
      // 00ac: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 00af: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00b2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00b5: aastore
      // 00b6: dup
      // 00b7: bipush 7
      // 00b9: new net/minecraft/client/animation/Keyframe
      // 00bc: dup
      // 00bd: ldc 2.04
      // 00bf: ldc 3.617
      // 00c1: fconst_0
      // 00c2: ldc -35.267
      // 00c4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 00c7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00ca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00cd: aastore
      // 00ce: dup
      // 00cf: bipush 8
      // 00d1: new net/minecraft/client/animation/Keyframe
      // 00d4: dup
      // 00d5: ldc 2.08
      // 00d7: ldc 3.439
      // 00d9: fconst_0
      // 00da: ldc -32.638
      // 00dc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 00df: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00e2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00e5: aastore
      // 00e6: dup
      // 00e7: bipush 9
      // 00e9: new net/minecraft/client/animation/Keyframe
      // 00ec: dup
      // 00ed: ldc 2.12
      // 00ef: ldc 3.187
      // 00f1: fconst_0
      // 00f2: ldc -28.921
      // 00f4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 00f7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00fa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00fd: aastore
      // 00fe: dup
      // 00ff: bipush 10
      // 0101: new net/minecraft/client/animation/Keyframe
      // 0104: dup
      // 0105: ldc 2.17
      // 0107: ldc 2.899
      // 0109: fconst_0
      // 010a: ldc -24.661
      // 010c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 010f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0112: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0115: aastore
      // 0116: dup
      // 0117: bipush 11
      // 0119: new net/minecraft/client/animation/Keyframe
      // 011c: dup
      // 011d: ldc 2.21
      // 011f: ldc 2.61
      // 0121: fconst_0
      // 0122: ldc -20.4
      // 0124: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0127: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 012a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 012d: aastore
      // 012e: dup
      // 012f: bipush 12
      // 0131: new net/minecraft/client/animation/Keyframe
      // 0134: dup
      // 0135: ldc 2.25
      // 0137: ldc 2.359
      // 0139: fconst_0
      // 013a: ldc -16.684
      // 013c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 013f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0142: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0145: aastore
      // 0146: dup
      // 0147: bipush 13
      // 0149: new net/minecraft/client/animation/Keyframe
      // 014c: dup
      // 014d: ldc 2.29
      // 014f: ldc 2.181
      // 0151: fconst_0
      // 0152: ldc -14.055
      // 0154: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0157: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 015a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 015d: aastore
      // 015e: dup
      // 015f: bipush 14
      // 0161: new net/minecraft/client/animation/Keyframe
      // 0164: dup
      // 0165: ldc 2.33
      // 0167: ldc 2.113
      // 0169: fconst_0
      // 016a: ldc -13.058
      // 016c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 016f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0172: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0175: aastore
      // 0176: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0179: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 017c: ldc "CTR_hips"
      // 017e: new net/minecraft/client/animation/AnimationChannel
      // 0181: dup
      // 0182: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0185: bipush 49
      // 0187: anewarray 28
      // 018a: dup
      // 018b: bipush 0
      // 018c: new net/minecraft/client/animation/Keyframe
      // 018f: dup
      // 0190: fconst_0
      // 0191: ldc -0.727
      // 0193: ldc 0.028
      // 0195: ldc -4.943
      // 0197: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 019a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 019d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01a0: aastore
      // 01a1: dup
      // 01a2: bipush 1
      // 01a3: new net/minecraft/client/animation/Keyframe
      // 01a6: dup
      // 01a7: ldc 0.04
      // 01a9: ldc -0.739
      // 01ab: ldc 0.41
      // 01ad: ldc -4.989
      // 01af: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 01b2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01b5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01b8: aastore
      // 01b9: dup
      // 01ba: bipush 2
      // 01bb: new net/minecraft/client/animation/Keyframe
      // 01be: dup
      // 01bf: ldc 0.08
      // 01c1: ldc -0.793
      // 01c3: ldc 1.629
      // 01c5: ldc -5.139
      // 01c7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 01ca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01cd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01d0: aastore
      // 01d1: dup
      // 01d2: bipush 3
      // 01d3: new net/minecraft/client/animation/Keyframe
      // 01d6: dup
      // 01d7: ldc 0.12
      // 01d9: ldc -0.936
      // 01db: ldc 3.795
      // 01dd: ldc -5.411
      // 01df: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 01e2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01e5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01e8: aastore
      // 01e9: dup
      // 01ea: bipush 4
      // 01eb: new net/minecraft/client/animation/Keyframe
      // 01ee: dup
      // 01ef: ldc 0.17
      // 01f1: ldc -1.246
      // 01f3: ldc 6.986
      // 01f5: ldc -5.825
      // 01f7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 01fa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01fd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0200: aastore
      // 0201: dup
      // 0202: bipush 5
      // 0203: new net/minecraft/client/animation/Keyframe
      // 0206: dup
      // 0207: ldc 0.21
      // 0209: ldc -1.846
      // 020b: ldc 11.167
      // 020d: ldc -6.405
      // 020f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0212: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0215: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0218: aastore
      // 0219: dup
      // 021a: bipush 6
      // 021c: new net/minecraft/client/animation/Keyframe
      // 021f: dup
      // 0220: ldc 0.25
      // 0222: ldc -2.89
      // 0224: ldc 16.063
      // 0226: ldc -7.165
      // 0228: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 022b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 022e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0231: aastore
      // 0232: dup
      // 0233: bipush 7
      // 0235: new net/minecraft/client/animation/Keyframe
      // 0238: dup
      // 0239: ldc 0.29
      // 023b: ldc -4.509
      // 023d: ldc 21.04
      // 023f: ldc -8.078
      // 0241: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0244: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0247: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 024a: aastore
      // 024b: dup
      // 024c: bipush 8
      // 024e: new net/minecraft/client/animation/Keyframe
      // 0251: dup
      // 0252: ldc 0.33
      // 0254: ldc -6.701
      // 0256: ldc 25.223
      // 0258: ldc -9.036
      // 025a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 025d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0260: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0263: aastore
      // 0264: dup
      // 0265: bipush 9
      // 0267: new net/minecraft/client/animation/Keyframe
      // 026a: dup
      // 026b: ldc 0.38
      // 026d: ldc -9.278
      // 026f: ldc 27.888
      // 0271: ldc -9.853
      // 0273: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0276: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0279: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 027c: aastore
      // 027d: dup
      // 027e: bipush 10
      // 0280: new net/minecraft/client/animation/Keyframe
      // 0283: dup
      // 0284: ldc 0.42
      // 0286: ldc -11.972
      // 0288: ldc 28.759
      // 028a: ldc -10.357
      // 028c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 028f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0292: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0295: aastore
      // 0296: dup
      // 0297: bipush 11
      // 0299: new net/minecraft/client/animation/Keyframe
      // 029c: dup
      // 029d: ldc 0.46
      // 029f: ldc -15.137
      // 02a1: ldc 28.745
      // 02a3: ldc -10.642
      // 02a5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02a8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02ab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02ae: aastore
      // 02af: dup
      // 02b0: bipush 12
      // 02b2: new net/minecraft/client/animation/Keyframe
      // 02b5: dup
      // 02b6: ldc 0.5
      // 02b8: ldc -18.982
      // 02ba: ldc 28.733
      // 02bc: ldc -10.865
      // 02be: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02c1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02c4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02c7: aastore
      // 02c8: dup
      // 02c9: bipush 13
      // 02cb: new net/minecraft/client/animation/Keyframe
      // 02ce: dup
      // 02cf: ldc 0.58
      // 02d1: ldc -27.165
      // 02d3: ldc 28.72
      // 02d5: ldc -11.124
      // 02d7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02da: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02dd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02e0: aastore
      // 02e1: dup
      // 02e2: bipush 14
      // 02e4: new net/minecraft/client/animation/Keyframe
      // 02e7: dup
      // 02e8: ldc 0.62
      // 02ea: ldc -30.768
      // 02ec: ldc 28.718
      // 02ee: ldc -11.152
      // 02f0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02f3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02f6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02f9: aastore
      // 02fa: dup
      // 02fb: bipush 15
      // 02fd: new net/minecraft/client/animation/Keyframe
      // 0300: dup
      // 0301: ldc 0.67
      // 0303: ldc -33.523
      // 0305: ldc 28.721
      // 0307: ldc -11.104
      // 0309: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 030c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 030f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0312: aastore
      // 0313: dup
      // 0314: bipush 16
      // 0316: new net/minecraft/client/animation/Keyframe
      // 0319: dup
      // 031a: ldc 0.71
      // 031c: ldc -34.966
      // 031e: ldc 28.728
      // 0320: ldc -10.969
      // 0322: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0325: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0328: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 032b: aastore
      // 032c: dup
      // 032d: bipush 17
      // 032f: new net/minecraft/client/animation/Keyframe
      // 0332: dup
      // 0333: ldc 0.75
      // 0335: ldc -34.507
      // 0337: ldc 28.741
      // 0339: ldc -10.728
      // 033b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 033e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0341: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0344: aastore
      // 0345: dup
      // 0346: bipush 18
      // 0348: new net/minecraft/client/animation/Keyframe
      // 034b: dup
      // 034c: ldc 0.83
      // 034e: ldc -32.885
      // 0350: ldc 28.751
      // 0352: ldc -10.511
      // 0354: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0357: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 035a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 035d: aastore
      // 035e: dup
      // 035f: bipush 19
      // 0361: new net/minecraft/client/animation/Keyframe
      // 0364: dup
      // 0365: ldc 0.88
      // 0367: ldc -31.34
      // 0369: ldc 28.759
      // 036b: ldc -10.357
      // 036d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0370: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0373: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0376: aastore
      // 0377: dup
      // 0378: bipush 20
      // 037a: new net/minecraft/client/animation/Keyframe
      // 037d: dup
      // 037e: ldc 0.92
      // 0380: ldc -25.393
      // 0382: ldc 23.923
      // 0384: ldc -9.009
      // 0386: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0389: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 038c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 038f: aastore
      // 0390: dup
      // 0391: bipush 21
      // 0393: new net/minecraft/client/animation/Keyframe
      // 0396: dup
      // 0397: ldc 0.96
      // 0399: ldc -18.996
      // 039b: ldc 12.893
      // 039d: ldc -7.251
      // 039f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03a2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03a5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03a8: aastore
      // 03a9: dup
      // 03aa: bipush 22
      // 03ac: new net/minecraft/client/animation/Keyframe
      // 03af: dup
      // 03b0: fconst_1
      // 03b1: ldc -13.167
      // 03b3: ldc 1.05
      // 03b5: ldc -5.753
      // 03b7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03ba: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03bd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03c0: aastore
      // 03c1: dup
      // 03c2: bipush 23
      // 03c4: new net/minecraft/client/animation/Keyframe
      // 03c7: dup
      // 03c8: ldc 1.04
      // 03ca: ldc -7.937
      // 03cc: ldc -6.176
      // 03ce: ldc -4.499
      // 03d0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03d3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03d6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03d9: aastore
      // 03da: dup
      // 03db: bipush 24
      // 03dd: new net/minecraft/client/animation/Keyframe
      // 03e0: dup
      // 03e1: ldc 1.08
      // 03e3: ldc -4.159
      // 03e5: ldc -7.988
      // 03e7: ldc -3.825
      // 03e9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03ec: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03ef: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03f2: aastore
      // 03f3: dup
      // 03f4: bipush 25
      // 03f6: new net/minecraft/client/animation/Keyframe
      // 03f9: dup
      // 03fa: ldc 1.12
      // 03fc: ldc -1.06
      // 03fe: ldc -8.247
      // 0400: ldc -3.638
      // 0402: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0405: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0408: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 040b: aastore
      // 040c: dup
      // 040d: bipush 26
      // 040f: new net/minecraft/client/animation/Keyframe
      // 0412: dup
      // 0413: ldc 1.17
      // 0415: ldc 1.209
      // 0417: ldc -8.186
      // 0419: ldc -3.693
      // 041b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 041e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0421: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0424: aastore
      // 0425: dup
      // 0426: bipush 27
      // 0428: new net/minecraft/client/animation/Keyframe
      // 042b: dup
      // 042c: ldc 1.21
      // 042e: ldc 1.518
      // 0430: ldc -8.029
      // 0432: ldc -3.783
      // 0434: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0437: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 043a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 043d: aastore
      // 043e: dup
      // 043f: bipush 28
      // 0441: new net/minecraft/client/animation/Keyframe
      // 0444: dup
      // 0445: ldc 1.29
      // 0447: ldc 1.571
      // 0449: ldc -7.581
      // 044b: ldc -3.993
      // 044d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0450: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0453: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0456: aastore
      // 0457: dup
      // 0458: bipush 29
      // 045a: new net/minecraft/client/animation/Keyframe
      // 045d: dup
      // 045e: ldc 1.38
      // 0460: ldc 1.419
      // 0462: ldc -7.21
      // 0464: ldc -4.224
      // 0466: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0469: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 046c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 046f: aastore
      // 0470: dup
      // 0471: bipush 30
      // 0473: new net/minecraft/client/animation/Keyframe
      // 0476: dup
      // 0477: ldc 1.42
      // 0479: ldc 1.197
      // 047b: ldc -7.15
      // 047d: ldc -4.347
      // 047f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0482: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0485: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0488: aastore
      // 0489: dup
      // 048a: bipush 31
      // 048c: new net/minecraft/client/animation/Keyframe
      // 048f: dup
      // 0490: ldc 1.46
      // 0492: ldc 0.623
      // 0494: ldc -7.15
      // 0496: ldc -4.394
      // 0498: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 049b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 049e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04a1: aastore
      // 04a2: dup
      // 04a3: bipush 32
      // 04a5: new net/minecraft/client/animation/Keyframe
      // 04a8: dup
      // 04a9: ldc 1.5
      // 04ab: ldc 0.397
      // 04ad: ldc -7.15
      // 04af: ldc -4.404
      // 04b1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04b4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04b7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04ba: aastore
      // 04bb: dup
      // 04bc: bipush 33
      // 04be: new net/minecraft/client/animation/Keyframe
      // 04c1: dup
      // 04c2: ldc 1.67
      // 04c4: ldc 0.495
      // 04c6: ldc -7.15
      // 04c8: ldc -4.371
      // 04ca: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04cd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04d0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04d3: aastore
      // 04d4: dup
      // 04d5: bipush 34
      // 04d7: new net/minecraft/client/animation/Keyframe
      // 04da: dup
      // 04db: ldc 1.75
      // 04dd: ldc 0.624
      // 04df: ldc -7.15
      // 04e1: ldc -4.347
      // 04e3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04e6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04e9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04ec: aastore
      // 04ed: dup
      // 04ee: bipush 35
      // 04f0: new net/minecraft/client/animation/Keyframe
      // 04f3: dup
      // 04f4: ldc 1.79
      // 04f6: ldc 0.809
      // 04f8: ldc -7.092
      // 04fa: ldc -4.363
      // 04fc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04ff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0502: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0505: aastore
      // 0506: dup
      // 0507: bipush 36
      // 0509: new net/minecraft/client/animation/Keyframe
      // 050c: dup
      // 050d: ldc 1.88
      // 050f: ldc 1.485
      // 0511: ldc -6.669
      // 0513: ldc -4.432
      // 0515: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0518: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 051b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 051e: aastore
      // 051f: dup
      // 0520: bipush 37
      // 0522: new net/minecraft/client/animation/Keyframe
      // 0525: dup
      // 0526: ldc 1.96
      // 0528: ldc 2.198
      // 052a: ldc -5.92
      // 052c: ldc -4.513
      // 052e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0531: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0534: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0537: aastore
      // 0538: dup
      // 0539: bipush 38
      // 053b: new net/minecraft/client/animation/Keyframe
      // 053e: dup
      // 053f: ldc 2.08
      // 0541: ldc 3.108
      // 0543: ldc -4.404
      // 0545: ldc -4.638
      // 0547: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 054a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 054d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0550: aastore
      // 0551: dup
      // 0552: bipush 39
      // 0554: new net/minecraft/client/animation/Keyframe
      // 0557: dup
      // 0558: ldc 2.12
      // 055a: ldc 3.339
      // 055c: ldc -3.844
      // 055e: ldc -4.679
      // 0560: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0563: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0566: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0569: aastore
      // 056a: dup
      // 056b: bipush 40
      // 056d: new net/minecraft/client/animation/Keyframe
      // 0570: dup
      // 0571: ldc 2.17
      // 0573: ldc 3.522
      // 0575: ldc -3.278
      // 0577: ldc -4.718
      // 0579: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 057c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 057f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0582: aastore
      // 0583: dup
      // 0584: bipush 41
      // 0586: new net/minecraft/client/animation/Keyframe
      // 0589: dup
      // 058a: ldc 2.25
      // 058c: ldc 3.702
      // 058e: ldc -2.177
      // 0590: ldc -4.792
      // 0592: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0595: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0598: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 059b: aastore
      // 059c: dup
      // 059d: bipush 42
      // 059f: new net/minecraft/client/animation/Keyframe
      // 05a2: dup
      // 05a3: ldc 2.29
      // 05a5: ldc 3.675
      // 05a7: ldc -1.667
      // 05a9: ldc -4.826
      // 05ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 05ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05b4: aastore
      // 05b5: dup
      // 05b6: bipush 43
      // 05b8: new net/minecraft/client/animation/Keyframe
      // 05bb: dup
      // 05bc: ldc 2.33
      // 05be: ldc 3.546
      // 05c0: ldc -1.201
      // 05c2: ldc -4.856
      // 05c4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 05c7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05ca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05cd: aastore
      // 05ce: dup
      // 05cf: bipush 44
      // 05d1: new net/minecraft/client/animation/Keyframe
      // 05d4: dup
      // 05d5: ldc 2.38
      // 05d7: ldc 3.285
      // 05d9: ldc -0.792
      // 05db: ldc -4.884
      // 05dd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 05e0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05e3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05e6: aastore
      // 05e7: dup
      // 05e8: bipush 45
      // 05ea: new net/minecraft/client/animation/Keyframe
      // 05ed: dup
      // 05ee: ldc_w 2.42
      // 05f1: ldc_w 2.852
      // 05f4: ldc_w -0.452
      // 05f7: ldc_w -4.907
      // 05fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 05fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0600: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0603: aastore
      // 0604: dup
      // 0605: bipush 46
      // 0607: new net/minecraft/client/animation/Keyframe
      // 060a: dup
      // 060b: ldc_w 2.46
      // 060e: ldc_w 2.172
      // 0611: ldc_w -0.194
      // 0614: ldc_w -4.926
      // 0617: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 061a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 061d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0620: aastore
      // 0621: dup
      // 0622: bipush 47
      // 0624: new net/minecraft/client/animation/Keyframe
      // 0627: dup
      // 0628: ldc_w 2.5
      // 062b: ldc_w 1.105
      // 062e: ldc_w -0.029
      // 0631: ldc_w -4.938
      // 0634: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0637: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 063a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 063d: aastore
      // 063e: dup
      // 063f: bipush 48
      // 0641: new net/minecraft/client/animation/Keyframe
      // 0644: dup
      // 0645: ldc 2.54
      // 0647: ldc -0.727
      // 0649: ldc 0.028
      // 064b: ldc -4.943
      // 064d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0650: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0653: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0656: aastore
      // 0657: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 065a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 065d: ldc "CTR_hips"
      // 065f: new net/minecraft/client/animation/AnimationChannel
      // 0662: dup
      // 0663: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0666: bipush 57
      // 0668: anewarray 28
      // 066b: dup
      // 066c: bipush 0
      // 066d: new net/minecraft/client/animation/Keyframe
      // 0670: dup
      // 0671: fconst_0
      // 0672: fconst_0
      // 0673: fconst_0
      // 0674: fconst_0
      // 0675: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0678: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 067b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 067e: aastore
      // 067f: dup
      // 0680: bipush 1
      // 0681: new net/minecraft/client/animation/Keyframe
      // 0684: dup
      // 0685: ldc 0.04
      // 0687: ldc_w 0.008
      // 068a: ldc_w -0.313
      // 068d: ldc_w 0.6
      // 0690: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0693: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0696: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0699: aastore
      // 069a: dup
      // 069b: bipush 2
      // 069c: new net/minecraft/client/animation/Keyframe
      // 069f: dup
      // 06a0: ldc 0.08
      // 06a2: ldc_w 0.035
      // 06a5: ldc_w -1.193
      // 06a8: ldc_w 2.223
      // 06ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 06ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06b4: aastore
      // 06b5: dup
      // 06b6: bipush 3
      // 06b7: new net/minecraft/client/animation/Keyframe
      // 06ba: dup
      // 06bb: ldc 0.12
      // 06bd: ldc_w 0.087
      // 06c0: ldc_w -2.465
      // 06c3: ldc_w 4.642
      // 06c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 06c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06cf: aastore
      // 06d0: dup
      // 06d1: bipush 4
      // 06d2: new net/minecraft/client/animation/Keyframe
      // 06d5: dup
      // 06d6: ldc 0.17
      // 06d8: ldc_w 0.171
      // 06db: ldc_w -3.855
      // 06de: ldc_w 7.667
      // 06e1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 06e4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06e7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06ea: aastore
      // 06eb: dup
      // 06ec: bipush 5
      // 06ed: new net/minecraft/client/animation/Keyframe
      // 06f0: dup
      // 06f1: ldc 0.21
      // 06f3: ldc_w 0.299
      // 06f6: ldc_w -5.069
      // 06f9: ldc_w 11.128
      // 06fc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 06ff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0702: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0705: aastore
      // 0706: dup
      // 0707: bipush 6
      // 0709: new net/minecraft/client/animation/Keyframe
      // 070c: dup
      // 070d: ldc 0.25
      // 070f: ldc_w 0.488
      // 0712: ldc_w -5.889
      // 0715: ldc_w 14.868
      // 0718: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 071b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 071e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0721: aastore
      // 0722: dup
      // 0723: bipush 7
      // 0725: new net/minecraft/client/animation/Keyframe
      // 0728: dup
      // 0729: ldc 0.29
      // 072b: ldc_w 0.753
      // 072e: ldc_w -6.202
      // 0731: ldc_w 18.732
      // 0734: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0737: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 073a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 073d: aastore
      // 073e: dup
      // 073f: bipush 8
      // 0741: new net/minecraft/client/animation/Keyframe
      // 0744: dup
      // 0745: ldc 0.33
      // 0747: ldc_w 1.084
      // 074a: ldc_w -5.987
      // 074d: ldc_w 22.557
      // 0750: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0753: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0756: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0759: aastore
      // 075a: dup
      // 075b: bipush 9
      // 075d: new net/minecraft/client/animation/Keyframe
      // 0760: dup
      // 0761: ldc 0.38
      // 0763: ldc_w 1.402
      // 0766: ldc_w -5.269
      // 0769: ldc_w 26.159
      // 076c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 076f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0772: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0775: aastore
      // 0776: dup
      // 0777: bipush 10
      // 0779: new net/minecraft/client/animation/Keyframe
      // 077c: dup
      // 077d: ldc 0.42
      // 077f: ldc_w 1.637
      // 0782: ldc_w -4.097
      // 0785: ldc_w 29.318
      // 0788: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 078b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 078e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0791: aastore
      // 0792: dup
      // 0793: bipush 11
      // 0795: new net/minecraft/client/animation/Keyframe
      // 0798: dup
      // 0799: ldc 0.46
      // 079b: ldc_w 1.789
      // 079e: ldc_w -2.941
      // 07a1: ldc_w 31.75
      // 07a4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 07a7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07aa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07ad: aastore
      // 07ae: dup
      // 07af: bipush 12
      // 07b1: new net/minecraft/client/animation/Keyframe
      // 07b4: dup
      // 07b5: ldc 0.5
      // 07b7: ldc_w 1.857
      // 07ba: ldc_w -2.194
      // 07bd: ldc_w 33.051
      // 07c0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 07c3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07c6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07c9: aastore
      // 07ca: dup
      // 07cb: bipush 13
      // 07cd: new net/minecraft/client/animation/Keyframe
      // 07d0: dup
      // 07d1: ldc_w 0.54
      // 07d4: ldc_w 1.832
      // 07d7: ldc_w -1.797
      // 07da: ldc_w 33.575
      // 07dd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 07e0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07e3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07e6: aastore
      // 07e7: dup
      // 07e8: bipush 14
      // 07ea: new net/minecraft/client/animation/Keyframe
      // 07ed: dup
      // 07ee: ldc 0.58
      // 07f0: ldc_w 1.701
      // 07f3: ldc_w -1.7
      // 07f6: ldc_w 34.004
      // 07f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 07fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0802: aastore
      // 0803: dup
      // 0804: bipush 15
      // 0806: new net/minecraft/client/animation/Keyframe
      // 0809: dup
      // 080a: ldc 0.62
      // 080c: ldc_w 1.455
      // 080f: ldc_w -1.854
      // 0812: ldc_w 34.262
      // 0815: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0818: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 081b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 081e: aastore
      // 081f: dup
      // 0820: bipush 16
      // 0822: new net/minecraft/client/animation/Keyframe
      // 0825: dup
      // 0826: ldc 0.67
      // 0828: ldc_w 1.095
      // 082b: ldc_w -2.215
      // 082e: ldc_w 34.186
      // 0831: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0834: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0837: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 083a: aastore
      // 083b: dup
      // 083c: bipush 17
      // 083e: new net/minecraft/client/animation/Keyframe
      // 0841: dup
      // 0842: ldc 0.71
      // 0844: ldc_w 0.637
      // 0847: ldc_w -2.739
      // 084a: ldc_w 33.375
      // 084d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0850: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0853: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0856: aastore
      // 0857: dup
      // 0858: bipush 18
      // 085a: new net/minecraft/client/animation/Keyframe
      // 085d: dup
      // 085e: ldc 0.75
      // 0860: ldc_w 0.126
      // 0863: ldc_w -3.381
      // 0866: ldc_w 30.769
      // 0869: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 086c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 086f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0872: aastore
      // 0873: dup
      // 0874: bipush 19
      // 0876: new net/minecraft/client/animation/Keyframe
      // 0879: dup
      // 087a: ldc_w 0.79
      // 087d: ldc_w -0.064
      // 0880: ldc_w -3.643
      // 0883: ldc_w 28.996
      // 0886: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0889: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 088c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 088f: aastore
      // 0890: dup
      // 0891: bipush 20
      // 0893: new net/minecraft/client/animation/Keyframe
      // 0896: dup
      // 0897: ldc 0.83
      // 0899: ldc_w -0.19
      // 089c: ldc_w -3.826
      // 089f: ldc_w 27.554
      // 08a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 08a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08ab: aastore
      // 08ac: dup
      // 08ad: bipush 21
      // 08af: new net/minecraft/client/animation/Keyframe
      // 08b2: dup
      // 08b3: ldc 0.88
      // 08b5: ldc_w -0.367
      // 08b8: ldc_w -4.097
      // 08bb: ldc_w 25.116
      // 08be: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 08c1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08c4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08c7: aastore
      // 08c8: dup
      // 08c9: bipush 22
      // 08cb: new net/minecraft/client/animation/Keyframe
      // 08ce: dup
      // 08cf: ldc 0.92
      // 08d1: ldc_w -0.708
      // 08d4: ldc_w -4.197
      // 08d7: ldc_w 14.623
      // 08da: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 08dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08e3: aastore
      // 08e4: dup
      // 08e5: bipush 23
      // 08e7: new net/minecraft/client/animation/Keyframe
      // 08ea: dup
      // 08eb: ldc 0.96
      // 08ed: ldc_w -0.921
      // 08f0: ldc_w -3.726
      // 08f3: ldc_w -3.195
      // 08f6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 08f9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08fc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08ff: aastore
      // 0900: dup
      // 0901: bipush 24
      // 0903: new net/minecraft/client/animation/Keyframe
      // 0906: dup
      // 0907: fconst_1
      // 0908: ldc_w -1.053
      // 090b: ldc_w -3.109
      // 090e: ldc_w -23.747
      // 0911: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0914: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0917: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 091a: aastore
      // 091b: dup
      // 091c: bipush 25
      // 091e: new net/minecraft/client/animation/Keyframe
      // 0921: dup
      // 0922: ldc 1.04
      // 0924: ldc_w -1.103
      // 0927: ldc_w -2.61
      // 092a: ldc_w -35.703
      // 092d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0930: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0933: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0936: aastore
      // 0937: dup
      // 0938: bipush 26
      // 093a: new net/minecraft/client/animation/Keyframe
      // 093d: dup
      // 093e: ldc 1.08
      // 0940: ldc_w -1.103
      // 0943: ldc_w -2.443
      // 0946: ldc_w -38.076
      // 0949: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 094c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 094f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0952: aastore
      // 0953: dup
      // 0954: bipush 27
      // 0956: new net/minecraft/client/animation/Keyframe
      // 0959: dup
      // 095a: ldc 1.12
      // 095c: ldc_w -1.103
      // 095f: ldc_w -2.39
      // 0962: ldc_w -39.102
      // 0965: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0968: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 096b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 096e: aastore
      // 096f: dup
      // 0970: bipush 28
      // 0972: new net/minecraft/client/animation/Keyframe
      // 0975: dup
      // 0976: ldc 1.17
      // 0978: ldc_w -1.103
      // 097b: ldc_w -2.382
      // 097e: ldc_w -39.613
      // 0981: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0984: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0987: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 098a: aastore
      // 098b: dup
      // 098c: bipush 29
      // 098e: new net/minecraft/client/animation/Keyframe
      // 0991: dup
      // 0992: ldc 1.21
      // 0994: ldc_w -1.103
      // 0997: ldc_w -2.4
      // 099a: ldc_w -39.828
      // 099d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 09a0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09a3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09a6: aastore
      // 09a7: dup
      // 09a8: bipush 30
      // 09aa: new net/minecraft/client/animation/Keyframe
      // 09ad: dup
      // 09ae: ldc_w 1.25
      // 09b1: ldc_w -1.103
      // 09b4: ldc_w -2.437
      // 09b7: ldc_w -39.841
      // 09ba: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 09bd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09c0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09c3: aastore
      // 09c4: dup
      // 09c5: bipush 31
      // 09c7: new net/minecraft/client/animation/Keyframe
      // 09ca: dup
      // 09cb: ldc 1.29
      // 09cd: ldc_w -1.103
      // 09d0: ldc_w -2.488
      // 09d3: ldc_w -39.708
      // 09d6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 09d9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09dc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09df: aastore
      // 09e0: dup
      // 09e1: bipush 32
      // 09e3: new net/minecraft/client/animation/Keyframe
      // 09e6: dup
      // 09e7: ldc 1.38
      // 09e9: ldc_w -1.103
      // 09ec: ldc_w -2.626
      // 09ef: ldc_w -39.13
      // 09f2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 09f5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09f8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09fb: aastore
      // 09fc: dup
      // 09fd: bipush 33
      // 09ff: new net/minecraft/client/animation/Keyframe
      // 0a02: dup
      // 0a03: ldc 1.42
      // 0a05: ldc_w -1.103
      // 0a08: ldc_w -2.717
      // 0a0b: ldc_w -38.756
      // 0a0e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a11: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a14: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a17: aastore
      // 0a18: dup
      // 0a19: bipush 34
      // 0a1b: new net/minecraft/client/animation/Keyframe
      // 0a1e: dup
      // 0a1f: ldc 1.46
      // 0a21: ldc_w -1.103
      // 0a24: ldc_w -2.755
      // 0a27: ldc_w -38.626
      // 0a2a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a2d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a30: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a33: aastore
      // 0a34: dup
      // 0a35: bipush 35
      // 0a37: new net/minecraft/client/animation/Keyframe
      // 0a3a: dup
      // 0a3b: ldc 1.5
      // 0a3d: ldc_w -1.103
      // 0a40: ldc_w -2.762
      // 0a43: ldc_w -38.6
      // 0a46: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a49: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a4c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a4f: aastore
      // 0a50: dup
      // 0a51: bipush 36
      // 0a53: new net/minecraft/client/animation/Keyframe
      // 0a56: dup
      // 0a57: ldc_w 1.58
      // 0a5a: ldc_w -1.103
      // 0a5d: ldc_w -2.755
      // 0a60: ldc_w -38.623
      // 0a63: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a66: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a69: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a6c: aastore
      // 0a6d: dup
      // 0a6e: bipush 37
      // 0a70: new net/minecraft/client/animation/Keyframe
      // 0a73: dup
      // 0a74: ldc 1.75
      // 0a76: ldc_w -1.103
      // 0a79: ldc_w -2.717
      // 0a7c: ldc_w -38.756
      // 0a7f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a82: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a85: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a88: aastore
      // 0a89: dup
      // 0a8a: bipush 38
      // 0a8c: new net/minecraft/client/animation/Keyframe
      // 0a8f: dup
      // 0a90: ldc 1.79
      // 0a92: ldc_w -1.094
      // 0a95: ldc_w -2.694
      // 0a98: ldc_w -38.439
      // 0a9b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a9e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0aa1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0aa4: aastore
      // 0aa5: dup
      // 0aa6: bipush 39
      // 0aa8: new net/minecraft/client/animation/Keyframe
      // 0aab: dup
      // 0aac: ldc_w 1.83
      // 0aaf: ldc_w -1.069
      // 0ab2: ldc_w -2.63
      // 0ab5: ldc_w -37.536
      // 0ab8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0abb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0abe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ac1: aastore
      // 0ac2: dup
      // 0ac3: bipush 40
      // 0ac5: new net/minecraft/client/animation/Keyframe
      // 0ac8: dup
      // 0ac9: ldc 1.88
      // 0acb: ldc_w -1.029
      // 0ace: ldc_w -2.53
      // 0ad1: ldc_w -36.122
      // 0ad4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0ad7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ada: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0add: aastore
      // 0ade: dup
      // 0adf: bipush 41
      // 0ae1: new net/minecraft/client/animation/Keyframe
      // 0ae4: dup
      // 0ae5: ldc_w 1.92
      // 0ae8: ldc_w -0.977
      // 0aeb: ldc_w -2.399
      // 0aee: ldc_w -34.267
      // 0af1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0af4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0af7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0afa: aastore
      // 0afb: dup
      // 0afc: bipush 42
      // 0afe: new net/minecraft/client/animation/Keyframe
      // 0b01: dup
      // 0b02: ldc 1.96
      // 0b04: ldc_w -0.914
      // 0b07: ldc_w -2.242
      // 0b0a: ldc_w -32.042
      // 0b0d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b10: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b13: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b16: aastore
      // 0b17: dup
      // 0b18: bipush 43
      // 0b1a: new net/minecraft/client/animation/Keyframe
      // 0b1d: dup
      // 0b1e: fconst_2
      // 0b1f: ldc_w -0.842
      // 0b22: ldc_w -2.065
      // 0b25: ldc_w -29.517
      // 0b28: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b2b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b2e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b31: aastore
      // 0b32: dup
      // 0b33: bipush 44
      // 0b35: new net/minecraft/client/animation/Keyframe
      // 0b38: dup
      // 0b39: ldc 2.04
      // 0b3b: ldc_w -0.764
      // 0b3e: ldc_w -1.871
      // 0b41: ldc_w -26.759
      // 0b44: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b47: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b4a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b4d: aastore
      // 0b4e: dup
      // 0b4f: bipush 45
      // 0b51: new net/minecraft/client/animation/Keyframe
      // 0b54: dup
      // 0b55: ldc 2.08
      // 0b57: ldc_w -0.681
      // 0b5a: ldc -1.667
      // 0b5c: ldc_w -23.837
      // 0b5f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b62: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b65: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b68: aastore
      // 0b69: dup
      // 0b6a: bipush 46
      // 0b6c: new net/minecraft/client/animation/Keyframe
      // 0b6f: dup
      // 0b70: ldc 2.12
      // 0b72: ldc_w -0.595
      // 0b75: ldc_w -1.455
      // 0b78: ldc_w -20.819
      // 0b7b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b7e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b81: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b84: aastore
      // 0b85: dup
      // 0b86: bipush 47
      // 0b88: new net/minecraft/client/animation/Keyframe
      // 0b8b: dup
      // 0b8c: ldc 2.17
      // 0b8e: ldc_w -0.508
      // 0b91: ldc_w -1.242
      // 0b94: ldc_w -17.771
      // 0b97: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b9a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b9d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ba0: aastore
      // 0ba1: dup
      // 0ba2: bipush 48
      // 0ba4: new net/minecraft/client/animation/Keyframe
      // 0ba7: dup
      // 0ba8: ldc 2.21
      // 0baa: ldc_w -0.422
      // 0bad: ldc_w -1.032
      // 0bb0: ldc_w -14.76
      // 0bb3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0bb6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0bb9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bbc: aastore
      // 0bbd: dup
      // 0bbe: bipush 49
      // 0bc0: new net/minecraft/client/animation/Keyframe
      // 0bc3: dup
      // 0bc4: ldc 2.25
      // 0bc6: ldc_w -0.339
      // 0bc9: ldc_w -0.828
      // 0bcc: ldc_w -11.852
      // 0bcf: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0bd2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0bd5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bd8: aastore
      // 0bd9: dup
      // 0bda: bipush 50
      // 0bdc: new net/minecraft/client/animation/Keyframe
      // 0bdf: dup
      // 0be0: ldc 2.29
      // 0be2: ldc_w -0.261
      // 0be5: ldc_w -0.637
      // 0be8: ldc_w -9.114
      // 0beb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0bee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0bf1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bf4: aastore
      // 0bf5: dup
      // 0bf6: bipush 51
      // 0bf8: new net/minecraft/client/animation/Keyframe
      // 0bfb: dup
      // 0bfc: ldc 2.33
      // 0bfe: ldc_w -0.189
      // 0c01: ldc_w -0.463
      // 0c04: ldc_w -6.613
      // 0c07: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0c0a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c0d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c10: aastore
      // 0c11: dup
      // 0c12: bipush 52
      // 0c14: new net/minecraft/client/animation/Keyframe
      // 0c17: dup
      // 0c18: ldc 2.38
      // 0c1a: ldc_w -0.126
      // 0c1d: ldc_w -0.309
      // 0c20: ldc_w -4.415
      // 0c23: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0c26: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c29: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c2c: aastore
      // 0c2d: dup
      // 0c2e: bipush 53
      // 0c30: new net/minecraft/client/animation/Keyframe
      // 0c33: dup
      // 0c34: ldc_w 2.42
      // 0c37: ldc_w -0.074
      // 0c3a: ldc_w -0.181
      // 0c3d: ldc_w -2.586
      // 0c40: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0c43: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c46: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c49: aastore
      // 0c4a: dup
      // 0c4b: bipush 54
      // 0c4d: new net/minecraft/client/animation/Keyframe
      // 0c50: dup
      // 0c51: ldc_w 2.46
      // 0c54: ldc_w -0.034
      // 0c57: ldc_w -0.084
      // 0c5a: ldc_w -1.196
      // 0c5d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0c60: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c63: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c66: aastore
      // 0c67: dup
      // 0c68: bipush 55
      // 0c6a: new net/minecraft/client/animation/Keyframe
      // 0c6d: dup
      // 0c6e: ldc_w 2.5
      // 0c71: ldc_w -0.009
      // 0c74: ldc_w -0.022
      // 0c77: ldc_w -0.311
      // 0c7a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0c7d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c80: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c83: aastore
      // 0c84: dup
      // 0c85: bipush 56
      // 0c87: new net/minecraft/client/animation/Keyframe
      // 0c8a: dup
      // 0c8b: ldc 2.54
      // 0c8d: fconst_0
      // 0c8e: fconst_0
      // 0c8f: fconst_0
      // 0c90: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0c93: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c96: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c99: aastore
      // 0c9a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0c9d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0ca0: ldc_w "CTR_chest"
      // 0ca3: new net/minecraft/client/animation/AnimationChannel
      // 0ca6: dup
      // 0ca7: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0caa: bipush 59
      // 0cac: anewarray 28
      // 0caf: dup
      // 0cb0: bipush 0
      // 0cb1: new net/minecraft/client/animation/Keyframe
      // 0cb4: dup
      // 0cb5: fconst_0
      // 0cb6: ldc_w 0.692
      // 0cb9: ldc_w 4.888
      // 0cbc: ldc_w 0.106
      // 0cbf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0cc2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0cc5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0cc8: aastore
      // 0cc9: dup
      // 0cca: bipush 1
      // 0ccb: new net/minecraft/client/animation/Keyframe
      // 0cce: dup
      // 0ccf: ldc 0.04
      // 0cd1: ldc_w 0.202
      // 0cd4: ldc_w 4.309
      // 0cd7: ldc_w -0.316
      // 0cda: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0cdd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ce0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ce3: aastore
      // 0ce4: dup
      // 0ce5: bipush 2
      // 0ce6: new net/minecraft/client/animation/Keyframe
      // 0ce9: dup
      // 0cea: ldc 0.08
      // 0cec: ldc_w -1.103
      // 0cef: ldc_w 2.774
      // 0cf2: ldc_w -1.399
      // 0cf5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0cf8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0cfb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0cfe: aastore
      // 0cff: dup
      // 0d00: bipush 3
      // 0d01: new net/minecraft/client/animation/Keyframe
      // 0d04: dup
      // 0d05: ldc 0.12
      // 0d07: ldc_w -2.995
      // 0d0a: ldc_w 0.585
      // 0d0d: ldc_w -2.964
      // 0d10: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d13: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d16: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d19: aastore
      // 0d1a: dup
      // 0d1b: bipush 4
      // 0d1c: new net/minecraft/client/animation/Keyframe
      // 0d1f: dup
      // 0d20: ldc 0.17
      // 0d22: ldc_w -5.29
      // 0d25: ldc_w -1.956
      // 0d28: ldc_w -4.921
      // 0d2b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d2e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d31: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d34: aastore
      // 0d35: dup
      // 0d36: bipush 5
      // 0d37: new net/minecraft/client/animation/Keyframe
      // 0d3a: dup
      // 0d3b: ldc 0.21
      // 0d3d: ldc_w -7.852
      // 0d40: ldc_w -4.539
      // 0d43: ldc_w -7.244
      // 0d46: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d49: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d4c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d4f: aastore
      // 0d50: dup
      // 0d51: bipush 6
      // 0d53: new net/minecraft/client/animation/Keyframe
      // 0d56: dup
      // 0d57: ldc 0.25
      // 0d59: ldc_w -10.611
      // 0d5c: ldc_w -6.841
      // 0d5f: ldc_w -9.962
      // 0d62: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d65: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d68: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d6b: aastore
      // 0d6c: dup
      // 0d6d: bipush 7
      // 0d6f: new net/minecraft/client/animation/Keyframe
      // 0d72: dup
      // 0d73: ldc 0.29
      // 0d75: ldc_w -13.561
      // 0d78: ldc_w -8.53
      // 0d7b: ldc_w -13.18
      // 0d7e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d81: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d84: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d87: aastore
      // 0d88: dup
      // 0d89: bipush 8
      // 0d8b: new net/minecraft/client/animation/Keyframe
      // 0d8e: dup
      // 0d8f: ldc 0.33
      // 0d91: ldc_w -16.785
      // 0d94: ldc_w -9.269
      // 0d97: ldc_w -17.252
      // 0d9a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d9d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0da0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0da3: aastore
      // 0da4: dup
      // 0da5: bipush 9
      // 0da7: new net/minecraft/client/animation/Keyframe
      // 0daa: dup
      // 0dab: ldc 0.38
      // 0dad: ldc_w -20.577
      // 0db0: ldc_w -8.745
      // 0db3: ldc_w -20.949
      // 0db6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0db9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0dbc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0dbf: aastore
      // 0dc0: dup
      // 0dc1: bipush 10
      // 0dc3: new net/minecraft/client/animation/Keyframe
      // 0dc6: dup
      // 0dc7: ldc 0.42
      // 0dc9: ldc_w -25.282
      // 0dcc: ldc_w -6.674
      // 0dcf: ldc_w -23.13
      // 0dd2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0dd5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0dd8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ddb: aastore
      // 0ddc: dup
      // 0ddd: bipush 11
      // 0ddf: new net/minecraft/client/animation/Keyframe
      // 0de2: dup
      // 0de3: ldc 0.46
      // 0de5: ldc_w -30.685
      // 0de8: ldc_w -4.055
      // 0deb: ldc_w -24.419
      // 0dee: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0df1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0df4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0df7: aastore
      // 0df8: dup
      // 0df9: bipush 12
      // 0dfb: new net/minecraft/client/animation/Keyframe
      // 0dfe: dup
      // 0dff: ldc 0.5
      // 0e01: ldc_w -35.713
      // 0e04: ldc_w -2.087
      // 0e07: ldc_w -25.081
      // 0e0a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e0d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e10: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e13: aastore
      // 0e14: dup
      // 0e15: bipush 13
      // 0e17: new net/minecraft/client/animation/Keyframe
      // 0e1a: dup
      // 0e1b: ldc_w 0.54
      // 0e1e: ldc_w -39.636
      // 0e21: ldc_w -0.752
      // 0e24: ldc_w -25.24
      // 0e27: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e2a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e2d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e30: aastore
      // 0e31: dup
      // 0e32: bipush 14
      // 0e34: new net/minecraft/client/animation/Keyframe
      // 0e37: dup
      // 0e38: ldc 0.58
      // 0e3a: ldc_w -42.39
      // 0e3d: ldc_w -0.048
      // 0e40: ldc_w -24.965
      // 0e43: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e46: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e49: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e4c: aastore
      // 0e4d: dup
      // 0e4e: bipush 15
      // 0e50: new net/minecraft/client/animation/Keyframe
      // 0e53: dup
      // 0e54: ldc 0.62
      // 0e56: ldc_w -43.885
      // 0e59: ldc_w 0.015
      // 0e5c: ldc_w -24.295
      // 0e5f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e62: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e65: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e68: aastore
      // 0e69: dup
      // 0e6a: bipush 16
      // 0e6c: new net/minecraft/client/animation/Keyframe
      // 0e6f: dup
      // 0e70: ldc 0.67
      // 0e72: ldc_w -43.994
      // 0e75: ldc_w -0.584
      // 0e78: ldc_w -23.25
      // 0e7b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e7e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e81: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e84: aastore
      // 0e85: dup
      // 0e86: bipush 17
      // 0e88: new net/minecraft/client/animation/Keyframe
      // 0e8b: dup
      // 0e8c: ldc 0.71
      // 0e8e: ldc_w -42.517
      // 0e91: ldc_w -1.874
      // 0e94: ldc_w -21.824
      // 0e97: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e9a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e9d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ea0: aastore
      // 0ea1: dup
      // 0ea2: bipush 18
      // 0ea4: new net/minecraft/client/animation/Keyframe
      // 0ea7: dup
      // 0ea8: ldc 0.75
      // 0eaa: ldc_w -39.123
      // 0ead: ldc_w -3.892
      // 0eb0: ldc_w -19.947
      // 0eb3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0eb6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0eb9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ebc: aastore
      // 0ebd: dup
      // 0ebe: bipush 19
      // 0ec0: new net/minecraft/client/animation/Keyframe
      // 0ec3: dup
      // 0ec4: ldc_w 0.79
      // 0ec7: ldc_w -35.982
      // 0eca: ldc_w -4.839
      // 0ecd: ldc_w -18.968
      // 0ed0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ed3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ed6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ed9: aastore
      // 0eda: dup
      // 0edb: bipush 20
      // 0edd: new net/minecraft/client/animation/Keyframe
      // 0ee0: dup
      // 0ee1: ldc 0.83
      // 0ee3: ldc_w -33.126
      // 0ee6: ldc_w -5.547
      // 0ee9: ldc_w -18.277
      // 0eec: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0eef: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ef2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ef5: aastore
      // 0ef6: dup
      // 0ef7: bipush 21
      // 0ef9: new net/minecraft/client/animation/Keyframe
      // 0efc: dup
      // 0efd: ldc 0.88
      // 0eff: ldc_w -29.668
      // 0f02: ldc_w -6.68
      // 0f05: ldc_w -17.24
      // 0f08: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f0b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f0e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f11: aastore
      // 0f12: dup
      // 0f13: bipush 22
      // 0f15: new net/minecraft/client/animation/Keyframe
      // 0f18: dup
      // 0f19: ldc 0.92
      // 0f1b: ldc_w -23.635
      // 0f1e: ldc_w -9.348
      // 0f21: ldc_w -13.045
      // 0f24: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f27: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f2a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f2d: aastore
      // 0f2e: dup
      // 0f2f: bipush 23
      // 0f31: new net/minecraft/client/animation/Keyframe
      // 0f34: dup
      // 0f35: ldc 0.96
      // 0f37: ldc_w -10.338
      // 0f3a: ldc_w -11.395
      // 0f3d: ldc_w -8.607
      // 0f40: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f43: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f46: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f49: aastore
      // 0f4a: dup
      // 0f4b: bipush 24
      // 0f4d: new net/minecraft/client/animation/Keyframe
      // 0f50: dup
      // 0f51: fconst_1
      // 0f52: ldc_w 3.064
      // 0f55: ldc_w -13.007
      // 0f58: ldc_w -4.091
      // 0f5b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f5e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f61: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f64: aastore
      // 0f65: dup
      // 0f66: bipush 25
      // 0f68: new net/minecraft/client/animation/Keyframe
      // 0f6b: dup
      // 0f6c: ldc 1.04
      // 0f6e: ldc_w 13.186
      // 0f71: ldc_w -14.17
      // 0f74: ldc_w 0.459
      // 0f77: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f7a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f7d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f80: aastore
      // 0f81: dup
      // 0f82: bipush 26
      // 0f84: new net/minecraft/client/animation/Keyframe
      // 0f87: dup
      // 0f88: ldc 1.08
      // 0f8a: ldc_w 15.572
      // 0f8d: ldc_w -14.682
      // 0f90: ldc_w 5.201
      // 0f93: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f96: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f99: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f9c: aastore
      // 0f9d: dup
      // 0f9e: bipush 27
      // 0fa0: new net/minecraft/client/animation/Keyframe
      // 0fa3: dup
      // 0fa4: ldc 1.12
      // 0fa6: ldc_w 16.299
      // 0fa9: ldc_w -14.945
      // 0fac: ldc_w 7.541
      // 0faf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fb2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fb5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fb8: aastore
      // 0fb9: dup
      // 0fba: bipush 28
      // 0fbc: new net/minecraft/client/animation/Keyframe
      // 0fbf: dup
      // 0fc0: ldc 1.17
      // 0fc2: ldc_w 16.545
      // 0fc5: ldc_w -15.125
      // 0fc8: ldc_w 8.694
      // 0fcb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fd1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fd4: aastore
      // 0fd5: dup
      // 0fd6: bipush 29
      // 0fd8: new net/minecraft/client/animation/Keyframe
      // 0fdb: dup
      // 0fdc: ldc 1.21
      // 0fde: ldc_w 16.471
      // 0fe1: ldc_w -15.241
      // 0fe4: ldc_w 9.504
      // 0fe7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ff0: aastore
      // 0ff1: dup
      // 0ff2: bipush 30
      // 0ff4: new net/minecraft/client/animation/Keyframe
      // 0ff7: dup
      // 0ff8: ldc_w 1.25
      // 0ffb: ldc_w 16.172
      // 0ffe: ldc_w -15.307
      // 1001: ldc_w 10.119
      // 1004: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1007: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 100a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 100d: aastore
      // 100e: dup
      // 100f: bipush 31
      // 1011: new net/minecraft/client/animation/Keyframe
      // 1014: dup
      // 1015: ldc 1.29
      // 1017: ldc_w 15.693
      // 101a: ldc_w -15.327
      // 101d: ldc_w 10.591
      // 1020: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1023: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1026: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1029: aastore
      // 102a: dup
      // 102b: bipush 32
      // 102d: new net/minecraft/client/animation/Keyframe
      // 1030: dup
      // 1031: ldc_w 1.33
      // 1034: ldc_w 15.058
      // 1037: ldc_w -15.297
      // 103a: ldc_w 10.942
      // 103d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1040: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1043: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1046: aastore
      // 1047: dup
      // 1048: bipush 33
      // 104a: new net/minecraft/client/animation/Keyframe
      // 104d: dup
      // 104e: ldc 1.38
      // 1050: ldc_w 14.262
      // 1053: ldc_w -15.204
      // 1056: ldc_w 11.166
      // 1059: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 105c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 105f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1062: aastore
      // 1063: dup
      // 1064: bipush 34
      // 1066: new net/minecraft/client/animation/Keyframe
      // 1069: dup
      // 106a: ldc 1.42
      // 106c: ldc_w 13.231
      // 106f: ldc_w -14.984
      // 1072: ldc_w 11.174
      // 1075: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1078: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 107b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 107e: aastore
      // 107f: dup
      // 1080: bipush 35
      // 1082: new net/minecraft/client/animation/Keyframe
      // 1085: dup
      // 1086: ldc 1.46
      // 1088: ldc_w 12.842
      // 108b: ldc_w -14.884
      // 108e: ldc_w 10.433
      // 1091: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1094: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1097: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 109a: aastore
      // 109b: dup
      // 109c: bipush 36
      // 109e: new net/minecraft/client/animation/Keyframe
      // 10a1: dup
      // 10a2: ldc 1.5
      // 10a4: ldc_w 13.021
      // 10a7: ldc_w -14.933
      // 10aa: ldc_w 8.776
      // 10ad: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10b0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10b3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10b6: aastore
      // 10b7: dup
      // 10b8: bipush 37
      // 10ba: new net/minecraft/client/animation/Keyframe
      // 10bd: dup
      // 10be: ldc_w 1.62
      // 10c1: ldc_w 14.098
      // 10c4: ldc_w -15.182
      // 10c7: ldc_w 3.611
      // 10ca: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10cd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10d0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10d3: aastore
      // 10d4: dup
      // 10d5: bipush 38
      // 10d7: new net/minecraft/client/animation/Keyframe
      // 10da: dup
      // 10db: ldc 1.67
      // 10dd: ldc_w 14.479
      // 10e0: ldc_w -15.268
      // 10e3: ldc_w 1.991
      // 10e6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10e9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10ec: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10ef: aastore
      // 10f0: dup
      // 10f1: bipush 39
      // 10f3: new net/minecraft/client/animation/Keyframe
      // 10f6: dup
      // 10f7: ldc_w 1.71
      // 10fa: ldc_w 14.813
      // 10fd: ldc_w -15.346
      // 1100: ldc_w 0.518
      // 1103: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1106: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1109: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 110c: aastore
      // 110d: dup
      // 110e: bipush 40
      // 1110: new net/minecraft/client/animation/Keyframe
      // 1113: dup
      // 1114: ldc 1.75
      // 1116: ldc_w 15.011
      // 1119: ldc_w -15.399
      // 111c: ldc_w -0.458
      // 111f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1122: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1125: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1128: aastore
      // 1129: dup
      // 112a: bipush 41
      // 112c: new net/minecraft/client/animation/Keyframe
      // 112f: dup
      // 1130: ldc 1.79
      // 1132: ldc_w 12.737
      // 1135: ldc_w -13.797
      // 1138: ldc_w -0.606
      // 113b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 113e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1141: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1144: aastore
      // 1145: dup
      // 1146: bipush 42
      // 1148: new net/minecraft/client/animation/Keyframe
      // 114b: dup
      // 114c: ldc_w 1.83
      // 114f: ldc_w 6.942
      // 1152: ldc_w -11.0
      // 1155: ldc_w -0.875
      // 1158: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 115b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 115e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1161: aastore
      // 1162: dup
      // 1163: bipush 43
      // 1165: new net/minecraft/client/animation/Keyframe
      // 1168: dup
      // 1169: ldc 1.88
      // 116b: ldc_w -0.885
      // 116e: ldc_w -7.895
      // 1171: ldc_w -1.211
      // 1174: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1177: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 117a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 117d: aastore
      // 117e: dup
      // 117f: bipush 44
      // 1181: new net/minecraft/client/animation/Keyframe
      // 1184: dup
      // 1185: ldc_w 1.92
      // 1188: ldc_w -9.359
      // 118b: ldc_w -4.789
      // 118e: ldc_w -1.624
      // 1191: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1194: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1197: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 119a: aastore
      // 119b: dup
      // 119c: bipush 45
      // 119e: new net/minecraft/client/animation/Keyframe
      // 11a1: dup
      // 11a2: ldc 1.96
      // 11a4: ldc_w -16.948
      // 11a7: ldc_w -1.906
      // 11aa: ldc_w -2.165
      // 11ad: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11b0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11b3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11b6: aastore
      // 11b7: dup
      // 11b8: bipush 46
      // 11ba: new net/minecraft/client/animation/Keyframe
      // 11bd: dup
      // 11be: fconst_2
      // 11bf: ldc_w -21.503
      // 11c2: ldc_w 0.483
      // 11c5: ldc_w -2.972
      // 11c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11d1: aastore
      // 11d2: dup
      // 11d3: bipush 47
      // 11d5: new net/minecraft/client/animation/Keyframe
      // 11d8: dup
      // 11d9: ldc 2.04
      // 11db: ldc_w -22.635
      // 11de: ldc_w 2.086
      // 11e1: ldc_w -3.64
      // 11e4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11e7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11ea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11ed: aastore
      // 11ee: dup
      // 11ef: bipush 48
      // 11f1: new net/minecraft/client/animation/Keyframe
      // 11f4: dup
      // 11f5: ldc 2.08
      // 11f7: ldc_w -22.598
      // 11fa: ldc_w 3.139
      // 11fd: ldc_w -3.805
      // 1200: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1203: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1206: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1209: aastore
      // 120a: dup
      // 120b: bipush 49
      // 120d: new net/minecraft/client/animation/Keyframe
      // 1210: dup
      // 1211: ldc 2.12
      // 1213: ldc_w -21.832
      // 1216: ldc_w 3.858
      // 1219: ldc_w -3.688
      // 121c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 121f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1222: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1225: aastore
      // 1226: dup
      // 1227: bipush 50
      // 1229: new net/minecraft/client/animation/Keyframe
      // 122c: dup
      // 122d: ldc 2.17
      // 122f: ldc_w -20.556
      // 1232: ldc_w 4.35
      // 1235: ldc_w -3.396
      // 1238: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 123b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 123e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1241: aastore
      // 1242: dup
      // 1243: bipush 51
      // 1245: new net/minecraft/client/animation/Keyframe
      // 1248: dup
      // 1249: ldc 2.21
      // 124b: ldc_w -18.902
      // 124e: ldc_w 4.679
      // 1251: ldc_w -2.994
      // 1254: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1257: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 125a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 125d: aastore
      // 125e: dup
      // 125f: bipush 52
      // 1261: new net/minecraft/client/animation/Keyframe
      // 1264: dup
      // 1265: ldc 2.25
      // 1267: ldc_w -16.955
      // 126a: ldc_w 4.885
      // 126d: ldc_w -2.527
      // 1270: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1273: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1276: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1279: aastore
      // 127a: dup
      // 127b: bipush 53
      // 127d: new net/minecraft/client/animation/Keyframe
      // 1280: dup
      // 1281: ldc 2.29
      // 1283: ldc_w -14.779
      // 1286: ldc_w 4.999
      // 1289: ldc_w -2.026
      // 128c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 128f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1292: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1295: aastore
      // 1296: dup
      // 1297: bipush 54
      // 1299: new net/minecraft/client/animation/Keyframe
      // 129c: dup
      // 129d: ldc 2.33
      // 129f: ldc_w -12.419
      // 12a2: ldc_w 5.045
      // 12a5: ldc_w -1.521
      // 12a8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 12ab: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12ae: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12b1: aastore
      // 12b2: dup
      // 12b3: bipush 55
      // 12b5: new net/minecraft/client/animation/Keyframe
      // 12b8: dup
      // 12b9: ldc 2.38
      // 12bb: ldc_w -9.915
      // 12be: ldc_w 5.041
      // 12c1: ldc_w -1.038
      // 12c4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 12c7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12ca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12cd: aastore
      // 12ce: dup
      // 12cf: bipush 56
      // 12d1: new net/minecraft/client/animation/Keyframe
      // 12d4: dup
      // 12d5: ldc_w 2.46
      // 12d8: ldc_w -4.614
      // 12db: ldc_w 4.955
      // 12de: ldc_w -0.24
      // 12e1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 12e4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12e7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12ea: aastore
      // 12eb: dup
      // 12ec: bipush 57
      // 12ee: new net/minecraft/client/animation/Keyframe
      // 12f1: dup
      // 12f2: ldc_w 2.5
      // 12f5: ldc_w -1.907
      // 12f8: ldc_w 4.909
      // 12fb: ldc_w 0.01
      // 12fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1301: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1304: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1307: aastore
      // 1308: dup
      // 1309: bipush 58
      // 130b: new net/minecraft/client/animation/Keyframe
      // 130e: dup
      // 130f: ldc 2.54
      // 1311: ldc_w 0.692
      // 1314: ldc_w 4.888
      // 1317: ldc_w 0.106
      // 131a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 131d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1320: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1323: aastore
      // 1324: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1327: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 132a: ldc_w "CTR_chest"
      // 132d: new net/minecraft/client/animation/AnimationChannel
      // 1330: dup
      // 1331: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1334: bipush 19
      // 1336: anewarray 28
      // 1339: dup
      // 133a: bipush 0
      // 133b: new net/minecraft/client/animation/Keyframe
      // 133e: dup
      // 133f: fconst_0
      // 1340: fconst_0
      // 1341: fconst_0
      // 1342: fconst_0
      // 1343: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1346: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1349: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 134c: aastore
      // 134d: dup
      // 134e: bipush 1
      // 134f: new net/minecraft/client/animation/Keyframe
      // 1352: dup
      // 1353: ldc 0.04
      // 1355: ldc_w 0.035
      // 1358: ldc_w 0.126
      // 135b: ldc_w -0.055
      // 135e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1361: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1364: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1367: aastore
      // 1368: dup
      // 1369: bipush 2
      // 136a: new net/minecraft/client/animation/Keyframe
      // 136d: dup
      // 136e: ldc 0.08
      // 1370: ldc_w 0.131
      // 1373: ldc_w 0.471
      // 1376: ldc_w -0.204
      // 1379: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 137c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 137f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1382: aastore
      // 1383: dup
      // 1384: bipush 3
      // 1385: new net/minecraft/client/animation/Keyframe
      // 1388: dup
      // 1389: ldc 0.17
      // 138b: ldc_w 0.444
      // 138e: ldc_w 1.593
      // 1391: ldc_w -0.691
      // 1394: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1397: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 139a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 139d: aastore
      // 139e: dup
      // 139f: bipush 4
      // 13a0: new net/minecraft/client/animation/Keyframe
      // 13a3: dup
      // 13a4: ldc 0.25
      // 13a6: ldc_w 0.818
      // 13a9: ldc_w 2.933
      // 13ac: ldc_w -1.272
      // 13af: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 13b2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13b5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13b8: aastore
      // 13b9: dup
      // 13ba: bipush 5
      // 13bb: new net/minecraft/client/animation/Keyframe
      // 13be: dup
      // 13bf: ldc 0.29
      // 13c1: ldc_w 0.989
      // 13c4: ldc_w 3.549
      // 13c7: ldc_w -1.539
      // 13ca: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 13cd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13d0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13d3: aastore
      // 13d4: dup
      // 13d5: bipush 6
      // 13d7: new net/minecraft/client/animation/Keyframe
      // 13da: dup
      // 13db: ldc 0.33
      // 13dd: ldc_w 1.131
      // 13e0: ldc_w 4.056
      // 13e3: ldc_w -1.758
      // 13e6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 13e9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13ec: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13ef: aastore
      // 13f0: dup
      // 13f1: bipush 7
      // 13f3: new net/minecraft/client/animation/Keyframe
      // 13f6: dup
      // 13f7: ldc 0.38
      // 13f9: ldc_w 1.227
      // 13fc: ldc_w 4.4
      // 13ff: ldc_w -1.908
      // 1402: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1405: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1408: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 140b: aastore
      // 140c: dup
      // 140d: bipush 8
      // 140f: new net/minecraft/client/animation/Keyframe
      // 1412: dup
      // 1413: ldc 0.42
      // 1415: ldc_w 1.262
      // 1418: ldc_w 4.526
      // 141b: ldc_w -1.963
      // 141e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1421: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1424: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1427: aastore
      // 1428: dup
      // 1429: bipush 9
      // 142b: new net/minecraft/client/animation/Keyframe
      // 142e: dup
      // 142f: ldc 0.88
      // 1431: ldc_w 1.262
      // 1434: ldc_w 4.526
      // 1437: ldc_w -1.963
      // 143a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 143d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1440: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1443: aastore
      // 1444: dup
      // 1445: bipush 10
      // 1447: new net/minecraft/client/animation/Keyframe
      // 144a: dup
      // 144b: ldc 0.92
      // 144d: ldc_w 1.174
      // 1450: ldc_w 3.936
      // 1453: ldc_w -1.749
      // 1456: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1459: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 145c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 145f: aastore
      // 1460: dup
      // 1461: bipush 11
      // 1463: new net/minecraft/client/animation/Keyframe
      // 1466: dup
      // 1467: fconst_1
      // 1468: ldc_w 0.789
      // 146b: ldc_w 1.336
      // 146e: ldc_w -0.809
      // 1471: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1474: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1477: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 147a: aastore
      // 147b: dup
      // 147c: bipush 12
      // 147e: new net/minecraft/client/animation/Keyframe
      // 1481: dup
      // 1482: ldc 1.04
      // 1484: ldc_w 0.701
      // 1487: ldc_w 0.745
      // 148a: ldc_w -0.596
      // 148d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1490: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1493: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1496: aastore
      // 1497: dup
      // 1498: bipush 13
      // 149a: new net/minecraft/client/animation/Keyframe
      // 149d: dup
      // 149e: ldc 1.79
      // 14a0: ldc_w 0.693
      // 14a3: ldc_w 0.737
      // 14a6: ldc_w -0.589
      // 14a9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 14ac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14af: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14b2: aastore
      // 14b3: dup
      // 14b4: bipush 14
      // 14b6: new net/minecraft/client/animation/Keyframe
      // 14b9: dup
      // 14ba: ldc 1.88
      // 14bc: ldc_w 0.639
      // 14bf: ldc_w 0.678
      // 14c2: ldc_w -0.543
      // 14c5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 14c8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14cb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14ce: aastore
      // 14cf: dup
      // 14d0: bipush 15
      // 14d2: new net/minecraft/client/animation/Keyframe
      // 14d5: dup
      // 14d6: ldc 2.12
      // 14d8: ldc_w 0.335
      // 14db: ldc_w 0.356
      // 14de: ldc_w -0.285
      // 14e1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 14e4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14e7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14ea: aastore
      // 14eb: dup
      // 14ec: bipush 16
      // 14ee: new net/minecraft/client/animation/Keyframe
      // 14f1: dup
      // 14f2: ldc 2.33
      // 14f4: ldc_w 0.101
      // 14f7: ldc_w 0.107
      // 14fa: ldc_w -0.086
      // 14fd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1500: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1503: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1506: aastore
      // 1507: dup
      // 1508: bipush 17
      // 150a: new net/minecraft/client/animation/Keyframe
      // 150d: dup
      // 150e: ldc_w 2.46
      // 1511: ldc_w 0.018
      // 1514: ldc_w 0.019
      // 1517: ldc_w -0.015
      // 151a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 151d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1520: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1523: aastore
      // 1524: dup
      // 1525: bipush 18
      // 1527: new net/minecraft/client/animation/Keyframe
      // 152a: dup
      // 152b: ldc 2.54
      // 152d: fconst_0
      // 152e: fconst_0
      // 152f: fconst_0
      // 1530: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1533: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1536: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1539: aastore
      // 153a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 153d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1540: ldc_w "CTR_head"
      // 1543: new net/minecraft/client/animation/AnimationChannel
      // 1546: dup
      // 1547: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 154a: bipush 61
      // 154c: anewarray 28
      // 154f: dup
      // 1550: bipush 0
      // 1551: new net/minecraft/client/animation/Keyframe
      // 1554: dup
      // 1555: fconst_0
      // 1556: ldc_w 0.412
      // 1559: ldc_w -4.079
      // 155c: ldc -4.432
      // 155e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1561: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1564: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1567: aastore
      // 1568: dup
      // 1569: bipush 1
      // 156a: new net/minecraft/client/animation/Keyframe
      // 156d: dup
      // 156e: ldc 0.04
      // 1570: ldc 1.197
      // 1572: ldc_w -3.168
      // 1575: ldc_w -3.292
      // 1578: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 157b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 157e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1581: aastore
      // 1582: dup
      // 1583: bipush 2
      // 1584: new net/minecraft/client/animation/Keyframe
      // 1587: dup
      // 1588: ldc 0.08
      // 158a: ldc_w 3.443
      // 158d: ldc_w -0.692
      // 1590: ldc_w -0.118
      // 1593: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1596: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1599: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 159c: aastore
      // 159d: dup
      // 159e: bipush 3
      // 159f: new net/minecraft/client/animation/Keyframe
      // 15a2: dup
      // 15a3: ldc 0.12
      // 15a5: ldc_w 7.123
      // 15a8: ldc_w 2.883
      // 15ab: ldc_w 4.891
      // 15ae: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15b1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15b4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15b7: aastore
      // 15b8: dup
      // 15b9: bipush 4
      // 15ba: new net/minecraft/client/animation/Keyframe
      // 15bd: dup
      // 15be: ldc 0.17
      // 15c0: ldc_w 12.278
      // 15c3: ldc_w 6.945
      // 15c6: ldc_w 11.661
      // 15c9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15cc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15cf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15d2: aastore
      // 15d3: dup
      // 15d4: bipush 5
      // 15d5: new net/minecraft/client/animation/Keyframe
      // 15d8: dup
      // 15d9: ldc 0.21
      // 15db: ldc_w 18.852
      // 15de: ldc_w 10.694
      // 15e1: ldc_w 20.1
      // 15e4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15e7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15ea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15ed: aastore
      // 15ee: dup
      // 15ef: bipush 6
      // 15f1: new net/minecraft/client/animation/Keyframe
      // 15f4: dup
      // 15f5: ldc 0.25
      // 15f7: ldc_w 26.427
      // 15fa: ldc_w 13.178
      // 15fd: ldc_w 29.815
      // 1600: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1603: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1606: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1609: aastore
      // 160a: dup
      // 160b: bipush 7
      // 160d: new net/minecraft/client/animation/Keyframe
      // 1610: dup
      // 1611: ldc 0.29
      // 1613: ldc_w 33.964
      // 1616: ldc_w 13.551
      // 1619: ldc_w 39.851
      // 161c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 161f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1622: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1625: aastore
      // 1626: dup
      // 1627: bipush 8
      // 1629: new net/minecraft/client/animation/Keyframe
      // 162c: dup
      // 162d: ldc 0.33
      // 162f: ldc_w 39.93
      // 1632: ldc_w 11.49
      // 1635: ldc_w 48.999
      // 1638: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 163b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 163e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1641: aastore
      // 1642: dup
      // 1643: bipush 9
      // 1645: new net/minecraft/client/animation/Keyframe
      // 1648: dup
      // 1649: ldc 0.38
      // 164b: ldc_w 43.383
      // 164e: ldc_w 7.354
      // 1651: ldc_w 54.505
      // 1654: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1657: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 165a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 165d: aastore
      // 165e: dup
      // 165f: bipush 10
      // 1661: new net/minecraft/client/animation/Keyframe
      // 1664: dup
      // 1665: ldc 0.42
      // 1667: ldc_w 44.815
      // 166a: ldc_w 1.595
      // 166d: ldc_w 54.809
      // 1670: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1673: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1676: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1679: aastore
      // 167a: dup
      // 167b: bipush 11
      // 167d: new net/minecraft/client/animation/Keyframe
      // 1680: dup
      // 1681: ldc 0.46
      // 1683: ldc_w 46.416
      // 1686: ldc_w -4.871
      // 1689: ldc_w 51.958
      // 168c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 168f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1692: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1695: aastore
      // 1696: dup
      // 1697: bipush 12
      // 1699: new net/minecraft/client/animation/Keyframe
      // 169c: dup
      // 169d: ldc 0.5
      // 169f: ldc_w 49.379
      // 16a2: ldc_w -10.245
      // 16a5: ldc_w 47.683
      // 16a8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16ab: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16ae: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16b1: aastore
      // 16b2: dup
      // 16b3: bipush 13
      // 16b5: new net/minecraft/client/animation/Keyframe
      // 16b8: dup
      // 16b9: ldc_w 0.54
      // 16bc: ldc_w 52.922
      // 16bf: ldc_w -13.591
      // 16c2: ldc_w 42.664
      // 16c5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16c8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16cb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16ce: aastore
      // 16cf: dup
      // 16d0: bipush 14
      // 16d2: new net/minecraft/client/animation/Keyframe
      // 16d5: dup
      // 16d6: ldc 0.58
      // 16d8: ldc_w 56.303
      // 16db: ldc_w -14.823
      // 16de: ldc_w 37.642
      // 16e1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16e4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16e7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16ea: aastore
      // 16eb: dup
      // 16ec: bipush 15
      // 16ee: new net/minecraft/client/animation/Keyframe
      // 16f1: dup
      // 16f2: ldc 0.62
      // 16f4: ldc_w 58.685
      // 16f7: ldc_w -14.129
      // 16fa: ldc_w 33.287
      // 16fd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1700: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1703: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1706: aastore
      // 1707: dup
      // 1708: bipush 16
      // 170a: new net/minecraft/client/animation/Keyframe
      // 170d: dup
      // 170e: ldc 0.67
      // 1710: ldc_w 59.344
      // 1713: ldc_w -11.859
      // 1716: ldc_w 30.014
      // 1719: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 171c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 171f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1722: aastore
      // 1723: dup
      // 1724: bipush 17
      // 1726: new net/minecraft/client/animation/Keyframe
      // 1729: dup
      // 172a: ldc 0.71
      // 172c: ldc_w 57.685
      // 172f: ldc_w -8.335
      // 1732: ldc_w 27.927
      // 1735: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1738: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 173b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 173e: aastore
      // 173f: dup
      // 1740: bipush 18
      // 1742: new net/minecraft/client/animation/Keyframe
      // 1745: dup
      // 1746: ldc 0.75
      // 1748: ldc_w 53.093
      // 174b: ldc_w -3.652
      // 174e: ldc_w 26.865
      // 1751: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1754: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1757: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 175a: aastore
      // 175b: dup
      // 175c: bipush 19
      // 175e: new net/minecraft/client/animation/Keyframe
      // 1761: dup
      // 1762: ldc_w 0.79
      // 1765: ldc_w 47.968
      // 1768: ldc_w -1.402
      // 176b: ldc_w 26.307
      // 176e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1771: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1774: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1777: aastore
      // 1778: dup
      // 1779: bipush 20
      // 177b: new net/minecraft/client/animation/Keyframe
      // 177e: dup
      // 177f: ldc 0.83
      // 1781: ldc_w 43.482
      // 1784: ldc_w -0.691
      // 1787: ldc_w 25.561
      // 178a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 178d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1790: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1793: aastore
      // 1794: dup
      // 1795: bipush 21
      // 1797: new net/minecraft/client/animation/Keyframe
      // 179a: dup
      // 179b: ldc 0.88
      // 179d: ldc_w 38.502
      // 17a0: ldc_w 0.236
      // 17a3: ldc_w 24.591
      // 17a6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 17a9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17ac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17af: aastore
      // 17b0: dup
      // 17b1: bipush 22
      // 17b3: new net/minecraft/client/animation/Keyframe
      // 17b6: dup
      // 17b7: ldc 0.92
      // 17b9: ldc_w 28.731
      // 17bc: ldc_w 4.991
      // 17bf: ldc_w 16.179
      // 17c2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 17c5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17c8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17cb: aastore
      // 17cc: dup
      // 17cd: bipush 23
      // 17cf: new net/minecraft/client/animation/Keyframe
      // 17d2: dup
      // 17d3: ldc 0.96
      // 17d5: ldc_w 9.085
      // 17d8: ldc_w 6.951
      // 17db: ldc_w 0.918
      // 17de: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 17e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17e7: aastore
      // 17e8: dup
      // 17e9: bipush 24
      // 17eb: new net/minecraft/client/animation/Keyframe
      // 17ee: dup
      // 17ef: fconst_1
      // 17f0: ldc_w -11.451
      // 17f3: ldc_w 0.423
      // 17f6: ldc_w -12.473
      // 17f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 17fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1802: aastore
      // 1803: dup
      // 1804: bipush 25
      // 1806: new net/minecraft/client/animation/Keyframe
      // 1809: dup
      // 180a: ldc 1.04
      // 180c: ldc_w -27.405
      // 180f: ldc_w -7.583
      // 1812: ldc_w -18.192
      // 1815: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1818: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 181b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 181e: aastore
      // 181f: dup
      // 1820: bipush 26
      // 1822: new net/minecraft/client/animation/Keyframe
      // 1825: dup
      // 1826: ldc 1.08
      // 1828: ldc_w -29.997
      // 182b: ldc_w -7.064
      // 182e: ldc_w -20.357
      // 1831: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1834: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1837: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 183a: aastore
      // 183b: dup
      // 183c: bipush 27
      // 183e: new net/minecraft/client/animation/Keyframe
      // 1841: dup
      // 1842: ldc 1.12
      // 1844: ldc_w -24.653
      // 1847: ldc_w -1.239
      // 184a: ldc_w -21.244
      // 184d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1850: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1853: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1856: aastore
      // 1857: dup
      // 1858: bipush 28
      // 185a: new net/minecraft/client/animation/Keyframe
      // 185d: dup
      // 185e: ldc 1.17
      // 1860: ldc_w -19.026
      // 1863: ldc_w 2.328
      // 1866: ldc_w -22.481
      // 1869: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 186c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 186f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1872: aastore
      // 1873: dup
      // 1874: bipush 29
      // 1876: new net/minecraft/client/animation/Keyframe
      // 1879: dup
      // 187a: ldc 1.21
      // 187c: ldc_w -16.876
      // 187f: ldc_w 2.918
      // 1882: ldc_w -23.369
      // 1885: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1888: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 188b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 188e: aastore
      // 188f: dup
      // 1890: bipush 30
      // 1892: new net/minecraft/client/animation/Keyframe
      // 1895: dup
      // 1896: ldc_w 1.25
      // 1899: ldc_w -15.479
      // 189c: ldc_w 3.267
      // 189f: ldc_w -23.978
      // 18a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 18a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18ab: aastore
      // 18ac: dup
      // 18ad: bipush 31
      // 18af: new net/minecraft/client/animation/Keyframe
      // 18b2: dup
      // 18b3: ldc 1.29
      // 18b5: ldc_w -14.121
      // 18b8: ldc_w 3.625
      // 18bb: ldc_w -24.416
      // 18be: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 18c1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18c4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18c7: aastore
      // 18c8: dup
      // 18c9: bipush 32
      // 18cb: new net/minecraft/client/animation/Keyframe
      // 18ce: dup
      // 18cf: ldc_w 1.33
      // 18d2: ldc_w -12.692
      // 18d5: ldc_w 3.979
      // 18d8: ldc_w -24.721
      // 18db: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 18de: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18e1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18e4: aastore
      // 18e5: dup
      // 18e6: bipush 33
      // 18e8: new net/minecraft/client/animation/Keyframe
      // 18eb: dup
      // 18ec: ldc 1.38
      // 18ee: ldc -11.104
      // 18f0: ldc_w 4.293
      // 18f3: ldc_w -24.899
      // 18f6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 18f9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18fc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18ff: aastore
      // 1900: dup
      // 1901: bipush 34
      // 1903: new net/minecraft/client/animation/Keyframe
      // 1906: dup
      // 1907: ldc 1.42
      // 1909: ldc_w -9.019
      // 190c: ldc_w 4.494
      // 190f: ldc_w -24.86
      // 1912: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1915: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1918: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 191b: aastore
      // 191c: dup
      // 191d: bipush 35
      // 191f: new net/minecraft/client/animation/Keyframe
      // 1922: dup
      // 1923: ldc 1.46
      // 1925: ldc_w -7.419
      // 1928: ldc_w 4.569
      // 192b: ldc_w -24.141
      // 192e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1931: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1934: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1937: aastore
      // 1938: dup
      // 1939: bipush 36
      // 193b: new net/minecraft/client/animation/Keyframe
      // 193e: dup
      // 193f: ldc 1.5
      // 1941: ldc_w -7.363
      // 1944: ldc_w 4.903
      // 1947: ldc_w -22.624
      // 194a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 194d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1950: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1953: aastore
      // 1954: dup
      // 1955: bipush 37
      // 1957: new net/minecraft/client/animation/Keyframe
      // 195a: dup
      // 195b: ldc_w 1.54
      // 195e: ldc_w -8.228
      // 1961: ldc_w 5.147
      // 1964: ldc_w -20.981
      // 1967: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 196a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 196d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1970: aastore
      // 1971: dup
      // 1972: bipush 38
      // 1974: new net/minecraft/client/animation/Keyframe
      // 1977: dup
      // 1978: ldc_w 1.58
      // 197b: ldc_w -10.085
      // 197e: ldc_w 7.349
      // 1981: ldc_w -20.3
      // 1984: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1987: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 198a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 198d: aastore
      // 198e: dup
      // 198f: bipush 39
      // 1991: new net/minecraft/client/animation/Keyframe
      // 1994: dup
      // 1995: ldc_w 1.62
      // 1998: ldc_w -11.842
      // 199b: ldc_w 9.775
      // 199e: ldc_w -19.794
      // 19a1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 19a4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19a7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19aa: aastore
      // 19ab: dup
      // 19ac: bipush 40
      // 19ae: new net/minecraft/client/animation/Keyframe
      // 19b1: dup
      // 19b2: ldc 1.67
      // 19b4: ldc_w -13.428
      // 19b7: ldc_w 12.006
      // 19ba: ldc_w -19.292
      // 19bd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 19c0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19c3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19c6: aastore
      // 19c7: dup
      // 19c8: bipush 41
      // 19ca: new net/minecraft/client/animation/Keyframe
      // 19cd: dup
      // 19ce: ldc_w 1.71
      // 19d1: ldc_w -14.71
      // 19d4: ldc_w 13.854
      // 19d7: ldc_w -18.753
      // 19da: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 19dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19e3: aastore
      // 19e4: dup
      // 19e5: bipush 42
      // 19e7: new net/minecraft/client/animation/Keyframe
      // 19ea: dup
      // 19eb: ldc 1.75
      // 19ed: ldc_w -15.354
      // 19f0: ldc_w 14.847
      // 19f3: ldc_w -18.265
      // 19f6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 19f9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19fc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19ff: aastore
      // 1a00: dup
      // 1a01: bipush 43
      // 1a03: new net/minecraft/client/animation/Keyframe
      // 1a06: dup
      // 1a07: ldc 1.79
      // 1a09: ldc_w -12.278
      // 1a0c: ldc_w 13.405
      // 1a0f: ldc_w -16.92
      // 1a12: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a15: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a18: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a1b: aastore
      // 1a1c: dup
      // 1a1d: bipush 44
      // 1a1f: new net/minecraft/client/animation/Keyframe
      // 1a22: dup
      // 1a23: ldc_w 1.83
      // 1a26: ldc_w -4.739
      // 1a29: ldc_w 10.618
      // 1a2c: ldc_w -14.61
      // 1a2f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a32: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a35: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a38: aastore
      // 1a39: dup
      // 1a3a: bipush 45
      // 1a3c: new net/minecraft/client/animation/Keyframe
      // 1a3f: dup
      // 1a40: ldc 1.88
      // 1a42: ldc_w 5.051
      // 1a45: ldc_w 7.194
      // 1a48: ldc_w -12.636
      // 1a4b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a4e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a51: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a54: aastore
      // 1a55: dup
      // 1a56: bipush 46
      // 1a58: new net/minecraft/client/animation/Keyframe
      // 1a5b: dup
      // 1a5c: ldc_w 1.92
      // 1a5f: ldc_w 15.099
      // 1a62: ldc 3.7
      // 1a64: ldc_w -11.439
      // 1a67: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a6a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a6d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a70: aastore
      // 1a71: dup
      // 1a72: bipush 47
      // 1a74: new net/minecraft/client/animation/Keyframe
      // 1a77: dup
      // 1a78: ldc 1.96
      // 1a7a: ldc_w 23.258
      // 1a7d: ldc_w 0.714
      // 1a80: ldc_w -10.67
      // 1a83: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a86: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a89: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a8c: aastore
      // 1a8d: dup
      // 1a8e: bipush 48
      // 1a90: new net/minecraft/client/animation/Keyframe
      // 1a93: dup
      // 1a94: fconst_2
      // 1a95: ldc_w 26.98
      // 1a98: ldc_w -1.702
      // 1a9b: ldc_w -9.698
      // 1a9e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1aa1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1aa4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1aa7: aastore
      // 1aa8: dup
      // 1aa9: bipush 49
      // 1aab: new net/minecraft/client/animation/Keyframe
      // 1aae: dup
      // 1aaf: ldc 2.04
      // 1ab1: ldc_w 26.264
      // 1ab4: ldc_w -3.42
      // 1ab7: ldc_w -8.616
      // 1aba: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1abd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ac0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ac3: aastore
      // 1ac4: dup
      // 1ac5: bipush 50
      // 1ac7: new net/minecraft/client/animation/Keyframe
      // 1aca: dup
      // 1acb: ldc 2.08
      // 1acd: ldc_w 23.933
      // 1ad0: ldc_w -4.37
      // 1ad3: ldc_w -7.792
      // 1ad6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ad9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1adc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1adf: aastore
      // 1ae0: dup
      // 1ae1: bipush 51
      // 1ae3: new net/minecraft/client/animation/Keyframe
      // 1ae6: dup
      // 1ae7: ldc 2.12
      // 1ae9: ldc_w 20.916
      // 1aec: ldc_w -4.818
      // 1aef: ldc_w -7.116
      // 1af2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1af5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1af8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1afb: aastore
      // 1afc: dup
      // 1afd: bipush 52
      // 1aff: new net/minecraft/client/animation/Keyframe
      // 1b02: dup
      // 1b03: ldc 2.17
      // 1b05: ldc_w 17.912
      // 1b08: ldc_w -4.951
      // 1b0b: ldc_w -6.592
      // 1b0e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b11: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b14: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b17: aastore
      // 1b18: dup
      // 1b19: bipush 53
      // 1b1b: new net/minecraft/client/animation/Keyframe
      // 1b1e: dup
      // 1b1f: ldc 2.21
      // 1b21: ldc_w 15.524
      // 1b24: ldc_w -4.925
      // 1b27: ldc_w -6.253
      // 1b2a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b2d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b30: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b33: aastore
      // 1b34: dup
      // 1b35: bipush 54
      // 1b37: new net/minecraft/client/animation/Keyframe
      // 1b3a: dup
      // 1b3b: ldc 2.25
      // 1b3d: ldc_w 13.54
      // 1b40: ldc_w -4.818
      // 1b43: ldc_w -6.033
      // 1b46: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b49: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b4c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b4f: aastore
      // 1b50: dup
      // 1b51: bipush 55
      // 1b53: new net/minecraft/client/animation/Keyframe
      // 1b56: dup
      // 1b57: ldc 2.33
      // 1b59: ldc_w 9.221
      // 1b5c: ldc -4.432
      // 1b5e: ldc_w -5.666
      // 1b61: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b64: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b67: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b6a: aastore
      // 1b6b: dup
      // 1b6c: bipush 56
      // 1b6e: new net/minecraft/client/animation/Keyframe
      // 1b71: dup
      // 1b72: ldc 2.38
      // 1b74: ldc_w 7.011
      // 1b77: ldc_w -4.226
      // 1b7a: ldc_w -5.505
      // 1b7d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b80: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b83: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b86: aastore
      // 1b87: dup
      // 1b88: bipush 57
      // 1b8a: new net/minecraft/client/animation/Keyframe
      // 1b8d: dup
      // 1b8e: ldc_w 2.42
      // 1b91: ldc_w 4.86
      // 1b94: ldc_w -4.054
      // 1b97: ldc_w -5.339
      // 1b9a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b9d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ba0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ba3: aastore
      // 1ba4: dup
      // 1ba5: bipush 58
      // 1ba7: new net/minecraft/client/animation/Keyframe
      // 1baa: dup
      // 1bab: ldc_w 2.46
      // 1bae: ldc_w 2.869
      // 1bb1: ldc_w -3.947
      // 1bb4: ldc_w -5.14
      // 1bb7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1bba: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1bbd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1bc0: aastore
      // 1bc1: dup
      // 1bc2: bipush 59
      // 1bc4: new net/minecraft/client/animation/Keyframe
      // 1bc7: dup
      // 1bc8: ldc_w 2.5
      // 1bcb: ldc_w 1.223
      // 1bce: ldc_w -3.938
      // 1bd1: ldc_w -4.862
      // 1bd4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1bd7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1bda: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1bdd: aastore
      // 1bde: dup
      // 1bdf: bipush 60
      // 1be1: new net/minecraft/client/animation/Keyframe
      // 1be4: dup
      // 1be5: ldc 2.54
      // 1be7: ldc_w 0.412
      // 1bea: ldc_w -4.079
      // 1bed: ldc -4.432
      // 1bef: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1bf2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1bf5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1bf8: aastore
      // 1bf9: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1bfc: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1bff: ldc_w "CTR_head"
      // 1c02: new net/minecraft/client/animation/AnimationChannel
      // 1c05: dup
      // 1c06: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1c09: bipush 1
      // 1c0a: anewarray 28
      // 1c0d: dup
      // 1c0e: bipush 0
      // 1c0f: new net/minecraft/client/animation/Keyframe
      // 1c12: dup
      // 1c13: ldc 1.46
      // 1c15: fconst_0
      // 1c16: fconst_0
      // 1c17: fconst_0
      // 1c18: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c1b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c1e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c21: aastore
      // 1c22: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1c25: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1c28: ldc_w "CTR_shoulde1r_left"
      // 1c2b: new net/minecraft/client/animation/AnimationChannel
      // 1c2e: dup
      // 1c2f: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1c32: bipush 47
      // 1c34: anewarray 28
      // 1c37: dup
      // 1c38: bipush 0
      // 1c39: new net/minecraft/client/animation/Keyframe
      // 1c3c: dup
      // 1c3d: fconst_0
      // 1c3e: ldc_w -0.112
      // 1c41: ldc_w -0.19
      // 1c44: ldc_w -0.487
      // 1c47: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c4a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c4d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c50: aastore
      // 1c51: dup
      // 1c52: bipush 1
      // 1c53: new net/minecraft/client/animation/Keyframe
      // 1c56: dup
      // 1c57: ldc 0.04
      // 1c59: ldc_w 0.106
      // 1c5c: ldc_w 0.356
      // 1c5f: ldc_w -1.185
      // 1c62: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c65: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c68: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c6b: aastore
      // 1c6c: dup
      // 1c6d: bipush 2
      // 1c6e: new net/minecraft/client/animation/Keyframe
      // 1c71: dup
      // 1c72: ldc 0.08
      // 1c74: ldc_w 0.627
      // 1c77: ldc_w 1.659
      // 1c7a: ldc_w -2.985
      // 1c7d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c80: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c83: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c86: aastore
      // 1c87: dup
      // 1c88: bipush 3
      // 1c89: new net/minecraft/client/animation/Keyframe
      // 1c8c: dup
      // 1c8d: ldc 0.12
      // 1c8f: ldc_w 1.248
      // 1c92: ldc_w 3.214
      // 1c95: ldc_w -5.445
      // 1c98: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c9b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c9e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ca1: aastore
      // 1ca2: dup
      // 1ca3: bipush 4
      // 1ca4: new net/minecraft/client/animation/Keyframe
      // 1ca7: dup
      // 1ca8: ldc 0.17
      // 1caa: ldc_w 1.769
      // 1cad: ldc_w 4.516
      // 1cb0: ldc_w -8.126
      // 1cb3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1cb6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1cb9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1cbc: aastore
      // 1cbd: dup
      // 1cbe: bipush 5
      // 1cbf: new net/minecraft/client/animation/Keyframe
      // 1cc2: dup
      // 1cc3: ldc 0.21
      // 1cc5: ldc_w 1.987
      // 1cc8: ldc_w 5.062
      // 1ccb: ldc_w -10.586
      // 1cce: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1cd1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1cd4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1cd7: aastore
      // 1cd8: dup
      // 1cd9: bipush 6
      // 1cdb: new net/minecraft/client/animation/Keyframe
      // 1cde: dup
      // 1cdf: ldc 0.25
      // 1ce1: ldc_w 1.987
      // 1ce4: ldc_w 4.931
      // 1ce7: ldc_w -12.611
      // 1cea: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1ced: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1cf0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1cf3: aastore
      // 1cf4: dup
      // 1cf5: bipush 7
      // 1cf7: new net/minecraft/client/animation/Keyframe
      // 1cfa: dup
      // 1cfb: ldc 0.29
      // 1cfd: ldc_w 1.987
      // 1d00: ldc_w 4.567
      // 1d03: ldc_w -14.299
      // 1d06: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d09: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d0c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d0f: aastore
      // 1d10: dup
      // 1d11: bipush 8
      // 1d13: new net/minecraft/client/animation/Keyframe
      // 1d16: dup
      // 1d17: ldc 0.33
      // 1d19: ldc_w 1.987
      // 1d1c: ldc_w 4.018
      // 1d1f: ldc_w -15.68
      // 1d22: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d25: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d28: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d2b: aastore
      // 1d2c: dup
      // 1d2d: bipush 9
      // 1d2f: new net/minecraft/client/animation/Keyframe
      // 1d32: dup
      // 1d33: ldc 0.38
      // 1d35: ldc_w 1.987
      // 1d38: ldc_w 3.33
      // 1d3b: ldc_w -16.787
      // 1d3e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d41: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d44: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d47: aastore
      // 1d48: dup
      // 1d49: bipush 10
      // 1d4b: new net/minecraft/client/animation/Keyframe
      // 1d4e: dup
      // 1d4f: ldc 0.42
      // 1d51: ldc_w 1.987
      // 1d54: ldc_w 2.548
      // 1d57: ldc_w -17.649
      // 1d5a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d5d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d60: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d63: aastore
      // 1d64: dup
      // 1d65: bipush 11
      // 1d67: new net/minecraft/client/animation/Keyframe
      // 1d6a: dup
      // 1d6b: ldc 0.46
      // 1d6d: ldc_w 1.987
      // 1d70: ldc_w 1.721
      // 1d73: ldc_w -18.296
      // 1d76: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d79: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d7c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d7f: aastore
      // 1d80: dup
      // 1d81: bipush 12
      // 1d83: new net/minecraft/client/animation/Keyframe
      // 1d86: dup
      // 1d87: ldc 0.5
      // 1d89: ldc_w 1.986
      // 1d8c: ldc_w 0.893
      // 1d8f: ldc_w -18.76
      // 1d92: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d95: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d98: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d9b: aastore
      // 1d9c: dup
      // 1d9d: bipush 13
      // 1d9f: new net/minecraft/client/animation/Keyframe
      // 1da2: dup
      // 1da3: ldc_w 0.54
      // 1da6: ldc_w 1.986
      // 1da9: ldc_w 0.112
      // 1dac: ldc_w -19.071
      // 1daf: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1db2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1db5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1db8: aastore
      // 1db9: dup
      // 1dba: bipush 14
      // 1dbc: new net/minecraft/client/animation/Keyframe
      // 1dbf: dup
      // 1dc0: ldc 0.58
      // 1dc2: ldc_w 1.985
      // 1dc5: ldc_w -0.577
      // 1dc8: ldc_w -19.26
      // 1dcb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1dce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1dd1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1dd4: aastore
      // 1dd5: dup
      // 1dd6: bipush 15
      // 1dd8: new net/minecraft/client/animation/Keyframe
      // 1ddb: dup
      // 1ddc: ldc 0.62
      // 1dde: ldc_w 1.984
      // 1de1: ldc_w -1.126
      // 1de4: ldc_w -19.357
      // 1de7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1dea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ded: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1df0: aastore
      // 1df1: dup
      // 1df2: bipush 16
      // 1df4: new net/minecraft/client/animation/Keyframe
      // 1df7: dup
      // 1df8: ldc 0.67
      // 1dfa: ldc_w 1.983
      // 1dfd: ldc_w -1.49
      // 1e00: ldc_w -19.393
      // 1e03: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e06: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e09: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e0c: aastore
      // 1e0d: dup
      // 1e0e: bipush 17
      // 1e10: new net/minecraft/client/animation/Keyframe
      // 1e13: dup
      // 1e14: ldc 0.71
      // 1e16: ldc_w 1.981
      // 1e19: ldc_w -1.621
      // 1e1c: ldc_w -19.398
      // 1e1f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e22: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e25: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e28: aastore
      // 1e29: dup
      // 1e2a: bipush 18
      // 1e2c: new net/minecraft/client/animation/Keyframe
      // 1e2f: dup
      // 1e30: ldc 0.75
      // 1e32: ldc_w 1.796
      // 1e35: ldc_w -1.102
      // 1e38: ldc_w -19.089
      // 1e3b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e3e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e41: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e44: aastore
      // 1e45: dup
      // 1e46: bipush 19
      // 1e48: new net/minecraft/client/animation/Keyframe
      // 1e4b: dup
      // 1e4c: ldc_w 0.79
      // 1e4f: ldc_w 1.812
      // 1e52: ldc_w -1.366
      // 1e55: ldc_w -18.564
      // 1e58: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e5b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e5e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e61: aastore
      // 1e62: dup
      // 1e63: bipush 20
      // 1e65: new net/minecraft/client/animation/Keyframe
      // 1e68: dup
      // 1e69: ldc 0.83
      // 1e6b: ldc_w 1.854
      // 1e6e: ldc_w -2.062
      // 1e71: ldc_w -17.985
      // 1e74: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e77: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e7a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e7d: aastore
      // 1e7e: dup
      // 1e7f: bipush 21
      // 1e81: new net/minecraft/client/animation/Keyframe
      // 1e84: dup
      // 1e85: ldc 0.88
      // 1e87: ldc_w 1.913
      // 1e8a: ldc_w -3.046
      // 1e8d: ldc_w -17.391
      // 1e90: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e93: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e96: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e99: aastore
      // 1e9a: dup
      // 1e9b: bipush 22
      // 1e9d: new net/minecraft/client/animation/Keyframe
      // 1ea0: dup
      // 1ea1: ldc 0.92
      // 1ea3: ldc_w 1.981
      // 1ea6: ldc_w -4.174
      // 1ea9: ldc_w -16.819
      // 1eac: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1eaf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1eb2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1eb5: aastore
      // 1eb6: dup
      // 1eb7: bipush 23
      // 1eb9: new net/minecraft/client/animation/Keyframe
      // 1ebc: dup
      // 1ebd: ldc 0.96
      // 1ebf: ldc_w 2.049
      // 1ec2: ldc_w -5.303
      // 1ec5: ldc_w -16.307
      // 1ec8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1ecb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ece: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ed1: aastore
      // 1ed2: dup
      // 1ed3: bipush 24
      // 1ed5: new net/minecraft/client/animation/Keyframe
      // 1ed8: dup
      // 1ed9: fconst_1
      // 1eda: ldc_w 2.108
      // 1edd: ldc_w -6.287
      // 1ee0: ldc_w -15.895
      // 1ee3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1ee6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ee9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1eec: aastore
      // 1eed: dup
      // 1eee: bipush 25
      // 1ef0: new net/minecraft/client/animation/Keyframe
      // 1ef3: dup
      // 1ef4: ldc 1.04
      // 1ef6: ldc_w 2.15
      // 1ef9: ldc_w -6.983
      // 1efc: ldc_w -15.62
      // 1eff: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1f02: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f05: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f08: aastore
      // 1f09: dup
      // 1f0a: bipush 26
      // 1f0c: new net/minecraft/client/animation/Keyframe
      // 1f0f: dup
      // 1f10: ldc 1.08
      // 1f12: ldc_w 2.166
      // 1f15: ldc_w -7.247
      // 1f18: ldc_w -15.519
      // 1f1b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1f1e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f21: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f24: aastore
      // 1f25: dup
      // 1f26: bipush 27
      // 1f28: new net/minecraft/client/animation/Keyframe
      // 1f2b: dup
      // 1f2c: ldc 1.75
      // 1f2e: ldc_w 2.166
      // 1f31: ldc_w -7.247
      // 1f34: ldc_w -15.519
      // 1f37: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1f3a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f3d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f40: aastore
      // 1f41: dup
      // 1f42: bipush 28
      // 1f44: new net/minecraft/client/animation/Keyframe
      // 1f47: dup
      // 1f48: ldc 1.79
      // 1f4a: ldc_w 2.167
      // 1f4d: ldc_w -5.798
      // 1f50: ldc_w -15.474
      // 1f53: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1f56: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f59: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f5c: aastore
      // 1f5d: dup
      // 1f5e: bipush 29
      // 1f60: new net/minecraft/client/animation/Keyframe
      // 1f63: dup
      // 1f64: ldc_w 1.83
      // 1f67: ldc_w 2.178
      // 1f6a: ldc_w -2.609
      // 1f6d: ldc_w -15.228
      // 1f70: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1f73: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f76: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f79: aastore
      // 1f7a: dup
      // 1f7b: bipush 30
      // 1f7d: new net/minecraft/client/animation/Keyframe
      // 1f80: dup
      // 1f81: ldc 1.88
      // 1f83: ldc_w 2.205
      // 1f86: ldc_w 0.579
      // 1f89: ldc_w -14.617
      // 1f8c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1f8f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f92: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f95: aastore
      // 1f96: dup
      // 1f97: bipush 31
      // 1f99: new net/minecraft/client/animation/Keyframe
      // 1f9c: dup
      // 1f9d: ldc_w 1.92
      // 1fa0: ldc_w 2.26
      // 1fa3: ldc_w 2.029
      // 1fa6: ldc_w -13.476
      // 1fa9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1fac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1faf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1fb2: aastore
      // 1fb3: dup
      // 1fb4: bipush 32
      // 1fb6: new net/minecraft/client/animation/Keyframe
      // 1fb9: dup
      // 1fba: ldc 1.96
      // 1fbc: ldc_w 2.377
      // 1fbf: ldc_w 1.385
      // 1fc2: ldc_w -11.718
      // 1fc5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1fc8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1fcb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1fce: aastore
      // 1fcf: dup
      // 1fd0: bipush 33
      // 1fd2: new net/minecraft/client/animation/Keyframe
      // 1fd5: dup
      // 1fd6: fconst_2
      // 1fd7: ldc_w 2.562
      // 1fda: ldc_w -0.224
      // 1fdd: ldc_w -9.566
      // 1fe0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1fe3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1fe6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1fe9: aastore
      // 1fea: dup
      // 1feb: bipush 34
      // 1fed: new net/minecraft/client/animation/Keyframe
      // 1ff0: dup
      // 1ff1: ldc 2.04
      // 1ff3: ldc_w 2.775
      // 1ff6: ldc_w -2.315
      // 1ff9: ldc_w -7.319
      // 1ffc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1fff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2002: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2005: aastore
      // 2006: dup
      // 2007: bipush 35
      // 2009: new net/minecraft/client/animation/Keyframe
      // 200c: dup
      // 200d: ldc 2.08
      // 200f: ldc_w 2.976
      // 2012: ldc_w -4.407
      // 2015: ldc_w -5.277
      // 2018: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 201b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 201e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2021: aastore
      // 2022: dup
      // 2023: bipush 36
      // 2025: new net/minecraft/client/animation/Keyframe
      // 2028: dup
      // 2029: ldc 2.12
      // 202b: ldc_w 3.126
      // 202e: ldc_w -6.016
      // 2031: ldc_w -3.74
      // 2034: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2037: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 203a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 203d: aastore
      // 203e: dup
      // 203f: bipush 37
      // 2041: new net/minecraft/client/animation/Keyframe
      // 2044: dup
      // 2045: ldc 2.17
      // 2047: ldc_w 3.185
      // 204a: ldc_w -6.66
      // 204d: ldc_w -3.008
      // 2050: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2053: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2056: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2059: aastore
      // 205a: dup
      // 205b: bipush 38
      // 205d: new net/minecraft/client/animation/Keyframe
      // 2060: dup
      // 2061: ldc 2.21
      // 2063: ldc_w 3.172
      // 2066: ldc_w -5.919
      // 2069: ldc_w -2.889
      // 206c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 206f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2072: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2075: aastore
      // 2076: dup
      // 2077: bipush 39
      // 2079: new net/minecraft/client/animation/Keyframe
      // 207c: dup
      // 207d: ldc 2.25
      // 207f: ldc_w 3.08
      // 2082: ldc_w -4.304
      // 2085: ldc_w -2.872
      // 2088: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 208b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 208e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2091: aastore
      // 2092: dup
      // 2093: bipush 40
      // 2095: new net/minecraft/client/animation/Keyframe
      // 2098: dup
      // 2099: ldc 2.29
      // 209b: ldc_w 2.832
      // 209e: ldc_w -2.72
      // 20a1: ldc_w -2.778
      // 20a4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 20a7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20aa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20ad: aastore
      // 20ae: dup
      // 20af: bipush 41
      // 20b1: new net/minecraft/client/animation/Keyframe
      // 20b4: dup
      // 20b5: ldc 2.33
      // 20b7: ldc_w 2.37
      // 20ba: ldc_w -1.654
      // 20bd: ldc_w -2.476
      // 20c0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 20c3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20c6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20c9: aastore
      // 20ca: dup
      // 20cb: bipush 42
      // 20cd: new net/minecraft/client/animation/Keyframe
      // 20d0: dup
      // 20d1: ldc 2.38
      // 20d3: ldc_w 1.756
      // 20d6: ldc_w -0.939
      // 20d9: ldc_w -2.015
      // 20dc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 20df: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20e2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20e5: aastore
      // 20e6: dup
      // 20e7: bipush 43
      // 20e9: new net/minecraft/client/animation/Keyframe
      // 20ec: dup
      // 20ed: ldc_w 2.42
      // 20f0: ldc_w 1.096
      // 20f3: ldc_w -0.506
      // 20f6: ldc_w -1.49
      // 20f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 20fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2102: aastore
      // 2103: dup
      // 2104: bipush 44
      // 2106: new net/minecraft/client/animation/Keyframe
      // 2109: dup
      // 210a: ldc_w 2.46
      // 210d: ldc 0.495
      // 210f: ldc_w -0.283
      // 2112: ldc_w -0.996
      // 2115: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2118: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 211b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 211e: aastore
      // 211f: dup
      // 2120: bipush 45
      // 2122: new net/minecraft/client/animation/Keyframe
      // 2125: dup
      // 2126: ldc_w 2.5
      // 2129: ldc_w 0.057
      // 212c: ldc_w -0.201
      // 212f: ldc_w -0.63
      // 2132: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2135: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2138: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 213b: aastore
      // 213c: dup
      // 213d: bipush 46
      // 213f: new net/minecraft/client/animation/Keyframe
      // 2142: dup
      // 2143: ldc 2.54
      // 2145: ldc_w -0.112
      // 2148: ldc_w -0.19
      // 214b: ldc_w -0.487
      // 214e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2151: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2154: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2157: aastore
      // 2158: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 215b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 215e: ldc_w "CTR_upperarm_left"
      // 2161: new net/minecraft/client/animation/AnimationChannel
      // 2164: dup
      // 2165: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2168: bipush 44
      // 216a: anewarray 28
      // 216d: dup
      // 216e: bipush 0
      // 216f: new net/minecraft/client/animation/Keyframe
      // 2172: dup
      // 2173: fconst_0
      // 2174: ldc_w -1.675
      // 2177: ldc_w -2.702
      // 217a: ldc_w -0.074
      // 217d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2180: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2183: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2186: aastore
      // 2187: dup
      // 2188: bipush 1
      // 2189: new net/minecraft/client/animation/Keyframe
      // 218c: dup
      // 218d: ldc 0.04
      // 218f: ldc_w -2.388
      // 2192: ldc_w -1.207
      // 2195: ldc_w 1.986
      // 2198: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 219b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 219e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21a1: aastore
      // 21a2: dup
      // 21a3: bipush 2
      // 21a4: new net/minecraft/client/animation/Keyframe
      // 21a7: dup
      // 21a8: ldc 0.08
      // 21aa: ldc_w -3.689
      // 21ad: ldc_w 2.459
      // 21b0: ldc_w 6.585
      // 21b3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 21b6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21b9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21bc: aastore
      // 21bd: dup
      // 21be: bipush 3
      // 21bf: new net/minecraft/client/animation/Keyframe
      // 21c2: dup
      // 21c3: ldc 0.12
      // 21c5: ldc_w -4.596
      // 21c8: ldc_w 7.296
      // 21cb: ldc_w 11.779
      // 21ce: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 21d1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21d4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21d7: aastore
      // 21d8: dup
      // 21d9: bipush 4
      // 21da: new net/minecraft/client/animation/Keyframe
      // 21dd: dup
      // 21de: ldc 0.17
      // 21e0: ldc_w -4.681
      // 21e3: ldc_w 12.591
      // 21e6: ldc_w 16.197
      // 21e9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 21ec: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21ef: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21f2: aastore
      // 21f3: dup
      // 21f4: bipush 5
      // 21f5: new net/minecraft/client/animation/Keyframe
      // 21f8: dup
      // 21f9: ldc 0.21
      // 21fb: ldc_w -2.213
      // 21fe: ldc_w 14.59
      // 2201: ldc_w 22.314
      // 2204: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2207: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 220a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 220d: aastore
      // 220e: dup
      // 220f: bipush 6
      // 2211: new net/minecraft/client/animation/Keyframe
      // 2214: dup
      // 2215: ldc 0.25
      // 2217: ldc_w 1.74
      // 221a: ldc_w 12.991
      // 221d: ldc_w 26.638
      // 2220: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2223: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2226: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2229: aastore
      // 222a: dup
      // 222b: bipush 7
      // 222d: new net/minecraft/client/animation/Keyframe
      // 2230: dup
      // 2231: ldc 0.29
      // 2233: ldc_w 3.787
      // 2236: ldc_w 15.533
      // 2239: ldc_w 27.31
      // 223c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 223f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2242: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2245: aastore
      // 2246: dup
      // 2247: bipush 8
      // 2249: new net/minecraft/client/animation/Keyframe
      // 224c: dup
      // 224d: ldc 0.33
      // 224f: ldc_w 3.431
      // 2252: ldc_w 22.039
      // 2255: ldc_w 27.484
      // 2258: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 225b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 225e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2261: aastore
      // 2262: dup
      // 2263: bipush 9
      // 2265: new net/minecraft/client/animation/Keyframe
      // 2268: dup
      // 2269: ldc 0.38
      // 226b: ldc_w 1.776
      // 226e: ldc_w 25.954
      // 2271: ldc_w 26.229
      // 2274: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2277: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 227a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 227d: aastore
      // 227e: dup
      // 227f: bipush 10
      // 2281: new net/minecraft/client/animation/Keyframe
      // 2284: dup
      // 2285: ldc 0.42
      // 2287: ldc_w -1.295
      // 228a: ldc_w 25.982
      // 228d: ldc_w 23.764
      // 2290: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2293: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2296: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2299: aastore
      // 229a: dup
      // 229b: bipush 11
      // 229d: new net/minecraft/client/animation/Keyframe
      // 22a0: dup
      // 22a1: ldc 0.46
      // 22a3: ldc_w -4.007
      // 22a6: ldc_w 23.452
      // 22a9: ldc_w 21.539
      // 22ac: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22af: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22b2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22b5: aastore
      // 22b6: dup
      // 22b7: bipush 12
      // 22b9: new net/minecraft/client/animation/Keyframe
      // 22bc: dup
      // 22bd: ldc 0.5
      // 22bf: ldc_w -8.53
      // 22c2: ldc_w 22.482
      // 22c5: ldc_w 23.133
      // 22c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22d1: aastore
      // 22d2: dup
      // 22d3: bipush 13
      // 22d5: new net/minecraft/client/animation/Keyframe
      // 22d8: dup
      // 22d9: ldc_w 0.54
      // 22dc: ldc_w -18.314
      // 22df: ldc_w 21.882
      // 22e2: ldc_w 23.968
      // 22e5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22e8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22eb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22ee: aastore
      // 22ef: dup
      // 22f0: bipush 14
      // 22f2: new net/minecraft/client/animation/Keyframe
      // 22f5: dup
      // 22f6: ldc 0.58
      // 22f8: ldc_w -31.215
      // 22fb: ldc_w 14.572
      // 22fe: ldc_w 18.86
      // 2301: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2304: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2307: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 230a: aastore
      // 230b: dup
      // 230c: bipush 15
      // 230e: new net/minecraft/client/animation/Keyframe
      // 2311: dup
      // 2312: ldc 0.62
      // 2314: ldc_w -41.204
      // 2317: ldc_w -1.496
      // 231a: ldc_w 13.931
      // 231d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2320: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2323: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2326: aastore
      // 2327: dup
      // 2328: bipush 16
      // 232a: new net/minecraft/client/animation/Keyframe
      // 232d: dup
      // 232e: ldc 0.67
      // 2330: ldc_w -43.747
      // 2333: ldc_w -22.866
      // 2336: ldc_w 11.493
      // 2339: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 233c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 233f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2342: aastore
      // 2343: dup
      // 2344: bipush 17
      // 2346: new net/minecraft/client/animation/Keyframe
      // 2349: dup
      // 234a: ldc 0.71
      // 234c: ldc_w -32.733
      // 234f: ldc_w -33.037
      // 2352: ldc_w -2.881
      // 2355: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2358: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 235b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 235e: aastore
      // 235f: dup
      // 2360: bipush 18
      // 2362: new net/minecraft/client/animation/Keyframe
      // 2365: dup
      // 2366: ldc 0.75
      // 2368: ldc_w -34.488
      // 236b: ldc_w -18.878
      // 236e: ldc_w -4.88
      // 2371: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2374: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2377: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 237a: aastore
      // 237b: dup
      // 237c: bipush 19
      // 237e: new net/minecraft/client/animation/Keyframe
      // 2381: dup
      // 2382: ldc_w 0.79
      // 2385: ldc_w -36.976
      // 2388: ldc_w -12.622
      // 238b: ldc_w -4.849
      // 238e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2391: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2394: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2397: aastore
      // 2398: dup
      // 2399: bipush 20
      // 239b: new net/minecraft/client/animation/Keyframe
      // 239e: dup
      // 239f: ldc 0.83
      // 23a1: ldc_w -38.32
      // 23a4: ldc_w -9.171
      // 23a7: ldc_w -5.876
      // 23aa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23ad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23b0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23b3: aastore
      // 23b4: dup
      // 23b5: bipush 21
      // 23b7: new net/minecraft/client/animation/Keyframe
      // 23ba: dup
      // 23bb: ldc 0.88
      // 23bd: ldc_w -40.268
      // 23c0: ldc_w -4.395
      // 23c3: ldc_w -7.149
      // 23c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23cf: aastore
      // 23d0: dup
      // 23d1: bipush 22
      // 23d3: new net/minecraft/client/animation/Keyframe
      // 23d6: dup
      // 23d7: ldc 0.92
      // 23d9: ldc_w -41.133
      // 23dc: ldc_w 10.339
      // 23df: ldc_w -9.876
      // 23e2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23e5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23e8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23eb: aastore
      // 23ec: dup
      // 23ed: bipush 23
      // 23ef: new net/minecraft/client/animation/Keyframe
      // 23f2: dup
      // 23f3: ldc 0.96
      // 23f5: ldc_w -39.507
      // 23f8: ldc_w 40.36
      // 23fb: ldc_w -17.172
      // 23fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2401: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2404: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2407: aastore
      // 2408: dup
      // 2409: bipush 24
      // 240b: new net/minecraft/client/animation/Keyframe
      // 240e: dup
      // 240f: fconst_1
      // 2410: ldc_w -46.004
      // 2413: ldc_w 65.413
      // 2416: ldc_w -32.146
      // 2419: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 241c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 241f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2422: aastore
      // 2423: dup
      // 2424: bipush 25
      // 2426: new net/minecraft/client/animation/Keyframe
      // 2429: dup
      // 242a: ldc 1.04
      // 242c: ldc_w -52.193
      // 242f: ldc_w 54.961
      // 2432: ldc_w -48.895
      // 2435: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2438: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 243b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 243e: aastore
      // 243f: dup
      // 2440: bipush 26
      // 2442: new net/minecraft/client/animation/Keyframe
      // 2445: dup
      // 2446: ldc_w 1.27
      // 2449: ldc_w -36.299
      // 244c: ldc_w 57.6883
      // 244f: ldc_w -23.239
      // 2452: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2455: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2458: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 245b: aastore
      // 245c: dup
      // 245d: bipush 27
      // 245f: new net/minecraft/client/animation/Keyframe
      // 2462: dup
      // 2463: ldc 1.88
      // 2465: ldc_w -0.76
      // 2468: ldc_w 51.077
      // 246b: ldc_w 22.298
      // 246e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2471: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2474: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2477: aastore
      // 2478: dup
      // 2479: bipush 28
      // 247b: new net/minecraft/client/animation/Keyframe
      // 247e: dup
      // 247f: ldc_w 1.92
      // 2482: ldc_w 3.142
      // 2485: ldc_w 21.478
      // 2488: ldc_w 31.475
      // 248b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 248e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2491: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2494: aastore
      // 2495: dup
      // 2496: bipush 29
      // 2498: new net/minecraft/client/animation/Keyframe
      // 249b: dup
      // 249c: ldc 1.96
      // 249e: ldc_w 6.614
      // 24a1: ldc_w 0.89
      // 24a4: ldc_w 22.763
      // 24a7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24aa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24ad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24b0: aastore
      // 24b1: dup
      // 24b2: bipush 30
      // 24b4: new net/minecraft/client/animation/Keyframe
      // 24b7: dup
      // 24b8: fconst_2
      // 24b9: ldc_w 10.051
      // 24bc: ldc_w -6.738
      // 24bf: ldc_w 12.066
      // 24c2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24c5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24c8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24cb: aastore
      // 24cc: dup
      // 24cd: bipush 31
      // 24cf: new net/minecraft/client/animation/Keyframe
      // 24d2: dup
      // 24d3: ldc 2.04
      // 24d5: ldc_w 10.957
      // 24d8: ldc_w -8.766
      // 24db: ldc_w 3.939
      // 24de: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24e7: aastore
      // 24e8: dup
      // 24e9: bipush 32
      // 24eb: new net/minecraft/client/animation/Keyframe
      // 24ee: dup
      // 24ef: ldc 2.08
      // 24f1: ldc_w 12.865
      // 24f4: ldc_w -11.333
      // 24f7: ldc_w -6.227
      // 24fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2500: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2503: aastore
      // 2504: dup
      // 2505: bipush 33
      // 2507: new net/minecraft/client/animation/Keyframe
      // 250a: dup
      // 250b: ldc 2.12
      // 250d: ldc_w 15.718
      // 2510: ldc_w -13.91
      // 2513: ldc_w -17.809
      // 2516: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2519: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 251c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 251f: aastore
      // 2520: dup
      // 2521: bipush 34
      // 2523: new net/minecraft/client/animation/Keyframe
      // 2526: dup
      // 2527: ldc 2.17
      // 2529: ldc_w 18.964
      // 252c: ldc_w -15.997
      // 252f: ldc_w -29.308
      // 2532: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2535: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2538: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 253b: aastore
      // 253c: dup
      // 253d: bipush 35
      // 253f: new net/minecraft/client/animation/Keyframe
      // 2542: dup
      // 2543: ldc 2.21
      // 2545: ldc_w 17.917
      // 2548: ldc_w -15.875
      // 254b: ldc_w -29.551
      // 254e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2551: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2554: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2557: aastore
      // 2558: dup
      // 2559: bipush 36
      // 255b: new net/minecraft/client/animation/Keyframe
      // 255e: dup
      // 255f: ldc 2.25
      // 2561: ldc_w 15.203
      // 2564: ldc_w -14.838
      // 2567: ldc_w -25.897
      // 256a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 256d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2570: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2573: aastore
      // 2574: dup
      // 2575: bipush 37
      // 2577: new net/minecraft/client/animation/Keyframe
      // 257a: dup
      // 257b: ldc 2.29
      // 257d: ldc_w 12.886
      // 2580: ldc_w -14.068
      // 2583: ldc_w -24.057
      // 2586: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2589: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 258c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 258f: aastore
      // 2590: dup
      // 2591: bipush 38
      // 2593: new net/minecraft/client/animation/Keyframe
      // 2596: dup
      // 2597: ldc 2.33
      // 2599: ldc_w 10.904
      // 259c: ldc_w -13.784
      // 259f: ldc_w -24.195
      // 25a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 25a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25ab: aastore
      // 25ac: dup
      // 25ad: bipush 39
      // 25af: new net/minecraft/client/animation/Keyframe
      // 25b2: dup
      // 25b3: ldc 2.38
      // 25b5: ldc_w 6.907
      // 25b8: ldc_w -12.292
      // 25bb: ldc_w -20.61
      // 25be: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 25c1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25c4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25c7: aastore
      // 25c8: dup
      // 25c9: bipush 40
      // 25cb: new net/minecraft/client/animation/Keyframe
      // 25ce: dup
      // 25cf: ldc_w 2.42
      // 25d2: ldc_w 0.73
      // 25d5: ldc_w -8.48
      // 25d8: ldc_w -11.889
      // 25db: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 25de: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25e1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25e4: aastore
      // 25e5: dup
      // 25e6: bipush 41
      // 25e8: new net/minecraft/client/animation/Keyframe
      // 25eb: dup
      // 25ec: ldc_w 2.46
      // 25ef: ldc_w -4.682
      // 25f2: ldc_w -3.32
      // 25f5: ldc_w -1.556
      // 25f8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 25fb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25fe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2601: aastore
      // 2602: dup
      // 2603: bipush 42
      // 2605: new net/minecraft/client/animation/Keyframe
      // 2608: dup
      // 2609: ldc_w 2.5
      // 260c: ldc_w -4.533
      // 260f: ldc_w -1.285
      // 2612: ldc_w 2.906
      // 2615: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2618: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 261b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 261e: aastore
      // 261f: dup
      // 2620: bipush 43
      // 2622: new net/minecraft/client/animation/Keyframe
      // 2625: dup
      // 2626: ldc 2.54
      // 2628: ldc_w -1.675
      // 262b: ldc_w -2.702
      // 262e: ldc_w -0.074
      // 2631: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2634: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2637: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 263a: aastore
      // 263b: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 263e: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2641: ldc_w "CTR_lowerarm_left"
      // 2644: new net/minecraft/client/animation/AnimationChannel
      // 2647: dup
      // 2648: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 264b: bipush 49
      // 264d: anewarray 28
      // 2650: dup
      // 2651: bipush 0
      // 2652: new net/minecraft/client/animation/Keyframe
      // 2655: dup
      // 2656: fconst_0
      // 2657: ldc_w 0.994
      // 265a: ldc_w -2.337
      // 265d: ldc_w -0.568
      // 2660: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2663: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2666: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2669: aastore
      // 266a: dup
      // 266b: bipush 1
      // 266c: new net/minecraft/client/animation/Keyframe
      // 266f: dup
      // 2670: ldc 0.04
      // 2672: ldc_w 2.581
      // 2675: ldc_w -5.785
      // 2678: ldc_w -1.541
      // 267b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 267e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2681: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2684: aastore
      // 2685: dup
      // 2686: bipush 2
      // 2687: new net/minecraft/client/animation/Keyframe
      // 268a: dup
      // 268b: ldc 0.08
      // 268d: ldc_w 6.474
      // 2690: ldc_w -12.898
      // 2693: ldc_w -4.203
      // 2696: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2699: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 269c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 269f: aastore
      // 26a0: dup
      // 26a1: bipush 3
      // 26a2: new net/minecraft/client/animation/Keyframe
      // 26a5: dup
      // 26a6: ldc 0.12
      // 26a8: ldc_w 11.29
      // 26ab: ldc_w -19.554
      // 26ae: ldc_w -7.895
      // 26b1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 26b4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26b7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26ba: aastore
      // 26bb: dup
      // 26bc: bipush 4
      // 26bd: new net/minecraft/client/animation/Keyframe
      // 26c0: dup
      // 26c1: ldc 0.17
      // 26c3: ldc_w 14.631
      // 26c6: ldc_w -23.124
      // 26c9: ldc_w -10.634
      // 26cc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 26cf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26d2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26d5: aastore
      // 26d6: dup
      // 26d7: bipush 5
      // 26d8: new net/minecraft/client/animation/Keyframe
      // 26db: dup
      // 26dc: ldc 0.21
      // 26de: ldc_w 23.91
      // 26e1: ldc_w -30.059
      // 26e4: ldc_w -18.713
      // 26e7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 26ea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26ed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26f0: aastore
      // 26f1: dup
      // 26f2: bipush 6
      // 26f4: new net/minecraft/client/animation/Keyframe
      // 26f7: dup
      // 26f8: ldc 0.25
      // 26fa: ldc_w 42.778
      // 26fd: ldc_w -37.073
      // 2700: ldc_w -36.169
      // 2703: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2706: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2709: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 270c: aastore
      // 270d: dup
      // 270e: bipush 7
      // 2710: new net/minecraft/client/animation/Keyframe
      // 2713: dup
      // 2714: ldc 0.29
      // 2716: ldc_w 45.752
      // 2719: ldc_w -37.711
      // 271c: ldc_w -38.984
      // 271f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2722: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2725: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2728: aastore
      // 2729: dup
      // 272a: bipush 8
      // 272c: new net/minecraft/client/animation/Keyframe
      // 272f: dup
      // 2730: ldc 0.33
      // 2732: ldc_w 31.146
      // 2735: ldc -33.523
      // 2737: ldc_w -25.301
      // 273a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 273d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2740: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2743: aastore
      // 2744: dup
      // 2745: bipush 9
      // 2747: new net/minecraft/client/animation/Keyframe
      // 274a: dup
      // 274b: ldc 0.38
      // 274d: ldc_w 19.769
      // 2750: ldc_w -27.403
      // 2753: ldc_w -15.04
      // 2756: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2759: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 275c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 275f: aastore
      // 2760: dup
      // 2761: bipush 10
      // 2763: new net/minecraft/client/animation/Keyframe
      // 2766: dup
      // 2767: ldc 0.42
      // 2769: ldc_w 12.517
      // 276c: ldc_w -20.951
      // 276f: ldc_w -8.887
      // 2772: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2775: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2778: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 277b: aastore
      // 277c: dup
      // 277d: bipush 11
      // 277f: new net/minecraft/client/animation/Keyframe
      // 2782: dup
      // 2783: ldc 0.46
      // 2785: ldc_w 6.737
      // 2788: ldc_w -13.316
      // 278b: ldc -4.394
      // 278d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2790: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2793: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2796: aastore
      // 2797: dup
      // 2798: bipush 12
      // 279a: new net/minecraft/client/animation/Keyframe
      // 279d: dup
      // 279e: ldc 0.5
      // 27a0: ldc_w 8.303
      // 27a3: ldc_w -15.669
      // 27a6: ldc_w -5.562
      // 27a9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 27ac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27af: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27b2: aastore
      // 27b3: dup
      // 27b4: bipush 13
      // 27b6: new net/minecraft/client/animation/Keyframe
      // 27b9: dup
      // 27ba: ldc_w 0.54
      // 27bd: ldc_w 18.321
      // 27c0: ldc_w -26.322
      // 27c3: ldc_w -13.78
      // 27c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 27c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27cf: aastore
      // 27d0: dup
      // 27d1: bipush 14
      // 27d3: new net/minecraft/client/animation/Keyframe
      // 27d6: dup
      // 27d7: ldc 0.58
      // 27d9: ldc_w 21.525
      // 27dc: ldc_w -28.602
      // 27df: ldc_w -16.587
      // 27e2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 27e5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27e8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27eb: aastore
      // 27ec: dup
      // 27ed: bipush 15
      // 27ef: new net/minecraft/client/animation/Keyframe
      // 27f2: dup
      // 27f3: ldc 0.62
      // 27f5: ldc_w 9.377
      // 27f8: ldc_w -17.151
      // 27fb: ldc_w -6.386
      // 27fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2801: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2804: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2807: aastore
      // 2808: dup
      // 2809: bipush 16
      // 280b: new net/minecraft/client/animation/Keyframe
      // 280e: dup
      // 280f: ldc 0.67
      // 2811: ldc_w -3.96
      // 2814: ldc_w 10.745
      // 2817: ldc_w 1.888
      // 281a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 281d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2820: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2823: aastore
      // 2824: dup
      // 2825: bipush 17
      // 2827: new net/minecraft/client/animation/Keyframe
      // 282a: dup
      // 282b: ldc 0.71
      // 282d: ldc_w -10.767
      // 2830: ldc_w 33.627
      // 2833: ldc_w 3.275
      // 2836: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2839: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 283c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 283f: aastore
      // 2840: dup
      // 2841: bipush 18
      // 2843: new net/minecraft/client/animation/Keyframe
      // 2846: dup
      // 2847: ldc 0.75
      // 2849: ldc_w -3.599
      // 284c: ldc_w 9.669
      // 284f: ldc_w 1.744
      // 2852: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2855: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2858: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 285b: aastore
      // 285c: dup
      // 285d: bipush 19
      // 285f: new net/minecraft/client/animation/Keyframe
      // 2862: dup
      // 2863: ldc_w 0.79
      // 2866: ldc_w 1.49
      // 2869: ldc_w -3.452
      // 286c: ldc_w -0.864
      // 286f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2872: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2875: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2878: aastore
      // 2879: dup
      // 287a: bipush 20
      // 287c: new net/minecraft/client/animation/Keyframe
      // 287f: dup
      // 2880: ldc 0.83
      // 2882: ldc_w 5.325
      // 2885: ldc_w -10.98
      // 2888: ldc_w -3.381
      // 288b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 288e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2891: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2894: aastore
      // 2895: dup
      // 2896: bipush 21
      // 2898: new net/minecraft/client/animation/Keyframe
      // 289b: dup
      // 289c: ldc 0.88
      // 289e: ldc_w 12.774
      // 28a1: ldc_w -21.23
      // 28a4: ldc_w -9.097
      // 28a7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 28aa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28ad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28b0: aastore
      // 28b1: dup
      // 28b2: bipush 22
      // 28b4: new net/minecraft/client/animation/Keyframe
      // 28b7: dup
      // 28b8: ldc 0.92
      // 28ba: ldc_w 73.84
      // 28bd: ldc_w -43.103
      // 28c0: ldc_w -68.361
      // 28c3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 28c6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28c9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28cc: aastore
      // 28cd: dup
      // 28ce: bipush 23
      // 28d0: new net/minecraft/client/animation/Keyframe
      // 28d3: dup
      // 28d4: ldc 0.96
      // 28d6: ldc_w 153.642
      // 28d9: ldc_w -26.69
      // 28dc: ldc_w -150.331
      // 28df: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 28e2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28e5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28e8: aastore
      // 28e9: dup
      // 28ea: bipush 24
      // 28ec: new net/minecraft/client/animation/Keyframe
      // 28ef: dup
      // 28f0: fconst_1
      // 28f1: ldc_w 157.711
      // 28f4: ldc_w -31.924
      // 28f7: ldc_w -159.098
      // 28fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 28fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2900: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2903: aastore
      // 2904: dup
      // 2905: bipush 25
      // 2907: new net/minecraft/client/animation/Keyframe
      // 290a: dup
      // 290b: ldc 1.04
      // 290d: ldc_w 194.074
      // 2910: ldc_w -63.235
      // 2913: ldc_w -199.067
      // 2916: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2919: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 291c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 291f: aastore
      // 2920: dup
      // 2921: bipush 26
      // 2923: new net/minecraft/client/animation/Keyframe
      // 2926: dup
      // 2927: ldc 1.08
      // 2929: ldc_w 172.983
      // 292c: ldc_w -79.128
      // 292f: ldc_w -179.624
      // 2932: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2935: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2938: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 293b: aastore
      // 293c: dup
      // 293d: bipush 27
      // 293f: new net/minecraft/client/animation/Keyframe
      // 2942: dup
      // 2943: ldc_w 1.37
      // 2946: ldc_w 162.564
      // 2949: ldc_w -101.379
      // 294c: ldc_w -178.261
      // 294f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2952: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2955: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2958: aastore
      // 2959: dup
      // 295a: bipush 28
      // 295c: new net/minecraft/client/animation/Keyframe
      // 295f: dup
      // 2960: ldc_w 1.44
      // 2963: ldc_w 161.954
      // 2966: ldc_w -102.689
      // 2969: ldc_w -178.181
      // 296c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 296f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2972: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2975: aastore
      // 2976: dup
      // 2977: bipush 29
      // 2979: new net/minecraft/client/animation/Keyframe
      // 297c: dup
      // 297d: ldc_w 1.51
      // 2980: ldc_w 162.784
      // 2983: ldc_w -99.739
      // 2986: ldc_w -178.131
      // 2989: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 298c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 298f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2992: aastore
      // 2993: dup
      // 2994: bipush 30
      // 2996: new net/minecraft/client/animation/Keyframe
      // 2999: dup
      // 299a: ldc 1.79
      // 299c: ldc_w 169.219
      // 299f: ldc_w -76.911
      // 29a2: ldc_w -177.712
      // 29a5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 29a8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29ab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29ae: aastore
      // 29af: dup
      // 29b0: bipush 31
      // 29b2: new net/minecraft/client/animation/Keyframe
      // 29b5: dup
      // 29b6: ldc_w 1.83
      // 29b9: ldc_w 169.049
      // 29bc: ldc_w -53.052
      // 29bf: ldc_w -177.524
      // 29c2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 29c5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29c8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29cb: aastore
      // 29cc: dup
      // 29cd: bipush 32
      // 29cf: new net/minecraft/client/animation/Keyframe
      // 29d2: dup
      // 29d3: ldc 1.88
      // 29d5: ldc_w 169.137
      // 29d8: ldc_w -37.666
      // 29db: ldc_w -177.57
      // 29de: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 29e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29e7: aastore
      // 29e8: dup
      // 29e9: bipush 33
      // 29eb: new net/minecraft/client/animation/Keyframe
      // 29ee: dup
      // 29ef: ldc_w 1.92
      // 29f2: ldc_w 168.977
      // 29f5: ldc_w -42.974
      // 29f8: ldc_w -177.341
      // 29fb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 29fe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a01: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a04: aastore
      // 2a05: dup
      // 2a06: bipush 34
      // 2a08: new net/minecraft/client/animation/Keyframe
      // 2a0b: dup
      // 2a0c: ldc 1.96
      // 2a0e: ldc_w 169.884
      // 2a11: ldc_w -65.403
      // 2a14: ldc_w -178.159
      // 2a17: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a1a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a1d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a20: aastore
      // 2a21: dup
      // 2a22: bipush 35
      // 2a24: new net/minecraft/client/animation/Keyframe
      // 2a27: dup
      // 2a28: fconst_2
      // 2a29: ldc_w 169.559
      // 2a2c: ldc_w -85.219
      // 2a2f: ldc_w -177.72
      // 2a32: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a35: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a38: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a3b: aastore
      // 2a3c: dup
      // 2a3d: bipush 36
      // 2a3f: new net/minecraft/client/animation/Keyframe
      // 2a42: dup
      // 2a43: ldc 2.04
      // 2a45: ldc_w 169.61
      // 2a48: ldc_w -95.537
      // 2a4b: ldc_w -177.614
      // 2a4e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a51: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a54: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a57: aastore
      // 2a58: dup
      // 2a59: bipush 37
      // 2a5b: new net/minecraft/client/animation/Keyframe
      // 2a5e: dup
      // 2a5f: ldc 2.08
      // 2a61: ldc_w 169.621
      // 2a64: ldc_w -108.461
      // 2a67: ldc_w -177.465
      // 2a6a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a6d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a70: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a73: aastore
      // 2a74: dup
      // 2a75: bipush 38
      // 2a77: new net/minecraft/client/animation/Keyframe
      // 2a7a: dup
      // 2a7b: ldc 2.12
      // 2a7d: ldc_w 169.554
      // 2a80: ldc_w -123.629
      // 2a83: ldc_w -177.29
      // 2a86: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a89: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a8c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a8f: aastore
      // 2a90: dup
      // 2a91: bipush 39
      // 2a93: new net/minecraft/client/animation/Keyframe
      // 2a96: dup
      // 2a97: ldc 2.17
      // 2a99: ldc_w 169.38
      // 2a9c: ldc_w -138.581
      // 2a9f: ldc_w -177.131
      // 2aa2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2aa5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2aa8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2aab: aastore
      // 2aac: dup
      // 2aad: bipush 40
      // 2aaf: new net/minecraft/client/animation/Keyframe
      // 2ab2: dup
      // 2ab3: ldc 2.21
      // 2ab5: ldc_w 169.618
      // 2ab8: ldc_w -133.605
      // 2abb: ldc_w -177.072
      // 2abe: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ac1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ac4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ac7: aastore
      // 2ac8: dup
      // 2ac9: bipush 41
      // 2acb: new net/minecraft/client/animation/Keyframe
      // 2ace: dup
      // 2acf: ldc 2.25
      // 2ad1: ldc_w 170.373
      // 2ad4: ldc_w -122.109
      // 2ad7: ldc_w -177.194
      // 2ada: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2add: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ae0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ae3: aastore
      // 2ae4: dup
      // 2ae5: bipush 42
      // 2ae7: new net/minecraft/client/animation/Keyframe
      // 2aea: dup
      // 2aeb: ldc 2.29
      // 2aed: ldc_w 171.531
      // 2af0: ldc_w -113.927
      // 2af3: ldc_w -177.55
      // 2af6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2af9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2afc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2aff: aastore
      // 2b00: dup
      // 2b01: bipush 43
      // 2b03: new net/minecraft/client/animation/Keyframe
      // 2b06: dup
      // 2b07: ldc 2.33
      // 2b09: ldc_w 173.06
      // 2b0c: ldc_w -109.344
      // 2b0f: ldc_w -178.109
      // 2b12: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b15: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b18: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b1b: aastore
      // 2b1c: dup
      // 2b1d: bipush 44
      // 2b1f: new net/minecraft/client/animation/Keyframe
      // 2b22: dup
      // 2b23: ldc 2.38
      // 2b25: ldc_w 176.365
      // 2b28: ldc_w -100.951
      // 2b2b: ldc_w -179.774
      // 2b2e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b31: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b34: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b37: aastore
      // 2b38: dup
      // 2b39: bipush 45
      // 2b3b: new net/minecraft/client/animation/Keyframe
      // 2b3e: dup
      // 2b3f: ldc_w 2.42
      // 2b42: ldc_w 183.706
      // 2b45: ldc_w -89.88
      // 2b48: ldc_w -184.559
      // 2b4b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b4e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b51: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b54: aastore
      // 2b55: dup
      // 2b56: bipush 46
      // 2b58: new net/minecraft/client/animation/Keyframe
      // 2b5b: dup
      // 2b5c: ldc_w 2.46
      // 2b5f: ldc_w 192.993
      // 2b62: ldc_w -84.47
      // 2b65: ldc_w -191.111
      // 2b68: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b6b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b6e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b71: aastore
      // 2b72: dup
      // 2b73: bipush 47
      // 2b75: new net/minecraft/client/animation/Keyframe
      // 2b78: dup
      // 2b79: ldc_w 2.5
      // 2b7c: ldc_w 187.98
      // 2b7f: ldc_w -95.359
      // 2b82: ldc_w -185.73
      // 2b85: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b88: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b8b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b8e: aastore
      // 2b8f: dup
      // 2b90: bipush 48
      // 2b92: new net/minecraft/client/animation/Keyframe
      // 2b95: dup
      // 2b96: ldc 2.54
      // 2b98: ldc_w 180.994
      // 2b9b: ldc_w -110.272
      // 2b9e: ldc_w -180.568
      // 2ba1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ba4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ba7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2baa: aastore
      // 2bab: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2bae: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2bb1: ldc_w "CTR_upperarm_left.001"
      // 2bb4: new net/minecraft/client/animation/AnimationChannel
      // 2bb7: dup
      // 2bb8: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2bbb: bipush 62
      // 2bbd: anewarray 28
      // 2bc0: dup
      // 2bc1: bipush 0
      // 2bc2: new net/minecraft/client/animation/Keyframe
      // 2bc5: dup
      // 2bc6: fconst_0
      // 2bc7: ldc_w 3.204
      // 2bca: ldc_w -22.398
      // 2bcd: ldc_w -1.822
      // 2bd0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2bd3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2bd6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2bd9: aastore
      // 2bda: dup
      // 2bdb: bipush 1
      // 2bdc: new net/minecraft/client/animation/Keyframe
      // 2bdf: dup
      // 2be0: ldc 0.04
      // 2be2: ldc 3.795
      // 2be4: ldc_w -24.106
      // 2be7: ldc_w -1.995
      // 2bea: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2bed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2bf0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2bf3: aastore
      // 2bf4: dup
      // 2bf5: bipush 2
      // 2bf6: new net/minecraft/client/animation/Keyframe
      // 2bf9: dup
      // 2bfa: ldc 0.08
      // 2bfc: ldc_w 5.16
      // 2bff: ldc_w -27.665
      // 2c02: ldc_w -2.421
      // 2c05: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c08: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c0b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c0e: aastore
      // 2c0f: dup
      // 2c10: bipush 3
      // 2c11: new net/minecraft/client/animation/Keyframe
      // 2c14: dup
      // 2c15: ldc 0.12
      // 2c17: ldc_w 6.675
      // 2c1a: ldc_w -31.147
      // 2c1d: ldc_w -2.903
      // 2c20: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c23: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c26: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c29: aastore
      // 2c2a: dup
      // 2c2b: bipush 4
      // 2c2c: new net/minecraft/client/animation/Keyframe
      // 2c2f: dup
      // 2c30: ldc 0.17
      // 2c32: ldc_w 7.622
      // 2c35: ldc_w -33.22
      // 2c38: ldc_w -3.207
      // 2c3b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c3e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c41: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c44: aastore
      // 2c45: dup
      // 2c46: bipush 5
      // 2c47: new net/minecraft/client/animation/Keyframe
      // 2c4a: dup
      // 2c4b: ldc 0.21
      // 2c4d: ldc_w 9.524
      // 2c50: ldc_w -35.074
      // 2c53: ldc_w -3.65
      // 2c56: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c59: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c5c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c5f: aastore
      // 2c60: dup
      // 2c61: bipush 6
      // 2c63: new net/minecraft/client/animation/Keyframe
      // 2c66: dup
      // 2c67: ldc 0.25
      // 2c69: ldc_w 11.696
      // 2c6c: ldc_w -33.31
      // 2c6f: ldc_w -3.665
      // 2c72: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c75: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c78: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c7b: aastore
      // 2c7c: dup
      // 2c7d: bipush 7
      // 2c7f: new net/minecraft/client/animation/Keyframe
      // 2c82: dup
      // 2c83: ldc 0.29
      // 2c85: ldc_w 11.286
      // 2c88: ldc_w -28.668
      // 2c8b: ldc_w -3.152
      // 2c8e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c91: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c94: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c97: aastore
      // 2c98: dup
      // 2c99: bipush 8
      // 2c9b: new net/minecraft/client/animation/Keyframe
      // 2c9e: dup
      // 2c9f: ldc 0.33
      // 2ca1: ldc_w 9.049
      // 2ca4: ldc_w -24.063
      // 2ca7: ldc_w -2.732
      // 2caa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2cad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2cb0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2cb3: aastore
      // 2cb4: dup
      // 2cb5: bipush 9
      // 2cb7: new net/minecraft/client/animation/Keyframe
      // 2cba: dup
      // 2cbb: ldc 0.38
      // 2cbd: ldc_w 6.512
      // 2cc0: ldc_w -17.029
      // 2cc3: ldc_w -2.332
      // 2cc6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2cc9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ccc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ccf: aastore
      // 2cd0: dup
      // 2cd1: bipush 10
      // 2cd3: new net/minecraft/client/animation/Keyframe
      // 2cd6: dup
      // 2cd7: ldc 0.42
      // 2cd9: ldc_w 3.47
      // 2cdc: ldc_w -7.732
      // 2cdf: ldc_w -2.68
      // 2ce2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ce5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ce8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ceb: aastore
      // 2cec: dup
      // 2ced: bipush 11
      // 2cef: new net/minecraft/client/animation/Keyframe
      // 2cf2: dup
      // 2cf3: ldc 0.46
      // 2cf5: ldc_w 25.267
      // 2cf8: ldc_w 2.819
      // 2cfb: ldc_w 21.552
      // 2cfe: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d01: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d04: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d07: aastore
      // 2d08: dup
      // 2d09: bipush 12
      // 2d0b: new net/minecraft/client/animation/Keyframe
      // 2d0e: dup
      // 2d0f: ldc 0.5
      // 2d11: ldc_w 6.391
      // 2d14: ldc_w 9.504
      // 2d17: ldc_w 3.315
      // 2d1a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d1d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d20: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d23: aastore
      // 2d24: dup
      // 2d25: bipush 13
      // 2d27: new net/minecraft/client/animation/Keyframe
      // 2d2a: dup
      // 2d2b: ldc_w 0.54
      // 2d2e: ldc_w 5.439
      // 2d31: ldc_w 15.967
      // 2d34: ldc_w 2.587
      // 2d37: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d3a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d3d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d40: aastore
      // 2d41: dup
      // 2d42: bipush 14
      // 2d44: new net/minecraft/client/animation/Keyframe
      // 2d47: dup
      // 2d48: ldc 0.58
      // 2d4a: ldc_w 3.342
      // 2d4d: ldc_w 27.942
      // 2d50: ldc_w 3.129
      // 2d53: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d56: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d59: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d5c: aastore
      // 2d5d: dup
      // 2d5e: bipush 15
      // 2d60: new net/minecraft/client/animation/Keyframe
      // 2d63: dup
      // 2d64: ldc 0.62
      // 2d66: ldc_w 0.848
      // 2d69: ldc_w 42.353
      // 2d6c: ldc_w 3.651
      // 2d6f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d72: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d75: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d78: aastore
      // 2d79: dup
      // 2d7a: bipush 16
      // 2d7c: new net/minecraft/client/animation/Keyframe
      // 2d7f: dup
      // 2d80: ldc 0.67
      // 2d82: ldc_w -0.089
      // 2d85: ldc_w 56.818
      // 2d88: ldc_w 3.172
      // 2d8b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d8e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d91: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d94: aastore
      // 2d95: dup
      // 2d96: bipush 17
      // 2d98: new net/minecraft/client/animation/Keyframe
      // 2d9b: dup
      // 2d9c: ldc 0.71
      // 2d9e: ldc_w 1.667
      // 2da1: ldc_w 29.45
      // 2da4: ldc_w 2.027
      // 2da7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2daa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2dad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2db0: aastore
      // 2db1: dup
      // 2db2: bipush 18
      // 2db4: new net/minecraft/client/animation/Keyframe
      // 2db7: dup
      // 2db8: ldc 0.75
      // 2dba: ldc_w 2.457
      // 2dbd: ldc_w -28.314
      // 2dc0: ldc_w -1.44
      // 2dc3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2dc6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2dc9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2dcc: aastore
      // 2dcd: dup
      // 2dce: bipush 19
      // 2dd0: new net/minecraft/client/animation/Keyframe
      // 2dd3: dup
      // 2dd4: ldc_w 0.79
      // 2dd7: ldc_w 4.687
      // 2dda: ldc_w -34.465
      // 2ddd: ldc_w -2.537
      // 2de0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2de3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2de6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2de9: aastore
      // 2dea: dup
      // 2deb: bipush 20
      // 2ded: new net/minecraft/client/animation/Keyframe
      // 2df0: dup
      // 2df1: ldc 0.83
      // 2df3: ldc_w 6.22
      // 2df6: ldc_w -34.272
      // 2df9: ldc_w -3.259
      // 2dfc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2dff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e02: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e05: aastore
      // 2e06: dup
      // 2e07: bipush 21
      // 2e09: new net/minecraft/client/animation/Keyframe
      // 2e0c: dup
      // 2e0d: ldc 0.88
      // 2e0f: ldc_w 8.744
      // 2e12: ldc_w -31.277
      // 2e15: ldc_w -4.166
      // 2e18: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e1b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e1e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e21: aastore
      // 2e22: dup
      // 2e23: bipush 22
      // 2e25: new net/minecraft/client/animation/Keyframe
      // 2e28: dup
      // 2e29: ldc 0.92
      // 2e2b: ldc_w 22.347
      // 2e2e: ldc_w -35.799
      // 2e31: ldc_w 0.153
      // 2e34: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e37: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e3a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e3d: aastore
      // 2e3e: dup
      // 2e3f: bipush 23
      // 2e41: new net/minecraft/client/animation/Keyframe
      // 2e44: dup
      // 2e45: ldc 0.96
      // 2e47: ldc_w 34.056
      // 2e4a: ldc_w -58.836
      // 2e4d: ldc_w 12.579
      // 2e50: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e53: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e56: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e59: aastore
      // 2e5a: dup
      // 2e5b: bipush 24
      // 2e5d: new net/minecraft/client/animation/Keyframe
      // 2e60: dup
      // 2e61: fconst_1
      // 2e62: ldc_w 14.957
      // 2e65: ldc_w -79.68
      // 2e68: ldc_w 15.056
      // 2e6b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e6e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e71: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e74: aastore
      // 2e75: dup
      // 2e76: bipush 25
      // 2e78: new net/minecraft/client/animation/Keyframe
      // 2e7b: dup
      // 2e7c: ldc 1.04
      // 2e7e: ldc_w 7.565
      // 2e81: ldc_w -53.844
      // 2e84: ldc_w 7.26
      // 2e87: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e8a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e8d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e90: aastore
      // 2e91: dup
      // 2e92: bipush 26
      // 2e94: new net/minecraft/client/animation/Keyframe
      // 2e97: dup
      // 2e98: ldc 1.08
      // 2e9a: ldc_w 1.365
      // 2e9d: ldc_w -37.464
      // 2ea0: ldc_w 0.141
      // 2ea3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ea6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ea9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2eac: aastore
      // 2ead: dup
      // 2eae: bipush 27
      // 2eb0: new net/minecraft/client/animation/Keyframe
      // 2eb3: dup
      // 2eb4: ldc 1.12
      // 2eb6: ldc_w -16.817
      // 2eb9: ldc_w -41.825
      // 2ebc: ldc_w -23.538
      // 2ebf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ec2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ec5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ec8: aastore
      // 2ec9: dup
      // 2eca: bipush 28
      // 2ecc: new net/minecraft/client/animation/Keyframe
      // 2ecf: dup
      // 2ed0: ldc 1.17
      // 2ed2: ldc_w -31.075
      // 2ed5: ldc_w -49.938
      // 2ed8: ldc_w -44.726
      // 2edb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ede: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ee1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ee4: aastore
      // 2ee5: dup
      // 2ee6: bipush 29
      // 2ee8: new net/minecraft/client/animation/Keyframe
      // 2eeb: dup
      // 2eec: ldc 1.21
      // 2eee: ldc_w -33.837
      // 2ef1: ldc_w -52.302
      // 2ef4: ldc_w -49.097
      // 2ef7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2efa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2efd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f00: aastore
      // 2f01: dup
      // 2f02: bipush 30
      // 2f04: new net/minecraft/client/animation/Keyframe
      // 2f07: dup
      // 2f08: ldc_w 1.25
      // 2f0b: ldc_w -34.847
      // 2f0e: ldc_w -52.846
      // 2f11: ldc_w -50.447
      // 2f14: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f17: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f1a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f1d: aastore
      // 2f1e: dup
      // 2f1f: bipush 31
      // 2f21: new net/minecraft/client/animation/Keyframe
      // 2f24: dup
      // 2f25: ldc 1.29
      // 2f27: ldc_w -35.389
      // 2f2a: ldc_w -52.642
      // 2f2d: ldc_w -50.84
      // 2f30: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f33: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f36: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f39: aastore
      // 2f3a: dup
      // 2f3b: bipush 32
      // 2f3d: new net/minecraft/client/animation/Keyframe
      // 2f40: dup
      // 2f41: ldc_w 1.33
      // 2f44: ldc_w -35.617
      // 2f47: ldc_w -51.884
      // 2f4a: ldc_w -50.55
      // 2f4d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f50: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f53: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f56: aastore
      // 2f57: dup
      // 2f58: bipush 33
      // 2f5a: new net/minecraft/client/animation/Keyframe
      // 2f5d: dup
      // 2f5e: ldc 1.38
      // 2f60: ldc_w -35.505
      // 2f63: ldc_w -50.612
      // 2f66: ldc_w -49.565
      // 2f69: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f6c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f6f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f72: aastore
      // 2f73: dup
      // 2f74: bipush 34
      // 2f76: new net/minecraft/client/animation/Keyframe
      // 2f79: dup
      // 2f7a: ldc 1.42
      // 2f7c: ldc_w -34.558
      // 2f7f: ldc_w -48.5
      // 2f82: ldc_w -47.15
      // 2f85: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f88: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f8b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f8e: aastore
      // 2f8f: dup
      // 2f90: bipush 35
      // 2f92: new net/minecraft/client/animation/Keyframe
      // 2f95: dup
      // 2f96: ldc 1.46
      // 2f98: ldc_w -31.761
      // 2f9b: ldc_w -45.985
      // 2f9e: ldc_w -42.512
      // 2fa1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2fa4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2fa7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2faa: aastore
      // 2fab: dup
      // 2fac: bipush 36
      // 2fae: new net/minecraft/client/animation/Keyframe
      // 2fb1: dup
      // 2fb2: ldc 1.5
      // 2fb4: ldc_w -30.454
      // 2fb7: ldc_w -45.545
      // 2fba: ldc_w -40.783
      // 2fbd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2fc0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2fc3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2fc6: aastore
      // 2fc7: dup
      // 2fc8: bipush 37
      // 2fca: new net/minecraft/client/animation/Keyframe
      // 2fcd: dup
      // 2fce: ldc_w 1.54
      // 2fd1: ldc_w -30.276
      // 2fd4: ldc_w -45.917
      // 2fd7: ldc_w -40.777
      // 2fda: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2fdd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2fe0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2fe3: aastore
      // 2fe4: dup
      // 2fe5: bipush 38
      // 2fe7: new net/minecraft/client/animation/Keyframe
      // 2fea: dup
      // 2feb: ldc_w 1.58
      // 2fee: ldc_w -30.49
      // 2ff1: ldc_w -46.441
      // 2ff4: ldc_w -41.31
      // 2ff7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ffa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ffd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3000: aastore
      // 3001: dup
      // 3002: bipush 39
      // 3004: new net/minecraft/client/animation/Keyframe
      // 3007: dup
      // 3008: ldc_w 1.62
      // 300b: ldc_w -30.869
      // 300e: ldc_w -46.924
      // 3011: ldc_w -42.016
      // 3014: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3017: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 301a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 301d: aastore
      // 301e: dup
      // 301f: bipush 40
      // 3021: new net/minecraft/client/animation/Keyframe
      // 3024: dup
      // 3025: ldc 1.67
      // 3027: ldc_w -30.537
      // 302a: ldc_w -46.774
      // 302d: ldc_w -41.549
      // 3030: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3033: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3036: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3039: aastore
      // 303a: dup
      // 303b: bipush 41
      // 303d: new net/minecraft/client/animation/Keyframe
      // 3040: dup
      // 3041: ldc_w 1.71
      // 3044: ldc_w -28.485
      // 3047: ldc_w -45.444
      // 304a: ldc_w -38.433
      // 304d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3050: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3053: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3056: aastore
      // 3057: dup
      // 3058: bipush 42
      // 305a: new net/minecraft/client/animation/Keyframe
      // 305d: dup
      // 305e: ldc 1.75
      // 3060: ldc_w -24.629
      // 3063: ldc_w -43.38
      // 3066: ldc_w -32.791
      // 3069: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 306c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 306f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3072: aastore
      // 3073: dup
      // 3074: bipush 43
      // 3076: new net/minecraft/client/animation/Keyframe
      // 3079: dup
      // 307a: ldc 1.79
      // 307c: ldc_w -15.0
      // 307f: ldc_w -52.915
      // 3082: ldc_w -24.269
      // 3085: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3088: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 308b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 308e: aastore
      // 308f: dup
      // 3090: bipush 44
      // 3092: new net/minecraft/client/animation/Keyframe
      // 3095: dup
      // 3096: ldc_w 1.83
      // 3099: ldc_w -4.219
      // 309c: ldc_w -74.276
      // 309f: ldc_w -13.869
      // 30a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 30a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30ab: aastore
      // 30ac: dup
      // 30ad: bipush 45
      // 30af: new net/minecraft/client/animation/Keyframe
      // 30b2: dup
      // 30b3: ldc 1.88
      // 30b5: ldc_w -1.139
      // 30b8: ldc_w -85.953
      // 30bb: ldc_w -4.405
      // 30be: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 30c1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30c4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30c7: aastore
      // 30c8: dup
      // 30c9: bipush 46
      // 30cb: new net/minecraft/client/animation/Keyframe
      // 30ce: dup
      // 30cf: ldc_w 1.92
      // 30d2: ldc_w -0.42
      // 30d5: ldc_w -64.881
      // 30d8: ldc_w 0.024
      // 30db: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 30de: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30e1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30e4: aastore
      // 30e5: dup
      // 30e6: bipush 47
      // 30e8: new net/minecraft/client/animation/Keyframe
      // 30eb: dup
      // 30ec: ldc 1.96
      // 30ee: ldc_w 0.387
      // 30f1: ldc_w -32.514
      // 30f4: ldc_w 0.407
      // 30f7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 30fa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30fd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3100: aastore
      // 3101: dup
      // 3102: bipush 48
      // 3104: new net/minecraft/client/animation/Keyframe
      // 3107: dup
      // 3108: fconst_2
      // 3109: ldc_w -0.488
      // 310c: ldc_w -13.567
      // 310f: ldc_w -0.675
      // 3112: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3115: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3118: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 311b: aastore
      // 311c: dup
      // 311d: bipush 49
      // 311f: new net/minecraft/client/animation/Keyframe
      // 3122: dup
      // 3123: ldc 2.04
      // 3125: ldc_w -2.468
      // 3128: ldc_w -5.063
      // 312b: ldc_w -2.68
      // 312e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3131: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3134: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3137: aastore
      // 3138: dup
      // 3139: bipush 50
      // 313b: new net/minecraft/client/animation/Keyframe
      // 313e: dup
      // 313f: ldc 2.08
      // 3141: ldc_w 14.594
      // 3144: ldc_w 3.623
      // 3147: ldc_w 14.447
      // 314a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 314d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3150: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3153: aastore
      // 3154: dup
      // 3155: bipush 51
      // 3157: new net/minecraft/client/animation/Keyframe
      // 315a: dup
      // 315b: ldc 2.12
      // 315d: ldc_w 3.294
      // 3160: ldc_w 12.38
      // 3163: ldc_w 3.293
      // 3166: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3169: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 316c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 316f: aastore
      // 3170: dup
      // 3171: bipush 52
      // 3173: new net/minecraft/client/animation/Keyframe
      // 3176: dup
      // 3177: ldc 2.17
      // 3179: ldc_w 2.174
      // 317c: ldc_w 20.651
      // 317f: ldc_w 2.348
      // 3182: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3185: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3188: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 318b: aastore
      // 318c: dup
      // 318d: bipush 53
      // 318f: new net/minecraft/client/animation/Keyframe
      // 3192: dup
      // 3193: ldc 2.21
      // 3195: ldc_w 2.249
      // 3198: ldc_w 19.876
      // 319b: ldc_w 2.392
      // 319e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31a1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31a4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31a7: aastore
      // 31a8: dup
      // 31a9: bipush 54
      // 31ab: new net/minecraft/client/animation/Keyframe
      // 31ae: dup
      // 31af: ldc 2.25
      // 31b1: ldc_w 2.745
      // 31b4: ldc_w 15.846
      // 31b7: ldc_w 2.735
      // 31ba: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31bd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31c0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31c3: aastore
      // 31c4: dup
      // 31c5: bipush 55
      // 31c7: new net/minecraft/client/animation/Keyframe
      // 31ca: dup
      // 31cb: ldc 2.29
      // 31cd: ldc_w 3.334
      // 31d0: ldc_w 13.036
      // 31d3: ldc_w 3.132
      // 31d6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31d9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31dc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31df: aastore
      // 31e0: dup
      // 31e1: bipush 56
      // 31e3: new net/minecraft/client/animation/Keyframe
      // 31e6: dup
      // 31e7: ldc 2.33
      // 31e9: ldc_w 3.824
      // 31ec: ldc_w 11.609
      // 31ef: ldc_w 3.424
      // 31f2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31f5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31f8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31fb: aastore
      // 31fc: dup
      // 31fd: bipush 57
      // 31ff: new net/minecraft/client/animation/Keyframe
      // 3202: dup
      // 3203: ldc 2.38
      // 3205: ldc_w 7.064
      // 3208: ldc_w 6.443
      // 320b: ldc_w 5.99
      // 320e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3211: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3214: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3217: aastore
      // 3218: dup
      // 3219: bipush 58
      // 321b: new net/minecraft/client/animation/Keyframe
      // 321e: dup
      // 321f: ldc_w 2.42
      // 3222: ldc_w -0.185
      // 3225: ldc_w -4.519
      // 3228: ldc_w -3.309
      // 322b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 322e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3231: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3234: aastore
      // 3235: dup
      // 3236: bipush 59
      // 3238: new net/minecraft/client/animation/Keyframe
      // 323b: dup
      // 323c: ldc_w 2.46
      // 323f: ldc_w 4.967
      // 3242: ldc_w -18.44
      // 3245: ldc_w -1.854
      // 3248: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 324b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 324e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3251: aastore
      // 3252: dup
      // 3253: bipush 60
      // 3255: new net/minecraft/client/animation/Keyframe
      // 3258: dup
      // 3259: ldc_w 2.5
      // 325c: ldc_w 5.226
      // 325f: ldc_w -25.598
      // 3262: ldc_w -2.285
      // 3265: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3268: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 326b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 326e: aastore
      // 326f: dup
      // 3270: bipush 61
      // 3272: new net/minecraft/client/animation/Keyframe
      // 3275: dup
      // 3276: ldc 2.54
      // 3278: ldc_w 3.204
      // 327b: ldc_w -22.398
      // 327e: ldc_w -1.822
      // 3281: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3284: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3287: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 328a: aastore
      // 328b: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 328e: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3291: ldc_w "CTR_upperarm_left.001"
      // 3294: new net/minecraft/client/animation/AnimationChannel
      // 3297: dup
      // 3298: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 329b: bipush 43
      // 329d: anewarray 28
      // 32a0: dup
      // 32a1: bipush 0
      // 32a2: new net/minecraft/client/animation/Keyframe
      // 32a5: dup
      // 32a6: fconst_0
      // 32a7: dconst_1
      // 32a8: dconst_1
      // 32a9: ldc2_w 1.7869999408721924
      // 32ac: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 32af: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32b2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32b5: aastore
      // 32b6: dup
      // 32b7: bipush 1
      // 32b8: new net/minecraft/client/animation/Keyframe
      // 32bb: dup
      // 32bc: ldc 0.04
      // 32be: dconst_1
      // 32bf: dconst_1
      // 32c0: ldc2_w 1.7660000324249268
      // 32c3: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 32c6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32c9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32cc: aastore
      // 32cd: dup
      // 32ce: bipush 2
      // 32cf: new net/minecraft/client/animation/Keyframe
      // 32d2: dup
      // 32d3: ldc 0.12
      // 32d5: dconst_1
      // 32d6: dconst_1
      // 32d7: ldc2_w 1.690000057220459
      // 32da: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 32dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32e3: aastore
      // 32e4: dup
      // 32e5: bipush 3
      // 32e6: new net/minecraft/client/animation/Keyframe
      // 32e9: dup
      // 32ea: ldc 0.21
      // 32ec: dconst_1
      // 32ed: dconst_1
      // 32ee: ldc2_w 1.6610000133514404
      // 32f1: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 32f4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32f7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32fa: aastore
      // 32fb: dup
      // 32fc: bipush 4
      // 32fd: new net/minecraft/client/animation/Keyframe
      // 3300: dup
      // 3301: ldc 0.29
      // 3303: dconst_1
      // 3304: dconst_1
      // 3305: ldc2_w 1.6920000314712524
      // 3308: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 330b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 330e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3311: aastore
      // 3312: dup
      // 3313: bipush 5
      // 3314: new net/minecraft/client/animation/Keyframe
      // 3317: dup
      // 3318: ldc 0.38
      // 331a: dconst_1
      // 331b: dconst_1
      // 331c: ldc2_w 1.684999942779541
      // 331f: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3322: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3325: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3328: aastore
      // 3329: dup
      // 332a: bipush 6
      // 332c: new net/minecraft/client/animation/Keyframe
      // 332f: dup
      // 3330: ldc 0.46
      // 3332: dconst_1
      // 3333: dconst_1
      // 3334: ldc2_w 1.6339999437332153
      // 3337: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 333a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 333d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3340: aastore
      // 3341: dup
      // 3342: bipush 7
      // 3344: new net/minecraft/client/animation/Keyframe
      // 3347: dup
      // 3348: ldc 0.5
      // 334a: dconst_1
      // 334b: dconst_1
      // 334c: ldc2_w 1.5770000219345093
      // 334f: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3352: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3355: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3358: aastore
      // 3359: dup
      // 335a: bipush 8
      // 335c: new net/minecraft/client/animation/Keyframe
      // 335f: dup
      // 3360: ldc_w 0.54
      // 3363: dconst_1
      // 3364: dconst_1
      // 3365: ldc2_w 1.5410000085830688
      // 3368: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 336b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 336e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3371: aastore
      // 3372: dup
      // 3373: bipush 9
      // 3375: new net/minecraft/client/animation/Keyframe
      // 3378: dup
      // 3379: ldc 0.58
      // 337b: dconst_1
      // 337c: dconst_1
      // 337d: ldc2_w 1.6109999418258667
      // 3380: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3383: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3386: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3389: aastore
      // 338a: dup
      // 338b: bipush 10
      // 338d: new net/minecraft/client/animation/Keyframe
      // 3390: dup
      // 3391: ldc 0.62
      // 3393: dconst_1
      // 3394: dconst_1
      // 3395: ldc2_w 1.746000051498413
      // 3398: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 339b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 339e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33a1: aastore
      // 33a2: dup
      // 33a3: bipush 11
      // 33a5: new net/minecraft/client/animation/Keyframe
      // 33a8: dup
      // 33a9: ldc 0.67
      // 33ab: dconst_1
      // 33ac: dconst_1
      // 33ad: ldc2_w 1.8910000324249268
      // 33b0: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 33b3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33b6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33b9: aastore
      // 33ba: dup
      // 33bb: bipush 12
      // 33bd: new net/minecraft/client/animation/Keyframe
      // 33c0: dup
      // 33c1: ldc 0.71
      // 33c3: dconst_1
      // 33c4: dconst_1
      // 33c5: ldc2_w 1.4019999504089355
      // 33c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 33cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33d1: aastore
      // 33d2: dup
      // 33d3: bipush 13
      // 33d5: new net/minecraft/client/animation/Keyframe
      // 33d8: dup
      // 33d9: ldc 0.75
      // 33db: dconst_1
      // 33dc: dconst_1
      // 33dd: ldc2_w 1.5269999504089355
      // 33e0: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 33e3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33e6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33e9: aastore
      // 33ea: dup
      // 33eb: bipush 14
      // 33ed: new net/minecraft/client/animation/Keyframe
      // 33f0: dup
      // 33f1: ldc_w 0.79
      // 33f4: dconst_1
      // 33f5: dconst_1
      // 33f6: ldc2_w 1.4049999713897705
      // 33f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 33fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3402: aastore
      // 3403: dup
      // 3404: bipush 15
      // 3406: new net/minecraft/client/animation/Keyframe
      // 3409: dup
      // 340a: ldc 0.88
      // 340c: dconst_1
      // 340d: dconst_1
      // 340e: ldc2_w 1.0570000410079956
      // 3411: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3414: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3417: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 341a: aastore
      // 341b: dup
      // 341c: bipush 16
      // 341e: new net/minecraft/client/animation/Keyframe
      // 3421: dup
      // 3422: ldc 0.92
      // 3424: dconst_1
      // 3425: dconst_1
      // 3426: ldc2_w 0.7210000157356262
      // 3429: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 342c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 342f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3432: aastore
      // 3433: dup
      // 3434: bipush 17
      // 3436: new net/minecraft/client/animation/Keyframe
      // 3439: dup
      // 343a: ldc 0.96
      // 343c: dconst_1
      // 343d: dconst_1
      // 343e: ldc2_w 0.45100000500679016
      // 3441: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3444: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3447: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 344a: aastore
      // 344b: dup
      // 344c: bipush 18
      // 344e: new net/minecraft/client/animation/Keyframe
      // 3451: dup
      // 3452: fconst_1
      // 3453: dconst_1
      // 3454: dconst_1
      // 3455: ldc2_w 0.574999988079071
      // 3458: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 345b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 345e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3461: aastore
      // 3462: dup
      // 3463: bipush 19
      // 3465: new net/minecraft/client/animation/Keyframe
      // 3468: dup
      // 3469: ldc 1.04
      // 346b: dconst_1
      // 346c: dconst_1
      // 346d: ldc2_w 0.8960000276565552
      // 3470: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3473: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3476: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3479: aastore
      // 347a: dup
      // 347b: bipush 20
      // 347d: new net/minecraft/client/animation/Keyframe
      // 3480: dup
      // 3481: ldc 1.08
      // 3483: dconst_1
      // 3484: dconst_1
      // 3485: ldc2_w 1.0509999990463257
      // 3488: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 348b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 348e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3491: aastore
      // 3492: dup
      // 3493: bipush 21
      // 3495: new net/minecraft/client/animation/Keyframe
      // 3498: dup
      // 3499: ldc 1.17
      // 349b: dconst_1
      // 349c: dconst_1
      // 349d: ldc2_w 1.090000033378601
      // 34a0: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 34a3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34a6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 34a9: aastore
      // 34aa: dup
      // 34ab: bipush 22
      // 34ad: new net/minecraft/client/animation/Keyframe
      // 34b0: dup
      // 34b1: ldc_w 1.33
      // 34b4: dconst_1
      // 34b5: dconst_1
      // 34b6: ldc2_w 1.090000033378601
      // 34b9: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 34bc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34bf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 34c2: aastore
      // 34c3: dup
      // 34c4: bipush 23
      // 34c6: new net/minecraft/client/animation/Keyframe
      // 34c9: dup
      // 34ca: ldc 1.46
      // 34cc: dconst_1
      // 34cd: dconst_1
      // 34ce: ldc2_w 1.0740000009536743
      // 34d1: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 34d4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34d7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 34da: aastore
      // 34db: dup
      // 34dc: bipush 24
      // 34de: new net/minecraft/client/animation/Keyframe
      // 34e1: dup
      // 34e2: ldc 1.67
      // 34e4: dconst_1
      // 34e5: dconst_1
      // 34e6: ldc2_w 1.1619999408721924
      // 34e9: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 34ec: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34ef: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 34f2: aastore
      // 34f3: dup
      // 34f4: bipush 25
      // 34f6: new net/minecraft/client/animation/Keyframe
      // 34f9: dup
      // 34fa: ldc 1.75
      // 34fc: dconst_1
      // 34fd: dconst_1
      // 34fe: ldc2_w 1.1959999799728394
      // 3501: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3504: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3507: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 350a: aastore
      // 350b: dup
      // 350c: bipush 26
      // 350e: new net/minecraft/client/animation/Keyframe
      // 3511: dup
      // 3512: ldc_w 1.83
      // 3515: dconst_1
      // 3516: dconst_1
      // 3517: ldc2_w 1.0360000133514404
      // 351a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 351d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3520: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3523: aastore
      // 3524: dup
      // 3525: bipush 27
      // 3527: new net/minecraft/client/animation/Keyframe
      // 352a: dup
      // 352b: ldc 1.88
      // 352d: dconst_1
      // 352e: dconst_1
      // 352f: ldc2_w 1.034999966621399
      // 3532: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3535: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3538: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 353b: aastore
      // 353c: dup
      // 353d: bipush 28
      // 353f: new net/minecraft/client/animation/Keyframe
      // 3542: dup
      // 3543: ldc_w 1.92
      // 3546: dconst_1
      // 3547: dconst_1
      // 3548: ldc2_w 1.062999963760376
      // 354b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 354e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3551: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3554: aastore
      // 3555: dup
      // 3556: bipush 29
      // 3558: new net/minecraft/client/animation/Keyframe
      // 355b: dup
      // 355c: fconst_2
      // 355d: dconst_1
      // 355e: dconst_1
      // 355f: ldc2_w 1.3559999465942383
      // 3562: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3565: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3568: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 356b: aastore
      // 356c: dup
      // 356d: bipush 30
      // 356f: new net/minecraft/client/animation/Keyframe
      // 3572: dup
      // 3573: ldc 2.04
      // 3575: dconst_1
      // 3576: dconst_1
      // 3577: ldc2_w 1.4500000476837158
      // 357a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 357d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3580: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3583: aastore
      // 3584: dup
      // 3585: bipush 31
      // 3587: new net/minecraft/client/animation/Keyframe
      // 358a: dup
      // 358b: ldc 2.08
      // 358d: dconst_1
      // 358e: dconst_1
      // 358f: ldc2_w 1.5709999799728394
      // 3592: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3595: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3598: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 359b: aastore
      // 359c: dup
      // 359d: bipush 32
      // 359f: new net/minecraft/client/animation/Keyframe
      // 35a2: dup
      // 35a3: ldc 2.12
      // 35a5: dconst_1
      // 35a6: dconst_1
      // 35a7: ldc2_w 1.718999981880188
      // 35aa: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 35ad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35b0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35b3: aastore
      // 35b4: dup
      // 35b5: bipush 33
      // 35b7: new net/minecraft/client/animation/Keyframe
      // 35ba: dup
      // 35bb: ldc 2.17
      // 35bd: dconst_1
      // 35be: dconst_1
      // 35bf: ldc2_w 1.8799999952316284
      // 35c2: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 35c5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35c8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35cb: aastore
      // 35cc: dup
      // 35cd: bipush 34
      // 35cf: new net/minecraft/client/animation/Keyframe
      // 35d2: dup
      // 35d3: ldc 2.21
      // 35d5: dconst_1
      // 35d6: dconst_1
      // 35d7: ldc2_w 1.9259999990463257
      // 35da: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 35dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35e3: aastore
      // 35e4: dup
      // 35e5: bipush 35
      // 35e7: new net/minecraft/client/animation/Keyframe
      // 35ea: dup
      // 35eb: ldc 2.25
      // 35ed: dconst_1
      // 35ee: dconst_1
      // 35ef: ldc2_w 1.940999984741211
      // 35f2: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 35f5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35f8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35fb: aastore
      // 35fc: dup
      // 35fd: bipush 36
      // 35ff: new net/minecraft/client/animation/Keyframe
      // 3602: dup
      // 3603: ldc 2.29
      // 3605: dconst_1
      // 3606: dconst_1
      // 3607: ldc2_w 1.9769999980926514
      // 360a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 360d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3610: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3613: aastore
      // 3614: dup
      // 3615: bipush 37
      // 3617: new net/minecraft/client/animation/Keyframe
      // 361a: dup
      // 361b: ldc 2.33
      // 361d: dconst_1
      // 361e: dconst_1
      // 361f: ldc2_w 2.0269999504089355
      // 3622: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3625: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3628: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 362b: aastore
      // 362c: dup
      // 362d: bipush 38
      // 362f: new net/minecraft/client/animation/Keyframe
      // 3632: dup
      // 3633: ldc 2.38
      // 3635: dconst_1
      // 3636: dconst_1
      // 3637: ldc2_w 2.0160000324249268
      // 363a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 363d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3640: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3643: aastore
      // 3644: dup
      // 3645: bipush 39
      // 3647: new net/minecraft/client/animation/Keyframe
      // 364a: dup
      // 364b: ldc_w 2.42
      // 364e: dconst_1
      // 364f: dconst_1
      // 3650: ldc2_w 1.9220000505447388
      // 3653: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3656: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3659: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 365c: aastore
      // 365d: dup
      // 365e: bipush 40
      // 3660: new net/minecraft/client/animation/Keyframe
      // 3663: dup
      // 3664: ldc_w 2.46
      // 3667: dconst_1
      // 3668: dconst_1
      // 3669: ldc2_w 1.7960000038146973
      // 366c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 366f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3672: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3675: aastore
      // 3676: dup
      // 3677: bipush 41
      // 3679: new net/minecraft/client/animation/Keyframe
      // 367c: dup
      // 367d: ldc_w 2.5
      // 3680: dconst_1
      // 3681: dconst_1
      // 3682: ldc2_w 1.7489999532699585
      // 3685: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3688: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 368b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 368e: aastore
      // 368f: dup
      // 3690: bipush 42
      // 3692: new net/minecraft/client/animation/Keyframe
      // 3695: dup
      // 3696: ldc 2.54
      // 3698: dconst_1
      // 3699: dconst_1
      // 369a: ldc2_w 1.7869999408721924
      // 369d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 36a0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36a3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36a6: aastore
      // 36a7: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 36aa: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 36ad: ldc_w "CTR_shoulder_right"
      // 36b0: new net/minecraft/client/animation/AnimationChannel
      // 36b3: dup
      // 36b4: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 36b7: bipush 8
      // 36b9: anewarray 28
      // 36bc: dup
      // 36bd: bipush 0
      // 36be: new net/minecraft/client/animation/Keyframe
      // 36c1: dup
      // 36c2: ldc_w 1.62
      // 36c5: fconst_0
      // 36c6: fconst_0
      // 36c7: fconst_0
      // 36c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 36cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36d1: aastore
      // 36d2: dup
      // 36d3: bipush 1
      // 36d4: new net/minecraft/client/animation/Keyframe
      // 36d7: dup
      // 36d8: ldc 1.67
      // 36da: ldc_w 1.115
      // 36dd: ldc_w -7.493
      // 36e0: ldc_w -5.535
      // 36e3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 36e6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36e9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36ec: aastore
      // 36ed: dup
      // 36ee: bipush 2
      // 36ef: new net/minecraft/client/animation/Keyframe
      // 36f2: dup
      // 36f3: ldc_w 1.71
      // 36f6: ldc_w 3.185
      // 36f9: ldc_w -21.408
      // 36fc: ldc_w -15.814
      // 36ff: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3702: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3705: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3708: aastore
      // 3709: dup
      // 370a: bipush 3
      // 370b: new net/minecraft/client/animation/Keyframe
      // 370e: dup
      // 370f: ldc 1.75
      // 3711: ldc_w 4.3
      // 3714: ldc_w -28.901
      // 3717: ldc_w -21.348
      // 371a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 371d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3720: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3723: aastore
      // 3724: dup
      // 3725: bipush 4
      // 3726: new net/minecraft/client/animation/Keyframe
      // 3729: dup
      // 372a: ldc 1.79
      // 372c: ldc_w 3.628
      // 372f: ldc_w -24.385
      // 3732: ldc_w -18.013
      // 3735: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3738: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 373b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 373e: aastore
      // 373f: dup
      // 3740: bipush 5
      // 3741: new net/minecraft/client/animation/Keyframe
      // 3744: dup
      // 3745: ldc_w 1.83
      // 3748: ldc_w 2.15
      // 374b: ldc_w -14.45
      // 374e: ldc_w -10.674
      // 3751: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3754: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3757: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 375a: aastore
      // 375b: dup
      // 375c: bipush 6
      // 375e: new net/minecraft/client/animation/Keyframe
      // 3761: dup
      // 3762: ldc 1.88
      // 3764: ldc_w 0.672
      // 3767: ldc_w -4.516
      // 376a: ldc_w -3.336
      // 376d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3770: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3773: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3776: aastore
      // 3777: dup
      // 3778: bipush 7
      // 377a: new net/minecraft/client/animation/Keyframe
      // 377d: dup
      // 377e: ldc_w 1.92
      // 3781: fconst_0
      // 3782: fconst_0
      // 3783: fconst_0
      // 3784: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3787: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 378a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 378d: aastore
      // 378e: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3791: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3794: ldc_w "CTR_shoulder_right"
      // 3797: new net/minecraft/client/animation/AnimationChannel
      // 379a: dup
      // 379b: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 379e: bipush 41
      // 37a0: anewarray 28
      // 37a3: dup
      // 37a4: bipush 0
      // 37a5: new net/minecraft/client/animation/Keyframe
      // 37a8: dup
      // 37a9: ldc 0.88
      // 37ab: ldc_w 0.016
      // 37ae: ldc_w -1.1
      // 37b1: ldc_w -0.172
      // 37b4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 37b7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37ba: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37bd: aastore
      // 37be: dup
      // 37bf: bipush 1
      // 37c0: new net/minecraft/client/animation/Keyframe
      // 37c3: dup
      // 37c4: ldc 0.92
      // 37c6: ldc_w 1.116
      // 37c9: ldc_w -0.438
      // 37cc: ldc_w -0.065
      // 37cf: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 37d2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37d5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37d8: aastore
      // 37d9: dup
      // 37da: bipush 2
      // 37db: new net/minecraft/client/animation/Keyframe
      // 37de: dup
      // 37df: ldc 0.96
      // 37e1: ldc_w 3.623
      // 37e4: ldc_w 1.018
      // 37e7: ldc_w 0.236
      // 37ea: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 37ed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37f0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37f3: aastore
      // 37f4: dup
      // 37f5: bipush 3
      // 37f6: new net/minecraft/client/animation/Keyframe
      // 37f9: dup
      // 37fa: fconst_1
      // 37fb: ldc_w 6.346
      // 37fe: ldc_w 2.473
      // 3801: ldc_w 0.7
      // 3804: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3807: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 380a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 380d: aastore
      // 380e: dup
      // 380f: bipush 4
      // 3810: new net/minecraft/client/animation/Keyframe
      // 3813: dup
      // 3814: ldc 1.04
      // 3816: ldc_w 8.096
      // 3819: ldc_w 3.135
      // 381c: ldc_w 1.294
      // 381f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3822: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3825: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3828: aastore
      // 3829: dup
      // 382a: bipush 5
      // 382b: new net/minecraft/client/animation/Keyframe
      // 382e: dup
      // 382f: ldc 1.08
      // 3831: ldc_w 8.706
      // 3834: ldc_w 2.479
      // 3837: ldc_w 1.958
      // 383a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 383d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3840: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3843: aastore
      // 3844: dup
      // 3845: bipush 6
      // 3847: new net/minecraft/client/animation/Keyframe
      // 384a: dup
      // 384b: ldc 1.12
      // 384d: ldc_w 8.931
      // 3850: ldc_w 1.261
      // 3853: ldc_w 2.51
      // 3856: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3859: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 385c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 385f: aastore
      // 3860: dup
      // 3861: bipush 7
      // 3863: new net/minecraft/client/animation/Keyframe
      // 3866: dup
      // 3867: ldc 1.17
      // 3869: ldc_w 8.963
      // 386c: ldc_w 0.605
      // 386f: ldc_w 2.741
      // 3872: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3875: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3878: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 387b: aastore
      // 387c: dup
      // 387d: bipush 8
      // 387f: new net/minecraft/client/animation/Keyframe
      // 3882: dup
      // 3883: ldc 1.21
      // 3885: ldc_w 8.958
      // 3888: ldc_w 0.618
      // 388b: ldc_w 2.734
      // 388e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3891: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3894: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3897: aastore
      // 3898: dup
      // 3899: bipush 9
      // 389b: new net/minecraft/client/animation/Keyframe
      // 389e: dup
      // 389f: ldc_w 1.25
      // 38a2: ldc_w 8.935
      // 38a5: ldc_w 0.687
      // 38a8: ldc_w 2.695
      // 38ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 38ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 38b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 38b4: aastore
      // 38b5: dup
      // 38b6: bipush 10
      // 38b8: new net/minecraft/client/animation/Keyframe
      // 38bb: dup
      // 38bc: ldc 1.29
      // 38be: ldc_w 8.876
      // 38c1: ldc_w 0.86
      // 38c4: ldc_w 2.596
      // 38c7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 38ca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 38cd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 38d0: aastore
      // 38d1: dup
      // 38d2: bipush 11
      // 38d4: new net/minecraft/client/animation/Keyframe
      // 38d7: dup
      // 38d8: ldc_w 1.33
      // 38db: ldc_w 8.764
      // 38de: ldc_w 1.184
      // 38e1: ldc_w 2.41
      // 38e4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 38e7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 38ea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 38ed: aastore
      // 38ee: dup
      // 38ef: bipush 12
      // 38f1: new net/minecraft/client/animation/Keyframe
      // 38f4: dup
      // 38f5: ldc 1.38
      // 38f7: ldc_w 8.585
      // 38fa: ldc_w 1.708
      // 38fd: ldc_w 2.11
      // 3900: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3903: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3906: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3909: aastore
      // 390a: dup
      // 390b: bipush 13
      // 390d: new net/minecraft/client/animation/Keyframe
      // 3910: dup
      // 3911: ldc 1.42
      // 3913: ldc_w 8.321
      // 3916: ldc_w 2.479
      // 3919: ldc_w 1.669
      // 391c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 391f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3922: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3925: aastore
      // 3926: dup
      // 3927: bipush 14
      // 3929: new net/minecraft/client/animation/Keyframe
      // 392c: dup
      // 392d: ldc 1.46
      // 392f: ldc_w 8.096
      // 3932: ldc_w 3.135
      // 3935: ldc_w 1.294
      // 3938: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 393b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 393e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3941: aastore
      // 3942: dup
      // 3943: bipush 15
      // 3945: new net/minecraft/client/animation/Keyframe
      // 3948: dup
      // 3949: ldc 1.5
      // 394b: ldc_w 8.462
      // 394e: ldc_w 1.351
      // 3951: ldc_w 2.64
      // 3954: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3957: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 395a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 395d: aastore
      // 395e: dup
      // 395f: bipush 16
      // 3961: new net/minecraft/client/animation/Keyframe
      // 3964: dup
      // 3965: ldc_w 1.54
      // 3968: ldc_w 9.268
      // 396b: ldc_w -2.762
      // 396e: ldc_w 5.599
      // 3971: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3974: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3977: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 397a: aastore
      // 397b: dup
      // 397c: bipush 17
      // 397e: new net/minecraft/client/animation/Keyframe
      // 3981: dup
      // 3982: ldc_w 1.58
      // 3985: ldc_w 10.074
      // 3988: ldc_w -7.345
      // 398b: ldc_w 8.558
      // 398e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3991: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3994: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3997: aastore
      // 3998: dup
      // 3999: bipush 18
      // 399b: new net/minecraft/client/animation/Keyframe
      // 399e: dup
      // 399f: ldc_w 1.62
      // 39a2: ldc_w 10.441
      // 39a5: ldc_w -10.54
      // 39a8: ldc_w 9.904
      // 39ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 39ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39b4: aastore
      // 39b5: dup
      // 39b6: bipush 19
      // 39b8: new net/minecraft/client/animation/Keyframe
      // 39bb: dup
      // 39bc: ldc 1.67
      // 39be: ldc_w 10.031
      // 39c1: ldc_w -11.864
      // 39c4: ldc_w 9.556
      // 39c7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 39ca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39cd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39d0: aastore
      // 39d1: dup
      // 39d2: bipush 20
      // 39d4: new net/minecraft/client/animation/Keyframe
      // 39d7: dup
      // 39d8: ldc_w 1.71
      // 39db: ldc_w 9.108
      // 39de: ldc_w -12.351
      // 39e1: ldc_w 8.714
      // 39e4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 39e7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39ea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39ed: aastore
      // 39ee: dup
      // 39ef: bipush 21
      // 39f1: new net/minecraft/client/animation/Keyframe
      // 39f4: dup
      // 39f5: ldc 1.75
      // 39f7: ldc_w 8.127
      // 39fa: ldc_w -12.421
      // 39fd: ldc_w 7.673
      // 3a00: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3a03: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a06: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a09: aastore
      // 3a0a: dup
      // 3a0b: bipush 22
      // 3a0d: new net/minecraft/client/animation/Keyframe
      // 3a10: dup
      // 3a11: ldc 1.79
      // 3a13: ldc_w 7.445
      // 3a16: ldc_w -12.025
      // 3a19: ldc_w 6.652
      // 3a1c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3a1f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a22: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a25: aastore
      // 3a26: dup
      // 3a27: bipush 23
      // 3a29: new net/minecraft/client/animation/Keyframe
      // 3a2c: dup
      // 3a2d: ldc_w 1.83
      // 3a30: ldc_w 7.012
      // 3a33: ldc_w -10.913
      // 3a36: ldc_w 5.543
      // 3a39: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3a3c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a3f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a42: aastore
      // 3a43: dup
      // 3a44: bipush 24
      // 3a46: new net/minecraft/client/animation/Keyframe
      // 3a49: dup
      // 3a4a: ldc 1.88
      // 3a4c: ldc_w 6.683
      // 3a4f: ldc_w -9.201
      // 3a52: ldc_w 4.16
      // 3a55: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3a58: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a5b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a5e: aastore
      // 3a5f: dup
      // 3a60: bipush 25
      // 3a62: new net/minecraft/client/animation/Keyframe
      // 3a65: dup
      // 3a66: ldc_w 1.92
      // 3a69: ldc_w 6.307
      // 3a6c: ldc_w -7.003
      // 3a6f: ldc_w 2.316
      // 3a72: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3a75: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a78: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a7b: aastore
      // 3a7c: dup
      // 3a7d: bipush 26
      // 3a7f: new net/minecraft/client/animation/Keyframe
      // 3a82: dup
      // 3a83: ldc 1.96
      // 3a85: ldc_w 5.773
      // 3a88: ldc_w -4.497
      // 3a8b: ldc_w -0.047
      // 3a8e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3a91: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a94: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a97: aastore
      // 3a98: dup
      // 3a99: bipush 27
      // 3a9b: new net/minecraft/client/animation/Keyframe
      // 3a9e: dup
      // 3a9f: fconst_2
      // 3aa0: ldc_w 5.111
      // 3aa3: ldc_w -2.105
      // 3aa6: ldc_w -2.467
      // 3aa9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3aac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3aaf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ab2: aastore
      // 3ab3: dup
      // 3ab4: bipush 28
      // 3ab6: new net/minecraft/client/animation/Keyframe
      // 3ab9: dup
      // 3aba: ldc 2.04
      // 3abc: ldc_w 4.387
      // 3abf: ldc_w -0.314
      // 3ac2: ldc_w -4.353
      // 3ac5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3ac8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3acb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ace: aastore
      // 3acf: dup
      // 3ad0: bipush 29
      // 3ad2: new net/minecraft/client/animation/Keyframe
      // 3ad5: dup
      // 3ad6: ldc 2.08
      // 3ad8: ldc_w 3.669
      // 3adb: ldc_w 0.391
      // 3ade: ldc_w -5.113
      // 3ae1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3ae4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ae7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3aea: aastore
      // 3aeb: dup
      // 3aec: bipush 30
      // 3aee: new net/minecraft/client/animation/Keyframe
      // 3af1: dup
      // 3af2: ldc 2.12
      // 3af4: ldc_w 3.01
      // 3af7: ldc_w 0.356
      // 3afa: ldc_w -4.998
      // 3afd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3b00: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b03: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b06: aastore
      // 3b07: dup
      // 3b08: bipush 31
      // 3b0a: new net/minecraft/client/animation/Keyframe
      // 3b0d: dup
      // 3b0e: ldc 2.17
      // 3b10: ldc_w 2.421
      // 3b13: ldc_w 0.261
      // 3b16: ldc_w -4.682
      // 3b19: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3b1c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b1f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b22: aastore
      // 3b23: dup
      // 3b24: bipush 32
      // 3b26: new net/minecraft/client/animation/Keyframe
      // 3b29: dup
      // 3b2a: ldc 2.21
      // 3b2c: ldc_w 1.901
      // 3b2f: ldc_w 0.119
      // 3b32: ldc_w -4.211
      // 3b35: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3b38: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b3b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b3e: aastore
      // 3b3f: dup
      // 3b40: bipush 33
      // 3b42: new net/minecraft/client/animation/Keyframe
      // 3b45: dup
      // 3b46: ldc 2.25
      // 3b48: ldc_w 1.447
      // 3b4b: ldc_w -0.057
      // 3b4e: ldc_w -3.628
      // 3b51: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3b54: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b57: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b5a: aastore
      // 3b5b: dup
      // 3b5c: bipush 34
      // 3b5e: new net/minecraft/client/animation/Keyframe
      // 3b61: dup
      // 3b62: ldc 2.29
      // 3b64: ldc_w 1.058
      // 3b67: ldc_w -0.253
      // 3b6a: ldc_w -2.979
      // 3b6d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3b70: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b73: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b76: aastore
      // 3b77: dup
      // 3b78: bipush 35
      // 3b7a: new net/minecraft/client/animation/Keyframe
      // 3b7d: dup
      // 3b7e: ldc 2.33
      // 3b80: ldc_w 0.734
      // 3b83: ldc_w -0.456
      // 3b86: ldc_w -2.307
      // 3b89: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3b8c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b8f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b92: aastore
      // 3b93: dup
      // 3b94: bipush 36
      // 3b96: new net/minecraft/client/animation/Keyframe
      // 3b99: dup
      // 3b9a: ldc 2.38
      // 3b9c: ldc_w 0.472
      // 3b9f: ldc_w -0.652
      // 3ba2: ldc_w -1.657
      // 3ba5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3ba8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3bab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3bae: aastore
      // 3baf: dup
      // 3bb0: bipush 37
      // 3bb2: new net/minecraft/client/animation/Keyframe
      // 3bb5: dup
      // 3bb6: ldc_w 2.42
      // 3bb9: ldc_w 0.27
      // 3bbc: ldc_w -0.828
      // 3bbf: ldc_w -1.074
      // 3bc2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3bc5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3bc8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3bcb: aastore
      // 3bcc: dup
      // 3bcd: bipush 38
      // 3bcf: new net/minecraft/client/animation/Keyframe
      // 3bd2: dup
      // 3bd3: ldc_w 2.46
      // 3bd6: ldc_w 0.128
      // 3bd9: ldc_w -0.97
      // 3bdc: ldc_w -0.603
      // 3bdf: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3be2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3be5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3be8: aastore
      // 3be9: dup
      // 3bea: bipush 39
      // 3bec: new net/minecraft/client/animation/Keyframe
      // 3bef: dup
      // 3bf0: ldc_w 2.5
      // 3bf3: ldc_w 0.044
      // 3bf6: ldc_w -1.065
      // 3bf9: ldc_w -0.287
      // 3bfc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3bff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c02: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c05: aastore
      // 3c06: dup
      // 3c07: bipush 40
      // 3c09: new net/minecraft/client/animation/Keyframe
      // 3c0c: dup
      // 3c0d: ldc 2.54
      // 3c0f: ldc_w 0.016
      // 3c12: ldc_w -1.1
      // 3c15: ldc_w -0.172
      // 3c18: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3c1b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c1e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c21: aastore
      // 3c22: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3c25: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3c28: ldc_w "CTR_upperarm_right"
      // 3c2b: new net/minecraft/client/animation/AnimationChannel
      // 3c2e: dup
      // 3c2f: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3c32: bipush 61
      // 3c34: anewarray 28
      // 3c37: dup
      // 3c38: bipush 0
      // 3c39: new net/minecraft/client/animation/Keyframe
      // 3c3c: dup
      // 3c3d: fconst_0
      // 3c3e: ldc_w 0.839
      // 3c41: ldc_w -2.513
      // 3c44: ldc_w 0.452
      // 3c47: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c4a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c4d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c50: aastore
      // 3c51: dup
      // 3c52: bipush 1
      // 3c53: new net/minecraft/client/animation/Keyframe
      // 3c56: dup
      // 3c57: ldc 0.04
      // 3c59: ldc_w -1.918
      // 3c5c: ldc_w -2.021
      // 3c5f: ldc_w 2.277
      // 3c62: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c65: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c68: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c6b: aastore
      // 3c6c: dup
      // 3c6d: bipush 2
      // 3c6e: new net/minecraft/client/animation/Keyframe
      // 3c71: dup
      // 3c72: ldc 0.08
      // 3c74: ldc_w -4.49
      // 3c77: ldc_w 0.632
      // 3c7a: ldc_w 3.872
      // 3c7d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c80: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c83: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c86: aastore
      // 3c87: dup
      // 3c88: bipush 3
      // 3c89: new net/minecraft/client/animation/Keyframe
      // 3c8c: dup
      // 3c8d: ldc 0.12
      // 3c8f: ldc_w -10.841
      // 3c92: ldc_w 4.916
      // 3c95: ldc_w 7.034
      // 3c98: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c9b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c9e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ca1: aastore
      // 3ca2: dup
      // 3ca3: bipush 4
      // 3ca4: new net/minecraft/client/animation/Keyframe
      // 3ca7: dup
      // 3ca8: ldc 0.17
      // 3caa: ldc_w -22.416
      // 3cad: ldc_w 9.563
      // 3cb0: ldc_w 21.332
      // 3cb3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3cb6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3cb9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3cbc: aastore
      // 3cbd: dup
      // 3cbe: bipush 5
      // 3cbf: new net/minecraft/client/animation/Keyframe
      // 3cc2: dup
      // 3cc3: ldc 0.21
      // 3cc5: ldc_w -31.647
      // 3cc8: ldc_w 12.104
      // 3ccb: ldc_w 33.861
      // 3cce: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3cd1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3cd4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3cd7: aastore
      // 3cd8: dup
      // 3cd9: bipush 6
      // 3cdb: new net/minecraft/client/animation/Keyframe
      // 3cde: dup
      // 3cdf: ldc 0.25
      // 3ce1: ldc_w -33.314
      // 3ce4: ldc_w 6.711
      // 3ce7: ldc_w 18.053
      // 3cea: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ced: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3cf0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3cf3: aastore
      // 3cf4: dup
      // 3cf5: bipush 7
      // 3cf7: new net/minecraft/client/animation/Keyframe
      // 3cfa: dup
      // 3cfb: ldc 0.29
      // 3cfd: ldc_w -38.722
      // 3d00: ldc_w -1.484
      // 3d03: ldc_w 30.141
      // 3d06: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d09: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d0c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d0f: aastore
      // 3d10: dup
      // 3d11: bipush 8
      // 3d13: new net/minecraft/client/animation/Keyframe
      // 3d16: dup
      // 3d17: ldc 0.33
      // 3d19: ldc_w -56.656
      // 3d1c: ldc_w 13.415
      // 3d1f: ldc_w 47.79
      // 3d22: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d25: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d28: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d2b: aastore
      // 3d2c: dup
      // 3d2d: bipush 9
      // 3d2f: new net/minecraft/client/animation/Keyframe
      // 3d32: dup
      // 3d33: ldc 0.38
      // 3d35: ldc_w -92.08
      // 3d38: ldc_w 5.676
      // 3d3b: ldc_w 85.989
      // 3d3e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d41: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d44: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d47: aastore
      // 3d48: dup
      // 3d49: bipush 10
      // 3d4b: new net/minecraft/client/animation/Keyframe
      // 3d4e: dup
      // 3d4f: ldc 0.42
      // 3d51: ldc_w -114.449
      // 3d54: ldc_w -3.896
      // 3d57: ldc_w 112.727
      // 3d5a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d5d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d60: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d63: aastore
      // 3d64: dup
      // 3d65: bipush 11
      // 3d67: new net/minecraft/client/animation/Keyframe
      // 3d6a: dup
      // 3d6b: ldc 0.46
      // 3d6d: ldc_w -115.945
      // 3d70: ldc_w -6.034
      // 3d73: ldc_w 115.656
      // 3d76: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d79: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d7c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d7f: aastore
      // 3d80: dup
      // 3d81: bipush 12
      // 3d83: new net/minecraft/client/animation/Keyframe
      // 3d86: dup
      // 3d87: ldc 0.5
      // 3d89: ldc_w -116.159
      // 3d8c: ldc_w -6.339
      // 3d8f: ldc_w 116.075
      // 3d92: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d95: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d98: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d9b: aastore
      // 3d9c: dup
      // 3d9d: bipush 13
      // 3d9f: new net/minecraft/client/animation/Keyframe
      // 3da2: dup
      // 3da3: ldc_w 0.54
      // 3da6: ldc_w -115.85
      // 3da9: ldc_w -6.161
      // 3dac: ldc_w 115.785
      // 3daf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3db2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3db5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3db8: aastore
      // 3db9: dup
      // 3dba: bipush 14
      // 3dbc: new net/minecraft/client/animation/Keyframe
      // 3dbf: dup
      // 3dc0: ldc 0.58
      // 3dc2: ldc_w -115.067
      // 3dc5: ldc_w -5.705
      // 3dc8: ldc_w 115.046
      // 3dcb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3dce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3dd1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3dd4: aastore
      // 3dd5: dup
      // 3dd6: bipush 15
      // 3dd8: new net/minecraft/client/animation/Keyframe
      // 3ddb: dup
      // 3ddc: ldc 0.62
      // 3dde: ldc_w -114.021
      // 3de1: ldc_w -5.091
      // 3de4: ldc_w 114.053
      // 3de7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3dea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ded: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3df0: aastore
      // 3df1: dup
      // 3df2: bipush 16
      // 3df4: new net/minecraft/client/animation/Keyframe
      // 3df7: dup
      // 3df8: ldc 0.71
      // 3dfa: ldc_w -111.992
      // 3dfd: ldc_w -3.862
      // 3e00: ldc_w 112.087
      // 3e03: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e06: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e09: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e0c: aastore
      // 3e0d: dup
      // 3e0e: bipush 17
      // 3e10: new net/minecraft/client/animation/Keyframe
      // 3e13: dup
      // 3e14: ldc 0.75
      // 3e16: ldc_w -111.433
      // 3e19: ldc_w -3.486
      // 3e1c: ldc_w 111.504
      // 3e1f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e22: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e25: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e28: aastore
      // 3e29: dup
      // 3e2a: bipush 18
      // 3e2c: new net/minecraft/client/animation/Keyframe
      // 3e2f: dup
      // 3e30: ldc_w 0.79
      // 3e33: ldc_w -111.221
      // 3e36: ldc_w -3.26
      // 3e39: ldc_w 111.256
      // 3e3c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e3f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e42: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e45: aastore
      // 3e46: dup
      // 3e47: bipush 19
      // 3e49: new net/minecraft/client/animation/Keyframe
      // 3e4c: dup
      // 3e4d: ldc 0.83
      // 3e4f: ldc_w -111.144
      // 3e52: ldc_w -2.473
      // 3e55: ldc_w 111.165
      // 3e58: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e5b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e5e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e61: aastore
      // 3e62: dup
      // 3e63: bipush 20
      // 3e65: new net/minecraft/client/animation/Keyframe
      // 3e68: dup
      // 3e69: ldc 0.88
      // 3e6b: ldc_w -111.132
      // 3e6e: ldc_w -0.247
      // 3e71: ldc_w 111.152
      // 3e74: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e77: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e7a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e7d: aastore
      // 3e7e: dup
      // 3e7f: bipush 21
      // 3e81: new net/minecraft/client/animation/Keyframe
      // 3e84: dup
      // 3e85: ldc 0.92
      // 3e87: ldc_w -103.203
      // 3e8a: ldc_w 29.371
      // 3e8d: ldc_w 139.101
      // 3e90: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e93: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e96: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e99: aastore
      // 3e9a: dup
      // 3e9b: bipush 22
      // 3e9d: new net/minecraft/client/animation/Keyframe
      // 3ea0: dup
      // 3ea1: ldc 0.96
      // 3ea3: ldc_w -58.017
      // 3ea6: ldc_w 44.126
      // 3ea9: ldc_w 129.047
      // 3eac: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3eaf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3eb2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3eb5: aastore
      // 3eb6: dup
      // 3eb7: bipush 23
      // 3eb9: new net/minecraft/client/animation/Keyframe
      // 3ebc: dup
      // 3ebd: fconst_1
      // 3ebe: ldc_w 3.414
      // 3ec1: ldc_w 49.451
      // 3ec4: ldc_w 109.538
      // 3ec7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3eca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ecd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ed0: aastore
      // 3ed1: dup
      // 3ed2: bipush 24
      // 3ed4: new net/minecraft/client/animation/Keyframe
      // 3ed7: dup
      // 3ed8: ldc 1.04
      // 3eda: ldc_w 40.748
      // 3edd: ldc_w 50.635
      // 3ee0: ldc_w 96.476
      // 3ee3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ee6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ee9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3eec: aastore
      // 3eed: dup
      // 3eee: bipush 25
      // 3ef0: new net/minecraft/client/animation/Keyframe
      // 3ef3: dup
      // 3ef4: ldc 1.08
      // 3ef6: ldc_w 47.44
      // 3ef9: ldc_w 51.947
      // 3efc: ldc_w 97.207
      // 3eff: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f02: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f05: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f08: aastore
      // 3f09: dup
      // 3f0a: bipush 26
      // 3f0c: new net/minecraft/client/animation/Keyframe
      // 3f0f: dup
      // 3f10: ldc 1.12
      // 3f12: ldc_w 53.469
      // 3f15: ldc_w 53.425
      // 3f18: ldc_w 99.963
      // 3f1b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f1e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f21: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f24: aastore
      // 3f25: dup
      // 3f26: bipush 27
      // 3f28: new net/minecraft/client/animation/Keyframe
      // 3f2b: dup
      // 3f2c: ldc 1.17
      // 3f2e: ldc_w 59.237
      // 3f31: ldc_w 54.931
      // 3f34: ldc_w 103.252
      // 3f37: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f3a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f3d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f40: aastore
      // 3f41: dup
      // 3f42: bipush 28
      // 3f44: new net/minecraft/client/animation/Keyframe
      // 3f47: dup
      // 3f48: ldc 1.21
      // 3f4a: ldc_w 64.798
      // 3f4d: ldc_w 56.438
      // 3f50: ldc_w 106.806
      // 3f53: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f56: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f59: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f5c: aastore
      // 3f5d: dup
      // 3f5e: bipush 29
      // 3f60: new net/minecraft/client/animation/Keyframe
      // 3f63: dup
      // 3f64: ldc_w 1.25
      // 3f67: ldc_w 70.15
      // 3f6a: ldc_w 57.93
      // 3f6d: ldc_w 110.507
      // 3f70: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f73: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f76: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f79: aastore
      // 3f7a: dup
      // 3f7b: bipush 30
      // 3f7d: new net/minecraft/client/animation/Keyframe
      // 3f80: dup
      // 3f81: ldc 1.29
      // 3f83: ldc_w 75.252
      // 3f86: ldc_w 59.388
      // 3f89: ldc_w 114.28
      // 3f8c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f8f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f92: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f95: aastore
      // 3f96: dup
      // 3f97: bipush 31
      // 3f99: new net/minecraft/client/animation/Keyframe
      // 3f9c: dup
      // 3f9d: ldc_w 1.33
      // 3fa0: ldc_w 80.008
      // 3fa3: ldc_w 60.787
      // 3fa6: ldc_w 118.053
      // 3fa9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3fac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3faf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3fb2: aastore
      // 3fb3: dup
      // 3fb4: bipush 32
      // 3fb6: new net/minecraft/client/animation/Keyframe
      // 3fb9: dup
      // 3fba: ldc 1.38
      // 3fbc: ldc_w 84.166
      // 3fbf: ldc_w 62.067
      // 3fc2: ldc_w 121.705
      // 3fc5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3fc8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3fcb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3fce: aastore
      // 3fcf: dup
      // 3fd0: bipush 33
      // 3fd2: new net/minecraft/client/animation/Keyframe
      // 3fd5: dup
      // 3fd6: ldc 1.42
      // 3fd8: ldc_w 86.304
      // 3fdb: ldc_w 62.895
      // 3fde: ldc_w 124.654
      // 3fe1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3fe4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3fe7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3fea: aastore
      // 3feb: dup
      // 3fec: bipush 34
      // 3fee: new net/minecraft/client/animation/Keyframe
      // 3ff1: dup
      // 3ff2: ldc 1.46
      // 3ff4: ldc_w 75.179
      // 3ff7: ldc_w 60.852
      // 3ffa: ldc_w 120.259
      // 3ffd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4000: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4003: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4006: aastore
      // 4007: dup
      // 4008: bipush 35
      // 400a: new net/minecraft/client/animation/Keyframe
      // 400d: dup
      // 400e: ldc 1.5
      // 4010: ldc_w 57.32
      // 4013: ldc_w 61.769
      // 4016: ldc_w 107.811
      // 4019: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 401c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 401f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4022: aastore
      // 4023: dup
      // 4024: bipush 36
      // 4026: new net/minecraft/client/animation/Keyframe
      // 4029: dup
      // 402a: ldc_w 1.54
      // 402d: ldc_w 33.331
      // 4030: ldc_w 63.378
      // 4033: ldc_w 86.694
      // 4036: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4039: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 403c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 403f: aastore
      // 4040: dup
      // 4041: bipush 37
      // 4043: new net/minecraft/client/animation/Keyframe
      // 4046: dup
      // 4047: ldc_w 1.58
      // 404a: ldc_w 7.37
      // 404d: ldc_w 63.411
      // 4050: ldc_w 60.943
      // 4053: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4056: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4059: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 405c: aastore
      // 405d: dup
      // 405e: bipush 38
      // 4060: new net/minecraft/client/animation/Keyframe
      // 4063: dup
      // 4064: ldc_w 1.62
      // 4067: ldc_w -17.864
      // 406a: ldc_w 61.986
      // 406d: ldc_w 33.588
      // 4070: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4073: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4076: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4079: aastore
      // 407a: dup
      // 407b: bipush 39
      // 407d: new net/minecraft/client/animation/Keyframe
      // 4080: dup
      // 4081: ldc 1.67
      // 4083: ldc_w -42.982
      // 4086: ldc_w 64.13
      // 4089: ldc_w 1.336
      // 408c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 408f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4092: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4095: aastore
      // 4096: dup
      // 4097: bipush 40
      // 4099: new net/minecraft/client/animation/Keyframe
      // 409c: dup
      // 409d: ldc_w 1.71
      // 40a0: ldc_w -79.858
      // 40a3: ldc_w 68.806
      // 40a6: ldc_w -48.876
      // 40a9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 40ac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 40af: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 40b2: aastore
      // 40b3: dup
      // 40b4: bipush 41
      // 40b6: new net/minecraft/client/animation/Keyframe
      // 40b9: dup
      // 40ba: ldc 1.75
      // 40bc: ldc_w -107.93
      // 40bf: ldc_w 64.187
      // 40c2: ldc_w -87.779
      // 40c5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 40c8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 40cb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 40ce: aastore
      // 40cf: dup
      // 40d0: bipush 42
      // 40d2: new net/minecraft/client/animation/Keyframe
      // 40d5: dup
      // 40d6: ldc 1.79
      // 40d8: ldc_w -106.905
      // 40db: ldc_w 57.572
      // 40de: ldc_w -88.755
      // 40e1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 40e4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 40e7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 40ea: aastore
      // 40eb: dup
      // 40ec: bipush 43
      // 40ee: new net/minecraft/client/animation/Keyframe
      // 40f1: dup
      // 40f2: ldc_w 1.83
      // 40f5: ldc_w -97.763
      // 40f8: ldc_w 56.265
      // 40fb: ldc_w -76.074
      // 40fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4101: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4104: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4107: aastore
      // 4108: dup
      // 4109: bipush 44
      // 410b: new net/minecraft/client/animation/Keyframe
      // 410e: dup
      // 410f: ldc 1.88
      // 4111: ldc_w -82.862
      // 4114: ldc_w 58.221
      // 4117: ldc_w -57.297
      // 411a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 411d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4120: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4123: aastore
      // 4124: dup
      // 4125: bipush 45
      // 4127: new net/minecraft/client/animation/Keyframe
      // 412a: dup
      // 412b: ldc_w 1.92
      // 412e: ldc_w -68.224
      // 4131: ldc_w 62.565
      // 4134: ldc_w -43.098
      // 4137: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 413a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 413d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4140: aastore
      // 4141: dup
      // 4142: bipush 46
      // 4144: new net/minecraft/client/animation/Keyframe
      // 4147: dup
      // 4148: ldc 1.96
      // 414a: ldc_w -50.561
      // 414d: ldc_w 67.18
      // 4150: ldc_w -28.69
      // 4153: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4156: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4159: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 415c: aastore
      // 415d: dup
      // 415e: bipush 47
      // 4160: new net/minecraft/client/animation/Keyframe
      // 4163: dup
      // 4164: fconst_2
      // 4165: ldc_w -33.61
      // 4168: ldc_w 65.551
      // 416b: ldc_w -13.3
      // 416e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4171: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4174: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4177: aastore
      // 4178: dup
      // 4179: bipush 48
      // 417b: new net/minecraft/client/animation/Keyframe
      // 417e: dup
      // 417f: ldc 2.04
      // 4181: ldc_w -25.389
      // 4184: ldc_w 59.774
      // 4187: ldc_w -4.753
      // 418a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 418d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4190: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4193: aastore
      // 4194: dup
      // 4195: bipush 49
      // 4197: new net/minecraft/client/animation/Keyframe
      // 419a: dup
      // 419b: ldc 2.08
      // 419d: ldc_w -13.271
      // 41a0: ldc_w 54.363
      // 41a3: ldc_w 9.306
      // 41a6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 41a9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 41ac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 41af: aastore
      // 41b0: dup
      // 41b1: bipush 50
      // 41b3: new net/minecraft/client/animation/Keyframe
      // 41b6: dup
      // 41b7: ldc 2.12
      // 41b9: ldc_w -13.649
      // 41bc: ldc_w 45.48
      // 41bf: ldc_w 7.653
      // 41c2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 41c5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 41c8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 41cb: aastore
      // 41cc: dup
      // 41cd: bipush 51
      // 41cf: new net/minecraft/client/animation/Keyframe
      // 41d2: dup
      // 41d3: ldc 2.17
      // 41d5: ldc_w -19.066
      // 41d8: ldc_w 33.423
      // 41db: ldc_w -5.327
      // 41de: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 41e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 41e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 41e7: aastore
      // 41e8: dup
      // 41e9: bipush 52
      // 41eb: new net/minecraft/client/animation/Keyframe
      // 41ee: dup
      // 41ef: ldc 2.21
      // 41f1: ldc_w -17.544
      // 41f4: ldc_w 23.987
      // 41f7: ldc_w -14.289
      // 41fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 41fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4200: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4203: aastore
      // 4204: dup
      // 4205: bipush 53
      // 4207: new net/minecraft/client/animation/Keyframe
      // 420a: dup
      // 420b: ldc 2.25
      // 420d: ldc_w -11.246
      // 4210: ldc_w 19.418
      // 4213: ldc_w -17.051
      // 4216: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4219: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 421c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 421f: aastore
      // 4220: dup
      // 4221: bipush 54
      // 4223: new net/minecraft/client/animation/Keyframe
      // 4226: dup
      // 4227: ldc 2.29
      // 4229: ldc_w -4.235
      // 422c: ldc_w 16.018
      // 422f: ldc_w -13.197
      // 4232: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4235: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4238: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 423b: aastore
      // 423c: dup
      // 423d: bipush 55
      // 423f: new net/minecraft/client/animation/Keyframe
      // 4242: dup
      // 4243: ldc 2.33
      // 4245: ldc_w -0.318
      // 4248: ldc_w 10.993
      // 424b: ldc_w -7.959
      // 424e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4251: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4254: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4257: aastore
      // 4258: dup
      // 4259: bipush 56
      // 425b: new net/minecraft/client/animation/Keyframe
      // 425e: dup
      // 425f: ldc 2.38
      // 4261: ldc_w 0.445
      // 4264: ldc_w 5.657
      // 4267: ldc_w -5.732
      // 426a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 426d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4270: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4273: aastore
      // 4274: dup
      // 4275: bipush 57
      // 4277: new net/minecraft/client/animation/Keyframe
      // 427a: dup
      // 427b: ldc_w 2.42
      // 427e: ldc_w 0.826
      // 4281: ldc_w 1.777
      // 4284: ldc_w -3.863
      // 4287: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 428a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 428d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4290: aastore
      // 4291: dup
      // 4292: bipush 58
      // 4294: new net/minecraft/client/animation/Keyframe
      // 4297: dup
      // 4298: ldc_w 2.46
      // 429b: ldc_w 0.861
      // 429e: ldc_w -0.861
      // 42a1: ldc_w -2.332
      // 42a4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 42a7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42aa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42ad: aastore
      // 42ae: dup
      // 42af: bipush 59
      // 42b1: new net/minecraft/client/animation/Keyframe
      // 42b4: dup
      // 42b5: ldc_w 2.5
      // 42b8: ldc_w 0.728
      // 42bb: ldc_w -2.34
      // 42be: ldc_w -1.029
      // 42c1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 42c4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42c7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42ca: aastore
      // 42cb: dup
      // 42cc: bipush 60
      // 42ce: new net/minecraft/client/animation/Keyframe
      // 42d1: dup
      // 42d2: ldc 2.54
      // 42d4: ldc_w 0.839
      // 42d7: ldc_w -2.513
      // 42da: ldc_w 0.452
      // 42dd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 42e0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42e3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42e6: aastore
      // 42e7: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 42ea: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 42ed: ldc_w "CTR_upperarm_right"
      // 42f0: new net/minecraft/client/animation/AnimationChannel
      // 42f3: dup
      // 42f4: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 42f7: bipush 11
      // 42f9: anewarray 28
      // 42fc: dup
      // 42fd: bipush 0
      // 42fe: new net/minecraft/client/animation/Keyframe
      // 4301: dup
      // 4302: fconst_0
      // 4303: fconst_0
      // 4304: fconst_0
      // 4305: fconst_0
      // 4306: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4309: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 430c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 430f: aastore
      // 4310: dup
      // 4311: bipush 1
      // 4312: new net/minecraft/client/animation/Keyframe
      // 4315: dup
      // 4316: ldc 0.04
      // 4318: ldc_w -1.007
      // 431b: ldc_w -0.923
      // 431e: ldc_w -0.968
      // 4321: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4324: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4327: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 432a: aastore
      // 432b: dup
      // 432c: bipush 2
      // 432d: new net/minecraft/client/animation/Keyframe
      // 4330: dup
      // 4331: ldc 0.08
      // 4333: ldc_w -3.047
      // 4336: ldc_w -3.112
      // 4339: ldc_w -3.275
      // 433c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 433f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4342: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4345: aastore
      // 4346: dup
      // 4347: bipush 3
      // 4348: new net/minecraft/client/animation/Keyframe
      // 434b: dup
      // 434c: ldc 0.12
      // 434e: ldc_w -4.645
      // 4351: ldc_w -5.698
      // 4354: ldc_w -6.028
      // 4357: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 435a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 435d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4360: aastore
      // 4361: dup
      // 4362: bipush 4
      // 4363: new net/minecraft/client/animation/Keyframe
      // 4366: dup
      // 4367: ldc 0.17
      // 4369: ldc_w -5.147
      // 436c: ldc_w -7.839
      // 436f: ldc_w -8.336
      // 4372: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4375: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4378: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 437b: aastore
      // 437c: dup
      // 437d: bipush 5
      // 437e: new net/minecraft/client/animation/Keyframe
      // 4381: dup
      // 4382: ldc 0.21
      // 4384: ldc_w -7.168
      // 4387: ldc_w -8.793
      // 438a: ldc_w -9.303
      // 438d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4390: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4393: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4396: aastore
      // 4397: dup
      // 4398: bipush 6
      // 439a: new net/minecraft/client/animation/Keyframe
      // 439d: dup
      // 439e: ldc 0.25
      // 43a0: ldc_w -11.713
      // 43a3: ldc_w -8.028
      // 43a6: ldc_w -8.358
      // 43a9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 43ac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43af: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43b2: aastore
      // 43b3: dup
      // 43b4: bipush 7
      // 43b6: new net/minecraft/client/animation/Keyframe
      // 43b9: dup
      // 43ba: ldc 0.29
      // 43bc: ldc_w -12.115
      // 43bf: ldc_w -8.284
      // 43c2: ldc_w -6.448
      // 43c5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 43c8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43cb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43ce: aastore
      // 43cf: dup
      // 43d0: bipush 8
      // 43d2: new net/minecraft/client/animation/Keyframe
      // 43d5: dup
      // 43d6: ldc 0.33
      // 43d8: ldc_w -12.857
      // 43db: ldc_w -4.167
      // 43de: ldc_w -11.067
      // 43e1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 43e4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43e7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43ea: aastore
      // 43eb: dup
      // 43ec: bipush 9
      // 43ee: new net/minecraft/client/animation/Keyframe
      // 43f1: dup
      // 43f2: ldc 0.38
      // 43f4: ldc_w -6.468
      // 43f7: ldc_w -0.814
      // 43fa: ldc_w -5.534
      // 43fd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4400: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4403: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4406: aastore
      // 4407: dup
      // 4408: bipush 10
      // 440a: new net/minecraft/client/animation/Keyframe
      // 440d: dup
      // 440e: ldc 0.42
      // 4410: fconst_0
      // 4411: fconst_0
      // 4412: fconst_0
      // 4413: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4416: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4419: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 441c: aastore
      // 441d: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4420: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4423: ldc_w "CTR_lowerarm_right"
      // 4426: new net/minecraft/client/animation/AnimationChannel
      // 4429: dup
      // 442a: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 442d: bipush 55
      // 442f: anewarray 28
      // 4432: dup
      // 4433: bipush 0
      // 4434: new net/minecraft/client/animation/Keyframe
      // 4437: dup
      // 4438: fconst_0
      // 4439: ldc_w -0.021
      // 443c: ldc_w -1.724
      // 443f: ldc_w -0.014
      // 4442: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4445: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4448: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 444b: aastore
      // 444c: dup
      // 444d: bipush 1
      // 444e: new net/minecraft/client/animation/Keyframe
      // 4451: dup
      // 4452: ldc 0.04
      // 4454: ldc_w 5.362
      // 4457: ldc_w -8.591
      // 445a: ldc_w 1.604
      // 445d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4460: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4463: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4466: aastore
      // 4467: dup
      // 4468: bipush 2
      // 4469: new net/minecraft/client/animation/Keyframe
      // 446c: dup
      // 446d: ldc 0.08
      // 446f: ldc_w 5.947
      // 4472: ldc_w -17.261
      // 4475: ldc_w 1.002
      // 4478: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 447b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 447e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4481: aastore
      // 4482: dup
      // 4483: bipush 3
      // 4484: new net/minecraft/client/animation/Keyframe
      // 4487: dup
      // 4488: ldc 0.12
      // 448a: ldc_w 7.381
      // 448d: ldc_w -14.172
      // 4490: ldc_w -0.232
      // 4493: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4496: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4499: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 449c: aastore
      // 449d: dup
      // 449e: bipush 4
      // 449f: new net/minecraft/client/animation/Keyframe
      // 44a2: dup
      // 44a3: ldc 0.17
      // 44a5: ldc_w 12.255
      // 44a8: ldc_w 0.82
      // 44ab: ldc_w -0.328
      // 44ae: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 44b1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44b4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 44b7: aastore
      // 44b8: dup
      // 44b9: bipush 5
      // 44ba: new net/minecraft/client/animation/Keyframe
      // 44bd: dup
      // 44be: ldc 0.21
      // 44c0: ldc_w 15.555
      // 44c3: ldc_w 12.545
      // 44c6: ldc_w 0.55
      // 44c9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 44cc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44cf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 44d2: aastore
      // 44d3: dup
      // 44d4: bipush 6
      // 44d6: new net/minecraft/client/animation/Keyframe
      // 44d9: dup
      // 44da: ldc 0.25
      // 44dc: ldc_w -5.972
      // 44df: ldc_w 9.658
      // 44e2: ldc_w -11.931
      // 44e5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 44e8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44eb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 44ee: aastore
      // 44ef: dup
      // 44f0: bipush 7
      // 44f2: new net/minecraft/client/animation/Keyframe
      // 44f5: dup
      // 44f6: ldc 0.29
      // 44f8: ldc_w 13.367
      // 44fb: ldc_w 7.449
      // 44fe: ldc_w -1.074
      // 4501: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4504: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4507: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 450a: aastore
      // 450b: dup
      // 450c: bipush 8
      // 450e: new net/minecraft/client/animation/Keyframe
      // 4511: dup
      // 4512: ldc 0.33
      // 4514: ldc_w 20.258
      // 4517: ldc_w 10.922
      // 451a: ldc_w 8.076
      // 451d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4520: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4523: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4526: aastore
      // 4527: dup
      // 4528: bipush 9
      // 452a: new net/minecraft/client/animation/Keyframe
      // 452d: dup
      // 452e: ldc 0.38
      // 4530: ldc_w 8.884
      // 4533: ldc_w 0.129
      // 4536: ldc_w 9.254
      // 4539: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 453c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 453f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4542: aastore
      // 4543: dup
      // 4544: bipush 10
      // 4546: new net/minecraft/client/animation/Keyframe
      // 4549: dup
      // 454a: ldc 0.42
      // 454c: ldc_w -0.363
      // 454f: ldc_w -6.852
      // 4552: ldc_w 9.391
      // 4555: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4558: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 455b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 455e: aastore
      // 455f: dup
      // 4560: bipush 11
      // 4562: new net/minecraft/client/animation/Keyframe
      // 4565: dup
      // 4566: ldc 0.46
      // 4568: ldc_w 4.651
      // 456b: ldc_w -5.576
      // 456e: ldc_w 9.391
      // 4571: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4574: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4577: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 457a: aastore
      // 457b: dup
      // 457c: bipush 12
      // 457e: new net/minecraft/client/animation/Keyframe
      // 4581: dup
      // 4582: ldc 0.5
      // 4584: ldc_w 7.916
      // 4587: ldc_w -2.649
      // 458a: ldc_w 9.391
      // 458d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4590: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4593: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4596: aastore
      // 4597: dup
      // 4598: bipush 13
      // 459a: new net/minecraft/client/animation/Keyframe
      // 459d: dup
      // 459e: ldc_w 0.54
      // 45a1: ldc_w 9.681
      // 45a4: ldc_w 1.526
      // 45a7: ldc_w 9.391
      // 45aa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 45ad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 45b0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 45b3: aastore
      // 45b4: dup
      // 45b5: bipush 14
      // 45b7: new net/minecraft/client/animation/Keyframe
      // 45ba: dup
      // 45bb: ldc 0.58
      // 45bd: ldc_w 10.111
      // 45c0: ldc_w 6.414
      // 45c3: ldc_w 9.391
      // 45c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 45c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 45cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 45cf: aastore
      // 45d0: dup
      // 45d1: bipush 15
      // 45d3: new net/minecraft/client/animation/Keyframe
      // 45d6: dup
      // 45d7: ldc 0.62
      // 45d9: ldc_w 9.309
      // 45dc: ldc_w 11.379
      // 45df: ldc_w 9.391
      // 45e2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 45e5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 45e8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 45eb: aastore
      // 45ec: dup
      // 45ed: bipush 16
      // 45ef: new net/minecraft/client/animation/Keyframe
      // 45f2: dup
      // 45f3: ldc 0.67
      // 45f5: ldc_w 7.323
      // 45f8: ldc_w 15.727
      // 45fb: ldc_w 9.391
      // 45fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4601: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4604: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4607: aastore
      // 4608: dup
      // 4609: bipush 17
      // 460b: new net/minecraft/client/animation/Keyframe
      // 460e: dup
      // 460f: ldc 0.71
      // 4611: ldc_w 4.137
      // 4614: ldc_w 18.771
      // 4617: ldc_w 9.391
      // 461a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 461d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4620: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4623: aastore
      // 4624: dup
      // 4625: bipush 18
      // 4627: new net/minecraft/client/animation/Keyframe
      // 462a: dup
      // 462b: ldc 0.75
      // 462d: ldc_w -0.363
      // 4630: ldc_w 19.902
      // 4633: ldc_w 9.391
      // 4636: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4639: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 463c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 463f: aastore
      // 4640: dup
      // 4641: bipush 19
      // 4643: new net/minecraft/client/animation/Keyframe
      // 4646: dup
      // 4647: ldc_w 0.79
      // 464a: ldc_w -3.298
      // 464d: ldc_w 18.802
      // 4650: ldc_w 9.391
      // 4653: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4656: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4659: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 465c: aastore
      // 465d: dup
      // 465e: bipush 20
      // 4660: new net/minecraft/client/animation/Keyframe
      // 4663: dup
      // 4664: ldc 0.83
      // 4666: ldc_w -2.968
      // 4669: ldc_w 16.19
      // 466c: ldc_w 9.391
      // 466f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4672: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4675: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4678: aastore
      // 4679: dup
      // 467a: bipush 21
      // 467c: new net/minecraft/client/animation/Keyframe
      // 467f: dup
      // 4680: ldc 0.88
      // 4682: ldc_w -1.339
      // 4685: ldc_w 13.104
      // 4688: ldc_w 9.391
      // 468b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 468e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4691: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4694: aastore
      // 4695: dup
      // 4696: bipush 22
      // 4698: new net/minecraft/client/animation/Keyframe
      // 469b: dup
      // 469c: ldc 0.92
      // 469e: ldc_w -0.363
      // 46a1: ldc_w 10.579
      // 46a4: ldc_w 9.391
      // 46a7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 46aa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 46ad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 46b0: aastore
      // 46b1: dup
      // 46b2: bipush 23
      // 46b4: new net/minecraft/client/animation/Keyframe
      // 46b7: dup
      // 46b8: ldc 0.96
      // 46ba: ldc_w -24.13
      // 46bd: ldc_w 7.69
      // 46c0: ldc_w 4.82
      // 46c3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 46c6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 46c9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 46cc: aastore
      // 46cd: dup
      // 46ce: bipush 24
      // 46d0: new net/minecraft/client/animation/Keyframe
      // 46d3: dup
      // 46d4: fconst_1
      // 46d5: ldc_w -50.234
      // 46d8: ldc_w 1.071
      // 46db: ldc_w -10.15
      // 46de: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 46e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 46e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 46e7: aastore
      // 46e8: dup
      // 46e9: bipush 25
      // 46eb: new net/minecraft/client/animation/Keyframe
      // 46ee: dup
      // 46ef: ldc 1.04
      // 46f1: ldc_w -26.386
      // 46f4: ldc_w 6.177
      // 46f7: ldc_w -1.155
      // 46fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 46fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4700: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4703: aastore
      // 4704: dup
      // 4705: bipush 26
      // 4707: new net/minecraft/client/animation/Keyframe
      // 470a: dup
      // 470b: ldc 1.08
      // 470d: ldc_w -0.363
      // 4710: ldc_w 10.579
      // 4713: ldc_w 9.391
      // 4716: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4719: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 471c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 471f: aastore
      // 4720: dup
      // 4721: bipush 27
      // 4723: new net/minecraft/client/animation/Keyframe
      // 4726: dup
      // 4727: ldc 1.42
      // 4729: ldc_w -0.363
      // 472c: ldc_w 10.579
      // 472f: ldc_w 9.391
      // 4732: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4735: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4738: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 473b: aastore
      // 473c: dup
      // 473d: bipush 28
      // 473f: new net/minecraft/client/animation/Keyframe
      // 4742: dup
      // 4743: ldc 1.46
      // 4745: ldc_w -0.363
      // 4748: ldc_w 12.159
      // 474b: ldc_w 9.391
      // 474e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4751: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4754: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4757: aastore
      // 4758: dup
      // 4759: bipush 29
      // 475b: new net/minecraft/client/animation/Keyframe
      // 475e: dup
      // 475f: ldc 1.5
      // 4761: ldc_w -0.363
      // 4764: ldc_w 15.95
      // 4767: ldc_w 9.391
      // 476a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 476d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4770: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4773: aastore
      // 4774: dup
      // 4775: bipush 30
      // 4777: new net/minecraft/client/animation/Keyframe
      // 477a: dup
      // 477b: ldc_w 1.54
      // 477e: ldc_w -0.363
      // 4781: ldc_w 19.296
      // 4784: ldc_w 9.391
      // 4787: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 478a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 478d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4790: aastore
      // 4791: dup
      // 4792: bipush 31
      // 4794: new net/minecraft/client/animation/Keyframe
      // 4797: dup
      // 4798: ldc_w 1.58
      // 479b: ldc_w -0.363
      // 479e: ldc_w 19.958
      // 47a1: ldc_w 9.391
      // 47a4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 47a7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 47aa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 47ad: aastore
      // 47ae: dup
      // 47af: bipush 32
      // 47b1: new net/minecraft/client/animation/Keyframe
      // 47b4: dup
      // 47b5: ldc_w 1.62
      // 47b8: ldc_w -0.363
      // 47bb: ldc_w 19.153
      // 47be: ldc_w 9.391
      // 47c1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 47c4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 47c7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 47ca: aastore
      // 47cb: dup
      // 47cc: bipush 33
      // 47ce: new net/minecraft/client/animation/Keyframe
      // 47d1: dup
      // 47d2: ldc 1.67
      // 47d4: ldc_w -0.363
      // 47d7: ldc_w 19.065
      // 47da: ldc_w 9.391
      // 47dd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 47e0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 47e3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 47e6: aastore
      // 47e7: dup
      // 47e8: bipush 34
      // 47ea: new net/minecraft/client/animation/Keyframe
      // 47ed: dup
      // 47ee: ldc_w 1.71
      // 47f1: ldc_w -0.363
      // 47f4: ldc_w 18.592
      // 47f7: ldc_w 9.391
      // 47fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 47fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4800: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4803: aastore
      // 4804: dup
      // 4805: bipush 35
      // 4807: new net/minecraft/client/animation/Keyframe
      // 480a: dup
      // 480b: ldc 1.75
      // 480d: ldc_w -0.363
      // 4810: ldc_w 19.89
      // 4813: ldc_w 9.391
      // 4816: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4819: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 481c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 481f: aastore
      // 4820: dup
      // 4821: bipush 36
      // 4823: new net/minecraft/client/animation/Keyframe
      // 4826: dup
      // 4827: ldc 1.79
      // 4829: ldc_w -0.473
      // 482c: ldc_w 19.106
      // 482f: ldc_w 9.25
      // 4832: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4835: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4838: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 483b: aastore
      // 483c: dup
      // 483d: bipush 37
      // 483f: new net/minecraft/client/animation/Keyframe
      // 4842: dup
      // 4843: ldc_w 1.83
      // 4846: ldc_w -0.703
      // 4849: ldc_w 13.215
      // 484c: ldc_w 8.918
      // 484f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4852: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4855: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4858: aastore
      // 4859: dup
      // 485a: bipush 38
      // 485c: new net/minecraft/client/animation/Keyframe
      // 485f: dup
      // 4860: ldc 1.88
      // 4862: ldc_w -0.9
      // 4865: ldc_w 4.378
      // 4868: ldc_w 8.515
      // 486b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 486e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4871: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4874: aastore
      // 4875: dup
      // 4876: bipush 39
      // 4878: new net/minecraft/client/animation/Keyframe
      // 487b: dup
      // 487c: ldc_w 1.92
      // 487f: ldc_w -0.969
      // 4882: ldc_w -6.804
      // 4885: ldc_w 8.088
      // 4888: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 488b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 488e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4891: aastore
      // 4892: dup
      // 4893: bipush 40
      // 4895: new net/minecraft/client/animation/Keyframe
      // 4898: dup
      // 4899: ldc 1.96
      // 489b: ldc -0.936
      // 489d: ldc_w -17.102
      // 48a0: ldc_w 7.593
      // 48a3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 48a6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 48a9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 48ac: aastore
      // 48ad: dup
      // 48ae: bipush 41
      // 48b0: new net/minecraft/client/animation/Keyframe
      // 48b3: dup
      // 48b4: fconst_2
      // 48b5: ldc_w -1.074
      // 48b8: ldc_w -18.845
      // 48bb: ldc_w 6.945
      // 48be: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 48c1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 48c4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 48c7: aastore
      // 48c8: dup
      // 48c9: bipush 42
      // 48cb: new net/minecraft/client/animation/Keyframe
      // 48ce: dup
      // 48cf: ldc 2.04
      // 48d1: ldc_w -1.512
      // 48d4: ldc_w -14.104
      // 48d7: ldc_w 6.129
      // 48da: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 48dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 48e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 48e3: aastore
      // 48e4: dup
      // 48e5: bipush 43
      // 48e7: new net/minecraft/client/animation/Keyframe
      // 48ea: dup
      // 48eb: ldc 2.08
      // 48ed: ldc_w -1.547
      // 48f0: ldc_w -17.562
      // 48f3: ldc_w 5.433
      // 48f6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 48f9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 48fc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 48ff: aastore
      // 4900: dup
      // 4901: bipush 44
      // 4903: new net/minecraft/client/animation/Keyframe
      // 4906: dup
      // 4907: ldc 2.12
      // 4909: ldc_w -2.17
      // 490c: ldc_w -11.907
      // 490f: ldc_w 4.475
      // 4912: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4915: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4918: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 491b: aastore
      // 491c: dup
      // 491d: bipush 45
      // 491f: new net/minecraft/client/animation/Keyframe
      // 4922: dup
      // 4923: ldc 2.17
      // 4925: ldc_w -4.02
      // 4928: ldc_w 3.476
      // 492b: ldc_w 2.741
      // 492e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4931: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4934: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4937: aastore
      // 4938: dup
      // 4939: bipush 46
      // 493b: new net/minecraft/client/animation/Keyframe
      // 493e: dup
      // 493f: ldc 2.21
      // 4941: ldc_w -5.669
      // 4944: ldc_w 11.607
      // 4947: ldc_w 0.918
      // 494a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 494d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4950: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4953: aastore
      // 4954: dup
      // 4955: bipush 47
      // 4957: new net/minecraft/client/animation/Keyframe
      // 495a: dup
      // 495b: ldc 2.25
      // 495d: ldc_w -5.354
      // 4960: ldc_w 8.494
      // 4963: ldc_w 0.522
      // 4966: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4969: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 496c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 496f: aastore
      // 4970: dup
      // 4971: bipush 48
      // 4973: new net/minecraft/client/animation/Keyframe
      // 4976: dup
      // 4977: ldc 2.29
      // 4979: ldc_w -3.677
      // 497c: ldc_w -3.156
      // 497f: ldc_w 0.982
      // 4982: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4985: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4988: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 498b: aastore
      // 498c: dup
      // 498d: bipush 49
      // 498f: new net/minecraft/client/animation/Keyframe
      // 4992: dup
      // 4993: ldc 2.33
      // 4995: ldc_w -2.74
      // 4998: ldc_w -10.227
      // 499b: ldc_w 0.84
      // 499e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 49a1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 49a4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 49a7: aastore
      // 49a8: dup
      // 49a9: bipush 50
      // 49ab: new net/minecraft/client/animation/Keyframe
      // 49ae: dup
      // 49af: ldc 2.38
      // 49b1: ldc_w -2.65
      // 49b4: ldc_w -7.463
      // 49b7: ldc_w 0.277
      // 49ba: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 49bd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 49c0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 49c3: aastore
      // 49c4: dup
      // 49c5: bipush 51
      // 49c7: new net/minecraft/client/animation/Keyframe
      // 49ca: dup
      // 49cb: ldc_w 2.42
      // 49ce: ldc_w -2.207
      // 49d1: ldc_w -4.942
      // 49d4: ldc_w -0.079
      // 49d7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 49da: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 49dd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 49e0: aastore
      // 49e1: dup
      // 49e2: bipush 52
      // 49e4: new net/minecraft/client/animation/Keyframe
      // 49e7: dup
      // 49e8: ldc_w 2.46
      // 49eb: ldc_w -1.395
      // 49ee: ldc_w -2.733
      // 49f1: ldc_w -0.183
      // 49f4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 49f7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 49fa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 49fd: aastore
      // 49fe: dup
      // 49ff: bipush 53
      // 4a01: new net/minecraft/client/animation/Keyframe
      // 4a04: dup
      // 4a05: ldc_w 2.5
      // 4a08: ldc_w -0.459
      // 4a0b: ldc_w -1.232
      // 4a0e: ldc_w -0.088
      // 4a11: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a14: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a17: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a1a: aastore
      // 4a1b: dup
      // 4a1c: bipush 54
      // 4a1e: new net/minecraft/client/animation/Keyframe
      // 4a21: dup
      // 4a22: ldc 2.54
      // 4a24: ldc_w -0.021
      // 4a27: ldc_w -1.724
      // 4a2a: ldc_w -0.014
      // 4a2d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a30: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a33: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a36: aastore
      // 4a37: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4a3a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4a3d: ldc_w "CTR_upperarm_right.001"
      // 4a40: new net/minecraft/client/animation/AnimationChannel
      // 4a43: dup
      // 4a44: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4a47: bipush 61
      // 4a49: anewarray 28
      // 4a4c: dup
      // 4a4d: bipush 0
      // 4a4e: new net/minecraft/client/animation/Keyframe
      // 4a51: dup
      // 4a52: fconst_0
      // 4a53: ldc_w 0.168
      // 4a56: ldc_w 17.864
      // 4a59: ldc_w 2.048
      // 4a5c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a5f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a62: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a65: aastore
      // 4a66: dup
      // 4a67: bipush 1
      // 4a68: new net/minecraft/client/animation/Keyframe
      // 4a6b: dup
      // 4a6c: ldc 0.04
      // 4a6e: ldc 0.62
      // 4a70: ldc_w 15.857
      // 4a73: ldc_w 2.375
      // 4a76: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a79: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a7c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a7f: aastore
      // 4a80: dup
      // 4a81: bipush 2
      // 4a82: new net/minecraft/client/animation/Keyframe
      // 4a85: dup
      // 4a86: ldc 0.08
      // 4a88: ldc_w 0.532
      // 4a8b: ldc_w 13.779
      // 4a8e: ldc_w 2.24
      // 4a91: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a94: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a97: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a9a: aastore
      // 4a9b: dup
      // 4a9c: bipush 3
      // 4a9d: new net/minecraft/client/animation/Keyframe
      // 4aa0: dup
      // 4aa1: ldc 0.12
      // 4aa3: ldc_w 1.695
      // 4aa6: ldc_w 8.482
      // 4aa9: ldc_w 2.802
      // 4aac: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4aaf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ab2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ab5: aastore
      // 4ab6: dup
      // 4ab7: bipush 4
      // 4ab8: new net/minecraft/client/animation/Keyframe
      // 4abb: dup
      // 4abc: ldc 0.17
      // 4abe: ldc_w -0.174
      // 4ac1: ldc_w -8.331
      // 4ac4: ldc_w 3.701
      // 4ac7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4aca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4acd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ad0: aastore
      // 4ad1: dup
      // 4ad2: bipush 5
      // 4ad3: new net/minecraft/client/animation/Keyframe
      // 4ad6: dup
      // 4ad7: ldc 0.21
      // 4ad9: ldc_w -2.61
      // 4adc: ldc_w -18.769
      // 4adf: ldc_w -0.4
      // 4ae2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ae5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ae8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4aeb: aastore
      // 4aec: dup
      // 4aed: bipush 6
      // 4aef: new net/minecraft/client/animation/Keyframe
      // 4af2: dup
      // 4af3: ldc 0.25
      // 4af5: ldc_w -2.826
      // 4af8: ldc_w -6.013
      // 4afb: ldc_w -1.334
      // 4afe: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b01: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b04: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b07: aastore
      // 4b08: dup
      // 4b09: bipush 7
      // 4b0b: new net/minecraft/client/animation/Keyframe
      // 4b0e: dup
      // 4b0f: ldc 0.29
      // 4b11: ldc_w -6.978
      // 4b14: ldc_w -12.405
      // 4b17: ldc_w -6.586
      // 4b1a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b1d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b20: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b23: aastore
      // 4b24: dup
      // 4b25: bipush 8
      // 4b27: new net/minecraft/client/animation/Keyframe
      // 4b2a: dup
      // 4b2b: ldc 0.33
      // 4b2d: ldc_w 0.661
      // 4b30: ldc_w -19.908
      // 4b33: ldc_w 3.587
      // 4b36: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b39: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b3c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b3f: aastore
      // 4b40: dup
      // 4b41: bipush 9
      // 4b43: new net/minecraft/client/animation/Keyframe
      // 4b46: dup
      // 4b47: ldc 0.38
      // 4b49: ldc_w 7.891
      // 4b4c: ldc_w -43.917
      // 4b4f: ldc_w 11.032
      // 4b52: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b55: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b58: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b5b: aastore
      // 4b5c: dup
      // 4b5d: bipush 10
      // 4b5f: new net/minecraft/client/animation/Keyframe
      // 4b62: dup
      // 4b63: ldc 0.42
      // 4b65: ldc_w 1.835
      // 4b68: ldc_w -51.467
      // 4b6b: ldc_w 4.409
      // 4b6e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b71: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b74: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b77: aastore
      // 4b78: dup
      // 4b79: bipush 11
      // 4b7b: new net/minecraft/client/animation/Keyframe
      // 4b7e: dup
      // 4b7f: ldc 0.46
      // 4b81: ldc_w 0.981
      // 4b84: ldc_w -44.129
      // 4b87: ldc_w 3.542
      // 4b8a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b8d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b90: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b93: aastore
      // 4b94: dup
      // 4b95: bipush 12
      // 4b97: new net/minecraft/client/animation/Keyframe
      // 4b9a: dup
      // 4b9b: ldc 0.5
      // 4b9d: ldc_w -0.174
      // 4ba0: ldc_w -38.921
      // 4ba3: ldc_w 2.318
      // 4ba6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ba9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4bac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4baf: aastore
      // 4bb0: dup
      // 4bb1: bipush 13
      // 4bb3: new net/minecraft/client/animation/Keyframe
      // 4bb6: dup
      // 4bb7: ldc_w 0.54
      // 4bba: ldc_w -1.204
      // 4bbd: ldc_w -35.515
      // 4bc0: ldc_w 1.199
      // 4bc3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4bc6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4bc9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4bcc: aastore
      // 4bcd: dup
      // 4bce: bipush 14
      // 4bd0: new net/minecraft/client/animation/Keyframe
      // 4bd3: dup
      // 4bd4: ldc 0.58
      // 4bd6: ldc_w -1.944
      // 4bd9: ldc_w -33.874
      // 4bdc: ldc_w 0.381
      // 4bdf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4be2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4be5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4be8: aastore
      // 4be9: dup
      // 4bea: bipush 15
      // 4bec: new net/minecraft/client/animation/Keyframe
      // 4bef: dup
      // 4bf0: ldc 0.62
      // 4bf2: ldc_w -2.289
      // 4bf5: ldc_w -33.61
      // 4bf8: ldc_w -0.003
      // 4bfb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4bfe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c01: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c04: aastore
      // 4c05: dup
      // 4c06: bipush 16
      // 4c08: new net/minecraft/client/animation/Keyframe
      // 4c0b: dup
      // 4c0c: ldc 0.67
      // 4c0e: ldc_w -2.165
      // 4c11: ldc_w -34.45
      // 4c14: ldc_w 0.134
      // 4c17: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c1a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c1d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c20: aastore
      // 4c21: dup
      // 4c22: bipush 17
      // 4c24: new net/minecraft/client/animation/Keyframe
      // 4c27: dup
      // 4c28: ldc 0.71
      // 4c2a: ldc_w -1.51
      // 4c2d: ldc_w -36.242
      // 4c30: ldc_w 0.859
      // 4c33: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c36: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c39: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c3c: aastore
      // 4c3d: dup
      // 4c3e: bipush 18
      // 4c40: new net/minecraft/client/animation/Keyframe
      // 4c43: dup
      // 4c44: ldc 0.75
      // 4c46: ldc_w -0.278
      // 4c49: ldc_w -38.996
      // 4c4c: ldc_w 2.203
      // 4c4f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c52: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c55: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c58: aastore
      // 4c59: dup
      // 4c5a: bipush 19
      // 4c5c: new net/minecraft/client/animation/Keyframe
      // 4c5f: dup
      // 4c60: ldc_w 0.79
      // 4c63: ldc_w 0.379
      // 4c66: ldc_w -40.96
      // 4c69: ldc_w 2.91
      // 4c6c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c6f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c72: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c75: aastore
      // 4c76: dup
      // 4c77: bipush 20
      // 4c79: new net/minecraft/client/animation/Keyframe
      // 4c7c: dup
      // 4c7d: ldc 0.83
      // 4c7f: ldc 0.624
      // 4c81: ldc_w -42.941
      // 4c84: ldc_w 3.163
      // 4c87: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c8a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c8d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c90: aastore
      // 4c91: dup
      // 4c92: bipush 21
      // 4c94: new net/minecraft/client/animation/Keyframe
      // 4c97: dup
      // 4c98: ldc 0.88
      // 4c9a: ldc_w 0.405
      // 4c9d: ldc_w -46.034
      // 4ca0: ldc_w 2.906
      // 4ca3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ca6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ca9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4cac: aastore
      // 4cad: dup
      // 4cae: bipush 22
      // 4cb0: new net/minecraft/client/animation/Keyframe
      // 4cb3: dup
      // 4cb4: ldc 0.92
      // 4cb6: ldc_w -21.465
      // 4cb9: ldc_w -40.528
      // 4cbc: ldc_w -20.925
      // 4cbf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4cc2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4cc5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4cc8: aastore
      // 4cc9: dup
      // 4cca: bipush 23
      // 4ccc: new net/minecraft/client/animation/Keyframe
      // 4ccf: dup
      // 4cd0: ldc 0.96
      // 4cd2: ldc_w -39.015
      // 4cd5: ldc_w -29.936
      // 4cd8: ldc_w -41.223
      // 4cdb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4cde: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ce1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ce4: aastore
      // 4ce5: dup
      // 4ce6: bipush 24
      // 4ce8: new net/minecraft/client/animation/Keyframe
      // 4ceb: dup
      // 4cec: fconst_1
      // 4ced: ldc_w -5.517
      // 4cf0: ldc_w -81.62
      // 4cf3: ldc_w -3.354
      // 4cf6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4cf9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4cfc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4cff: aastore
      // 4d00: dup
      // 4d01: bipush 25
      // 4d03: new net/minecraft/client/animation/Keyframe
      // 4d06: dup
      // 4d07: ldc 1.04
      // 4d09: ldc_w 25.489
      // 4d0c: ldc_w -51.09
      // 4d0f: ldc_w 29.649
      // 4d12: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d15: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d18: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d1b: aastore
      // 4d1c: dup
      // 4d1d: bipush 26
      // 4d1f: new net/minecraft/client/animation/Keyframe
      // 4d22: dup
      // 4d23: ldc 1.08
      // 4d25: ldc_w 29.448
      // 4d28: ldc_w -39.443
      // 4d2b: ldc_w 34.607
      // 4d2e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d31: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d34: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d37: aastore
      // 4d38: dup
      // 4d39: bipush 27
      // 4d3b: new net/minecraft/client/animation/Keyframe
      // 4d3e: dup
      // 4d3f: ldc 1.12
      // 4d41: ldc_w 30.518
      // 4d44: ldc_w -33.62
      // 4d47: ldc_w 36.369
      // 4d4a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d4d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d50: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d53: aastore
      // 4d54: dup
      // 4d55: bipush 28
      // 4d57: new net/minecraft/client/animation/Keyframe
      // 4d5a: dup
      // 4d5b: ldc 1.17
      // 4d5d: ldc_w 29.754
      // 4d60: ldc_w -33.273
      // 4d63: ldc_w 35.575
      // 4d66: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d69: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d6c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d6f: aastore
      // 4d70: dup
      // 4d71: bipush 29
      // 4d73: new net/minecraft/client/animation/Keyframe
      // 4d76: dup
      // 4d77: ldc 1.21
      // 4d79: ldc_w 27.484
      // 4d7c: ldc_w -38.298
      // 4d7f: ldc_w 32.58
      // 4d82: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d85: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d88: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d8b: aastore
      // 4d8c: dup
      // 4d8d: bipush 30
      // 4d8f: new net/minecraft/client/animation/Keyframe
      // 4d92: dup
      // 4d93: ldc_w 1.25
      // 4d96: ldc_w 22.961
      // 4d99: ldc_w -45.654
      // 4d9c: ldc_w 27.212
      // 4d9f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4da2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4da5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4da8: aastore
      // 4da9: dup
      // 4daa: bipush 31
      // 4dac: new net/minecraft/client/animation/Keyframe
      // 4daf: dup
      // 4db0: ldc 1.29
      // 4db2: ldc_w 16.669
      // 4db5: ldc_w -52.246
      // 4db8: ldc_w 20.229
      // 4dbb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4dbe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4dc1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4dc4: aastore
      // 4dc5: dup
      // 4dc6: bipush 32
      // 4dc8: new net/minecraft/client/animation/Keyframe
      // 4dcb: dup
      // 4dcc: ldc_w 1.33
      // 4dcf: ldc_w 10.548
      // 4dd2: ldc_w -55.881
      // 4dd5: ldc_w 13.637
      // 4dd8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ddb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4dde: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4de1: aastore
      // 4de2: dup
      // 4de3: bipush 33
      // 4de5: new net/minecraft/client/animation/Keyframe
      // 4de8: dup
      // 4de9: ldc 1.38
      // 4deb: ldc_w 6.391
      // 4dee: ldc_w -56.972
      // 4df1: ldc_w 9.21
      // 4df4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4df7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4dfa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4dfd: aastore
      // 4dfe: dup
      // 4dff: bipush 34
      // 4e01: new net/minecraft/client/animation/Keyframe
      // 4e04: dup
      // 4e05: ldc 1.42
      // 4e07: ldc_w 4.594
      // 4e0a: ldc_w -57.227
      // 4e0d: ldc_w 7.301
      // 4e10: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e13: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e16: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e19: aastore
      // 4e1a: dup
      // 4e1b: bipush 35
      // 4e1d: new net/minecraft/client/animation/Keyframe
      // 4e20: dup
      // 4e21: ldc 1.46
      // 4e23: ldc_w 0.823
      // 4e26: ldc_w -58.956
      // 4e29: ldc_w 3.293
      // 4e2c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e2f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e32: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e35: aastore
      // 4e36: dup
      // 4e37: bipush 36
      // 4e39: new net/minecraft/client/animation/Keyframe
      // 4e3c: dup
      // 4e3d: ldc 1.5
      // 4e3f: ldc_w -3.452
      // 4e42: ldc_w -54.495
      // 4e45: ldc_w -1.242
      // 4e48: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e4b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e4e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e51: aastore
      // 4e52: dup
      // 4e53: bipush 37
      // 4e55: new net/minecraft/client/animation/Keyframe
      // 4e58: dup
      // 4e59: ldc_w 1.54
      // 4e5c: ldc_w -6.515
      // 4e5f: ldc_w -45.027
      // 4e62: ldc_w -4.576
      // 4e65: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e68: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e6b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e6e: aastore
      // 4e6f: dup
      // 4e70: bipush 38
      // 4e72: new net/minecraft/client/animation/Keyframe
      // 4e75: dup
      // 4e76: ldc_w 1.58
      // 4e79: ldc_w -8.127
      // 4e7c: ldc_w -33.499
      // 4e7f: ldc_w -6.52
      // 4e82: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e85: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e88: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e8b: aastore
      // 4e8c: dup
      // 4e8d: bipush 39
      // 4e8f: new net/minecraft/client/animation/Keyframe
      // 4e92: dup
      // 4e93: ldc_w 1.62
      // 4e96: ldc_w -8.381
      // 4e99: ldc_w -21.772
      // 4e9c: ldc_w -7.279
      // 4e9f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ea2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ea5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ea8: aastore
      // 4ea9: dup
      // 4eaa: bipush 40
      // 4eac: new net/minecraft/client/animation/Keyframe
      // 4eaf: dup
      // 4eb0: ldc 1.67
      // 4eb2: ldc_w -6.778
      // 4eb5: ldc_w -9.39
      // 4eb8: ldc_w -7.25
      // 4ebb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ebe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ec1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ec4: aastore
      // 4ec5: dup
      // 4ec6: bipush 41
      // 4ec8: new net/minecraft/client/animation/Keyframe
      // 4ecb: dup
      // 4ecc: ldc_w 1.71
      // 4ecf: ldc_w -7.714
      // 4ed2: ldc_w 0.996
      // 4ed5: ldc_w -1.183
      // 4ed8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4edb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ede: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ee1: aastore
      // 4ee2: dup
      // 4ee3: bipush 42
      // 4ee5: new net/minecraft/client/animation/Keyframe
      // 4ee8: dup
      // 4ee9: ldc 1.75
      // 4eeb: ldc_w -3.485
      // 4eee: ldc_w 8.222
      // 4ef1: ldc_w -0.836
      // 4ef4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ef7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4efa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4efd: aastore
      // 4efe: dup
      // 4eff: bipush 43
      // 4f01: new net/minecraft/client/animation/Keyframe
      // 4f04: dup
      // 4f05: ldc 1.79
      // 4f07: ldc_w -1.787
      // 4f0a: ldc_w 12.981
      // 4f0d: ldc_w 0.392
      // 4f10: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f13: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f16: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f19: aastore
      // 4f1a: dup
      // 4f1b: bipush 44
      // 4f1d: new net/minecraft/client/animation/Keyframe
      // 4f20: dup
      // 4f21: ldc_w 1.83
      // 4f24: ldc_w -1.004
      // 4f27: ldc_w 13.489
      // 4f2a: ldc_w 1.014
      // 4f2d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f30: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f33: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f36: aastore
      // 4f37: dup
      // 4f38: bipush 45
      // 4f3a: new net/minecraft/client/animation/Keyframe
      // 4f3d: dup
      // 4f3e: ldc 1.88
      // 4f40: ldc_w -0.967
      // 4f43: ldc_w 10.781
      // 4f46: ldc_w 0.991
      // 4f49: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f4c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f4f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f52: aastore
      // 4f53: dup
      // 4f54: bipush 46
      // 4f56: new net/minecraft/client/animation/Keyframe
      // 4f59: dup
      // 4f5a: ldc_w 1.92
      // 4f5d: ldc_w -0.865
      // 4f60: ldc_w 6.198
      // 4f63: ldc_w 0.901
      // 4f66: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f69: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f6c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f6f: aastore
      // 4f70: dup
      // 4f71: bipush 47
      // 4f73: new net/minecraft/client/animation/Keyframe
      // 4f76: dup
      // 4f77: ldc 1.96
      // 4f79: ldc_w -0.221
      // 4f7c: ldc_w 0.523
      // 4f7f: ldc_w 0.263
      // 4f82: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f85: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f88: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f8b: aastore
      // 4f8c: dup
      // 4f8d: bipush 48
      // 4f8f: new net/minecraft/client/animation/Keyframe
      // 4f92: dup
      // 4f93: fconst_2
      // 4f94: ldc_w -3.296
      // 4f97: ldc_w -2.751
      // 4f9a: ldc_w 3.348
      // 4f9d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4fa0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4fa3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4fa6: aastore
      // 4fa7: dup
      // 4fa8: bipush 49
      // 4faa: new net/minecraft/client/animation/Keyframe
      // 4fad: dup
      // 4fae: ldc 2.04
      // 4fb0: ldc_w -1.842
      // 4fb3: ldc_w -4.662
      // 4fb6: ldc_w 1.916
      // 4fb9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4fbc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4fbf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4fc2: aastore
      // 4fc3: dup
      // 4fc4: bipush 50
      // 4fc6: new net/minecraft/client/animation/Keyframe
      // 4fc9: dup
      // 4fca: ldc 2.08
      // 4fcc: ldc_w -1.337
      // 4fcf: ldc_w -10.936
      // 4fd2: ldc_w 1.41
      // 4fd5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4fd8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4fdb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4fde: aastore
      // 4fdf: dup
      // 4fe0: bipush 51
      // 4fe2: new net/minecraft/client/animation/Keyframe
      // 4fe5: dup
      // 4fe6: ldc 2.12
      // 4fe8: ldc_w -1.363
      // 4feb: ldc_w -9.604
      // 4fee: ldc_w 1.47
      // 4ff1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ff4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ff7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ffa: aastore
      // 4ffb: dup
      // 4ffc: bipush 52
      // 4ffe: new net/minecraft/client/animation/Keyframe
      // 5001: dup
      // 5002: ldc 2.17
      // 5004: ldc_w -0.3
      // 5007: ldc_w 1.455
      // 500a: ldc_w 0.573
      // 500d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5010: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5013: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5016: aastore
      // 5017: dup
      // 5018: bipush 53
      // 501a: new net/minecraft/client/animation/Keyframe
      // 501d: dup
      // 501e: ldc 2.21
      // 5020: ldc_w -0.68
      // 5023: ldc_w 12.591
      // 5026: ldc_w 1.252
      // 5029: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 502c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 502f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5032: aastore
      // 5033: dup
      // 5034: bipush 54
      // 5036: new net/minecraft/client/animation/Keyframe
      // 5039: dup
      // 503a: ldc 2.25
      // 503c: ldc_w -0.602
      // 503f: ldc_w 18.414
      // 5042: ldc_w 1.413
      // 5045: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5048: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 504b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 504e: aastore
      // 504f: dup
      // 5050: bipush 55
      // 5052: new net/minecraft/client/animation/Keyframe
      // 5055: dup
      // 5056: ldc 2.29
      // 5058: ldc_w -0.61
      // 505b: ldc_w 17.593
      // 505e: ldc_w 1.395
      // 5061: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5064: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5067: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 506a: aastore
      // 506b: dup
      // 506c: bipush 56
      // 506e: new net/minecraft/client/animation/Keyframe
      // 5071: dup
      // 5072: ldc 2.33
      // 5074: ldc_w -0.61
      // 5077: ldc_w 15.467
      // 507a: ldc_w 1.363
      // 507d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5080: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5083: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5086: aastore
      // 5087: dup
      // 5088: bipush 57
      // 508a: new net/minecraft/client/animation/Keyframe
      // 508d: dup
      // 508e: ldc 2.38
      // 5090: ldc_w -0.474
      // 5093: ldc_w 16.387
      // 5096: ldc_w 1.49
      // 5099: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 509c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 509f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 50a2: aastore
      // 50a3: dup
      // 50a4: bipush 58
      // 50a6: new net/minecraft/client/animation/Keyframe
      // 50a9: dup
      // 50aa: ldc_w 2.46
      // 50ad: ldc_w -0.076
      // 50b0: ldc_w 17.978
      // 50b3: ldc_w 1.846
      // 50b6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 50b9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 50bc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 50bf: aastore
      // 50c0: dup
      // 50c1: bipush 59
      // 50c3: new net/minecraft/client/animation/Keyframe
      // 50c6: dup
      // 50c7: ldc_w 2.5
      // 50ca: ldc_w 0.125
      // 50cd: ldc_w 18.455
      // 50d0: ldc_w 2.023
      // 50d3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 50d6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 50d9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 50dc: aastore
      // 50dd: dup
      // 50de: bipush 60
      // 50e0: new net/minecraft/client/animation/Keyframe
      // 50e3: dup
      // 50e4: ldc 2.54
      // 50e6: ldc_w 0.168
      // 50e9: ldc_w 17.864
      // 50ec: ldc_w 2.048
      // 50ef: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 50f2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 50f5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 50f8: aastore
      // 50f9: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 50fc: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 50ff: ldc_w "CTR_upperarm_right.001"
      // 5102: new net/minecraft/client/animation/AnimationChannel
      // 5105: dup
      // 5106: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5109: bipush 57
      // 510b: anewarray 28
      // 510e: dup
      // 510f: bipush 0
      // 5110: new net/minecraft/client/animation/Keyframe
      // 5113: dup
      // 5114: fconst_0
      // 5115: dconst_1
      // 5116: dconst_1
      // 5117: ldc2_w 1.6740000247955322
      // 511a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 511d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5120: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5123: aastore
      // 5124: dup
      // 5125: bipush 1
      // 5126: new net/minecraft/client/animation/Keyframe
      // 5129: dup
      // 512a: ldc 0.04
      // 512c: dconst_1
      // 512d: dconst_1
      // 512e: ldc2_w 1.6670000553131104
      // 5131: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5134: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5137: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 513a: aastore
      // 513b: dup
      // 513c: bipush 2
      // 513d: new net/minecraft/client/animation/Keyframe
      // 5140: dup
      // 5141: ldc 0.12
      // 5143: dconst_1
      // 5144: dconst_1
      // 5145: ldc2_w 1.562000036239624
      // 5148: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 514b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 514e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5151: aastore
      // 5152: dup
      // 5153: bipush 3
      // 5154: new net/minecraft/client/animation/Keyframe
      // 5157: dup
      // 5158: ldc 0.17
      // 515a: dconst_1
      // 515b: dconst_1
      // 515c: ldc2_w 1.6349999904632568
      // 515f: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5162: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5165: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5168: aastore
      // 5169: dup
      // 516a: bipush 4
      // 516b: new net/minecraft/client/animation/Keyframe
      // 516e: dup
      // 516f: ldc 0.21
      // 5171: dconst_1
      // 5172: dconst_1
      // 5173: ldc2_w 1.5329999923706055
      // 5176: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5179: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 517c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 517f: aastore
      // 5180: dup
      // 5181: bipush 5
      // 5182: new net/minecraft/client/animation/Keyframe
      // 5185: dup
      // 5186: ldc 0.25
      // 5188: dconst_1
      // 5189: dconst_1
      // 518a: ldc2_w 0.9359999895095825
      // 518d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5190: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5193: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5196: aastore
      // 5197: dup
      // 5198: bipush 6
      // 519a: new net/minecraft/client/animation/Keyframe
      // 519d: dup
      // 519e: ldc 0.29
      // 51a0: dconst_1
      // 51a1: dconst_1
      // 51a2: ldc2_w 0.652999997138977
      // 51a5: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 51a8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 51ab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 51ae: aastore
      // 51af: dup
      // 51b0: bipush 7
      // 51b2: new net/minecraft/client/animation/Keyframe
      // 51b5: dup
      // 51b6: ldc 0.33
      // 51b8: dconst_1
      // 51b9: dconst_1
      // 51ba: ldc2_w 0.5680000185966492
      // 51bd: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 51c0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 51c3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 51c6: aastore
      // 51c7: dup
      // 51c8: bipush 8
      // 51ca: new net/minecraft/client/animation/Keyframe
      // 51cd: dup
      // 51ce: ldc 0.38
      // 51d0: dconst_1
      // 51d1: dconst_1
      // 51d2: ldc2_w 0.6620000004768372
      // 51d5: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 51d8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 51db: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 51de: aastore
      // 51df: dup
      // 51e0: bipush 9
      // 51e2: new net/minecraft/client/animation/Keyframe
      // 51e5: dup
      // 51e6: ldc 0.42
      // 51e8: dconst_1
      // 51e9: dconst_1
      // 51ea: ldc2_w 0.7099999785423279
      // 51ed: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 51f0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 51f3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 51f6: aastore
      // 51f7: dup
      // 51f8: bipush 10
      // 51fa: new net/minecraft/client/animation/Keyframe
      // 51fd: dup
      // 51fe: ldc 0.5
      // 5200: dconst_1
      // 5201: dconst_1
      // 5202: ldc2_w 0.9459999799728394
      // 5205: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5208: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 520b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 520e: aastore
      // 520f: dup
      // 5210: bipush 11
      // 5212: new net/minecraft/client/animation/Keyframe
      // 5215: dup
      // 5216: ldc_w 0.54
      // 5219: dconst_1
      // 521a: dconst_1
      // 521b: ldc2_w 1.0369999408721924
      // 521e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5221: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5224: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5227: aastore
      // 5228: dup
      // 5229: bipush 12
      // 522b: new net/minecraft/client/animation/Keyframe
      // 522e: dup
      // 522f: ldc 0.58
      // 5231: dconst_1
      // 5232: dconst_1
      // 5233: ldc2_w 1.1139999628067017
      // 5236: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5239: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 523c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 523f: aastore
      // 5240: dup
      // 5241: bipush 13
      // 5243: new net/minecraft/client/animation/Keyframe
      // 5246: dup
      // 5247: ldc 0.62
      // 5249: dconst_1
      // 524a: dconst_1
      // 524b: ldc2_w 1.1699999570846558
      // 524e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5251: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5254: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5257: aastore
      // 5258: dup
      // 5259: bipush 14
      // 525b: new net/minecraft/client/animation/Keyframe
      // 525e: dup
      // 525f: ldc 0.67
      // 5261: dconst_1
      // 5262: dconst_1
      // 5263: ldc2_w 1.194000005722046
      // 5266: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5269: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 526c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 526f: aastore
      // 5270: dup
      // 5271: bipush 15
      // 5273: new net/minecraft/client/animation/Keyframe
      // 5276: dup
      // 5277: ldc 0.71
      // 5279: dconst_1
      // 527a: dconst_1
      // 527b: ldc2_w 1.1660000085830688
      // 527e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5281: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5284: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5287: aastore
      // 5288: dup
      // 5289: bipush 16
      // 528b: new net/minecraft/client/animation/Keyframe
      // 528e: dup
      // 528f: ldc 0.75
      // 5291: dconst_1
      // 5292: dconst_1
      // 5293: ldc2_w 1.0509999990463257
      // 5296: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5299: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 529c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 529f: aastore
      // 52a0: dup
      // 52a1: bipush 17
      // 52a3: new net/minecraft/client/animation/Keyframe
      // 52a6: dup
      // 52a7: ldc_w 0.79
      // 52aa: dconst_1
      // 52ab: dconst_1
      // 52ac: ldc2_w 0.9639999866485596
      // 52af: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 52b2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 52b5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 52b8: aastore
      // 52b9: dup
      // 52ba: bipush 18
      // 52bc: new net/minecraft/client/animation/Keyframe
      // 52bf: dup
      // 52c0: ldc 0.83
      // 52c2: dconst_1
      // 52c3: dconst_1
      // 52c4: ldc2_w 0.8970000147819519
      // 52c7: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 52ca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 52cd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 52d0: aastore
      // 52d1: dup
      // 52d2: bipush 19
      // 52d4: new net/minecraft/client/animation/Keyframe
      // 52d7: dup
      // 52d8: ldc 0.88
      // 52da: dconst_1
      // 52db: dconst_1
      // 52dc: ldc2_w 0.7979999780654907
      // 52df: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 52e2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 52e5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 52e8: aastore
      // 52e9: dup
      // 52ea: bipush 20
      // 52ec: new net/minecraft/client/animation/Keyframe
      // 52ef: dup
      // 52f0: ldc 0.92
      // 52f2: dconst_1
      // 52f3: dconst_1
      // 52f4: ldc2_w 0.44999998807907104
      // 52f7: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 52fa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 52fd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5300: aastore
      // 5301: dup
      // 5302: bipush 21
      // 5304: new net/minecraft/client/animation/Keyframe
      // 5307: dup
      // 5308: ldc 0.96
      // 530a: dconst_1
      // 530b: dconst_1
      // 530c: ldc2_w 1.5240000486373901
      // 530f: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5312: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5315: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5318: aastore
      // 5319: dup
      // 531a: bipush 22
      // 531c: new net/minecraft/client/animation/Keyframe
      // 531f: dup
      // 5320: fconst_1
      // 5321: dconst_1
      // 5322: dconst_1
      // 5323: ldc2_w 3.2039999961853027
      // 5326: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5329: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 532c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 532f: aastore
      // 5330: dup
      // 5331: bipush 23
      // 5333: new net/minecraft/client/animation/Keyframe
      // 5336: dup
      // 5337: ldc 1.04
      // 5339: dconst_1
      // 533a: dconst_1
      // 533b: ldc2_w 3.5230000019073486
      // 533e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5341: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5344: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5347: aastore
      // 5348: dup
      // 5349: bipush 24
      // 534b: new net/minecraft/client/animation/Keyframe
      // 534e: dup
      // 534f: ldc 1.08
      // 5351: dconst_1
      // 5352: dconst_1
      // 5353: ldc2_w 3.572000026702881
      // 5356: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5359: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 535c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 535f: aastore
      // 5360: dup
      // 5361: bipush 25
      // 5363: new net/minecraft/client/animation/Keyframe
      // 5366: dup
      // 5367: ldc 1.12
      // 5369: dconst_1
      // 536a: dconst_1
      // 536b: ldc2_w 3.4590001106262207
      // 536e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5371: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5374: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5377: aastore
      // 5378: dup
      // 5379: bipush 26
      // 537b: new net/minecraft/client/animation/Keyframe
      // 537e: dup
      // 537f: ldc 1.17
      // 5381: dconst_1
      // 5382: dconst_1
      // 5383: ldc2_w 3.194999933242798
      // 5386: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5389: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 538c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 538f: aastore
      // 5390: dup
      // 5391: bipush 27
      // 5393: new net/minecraft/client/animation/Keyframe
      // 5396: dup
      // 5397: ldc 1.21
      // 5399: dconst_1
      // 539a: dconst_1
      // 539b: ldc2_w 2.9010000228881836
      // 539e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 53a1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 53a4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 53a7: aastore
      // 53a8: dup
      // 53a9: bipush 28
      // 53ab: new net/minecraft/client/animation/Keyframe
      // 53ae: dup
      // 53af: ldc_w 1.25
      // 53b2: dconst_1
      // 53b3: dconst_1
      // 53b4: ldc2_w 2.684999942779541
      // 53b7: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 53ba: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 53bd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 53c0: aastore
      // 53c1: dup
      // 53c2: bipush 29
      // 53c4: new net/minecraft/client/animation/Keyframe
      // 53c7: dup
      // 53c8: ldc 1.29
      // 53ca: dconst_1
      // 53cb: dconst_1
      // 53cc: ldc2_w 2.625
      // 53cf: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 53d2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 53d5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 53d8: aastore
      // 53d9: dup
      // 53da: bipush 30
      // 53dc: new net/minecraft/client/animation/Keyframe
      // 53df: dup
      // 53e0: ldc_w 1.33
      // 53e3: dconst_1
      // 53e4: dconst_1
      // 53e5: ldc2_w 2.7060000896453857
      // 53e8: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 53eb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 53ee: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 53f1: aastore
      // 53f2: dup
      // 53f3: bipush 31
      // 53f5: new net/minecraft/client/animation/Keyframe
      // 53f8: dup
      // 53f9: ldc 1.38
      // 53fb: dconst_1
      // 53fc: dconst_1
      // 53fd: ldc2_w 2.8329999446868896
      // 5400: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5403: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5406: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5409: aastore
      // 540a: dup
      // 540b: bipush 32
      // 540d: new net/minecraft/client/animation/Keyframe
      // 5410: dup
      // 5411: ldc 1.42
      // 5413: dconst_1
      // 5414: dconst_1
      // 5415: ldc2_w 2.8919999599456787
      // 5418: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 541b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 541e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5421: aastore
      // 5422: dup
      // 5423: bipush 33
      // 5425: new net/minecraft/client/animation/Keyframe
      // 5428: dup
      // 5429: ldc 1.46
      // 542b: dconst_1
      // 542c: dconst_1
      // 542d: ldc2_w 2.9019999504089355
      // 5430: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5433: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5436: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5439: aastore
      // 543a: dup
      // 543b: bipush 34
      // 543d: new net/minecraft/client/animation/Keyframe
      // 5440: dup
      // 5441: ldc 1.5
      // 5443: dconst_1
      // 5444: dconst_1
      // 5445: ldc2_w 2.822000026702881
      // 5448: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 544b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 544e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5451: aastore
      // 5452: dup
      // 5453: bipush 35
      // 5455: new net/minecraft/client/animation/Keyframe
      // 5458: dup
      // 5459: ldc_w 1.54
      // 545c: dconst_1
      // 545d: dconst_1
      // 545e: ldc2_w 2.6679999828338623
      // 5461: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5464: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5467: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 546a: aastore
      // 546b: dup
      // 546c: bipush 36
      // 546e: new net/minecraft/client/animation/Keyframe
      // 5471: dup
      // 5472: ldc_w 1.62
      // 5475: dconst_1
      // 5476: dconst_1
      // 5477: ldc2_w 2.2860000133514404
      // 547a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 547d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5480: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5483: aastore
      // 5484: dup
      // 5485: bipush 37
      // 5487: new net/minecraft/client/animation/Keyframe
      // 548a: dup
      // 548b: ldc_w 1.71
      // 548e: dconst_1
      // 548f: dconst_1
      // 5490: ldc2_w 1.8930000066757202
      // 5493: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5496: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5499: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 549c: aastore
      // 549d: dup
      // 549e: bipush 38
      // 54a0: new net/minecraft/client/animation/Keyframe
      // 54a3: dup
      // 54a4: ldc 1.75
      // 54a6: dconst_1
      // 54a7: dconst_1
      // 54a8: ldc2_w 1.7710000276565552
      // 54ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 54ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 54b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 54b4: aastore
      // 54b5: dup
      // 54b6: bipush 39
      // 54b8: new net/minecraft/client/animation/Keyframe
      // 54bb: dup
      // 54bc: ldc 1.79
      // 54be: dconst_1
      // 54bf: dconst_1
      // 54c0: ldc2_w 1.7029999494552612
      // 54c3: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 54c6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 54c9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 54cc: aastore
      // 54cd: dup
      // 54ce: bipush 40
      // 54d0: new net/minecraft/client/animation/Keyframe
      // 54d3: dup
      // 54d4: ldc_w 1.83
      // 54d7: dconst_1
      // 54d8: dconst_1
      // 54d9: ldc2_w 1.6820000410079956
      // 54dc: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 54df: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 54e2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 54e5: aastore
      // 54e6: dup
      // 54e7: bipush 41
      // 54e9: new net/minecraft/client/animation/Keyframe
      // 54ec: dup
      // 54ed: ldc_w 1.92
      // 54f0: dconst_1
      // 54f1: dconst_1
      // 54f2: ldc2_w 1.6959999799728394
      // 54f5: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 54f8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 54fb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 54fe: aastore
      // 54ff: dup
      // 5500: bipush 42
      // 5502: new net/minecraft/client/animation/Keyframe
      // 5505: dup
      // 5506: ldc 1.96
      // 5508: dconst_1
      // 5509: dconst_1
      // 550a: ldc2_w 1.690999984741211
      // 550d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5510: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5513: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5516: aastore
      // 5517: dup
      // 5518: bipush 43
      // 551a: new net/minecraft/client/animation/Keyframe
      // 551d: dup
      // 551e: fconst_2
      // 551f: dconst_1
      // 5520: dconst_1
      // 5521: ldc2_w 1.6299999952316284
      // 5524: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5527: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 552a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 552d: aastore
      // 552e: dup
      // 552f: bipush 44
      // 5531: new net/minecraft/client/animation/Keyframe
      // 5534: dup
      // 5535: ldc 2.04
      // 5537: dconst_1
      // 5538: dconst_1
      // 5539: ldc2_w 1.5390000343322754
      // 553c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 553f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5542: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5545: aastore
      // 5546: dup
      // 5547: bipush 45
      // 5549: new net/minecraft/client/animation/Keyframe
      // 554c: dup
      // 554d: ldc 2.08
      // 554f: dconst_1
      // 5550: dconst_1
      // 5551: ldc2_w 1.527999997138977
      // 5554: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5557: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 555a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 555d: aastore
      // 555e: dup
      // 555f: bipush 46
      // 5561: new net/minecraft/client/animation/Keyframe
      // 5564: dup
      // 5565: ldc 2.12
      // 5567: dconst_1
      // 5568: dconst_1
      // 5569: ldc2_w 1.4359999895095825
      // 556c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 556f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5572: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5575: aastore
      // 5576: dup
      // 5577: bipush 47
      // 5579: new net/minecraft/client/animation/Keyframe
      // 557c: dup
      // 557d: ldc 2.17
      // 557f: dconst_1
      // 5580: dconst_1
      // 5581: ldc2_w 1.246000051498413
      // 5584: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5587: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 558a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 558d: aastore
      // 558e: dup
      // 558f: bipush 48
      // 5591: new net/minecraft/client/animation/Keyframe
      // 5594: dup
      // 5595: ldc 2.21
      // 5597: dconst_1
      // 5598: dconst_1
      // 5599: ldc2_w 1.11899995803833
      // 559c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 559f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 55a2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 55a5: aastore
      // 55a6: dup
      // 55a7: bipush 49
      // 55a9: new net/minecraft/client/animation/Keyframe
      // 55ac: dup
      // 55ad: ldc 2.25
      // 55af: dconst_1
      // 55b0: dconst_1
      // 55b1: ldc2_w 1.1100000143051147
      // 55b4: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 55b7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 55ba: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 55bd: aastore
      // 55be: dup
      // 55bf: bipush 50
      // 55c1: new net/minecraft/client/animation/Keyframe
      // 55c4: dup
      // 55c5: ldc 2.29
      // 55c7: dconst_1
      // 55c8: dconst_1
      // 55c9: ldc2_w 1.2050000429153442
      // 55cc: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 55cf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 55d2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 55d5: aastore
      // 55d6: dup
      // 55d7: bipush 51
      // 55d9: new net/minecraft/client/animation/Keyframe
      // 55dc: dup
      // 55dd: ldc 2.33
      // 55df: dconst_1
      // 55e0: dconst_1
      // 55e1: ldc2_w 1.3339999914169312
      // 55e4: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 55e7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 55ea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 55ed: aastore
      // 55ee: dup
      // 55ef: bipush 52
      // 55f1: new net/minecraft/client/animation/Keyframe
      // 55f4: dup
      // 55f5: ldc 2.38
      // 55f7: dconst_1
      // 55f8: dconst_1
      // 55f9: ldc2_w 1.4329999685287476
      // 55fc: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 55ff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5602: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5605: aastore
      // 5606: dup
      // 5607: bipush 53
      // 5609: new net/minecraft/client/animation/Keyframe
      // 560c: dup
      // 560d: ldc_w 2.42
      // 5610: dconst_1
      // 5611: dconst_1
      // 5612: ldc2_w 1.5269999504089355
      // 5615: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5618: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 561b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 561e: aastore
      // 561f: dup
      // 5620: bipush 54
      // 5622: new net/minecraft/client/animation/Keyframe
      // 5625: dup
      // 5626: ldc_w 2.46
      // 5629: dconst_1
      // 562a: dconst_1
      // 562b: ldc2_w 1.6019999980926514
      // 562e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5631: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5634: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5637: aastore
      // 5638: dup
      // 5639: bipush 55
      // 563b: new net/minecraft/client/animation/Keyframe
      // 563e: dup
      // 563f: ldc_w 2.5
      // 5642: dconst_1
      // 5643: dconst_1
      // 5644: ldc2_w 1.652999997138977
      // 5647: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 564a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 564d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5650: aastore
      // 5651: dup
      // 5652: bipush 56
      // 5654: new net/minecraft/client/animation/Keyframe
      // 5657: dup
      // 5658: ldc 2.54
      // 565a: dconst_1
      // 565b: dconst_1
      // 565c: ldc2_w 1.6740000247955322
      // 565f: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5662: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5665: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5668: aastore
      // 5669: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 566c: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 566f: ldc_w "CTR_leg_left"
      // 5672: new net/minecraft/client/animation/AnimationChannel
      // 5675: dup
      // 5676: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5679: bipush 54
      // 567b: anewarray 28
      // 567e: dup
      // 567f: bipush 0
      // 5680: new net/minecraft/client/animation/Keyframe
      // 5683: dup
      // 5684: fconst_0
      // 5685: ldc_w -177.066
      // 5688: ldc_w 52.262
      // 568b: ldc_w 182.934
      // 568e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5691: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5694: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5697: aastore
      // 5698: dup
      // 5699: bipush 1
      // 569a: new net/minecraft/client/animation/Keyframe
      // 569d: dup
      // 569e: ldc 0.04
      // 56a0: ldc_w -177.224
      // 56a3: ldc_w 53.344
      // 56a6: ldc_w 182.776
      // 56a9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 56ac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 56af: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 56b2: aastore
      // 56b3: dup
      // 56b4: bipush 2
      // 56b5: new net/minecraft/client/animation/Keyframe
      // 56b8: dup
      // 56b9: ldc 0.08
      // 56bb: ldc_w -177.735
      // 56be: ldc_w 56.248
      // 56c1: ldc_w 182.265
      // 56c4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 56c7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 56ca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 56cd: aastore
      // 56ce: dup
      // 56cf: bipush 3
      // 56d0: new net/minecraft/client/animation/Keyframe
      // 56d3: dup
      // 56d4: ldc 0.12
      // 56d6: ldc_w -178.765
      // 56d9: ldc_w 60.561
      // 56dc: ldc_w 181.235
      // 56df: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 56e2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 56e5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 56e8: aastore
      // 56e9: dup
      // 56ea: bipush 4
      // 56eb: new net/minecraft/client/animation/Keyframe
      // 56ee: dup
      // 56ef: ldc 0.17
      // 56f1: ldc_w -180.573
      // 56f4: ldc_w 65.669
      // 56f7: ldc_w 179.427
      // 56fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 56fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5700: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5703: aastore
      // 5704: dup
      // 5705: bipush 5
      // 5706: new net/minecraft/client/animation/Keyframe
      // 5709: dup
      // 570a: ldc 0.21
      // 570c: ldc_w -183.318
      // 570f: ldc_w 70.418
      // 5712: ldc_w 176.682
      // 5715: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5718: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 571b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 571e: aastore
      // 571f: dup
      // 5720: bipush 6
      // 5722: new net/minecraft/client/animation/Keyframe
      // 5725: dup
      // 5726: ldc 0.25
      // 5728: ldc_w -186.731
      // 572b: ldc_w 73.443
      // 572e: ldc_w 173.269
      // 5731: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5734: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5737: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 573a: aastore
      // 573b: dup
      // 573c: bipush 7
      // 573e: new net/minecraft/client/animation/Keyframe
      // 5741: dup
      // 5742: ldc 0.29
      // 5744: ldc_w -190.046
      // 5747: ldc_w 74.259
      // 574a: ldc_w 169.954
      // 574d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5750: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5753: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5756: aastore
      // 5757: dup
      // 5758: bipush 8
      // 575a: new net/minecraft/client/animation/Keyframe
      // 575d: dup
      // 575e: ldc 0.33
      // 5760: ldc_w -188.538
      // 5763: ldc_w 62.663
      // 5766: ldc_w 171.462
      // 5769: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 576c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 576f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5772: aastore
      // 5773: dup
      // 5774: bipush 9
      // 5776: new net/minecraft/client/animation/Keyframe
      // 5779: dup
      // 577a: ldc 0.38
      // 577c: ldc_w -181.848
      // 577f: ldc_w 40.58
      // 5782: ldc_w 178.152
      // 5785: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5788: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 578b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 578e: aastore
      // 578f: dup
      // 5790: bipush 10
      // 5792: new net/minecraft/client/animation/Keyframe
      // 5795: dup
      // 5796: ldc 0.42
      // 5798: ldc_w -178.927
      // 579b: ldc_w 31.827
      // 579e: ldc_w 181.073
      // 57a1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 57a4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 57a7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 57aa: aastore
      // 57ab: dup
      // 57ac: bipush 11
      // 57ae: new net/minecraft/client/animation/Keyframe
      // 57b1: dup
      // 57b2: ldc 0.46
      // 57b4: ldc_w -180.1
      // 57b7: ldc_w 34.459
      // 57ba: ldc_w 179.9
      // 57bd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 57c0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 57c3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 57c6: aastore
      // 57c7: dup
      // 57c8: bipush 12
      // 57ca: new net/minecraft/client/animation/Keyframe
      // 57cd: dup
      // 57ce: ldc 0.5
      // 57d0: ldc_w -180.529
      // 57d3: ldc_w 33.696
      // 57d6: ldc_w 179.471
      // 57d9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 57dc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 57df: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 57e2: aastore
      // 57e3: dup
      // 57e4: bipush 13
      // 57e6: new net/minecraft/client/animation/Keyframe
      // 57e9: dup
      // 57ea: ldc_w 0.54
      // 57ed: ldc_w -180.593
      // 57f0: ldc_w 30.914
      // 57f3: ldc_w 179.407
      // 57f6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 57f9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 57fc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 57ff: aastore
      // 5800: dup
      // 5801: bipush 14
      // 5803: new net/minecraft/client/animation/Keyframe
      // 5806: dup
      // 5807: ldc 0.62
      // 5809: ldc_w -181.103
      // 580c: ldc_w 24.58
      // 580f: ldc_w 178.899
      // 5812: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5815: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5818: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 581b: aastore
      // 581c: dup
      // 581d: bipush 15
      // 581f: new net/minecraft/client/animation/Keyframe
      // 5822: dup
      // 5823: ldc 0.67
      // 5825: ldc_w -181.41
      // 5828: ldc_w 21.392
      // 582b: ldc_w 178.59
      // 582e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5831: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5834: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5837: aastore
      // 5838: dup
      // 5839: bipush 16
      // 583b: new net/minecraft/client/animation/Keyframe
      // 583e: dup
      // 583f: ldc 0.71
      // 5841: ldc_w -181.464
      // 5844: ldc_w 17.942
      // 5847: ldc_w 178.536
      // 584a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 584d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5850: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5853: aastore
      // 5854: dup
      // 5855: bipush 17
      // 5857: new net/minecraft/client/animation/Keyframe
      // 585a: dup
      // 585b: ldc 0.75
      // 585d: ldc_w -180.511
      // 5860: ldc_w 12.842
      // 5863: ldc_w 179.489
      // 5866: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5869: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 586c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 586f: aastore
      // 5870: dup
      // 5871: bipush 18
      // 5873: new net/minecraft/client/animation/Keyframe
      // 5876: dup
      // 5877: ldc_w 0.79
      // 587a: ldc_w -179.623
      // 587d: ldc_w 10.06
      // 5880: ldc_w 180.377
      // 5883: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5886: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5889: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 588c: aastore
      // 588d: dup
      // 588e: bipush 19
      // 5890: new net/minecraft/client/animation/Keyframe
      // 5893: dup
      // 5894: ldc 0.83
      // 5896: ldc_w -178.843
      // 5899: ldc_w 7.972
      // 589c: ldc_w 181.157
      // 589f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 58a2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 58a5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 58a8: aastore
      // 58a9: dup
      // 58aa: bipush 20
      // 58ac: new net/minecraft/client/animation/Keyframe
      // 58af: dup
      // 58b0: ldc 0.88
      // 58b2: ldc_w -177.454
      // 58b5: ldc_w 4.757
      // 58b8: ldc_w 182.546
      // 58bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 58be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 58c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 58c4: aastore
      // 58c5: dup
      // 58c6: bipush 21
      // 58c8: new net/minecraft/client/animation/Keyframe
      // 58cb: dup
      // 58cc: ldc 0.92
      // 58ce: ldc_w -172.562
      // 58d1: ldc_w -4.512
      // 58d4: ldc_w 187.438
      // 58d7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 58da: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 58dd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 58e0: aastore
      // 58e1: dup
      // 58e2: bipush 22
      // 58e4: new net/minecraft/client/animation/Keyframe
      // 58e7: dup
      // 58e8: ldc 0.96
      // 58ea: ldc_w -177.807
      // 58ed: ldc_w 15.084
      // 58f0: ldc_w 182.193
      // 58f3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 58f6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 58f9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 58fc: aastore
      // 58fd: dup
      // 58fe: bipush 23
      // 5900: new net/minecraft/client/animation/Keyframe
      // 5903: dup
      // 5904: fconst_1
      // 5905: ldc_w -179.916
      // 5908: ldc_w 35.826
      // 590b: ldc_w 180.084
      // 590e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5911: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5914: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5917: aastore
      // 5918: dup
      // 5919: bipush 24
      // 591b: new net/minecraft/client/animation/Keyframe
      // 591e: dup
      // 591f: ldc 1.04
      // 5921: ldc_w -178.889
      // 5924: ldc_w 19.409
      // 5927: ldc_w 181.111
      // 592a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 592d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5930: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5933: aastore
      // 5934: dup
      // 5935: bipush 25
      // 5937: new net/minecraft/client/animation/Keyframe
      // 593a: dup
      // 593b: ldc 1.08
      // 593d: ldc_w -179.303
      // 5940: ldc_w 19.46
      // 5943: ldc_w 180.697
      // 5946: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5949: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 594c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 594f: aastore
      // 5950: dup
      // 5951: bipush 26
      // 5953: new net/minecraft/client/animation/Keyframe
      // 5956: dup
      // 5957: ldc 1.12
      // 5959: ldc_w -179.378
      // 595c: ldc_w 20.597
      // 595f: ldc_w 180.623
      // 5962: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5965: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5968: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 596b: aastore
      // 596c: dup
      // 596d: bipush 27
      // 596f: new net/minecraft/client/animation/Keyframe
      // 5972: dup
      // 5973: ldc 1.17
      // 5975: ldc_w -179.317
      // 5978: ldc_w 21.783
      // 597b: ldc_w 180.683
      // 597e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5981: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5984: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5987: aastore
      // 5988: dup
      // 5989: bipush 28
      // 598b: new net/minecraft/client/animation/Keyframe
      // 598e: dup
      // 598f: ldc_w 1.25
      // 5992: ldc_w -179.132
      // 5995: ldc_w 21.54
      // 5998: ldc_w 180.868
      // 599b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 599e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 59a1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 59a4: aastore
      // 59a5: dup
      // 59a6: bipush 29
      // 59a8: new net/minecraft/client/animation/Keyframe
      // 59ab: dup
      // 59ac: ldc 1.29
      // 59ae: ldc_w -179.05
      // 59b1: ldc_w 21.61
      // 59b4: ldc_w 180.95
      // 59b7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 59ba: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 59bd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 59c0: aastore
      // 59c1: dup
      // 59c2: bipush 30
      // 59c4: new net/minecraft/client/animation/Keyframe
      // 59c7: dup
      // 59c8: ldc 1.38
      // 59ca: ldc_w -178.939
      // 59cd: ldc_w 22.161
      // 59d0: ldc_w 181.061
      // 59d3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 59d6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 59d9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 59dc: aastore
      // 59dd: dup
      // 59de: bipush 31
      // 59e0: new net/minecraft/client/animation/Keyframe
      // 59e3: dup
      // 59e4: ldc 1.42
      // 59e6: ldc_w -178.92
      // 59e9: ldc_w 22.503
      // 59ec: ldc_w 181.08
      // 59ef: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 59f2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 59f5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 59f8: aastore
      // 59f9: dup
      // 59fa: bipush 32
      // 59fc: new net/minecraft/client/animation/Keyframe
      // 59ff: dup
      // 5a00: ldc 1.46
      // 5a02: ldc_w -178.91
      // 5a05: ldc_w 22.177
      // 5a08: ldc_w 181.091
      // 5a0b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a0e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a11: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a14: aastore
      // 5a15: dup
      // 5a16: bipush 33
      // 5a18: new net/minecraft/client/animation/Keyframe
      // 5a1b: dup
      // 5a1c: ldc 1.5
      // 5a1e: ldc_w -178.906
      // 5a21: ldc_w 22.011
      // 5a24: ldc_w 181.094
      // 5a27: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a2a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a2d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a30: aastore
      // 5a31: dup
      // 5a32: bipush 34
      // 5a34: new net/minecraft/client/animation/Keyframe
      // 5a37: dup
      // 5a38: ldc 1.75
      // 5a3a: ldc_w -178.912
      // 5a3d: ldc_w 21.981
      // 5a40: ldc_w 181.088
      // 5a43: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a46: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a49: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a4c: aastore
      // 5a4d: dup
      // 5a4e: bipush 35
      // 5a50: new net/minecraft/client/animation/Keyframe
      // 5a53: dup
      // 5a54: ldc 1.79
      // 5a56: ldc_w -178.919
      // 5a59: ldc_w 22.707
      // 5a5c: ldc_w 181.081
      // 5a5f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a62: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a65: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a68: aastore
      // 5a69: dup
      // 5a6a: bipush 36
      // 5a6c: new net/minecraft/client/animation/Keyframe
      // 5a6f: dup
      // 5a70: ldc_w 1.83
      // 5a73: ldc_w -178.947
      // 5a76: ldc_w 24.617
      // 5a79: ldc_w 181.054
      // 5a7c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a7f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a82: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a85: aastore
      // 5a86: dup
      // 5a87: bipush 37
      // 5a89: new net/minecraft/client/animation/Keyframe
      // 5a8c: dup
      // 5a8d: ldc 1.88
      // 5a8f: ldc_w -179.005
      // 5a92: ldc_w 27.587
      // 5a95: ldc_w 180.995
      // 5a98: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a9b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a9e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5aa1: aastore
      // 5aa2: dup
      // 5aa3: bipush 38
      // 5aa5: new net/minecraft/client/animation/Keyframe
      // 5aa8: dup
      // 5aa9: ldc_w 1.92
      // 5aac: ldc_w -179.103
      // 5aaf: ldc_w 31.591
      // 5ab2: ldc_w 180.897
      // 5ab5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5ab8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5abb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5abe: aastore
      // 5abf: dup
      // 5ac0: bipush 39
      // 5ac2: new net/minecraft/client/animation/Keyframe
      // 5ac5: dup
      // 5ac6: ldc 1.96
      // 5ac8: ldc_w -179.256
      // 5acb: ldc_w 36.616
      // 5ace: ldc_w 180.744
      // 5ad1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5ad4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ad7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ada: aastore
      // 5adb: dup
      // 5adc: bipush 40
      // 5ade: new net/minecraft/client/animation/Keyframe
      // 5ae1: dup
      // 5ae2: fconst_2
      // 5ae3: ldc_w -179.474
      // 5ae6: ldc_w 42.607
      // 5ae9: ldc_w 180.526
      // 5aec: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5aef: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5af2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5af5: aastore
      // 5af6: dup
      // 5af7: bipush 41
      // 5af9: new net/minecraft/client/animation/Keyframe
      // 5afc: dup
      // 5afd: ldc 2.04
      // 5aff: ldc_w -179.543
      // 5b02: ldc_w 46.249
      // 5b05: ldc_w 180.457
      // 5b08: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5b0b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b0e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b11: aastore
      // 5b12: dup
      // 5b13: bipush 42
      // 5b15: new net/minecraft/client/animation/Keyframe
      // 5b18: dup
      // 5b19: ldc 2.08
      // 5b1b: ldc_w -179.282
      // 5b1e: ldc_w 45.133
      // 5b21: ldc_w 180.718
      // 5b24: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5b27: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b2a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b2d: aastore
      // 5b2e: dup
      // 5b2f: bipush 43
      // 5b31: new net/minecraft/client/animation/Keyframe
      // 5b34: dup
      // 5b35: ldc 2.12
      // 5b37: ldc_w -178.76
      // 5b3a: ldc_w 40.713
      // 5b3d: ldc_w 181.24
      // 5b40: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5b43: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b46: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b49: aastore
      // 5b4a: dup
      // 5b4b: bipush 44
      // 5b4d: new net/minecraft/client/animation/Keyframe
      // 5b50: dup
      // 5b51: ldc 2.17
      // 5b53: ldc_w -178.079
      // 5b56: ldc_w 34.659
      // 5b59: ldc_w 181.921
      // 5b5c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5b5f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b62: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b65: aastore
      // 5b66: dup
      // 5b67: bipush 45
      // 5b69: new net/minecraft/client/animation/Keyframe
      // 5b6c: dup
      // 5b6d: ldc 2.21
      // 5b6f: ldc_w -177.373
      // 5b72: ldc_w 28.695
      // 5b75: ldc_w 182.626
      // 5b78: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5b7b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b7e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b81: aastore
      // 5b82: dup
      // 5b83: bipush 46
      // 5b85: new net/minecraft/client/animation/Keyframe
      // 5b88: dup
      // 5b89: ldc 2.25
      // 5b8b: ldc_w -176.789
      // 5b8e: ldc_w 24.271
      // 5b91: ldc_w 183.212
      // 5b94: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5b97: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b9a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b9d: aastore
      // 5b9e: dup
      // 5b9f: bipush 47
      // 5ba1: new net/minecraft/client/animation/Keyframe
      // 5ba4: dup
      // 5ba5: ldc 2.29
      // 5ba7: ldc_w -176.458
      // 5baa: ldc_w 22.498
      // 5bad: ldc_w 183.542
      // 5bb0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5bb3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5bb6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5bb9: aastore
      // 5bba: dup
      // 5bbb: bipush 48
      // 5bbd: new net/minecraft/client/animation/Keyframe
      // 5bc0: dup
      // 5bc1: ldc 2.33
      // 5bc3: ldc_w -176.531
      // 5bc6: ldc_w 24.577
      // 5bc9: ldc_w 183.469
      // 5bcc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5bcf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5bd2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5bd5: aastore
      // 5bd6: dup
      // 5bd7: bipush 49
      // 5bd9: new net/minecraft/client/animation/Keyframe
      // 5bdc: dup
      // 5bdd: ldc 2.38
      // 5bdf: ldc_w -176.833
      // 5be2: ldc_w 30.961
      // 5be5: ldc_w 183.168
      // 5be8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5beb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5bee: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5bf1: aastore
      // 5bf2: dup
      // 5bf3: bipush 50
      // 5bf5: new net/minecraft/client/animation/Keyframe
      // 5bf8: dup
      // 5bf9: ldc_w 2.42
      // 5bfc: ldc_w -177.03
      // 5bff: ldc_w 39.366
      // 5c02: ldc_w 182.97
      // 5c05: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5c08: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c0b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c0e: aastore
      // 5c0f: dup
      // 5c10: bipush 51
      // 5c12: new net/minecraft/client/animation/Keyframe
      // 5c15: dup
      // 5c16: ldc_w 2.46
      // 5c19: ldc_w -177.112
      // 5c1c: ldc_w 47.099
      // 5c1f: ldc_w 182.888
      // 5c22: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5c25: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c28: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c2b: aastore
      // 5c2c: dup
      // 5c2d: bipush 52
      // 5c2f: new net/minecraft/client/animation/Keyframe
      // 5c32: dup
      // 5c33: ldc_w 2.5
      // 5c36: ldc_w -177.109
      // 5c39: ldc_w 51.941
      // 5c3c: ldc_w 182.891
      // 5c3f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5c42: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c45: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c48: aastore
      // 5c49: dup
      // 5c4a: bipush 53
      // 5c4c: new net/minecraft/client/animation/Keyframe
      // 5c4f: dup
      // 5c50: ldc 2.54
      // 5c52: ldc_w -177.066
      // 5c55: ldc_w 52.262
      // 5c58: ldc_w 182.934
      // 5c5b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5c5e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c61: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c64: aastore
      // 5c65: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 5c68: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5c6b: ldc_w "CTR_leg_left"
      // 5c6e: new net/minecraft/client/animation/AnimationChannel
      // 5c71: dup
      // 5c72: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5c75: bipush 57
      // 5c77: anewarray 28
      // 5c7a: dup
      // 5c7b: bipush 0
      // 5c7c: new net/minecraft/client/animation/Keyframe
      // 5c7f: dup
      // 5c80: fconst_0
      // 5c81: ldc_w 0.878
      // 5c84: ldc_w -1.018
      // 5c87: ldc_w -0.451
      // 5c8a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5c8d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c90: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c93: aastore
      // 5c94: dup
      // 5c95: bipush 1
      // 5c96: new net/minecraft/client/animation/Keyframe
      // 5c99: dup
      // 5c9a: ldc 0.04
      // 5c9c: ldc_w 0.818
      // 5c9f: ldc_w -0.788
      // 5ca2: ldc_w -0.183
      // 5ca5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5ca8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5cab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5cae: aastore
      // 5caf: dup
      // 5cb0: bipush 2
      // 5cb1: new net/minecraft/client/animation/Keyframe
      // 5cb4: dup
      // 5cb5: ldc 0.08
      // 5cb7: ldc_w 0.622
      // 5cba: ldc_w -0.235
      // 5cbd: ldc_w 0.574
      // 5cc0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5cc3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5cc6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5cc9: aastore
      // 5cca: dup
      // 5ccb: bipush 3
      // 5ccc: new net/minecraft/client/animation/Keyframe
      // 5ccf: dup
      // 5cd0: ldc 0.12
      // 5cd2: ldc_w 0.237
      // 5cd5: ldc_w 0.287
      // 5cd8: ldc_w 1.7
      // 5cdb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5cde: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ce1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ce4: aastore
      // 5ce5: dup
      // 5ce6: bipush 4
      // 5ce7: new net/minecraft/client/animation/Keyframe
      // 5cea: dup
      // 5ceb: ldc 0.17
      // 5ced: ldc_w -0.389
      // 5cf0: ldc_w 0.358
      // 5cf3: ldc_w 2.889
      // 5cf6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5cf9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5cfc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5cff: aastore
      // 5d00: dup
      // 5d01: bipush 5
      // 5d02: new net/minecraft/client/animation/Keyframe
      // 5d05: dup
      // 5d06: ldc 0.21
      // 5d08: ldc_w -1.191
      // 5d0b: ldc_w -0.244
      // 5d0e: ldc_w 3.624
      // 5d11: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5d14: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d17: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d1a: aastore
      // 5d1b: dup
      // 5d1c: bipush 6
      // 5d1e: new net/minecraft/client/animation/Keyframe
      // 5d21: dup
      // 5d22: ldc 0.25
      // 5d24: ldc_w -1.869
      // 5d27: ldc_w -1.386
      // 5d2a: ldc_w 3.438
      // 5d2d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5d30: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d33: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d36: aastore
      // 5d37: dup
      // 5d38: bipush 7
      // 5d3a: new net/minecraft/client/animation/Keyframe
      // 5d3d: dup
      // 5d3e: ldc 0.29
      // 5d40: ldc_w -2.001
      // 5d43: ldc_w -2.778
      // 5d46: ldc_w 2.284
      // 5d49: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5d4c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d4f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d52: aastore
      // 5d53: dup
      // 5d54: bipush 8
      // 5d56: new net/minecraft/client/animation/Keyframe
      // 5d59: dup
      // 5d5a: ldc 0.33
      // 5d5c: ldc_w -2.261
      // 5d5f: ldc_w -1.043
      // 5d62: ldc_w 1.599
      // 5d65: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5d68: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d6b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d6e: aastore
      // 5d6f: dup
      // 5d70: bipush 9
      // 5d72: new net/minecraft/client/animation/Keyframe
      // 5d75: dup
      // 5d76: ldc 0.38
      // 5d78: ldc_w -0.837
      // 5d7b: ldc_w 1.53
      // 5d7e: ldc_w -1.175
      // 5d81: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5d84: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d87: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d8a: aastore
      // 5d8b: dup
      // 5d8c: bipush 10
      // 5d8e: new net/minecraft/client/animation/Keyframe
      // 5d91: dup
      // 5d92: ldc 0.42
      // 5d94: ldc_w 0.313
      // 5d97: ldc_w 0.246
      // 5d9a: ldc_w -2.739
      // 5d9d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5da0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5da3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5da6: aastore
      // 5da7: dup
      // 5da8: bipush 11
      // 5daa: new net/minecraft/client/animation/Keyframe
      // 5dad: dup
      // 5dae: ldc 0.46
      // 5db0: ldc_w 0.244
      // 5db3: ldc_w -1.067
      // 5db6: ldc_w -2.585
      // 5db9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5dbc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5dbf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5dc2: aastore
      // 5dc3: dup
      // 5dc4: bipush 12
      // 5dc6: new net/minecraft/client/animation/Keyframe
      // 5dc9: dup
      // 5dca: ldc 0.5
      // 5dcc: ldc_w 0.378
      // 5dcf: ldc -2.177
      // 5dd1: ldc_w -2.823
      // 5dd4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5dd7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5dda: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ddd: aastore
      // 5dde: dup
      // 5ddf: bipush 13
      // 5de1: new net/minecraft/client/animation/Keyframe
      // 5de4: dup
      // 5de5: ldc_w 0.54
      // 5de8: ldc_w 0.538
      // 5deb: ldc_w -2.992
      // 5dee: ldc_w -3.147
      // 5df1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5df4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5df7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5dfa: aastore
      // 5dfb: dup
      // 5dfc: bipush 14
      // 5dfe: new net/minecraft/client/animation/Keyframe
      // 5e01: dup
      // 5e02: ldc 0.58
      // 5e04: ldc_w 0.631
      // 5e07: ldc_w -3.563
      // 5e0a: ldc_w -3.401
      // 5e0d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5e10: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e13: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e16: aastore
      // 5e17: dup
      // 5e18: bipush 15
      // 5e1a: new net/minecraft/client/animation/Keyframe
      // 5e1d: dup
      // 5e1e: ldc 0.62
      // 5e20: ldc_w 0.663
      // 5e23: ldc_w -3.883
      // 5e26: ldc_w -3.6
      // 5e29: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5e2c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e2f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e32: aastore
      // 5e33: dup
      // 5e34: bipush 16
      // 5e36: new net/minecraft/client/animation/Keyframe
      // 5e39: dup
      // 5e3a: ldc 0.67
      // 5e3c: ldc_w 0.637
      // 5e3f: ldc_w -3.934
      // 5e42: ldc_w -3.784
      // 5e45: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5e48: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e4b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e4e: aastore
      // 5e4f: dup
      // 5e50: bipush 17
      // 5e52: new net/minecraft/client/animation/Keyframe
      // 5e55: dup
      // 5e56: ldc 0.71
      // 5e58: ldc_w 0.568
      // 5e5b: ldc_w -3.688
      // 5e5e: ldc_w -4.022
      // 5e61: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5e64: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e67: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e6a: aastore
      // 5e6b: dup
      // 5e6c: bipush 18
      // 5e6e: new net/minecraft/client/animation/Keyframe
      // 5e71: dup
      // 5e72: ldc 0.75
      // 5e74: ldc_w 0.587
      // 5e77: ldc_w -3.214
      // 5e7a: ldc_w -4.457
      // 5e7d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5e80: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e83: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e86: aastore
      // 5e87: dup
      // 5e88: bipush 19
      // 5e8a: new net/minecraft/client/animation/Keyframe
      // 5e8d: dup
      // 5e8e: ldc_w 0.79
      // 5e91: ldc_w 0.693
      // 5e94: ldc_w -3.106
      // 5e97: ldc_w -4.692
      // 5e9a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5e9d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ea0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ea3: aastore
      // 5ea4: dup
      // 5ea5: bipush 20
      // 5ea7: new net/minecraft/client/animation/Keyframe
      // 5eaa: dup
      // 5eab: ldc 0.83
      // 5ead: ldc_w 0.808
      // 5eb0: ldc_w -3.118
      // 5eb3: ldc_w -4.848
      // 5eb6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5eb9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ebc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ebf: aastore
      // 5ec0: dup
      // 5ec1: bipush 21
      // 5ec3: new net/minecraft/client/animation/Keyframe
      // 5ec6: dup
      // 5ec7: ldc 0.88
      // 5ec9: ldc_w 1.029
      // 5ecc: ldc_w -3.327
      // 5ecf: ldc_w -5.019
      // 5ed2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5ed5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ed8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5edb: aastore
      // 5edc: dup
      // 5edd: bipush 22
      // 5edf: new net/minecraft/client/animation/Keyframe
      // 5ee2: dup
      // 5ee3: ldc 0.92
      // 5ee5: ldc_w 1.486
      // 5ee8: ldc_w -6.249
      // 5eeb: ldc_w -4.077
      // 5eee: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5ef1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ef4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ef7: aastore
      // 5ef8: dup
      // 5ef9: bipush 23
      // 5efb: new net/minecraft/client/animation/Keyframe
      // 5efe: dup
      // 5eff: ldc 0.96
      // 5f01: ldc_w 0.802
      // 5f04: ldc_w -1.583
      // 5f07: ldc_w -4.595
      // 5f0a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5f0d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f10: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f13: aastore
      // 5f14: dup
      // 5f15: bipush 24
      // 5f17: new net/minecraft/client/animation/Keyframe
      // 5f1a: dup
      // 5f1b: fconst_1
      // 5f1c: ldc_w -0.006
      // 5f1f: ldc_w 0.505
      // 5f22: ldc_w -2.153
      // 5f25: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5f28: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f2b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f2e: aastore
      // 5f2f: dup
      // 5f30: bipush 25
      // 5f32: new net/minecraft/client/animation/Keyframe
      // 5f35: dup
      // 5f36: ldc 1.04
      // 5f38: ldc_w 0.675
      // 5f3b: ldc_w -2.167
      // 5f3e: ldc_w -4.096
      // 5f41: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5f44: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f47: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f4a: aastore
      // 5f4b: dup
      // 5f4c: bipush 26
      // 5f4e: new net/minecraft/client/animation/Keyframe
      // 5f51: dup
      // 5f52: ldc 1.08
      // 5f54: ldc_w 0.701
      // 5f57: ldc_w -2.789
      // 5f5a: ldc_w -4.022
      // 5f5d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5f60: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f63: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f66: aastore
      // 5f67: dup
      // 5f68: bipush 27
      // 5f6a: new net/minecraft/client/animation/Keyframe
      // 5f6d: dup
      // 5f6e: ldc 1.12
      // 5f70: ldc_w 0.729
      // 5f73: ldc_w -3.028
      // 5f76: ldc_w -3.911
      // 5f79: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5f7c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f7f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f82: aastore
      // 5f83: dup
      // 5f84: bipush 28
      // 5f86: new net/minecraft/client/animation/Keyframe
      // 5f89: dup
      // 5f8a: ldc 1.17
      // 5f8c: ldc_w 0.754
      // 5f8f: ldc_w -3.128
      // 5f92: ldc_w -3.817
      // 5f95: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5f98: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f9b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f9e: aastore
      // 5f9f: dup
      // 5fa0: bipush 29
      // 5fa2: new net/minecraft/client/animation/Keyframe
      // 5fa5: dup
      // 5fa6: ldc 1.21
      // 5fa8: ldc_w 0.788
      // 5fab: ldc_w -3.254
      // 5fae: ldc_w -3.818
      // 5fb1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5fb4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5fb7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5fba: aastore
      // 5fbb: dup
      // 5fbc: bipush 30
      // 5fbe: new net/minecraft/client/animation/Keyframe
      // 5fc1: dup
      // 5fc2: ldc_w 1.25
      // 5fc5: ldc_w 0.805
      // 5fc8: ldc_w -3.283
      // 5fcb: ldc_w -3.822
      // 5fce: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5fd1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5fd4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5fd7: aastore
      // 5fd8: dup
      // 5fd9: bipush 31
      // 5fdb: new net/minecraft/client/animation/Keyframe
      // 5fde: dup
      // 5fdf: ldc 1.29
      // 5fe1: ldc_w 0.807
      // 5fe4: ldc_w -3.222
      // 5fe7: ldc_w -3.822
      // 5fea: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5fed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ff0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ff3: aastore
      // 5ff4: dup
      // 5ff5: bipush 32
      // 5ff7: new net/minecraft/client/animation/Keyframe
      // 5ffa: dup
      // 5ffb: ldc_w 1.33
      // 5ffe: ldc_w 0.797
      // 6001: ldc_w -3.086
      // 6004: ldc_w -3.816
      // 6007: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 600a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 600d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6010: aastore
      // 6011: dup
      // 6012: bipush 33
      // 6014: new net/minecraft/client/animation/Keyframe
      // 6017: dup
      // 6018: ldc 1.38
      // 601a: ldc_w 0.774
      // 601d: ldc_w -2.886
      // 6020: ldc_w -3.805
      // 6023: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6026: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6029: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 602c: aastore
      // 602d: dup
      // 602e: bipush 34
      // 6030: new net/minecraft/client/animation/Keyframe
      // 6033: dup
      // 6034: ldc 1.42
      // 6036: ldc_w 0.742
      // 6039: ldc_w -2.647
      // 603c: ldc_w -3.793
      // 603f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6042: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6045: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6048: aastore
      // 6049: dup
      // 604a: bipush 35
      // 604c: new net/minecraft/client/animation/Keyframe
      // 604f: dup
      // 6050: ldc 1.46
      // 6052: ldc_w 0.738
      // 6055: ldc_w -2.609
      // 6058: ldc_w -3.821
      // 605b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 605e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6061: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6064: aastore
      // 6065: dup
      // 6066: bipush 36
      // 6068: new net/minecraft/client/animation/Keyframe
      // 606b: dup
      // 606c: ldc 1.67
      // 606e: ldc_w 0.746
      // 6071: ldc_w -2.669
      // 6074: ldc_w -3.834
      // 6077: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 607a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 607d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6080: aastore
      // 6081: dup
      // 6082: bipush 37
      // 6084: new net/minecraft/client/animation/Keyframe
      // 6087: dup
      // 6088: ldc 1.75
      // 608a: ldc_w 0.752
      // 608d: ldc_w -2.706
      // 6090: ldc_w -3.83
      // 6093: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6096: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6099: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 609c: aastore
      // 609d: dup
      // 609e: bipush 38
      // 60a0: new net/minecraft/client/animation/Keyframe
      // 60a3: dup
      // 60a4: ldc 1.79
      // 60a6: ldc_w 0.725
      // 60a9: ldc_w -2.529
      // 60ac: ldc -3.783
      // 60ae: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 60b1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 60b4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 60b7: aastore
      // 60b8: dup
      // 60b9: bipush 39
      // 60bb: new net/minecraft/client/animation/Keyframe
      // 60be: dup
      // 60bf: ldc_w 1.83
      // 60c2: ldc_w 0.651
      // 60c5: ldc_w -2.058
      // 60c8: ldc_w -3.636
      // 60cb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 60ce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 60d1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 60d4: aastore
      // 60d5: dup
      // 60d6: bipush 40
      // 60d8: new net/minecraft/client/animation/Keyframe
      // 60db: dup
      // 60dc: ldc 1.88
      // 60de: ldc_w 0.54
      // 60e1: ldc_w -1.387
      // 60e4: ldc_w -3.347
      // 60e7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 60ea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 60ed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 60f0: aastore
      // 60f1: dup
      // 60f2: bipush 41
      // 60f4: new net/minecraft/client/animation/Keyframe
      // 60f7: dup
      // 60f8: ldc_w 1.92
      // 60fb: ldc_w 0.401
      // 60fe: ldc_w -0.631
      // 6101: ldc_w -2.859
      // 6104: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6107: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 610a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 610d: aastore
      // 610e: dup
      // 610f: bipush 42
      // 6111: new net/minecraft/client/animation/Keyframe
      // 6114: dup
      // 6115: ldc 1.96
      // 6117: ldc_w 0.247
      // 611a: ldc_w 0.064
      // 611d: ldc_w -2.132
      // 6120: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6123: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6126: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6129: aastore
      // 612a: dup
      // 612b: bipush 43
      // 612d: new net/minecraft/client/animation/Keyframe
      // 6130: dup
      // 6131: fconst_2
      // 6132: ldc_w 0.094
      // 6135: ldc_w 0.534
      // 6138: ldc_w -1.174
      // 613b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 613e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6141: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6144: aastore
      // 6145: dup
      // 6146: bipush 44
      // 6148: new net/minecraft/client/animation/Keyframe
      // 614b: dup
      // 614c: ldc 2.04
      // 614e: ldc_w 0.007
      // 6151: ldc_w 0.727
      // 6154: ldc_w -0.846
      // 6157: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 615a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 615d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6160: aastore
      // 6161: dup
      // 6162: bipush 45
      // 6164: new net/minecraft/client/animation/Keyframe
      // 6167: dup
      // 6168: ldc 2.08
      // 616a: ldc_w 0.007
      // 616d: ldc_w 0.946
      // 6170: ldc_w -1.738
      // 6173: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6176: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6179: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 617c: aastore
      // 617d: dup
      // 617e: bipush 46
      // 6180: new net/minecraft/client/animation/Keyframe
      // 6183: dup
      // 6184: ldc 2.12
      // 6186: ldc_w 0.096
      // 6189: ldc_w 1.108
      // 618c: ldc_w -3.536
      // 618f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6192: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6195: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6198: aastore
      // 6199: dup
      // 619a: bipush 47
      // 619c: new net/minecraft/client/animation/Keyframe
      // 619f: dup
      // 61a0: ldc 2.17
      // 61a2: ldc_w 0.273
      // 61a5: ldc_w 0.94
      // 61a8: ldc_w -5.838
      // 61ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 61ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 61b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 61b4: aastore
      // 61b5: dup
      // 61b6: bipush 48
      // 61b8: new net/minecraft/client/animation/Keyframe
      // 61bb: dup
      // 61bc: ldc 2.21
      // 61be: ldc_w 0.482
      // 61c1: ldc_w 0.385
      // 61c4: ldc_w -8.11
      // 61c7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 61ca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 61cd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 61d0: aastore
      // 61d1: dup
      // 61d2: bipush 49
      // 61d4: new net/minecraft/client/animation/Keyframe
      // 61d7: dup
      // 61d8: ldc 2.25
      // 61da: ldc_w 0.645
      // 61dd: ldc_w -0.242
      // 61e0: ldc_w -9.906
      // 61e3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 61e6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 61e9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 61ec: aastore
      // 61ed: dup
      // 61ee: bipush 50
      // 61f0: new net/minecraft/client/animation/Keyframe
      // 61f3: dup
      // 61f4: ldc 2.29
      // 61f6: ldc_w 0.712
      // 61f9: ldc_w -0.428
      // 61fc: ldc_w -10.97
      // 61ff: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6202: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6205: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6208: aastore
      // 6209: dup
      // 620a: bipush 51
      // 620c: new net/minecraft/client/animation/Keyframe
      // 620f: dup
      // 6210: ldc 2.33
      // 6212: ldc_w 0.636
      // 6215: ldc_w 0.255
      // 6218: ldc_w -11.02
      // 621b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 621e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6221: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6224: aastore
      // 6225: dup
      // 6226: bipush 52
      // 6228: new net/minecraft/client/animation/Keyframe
      // 622b: dup
      // 622c: ldc 2.38
      // 622e: ldc_w 0.514
      // 6231: ldc_w 1.146
      // 6234: ldc_w -9.249
      // 6237: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 623a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 623d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6240: aastore
      // 6241: dup
      // 6242: bipush 53
      // 6244: new net/minecraft/client/animation/Keyframe
      // 6247: dup
      // 6248: ldc_w 2.42
      // 624b: ldc_w 0.519
      // 624e: ldc_w 1.178
      // 6251: ldc_w -6.094
      // 6254: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6257: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 625a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 625d: aastore
      // 625e: dup
      // 625f: bipush 54
      // 6261: new net/minecraft/client/animation/Keyframe
      // 6264: dup
      // 6265: ldc_w 2.46
      // 6268: ldc_w 0.641
      // 626b: ldc_w 0.401
      // 626e: ldc_w -3.006
      // 6271: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6274: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6277: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 627a: aastore
      // 627b: dup
      // 627c: bipush 55
      // 627e: new net/minecraft/client/animation/Keyframe
      // 6281: dup
      // 6282: ldc_w 2.5
      // 6285: ldc_w 0.794
      // 6288: ldc_w -0.547
      // 628b: ldc_w -0.974
      // 628e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6291: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6294: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6297: aastore
      // 6298: dup
      // 6299: bipush 56
      // 629b: new net/minecraft/client/animation/Keyframe
      // 629e: dup
      // 629f: ldc 2.54
      // 62a1: ldc_w 0.878
      // 62a4: ldc_w -1.018
      // 62a7: ldc_w -0.451
      // 62aa: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 62ad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 62b0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 62b3: aastore
      // 62b4: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 62b7: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 62ba: ldc_w "CTR_leg_right"
      // 62bd: new net/minecraft/client/animation/AnimationChannel
      // 62c0: dup
      // 62c1: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 62c4: bipush 56
      // 62c6: anewarray 28
      // 62c9: dup
      // 62ca: bipush 0
      // 62cb: new net/minecraft/client/animation/Keyframe
      // 62ce: dup
      // 62cf: fconst_0
      // 62d0: ldc_w -2.789
      // 62d3: ldc_w 0.901
      // 62d6: ldc_w 2.789
      // 62d9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 62dc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 62df: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 62e2: aastore
      // 62e3: dup
      // 62e4: bipush 1
      // 62e5: new net/minecraft/client/animation/Keyframe
      // 62e8: dup
      // 62e9: ldc 0.04
      // 62eb: ldc_w -2.847
      // 62ee: ldc_w -0.566
      // 62f1: ldc_w 2.847
      // 62f4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 62f7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 62fa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 62fd: aastore
      // 62fe: dup
      // 62ff: bipush 2
      // 6300: new net/minecraft/client/animation/Keyframe
      // 6303: dup
      // 6304: ldc 0.08
      // 6306: ldc_w -2.977
      // 6309: ldc_w -4.939
      // 630c: ldc_w 2.977
      // 630f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6312: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6315: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6318: aastore
      // 6319: dup
      // 631a: bipush 3
      // 631b: new net/minecraft/client/animation/Keyframe
      // 631e: dup
      // 631f: ldc 0.12
      // 6321: ldc_w -2.963
      // 6324: ldc_w -12.573
      // 6327: ldc_w 2.963
      // 632a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 632d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6330: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6333: aastore
      // 6334: dup
      // 6335: bipush 4
      // 6336: new net/minecraft/client/animation/Keyframe
      // 6339: dup
      // 633a: ldc 0.17
      // 633c: ldc_w -4.555
      // 633f: ldc_w -13.582
      // 6342: ldc_w 4.555
      // 6345: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6348: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 634b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 634e: aastore
      // 634f: dup
      // 6350: bipush 5
      // 6351: new net/minecraft/client/animation/Keyframe
      // 6354: dup
      // 6355: ldc 0.21
      // 6357: ldc_w -8.91
      // 635a: ldc_w -4.436
      // 635d: ldc_w 8.91
      // 6360: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6363: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6366: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6369: aastore
      // 636a: dup
      // 636b: bipush 6
      // 636d: new net/minecraft/client/animation/Keyframe
      // 6370: dup
      // 6371: ldc 0.25
      // 6373: ldc_w -13.456
      // 6376: ldc_w 2.542
      // 6379: ldc_w 13.456
      // 637c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 637f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6382: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6385: aastore
      // 6386: dup
      // 6387: bipush 7
      // 6389: new net/minecraft/client/animation/Keyframe
      // 638c: dup
      // 638d: ldc 0.29
      // 638f: ldc_w -15.539
      // 6392: ldc_w -3.347
      // 6395: ldc_w 15.54
      // 6398: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 639b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 639e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 63a1: aastore
      // 63a2: dup
      // 63a3: bipush 8
      // 63a5: new net/minecraft/client/animation/Keyframe
      // 63a8: dup
      // 63a9: ldc 0.33
      // 63ab: ldc_w -13.845
      // 63ae: ldc_w -18.42
      // 63b1: ldc_w 13.846
      // 63b4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 63b7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 63ba: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 63bd: aastore
      // 63be: dup
      // 63bf: bipush 9
      // 63c1: new net/minecraft/client/animation/Keyframe
      // 63c4: dup
      // 63c5: ldc 0.38
      // 63c7: ldc_w -10.338
      // 63ca: ldc_w -29.121
      // 63cd: ldc_w 10.338
      // 63d0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 63d3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 63d6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 63d9: aastore
      // 63da: dup
      // 63db: bipush 10
      // 63dd: new net/minecraft/client/animation/Keyframe
      // 63e0: dup
      // 63e1: ldc 0.42
      // 63e3: ldc_w -6.777
      // 63e6: ldc_w -33.644
      // 63e9: ldc_w 6.778
      // 63ec: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 63ef: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 63f2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 63f5: aastore
      // 63f6: dup
      // 63f7: bipush 11
      // 63f9: new net/minecraft/client/animation/Keyframe
      // 63fc: dup
      // 63fd: ldc 0.46
      // 63ff: ldc_w -7.361
      // 6402: ldc_w -19.499
      // 6405: ldc_w 7.361
      // 6408: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 640b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 640e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6411: aastore
      // 6412: dup
      // 6413: bipush 12
      // 6415: new net/minecraft/client/animation/Keyframe
      // 6418: dup
      // 6419: ldc 0.5
      // 641b: ldc_w -10.304
      // 641e: ldc_w 11.375
      // 6421: ldc_w 10.305
      // 6424: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6427: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 642a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 642d: aastore
      // 642e: dup
      // 642f: bipush 13
      // 6431: new net/minecraft/client/animation/Keyframe
      // 6434: dup
      // 6435: ldc_w 0.54
      // 6438: ldc_w -11.229
      // 643b: ldc_w 27.973
      // 643e: ldc_w 11.229
      // 6441: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6444: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6447: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 644a: aastore
      // 644b: dup
      // 644c: bipush 14
      // 644e: new net/minecraft/client/animation/Keyframe
      // 6451: dup
      // 6452: ldc 0.58
      // 6454: ldc_w -10.84
      // 6457: ldc_w 30.932
      // 645a: ldc_w 10.84
      // 645d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6460: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6463: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6466: aastore
      // 6467: dup
      // 6468: bipush 15
      // 646a: new net/minecraft/client/animation/Keyframe
      // 646d: dup
      // 646e: ldc 0.62
      // 6470: ldc_w -10.522
      // 6473: ldc_w 34.154
      // 6476: ldc_w 10.522
      // 6479: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 647c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 647f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6482: aastore
      // 6483: dup
      // 6484: bipush 16
      // 6486: new net/minecraft/client/animation/Keyframe
      // 6489: dup
      // 648a: ldc 0.67
      // 648c: ldc_w -10.345
      // 648f: ldc_w 37.521
      // 6492: ldc_w 10.345
      // 6495: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6498: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 649b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 649e: aastore
      // 649f: dup
      // 64a0: bipush 17
      // 64a2: new net/minecraft/client/animation/Keyframe
      // 64a5: dup
      // 64a6: ldc 0.71
      // 64a8: ldc_w -10.532
      // 64ab: ldc_w 41.215
      // 64ae: ldc_w 10.532
      // 64b1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 64b4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 64b7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 64ba: aastore
      // 64bb: dup
      // 64bc: bipush 18
      // 64be: new net/minecraft/client/animation/Keyframe
      // 64c1: dup
      // 64c2: ldc 0.75
      // 64c4: ldc_w -11.64
      // 64c7: ldc_w 46.1
      // 64ca: ldc_w 11.64
      // 64cd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 64d0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 64d3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 64d6: aastore
      // 64d7: dup
      // 64d8: bipush 19
      // 64da: new net/minecraft/client/animation/Keyframe
      // 64dd: dup
      // 64de: ldc_w 0.79
      // 64e1: ldc_w -9.651
      // 64e4: ldc_w 36.814
      // 64e7: ldc_w 9.651
      // 64ea: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 64ed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 64f0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 64f3: aastore
      // 64f4: dup
      // 64f5: bipush 20
      // 64f7: new net/minecraft/client/animation/Keyframe
      // 64fa: dup
      // 64fb: ldc 0.83
      // 64fd: ldc_w -1.08
      // 6500: ldc_w 4.081
      // 6503: ldc 1.08
      // 6505: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6508: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 650b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 650e: aastore
      // 650f: dup
      // 6510: bipush 21
      // 6512: new net/minecraft/client/animation/Keyframe
      // 6515: dup
      // 6516: ldc 0.88
      // 6518: ldc_w 8.607
      // 651b: ldc_w -23.51
      // 651e: ldc_w -8.607
      // 6521: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6524: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6527: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 652a: aastore
      // 652b: dup
      // 652c: bipush 22
      // 652e: new net/minecraft/client/animation/Keyframe
      // 6531: dup
      // 6532: ldc 0.92
      // 6534: ldc_w 35.759
      // 6537: ldc_w -20.601
      // 653a: ldc_w 35.591
      // 653d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6540: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6543: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6546: aastore
      // 6547: dup
      // 6548: bipush 23
      // 654a: new net/minecraft/client/animation/Keyframe
      // 654d: dup
      // 654e: ldc 0.96
      // 6550: ldc_w -6.087
      // 6553: ldc_w 9.833
      // 6556: ldc_w 6.087
      // 6559: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 655c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 655f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6562: aastore
      // 6563: dup
      // 6564: bipush 24
      // 6566: new net/minecraft/client/animation/Keyframe
      // 6569: dup
      // 656a: fconst_1
      // 656b: ldc_w -7.912
      // 656e: ldc_w 35.933
      // 6571: ldc_w 7.912
      // 6574: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6577: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 657a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 657d: aastore
      // 657e: dup
      // 657f: bipush 25
      // 6581: new net/minecraft/client/animation/Keyframe
      // 6584: dup
      // 6585: ldc 1.04
      // 6587: ldc_w -0.506
      // 658a: ldc_w 27.919
      // 658d: ldc_w 0.506
      // 6590: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6593: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6596: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6599: aastore
      // 659a: dup
      // 659b: bipush 26
      // 659d: new net/minecraft/client/animation/Keyframe
      // 65a0: dup
      // 65a1: ldc 1.08
      // 65a3: ldc_w -3.06
      // 65a6: ldc_w -2.653
      // 65a9: ldc_w 3.06
      // 65ac: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 65af: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 65b2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 65b5: aastore
      // 65b6: dup
      // 65b7: bipush 27
      // 65b9: new net/minecraft/client/animation/Keyframe
      // 65bc: dup
      // 65bd: ldc 1.12
      // 65bf: ldc_w -3.327
      // 65c2: ldc_w -3.109
      // 65c5: ldc_w 3.327
      // 65c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 65cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 65ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 65d1: aastore
      // 65d2: dup
      // 65d3: bipush 28
      // 65d5: new net/minecraft/client/animation/Keyframe
      // 65d8: dup
      // 65d9: ldc 1.17
      // 65db: ldc_w -3.72
      // 65de: ldc_w -5.198
      // 65e1: ldc_w 3.72
      // 65e4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 65e7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 65ea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 65ed: aastore
      // 65ee: dup
      // 65ef: bipush 29
      // 65f1: new net/minecraft/client/animation/Keyframe
      // 65f4: dup
      // 65f5: ldc 1.21
      // 65f7: ldc_w -3.797
      // 65fa: ldc_w -4.857
      // 65fd: ldc_w 3.797
      // 6600: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6603: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6606: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6609: aastore
      // 660a: dup
      // 660b: bipush 30
      // 660d: new net/minecraft/client/animation/Keyframe
      // 6610: dup
      // 6611: ldc_w 1.25
      // 6614: ldc_w -3.863
      // 6617: ldc_w -5.247
      // 661a: ldc_w 3.863
      // 661d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6620: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6623: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6626: aastore
      // 6627: dup
      // 6628: bipush 31
      // 662a: new net/minecraft/client/animation/Keyframe
      // 662d: dup
      // 662e: ldc 1.29
      // 6630: ldc_w -3.953
      // 6633: ldc_w -6.424
      // 6636: ldc_w 3.953
      // 6639: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 663c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 663f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6642: aastore
      // 6643: dup
      // 6644: bipush 32
      // 6646: new net/minecraft/client/animation/Keyframe
      // 6649: dup
      // 664a: ldc 1.38
      // 664c: ldc_w -4.25
      // 664f: ldc_w -10.273
      // 6652: ldc_w 4.25
      // 6655: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6658: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 665b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 665e: aastore
      // 665f: dup
      // 6660: bipush 33
      // 6662: new net/minecraft/client/animation/Keyframe
      // 6665: dup
      // 6666: ldc 1.42
      // 6668: ldc_w -4.478
      // 666b: ldc_w -12.18
      // 666e: ldc_w 4.478
      // 6671: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6674: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6677: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 667a: aastore
      // 667b: dup
      // 667c: bipush 34
      // 667e: new net/minecraft/client/animation/Keyframe
      // 6681: dup
      // 6682: ldc 1.46
      // 6684: ldc_w -4.455
      // 6687: ldc_w -11.552
      // 668a: ldc_w 4.455
      // 668d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6690: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6693: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6696: aastore
      // 6697: dup
      // 6698: bipush 35
      // 669a: new net/minecraft/client/animation/Keyframe
      // 669d: dup
      // 669e: ldc 1.5
      // 66a0: ldc_w -4.422
      // 66a3: ldc_w -11.15
      // 66a6: ldc_w 4.422
      // 66a9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 66ac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 66af: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 66b2: aastore
      // 66b3: dup
      // 66b4: bipush 36
      // 66b6: new net/minecraft/client/animation/Keyframe
      // 66b9: dup
      // 66ba: ldc 1.75
      // 66bc: ldc_w -4.333
      // 66bf: ldc_w -10.75
      // 66c2: ldc_w 4.333
      // 66c5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 66c8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 66cb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 66ce: aastore
      // 66cf: dup
      // 66d0: bipush 37
      // 66d2: new net/minecraft/client/animation/Keyframe
      // 66d5: dup
      // 66d6: ldc 1.79
      // 66d8: ldc_w -3.646
      // 66db: ldc_w -16.831
      // 66de: ldc_w 3.646
      // 66e1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 66e4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 66e7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 66ea: aastore
      // 66eb: dup
      // 66ec: bipush 38
      // 66ee: new net/minecraft/client/animation/Keyframe
      // 66f1: dup
      // 66f2: ldc_w 1.83
      // 66f5: ldc_w -1.791
      // 66f8: ldc_w -30.242
      // 66fb: ldc_w 1.791
      // 66fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6701: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6704: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6707: aastore
      // 6708: dup
      // 6709: bipush 39
      // 670b: new net/minecraft/client/animation/Keyframe
      // 670e: dup
      // 670f: ldc 1.88
      // 6711: ldc_w 0.942
      // 6714: ldc_w -23.964
      // 6717: ldc_w -0.942
      // 671a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 671d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6720: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6723: aastore
      // 6724: dup
      // 6725: bipush 40
      // 6727: new net/minecraft/client/animation/Keyframe
      // 672a: dup
      // 672b: ldc_w 1.92
      // 672e: ldc_w 3.106
      // 6731: ldc_w 3.026
      // 6734: ldc_w -3.106
      // 6737: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 673a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 673d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6740: aastore
      // 6741: dup
      // 6742: bipush 41
      // 6744: new net/minecraft/client/animation/Keyframe
      // 6747: dup
      // 6748: ldc 1.96
      // 674a: ldc_w 3.916
      // 674d: ldc_w 21.138
      // 6750: ldc_w -3.916
      // 6753: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6756: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6759: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 675c: aastore
      // 675d: dup
      // 675e: bipush 42
      // 6760: new net/minecraft/client/animation/Keyframe
      // 6763: dup
      // 6764: fconst_2
      // 6765: ldc_w 4.141
      // 6768: ldc_w 22.771
      // 676b: ldc_w -4.141
      // 676e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6771: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6774: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6777: aastore
      // 6778: dup
      // 6779: bipush 43
      // 677b: new net/minecraft/client/animation/Keyframe
      // 677e: dup
      // 677f: ldc 2.04
      // 6781: ldc_w 4.36
      // 6784: ldc_w 15.679
      // 6787: ldc_w -4.36
      // 678a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 678d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6790: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6793: aastore
      // 6794: dup
      // 6795: bipush 44
      // 6797: new net/minecraft/client/animation/Keyframe
      // 679a: dup
      // 679b: ldc 2.08
      // 679d: ldc_w 4.663
      // 67a0: ldc_w 9.904
      // 67a3: ldc_w -4.663
      // 67a6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 67a9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 67ac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 67af: aastore
      // 67b0: dup
      // 67b1: bipush 45
      // 67b3: new net/minecraft/client/animation/Keyframe
      // 67b6: dup
      // 67b7: ldc 2.12
      // 67b9: ldc_w 5.04
      // 67bc: ldc_w 5.135
      // 67bf: ldc_w -5.04
      // 67c2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 67c5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 67c8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 67cb: aastore
      // 67cc: dup
      // 67cd: bipush 46
      // 67cf: new net/minecraft/client/animation/Keyframe
      // 67d2: dup
      // 67d3: ldc 2.17
      // 67d5: ldc_w 5.46
      // 67d8: ldc_w 0.833
      // 67db: ldc_w -5.46
      // 67de: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 67e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 67e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 67e7: aastore
      // 67e8: dup
      // 67e9: bipush 47
      // 67eb: new net/minecraft/client/animation/Keyframe
      // 67ee: dup
      // 67ef: ldc 2.21
      // 67f1: ldc_w 5.852
      // 67f4: ldc_w -3.726
      // 67f7: ldc_w -5.852
      // 67fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 67fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6800: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6803: aastore
      // 6804: dup
      // 6805: bipush 48
      // 6807: new net/minecraft/client/animation/Keyframe
      // 680a: dup
      // 680b: ldc 2.25
      // 680d: ldc_w 6.091
      // 6810: ldc_w -9.375
      // 6813: ldc_w -6.091
      // 6816: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6819: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 681c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 681f: aastore
      // 6820: dup
      // 6821: bipush 49
      // 6823: new net/minecraft/client/animation/Keyframe
      // 6826: dup
      // 6827: ldc 2.29
      // 6829: ldc_w 5.99
      // 682c: ldc_w -16.811
      // 682f: ldc_w -5.99
      // 6832: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6835: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6838: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 683b: aastore
      // 683c: dup
      // 683d: bipush 50
      // 683f: new net/minecraft/client/animation/Keyframe
      // 6842: dup
      // 6843: ldc 2.33
      // 6845: ldc_w 5.332
      // 6848: ldc_w -26.049
      // 684b: ldc_w -5.332
      // 684e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6851: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6854: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6857: aastore
      // 6858: dup
      // 6859: bipush 51
      // 685b: new net/minecraft/client/animation/Keyframe
      // 685e: dup
      // 685f: ldc 2.38
      // 6861: ldc_w 3.152
      // 6864: ldc_w -27.335
      // 6867: ldc_w -3.152
      // 686a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 686d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6870: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6873: aastore
      // 6874: dup
      // 6875: bipush 52
      // 6877: new net/minecraft/client/animation/Keyframe
      // 687a: dup
      // 687b: ldc_w 2.42
      // 687e: ldc_w 0.416
      // 6881: ldc_w -18.998
      // 6884: ldc_w -0.416
      // 6887: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 688a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 688d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6890: aastore
      // 6891: dup
      // 6892: bipush 53
      // 6894: new net/minecraft/client/animation/Keyframe
      // 6897: dup
      // 6898: ldc_w 2.46
      // 689b: ldc_w -1.482
      // 689e: ldc_w -9.765
      // 68a1: ldc_w 1.482
      // 68a4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 68a7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 68aa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 68ad: aastore
      // 68ae: dup
      // 68af: bipush 54
      // 68b1: new net/minecraft/client/animation/Keyframe
      // 68b4: dup
      // 68b5: ldc_w 2.5
      // 68b8: ldc_w -2.494
      // 68bb: ldc_w -3.011
      // 68be: ldc_w 2.494
      // 68c1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 68c4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 68c7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 68ca: aastore
      // 68cb: dup
      // 68cc: bipush 55
      // 68ce: new net/minecraft/client/animation/Keyframe
      // 68d1: dup
      // 68d2: ldc 2.54
      // 68d4: ldc_w -2.789
      // 68d7: ldc_w 0.901
      // 68da: ldc_w 2.789
      // 68dd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 68e0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 68e3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 68e6: aastore
      // 68e7: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 68ea: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 68ed: ldc_w "CTR_leg_right"
      // 68f0: new net/minecraft/client/animation/AnimationChannel
      // 68f3: dup
      // 68f4: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 68f7: bipush 58
      // 68f9: anewarray 28
      // 68fc: dup
      // 68fd: bipush 0
      // 68fe: new net/minecraft/client/animation/Keyframe
      // 6901: dup
      // 6902: fconst_0
      // 6903: ldc_w 0.698
      // 6906: ldc_w 0.987
      // 6909: ldc_w 0.254
      // 690c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 690f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6912: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6915: aastore
      // 6916: dup
      // 6917: bipush 1
      // 6918: new net/minecraft/client/animation/Keyframe
      // 691b: dup
      // 691c: ldc 0.04
      // 691e: ldc_w 0.786
      // 6921: ldc_w 1.373
      // 6924: ldc_w 0.572
      // 6927: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 692a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 692d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6930: aastore
      // 6931: dup
      // 6932: bipush 2
      // 6933: new net/minecraft/client/animation/Keyframe
      // 6936: dup
      // 6937: ldc 0.08
      // 6939: ldc_w 1.012
      // 693c: ldc_w 2.299
      // 693f: ldc_w 1.578
      // 6942: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6945: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6948: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 694b: aastore
      // 694c: dup
      // 694d: bipush 3
      // 694e: new net/minecraft/client/animation/Keyframe
      // 6951: dup
      // 6952: ldc 0.12
      // 6954: ldc_w 1.223
      // 6957: ldc_w 3.113
      // 695a: ldc_w 3.379
      // 695d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6960: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6963: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6966: aastore
      // 6967: dup
      // 6968: bipush 4
      // 6969: new net/minecraft/client/animation/Keyframe
      // 696c: dup
      // 696d: ldc 0.17
      // 696f: ldc_w 1.983
      // 6972: ldc_w 3.967
      // 6975: ldc_w 4.199
      // 6978: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 697b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 697e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6981: aastore
      // 6982: dup
      // 6983: bipush 5
      // 6984: new net/minecraft/client/animation/Keyframe
      // 6987: dup
      // 6988: ldc 0.21
      // 698a: ldc_w 3.759
      // 698d: ldc_w 4.608
      // 6990: ldc_w 2.73
      // 6993: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6996: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6999: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 699c: aastore
      // 699d: dup
      // 699e: bipush 6
      // 69a0: new net/minecraft/client/animation/Keyframe
      // 69a3: dup
      // 69a4: ldc 0.25
      // 69a6: ldc_w 5.081
      // 69a9: ldc 3.675
      // 69ab: ldc_w 1.177
      // 69ae: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 69b1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 69b4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 69b7: aastore
      // 69b8: dup
      // 69b9: bipush 7
      // 69bb: new net/minecraft/client/animation/Keyframe
      // 69be: dup
      // 69bf: ldc 0.29
      // 69c1: ldc_w 5.79
      // 69c4: ldc_w 3.148
      // 69c7: ldc_w 2.255
      // 69ca: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 69cd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 69d0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 69d3: aastore
      // 69d4: dup
      // 69d5: bipush 8
      // 69d7: new net/minecraft/client/animation/Keyframe
      // 69da: dup
      // 69db: ldc 0.33
      // 69dd: ldc_w 5.101
      // 69e0: ldc_w 2.462
      // 69e3: ldc_w 4.994
      // 69e6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 69e9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 69ec: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 69ef: aastore
      // 69f0: dup
      // 69f1: bipush 9
      // 69f3: new net/minecraft/client/animation/Keyframe
      // 69f6: dup
      // 69f7: ldc 0.38
      // 69f9: ldc_w 3.309
      // 69fc: ldc_w 1.054
      // 69ff: ldc_w 5.889
      // 6a02: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6a05: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a08: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a0b: aastore
      // 6a0c: dup
      // 6a0d: bipush 10
      // 6a0f: new net/minecraft/client/animation/Keyframe
      // 6a12: dup
      // 6a13: ldc 0.42
      // 6a15: ldc_w 1.728
      // 6a18: ldc_w -0.251
      // 6a1b: ldc_w 5.01
      // 6a1e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6a21: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a24: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a27: aastore
      // 6a28: dup
      // 6a29: bipush 11
      // 6a2b: new net/minecraft/client/animation/Keyframe
      // 6a2e: dup
      // 6a2f: ldc 0.46
      // 6a31: ldc_w 2.202
      // 6a34: ldc 1.419
      // 6a36: ldc_w 3.728
      // 6a39: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6a3c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a3f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a42: aastore
      // 6a43: dup
      // 6a44: bipush 12
      // 6a46: new net/minecraft/client/animation/Keyframe
      // 6a49: dup
      // 6a4a: ldc 0.5
      // 6a4c: ldc_w 2.96
      // 6a4f: ldc_w 2.049
      // 6a52: ldc_w -0.736
      // 6a55: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6a58: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a5b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a5e: aastore
      // 6a5f: dup
      // 6a60: bipush 13
      // 6a62: new net/minecraft/client/animation/Keyframe
      // 6a65: dup
      // 6a66: ldc_w 0.54
      // 6a69: ldc_w 2.022
      // 6a6c: ldc_w -0.383
      // 6a6f: ldc_w -2.599
      // 6a72: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6a75: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a78: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a7b: aastore
      // 6a7c: dup
      // 6a7d: bipush 14
      // 6a7f: new net/minecraft/client/animation/Keyframe
      // 6a82: dup
      // 6a83: ldc 0.58
      // 6a85: ldc_w 1.909
      // 6a88: ldc_w -0.471
      // 6a8b: ldc_w -2.852
      // 6a8e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6a91: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a94: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a97: aastore
      // 6a98: dup
      // 6a99: bipush 15
      // 6a9b: new net/minecraft/client/animation/Keyframe
      // 6a9e: dup
      // 6a9f: ldc 0.62
      // 6aa1: ldc_w 1.871
      // 6aa4: ldc_w -0.452
      // 6aa7: ldc_w -3.155
      // 6aaa: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6aad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ab0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6ab3: aastore
      // 6ab4: dup
      // 6ab5: bipush 16
      // 6ab7: new net/minecraft/client/animation/Keyframe
      // 6aba: dup
      // 6abb: ldc 0.67
      // 6abd: ldc_w 1.876
      // 6ac0: ldc_w -0.413
      // 6ac3: ldc_w -3.492
      // 6ac6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6ac9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6acc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6acf: aastore
      // 6ad0: dup
      // 6ad1: bipush 17
      // 6ad3: new net/minecraft/client/animation/Keyframe
      // 6ad6: dup
      // 6ad7: ldc 0.71
      // 6ad9: ldc_w 1.864
      // 6adc: ldc_w -0.579
      // 6adf: ldc_w -3.802
      // 6ae2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6ae5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ae8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6aeb: aastore
      // 6aec: dup
      // 6aed: bipush 18
      // 6aef: new net/minecraft/client/animation/Keyframe
      // 6af2: dup
      // 6af3: ldc 0.75
      // 6af5: ldc_w 1.599
      // 6af8: ldc_w -1.576
      // 6afb: ldc_w -3.799
      // 6afe: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6b01: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6b04: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6b07: aastore
      // 6b08: dup
      // 6b09: bipush 19
      // 6b0b: new net/minecraft/client/animation/Keyframe
      // 6b0e: dup
      // 6b0f: ldc_w 0.79
      // 6b12: ldc_w 3.891
      // 6b15: ldc_w 1.207
      // 6b18: ldc_w -5.434
      // 6b1b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6b1e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6b21: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6b24: aastore
      // 6b25: dup
      // 6b26: bipush 20
      // 6b28: new net/minecraft/client/animation/Keyframe
      // 6b2b: dup
      // 6b2c: ldc 0.83
      // 6b2e: ldc_w 4.499
      // 6b31: ldc_w 4.205
      // 6b34: ldc_w -3.437
      // 6b37: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6b3a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6b3d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6b40: aastore
      // 6b41: dup
      // 6b42: bipush 21
      // 6b44: new net/minecraft/client/animation/Keyframe
      // 6b47: dup
      // 6b48: ldc 0.88
      // 6b4a: ldc_w 3.541
      // 6b4d: ldc_w -2.285
      // 6b50: ldc_w -5.472
      // 6b53: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6b56: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6b59: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6b5c: aastore
      // 6b5d: dup
      // 6b5e: bipush 22
      // 6b60: new net/minecraft/client/animation/Keyframe
      // 6b63: dup
      // 6b64: ldc 0.92
      // 6b66: ldc_w 3.951
      // 6b69: ldc_w 4.42
      // 6b6c: ldc_w -10.277
      // 6b6f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6b72: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6b75: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6b78: aastore
      // 6b79: dup
      // 6b7a: bipush 23
      // 6b7c: new net/minecraft/client/animation/Keyframe
      // 6b7f: dup
      // 6b80: ldc 0.96
      // 6b82: ldc_w 2.717
      // 6b85: ldc_w 5.434
      // 6b88: ldc_w -5.681
      // 6b8b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6b8e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6b91: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6b94: aastore
      // 6b95: dup
      // 6b96: bipush 24
      // 6b98: new net/minecraft/client/animation/Keyframe
      // 6b9b: dup
      // 6b9c: fconst_1
      // 6b9d: ldc_w 0.476
      // 6ba0: ldc_w -3.937
      // 6ba3: ldc_w 1.401
      // 6ba6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6ba9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6bac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6baf: aastore
      // 6bb0: dup
      // 6bb1: bipush 25
      // 6bb3: new net/minecraft/client/animation/Keyframe
      // 6bb6: dup
      // 6bb7: ldc 1.04
      // 6bb9: ldc_w -2.903
      // 6bbc: ldc_w 6.911
      // 6bbf: ldc_w -6.389
      // 6bc2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6bc5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6bc8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6bcb: aastore
      // 6bcc: dup
      // 6bcd: bipush 26
      // 6bcf: new net/minecraft/client/animation/Keyframe
      // 6bd2: dup
      // 6bd3: ldc 1.08
      // 6bd5: ldc_w 0.19
      // 6bd8: ldc_w 8.658
      // 6bdb: ldc_w -9.274
      // 6bde: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6be1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6be4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6be7: aastore
      // 6be8: dup
      // 6be9: bipush 27
      // 6beb: new net/minecraft/client/animation/Keyframe
      // 6bee: dup
      // 6bef: ldc 1.12
      // 6bf1: ldc_w 0.471
      // 6bf4: ldc_w 9.093
      // 6bf7: ldc_w -8.909
      // 6bfa: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6bfd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6c00: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6c03: aastore
      // 6c04: dup
      // 6c05: bipush 28
      // 6c07: new net/minecraft/client/animation/Keyframe
      // 6c0a: dup
      // 6c0b: ldc 1.17
      // 6c0d: ldc_w 0.814
      // 6c10: ldc_w 9.213
      // 6c13: ldc_w -7.998
      // 6c16: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6c19: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6c1c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6c1f: aastore
      // 6c20: dup
      // 6c21: bipush 29
      // 6c23: new net/minecraft/client/animation/Keyframe
      // 6c26: dup
      // 6c27: ldc 1.21
      // 6c29: ldc_w 0.893
      // 6c2c: ldc_w 9.341
      // 6c2f: ldc_w -8.076
      // 6c32: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6c35: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6c38: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6c3b: aastore
      // 6c3c: dup
      // 6c3d: bipush 30
      // 6c3f: new net/minecraft/client/animation/Keyframe
      // 6c42: dup
      // 6c43: ldc_w 1.25
      // 6c46: ldc_w 0.952
      // 6c49: ldc_w 9.353
      // 6c4c: ldc_w -7.906
      // 6c4f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6c52: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6c55: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6c58: aastore
      // 6c59: dup
      // 6c5a: bipush 31
      // 6c5c: new net/minecraft/client/animation/Keyframe
      // 6c5f: dup
      // 6c60: ldc 1.29
      // 6c62: ldc_w 1.018
      // 6c65: ldc_w 9.255
      // 6c68: ldc_w -7.457
      // 6c6b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6c6e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6c71: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6c74: aastore
      // 6c75: dup
      // 6c76: bipush 32
      // 6c78: new net/minecraft/client/animation/Keyframe
      // 6c7b: dup
      // 6c7c: ldc_w 1.33
      // 6c7f: ldc_w 1.099
      // 6c82: ldc_w 9.05
      // 6c85: ldc_w -6.809
      // 6c88: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6c8b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6c8e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6c91: aastore
      // 6c92: dup
      // 6c93: bipush 33
      // 6c95: new net/minecraft/client/animation/Keyframe
      // 6c98: dup
      // 6c99: ldc 1.38
      // 6c9b: ldc_w 1.204
      // 6c9e: ldc_w 8.748
      // 6ca1: ldc_w -6.053
      // 6ca4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6ca7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6caa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6cad: aastore
      // 6cae: dup
      // 6caf: bipush 34
      // 6cb1: new net/minecraft/client/animation/Keyframe
      // 6cb4: dup
      // 6cb5: ldc 1.42
      // 6cb7: ldc_w 1.341
      // 6cba: ldc_w 8.42
      // 6cbd: ldc_w -5.383
      // 6cc0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6cc3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6cc6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6cc9: aastore
      // 6cca: dup
      // 6ccb: bipush 35
      // 6ccd: new net/minecraft/client/animation/Keyframe
      // 6cd0: dup
      // 6cd1: ldc 1.46
      // 6cd3: ldc_w 1.322
      // 6cd6: ldc_w 8.48
      // 6cd9: ldc_w -5.63
      // 6cdc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6cdf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ce2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6ce5: aastore
      // 6ce6: dup
      // 6ce7: bipush 36
      // 6ce9: new net/minecraft/client/animation/Keyframe
      // 6cec: dup
      // 6ced: ldc 1.5
      // 6cef: ldc_w 1.298
      // 6cf2: ldc_w 8.527
      // 6cf5: ldc_w -5.783
      // 6cf8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6cfb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6cfe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6d01: aastore
      // 6d02: dup
      // 6d03: bipush 37
      // 6d05: new net/minecraft/client/animation/Keyframe
      // 6d08: dup
      // 6d09: ldc_w 1.62
      // 6d0c: ldc_w 1.272
      // 6d0f: ldc_w 8.568
      // 6d12: ldc_w -5.871
      // 6d15: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6d18: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6d1b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6d1e: aastore
      // 6d1f: dup
      // 6d20: bipush 38
      // 6d22: new net/minecraft/client/animation/Keyframe
      // 6d25: dup
      // 6d26: ldc 1.75
      // 6d28: ldc_w 1.243
      // 6d2b: ldc_w 8.608
      // 6d2e: ldc_w -5.918
      // 6d31: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6d34: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6d37: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6d3a: aastore
      // 6d3b: dup
      // 6d3c: bipush 39
      // 6d3e: new net/minecraft/client/animation/Keyframe
      // 6d41: dup
      // 6d42: ldc 1.79
      // 6d44: ldc_w 0.391
      // 6d47: ldc_w 7.234
      // 6d4a: ldc_w -3.48
      // 6d4d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6d50: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6d53: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6d56: aastore
      // 6d57: dup
      // 6d58: bipush 40
      // 6d5a: new net/minecraft/client/animation/Keyframe
      // 6d5d: dup
      // 6d5e: ldc_w 1.83
      // 6d61: ldc_w -1.711
      // 6d64: ldc_w 3.279
      // 6d67: ldc_w 0.993
      // 6d6a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6d6d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6d70: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6d73: aastore
      // 6d74: dup
      // 6d75: bipush 41
      // 6d77: new net/minecraft/client/animation/Keyframe
      // 6d7a: dup
      // 6d7b: ldc 1.88
      // 6d7d: ldc_w -3.944
      // 6d80: ldc_w 4.425
      // 6d83: ldc_w 0.967
      // 6d86: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6d89: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6d8c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6d8f: aastore
      // 6d90: dup
      // 6d91: bipush 42
      // 6d93: new net/minecraft/client/animation/Keyframe
      // 6d96: dup
      // 6d97: ldc_w 1.92
      // 6d9a: ldc_w -5.876
      // 6d9d: ldc_w 7.303
      // 6da0: ldc_w -5.421
      // 6da3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6da6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6da9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6dac: aastore
      // 6dad: dup
      // 6dae: bipush 43
      // 6db0: new net/minecraft/client/animation/Keyframe
      // 6db3: dup
      // 6db4: ldc 1.96
      // 6db6: ldc_w -6.647
      // 6db9: ldc_w 4.307
      // 6dbc: ldc_w -9.298
      // 6dbf: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6dc2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6dc5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6dc8: aastore
      // 6dc9: dup
      // 6dca: bipush 44
      // 6dcc: new net/minecraft/client/animation/Keyframe
      // 6dcf: dup
      // 6dd0: fconst_2
      // 6dd1: ldc_w -6.874
      // 6dd4: ldc_w 3.203
      // 6dd7: ldc_w -8.993
      // 6dda: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6ddd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6de0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6de3: aastore
      // 6de4: dup
      // 6de5: bipush 45
      // 6de7: new net/minecraft/client/animation/Keyframe
      // 6dea: dup
      // 6deb: ldc 2.04
      // 6ded: ldc_w -7.085
      // 6df0: ldc_w 4.79
      // 6df3: ldc_w -7.761
      // 6df6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6df9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6dfc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6dff: aastore
      // 6e00: dup
      // 6e01: bipush 46
      // 6e03: new net/minecraft/client/animation/Keyframe
      // 6e06: dup
      // 6e07: ldc 2.08
      // 6e09: ldc_w -7.314
      // 6e0c: ldc_w 6.126
      // 6e0f: ldc_w -7.378
      // 6e12: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6e15: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6e18: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6e1b: aastore
      // 6e1c: dup
      // 6e1d: bipush 47
      // 6e1f: new net/minecraft/client/animation/Keyframe
      // 6e22: dup
      // 6e23: ldc 2.12
      // 6e25: ldc_w -7.546
      // 6e28: ldc_w 7.343
      // 6e2b: ldc_w -7.631
      // 6e2e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6e31: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6e34: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6e37: aastore
      // 6e38: dup
      // 6e39: bipush 48
      // 6e3b: new net/minecraft/client/animation/Keyframe
      // 6e3e: dup
      // 6e3f: ldc 2.17
      // 6e41: ldc_w -7.745
      // 6e44: ldc_w 8.443
      // 6e47: ldc_w -8.173
      // 6e4a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6e4d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6e50: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6e53: aastore
      // 6e54: dup
      // 6e55: bipush 49
      // 6e57: new net/minecraft/client/animation/Keyframe
      // 6e5a: dup
      // 6e5b: ldc 2.21
      // 6e5d: ldc_w -7.832
      // 6e60: ldc_w 9.294
      // 6e63: ldc_w -8.584
      // 6e66: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6e69: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6e6c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6e6f: aastore
      // 6e70: dup
      // 6e71: bipush 50
      // 6e73: new net/minecraft/client/animation/Keyframe
      // 6e76: dup
      // 6e77: ldc 2.25
      // 6e79: ldc_w -7.672
      // 6e7c: ldc_w 9.627
      // 6e7f: ldc_w -8.433
      // 6e82: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6e85: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6e88: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6e8b: aastore
      // 6e8c: dup
      // 6e8d: bipush 51
      // 6e8f: new net/minecraft/client/animation/Keyframe
      // 6e92: dup
      // 6e93: ldc 2.29
      // 6e95: ldc_w -7.079
      // 6e98: ldc_w 9.057
      // 6e9b: ldc_w -7.457
      // 6e9e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6ea1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ea4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6ea7: aastore
      // 6ea8: dup
      // 6ea9: bipush 52
      // 6eab: new net/minecraft/client/animation/Keyframe
      // 6eae: dup
      // 6eaf: ldc 2.33
      // 6eb1: ldc_w -5.897
      // 6eb4: ldc_w 7.257
      // 6eb7: ldc_w -5.846
      // 6eba: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6ebd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ec0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6ec3: aastore
      // 6ec4: dup
      // 6ec5: bipush 53
      // 6ec7: new net/minecraft/client/animation/Keyframe
      // 6eca: dup
      // 6ecb: ldc 2.38
      // 6ecd: ldc_w -3.767
      // 6ed0: ldc_w 6.309
      // 6ed3: ldc_w -4.88
      // 6ed6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6ed9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6edc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6edf: aastore
      // 6ee0: dup
      // 6ee1: bipush 54
      // 6ee3: new net/minecraft/client/animation/Keyframe
      // 6ee6: dup
      // 6ee7: ldc_w 2.42
      // 6eea: ldc_w -1.481
      // 6eed: ldc_w 6.099
      // 6ef0: ldc_w -4.122
      // 6ef3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6ef6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ef9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6efc: aastore
      // 6efd: dup
      // 6efe: bipush 55
      // 6f00: new net/minecraft/client/animation/Keyframe
      // 6f03: dup
      // 6f04: ldc_w 2.46
      // 6f07: ldc_w -0.029
      // 6f0a: ldc_w 4.436
      // 6f0d: ldc_w -2.6
      // 6f10: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6f13: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6f16: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6f19: aastore
      // 6f1a: dup
      // 6f1b: bipush 56
      // 6f1d: new net/minecraft/client/animation/Keyframe
      // 6f20: dup
      // 6f21: ldc_w 2.5
      // 6f24: ldc_w 0.575
      // 6f27: ldc_w 2.132
      // 6f2a: ldc_w -0.619
      // 6f2d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6f30: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6f33: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6f36: aastore
      // 6f37: dup
      // 6f38: bipush 57
      // 6f3a: new net/minecraft/client/animation/Keyframe
      // 6f3d: dup
      // 6f3e: ldc 2.54
      // 6f40: ldc_w 0.698
      // 6f43: ldc_w 0.987
      // 6f46: ldc_w 0.254
      // 6f49: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6f4c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6f4f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6f52: aastore
      // 6f53: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 6f56: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 6f59: ldc_w "CTR_leg_right.002"
      // 6f5c: new net/minecraft/client/animation/AnimationChannel
      // 6f5f: dup
      // 6f60: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 6f63: bipush 26
      // 6f65: anewarray 28
      // 6f68: dup
      // 6f69: bipush 0
      // 6f6a: new net/minecraft/client/animation/Keyframe
      // 6f6d: dup
      // 6f6e: ldc 0.12
      // 6f70: ldc_w -2.707
      // 6f73: fconst_0
      // 6f74: ldc_w -2.155
      // 6f77: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6f7a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6f7d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6f80: aastore
      // 6f81: dup
      // 6f82: bipush 1
      // 6f83: new net/minecraft/client/animation/Keyframe
      // 6f86: dup
      // 6f87: ldc 0.17
      // 6f89: ldc_w -3.33
      // 6f8c: ldc_w -0.047
      // 6f8f: ldc_w 1.517
      // 6f92: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6f95: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6f98: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6f9b: aastore
      // 6f9c: dup
      // 6f9d: bipush 2
      // 6f9e: new net/minecraft/client/animation/Keyframe
      // 6fa1: dup
      // 6fa2: ldc 0.25
      // 6fa4: ldc_w -6.071
      // 6fa7: ldc_w -0.252
      // 6faa: ldc_w 17.673
      // 6fad: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6fb0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6fb3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6fb6: aastore
      // 6fb7: dup
      // 6fb8: bipush 3
      // 6fb9: new net/minecraft/client/animation/Keyframe
      // 6fbc: dup
      // 6fbd: ldc 0.29
      // 6fbf: ldc_w -6.694
      // 6fc2: ldc_w -0.299
      // 6fc5: ldc_w 21.345
      // 6fc8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6fcb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6fce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6fd1: aastore
      // 6fd2: dup
      // 6fd3: bipush 4
      // 6fd4: new net/minecraft/client/animation/Keyframe
      // 6fd7: dup
      // 6fd8: ldc 0.42
      // 6fda: ldc_w -6.694
      // 6fdd: ldc_w -0.299
      // 6fe0: ldc_w 21.345
      // 6fe3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6fe6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6fe9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6fec: aastore
      // 6fed: dup
      // 6fee: bipush 5
      // 6fef: new net/minecraft/client/animation/Keyframe
      // 6ff2: dup
      // 6ff3: ldc 0.46
      // 6ff5: ldc_w -4.711
      // 6ff8: ldc_w -0.299
      // 6ffb: ldc_w 27.369
      // 6ffe: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7001: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7004: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7007: aastore
      // 7008: dup
      // 7009: bipush 6
      // 700b: new net/minecraft/client/animation/Keyframe
      // 700e: dup
      // 700f: ldc 0.5
      // 7011: ldc_w -1.026
      // 7014: ldc_w -0.299
      // 7017: ldc_w 38.556
      // 701a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 701d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7020: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7023: aastore
      // 7024: dup
      // 7025: bipush 7
      // 7027: new net/minecraft/client/animation/Keyframe
      // 702a: dup
      // 702b: ldc_w 0.54
      // 702e: ldc_w 0.958
      // 7031: ldc_w -0.299
      // 7034: ldc_w 44.579
      // 7037: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 703a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 703d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7040: aastore
      // 7041: dup
      // 7042: bipush 8
      // 7044: new net/minecraft/client/animation/Keyframe
      // 7047: dup
      // 7048: ldc 0.75
      // 704a: ldc_w 0.958
      // 704d: ldc_w -0.299
      // 7050: ldc_w 44.579
      // 7053: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7056: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7059: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 705c: aastore
      // 705d: dup
      // 705e: bipush 9
      // 7060: new net/minecraft/client/animation/Keyframe
      // 7063: dup
      // 7064: ldc_w 0.79
      // 7067: ldc_w 0.192
      // 706a: ldc_w -0.295
      // 706d: ldc_w 35.949
      // 7070: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7073: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7076: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7079: aastore
      // 707a: dup
      // 707b: bipush 10
      // 707d: new net/minecraft/client/animation/Keyframe
      // 7080: dup
      // 7081: ldc 0.83
      // 7083: ldc_w -1.635
      // 7086: ldc_w -0.288
      // 7089: ldc_w 16.962
      // 708c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 708f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7092: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7095: aastore
      // 7096: dup
      // 7097: bipush 11
      // 7099: new net/minecraft/client/animation/Keyframe
      // 709c: dup
      // 709d: ldc 0.88
      // 709f: ldc_w -3.817
      // 70a2: ldc_w -0.281
      // 70a5: ldc_w -2.025
      // 70a8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 70ab: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 70ae: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 70b1: aastore
      // 70b2: dup
      // 70b3: bipush 12
      // 70b5: new net/minecraft/client/animation/Keyframe
      // 70b8: dup
      // 70b9: ldc 0.92
      // 70bb: ldc_w -5.649
      // 70be: ldc_w -0.278
      // 70c1: ldc_w -10.655
      // 70c4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 70c7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 70ca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 70cd: aastore
      // 70ce: dup
      // 70cf: bipush 13
      // 70d1: new net/minecraft/client/animation/Keyframe
      // 70d4: dup
      // 70d5: ldc 0.96
      // 70d7: ldc_w -6.419
      // 70da: ldc_w -0.278
      // 70dd: ldc_w -10.655
      // 70e0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 70e3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 70e6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 70e9: aastore
      // 70ea: dup
      // 70eb: bipush 14
      // 70ed: new net/minecraft/client/animation/Keyframe
      // 70f0: dup
      // 70f1: fconst_1
      // 70f2: ldc_w -6.419
      // 70f5: ldc_w -0.278
      // 70f8: ldc_w -10.655
      // 70fb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 70fe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7101: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7104: aastore
      // 7105: dup
      // 7106: bipush 15
      // 7108: new net/minecraft/client/animation/Keyframe
      // 710b: dup
      // 710c: ldc 1.08
      // 710e: ldc_w -5.633
      // 7111: ldc_w -0.219
      // 7114: ldc_w -58.119
      // 7117: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 711a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 711d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7120: aastore
      // 7121: dup
      // 7122: bipush 16
      // 7124: new net/minecraft/client/animation/Keyframe
      // 7127: dup
      // 7128: ldc_w 1.83
      // 712b: ldc_w -5.633
      // 712e: ldc_w -0.219
      // 7131: ldc_w -58.119
      // 7134: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7137: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 713a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 713d: aastore
      // 713e: dup
      // 713f: bipush 17
      // 7141: new net/minecraft/client/animation/Keyframe
      // 7144: dup
      // 7145: ldc 1.88
      // 7147: ldc_w -5.405
      // 714a: ldc_w -0.202
      // 714d: ldc_w -53.747
      // 7150: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7153: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7156: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7159: aastore
      // 715a: dup
      // 715b: bipush 18
      // 715d: new net/minecraft/client/animation/Keyframe
      // 7160: dup
      // 7161: ldc 1.96
      // 7163: ldc_w -4.399
      // 7166: ldc_w -0.127
      // 7169: ldc_w -34.509
      // 716c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 716f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7172: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7175: aastore
      // 7176: dup
      // 7177: bipush 19
      // 7179: new net/minecraft/client/animation/Keyframe
      // 717c: dup
      // 717d: fconst_2
      // 717e: ldc_w -4.17
      // 7181: ldc_w -0.11
      // 7184: ldc_w -30.137
      // 7187: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 718a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 718d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7190: aastore
      // 7191: dup
      // 7192: bipush 20
      // 7194: new net/minecraft/client/animation/Keyframe
      // 7197: dup
      // 7198: ldc 2.33
      // 719a: ldc_w -4.17
      // 719d: ldc_w -0.11
      // 71a0: ldc_w -30.137
      // 71a3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 71a6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 71a9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 71ac: aastore
      // 71ad: dup
      // 71ae: bipush 21
      // 71b0: new net/minecraft/client/animation/Keyframe
      // 71b3: dup
      // 71b4: ldc 2.38
      // 71b6: ldc_w -4.018
      // 71b9: ldc_w -0.098
      // 71bc: ldc_w -27.227
      // 71bf: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 71c2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 71c5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 71c8: aastore
      // 71c9: dup
      // 71ca: bipush 22
      // 71cc: new net/minecraft/client/animation/Keyframe
      // 71cf: dup
      // 71d0: ldc_w 2.42
      // 71d3: ldc_w -3.655
      // 71d6: ldc_w -0.071
      // 71d9: ldc_w -20.287
      // 71dc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 71df: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 71e2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 71e5: aastore
      // 71e6: dup
      // 71e7: bipush 23
      // 71e9: new net/minecraft/client/animation/Keyframe
      // 71ec: dup
      // 71ed: ldc_w 2.46
      // 71f0: ldc_w -3.222
      // 71f3: ldc_w -0.039
      // 71f6: ldc_w -12.005
      // 71f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 71fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 71ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7202: aastore
      // 7203: dup
      // 7204: bipush 24
      // 7206: new net/minecraft/client/animation/Keyframe
      // 7209: dup
      // 720a: ldc_w 2.5
      // 720d: ldc_w -2.859
      // 7210: ldc_w -0.011
      // 7213: ldc_w -5.065
      // 7216: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7219: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 721c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 721f: aastore
      // 7220: dup
      // 7221: bipush 25
      // 7223: new net/minecraft/client/animation/Keyframe
      // 7226: dup
      // 7227: ldc 2.54
      // 7229: ldc_w -2.707
      // 722c: fconst_0
      // 722d: ldc_w -2.155
      // 7230: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7233: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7236: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7239: aastore
      // 723a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 723d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 7240: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.build ()Lnet/minecraft/client/animation/AnimationDefinition;
      // 7243: putstatic net/unusual/block_factorys_bosses/client/animation/yeti/yetiThrowSnowball.THROW_SNOWBALL Lnet/minecraft/client/animation/AnimationDefinition;
      // 7246: return
   }
}
