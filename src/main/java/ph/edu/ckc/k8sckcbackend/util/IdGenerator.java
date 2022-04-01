package ph.edu.ckc.k8sckcbackend.util;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.IntStream;


public final class IdGenerator {

    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final Random SECURE_RANDOM = new SecureRandom();
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    private static final Pattern EDGESDHASHES = Pattern.compile("(^-|-$)");

    private IdGenerator() {
        throw new IllegalStateException("Utility class");
    }

    public static String generateId() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hhmmssyyyyMMdd");
        String id = simpleDateFormat.format(date);
        id += UUID.randomUUID().toString().replace("-", "");

        https://www.codewars.com/kata/55bf01e5a717a0d57e0000ec/train/java


        return id;
    }

    public static String generateIdForSlug() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hhmmssyyyyMMdd");
        return "-"+simpleDateFormat.format(date);
    }

    public static String generateIdForSlugV2(int seeds) {
        return generateCharactersInternal(seeds);
    }

    private static String generateCharactersInternal(int seeds) {
        StringBuilder stringBuilder = new StringBuilder(seeds);
        for(int i = 0; i < seeds; i++) {
            stringBuilder.append(CHARACTERS.charAt(SECURE_RANDOM.nextInt(CHARACTERS.length())));
        }
        return new String(stringBuilder);
    }

    public static String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        slug = EDGESDHASHES.matcher(slug).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }
}
