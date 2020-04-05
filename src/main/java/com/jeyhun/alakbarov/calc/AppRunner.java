package com.jeyhun.alakbarov.calc;

import java.util.Scanner;

import com.jeyhun.alakbarov.calc.helpers.BaseHelper;
import com.jeyhun.alakbarov.calc.rpn.InfixPostfixConverter;
import com.jeyhun.alakbarov.calc.rpn.PostfixCalculator;

/**
 * Class which run's application.
 *
 * @author Jeyhun_Alakbarov
 */
public class AppRunner
{
	/**
	 * Main method which run's application.
	 *
	 * @param args
	 */
	public static void main(String... args)
	{
		System.out.println(AppConstants.ENTER_EXPRESSION_MESSAGE);

		try (Scanner in = new Scanner(System.in))
		{
			String infixExpression = in.nextLine();
			String postfixExpression = InfixPostfixConverter.convertToPostfix(infixExpression);
			System.out.println(AppConstants.RPN_OUTPUT_EXPRESSION_MESSAGE + postfixExpression);

			Double result = PostfixCalculator.calculate(postfixExpression);
			System.out.println(AppConstants.RESULT_OUTPUT_MESSAGE + result);
		}
		catch (Exception ex)
		{
			BaseHelper.exitWithError(AppConstants.GENERAL_ERR_MESSAGE);
		}
	}
}
