package ee.taltech.iti0200.idcode;
public class IdCode {

    private final String idCodeValue;
    public static final int GENDER_POSITION = 0;
    public static final int YEAR_POSITION_START = 1;
    public static final int MONTH_POSITION_START = 3;
    public static final int DAY_POSITION_START = 5;
    public static final int QUEUE_POSITION_START = 7;
    public static final int CONTROL_POSITION = 10;
    public static final int KURESSAARE = 10;
    public static final int TARTU_1 = 20;
    public static final int TARTU_2 = 271;
    public static final int TARTU_3 = 370;
    public static final int TALLINN_1 = 220;
    public static final int TALLINN_2 = 471;
    public static final int TALLINN_3 = 490;
    public static final int KOHTLA_JARVE = 270;
    public static final int NARVA = 420;
    public static final int PARNU = 470;
    public static final int PAIDE = 520;
    public static final int RAKVERE = 570;
    public static final int VALGA = 600;
    public static final int VILJANDI = 650;
    public static final int VORU = 710;
    public static final int GENDER1800_1 = 1;
    public static final int GENDER1800_2 = 2;
    public static final int GENDER1800 = 1800;
    public static final int GENDER1900_1 = 3;
    public static final int GENDER1900_2 = 4;
    public static final int GENDER1900 = 1900;
    public static final int GENDER2000 = 2000;
    public static final int WRONG_YEAR = -1;
    public static final int GENDER_START = 1;
    public static final int GENDER_END = 6;
    public static final int YEAR_START = 0;
    public static final int YEAR_END = 99;
    public static final int MONTH_START = 1;
    public static final int MONTH_END = 12;
    public static final int MONTH_NUM_4 = 4;
    public static final int MONTH_NUM_6 = 6;
    public static final int MONTH_NUM_9 = 9;
    public static final int MONTH_NUM_11 = 11;
    public static final int DAY_START = 1;
    public static final int DAYS_30 = 30;
    public static final int DAYS_29 = 29;
    public static final int DAYS_28 = 28;
    public static final int DAYS_31 = 31;
    public static final int FEBRUARY = 2;
    public static final int QUEUE_START = 0;
    public static final int QUEUE_END = 999;
    public static final int MODULUS_EQUALS_ZERO = 0;
    public static final int MODULUS_EQUALS_TEN = 10;
    public static final int YEAR_2000 = 2000;
    public static final int[] FIRSTMULTIPLIERS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 1};
    public static final int[] SECONDMULTIPLIERS = {3, 4, 5, 6, 7, 8, 9, 1, 2, 3};
    public static final int CONTROL_NUMBER_MODULUS = 11;
    public static final int YEAR_400TH = 400;


    enum Gender {
        MALE, FEMALE
    }

    public String getIdCodeValue() {
        return idCodeValue;
    }

    public IdCode(String idCodeValue) {
        this.idCodeValue = idCodeValue;
    }

    public boolean isCorrect() {
        for (char c: idCodeValue.toCharArray()) {
            if (Character.isAlphabetic(c)) {
                return false;
            }
        }
        return isGenderNumberCorrect() && isYearNumberCorrect()
                && isMonthNumberCorrect() && isDayNumberCorrect()
                && isQueueNumberCorrect() && isControlNumberCorrect();
    }

    public String getInformation() {
        if (isCorrect()) {
            return "This is a " + getGender() + " born on "
                    + idCodeValue.substring(DAY_POSITION_START, QUEUE_POSITION_START) + "."
                    + idCodeValue.substring(MONTH_POSITION_START, DAY_POSITION_START) + "." + getFullYear()
                    + " in " + getBirthPlace();
        }
        return "Given invalid ID code!";
    }

    public Gender getGender() {
        if (isGenderNumberCorrect()) {
            int genderNum = Character.getNumericValue(idCodeValue.charAt(GENDER_POSITION));
            if (genderNum % 2 == 1) {
                return Gender.MALE;
            }   else {
                return Gender.FEMALE;
            }
        }
        return null;
    }

    public String getBirthPlace() {
        if (isQueueNumberCorrect()) {
            int queueNum = Integer.parseInt(idCodeValue.substring(QUEUE_POSITION_START, CONTROL_POSITION));
            if (queueNum == 0) {
                return null;
            } else if (queueNum <= KURESSAARE) {
                return "Kuressaare";
            } else if (queueNum <= TARTU_1 || queueNum >= TARTU_2 && queueNum <= TARTU_3) {
                return "Tartu";
            } else if (queueNum <= TALLINN_1 || queueNum >= TALLINN_2 && queueNum <= TALLINN_3) {
                return "Tallinn";
            } else if (queueNum <= KOHTLA_JARVE) {
                return "Kohtla-Järve";
            } else if (queueNum <= NARVA) {
                return "Narva";
            } else if (queueNum <= PARNU) {
                return "Pärnu";
            } else if (queueNum <= PAIDE) {
                return "Paide";
            } else if (queueNum <= RAKVERE) {
                return "Rakvere";
            } else if (queueNum <= VALGA) {
                return "Valga";
            } else if (queueNum <= VILJANDI) {
                return "Viljandi";
            } else if (queueNum <= VORU) {
                return "Võru";
            } else {
                return null;
            }
        }
        return "Wrong input!";
    }

    public int getFullYear() {
        if (isGenderNumberCorrect() && isYearNumberCorrect()) {
            int genderNum = Character.getNumericValue(idCodeValue.charAt(GENDER_POSITION));
            int yearNum = Integer.parseInt(idCodeValue.substring(YEAR_POSITION_START, MONTH_POSITION_START));
            if (genderNum == GENDER1800_1 || genderNum == GENDER1800_2) {
                return GENDER1800 + yearNum;
            } else if (genderNum == GENDER1900_1 || genderNum == GENDER1900_2) {
                return GENDER1900 + yearNum;
            } else {
                return GENDER2000 + yearNum;
            }
        }
        return WRONG_YEAR;
    }

    private boolean isGenderNumberCorrect() {
        int genderNum = Character.getNumericValue(idCodeValue.charAt(GENDER_POSITION));
        return genderNum >= GENDER_START && genderNum <= GENDER_END;
    }

    private boolean isYearNumberCorrect() {
        for (char c: idCodeValue.toCharArray()) {
            if (Character.isAlphabetic(c) || idCodeValue.contains("+") || idCodeValue.contains("-")) {
                return false;
            }
        }
        int yearNum = Integer.parseInt(idCodeValue.substring(YEAR_POSITION_START, MONTH_POSITION_START));
        return yearNum >= YEAR_START && yearNum <= YEAR_END;
    }

    private boolean isMonthNumberCorrect() {
        for (char c: idCodeValue.toCharArray()) {
            if (Character.isAlphabetic(c) || idCodeValue.contains("+") || idCodeValue.contains("-")) {
                return false;
            }
        }
        int monthNum = Integer.parseInt(idCodeValue.substring(MONTH_POSITION_START, DAY_POSITION_START));
        return monthNum >= MONTH_START && monthNum <= MONTH_END;
    }

    private boolean isDayNumberCorrect() {
        for (char c: idCodeValue.toCharArray()) {
            if (Character.isAlphabetic(c) || idCodeValue.contains("+") || idCodeValue.contains("-")) {
                return false;
            }
        }
        int year = getFullYear();
        int monthNum = Integer.parseInt(idCodeValue.substring(MONTH_POSITION_START, DAY_POSITION_START));
        int dayNum = Integer.parseInt(idCodeValue.substring(DAY_POSITION_START, QUEUE_POSITION_START));
        if (monthNum == MONTH_NUM_4 || monthNum == MONTH_NUM_6 || monthNum == MONTH_NUM_9 || monthNum == MONTH_NUM_11) {
            return dayNum >= DAY_START && dayNum <= DAYS_30;
        } else if (monthNum == FEBRUARY) {
            if (isLeapYear(year)) {
                return dayNum >= DAY_START && dayNum <= DAYS_29;
            } else {
                return dayNum >= DAY_START && dayNum <= DAYS_28;
            }
        } else if (monthNum >= MONTH_START && monthNum <= MONTH_END) {
            return dayNum >= DAY_START && dayNum <= DAYS_31;
        } else {
            return false;
        }
    }

    private boolean isQueueNumberCorrect() {
        for (char c: idCodeValue.toCharArray()) {
            if (Character.isAlphabetic(c) || idCodeValue.contains("+") || idCodeValue.contains("-")) {
                return false;
            }
        }
        int queueNum = Integer.parseInt(idCodeValue.substring(QUEUE_POSITION_START, CONTROL_POSITION));
        return queueNum >= QUEUE_START && queueNum <= QUEUE_END;
    }

    private boolean isControlNumberCorrect() {
        int[] shortId = new int[10];
        for (int i = 0; i < 10; i++) {
            shortId[i] = Character.getNumericValue(idCodeValue.charAt(i));
        }
        int controlNum = Character.getNumericValue(idCodeValue.charAt(CONTROL_POSITION));
        int first = 0;
        int second = 0;
        for (int i = 0; i < 10; i++) {
            first += shortId[i] * FIRSTMULTIPLIERS[i];
        }
        if (first % CONTROL_NUMBER_MODULUS >= MODULUS_EQUALS_ZERO && first % CONTROL_NUMBER_MODULUS < MODULUS_EQUALS_TEN
                && controlNum == first % CONTROL_NUMBER_MODULUS) {
            return true;
        } else if (first % CONTROL_NUMBER_MODULUS == MODULUS_EQUALS_TEN) {
            for (int i = 0; i < 10; i++) {
                second += shortId[i] * SECONDMULTIPLIERS[i];
            }
            return second % CONTROL_NUMBER_MODULUS >= MODULUS_EQUALS_ZERO
                    && second % CONTROL_NUMBER_MODULUS < MODULUS_EQUALS_TEN
                    && controlNum == second % CONTROL_NUMBER_MODULUS
                    || second % CONTROL_NUMBER_MODULUS == MODULUS_EQUALS_TEN && controlNum == MODULUS_EQUALS_ZERO;
        } else {
            return false;
        }
    }

    private boolean isLeapYear(int fullYear) {
        return fullYear % 4 == MODULUS_EQUALS_ZERO && fullYear % 100 != MODULUS_EQUALS_ZERO
                || fullYear % YEAR_400TH == MODULUS_EQUALS_ZERO && fullYear % 100 == MODULUS_EQUALS_ZERO
                || fullYear == YEAR_2000;
    }

    public static void main(String[] args) {
        IdCode validMaleIdCode = new IdCode("51401010009");
        validMaleIdCode.getInformation();
        System.out.println(validMaleIdCode.isCorrect());
        System.out.println(validMaleIdCode.getBirthPlace());
    }
}
