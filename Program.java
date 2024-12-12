import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

/**
 * Клас, що представляє окрему літеру.
 * Забезпечує перевірку на валідність символу і надає методи доступу до нього.
 */
class Letter {
    private char character; // Символ, що представляє літеру.

    /**
     * Конструктор літери.
     * Перевіряє, чи символ є літерою.
     *
     * @param character символ літери.
     * @throws IllegalArgumentException якщо символ не є літерою.
     */
    public Letter(char character) {
        if (Character.isLetter(character)) {
            this.character = character;
        } else {
            throw new IllegalArgumentException("Тільки літера може бути створена");
        }
    }

    /**
     * Повертає символ літери.
     *
     * @return символ літери.
     */
    public char getCharacter() {
        return character;
    }

    /**
     * Повертає рядкове представлення літери.
     *
     * @return літеру у вигляді рядка.
     */
    @Override
    public String toString() {
        return String.valueOf(character);
    }
}

/**
 * Клас, що представляє слово, яке складається з літер.
 * Забезпечує функціонал для аналізу, доступу і маніпуляції зі словами.
 */
class Word {
    private List<Letter> letters; // Список літер, що формують слово.

    /**
     * Конструктор слова.
     * Перетворює рядок у список літер, ігноруючи неалфавітні символи.
     *
     * @param word рядок, що представляє слово.
     */
    public Word(String word) {
        letters = new ArrayList<>();
        for (char ch : word.toCharArray()) {
            if (Character.toString(ch).matches("[а-яА-ЯіІєЄїЇґҐa-zA-Z]")) {
                letters.add(new Letter(ch));
            }
        }
    }

    /**
     * Повертає довжину слова.
     *
     * @return кількість літер у слові.
     */
    public int length() {
        return letters.size();
    }

    /**
     * Повертає слово у вигляді рядка.
     *
     * @return слово у вигляді рядка.
     */
    public String getWord() {
        StringBuilder word = new StringBuilder();
        for (Letter letter : letters) {
            word.append(letter.getCharacter());
        }
        return word.toString();
    }

    /**
     * Перетворює всі літери у слові на малі.
     */
    public void toLowerCase() {
        for (int i = 0; i < letters.size(); i++) {
            letters.set(i, new Letter(Character.toLowerCase(letters.get(i).getCharacter())));
        }
    }

    /**
     * Перетворює всі літери у слові на великі.
     */
    public void toUpperCase() {
        for (int i = 0; i < letters.size(); i++) {
            letters.set(i, new Letter(Character.toUpperCase(letters.get(i).getCharacter())));
        }
    }

    /**
     * Повертає слово у вигляді рядка.
     *
     * @return слово у вигляді рядка.
     */
    @Override
    public String toString() {
        return getWord();
    }
}

/**
 * Клас, що представляє речення, яке складається з слів і розділових знаків.
 */
class Sentence {
    private List<Word> words; // Список слів у реченні.
    private String punctuationMark; // Розділовий знак наприкінці речення (якщо є).

    /**
     * Конструктор речення.
     * Розбиває рядок на слова і визначає розділовий знак.
     *
     * @param sentence рядок, що представляє речення.
     */
    public Sentence(String sentence) {
        words = new ArrayList<>();
        StringBuilder wordBuilder = new StringBuilder();

        for (int i = 0; i < sentence.length(); i++) {
            char ch = sentence.charAt(i);
            if (Character.isLetterOrDigit(ch) || ch == '-') {
                wordBuilder.append(ch); // Додаємо символ до поточного слова.
            } else {
                if (wordBuilder.length() > 0) {
                    words.add(new Word(wordBuilder.toString())); // Додаємо слово до списку.
                    wordBuilder.setLength(0); // Очищуємо конструктор слова.
                }
                if (ch == '?' || ch == '!' || ch == '.') {
                    punctuationMark = String.valueOf(ch); // Зберігаємо розділовий знак.
                }
            }
        }
        if (wordBuilder.length() > 0) {
            words.add(new Word(wordBuilder.toString())); // Додаємо останнє слово.
        }
    }

    /**
     * Перевіряє, чи закінчується речення знаком питання.
     *
     * @return true, якщо речення закінчується знаком питання, інакше false.
     */
    public boolean endsWithQuestionMark() {
        return "?".equals(punctuationMark);
    }

    /**
     * Повертає список слів у реченні.
     *
     * @return список слів.
     */
    public List<Word> getWords() {
        return words;
    }
}

/**
 * Клас, що представляє текст, який складається з речень.
 */
class Text {
    private List<Sentence> sentences; // Список речень у тексті.

    /**
     * Конструктор тексту.
     * Розбиває текст на речення, використовуючи розділові знаки.
     *
     * @param text рядок, що представляє текст.
     */
    public Text(String text) {
        sentences = new ArrayList<>();
        String[] sentenceArray = text.split("(?<=[.!?])\\s+");
        for (String sentence : sentenceArray) {
            sentences.add(new Sentence(sentence));
        }
    }

    /**
     * Повертає список речень у тексті.
     *
     * @return список речень.
     */
    public List<Sentence> getSentences() {
        return sentences;
    }

    /**
     * Очищує зайві пробіли у тексті.
     */
    public void cleanSpaces() {
        for (Sentence sentence : sentences) {
            String cleanedSentence = sentence.toString().replaceAll("[\\t\\s]+", " ");
            sentence = new Sentence(cleanedSentence); // Створюємо нове речення.
        }
    }

    /**
     * Повертає текст у вигляді рядка.
     *
     * @return текст у вигляді рядка.
     */
    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        for (Sentence sentence : sentences) {
            text.append(sentence).append(" ");
        }
        return text.toString().trim();
    }
}

/**
 * Головний клас для обробки тексту та виконання основної логіки.
 */
public class Program {
    public static void main(String[] args) {
        try {
            processText();
        } catch (Exception e) {
            System.out.println("Виникла помилка: " + e.getMessage());
        }
    }

    /**
     * Основний метод для обробки тексту.
     */
    public static void processText() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(System.in, "UTF-8");

            // Введення тексту
            //System.out.println("Введіть текст для аналізу:");
            //String inputText = scanner.nextLine();
            // Введення тексту
            String inputText = "я люблю небо і зорі? Ти дивишся фільм. Хто це сказав?";

            // Введення довжини слова
            System.out.println("Введіть довжину слова:");
            int wordLength = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера

            // Обробка тексту
            Text text = new Text(inputText);
            text.cleanSpaces();

            // Збір слів із питальних речень
            HashSet<String> uniqueWords = findWordsInQuestions(text, wordLength);

            // Виведення результатів
            System.out.println("Слова довжиною " + wordLength + " у питальних реченнях:");
            if (uniqueWords.isEmpty()) {
                System.out.println("Слів заданої довжини не знайдено.");
            } else {
                for (String word : uniqueWords) {
                    System.out.println(word);
                }
            }

        } catch (Exception e) {
            System.out.println("Помилка вводу або обробки тексту: " + e.getMessage());
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    /**
     * Метод для пошуку слів заданої довжини в питальних реченнях.
     *
     * @param text текст, у якому здійснюється пошук.
     * @param wordLength довжина шуканих слів.
     * @return множина унікальних слів.
     */
    public static HashSet<String> findWordsInQuestions(Text text, int wordLength) {
        HashSet<String> uniqueWords = new HashSet<>();
        for (Sentence sentence : text.getSentences()) {
            if (sentence.endsWithQuestionMark()) {
                for (Word word : sentence.getWords()) {
                    if (word.length() == wordLength) {
                        uniqueWords.add(word.getWord().toLowerCase());
                    }
                }
            }
        }
        return uniqueWords;
    }
}