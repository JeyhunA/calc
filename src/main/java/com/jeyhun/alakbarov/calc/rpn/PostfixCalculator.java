package com.jeyhun.alakbarov.calc.rpn;

import java.util.Stack;

import com.jeyhun.alakbarov.calc.AppConstants;
import com.jeyhun.alakbarov.calc.helpers.BaseHelper;

/**
 * This is calculator class which evaluates Reverse Polish notation expression.
 *
 * @author Jeyhun_Alakbarov
 */
public class PostfixCalculator
{
	/**
	 * This method evaluate provided Reverse Polish notation expression and returns the result.
	 *
	 * @param postfixExpression
	 * @return {@link Double}
	 */
	public static Double calculate(String postfixExpression)
	{
		Double result = 0.0;
		String[] tokens = postfixExpression.split(" ");
		Stack<Double> stack = new Stack<>();

		for (String token : tokens)
		{
			if (BaseHelper.isNumber(token))
			{
				stack.push(Double.valueOf(token));
			}
			else if (isUnaryOperator(token))
			{
				Double number = stack.pop();
				switch (token)
				{
					case "%":
						stack.push(number / 100.0);
						break;
					case "U-":
						stack.push((-number));
						break;
					default:
						BaseHelper.exitWithError(AppConstants.EVALUATE_RPN_ERR_MESSAGE);
				}
			}
			else
			{
				Double b = stack.pop();
				Double a = stack.pop();
				switch (token)
				{
					case "+":
						stack.push(a + b);
						break;
					case "-":
						stack.push(a - b);
						break;
					case "*":
						stack.push(a * b);
						break;
					case "/":
						if (b == 0.0)
						{
							BaseHelper.exitWithError(AppConstants.DIVIDE_ZERO_ERR_MESSAGE);
						}
						stack.push(a / b);
						break;
					case "^":
						stack.push(Math.pow(a, b));
						break;
					default:
						BaseHelper.exitWithError(AppConstants.EVALUATE_RPN_ERR_MESSAGE);
				}
			}
		}
		result = stack.pop();

		return result;
	}

	//////////////////////////////////////////////
	//
	// Helper methods
	//
	//////////////////////////////////////////////

	/**
	 * Checks is provided operator unary or not.
	 *
	 * @param operator {@link String}
	 * @return <code>true</code> if provided operator unary and <code>false</code> if not.
	 */
	private static boolean isUnaryOperator(String operator)
	{
		return operator.equals("%") || operator.equals("U-");
	}
}
