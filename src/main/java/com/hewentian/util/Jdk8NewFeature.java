package com.hewentian.util;

import java.lang.annotation.Repeatable;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

interface Formula {
	double calculate(int a, int b);

	default double abs(int n) {
		return Math.abs(n);
	}

	default double sqrt(int n) {
		return Math.sqrt(n);
	}
}

@FunctionalInterface
interface Converter<F, T> {
	T convert(F from);
}

class Person {
	String firstName;
	String lastName;

	Person() {
	}

	Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
}

interface PersonFactory<P extends Person> {
	P create(String firstName, String lastName);
}

class Lambda4 {
	int outerNum;
	static int outerStaticNum;

	void testScopes() {
		Converter<Integer, String> stringConverter1 = (from) -> {
			outerNum = 23;
			return String.valueOf(from);
		};

		Converter<Integer, String> stringConverter2 = (from) -> {
			outerStaticNum = 46;
			return String.valueOf(from);
		};

		System.out.println(stringConverter1.convert(2)); // 2
		System.out.println(stringConverter2.convert(2)); // 2
		System.out.println(outerNum); // 23
		System.out.println(outerStaticNum); // 46
	}
}

@interface Hints {
	Hint[] value();
}

@Repeatable(Hints.class)
@interface Hint {
	String value();
}

// old method
@Hints({ @Hint("hint1"), @Hint("hint2") })
class Person1 {
}

// new method
@Hint("hint1")
@Hint("hint2")
class Person2 {
}

/**
 * 
 * <p>
 * <b>Jdk8NewFeature</b> 是 JDK8 新功能示例类, 参考：http://www.jb51.net/article/48304.htm
 * </p>
 *
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2017年6月16日 上午11:31:51
 * @since JDK 1.8
 *
 */
public class Jdk8NewFeature {
	public static void main(String[] args) {
		System.out.println("1.接口的默认方法，可以定义多于一个默认方法");
		Formula formula = new Formula() {
			@Override
			public double calculate(int a, int b) {
				double c = abs(a) + sqrt(b);
				return c;
			}
		};

		System.out.println(formula.calculate(-2, 16)); // 6.0
		System.out.println(formula.abs(-2)); // 2.0
		System.out.println(formula.sqrt(16)); // 4.0

		System.out.println("\n########################################");
		System.out.println("2.Lambda表达式");
		List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");

		Collections.sort(names, new Comparator<String>() {
			@Override
			public int compare(String a, String b) {
				return a.compareTo(b);
			}
		});

		System.out.println(names);

		names = Arrays.asList("peter", "anna", "mike", "xenia");
		Collections.sort(names, (String a, String b) -> {
			return a.compareTo(b);
		});

		// 对于函数体只有一行代码的，你可以去掉大括号{}以及return关键字
		Collections.sort(names, (String a, String b) -> a.compareTo(b));

		// 你还可以写得更短点, Java编译器可以自动推导出参数类型，所以你可以不用再写一次类型
		Collections.sort(names, (a, b) -> a.compareTo(b));
		System.out.println(names);

		System.out.println("\n########################################");
		System.out.println("3.函数式接口");
		Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
		Integer converted = converter.convert("123");
		System.out.println(converted); // 123

		System.out.println("\n########################################");
		System.out.println("4.方法与构造函数引用");
		converter = Integer::valueOf;
		converted = converter.convert("123");
		System.out.println(converted); // 123

		PersonFactory<Person> personFactory = Person::new;
		Person person = personFactory.create("Peter", "Parker");
		// Peter Parker
		System.out.println(person.firstName + " " + person.lastName);

		System.out.println("\n########################################");
		System.out.println("6.访问局部变量");
		int num1 = 1;
		final int num2 = 2;
		Converter<Integer, String> stringConverter = (from) -> String.valueOf(from + num1 + num2);
		System.out.println(stringConverter.convert(2)); // 5

		System.out.println("\n########################################");
		System.out.println("7.访问对象字段与静态变量");
		Lambda4 lambda4 = new Lambda4();
		lambda4.testScopes();

		System.out.println("\n########################################");
		System.out.println("8.访问接口的默认方法");
		// 还记得第一节中的formula例子么，接口Formula定义了一个默认方法sqrt可以直接被formula的实例包括匿名对象访问到，
		// 但是在lambda表达式中这个是不行的。Lambda表达式中是无法访问到默认方法的，以下代码将无法编译：
		// Formula formula = (a) -> sqrt( a * 100);

		System.out.println("\n########################################");
		System.out.println("Predicate接口: 只有一个参数，返回boolean类型。该接口包含多种默认方法来将Predicate组合成其他复杂的逻辑（比如：与，或，非）");
		Predicate<String> predicate = (s) -> s.length() > 0;
		System.out.println(predicate.test("foo")); // true
		System.out.println(predicate.negate().test("foo")); // false

		Predicate<String> nonNull = Objects::nonNull;
		Predicate<String> isNull = Objects::isNull;
		System.out.println(nonNull.test("abc")); // true
		System.out.println(isNull.test(null)); // true

		Predicate<String> isEmpty = String::isEmpty;
		Predicate<String> notEmpty = isEmpty.negate();
		System.out.println(isEmpty.test("")); // true
		System.out.println(notEmpty.test("")); // false

		System.out.println("########## and");
		System.out.println(nonNull.and(predicate).test(""));
		System.out.println(nonNull.and(predicate).test(null));
		System.out.println(nonNull.and(predicate).test("ab"));

		System.out.println("########## or");
		System.out.println(nonNull.or(predicate).test(""));
		System.out.println(isEmpty.or(predicate).test(""));

		System.out.println("\n########################################");
		System.out.println("Function接口: 有一个参数并且返回一个结果，并附带了一些可以和其他函数组合的默认方法（compose, andThen）");
		Function<String, Integer> toInteger = Integer::valueOf;
		Function<Integer, String> toString = String::valueOf;

		// this -> after
		Function<String, String> andThenTest = toInteger.andThen(toString);
		System.out.println(andThenTest.apply("123")); // 123

		// before -> this
		Function<String, String> composeTest = toString.compose(toInteger);
		System.out.println(composeTest.apply("123")); // 123

		System.out.println("\n########################################");
		System.out.println("Supplier接口: 返回一个任意范型的值，和Function接口不同的是该接口没有任何参数");
		Supplier<Person> personSupplier = Person::new;
		Person person2 = personSupplier.get();
		System.out.println(person2.firstName + " " + person2.lastName);

		System.out.println("\n########################################");
		System.out.println("Consumer接口: 表示执行在单个参数上的操作");
		Consumer<Person> greeter = (p) -> System.out.println("Hello, " + p.firstName);
		greeter.accept(new Person("Luke", "Skywalker"));

		System.out.println("\n########################################");
		System.out.println("Comparator接口: 表示执行在单个参数上的操作");
		Comparator<Person> comparator = (p1, p2) -> p1.firstName.compareTo(p2.firstName);
		Person p1 = new Person("John", "Doe");
		Person p2 = new Person("Alice", "Wonderland");
		System.out.println(comparator.compare(p1, p2)); // 9
		System.out.println(comparator.reversed().compare(p1, p2)); // -9

		System.out.println("\n########################################");
		System.out.println("Optional接口: 表示执行在单个参数上的操作");
		Optional<String> optional = Optional.of("bam");
		System.out.println(optional.isPresent()); // true
		System.out.println(optional.get()); // bam
		System.out.println(optional.orElse("fallback")); // bam
		optional.ifPresent((s) -> System.out.println(s.charAt(0))); // b

		System.out.println("\n########################################");
		System.out.println("Stream接口: java.util.Stream 表示能应用在一组元素上一次执行的操作序列。"
				+ "Stream 操作分为中间操作或者最终操作两种，最终操作返回一特定类型的计算结果，而中间" + "操作返回Stream本身，这样你就可以将多个操作依次串起来。Stream 的创建需"
				+ "要指定一个数据源，比如 java.util.Collection的子类，List或者Set， Map不支持。" + "Stream的操作可以串行执行或者并行执行。");

		List<String> stringCollection = new ArrayList<String>();
		stringCollection.add("ddd2");
		stringCollection.add("aaa2");
		stringCollection.add("bbb1");
		stringCollection.add("aaa1");
		stringCollection.add("bbb3");
		stringCollection.add("ccc");
		stringCollection.add("bbb2");
		stringCollection.add("ddd1");

		System.out.println("########## filter");
		stringCollection.stream().filter((s) -> s.startsWith("a")).forEach(System.out::println);
		// aaa2
		// aaa1

		System.out.println("########## sort");
		stringCollection.stream().sorted().forEach(System.out::println);
		// aaa1
		// aaa2
		// bbb1
		// bbb2
		// bbb3
		// ccc
		// ddd1
		// ddd2

		// filter, sorted, map不会影响原有的数据源
		System.out.println("########## map");
		stringCollection.stream().map(String::toUpperCase).sorted((a, b) -> b.compareTo(a)).forEach(System.out::println);
		// DDD2
		// DDD1
		// CCC
		// BBB3
		// BBB2
		// BBB1
		// AAA2
		// AAA1

		System.out.println("########## match");
		System.out.println(stringCollection.stream().anyMatch((s) -> s.startsWith("a"))); // true
		System.out.println(stringCollection.stream().allMatch((s) -> s.startsWith("a"))); // false
		System.out.println(stringCollection.stream().noneMatch((s) -> s.startsWith("z"))); // true

		System.out.println("########## count");
		System.out.println(stringCollection.stream().filter((s) -> s.startsWith("b")).count()); // 3

		System.out.println("########## reduce");
		Optional<String> reduced = stringCollection.stream().sorted().reduce((s1, s2) -> s1 + "#" + s2);
		reduced.ifPresent(System.out::println); // aaa1#aaa2#bbb1#bbb2#bbb3#ccc#ddd1#ddd2

		System.out.println("########## 并行Streams");
		int max = 1000000;
		List<String> values = new ArrayList<>(max);
		for (int i = 0; i < max; i++) {
			UUID uuid = UUID.randomUUID();
			values.add(uuid.toString());
		}

		long t0 = System.nanoTime();
		// long count = values.stream().sorted().count(); // 串行：692
		long count = values.parallelStream().sorted().count(); // 并行：355
		System.out.println(count);
		long t1 = System.nanoTime();
		long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
		System.out.println(String.format("sort took: %d ms", millis));

		System.out.println("\n########################################");
		System.out.println("Map 新功能");
		Map<Integer, String> map = new HashMap<Integer, String>();
		for (int i = 0; i < 10; i++) {
			map.putIfAbsent(i, "val" + i);
		}

		map.forEach((id, val) -> System.out.println(id + " -> " + val));
		// 0 -> val0
		// 1 -> val1
		// 2 -> val2
		// 3 -> val3
		// 4 -> val4
		// 5 -> val5
		// 6 -> val6
		// 7 -> val7
		// 8 -> val8
		// 9 -> val9

		map.computeIfPresent(3, (num, val) -> val + num);
		System.out.println(map.get(3)); // val33

		map.computeIfPresent(9, (num, val) -> null);
		System.out.println(map.containsKey(9)); // false

		map.computeIfAbsent(23, num -> "val" + num);
		System.out.println(map.get(23));

		map.computeIfAbsent(3, num -> "bam");
		System.out.println(map.get(3)); // val33

		map.remove(3, "val3");
		System.out.println(map.get(3)); // val33

		map.remove(3, "val33");
		System.out.println(map.get(3)); // null

		System.out.println(map.getOrDefault(42, "not found")); // not found

		map.merge(9, "val9", (value, newValue) -> value.concat(newValue));
		System.out.println(map.get(9)); // val9

		map.merge(9, "concat", (value, newValue) -> value.concat(newValue));
		System.out.println(map.get(9)); // val9concat

		System.out.println("\n########################################");
		System.out.println("9.Date API");
		Clock clock = Clock.systemDefaultZone();
		long millis2 = clock.millis();
		System.out.println(millis2);

		Instant instant = clock.instant();
		Date legacyDate = Date.from(instant);
		System.out.println(legacyDate);

		System.out.println(ZoneId.getAvailableZoneIds());
		ZoneId zone1 = ZoneId.of("Europe/Berlin");
		ZoneId zone2 = ZoneId.of("Brazil/East");
		System.out.println(zone1.getRules()); // ZoneRules[currentStandardOffset=+01:00]
		System.out.println(zone2.getRules()); // ZoneRules[currentStandardOffset=-03:00]

		System.out.println("\nLocalTime");
		LocalTime now1 = LocalTime.now(zone1);
		LocalTime now2 = LocalTime.now(zone2);
		System.out.println(now1);
		System.out.println(now2);
		System.out.println(now1.isBefore(now2)); // false

		long hourBetween = ChronoUnit.HOURS.between(now1, now2);
		long minuteBetween = ChronoUnit.MINUTES.between(now1, now2);
		System.out.println(hourBetween); // -5
		System.out.println(minuteBetween); // -300

		LocalTime late = LocalTime.of(23, 59, 59);
		System.out.println(late); // 23:59:59

		DateTimeFormatter germanFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.GERMAN);
		LocalTime leetTime = LocalTime.parse("13:37", germanFormatter);
		System.out.println(leetTime); // 13:37

		System.out.println("\nLocalDate");
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
		LocalDate yesterday = tomorrow.minusDays(2);
		System.out.println(yesterday); // 2017-06-18

		LocalDate independenceDay = LocalDate.of(2014, Month.JULY, 4);
		DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();
		System.out.println(dayOfWeek); // FRIDAY

		DateTimeFormatter germanFormatter2 = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(
				Locale.GERMAN);
		LocalDate xmas = LocalDate.parse("24.12.2014", germanFormatter2);
		System.out.println(xmas); // 2014-12-24

		System.out.println("\nLocalDateTime");
		LocalDateTime sylvester = LocalDateTime.of(2014, Month.DECEMBER, 31, 23, 59, 59);
		DayOfWeek dayOfWeek2 = sylvester.getDayOfWeek();
		System.out.println(dayOfWeek2); // WEDNESDAY

		Month month = sylvester.getMonth();
		System.out.println(month); // DECEMBER

		long minuteOfDay = sylvester.getLong(ChronoField.MINUTE_OF_DAY);
		System.out.println(minuteOfDay); // 1439

		Instant instant2 = sylvester.atZone(ZoneId.systemDefault()).toInstant();
		Date legacyDate2 = Date.from(instant2);
		System.out.println(legacyDate2); // Wed Dec 31 23:59:59 CST 2014

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy - HH:mm", Locale.ENGLISH);
		LocalDateTime localDateTime = LocalDateTime.parse("Nov 03, 2014 - 07:13", formatter);
		String string = formatter.format(localDateTime);
		System.out.println(string); // Nov 03, 2014 - 07:13

		System.out.println("\n########################################");
		System.out.println("10.Annotation");
		Hint hint1 = Person1.class.getAnnotation(Hint.class);
		System.out.println(hint1); // null

		Hints hints1 = Person1.class.getAnnotation(Hints.class);
		System.out.println(hints1); // null

		Hint[] hints12 = Person1.class.getAnnotationsByType(Hint.class);
		System.out.println(hints12.length); // 0

		Hints[] hints13 = Person1.class.getAnnotationsByType(Hints.class);
		System.out.println(hints13.length); // 0

		Hint hint2 = Person2.class.getAnnotation(Hint.class);
		System.out.println(hint2); // null

		Hints hints2 = Person2.class.getAnnotation(Hints.class);
		System.out.println(hints2); // null

		Hint[] hints22 = Person2.class.getAnnotationsByType(Hint.class);
		System.out.println(hints22.length); // 0

		Hints[] hints23 = Person2.class.getAnnotationsByType(Hints.class);
		System.out.println(hints23.length); // 0

		// 等续...
	}
}
