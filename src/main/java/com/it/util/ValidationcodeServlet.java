package com.it.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.SecureRandom;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 生成验证码
 * @author Administrator
 *
 */
public class ValidationcodeServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -882161020848959129L;

	/**
     * 验证码图片的宽度
     * 
     */
    private int width = 60;
     
    /**
     * 验证码图片的高度
     * 
     */
    private int height = 28;
     
    /**
     * 验证码字符个数
     * 
     */
    private int codeCount = 4;
 
    /**
     * 字体宽度
     * 
     */
    private int x = 0;
     
    /**
     * 字体高度
     * 
     */
    private int fontHeight;
     
    /**
     * 字体纵坐标位置
     * 
     */
    private int codeY;
 
    /**
     * 用于随机生成字符的数组
     * 
     */
    char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
 
    /** 
     * （重写方法）
     * init方法<br>
     * 初始化验证图片属性
     * 
     *
     * @throws ServletException
     */
    public void init() throws ServletException {
        // 从web.xml中获取初始信息
        // 宽度
        String strWidth = this.getInitParameter("width");
        // 高度
        String strHeight = this.getInitParameter("height");
        // 字符个数
        String strCodeCount = this.getInitParameter("codeCount");
 
        // 将配置的信息转换成数值
        try {
            if (strWidth != null && strWidth.length() != 0) {
                width = Integer.parseInt(strWidth);
            }
            if (strHeight != null && strHeight.length() != 0) {
                height = Integer.parseInt(strHeight);
            }
            if (strCodeCount != null && strCodeCount.length() != 0) {
                codeCount = Integer.parseInt(strCodeCount);
            }
        } catch (NumberFormatException e) {
        }
 
        x = width / (codeCount + 1);
        fontHeight = height - 8;
        codeY = height - 4;
 
    }
 
    /** 
     * （重写方法）
     * doGet方法
     * 
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws java.io.IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
 
        HttpSession session = request.getSession();
         
        // 定义图像buffer
        BufferedImage buffImg = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = buffImg.createGraphics();
 
 
        // 将图像填充为白色
        graphics2D.setColor(new Color(233, 240, 186));
        graphics2D.fillRect(0, 0, width, height);
 
        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
        // 设置字体。
        graphics2D.setFont(font);
 
        // 画边框。
        graphics2D.setColor(Color.BLACK);
        //graphics2D.drawRect(0, 0, width - 1, height - 1);
 
        // 随机产生20条干扰线，使图象中的认证码不易被其它程序探测到。
        graphics2D.setColor(Color.BLACK);
         
        // 创建一个随机数生成器类
        //Random random = new Random();//Random不安全SecureRandom 中提供了一个加密 PRNG
        SecureRandom secureRandom = new SecureRandom();
        StringBuffer randomCode = new StringBuffer();
        int red = 0, green = 0, blue = 0;
 
        // 随机产生codeCount数字的验证码。
        for (int i = 0; i < codeCount; i++) {
            // 得到随机产生的验证码数字。
            //String strRand = String.valueOf(codeSequence[random.nextInt(34)]);
        	String strRand = String.valueOf(codeSequence[secureRandom.nextInt(34)]);
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            
            red=secureRandom.nextInt(255);
            green=secureRandom.nextInt(255);
            blue=secureRandom.nextInt(255);
            /*red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);*/
 
            if (red > 200 && green > 200 && blue > 200 ) {
            	red = green = blue = 0;
            }
 
            // 用随机产生的颜色将验证码绘制到图像中。
            graphics2D.setColor(new Color(red, green, blue));
            graphics2D.drawString(strRand, (i + 1) * x - codeCount, codeY);
            
            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand);
        } 
        // 将四位数字的验证码保存到Session中。
        session.setAttribute(SysConstant.IDENTIFY_CODE, randomCode.toString());
 
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
 
        response.setContentType("image/jpeg");
 
        //清空缓存
        graphics2D.dispose();
         
//        // 将图像输出到Servlet输出流中。
        ServletOutputStream servletOutputStream = response.getOutputStream();
//        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(servletOutputStream);
//        encoder.encode(buffImg);
        ImageIO.write(buffImg, "jpeg", servletOutputStream);
        servletOutputStream.close();
    }
     
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
