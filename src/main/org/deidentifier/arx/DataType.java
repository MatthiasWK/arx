/*
 * ARX: Efficient, Stable and Optimal Data Anonymization
 * Copyright (C) 2012 - 2014 Florian Kohlmayer, Fabian Prasser
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.deidentifier.arx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.deidentifier.arx.aggregates.AggregateFunction;
import org.deidentifier.arx.aggregates.AggregateFunction.AggregateFunctionBuilder;

/**
 * This class provides access to the data types supported by the ARX framework
 * 
 * @author Fabian Prasser
 * @author Florian Kohlmayer
 */
public abstract class DataType<T> implements Serializable, Comparator<T> {
    
    /**
     * Base class for date/time types
     * @author Fabian Prasser
     */
	public static class ARXDate extends DataType<Date> implements DataTypeWithFormat, DataTypeWithRatioScale<Date> {
	
        private static final long serialVersionUID = -1658470914184442833L;

        /** The description of the data type*/
        private static final DataTypeDescription<Date> description = new DataTypeDescription<Date>(Date.class, "Date/Time",  true, listDateFormats()){
            /**
             * 
             */
            private static final long serialVersionUID = -1723392257250720908L;
            @Override public DataType<Date> newInstance() { return DATE; }
            @Override public DataType<Date> newInstance(String format) {return createDate(format);}
        };
        
        private SimpleDateFormat format;

        private String           string;
        /**
         * Create a data with a "dd.MM.yyyy" format string
         * for <code>SimpleDateFormat</code>.
         * @see <a href="http://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html">SimpleDateFormat</a>
         */
        private ARXDate() {
            this("Default");
        }

        /**
         * Create a date with a format string. Format strings must be valid formats
         * for <code>SimpleDateFormat</code>.
         * @param format
         * @see <a href="http://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html">SimpleDateFormat</a>
         */
        private ARXDate(final String formatString) {
            if (format == null || format.equals("Default")) {
                string = "dd.MM.yyyy";
                format = new SimpleDateFormat(string);
            } else {
                this.format = new SimpleDateFormat(formatString);
                this.string = formatString;
            }
        }

        @Override
        public Date add(Date augend, Date addend) {
            long d1 = augend.getTime();
            long d2 = addend.getTime();
            return new Date(d1 + d2);
        }

        @Override
        public DataType<Date> clone() {
            return new ARXDate(string);
        }

        @Override
        public int compare(Date t1, Date t2) {
            return t1.compareTo(t2);
        }
        
        @Override
        public int compare(final String s1, final String s2) throws ParseException {
            try {
                return format.parse(s1).compareTo(format.parse(s2));
            } catch (Exception e){
                throw new IllegalArgumentException("Invalid value", e);
            }
        }

        @Override
        public Date divide(Date dividend, Date divisor) {
            long d1 = dividend.getTime();
            long d2 = divisor.getTime();
            return new Date(d1 / d2);
        }

        @Override
        public String divide(String dividend, String divisor) {
            long d1 = parse(dividend).getTime();
            long d2 = parse(divisor).getTime();
            return format(new Date(d1 / d2));
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) { return true; }
            if (obj == null) { return false; }
            if (getClass() != obj.getClass()) { return false; }
            final ARXDate other = (ARXDate) obj;
            if (string == null) { if (other.string != null) { return false; }
            } else if (!string.equals(other.string)) { return false; }
            return true;
        }
        
        @Override
        public String format(Date s){
        	return format.format(s);
        }

        @Override
        public DataTypeDescription<Date> getDescription(){
            return description;
        }

        @Override
        public String getFormat() {
            return string;
        }
        
        @Override
        public Date getMaximum() {
            return new Date(Long.MAX_VALUE);
        }

        @Override
        public Date getMinimum() {
            return new Date(Long.MIN_VALUE);
        }

        @Override
        public int hashCode() {
            if (string==null) return 0;
            else return string.hashCode();
        }

        @Override
        public boolean isValid(String s) {
            try {
                parse(s);
                return true;
            } catch (Exception e){
                return false;
            }
        }

        @Override
        public Date multiply(Date multiplicand, Date multiplicator) {
            long d1 = multiplicand.getTime();
            long d2 = multiplicator.getTime();
            return new Date(d1 * d2);
        }

        @Override
        public Date multiply(Date multiplicand, double multiplicator) {
            long d1 = multiplicand.getTime();
            return new Date((long)((double)d1 * multiplicator));
        }

        @Override
        public Date multiply(Date multiplicand, int multiplicator) {
            long d1 = multiplicand.getTime();
            return new Date(d1 * multiplicator);
        }

        @Override
        public String multiply(String multiplicand, String multiplicator) {
            long d1 = parse(multiplicand).getTime();
            long d2 = parse(multiplicator).getTime();
            return format(new Date(d1 * d2));
        }

        @Override
        public Date parse(String s) {
        	try {
				return format.parse(s);
        	} catch (Exception e) {
                throw new IllegalArgumentException(e.getMessage() + ": " + s, e);
            }
        }
        
        @Override
        public double ratio(Date dividend, Date divisor) {
            long d1 = dividend.getTime();
            long d2 = divisor.getTime();
            return (double)d1 / (double)d2;
        }

        @Override
        public Date subtract(Date minuend, Date subtrahend) {
            long d1 = minuend.getTime();
            long d2 = subtrahend.getTime();
            return new Date(d1 - d2);
        }

        @Override
        public String toString() {
            return "Date(" + string + ")";
        }
        
        
    }

    /**
	 * Base class for numeric types
	 * @author Fabian Prasser
	 */
    public static class ARXDecimal extends DataType<Double> implements DataTypeWithFormat, DataTypeWithRatioScale<Double> {
  
        private static final long serialVersionUID = 7293446977526103610L;

        /** The description of the data type*/
        private static final DataTypeDescription<Double> description = new DataTypeDescription<Double>(Double.class, "Decimal", true, listDecimalFormats()){
            /**
             * 
             */
            private static final long serialVersionUID = -3549629178680030868L;
            @Override public DataType<Double> newInstance() { return DECIMAL; }
            @Override public DataType<Double> newInstance(String format) {return createDecimal(format);}
        };
        
        private DecimalFormat format;
        
        private String        string;
        
        private ARXDecimal(){
            this("Default");
        }
        
        /**
         * Create a numeric with a format string. Format strings must be valid formats
         * for <code>DecimalFormat</code>.
         * @param format
         * @see <a href="http://docs.oracle.com/javase/7/docs/api/java/text/DecimalFormat.html">DecimalFormat</a>
         */
        private ARXDecimal(String format){
            if (format == null || format.equals("Default")){
                this.format = null;
                this.string = null;
            } else {
                this.format = new DecimalFormat(format);
                this.string = format;
            }
        }
        
        @Override
        public Double add(Double augend, Double addend) {
            return augend + addend;
        }
        
        @Override
        public DataType<Double> clone() {
            return this;
        }

        @Override
        public int compare(Double t1, Double t2) {
            return t1.compareTo(t2);
        }

        @Override
        public int compare(final String s1, final String s2) throws NumberFormatException {
            try {
                return parse(s1).compareTo(parse(s2));
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid value: '"+s1+"' or '"+s2+"'", e);
            }
        }

        @Override
        public Double divide(Double dividend, Double divisor) {
            return dividend / divisor;
        }

        @Override
        public String divide(String dividend, String divisor) {
            Double d1 = parse(dividend);
            Double d2 = parse(divisor);
            return format(d1 / d2);
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) { return true; }
            if (obj == null) { return false; }
            if (getClass() != obj.getClass()) { return false; }
            final ARXDecimal other = (ARXDecimal) obj;
            if (string == null) { if (other.string != null) { return false; }
            } else if (!string.equals(other.string)) { return false; }
            return true;
        }

        @Override
        public String format(Double s){
            if (format==null){
                return String.valueOf(s);
            } else {
                return format.format(s);
            }
        }

        @Override
        public DataTypeDescription<Double> getDescription(){
            return description;
        }

        @Override
        public String getFormat() {
            return string;
        }

        @Override
        public Double getMaximum() {
            return Double.MAX_VALUE;
        }

        @Override
        public Double getMinimum() {
            return Double.MIN_VALUE;
        }

        @Override
        public int hashCode() {
            if (string==null) return 0;
            else return string.hashCode();
        }

        @Override
        public boolean isValid(String s) {
            try {
                parse(s);
                return true;
            } catch (Exception e){
                return false;
            }
        }

        @Override
        public Double multiply(Double multiplicand, double multiplicator) {
            return multiplicand * multiplicator;
        }

        @Override
        public Double multiply(Double multiplicand, Double multiplicator) {
            return multiplicand * multiplicator;
        }

        @Override
        public Double multiply(Double multiplicand, int multiplicator) {
            return multiplicand * multiplicator;
        }

        @Override
        public String multiply(String multiplicand, String multiplicator) {
            Double d1 = parse(multiplicand);
            Double d2 = parse(multiplicator);
            return format(d1 * d2);
        }

        @Override
        public Double parse(String s) {
            try {
                if (format == null) {
                    return Double.valueOf(s);
                } else {
                    return format.parse(s).doubleValue();
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(e.getMessage() + ": " + s, e);
            }
        }

        @Override
        public double ratio(Double dividend, Double divisor) {
            return dividend / divisor;
        }

        @Override
        public Double subtract(Double minuend, Double subtrahend) {
            return minuend - subtrahend;
        }

        @Override
        public String toString() {
            return "Decimal";
        }
    }

    /**
     * Base class for numeric types
     * @author Fabian Prasser
     */
    public static class ARXInteger extends DataType<Long> implements DataTypeWithFormat, DataTypeWithRatioScale<Long>  {
        
        private static final long serialVersionUID = -631163546929231044L;

        /** The description of the data type*/
        private static final DataTypeDescription<Long> description = new DataTypeDescription<Long>(Long.class, "Integer", false, new ArrayList<String>()){
            /**
             * 
             */
            private static final long serialVersionUID = -4498725217659811835L;
            @Override public DataType<Long> newInstance() { return INTEGER; }
            @Override public DataType<Long> newInstance(String format) {return createInteger(format);}
        };
        
        private DecimalFormat format;
        
        private String        string;
        
        private ARXInteger(){
            this("Default");
        }
        
        /**
         * Create a numeric with a format string. Format strings must be valid formats
         * for <code>DecimalFormat</code>.
         * @param format
         * @see <a href="http://docs.oracle.com/javase/7/docs/api/java/text/DecimalFormat.html">DecimalFormat</a>
         */
        private ARXInteger(String format){
            if (format == null || format.equals("Default")){
                this.format = null;
                this.string = null;   
            } else {
                this.format = new DecimalFormat(format);
                this.string = format;
            }
        }
        
        @Override
        public Long add(Long augend, Long addend) {
            return augend + addend;
        }
        
        @Override
        public DataType<Long> clone() {
            return this;
        }

        @Override
        public int compare(Long t1, Long t2) {
            return t1.compareTo(t2);
        }

        @Override
        public int compare(final String s1, final String s2) throws NumberFormatException {
            try {
                return parse(s1).compareTo(parse(s2));
            } catch (Exception e) {
                throw new IllegalArgumentException(e.getMessage() + ": " + s1 +" or " + s2, e);
            }
        }

        @Override
        public Long divide(Long dividend, Long divisor) {
            return (long)Math.round((double)dividend / (double)divisor);
        }

        @Override
        public String divide(String dividend, String divisor) {
            Long d1 = parse(dividend);
            Long d2 = parse(divisor);
            return format(d1 / d2);
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) { return true; }
            if (obj == null) { return false; }
            if (getClass() != obj.getClass()) { return false; }
            final ARXInteger other = (ARXInteger) obj;
            if (string == null) { if (other.string != null) { return false; }
            } else if (!string.equals(other.string)) { return false; }
            return true;
        }
        
        @Override
        public String format(Long s){
            if (format==null){
                return String.valueOf(s);
            } else {
                return format.format(s);
            }
        }

        @Override
        public DataTypeDescription<Long> getDescription(){
            return description;
        }
        
        @Override
        public String getFormat() {
            return string;
        }

        @Override
        public Long getMaximum() {
            return Long.MAX_VALUE;
        }

        @Override
        public Long getMinimum() {
            return Long.MIN_VALUE;
        }

        @Override
        public int hashCode() {
            if (string==null) return 0;
            else return string.hashCode();
        }

        @Override
        public boolean isValid(String s) {
            try {
                parse(s);
                return true;
            } catch (Exception e){
                return false;
            }
        }

        @Override
        public Long multiply(Long multiplicand, double multiplicator) {
            return (long)((double)multiplicand * multiplicator);
        }

        @Override
        public Long multiply(Long multiplicand, int multiplicator) {
            return multiplicand * multiplicator;
        }

        @Override
        public Long multiply(Long multiplicand, Long multiplicator) {
            return (long)Math.round((double)multiplicand * (double)multiplicator);
        }
        
        @Override
        public String multiply(String multiplicand, String multiplicator) {
            Long d1 = parse(multiplicand);
            Long d2 = parse(multiplicator);
            return format(d1 * d2);
        }

        @Override
        public Long parse(String s) {
            try {
                if (format == null) {
                    return Long.valueOf(s);
                } else {
                    return format.parse(s).longValue();
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(e.getMessage() + ": " + s, e);
            }
        }
        @Override
        public double ratio(Long dividend, Long divisor) {
            return (double)dividend / (double)divisor;
        }

        @Override
        public Long subtract(Long minuend, Long subtrahend) {
            return minuend - subtrahend;
        }

        @Override
        public String toString() {
            return "Integer";
        }
    }
    
    /**
     * Base class for ordered string types
     * @author Fabian Prasser
     */
    public static class ARXOrderedString extends DataType<String> implements DataTypeWithFormat {
        
        private static final long serialVersionUID = -830897705078418835L;
        
        private Map<String, Integer> order;
        
        /** The description of the data type*/
        private static final DataTypeDescription<String> description = new DataTypeDescription<String>(String.class, "OrderedString", true, new ArrayList<String>()){
            
            private static final long serialVersionUID = -6300869938311742699L;
            @Override public DataType<String> newInstance() { return ORDERED_STRING; }
            @Override public DataType<String> newInstance(String format) {return createOrderedString(format);}
        };
        
        /**
         * Creates a new instance
         */
        private ARXOrderedString(){
            this("Default");
        }

        /**
         * Creates a new instance
         * @param format Ordered list of strings
         */
        private ARXOrderedString(List<String> format){
            if (format.size()==0) {
                this.order = null;
            } else {
                this.order = new HashMap<String, Integer>(); 
                for (int i=0; i< format.size(); i++){
                    if (this.order.put(format.get(i), i) != null) {
                        throw new IllegalArgumentException("Duplicate value '"+format.get(i)+"'");
                    }
                }
            }
        }

        /**
         * Creates a new instance
         * @param format Ordered list of string separated by line feeds
         */
        private ARXOrderedString(String format){
            if (format==null || format.equals("Default") || format.equals("")) {
                this.order = null;
            } else {
                try {
                    this.order = new HashMap<String, Integer>(); 
                    BufferedReader reader = new BufferedReader(new StringReader(format));
                    int index = 0;
                    String line = reader.readLine();
                    while (line != null) {
                        if (this.order.put(line, index) != null) {
                            throw new IllegalArgumentException("Duplicate value '"+line+"'");
                        }
                        line = reader.readLine();
                        index++;
                    }
                    reader.close();
                } catch (IOException e) {
                    throw new IllegalArgumentException("Error reading input data");
                }
            }
        }
        
        /**
         * Creates a new instance
         * @param format Ordered list of strings
         */
        private ARXOrderedString(String[] format){
            if (format.length == 0) {
                this.order = null;
            } else {
                this.order = new HashMap<String, Integer>(); 
                for (int i=0; i< format.length; i++){
                    if (this.order.put(format[i], i) != null) {
                        throw new IllegalArgumentException("Duplicate value '"+format[i]+"'");
                    }
                }
            }
        }
        
        @Override
        public DataType<String> clone() {
            return this;
        }
        
        @Override
        public int compare(final String s1, final String s2) {
            if (order != null){
                try {
                    return order.get(s1).compareTo(order.get(s2));
                } catch (Exception e) {
                    throw new IllegalArgumentException("Invalid value", e);
                }
            } else {
                return s1.compareTo(s2);
            }
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) { return true; }
            if (obj == null) { return false; }
            if (getClass() != obj.getClass()) { return false; }
            if (this.order == null) {
                if (((ARXOrderedString)obj).order != null) { 
                    return false;
                }
            } else {
                if (!((ARXOrderedString)obj).order.equals(this.order)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public String format(String s){
            if (order != null && !order.containsKey(s)) {
                throw new IllegalArgumentException("Unknown string '"+s+"'");
            }
        	return s;
        }
        
        @Override
        public DataTypeDescription<String> getDescription(){
            return description;
        }

        @Override
        public String getFormat() {
            if (order == null) return "";
            List<String> list = new ArrayList<String>();
            list.addAll(order.keySet());
            Collections.sort(list, new Comparator<String>(){
                @Override
                public int compare(String arg0, String arg1) {
                    return order.get(arg0).compareTo(order.get(arg1));
                } 
            });
            StringBuilder b = new StringBuilder();
            for (int i=0; i<list.size(); i++) {
                b.append(list.get(i));
                if (i<list.size()-1) {
                    b.append("\n");
                }
            }
            return b.toString();
        }
        
        @Override
        public int hashCode() {
            return ARXOrderedString.class.hashCode();
        }

        @Override
        public boolean isValid(String s) {
            if (order != null && !order.containsKey(s)) {
                return false;
            } else {
                return true;
            }
        }

        @Override
        public String parse(String s) {
            if (order != null && !order.containsKey(s)) {
                throw new IllegalArgumentException("Unknown string '"+s+"'");
            }
        	return s;
        }

        @Override
        public String toString() {
            return "OrderedString";
        }

        /**
         * Returns all elements backing this datatype
         * @return
         */
        public List<String> getElements() {
            List<String> result = new ArrayList<String>();
            if (order == null) {
                return result;
            }
            result.addAll(order.keySet());
            Collections.sort(result, new Comparator<String>(){
                @Override public int compare(String arg0, String arg1) {
                    return order.get(arg0).compareTo(order.get(arg1));
                }
            });
            return result;
        }
    }
    
    /**
     * Base class for string types
     * @author Fabian Prasser
     */
    public static class ARXString extends DataType<String> {
        
        private static final long serialVersionUID = 903334212175979691L;
        
        /** The description of the data type*/
        private static final DataTypeDescription<String> description = new DataTypeDescription<String>(String.class, "String", false, new ArrayList<String>()){
            /**
             * 
             */
            private static final long serialVersionUID = -6679110898204862834L;
            @Override public DataType<String> newInstance() { return STRING; }
            @Override public DataType<String> newInstance(String format) {return STRING;}
        };
        
        @Override
        public DataType<String> clone() {
            return this;
        }
        
        @Override
        public int compare(final String s1, final String s2) {
            return s1.compareTo(s2);
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) { return true; }
            if (obj == null) { return false; }
            if (getClass() != obj.getClass()) { return false; }
            return true;
        }

        @Override
        public String format(String s){
            return s;
        }
        
        @Override
        public DataTypeDescription<String> getDescription(){
            return description;
        }

        @Override
        public int hashCode() {
            return ARXString.class.hashCode();
        }
        
        @Override
        public boolean isValid(String s) {
            return true;
        }

        @Override
        public String parse(String s) {
            return s;
        }

        @Override
        public String toString() {
            return "String";
        }
    }
    
    /**
     * An entry in the list of available data types
     * @author Fabian Prasser
     * @param <T>
     */
    public static abstract class DataTypeDescription<T> implements Serializable {

        private static final long serialVersionUID = 6369986224526795419L;
        
        /** The wrapped java class*/
        private Class<?> clazz;
        /** If yes, a list of available formats*/
        private List<String> exampleFormats;
        /** Can the type be parameterized with a format string*/
        private boolean hasFormat;
        /** A human readable label*/
        private String label;
        
        /**
         * Internal constructor
         * @param label
         * @param hasFormat
         * @param exampleFormats
         */
        private DataTypeDescription(Class<T> clazz, String label, boolean hasFormat, List<String> exampleFormats) {
            this.clazz = clazz;
            this.label = label;
            this.hasFormat = hasFormat;
            this.exampleFormats = exampleFormats;
        }
        
        /**
         * Returns a list of example formats
         * @return
         */
        public List<String> getExampleFormats() {
            return exampleFormats;
        }
        
        /**
         * Returns a human readable label
         * @return
         */
        public String getLabel() {
            return label;
        }
        
        /**
         * Returns the wrapped java class
         * @return
         */
        public Class<?> getWrappedClass() {
            return clazz;
        }
        
        /**
         * Returns whether the type be parameterized with a format string. Note that every data type
         * can be instantiated without a format string, using a default format.
         * @return
         */
        public boolean hasFormat() {
            return hasFormat;
        }
        
        /**
         * Creates a new instance with default format string
         * @return
         */
        public abstract DataType<T> newInstance();
        
        /**
         * Creates a new instance with the given format string
         * @param format
         * @return
         */
        public abstract DataType<T> newInstance(String format);
    }

	/**
     * An interface for data types with format
     * @author Fabian Prasser
     */
    public static interface DataTypeWithFormat {
        public abstract String getFormat();
    }

    /**
     * An interface for data types with a ratio scale
     * @author Fabian Prasser
     *
     * @param <T>
     */
    public static interface DataTypeWithRatioScale<T> {
        
        public abstract T add(T augend, T addend);

        public abstract int compare(String s1, String s2) throws NumberFormatException,
                                                                 ParseException;

        public abstract int compare(T t1, T t2);

        public abstract String divide(String dividend, String divisor);
        
        public abstract T divide(T dividend, T divisor);
        
        public abstract String format(T t);

        public abstract DataTypeDescription<T> getDescription();

        public T getMaximum();

        public T getMinimum();
        
        public abstract boolean isValid(String s);

        public abstract String multiply(String multiplicand,
                                        String multiplicator);

        public abstract T multiply(T multiplicand, double multiplicator);

        public abstract T multiply(T multiplicand, int multiplicator);

        public abstract T multiply(T multiplicand, T multiplicator);

        public abstract T parse(String s);
        
        public abstract double ratio(T dividend, T divisor);
        
        public abstract T subtract(T minuend, T subtrahend);
    }
    
    private static final long serialVersionUID = -4380267779210935078L;

    /** A date data type with default format dd.mm.yyyy */
    public static final DataType<Date>               DATE    = new ARXDate();

    /** A generic decimal data type */
    public static final DataType<Double>             DECIMAL = new ARXDecimal();

    /** A generic integer data type */
    public static final DataType<Long>               INTEGER = new ARXInteger();

    /** A string data type */
    public static final DataType<String>             STRING  = new ARXString();

    /** A ordered string data type */
    public static final DataType<String>             ORDERED_STRING  = new ARXOrderedString();
    
    /**
     * A date data type with given format
     * 
     * @see SimpleDateFormat
     * @param format
     * @return
     */
    public static final DataType<Date> createDate(final String format) {
        return new ARXDate(format);
    }
    
    /**
     * A decimal data type with given format
     * 
     * @see DecimalFormat
     * @param format
     * @return
     */
    public static final DataType<Double> createDecimal(final String format) {
        return new ARXDecimal(format);
    }
    
    /**
     * An integer data type with given format
     * 
     * @see DecimalFormat
     * @param format
     * @return
     */
    public static final DataType<Long> createInteger(final String format) {
        return new ARXInteger(format);
    }
    
    /**
     * A ordered string type with given format. 
     * 
     * @param format List of ordered strings
     * @return
     */
    public static final DataType<String> createOrderedString(final List<String> format) {
        return new ARXOrderedString(format);
    }
    
    /**
     * A ordered string type with given format. 
     * 
     * @param format List of ordered strings separated by line feeds
     * @return
     */
    public static final DataType<String> createOrderedString(final String format) {
        return new ARXOrderedString(format);
    }
    
    /**
     * A ordered string type with given format. 
     * 
     * @param format List of ordered strings
     * @return
     */
    public static final DataType<String> createOrderedString(final String[] format) {
        return new ARXOrderedString(format);
    }
    
    /**
     * Lists all available data types
     * @return
     */
    public static final List<DataTypeDescription<?>> list(){
        List<DataTypeDescription<?>> list = new ArrayList<DataTypeDescription<?>>();
        list.add(STRING.getDescription());
        list.add(ORDERED_STRING.getDescription());
        list.add(DATE.getDescription());
        list.add(DECIMAL.getDescription());
        list.add(INTEGER.getDescription());
        return list;
    }

    /** 
     * Returns a datatype for the given class
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static final <U> DataTypeDescription<U> list(Class<U> clazz){
        for (DataTypeDescription<?> entry : list()) {
            if (entry.getWrappedClass() == clazz) {
                return (DataTypeDescription<U>)entry;
            }
        }
        return null;
    }
    
    /**
     * Provides a list of example formats for the <code>Date</code> data type
     * @return
     */
    private static List<String> listDateFormats(){
        List<String> result = new ArrayList<String>();
        result.add("yyyy-MM-dd'T'HH:mm:ss'Z'");
        result.add("yyyy-MM-ddZZ");
        result.add("yyyy-MM-dd'T'HH:mm:ssz");
        result.add("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        result.add("EEE MMM d hh:mm:ss z yyyy");
        result.add("EEE MMM dd HH:mm:ss yyyy");
        result.add("EEEE, dd-MMM-yy HH:mm:ss zzz");
        result.add("EEE, dd MMM yyyy HH:mm:ss zzz");
        result.add("EEE, dd MMM yy HH:mm:ss z");
        result.add("EEE, dd MMM yy HH:mm z");
        result.add("EEE, dd MMM yyyy HH:mm:ss z");
        result.add("yyyy-MM-dd'T'HH:mm:ss");
        result.add("EEE, dd MMM yyyy HH:mm:ss Z");
        result.add("dd MMM yy HH:mm:ss z");
        result.add("dd MMM yy HH:mm z");
        result.add("'T'HH:mm:ss");
        result.add("'T'HH:mm:ssZZ");
        result.add("HH:mm:ss");
        result.add("HH:mm:ssZZ");
        result.add("yyyy-MM-dd");
        result.add("yyyy-MM-dd hh:mm:ss");
        result.add("yyyy-MM-dd HH:mm:ss");
        result.add("yyyy-MM-dd'T'HH:mm:ssz");
        result.add("yyyy-MM-dd'T'HH:mm:ss");
        result.add("yyyy-MM-dd'T'HH:mm:ssZZ");
        result.add("yyyy-MM-dd");
        return result;
    }

    /**
     * Provides a list of example formats for the <code>Decimal</code> data type
     * @return
     */
    private static List<String> listDecimalFormats(){
        List<String> result = new ArrayList<String>();
        result.add("#,##0");
        result.add("#,##0.###");
        result.add("#,##0%");
        result.add("¤#,##0.00;(¤#,##0.00)");
        return result;
    }
    
    @Override
    public abstract DataType<T> clone();
    
    @Override
    public abstract int hashCode();
    
    @Override
    public abstract boolean equals(Object other);
    
    /**
     * Compares two values. The result is 0 if both values are equal, 
     * less than 0 if the first value is less than the second argument, 
     * and greater than 0 if the first value is greater than the second argument.
     * @param s1
     * @param s2
     * @return
     * @throws NumberFormatException
     * @throws ParseException
     */
    public abstract int compare(String s1, String s2) throws NumberFormatException, ParseException;
    
    /**
     * Returns a new function builder
     * @return
     */
    public AggregateFunctionBuilder<T> createAggregate(){
        return AggregateFunction.forType(this);
    }

    /**
     * Converts a value into a string
     * @param t
     * @return
     */
    public abstract String format(T t);
    
    /**
     * Returns a description of the data type
     * @return
     */
    public abstract DataTypeDescription<T> getDescription();

    /**
     * Compare
     * @param t1
     * @param t2
     * @return
     */
    public abstract int compare(T t1, T t2);

    /**
     * Checks whether the given string conforms to the data type's format
     * @param s
     * @return
     */
    public abstract boolean isValid(String s);
    
    /**
     * Converts a string into a value
     * @param s
     * @return
     */
    public abstract T parse(String s);
}
