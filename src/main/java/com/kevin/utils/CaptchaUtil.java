/*
 * Copyright (C) 1993-2015 Kingdee Inc.All Rights Reserved.
 * 
 * FileName：CaptchaUtil.java
 * 
 * Description：图片验证码
 * 
 * History：
 * 1.0 Kai.Zhao 2015年6月10日 Create
 * 1.1 Kai.Zhao 2015年6月10日 生成图片验证码
 */
package com.kevin.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;

/** 
 * 图片验证码工具
 * 
 * @author Kai.Zhao
 * @version 1.0
 * @see
 */
public class CaptchaUtil {

	private static final String RAND_STRING = "abcdefghijkmnpqrstuvwxyz23456789ABCDEFGHJKLMNPQRSTUVWXYZ";// 随机产生的字符串

	private static SecureRandom random = new SecureRandom();

	private final static int width = 64;// 图片宽
	private final static int height = 26;// 图片高
	private final static int lineSize = 40;// 干扰线数量
	private final static int stringNum = 4;// 随机产生字符数量

	public static void main(String[] args) {
		generateCaptchaImage(generateRandomString());
	}

	/**
	 * 生成图片验证码
	 * 
	 * @param captcha 验证码
	 * @return
	 * @see
	 */
	public static BufferedImage generateCaptchaImage(String captcha) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
		// 绘制矩形
		g.fillRect(0, 0, width, height);
		// 边框
		/*g.setColor(new Color(0x999999));
		g.drawLine(0, 0, width, 0);
		g.drawLine(0, 0, 0, height);
		g.drawLine(width - 1, 0, width - 1, height - 1);
		g.drawLine(0, height - 1, width - 1, height - 1);*/
		g.setFont(new Font("Microsoft Yahei", Font.ROMAN_BASELINE, 18));
		g.setColor(getRandColor(110, 133));
		// 绘制干扰线
		for (int i = 0; i <= lineSize; i++) {
			drawBlurLine(g);
		}
		// 绘制随机字符
		for (int i = 0; i < captcha.length(); i++) {
			drawString(g, String.valueOf(captcha.charAt(i)), i);
		}

		g.dispose();
		// ImageIO.write(image, "JPEG", new FileOutputStream(new File("c://1.jpeg")));// 将内存中的图片通过流动形式输出到客户端
		return image;
	}

	/*
	 * 绘制字符串
	 */
	private static void drawString(Graphics g, String randomString, int i) {
		g.setFont(getFont());
		g.setColor(new Color(random.nextInt(101), random.nextInt(111), random.nextInt(121)));
		g.translate(random.nextInt(3), random.nextInt(3));
		g.drawString(randomString, 14 * i + random.nextInt(5), 16);
	}

	/**
	 * 生成随机验证码
	 * 
	 * @return
	 * @see
	 */
	public static String generateRandomString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < stringNum; i++) {
			sb.append(getRandomString(random.nextInt(RAND_STRING.length())));
		}
		return sb.toString();
	}

	/*
	 * 获取随机的字符
	 */
	private static String getRandomString(int num) {
		return String.valueOf(RAND_STRING.charAt(num));
	}

	/*
	 * 绘制干扰线
	 */
	private static void drawBlurLine(Graphics g) {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int xl = random.nextInt(13);
		int yl = random.nextInt(15);
		g.drawLine(x, y, x + xl, y + yl);
	}

	/*
	 * 获得字体
	 */
	private static Font getFont() {
		return new Font("Microsoft Yahei", Font.CENTER_BASELINE, 18);
	}

	/*
	 * 获得颜色
	 */
	private static Color getRandColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc - 16);
		int g = fc + random.nextInt(bc - fc - 14);
		int b = fc + random.nextInt(bc - fc - 18);
		return new Color(r, g, b);
	}

}
