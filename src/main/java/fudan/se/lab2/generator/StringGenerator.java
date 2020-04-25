package fudan.se.lab2.generator;

public class StringGenerator {

    private static final char[] DIGITS_SET = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final char[] LETTERS_SET = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
                                        'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
                                        'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
                                        'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final char[] SPECIAL_CHARACTERS_SET = {'`', '~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-',
                                                    '=', '_', '+', '[', ']', '{', '}', '|', '\\', '/', '?', ';', ':'};

    private static final char[] DIGITS_AND_LETTERS_SET = concat(DIGITS_SET, LETTERS_SET);

    private static final char[] DIGITS_AND_SPECIAL_CHARACTERS_SET = concat(DIGITS_SET, SPECIAL_CHARACTERS_SET);

    private static final char[] LETTERS_AND_SPECIAL_CHARACTERS_SET = concat(LETTERS_SET, SPECIAL_CHARACTERS_SET, new char[]{' '});

    private static final char[] ALL_SET = concat(DIGITS_SET, LETTERS_SET, SPECIAL_CHARACTERS_SET);

    private static char[] concat(char[]...chars){
        int totalLength = 0;
        int currIndex= 0;
        for(int i = 0; i < chars.length; i++){
            totalLength += chars[i].length;
        }
        char[] result = new char[totalLength];
        for(int i = 0; i < chars.length; i++){
            for(int j = 0; j < chars[i].length; j++){
                result[currIndex++] = chars[i][j];
            }
        }
        return result;
    }


    protected static String getRandomString(int minLength, int maxLength, boolean hasLetters,
                                            boolean hasDigits, boolean hasSpecialCharacters){
        // invalid request
        if((minLength < 1) || (maxLength < minLength) || ((!hasLetters)&&(!hasDigits)&&(!hasSpecialCharacters))){
            return null;
        }

        // select charSet
        char[] charSet;
        if(hasDigits && hasLetters && (!hasSpecialCharacters)){
            charSet = DIGITS_AND_LETTERS_SET;
        }else if(hasDigits && hasLetters && hasSpecialCharacters){
            charSet = ALL_SET;
        }else if(hasDigits && (!hasLetters) && hasSpecialCharacters){
            charSet = DIGITS_AND_SPECIAL_CHARACTERS_SET;
        }else if(hasDigits && (!hasLetters) && (!hasSpecialCharacters)){
            charSet = DIGITS_SET;
        }else if((!hasDigits) && hasLetters && hasSpecialCharacters){
            charSet = LETTERS_AND_SPECIAL_CHARACTERS_SET;
        }else if((!hasDigits) && hasLetters && (!hasSpecialCharacters)){
            charSet = LETTERS_SET;
        }else if((!hasDigits) && (!hasLetters) && hasSpecialCharacters){
            charSet = SPECIAL_CHARACTERS_SET;
        }else{
            charSet = ALL_SET;
        }

        int setLength = charSet.length;
        StringBuilder tempString = new StringBuilder();
        int stringLength = (int)(Math.random() * (maxLength - minLength + 1)) + minLength;

        for(int i = 0; i < stringLength; i++){
            tempString.append(charSet[(int)(Math.random() * setLength)]);
        }

        return tempString.toString();
    }

    protected static String getRandomString(){
        return getRandomString(6, 12, true, true, false);
    }

    protected static String getRandomString(int minLength, int maxLength){
        return getRandomString(minLength, maxLength, true, true, false);
    }
}
