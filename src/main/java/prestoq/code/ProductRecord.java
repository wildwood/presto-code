package prestoq.code;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public final class ProductRecord {
    public static final double TAX_RATE = .07775;

    private int productId;
    private String productDescription;
    private int regularSingularPrice;
    private int promotionalSingularPrice;
    private int regularSplitPrice;
    private int promotionalSplitPrice;
    private int regularForX;
    private int promotionalForX;
    private boolean[] flags;
    private String productSize;

    private ProductRecord(Builder b) {
	productId = b.getProductId();
	productDescription = b.getProductDescription();
	regularSingularPrice = b.getRegularSingularPrice();
	promotionalSingularPrice = b.getPromotionalSingularPrice();
	regularSplitPrice = b.getRegularSplitPrice();
	promotionalSplitPrice = b.getPromotionalSplitPrice();
	regularForX = b.getRegularForX();
	promotionalForX = b.getPromotionalForX();
	flags = b.getFlags();
	productSize = b.getProductSize();
    }

    private ProductRecord() {

    }

    public int getProductId() {
	return productId;
    }
    public String getProductDescription() {
	return productDescription;
    }

    private String displayCentsAsDollars(int cents) {
	double dollars = (double) cents / 100.0;
	return String.format("$%.2f", dollars);
    }
    private String displayCentsAsCalculatedDollars(double cents) {
	double dollars = (double) cents / 100.0;
	DecimalFormat df = new DecimalFormat("$#.####");
	df.setRoundingMode(RoundingMode.HALF_DOWN);
	return df.format(dollars);
    }
    
    public String getRegularDisplayPrice() {
	if (regularSingularPrice > 0) {
	    return displayCentsAsDollars(regularSingularPrice);
	} else {
	    return displayCentsAsDollars(regularSplitPrice) + " for " + regularForX;
	}
    }
    public String getRegularCalculatedPrice() {
	if (regularSingularPrice > 0) {
	    return displayCentsAsCalculatedDollars(regularSingularPrice);
	} else {
	    return displayCentsAsCalculatedDollars((double) regularSplitPrice / regularForX);
	}
    }
    public String getPromotionalDisplayPrice() {
	if (promotionalSingularPrice > 0) {
	    return displayCentsAsDollars(promotionalSingularPrice);
	} else if (promotionalSplitPrice > 0) {
	    return displayCentsAsDollars(promotionalSplitPrice) + " for " + promotionalForX;
	} else {
	    // may need a different null value, or return an Optional<String> for promo prices
	    return "";
	}
    }
    public String getPromotionalCalculatedPrice() {
	if (promotionalSingularPrice > 0) {
	    return displayCentsAsCalculatedDollars(promotionalSingularPrice);
	} else if (promotionalSplitPrice > 0) {
	    return displayCentsAsCalculatedDollars((double) promotionalSplitPrice / promotionalForX);
	} else {
	    // may need a different null value, or return an Optional<String> for promo prices
	    return "";
	}
    }

    public String getUnits() {
	if (flags[2]) {
	    return "pound";
	} else {
	    return "each";
	}
    }

    public String getProductSize() {
	return productSize;
    }

    public double getTaxRate() {
	if (flags[4]) {
	    return TAX_RATE;
	} else {
	    return 0.0;
	}
    }

    public String toString() {
	StringBuffer sb = new StringBuffer();
	sb.append(getProductId());
	sb.append("\t");
	sb.append(getProductDescription());
	sb.append("\t");
	sb.append(getRegularDisplayPrice());
	sb.append("\t");
	sb.append(getRegularCalculatedPrice());
	sb.append("\t");
	sb.append(getPromotionalDisplayPrice());
	sb.append("\t");
	sb.append(getPromotionalCalculatedPrice());
	sb.append("\t");
	sb.append(getPromotionalDisplayPrice());
	sb.append("\t");
	sb.append(getUnits());
	sb.append("\t");
	sb.append(getProductSize());
	sb.append("\t");
	sb.append(getTaxRate());
	return sb.toString();
    }
    
    
    public static class Builder {
	private int productId;
	private String productDescription;
	private int regularSingularPrice;
	private int promotionalSingularPrice;
	private int regularSplitPrice;
	private int promotionalSplitPrice;
	private int regularForX;
	private int promotionalForX;
	private boolean[] flags;
	private String productSize;

	Builder() { }
	
	public Builder(String line) {
	    setProductId(ProductInfoLineStructure.getFieldAsInt(line, ProductInfoLineStructure.FIELD_PRODUCT_ID));
	    setProductDescription(ProductInfoLineStructure.getFieldAsString(line, ProductInfoLineStructure.FIELD_PRODUCT_DESCRIPTION));
	    setRegularSingularPrice(ProductInfoLineStructure.getFieldAsInt(line, ProductInfoLineStructure.FIELD_REGULAR_SINGULAR_PRICE));
	    setPromotionalSingularPrice(ProductInfoLineStructure.getFieldAsInt(line, ProductInfoLineStructure.FIELD_PROMOTIONAL_SINGULAR_PRICE));
	    setRegularSplitPrice(ProductInfoLineStructure.getFieldAsInt(line, ProductInfoLineStructure.FIELD_REGULAR_SPLIT_PRICE));
	    setPromotionalSplitPrice(ProductInfoLineStructure.getFieldAsInt(line, ProductInfoLineStructure.FIELD_PROMOTIONAL_SPLIT_PRICE));
	    setRegularForX(ProductInfoLineStructure.getFieldAsInt(line, ProductInfoLineStructure.FIELD_REGULAR_FOR_X));
	    setPromotionalForX(ProductInfoLineStructure.getFieldAsInt(line, ProductInfoLineStructure.FIELD_PROMOTIONAL_FOR_X));
	    setFlags(ProductInfoLineStructure.getFieldAsFlags(line, ProductInfoLineStructure.FIELD_FLAGS));
	    setProductSize(ProductInfoLineStructure.getFieldAsString(line, ProductInfoLineStructure.FIELD_PRODUCT_SIZE));
	}

	public final Builder setProductId(int id) {
	    this.productId = id;
	    return this;
	}
	public final int getProductId() {
	    return this.productId;
	}
	
	public final Builder setProductDescription(String description) {
	    this.productDescription = description;
	    return this;
	}
	public final String getProductDescription() {
	    return this.productDescription;
	}
	
	public final Builder setRegularSingularPrice(int price) {
	    this.regularSingularPrice = price;
	    return this;
	}
	public final int getRegularSingularPrice() {
	    return this.regularSingularPrice;
	}
	
	public final Builder setPromotionalSingularPrice(int price) {
	    this.promotionalSingularPrice = price;
	    return this;
	}
	public final int getPromotionalSingularPrice() {
	    return this.promotionalSingularPrice;
	}

	public final Builder setRegularSplitPrice(int price) {
	    this.regularSplitPrice = price;
	    return this;
	}
	public final int getRegularSplitPrice() {
	    return this.regularSplitPrice;
	}
	
	public final Builder setPromotionalSplitPrice(int price) {
	    this.promotionalSplitPrice = price;
	    return this;
	}
	public final int getPromotionalSplitPrice() {
	    return this.promotionalSplitPrice;
	}
	
	public final Builder setRegularForX(int forX) {
	    this.regularForX = forX;
	    return this;
	}
	public final int getRegularForX() {
	    return this.regularForX;
	}
	
	public final Builder setPromotionalForX(int forX) {
	    this.promotionalForX = forX;
	    return this;
	}
	public final int getPromotionalForX() {
	    return this.promotionalForX;
	}
	
	public final Builder setFlags(boolean[] flags) {
	    this.flags = flags;
	    return this;
	}
	public final boolean[] getFlags() {
	    return this.flags;
	}
	
	public final Builder setProductSize(String size) {
	    this.productSize = size;
	    return this;
	}
	public final String getProductSize() {
	    return this.productSize;
	}
	
	public ProductRecord build() {
	    System.out.println(toString());
	    return new ProductRecord(this);
	}

	public String toString() {
	    return productId + "\t" + productDescription;
	}
    }
}
