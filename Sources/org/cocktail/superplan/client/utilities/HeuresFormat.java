package org.cocktail.superplan.client.utilities;

import java.math.BigDecimal;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class HeuresFormat extends Format {

	@Override
	public StringBuffer format(Object obj, StringBuffer stringbuffer, FieldPosition fieldposition) {
		if (obj instanceof BigDecimal) {
			BigDecimal n = ((BigDecimal) obj);
			int minutes = n.multiply(new BigDecimal(60)).intValue();
			stringbuffer.append((minutes / 60) + ":" + (minutes % 60 < 10 ? "0" : "") + (minutes % 60));
			return stringbuffer;
		}
		else {
			throw new IllegalArgumentException("Cannot format given Object as a BigDecimal");
		}
	}

	@Override
	public Object parseObject(String s, ParsePosition parseposition) {
		if (s == null) {
			return null;
		}
		String[] sArray = s.split(":");
		int res = Integer.decode(sArray[0]).intValue();
		if (sArray.length > 1) {
			res = (res * 60) + Integer.decode(sArray[1]).intValue();
		}
		return BigDecimal.valueOf((double) res / 60);
	}
}
