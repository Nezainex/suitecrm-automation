package utils;

import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.Date;
import java.util.Locale;

@UtilityClass
public class DateTimeUtils {

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
            throw new IllegalArgumentException("Некорректный номер месяца " + month);
        }
        return YearMonth.of(year, month).lengthOfMonth();
    }

    // Проверка високосного года
    private static boolean isLeapYear(int year) {
        return Year.of(year).isLeap();
    }

    // Возвращаем строковое представление месяца по его номеру (1..12)
    public static String returnMonth(int number) {
        return Month.of(number).toString().substring(0, 3);
    }

    // Возвращаем номер месяца по строке ("Jan" -> 1), иначе 0
    public static int returnMonth(String month) {
        return Month.valueOf(month).getValue();
    }

    // Возвращаем строку дня недели (1..7) => "Mon"
    // Возвращает название дня недели по номеру (1 - Monday, 7 - Sunday)
    public static String returnDayOfTheWeek(int number) {
        if (number < 1 || number > 7) {
            return "Error";
        }
        return DayOfWeek.of(number).name().substring(0, 3);
    }

    // Возвращает номер дня недели по названию ("Mon" -> 1), иначе 0
    public static int returnDayOfTheWeek(String day) {
        if (day == null || day.length() < 3) return 0;
        try {
            return DayOfWeek.valueOf(day.toUpperCase(Locale.ROOT)).getValue();
        } catch (IllegalArgumentException e) {
            return 0;
        }
    }

    //TODO: Рефакторинг доделать

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
