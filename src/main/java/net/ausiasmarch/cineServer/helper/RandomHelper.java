package net.ausiasmarch.cineServer.helper;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.ThreadLocalRandom;

public class RandomHelper {
    
    protected static SecureRandom random = new SecureRandom();

    public static synchronized String getRandomHexString(int size) {
        StringBuffer sb = new StringBuffer();
        while (sb.length() < size) {
            sb.append(Integer.toHexString(random.nextInt()));
        }
        return sb.toString().substring(0, size);
    }

    public static synchronized String getToken(int size) {
        return Long.toString(Math.abs(random.nextLong()), size);
    }

    public static int getRandomInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static int getRandomInt2(int minValue, int maxValue) {
        return ThreadLocalRandom.current().nextInt(minValue, maxValue);
    }

    public static LocalDateTime getRadomDateTime() {
        return RandomHelper.getRadomDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Date getRadomDate() {
        GregorianCalendar gc = new GregorianCalendar();
        int year = getRandomInt(2010, 2019);
        gc.set(Calendar.YEAR, year);
        int dayOfYear = getRandomInt(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));
        gc.set(Calendar.DAY_OF_YEAR, dayOfYear);
        Date date = new Date(gc.getTimeInMillis());
        return date;
    }
    //FECHA PARA AÑADIRSELO AL NOMBRE DE IMAGEN PUEDE SERVIR PARA FECHA DE BAJA
    public static LocalDateTime randomDate() {
        LocalDateTime now = LocalDateTime.now();
        return now;
    }

    //DEVUELVE FECHA ACTUAL EN FORMATO LONG. Se utiliza en el sufijo de nombre de pelicula
    public static Long dateLong() {
        LocalDateTime now =LocalDateTime.now().minusNanos(getRadomChar());
        
        ZonedDateTime zdt = ZonedDateTime.of(now, ZoneId.systemDefault());
        long date = zdt.toInstant().toEpochMilli();

        return date;
    }

    public static LocalDateTime getRadomDate2() {
        int randomSeconds = new Random().nextInt(3600 * 24);
        LocalDateTime anyTime = LocalDateTime.now().minusSeconds(randomSeconds);
        return anyTime;
    }

    public static char getRadomChar() {
        Random r = new Random();
        char c = (char) (r.nextInt(26) + 'a');
        return Character.toUpperCase(c);
    }

    public static double getRadomDouble(int rangeMin, int rangeMax) {
        Random r = new Random();
        return rangeMin + (rangeMax - rangeMin) * r.nextDouble();
    }

    public static double getRadomDouble(double minValue, double maxValue) {
        return Math.round(ThreadLocalRandom.current().nextDouble(minValue, maxValue) * 100d) / 100d;
    }
}
