package com.jeyhun.alakbarov.calc.rpn;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.jeyhun.alakbarov.calc.AppConstants;
import com.jeyhun.alakbarov.calc.helpers.BaseHelper;

/**
 * This is converter class which converts infix expression into postfix using Reverse Polish notation.
 *
 * @author Jeyhun_Alakbarov
 */
public class InfixPostfixConverter
{
	private final static String RIGHT_ASSOCIATIVE_OPERATORS = "^";
	private final static String POSTFIX_OPERATORS = "%";
	private static Map<String, Integer> operatorPrecedenceMap = new HashMap<>();

	static
	{
		operatorPrecedenceMap.put("%", 9);
		operatorPrecedenceMap.put("U-", 9);
		operatorPrecedenceMap.put("^", 7);
		operatorPrecedenceMap.put("/", 5);
		operatorPrecedenceMap.put("*", 5);
		operatorPrecedenceMap.put("+", 3);
		operatorPrecedenceMap.put("-", 3);
		operatorPrecedenceMap.put("(", 1);
		operatorPrecedenceMap.put(")", 1);
	}

	/**
	 * Converts input infix expression containing into Reverse Polish notation expression.
	 *
	 * @param infixExpression
	 * @return {@link String}
	 */
	public static String convertToPostfix(String infixExpression)
	{
		String[] tokens = infixExpression.split(" ");
		Stack<String> stack = new Stack<>();
		StringBuilder result = new StringBuilder("");
		String lastToken = "";

		for (int i = 0; i < tokens.length; i++)
		{
			String token = tokens[i];

			if (i == tokens.length - 1 && !BaseHelper.isNumber(token) && !isPostfixOperator(token) && !token.equals(")"))
			{
				BaseHelper.exitWithError(AppConstants.INVALID_INPUT_ERR_MESSAGE);
			}

			if (isOperator(token))
			{
				if ("(".equals(token))
				{
					stack.push(token);
				}
				else if (")".equals(token))
				{
					if (!stack.contains("("))
					{
						BaseHelper.exitWithError(AppConstants.INVALID_INPUT_ERR_MESSAGE);
					}
					while (!"(".equals(stack.peek()))
					{
						result.append(stack.pop() + " ");
					}
					stack.pop();
				}
				else if (isPostfixOperator(token))
				{
					result.append(token + " ");
				}
				else if (token.equals("-") && (lastToken.equals("") || (operatorPrecedenceMap.containsKey(lastToken)
						&& !lastToken.equals(")"))))
				{
					if (lastToken.equals("U-"))
					{
						BaseHelper.exitWithError(AppConstants.INVALID_INPUT_ERR_MESSAGE);
					}
					stack.push("U-");
					lastToken = "U-";
					continue;
				}
				else
				{
					while (!stack.empty() && ((operatorPrecedenceMap.get(token) < operatorPrecedenceMap.get(stack.peek()))
							|| (operatorPrecedenceMap.get(token) == operatorPrecedenceMap.get(stack.peek())
							&& !isRightAssociativeOperator(token))))
					{
						result.append(stack.pop() + " ");
					}
					stack.push(token);
				}

				lastToken = token;
				continue;
			}

			if (BaseHelper.isNumber(token) && !BaseHelper.isNumber(lastToken))
			{
				result.append(token + " ");
				lastToken = token;
				continue;
			}

			BaseHelper.exitWithError(AppConstants.INVALID_INPUT_ERR_MESSAGE);
		}
		while (!stack.isEmpty())
		{
			result.append(stack.pop() + " ");
		}

		return result.toString().trim();
	}

	//////////////////////////////////////////////
	//
	// Helper methods
	//
	//////////////////////////////////////////////

	/**
	 * Checks is provided string operator or not.
	 *
	 * @param str {@link String}
	 * @return <code>true</code> if provided string operator and <code>false</code> if not.
	 */
	private static boolean isOperator(String str)
	{
		return operatorPrecedenceMap.containsKey(str);
	}

	/**
	 * Checks is provided operator right associative or not.
	 *
	 * @param operator {@link String}
	 * @return <code>true</code> if provided operator right associative and <code>false</code> if not.
	 */
	private static boolean isRightAssociativeOperator(String operator)
	{
		return RIGHT_ASSOCIATIVE_OPERATORS.contains(operator);
	}

	/**
	 * Checks is provided operator right associative or not.
	 *
	 * @param operator {@link String}
	 * @return <code>true</code> if provided operator right associative and <code>false</code> if not.
	 */
	private static boolean isPostfixOperator(String operator)
	{
		return POSTFIX_OPERATORS.contains(operator);
	}
}
