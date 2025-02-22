package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {

    // Индексы массива: 1..12
    // MONTHS_ARRAY[1] = "Jan", MONTHS_ARRAY[12] = "Dec"
    private static final String[] MONTHS_ARRAY = {
            "Error", "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };

    // Индексы массива: 1..7
    // DAYS_ARRAY[1] = "Mon", DAYS_ARRAY[7] = "Sun"
    private static final String[] DAYS_ARRAY = {
            "Error", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"
    };

    private DateTimeUtils() {
        // закрытый конструктор, утилитный класс
    }

    // Возвращает текущую дату-время в формате "yyyy-MM-dd, hh:mma"
    public static String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd, hh:mma");
        return dateFormat.format(new Date());
    }

    // Преобразует миллисекунды в формат "X min Y sec"
    public static String getTimeInMinSecFormat(long time) {
        int minutes = (int) ((time / 1000) / 60);
        int seconds = (int) ((time / 1000) % 60);
        return minutes + " min " + seconds + " sec";
    }

    // Возвращает количество дней в месяце (учитывая високосный год для февраля)
    public static int daysInMonth(int month, int year) {
        if (month < 1 || month > 12) {
            return 0;
        }
        if (month == 2) {
            return isLeapYear(year) ? 29 : 28;
        }
        // Месяцы с 31 днями
        if (month == 1 || month == 3 || month == 5 || month == 7 ||
                month == 8 || month == 10 || month == 12) {
            return 31;
        }
        // Остальные (4,6,9,11) - 30 дней
        return 30;
    }

    // Проверка високосного года
    private static boolean isLeapYear(int year) {
        // год високосный:
        // кратен 400, либо кратен 4, но не кратен 100
        return (year % 400 == 0) || (year % 4 == 0 && year % 100 != 0);
    }

    // Возвращаем строковое представление месяца по его номеру (1..12)
    public static String returnMonth(int number) {
        if (number >= 1 && number <= 12) {
            return MONTHS_ARRAY[number];
        }
        return "Error";
    }

    // Возвращаем номер месяца по строке ("Jan" -> 1), иначе 0
    public static int returnMonth(String month) {
        if (month == null) return 0;
        for (int i = 1; i < MONTHS_ARRAY.length; i++) {
            if (MONTHS_ARRAY[i].equalsIgnoreCase(month)) {
                return i;
            }
        }
        return 0;
    }

    // Возвращаем строку дня недели (1..7) => "Mon"
    public static String returnDayOfTheWeek(int number) {
        if (number >= 1 && number <= 7) {
            return DAYS_ARRAY[number];
        }
        return "Error";
    }

    // Возвращаем номер дня недели по строке ("Mon"->1), иначе 0
    public static int returnDayOfTheWeek(String day) {
        if (day == null) return 0;
        for (int i = 1; i < DAYS_ARRAY.length; i++) {
            if (DAYS_ARRAY[i].equalsIgnoreCase(day)) {
                return i;
            }
        }
        return 0;
    }

    // Формируем диапазон из 8 дней (сегодня + 7) с выводом дня недели, месяца, даты
    public static String getEightDaysFromDate(String day, int month, int date, int year) {
        // Валидации
        if (day == null || returnDayOfTheWeek(day) == 0 ||
                "Error".equals(returnMonth(month)) ||
                month < 1 || month > 12 || date < 1 || date > daysInMonth(month, year)) {
            return "Please enter correct data.";
        }

        StringBuilder sb = new StringBuilder();
        int currentDay = returnDayOfTheWeek(day);
        int currentMonth = month;
        int currentDate = date;
        int currentYear = year;

        for (int i = 0; i <= 7; i++) {
            sb.append(returnDayOfTheWeek(currentDay)).append(", ");
            // инкремент дня недели
            currentDay = (currentDay == 7) ? 1 : currentDay + 1;

            // проверяем, не вышли ли за предел дней в месяце
            if (currentDate <= daysInMonth(currentMonth, currentYear)) {
                sb.append(returnMonth(currentMonth)).append(" ")
                        .append(returnDate(currentDate)).append(", ");
                currentDate++;
            } else {
                // переходим на следующий месяц
                if (currentMonth == 12) {
                    currentMonth = 1;
                    currentYear += 1;
                } else {
                    currentMonth++;
                }
                currentDate = 1;
                sb.append(returnMonth(currentMonth)).append(" ")
                        .append(returnDate(currentDate)).append(", ");
                currentDate++;
            }
        }
        // убираем завершающую запятую и пробел
        return sb.substring(0, sb.length() - 2);
    }

    // Форматирование числа даты (1..9 => "01")
    public static String returnDate(int date) {
        return (date < 10) ? "0" + date : String.valueOf(date);
    }
}
