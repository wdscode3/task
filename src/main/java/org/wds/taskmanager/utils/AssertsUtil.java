package org.wds.taskmanager.utils;

import org.wds.taskmanager.exception.ValidacaoException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;


public class AssertsUtil {

	private AssertsUtil() {
		super();
	}

	public static void assertNull(Object obj, String mensagem) {
		if (isNull(obj)) {
			throw new ValidacaoException(mensagem);
		}
	}

	public static void assertAllNull(Object[] objs, String mensagem) {
		if (isAllNull(objs)) {
			throw new ValidacaoException(mensagem);
		}
	}

	public static void assertEmpty(Object obj, String mensagem) {
		if (isEmpty(obj)) {
			throw new ValidacaoException(mensagem);
		}
	}

	public static void assertNotEmpty(Object obj, String mensagem) {
		if (isNotEmpty(obj)) {
			throw new ValidacaoException(mensagem);
		}
	}

	public static void assertAllEmpty(Object[] objs, String mensagem) {
		if (isAllEmpty(objs)) {
			throw new ValidacaoException(mensagem);
		}
	}

	public static void assertAnyEmpty(Object[] objs, String mensagem) {
		if (anyEmpty(objs)) {
			throw new ValidacaoException(mensagem);
		}
	}

	public static void assertTrue(boolean expression, String mensagem, Object... args) {
		if (expression) {
			throw new ValidacaoException(String.format(mensagem, args));
		}
	}

	public static void assertFalse(boolean expression, String mensagem) {
		if (!expression) {
			throw new ValidacaoException(mensagem);
		}
	}	

	private static boolean isNull(Object obj) {
		return obj == null;
	}

	public static boolean isAllNull(Object... objs) {
		if (isNull(objs))
			return true;

		return Arrays.stream(objs).allMatch(AssertsUtil::isNull);
	}	

	public static boolean isNotNull(Object obj) {
		return !isNull(obj);
	}

	public static boolean isAllNotNull(Object... objs) {
		if (isNull(objs))
			return false;

		return Arrays.stream(objs).allMatch(AssertsUtil::isNotNull);
	}

	public static boolean isEmpty(Object obj) {

		if (isNull(obj)) {
			return true;
		}

		if (obj instanceof String) {
			return isEmpty((String) obj);
		}

		if (obj instanceof Collection<?>) {
			return isEmpty((Collection<?>) obj);
		}

		if (obj instanceof Object[]) {
			return isEmpty((Object[]) obj);
		}

		if (obj instanceof byte[]) {
			return isEmpty((byte[]) obj);
		}

		if (obj instanceof LocalDate) {
			return isNull(obj);
		}

		if (obj instanceof Optional) {
			return isEmpty((Optional<?>) obj);
		}

		return isNull(obj);
	}

	public static boolean isAllEmpty(Object... objs) {
		if (isNull(objs)) 
			return true;

		return Arrays.stream(objs).allMatch(AssertsUtil::isEmpty);
	}

	public static boolean isAnyEmpty(Object... objs) {
		if (isNull(objs))
			return true;

		return Arrays.stream(objs).anyMatch(AssertsUtil::isEmpty);
	}

	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}	

	private static boolean anyEmpty(Object... objs) {
		if (isNull(objs))
			return true;
		
		return Arrays.stream(objs).anyMatch(AssertsUtil::isEmpty);
	}

	public static <T> T requireNonNull(T obj, String message) {
        if (obj == null)
            throw new ValidacaoException(message);
        return obj;
    }

	public static void startDateAfterEndDate(LocalDate start, LocalDate end, String mensagem) {
		if (isNull(start) || isNull(end)){
			return;
		}
		if (start.isAfter(end)) {
			throw new ValidacaoException(mensagem);
		}
	}

	public static void startDateBeforeEndDate(LocalDate start, LocalDate end, String mensagem) {
		if (start.isBefore(end)) {
			throw new ValidacaoException(mensagem);
		}
	}

	private static boolean isEmpty(String str) {
		return isNull(str) || str.trim().isEmpty();
	}

	private static boolean isEmpty(Optional<?> opt) {
		return opt.isEmpty();
	}

	private static boolean isEmpty(Collection<?> lista) {
		if (lista.isEmpty()) 
			return true;

		return lista.stream().allMatch(AssertsUtil::isEmpty);
	}
	

	private static boolean isEmpty(Object[] array) {
		return array.length == 0;
	}

	private static boolean isEmpty(byte[] array) {
		return array.length == 0;
	}

    public static boolean isAllFalse(Boolean... obj) {
        return Arrays.stream(obj).allMatch(b -> !b);
    }

	public static  <T> T requireNonNullElseNull(T t) {
		if (t != null) {
			return t;
		} else {
			return null;
		}
	}

    public static String getStringOrNull(String string) {
		if (isEmpty(string))
			return null;

		return string.trim();
    }

	public static <T> T[] getArrayOrNUll(T[] array) {
		if (isNull(array) || isEmpty(array) || isAllEmpty(array))
			return null;
		return array;
	}
}
