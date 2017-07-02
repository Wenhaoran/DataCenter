package cn.digitalpublishing.util;

import java.math.BigDecimal;

/**
 * double的计算不精确，会有类似0.0000000000000002的误差，正确的方法是使用BigDecimal或者用整型
 * 整型地方法适合于货币精度已知的情况，比如12.11+1.10转成1211+110计算，最后再/100即可 以下是摘抄的BigDecimal方法:
 */
public class MathUtil {

	// 默认除法运算精度
	private static final Integer DEF_DIV_SCALE = 2;
	
	/**
	 * 判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNum(String str){
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param value1
	 *            被加数
	 * @param value2
	 *            加数
	 * @return 两个参数的和
	 */
	public static Double add(Number value1, Number value2) {
		BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
		BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确的减法运算。
	 * 
	 * @param value1
	 *            被减数
	 * @param value2
	 *            减数
	 * @return 两个参数的差
	 */
	public static Double subtract(Number value1, Number value2) {
		BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
		BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param value1
	 *            被乘数
	 * @param value2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static Double multiply(Number value1, Number value2) {
		BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
		BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时， 精确到小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param dividend
	 *            被除数
	 * @param divisor
	 *            除数
	 * @return 两个参数的商
	 */
	public static Double divide(Double dividend, Double divisor) {
		return divide(dividend, divisor, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
	 * 
	 * @param dividend
	 *            被除数
	 * @param divisor
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static Double divide(Double dividend, Double divisor, Integer scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(dividend));
		BigDecimal b2 = new BigDecimal(Double.toString(divisor));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param value
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static Double round(Double value, Integer scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(value));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static void main(String[] args) {
		Double d1 = 1.324455446;
		Double d2 = 2.343546677;
		Double add = MathUtil.add(d1, d2);
		Double subtract = MathUtil.subtract(d1, d2);
		Double multiply = MathUtil.multiply(d1, d2);
		Double divide = MathUtil.divide(d1, d2);
		System.out.println(add);
		System.out.println(subtract);
		System.out.println(multiply);
		System.out.println(divide);
	}


	/**
	 * 提供精确的加法运算。
	 * 
	 * @param value1
	 *            被加数
	 * @param value2
	 *            加数
	 * @return 两个参数的和
	 */
	public static BigDecimal add(BigDecimal value1, BigDecimal value2) {
		return value1.add(value2);
	}

	/**
	 * 提供精确的减法运算。
	 * 
	 * @param value1
	 *            被减数
	 * @param value2
	 *            减数
	 * @return 两个参数的差
	 */
	public static BigDecimal subtract(BigDecimal value1, BigDecimal value2) {
		return value1.subtract(value2);
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param value1
	 *            被乘数
	 * @param value2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static BigDecimal multiply(BigDecimal value1, BigDecimal value2) {
		return value1.multiply(value2);
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时， 精确到小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param dividend
	 *            被除数
	 * @param divisor
	 *            除数
	 * @return 两个参数的商
	 */
	public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor) {
		return divide(dividend, divisor, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
	 * 
	 * @param dividend
	 *            被除数
	 * @param divisor
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor, Integer scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		return dividend.divide(divisor, scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param value
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static BigDecimal round(BigDecimal value, Integer scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal one = new BigDecimal("1");
		return value.divide(one, scale, BigDecimal.ROUND_HALF_UP);
	}
}
