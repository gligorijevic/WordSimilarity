/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webevo.logic.utility.schema.mapper.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Jelena
 */
public class CollectionsTool {
    public static String join( String delimiter, String[] array ) {
		StringBuilder sb = new StringBuilder();
		int counter=0;
		for ( String element : array ) {
			sb.append( counter++>0?delimiter:"" );
			sb.append( element );
		}
		return sb.toString();
	}

	public static String join( String delimiter, List<String> list ) {
		StringBuilder sb = new StringBuilder();
		int counter=0;
		for ( String element : list ) {
			sb.append( counter++>0?delimiter:"" );
			sb.append( element );
		}
		return sb.toString();
	}

	/**
	 * Create a new instance of list which has elements in reverse order
	 * @param list list of String
	 * @return reversed list
	 */
	public static List<String> reverse( List<String> list ) {
		List<String> reversedList = new ArrayList<>( list );
		Collections.reverse( reversedList );
		return reversedList;
	}
}
