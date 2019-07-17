package prestoq.code;

import java.util.HashMap;
import java.util.Map;

public final class ProductInfoLineStructure {
    public static final String FIELD_PRODUCT_ID = "ProductId";
    public static final String FIELD_PRODUCT_DESCRIPTION = "ProductDescription";
    public static final String FIELD_REGULAR_SINGULAR_PRICE = "RegularSingularPrice";
    public static final String FIELD_PROMOTIONAL_SINGULAR_PRICE = "PromotionalSingularPrice";
    public static final String FIELD_REGULAR_SPLIT_PRICE = "RegularSplitPrice";
    public static final String FIELD_PROMOTIONAL_SPLIT_PRICE = "PromotionalSplitPrice";
    public static final String FIELD_REGULAR_FOR_X = "RegularForX";
    public static final String FIELD_PROMOTIONAL_FOR_X = "PromotionalForX";
    public static final String FIELD_FLAGS = "Flags";
    public static final String FIELD_PRODUCT_SIZE = "ProductSize";
    
    static final class LineInfo {
	int start;
	int end;
	FieldType type;
	public LineInfo(int start, int end, FieldType type) {
	    this.start = start;
	    this.end = end;
	    this.type = type;
	}
    }

    private static Map<String, LineInfo> fields;

    // this would be a lot cleaner and simpler in Java 8 or 9
    static {
	Map<String, LineInfo> fieldSet = new HashMap<>();
	fieldSet.put(FIELD_PRODUCT_ID, new LineInfo(1, 8, FieldType.NUMBER));
	fieldSet.put(FIELD_PRODUCT_DESCRIPTION, new LineInfo(10, 68, FieldType.STRING));
	fieldSet.put(FIELD_REGULAR_SINGULAR_PRICE, new LineInfo(70, 77, FieldType.CURRENCY));
	fieldSet.put(FIELD_PROMOTIONAL_SINGULAR_PRICE, new LineInfo(79, 86, FieldType.CURRENCY));
	fieldSet.put(FIELD_REGULAR_SPLIT_PRICE, new LineInfo(88, 95, FieldType.CURRENCY));
	fieldSet.put(FIELD_PROMOTIONAL_SPLIT_PRICE, new LineInfo(97, 104, FieldType.CURRENCY));
	fieldSet.put(FIELD_REGULAR_FOR_X, new LineInfo(106, 113, FieldType.NUMBER));
	fieldSet.put(FIELD_PROMOTIONAL_FOR_X, new LineInfo(115, 122, FieldType.NUMBER));
	fieldSet.put(FIELD_FLAGS, new LineInfo(124, 132, FieldType.FLAGS));
	fieldSet.put(FIELD_PRODUCT_SIZE, new LineInfo(134, 142, FieldType.STRING));
	fields = fieldSet;
    }
    
    private ProductInfoLineStructure() { }
    public enum FieldType {
	NUMBER, STRING, CURRENCY, FLAGS;
    }

    private static String extractField(String line, String field) {
	LineInfo info = fields.get(field);
	return line.substring(info.start - 1, info.end);
    }
    
    // if we keep the Type data in line info, it wouldn't hurt
    // to check that in these type conversion functions.
    public static int getFieldAsInt(String line, String field) {
	return Integer.parseInt(extractField(line, field));
    }
    public static String getFieldAsString(String line, String field) {
	return extractField(line, field);
    }
    public static boolean[] getFieldAsFlags(String line, String field) {
	String flagsField = extractField(line, field);
	boolean[] result = new boolean[flagsField.length()];
	for (int i = 0; i < flagsField.length(); i++) {
	    if (flagsField.charAt(i) == 'Y') {
		result[i] = true;
	    } else {
		result[i] = false;
	    }
	}
	return result;
    }
    
}

