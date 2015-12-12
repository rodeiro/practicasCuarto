package es.udc.ws.app.util.validation;

import java.math.BigInteger;
import java.util.Calendar;
import es.udc.ws.util.exceptions.InputValidationException;

public final class PropertyValidator {

    private PropertyValidator() {}

    public static void validateLong(String propertyName,
            long value, int lowerValidLimit, int upperValidLimit)
            throws InputValidationException {

        if ( (value < lowerValidLimit) || (value > upperValidLimit) ) {
            throw new InputValidationException("Invalid " + propertyName +
                    " value (it must be greater than " + lowerValidLimit +
                    " and lower than " + upperValidLimit + "): " + value);
        }

    }

    public static void validateNotNegativeLong(String propertyName,
            long longValue) throws InputValidationException {

        if (longValue < 0) {
            throw new InputValidationException("Invalid " + propertyName +
                    " value (it must be greater than 0): " +	longValue);
        }

    }
    public static void validateFloat(String propertyName,
            float floatValue) throws InputValidationException {

        if (floatValue < 0.0f) {
            throw new InputValidationException("Invalid " + propertyName +
                    " value (it must be greater than 0.0): " +	floatValue);
        }

    }
    public static void validateInt(String propertyName, Integer intValue)
    		throws InputValidationException{
    	if(intValue != null && intValue.intValue() < 0 ){
    		throw new InputValidationException("Invalid " + propertyName +
    				"value(it must be greater than 0): " + intValue);
    	}
    	
    }
    public static void validateFloat2(String propertyName,
            double doubleValue, double lowerValidLimit, double upperValidLimit)
            throws InputValidationException {

        if ((doubleValue < lowerValidLimit) ||
                (doubleValue > upperValidLimit)) {
            throw new InputValidationException("Invalid " + propertyName +
                    " value (it must be gtrater than " + lowerValidLimit +
                    " and lower than " + upperValidLimit + "): " +
                    doubleValue);
        }

    }

    public static void validateMandatoryString(String propertyName,
            String stringValue) throws InputValidationException {

        if ( (stringValue == null) || (stringValue.length() == 0) ) {
            throw new InputValidationException("Invalid " + propertyName +
                    " value (it cannot be null neither empty): " +
                    stringValue);
        }

    }

    public static void validatePastDate(String propertyName,
            Calendar propertyValue) throws InputValidationException {

        Calendar now = Calendar.getInstance();
        if ( (propertyValue == null)) {
        	// || (propertyValue.after(now))
            throw new InputValidationException("Invalid " + propertyName +
                    " value (can't be null): " +
                    propertyValue);
        }

    }
    public static void validateTwoDates(String propertyName,
            Calendar propertyValue1,Calendar propertyValue2)
            		throws InputValidationException {

        if ( (propertyValue2.after(propertyValue1)) ) {
            throw new InputValidationException("Invalid " + propertyName +
                    " value (it must be greater");
        }

    }
    public static void validateCreditCard(String propertyValue)
            throws InputValidationException {

        boolean validCreditCard = true;
        if ( (propertyValue != null) && (propertyValue.length() == 16) ) {
            try {
                new BigInteger(propertyValue);
            } catch (NumberFormatException e) {
                validCreditCard = false;
            }
        } else {
            validCreditCard = false;
        }
        if (!validCreditCard) {
            throw new InputValidationException("Invalid credit card number" +
                    " (it should be a sequence of 16 numeric digits): " +
                    propertyValue);
        }

    }

}