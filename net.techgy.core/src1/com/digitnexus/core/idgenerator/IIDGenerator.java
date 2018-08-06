package com.digitnexus.core.idgenerator;

import java.util.Set;


public interface IIDGenerator {
	
	//String getSingleStringId(String entityId);
	String getStringId(Class<?> entityCls,String client, String...prefixStrs);
    String getStringId(Class<?> entityCls,String client,int idLen, String...prefixStrs);
    
	//Set<String> getStringIds(String entityId,int idNum);
	Set<String> getStringIds(Class<?> entityCls,String client,int idNum,String...prefixStrs);
	Set<String> getStringIds(Class<?> entityCls,String client, int idNum,int idLen,String...prefixStrs);
	
	public String getStringId(Class<?> entityCls);
	
	//String getSingleStringId(String entityId);
	/*String getStringId(Class<?> entityCls,String radix, String...prefixStrs);
    String getStringId(Class<?> entityCls,int idLen, String radix, String...prefixStrs);
    
	//Set<String> getStringIds(String entityId,int idNum);
	Set<String> getStringIds(Class<?> entityCls,int idNum,String radix, String...prefixStrs);
	Set<String> getStringIds(Class<?> entityCls, int idNum,int idLen,String radix, String...prefixStrs);*/
	
	
	//<T extends Number> T getSingleNumId(String entityId);
	<T extends Number> T getNumId(Class<?> entityCls, String client);
	
	//<T extends Number> Set<T> getNumIds(String entityId,int idNum);
	<T extends Number> Set<T> getNumIds(Class<?> entityCls,String client,int idNum);
	
	IDAssignment createNewPrefixIDAssignment(IDAssignment pa);
	
    static final int DEFAULT_INIT_VALUE = 1;
	
	static final String RADIX_8 = "radix8";
	
	static final String RADIX_10 = "radix10";
	
	static final String RADIX_2 = "radix2";
	
	static final String RADIX_16 = "radix16";
	
	static final String TABLE_NAME = "tableName";
	
	public static enum IDType{
		
		STRING("string",128),
		BYTE("byte",8),
		SHORT("short",16),
		INT("int",32),
		LONG("long",64);
		
		private String type;
		private int bitLen;
		
		IDType(String type,int bitLen) {
			this.type=type;
			this.bitLen = bitLen;
		}

		public String getType() {
			return type;
		}

		public int getBitLen() {
			return bitLen;
		}
		
		public static int getBitLenByType(String type) {
			for(IDType t : values()) {
				if(type.equals(t.type)) {
					return t.bitLen;
				}
			}
			throw new IllegalArgumentException("ID type Name is invalid: " + type);
		}
	};
	
	  public static enum IDStatu{
			
		    USING("using"),
		    USED("USED");
		   
			private String statu;
			
			IDStatu(String statu) {
				this.statu=statu;
			}

			public String getStatu() {
				return statu;
			}
			
		};
		
		public static enum IntegerMask32{
			
			MASK_INTEGER32_ZERO0(0XFFFFFFFF),
			MASK_INTEGER32_ZERO1(0X7FFFFFFF),
			MASK_INTEGER32_ZERO2(0X3FFFFFFF),
			MASK_INTEGER32_ZERO3(0X1FFFFFFF),
			MASK_INTEGER32_ZERO4(0X0FFFFFFF),
			MASK_INTEGER32_ZERO5(0X07FFFFFF),
			MASK_INTEGER32_ZERO6(0X03FFFFFF),
			MASK_INTEGER32_ZERO7(0X01FFFFFF),
			MASK_INTEGER32_ZERO8(0X00FFFFFF),
			MASK_INTEGER32_ZERO9(0X007FFFFF),
			MASK_INTEGER32_ZERO1O(0X003FFFFF),
			MASK_INTEGER32_ZERO11(0X001FFFFF),
			MASK_INTEGER32_ZERO12(0X000FFFFF),
			MASK_INTEGER32_ZERO13(0X0007FFFF),
			MASK_INTEGER32_ZERO14(0X0003FFFF),
			MASK_INTEGER32_ZERO15(0X0001FFFF),
			MASK_INTEGER32_ZERO16(0X0000FFFF);
			
			private int maskValue;		
			IntegerMask32(int maskValue) {
				this.maskValue= maskValue;
			}
			
			public static int getMask(int bitLen) {
				if(bitLen >= 0 && bitLen < values().length) {
					return values()[bitLen].maskValue;
				}else {
					throw new ArrayIndexOutOfBoundsException();
				}
			}
			public long getMaskValue() {
				return maskValue;
			}
		};
		
	  public static enum LongMask64{
			
			MASK_LONG64_ZERO0(0XFFFFFFFF),
			MASK_LONG64_ZERO1(0X7FFFFFFF),
			MASK_LONG64_ZERO2(0X3FFFFFFF),
			MASK_LONG64_ZERO3(0X1FFFFFFF),
			MASK_LONG64_ZERO4(0X0FFFFFFF),
			MASK_LONG64_ZERO5(0X07FFFFFF),
			MASK_LONG64_ZERO6(0X03FFFFFF),
			MASK_LONG64_ZERO7(0X01FFFFFF),
			MASK_LONG64_ZERO8(0X00FFFFFF),
			MASK_LONG64_ZERO9(0X007FFFFF),
			MASK_LONG64_ZERO1O(0X003FFFFF),
			MASK_LONG64_ZERO11(0X001FFFFF),
			MASK_LONG64_ZERO12(0X000FFFFF),
			MASK_LONG64_ZERO13(0X0007FFFF),
			MASK_LONG64_ZERO14(0X0003FFFF),
			MASK_LONG64_ZERO15(0X0001FFFF),
			MASK_LONG64_ZERO16(0X0000FFFF);
			
			private long maskValue;		
			LongMask64(long mask) {
				this.maskValue= mask;
			}
			
			public static long getMask(int bitLen) {
				if(bitLen >= 0 && bitLen < values().length) {
					return values()[bitLen].maskValue;
				}else {
					throw new ArrayIndexOutOfBoundsException();
				}
			}

			public long getMaskValue() {
				return maskValue;
			}
			
		};
}
